package com.facebook.react.views.text;

import android.os.Build;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.Spannable;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactNoCrashSoftException;
import com.facebook.react.bridge.ReactSoftException;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.NativeViewHierarchyOptimizer;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ReactShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIViewOperationQueue;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.yoga.YogaConstants;
import com.facebook.yoga.YogaDirection;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureOutput;
import com.facebook.yoga.YogaNode;
import java.util.ArrayList;

public class ReactTextShadowNode extends ReactBaseTextShadowNode {
    /* access modifiers changed from: private */
    public static final TextPaint sTextPaintInstance = new TextPaint(1);
    /* access modifiers changed from: private */
    public Spannable mPreparedSpannableText;
    /* access modifiers changed from: private */
    public boolean mShouldNotifyOnTextLayout;
    private final YogaMeasureFunction mTextMeasureFunction;

    public boolean hoistNativeChildren() {
        return true;
    }

    public boolean isVirtualAnchor() {
        return false;
    }

    public ReactTextShadowNode() {
        this((ReactTextViewManagerCallback) null);
    }

    public ReactTextShadowNode(ReactTextViewManagerCallback reactTextViewManagerCallback) {
        super(reactTextViewManagerCallback);
        this.mTextMeasureFunction = new YogaMeasureFunction() {
            public long measure(YogaNode yogaNode, float f, YogaMeasureMode yogaMeasureMode, float f2, YogaMeasureMode yogaMeasureMode2) {
                float f3 = f;
                YogaMeasureMode yogaMeasureMode3 = yogaMeasureMode;
                Spannable spannable = (Spannable) Assertions.assertNotNull(ReactTextShadowNode.this.mPreparedSpannableText, "Spannable element has not been prepared in onBeforeLayout");
                Layout access$100 = ReactTextShadowNode.this.measureSpannedText(spannable, f3, yogaMeasureMode3);
                int i = -1;
                if (ReactTextShadowNode.this.mAdjustsFontSizeToFit) {
                    int effectiveFontSize = ReactTextShadowNode.this.mTextAttributes.getEffectiveFontSize();
                    int effectiveFontSize2 = ReactTextShadowNode.this.mTextAttributes.getEffectiveFontSize();
                    float f4 = (float) effectiveFontSize;
                    int max = (int) Math.max(ReactTextShadowNode.this.mMinimumFontScale * f4, PixelUtil.toPixelFromDIP(4.0f));
                    while (effectiveFontSize2 > max) {
                        if (ReactTextShadowNode.this.mNumberOfLines == i || access$100.getLineCount() <= ReactTextShadowNode.this.mNumberOfLines) {
                            if (yogaMeasureMode2 != YogaMeasureMode.UNDEFINED) {
                                if (((float) access$100.getHeight()) <= f2) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        } else {
                            YogaMeasureMode yogaMeasureMode4 = yogaMeasureMode2;
                        }
                        effectiveFontSize2 -= (int) PixelUtil.toPixelFromDIP(1.0f);
                        float f5 = ((float) effectiveFontSize2) / f4;
                        int i2 = 0;
                        ReactAbsoluteSizeSpan[] reactAbsoluteSizeSpanArr = (ReactAbsoluteSizeSpan[]) spannable.getSpans(0, spannable.length(), ReactAbsoluteSizeSpan.class);
                        int length = reactAbsoluteSizeSpanArr.length;
                        while (i2 < length) {
                            ReactAbsoluteSizeSpan reactAbsoluteSizeSpan = reactAbsoluteSizeSpanArr[i2];
                            spannable.setSpan(new ReactAbsoluteSizeSpan((int) Math.max(((float) reactAbsoluteSizeSpan.getSize()) * f5, (float) max)), spannable.getSpanStart(reactAbsoluteSizeSpan), spannable.getSpanEnd(reactAbsoluteSizeSpan), spannable.getSpanFlags(reactAbsoluteSizeSpan));
                            spannable.removeSpan(reactAbsoluteSizeSpan);
                            i2++;
                            f5 = f5;
                        }
                        access$100 = ReactTextShadowNode.this.measureSpannedText(spannable, f3, yogaMeasureMode3);
                        i = -1;
                    }
                }
                if (ReactTextShadowNode.this.mShouldNotifyOnTextLayout) {
                    ThemedReactContext themedContext = ReactTextShadowNode.this.getThemedContext();
                    WritableArray fontMetrics = FontMetricsUtil.getFontMetrics(spannable, access$100, ReactTextShadowNode.sTextPaintInstance, themedContext);
                    WritableMap createMap = Arguments.createMap();
                    createMap.putArray("lines", fontMetrics);
                    if (themedContext.hasActiveCatalystInstance()) {
                        ((RCTEventEmitter) themedContext.getJSModule(RCTEventEmitter.class)).receiveEvent(ReactTextShadowNode.this.getReactTag(), "topTextLayout", createMap);
                    } else {
                        ReactSoftException.logSoftException("ReactTextShadowNode", new ReactNoCrashSoftException("Cannot get RCTEventEmitter, no CatalystInstance"));
                    }
                }
                if (ReactTextShadowNode.this.mNumberOfLines == -1 || ReactTextShadowNode.this.mNumberOfLines >= access$100.getLineCount()) {
                    return YogaMeasureOutput.make(access$100.getWidth(), access$100.getHeight());
                }
                return YogaMeasureOutput.make(access$100.getWidth(), access$100.getLineBottom(ReactTextShadowNode.this.mNumberOfLines - 1));
            }
        };
        initMeasureFunction();
    }

    private void initMeasureFunction() {
        if (!isVirtual()) {
            setMeasureFunction(this.mTextMeasureFunction);
        }
    }

    /* access modifiers changed from: private */
    public Layout measureSpannedText(Spannable spannable, float f, YogaMeasureMode yogaMeasureMode) {
        TextPaint textPaint = sTextPaintInstance;
        textPaint.setTextSize((float) this.mTextAttributes.getEffectiveFontSize());
        BoringLayout.Metrics isBoring = BoringLayout.isBoring(spannable, textPaint);
        float desiredWidth = isBoring == null ? Layout.getDesiredWidth(spannable, textPaint) : Float.NaN;
        boolean z = yogaMeasureMode == YogaMeasureMode.UNDEFINED || f < 0.0f;
        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
        int textAlign = getTextAlign();
        if (textAlign == 1) {
            alignment = Layout.Alignment.ALIGN_CENTER;
        } else if (textAlign == 3) {
            alignment = Layout.Alignment.ALIGN_NORMAL;
        } else if (textAlign == 5) {
            alignment = Layout.Alignment.ALIGN_OPPOSITE;
        }
        Layout.Alignment alignment2 = alignment;
        if (isBoring == null && (z || (!YogaConstants.isUndefined(desiredWidth) && desiredWidth <= f))) {
            int ceil = (int) Math.ceil((double) desiredWidth);
            if (Build.VERSION.SDK_INT < 23) {
                return new StaticLayout(spannable, textPaint, ceil, alignment2, 1.0f, 0.0f, this.mIncludeFontPadding);
            }
            StaticLayout.Builder hyphenationFrequency = StaticLayout.Builder.obtain(spannable, 0, spannable.length(), textPaint, ceil).setAlignment(alignment2).setLineSpacing(0.0f, 1.0f).setIncludePad(this.mIncludeFontPadding).setBreakStrategy(this.mTextBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency);
            if (Build.VERSION.SDK_INT >= 26) {
                hyphenationFrequency.setJustificationMode(this.mJustificationMode);
            }
            if (Build.VERSION.SDK_INT >= 28) {
                hyphenationFrequency.setUseLineSpacingFromFallbacks(true);
            }
            return hyphenationFrequency.build();
        } else if (isBoring != null && (z || ((float) isBoring.width) <= f)) {
            return BoringLayout.make(spannable, textPaint, isBoring.width, alignment2, 1.0f, 0.0f, isBoring, this.mIncludeFontPadding);
        } else if (Build.VERSION.SDK_INT < 23) {
            return new StaticLayout(spannable, textPaint, (int) f, alignment2, 1.0f, 0.0f, this.mIncludeFontPadding);
        } else {
            StaticLayout.Builder hyphenationFrequency2 = StaticLayout.Builder.obtain(spannable, 0, spannable.length(), textPaint, (int) f).setAlignment(alignment2).setLineSpacing(0.0f, 1.0f).setIncludePad(this.mIncludeFontPadding).setBreakStrategy(this.mTextBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency);
            if (Build.VERSION.SDK_INT >= 28) {
                hyphenationFrequency2.setUseLineSpacingFromFallbacks(true);
            }
            return hyphenationFrequency2.build();
        }
    }

    private int getTextAlign() {
        int i = this.mTextAlign;
        if (getLayoutDirection() != YogaDirection.RTL) {
            return i;
        }
        if (i == 5) {
            return 3;
        }
        if (i == 3) {
            return 5;
        }
        return i;
    }

    public void onBeforeLayout(NativeViewHierarchyOptimizer nativeViewHierarchyOptimizer) {
        this.mPreparedSpannableText = spannedFromShadowNode(this, (String) null, true, nativeViewHierarchyOptimizer);
        markUpdated();
    }

    public void markUpdated() {
        super.markUpdated();
        super.dirty();
    }

    public void onCollectExtraUpdates(UIViewOperationQueue uIViewOperationQueue) {
        super.onCollectExtraUpdates(uIViewOperationQueue);
        Spannable spannable = this.mPreparedSpannableText;
        if (spannable != null) {
            uIViewOperationQueue.enqueueUpdateExtraData(getReactTag(), new ReactTextUpdate(spannable, -1, this.mContainsImages, getPadding(4), getPadding(1), getPadding(5), getPadding(3), getTextAlign(), this.mTextBreakStrategy, this.mJustificationMode));
        }
    }

    @ReactProp(name = "onTextLayout")
    public void setShouldNotifyOnTextLayout(boolean z) {
        this.mShouldNotifyOnTextLayout = z;
    }

    public Iterable<? extends ReactShadowNode> calculateLayoutOnChildren() {
        if (this.mInlineViews == null || this.mInlineViews.isEmpty()) {
            return null;
        }
        Spanned spanned = (Spanned) Assertions.assertNotNull(this.mPreparedSpannableText, "Spannable element has not been prepared in onBeforeLayout");
        TextInlineViewPlaceholderSpan[] textInlineViewPlaceholderSpanArr = (TextInlineViewPlaceholderSpan[]) spanned.getSpans(0, spanned.length(), TextInlineViewPlaceholderSpan.class);
        ArrayList arrayList = new ArrayList(textInlineViewPlaceholderSpanArr.length);
        for (TextInlineViewPlaceholderSpan reactTag : textInlineViewPlaceholderSpanArr) {
            ReactShadowNode reactShadowNode = (ReactShadowNode) this.mInlineViews.get(Integer.valueOf(reactTag.getReactTag()));
            reactShadowNode.calculateLayout();
            arrayList.add(reactShadowNode);
        }
        return arrayList;
    }
}
