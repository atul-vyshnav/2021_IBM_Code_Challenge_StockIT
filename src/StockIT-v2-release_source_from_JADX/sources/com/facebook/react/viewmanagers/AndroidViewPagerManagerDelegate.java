package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface;

public class AndroidViewPagerManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidViewPagerManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public AndroidViewPagerManagerDelegate(U u) {
        super(u);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: boolean} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r7, java.lang.String r8, java.lang.Object r9) {
        /*
            r6 = this;
            int r0 = r8.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            r5 = 0
            switch(r0) {
                case -1151046732: goto L_0x0035;
                case -764307226: goto L_0x002b;
                case 1097821469: goto L_0x0021;
                case 1233251315: goto L_0x0017;
                case 1919780198: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x003f
        L_0x000d:
            java.lang.String r0 = "peekEnabled"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 2
            goto L_0x0040
        L_0x0017:
            java.lang.String r0 = "initialPage"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 0
            goto L_0x0040
        L_0x0021:
            java.lang.String r0 = "pageMargin"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 1
            goto L_0x0040
        L_0x002b:
            java.lang.String r0 = "keyboardDismissMode"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 3
            goto L_0x0040
        L_0x0035:
            java.lang.String r0 = "scrollEnabled"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 4
            goto L_0x0040
        L_0x003f:
            r0 = -1
        L_0x0040:
            if (r0 == 0) goto L_0x008b
            if (r0 == r4) goto L_0x007a
            if (r0 == r3) goto L_0x0069
            if (r0 == r2) goto L_0x005f
            if (r0 == r1) goto L_0x004e
            super.setProperty(r7, r8, r9)
            goto L_0x009b
        L_0x004e:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface) r8
            if (r9 != 0) goto L_0x0055
            goto L_0x005b
        L_0x0055:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r4 = r9.booleanValue()
        L_0x005b:
            r8.setScrollEnabled(r7, r4)
            goto L_0x009b
        L_0x005f:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface) r8
            java.lang.String r9 = (java.lang.String) r9
            r8.setKeyboardDismissMode(r7, r9)
            goto L_0x009b
        L_0x0069:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface) r8
            if (r9 != 0) goto L_0x0070
            goto L_0x0076
        L_0x0070:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r5 = r9.booleanValue()
        L_0x0076:
            r8.setPeekEnabled(r7, r5)
            goto L_0x009b
        L_0x007a:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface) r8
            if (r9 != 0) goto L_0x0081
            goto L_0x0087
        L_0x0081:
            java.lang.Double r9 = (java.lang.Double) r9
            int r5 = r9.intValue()
        L_0x0087:
            r8.setPageMargin(r7, r5)
            goto L_0x009b
        L_0x008b:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface) r8
            if (r9 != 0) goto L_0x0092
            goto L_0x0098
        L_0x0092:
            java.lang.Double r9 = (java.lang.Double) r9
            int r5 = r9.intValue()
        L_0x0098:
            r8.setInitialPage(r7, r5)
        L_0x009b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidViewPagerManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0033  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void receiveCommand(com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface<T> r5, T r6, java.lang.String r7, com.facebook.react.bridge.ReadableArray r8) {
        /*
            r4 = this;
            int r0 = r7.hashCode()
            r1 = -445763635(0xffffffffe56e2fcd, float:-7.030031E22)
            r2 = 1
            r3 = 0
            if (r0 == r1) goto L_0x001b
            r1 = 1984860689(0x764e9211, float:1.0474372E33)
            if (r0 == r1) goto L_0x0011
            goto L_0x0025
        L_0x0011:
            java.lang.String r0 = "setPage"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0025
            r7 = 0
            goto L_0x0026
        L_0x001b:
            java.lang.String r0 = "setPageWithoutAnimation"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0025
            r7 = 1
            goto L_0x0026
        L_0x0025:
            r7 = -1
        L_0x0026:
            if (r7 == 0) goto L_0x0033
            if (r7 == r2) goto L_0x002b
            goto L_0x003a
        L_0x002b:
            int r7 = r8.getInt(r3)
            r5.setPageWithoutAnimation(r6, r7)
            goto L_0x003a
        L_0x0033:
            int r7 = r8.getInt(r3)
            r5.setPage(r6, r7)
        L_0x003a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidViewPagerManagerDelegate.receiveCommand(com.facebook.react.viewmanagers.AndroidViewPagerManagerInterface, android.view.View, java.lang.String, com.facebook.react.bridge.ReadableArray):void");
    }
}
