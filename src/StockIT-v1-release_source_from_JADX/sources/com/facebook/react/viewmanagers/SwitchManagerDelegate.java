package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.SwitchManagerInterface;

public class SwitchManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & SwitchManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public SwitchManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r4, java.lang.String r5, java.lang.Object r6) {
        /*
            r3 = this;
            int r0 = r5.hashCode()
            r1 = 0
            switch(r0) {
                case -1742453971: goto L_0x0054;
                case 111972721: goto L_0x0049;
                case 270940796: goto L_0x003f;
                case 1084662482: goto L_0x0034;
                case 1296813577: goto L_0x002a;
                case 1327599912: goto L_0x001f;
                case 1912319986: goto L_0x0014;
                case 2113632767: goto L_0x0009;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x005f
        L_0x0009:
            java.lang.String r0 = "trackColorForTrue"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 7
            goto L_0x0060
        L_0x0014:
            java.lang.String r0 = "thumbTintColor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 4
            goto L_0x0060
        L_0x001f:
            java.lang.String r0 = "tintColor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 2
            goto L_0x0060
        L_0x002a:
            java.lang.String r0 = "onTintColor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 3
            goto L_0x0060
        L_0x0034:
            java.lang.String r0 = "trackColorForFalse"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 6
            goto L_0x0060
        L_0x003f:
            java.lang.String r0 = "disabled"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 0
            goto L_0x0060
        L_0x0049:
            java.lang.String r0 = "value"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 1
            goto L_0x0060
        L_0x0054:
            java.lang.String r0 = "thumbColor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x005f
            r0 = 5
            goto L_0x0060
        L_0x005f:
            r0 = -1
        L_0x0060:
            r2 = 0
            switch(r0) {
                case 0: goto L_0x00fa;
                case 1: goto L_0x00e9;
                case 2: goto L_0x00d4;
                case 3: goto L_0x00bf;
                case 4: goto L_0x00aa;
                case 5: goto L_0x0095;
                case 6: goto L_0x007f;
                case 7: goto L_0x0069;
                default: goto L_0x0064;
            }
        L_0x0064:
            super.setProperty(r4, r5, r6)
            goto L_0x010a
        L_0x0069:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x0070
            goto L_0x007a
        L_0x0070:
            java.lang.Double r6 = (java.lang.Double) r6
            int r6 = r6.intValue()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
        L_0x007a:
            r5.setTrackColorForTrue(r4, r2)
            goto L_0x010a
        L_0x007f:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x0086
            goto L_0x0090
        L_0x0086:
            java.lang.Double r6 = (java.lang.Double) r6
            int r6 = r6.intValue()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
        L_0x0090:
            r5.setTrackColorForFalse(r4, r2)
            goto L_0x010a
        L_0x0095:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x009c
            goto L_0x00a6
        L_0x009c:
            java.lang.Double r6 = (java.lang.Double) r6
            int r6 = r6.intValue()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
        L_0x00a6:
            r5.setThumbColor(r4, r2)
            goto L_0x010a
        L_0x00aa:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x00b1
            goto L_0x00bb
        L_0x00b1:
            java.lang.Double r6 = (java.lang.Double) r6
            int r6 = r6.intValue()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
        L_0x00bb:
            r5.setThumbTintColor(r4, r2)
            goto L_0x010a
        L_0x00bf:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x00c6
            goto L_0x00d0
        L_0x00c6:
            java.lang.Double r6 = (java.lang.Double) r6
            int r6 = r6.intValue()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
        L_0x00d0:
            r5.setOnTintColor(r4, r2)
            goto L_0x010a
        L_0x00d4:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x00db
            goto L_0x00e5
        L_0x00db:
            java.lang.Double r6 = (java.lang.Double) r6
            int r6 = r6.intValue()
            java.lang.Integer r2 = java.lang.Integer.valueOf(r6)
        L_0x00e5:
            r5.setTintColor(r4, r2)
            goto L_0x010a
        L_0x00e9:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x00f0
            goto L_0x00f6
        L_0x00f0:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r1 = r6.booleanValue()
        L_0x00f6:
            r5.setValue(r4, r1)
            goto L_0x010a
        L_0x00fa:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.SwitchManagerInterface r5 = (com.facebook.react.viewmanagers.SwitchManagerInterface) r5
            if (r6 != 0) goto L_0x0101
            goto L_0x0107
        L_0x0101:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r1 = r6.booleanValue()
        L_0x0107:
            r5.setDisabled(r4, r1)
        L_0x010a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.SwitchManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
