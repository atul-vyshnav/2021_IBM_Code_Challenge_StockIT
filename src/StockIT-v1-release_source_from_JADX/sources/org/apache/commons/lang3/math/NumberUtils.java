package org.apache.commons.lang3.math;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class NumberUtils {
    public static final Byte BYTE_MINUS_ONE = (byte) -1;
    public static final Byte BYTE_ONE = (byte) 1;
    public static final Byte BYTE_ZERO = (byte) 0;
    public static final Double DOUBLE_MINUS_ONE = Double.valueOf(-1.0d);
    public static final Double DOUBLE_ONE = Double.valueOf(1.0d);
    public static final Double DOUBLE_ZERO = Double.valueOf(0.0d);
    public static final Float FLOAT_MINUS_ONE = Float.valueOf(-1.0f);
    public static final Float FLOAT_ONE = Float.valueOf(1.0f);
    public static final Float FLOAT_ZERO = Float.valueOf(0.0f);
    public static final Integer INTEGER_MINUS_ONE = -1;
    public static final Integer INTEGER_ONE = 1;
    public static final Integer INTEGER_TWO = 2;
    public static final Integer INTEGER_ZERO = 0;
    public static final Long LONG_MINUS_ONE = -1L;
    public static final Long LONG_ONE = 1L;
    public static final Long LONG_ZERO = 0L;
    public static final Short SHORT_MINUS_ONE = -1;
    public static final Short SHORT_ONE = 1;
    public static final Short SHORT_ZERO = 0;

    public static int compare(byte b, byte b2) {
        return b - b2;
    }

    public static int compare(int i, int i2) {
        if (i == i2) {
            return 0;
        }
        return i < i2 ? -1 : 1;
    }

    public static int compare(long j, long j2) {
        int i = (j > j2 ? 1 : (j == j2 ? 0 : -1));
        if (i == 0) {
            return 0;
        }
        return i < 0 ? -1 : 1;
    }

    public static int compare(short s, short s2) {
        if (s == s2) {
            return 0;
        }
        return s < s2 ? -1 : 1;
    }

    public static byte max(byte b, byte b2, byte b3) {
        if (b2 > b) {
            b = b2;
        }
        return b3 > b ? b3 : b;
    }

    public static int max(int i, int i2, int i3) {
        if (i2 > i) {
            i = i2;
        }
        return i3 > i ? i3 : i;
    }

    public static long max(long j, long j2, long j3) {
        if (j2 > j) {
            j = j2;
        }
        return j3 > j ? j3 : j;
    }

    public static short max(short s, short s2, short s3) {
        if (s2 > s) {
            s = s2;
        }
        return s3 > s ? s3 : s;
    }

    public static byte min(byte b, byte b2, byte b3) {
        if (b2 < b) {
            b = b2;
        }
        return b3 < b ? b3 : b;
    }

    public static int min(int i, int i2, int i3) {
        if (i2 < i) {
            i = i2;
        }
        return i3 < i ? i3 : i;
    }

    public static long min(long j, long j2, long j3) {
        if (j2 < j) {
            j = j2;
        }
        return j3 < j ? j3 : j;
    }

    public static short min(short s, short s2, short s3) {
        if (s2 < s) {
            s = s2;
        }
        return s3 < s ? s3 : s;
    }

    public static int toInt(String str) {
        return toInt(str, 0);
    }

    public static int toInt(String str, int i) {
        if (str == null) {
            return i;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    public static long toLong(String str) {
        return toLong(str, 0);
    }

    public static long toLong(String str, long j) {
        if (str == null) {
            return j;
        }
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return j;
        }
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.0f);
    }

    public static float toFloat(String str, float f) {
        if (str == null) {
            return f;
        }
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            return f;
        }
    }

    public static double toDouble(String str) {
        return toDouble(str, 0.0d);
    }

    public static double toDouble(String str, double d) {
        if (str == null) {
            return d;
        }
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return d;
        }
    }

    public static double toDouble(BigDecimal bigDecimal) {
        return toDouble(bigDecimal, 0.0d);
    }

    public static double toDouble(BigDecimal bigDecimal, double d) {
        return bigDecimal == null ? d : bigDecimal.doubleValue();
    }

    public static byte toByte(String str) {
        return toByte(str, (byte) 0);
    }

    public static byte toByte(String str, byte b) {
        if (str == null) {
            return b;
        }
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException unused) {
            return b;
        }
    }

    public static short toShort(String str) {
        return toShort(str, 0);
    }

    public static short toShort(String str, short s) {
        if (str == null) {
            return s;
        }
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException unused) {
            return s;
        }
    }

    public static BigDecimal toScaledBigDecimal(BigDecimal bigDecimal) {
        return toScaledBigDecimal(bigDecimal, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(BigDecimal bigDecimal, int i, RoundingMode roundingMode) {
        if (bigDecimal == null) {
            return BigDecimal.ZERO;
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_EVEN;
        }
        return bigDecimal.setScale(i, roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(Float f) {
        return toScaledBigDecimal(f, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(Float f, int i, RoundingMode roundingMode) {
        if (f == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(BigDecimal.valueOf((double) f.floatValue()), i, roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(Double d) {
        return toScaledBigDecimal(d, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(Double d, int i, RoundingMode roundingMode) {
        if (d == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(BigDecimal.valueOf(d.doubleValue()), i, roundingMode);
    }

    public static BigDecimal toScaledBigDecimal(String str) {
        return toScaledBigDecimal(str, INTEGER_TWO.intValue(), RoundingMode.HALF_EVEN);
    }

    public static BigDecimal toScaledBigDecimal(String str, int i, RoundingMode roundingMode) {
        if (str == null) {
            return BigDecimal.ZERO;
        }
        return toScaledBigDecimal(createBigDecimal(str), i, roundingMode);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(11:(1:59)|60|(1:65)(1:64)|66|(5:68|(3:70|(2:72|(2:74|(1:76)))|(2:94|95)(3:88|89|90))|96|97|(1:103))|104|105|(1:111)|112|113|114) */
    /* JADX WARNING: Code restructure failed: missing block: B:117:0x01bd, code lost:
        throw new java.lang.NumberFormatException(r14 + " is not a valid number.");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:129:0x01dd, code lost:
        return createLong(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x01e2, code lost:
        return createBigInteger(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x0136, code lost:
        if (r1 == 'l') goto L_0x0138;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:104:0x018e */
    /* JADX WARNING: Missing exception handler attribute for start block: B:112:0x01a4 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:127:0x01d9 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Number createNumber(java.lang.String r14) {
        /*
            r0 = 0
            if (r14 != 0) goto L_0x0004
            return r0
        L_0x0004:
            boolean r1 = org.apache.commons.lang3.StringUtils.isBlank(r14)
            if (r1 != 0) goto L_0x0240
            r1 = 6
            java.lang.String r2 = "0x"
            java.lang.String r3 = "0X"
            java.lang.String r4 = "-0x"
            java.lang.String r5 = "-0X"
            java.lang.String r6 = "#"
            java.lang.String r7 = "-#"
            java.lang.String[] r2 = new java.lang.String[]{r2, r3, r4, r5, r6, r7}
            r3 = 0
            r4 = 0
        L_0x001d:
            if (r4 >= r1) goto L_0x0030
            r5 = r2[r4]
            boolean r6 = r14.startsWith(r5)
            if (r6 == 0) goto L_0x002d
            int r1 = r5.length()
            int r1 = r1 + r3
            goto L_0x0031
        L_0x002d:
            int r4 = r4 + 1
            goto L_0x001d
        L_0x0030:
            r1 = 0
        L_0x0031:
            if (r1 <= 0) goto L_0x006f
            r0 = r1
        L_0x0034:
            int r2 = r14.length()
            if (r1 >= r2) goto L_0x0047
            char r3 = r14.charAt(r1)
            r2 = 48
            if (r3 != r2) goto L_0x0047
            int r0 = r0 + 1
            int r1 = r1 + 1
            goto L_0x0034
        L_0x0047:
            int r1 = r14.length()
            int r1 = r1 - r0
            r0 = 16
            if (r1 > r0) goto L_0x006a
            r2 = 55
            if (r1 != r0) goto L_0x0057
            if (r3 <= r2) goto L_0x0057
            goto L_0x006a
        L_0x0057:
            r0 = 8
            if (r1 > r0) goto L_0x0065
            if (r1 != r0) goto L_0x0060
            if (r3 <= r2) goto L_0x0060
            goto L_0x0065
        L_0x0060:
            java.lang.Integer r14 = createInteger(r14)
            return r14
        L_0x0065:
            java.lang.Long r14 = createLong(r14)
            return r14
        L_0x006a:
            java.math.BigInteger r14 = createBigInteger(r14)
            return r14
        L_0x006f:
            int r1 = r14.length()
            r2 = 1
            int r1 = r1 - r2
            char r1 = r14.charAt(r1)
            r4 = 46
            int r5 = r14.indexOf(r4)
            r6 = 101(0x65, float:1.42E-43)
            int r6 = r14.indexOf(r6)
            r7 = 69
            int r7 = r14.indexOf(r7)
            int r6 = r6 + r7
            int r6 = r6 + r2
            java.lang.String r7 = " is not a valid number."
            r8 = -1
            if (r5 <= r8) goto L_0x00c3
            if (r6 <= r8) goto L_0x00b8
            if (r6 < r5) goto L_0x00a3
            int r9 = r14.length()
            if (r6 > r9) goto L_0x00a3
            int r9 = r5 + 1
            java.lang.String r9 = r14.substring(r9, r6)
            goto L_0x00be
        L_0x00a3:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            r1.append(r7)
            java.lang.String r14 = r1.toString()
            r0.<init>(r14)
            throw r0
        L_0x00b8:
            int r9 = r5 + 1
            java.lang.String r9 = r14.substring(r9)
        L_0x00be:
            java.lang.String r5 = getMantissa(r14, r5)
            goto L_0x00ea
        L_0x00c3:
            if (r6 <= r8) goto L_0x00e5
            int r5 = r14.length()
            if (r6 > r5) goto L_0x00d0
            java.lang.String r5 = getMantissa(r14, r6)
            goto L_0x00e9
        L_0x00d0:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            r1.append(r7)
            java.lang.String r14 = r1.toString()
            r0.<init>(r14)
            throw r0
        L_0x00e5:
            java.lang.String r5 = getMantissa(r14)
        L_0x00e9:
            r9 = r0
        L_0x00ea:
            boolean r10 = java.lang.Character.isDigit(r1)
            r11 = 0
            r12 = 0
            if (r10 != 0) goto L_0x01be
            if (r1 == r4) goto L_0x01be
            if (r6 <= r8) goto L_0x0108
            int r4 = r14.length()
            int r4 = r4 - r2
            if (r6 >= r4) goto L_0x0108
            int r6 = r6 + r2
            int r0 = r14.length()
            int r0 = r0 - r2
            java.lang.String r0 = r14.substring(r6, r0)
        L_0x0108:
            int r4 = r14.length()
            int r4 = r4 - r2
            java.lang.String r4 = r14.substring(r3, r4)
            boolean r5 = isAllZeros(r5)
            if (r5 == 0) goto L_0x011f
            boolean r5 = isAllZeros(r0)
            if (r5 == 0) goto L_0x011f
            r5 = 1
            goto L_0x0120
        L_0x011f:
            r5 = 0
        L_0x0120:
            r6 = 68
            if (r1 == r6) goto L_0x018e
            r6 = 70
            if (r1 == r6) goto L_0x0179
            r6 = 76
            if (r1 == r6) goto L_0x0138
            r6 = 100
            if (r1 == r6) goto L_0x018e
            r6 = 102(0x66, float:1.43E-43)
            if (r1 == r6) goto L_0x0179
            r5 = 108(0x6c, float:1.51E-43)
            if (r1 != r5) goto L_0x01a9
        L_0x0138:
            if (r9 != 0) goto L_0x0164
            if (r0 != 0) goto L_0x0164
            boolean r0 = r4.isEmpty()
            if (r0 != 0) goto L_0x0154
            char r0 = r4.charAt(r3)
            r1 = 45
            if (r0 != r1) goto L_0x0154
            java.lang.String r0 = r4.substring(r2)
            boolean r0 = isDigits(r0)
            if (r0 != 0) goto L_0x015a
        L_0x0154:
            boolean r0 = isDigits(r4)
            if (r0 == 0) goto L_0x0164
        L_0x015a:
            java.lang.Long r14 = createLong(r4)     // Catch:{ NumberFormatException -> 0x015f }
            return r14
        L_0x015f:
            java.math.BigInteger r14 = createBigInteger(r4)
            return r14
        L_0x0164:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            r1.append(r7)
            java.lang.String r14 = r1.toString()
            r0.<init>(r14)
            throw r0
        L_0x0179:
            java.lang.Float r0 = createFloat(r14)     // Catch:{ NumberFormatException -> 0x018e }
            boolean r1 = r0.isInfinite()     // Catch:{ NumberFormatException -> 0x018e }
            if (r1 != 0) goto L_0x018e
            float r1 = r0.floatValue()     // Catch:{ NumberFormatException -> 0x018e }
            int r1 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r1 != 0) goto L_0x018d
            if (r5 == 0) goto L_0x018e
        L_0x018d:
            return r0
        L_0x018e:
            java.lang.Double r0 = createDouble(r14)     // Catch:{ NumberFormatException -> 0x01a4 }
            boolean r1 = r0.isInfinite()     // Catch:{ NumberFormatException -> 0x01a4 }
            if (r1 != 0) goto L_0x01a4
            float r1 = r0.floatValue()     // Catch:{ NumberFormatException -> 0x01a4 }
            double r1 = (double) r1
            int r3 = (r1 > r12 ? 1 : (r1 == r12 ? 0 : -1))
            if (r3 != 0) goto L_0x01a3
            if (r5 == 0) goto L_0x01a4
        L_0x01a3:
            return r0
        L_0x01a4:
            java.math.BigDecimal r14 = createBigDecimal(r4)     // Catch:{ NumberFormatException -> 0x01a9 }
            return r14
        L_0x01a9:
            java.lang.NumberFormatException r0 = new java.lang.NumberFormatException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r14)
            r1.append(r7)
            java.lang.String r14 = r1.toString()
            r0.<init>(r14)
            throw r0
        L_0x01be:
            if (r6 <= r8) goto L_0x01d0
            int r1 = r14.length()
            int r1 = r1 - r2
            if (r6 >= r1) goto L_0x01d0
            int r6 = r6 + r2
            int r0 = r14.length()
            java.lang.String r0 = r14.substring(r6, r0)
        L_0x01d0:
            if (r9 != 0) goto L_0x01e3
            if (r0 != 0) goto L_0x01e3
            java.lang.Integer r14 = createInteger(r14)     // Catch:{ NumberFormatException -> 0x01d9 }
            return r14
        L_0x01d9:
            java.lang.Long r14 = createLong(r14)     // Catch:{ NumberFormatException -> 0x01de }
            return r14
        L_0x01de:
            java.math.BigInteger r14 = createBigInteger(r14)
            return r14
        L_0x01e3:
            boolean r1 = isAllZeros(r5)
            if (r1 == 0) goto L_0x01f0
            boolean r0 = isAllZeros(r0)
            if (r0 == 0) goto L_0x01f0
            r3 = 1
        L_0x01f0:
            java.lang.Float r0 = createFloat(r14)     // Catch:{ NumberFormatException -> 0x023b }
            java.lang.Double r1 = createDouble(r14)     // Catch:{ NumberFormatException -> 0x023b }
            boolean r2 = r0.isInfinite()     // Catch:{ NumberFormatException -> 0x023b }
            if (r2 != 0) goto L_0x0217
            float r2 = r0.floatValue()     // Catch:{ NumberFormatException -> 0x023b }
            int r2 = (r2 > r11 ? 1 : (r2 == r11 ? 0 : -1))
            if (r2 != 0) goto L_0x0208
            if (r3 == 0) goto L_0x0217
        L_0x0208:
            java.lang.String r2 = r0.toString()     // Catch:{ NumberFormatException -> 0x023b }
            java.lang.String r4 = r1.toString()     // Catch:{ NumberFormatException -> 0x023b }
            boolean r2 = r2.equals(r4)     // Catch:{ NumberFormatException -> 0x023b }
            if (r2 == 0) goto L_0x0217
            return r0
        L_0x0217:
            boolean r0 = r1.isInfinite()     // Catch:{ NumberFormatException -> 0x023b }
            if (r0 != 0) goto L_0x023b
            double r4 = r1.doubleValue()     // Catch:{ NumberFormatException -> 0x023b }
            int r0 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r0 != 0) goto L_0x0227
            if (r3 == 0) goto L_0x023b
        L_0x0227:
            java.math.BigDecimal r0 = createBigDecimal(r14)     // Catch:{ NumberFormatException -> 0x023b }
            double r2 = r1.doubleValue()     // Catch:{ NumberFormatException -> 0x023b }
            java.math.BigDecimal r2 = java.math.BigDecimal.valueOf(r2)     // Catch:{ NumberFormatException -> 0x023b }
            int r14 = r0.compareTo(r2)     // Catch:{ NumberFormatException -> 0x023b }
            if (r14 != 0) goto L_0x023a
            return r1
        L_0x023a:
            return r0
        L_0x023b:
            java.math.BigDecimal r14 = createBigDecimal(r14)
            return r14
        L_0x0240:
            java.lang.NumberFormatException r14 = new java.lang.NumberFormatException
            java.lang.String r0 = "A blank string is not a valid number"
            r14.<init>(r0)
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.math.NumberUtils.createNumber(java.lang.String):java.lang.Number");
    }

    private static String getMantissa(String str) {
        return getMantissa(str, str.length());
    }

    private static String getMantissa(String str, int i) {
        char charAt = str.charAt(0);
        return charAt == '-' || charAt == '+' ? str.substring(1, i) : str.substring(0, i);
    }

    private static boolean isAllZeros(String str) {
        if (str == null) {
            return true;
        }
        for (int length = str.length() - 1; length >= 0; length--) {
            if (str.charAt(length) != '0') {
                return false;
            }
        }
        return !str.isEmpty();
    }

    public static Float createFloat(String str) {
        if (str == null) {
            return null;
        }
        return Float.valueOf(str);
    }

    public static Double createDouble(String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }

    public static Integer createInteger(String str) {
        if (str == null) {
            return null;
        }
        return Integer.decode(str);
    }

    public static Long createLong(String str) {
        if (str == null) {
            return null;
        }
        return Long.decode(str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.math.BigInteger createBigInteger(java.lang.String r5) {
        /*
            if (r5 != 0) goto L_0x0004
            r5 = 0
            return r5
        L_0x0004:
            r0 = 10
            java.lang.String r1 = "-"
            boolean r1 = r5.startsWith(r1)
            r2 = 0
            r3 = 1
            if (r1 == 0) goto L_0x0012
            r2 = 1
            goto L_0x0013
        L_0x0012:
            r3 = 0
        L_0x0013:
            java.lang.String r1 = "0x"
            boolean r1 = r5.startsWith(r1, r2)
            r4 = 16
            if (r1 != 0) goto L_0x0045
            java.lang.String r1 = "0X"
            boolean r1 = r5.startsWith(r1, r2)
            if (r1 == 0) goto L_0x0026
            goto L_0x0045
        L_0x0026:
            java.lang.String r1 = "#"
            boolean r1 = r5.startsWith(r1, r2)
            if (r1 == 0) goto L_0x0031
            int r2 = r2 + 1
            goto L_0x0047
        L_0x0031:
            java.lang.String r1 = "0"
            boolean r1 = r5.startsWith(r1, r2)
            if (r1 == 0) goto L_0x0049
            int r1 = r5.length()
            int r4 = r2 + 1
            if (r1 <= r4) goto L_0x0049
            r0 = 8
            r2 = r4
            goto L_0x0049
        L_0x0045:
            int r2 = r2 + 2
        L_0x0047:
            r0 = 16
        L_0x0049:
            java.math.BigInteger r1 = new java.math.BigInteger
            java.lang.String r5 = r5.substring(r2)
            r1.<init>(r5, r0)
            if (r3 == 0) goto L_0x0058
            java.math.BigInteger r1 = r1.negate()
        L_0x0058:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.math.NumberUtils.createBigInteger(java.lang.String):java.math.BigInteger");
    }

    public static BigDecimal createBigDecimal(String str) {
        if (str == null) {
            return null;
        }
        if (StringUtils.isBlank(str)) {
            throw new NumberFormatException("A blank string is not a valid number");
        } else if (!str.trim().startsWith("--")) {
            return new BigDecimal(str);
        } else {
            throw new NumberFormatException(str + " is not a valid number.");
        }
    }

    public static long min(long... jArr) {
        validateArray(jArr);
        long j = jArr[0];
        for (int i = 1; i < jArr.length; i++) {
            if (jArr[i] < j) {
                j = jArr[i];
            }
        }
        return j;
    }

    public static int min(int... iArr) {
        validateArray(iArr);
        int i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (iArr[i2] < i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public static short min(short... sArr) {
        validateArray(sArr);
        short s = sArr[0];
        for (int i = 1; i < sArr.length; i++) {
            if (sArr[i] < s) {
                s = sArr[i];
            }
        }
        return s;
    }

    public static byte min(byte... bArr) {
        validateArray(bArr);
        byte b = bArr[0];
        for (int i = 1; i < bArr.length; i++) {
            if (bArr[i] < b) {
                b = bArr[i];
            }
        }
        return b;
    }

    public static double min(double... dArr) {
        validateArray(dArr);
        double d = dArr[0];
        for (int i = 1; i < dArr.length; i++) {
            if (Double.isNaN(dArr[i])) {
                return Double.NaN;
            }
            if (dArr[i] < d) {
                d = dArr[i];
            }
        }
        return d;
    }

    public static float min(float... fArr) {
        validateArray(fArr);
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            if (Float.isNaN(fArr[i])) {
                return Float.NaN;
            }
            if (fArr[i] < f) {
                f = fArr[i];
            }
        }
        return f;
    }

    public static long max(long... jArr) {
        validateArray(jArr);
        long j = jArr[0];
        for (int i = 1; i < jArr.length; i++) {
            if (jArr[i] > j) {
                j = jArr[i];
            }
        }
        return j;
    }

    public static int max(int... iArr) {
        validateArray(iArr);
        int i = iArr[0];
        for (int i2 = 1; i2 < iArr.length; i2++) {
            if (iArr[i2] > i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public static short max(short... sArr) {
        validateArray(sArr);
        short s = sArr[0];
        for (int i = 1; i < sArr.length; i++) {
            if (sArr[i] > s) {
                s = sArr[i];
            }
        }
        return s;
    }

    public static byte max(byte... bArr) {
        validateArray(bArr);
        byte b = bArr[0];
        for (int i = 1; i < bArr.length; i++) {
            if (bArr[i] > b) {
                b = bArr[i];
            }
        }
        return b;
    }

    public static double max(double... dArr) {
        validateArray(dArr);
        double d = dArr[0];
        for (int i = 1; i < dArr.length; i++) {
            if (Double.isNaN(dArr[i])) {
                return Double.NaN;
            }
            if (dArr[i] > d) {
                d = dArr[i];
            }
        }
        return d;
    }

    public static float max(float... fArr) {
        validateArray(fArr);
        float f = fArr[0];
        for (int i = 1; i < fArr.length; i++) {
            if (Float.isNaN(fArr[i])) {
                return Float.NaN;
            }
            if (fArr[i] > f) {
                f = fArr[i];
            }
        }
        return f;
    }

    private static void validateArray(Object obj) {
        boolean z = true;
        Validate.isTrue(obj != null, "The Array must not be null", new Object[0]);
        if (Array.getLength(obj) == 0) {
            z = false;
        }
        Validate.isTrue(z, "Array cannot be empty.", new Object[0]);
    }

    public static double min(double d, double d2, double d3) {
        return Math.min(Math.min(d, d2), d3);
    }

    public static float min(float f, float f2, float f3) {
        return Math.min(Math.min(f, f2), f3);
    }

    public static double max(double d, double d2, double d3) {
        return Math.max(Math.max(d, d2), d3);
    }

    public static float max(float f, float f2, float f3) {
        return Math.max(Math.max(f, f2), f3);
    }

    public static boolean isDigits(String str) {
        return StringUtils.isNumeric(str);
    }

    @Deprecated
    public static boolean isNumber(String str) {
        return isCreatable(str);
    }

    public static boolean isCreatable(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        boolean z = true;
        int i = (charArray[0] == '-' || charArray[0] == '+') ? 1 : 0;
        int i2 = i + 1;
        if (length > i2 && charArray[i] == '0' && !StringUtils.contains((CharSequence) str, 46)) {
            if (charArray[i2] == 'x' || charArray[i2] == 'X') {
                int i3 = i + 2;
                if (i3 == length) {
                    return false;
                }
                while (i3 < charArray.length) {
                    if ((charArray[i3] < '0' || charArray[i3] > '9') && ((charArray[i3] < 'a' || charArray[i3] > 'f') && (charArray[i3] < 'A' || charArray[i3] > 'F'))) {
                        return false;
                    }
                    i3++;
                }
                return true;
            } else if (Character.isDigit(charArray[i2])) {
                while (i2 < charArray.length) {
                    if (charArray[i2] < '0' || charArray[i2] > '7') {
                        return false;
                    }
                    i2++;
                }
                return true;
            }
        }
        int i4 = length - 1;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        while (true) {
            if (i >= i4) {
                if (i >= i4 + 1 || !z2 || z3) {
                }
            }
            if (charArray[i] >= '0' && charArray[i] <= '9') {
                z2 = false;
                z3 = true;
            } else if (charArray[i] == '.') {
                if (z5 || z4) {
                    return false;
                }
                z5 = true;
            } else if (charArray[i] != 'e' && charArray[i] != 'E') {
                if (charArray[i] != '+') {
                    if (charArray[i] != '-') {
                        return false;
                    }
                }
                if (!z2) {
                    return false;
                }
                z2 = false;
                z3 = false;
            } else if (z4 || !z3) {
                return false;
            } else {
                z2 = true;
                z4 = true;
            }
            i++;
            z = true;
        }
        if (i < charArray.length) {
            if (charArray[i] >= '0' && charArray[i] <= '9') {
                return z;
            }
            if (charArray[i] == 'e' || charArray[i] == 'E') {
                return false;
            }
            if (charArray[i] == '.') {
                if (z5 || z4) {
                    return false;
                }
                return z3;
            } else if (!z2 && (charArray[i] == 'd' || charArray[i] == 'D' || charArray[i] == 'f' || charArray[i] == 'F')) {
                return z3;
            } else {
                if ((charArray[i] == 'l' || charArray[i] == 'L') && z3 && !z4 && !z5) {
                    return true;
                }
                return false;
            }
        } else if (z2 || !z3) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isParsable(String str) {
        if (StringUtils.isEmpty(str) || str.charAt(str.length() - 1) == '.') {
            return false;
        }
        if (str.charAt(0) != '-') {
            return withDecimalsParsing(str, 0);
        }
        if (str.length() == 1) {
            return false;
        }
        return withDecimalsParsing(str, 1);
    }

    private static boolean withDecimalsParsing(String str, int i) {
        int i2 = 0;
        while (i < str.length()) {
            boolean z = str.charAt(i) == '.';
            if (z) {
                i2++;
            }
            if (i2 > 1) {
                return false;
            }
            if (!z && !Character.isDigit(str.charAt(i))) {
                return false;
            }
            i++;
        }
        return true;
    }
}
