package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface;

public class ActivityIndicatorViewManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & ActivityIndicatorViewManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public ActivityIndicatorViewManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r6, java.lang.String r7, java.lang.Object r8) {
        /*
            r5 = this;
            int r0 = r7.hashCode()
            r1 = 3
            r2 = 2
            r3 = 1
            r4 = 0
            switch(r0) {
                case 3530753: goto L_0x002a;
                case 94842723: goto L_0x0020;
                case 865748226: goto L_0x0016;
                case 1118509918: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0034
        L_0x000c:
            java.lang.String r0 = "animating"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 1
            goto L_0x0035
        L_0x0016:
            java.lang.String r0 = "hidesWhenStopped"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 0
            goto L_0x0035
        L_0x0020:
            java.lang.String r0 = "color"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 2
            goto L_0x0035
        L_0x002a:
            java.lang.String r0 = "size"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x0034
            r0 = 3
            goto L_0x0035
        L_0x0034:
            r0 = -1
        L_0x0035:
            if (r0 == 0) goto L_0x006c
            if (r0 == r3) goto L_0x005b
            if (r0 == r2) goto L_0x004b
            if (r0 == r1) goto L_0x0041
            super.setProperty(r6, r7, r8)
            goto L_0x007c
        L_0x0041:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface r7 = (com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface) r7
            java.lang.String r8 = (java.lang.String) r8
            r7.setSize(r6, r8)
            goto L_0x007c
        L_0x004b:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface r7 = (com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface) r7
            android.content.Context r0 = r6.getContext()
            java.lang.Integer r8 = com.facebook.react.bridge.ColorPropConverter.getColor(r8, r0)
            r7.setColor(r6, r8)
            goto L_0x007c
        L_0x005b:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface r7 = (com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface) r7
            if (r8 != 0) goto L_0x0062
            goto L_0x0068
        L_0x0062:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r4 = r8.booleanValue()
        L_0x0068:
            r7.setAnimating(r6, r4)
            goto L_0x007c
        L_0x006c:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface r7 = (com.facebook.react.viewmanagers.ActivityIndicatorViewManagerInterface) r7
            if (r8 != 0) goto L_0x0073
            goto L_0x0079
        L_0x0073:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r4 = r8.booleanValue()
        L_0x0079:
            r7.setHidesWhenStopped(r6, r4)
        L_0x007c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.ActivityIndicatorViewManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
