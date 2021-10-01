package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface;

public class AndroidDialogPickerManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidDialogPickerManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public AndroidDialogPickerManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r9, java.lang.String r10, java.lang.Object r11) {
        /*
            r8 = this;
            int r0 = r10.hashCode()
            r1 = 0
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r0) {
                case -1609594047: goto L_0x0040;
                case -979805852: goto L_0x0036;
                case 94842723: goto L_0x002c;
                case 100526016: goto L_0x0022;
                case 1191572123: goto L_0x0018;
                case 1287124693: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x004a
        L_0x000e:
            java.lang.String r0 = "backgroundColor"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 1
            goto L_0x004b
        L_0x0018:
            java.lang.String r0 = "selected"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 5
            goto L_0x004b
        L_0x0022:
            java.lang.String r0 = "items"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 3
            goto L_0x004b
        L_0x002c:
            java.lang.String r0 = "color"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 0
            goto L_0x004b
        L_0x0036:
            java.lang.String r0 = "prompt"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 4
            goto L_0x004b
        L_0x0040:
            java.lang.String r0 = "enabled"
            boolean r0 = r10.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 2
            goto L_0x004b
        L_0x004a:
            r0 = -1
        L_0x004b:
            r7 = 0
            if (r0 == 0) goto L_0x00ad
            if (r0 == r6) goto L_0x0098
            if (r0 == r5) goto L_0x0087
            if (r0 == r4) goto L_0x007d
            if (r0 == r3) goto L_0x006e
            if (r0 == r2) goto L_0x005d
            super.setProperty(r9, r10, r11)
            goto L_0x00c1
        L_0x005d:
            com.facebook.react.uimanager.BaseViewManagerInterface r10 = r8.mViewManager
            com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface r10 = (com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface) r10
            if (r11 != 0) goto L_0x0064
            goto L_0x006a
        L_0x0064:
            java.lang.Double r11 = (java.lang.Double) r11
            int r1 = r11.intValue()
        L_0x006a:
            r10.setSelected(r9, r1)
            goto L_0x00c1
        L_0x006e:
            com.facebook.react.uimanager.BaseViewManagerInterface r10 = r8.mViewManager
            com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface r10 = (com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface) r10
            if (r11 != 0) goto L_0x0077
            java.lang.String r11 = ""
            goto L_0x0079
        L_0x0077:
            java.lang.String r11 = (java.lang.String) r11
        L_0x0079:
            r10.setPrompt(r9, r11)
            goto L_0x00c1
        L_0x007d:
            com.facebook.react.uimanager.BaseViewManagerInterface r10 = r8.mViewManager
            com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface r10 = (com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface) r10
            com.facebook.react.bridge.ReadableArray r11 = (com.facebook.react.bridge.ReadableArray) r11
            r10.setItems(r9, r11)
            goto L_0x00c1
        L_0x0087:
            com.facebook.react.uimanager.BaseViewManagerInterface r10 = r8.mViewManager
            com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface r10 = (com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface) r10
            if (r11 != 0) goto L_0x008e
            goto L_0x0094
        L_0x008e:
            java.lang.Boolean r11 = (java.lang.Boolean) r11
            boolean r6 = r11.booleanValue()
        L_0x0094:
            r10.setEnabled(r9, r6)
            goto L_0x00c1
        L_0x0098:
            com.facebook.react.uimanager.BaseViewManagerInterface r10 = r8.mViewManager
            com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface r10 = (com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface) r10
            if (r11 != 0) goto L_0x009f
            goto L_0x00a9
        L_0x009f:
            java.lang.Double r11 = (java.lang.Double) r11
            int r11 = r11.intValue()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r11)
        L_0x00a9:
            r10.setBackgroundColor(r9, r7)
            goto L_0x00c1
        L_0x00ad:
            com.facebook.react.uimanager.BaseViewManagerInterface r10 = r8.mViewManager
            com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface r10 = (com.facebook.react.viewmanagers.AndroidDialogPickerManagerInterface) r10
            if (r11 != 0) goto L_0x00b4
            goto L_0x00be
        L_0x00b4:
            java.lang.Double r11 = (java.lang.Double) r11
            int r11 = r11.intValue()
            java.lang.Integer r7 = java.lang.Integer.valueOf(r11)
        L_0x00be:
            r10.setColor(r9, r7)
        L_0x00c1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidDialogPickerManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
