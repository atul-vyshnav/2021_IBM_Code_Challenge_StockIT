package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.ProgressViewManagerInterface;

public class ProgressViewManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & ProgressViewManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public ProgressViewManagerDelegate(U u) {
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
                case -1948954017: goto L_0x0041;
                case -1001078227: goto L_0x0037;
                case -287374307: goto L_0x002c;
                case 760630062: goto L_0x0022;
                case 962728315: goto L_0x0018;
                case 1139400400: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x004b
        L_0x000d:
            java.lang.String r0 = "trackImage"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004b
            r0 = 5
            goto L_0x004c
        L_0x0018:
            java.lang.String r0 = "progressTintColor"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004b
            r0 = 2
            goto L_0x004c
        L_0x0022:
            java.lang.String r0 = "progressImage"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004b
            r0 = 4
            goto L_0x004c
        L_0x002c:
            java.lang.String r0 = "trackTintColor"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004b
            r0 = 3
            goto L_0x004c
        L_0x0037:
            java.lang.String r0 = "progress"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004b
            r0 = 1
            goto L_0x004c
        L_0x0041:
            java.lang.String r0 = "progressViewStyle"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x004b
            r0 = 0
            goto L_0x004c
        L_0x004b:
            r0 = -1
        L_0x004c:
            if (r0 == 0) goto L_0x00a2
            if (r0 == r5) goto L_0x0090
            if (r0 == r4) goto L_0x0080
            if (r0 == r3) goto L_0x0070
            if (r0 == r2) goto L_0x0066
            if (r0 == r1) goto L_0x005c
            super.setProperty(r7, r8, r9)
            goto L_0x00ab
        L_0x005c:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.ProgressViewManagerInterface r8 = (com.facebook.react.viewmanagers.ProgressViewManagerInterface) r8
            com.facebook.react.bridge.ReadableMap r9 = (com.facebook.react.bridge.ReadableMap) r9
            r8.setTrackImage(r7, r9)
            goto L_0x00ab
        L_0x0066:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.ProgressViewManagerInterface r8 = (com.facebook.react.viewmanagers.ProgressViewManagerInterface) r8
            com.facebook.react.bridge.ReadableMap r9 = (com.facebook.react.bridge.ReadableMap) r9
            r8.setProgressImage(r7, r9)
            goto L_0x00ab
        L_0x0070:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.ProgressViewManagerInterface r8 = (com.facebook.react.viewmanagers.ProgressViewManagerInterface) r8
            android.content.Context r0 = r7.getContext()
            java.lang.Integer r9 = com.facebook.react.bridge.ColorPropConverter.getColor(r9, r0)
            r8.setTrackTintColor(r7, r9)
            goto L_0x00ab
        L_0x0080:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.ProgressViewManagerInterface r8 = (com.facebook.react.viewmanagers.ProgressViewManagerInterface) r8
            android.content.Context r0 = r7.getContext()
            java.lang.Integer r9 = com.facebook.react.bridge.ColorPropConverter.getColor(r9, r0)
            r8.setProgressTintColor(r7, r9)
            goto L_0x00ab
        L_0x0090:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.ProgressViewManagerInterface r8 = (com.facebook.react.viewmanagers.ProgressViewManagerInterface) r8
            if (r9 != 0) goto L_0x0098
            r9 = 0
            goto L_0x009e
        L_0x0098:
            java.lang.Double r9 = (java.lang.Double) r9
            float r9 = r9.floatValue()
        L_0x009e:
            r8.setProgress(r7, r9)
            goto L_0x00ab
        L_0x00a2:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.ProgressViewManagerInterface r8 = (com.facebook.react.viewmanagers.ProgressViewManagerInterface) r8
            java.lang.String r9 = (java.lang.String) r9
            r8.setProgressViewStyle(r7, r9)
        L_0x00ab:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.ProgressViewManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
