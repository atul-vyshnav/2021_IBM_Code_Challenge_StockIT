package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface;

public class AndroidDrawerLayoutManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidDrawerLayoutManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public AndroidDrawerLayoutManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r7, java.lang.String r8, java.lang.Object r9) {
        /*
            r6 = this;
            int r0 = r8.hashCode()
            r1 = 5
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r0) {
                case -2082382380: goto L_0x003f;
                case -1233873500: goto L_0x0035;
                case -764307226: goto L_0x002b;
                case 268251989: goto L_0x0021;
                case 695891258: goto L_0x0017;
                case 1857208703: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x004a
        L_0x000d:
            java.lang.String r0 = "drawerLockMode"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 4
            goto L_0x004b
        L_0x0017:
            java.lang.String r0 = "drawerPosition"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 2
            goto L_0x004b
        L_0x0021:
            java.lang.String r0 = "drawerWidth"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 3
            goto L_0x004b
        L_0x002b:
            java.lang.String r0 = "keyboardDismissMode"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 0
            goto L_0x004b
        L_0x0035:
            java.lang.String r0 = "drawerBackgroundColor"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 1
            goto L_0x004b
        L_0x003f:
            java.lang.String r0 = "statusBarBackgroundColor"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 5
            goto L_0x004b
        L_0x004a:
            r0 = -1
        L_0x004b:
            if (r0 == 0) goto L_0x00a5
            if (r0 == r5) goto L_0x0095
            if (r0 == r4) goto L_0x008b
            if (r0 == r3) goto L_0x0075
            if (r0 == r2) goto L_0x006b
            if (r0 == r1) goto L_0x005b
            super.setProperty(r7, r8, r9)
            goto L_0x00ae
        L_0x005b:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface) r8
            android.content.Context r0 = r7.getContext()
            java.lang.Integer r9 = com.facebook.react.bridge.ColorPropConverter.getColor(r9, r0)
            r8.setStatusBarBackgroundColor(r7, r9)
            goto L_0x00ae
        L_0x006b:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface) r8
            java.lang.String r9 = (java.lang.String) r9
            r8.setDrawerLockMode(r7, r9)
            goto L_0x00ae
        L_0x0075:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface) r8
            if (r9 != 0) goto L_0x007d
            r9 = 0
            goto L_0x0087
        L_0x007d:
            java.lang.Double r9 = (java.lang.Double) r9
            float r9 = r9.floatValue()
            java.lang.Float r9 = java.lang.Float.valueOf(r9)
        L_0x0087:
            r8.setDrawerWidth(r7, r9)
            goto L_0x00ae
        L_0x008b:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface) r8
            java.lang.String r9 = (java.lang.String) r9
            r8.setDrawerPosition(r7, r9)
            goto L_0x00ae
        L_0x0095:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface) r8
            android.content.Context r0 = r7.getContext()
            java.lang.Integer r9 = com.facebook.react.bridge.ColorPropConverter.getColor(r9, r0)
            r8.setDrawerBackgroundColor(r7, r9)
            goto L_0x00ae
        L_0x00a5:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface) r8
            java.lang.String r9 = (java.lang.String) r9
            r8.setKeyboardDismissMode(r7, r9)
        L_0x00ae:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0027  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void receiveCommand(com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface<T> r3, T r4, java.lang.String r5, com.facebook.react.bridge.ReadableArray r6) {
        /*
            r2 = this;
            int r6 = r5.hashCode()
            r0 = -258774775(0xfffffffff0936909, float:-3.649702E29)
            r1 = 1
            if (r6 == r0) goto L_0x001a
            r0 = -83186725(0xfffffffffb0aabdb, float:-7.200226E35)
            if (r6 == r0) goto L_0x0010
            goto L_0x0024
        L_0x0010:
            java.lang.String r6 = "openDrawer"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0024
            r5 = 0
            goto L_0x0025
        L_0x001a:
            java.lang.String r6 = "closeDrawer"
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L_0x0024
            r5 = 1
            goto L_0x0025
        L_0x0024:
            r5 = -1
        L_0x0025:
            if (r5 == 0) goto L_0x002e
            if (r5 == r1) goto L_0x002a
            goto L_0x0031
        L_0x002a:
            r3.closeDrawer(r4)
            goto L_0x0031
        L_0x002e:
            r3.openDrawer(r4)
        L_0x0031:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerDelegate.receiveCommand(com.facebook.react.viewmanagers.AndroidDrawerLayoutManagerInterface, android.view.View, java.lang.String, com.facebook.react.bridge.ReadableArray):void");
    }
}
