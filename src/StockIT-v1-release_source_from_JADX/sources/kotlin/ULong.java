package kotlin;

import kotlin.ranges.ULongRange;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\t\n\u0002\b\t\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0005\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b@\u0018\u0000 p2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001pB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b \u0010\u000bJ\u001b\u0010\u001b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b!\u0010\"J\u001a\u0010#\u001a\u00020$2\b\u0010\t\u001a\u0004\u0018\u00010%HÖ\u0003¢\u0006\u0004\b&\u0010'J\u0010\u0010(\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b)\u0010*J\u0016\u0010+\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b,\u0010\u0005J\u0016\u0010-\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b.\u0010\u0005J\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b0\u0010\u001dJ\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u001fJ\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b2\u0010\u000bJ\u001b\u0010/\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b3\u0010\"J\u001b\u00104\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b5\u0010\u000bJ\u001b\u00106\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b7\u0010\u001dJ\u001b\u00106\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\b8\u0010\u001fJ\u001b\u00106\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b9\u0010\u000bJ\u001b\u00106\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b:\u0010\"J\u001b\u0010;\u001a\u00020<2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b=\u0010>J\u001b\u0010?\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u001dJ\u001b\u0010?\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u001fJ\u001b\u0010?\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bB\u0010\u000bJ\u001b\u0010?\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\bC\u0010\"J\u001e\u0010D\u001a\u00020\u00002\u0006\u0010E\u001a\u00020\rH\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bF\u0010\u001fJ\u001e\u0010G\u001a\u00020\u00002\u0006\u0010E\u001a\u00020\rH\fø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bH\u0010\u001fJ\u001b\u0010I\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u000eH\nø\u0001\u0000¢\u0006\u0004\bJ\u0010\u001dJ\u001b\u0010I\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0011H\nø\u0001\u0000¢\u0006\u0004\bK\u0010\u001fJ\u001b\u0010I\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bL\u0010\u000bJ\u001b\u0010I\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\bM\u0010\"J\u0010\u0010N\u001a\u00020OH\b¢\u0006\u0004\bP\u0010QJ\u0010\u0010R\u001a\u00020SH\b¢\u0006\u0004\bT\u0010UJ\u0010\u0010V\u001a\u00020WH\b¢\u0006\u0004\bX\u0010YJ\u0010\u0010Z\u001a\u00020\rH\b¢\u0006\u0004\b[\u0010*J\u0010\u0010\\\u001a\u00020\u0003H\b¢\u0006\u0004\b]\u0010\u0005J\u0010\u0010^\u001a\u00020_H\b¢\u0006\u0004\b`\u0010aJ\u000f\u0010b\u001a\u00020cH\u0016¢\u0006\u0004\bd\u0010eJ\u0016\u0010f\u001a\u00020\u000eH\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bg\u0010QJ\u0016\u0010h\u001a\u00020\u0011H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bi\u0010*J\u0016\u0010j\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bk\u0010\u0005J\u0016\u0010l\u001a\u00020\u0016H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bm\u0010aJ\u001b\u0010n\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\bo\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006q"}, mo40253d2 = {"Lkotlin/ULong;", "", "data", "", "constructor-impl", "(J)J", "getData$annotations", "()V", "and", "other", "and-VKZWuLQ", "(JJ)J", "compareTo", "", "Lkotlin/UByte;", "compareTo-7apg3OU", "(JB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(JI)I", "compareTo-VKZWuLQ", "(JJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(JS)I", "dec", "dec-s-VKNKU", "div", "div-7apg3OU", "(JB)J", "div-WZ4Q5Ns", "(JI)J", "div-VKZWuLQ", "div-xj2QHRw", "(JS)J", "equals", "", "", "equals-impl", "(JLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "(J)I", "inc", "inc-s-VKNKU", "inv", "inv-s-VKNKU", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-VKZWuLQ", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/ULongRange;", "rangeTo-VKZWuLQ", "(JJ)Lkotlin/ranges/ULongRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "shl", "bitCount", "shl-s-VKNKU", "shr", "shr-s-VKNKU", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "", "toByte-impl", "(J)B", "toDouble", "", "toDouble-impl", "(J)D", "toFloat", "", "toFloat-impl", "(J)F", "toInt", "toInt-impl", "toLong", "toLong-impl", "toShort", "", "toShort-impl", "(J)S", "toString", "", "toString-impl", "(J)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-VKZWuLQ", "Companion", "kotlin-stdlib"}, mo40254k = 1, mo40255mv = {1, 4, 1})
/* compiled from: ULong.kt */
public final class ULong implements Comparable<ULong> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final long MAX_VALUE = -1;
    public static final long MIN_VALUE = 0;
    public static final int SIZE_BITS = 64;
    public static final int SIZE_BYTES = 8;
    private final long data;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ ULong m1357boximpl(long j) {
        return new ULong(j);
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private int m1359compareToVKZWuLQ(long j) {
        return m1360compareToVKZWuLQ(this.data, j);
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static long m1363constructorimpl(long j) {
        return j;
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m1369equalsimpl(long j, Object obj) {
        return (obj instanceof ULong) && j == ((ULong) obj).m1406unboximpl();
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m1370equalsimpl0(long j, long j2) {
        return j == j2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m1371hashCodeimpl(long j) {
        return (int) (j ^ (j >>> 32));
    }

    /* renamed from: toByte-impl  reason: not valid java name */
    private static final byte m1394toByteimpl(long j) {
        return (byte) ((int) j);
    }

    /* renamed from: toInt-impl  reason: not valid java name */
    private static final int m1397toIntimpl(long j) {
        return (int) j;
    }

    /* renamed from: toLong-impl  reason: not valid java name */
    private static final long m1398toLongimpl(long j) {
        return j;
    }

    /* renamed from: toShort-impl  reason: not valid java name */
    private static final short m1399toShortimpl(long j) {
        return (short) ((int) j);
    }

    /* renamed from: toULong-s-VKNKU  reason: not valid java name */
    private static final long m1403toULongsVKNKU(long j) {
        return j;
    }

    public boolean equals(Object obj) {
        return m1369equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m1371hashCodeimpl(this.data);
    }

    public String toString() {
        return m1400toStringimpl(this.data);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ long m1406unboximpl() {
        return this.data;
    }

    private /* synthetic */ ULong(long j) {
        this.data = j;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return m1359compareToVKZWuLQ(((ULong) obj).m1406unboximpl());
    }

    @Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, mo40253d2 = {"Lkotlin/ULong$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/ULong;", "J", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, mo40254k = 1, mo40255mv = {1, 4, 1})
    /* compiled from: ULong.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private static final int m1358compareTo7apg3OU(long j, byte b) {
        return UnsignedKt.ulongCompare(j, m1363constructorimpl(((long) b) & 255));
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private static final int m1362compareToxj2QHRw(long j, short s) {
        return UnsignedKt.ulongCompare(j, m1363constructorimpl(((long) s) & 65535));
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private static final int m1361compareToWZ4Q5Ns(long j, int i) {
        return UnsignedKt.ulongCompare(j, m1363constructorimpl(((long) i) & 4294967295L));
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private static int m1360compareToVKZWuLQ(long j, long j2) {
        return UnsignedKt.ulongCompare(j, j2);
    }

    /* renamed from: plus-7apg3OU  reason: not valid java name */
    private static final long m1379plus7apg3OU(long j, byte b) {
        return m1363constructorimpl(j + m1363constructorimpl(((long) b) & 255));
    }

    /* renamed from: plus-xj2QHRw  reason: not valid java name */
    private static final long m1382plusxj2QHRw(long j, short s) {
        return m1363constructorimpl(j + m1363constructorimpl(((long) s) & 65535));
    }

    /* renamed from: plus-WZ4Q5Ns  reason: not valid java name */
    private static final long m1381plusWZ4Q5Ns(long j, int i) {
        return m1363constructorimpl(j + m1363constructorimpl(((long) i) & 4294967295L));
    }

    /* renamed from: plus-VKZWuLQ  reason: not valid java name */
    private static final long m1380plusVKZWuLQ(long j, long j2) {
        return m1363constructorimpl(j + j2);
    }

    /* renamed from: minus-7apg3OU  reason: not valid java name */
    private static final long m1374minus7apg3OU(long j, byte b) {
        return m1363constructorimpl(j - m1363constructorimpl(((long) b) & 255));
    }

    /* renamed from: minus-xj2QHRw  reason: not valid java name */
    private static final long m1377minusxj2QHRw(long j, short s) {
        return m1363constructorimpl(j - m1363constructorimpl(((long) s) & 65535));
    }

    /* renamed from: minus-WZ4Q5Ns  reason: not valid java name */
    private static final long m1376minusWZ4Q5Ns(long j, int i) {
        return m1363constructorimpl(j - m1363constructorimpl(((long) i) & 4294967295L));
    }

    /* renamed from: minus-VKZWuLQ  reason: not valid java name */
    private static final long m1375minusVKZWuLQ(long j, long j2) {
        return m1363constructorimpl(j - j2);
    }

    /* renamed from: times-7apg3OU  reason: not valid java name */
    private static final long m1390times7apg3OU(long j, byte b) {
        return m1363constructorimpl(j * m1363constructorimpl(((long) b) & 255));
    }

    /* renamed from: times-xj2QHRw  reason: not valid java name */
    private static final long m1393timesxj2QHRw(long j, short s) {
        return m1363constructorimpl(j * m1363constructorimpl(((long) s) & 65535));
    }

    /* renamed from: times-WZ4Q5Ns  reason: not valid java name */
    private static final long m1392timesWZ4Q5Ns(long j, int i) {
        return m1363constructorimpl(j * m1363constructorimpl(((long) i) & 4294967295L));
    }

    /* renamed from: times-VKZWuLQ  reason: not valid java name */
    private static final long m1391timesVKZWuLQ(long j, long j2) {
        return m1363constructorimpl(j * j2);
    }

    /* renamed from: div-7apg3OU  reason: not valid java name */
    private static final long m1365div7apg3OU(long j, byte b) {
        return UnsignedKt.m1524ulongDivideeb3DHEI(j, m1363constructorimpl(((long) b) & 255));
    }

    /* renamed from: div-xj2QHRw  reason: not valid java name */
    private static final long m1368divxj2QHRw(long j, short s) {
        return UnsignedKt.m1524ulongDivideeb3DHEI(j, m1363constructorimpl(((long) s) & 65535));
    }

    /* renamed from: div-WZ4Q5Ns  reason: not valid java name */
    private static final long m1367divWZ4Q5Ns(long j, int i) {
        return UnsignedKt.m1524ulongDivideeb3DHEI(j, m1363constructorimpl(((long) i) & 4294967295L));
    }

    /* renamed from: div-VKZWuLQ  reason: not valid java name */
    private static final long m1366divVKZWuLQ(long j, long j2) {
        return UnsignedKt.m1524ulongDivideeb3DHEI(j, j2);
    }

    /* renamed from: rem-7apg3OU  reason: not valid java name */
    private static final long m1384rem7apg3OU(long j, byte b) {
        return UnsignedKt.m1525ulongRemaindereb3DHEI(j, m1363constructorimpl(((long) b) & 255));
    }

    /* renamed from: rem-xj2QHRw  reason: not valid java name */
    private static final long m1387remxj2QHRw(long j, short s) {
        return UnsignedKt.m1525ulongRemaindereb3DHEI(j, m1363constructorimpl(((long) s) & 65535));
    }

    /* renamed from: rem-WZ4Q5Ns  reason: not valid java name */
    private static final long m1386remWZ4Q5Ns(long j, int i) {
        return UnsignedKt.m1525ulongRemaindereb3DHEI(j, m1363constructorimpl(((long) i) & 4294967295L));
    }

    /* renamed from: rem-VKZWuLQ  reason: not valid java name */
    private static final long m1385remVKZWuLQ(long j, long j2) {
        return UnsignedKt.m1525ulongRemaindereb3DHEI(j, j2);
    }

    /* renamed from: inc-s-VKNKU  reason: not valid java name */
    private static final long m1372incsVKNKU(long j) {
        return m1363constructorimpl(j + 1);
    }

    /* renamed from: dec-s-VKNKU  reason: not valid java name */
    private static final long m1364decsVKNKU(long j) {
        return m1363constructorimpl(j - 1);
    }

    /* renamed from: rangeTo-VKZWuLQ  reason: not valid java name */
    private static final ULongRange m1383rangeToVKZWuLQ(long j, long j2) {
        return new ULongRange(j, j2, (DefaultConstructorMarker) null);
    }

    /* renamed from: shl-s-VKNKU  reason: not valid java name */
    private static final long m1388shlsVKNKU(long j, int i) {
        return m1363constructorimpl(j << i);
    }

    /* renamed from: shr-s-VKNKU  reason: not valid java name */
    private static final long m1389shrsVKNKU(long j, int i) {
        return m1363constructorimpl(j >>> i);
    }

    /* renamed from: and-VKZWuLQ  reason: not valid java name */
    private static final long m1356andVKZWuLQ(long j, long j2) {
        return m1363constructorimpl(j & j2);
    }

    /* renamed from: or-VKZWuLQ  reason: not valid java name */
    private static final long m1378orVKZWuLQ(long j, long j2) {
        return m1363constructorimpl(j | j2);
    }

    /* renamed from: xor-VKZWuLQ  reason: not valid java name */
    private static final long m1405xorVKZWuLQ(long j, long j2) {
        return m1363constructorimpl(j ^ j2);
    }

    /* renamed from: inv-s-VKNKU  reason: not valid java name */
    private static final long m1373invsVKNKU(long j) {
        return m1363constructorimpl(~j);
    }

    /* renamed from: toUByte-w2LRezQ  reason: not valid java name */
    private static final byte m1401toUBytew2LRezQ(long j) {
        return UByte.m1225constructorimpl((byte) ((int) j));
    }

    /* renamed from: toUShort-Mh2AYeg  reason: not valid java name */
    private static final short m1404toUShortMh2AYeg(long j) {
        return UShort.m1461constructorimpl((short) ((int) j));
    }

    /* renamed from: toUInt-pVg5ArA  reason: not valid java name */
    private static final int m1402toUIntpVg5ArA(long j) {
        return UInt.m1293constructorimpl((int) j);
    }

    /* renamed from: toFloat-impl  reason: not valid java name */
    private static final float m1396toFloatimpl(long j) {
        return (float) UnsignedKt.ulongToDouble(j);
    }

    /* renamed from: toDouble-impl  reason: not valid java name */
    private static final double m1395toDoubleimpl(long j) {
        return UnsignedKt.ulongToDouble(j);
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m1400toStringimpl(long j) {
        return UnsignedKt.ulongToString(j);
    }
}
