package com.google.android.gms.internal.vision;

import java.lang.reflect.Type;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
public enum zzgn {
    DOUBLE(0, zzgp.SCALAR, zzhe.DOUBLE),
    FLOAT(1, zzgp.SCALAR, zzhe.FLOAT),
    INT64(2, zzgp.SCALAR, zzhe.LONG),
    UINT64(3, zzgp.SCALAR, zzhe.LONG),
    INT32(4, zzgp.SCALAR, zzhe.INT),
    FIXED64(5, zzgp.SCALAR, zzhe.LONG),
    FIXED32(6, zzgp.SCALAR, zzhe.INT),
    BOOL(7, zzgp.SCALAR, zzhe.BOOLEAN),
    STRING(8, zzgp.SCALAR, zzhe.STRING),
    MESSAGE(9, zzgp.SCALAR, zzhe.MESSAGE),
    BYTES(10, zzgp.SCALAR, zzhe.BYTE_STRING),
    UINT32(11, zzgp.SCALAR, zzhe.INT),
    ENUM(12, zzgp.SCALAR, zzhe.ENUM),
    SFIXED32(13, zzgp.SCALAR, zzhe.INT),
    SFIXED64(14, zzgp.SCALAR, zzhe.LONG),
    SINT32(15, zzgp.SCALAR, zzhe.INT),
    SINT64(16, zzgp.SCALAR, zzhe.LONG),
    GROUP(17, zzgp.SCALAR, zzhe.MESSAGE),
    DOUBLE_LIST(18, zzgp.VECTOR, zzhe.DOUBLE),
    FLOAT_LIST(19, zzgp.VECTOR, zzhe.FLOAT),
    INT64_LIST(20, zzgp.VECTOR, zzhe.LONG),
    UINT64_LIST(21, zzgp.VECTOR, zzhe.LONG),
    INT32_LIST(22, zzgp.VECTOR, zzhe.INT),
    FIXED64_LIST(23, zzgp.VECTOR, zzhe.LONG),
    FIXED32_LIST(24, zzgp.VECTOR, zzhe.INT),
    BOOL_LIST(25, zzgp.VECTOR, zzhe.BOOLEAN),
    STRING_LIST(26, zzgp.VECTOR, zzhe.STRING),
    MESSAGE_LIST(27, zzgp.VECTOR, zzhe.MESSAGE),
    BYTES_LIST(28, zzgp.VECTOR, zzhe.BYTE_STRING),
    UINT32_LIST(29, zzgp.VECTOR, zzhe.INT),
    ENUM_LIST(30, zzgp.VECTOR, zzhe.ENUM),
    SFIXED32_LIST(31, zzgp.VECTOR, zzhe.INT),
    SFIXED64_LIST(32, zzgp.VECTOR, zzhe.LONG),
    SINT32_LIST(33, zzgp.VECTOR, zzhe.INT),
    SINT64_LIST(34, zzgp.VECTOR, zzhe.LONG),
    DOUBLE_LIST_PACKED(35, zzgp.PACKED_VECTOR, zzhe.DOUBLE),
    FLOAT_LIST_PACKED(36, zzgp.PACKED_VECTOR, zzhe.FLOAT),
    INT64_LIST_PACKED(37, zzgp.PACKED_VECTOR, zzhe.LONG),
    UINT64_LIST_PACKED(38, zzgp.PACKED_VECTOR, zzhe.LONG),
    INT32_LIST_PACKED(39, zzgp.PACKED_VECTOR, zzhe.INT),
    FIXED64_LIST_PACKED(40, zzgp.PACKED_VECTOR, zzhe.LONG),
    FIXED32_LIST_PACKED(41, zzgp.PACKED_VECTOR, zzhe.INT),
    BOOL_LIST_PACKED(42, zzgp.PACKED_VECTOR, zzhe.BOOLEAN),
    UINT32_LIST_PACKED(43, zzgp.PACKED_VECTOR, zzhe.INT),
    ENUM_LIST_PACKED(44, zzgp.PACKED_VECTOR, zzhe.ENUM),
    SFIXED32_LIST_PACKED(45, zzgp.PACKED_VECTOR, zzhe.INT),
    SFIXED64_LIST_PACKED(46, zzgp.PACKED_VECTOR, zzhe.LONG),
    SINT32_LIST_PACKED(47, zzgp.PACKED_VECTOR, zzhe.INT),
    SINT64_LIST_PACKED(48, zzgp.PACKED_VECTOR, zzhe.LONG),
    GROUP_LIST(49, zzgp.VECTOR, zzhe.MESSAGE),
    MAP(50, zzgp.MAP, zzhe.VOID);
    
    private static final zzgn[] zzvn = null;
    private static final Type[] zzvo = null;

    /* renamed from: id */
    private final int f233id;
    private final zzhe zzvj;
    private final zzgp zzvk;
    private final Class<?> zzvl;
    private final boolean zzvm;

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002d, code lost:
        r5 = com.google.android.gms.internal.vision.zzgm.zztj[r6.ordinal()];
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private zzgn(int r4, com.google.android.gms.internal.vision.zzgp r5, com.google.android.gms.internal.vision.zzhe r6) {
        /*
            r1 = this;
            r1.<init>(r2, r3)
            r1.f233id = r4
            r1.zzvk = r5
            r1.zzvj = r6
            int[] r2 = com.google.android.gms.internal.vision.zzgm.zzti
            int r3 = r5.ordinal()
            r2 = r2[r3]
            r3 = 2
            r4 = 1
            if (r2 == r4) goto L_0x0022
            if (r2 == r3) goto L_0x001b
            r2 = 0
            r1.zzvl = r2
            goto L_0x0028
        L_0x001b:
            java.lang.Class r2 = r6.zzgv()
            r1.zzvl = r2
            goto L_0x0028
        L_0x0022:
            java.lang.Class r2 = r6.zzgv()
            r1.zzvl = r2
        L_0x0028:
            r2 = 0
            com.google.android.gms.internal.vision.zzgp r0 = com.google.android.gms.internal.vision.zzgp.SCALAR
            if (r5 != r0) goto L_0x003d
            int[] r5 = com.google.android.gms.internal.vision.zzgm.zztj
            int r6 = r6.ordinal()
            r5 = r5[r6]
            if (r5 == r4) goto L_0x003d
            if (r5 == r3) goto L_0x003d
            r3 = 3
            if (r5 == r3) goto L_0x003d
            goto L_0x003e
        L_0x003d:
            r4 = 0
        L_0x003e:
            r1.zzvm = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.vision.zzgn.<init>(java.lang.String, int, int, com.google.android.gms.internal.vision.zzgp, com.google.android.gms.internal.vision.zzhe):void");
    }

    /* renamed from: id */
    public final int mo29947id() {
        return this.f233id;
    }

    static {
        int i;
        zzvo = new Type[0];
        zzgn[] values = values();
        zzvn = new zzgn[values.length];
        for (zzgn zzgn : values) {
            zzvn[zzgn.f233id] = zzgn;
        }
    }
}
