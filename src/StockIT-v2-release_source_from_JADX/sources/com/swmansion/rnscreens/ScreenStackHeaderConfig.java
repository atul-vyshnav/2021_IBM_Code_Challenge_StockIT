package com.swmansion.rnscreens;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import com.facebook.react.ReactApplication;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.views.text.ReactFontManager;
import com.swmansion.rnscreens.ScreenStackHeaderSubview;
import java.util.ArrayList;

public class ScreenStackHeaderConfig extends ViewGroup {
    private boolean mBackButtonInCustomView;
    private View.OnClickListener mBackClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            ScreenStackFragment screenFragment = ScreenStackHeaderConfig.this.getScreenFragment();
            if (screenFragment != null) {
                ScreenStack access$000 = ScreenStackHeaderConfig.this.getScreenStack();
                if (access$000 == null || access$000.getRootScreen() != screenFragment.getScreen()) {
                    screenFragment.dismiss();
                    return;
                }
                Fragment parentFragment = screenFragment.getParentFragment();
                if (parentFragment instanceof ScreenStackFragment) {
                    ((ScreenStackFragment) parentFragment).dismiss();
                }
            }
        }
    };
    private Integer mBackgroundColor;
    private final ArrayList<ScreenStackHeaderSubview> mConfigSubviews = new ArrayList<>(3);
    private int mDefaultStartInset;
    private int mDefaultStartInsetWithNavigation;
    private boolean mDestroyed;
    private String mDirection;
    private boolean mIsAttachedToWindow = false;
    private boolean mIsBackButtonHidden;
    private boolean mIsHidden;
    private boolean mIsShadowHidden;
    private boolean mIsTopInsetEnabled = true;
    private boolean mIsTranslucent;
    private int mScreenOrientation = -1;
    private int mTintColor;
    private String mTitle;
    private int mTitleColor;
    private String mTitleFontFamily;
    private float mTitleFontSize;
    private final Toolbar mToolbar;

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    private static class DebugMenuToolbar extends Toolbar {
        public DebugMenuToolbar(Context context) {
            super(context);
        }

        public boolean showOverflowMenu() {
            ((ReactApplication) getContext().getApplicationContext()).getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
            return true;
        }
    }

    public ScreenStackHeaderConfig(Context context) {
        super(context);
        setVisibility(8);
        Toolbar toolbar = new Toolbar(context);
        this.mToolbar = toolbar;
        this.mDefaultStartInset = toolbar.getContentInsetStart();
        this.mDefaultStartInsetWithNavigation = this.mToolbar.getContentInsetStartWithNavigation();
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(16843827, typedValue, true)) {
            this.mToolbar.setBackgroundColor(typedValue.data);
        }
        this.mToolbar.setClipChildren(false);
    }

    public void destroy() {
        this.mDestroyed = true;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsAttachedToWindow = true;
        onUpdate();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIsAttachedToWindow = false;
    }

    private Screen getScreen() {
        ViewParent parent = getParent();
        if (parent instanceof Screen) {
            return (Screen) parent;
        }
        return null;
    }

    /* access modifiers changed from: private */
    public ScreenStack getScreenStack() {
        Screen screen = getScreen();
        if (screen == null) {
            return null;
        }
        ScreenContainer container = screen.getContainer();
        if (container instanceof ScreenStack) {
            return (ScreenStack) container;
        }
        return null;
    }

    /* access modifiers changed from: protected */
    public ScreenStackFragment getScreenFragment() {
        ViewParent parent = getParent();
        if (!(parent instanceof Screen)) {
            return null;
        }
        ScreenFragment fragment = ((Screen) parent).getFragment();
        if (fragment instanceof ScreenStackFragment) {
            return (ScreenStackFragment) fragment;
        }
        return null;
    }

    public void onUpdate() {
        boolean z;
        AppCompatActivity appCompatActivity;
        Drawable navigationIcon;
        String str;
        Screen screen = (Screen) getParent();
        ScreenStack screenStack = getScreenStack();
        if (screenStack == null || screenStack.getTopScreen() == screen) {
            z = true;
        } else {
            z = false;
        }
        if (this.mIsAttachedToWindow && z && !this.mDestroyed && (appCompatActivity = (AppCompatActivity) getScreenFragment().getActivity()) != null) {
            if (Build.VERSION.SDK_INT >= 17 && (str = this.mDirection) != null) {
                if (str.equals("rtl")) {
                    this.mToolbar.setLayoutDirection(1);
                } else if (this.mDirection.equals("ltr")) {
                    this.mToolbar.setLayoutDirection(0);
                }
            }
            if (getScreenFragment() == null || !getScreenFragment().hasChildScreenWithConfig(getScreen())) {
                appCompatActivity.setRequestedOrientation(this.mScreenOrientation);
            }
            if (!this.mIsHidden) {
                if (this.mToolbar.getParent() == null) {
                    getScreenFragment().setToolbar(this.mToolbar);
                }
                if (this.mIsTopInsetEnabled) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        this.mToolbar.setPadding(0, getRootWindowInsets().getSystemWindowInsetTop(), 0, 0);
                    } else {
                        this.mToolbar.setPadding(0, (int) (getResources().getDisplayMetrics().density * 25.0f), 0, 0);
                    }
                } else if (this.mToolbar.getPaddingTop() > 0) {
                    this.mToolbar.setPadding(0, 0, 0, 0);
                }
                appCompatActivity.setSupportActionBar(this.mToolbar);
                ActionBar supportActionBar = appCompatActivity.getSupportActionBar();
                this.mToolbar.setContentInsetStartWithNavigation(this.mDefaultStartInsetWithNavigation);
                Toolbar toolbar = this.mToolbar;
                int i = this.mDefaultStartInset;
                toolbar.setContentInsetsRelative(i, i);
                supportActionBar.setDisplayHomeAsUpEnabled(getScreenFragment().canNavigateBack() && !this.mIsBackButtonHidden);
                this.mToolbar.setNavigationOnClickListener(this.mBackClickListener);
                getScreenFragment().setToolbarShadowHidden(this.mIsShadowHidden);
                getScreenFragment().setToolbarTranslucent(this.mIsTranslucent);
                supportActionBar.setTitle((CharSequence) this.mTitle);
                if (TextUtils.isEmpty(this.mTitle)) {
                    this.mToolbar.setContentInsetStartWithNavigation(0);
                }
                TextView titleTextView = getTitleTextView();
                int i2 = this.mTitleColor;
                if (i2 != 0) {
                    this.mToolbar.setTitleTextColor(i2);
                }
                if (titleTextView != null) {
                    if (this.mTitleFontFamily != null) {
                        titleTextView.setTypeface(ReactFontManager.getInstance().getTypeface(this.mTitleFontFamily, 0, getContext().getAssets()));
                    }
                    float f = this.mTitleFontSize;
                    if (f > 0.0f) {
                        titleTextView.setTextSize(f);
                    }
                }
                Integer num = this.mBackgroundColor;
                if (num != null) {
                    this.mToolbar.setBackgroundColor(num.intValue());
                }
                if (!(this.mTintColor == 0 || (navigationIcon = this.mToolbar.getNavigationIcon()) == null)) {
                    navigationIcon.setColorFilter(this.mTintColor, PorterDuff.Mode.SRC_ATOP);
                }
                for (int childCount = this.mToolbar.getChildCount() - 1; childCount >= 0; childCount--) {
                    if (this.mToolbar.getChildAt(childCount) instanceof ScreenStackHeaderSubview) {
                        this.mToolbar.removeViewAt(childCount);
                    }
                }
                int size = this.mConfigSubviews.size();
                for (int i3 = 0; i3 < size; i3++) {
                    ScreenStackHeaderSubview screenStackHeaderSubview = this.mConfigSubviews.get(i3);
                    ScreenStackHeaderSubview.Type type = screenStackHeaderSubview.getType();
                    if (type == ScreenStackHeaderSubview.Type.BACK) {
                        View childAt = screenStackHeaderSubview.getChildAt(0);
                        if (childAt instanceof ImageView) {
                            supportActionBar.setHomeAsUpIndicator(((ImageView) childAt).getDrawable());
                        } else {
                            throw new JSApplicationIllegalArgumentException("Back button header config view should have Image as first child");
                        }
                    } else {
                        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(-2, -1);
                        int i4 = C28602.$SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type[type.ordinal()];
                        if (i4 == 1) {
                            if (!this.mBackButtonInCustomView) {
                                this.mToolbar.setNavigationIcon((Drawable) null);
                            }
                            this.mToolbar.setTitle((CharSequence) null);
                            layoutParams.gravity = GravityCompat.START;
                        } else if (i4 == 2) {
                            layoutParams.gravity = GravityCompat.END;
                        } else if (i4 == 3) {
                            layoutParams.width = -1;
                            layoutParams.gravity = 1;
                            this.mToolbar.setTitle((CharSequence) null);
                        }
                        screenStackHeaderSubview.setLayoutParams(layoutParams);
                        this.mToolbar.addView(screenStackHeaderSubview);
                    }
                }
            } else if (this.mToolbar.getParent() != null) {
                getScreenFragment().removeToolbar();
            }
        }
    }

    /* renamed from: com.swmansion.rnscreens.ScreenStackHeaderConfig$2 */
    static /* synthetic */ class C28602 {
        static final /* synthetic */ int[] $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.swmansion.rnscreens.ScreenStackHeaderSubview$Type[] r0 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type = r0
                com.swmansion.rnscreens.ScreenStackHeaderSubview$Type r1 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.LEFT     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type     // Catch:{ NoSuchFieldError -> 0x001d }
                com.swmansion.rnscreens.ScreenStackHeaderSubview$Type r1 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.RIGHT     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$swmansion$rnscreens$ScreenStackHeaderSubview$Type     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.swmansion.rnscreens.ScreenStackHeaderSubview$Type r1 = com.swmansion.rnscreens.ScreenStackHeaderSubview.Type.CENTER     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenStackHeaderConfig.C28602.<clinit>():void");
        }
    }

    private void maybeUpdate() {
        if (getParent() != null && !this.mDestroyed) {
            onUpdate();
        }
    }

    public Toolbar getToolbar() {
        return this.mToolbar;
    }

    public ScreenStackHeaderSubview getConfigSubview(int i) {
        return this.mConfigSubviews.get(i);
    }

    public int getConfigSubviewsCount() {
        return this.mConfigSubviews.size();
    }

    public void removeConfigSubview(int i) {
        this.mConfigSubviews.remove(i);
        maybeUpdate();
    }

    public void removeAllConfigSubviews() {
        this.mConfigSubviews.clear();
        maybeUpdate();
    }

    public void addConfigSubview(ScreenStackHeaderSubview screenStackHeaderSubview, int i) {
        this.mConfigSubviews.add(i, screenStackHeaderSubview);
        maybeUpdate();
    }

    private TextView getTitleTextView() {
        int childCount = this.mToolbar.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mToolbar.getChildAt(i);
            if (childAt instanceof TextView) {
                TextView textView = (TextView) childAt;
                if (textView.getText().equals(this.mToolbar.getTitle())) {
                    return textView;
                }
            }
        }
        return null;
    }

    public int getScreenOrientation() {
        return this.mScreenOrientation;
    }

    public void setTitle(String str) {
        this.mTitle = str;
    }

    public void setTitleFontFamily(String str) {
        this.mTitleFontFamily = str;
    }

    public void setTitleFontSize(float f) {
        this.mTitleFontSize = f;
    }

    public void setTitleColor(int i) {
        this.mTitleColor = i;
    }

    public void setTintColor(int i) {
        this.mTintColor = i;
    }

    public void setTopInsetEnabled(boolean z) {
        this.mIsTopInsetEnabled = z;
    }

    public void setBackgroundColor(Integer num) {
        this.mBackgroundColor = num;
    }

    public void setHideShadow(boolean z) {
        this.mIsShadowHidden = z;
    }

    public void setHideBackButton(boolean z) {
        this.mIsBackButtonHidden = z;
    }

    public void setHidden(boolean z) {
        this.mIsHidden = z;
    }

    public void setTranslucent(boolean z) {
        this.mIsTranslucent = z;
    }

    public void setBackButtonInCustomView(boolean z) {
        this.mBackButtonInCustomView = z;
    }

    public void setDirection(String str) {
        this.mDirection = str;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setScreenOrientation(java.lang.String r6) {
        /*
            r5 = this;
            r0 = -1
            if (r6 != 0) goto L_0x0006
            r5.mScreenOrientation = r0
            return
        L_0x0006:
            int r1 = r6.hashCode()
            r2 = 0
            r3 = 6
            r4 = 1
            switch(r1) {
                case -1894896954: goto L_0x004d;
                case 96673: goto L_0x0043;
                case 729267099: goto L_0x0039;
                case 1430647483: goto L_0x002f;
                case 1651658175: goto L_0x0025;
                case 1730732811: goto L_0x001b;
                case 2118770584: goto L_0x0011;
                default: goto L_0x0010;
            }
        L_0x0010:
            goto L_0x0057
        L_0x0011:
            java.lang.String r1 = "landscape_right"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x0057
            r6 = 6
            goto L_0x0058
        L_0x001b:
            java.lang.String r1 = "landscape_left"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x0057
            r6 = 5
            goto L_0x0058
        L_0x0025:
            java.lang.String r1 = "portrait_up"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x0057
            r6 = 2
            goto L_0x0058
        L_0x002f:
            java.lang.String r1 = "landscape"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x0057
            r6 = 4
            goto L_0x0058
        L_0x0039:
            java.lang.String r1 = "portrait"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x0057
            r6 = 1
            goto L_0x0058
        L_0x0043:
            java.lang.String r1 = "all"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x0057
            r6 = 0
            goto L_0x0058
        L_0x004d:
            java.lang.String r1 = "portrait_down"
            boolean r6 = r6.equals(r1)
            if (r6 == 0) goto L_0x0057
            r6 = 3
            goto L_0x0058
        L_0x0057:
            r6 = -1
        L_0x0058:
            switch(r6) {
                case 0: goto L_0x0075;
                case 1: goto L_0x0071;
                case 2: goto L_0x006e;
                case 3: goto L_0x0069;
                case 4: goto L_0x0066;
                case 5: goto L_0x0061;
                case 6: goto L_0x005e;
                default: goto L_0x005b;
            }
        L_0x005b:
            r5.mScreenOrientation = r0
            goto L_0x0079
        L_0x005e:
            r5.mScreenOrientation = r2
            goto L_0x0079
        L_0x0061:
            r6 = 8
            r5.mScreenOrientation = r6
            goto L_0x0079
        L_0x0066:
            r5.mScreenOrientation = r3
            goto L_0x0079
        L_0x0069:
            r6 = 9
            r5.mScreenOrientation = r6
            goto L_0x0079
        L_0x006e:
            r5.mScreenOrientation = r4
            goto L_0x0079
        L_0x0071:
            r6 = 7
            r5.mScreenOrientation = r6
            goto L_0x0079
        L_0x0075:
            r6 = 10
            r5.mScreenOrientation = r6
        L_0x0079:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.swmansion.rnscreens.ScreenStackHeaderConfig.setScreenOrientation(java.lang.String):void");
    }
}
