package com.horcrux.svg;

import android.graphics.Path;
import android.graphics.RectF;
import java.util.ArrayList;

class PathParser {
    static ArrayList<PathElement> elements;

    /* renamed from: i */
    private static int f1135i;

    /* renamed from: l */
    private static int f1136l;
    private static Path mPath;
    private static boolean mPenDown;
    private static float mPenDownX;
    private static float mPenDownY;
    private static float mPenX;
    private static float mPenY;
    private static float mPivotX;
    private static float mPivotY;
    static float mScale;

    /* renamed from: s */
    private static String f1137s;

    private static boolean is_cmd(char c) {
        switch (c) {
            case 'A':
            case 'C':
            case 'H':
            case 'L':
            case 'M':
            case 'Q':
            case 'S':
            case 'T':
            case 'V':
            case 'Z':
            case 'a':
            case 'c':
            case 'h':
            case 'l':
            case 'm':
            case 'q':
            case 's':
            case 't':
            case 'v':
            case 'z':
                return true;
            default:
                return false;
        }
    }

    private static boolean is_number_start(char c) {
        return (c >= '0' && c <= '9') || c == '.' || c == '-' || c == '+';
    }

    PathParser() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00c8  */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00d1  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0108  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0115  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0122  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x012b  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0148  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0169  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x016e  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0179  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0186  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x019a  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01ae  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01ba  */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01ec  */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x020d  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x00b0 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x002f A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static android.graphics.Path parse(java.lang.String r25) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            elements = r0
            android.graphics.Path r0 = new android.graphics.Path
            r0.<init>()
            mPath = r0
            if (r25 != 0) goto L_0x0011
            return r0
        L_0x0011:
            int r0 = r25.length()
            f1136l = r0
            f1137s = r25
            r0 = 0
            f1135i = r0
            r1 = 0
            mPenX = r1
            mPenY = r1
            mPivotX = r1
            mPivotY = r1
            mPenDownX = r1
            mPenDownY = r1
            mPenDown = r0
            r2 = 32
            r3 = 32
        L_0x002f:
            int r4 = f1135i
            int r5 = f1136l
            if (r4 >= r5) goto L_0x0247
            skip_spaces()
            int r4 = f1135i
            int r5 = f1136l
            if (r4 < r5) goto L_0x0040
            goto L_0x0247
        L_0x0040:
            r4 = 1
            if (r3 == r2) goto L_0x0045
            r5 = 1
            goto L_0x0046
        L_0x0045:
            r5 = 0
        L_0x0046:
            java.lang.String r6 = f1137s
            int r7 = f1135i
            char r6 = r6.charAt(r7)
            r7 = 3
            java.lang.String r8 = "Unexpected character '%c' (i=%d, s=%s)"
            r9 = 2
            r10 = 109(0x6d, float:1.53E-43)
            r11 = 77
            if (r5 != 0) goto L_0x007b
            if (r6 == r11) goto L_0x007b
            if (r6 != r10) goto L_0x005d
            goto L_0x007b
        L_0x005d:
            java.lang.Error r1 = new java.lang.Error
            java.lang.Object[] r2 = new java.lang.Object[r7]
            java.lang.Character r3 = java.lang.Character.valueOf(r6)
            r2[r0] = r3
            int r0 = f1135i
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2[r4] = r0
            java.lang.String r0 = f1137s
            r2[r9] = r0
            java.lang.String r0 = java.lang.String.format(r8, r2)
            r1.<init>(r0)
            throw r1
        L_0x007b:
            boolean r12 = is_cmd(r6)
            if (r12 == 0) goto L_0x0089
            int r3 = f1135i
            int r3 = r3 + r4
            f1135i = r3
            r3 = r6
        L_0x0087:
            r5 = 0
            goto L_0x00a9
        L_0x0089:
            boolean r12 = is_number_start(r6)
            if (r12 == 0) goto L_0x0229
            if (r5 == 0) goto L_0x0229
            r5 = 90
            if (r3 == r5) goto L_0x0217
            r5 = 122(0x7a, float:1.71E-43)
            if (r3 == r5) goto L_0x0217
            if (r3 == r11) goto L_0x009d
            if (r3 != r10) goto L_0x0087
        L_0x009d:
            boolean r3 = is_absolute(r3)
            if (r3 == 0) goto L_0x00a6
            r3 = 76
            goto L_0x00a8
        L_0x00a6:
            r3 = 108(0x6c, float:1.51E-43)
        L_0x00a8:
            r5 = 1
        L_0x00a9:
            boolean r6 = is_absolute(r3)
            switch(r3) {
                case 65: goto L_0x01ec;
                case 67: goto L_0x01d0;
                case 72: goto L_0x01c6;
                case 76: goto L_0x01ba;
                case 77: goto L_0x01ae;
                case 81: goto L_0x019a;
                case 83: goto L_0x0186;
                case 84: goto L_0x0179;
                case 86: goto L_0x016e;
                case 90: goto L_0x0169;
                case 97: goto L_0x0148;
                case 99: goto L_0x012b;
                case 104: goto L_0x0122;
                case 108: goto L_0x0115;
                case 109: goto L_0x0108;
                case 113: goto L_0x00f3;
                case 115: goto L_0x00de;
                case 116: goto L_0x00d1;
                case 118: goto L_0x00c8;
                case 122: goto L_0x0169;
                default: goto L_0x00b0;
            }
        L_0x00b0:
            java.lang.Error r1 = new java.lang.Error
            java.lang.Object[] r2 = new java.lang.Object[r9]
            java.lang.Character r3 = java.lang.Character.valueOf(r3)
            r2[r0] = r3
            java.lang.String r0 = f1137s
            r2[r4] = r0
            java.lang.String r0 = "Unexpected comand '%c' (s=%s)"
            java.lang.String r0 = java.lang.String.format(r0, r2)
            r1.<init>(r0)
            throw r1
        L_0x00c8:
            float r4 = parse_list_number()
            line(r1, r4)
            goto L_0x020b
        L_0x00d1:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            smoothQuadraticBezierCurve(r4, r7)
            goto L_0x020b
        L_0x00de:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            float r8 = parse_list_number()
            float r9 = parse_list_number()
            smoothCurve(r4, r7, r8, r9)
            goto L_0x020b
        L_0x00f3:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            float r8 = parse_list_number()
            float r9 = parse_list_number()
            quadraticBezierCurve(r4, r7, r8, r9)
            goto L_0x020b
        L_0x0108:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            move(r4, r7)
            goto L_0x020b
        L_0x0115:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            line(r4, r7)
            goto L_0x020b
        L_0x0122:
            float r4 = parse_list_number()
            line(r4, r1)
            goto L_0x020b
        L_0x012b:
            float r12 = parse_list_number()
            float r13 = parse_list_number()
            float r14 = parse_list_number()
            float r15 = parse_list_number()
            float r16 = parse_list_number()
            float r17 = parse_list_number()
            curve(r12, r13, r14, r15, r16, r17)
            goto L_0x020b
        L_0x0148:
            float r18 = parse_list_number()
            float r19 = parse_list_number()
            float r20 = parse_list_number()
            boolean r21 = parse_flag()
            boolean r22 = parse_flag()
            float r23 = parse_list_number()
            float r24 = parse_list_number()
            arc(r18, r19, r20, r21, r22, r23, r24)
            goto L_0x020b
        L_0x0169:
            close()
            goto L_0x020b
        L_0x016e:
            float r4 = mPenX
            float r7 = parse_list_number()
            lineTo(r4, r7)
            goto L_0x020b
        L_0x0179:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            smoothQuadraticBezierCurveTo(r4, r7)
            goto L_0x020b
        L_0x0186:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            float r8 = parse_list_number()
            float r9 = parse_list_number()
            smoothCurveTo(r4, r7, r8, r9)
            goto L_0x020b
        L_0x019a:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            float r8 = parse_list_number()
            float r9 = parse_list_number()
            quadraticBezierCurveTo(r4, r7, r8, r9)
            goto L_0x020b
        L_0x01ae:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            moveTo(r4, r7)
            goto L_0x020b
        L_0x01ba:
            float r4 = parse_list_number()
            float r7 = parse_list_number()
            lineTo(r4, r7)
            goto L_0x020b
        L_0x01c6:
            float r4 = parse_list_number()
            float r7 = mPenY
            lineTo(r4, r7)
            goto L_0x020b
        L_0x01d0:
            float r12 = parse_list_number()
            float r13 = parse_list_number()
            float r14 = parse_list_number()
            float r15 = parse_list_number()
            float r16 = parse_list_number()
            float r17 = parse_list_number()
            curveTo(r12, r13, r14, r15, r16, r17)
            goto L_0x020b
        L_0x01ec:
            float r18 = parse_list_number()
            float r19 = parse_list_number()
            float r20 = parse_list_number()
            boolean r21 = parse_flag()
            boolean r22 = parse_flag()
            float r23 = parse_list_number()
            float r24 = parse_list_number()
            arcTo(r18, r19, r20, r21, r22, r23, r24)
        L_0x020b:
            if (r5 == 0) goto L_0x002f
            if (r6 == 0) goto L_0x0213
            r3 = 77
            goto L_0x002f
        L_0x0213:
            r3 = 109(0x6d, float:1.53E-43)
            goto L_0x002f
        L_0x0217:
            java.lang.Error r1 = new java.lang.Error
            java.lang.Object[] r2 = new java.lang.Object[r4]
            java.lang.String r3 = f1137s
            r2[r0] = r3
            java.lang.String r0 = "Unexpected number after 'z' (s=%s)"
            java.lang.String r0 = java.lang.String.format(r0, r2)
            r1.<init>(r0)
            throw r1
        L_0x0229:
            java.lang.Error r1 = new java.lang.Error
            java.lang.Object[] r2 = new java.lang.Object[r7]
            java.lang.Character r3 = java.lang.Character.valueOf(r6)
            r2[r0] = r3
            int r0 = f1135i
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)
            r2[r4] = r0
            java.lang.String r0 = f1137s
            r2[r9] = r0
            java.lang.String r0 = java.lang.String.format(r8, r2)
            r1.<init>(r0)
            throw r1
        L_0x0247:
            android.graphics.Path r0 = mPath
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PathParser.parse(java.lang.String):android.graphics.Path");
    }

    private static void move(float f, float f2) {
        moveTo(f + mPenX, f2 + mPenY);
    }

    private static void moveTo(float f, float f2) {
        mPenX = f;
        mPivotX = f;
        mPenDownX = f;
        mPenY = f2;
        mPivotY = f2;
        mPenDownY = f2;
        Path path = mPath;
        float f3 = mScale;
        path.moveTo(f * f3, f3 * f2);
        elements.add(new PathElement(ElementType.kCGPathElementMoveToPoint, new Point[]{new Point((double) f, (double) f2)}));
    }

    private static void line(float f, float f2) {
        lineTo(f + mPenX, f2 + mPenY);
    }

    private static void lineTo(float f, float f2) {
        setPenDown();
        mPenX = f;
        mPivotX = f;
        mPenY = f2;
        mPivotY = f2;
        Path path = mPath;
        float f3 = mScale;
        path.lineTo(f * f3, f3 * f2);
        elements.add(new PathElement(ElementType.kCGPathElementAddLineToPoint, new Point[]{new Point((double) f, (double) f2)}));
    }

    private static void curve(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = mPenX;
        float f8 = mPenY;
        curveTo(f + f7, f2 + f8, f3 + f7, f4 + f8, f5 + f7, f6 + f8);
    }

    private static void curveTo(float f, float f2, float f3, float f4, float f5, float f6) {
        mPivotX = f3;
        mPivotY = f4;
        cubicTo(f, f2, f3, f4, f5, f6);
    }

    private static void cubicTo(float f, float f2, float f3, float f4, float f5, float f6) {
        setPenDown();
        mPenX = f5;
        mPenY = f6;
        Path path = mPath;
        float f7 = mScale;
        path.cubicTo(f * f7, f2 * f7, f3 * f7, f4 * f7, f5 * f7, f6 * f7);
        elements.add(new PathElement(ElementType.kCGPathElementAddCurveToPoint, new Point[]{new Point((double) f, (double) f2), new Point((double) f3, (double) f4), new Point((double) f5, (double) f6)}));
    }

    private static void smoothCurve(float f, float f2, float f3, float f4) {
        float f5 = mPenX;
        float f6 = mPenY;
        smoothCurveTo(f + f5, f2 + f6, f3 + f5, f4 + f6);
    }

    private static void smoothCurveTo(float f, float f2, float f3, float f4) {
        mPivotX = f;
        mPivotY = f2;
        cubicTo((mPenX * 2.0f) - mPivotX, (mPenY * 2.0f) - mPivotY, f, f2, f3, f4);
    }

    private static void quadraticBezierCurve(float f, float f2, float f3, float f4) {
        float f5 = mPenX;
        float f6 = mPenY;
        quadraticBezierCurveTo(f + f5, f2 + f6, f3 + f5, f4 + f6);
    }

    private static void quadraticBezierCurveTo(float f, float f2, float f3, float f4) {
        mPivotX = f;
        mPivotY = f2;
        float f5 = f * 2.0f;
        float f6 = f2 * 2.0f;
        float f7 = (mPenX + f5) / 3.0f;
        float f8 = (mPenY + f6) / 3.0f;
        cubicTo(f7, f8, (f3 + f5) / 3.0f, (f4 + f6) / 3.0f, f3, f4);
    }

    private static void smoothQuadraticBezierCurve(float f, float f2) {
        smoothQuadraticBezierCurveTo(f + mPenX, f2 + mPenY);
    }

    private static void smoothQuadraticBezierCurveTo(float f, float f2) {
        quadraticBezierCurveTo((mPenX * 2.0f) - mPivotX, (mPenY * 2.0f) - mPivotY, f, f2);
    }

    private static void arc(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
        arcTo(f, f2, f3, z, z2, f4 + mPenX, f5 + mPenY);
    }

    private static void arcTo(float f, float f2, float f3, boolean z, boolean z2, float f4, float f5) {
        float f6;
        float f7;
        float f8;
        boolean z3 = z;
        boolean z4 = z2;
        float f9 = mPenX;
        float f10 = mPenY;
        float abs = Math.abs(f2 == 0.0f ? f == 0.0f ? f5 - f10 : f : f2);
        float abs2 = Math.abs(f == 0.0f ? f4 - f9 : f);
        if (abs2 == 0.0f || abs == 0.0f || (f4 == f9 && f5 == f10)) {
            lineTo(f4, f5);
            return;
        }
        float radians = (float) Math.toRadians((double) f3);
        double d = (double) radians;
        float cos = (float) Math.cos(d);
        float sin = (float) Math.sin(d);
        float f11 = f4 - f9;
        float f12 = f5 - f10;
        float f13 = ((cos * f11) / 2.0f) + ((sin * f12) / 2.0f);
        float f14 = -sin;
        float f15 = ((f14 * f11) / 2.0f) + ((cos * f12) / 2.0f);
        float f16 = abs2 * abs2;
        float f17 = f16 * abs * abs;
        float f18 = abs * abs * f13 * f13;
        float f19 = f16 * f15 * f15;
        float f20 = (f17 - f19) - f18;
        if (f20 < 0.0f) {
            f6 = f14;
            float sqrt = (float) Math.sqrt((double) (1.0f - (f20 / f17)));
            abs2 *= sqrt;
            abs *= sqrt;
            f7 = f11 / 2.0f;
            f8 = f12 / 2.0f;
        } else {
            f6 = f14;
            float sqrt2 = (float) Math.sqrt((double) (f20 / (f19 + f18)));
            if (z3 == z4) {
                sqrt2 = -sqrt2;
            }
            float f21 = (((-sqrt2) * f15) * abs2) / abs;
            float f22 = ((sqrt2 * f13) * abs) / abs2;
            f7 = ((cos * f21) - (sin * f22)) + (f11 / 2.0f);
            f8 = (f12 / 2.0f) + (f21 * sin) + (f22 * cos);
        }
        float f23 = cos / abs2;
        float f24 = sin / abs2;
        float f25 = f6 / abs;
        float f26 = cos / abs;
        float f27 = -f7;
        float f28 = -f8;
        float f29 = abs;
        float f30 = abs2;
        float f31 = radians;
        float atan2 = (float) Math.atan2((double) ((f25 * f27) + (f26 * f28)), (double) ((f27 * f23) + (f28 * f24)));
        float f32 = f11 - f7;
        float f33 = f12 - f8;
        float atan22 = (float) Math.atan2((double) ((f25 * f32) + (f26 * f33)), (double) ((f23 * f32) + (f24 * f33)));
        float f34 = f7 + f9;
        float f35 = f8 + f10;
        float f36 = f11 + f9;
        float f37 = f12 + f10;
        setPenDown();
        mPivotX = f36;
        mPenX = f36;
        mPivotY = f37;
        mPenY = f37;
        if (f30 == f29 && f31 == 0.0f) {
            float degrees = (float) Math.toDegrees((double) atan2);
            float abs3 = Math.abs((degrees - ((float) Math.toDegrees((double) atan22))) % 360.0f);
            if (!z ? abs3 > 180.0f : abs3 < 180.0f) {
                abs3 = 360.0f - abs3;
            }
            if (!z2) {
                abs3 = -abs3;
            }
            float f38 = mScale;
            mPath.arcTo(new RectF((f34 - f30) * f38, (f35 - f30) * f38, (f34 + f30) * f38, (f35 + f30) * f38), degrees, abs3);
            elements.add(new PathElement(ElementType.kCGPathElementAddCurveToPoint, new Point[]{new Point((double) f36, (double) f37)}));
            return;
        }
        arcToBezier(f34, f35, f30, f29, atan2, atan22, z2, f31);
    }

    private static void close() {
        if (mPenDown) {
            mPenX = mPenDownX;
            mPenY = mPenDownY;
            mPenDown = false;
            mPath.close();
            elements.add(new PathElement(ElementType.kCGPathElementCloseSubpath, new Point[]{new Point((double) mPenX, (double) mPenY)}));
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0068 A[LOOP:0: B:10:0x0066->B:11:0x0068, LOOP_END] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void arcToBezier(float r24, float r25, float r26, float r27, float r28, float r29, boolean r30, float r31) {
        /*
            r0 = r28
            r1 = r31
            double r1 = (double) r1
            double r3 = java.lang.Math.cos(r1)
            float r3 = (float) r3
            double r1 = java.lang.Math.sin(r1)
            float r1 = (float) r1
            float r2 = r3 * r26
            float r4 = -r1
            float r4 = r4 * r27
            float r1 = r1 * r26
            float r3 = r3 * r27
            float r5 = r29 - r0
            r6 = 4618760256179416344(0x401921fb54442d18, double:6.283185307179586)
            r8 = 0
            int r9 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r9 >= 0) goto L_0x002a
            if (r30 == 0) goto L_0x002a
            double r8 = (double) r5
            double r8 = r8 + r6
        L_0x0028:
            float r5 = (float) r8
            goto L_0x0033
        L_0x002a:
            int r8 = (r5 > r8 ? 1 : (r5 == r8 ? 0 : -1))
            if (r8 <= 0) goto L_0x0033
            if (r30 != 0) goto L_0x0033
            double r8 = (double) r5
            double r8 = r8 - r6
            goto L_0x0028
        L_0x0033:
            double r6 = (double) r5
            r8 = 4609753056924675352(0x3ff921fb54442d18, double:1.5707963267948966)
            double r6 = r6 / r8
            double r6 = round(r6)
            double r6 = java.lang.Math.abs(r6)
            double r6 = java.lang.Math.ceil(r6)
            int r6 = (int) r6
            float r7 = (float) r6
            float r5 = r5 / r7
            r7 = 4608683618675807573(0x3ff5555555555555, double:1.3333333333333333)
            r9 = 1082130432(0x40800000, float:4.0)
            float r9 = r5 / r9
            double r9 = (double) r9
            double r9 = java.lang.Math.tan(r9)
            double r9 = r9 * r7
            float r7 = (float) r9
            double r8 = (double) r0
            double r10 = java.lang.Math.cos(r8)
            float r10 = (float) r10
            double r8 = java.lang.Math.sin(r8)
            float r8 = (float) r8
            r11 = 0
        L_0x0066:
            if (r11 >= r6) goto L_0x0126
            float r12 = r7 * r8
            float r12 = r10 - r12
            float r10 = r10 * r7
            float r8 = r8 + r10
            float r0 = r0 + r5
            double r13 = (double) r0
            double r9 = java.lang.Math.cos(r13)
            float r10 = (float) r9
            double r13 = java.lang.Math.sin(r13)
            float r9 = (float) r13
            float r13 = r7 * r9
            float r13 = r13 + r10
            float r14 = r7 * r10
            float r14 = r9 - r14
            float r15 = r2 * r12
            float r15 = r24 + r15
            float r16 = r4 * r8
            float r15 = r15 + r16
            float r12 = r12 * r1
            float r12 = r25 + r12
            float r8 = r8 * r3
            float r12 = r12 + r8
            float r8 = r2 * r13
            float r8 = r24 + r8
            float r16 = r4 * r14
            float r8 = r8 + r16
            float r13 = r13 * r1
            float r13 = r25 + r13
            float r14 = r14 * r3
            float r13 = r13 + r14
            float r14 = r2 * r10
            float r14 = r24 + r14
            float r16 = r4 * r9
            float r14 = r14 + r16
            float r16 = r1 * r10
            float r16 = r25 + r16
            float r17 = r3 * r9
            r27 = r0
            float r0 = r16 + r17
            android.graphics.Path r16 = mPath
            float r17 = mScale
            float r18 = r15 * r17
            float r19 = r12 * r17
            float r20 = r8 * r17
            float r21 = r13 * r17
            float r22 = r14 * r17
            float r23 = r0 * r17
            r17 = r18
            r18 = r19
            r19 = r20
            r20 = r21
            r21 = r22
            r22 = r23
            r16.cubicTo(r17, r18, r19, r20, r21, r22)
            r31 = r1
            java.util.ArrayList<com.horcrux.svg.PathElement> r1 = elements
            r16 = r2
            com.horcrux.svg.PathElement r2 = new com.horcrux.svg.PathElement
            r17 = r3
            com.horcrux.svg.ElementType r3 = com.horcrux.svg.ElementType.kCGPathElementAddCurveToPoint
            r18 = r4
            r4 = 3
            com.horcrux.svg.Point[] r4 = new com.horcrux.svg.Point[r4]
            r29 = r5
            com.horcrux.svg.Point r5 = new com.horcrux.svg.Point
            r19 = r6
            r20 = r7
            double r6 = (double) r15
            r21 = r9
            r15 = r10
            double r9 = (double) r12
            r5.<init>(r6, r9)
            r6 = 0
            r4[r6] = r5
            com.horcrux.svg.Point r5 = new com.horcrux.svg.Point
            double r7 = (double) r8
            double r9 = (double) r13
            r5.<init>(r7, r9)
            r7 = 1
            r4[r7] = r5
            r5 = 2
            com.horcrux.svg.Point r7 = new com.horcrux.svg.Point
            double r8 = (double) r14
            double r12 = (double) r0
            r7.<init>(r8, r12)
            r4[r5] = r7
            r2.<init>(r3, r4)
            r1.add(r2)
            int r11 = r11 + 1
            r0 = r27
            r5 = r29
            r1 = r31
            r10 = r15
            r2 = r16
            r3 = r17
            r4 = r18
            r6 = r19
            r7 = r20
            r8 = r21
            goto L_0x0066
        L_0x0126:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.PathParser.arcToBezier(float, float, float, float, float, float, boolean, float):void");
    }

    private static void setPenDown() {
        if (!mPenDown) {
            mPenDownX = mPenX;
            mPenDownY = mPenY;
            mPenDown = true;
        }
    }

    private static double round(double d) {
        double pow = Math.pow(10.0d, 4.0d);
        return ((double) Math.round(d * pow)) / pow;
    }

    private static void skip_spaces() {
        while (true) {
            int i = f1135i;
            if (i < f1136l && Character.isWhitespace(f1137s.charAt(i))) {
                f1135i++;
            } else {
                return;
            }
        }
    }

    private static boolean is_absolute(char c) {
        return Character.isUpperCase(c);
    }

    private static boolean parse_flag() {
        skip_spaces();
        char charAt = f1137s.charAt(f1135i);
        if (charAt == '0' || charAt == '1') {
            int i = f1135i + 1;
            f1135i = i;
            if (i < f1136l && f1137s.charAt(i) == ',') {
                f1135i++;
            }
            skip_spaces();
            if (charAt == '1') {
                return true;
            }
            return false;
        }
        throw new Error(String.format("Unexpected flag '%c' (i=%d, s=%s)", new Object[]{Character.valueOf(charAt), Integer.valueOf(f1135i), f1137s}));
    }

    private static float parse_list_number() {
        if (f1135i != f1136l) {
            float parse_number = parse_number();
            skip_spaces();
            parse_list_separator();
            return parse_number;
        }
        throw new Error(String.format("Unexpected end (s=%s)", new Object[]{f1137s}));
    }

    private static float parse_number() {
        char charAt;
        skip_spaces();
        int i = f1135i;
        if (i != f1136l) {
            char charAt2 = f1137s.charAt(i);
            if (charAt2 == '-' || charAt2 == '+') {
                int i2 = f1135i + 1;
                f1135i = i2;
                charAt2 = f1137s.charAt(i2);
            }
            if (charAt2 >= '0' && charAt2 <= '9') {
                skip_digits();
                int i3 = f1135i;
                if (i3 < f1136l) {
                    charAt2 = f1137s.charAt(i3);
                }
            } else if (charAt2 != '.') {
                throw new Error(String.format("Invalid number formating character '%c' (i=%d, s=%s)", new Object[]{Character.valueOf(charAt2), Integer.valueOf(f1135i), f1137s}));
            }
            if (charAt2 == '.') {
                f1135i++;
                skip_digits();
                int i4 = f1135i;
                if (i4 < f1136l) {
                    charAt2 = f1137s.charAt(i4);
                }
            }
            if (charAt2 == 'e' || charAt2 == 'E') {
                int i5 = f1135i;
                if (!(i5 + 1 >= f1136l || (charAt = f1137s.charAt(i5 + 1)) == 'm' || charAt == 'x')) {
                    int i6 = f1135i + 1;
                    f1135i = i6;
                    char charAt3 = f1137s.charAt(i6);
                    if (charAt3 == '+' || charAt3 == '-') {
                        f1135i++;
                        skip_digits();
                    } else if (charAt3 < '0' || charAt3 > '9') {
                        throw new Error(String.format("Invalid number formating character '%c' (i=%d, s=%s)", new Object[]{Character.valueOf(charAt3), Integer.valueOf(f1135i), f1137s}));
                    } else {
                        skip_digits();
                    }
                }
            }
            String substring = f1137s.substring(i, f1135i);
            float parseFloat = Float.parseFloat(substring);
            if (!Float.isInfinite(parseFloat) && !Float.isNaN(parseFloat)) {
                return parseFloat;
            }
            throw new Error(String.format("Invalid number '%s' (start=%d, i=%d, s=%s)", new Object[]{substring, Integer.valueOf(i), Integer.valueOf(f1135i), f1137s}));
        }
        throw new Error(String.format("Unexpected end (s=%s)", new Object[]{f1137s}));
    }

    private static void parse_list_separator() {
        int i = f1135i;
        if (i < f1136l && f1137s.charAt(i) == ',') {
            f1135i++;
        }
    }

    private static void skip_digits() {
        while (true) {
            int i = f1135i;
            if (i < f1136l && Character.isDigit(f1137s.charAt(i))) {
                f1135i++;
            } else {
                return;
            }
        }
    }
}
