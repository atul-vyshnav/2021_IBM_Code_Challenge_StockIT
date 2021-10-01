package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface;

public class AndroidDropdownPickerManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidDropdownPickerManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public AndroidDropdownPickerManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r7, java.lang.String r8, java.lang.Object r9) {
        /*
            r6 = this;
            int r0 = r8.hashCode()
            r1 = 0
            r2 = 4
            r3 = 3
            r4 = 2
            r5 = 1
            switch(r0) {
                case -1609594047: goto L_0x0035;
                case -979805852: goto L_0x002b;
                case 94842723: goto L_0x0021;
                case 100526016: goto L_0x0017;
                case 1191572123: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x003f
        L_0x000d:
            java.lang.String r0 = "selected"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 4
            goto L_0x0040
        L_0x0017:
            java.lang.String r0 = "items"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 2
            goto L_0x0040
        L_0x0021:
            java.lang.String r0 = "color"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 0
            goto L_0x0040
        L_0x002b:
            java.lang.String r0 = "prompt"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 3
            goto L_0x0040
        L_0x0035:
            java.lang.String r0 = "enabled"
            boolean r0 = r8.equals(r0)
            if (r0 == 0) goto L_0x003f
            r0 = 1
            goto L_0x0040
        L_0x003f:
            r0 = -1
        L_0x0040:
            if (r0 == 0) goto L_0x0089
            if (r0 == r5) goto L_0x0078
            if (r0 == r4) goto L_0x006e
            if (r0 == r3) goto L_0x005f
            if (r0 == r2) goto L_0x004e
            super.setProperty(r7, r8, r9)
            goto L_0x009e
        L_0x004e:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface) r8
            if (r9 != 0) goto L_0x0055
            goto L_0x005b
        L_0x0055:
            java.lang.Double r9 = (java.lang.Double) r9
            int r1 = r9.intValue()
        L_0x005b:
            r8.setSelected(r7, r1)
            goto L_0x009e
        L_0x005f:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface) r8
            if (r9 != 0) goto L_0x0068
            java.lang.String r9 = ""
            goto L_0x006a
        L_0x0068:
            java.lang.String r9 = (java.lang.String) r9
        L_0x006a:
            r8.setPrompt(r7, r9)
            goto L_0x009e
        L_0x006e:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface) r8
            com.facebook.react.bridge.ReadableArray r9 = (com.facebook.react.bridge.ReadableArray) r9
            r8.setItems(r7, r9)
            goto L_0x009e
        L_0x0078:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface) r8
            if (r9 != 0) goto L_0x007f
            goto L_0x0085
        L_0x007f:
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r5 = r9.booleanValue()
        L_0x0085:
            r8.setEnabled(r7, r5)
            goto L_0x009e
        L_0x0089:
            com.facebook.react.uimanager.BaseViewManagerInterface r8 = r6.mViewManager
            com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface r8 = (com.facebook.react.viewmanagers.AndroidDropdownPickerManagerInterface) r8
            if (r9 != 0) goto L_0x0091
            r9 = 0
            goto L_0x009b
        L_0x0091:
            java.lang.Double r9 = (java.lang.Double) r9
            int r9 = r9.intValue()
            java.lang.Integer r9 = java.lang.Integer.valueOf(r9)
        L_0x009b:
            r8.setColor(r7, r9)
        L_0x009e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidDropdownPickerManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
