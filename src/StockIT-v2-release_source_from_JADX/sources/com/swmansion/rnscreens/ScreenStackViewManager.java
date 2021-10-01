package com.swmansion.rnscreens;

import android.view.View;
import android.view.ViewGroup;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;

@ReactModule(name = "RNSScreenStack")
public class ScreenStackViewManager extends ViewGroupManager<ScreenStack> {
    protected static final String REACT_CLASS = "RNSScreenStack";

    public String getName() {
        return REACT_CLASS;
    }

    public boolean needsCustomLayoutForChildren() {
        return true;
    }

    /* access modifiers changed from: protected */
    public ScreenStack createViewInstance(ThemedReactContext themedReactContext) {
        return new ScreenStack(themedReactContext);
    }

    public void addView(ScreenStack screenStack, View view, int i) {
        if (view instanceof Screen) {
            screenStack.addScreen((Screen) view, i);
            return;
        }
        throw new IllegalArgumentException("Attempt attach child that is not of type RNScreen");
    }

    public void removeViewAt(ScreenStack screenStack, int i) {
        prepareOutTransition(screenStack.getScreenAt(i));
        screenStack.removeScreenAt(i);
    }

    private void prepareOutTransition(Screen screen) {
        startTransitionRecursive(screen);
    }

    private void startTransitionRecursive(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            viewGroup.startViewTransition(childAt);
            if (childAt instanceof ScreenStackHeaderConfig) {
                startTransitionRecursive(((ScreenStackHeaderConfig) childAt).getToolbar());
            }
            if (childAt instanceof ViewGroup) {
                startTransitionRecursive((ViewGroup) childAt);
            }
        }
    }

    public int getChildCount(ScreenStack screenStack) {
        return screenStack.getScreenCount();
    }

    public View getChildAt(ScreenStack screenStack, int i) {
        return screenStack.getScreenAt(i);
    }
}
