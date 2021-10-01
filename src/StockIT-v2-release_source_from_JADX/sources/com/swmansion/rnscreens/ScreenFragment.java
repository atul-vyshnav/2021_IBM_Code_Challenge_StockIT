package com.swmansion.rnscreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.fragment.app.Fragment;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import java.util.ArrayList;
import java.util.List;

public class ScreenFragment extends Fragment {
    private List<ScreenContainer> mChildScreenContainers = new ArrayList();
    protected Screen mScreenView;

    protected static View recycleView(View view) {
        ViewParent parent = view.getParent();
        if (parent != null) {
            ViewGroup viewGroup = (ViewGroup) parent;
            viewGroup.endViewTransition(view);
            viewGroup.removeView(view);
        }
        view.setVisibility(0);
        return view;
    }

    public ScreenFragment() {
        throw new IllegalStateException("Screen fragments should never be restored. Follow instructions from https://github.com/software-mansion/react-native-screens/issues/17#issuecomment-424704067 to properly configure your main activity.");
    }

    public ScreenFragment(Screen screen) {
        this.mScreenView = screen;
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        this.mScreenView.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        frameLayout.addView(recycleView(this.mScreenView));
        return frameLayout;
    }

    public Screen getScreen() {
        return this.mScreenView;
    }

    public void onContainerUpdate() {
        ScreenStackHeaderConfig findHeaderConfig;
        if (!hasChildScreenWithConfig(getScreen()) && (findHeaderConfig = findHeaderConfig()) != null && findHeaderConfig.getScreenFragment().getActivity() != null) {
            findHeaderConfig.getScreenFragment().getActivity().setRequestedOrientation(findHeaderConfig.getScreenOrientation());
        }
    }

    private ScreenStackHeaderConfig findHeaderConfig() {
        ScreenStackHeaderConfig headerConfig;
        for (ViewParent container = getScreen().getContainer(); container != null; container = container.getParent()) {
            if ((container instanceof Screen) && (headerConfig = ((Screen) container).getHeaderConfig()) != null) {
                return headerConfig;
            }
        }
        return null;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:6:0x0016  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean hasChildScreenWithConfig(com.swmansion.rnscreens.Screen r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            com.swmansion.rnscreens.ScreenFragment r5 = r5.getFragment()
            java.util.List r5 = r5.getChildScreenContainers()
            java.util.Iterator r5 = r5.iterator()
        L_0x0010:
            boolean r1 = r5.hasNext()
            if (r1 == 0) goto L_0x0033
            java.lang.Object r1 = r5.next()
            com.swmansion.rnscreens.ScreenContainer r1 = (com.swmansion.rnscreens.ScreenContainer) r1
            com.swmansion.rnscreens.Screen r1 = r1.getTopScreen()
            if (r1 == 0) goto L_0x0027
            com.swmansion.rnscreens.ScreenStackHeaderConfig r2 = r1.getHeaderConfig()
            goto L_0x0028
        L_0x0027:
            r2 = 0
        L_0x0028:
            r3 = 1
            if (r2 == 0) goto L_0x002c
            return r3
        L_0x002c:
            boolean r1 = r4.hasChildScreenWithConfig(r1)
            if (r1 == 0) goto L_0x0010
            return r3
        L_0x0033:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenFragment.hasChildScreenWithConfig(com.swmansion.rnscreens.Screen):boolean");
    }

    public List<ScreenContainer> getChildScreenContainers() {
        return this.mChildScreenContainers;
    }

    /* access modifiers changed from: protected */
    public void dispatchOnWillAppear() {
        ((UIManagerModule) ((ReactContext) this.mScreenView.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ScreenWillAppearEvent(this.mScreenView.getId()));
        for (ScreenContainer next : this.mChildScreenContainers) {
            if (next.getScreenCount() > 0) {
                next.getScreenAt(next.getScreenCount() - 1).getFragment().dispatchOnWillAppear();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchOnAppear() {
        ((UIManagerModule) ((ReactContext) this.mScreenView.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ScreenAppearEvent(this.mScreenView.getId()));
        for (ScreenContainer next : this.mChildScreenContainers) {
            if (next.getScreenCount() > 0) {
                next.getScreenAt(next.getScreenCount() - 1).getFragment().dispatchOnAppear();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchOnWillDisappear() {
        ((UIManagerModule) ((ReactContext) this.mScreenView.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ScreenWillDisappearEvent(this.mScreenView.getId()));
        for (ScreenContainer next : this.mChildScreenContainers) {
            if (next.getScreenCount() > 0) {
                next.getScreenAt(next.getScreenCount() - 1).getFragment().dispatchOnWillDisappear();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchOnDisappear() {
        ((UIManagerModule) ((ReactContext) this.mScreenView.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ScreenDisappearEvent(this.mScreenView.getId()));
        for (ScreenContainer next : this.mChildScreenContainers) {
            if (next.getScreenCount() > 0) {
                next.getScreenAt(next.getScreenCount() - 1).getFragment().dispatchOnDisappear();
            }
        }
    }

    public void registerChildScreenContainer(ScreenContainer screenContainer) {
        this.mChildScreenContainers.add(screenContainer);
    }

    public void unregisterChildScreenContainer(ScreenContainer screenContainer) {
        this.mChildScreenContainers.remove(screenContainer);
    }

    public void onViewAnimationStart() {
        if (isResumed()) {
            dispatchOnWillAppear();
        } else {
            dispatchOnWillDisappear();
        }
    }

    public void onViewAnimationEnd() {
        if (isResumed()) {
            dispatchOnAppear();
        } else {
            dispatchOnDisappear();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        ScreenContainer container = this.mScreenView.getContainer();
        if (container == null || !container.hasScreen(this)) {
            ((UIManagerModule) ((ReactContext) this.mScreenView.getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new ScreenDismissedEvent(this.mScreenView.getId()));
        }
        this.mChildScreenContainers.clear();
    }
}
