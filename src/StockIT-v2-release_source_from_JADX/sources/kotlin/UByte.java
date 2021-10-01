package kotlin;

import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.UIntRange;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0005\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\n\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u000e\b@\u0018\u0000 i2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001iB\u0014\b\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0010H\nø\u0001\u0000¢\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0013H\nø\u0001\u0000¢\u0006\u0004\b\u0014\u0010\u0015J\u001b\u0010\f\u001a\u00020\r2\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b\u0017\u0010\u0018J\u0016\u0010\u0019\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b\u001a\u0010\u0005J\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b\u001c\u0010\u000fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\nø\u0001\u0000¢\u0006\u0004\b\u001d\u0010\u0012J\u001b\u0010\u001b\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\nø\u0001\u0000¢\u0006\u0004\b\u001e\u0010\u001fJ\u001b\u0010\u001b\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b \u0010\u0018J\u001a\u0010!\u001a\u00020\"2\b\u0010\t\u001a\u0004\u0018\u00010#HÖ\u0003¢\u0006\u0004\b$\u0010%J\u0010\u0010&\u001a\u00020\rHÖ\u0001¢\u0006\u0004\b'\u0010(J\u0016\u0010)\u001a\u00020\u0000H\nø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b*\u0010\u0005J\u0016\u0010+\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b,\u0010\u0005J\u001b\u0010-\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b.\u0010\u000fJ\u001b\u0010-\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\nø\u0001\u0000¢\u0006\u0004\b/\u0010\u0012J\u001b\u0010-\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\nø\u0001\u0000¢\u0006\u0004\b0\u0010\u001fJ\u001b\u0010-\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b1\u0010\u0018J\u001b\u00102\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\b3\u0010\u000bJ\u001b\u00104\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b5\u0010\u000fJ\u001b\u00104\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\nø\u0001\u0000¢\u0006\u0004\b6\u0010\u0012J\u001b\u00104\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\nø\u0001\u0000¢\u0006\u0004\b7\u0010\u001fJ\u001b\u00104\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\b8\u0010\u0018J\u001b\u00109\u001a\u00020:2\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b;\u0010<J\u001b\u0010=\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\b>\u0010\u000fJ\u001b\u0010=\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\nø\u0001\u0000¢\u0006\u0004\b?\u0010\u0012J\u001b\u0010=\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\nø\u0001\u0000¢\u0006\u0004\b@\u0010\u001fJ\u001b\u0010=\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\bA\u0010\u0018J\u001b\u0010B\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0000H\nø\u0001\u0000¢\u0006\u0004\bC\u0010\u000fJ\u001b\u0010B\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0010H\nø\u0001\u0000¢\u0006\u0004\bD\u0010\u0012J\u001b\u0010B\u001a\u00020\u00132\u0006\u0010\t\u001a\u00020\u0013H\nø\u0001\u0000¢\u0006\u0004\bE\u0010\u001fJ\u001b\u0010B\u001a\u00020\u00102\u0006\u0010\t\u001a\u00020\u0016H\nø\u0001\u0000¢\u0006\u0004\bF\u0010\u0018J\u0010\u0010G\u001a\u00020\u0003H\b¢\u0006\u0004\bH\u0010\u0005J\u0010\u0010I\u001a\u00020JH\b¢\u0006\u0004\bK\u0010LJ\u0010\u0010M\u001a\u00020NH\b¢\u0006\u0004\bO\u0010PJ\u0010\u0010Q\u001a\u00020\rH\b¢\u0006\u0004\bR\u0010(J\u0010\u0010S\u001a\u00020TH\b¢\u0006\u0004\bU\u0010VJ\u0010\u0010W\u001a\u00020XH\b¢\u0006\u0004\bY\u0010ZJ\u000f\u0010[\u001a\u00020\\H\u0016¢\u0006\u0004\b]\u0010^J\u0016\u0010_\u001a\u00020\u0000H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\b`\u0010\u0005J\u0016\u0010a\u001a\u00020\u0010H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bb\u0010(J\u0016\u0010c\u001a\u00020\u0013H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bd\u0010VJ\u0016\u0010e\u001a\u00020\u0016H\bø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\bf\u0010ZJ\u001b\u0010g\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0000H\fø\u0001\u0000¢\u0006\u0004\bh\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0000X\u0004¢\u0006\b\n\u0000\u0012\u0004\b\u0006\u0010\u0007ø\u0001\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006j"}, mo40253d2 = {"Lkotlin/UByte;", "", "data", "", "constructor-impl", "(B)B", "getData$annotations", "()V", "and", "other", "and-7apg3OU", "(BB)B", "compareTo", "", "compareTo-7apg3OU", "(BB)I", "Lkotlin/UInt;", "compareTo-WZ4Q5Ns", "(BI)I", "Lkotlin/ULong;", "compareTo-VKZWuLQ", "(BJ)I", "Lkotlin/UShort;", "compareTo-xj2QHRw", "(BS)I", "dec", "dec-w2LRezQ", "div", "div-7apg3OU", "div-WZ4Q5Ns", "div-VKZWuLQ", "(BJ)J", "div-xj2QHRw", "equals", "", "", "equals-impl", "(BLjava/lang/Object;)Z", "hashCode", "hashCode-impl", "(B)I", "inc", "inc-w2LRezQ", "inv", "inv-w2LRezQ", "minus", "minus-7apg3OU", "minus-WZ4Q5Ns", "minus-VKZWuLQ", "minus-xj2QHRw", "or", "or-7apg3OU", "plus", "plus-7apg3OU", "plus-WZ4Q5Ns", "plus-VKZWuLQ", "plus-xj2QHRw", "rangeTo", "Lkotlin/ranges/UIntRange;", "rangeTo-7apg3OU", "(BB)Lkotlin/ranges/UIntRange;", "rem", "rem-7apg3OU", "rem-WZ4Q5Ns", "rem-VKZWuLQ", "rem-xj2QHRw", "times", "times-7apg3OU", "times-WZ4Q5Ns", "times-VKZWuLQ", "times-xj2QHRw", "toByte", "toByte-impl", "toDouble", "", "toDouble-impl", "(B)D", "toFloat", "", "toFloat-impl", "(B)F", "toInt", "toInt-impl", "toLong", "", "toLong-impl", "(B)J", "toShort", "", "toShort-impl", "(B)S", "toString", "", "toString-impl", "(B)Ljava/lang/String;", "toUByte", "toUByte-w2LRezQ", "toUInt", "toUInt-pVg5ArA", "toULong", "toULong-s-VKNKU", "toUShort", "toUShort-Mh2AYeg", "xor", "xor-7apg3OU", "Companion", "kotlin-stdlib"}, mo40254k = 1, mo40255mv = {1, 4, 1})
/* compiled from: UByte.kt */
public final class UByte implements Comparable<UByte> {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static final byte MAX_VALUE = -1;
    public static final byte MIN_VALUE = 0;
    public static final int SIZE_BITS = 8;
    public static final int SIZE_BYTES = 1;
    private final byte data;

