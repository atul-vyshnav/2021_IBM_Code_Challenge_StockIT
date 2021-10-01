package com.facebook.react.uimanager;

import android.view.View;
import com.facebook.react.uimanager.BaseViewManagerInterface;

public abstract class BaseViewManagerDelegate<T extends View, U extends BaseViewManagerInterface<T>> implements ViewManagerDelegate<T> {
    protected final U mViewManager;

    public BaseViewManagerDelegate(U u) {
        this.mViewManager = u;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: boolean} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r5, java.lang.String r6, java.lang.Object r7) {
        /*
            r4 = this;
            int r0 = r6.hashCode()
            r1 = 0
            switch(r0) {
                case -1721943862: goto L_0x011e;
                case -1721943861: goto L_0x0112;
                case -1267206133: goto L_0x0107;
                case -1228066334: goto L_0x00fc;
                case -908189618: goto L_0x00f1;
                case -908189617: goto L_0x00e6;
                case -877170387: goto L_0x00da;
                case -731417480: goto L_0x00ce;
                case -101663499: goto L_0x00c4;
                case -101359900: goto L_0x00b9;
                case -80891667: goto L_0x00ad;
                case -40300674: goto L_0x00a1;
                case -4379043: goto L_0x0095;
                case 36255470: goto L_0x008a;
                case 333432965: goto L_0x007e;
                case 581268560: goto L_0x0072;
                case 588239831: goto L_0x0066;
                case 746986311: goto L_0x005a;
                case 1052666732: goto L_0x004d;
                case 1146842694: goto L_0x0042;
                case 1153872867: goto L_0x0037;
                case 1287124693: goto L_0x002c;
                case 1349188574: goto L_0x0021;
                case 1505602511: goto L_0x0016;
                case 2045685618: goto L_0x000a;
                default: goto L_0x0008;
            }
        L_0x0008:
            goto L_0x012a
        L_0x000a:
            java.lang.String r0 = "nativeID"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 14
            goto L_0x012b
        L_0x0016:
            java.lang.String r0 = "accessibilityActions"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 0
            goto L_0x012b
        L_0x0021:
            java.lang.String r0 = "borderRadius"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 7
            goto L_0x012b
        L_0x002c:
            java.lang.String r0 = "backgroundColor"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 6
            goto L_0x012b
        L_0x0037:
            java.lang.String r0 = "accessibilityState"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 5
            goto L_0x012b
        L_0x0042:
            java.lang.String r0 = "accessibilityLabel"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 2
            goto L_0x012b
        L_0x004d:
            java.lang.String r0 = "transform"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 21
            goto L_0x012b
        L_0x005a:
            java.lang.String r0 = "importantForAccessibility"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 13
            goto L_0x012b
        L_0x0066:
            java.lang.String r0 = "borderBottomRightRadius"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 9
            goto L_0x012b
        L_0x0072:
            java.lang.String r0 = "borderBottomLeftRadius"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 8
            goto L_0x012b
        L_0x007e:
            java.lang.String r0 = "borderTopRightRadius"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 11
            goto L_0x012b
        L_0x008a:
            java.lang.String r0 = "accessibilityLiveRegion"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 3
            goto L_0x012b
        L_0x0095:
            java.lang.String r0 = "elevation"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 12
            goto L_0x012b
        L_0x00a1:
            java.lang.String r0 = "rotation"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 17
            goto L_0x012b
        L_0x00ad:
            java.lang.String r0 = "renderToHardwareTextureAndroid"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 16
            goto L_0x012b
        L_0x00b9:
            java.lang.String r0 = "accessibilityRole"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 4
            goto L_0x012b
        L_0x00c4:
            java.lang.String r0 = "accessibilityHint"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 1
            goto L_0x012b
        L_0x00ce:
            java.lang.String r0 = "zIndex"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 24
            goto L_0x012b
        L_0x00da:
            java.lang.String r0 = "testID"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 20
            goto L_0x012b
        L_0x00e6:
            java.lang.String r0 = "scaleY"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 19
            goto L_0x012b
        L_0x00f1:
            java.lang.String r0 = "scaleX"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 18
            goto L_0x012b
        L_0x00fc:
            java.lang.String r0 = "borderTopLeftRadius"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 10
            goto L_0x012b
        L_0x0107:
            java.lang.String r0 = "opacity"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 15
            goto L_0x012b
        L_0x0112:
            java.lang.String r0 = "translateY"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 23
            goto L_0x012b
        L_0x011e:
            java.lang.String r0 = "translateX"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x012a
            r6 = 22
            goto L_0x012b
        L_0x012a:
            r6 = -1
        L_0x012b:
            r0 = 1065353216(0x3f800000, float:1.0)
            r2 = 0
            r3 = 2143289344(0x7fc00000, float:NaN)
            switch(r6) {
                case 0: goto L_0x0273;
                case 1: goto L_0x026b;
                case 2: goto L_0x0263;
                case 3: goto L_0x025b;
                case 4: goto L_0x0253;
                case 5: goto L_0x024b;
                case 6: goto L_0x0236;
                case 7: goto L_0x0227;
                case 8: goto L_0x0218;
                case 9: goto L_0x0209;
                case 10: goto L_0x01f9;
                case 11: goto L_0x01e9;
                case 12: goto L_0x01d9;
                case 13: goto L_0x01d0;
                case 14: goto L_0x01c7;
                case 15: goto L_0x01b7;
                case 16: goto L_0x01a7;
                case 17: goto L_0x0197;
                case 18: goto L_0x0187;
                case 19: goto L_0x0177;
                case 20: goto L_0x016e;
                case 21: goto L_0x0165;
                case 22: goto L_0x0155;
                case 23: goto L_0x0145;
                case 24: goto L_0x0135;
                default: goto L_0x0133;
            }
        L_0x0133:
            goto L_0x027a
        L_0x0135:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x013a
            goto L_0x0140
        L_0x013a:
            java.lang.Double r7 = (java.lang.Double) r7
            float r2 = r7.floatValue()
        L_0x0140:
            r6.setZIndex(r5, r2)
            goto L_0x027a
        L_0x0145:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x014a
            goto L_0x0150
        L_0x014a:
            java.lang.Double r7 = (java.lang.Double) r7
            float r2 = r7.floatValue()
        L_0x0150:
            r6.setTranslateY(r5, r2)
            goto L_0x027a
        L_0x0155:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x015a
            goto L_0x0160
        L_0x015a:
            java.lang.Double r7 = (java.lang.Double) r7
            float r2 = r7.floatValue()
        L_0x0160:
            r6.setTranslateX(r5, r2)
            goto L_0x027a
        L_0x0165:
            U r6 = r4.mViewManager
            com.facebook.react.bridge.ReadableArray r7 = (com.facebook.react.bridge.ReadableArray) r7
            r6.setTransform(r5, r7)
            goto L_0x027a
        L_0x016e:
            U r6 = r4.mViewManager
            java.lang.String r7 = (java.lang.String) r7
            r6.setTestId(r5, r7)
            goto L_0x027a
        L_0x0177:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x017c
            goto L_0x0182
        L_0x017c:
            java.lang.Double r7 = (java.lang.Double) r7
            float r0 = r7.floatValue()
        L_0x0182:
            r6.setScaleY(r5, r0)
            goto L_0x027a
        L_0x0187:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x018c
            goto L_0x0192
        L_0x018c:
            java.lang.Double r7 = (java.lang.Double) r7
            float r0 = r7.floatValue()
        L_0x0192:
            r6.setScaleX(r5, r0)
            goto L_0x027a
        L_0x0197:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x019c
            goto L_0x01a2
        L_0x019c:
            java.lang.Double r7 = (java.lang.Double) r7
            float r2 = r7.floatValue()
        L_0x01a2:
            r6.setRotation(r5, r2)
            goto L_0x027a
        L_0x01a7:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x01ac
            goto L_0x01b2
        L_0x01ac:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r1 = r7.booleanValue()
        L_0x01b2:
            r6.setRenderToHardwareTexture(r5, r1)
            goto L_0x027a
        L_0x01b7:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x01bc
            goto L_0x01c2
        L_0x01bc:
            java.lang.Double r7 = (java.lang.Double) r7
            float r0 = r7.floatValue()
        L_0x01c2:
            r6.setOpacity(r5, r0)
            goto L_0x027a
        L_0x01c7:
            U r6 = r4.mViewManager
            java.lang.String r7 = (java.lang.String) r7
            r6.setNativeId(r5, r7)
            goto L_0x027a
        L_0x01d0:
            U r6 = r4.mViewManager
            java.lang.String r7 = (java.lang.String) r7
            r6.setImportantForAccessibility(r5, r7)
            goto L_0x027a
        L_0x01d9:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x01de
            goto L_0x01e4
        L_0x01de:
            java.lang.Double r7 = (java.lang.Double) r7
            float r2 = r7.floatValue()
        L_0x01e4:
            r6.setElevation(r5, r2)
            goto L_0x027a
        L_0x01e9:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x01ee
            goto L_0x01f4
        L_0x01ee:
            java.lang.Double r7 = (java.lang.Double) r7
            float r3 = r7.floatValue()
        L_0x01f4:
            r6.setBorderTopRightRadius(r5, r3)
            goto L_0x027a
        L_0x01f9:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x01fe
            goto L_0x0204
        L_0x01fe:
            java.lang.Double r7 = (java.lang.Double) r7
            float r3 = r7.floatValue()
        L_0x0204:
            r6.setBorderTopLeftRadius(r5, r3)
            goto L_0x027a
        L_0x0209:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x020e
            goto L_0x0214
        L_0x020e:
            java.lang.Double r7 = (java.lang.Double) r7
            float r3 = r7.floatValue()
        L_0x0214:
            r6.setBorderBottomRightRadius(r5, r3)
            goto L_0x027a
        L_0x0218:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x021d
            goto L_0x0223
        L_0x021d:
            java.lang.Double r7 = (java.lang.Double) r7
            float r3 = r7.floatValue()
        L_0x0223:
            r6.setBorderBottomLeftRadius(r5, r3)
            goto L_0x027a
        L_0x0227:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x022c
            goto L_0x0232
        L_0x022c:
            java.lang.Double r7 = (java.lang.Double) r7
            float r3 = r7.floatValue()
        L_0x0232:
            r6.setBorderRadius(r5, r3)
            goto L_0x027a
        L_0x0236:
            U r6 = r4.mViewManager
            if (r7 != 0) goto L_0x023b
            goto L_0x0247
        L_0x023b:
            android.content.Context r0 = r5.getContext()
            java.lang.Integer r7 = com.facebook.react.bridge.ColorPropConverter.getColor(r7, r0)
            int r1 = r7.intValue()
        L_0x0247:
            r6.setBackgroundColor(r5, r1)
            goto L_0x027a
        L_0x024b:
            U r6 = r4.mViewManager
            com.facebook.react.bridge.ReadableMap r7 = (com.facebook.react.bridge.ReadableMap) r7
            r6.setViewState(r5, r7)
            goto L_0x027a
        L_0x0253:
            U r6 = r4.mViewManager
            java.lang.String r7 = (java.lang.String) r7
            r6.setAccessibilityRole(r5, r7)
            goto L_0x027a
        L_0x025b:
            U r6 = r4.mViewManager
            java.lang.String r7 = (java.lang.String) r7
            r6.setAccessibilityLiveRegion(r5, r7)
            goto L_0x027a
        L_0x0263:
            U r6 = r4.mViewManager
            java.lang.String r7 = (java.lang.String) r7
            r6.setAccessibilityLabel(r5, r7)
            goto L_0x027a
        L_0x026b:
            U r6 = r4.mViewManager
            java.lang.String r7 = (java.lang.String) r7
            r6.setAccessibilityHint(r5, r7)
            goto L_0x027a
        L_0x0273:
            U r6 = r4.mViewManager
            com.facebook.react.bridge.ReadableArray r7 = (com.facebook.react.bridge.ReadableArray) r7
            r6.setAccessibilityActions(r5, r7)
        L_0x027a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.BaseViewManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }
}
