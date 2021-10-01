package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.AndroidSwitchManagerInterface;

public class AndroidSwitchManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidSwitchManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public AndroidSwitchManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r4, java.lang.String r5, java.lang.Object r6) {
        /*
            r3 = this;
            int r0 = r5.hashCode()
            r1 = 1
            r2 = 0
            switch(r0) {
                case -1742453971: goto L_0x0060;
                case -1609594047: goto L_0x0056;
                case -287374307: goto L_0x004a;
                case 3551: goto L_0x0040;
                case 111972721: goto L_0x0035;
                case 270940796: goto L_0x002b;
                case 1084662482: goto L_0x0020;
                case 1912319986: goto L_0x0015;
                case 2113632767: goto L_0x000a;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x006b
        L_0x000a:
            java.lang.String r0 = "trackColorForTrue"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 4
            goto L_0x006c
        L_0x0015:
            java.lang.String r0 = "thumbTintColor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 7
            goto L_0x006c
        L_0x0020:
            java.lang.String r0 = "trackColorForFalse"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 3
            goto L_0x006c
        L_0x002b:
            java.lang.String r0 = "disabled"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 0
            goto L_0x006c
        L_0x0035:
            java.lang.String r0 = "value"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 5
            goto L_0x006c
        L_0x0040:
            java.lang.String r0 = "on"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 6
            goto L_0x006c
        L_0x004a:
            java.lang.String r0 = "trackTintColor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 8
            goto L_0x006c
        L_0x0056:
            java.lang.String r0 = "enabled"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 1
            goto L_0x006c
        L_0x0060:
            java.lang.String r0 = "thumbColor"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x006b
            r0 = 2
            goto L_0x006c
        L_0x006b:
            r0 = -1
        L_0x006c:
            switch(r0) {
                case 0: goto L_0x00f9;
                case 1: goto L_0x00e8;
                case 2: goto L_0x00d8;
                case 3: goto L_0x00c8;
                case 4: goto L_0x00b8;
                case 5: goto L_0x00a7;
                case 6: goto L_0x0096;
                case 7: goto L_0x0085;
                case 8: goto L_0x0074;
                default: goto L_0x006f;
            }
        L_0x006f:
            super.setProperty(r4, r5, r6)
            goto L_0x0109
        L_0x0074:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            android.content.Context r0 = r4.getContext()
            java.lang.Integer r6 = com.facebook.react.bridge.ColorPropConverter.getColor(r6, r0)
            r5.setTrackTintColor(r4, r6)
            goto L_0x0109
        L_0x0085:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            android.content.Context r0 = r4.getContext()
            java.lang.Integer r6 = com.facebook.react.bridge.ColorPropConverter.getColor(r6, r0)
            r5.setThumbTintColor(r4, r6)
            goto L_0x0109
        L_0x0096:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            if (r6 != 0) goto L_0x009d
            goto L_0x00a3
        L_0x009d:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r2 = r6.booleanValue()
        L_0x00a3:
            r5.setOn(r4, r2)
            goto L_0x0109
        L_0x00a7:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            if (r6 != 0) goto L_0x00ae
            goto L_0x00b4
        L_0x00ae:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r2 = r6.booleanValue()
        L_0x00b4:
            r5.setValue(r4, r2)
            goto L_0x0109
        L_0x00b8:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            android.content.Context r0 = r4.getContext()
            java.lang.Integer r6 = com.facebook.react.bridge.ColorPropConverter.getColor(r6, r0)
            r5.setTrackColorForTrue(r4, r6)
            goto L_0x0109
        L_0x00c8:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            android.content.Context r0 = r4.getContext()
            java.lang.Integer r6 = com.facebook.react.bridge.ColorPropConverter.getColor(r6, r0)
            r5.setTrackColorForFalse(r4, r6)
            goto L_0x0109
        L_0x00d8:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            android.content.Context r0 = r4.getContext()
            java.lang.Integer r6 = com.facebook.react.bridge.ColorPropConverter.getColor(r6, r0)
            r5.setThumbColor(r4, r6)
            goto L_0x0109
        L_0x00e8:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            if (r6 != 0) goto L_0x00ef
            goto L_0x00f5
        L_0x00ef:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r1 = r6.booleanValue()
        L_0x00f5:
            r5.setEnabled(r4, r1)
            goto L_0x0109
        L_0x00f9:
            com.facebook.react.uimanager.BaseViewManagerInterface r5 = r3.mViewManager
            com.facebook.react.viewmanagers.AndroidSwitchManagerInterface r5 = (com.facebook.react.viewmanagers.AndroidSwitchManagerInterface) r5
            if (r6 != 0) goto L_0x0100
            goto L_0x0106
        L_0x0100:
            java.lang.Boolean r6 = (java.lang.Boolean) r6
            boolean r2 = r6.booleanValue()
        L_0x0106:
            r5.setDisabled(r4, r2)
        L_0x0109:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidSwitchManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }

    public void receiveCommand(AndroidSwitchManagerInterface<T> androidSwitchManagerInterface, T t, String str, ReadableArray readableArray) {
        if (((str.hashCode() == -669744680 && str.equals("setNativeValue")) ? (char) 0 : 65535) == 0) {
            androidSwitchManagerInterface.setNativeValue(t, readableArray.getBoolean(0));
        }
    }
}
