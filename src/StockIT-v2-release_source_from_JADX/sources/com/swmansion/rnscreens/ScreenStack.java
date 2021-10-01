package com.swmansion.rnscreens;

import android.content.Context;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.swmansion.rnscreens.Screen;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ScreenStack extends ScreenContainer<ScreenStackFragment> {
    private static final String BACK_STACK_TAG = "RN_SCREEN_LAST";
    private final FragmentManager.OnBackStackChangedListener mBackStackListener = new FragmentManager.OnBackStackChangedListener() {
        public void onBackStackChanged() {
            if (ScreenStack.this.mFragmentManager.getBackStackEntryCount() == 0) {
                ScreenStack screenStack = ScreenStack.this;
                screenStack.dismiss(screenStack.mTopScreen);
            }
        }
    };
    private final Set<ScreenStackFragment> mDismissed = new HashSet();
    private final FragmentManager.FragmentLifecycleCallbacks mLifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
        public void onFragmentResumed(FragmentManager fragmentManager, Fragment fragment) {
            if (ScreenStack.this.mTopScreen == fragment) {
                ScreenStack screenStack = ScreenStack.this;
                screenStack.setupBackHandlerIfNeeded(screenStack.mTopScreen);
            }
        }
    };
    private boolean mRemovalTransitionStarted = false;
    private final ArrayList<ScreenStackFragment> mStack = new ArrayList<>();
    /* access modifiers changed from: private */
    public ScreenStackFragment mTopScreen = null;

    public ScreenStack(Context context) {
        super(context);
    }

    public void dismiss(ScreenStackFragment screenStackFragment) {
        this.mDismissed.add(screenStackFragment);
        markUpdated();
    }

    public Screen getTopScreen() {
        ScreenStackFragment screenStackFragment = this.mTopScreen;
        if (screenStackFragment != null) {
            return screenStackFragment.getScreen();
        }
        return null;
    }

    public Screen getRootScreen() {
        int screenCount = getScreenCount();
        for (int i = 0; i < screenCount; i++) {
            Screen screenAt = getScreenAt(i);
            if (!this.mDismissed.contains(screenAt.getFragment())) {
                return screenAt;
            }
        }
        throw new IllegalStateException("Stack has no root screen set");
    }

    /* access modifiers changed from: protected */
    public ScreenStackFragment adapt(Screen screen) {
        return new ScreenStackFragment(screen);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        if (this.mFragmentManager != null) {
            this.mFragmentManager.removeOnBackStackChangedListener(this.mBackStackListener);
            this.mFragmentManager.unregisterFragmentLifecycleCallbacks(this.mLifecycleCallbacks);
            if (!this.mFragmentManager.isStateSaved() && !this.mFragmentManager.isDestroyed()) {
                this.mFragmentManager.popBackStack(BACK_STACK_TAG, 1);
            }
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFragmentManager.registerFragmentLifecycleCallbacks(this.mLifecycleCallbacks, false);
    }

    public void startViewTransition(View view) {
        super.startViewTransition(view);
        this.mRemovalTransitionStarted = true;
    }

    public void endViewTransition(View view) {
        super.endViewTransition(view);
        if (this.mRemovalTransitionStarted) {
            this.mRemovalTransitionStarted = false;
            dispatchOnFinishTransitioning();
        }
    }

    public void onViewAppearTransitionEnd() {
        if (!this.mRemovalTransitionStarted) {
            dispatchOnFinishTransitioning();
        }
    }

    private void dispatchOnFinishTransitioning() {
        ((UIManagerModule) ((ReactContext) getContext()).getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new StackFinishTransitioningEvent(getId()));
    }

    /* access modifiers changed from: protected */
    public void removeScreenAt(int i) {
        this.mDismissed.remove(getScreenAt(i).getFragment());
        super.removeScreenAt(i);
    }

    /* access modifiers changed from: protected */
    public void removeAllScreens() {
        this.mDismissed.clear();
        super.removeAllScreens();
    }

    /* access modifiers changed from: protected */
    public boolean hasScreen(ScreenFragment screenFragment) {
        return super.hasScreen(screenFragment) && !this.mDismissed.contains(screenFragment);
    }

    /* access modifiers changed from: protected */
    public void performUpdate() {
        boolean z = true;
        int size = this.mScreenFragments.size() - 1;
        ScreenStackFragment screenStackFragment = null;
        final ScreenStackFragment screenStackFragment2 = null;
        while (true) {
            if (size < 0) {
                break;
            }
            ScreenStackFragment screenStackFragment3 = (ScreenStackFragment) this.mScreenFragments.get(size);
            if (!this.mDismissed.contains(screenStackFragment3)) {
                if (screenStackFragment2 != null) {
                    screenStackFragment = screenStackFragment3;
                    break;
                } else if (screenStackFragment3.getScreen().getStackPresentation() != Screen.StackPresentation.TRANSPARENT_MODAL) {
                    screenStackFragment2 = screenStackFragment3;
                    break;
                } else {
                    screenStackFragment2 = screenStackFragment3;
                }
            }
            size--;
        }
        boolean contains = this.mStack.contains(screenStackFragment2);
        int i = FragmentTransaction.TRANSIT_FRAGMENT_FADE;
        int i2 = 8194;
        if (contains) {
            ScreenStackFragment screenStackFragment4 = this.mTopScreen;
            if (screenStackFragment4 != null && !screenStackFragment4.equals(screenStackFragment2)) {
                int i3 = C28574.$SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation[this.mTopScreen.getScreen().getStackAnimation().ordinal()];
                if (i3 == 1) {
                    z = false;
                    i = 0;
                } else if (i3 != 2) {
                    if (i3 == 3) {
                        getOrCreateTransaction().setCustomAnimations(C2843R.anim.rns_slide_in_from_left, C2843R.anim.rns_slide_out_to_right);
                    } else if (i3 != 4) {
                        z = false;
                    } else {
                        getOrCreateTransaction().setCustomAnimations(C2843R.anim.rns_slide_in_from_right, C2843R.anim.rns_slide_out_to_left);
                    }
                    i = 8194;
                } else {
                    z = false;
                }
                if (!z) {
                    getOrCreateTransaction().setTransition(i);
                }
            }
        } else if (!(this.mTopScreen == null || screenStackFragment2 == null)) {
            if (this.mScreenFragments.contains(this.mTopScreen) || screenStackFragment2.getScreen().getReplaceAnimation() != Screen.ReplaceAnimation.POP) {
                i2 = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
            }
            int i4 = C28574.$SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation[screenStackFragment2.getScreen().getStackAnimation().ordinal()];
            if (i4 != 1) {
                if (i4 != 2) {
                    if (i4 == 3) {
                        getOrCreateTransaction().setCustomAnimations(C2843R.anim.rns_slide_in_from_right, C2843R.anim.rns_slide_out_to_left);
                    } else if (i4 != 4) {
                        i = i2;
                    } else {
                        getOrCreateTransaction().setCustomAnimations(C2843R.anim.rns_slide_in_from_left, C2843R.anim.rns_slide_out_to_right);
                    }
                    i = i2;
                }
                z = false;
            } else {
                z = false;
                i = 0;
            }
            if (!z) {
                getOrCreateTransaction().setTransition(i);
            }
        }
        Iterator<ScreenStackFragment> it = this.mStack.iterator();
        while (it.hasNext()) {
            ScreenStackFragment next = it.next();
            if (!this.mScreenFragments.contains(next) || this.mDismissed.contains(next)) {
                getOrCreateTransaction().remove(next);
            }
        }
        Iterator it2 = this.mScreenFragments.iterator();
        while (it2.hasNext()) {
            ScreenStackFragment screenStackFragment5 = (ScreenStackFragment) it2.next();
            if (!(screenStackFragment5 == screenStackFragment2 || screenStackFragment5 == screenStackFragment || this.mDismissed.contains(screenStackFragment5))) {
                getOrCreateTransaction().remove(screenStackFragment5);
            }
        }
        if (screenStackFragment != null && !screenStackFragment.isAdded()) {
            getOrCreateTransaction().add(getId(), (Fragment) screenStackFragment).runOnCommit(new Runnable() {
                public void run() {
                    screenStackFragment2.getScreen().bringToFront();
                }
            });
        }
        if (screenStackFragment2 != null && !screenStackFragment2.isAdded()) {
            getOrCreateTransaction().add(getId(), (Fragment) screenStackFragment2);
        }
        this.mTopScreen = screenStackFragment2;
        this.mStack.clear();
        this.mStack.addAll(this.mScreenFragments);
        tryCommitTransaction();
        ScreenStackFragment screenStackFragment6 = this.mTopScreen;
        if (screenStackFragment6 != null) {
            setupBackHandlerIfNeeded(screenStackFragment6);
        }
    }

    /* renamed from: com.swmansion.rnscreens.ScreenStack$4 */
    static /* synthetic */ class C28574 {
        static final /* synthetic */ int[] $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation;

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        static {
            /*
                com.swmansion.rnscreens.Screen$StackAnimation[] r0 = com.swmansion.rnscreens.Screen.StackAnimation.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation = r0
                com.swmansion.rnscreens.Screen$StackAnimation r1 = com.swmansion.rnscreens.Screen.StackAnimation.NONE     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation     // Catch:{ NoSuchFieldError -> 0x001d }
                com.swmansion.rnscreens.Screen$StackAnimation r1 = com.swmansion.rnscreens.Screen.StackAnimation.FADE     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.swmansion.rnscreens.Screen$StackAnimation r1 = com.swmansion.rnscreens.Screen.StackAnimation.SLIDE_FROM_RIGHT     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$swmansion$rnscreens$Screen$StackAnimation     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.swmansion.rnscreens.Screen$StackAnimation r1 = com.swmansion.rnscreens.Screen.StackAnimation.SLIDE_FROM_LEFT     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenStack.C28574.<clinit>():void");
        }
    }

    /* access modifiers changed from: protected */
    public void notifyContainerUpdate() {
        Iterator<ScreenStackFragment> it = this.mStack.iterator();
        while (it.hasNext()) {
            it.next().onContainerUpdate();
        }
    }

    /* access modifiers changed from: private */
    public void setupBackHandlerIfNeeded(ScreenStackFragment screenStackFragment) {
        if (this.mTopScreen.isResumed()) {
            this.mFragmentManager.removeOnBackStackChangedListener(this.mBackStackListener);
            this.mFragmentManager.popBackStack(BACK_STACK_TAG, 1);
            ScreenStackFragment screenStackFragment2 = null;
            int i = 0;
            int size = this.mStack.size();
            while (true) {
                if (i >= size) {
                    break;
                }
                ScreenStackFragment screenStackFragment3 = this.mStack.get(i);
                if (!this.mDismissed.contains(screenStackFragment3)) {
                    screenStackFragment2 = screenStackFragment3;
                    break;
                }
                i++;
            }
            if (screenStackFragment != screenStackFragment2 && screenStackFragment.isDismissable()) {
                this.mFragmentManager.beginTransaction().show(screenStackFragment).addToBackStack(BACK_STACK_TAG).setPrimaryNavigationFragment(screenStackFragment).commitAllowingStateLoss();
                this.mFragmentManager.addOnBackStackChangedListener(this.mBackStackListener);
            }
        }
    }
}
