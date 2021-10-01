package com.facebook.react.views.text;

import android.content.Context;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.util.LruCache;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactStylesDiffMap;
import com.facebook.react.uimanager.ViewProps;
import java.util.ArrayList;
import java.util.List;

public class TextLayoutManager {
    private static final String INLINE_VIEW_PLACEHOLDER = "0";
    private static LruCache<String, Spannable> sSpannableCache = new LruCache<>(100);
    private static final Object sSpannableCacheLock = new Object();
    private static final TextPaint sTextPaintInstance = new TextPaint(1);
    private static final int spannableCacheSize = 100;

    private static void buildSpannableFromFragment(Context context, ReadableArray readableArray, SpannableStringBuilder spannableStringBuilder, List<SetSpanOperation> list) {
        int i;
        List<SetSpanOperation> list2 = list;
        int i2 = 0;
        for (int size = readableArray.size(); i2 < size; size = i) {
            ReadableMap map = readableArray.getMap(i2);
            int length = spannableStringBuilder.length();
            TextAttributeProps textAttributeProps = new TextAttributeProps(new ReactStylesDiffMap(map.getMap("textAttributes")));
            spannableStringBuilder.append(TextTransform.apply(map.getString("string"), textAttributeProps.mTextTransform));
            int length2 = spannableStringBuilder.length();
            int i3 = map.getInt("reactTag");
            if (map.hasKey(ViewProps.IS_ATTACHMENT) && map.getBoolean(ViewProps.IS_ATTACHMENT)) {
                list2.add(new SetSpanOperation(spannableStringBuilder.length() - 1, spannableStringBuilder.length(), new TextInlineViewPlaceholderSpan(i3, (int) PixelUtil.toPixelFromSP(map.getDouble("width")), (int) PixelUtil.toPixelFromSP(map.getDouble("height")))));
            } else if (length2 >= length) {
                if (textAttributeProps.mIsColorSet) {
                    list2.add(new SetSpanOperation(length, length2, new ReactForegroundColorSpan(textAttributeProps.mColor)));
                }
                if (textAttributeProps.mIsBackgroundColorSet) {
                    list2.add(new SetSpanOperation(length, length2, new ReactBackgroundColorSpan(textAttributeProps.mBackgroundColor)));
                }
                if (Build.VERSION.SDK_INT >= 21 && !Float.isNaN(textAttributeProps.getLetterSpacing())) {
                    list2.add(new SetSpanOperation(length, length2, new CustomLetterSpacingSpan(textAttributeProps.getLetterSpacing())));
                }
                list2.add(new SetSpanOperation(length, length2, new ReactAbsoluteSizeSpan(textAttributeProps.mFontSize)));
                if (textAttributeProps.mFontStyle == -1 && textAttributeProps.mFontWeight == -1 && textAttributeProps.mFontFamily == null) {
                    i = size;
                } else {
                    i = size;
                    CustomStyleSpan customStyleSpan = r10;
                    CustomStyleSpan customStyleSpan2 = new CustomStyleSpan(textAttributeProps.mFontStyle, textAttributeProps.mFontWeight, textAttributeProps.mFontFeatureSettings, textAttributeProps.mFontFamily, context.getAssets());
                    list2.add(new SetSpanOperation(length, length2, customStyleSpan));
                }
                if (textAttributeProps.mIsUnderlineTextDecorationSet) {
                    list2.add(new SetSpanOperation(length, length2, new ReactUnderlineSpan()));
                }
                if (textAttributeProps.mIsLineThroughTextDecorationSet) {
                    list2.add(new SetSpanOperation(length, length2, new ReactStrikethroughSpan()));
                }
                if (!(textAttributeProps.mTextShadowOffsetDx == 0.0f && textAttributeProps.mTextShadowOffsetDy == 0.0f)) {
                    list2.add(new SetSpanOperation(length, length2, new ShadowStyleSpan(textAttributeProps.mTextShadowOffsetDx, textAttributeProps.mTextShadowOffsetDy, textAttributeProps.mTextShadowRadius, textAttributeProps.mTextShadowColor)));
                }
                if (!Float.isNaN(textAttributeProps.getEffectiveLineHeight())) {
                    list2.add(new SetSpanOperation(length, length2, new CustomLineHeightSpan(textAttributeProps.getEffectiveLineHeight())));
                }
                list2.add(new SetSpanOperation(length, length2, new ReactTagSpan(i3)));
                i2++;
            }
            i = size;
            i2++;
        }
    }

