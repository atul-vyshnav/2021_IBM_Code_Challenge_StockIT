package com.horcrux.svg;

import android.view.View;
import java.util.ArrayList;

class TextLayoutAlgorithm {
    TextLayoutAlgorithm() {
    }

    class CharacterInformation {
        boolean addressable = true;
        double advance;
        boolean anchoredChunk = false;
        char character;
        TextView element;
        boolean firstCharacterInResolvedDescendant = false;
        boolean hidden = false;
        int index;
        boolean middle = false;
        boolean resolved = false;
        double rotate = 0.0d;
        boolean rotateSpecified = false;

        /* renamed from: x */
        double f1155x = 0.0d;
        boolean xSpecified = false;

        /* renamed from: y */
        double f1156y = 0.0d;
        boolean ySpecified = false;

        CharacterInformation(int i, char c) {
            this.index = i;
            this.character = c;
        }
    }

    class LayoutInput {
        boolean horizontal;
        TextView text;

        LayoutInput() {
        }
    }

    private void getSubTreeTypographicCharacterPositions(ArrayList<TextPathView> arrayList, ArrayList<TextView> arrayList2, StringBuilder sb, View view, TextPathView textPathView) {
        int i = 0;
        if (view instanceof TSpanView) {
            TSpanView tSpanView = (TSpanView) view;
            String str = tSpanView.mContent;
            if (str == null) {
                while (i < tSpanView.getChildCount()) {
                    getSubTreeTypographicCharacterPositions(arrayList, arrayList2, sb, tSpanView.getChildAt(i), textPathView);
                    i++;
                }
                return;
            }
            while (i < str.length()) {
                arrayList2.add(tSpanView);
                arrayList.add(textPathView);
                i++;
            }
            sb.append(str);
            return;
        }
        if (view instanceof TextPathView) {
            textPathView = (TextPathView) view;
        }
        while (i < textPathView.getChildCount()) {
            getSubTreeTypographicCharacterPositions(arrayList, arrayList2, sb, textPathView.getChildAt(i), textPathView);
            i++;
        }
    }

