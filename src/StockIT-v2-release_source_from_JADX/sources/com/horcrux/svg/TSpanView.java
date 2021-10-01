package com.horcrux.svg;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewParent;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.horcrux.svg.TextProperties;
import java.text.Bidi;
import java.util.ArrayList;
import javax.annotation.Nullable;

class TSpanView extends TextView {
    private static final String FONTS = "fonts/";
    private static final String OTF = ".otf";
    private static final String TTF = ".ttf";
    static final String additionalLigatures = "'hlig', 'cala', ";
    static final String defaultFeatures = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', ";
    static final String disableDiscretionaryLigatures = "'liga' 0, 'clig' 0, 'dlig' 0, 'hlig' 0, 'cala' 0, ";
    static final String fontWeightTag = "'wght' ";
    private static final double radToDeg = 57.29577951308232d;
    static final String requiredFontFeatures = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk',";
    private static final double tau = 6.283185307179586d;
    private final AssetManager assets = this.mContext.getResources().getAssets();
    private final ArrayList<String> emoji = new ArrayList<>();
    private final ArrayList<Matrix> emojiTransforms = new ArrayList<>();
    private Path mCachedPath;
    @Nullable
    String mContent;
    private TextPathView textPath;

    public TSpanView(ReactContext reactContext) {
        super(reactContext);
    }

    @ReactProp(name = "content")
    public void setContent(@Nullable String str) {
        this.mContent = str;
        invalidate();
    }

    public void invalidate() {
        this.mCachedPath = null;
        super.invalidate();
    }

    /* access modifiers changed from: package-private */
    public void clearCache() {
        this.mCachedPath = null;
        super.clearCache();
    }

    /* access modifiers changed from: package-private */
    public void draw(Canvas canvas, Paint paint, float f) {
        if (this.mContent == null) {
            clip(canvas, paint);
            drawGroup(canvas, paint, f);
        } else if (this.mInlineSize == null || this.mInlineSize.value == 0.0d) {
            int size = this.emoji.size();
            if (size > 0) {
                applyTextPropertiesToPaint(paint, getTextRootGlyphContext().getFont());
                for (int i = 0; i < size; i++) {
                    canvas.save();
                    canvas.concat(this.emojiTransforms.get(i));
                    canvas.drawText(this.emoji.get(i), 0.0f, 0.0f, paint);
                    canvas.restore();
                }
            }
            drawPath(canvas, paint, f);
        } else {
            if (setupFillPaint(paint, this.fillOpacity * f)) {
                drawWrappedText(canvas, paint);
            }
            if (setupStrokePaint(paint, f * this.strokeOpacity)) {
                drawWrappedText(canvas, paint);
            }
        }
    }

    private void drawWrappedText(Canvas canvas, Paint paint) {
        Layout.Alignment alignment;
        Canvas canvas2 = canvas;
        GlyphContext textRootGlyphContext = getTextRootGlyphContext();
        pushGlyphContext();
        FontData font = textRootGlyphContext.getFont();
        TextPaint textPaint = new TextPaint(paint);
        applyTextPropertiesToPaint(textPaint, font);
        applySpacingAndFeatures(textPaint, font);
        double fontSize = textRootGlyphContext.getFontSize();
        int i = C22551.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[font.textAnchor.ordinal()];
        if (i == 2) {
            alignment = Layout.Alignment.ALIGN_CENTER;
        } else if (i != 3) {
            alignment = Layout.Alignment.ALIGN_NORMAL;
        } else {
            alignment = Layout.Alignment.ALIGN_OPPOSITE;
        }
        Layout.Alignment alignment2 = alignment;
        StaticLayout staticLayout = getStaticLayout(textPaint, alignment2, true, new SpannableString(this.mContent), (int) PropHelper.fromRelative(this.mInlineSize, (double) canvas.getWidth(), 0.0d, (double) this.mScale, fontSize));
        int lineAscent = staticLayout.getLineAscent(0);
        popGlyphContext();
        canvas.save();
        canvas2.translate((float) textRootGlyphContext.nextX(0.0d), (float) (textRootGlyphContext.nextY() + ((double) lineAscent)));
        staticLayout.draw(canvas2);
        canvas.restore();
    }

    private StaticLayout getStaticLayout(TextPaint textPaint, Layout.Alignment alignment, boolean z, SpannableString spannableString, int i) {
        if (Build.VERSION.SDK_INT < 23) {
            return new StaticLayout(spannableString, textPaint, i, alignment, 1.0f, 0.0f, z);
        }
        return StaticLayout.Builder.obtain(spannableString, 0, spannableString.length(), textPaint, i).setAlignment(alignment).setLineSpacing(0.0f, 1.0f).setIncludePad(z).setBreakStrategy(1).setHyphenationFrequency(1).build();
    }