    public static Spannable getOrCreateSpannableForText(Context context, ReadableMap readableMap, ReactTextViewManagerCallback reactTextViewManagerCallback) {
        String obj = readableMap.toString();
        synchronized (sSpannableCacheLock) {
            Spannable spannable = sSpannableCache.get(obj);
            if (spannable != null) {
                return spannable;
            }
            Spannable createSpannableFromAttributedString = createSpannableFromAttributedString(context, readableMap, reactTextViewManagerCallback);
            synchronized (sSpannableCacheLock) {
                sSpannableCache.put(obj, createSpannableFromAttributedString);
            }
            return createSpannableFromAttributedString;
        }
    }

    private static Spannable createSpannableFromAttributedString(Context context, ReadableMap readableMap, ReactTextViewManagerCallback reactTextViewManagerCallback) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        ArrayList<SetSpanOperation> arrayList = new ArrayList<>();
        buildSpannableFromFragment(context, readableMap.getArray("fragments"), spannableStringBuilder, arrayList);
        int i = 0;
        for (SetSpanOperation execute : arrayList) {
            execute.execute(spannableStringBuilder, i);
            i++;
        }
        if (reactTextViewManagerCallback != null) {
            reactTextViewManagerCallback.onPostProcessSpannable(spannableStringBuilder);
        }
        return spannableStringBuilder;
    }

    /* JADX WARNING: type inference failed for: r13v5, types: [android.text.BoringLayout] */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x018d, code lost:
        if (r11 != false) goto L_0x018f;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long measureText(android.content.Context r18, com.facebook.react.bridge.ReadableMap r19, com.facebook.react.bridge.ReadableMap r20, float r21, com.facebook.yoga.YogaMeasureMode r22, float r23, com.facebook.yoga.YogaMeasureMode r24, com.facebook.react.views.text.ReactTextViewManagerCallback r25, int[] r26) {
        /*
            r0 = r20
            r1 = r21
            android.text.TextPaint r3 = sTextPaintInstance
            r2 = r18
            r4 = r19
            r5 = r25
            android.text.Spannable r9 = getOrCreateSpannableForText(r2, r4, r5)
            java.lang.String r2 = "textBreakStrategy"
            java.lang.String r2 = r0.getString(r2)
            int r2 = com.facebook.react.views.text.TextAttributeProps.getTextBreakStrategy(r2)
            if (r9 == 0) goto L_0x01c6
            android.text.BoringLayout$Metrics r7 = android.text.BoringLayout.isBoring(r9, r3)
            if (r7 != 0) goto L_0x0028
            float r4 = android.text.Layout.getDesiredWidth(r9, r3)
            goto L_0x002a
        L_0x0028:
            r4 = 2143289344(0x7fc00000, float:NaN)
        L_0x002a:
            com.facebook.yoga.YogaMeasureMode r5 = com.facebook.yoga.YogaMeasureMode.UNDEFINED
            r6 = 0
            r10 = 0
            r11 = 1
            r8 = r22
            if (r8 == r5) goto L_0x003a
            int r5 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1))
            if (r5 >= 0) goto L_0x0038
            goto L_0x003a
        L_0x0038:
            r5 = 0
            goto L_0x003b
        L_0x003a:
            r5 = 1
        L_0x003b:
            int r12 = r9.length()
            r8 = 1065353216(0x3f800000, float:1.0)
            r13 = 23
            if (r7 != 0) goto L_0x0089
            if (r5 != 0) goto L_0x0051
            boolean r14 = com.facebook.yoga.YogaConstants.isUndefined((float) r4)
            if (r14 != 0) goto L_0x0089
            int r14 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r14 > 0) goto L_0x0089
        L_0x0051:
            double r4 = (double) r4
            double r4 = java.lang.Math.ceil(r4)
            int r4 = (int) r4
            int r1 = android.os.Build.VERSION.SDK_INT
            if (r1 >= r13) goto L_0x006a
            android.text.StaticLayout r13 = new android.text.StaticLayout
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_NORMAL
            r6 = 1065353216(0x3f800000, float:1.0)
            r7 = 0
            r8 = 1
            r1 = r13
            r2 = r9
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            goto L_0x00da
        L_0x006a:
            android.text.StaticLayout$Builder r1 = android.text.StaticLayout.Builder.obtain(r9, r10, r12, r3, r4)
            android.text.Layout$Alignment r3 = android.text.Layout.Alignment.ALIGN_NORMAL
            android.text.StaticLayout$Builder r1 = r1.setAlignment(r3)
            android.text.StaticLayout$Builder r1 = r1.setLineSpacing(r6, r8)
            android.text.StaticLayout$Builder r1 = r1.setIncludePad(r11)
            android.text.StaticLayout$Builder r1 = r1.setBreakStrategy(r2)
            android.text.StaticLayout$Builder r1 = r1.setHyphenationFrequency(r11)
            android.text.StaticLayout r13 = r1.build()
            goto L_0x00da
        L_0x0089:
            if (r7 == 0) goto L_0x00a8
            if (r5 != 0) goto L_0x0094
            int r4 = r7.width
            float r4 = (float) r4
            int r4 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
            if (r4 > 0) goto L_0x00a8
        L_0x0094:
            int r4 = r7.width
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_NORMAL
            r6 = 1065353216(0x3f800000, float:1.0)
            r8 = 0
            r13 = 1
            r1 = r9
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r8
            r8 = r13
            android.text.BoringLayout r13 = android.text.BoringLayout.make(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x00da
        L_0x00a8:
            int r4 = android.os.Build.VERSION.SDK_INT
            if (r4 >= r13) goto L_0x00bb
            android.text.StaticLayout r13 = new android.text.StaticLayout
            int r4 = (int) r1
            android.text.Layout$Alignment r5 = android.text.Layout.Alignment.ALIGN_NORMAL
            r6 = 1065353216(0x3f800000, float:1.0)
            r7 = 0
            r8 = 1
            r1 = r13
            r2 = r9
            r1.<init>(r2, r3, r4, r5, r6, r7, r8)
            goto L_0x00da
        L_0x00bb:
            int r1 = (int) r1
            android.text.StaticLayout$Builder r1 = android.text.StaticLayout.Builder.obtain(r9, r10, r12, r3, r1)
            android.text.Layout$Alignment r3 = android.text.Layout.Alignment.ALIGN_NORMAL
            android.text.StaticLayout$Builder r1 = r1.setAlignment(r3)
            android.text.StaticLayout$Builder r1 = r1.setLineSpacing(r6, r8)
            android.text.StaticLayout$Builder r1 = r1.setIncludePad(r11)
            android.text.StaticLayout$Builder r1 = r1.setBreakStrategy(r2)
            android.text.StaticLayout$Builder r1 = r1.setHyphenationFrequency(r11)
            android.text.StaticLayout r13 = r1.build()
        L_0x00da:
            java.lang.String r1 = "maximumNumberOfLines"
            boolean r2 = r0.hasKey(r1)
            r3 = -1
            if (r2 == 0) goto L_0x00e8
            int r0 = r0.getInt(r1)
            goto L_0x00e9
        L_0x00e8:
            r0 = -1
        L_0x00e9:
            int r1 = r13.getWidth()
            if (r0 == r3) goto L_0x00fd
            if (r0 == 0) goto L_0x00fd
            int r2 = r13.getLineCount()
            if (r0 >= r2) goto L_0x00fd
            int r0 = r0 - r11
            int r0 = r13.getLineBottom(r0)
            goto L_0x0101
        L_0x00fd:
            int r0 = r13.getHeight()
        L_0x0101:
            r2 = 0
            r4 = 0
        L_0x0103:
            if (r2 >= r12) goto L_0x01b7
            java.lang.Class<com.facebook.react.views.text.TextInlineViewPlaceholderSpan> r5 = com.facebook.react.views.text.TextInlineViewPlaceholderSpan.class
            int r5 = r9.nextSpanTransition(r2, r12, r5)
            java.lang.Class<com.facebook.react.views.text.TextInlineViewPlaceholderSpan> r6 = com.facebook.react.views.text.TextInlineViewPlaceholderSpan.class
            java.lang.Object[] r2 = r9.getSpans(r2, r5, r6)
            com.facebook.react.views.text.TextInlineViewPlaceholderSpan[] r2 = (com.facebook.react.views.text.TextInlineViewPlaceholderSpan[]) r2
            int r6 = r2.length
            r7 = 0
        L_0x0115:
            if (r7 >= r6) goto L_0x01b4
            r8 = r2[r7]
            int r14 = r9.getSpanStart(r8)
            int r15 = r13.getLineForOffset(r14)
            int r16 = r13.getEllipsisCount(r15)
            if (r16 <= 0) goto L_0x012a
            r16 = 1
            goto L_0x012c
        L_0x012a:
            r16 = 0
        L_0x012c:
            if (r16 == 0) goto L_0x0145
            int r16 = r13.getLineStart(r15)
            int r17 = r13.getEllipsisStart(r15)
            int r10 = r16 + r17
            if (r14 < r10) goto L_0x0145
            int r10 = r13.getLineEnd(r15)
            if (r14 < r10) goto L_0x0141
            goto L_0x0145
        L_0x0141:
            r20 = r2
            r3 = 1
            goto L_0x01ab
        L_0x0145:
            int r10 = r8.getWidth()
            int r8 = r8.getHeight()
            boolean r11 = r13.isRtlCharAt(r14)
            r20 = r2
            int r2 = r13.getParagraphDirection(r15)
            if (r2 != r3) goto L_0x015b
            r2 = 1
            goto L_0x015c
        L_0x015b:
            r2 = 0
        L_0x015c:
            int r3 = r12 + -1
            if (r14 != r3) goto L_0x0170
            if (r2 == 0) goto L_0x016a
            float r2 = r13.getLineWidth(r15)
            int r2 = (int) r2
            int r2 = r1 - r2
            goto L_0x0190
        L_0x016a:
            float r2 = r13.getLineRight(r15)
            int r2 = (int) r2
            goto L_0x018f
        L_0x0170:
            if (r2 != r11) goto L_0x0174
            r3 = 1
            goto L_0x0175
        L_0x0174:
            r3 = 0
        L_0x0175:
            if (r3 == 0) goto L_0x017c
            float r3 = r13.getPrimaryHorizontal(r14)
            goto L_0x0180
        L_0x017c:
            float r3 = r13.getSecondaryHorizontal(r14)
        L_0x0180:
            int r3 = (int) r3
            if (r2 == 0) goto L_0x018c
            float r2 = r13.getLineRight(r15)
            int r2 = (int) r2
            int r2 = r2 - r3
            int r2 = r1 - r2
            goto L_0x018d
        L_0x018c:
            r2 = r3
        L_0x018d:
            if (r11 == 0) goto L_0x0190
        L_0x018f:
            int r2 = r2 - r10
        L_0x0190:
            int r3 = r13.getLineBaseline(r15)
            int r3 = r3 - r8
            int r8 = r4 * 2
            float r3 = (float) r3
            float r3 = com.facebook.react.uimanager.PixelUtil.toSPFromPixel(r3)
            int r3 = (int) r3
            r26[r8] = r3
            r3 = 1
            int r8 = r8 + r3
            float r2 = (float) r2
            float r2 = com.facebook.react.uimanager.PixelUtil.toSPFromPixel(r2)
            int r2 = (int) r2
            r26[r8] = r2
            int r4 = r4 + 1
        L_0x01ab:
            int r7 = r7 + 1
            r2 = r20
            r3 = -1
            r10 = 0
            r11 = 1
            goto L_0x0115
        L_0x01b4:
            r2 = r5
            goto L_0x0103
        L_0x01b7:
            float r1 = (float) r1
            float r1 = com.facebook.react.uimanager.PixelUtil.toSPFromPixel(r1)
            float r0 = (float) r0
            float r0 = com.facebook.react.uimanager.PixelUtil.toSPFromPixel(r0)
            long r0 = com.facebook.yoga.YogaMeasureOutput.make((float) r1, (float) r0)
            return r0
        L_0x01c6:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Spannable element has not been prepared in onBeforeLayout"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.views.text.TextLayoutManager.measureText(android.content.Context, com.facebook.react.bridge.ReadableMap, com.facebook.react.bridge.ReadableMap, float, com.facebook.yoga.YogaMeasureMode, float, com.facebook.yoga.YogaMeasureMode, com.facebook.react.views.text.ReactTextViewManagerCallback, int[]):long");
    }

    public static class SetSpanOperation {
        protected int end;
        protected int start;
        protected ReactSpan what;

        SetSpanOperation(int i, int i2, ReactSpan reactSpan) {
            this.start = i;
            this.end = i2;
            this.what = reactSpan;
        }

        public void execute(SpannableStringBuilder spannableStringBuilder, int i) {
            spannableStringBuilder.setSpan(this.what, this.start, this.end, ((i << 16) & 16711680) | ((this.start == 0 ? 18 : 34) & -16711681));
        }
    }
}