    /* JADX WARNING: type inference failed for: r3v3 */
    /* JADX WARNING: type inference failed for: r3v4, types: [android.graphics.Canvas, android.graphics.Paint] */
    /* JADX WARNING: type inference failed for: r3v5 */
    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x019a, code lost:
        if (r18 == Double.POSITIVE_INFINITY) goto L_0x019f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x01a1, code lost:
        if (r4 == (r10 - 1)) goto L_0x01a3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x01a3, code lost:
        r12 = com.horcrux.svg.TextProperties.TextAnchor.start;
        r13 = com.horcrux.svg.TextProperties.Direction.ltr;
        r14 = r10 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x01a9, code lost:
        if (r4 != r14) goto L_0x01af;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01ab, code lost:
        r16 = r0;
        r18 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:69:0x01af, code lost:
        r2 = r11[r8].f1155x;
        r12 = com.horcrux.svg.TextLayoutAlgorithm.C22561.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[r12.ordinal()];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x01bc, code lost:
        if (r12 == 1) goto L_0x01d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01bf, code lost:
        if (r12 == 2) goto L_0x01ca;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x01c2, code lost:
        if (r12 == 3) goto L_0x01c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x01c7, code lost:
        if (r13 != com.horcrux.svg.TextProperties.Direction.ltr) goto L_0x01d6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x01ca, code lost:
        r12 = com.horcrux.svg.TextProperties.Direction.ltr;
        r2 = r2 - ((r18 + r16) / 2.0d);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x01d4, code lost:
        if (r13 != com.horcrux.svg.TextProperties.Direction.ltr) goto L_0x01d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x01d6, code lost:
        r2 = r2 - r18;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x01d9, code lost:
        r2 = r2 - r16;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01db, code lost:
        if (r4 != r14) goto L_0x01df;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01dd, code lost:
        r12 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01df, code lost:
        r12 = r4 - 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01e1, code lost:
        if (r8 > r12) goto L_0x01ed;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01e3, code lost:
        r11[r8].f1155x += r2;
        r8 = r8 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01ed, code lost:
        r8 = r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01ee, code lost:
        r20 = r16;
        r16 = r0;
     */
    /* JADX WARNING: Removed duplicated region for block: B:145:0x02e7  */
    /* JADX WARNING: Removed duplicated region for block: B:146:0x0318  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.horcrux.svg.TextLayoutAlgorithm.CharacterInformation[] layoutText(com.horcrux.svg.TextLayoutAlgorithm.LayoutInput r27) {
        /*
            r26 = this;
            r7 = r26
            r0 = r27
            com.horcrux.svg.TextView r8 = r0.text
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r5 = 0
            r0 = r26
            r1 = r9
            r3 = r6
            r4 = r8
            r0.getSubTreeTypographicCharacterPositions(r1, r2, r3, r4, r5)
            java.lang.String r0 = r6.toString()
            char[] r0 = r0.toCharArray()
            int r10 = r0.length
            com.horcrux.svg.TextLayoutAlgorithm$CharacterInformation[] r11 = new com.horcrux.svg.TextLayoutAlgorithm.CharacterInformation[r10]
            r12 = 0
            r1 = 0
        L_0x002b:
            if (r1 >= r10) goto L_0x0039
            com.horcrux.svg.TextLayoutAlgorithm$CharacterInformation r2 = new com.horcrux.svg.TextLayoutAlgorithm$CharacterInformation
            char r3 = r0[r1]
            r2.<init>(r1, r3)
            r11[r1] = r2
            int r1 = r1 + 1
            goto L_0x002b
        L_0x0039:
            if (r10 != 0) goto L_0x003c
            return r11
        L_0x003c:
            android.graphics.PointF[] r13 = new android.graphics.PointF[r10]
            r0 = 0
        L_0x003f:
            r14 = 0
            if (r0 >= r10) goto L_0x004c
            android.graphics.PointF r1 = new android.graphics.PointF
            r1.<init>(r14, r14)
            r13[r0] = r1
            int r0 = r0 + 1
            goto L_0x003f
        L_0x004c:
            r0 = 0
        L_0x004d:
            r15 = 1
            if (r0 >= r10) goto L_0x0080
            r1 = r11[r0]
            r1.addressable = r15
            r1 = r11[r0]
            r1.middle = r12
            r1 = r11[r0]
            if (r0 != 0) goto L_0x005d
            goto L_0x005e
        L_0x005d:
            r15 = 0
        L_0x005e:
            r1.anchoredChunk = r15
            r1 = r11[r0]
            boolean r1 = r1.addressable
            if (r1 == 0) goto L_0x0072
            r1 = r11[r0]
            boolean r1 = r1.middle
            if (r1 != 0) goto L_0x0072
            r1 = r13[r0]
            r1.set(r14, r14)
            goto L_0x007d
        L_0x0072:
            if (r0 <= 0) goto L_0x007d
            r1 = r13[r0]
            int r2 = r0 + -1
            r2 = r13[r2]
            r1.set(r2)
        L_0x007d:
            int r0 = r0 + 1
            goto L_0x004d
        L_0x0080:
            java.lang.String[] r6 = new java.lang.String[r10]
            java.lang.String[] r5 = new java.lang.String[r10]
            java.lang.String[] r4 = new java.lang.String[r10]
            java.lang.String[] r3 = new java.lang.String[r10]
            com.horcrux.svg.TextLayoutAlgorithm$1CharacterPositioningResolver r0 = new com.horcrux.svg.TextLayoutAlgorithm$1CharacterPositioningResolver
            r1 = r26
            r2 = r11
            r16 = r3
            r3 = r6
            r17 = r4
            r4 = r5
            r18 = r5
            r5 = r17
            r17 = r6
            r6 = r16
            r0.<init>(r2, r3, r4, r5, r6)
            android.graphics.PointF r0 = new android.graphics.PointF
            r0.<init>(r14, r14)
            r1 = 0
        L_0x00a4:
            if (r1 >= r10) goto L_0x00ef
            r2 = r17[r1]
            java.lang.String r3 = ""
            boolean r2 = r2.equals(r3)
            java.lang.String r4 = "0"
            if (r2 == 0) goto L_0x00b4
            r17[r1] = r4
        L_0x00b4:
            r2 = r18[r1]
            boolean r2 = r2.equals(r3)
            if (r2 == 0) goto L_0x00be
            r18[r1] = r4
        L_0x00be:
            float r2 = r0.x
            r3 = r17[r1]
            float r3 = java.lang.Float.parseFloat(r3)
            float r2 = r2 + r3
            r0.x = r2
            float r2 = r0.y
            r3 = r18[r1]
            float r3 = java.lang.Float.parseFloat(r3)
            float r2 = r2 + r3
            r0.y = r2
            r2 = r11[r1]
            r3 = r13[r1]
            float r3 = r3.x
            float r4 = r0.x
            float r3 = r3 + r4
            double r3 = (double) r3
            r2.f1155x = r3
            r2 = r11[r1]
            r3 = r13[r1]
            float r3 = r3.y
            float r4 = r0.y
            float r3 = r3 + r4
            double r3 = (double) r3
            r2.f1156y = r3
            int r1 = r1 + 1
            goto L_0x00a4
        L_0x00ef:
            com.horcrux.svg.TextLayoutAlgorithm$1TextLengthResolver r1 = new com.horcrux.svg.TextLayoutAlgorithm$1TextLengthResolver
            r1.<init>(r11)
            r1.resolveTextLength(r8)
            r0.set(r14, r14)
            r1 = 1
        L_0x00fb:
            if (r1 >= r10) goto L_0x014e
            r2 = r17[r1]
            if (r2 == 0) goto L_0x010f
            r2 = r17[r1]
            double r2 = java.lang.Double.parseDouble(r2)
            r4 = r11[r1]
            double r4 = r4.f1155x
            double r2 = r2 - r4
            float r2 = (float) r2
            r0.x = r2
        L_0x010f:
            r2 = r18[r1]
            if (r2 == 0) goto L_0x0121
            r2 = r18[r1]
            double r2 = java.lang.Double.parseDouble(r2)
            r4 = r11[r1]
            double r4 = r4.f1156y
            double r2 = r2 - r4
            float r2 = (float) r2
            r0.y = r2
        L_0x0121:
            r2 = r11[r1]
            double r3 = r2.f1155x
            float r5 = r0.x
            double r5 = (double) r5
            double r3 = r3 + r5
            r2.f1155x = r3
            r2 = r11[r1]
            double r3 = r2.f1156y
            float r5 = r0.y
            double r5 = (double) r5
            double r3 = r3 + r5
            r2.f1156y = r3
            r2 = r11[r1]
            boolean r2 = r2.middle
            if (r2 == 0) goto L_0x0145
            r2 = r11[r1]
            boolean r2 = r2.anchoredChunk
            if (r2 == 0) goto L_0x0145
            r2 = r11[r1]
            r2.anchoredChunk = r12
        L_0x0145:
            int r1 = r1 + 1
            if (r1 >= r10) goto L_0x00fb
            r2 = r11[r1]
            r2.anchoredChunk = r15
            goto L_0x00fb
        L_0x014e:
            r4 = 0
            r5 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            r8 = 0
            r16 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            r18 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            r20 = -4503599627370496(0xfff0000000000000, double:-Infinity)
        L_0x0158:
            r22 = 4611686018427387904(0x4000000000000000, double:2.0)
            if (r4 >= r10) goto L_0x01f9
            r1 = r11[r4]
            boolean r1 = r1.addressable
            if (r1 != 0) goto L_0x0164
            goto L_0x01f2
        L_0x0164:
            r1 = r11[r4]
            boolean r1 = r1.anchoredChunk
            if (r1 == 0) goto L_0x0171
            r18 = r5
            r5 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            r12 = -4503599627370496(0xfff0000000000000, double:-Infinity)
            goto L_0x0175
        L_0x0171:
            r12 = r16
            r16 = r20
        L_0x0175:
            r1 = r11[r4]
            double r0 = r1.f1155x
            r14 = r11[r4]
            double r2 = r14.advance
            double r2 = r2 + r0
            double r14 = java.lang.Math.min(r0, r2)
            double r5 = java.lang.Math.min(r5, r14)
            double r0 = java.lang.Math.max(r0, r2)
            double r0 = java.lang.Math.max(r12, r0)
            if (r4 <= 0) goto L_0x019d
            r2 = r11[r4]
            boolean r2 = r2.anchoredChunk
            if (r2 == 0) goto L_0x019d
            r2 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
            int r12 = (r18 > r2 ? 1 : (r18 == r2 ? 0 : -1))
            if (r12 != 0) goto L_0x01a3
            goto L_0x019f
        L_0x019d:
            r2 = 9218868437227405312(0x7ff0000000000000, double:Infinity)
        L_0x019f:
            int r12 = r10 + -1
            if (r4 != r12) goto L_0x01ee
        L_0x01a3:
            com.horcrux.svg.TextProperties$TextAnchor r12 = com.horcrux.svg.TextProperties.TextAnchor.start
            com.horcrux.svg.TextProperties$Direction r13 = com.horcrux.svg.TextProperties.Direction.ltr
            int r14 = r10 + -1
            if (r4 != r14) goto L_0x01af
            r16 = r0
            r18 = r5
        L_0x01af:
            r15 = r11[r8]
            double r2 = r15.f1155x
            int[] r15 = com.horcrux.svg.TextLayoutAlgorithm.C22561.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor
            int r12 = r12.ordinal()
            r12 = r15[r12]
            r15 = 1
            if (r12 == r15) goto L_0x01d2
            r15 = 2
            if (r12 == r15) goto L_0x01ca
            r15 = 3
            if (r12 == r15) goto L_0x01c5
            goto L_0x01db
        L_0x01c5:
            com.horcrux.svg.TextProperties$Direction r12 = com.horcrux.svg.TextProperties.Direction.ltr
            if (r13 != r12) goto L_0x01d6
            goto L_0x01d9
        L_0x01ca:
            com.horcrux.svg.TextProperties$Direction r12 = com.horcrux.svg.TextProperties.Direction.ltr
            double r12 = r18 + r16
            double r12 = r12 / r22
            double r2 = r2 - r12
            goto L_0x01db
        L_0x01d2:
            com.horcrux.svg.TextProperties$Direction r12 = com.horcrux.svg.TextProperties.Direction.ltr
            if (r13 != r12) goto L_0x01d9
        L_0x01d6:
            double r2 = r2 - r18
            goto L_0x01db
        L_0x01d9:
            double r2 = r2 - r16
        L_0x01db:
            if (r4 != r14) goto L_0x01df
            r12 = r4
            goto L_0x01e1
        L_0x01df:
            int r12 = r4 + -1
        L_0x01e1:
            if (r8 > r12) goto L_0x01ed
            r13 = r11[r8]
            double r14 = r13.f1155x
            double r14 = r14 + r2
            r13.f1155x = r14
            int r8 = r8 + 1
            goto L_0x01e1
        L_0x01ed:
            r8 = r4
        L_0x01ee:
            r20 = r16
            r16 = r0
        L_0x01f2:
            int r4 = r4 + 1
            r12 = 0
            r14 = 0
            r15 = 1
            goto L_0x0158
        L_0x01f9:
            android.graphics.PointF r0 = new android.graphics.PointF
            r2 = 0
            r0.<init>(r2, r2)
            android.graphics.PathMeasure r2 = new android.graphics.PathMeasure
            r2.<init>()
            r3 = 0
            r6 = r3
            r4 = 0
            r5 = 0
            r15 = 0
        L_0x0209:
            if (r5 >= r10) goto L_0x03a2
            java.lang.Object r12 = r9.get(r5)
            com.horcrux.svg.TextPathView r12 = (com.horcrux.svg.TextPathView) r12
            if (r12 == 0) goto L_0x0342
            r13 = r11[r5]
            boolean r13 = r13.addressable
            if (r13 == 0) goto L_0x0342
            android.graphics.Path r6 = r12.getTextPath(r3, r3)
            r13 = r11[r5]
            boolean r13 = r13.middle
            if (r13 != 0) goto L_0x031b
            r12.getSide()
            com.horcrux.svg.TextProperties$TextPathSide r13 = com.horcrux.svg.TextProperties.TextPathSide.right
            r13 = 0
            r2.setPath(r6, r13)
            float r13 = r2.getLength()
            double r13 = (double) r13
            com.horcrux.svg.SVGLength r15 = r12.getStartOffset()
            r17 = r4
            double r3 = r15.value
            r15 = r11[r5]
            r18 = r2
            double r1 = r15.advance
            r15 = r11[r5]
            r19 = r6
            double r6 = r15.f1155x
            r15 = r11[r5]
            r24 = r9
            r25 = r10
            double r9 = r15.f1156y
            r9 = r11[r5]
            double r9 = r9.rotate
            double r1 = r1 / r22
            double r6 = r6 + r1
            double r6 = r6 + r3
            boolean r1 = r18.isClosed()
            r2 = 0
            if (r1 != 0) goto L_0x026a
            int r1 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r1 < 0) goto L_0x0265
            int r1 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r1 <= 0) goto L_0x026a
        L_0x0265:
            r1 = r11[r5]
            r4 = 1
            r1.hidden = r4
        L_0x026a:
            boolean r1 = r18.isClosed()
            if (r1 == 0) goto L_0x02df
            com.horcrux.svg.TextProperties$TextAnchor r1 = com.horcrux.svg.TextProperties.TextAnchor.start
            com.horcrux.svg.TextProperties$Direction r4 = com.horcrux.svg.TextProperties.Direction.ltr
            r9 = r11[r8]
            double r9 = r9.f1155x
            int[] r9 = com.horcrux.svg.TextLayoutAlgorithm.C22561.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor
            int r1 = r1.ordinal()
            r1 = r9[r1]
            r9 = 1
            if (r1 == r9) goto L_0x02be
            r9 = 2
            if (r1 == r9) goto L_0x02ab
            r9 = 3
            if (r1 == r9) goto L_0x028a
            goto L_0x02df
        L_0x028a:
            com.horcrux.svg.TextProperties$Direction r1 = com.horcrux.svg.TextProperties.Direction.ltr
            if (r4 != r1) goto L_0x029d
            double r9 = -r13
            int r1 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r1 < 0) goto L_0x0297
            int r1 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x02df
        L_0x0297:
            r1 = r11[r5]
            r4 = 1
            r1.hidden = r4
            goto L_0x02e0
        L_0x029d:
            r4 = 1
            int r1 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r1 < 0) goto L_0x02a6
            int r1 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r1 <= 0) goto L_0x02e0
        L_0x02a6:
            r1 = r11[r5]
            r1.hidden = r4
            goto L_0x02e0
        L_0x02ab:
            double r1 = -r13
            double r1 = r1 / r22
            int r3 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r3 < 0) goto L_0x02b8
            double r1 = r13 / r22
            int r3 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            if (r3 <= 0) goto L_0x02df
        L_0x02b8:
            r1 = r11[r5]
            r2 = 1
            r1.hidden = r2
            goto L_0x02df
        L_0x02be:
            com.horcrux.svg.TextProperties$Direction r1 = com.horcrux.svg.TextProperties.Direction.ltr
            if (r4 != r1) goto L_0x02d0
            int r1 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r1 < 0) goto L_0x02ca
            int r1 = (r6 > r13 ? 1 : (r6 == r13 ? 0 : -1))
            if (r1 <= 0) goto L_0x02df
        L_0x02ca:
            r1 = r11[r5]
            r4 = 1
            r1.hidden = r4
            goto L_0x02e0
        L_0x02d0:
            r4 = 1
            double r9 = -r13
            int r1 = (r6 > r9 ? 1 : (r6 == r9 ? 0 : -1))
            if (r1 < 0) goto L_0x02da
            int r1 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r1 <= 0) goto L_0x02e0
        L_0x02da:
            r1 = r11[r5]
            r1.hidden = r4
            goto L_0x02e0
        L_0x02df:
            r4 = 1
        L_0x02e0:
            double r6 = r6 % r13
            r1 = r11[r5]
            boolean r1 = r1.hidden
            if (r1 != 0) goto L_0x0318
            r1 = 2
            float[] r2 = new float[r1]
            float[] r3 = new float[r1]
            float r1 = (float) r6
            r7 = r18
            r7.getPosTan(r1, r2, r3)
            r1 = r3[r4]
            double r1 = (double) r1
            r4 = 0
            r3 = r3[r4]
            double r3 = (double) r3
            double r1 = java.lang.Math.atan2(r1, r3)
            r3 = 4633260481411531256(0x404ca5dc1a63c1f8, double:57.29577951308232)
            double r1 = r1 * r3
            r3 = 4636033603912859648(0x4056800000000000, double:90.0)
            double r3 = r3 + r1
            java.lang.Math.cos(r3)
            java.lang.Math.sin(r3)
            r3 = r11[r5]
            double r9 = r3.rotate
            double r9 = r9 + r1
            r3.rotate = r9
            goto L_0x033e
        L_0x0318:
            r7 = r18
            goto L_0x033e
        L_0x031b:
            r7 = r2
            r17 = r4
            r19 = r6
            r24 = r9
            r25 = r10
            r1 = r11[r5]
            int r2 = r5 + -1
            r3 = r11[r2]
            double r3 = r3.f1155x
            r1.f1155x = r3
            r1 = r11[r5]
            r3 = r11[r2]
            double r3 = r3.f1156y
            r1.f1156y = r3
            r1 = r11[r5]
            r2 = r11[r2]
            double r2 = r2.rotate
            r1.rotate = r2
        L_0x033e:
            r6 = r19
            r15 = 1
            goto L_0x0349
        L_0x0342:
            r7 = r2
            r17 = r4
            r24 = r9
            r25 = r10
        L_0x0349:
            if (r12 != 0) goto L_0x0392
            r1 = r11[r5]
            boolean r1 = r1.addressable
            if (r1 == 0) goto L_0x0392
            if (r15 == 0) goto L_0x036e
            r13 = 0
            r7.setPath(r6, r13)
            r1 = 2
            float[] r2 = new float[r1]
            float r3 = r7.getLength()
            r4 = 0
            r7.getPosTan(r3, r2, r4)
            r3 = r2[r13]
            r9 = 1
            r2 = r2[r9]
            r0.set(r3, r2)
            r15 = 0
            r17 = 1
            goto L_0x0372
        L_0x036e:
            r1 = 2
            r4 = 0
            r9 = 1
            r13 = 0
        L_0x0372:
            if (r17 == 0) goto L_0x0394
            r2 = r11[r5]
            boolean r2 = r2.anchoredChunk
            if (r2 == 0) goto L_0x037d
            r17 = 0
            goto L_0x0394
        L_0x037d:
            r2 = r11[r5]
            double r9 = r2.f1155x
            float r3 = r0.x
            double r13 = (double) r3
            double r9 = r9 + r13
            r2.f1155x = r9
            r2 = r11[r5]
            double r9 = r2.f1156y
            float r3 = r0.y
            double r12 = (double) r3
            double r9 = r9 + r12
            r2.f1156y = r9
            goto L_0x0394
        L_0x0392:
            r1 = 2
            r4 = 0
        L_0x0394:
            int r5 = r5 + 1
            r3 = r4
            r2 = r7
            r4 = r17
            r9 = r24
            r10 = r25
            r7 = r26
            goto L_0x0209
        L_0x03a2:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TextLayoutAlgorithm.layoutText(com.horcrux.svg.TextLayoutAlgorithm$LayoutInput):com.horcrux.svg.TextLayoutAlgorithm$CharacterInformation[]");
    }

    /* renamed from: com.horcrux.svg.TextLayoutAlgorithm$1 */
    static /* synthetic */ class C22561 {
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.horcrux.svg.TextProperties$TextAnchor[] r0 = com.horcrux.svg.TextProperties.TextAnchor.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor = r0
                com.horcrux.svg.TextProperties$TextAnchor r1 = com.horcrux.svg.TextProperties.TextAnchor.start     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor     // Catch:{ NoSuchFieldError -> 0x001d }
                com.horcrux.svg.TextProperties$TextAnchor r1 = com.horcrux.svg.TextProperties.TextAnchor.middle     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.horcrux.svg.TextProperties$TextAnchor r1 = com.horcrux.svg.TextProperties.TextAnchor.end     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TextLayoutAlgorithm.C22561.<clinit>():void");
        }
    }
}
