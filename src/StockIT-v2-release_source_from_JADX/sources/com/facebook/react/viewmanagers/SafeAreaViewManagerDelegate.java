package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.SafeAreaViewManagerInterface;

public class SafeAreaViewManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & SafeAreaViewManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public SafeAreaViewManagerDelegate(U u) {
        super(u);
    }

    public void setProperty(T t, String str, Object obj) {
        boolean z = false;
        if (((str.hashCode() == -1190582359 && str.equals("emulateUnlessSupported")) ? (char) 0 : 65535) != 0) {
            super.setProperty(t, str, obj);
            return;
        }
        SafeAreaViewManagerInterface safeAreaViewManagerInterface = (SafeAreaViewManagerInterface) this.mViewManager;
        if (obj != null) {
            z = ((Boolean) obj).booleanValue();
        }
        safeAreaViewManagerInterface.setEmulateUnlessSupported(t, z);
    }
}
