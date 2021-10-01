package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface;

public class AndroidProgressBarManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidProgressBarManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public AndroidProgressBarManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r5, java.lang.String r6, java.lang.Object r7) {
        /*
            r4 = this;
            int r0 = r6.hashCode()
            r1 = 0
            r2 = 1
            switch(r0) {
                case -1001078227: goto L_0x0049;
                case -877170387: goto L_0x003e;
                case -676876213: goto L_0x0033;
                case 94842723: goto L_0x0029;
                case 633138363: goto L_0x001f;
                case 1118509918: goto L_0x0015;
                case 1804741442: goto L_0x000a;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x0053
        L_0x000a:
            java.lang.String r0 = "styleAttr"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0053
            r0 = 0
            goto L_0x0054
        L_0x0015:
            java.lang.String r0 = "animating"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0053
            r0 = 4
            goto L_0x0054
        L_0x001f:
            java.lang.String r0 = "indeterminate"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0053
            r0 = 2
            goto L_0x0054
        L_0x0029:
            java.lang.String r0 = "color"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0053
            r0 = 5
            goto L_0x0054
        L_0x0033:
            java.lang.String r0 = "typeAttr"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0053
            r0 = 1
            goto L_0x0054
        L_0x003e:
            java.lang.String r0 = "testID"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0053
            r0 = 6
            goto L_0x0054
        L_0x0049:
            java.lang.String r0 = "progress"
            boolean r0 = r6.equals(r0)
            if (r0 == 0) goto L_0x0053
            r0 = 3
            goto L_0x0054
        L_0x0053:
            r0 = -1
        L_0x0054:
            r3 = 0
            switch(r0) {
                case 0: goto L_0x00bf;
                case 1: goto L_0x00b1;
                case 2: goto L_0x00a0;
                case 3: goto L_0x008d;
                case 4: goto L_0x007c;
                case 5: goto L_0x006c;
                case 6: goto L_0x005d;
                default: goto L_0x0058;
            }
        L_0x0058:
            super.setProperty(r5, r6, r7)
            goto L_0x00cc
        L_0x005d:
            com.facebook.react.uimanager.BaseViewManagerInterface r6 = r4.mViewManager
            com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface r6 = (com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface) r6
            if (r7 != 0) goto L_0x0066
            java.lang.String r7 = ""
            goto L_0x0068
        L_0x0066:
            java.lang.String r7 = (java.lang.String) r7
        L_0x0068:
            r6.setTestID(r5, r7)
            goto L_0x00cc
        L_0x006c:
            com.facebook.react.uimanager.BaseViewManagerInterface r6 = r4.mViewManager
            com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface r6 = (com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface) r6
            android.content.Context r0 = r5.getContext()
            java.lang.Integer r7 = com.facebook.react.bridge.ColorPropConverter.getColor(r7, r0)
            r6.setColor(r5, r7)
            goto L_0x00cc
        L_0x007c:
            com.facebook.react.uimanager.BaseViewManagerInterface r6 = r4.mViewManager
            com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface r6 = (com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface) r6
            if (r7 != 0) goto L_0x0083
            goto L_0x0089
        L_0x0083:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r2 = r7.booleanValue()
        L_0x0089:
            r6.setAnimating(r5, r2)
            goto L_0x00cc
        L_0x008d:
            com.facebook.react.uimanager.BaseViewManagerInterface r6 = r4.mViewManager
            com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface r6 = (com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface) r6
            if (r7 != 0) goto L_0x0096
            r0 = 0
            goto L_0x009c
        L_0x0096:
            java.lang.Double r7 = (java.lang.Double) r7
            double r0 = r7.doubleValue()
        L_0x009c:
            r6.setProgress(r5, r0)
            goto L_0x00cc
        L_0x00a0:
            com.facebook.react.uimanager.BaseViewManagerInterface r6 = r4.mViewManager
            com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface r6 = (com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface) r6
            if (r7 != 0) goto L_0x00a7
            goto L_0x00ad
        L_0x00a7:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r1 = r7.booleanValue()
        L_0x00ad:
            r6.setIndeterminate(r5, r1)
            goto L_0x00cc
        L_0x00b1:
            com.facebook.react.uimanager.BaseViewManagerInterface r6 = r4.mViewManager
            com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface r6 = (com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface) r6
            if (r7 != 0) goto L_0x00b8
            goto L_0x00bb
        L_0x00b8:
            r3 = r7
            java.lang.String r3 = (java.lang.String) r3
        L_0x00bb:
            r6.setTypeAttr(r5, r3)
            goto L_0x00cc
        L_0x00bf:
            com.facebook.react.uimanager.BaseViewManagerInterface r6 = r4.mViewManager
            com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface r6 = (com.facebook.react.viewmanagers.AndroidProgressBarManagerInterface) r6
            if (r7 != 0) goto L_0x00c6
            goto L_0x00c9
        L_0x00c6:
            r3 = r7
            java.lang.String r3 = (java.lang.String) r3
        L_0x00c9:
            r6.setStyleAttr(r5, r3)
        L_0x00cc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidProgressBarManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
