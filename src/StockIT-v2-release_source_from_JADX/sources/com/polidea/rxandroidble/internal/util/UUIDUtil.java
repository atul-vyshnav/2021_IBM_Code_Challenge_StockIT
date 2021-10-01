package com.polidea.rxandroidble.internal.util;

import android.os.ParcelUuid;
import android.util.SparseArray;
import com.polidea.rxandroidble.internal.RxBleLog;
import com.polidea.rxandroidble.internal.scan.ScanRecordImplCompat;
import com.polidea.rxandroidble.scan.ScanRecord;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import kotlin.UByte;

@Deprecated
public class UUIDUtil {
    public static final ParcelUuid BASE_UUID = ParcelUuid.fromString("00000000-0000-1000-8000-00805F9B34FB");
    private static final int DATA_TYPE_FLAGS = 1;
    private static final int DATA_TYPE_LOCAL_NAME_COMPLETE = 9;
    private static final int DATA_TYPE_LOCAL_NAME_SHORT = 8;
    private static final int DATA_TYPE_MANUFACTURER_SPECIFIC_DATA = 255;
    private static final int DATA_TYPE_SERVICE_DATA = 22;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_COMPLETE = 7;
    private static final int DATA_TYPE_SERVICE_UUIDS_128_BIT_PARTIAL = 6;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_COMPLETE = 3;
    private static final int DATA_TYPE_SERVICE_UUIDS_16_BIT_PARTIAL = 2;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_COMPLETE = 5;
    private static final int DATA_TYPE_SERVICE_UUIDS_32_BIT_PARTIAL = 4;
    private static final int DATA_TYPE_TX_POWER_LEVEL = 10;
    private static final String UUID_BASE_FORMAT = "%08x-0000-1000-8000-00805f9b34fb";
    public static final int UUID_BYTES_128_BIT = 16;
    public static final int UUID_BYTES_16_BIT = 2;
    public static final int UUID_BYTES_32_BIT = 4;

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<java.util.UUID> extractUUIDs(byte[] r9) {
        /*
            r8 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.nio.ByteBuffer r9 = java.nio.ByteBuffer.wrap(r9)
            java.nio.ByteOrder r1 = java.nio.ByteOrder.LITTLE_ENDIAN
            java.nio.ByteBuffer r9 = r9.order(r1)
        L_0x000f:
            int r1 = r9.remaining()
            r2 = 2
            if (r1 <= r2) goto L_0x008d
            byte r1 = r9.get()
            r1 = r1 & 255(0xff, float:3.57E-43)
            if (r1 != 0) goto L_0x001f
            goto L_0x008d
        L_0x001f:
            byte r3 = r9.get()
            r4 = 0
            java.lang.String r5 = "%08x-0000-1000-8000-00805f9b34fb"
            r6 = 1
            switch(r3) {
                case 2: goto L_0x0071;
                case 3: goto L_0x0071;
                case 4: goto L_0x0054;
                case 5: goto L_0x0054;
                case 6: goto L_0x003d;
                case 7: goto L_0x003d;
                default: goto L_0x002a;
            }
        L_0x002a:
            int r1 = r1 + -1
            int r2 = r9.remaining()
            int r1 = java.lang.Math.min(r1, r2)
            int r2 = r9.position()
            int r2 = r2 + r1
            r9.position(r2)
            goto L_0x000f
        L_0x003d:
            r2 = 16
            if (r1 < r2) goto L_0x000f
            long r2 = r9.getLong()
            long r4 = r9.getLong()
            java.util.UUID r6 = new java.util.UUID
            r6.<init>(r4, r2)
            r0.add(r6)
            int r1 = r1 + -16
            goto L_0x003d
        L_0x0054:
            r2 = 4
            if (r1 < r2) goto L_0x000f
            java.lang.Object[] r2 = new java.lang.Object[r6]
            int r3 = r9.getInt()
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r2[r4] = r3
            java.lang.String r2 = java.lang.String.format(r5, r2)
            java.util.UUID r2 = java.util.UUID.fromString(r2)
            r0.add(r2)
            int r1 = r1 + -4
            goto L_0x0054
        L_0x0071:
            if (r1 < r2) goto L_0x000f
            java.lang.Object[] r3 = new java.lang.Object[r6]
            short r7 = r9.getShort()
            java.lang.Short r7 = java.lang.Short.valueOf(r7)
            r3[r4] = r7
            java.lang.String r3 = java.lang.String.format(r5, r3)
            java.util.UUID r3 = java.util.UUID.fromString(r3)
            r0.add(r3)
            int r1 = r1 + -2
            goto L_0x0071
        L_0x008d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.polidea.rxandroidble.internal.util.UUIDUtil.extractUUIDs(byte[]):java.util.List");
    }

    public Set<UUID> toDistinctSet(UUID[] uuidArr) {
        if (uuidArr == null) {
            uuidArr = new UUID[0];
        }
        return new HashSet(Arrays.asList(uuidArr));
    }

    public ScanRecord parseFromBytes(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        SparseArray sparseArray = new SparseArray();
        HashMap hashMap = new HashMap();
        String str = null;
        int i = 0;
        byte b = -1;
        byte b2 = UByte.MIN_VALUE;
        while (true) {
            try {
                if (i < bArr.length) {
                    int i2 = i + 1;
                    byte b3 = bArr[i] & UByte.MAX_VALUE;
                    if (b3 != 0) {
                        int i3 = b3 - 1;
                        int i4 = i2 + 1;
                        byte b4 = bArr[i2] & UByte.MAX_VALUE;
                        if (b4 == 22) {
                            hashMap.put(parseUuidFrom(extractBytes(bArr, i4, 2)), extractBytes(bArr, i4 + 2, i3 - 2));
                        } else if (b4 != 255) {
                            switch (b4) {
                                case 1:
                                    b = bArr[i4] & UByte.MAX_VALUE;
                                    break;
                                case 2:
                                case 3:
                                    parseServiceUuid(bArr, i4, i3, 2, arrayList);
                                    break;
                                case 4:
                                case 5:
                                    parseServiceUuid(bArr, i4, i3, 4, arrayList);
                                    break;
                                case 6:
                                case 7:
                                    parseServiceUuid(bArr, i4, i3, 16, arrayList);
                                    break;
                                case 8:
                                case 9:
                                    str = new String(extractBytes(bArr, i4, i3));
                                    break;
                                case 10:
                                    b2 = bArr[i4];
                                    break;
                            }
                        } else {
                            sparseArray.put(((bArr[i4 + 1] & UByte.MAX_VALUE) << 8) + (255 & bArr[i4]), extractBytes(bArr, i4 + 2, i3 - 2));
                        }
                        i = i3 + i4;
                    }
                }
            } catch (Exception e) {
                RxBleLog.m1113e(e, "unable to parse scan record: " + Arrays.toString(bArr), new Object[0]);
                return new ScanRecordImplCompat((List<ParcelUuid>) null, (SparseArray<byte[]>) null, (Map<ParcelUuid, byte[]>) null, -1, Integer.MIN_VALUE, (String) null, bArr);
            }
        }
        return new ScanRecordImplCompat(arrayList.isEmpty() ? null : arrayList, sparseArray, hashMap, b, b2, str, bArr);
    }

    private static ParcelUuid parseUuidFrom(byte[] bArr) {
        long j;
        if (bArr != null) {
            int length = bArr.length;
            if (length != 2 && length != 4 && length != 16) {
                throw new IllegalArgumentException("uuidBytes length invalid - " + length);
            } else if (length == 16) {
                ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
                return new ParcelUuid(new UUID(order.getLong(8), order.getLong(0)));
            } else {
                if (length == 2) {
                    j = ((long) (bArr[0] & UByte.MAX_VALUE)) + ((long) ((bArr[1] & UByte.MAX_VALUE) << 8));
                } else {
                    j = ((long) ((bArr[3] & UByte.MAX_VALUE) << 24)) + ((long) (bArr[0] & UByte.MAX_VALUE)) + ((long) ((bArr[1] & UByte.MAX_VALUE) << 8)) + ((long) ((bArr[2] & UByte.MAX_VALUE) << 16));
                }
                return new ParcelUuid(new UUID(BASE_UUID.getUuid().getMostSignificantBits() + (j << 32), BASE_UUID.getUuid().getLeastSignificantBits()));
            }
        } else {
            throw new IllegalArgumentException("uuidBytes cannot be null");
        }
    }

    private static int parseServiceUuid(byte[] bArr, int i, int i2, int i3, List<ParcelUuid> list) {
        while (i2 > 0) {
            list.add(parseUuidFrom(extractBytes(bArr, i, i3)));
            i2 -= i3;
            i += i3;
        }
        return i;
    }

    private static byte[] extractBytes(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }
}