    /* renamed from: box-impl  reason: not valid java name */
    public static final /* synthetic */ UByte m1219boximpl(byte b) {
        return new UByte(b);
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private int m1220compareTo7apg3OU(byte b) {
        return m1221compareTo7apg3OU(this.data, b);
    }

    /* renamed from: constructor-impl  reason: not valid java name */
    public static byte m1225constructorimpl(byte b) {
        return b;
    }

    /* renamed from: equals-impl  reason: not valid java name */
    public static boolean m1231equalsimpl(byte b, Object obj) {
        return (obj instanceof UByte) && b == ((UByte) obj).m1266unboximpl();
    }

    /* renamed from: equals-impl0  reason: not valid java name */
    public static final boolean m1232equalsimpl0(byte b, byte b2) {
        return b == b2;
    }

    public static /* synthetic */ void getData$annotations() {
    }

    /* renamed from: hashCode-impl  reason: not valid java name */
    public static int m1233hashCodeimpl(byte b) {
        return b;
    }

    /* renamed from: toByte-impl  reason: not valid java name */
    private static final byte m1254toByteimpl(byte b) {
        return b;
    }

    /* renamed from: toDouble-impl  reason: not valid java name */
    private static final double m1255toDoubleimpl(byte b) {
        return (double) (b & MAX_VALUE);
    }

    /* renamed from: toFloat-impl  reason: not valid java name */
    private static final float m1256toFloatimpl(byte b) {
        return (float) (b & MAX_VALUE);
    }

    /* renamed from: toInt-impl  reason: not valid java name */
    private static final int m1257toIntimpl(byte b) {
        return b & MAX_VALUE;
    }

    /* renamed from: toLong-impl  reason: not valid java name */
    private static final long m1258toLongimpl(byte b) {
        return ((long) b) & 255;
    }

    /* renamed from: toShort-impl  reason: not valid java name */
    private static final short m1259toShortimpl(byte b) {
        return (short) (((short) b) & 255);
    }

    /* renamed from: toUByte-w2LRezQ  reason: not valid java name */
    private static final byte m1261toUBytew2LRezQ(byte b) {
        return b;
    }

    public boolean equals(Object obj) {
        return m1231equalsimpl(this.data, obj);
    }

    public int hashCode() {
        return m1233hashCodeimpl(this.data);
    }

    public String toString() {
        return m1260toStringimpl(this.data);
    }

    /* renamed from: unbox-impl  reason: not valid java name */
    public final /* synthetic */ byte m1266unboximpl() {
        return this.data;
    }

    private /* synthetic */ UByte(byte b) {
        this.data = b;
    }

    public /* bridge */ /* synthetic */ int compareTo(Object obj) {
        return m1220compareTo7apg3OU(((UByte) obj).m1266unboximpl());
    }

    @Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u00020\u0004XTø\u0001\u0000ø\u0001\u0001¢\u0006\u0004\n\u0002\u0010\u0005R\u000e\u0010\u0007\u001a\u00020\bXT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bXT¢\u0006\u0002\n\u0000\u0002\b\n\u0002\b\u0019\n\u0002\b!¨\u0006\n"}, mo40253d2 = {"Lkotlin/UByte$Companion;", "", "()V", "MAX_VALUE", "Lkotlin/UByte;", "B", "MIN_VALUE", "SIZE_BITS", "", "SIZE_BYTES", "kotlin-stdlib"}, mo40254k = 1, mo40255mv = {1, 4, 1})
    /* compiled from: UByte.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* renamed from: compareTo-7apg3OU  reason: not valid java name */
    private static int m1221compareTo7apg3OU(byte b, byte b2) {
        return Intrinsics.compare((int) b & MAX_VALUE, (int) b2 & MAX_VALUE);
    }

    /* renamed from: compareTo-xj2QHRw  reason: not valid java name */
    private static final int m1224compareToxj2QHRw(byte b, short s) {
        return Intrinsics.compare((int) b & MAX_VALUE, (int) s & UShort.MAX_VALUE);
    }

    /* renamed from: compareTo-WZ4Q5Ns  reason: not valid java name */
    private static final int m1223compareToWZ4Q5Ns(byte b, int i) {
        return UnsignedKt.uintCompare(UInt.m1293constructorimpl(b & MAX_VALUE), i);
    }

    /* renamed from: compareTo-VKZWuLQ  reason: not valid java name */
    private static final int m1222compareToVKZWuLQ(byte b, long j) {
        return UnsignedKt.ulongCompare(ULong.m1363constructorimpl(((long) b) & 255), j);
    }

    /* renamed from: plus-7apg3OU  reason: not valid java name */
    private static final int m1241plus7apg3OU(byte b, byte b2) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) + UInt.m1293constructorimpl(b2 & MAX_VALUE));
    }

    /* renamed from: plus-xj2QHRw  reason: not valid java name */
    private static final int m1244plusxj2QHRw(byte b, short s) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) + UInt.m1293constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: plus-WZ4Q5Ns  reason: not valid java name */
    private static final int m1243plusWZ4Q5Ns(byte b, int i) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) + i);
    }

    /* renamed from: plus-VKZWuLQ  reason: not valid java name */
    private static final long m1242plusVKZWuLQ(byte b, long j) {
        return ULong.m1363constructorimpl(ULong.m1363constructorimpl(((long) b) & 255) + j);
    }

    /* renamed from: minus-7apg3OU  reason: not valid java name */
    private static final int m1236minus7apg3OU(byte b, byte b2) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) - UInt.m1293constructorimpl(b2 & MAX_VALUE));
    }

    /* renamed from: minus-xj2QHRw  reason: not valid java name */
    private static final int m1239minusxj2QHRw(byte b, short s) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) - UInt.m1293constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: minus-WZ4Q5Ns  reason: not valid java name */
    private static final int m1238minusWZ4Q5Ns(byte b, int i) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) - i);
    }

    /* renamed from: minus-VKZWuLQ  reason: not valid java name */
    private static final long m1237minusVKZWuLQ(byte b, long j) {
        return ULong.m1363constructorimpl(ULong.m1363constructorimpl(((long) b) & 255) - j);
    }

    /* renamed from: times-7apg3OU  reason: not valid java name */
    private static final int m1250times7apg3OU(byte b, byte b2) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) * UInt.m1293constructorimpl(b2 & MAX_VALUE));
    }

    /* renamed from: times-xj2QHRw  reason: not valid java name */
    private static final int m1253timesxj2QHRw(byte b, short s) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) * UInt.m1293constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: times-WZ4Q5Ns  reason: not valid java name */
    private static final int m1252timesWZ4Q5Ns(byte b, int i) {
        return UInt.m1293constructorimpl(UInt.m1293constructorimpl(b & MAX_VALUE) * i);
    }

    /* renamed from: times-VKZWuLQ  reason: not valid java name */
    private static final long m1251timesVKZWuLQ(byte b, long j) {
        return ULong.m1363constructorimpl(ULong.m1363constructorimpl(((long) b) & 255) * j);
    }

    /* renamed from: div-7apg3OU  reason: not valid java name */
    private static final int m1227div7apg3OU(byte b, byte b2) {
        return UnsignedKt.m1522uintDivideJ1ME1BU(UInt.m1293constructorimpl(b & MAX_VALUE), UInt.m1293constructorimpl(b2 & MAX_VALUE));
    }

    /* renamed from: div-xj2QHRw  reason: not valid java name */
    private static final int m1230divxj2QHRw(byte b, short s) {
        return UnsignedKt.m1522uintDivideJ1ME1BU(UInt.m1293constructorimpl(b & MAX_VALUE), UInt.m1293constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: div-WZ4Q5Ns  reason: not valid java name */
    private static final int m1229divWZ4Q5Ns(byte b, int i) {
        return UnsignedKt.m1522uintDivideJ1ME1BU(UInt.m1293constructorimpl(b & MAX_VALUE), i);
    }

    /* renamed from: div-VKZWuLQ  reason: not valid java name */
    private static final long m1228divVKZWuLQ(byte b, long j) {
        return UnsignedKt.m1524ulongDivideeb3DHEI(ULong.m1363constructorimpl(((long) b) & 255), j);
    }

    /* renamed from: rem-7apg3OU  reason: not valid java name */
    private static final int m1246rem7apg3OU(byte b, byte b2) {
        return UnsignedKt.m1523uintRemainderJ1ME1BU(UInt.m1293constructorimpl(b & MAX_VALUE), UInt.m1293constructorimpl(b2 & MAX_VALUE));
    }

    /* renamed from: rem-xj2QHRw  reason: not valid java name */
    private static final int m1249remxj2QHRw(byte b, short s) {
        return UnsignedKt.m1523uintRemainderJ1ME1BU(UInt.m1293constructorimpl(b & MAX_VALUE), UInt.m1293constructorimpl(s & UShort.MAX_VALUE));
    }

    /* renamed from: rem-WZ4Q5Ns  reason: not valid java name */
    private static final int m1248remWZ4Q5Ns(byte b, int i) {
        return UnsignedKt.m1523uintRemainderJ1ME1BU(UInt.m1293constructorimpl(b & MAX_VALUE), i);
    }

    /* renamed from: rem-VKZWuLQ  reason: not valid java name */
    private static final long m1247remVKZWuLQ(byte b, long j) {
        return UnsignedKt.m1525ulongRemaindereb3DHEI(ULong.m1363constructorimpl(((long) b) & 255), j);
    }

    /* renamed from: inc-w2LRezQ  reason: not valid java name */
    private static final byte m1234incw2LRezQ(byte b) {
        return m1225constructorimpl((byte) (b + 1));
    }

    /* renamed from: dec-w2LRezQ  reason: not valid java name */
    private static final byte m1226decw2LRezQ(byte b) {
        return m1225constructorimpl((byte) (b - 1));
    }

    /* renamed from: rangeTo-7apg3OU  reason: not valid java name */
    private static final UIntRange m1245rangeTo7apg3OU(byte b, byte b2) {
        return new UIntRange(UInt.m1293constructorimpl(b & MAX_VALUE), UInt.m1293constructorimpl(b2 & MAX_VALUE), (DefaultConstructorMarker) null);
    }

    /* renamed from: and-7apg3OU  reason: not valid java name */
    private static final byte m1218and7apg3OU(byte b, byte b2) {
        return m1225constructorimpl((byte) (b & b2));
    }

    /* renamed from: or-7apg3OU  reason: not valid java name */
    private static final byte m1240or7apg3OU(byte b, byte b2) {
        return m1225constructorimpl((byte) (b | b2));
    }

    /* renamed from: xor-7apg3OU  reason: not valid java name */
    private static final byte m1265xor7apg3OU(byte b, byte b2) {
        return m1225constructorimpl((byte) (b ^ b2));
    }

    /* renamed from: inv-w2LRezQ  reason: not valid java name */
    private static final byte m1235invw2LRezQ(byte b) {
        return m1225constructorimpl((byte) (~b));
    }

    /* renamed from: toUShort-Mh2AYeg  reason: not valid java name */
    private static final short m1264toUShortMh2AYeg(byte b) {
        return UShort.m1461constructorimpl((short) (((short) b) & 255));
    }

    /* renamed from: toUInt-pVg5ArA  reason: not valid java name */
    private static final int m1262toUIntpVg5ArA(byte b) {
        return UInt.m1293constructorimpl(b & MAX_VALUE);
    }

    /* renamed from: toULong-s-VKNKU  reason: not valid java name */
    private static final long m1263toULongsVKNKU(byte b) {
        return ULong.m1363constructorimpl(((long) b) & 255);
    }

    /* renamed from: toString-impl  reason: not valid java name */
    public static String m1260toStringimpl(byte b) {
        return String.valueOf(b & MAX_VALUE);
    }
}
