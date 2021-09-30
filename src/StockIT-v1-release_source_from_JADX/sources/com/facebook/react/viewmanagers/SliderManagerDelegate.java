package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.SliderManagerInterface;

public class SliderManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & SliderManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public SliderManagerDelegate(U u) {
        super(u);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r6, java.lang.String r7, java.lang.Object r8) {
        /*
            r5 = this;
            int r0 = r7.hashCode()
            r1 = 0
            r2 = 1
            switch(r0) {
                case -1900655011: goto L_0x009d;
                case -1736983259: goto L_0x0091;
                case -1609594047: goto L_0x0087;
                case -1021497397: goto L_0x007d;
                case -981448432: goto L_0x0073;
                case -877170387: goto L_0x0067;
                case 3540684: goto L_0x005b;
                case 111972721: goto L_0x004f;
                case 270940796: goto L_0x0045;
                case 718061361: goto L_0x003b;
                case 1139400400: goto L_0x002e;
                case 1192487427: goto L_0x0023;
                case 1333596542: goto L_0x0018;
                case 1912319986: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x00a7
        L_0x000b:
            java.lang.String r0 = "thumbTintColor"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 11
            goto L_0x00a8
        L_0x0018:
            java.lang.String r0 = "minimumTrackImage"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 5
            goto L_0x00a8
        L_0x0023:
            java.lang.String r0 = "minimumValue"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 7
            goto L_0x00a8
        L_0x002e:
            java.lang.String r0 = "trackImage"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 12
            goto L_0x00a8
        L_0x003b:
            java.lang.String r0 = "maximumValue"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 4
            goto L_0x00a8
        L_0x0045:
            java.lang.String r0 = "disabled"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 0
            goto L_0x00a8
        L_0x004f:
            java.lang.String r0 = "value"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 13
            goto L_0x00a8
        L_0x005b:
            java.lang.String r0 = "step"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 8
            goto L_0x00a8
        L_0x0067:
            java.lang.String r0 = "testID"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 9
            goto L_0x00a8
        L_0x0073:
            java.lang.String r0 = "maximumTrackImage"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 2
            goto L_0x00a8
        L_0x007d:
            java.lang.String r0 = "minimumTrackTintColor"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 6
            goto L_0x00a8
        L_0x0087:
            java.lang.String r0 = "enabled"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 1
            goto L_0x00a8
        L_0x0091:
            java.lang.String r0 = "thumbImage"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 10
            goto L_0x00a8
        L_0x009d:
            java.lang.String r0 = "maximumTrackTintColor"
            boolean r0 = r7.equals(r0)
            if (r0 == 0) goto L_0x00a7
            r0 = 3
            goto L_0x00a8
        L_0x00a7:
            r0 = -1
        L_0x00a8:
            r3 = 0
            switch(r0) {
                case 0: goto L_0x0177;
                case 1: goto L_0x0166;
                case 2: goto L_0x015c;
                case 3: goto L_0x014c;
                case 4: goto L_0x0139;
                case 5: goto L_0x012f;
                case 6: goto L_0x011f;
                case 7: goto L_0x010d;
                case 8: goto L_0x00fb;
                case 9: goto L_0x00eb;
                case 10: goto L_0x00e0;
                case 11: goto L_0x00cf;
                case 12: goto L_0x00c4;
                case 13: goto L_0x00b2;
                default: goto L_0x00ad;
            }
        L_0x00ad:
            super.setProperty(r6, r7, r8)
            goto L_0x0187
        L_0x00b2:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            if (r8 != 0) goto L_0x00b9
            goto L_0x00bf
        L_0x00b9:
            java.lang.Double r8 = (java.lang.Double) r8
            double r3 = r8.doubleValue()
        L_0x00bf:
            r7.setValue(r6, r3)
            goto L_0x0187
        L_0x00c4:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            com.facebook.react.bridge.ReadableMap r8 = (com.facebook.react.bridge.ReadableMap) r8
            r7.setTrackImage(r6, r8)
            goto L_0x0187
        L_0x00cf:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            android.content.Context r0 = r6.getContext()
            java.lang.Integer r8 = com.facebook.react.bridge.ColorPropConverter.getColor(r8, r0)
            r7.setThumbTintColor(r6, r8)
            goto L_0x0187
        L_0x00e0:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            com.facebook.react.bridge.ReadableMap r8 = (com.facebook.react.bridge.ReadableMap) r8
            r7.setThumbImage(r6, r8)
            goto L_0x0187
        L_0x00eb:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            if (r8 != 0) goto L_0x00f4
            java.lang.String r8 = ""
            goto L_0x00f6
        L_0x00f4:
            java.lang.String r8 = (java.lang.String) r8
        L_0x00f6:
            r7.setTestID(r6, r8)
            goto L_0x0187
        L_0x00fb:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            if (r8 != 0) goto L_0x0102
            goto L_0x0108
        L_0x0102:
            java.lang.Double r8 = (java.lang.Double) r8
            double r3 = r8.doubleValue()
        L_0x0108:
            r7.setStep(r6, r3)
            goto L_0x0187
        L_0x010d:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            if (r8 != 0) goto L_0x0114
            goto L_0x011a
        L_0x0114:
            java.lang.Double r8 = (java.lang.Double) r8
            double r3 = r8.doubleValue()
        L_0x011a:
            r7.setMinimumValue(r6, r3)
            goto L_0x0187
        L_0x011f:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            android.content.Context r0 = r6.getContext()
            java.lang.Integer r8 = com.facebook.react.bridge.ColorPropConverter.getColor(r8, r0)
            r7.setMinimumTrackTintColor(r6, r8)
            goto L_0x0187
        L_0x012f:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            com.facebook.react.bridge.ReadableMap r8 = (com.facebook.react.bridge.ReadableMap) r8
            r7.setMinimumTrackImage(r6, r8)
            goto L_0x0187
        L_0x0139:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            if (r8 != 0) goto L_0x0142
            r0 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            goto L_0x0148
        L_0x0142:
            java.lang.Double r8 = (java.lang.Double) r8
            double r0 = r8.doubleValue()
        L_0x0148:
            r7.setMaximumValue(r6, r0)
            goto L_0x0187
        L_0x014c:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            android.content.Context r0 = r6.getContext()
            java.lang.Integer r8 = com.facebook.react.bridge.ColorPropConverter.getColor(r8, r0)
            r7.setMaximumTrackTintColor(r6, r8)
            goto L_0x0187
        L_0x015c:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            com.facebook.react.bridge.ReadableMap r8 = (com.facebook.react.bridge.ReadableMap) r8
            r7.setMaximumTrackImage(r6, r8)
            goto L_0x0187
        L_0x0166:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            if (r8 != 0) goto L_0x016d
            goto L_0x0173
        L_0x016d:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r2 = r8.booleanValue()
        L_0x0173:
            r7.setEnabled(r6, r2)
            goto L_0x0187
        L_0x0177:
            com.facebook.react.uimanager.BaseViewManagerInterface r7 = r5.mViewManager
            com.facebook.react.viewmanagers.SliderManagerInterface r7 = (com.facebook.react.viewmanagers.SliderManagerInterface) r7
            if (r8 != 0) goto L_0x017e
            goto L_0x0184
        L_0x017e:
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r1 = r8.booleanValue()
        L_0x0184:
            r7.setDisabled(r6, r1)
        L_0x0187:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.SliderManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