    public static String visualToLogical(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        Bidi bidi = new Bidi(str, -2);
        if (bidi.isLeftToRight()) {
            return str;
        }
        int runCount = bidi.getRunCount();
        byte[] bArr = new byte[runCount];
        Integer[] numArr = new Integer[runCount];
        for (int i = 0; i < runCount; i++) {
            bArr[i] = (byte) bidi.getRunLevel(i);
            numArr[i] = Integer.valueOf(i);
        }
        Bidi.reorderVisually(bArr, 0, numArr, 0, runCount);
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < runCount; i2++) {
            int intValue = numArr[i2].intValue();
            int runStart = bidi.getRunStart(intValue);
            int runLimit = bidi.getRunLimit(intValue);
            if ((bArr[intValue] & 1) != 0) {
                while (true) {
                    runLimit--;
                    if (runLimit < runStart) {
                        break;
                    }
                    sb.append(str.charAt(runLimit));
                }
            } else {
                sb.append(str, runStart, runLimit);
            }
        }
        return sb.toString();
    }

    /* access modifiers changed from: package-private */
    public Path getPath(Canvas canvas, Paint paint) {
        Path path = this.mCachedPath;
        if (path != null) {
            return path;
        }
        if (this.mContent == null) {
            Path groupPath = getGroupPath(canvas, paint);
            this.mCachedPath = groupPath;
            return groupPath;
        }
        setupTextPath();
        pushGlyphContext();
        this.mCachedPath = getLinePath(visualToLogical(this.mContent), paint, canvas);
        popGlyphContext();
        return this.mCachedPath;
    }

    /* access modifiers changed from: package-private */
    public double getSubtreeTextChunksTotalAdvance(Paint paint) {
        if (!Double.isNaN(this.cachedAdvance)) {
            return this.cachedAdvance;
        }
        String str = this.mContent;
        double d = 0.0d;
        if (str == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View childAt = getChildAt(i);
                if (childAt instanceof TextView) {
                    d += ((TextView) childAt).getSubtreeTextChunksTotalAdvance(paint);
                }
            }
            this.cachedAdvance = d;
            return d;
        } else if (str.length() == 0) {
            this.cachedAdvance = 0.0d;
            return 0.0d;
        } else {
            FontData font = getTextRootGlyphContext().getFont();
            applyTextPropertiesToPaint(paint, font);
            applySpacingAndFeatures(paint, font);
            this.cachedAdvance = (double) paint.measureText(str);
            return this.cachedAdvance;
        }
    }

    private void applySpacingAndFeatures(Paint paint, FontData fontData) {
        if (Build.VERSION.SDK_INT >= 21) {
            double d = fontData.letterSpacing;
            paint.setLetterSpacing((float) (d / (fontData.fontSize * ((double) this.mScale))));
            if (d == 0.0d && fontData.fontVariantLigatures == TextProperties.FontVariantLigatures.normal) {
                paint.setFontFeatureSettings("'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'hlig', 'cala', " + fontData.fontFeatureSettings);
            } else {
                paint.setFontFeatureSettings("'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'liga' 0, 'clig' 0, 'dlig' 0, 'hlig' 0, 'cala' 0, " + fontData.fontFeatureSettings);
            }
            if (Build.VERSION.SDK_INT >= 26) {
                paint.setFontVariationSettings(fontWeightTag + fontData.absoluteFontWeight + fontData.fontVariationSettings);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:68:0x01eb, code lost:
        r10 = r4 * r2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:114:0x02b1 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x0260  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.Path getLinePath(java.lang.String r70, android.graphics.Paint r71, android.graphics.Canvas r72) {
        /*
            r69 = this;
            r6 = r69
            r14 = r71
            r15 = r72
            int r13 = r70.length()
            android.graphics.Path r12 = new android.graphics.Path
            r12.<init>()
            java.util.ArrayList<java.lang.String> r0 = r6.emoji
            r0.clear()
            java.util.ArrayList<android.graphics.Matrix> r0 = r6.emojiTransforms
            r0.clear()
            if (r13 != 0) goto L_0x001c
            return r12
        L_0x001c:
            r0 = 0
            com.horcrux.svg.TextPathView r1 = r6.textPath
            r11 = 0
            if (r1 == 0) goto L_0x0025
            r16 = 1
            goto L_0x0027
        L_0x0025:
            r16 = 0
        L_0x0027:
            r17 = 0
            if (r16 == 0) goto L_0x0049
            android.graphics.PathMeasure r0 = new android.graphics.PathMeasure
            com.horcrux.svg.TextPathView r1 = r6.textPath
            android.graphics.Path r1 = r1.getTextPath(r15, r14)
            r0.<init>(r1, r11)
            float r1 = r0.getLength()
            double r1 = (double) r1
            boolean r3 = r0.isClosed()
            int r4 = (r1 > r17 ? 1 : (r1 == r17 ? 0 : -1))
            if (r4 != 0) goto L_0x0044
            return r12
        L_0x0044:
            r9 = r0
            r7 = r1
            r19 = r3
            goto L_0x004e
        L_0x0049:
            r9 = r0
            r7 = r17
            r19 = 0
        L_0x004e:
            com.horcrux.svg.GlyphContext r4 = r69.getTextRootGlyphContext()
            com.horcrux.svg.FontData r0 = r4.getFont()
            r6.applyTextPropertiesToPaint(r14, r0)
            com.horcrux.svg.GlyphPathBag r5 = new com.horcrux.svg.GlyphPathBag
            r5.<init>(r14)
            boolean[] r2 = new boolean[r13]
            char[] r20 = r70.toCharArray()
            r21 = r12
            double r11 = r0.kerning
            r23 = r11
            double r11 = r0.wordSpacing
            r26 = r11
            double r10 = r0.letterSpacing
            boolean r1 = r0.manualKerning
            r3 = 1
            r28 = r1 ^ 1
            int r1 = (r10 > r17 ? 1 : (r10 == r17 ? 0 : -1))
            if (r1 != 0) goto L_0x0081
            com.horcrux.svg.TextProperties$FontVariantLigatures r1 = r0.fontVariantLigatures
            com.horcrux.svg.TextProperties$FontVariantLigatures r3 = com.horcrux.svg.TextProperties.FontVariantLigatures.normal
            if (r1 != r3) goto L_0x0081
            r1 = 1
            goto L_0x0082
        L_0x0081:
            r1 = 0
        L_0x0082:
            int r3 = android.os.Build.VERSION.SDK_INT
            r12 = 21
            if (r3 < r12) goto L_0x00d8
            if (r1 == 0) goto L_0x00a1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'hlig', 'cala', "
            r1.append(r3)
            java.lang.String r3 = r0.fontFeatureSettings
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r14.setFontFeatureSettings(r1)
            goto L_0x00b7
        L_0x00a1:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "'rlig', 'liga', 'clig', 'calt', 'locl', 'ccmp', 'mark', 'mkmk','kern', 'liga' 0, 'clig' 0, 'dlig' 0, 'hlig' 0, 'cala' 0, "
            r1.append(r3)
            java.lang.String r3 = r0.fontFeatureSettings
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r14.setFontFeatureSettings(r1)
        L_0x00b7:
            int r1 = android.os.Build.VERSION.SDK_INT
            r3 = 26
            if (r1 < r3) goto L_0x00d8
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "'wght' "
            r1.append(r3)
            int r3 = r0.absoluteFontWeight
            r1.append(r3)
            java.lang.String r3 = r0.fontVariationSettings
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r14.setFontVariationSettings(r1)
        L_0x00d8:
            com.facebook.react.bridge.ReadableMap r12 = r0.fontData
            float[] r3 = new float[r13]
            r1 = r70
            r14.getTextWidths(r1, r3)
            com.horcrux.svg.TextProperties$TextAnchor r1 = r0.textAnchor
            com.horcrux.svg.TextView r0 = r69.getTextAnchorRoot()
            r29 = r9
            r30 = r10
            double r9 = r0.getSubtreeTextChunksTotalAdvance(r14)
            double r32 = r6.getTextAnchorOffset(r1, r9)
            double r43 = r4.getFontSize()
            r45 = 4611686018427387904(0x4000000000000000, double:2.0)
            if (r16 == 0) goto L_0x014d
            com.horcrux.svg.TextPathView r0 = r6.textPath
            com.horcrux.svg.TextProperties$TextPathMidLine r0 = r0.getMidLine()
            com.horcrux.svg.TextProperties$TextPathMidLine r11 = com.horcrux.svg.TextProperties.TextPathMidLine.sharp
            if (r0 != r11) goto L_0x0107
            r11 = 1
            goto L_0x0108
        L_0x0107:
            r11 = 0
        L_0x0108:
            com.horcrux.svg.TextPathView r0 = r6.textPath
            com.horcrux.svg.TextProperties$TextPathSide r0 = r0.getSide()
            r34 = r1
            com.horcrux.svg.TextProperties$TextPathSide r1 = com.horcrux.svg.TextProperties.TextPathSide.right
            if (r0 != r1) goto L_0x0117
            r35 = -1
            goto L_0x0119
        L_0x0117:
            r35 = 1
        L_0x0119:
            com.horcrux.svg.TextPathView r0 = r6.textPath
            com.horcrux.svg.SVGLength r1 = r0.getStartOffset()
            r0 = r69
            r36 = r11
            r11 = r34
            r47 = r2
            r48 = r3
            r2 = r7
            r15 = r4
            r49 = r5
            r4 = r43
            double r0 = r0.getAbsoluteStartOffset(r1, r2, r4)
            double r32 = r32 + r0
            if (r19 == 0) goto L_0x0145
            double r2 = r7 / r45
            com.horcrux.svg.TextProperties$TextAnchor r4 = com.horcrux.svg.TextProperties.TextAnchor.middle
            if (r11 != r4) goto L_0x013f
            double r2 = -r2
            goto L_0x0141
        L_0x013f:
            r2 = r17
        L_0x0141:
            double r0 = r0 + r2
            double r2 = r0 + r7
            goto L_0x0148
        L_0x0145:
            r2 = r7
            r0 = r17
        L_0x0148:
            r4 = r35
            r5 = r36
            goto L_0x0159
        L_0x014d:
            r47 = r2
            r48 = r3
            r15 = r4
            r49 = r5
            r2 = r7
            r0 = r17
            r4 = 1
            r5 = 0
        L_0x0159:
            r50 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            com.horcrux.svg.SVGLength r11 = r6.mTextLength
            r52 = r7
            if (r11 == 0) goto L_0x01a1
            com.horcrux.svg.SVGLength r8 = r6.mTextLength
            int r11 = r72.getWidth()
            r34 = r8
            double r7 = (double) r11
            r37 = 0
            float r11 = r6.mScale
            r54 = r0
            double r0 = (double) r11
            r35 = r7
            r39 = r0
            r41 = r43
            double r0 = com.horcrux.svg.PropHelper.fromRelative(r34, r35, r37, r39, r41)
            int r7 = (r0 > r17 ? 1 : (r0 == r17 ? 0 : -1))
            if (r7 < 0) goto L_0x0199
            int[] r7 = com.horcrux.svg.TSpanView.C22551.$SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust
            com.horcrux.svg.TextProperties$TextLengthAdjust r8 = r6.mLengthAdjust
            int r8 = r8.ordinal()
            r7 = r7[r8]
            r8 = 2
            if (r7 == r8) goto L_0x0196
            double r0 = r0 - r9
            int r7 = r13 + -1
            double r7 = (double) r7
            double r0 = r0 / r7
            double r10 = r30 + r0
            r30 = r10
            goto L_0x01a3
        L_0x0196:
            double r50 = r0 / r9
            goto L_0x01a3
        L_0x0199:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.String r1 = "Negative textLength value"
            r0.<init>(r1)
            throw r0
        L_0x01a1:
            r54 = r0
        L_0x01a3:
            double r0 = (double) r4
            double r10 = r50 * r0
            android.graphics.Paint$FontMetrics r7 = r71.getFontMetrics()
            float r8 = r7.descent
            double r8 = (double) r8
            r41 = r4
            float r4 = r7.leading
            r56 = r10
            double r10 = (double) r4
            double r10 = r10 + r8
            float r4 = r7.ascent
            float r4 = -r4
            r42 = r5
            float r5 = r7.leading
            float r4 = r4 + r5
            double r4 = (double) r4
            float r7 = r7.top
            float r7 = -r7
            r58 = r2
            double r2 = (double) r7
            double r34 = r2 + r10
            java.lang.String r7 = r69.getBaselineShift()
            com.horcrux.svg.TextProperties$AlignmentBaseline r36 = r69.getAlignmentBaseline()
            if (r36 == 0) goto L_0x020a
            int[] r37 = com.horcrux.svg.TSpanView.C22551.$SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline
            int r38 = r36.ordinal()
            r37 = r37[r38]
            switch(r37) {
                case 2: goto L_0x0207;
                case 3: goto L_0x0207;
                case 4: goto L_0x0207;
                case 5: goto L_0x020a;
                case 6: goto L_0x0207;
                case 7: goto L_0x01f3;
                case 8: goto L_0x01ee;
                case 9: goto L_0x01e9;
                case 10: goto L_0x01e3;
                case 11: goto L_0x01e1;
                case 12: goto L_0x01e1;
                case 13: goto L_0x01e1;
                case 14: goto L_0x01f1;
                case 15: goto L_0x01de;
                case 16: goto L_0x01dc;
                default: goto L_0x01db;
            }
        L_0x01db:
            goto L_0x020a
        L_0x01dc:
            r10 = r2
            goto L_0x01f1
        L_0x01de:
            double r10 = r34 / r45
            goto L_0x01f1
        L_0x01e1:
            r10 = r4
            goto L_0x01f1
        L_0x01e3:
            r2 = 4605380978949069210(0x3fe999999999999a, double:0.8)
            goto L_0x01eb
        L_0x01e9:
            r2 = 4602678819172646912(0x3fe0000000000000, double:0.5)
        L_0x01eb:
            double r10 = r4 * r2
            goto L_0x01f1
        L_0x01ee:
            double r4 = r4 - r8
            double r10 = r4 / r45
        L_0x01f1:
            r4 = 0
            goto L_0x020d
        L_0x01f3:
            android.graphics.Rect r2 = new android.graphics.Rect
            r2.<init>()
            java.lang.String r3 = "x"
            r4 = 0
            r5 = 1
            r14.getTextBounds(r3, r4, r5, r2)
            int r2 = r2.height()
            double r2 = (double) r2
            double r10 = r2 / r45
            goto L_0x020d
        L_0x0207:
            r4 = 0
            double r10 = -r8
            goto L_0x020d
        L_0x020a:
            r4 = 0
            r10 = r17
        L_0x020d:
            if (r7 == 0) goto L_0x02e7
            boolean r2 = r7.isEmpty()
            if (r2 != 0) goto L_0x02e7
            int[] r2 = com.horcrux.svg.TSpanView.C22551.$SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline
            int r3 = r36.ordinal()
            r2 = r2[r3]
            r3 = 14
            if (r2 == r3) goto L_0x02e7
            r3 = 16
            if (r2 == r3) goto L_0x02e7
            int r2 = r7.hashCode()
            r3 = -1720785339(0xffffffff996ee645, float:-1.2350814E-23)
            if (r2 == r3) goto L_0x024d
            r3 = 114240(0x1be40, float:1.60084E-40)
            if (r2 == r3) goto L_0x0243
            r3 = 109801339(0x68b6f7b, float:5.2449795E-35)
            if (r2 == r3) goto L_0x0239
            goto L_0x0257
        L_0x0239:
            java.lang.String r2 = "super"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0257
            r2 = 1
            goto L_0x0258
        L_0x0243:
            java.lang.String r2 = "sub"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0257
            r2 = 0
            goto L_0x0258
        L_0x024d:
            java.lang.String r2 = "baseline"
            boolean r2 = r7.equals(r2)
            if (r2 == 0) goto L_0x0257
            r2 = 2
            goto L_0x0258
        L_0x0257:
            r2 = -1
        L_0x0258:
            java.lang.String r3 = "os2"
            java.lang.String r5 = "unitsPerEm"
            java.lang.String r8 = "tables"
            if (r2 == 0) goto L_0x02b1
            r9 = 1
            if (r2 == r9) goto L_0x027a
            r9 = 2
            if (r2 == r9) goto L_0x02e7
            float r2 = r6.mScale
            double r2 = (double) r2
            double r35 = r2 * r43
            float r2 = r6.mScale
            double r2 = (double) r2
            r34 = r7
            r37 = r2
            r39 = r43
            double r2 = com.horcrux.svg.PropHelper.fromRelative(r34, r35, r37, r39)
            double r10 = r10 - r2
            goto L_0x02e7
        L_0x027a:
            if (r12 == 0) goto L_0x02e7
            boolean r2 = r12.hasKey(r8)
            if (r2 == 0) goto L_0x02e7
            boolean r2 = r12.hasKey(r5)
            if (r2 == 0) goto L_0x02e7
            int r2 = r12.getInt(r5)
            com.facebook.react.bridge.ReadableMap r5 = r12.getMap(r8)
            boolean r7 = r5.hasKey(r3)
            if (r7 == 0) goto L_0x02e7
            com.facebook.react.bridge.ReadableMap r3 = r5.getMap(r3)
            java.lang.String r5 = "ySuperscriptYOffset"
            boolean r7 = r3.hasKey(r5)
            if (r7 == 0) goto L_0x02e7
            double r7 = r3.getDouble(r5)
            float r3 = r6.mScale
            double r4 = (double) r3
            double r4 = r4 * r43
            double r4 = r4 * r7
            double r2 = (double) r2
            double r4 = r4 / r2
            double r10 = r10 - r4
            goto L_0x02e7
        L_0x02b1:
            if (r12 == 0) goto L_0x02e7
            boolean r2 = r12.hasKey(r8)
            if (r2 == 0) goto L_0x02e7
            boolean r2 = r12.hasKey(r5)
            if (r2 == 0) goto L_0x02e7
            int r2 = r12.getInt(r5)
            com.facebook.react.bridge.ReadableMap r4 = r12.getMap(r8)
            boolean r5 = r4.hasKey(r3)
            if (r5 == 0) goto L_0x02e7
            com.facebook.react.bridge.ReadableMap r3 = r4.getMap(r3)
            java.lang.String r4 = "ySubscriptYOffset"
            boolean r5 = r3.hasKey(r4)
            if (r5 == 0) goto L_0x02e7
            double r3 = r3.getDouble(r4)
            float r5 = r6.mScale
            double r7 = (double) r5
            double r7 = r7 * r43
            double r7 = r7 * r3
            double r2 = (double) r2
            double r7 = r7 / r2
            double r10 = r10 + r7
        L_0x02e7:
            r2 = r10
            android.graphics.Matrix r4 = new android.graphics.Matrix
            r4.<init>()
            android.graphics.Matrix r5 = new android.graphics.Matrix
            r5.<init>()
            android.graphics.Matrix r12 = new android.graphics.Matrix
            r12.<init>()
            r7 = 9
            float[] r11 = new float[r7]
            float[] r10 = new float[r7]
            r9 = 0
        L_0x02fe:
            if (r9 >= r13) goto L_0x0552
            char r7 = r20[r9]
            java.lang.String r8 = java.lang.String.valueOf(r7)
            boolean r34 = r47[r9]
            if (r34 == 0) goto L_0x0311
            java.lang.String r8 = ""
            r6 = r8
            r35 = r13
            r8 = 0
            goto L_0x0348
        L_0x0311:
            r60 = r8
            r25 = r9
            r8 = 0
        L_0x0316:
            r35 = 1
            int r6 = r25 + 1
            if (r6 >= r13) goto L_0x0343
            r35 = r48[r6]
            r36 = 0
            int r35 = (r35 > r36 ? 1 : (r35 == r36 ? 0 : -1))
            if (r35 <= 0) goto L_0x0325
            goto L_0x0343
        L_0x0325:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r35 = r13
            r13 = r60
            r8.append(r13)
            char r13 = r20[r6]
            r8.append(r13)
            java.lang.String r60 = r8.toString()
            r8 = 1
            r47[r6] = r8
            r25 = r6
            r13 = r35
            r8 = 1
            goto L_0x0316
        L_0x0343:
            r35 = r13
            r13 = r60
            r6 = r13
        L_0x0348:
            float r13 = r14.measureText(r6)
            double r13 = (double) r13
            double r13 = r13 * r50
            r36 = r6
            if (r28 == 0) goto L_0x0360
            r6 = r48[r9]
            r38 = r8
            r37 = r9
            double r8 = (double) r6
            double r8 = r8 * r50
            double r8 = r8 - r13
            r23 = r8
            goto L_0x0364
        L_0x0360:
            r38 = r8
            r37 = r9
        L_0x0364:
            r6 = 32
            if (r7 != r6) goto L_0x036a
            r6 = 1
            goto L_0x036b
        L_0x036a:
            r6 = 0
        L_0x036b:
            if (r6 == 0) goto L_0x0370
            r8 = r26
            goto L_0x0372
        L_0x0370:
            r8 = r17
        L_0x0372:
            double r8 = r8 + r30
            double r8 = r8 + r13
            if (r34 == 0) goto L_0x037c
            r43 = r2
            r2 = r17
            goto L_0x0382
        L_0x037c:
            double r39 = r23 + r8
            r43 = r2
            r2 = r39
        L_0x0382:
            double r2 = r15.nextX(r2)
            r40 = r10
            r39 = r11
            double r10 = r15.nextY()
            double r60 = r15.nextDeltaX()
            double r62 = r15.nextDeltaY()
            r64 = r10
            double r10 = r15.nextRotation()
            if (r34 != 0) goto L_0x050e
            if (r6 == 0) goto L_0x03a2
            goto L_0x050e
        L_0x03a2:
            double r8 = r8 * r0
            double r13 = r13 * r0
            double r2 = r2 + r60
            double r2 = r2 * r0
            double r2 = r32 + r2
            double r2 = r2 - r8
            if (r16 == 0) goto L_0x0451
            double r8 = r2 + r13
            double r13 = r13 / r45
            r34 = r7
            double r6 = r2 + r13
            int r60 = (r6 > r58 ? 1 : (r6 == r58 ? 0 : -1))
            if (r60 <= 0) goto L_0x03bd
            goto L_0x050e
        L_0x03bd:
            int r60 = (r6 > r54 ? 1 : (r6 == r54 ? 0 : -1))
            if (r60 >= 0) goto L_0x03c3
            goto L_0x050e
        L_0x03c3:
            r60 = r15
            r15 = 3
            if (r42 == 0) goto L_0x03d2
            float r2 = (float) r6
            r3 = r29
            r3.getMatrix(r2, r5, r15)
            r15 = r3
            r66 = r10
            goto L_0x0438
        L_0x03d2:
            r15 = r29
            int r61 = (r2 > r17 ? 1 : (r2 == r17 ? 0 : -1))
            if (r61 >= 0) goto L_0x03e5
            r66 = r10
            r10 = 3
            r11 = 0
            r15.getMatrix(r11, r4, r10)
            float r2 = (float) r2
            r4.preTranslate(r2, r11)
            r10 = 1
            goto L_0x03ec
        L_0x03e5:
            r66 = r10
            float r2 = (float) r2
            r10 = 1
            r15.getMatrix(r2, r4, r10)
        L_0x03ec:
            float r2 = (float) r6
            r15.getMatrix(r2, r5, r10)
            int r2 = (r8 > r52 ? 1 : (r8 == r52 ? 0 : -1))
            if (r2 <= 0) goto L_0x0402
            r6 = r52
            float r2 = (float) r6
            r3 = 3
            r15.getMatrix(r2, r12, r3)
            double r8 = r8 - r6
            float r2 = (float) r8
            r3 = 0
            r12.preTranslate(r2, r3)
            goto L_0x0408
        L_0x0402:
            r6 = r52
            float r2 = (float) r8
            r15.getMatrix(r2, r12, r10)
        L_0x0408:
            r11 = r39
            r4.getValues(r11)
            r9 = r40
            r12.getValues(r9)
            r8 = 2
            r2 = r11[r8]
            double r2 = (double) r2
            r19 = 5
            r10 = r11[r19]
            r52 = r6
            double r6 = (double) r10
            r10 = r9[r8]
            r39 = r11
            double r10 = (double) r10
            r8 = r9[r19]
            double r8 = (double) r8
            double r10 = r10 - r2
            double r8 = r8 - r6
            double r2 = java.lang.Math.atan2(r8, r10)
            r6 = 4633260481411531256(0x404ca5dc1a63c1f8, double:57.29577951308232)
            double r2 = r2 * r6
            double r2 = r2 * r0
            float r2 = (float) r2
            r5.preRotate(r2)
        L_0x0438:
            double r2 = -r13
            float r2 = (float) r2
            double r6 = r62 + r43
            float r3 = (float) r6
            r5.preTranslate(r2, r3)
            r10 = r56
            float r2 = (float) r10
            r6 = r41
            float r3 = (float) r6
            r5.preScale(r2, r3)
            r7 = r64
            float r2 = (float) r7
            r3 = 0
            r5.postTranslate(r3, r2)
            goto L_0x0468
        L_0x0451:
            r34 = r7
            r66 = r10
            r60 = r15
            r15 = r29
            r6 = r41
            r10 = r56
            r7 = r64
            float r2 = (float) r2
            double r7 = r7 + r62
            double r7 = r7 + r43
            float r3 = (float) r7
            r5.setTranslate(r2, r3)
        L_0x0468:
            r2 = r66
            float r2 = (float) r2
            r5.preRotate(r2)
            if (r38 == 0) goto L_0x04a6
            android.graphics.Path r2 = new android.graphics.Path
            r2.<init>()
            r9 = 0
            int r3 = r36.length()
            r13 = 0
            r14 = 0
            r19 = 2
            r7 = r71
            r8 = r36
            r34 = r15
            r29 = r37
            r15 = r40
            r37 = r10
            r11 = 1
            r10 = r3
            r25 = r26
            r3 = r39
            r22 = 0
            r11 = r13
            r13 = r21
            r21 = r12
            r12 = r14
            r39 = r0
            r0 = r13
            r14 = r35
            r13 = r2
            r7.getTextPath(r8, r9, r10, r11, r12, r13)
            r13 = r36
            r1 = r49
            goto L_0x04c8
        L_0x04a6:
            r25 = r26
            r2 = r34
            r14 = r35
            r13 = r36
            r29 = r37
            r3 = r39
            r19 = 2
            r22 = 0
            r37 = r10
            r34 = r15
            r15 = r40
            r39 = r0
            r0 = r21
            r1 = r49
            r21 = r12
            android.graphics.Path r2 = r1.getOrCreateAndCache(r2, r13)
        L_0x04c8:
            android.graphics.RectF r7 = new android.graphics.RectF
            r7.<init>()
            r8 = 1
            r2.computeBounds(r7, r8)
            float r7 = r7.width()
            r9 = 0
            int r7 = (r7 > r9 ? 1 : (r7 == r9 ? 0 : -1))
            if (r7 != 0) goto L_0x04ff
            r72.save()
            r7 = r72
            r10 = r60
            r7.concat(r5)
            r2 = 0
            r9 = r69
            java.util.ArrayList<java.lang.String> r11 = r9.emoji
            r11.add(r13)
            java.util.ArrayList<android.graphics.Matrix> r11 = r9.emojiTransforms
            android.graphics.Matrix r12 = new android.graphics.Matrix
            r12.<init>(r5)
            r11.add(r12)
            r11 = r71
            r7.drawText(r13, r2, r2, r11)
            r72.restore()
            goto L_0x0532
        L_0x04ff:
            r9 = r69
            r11 = r71
            r7 = r72
            r10 = r60
            r2.transform(r5)
            r0.addPath(r2)
            goto L_0x0532
        L_0x050e:
            r9 = r69
            r11 = r71
            r7 = r72
            r10 = r15
            r25 = r26
            r34 = r29
            r14 = r35
            r29 = r37
            r3 = r39
            r15 = r40
            r6 = r41
            r37 = r56
            r8 = 1
            r19 = 2
            r22 = 0
            r39 = r0
            r0 = r21
            r1 = r49
            r21 = r12
        L_0x0532:
            int r2 = r29 + 1
            r49 = r1
            r41 = r6
            r6 = r9
            r13 = r14
            r12 = r21
            r26 = r25
            r29 = r34
            r56 = r37
            r21 = r0
            r9 = r2
            r14 = r11
            r0 = r39
            r11 = r3
            r2 = r43
            r68 = r15
            r15 = r10
            r10 = r68
            goto L_0x02fe
        L_0x0552:
            r9 = r6
            r0 = r21
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TSpanView.getLinePath(java.lang.String, android.graphics.Paint, android.graphics.Canvas):android.graphics.Path");
    }

    /* renamed from: com.horcrux.svg.TSpanView$1 */
    static /* synthetic */ class C22551 {
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor;
        static final /* synthetic */ int[] $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust;

        /* JADX WARNING: Can't wrap try/catch for region: R(42:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|(2:35|36)|37|39|40|(2:41|42)|43|45|46|47|48|(3:49|50|52)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(45:0|(2:1|2)|3|(2:5|6)|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|39|40|(2:41|42)|43|45|46|47|48|49|50|52) */
        /* JADX WARNING: Can't wrap try/catch for region: R(46:0|(2:1|2)|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|39|40|(2:41|42)|43|45|46|47|48|49|50|52) */
        /* JADX WARNING: Can't wrap try/catch for region: R(47:0|(2:1|2)|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|39|40|41|42|43|45|46|47|48|49|50|52) */
        /* JADX WARNING: Can't wrap try/catch for region: R(48:0|1|2|3|5|6|7|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|39|40|41|42|43|45|46|47|48|49|50|52) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0033 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x0084 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0090 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x009c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00a8 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00b4 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00d1 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x00ec */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x00f6 */
        static {
            /*
                com.horcrux.svg.TextProperties$AlignmentBaseline[] r0 = com.horcrux.svg.TextProperties.AlignmentBaseline.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline = r0
                r1 = 1
                com.horcrux.svg.TextProperties$AlignmentBaseline r2 = com.horcrux.svg.TextProperties.AlignmentBaseline.baseline     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r2 = r2.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r0[r2] = r1     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                r0 = 2
                int[] r2 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x001d }
                com.horcrux.svg.TextProperties$AlignmentBaseline r3 = com.horcrux.svg.TextProperties.AlignmentBaseline.textBottom     // Catch:{ NoSuchFieldError -> 0x001d }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2[r3] = r0     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                r2 = 3
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.afterEdge     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r3[r4] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.textAfterEdge     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r5 = 4
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x003e }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.alphabetic     // Catch:{ NoSuchFieldError -> 0x003e }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r5 = 5
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.ideographic     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r5 = 6
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.middle     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r5 = 7
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.central     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r5 = 8
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x006c }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.mathematical     // Catch:{ NoSuchFieldError -> 0x006c }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r5 = 9
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.hanging     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r5 = 10
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.textTop     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r5 = 11
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x0090 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.beforeEdge     // Catch:{ NoSuchFieldError -> 0x0090 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x0090 }
                r5 = 12
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x0090 }
            L_0x0090:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x009c }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.textBeforeEdge     // Catch:{ NoSuchFieldError -> 0x009c }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x009c }
                r5 = 13
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x009c }
            L_0x009c:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x00a8 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.bottom     // Catch:{ NoSuchFieldError -> 0x00a8 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a8 }
                r5 = 14
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x00a8 }
            L_0x00a8:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x00b4 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.center     // Catch:{ NoSuchFieldError -> 0x00b4 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x00b4 }
                r5 = 15
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x00b4 }
            L_0x00b4:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$AlignmentBaseline     // Catch:{ NoSuchFieldError -> 0x00c0 }
                com.horcrux.svg.TextProperties$AlignmentBaseline r4 = com.horcrux.svg.TextProperties.AlignmentBaseline.top     // Catch:{ NoSuchFieldError -> 0x00c0 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x00c0 }
                r5 = 16
                r3[r4] = r5     // Catch:{ NoSuchFieldError -> 0x00c0 }
            L_0x00c0:
                com.horcrux.svg.TextProperties$TextLengthAdjust[] r3 = com.horcrux.svg.TextProperties.TextLengthAdjust.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust = r3
                com.horcrux.svg.TextProperties$TextLengthAdjust r4 = com.horcrux.svg.TextProperties.TextLengthAdjust.spacing     // Catch:{ NoSuchFieldError -> 0x00d1 }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x00d1 }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x00d1 }
            L_0x00d1:
                int[] r3 = $SwitchMap$com$horcrux$svg$TextProperties$TextLengthAdjust     // Catch:{ NoSuchFieldError -> 0x00db }
                com.horcrux.svg.TextProperties$TextLengthAdjust r4 = com.horcrux.svg.TextProperties.TextLengthAdjust.spacingAndGlyphs     // Catch:{ NoSuchFieldError -> 0x00db }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x00db }
                r3[r4] = r0     // Catch:{ NoSuchFieldError -> 0x00db }
            L_0x00db:
                com.horcrux.svg.TextProperties$TextAnchor[] r3 = com.horcrux.svg.TextProperties.TextAnchor.values()
                int r3 = r3.length
                int[] r3 = new int[r3]
                $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor = r3
                com.horcrux.svg.TextProperties$TextAnchor r4 = com.horcrux.svg.TextProperties.TextAnchor.start     // Catch:{ NoSuchFieldError -> 0x00ec }
                int r4 = r4.ordinal()     // Catch:{ NoSuchFieldError -> 0x00ec }
                r3[r4] = r1     // Catch:{ NoSuchFieldError -> 0x00ec }
            L_0x00ec:
                int[] r1 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor     // Catch:{ NoSuchFieldError -> 0x00f6 }
                com.horcrux.svg.TextProperties$TextAnchor r3 = com.horcrux.svg.TextProperties.TextAnchor.middle     // Catch:{ NoSuchFieldError -> 0x00f6 }
                int r3 = r3.ordinal()     // Catch:{ NoSuchFieldError -> 0x00f6 }
                r1[r3] = r0     // Catch:{ NoSuchFieldError -> 0x00f6 }
            L_0x00f6:
                int[] r0 = $SwitchMap$com$horcrux$svg$TextProperties$TextAnchor     // Catch:{ NoSuchFieldError -> 0x0100 }
                com.horcrux.svg.TextProperties$TextAnchor r1 = com.horcrux.svg.TextProperties.TextAnchor.end     // Catch:{ NoSuchFieldError -> 0x0100 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0100 }
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0100 }
            L_0x0100:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TSpanView.C22551.<clinit>():void");
        }
    }

    private double getAbsoluteStartOffset(SVGLength sVGLength, double d, double d2) {
        return PropHelper.fromRelative(sVGLength, d, 0.0d, (double) this.mScale, d2);
    }

    private double getTextAnchorOffset(TextProperties.TextAnchor textAnchor, double d) {
        int i = C22551.$SwitchMap$com$horcrux$svg$TextProperties$TextAnchor[textAnchor.ordinal()];
        if (i == 2) {
            return (-d) / 2.0d;
        }
        if (i != 3) {
            return 0.0d;
        }
        return -d;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(2:28|29) */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r0 = android.graphics.Typeface.create(android.graphics.Typeface.createFromAsset(r10.assets, r7), r2);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:28:0x00c5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void applyTextPropertiesToPaint(android.graphics.Paint r11, com.horcrux.svg.FontData r12) {
        /*
            r10 = this;
            com.horcrux.svg.TextProperties$FontWeight r0 = r12.fontWeight
            com.horcrux.svg.TextProperties$FontWeight r1 = com.horcrux.svg.TextProperties.FontWeight.Bold
            r2 = 0
            r3 = 1
            if (r0 == r1) goto L_0x0011
            int r0 = r12.absoluteFontWeight
            r1 = 550(0x226, float:7.71E-43)
            if (r0 < r1) goto L_0x000f
            goto L_0x0011
        L_0x000f:
            r0 = 0
            goto L_0x0012
        L_0x0011:
            r0 = 1
        L_0x0012:
            com.horcrux.svg.TextProperties$FontStyle r1 = r12.fontStyle
            com.horcrux.svg.TextProperties$FontStyle r4 = com.horcrux.svg.TextProperties.FontStyle.italic
            if (r1 != r4) goto L_0x001a
            r1 = 1
            goto L_0x001b
        L_0x001a:
            r1 = 0
        L_0x001b:
            if (r0 == 0) goto L_0x0021
            if (r1 == 0) goto L_0x0021
            r2 = 3
            goto L_0x0028
        L_0x0021:
            if (r0 == 0) goto L_0x0025
            r2 = 1
            goto L_0x0028
        L_0x0025:
            if (r1 == 0) goto L_0x0028
            r2 = 2
        L_0x0028:
            r0 = 0
            int r4 = r12.absoluteFontWeight
            java.lang.String r5 = r12.fontFamily
            if (r5 == 0) goto L_0x00d1
            int r6 = r5.length()
            if (r6 <= 0) goto L_0x00d1
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "fonts/"
            r6.append(r7)
            r6.append(r5)
            java.lang.String r8 = ".otf"
            r6.append(r8)
            java.lang.String r6 = r6.toString()
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r8.append(r7)
            r8.append(r5)
            java.lang.String r7 = ".ttf"
            r8.append(r7)
            java.lang.String r7 = r8.toString()
            int r8 = android.os.Build.VERSION.SDK_INT
            r9 = 26
            if (r8 < r9) goto L_0x00ba
            android.graphics.Typeface$Builder r0 = new android.graphics.Typeface$Builder
            android.content.res.AssetManager r8 = r10.assets
            r0.<init>(r8, r6)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r8 = "'wght' "
            r6.append(r8)
            r6.append(r4)
            java.lang.String r9 = r12.fontVariationSettings
            r6.append(r9)
            java.lang.String r6 = r6.toString()
            r0.setFontVariationSettings(r6)
            r0.setWeight(r4)
            r0.setItalic(r1)
            android.graphics.Typeface r0 = r0.build()
            if (r0 != 0) goto L_0x00d1
            android.graphics.Typeface$Builder r0 = new android.graphics.Typeface$Builder
            android.content.res.AssetManager r6 = r10.assets
            r0.<init>(r6, r7)
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r8)
            r6.append(r4)
            java.lang.String r7 = r12.fontVariationSettings
            r6.append(r7)
            java.lang.String r6 = r6.toString()
            r0.setFontVariationSettings(r6)
            r0.setWeight(r4)
            r0.setItalic(r1)
            android.graphics.Typeface r0 = r0.build()
            goto L_0x00d1
        L_0x00ba:
            android.content.res.AssetManager r8 = r10.assets     // Catch:{ Exception -> 0x00c5 }
            android.graphics.Typeface r0 = android.graphics.Typeface.createFromAsset(r8, r6)     // Catch:{ Exception -> 0x00c5 }
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r0, r2)     // Catch:{ Exception -> 0x00c5 }
            goto L_0x00d1
        L_0x00c5:
            android.content.res.AssetManager r6 = r10.assets     // Catch:{ Exception -> 0x00d0 }
            android.graphics.Typeface r0 = android.graphics.Typeface.createFromAsset(r6, r7)     // Catch:{ Exception -> 0x00d0 }
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r0, r2)     // Catch:{ Exception -> 0x00d0 }
            goto L_0x00d1
        L_0x00d0:
        L_0x00d1:
            if (r0 != 0) goto L_0x00df
            com.facebook.react.views.text.ReactFontManager r6 = com.facebook.react.views.text.ReactFontManager.getInstance()     // Catch:{ Exception -> 0x00de }
            android.content.res.AssetManager r7 = r10.assets     // Catch:{ Exception -> 0x00de }
            android.graphics.Typeface r0 = r6.getTypeface(r5, r2, r7)     // Catch:{ Exception -> 0x00de }
            goto L_0x00df
        L_0x00de:
        L_0x00df:
            int r2 = android.os.Build.VERSION.SDK_INT
            r5 = 28
            if (r2 < r5) goto L_0x00e9
            android.graphics.Typeface r0 = android.graphics.Typeface.create(r0, r4, r1)
        L_0x00e9:
            r11.setLinearText(r3)
            r11.setSubpixelText(r3)
            r11.setTypeface(r0)
            double r0 = r12.fontSize
            float r12 = r10.mScale
            double r2 = (double) r12
            double r0 = r0 * r2
            float r12 = (float) r0
            r11.setTextSize(r12)
            int r12 = android.os.Build.VERSION.SDK_INT
            r0 = 21
            if (r12 < r0) goto L_0x0107
            r12 = 0
            r11.setLetterSpacing(r12)
        L_0x0107:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.TSpanView.applyTextPropertiesToPaint(android.graphics.Paint, com.horcrux.svg.FontData):void");
    }

    private void setupTextPath() {
        ViewParent parent = getParent();
        while (parent != null) {
            if (parent.getClass() == TextPathView.class) {
                this.textPath = (TextPathView) parent;
                return;
            } else if (parent instanceof TextView) {
                parent = parent.getParent();
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int hitTest(float[] fArr) {
        if (this.mContent == null) {
            return super.hitTest(fArr);
        }
        if (this.mPath != null && this.mInvertible && this.mTransformInvertible) {
            float[] fArr2 = new float[2];
            this.mInvMatrix.mapPoints(fArr2, fArr);
            this.mInvTransform.mapPoints(fArr2);
            int round = Math.round(fArr2[0]);
            int round2 = Math.round(fArr2[1]);
            initBounds();
            if ((this.mRegion != null && this.mRegion.contains(round, round2)) || (this.mStrokeRegion != null && this.mStrokeRegion.contains(round, round2))) {
                if (getClipPath() == null || this.mClipRegion.contains(round, round2)) {
                    return getId();
                }
                return -1;
            }
        }
        return -1;
    }
}
