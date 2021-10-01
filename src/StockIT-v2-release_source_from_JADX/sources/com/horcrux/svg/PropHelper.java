package com.horcrux.svg;

import com.facebook.react.bridge.ReadableArray;
import com.horcrux.svg.SVGLength;

class PropHelper {
    private static final int inputMatrixDataSize = 6;

    PropHelper() {
    }

    static int toMatrixData(ReadableArray readableArray, float[] fArr, float f) {
        int size = readableArray.size();
        if (size != 6) {
            return size;
        }
        fArr[0] = (float) readableArray.getDouble(0);
        fArr[1] = (float) readableArray.getDouble(2);
        fArr[2] = ((float) readableArray.getDouble(4)) * f;
        fArr[3] = (float) readableArray.getDouble(1);
        fArr[4] = (float) readableArray.getDouble(3);
        fArr[5] = ((float) readableArray.getDouble(5)) * f;
        return 6;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00bd, code lost:
        r0 = r8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c0, code lost:
        r12 = 1.0d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:54:0x00c1, code lost:
        r7 = java.lang.Double.valueOf(r7.substring(0, r0)).doubleValue() * r12;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static double fromRelative(java.lang.String r7, double r8, double r10, double r12) {
        /*
            java.lang.String r7 = r7.trim()
            int r0 = r7.length()
            int r1 = r0 + -1
            if (r0 == 0) goto L_0x00db
            java.lang.String r2 = "normal"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0016
            goto L_0x00db
        L_0x0016:
            int r2 = r7.codePointAt(r1)
            r3 = 37
            r4 = 0
            if (r2 != r3) goto L_0x0031
            java.lang.String r7 = r7.substring(r4, r1)
            java.lang.Double r7 = java.lang.Double.valueOf(r7)
            double r10 = r7.doubleValue()
            r12 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r10 = r10 / r12
            double r10 = r10 * r8
            return r10
        L_0x0031:
            int r8 = r0 + -2
            if (r8 <= 0) goto L_0x00d2
            java.lang.String r9 = r7.substring(r8)
            r1 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            r3 = -1
            int r5 = r9.hashCode()
            r6 = 3178(0xc6a, float:4.453E-42)
            if (r5 == r6) goto L_0x0099
            r6 = 3240(0xca8, float:4.54E-42)
            if (r5 == r6) goto L_0x008f
            r6 = 3365(0xd25, float:4.715E-42)
            if (r5 == r6) goto L_0x0085
            r6 = 3488(0xda0, float:4.888E-42)
            if (r5 == r6) goto L_0x007b
            r6 = 3571(0xdf3, float:5.004E-42)
            if (r5 == r6) goto L_0x0071
            r6 = 3588(0xe04, float:5.028E-42)
            if (r5 == r6) goto L_0x0067
            r6 = 3592(0xe08, float:5.033E-42)
            if (r5 == r6) goto L_0x005d
            goto L_0x00a2
        L_0x005d:
            java.lang.String r5 = "px"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x00a2
            r3 = 0
            goto L_0x00a2
        L_0x0067:
            java.lang.String r5 = "pt"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x00a2
            r3 = 2
            goto L_0x00a2
        L_0x0071:
            java.lang.String r5 = "pc"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x00a2
            r3 = 3
            goto L_0x00a2
        L_0x007b:
            java.lang.String r5 = "mm"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x00a2
            r3 = 4
            goto L_0x00a2
        L_0x0085:
            java.lang.String r5 = "in"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x00a2
            r3 = 6
            goto L_0x00a2
        L_0x008f:
            java.lang.String r5 = "em"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x00a2
            r3 = 1
            goto L_0x00a2
        L_0x0099:
            java.lang.String r5 = "cm"
            boolean r9 = r9.equals(r5)
            if (r9 == 0) goto L_0x00a2
            r3 = 5
        L_0x00a2:
            switch(r3) {
                case 0: goto L_0x00bf;
                case 1: goto L_0x00bd;
                case 2: goto L_0x00bb;
                case 3: goto L_0x00b8;
                case 4: goto L_0x00b2;
                case 5: goto L_0x00ac;
                case 6: goto L_0x00a6;
                default: goto L_0x00a5;
            }
        L_0x00a5:
            goto L_0x00c0
        L_0x00a6:
            r12 = 4636033603912859648(0x4056800000000000, double:90.0)
            goto L_0x00bd
        L_0x00ac:
            r12 = 4630183578586017914(0x4041b76ed677707a, double:35.43307)
            goto L_0x00bd
        L_0x00b2:
            r12 = 4615161236842447043(0x400c58b1572580c3, double:3.543307)
            goto L_0x00bd
        L_0x00b8:
            r12 = 4624633867356078080(0x402e000000000000, double:15.0)
            goto L_0x00bd
        L_0x00bb:
            r12 = 4608308318706860032(0x3ff4000000000000, double:1.25)
        L_0x00bd:
            r0 = r8
            goto L_0x00c1
        L_0x00bf:
            r0 = r8
        L_0x00c0:
            r12 = r1
        L_0x00c1:
            java.lang.String r7 = r7.substring(r4, r0)
            java.lang.Double r7 = java.lang.Double.valueOf(r7)
            double r7 = r7.doubleValue()
            double r7 = r7 * r12
        L_0x00cf:
            double r7 = r7 * r10
            return r7
        L_0x00d2:
            java.lang.Double r7 = java.lang.Double.valueOf(r7)
            double r7 = r7.doubleValue()
            goto L_0x00cf
        L_0x00db:
            r7 = 0
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PropHelper.fromRelative(java.lang.String, double, double, double):double");
    }

    static double fromRelative(SVGLength sVGLength, double d, double d2, double d3, double d4) {
        double d5;
        if (sVGLength == null) {
            return d2;
        }
        SVGLength.UnitType unitType = sVGLength.unit;
        double d6 = sVGLength.value;
        switch (C22411.$SwitchMap$com$horcrux$svg$SVGLength$UnitType[unitType.ordinal()]) {
            case 1:
            case 2:
                d4 = 1.0d;
                break;
            case 3:
                d5 = (d6 / 100.0d) * d;
                break;
            case 4:
                break;
            case 5:
                d4 /= 2.0d;
                break;
            case 6:
                d4 = 35.43307d;
                break;
            case 7:
                d4 = 3.543307d;
                break;
            case 8:
                d4 = 90.0d;
                break;
            case 9:
                d4 = 1.25d;
                break;
            case 10:
                d4 = 15.0d;
                break;
        }
        d6 *= d4;
        d5 = d6 * d3;
        return d5 + d2;
    }

    /* renamed from: com.horcrux.svg.PropHelper$1 */
    static /* synthetic */ class C22411 {
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$SVGLength$UnitType;

        /* JADX WARNING: Can't wrap try/catch for region: R(22:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|(3:21|22|24)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(24:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|24) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.horcrux.svg.SVGLength$UnitType[] r0 = com.horcrux.svg.SVGLength.UnitType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$horcrux$svg$SVGLength$UnitType = r0
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.NUMBER     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.PX     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.PERCENTAGE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.EMS     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x003e }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.EXS     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.CM     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.MM     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.IN     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x006c }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.PT     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.PC     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = $SwitchMap$com$horcrux$svg$SVGLength$UnitType     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.horcrux.svg.SVGLength$UnitType r1 = com.horcrux.svg.SVGLength.UnitType.UNKNOWN     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PropHelper.C22411.<clinit>():void");
        }
    }
}
