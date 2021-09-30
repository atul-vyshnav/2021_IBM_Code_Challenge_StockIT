package org.apache.commons.lang3.time;

import androidx.exifinterface.media.ExifInterface;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class DurationFormatUtils {

    /* renamed from: H */
    static final Object f1327H = "H";
    public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.SSS'S'";

    /* renamed from: M */
    static final Object f1328M = "M";

    /* renamed from: S */
    static final Object f1329S = ExifInterface.LATITUDE_SOUTH;

    /* renamed from: d */
    static final Object f1330d = "d";

    /* renamed from: m */
    static final Object f1331m = "m";

    /* renamed from: s */
    static final Object f1332s = "s";

    /* renamed from: y */
    static final Object f1333y = "y";

    public static String formatDurationHMS(long j) {
        return formatDuration(j, "HH:mm:ss.SSS");
    }

    public static String formatDurationISO(long j) {
        return formatDuration(j, ISO_EXTENDED_FORMAT_PATTERN, false);
    }

    public static String formatDuration(long j, String str) {
        return formatDuration(j, str, true);
    }

    public static String formatDuration(long j, String str, boolean z) {
        long j2;
        long j3;
        long j4;
        long j5;
        long j6;
        long j7;
        Validate.inclusiveBetween(0, Long.MAX_VALUE, j, "durationMillis must not be negative");
        Token[] lexx = lexx(str);
        if (Token.containsTokenWithValue(lexx, f1330d)) {
            long j8 = j / DateUtils.MILLIS_PER_DAY;
            j3 = j - (DateUtils.MILLIS_PER_DAY * j8);
            j2 = j8;
        } else {
            j3 = j;
            j2 = 0;
        }
        if (Token.containsTokenWithValue(lexx, f1327H)) {
            long j9 = j3 / DateUtils.MILLIS_PER_HOUR;
            j3 -= DateUtils.MILLIS_PER_HOUR * j9;
            j4 = j9;
        } else {
            j4 = 0;
        }
        if (Token.containsTokenWithValue(lexx, f1331m)) {
            long j10 = j3 / 60000;
            j3 -= 60000 * j10;
            j5 = j10;
        } else {
            j5 = 0;
        }
        if (Token.containsTokenWithValue(lexx, f1332s)) {
            long j11 = j3 / 1000;
            j6 = j3 - (1000 * j11);
            j7 = j11;
        } else {
            j7 = 0;
            j6 = j3;
        }
        return format(lexx, 0, 0, j2, j4, j5, j7, j6, z);
    }

    public static String formatDurationWords(long j, boolean z, boolean z2) {
        String formatDuration = formatDuration(j, "d' days 'H' hours 'm' minutes 's' seconds'");
        if (z) {
            formatDuration = StringUtils.SPACE + formatDuration;
            String replaceOnce = StringUtils.replaceOnce(formatDuration, " 0 days", "");
            if (replaceOnce.length() != formatDuration.length()) {
                String replaceOnce2 = StringUtils.replaceOnce(replaceOnce, " 0 hours", "");
                if (replaceOnce2.length() != replaceOnce.length()) {
                    formatDuration = StringUtils.replaceOnce(replaceOnce2, " 0 minutes", "");
                    if (formatDuration.length() != formatDuration.length()) {
                        formatDuration = StringUtils.replaceOnce(formatDuration, " 0 seconds", "");
                    }
                } else {
                    formatDuration = replaceOnce;
                }
            }
            if (!formatDuration.isEmpty()) {
                formatDuration = formatDuration.substring(1);
            }
        }
        if (z2) {
            String replaceOnce3 = StringUtils.replaceOnce(formatDuration, " 0 seconds", "");
            if (replaceOnce3.length() != formatDuration.length()) {
                formatDuration = StringUtils.replaceOnce(replaceOnce3, " 0 minutes", "");
                if (formatDuration.length() != replaceOnce3.length()) {
                    String replaceOnce4 = StringUtils.replaceOnce(formatDuration, " 0 hours", "");
                    if (replaceOnce4.length() != formatDuration.length()) {
                        formatDuration = StringUtils.replaceOnce(replaceOnce4, " 0 days", "");
                    }
                } else {
                    formatDuration = replaceOnce3;
                }
            }
        }
        return StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.replaceOnce(StringUtils.SPACE + formatDuration, " 1 seconds", " 1 second"), " 1 minutes", " 1 minute"), " 1 hours", " 1 hour"), " 1 days", " 1 day").trim();
    }

    public static String formatPeriodISO(long j, long j2) {
        return formatPeriod(j, j2, ISO_EXTENDED_FORMAT_PATTERN, false, TimeZone.getDefault());
    }

    public static String formatPeriod(long j, long j2, String str) {
        return formatPeriod(j, j2, str, true, TimeZone.getDefault());
    }

    public static String formatPeriod(long j, long j2, String str, boolean z, TimeZone timeZone) {
        int i;
        long j3 = j;
        long j4 = j2;
        int i2 = 0;
        Validate.isTrue(j3 <= j4, "startMillis must not be greater than endMillis", new Object[0]);
        Token[] lexx = lexx(str);
        Calendar instance = Calendar.getInstance(timeZone);
        instance.setTime(new Date(j3));
        Calendar instance2 = Calendar.getInstance(timeZone);
        instance2.setTime(new Date(j4));
        int i3 = instance2.get(14) - instance.get(14);
        int i4 = instance2.get(13) - instance.get(13);
        int i5 = instance2.get(12) - instance.get(12);
        int i6 = instance2.get(11) - instance.get(11);
        int i7 = instance2.get(5) - instance.get(5);
        int i8 = instance2.get(2) - instance.get(2);
        int i9 = instance2.get(1) - instance.get(1);
        while (i3 < 0) {
            i3 += 1000;
            i4--;
        }
        while (i4 < 0) {
            i4 += 60;
            i5--;
        }
        while (i5 < 0) {
            i5 += 60;
            i6--;
        }
        while (i6 < 0) {
            i6 += 24;
            i7--;
        }
        if (Token.containsTokenWithValue(lexx, f1328M)) {
            while (i7 < 0) {
                i7 += instance.getActualMaximum(5);
                i8--;
                instance.add(2, 1);
            }
            while (i < 0) {
                i8 = i + 12;
                i9--;
            }
            if (!Token.containsTokenWithValue(lexx, f1333y) && i9 != 0) {
                while (i9 != 0) {
                    i += i9 * 12;
                    i9 = 0;
                }
            }
        } else {
            if (!Token.containsTokenWithValue(lexx, f1333y)) {
                int i10 = instance2.get(1);
                if (i8 < 0) {
                    i10--;
                }
                while (instance.get(1) != i10) {
                    int actualMaximum = i7 + (instance.getActualMaximum(6) - instance.get(6));
                    if ((instance instanceof GregorianCalendar) && instance.get(2) == 1 && instance.get(5) == 29) {
                        actualMaximum++;
                    }
                    instance.add(1, 1);
                    i7 = actualMaximum + instance.get(6);
                }
                i9 = 0;
            }
            while (instance.get(2) != instance2.get(2)) {
                i7 += instance.getActualMaximum(5);
                instance.add(2, 1);
            }
            i = 0;
            while (i7 < 0) {
                i7 += instance.getActualMaximum(5);
                i--;
                instance.add(2, 1);
            }
        }
        if (!Token.containsTokenWithValue(lexx, f1330d)) {
            i6 += i7 * 24;
            i7 = 0;
        }
        if (!Token.containsTokenWithValue(lexx, f1327H)) {
            i5 += i6 * 60;
            i6 = 0;
        }
        if (!Token.containsTokenWithValue(lexx, f1331m)) {
            i4 += i5 * 60;
            i5 = 0;
        }
        if (!Token.containsTokenWithValue(lexx, f1332s)) {
            i3 += i4 * 1000;
        } else {
            i2 = i4;
        }
        return format(lexx, (long) i9, (long) i, (long) i7, (long) i6, (long) i5, (long) i2, (long) i3, z);
    }

    static String format(Token[] tokenArr, long j, long j2, long j3, long j4, long j5, long j6, long j7, boolean z) {
        int i;
        int i2;
        Token[] tokenArr2 = tokenArr;
        long j8 = j7;
        boolean z2 = z;
        StringBuilder sb = new StringBuilder();
        int length = tokenArr2.length;
        int i3 = 0;
        boolean z3 = false;
        while (i3 < length) {
            Token token = tokenArr2[i3];
            Object value = token.getValue();
            int count = token.getCount();
            if (value instanceof StringBuilder) {
                sb.append(value.toString());
                long j9 = j4;
                long j10 = j5;
                i2 = length;
                i = i3;
            } else {
                if (value.equals(f1333y)) {
                    sb.append(paddedValue(j, z2, count));
                    long j11 = j4;
                    long j12 = j5;
                    i2 = length;
                    i = i3;
                } else {
                    long j13 = j;
                    if (value.equals(f1328M)) {
                        i = i3;
                        sb.append(paddedValue(j2, z2, count));
                    } else {
                        i = i3;
                        long j14 = j2;
                        if (value.equals(f1330d)) {
                            sb.append(paddedValue(j3, z2, count));
                        } else {
                            long j15 = j3;
                            if (value.equals(f1327H)) {
                                sb.append(paddedValue(j4, z2, count));
                                long j16 = j5;
                                i2 = length;
                            } else {
                                long j17 = j4;
                                if (value.equals(f1331m)) {
                                    sb.append(paddedValue(j5, z2, count));
                                    i2 = length;
                                } else {
                                    long j18 = j5;
                                    if (value.equals(f1332s)) {
                                        i2 = length;
                                        sb.append(paddedValue(j6, z2, count));
                                        z3 = true;
                                    } else {
                                        i2 = length;
                                        long j19 = j6;
                                        if (value.equals(f1329S)) {
                                            if (z3) {
                                                int i4 = 3;
                                                if (z2) {
                                                    i4 = Math.max(3, count);
                                                }
                                                sb.append(paddedValue(j8, true, i4));
                                            } else {
                                                sb.append(paddedValue(j8, z2, count));
                                            }
                                            z3 = false;
                                        }
                                    }
                                    i3 = i + 1;
                                    length = i2;
                                    tokenArr2 = tokenArr;
                                }
                            }
                        }
                    }
                    long j20 = j4;
                    long j162 = j5;
                    i2 = length;
                }
                z3 = false;
            }
            long j21 = j6;
            i3 = i + 1;
            length = i2;
            tokenArr2 = tokenArr;
        }
        return sb.toString();
    }

    private static String paddedValue(long j, boolean z, int i) {
        String l = Long.toString(j);
        return z ? StringUtils.leftPad(l, i, '0') : l;
    }

    /* JADX WARNING: Removed duplicated region for block: B:37:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x009b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static org.apache.commons.lang3.time.DurationFormatUtils.Token[] lexx(java.lang.String r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r9.length()
            r0.<init>(r1)
            r1 = 0
            r2 = 0
            r5 = r2
            r6 = r5
            r3 = 0
            r4 = 0
        L_0x000f:
            int r7 = r9.length()
            if (r3 >= r7) goto L_0x009f
            char r7 = r9.charAt(r3)
            r8 = 39
            if (r4 == 0) goto L_0x0024
            if (r7 == r8) goto L_0x0024
            r5.append(r7)
            goto L_0x009b
        L_0x0024:
            if (r7 == r8) goto L_0x006a
            r8 = 72
            if (r7 == r8) goto L_0x0067
            r8 = 77
            if (r7 == r8) goto L_0x0064
            r8 = 83
            if (r7 == r8) goto L_0x0061
            r8 = 100
            if (r7 == r8) goto L_0x005e
            r8 = 109(0x6d, float:1.53E-43)
            if (r7 == r8) goto L_0x005b
            r8 = 115(0x73, float:1.61E-43)
            if (r7 == r8) goto L_0x0058
            r8 = 121(0x79, float:1.7E-43)
            if (r7 == r8) goto L_0x0055
            if (r5 != 0) goto L_0x0051
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            org.apache.commons.lang3.time.DurationFormatUtils$Token r8 = new org.apache.commons.lang3.time.DurationFormatUtils$Token
            r8.<init>(r5)
            r0.add(r8)
        L_0x0051:
            r5.append(r7)
            goto L_0x007e
        L_0x0055:
            java.lang.Object r7 = f1333y
            goto L_0x007f
        L_0x0058:
            java.lang.Object r7 = f1332s
            goto L_0x007f
        L_0x005b:
            java.lang.Object r7 = f1331m
            goto L_0x007f
        L_0x005e:
            java.lang.Object r7 = f1330d
            goto L_0x007f
        L_0x0061:
            java.lang.Object r7 = f1329S
            goto L_0x007f
        L_0x0064:
            java.lang.Object r7 = f1328M
            goto L_0x007f
        L_0x0067:
            java.lang.Object r7 = f1327H
            goto L_0x007f
        L_0x006a:
            if (r4 == 0) goto L_0x0070
            r5 = r2
            r7 = r5
            r4 = 0
            goto L_0x007f
        L_0x0070:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            org.apache.commons.lang3.time.DurationFormatUtils$Token r4 = new org.apache.commons.lang3.time.DurationFormatUtils$Token
            r4.<init>(r5)
            r0.add(r4)
            r4 = 1
        L_0x007e:
            r7 = r2
        L_0x007f:
            if (r7 == 0) goto L_0x009b
            if (r6 == 0) goto L_0x0091
            java.lang.Object r5 = r6.getValue()
            boolean r5 = r5.equals(r7)
            if (r5 == 0) goto L_0x0091
            r6.increment()
            goto L_0x009a
        L_0x0091:
            org.apache.commons.lang3.time.DurationFormatUtils$Token r5 = new org.apache.commons.lang3.time.DurationFormatUtils$Token
            r5.<init>(r7)
            r0.add(r5)
            r6 = r5
        L_0x009a:
            r5 = r2
        L_0x009b:
            int r3 = r3 + 1
            goto L_0x000f
        L_0x009f:
            if (r4 != 0) goto L_0x00ae
            int r9 = r0.size()
            org.apache.commons.lang3.time.DurationFormatUtils$Token[] r9 = new org.apache.commons.lang3.time.DurationFormatUtils.Token[r9]
            java.lang.Object[] r9 = r0.toArray(r9)
            org.apache.commons.lang3.time.DurationFormatUtils$Token[] r9 = (org.apache.commons.lang3.time.DurationFormatUtils.Token[]) r9
            return r9
        L_0x00ae:
            java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Unmatched quote in format: "
            r1.append(r2)
            r1.append(r9)
            java.lang.String r9 = r1.toString()
            r0.<init>(r9)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.time.DurationFormatUtils.lexx(java.lang.String):org.apache.commons.lang3.time.DurationFormatUtils$Token[]");
    }

    static class Token {
        private int count;
        private final Object value;

        static boolean containsTokenWithValue(Token[] tokenArr, Object obj) {
            for (Token value2 : tokenArr) {
                if (value2.getValue() == obj) {
                    return true;
                }
            }
            return false;
        }

        Token(Object obj) {
            this.value = obj;
            this.count = 1;
        }

        Token(Object obj, int i) {
            this.value = obj;
            this.count = i;
        }

        /* access modifiers changed from: package-private */
        public void increment() {
            this.count++;
        }

        /* access modifiers changed from: package-private */
        public int getCount() {
            return this.count;
        }

        /* access modifiers changed from: package-private */
        public Object getValue() {
            return this.value;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            if (this.value.getClass() != token.value.getClass() || this.count != token.count) {
                return false;
            }
            Object obj2 = this.value;
            if (obj2 instanceof StringBuilder) {
                return obj2.toString().equals(token.value.toString());
            }
            if (obj2 instanceof Number) {
                return obj2.equals(token.value);
            }
            if (obj2 == token.value) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.value.hashCode();
        }

        public String toString() {
            return StringUtils.repeat(this.value.toString(), this.count);
        }
    }
}
