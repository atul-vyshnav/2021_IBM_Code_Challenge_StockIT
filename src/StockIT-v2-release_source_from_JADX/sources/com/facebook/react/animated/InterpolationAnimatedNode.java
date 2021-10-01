package com.facebook.react.animated;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class InterpolationAnimatedNode extends ValueAnimatedNode {
    public static final String EXTRAPOLATE_TYPE_CLAMP = "clamp";
    public static final String EXTRAPOLATE_TYPE_EXTEND = "extend";
    public static final String EXTRAPOLATE_TYPE_IDENTITY = "identity";
    private static final Pattern fpPattern = Pattern.compile(fpRegex);
    private static final String fpRegex = "[+-]?(\\d+\\.?\\d*|\\.\\d+)([eE][+-]?\\d+)?";
    private final String mExtrapolateLeft;
    private final String mExtrapolateRight;
    private final boolean mHasStringOutput;
    private final double[] mInputRange;
    private int mNumVals;
    private final double[] mOutputRange;
    private double[][] mOutputs;
    private ValueAnimatedNode mParent;
    private String mPattern;
    private final Matcher mSOutputMatcher;
    private boolean mShouldRound;

    private static double[] fromDoubleArray(ReadableArray readableArray) {
        int size = readableArray.size();
        double[] dArr = new double[size];
        for (int i = 0; i < size; i++) {
            dArr[i] = readableArray.getDouble(i);
        }
        return dArr;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0083, code lost:
        if (r1.equals(EXTRAPOLATE_TYPE_IDENTITY) != false) goto L_0x008f;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0043  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0065 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00b3 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00b8 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00b9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static double interpolate(double r16, double r18, double r20, double r22, double r24, java.lang.String r26, java.lang.String r27) {
        /*
            r0 = r26
            r1 = r27
            r2 = 0
            java.lang.String r3 = "Invalid extrapolation type "
            java.lang.String r4 = "clamp"
            java.lang.String r5 = "identity"
            java.lang.String r6 = "extend"
            r7 = 94742715(0x5a5a8bb, float:1.5578507E-35)
            r8 = -135761730(0xfffffffff7e870be, float:-9.428903E33)
            r9 = -1289044198(0xffffffffb32abf1a, float:-3.9755015E-8)
            r10 = -1
            r11 = 2
            r12 = 1
            int r13 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1))
            if (r13 >= 0) goto L_0x0066
            int r14 = r26.hashCode()
            if (r14 == r9) goto L_0x0038
            if (r14 == r8) goto L_0x0030
            if (r14 == r7) goto L_0x0028
            goto L_0x0040
        L_0x0028:
            boolean r14 = r0.equals(r4)
            if (r14 == 0) goto L_0x0040
            r14 = 1
            goto L_0x0041
        L_0x0030:
            boolean r14 = r0.equals(r5)
            if (r14 == 0) goto L_0x0040
            r14 = 0
            goto L_0x0041
        L_0x0038:
            boolean r14 = r0.equals(r6)
            if (r14 == 0) goto L_0x0040
            r14 = 2
            goto L_0x0041
        L_0x0040:
            r14 = -1
        L_0x0041:
            if (r14 == 0) goto L_0x0065
            if (r14 == r12) goto L_0x0062
            if (r14 != r11) goto L_0x0048
            goto L_0x0066
        L_0x0048:
            com.facebook.react.bridge.JSApplicationIllegalArgumentException r1 = new com.facebook.react.bridge.JSApplicationIllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = "for left extrapolation"
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            r1.<init>(r0)
            throw r1
        L_0x0062:
            r14 = r18
            goto L_0x0068
        L_0x0065:
            return r16
        L_0x0066:
            r14 = r16
        L_0x0068:
            int r0 = (r14 > r20 ? 1 : (r14 == r20 ? 0 : -1))
            if (r0 <= 0) goto L_0x00b4
            int r0 = r27.hashCode()
            if (r0 == r9) goto L_0x0086
            if (r0 == r8) goto L_0x007f
            if (r0 == r7) goto L_0x0077
            goto L_0x008e
        L_0x0077:
            boolean r0 = r1.equals(r4)
            if (r0 == 0) goto L_0x008e
            r2 = 1
            goto L_0x008f
        L_0x007f:
            boolean r0 = r1.equals(r5)
            if (r0 == 0) goto L_0x008e
            goto L_0x008f
        L_0x0086:
            boolean r0 = r1.equals(r6)
            if (r0 == 0) goto L_0x008e
            r2 = 2
            goto L_0x008f
        L_0x008e:
            r2 = -1
        L_0x008f:
            if (r2 == 0) goto L_0x00b3
            if (r2 == r12) goto L_0x00b0
            if (r2 != r11) goto L_0x0096
            goto L_0x00b4
        L_0x0096:
            com.facebook.react.bridge.JSApplicationIllegalArgumentException r0 = new com.facebook.react.bridge.JSApplicationIllegalArgumentException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            r2.append(r1)
            java.lang.String r1 = "for right extrapolation"
            r2.append(r1)
            java.lang.String r1 = r2.toString()
            r0.<init>(r1)
            throw r0
        L_0x00b0:
            r14 = r20
            goto L_0x00b4
        L_0x00b3:
            return r14
        L_0x00b4:
            int r0 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1))
            if (r0 != 0) goto L_0x00b9
            return r22
        L_0x00b9:
            int r0 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1))
            if (r0 != 0) goto L_0x00c1
            if (r13 > 0) goto L_0x00c0
            return r22
        L_0x00c0:
            return r24
        L_0x00c1:
            double r0 = r24 - r22
            double r14 = r14 - r18
            double r0 = r0 * r14
            double r2 = r20 - r18
            double r0 = r0 / r2
            double r0 = r22 + r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.animated.InterpolationAnimatedNode.interpolate(double, double, double, double, double, java.lang.String, java.lang.String):double");
    }

    static double interpolate(double d, double[] dArr, double[] dArr2, String str, String str2) {
        int findRangeIndex = findRangeIndex(d, dArr);
        int i = findRangeIndex + 1;
        return interpolate(d, dArr[findRangeIndex], dArr[i], dArr2[findRangeIndex], dArr2[i], str, str2);
    }

    private static int findRangeIndex(double d, double[] dArr) {
        int i = 1;
        while (i < dArr.length - 1 && dArr[i] < d) {
            i++;
        }
        return i - 1;
    }

    public InterpolationAnimatedNode(ReadableMap readableMap) {
        this.mInputRange = fromDoubleArray(readableMap.getArray("inputRange"));
        ReadableArray array = readableMap.getArray("outputRange");
        boolean z = array.getType(0) == ReadableType.String;
        this.mHasStringOutput = z;
        if (z) {
            int size = array.size();
            this.mOutputRange = new double[size];
            String string = array.getString(0);
            this.mPattern = string;
            this.mShouldRound = string.startsWith("rgb");
            this.mSOutputMatcher = fpPattern.matcher(this.mPattern);
            ArrayList arrayList = new ArrayList();
            for (int i = 0; i < size; i++) {
                Matcher matcher = fpPattern.matcher(array.getString(i));
                ArrayList arrayList2 = new ArrayList();
                arrayList.add(arrayList2);
                while (matcher.find()) {
                    arrayList2.add(Double.valueOf(Double.parseDouble(matcher.group())));
                }
                this.mOutputRange[i] = ((Double) arrayList2.get(0)).doubleValue();
            }
            int size2 = ((ArrayList) arrayList.get(0)).size();
            this.mNumVals = size2;
            this.mOutputs = new double[size2][];
            for (int i2 = 0; i2 < this.mNumVals; i2++) {
                double[] dArr = new double[size];
                this.mOutputs[i2] = dArr;
                for (int i3 = 0; i3 < size; i3++) {
                    dArr[i3] = ((Double) ((ArrayList) arrayList.get(i3)).get(i2)).doubleValue();
                }
            }
        } else {
            this.mOutputRange = fromDoubleArray(array);
            this.mSOutputMatcher = null;
        }
        this.mExtrapolateLeft = readableMap.getString("extrapolateLeft");
        this.mExtrapolateRight = readableMap.getString("extrapolateRight");
    }

    public void onAttachedToNode(AnimatedNode animatedNode) {
        if (this.mParent != null) {
            throw new IllegalStateException("Parent already attached");
        } else if (animatedNode instanceof ValueAnimatedNode) {
            this.mParent = (ValueAnimatedNode) animatedNode;
        } else {
            throw new IllegalArgumentException("Parent is of an invalid type");
        }
    }

    public void onDetachedFromNode(AnimatedNode animatedNode) {
        if (animatedNode == this.mParent) {
            this.mParent = null;
            return;
        }
        throw new IllegalArgumentException("Invalid parent node provided");
    }

    public void update() {
        ValueAnimatedNode valueAnimatedNode = this.mParent;
        if (valueAnimatedNode != null) {
            double value = valueAnimatedNode.getValue();
            this.mValue = interpolate(value, this.mInputRange, this.mOutputRange, this.mExtrapolateLeft, this.mExtrapolateRight);
            if (!this.mHasStringOutput) {
                return;
            }
            if (this.mNumVals > 1) {
                StringBuffer stringBuffer = new StringBuffer(this.mPattern.length());
                this.mSOutputMatcher.reset();
                int i = 0;
                while (this.mSOutputMatcher.find()) {
                    int i2 = i + 1;
                    double interpolate = interpolate(value, this.mInputRange, this.mOutputs[i], this.mExtrapolateLeft, this.mExtrapolateRight);
                    if (this.mShouldRound) {
                        boolean z = i2 == 4;
                        if (z) {
                            interpolate *= 1000.0d;
                        }
                        int round = (int) Math.round(interpolate);
                        this.mSOutputMatcher.appendReplacement(stringBuffer, z ? Double.toString(((double) round) / 1000.0d) : Integer.toString(round));
                    } else {
                        int i3 = (int) interpolate;
                        this.mSOutputMatcher.appendReplacement(stringBuffer, ((double) i3) != interpolate ? Double.toString(interpolate) : Integer.toString(i3));
                    }
                    i = i2;
                }
                this.mSOutputMatcher.appendTail(stringBuffer);
                this.mAnimatedObject = stringBuffer.toString();
                return;
            }
            this.mAnimatedObject = this.mSOutputMatcher.replaceFirst(String.valueOf(this.mValue));
        }
    }
}
