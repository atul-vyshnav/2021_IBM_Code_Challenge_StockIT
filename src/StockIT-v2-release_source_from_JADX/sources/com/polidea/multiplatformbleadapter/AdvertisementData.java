package com.polidea.multiplatformbleadapter;

import android.support.p002v4.media.session.PlaybackStateCompat;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.UByte;

public class AdvertisementData {
    private static final long BLUETOOTH_BASE_UUID_LSB = -9223371485494954757L;
    private static final int BLUETOOTH_BASE_UUID_MSB = 4096;
    private String localName;
    private byte[] manufacturerData;
    private Map<UUID, byte[]> serviceData;
    private List<UUID> serviceUUIDs;
    private List<UUID> solicitedServiceUUIDs;
    private Integer txPowerLevel;

    public String getLocalName() {
        return this.localName;
    }

    public byte[] getManufacturerData() {
        return this.manufacturerData;
    }

    public Map<UUID, byte[]> getServiceData() {
        return this.serviceData;
    }

    public List<UUID> getServiceUUIDs() {
        return this.serviceUUIDs;
    }

    public Integer getTxPowerLevel() {
        return this.txPowerLevel;
    }

    public List<UUID> getSolicitedServiceUUIDs() {
        return this.solicitedServiceUUIDs;
    }

    private AdvertisementData() {
    }

    public AdvertisementData(byte[] bArr, Map<UUID, byte[]> map, List<UUID> list, String str, Integer num, List<UUID> list2) {
        this.manufacturerData = bArr;
        this.serviceData = map;
        this.serviceUUIDs = list;
        this.localName = str;
        this.txPowerLevel = num;
        this.solicitedServiceUUIDs = list2;
    }

    public static AdvertisementData parseScanResponseData(byte[] bArr) {
        byte b;
        AdvertisementData advertisementData = new AdvertisementData();
        ByteBuffer order = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        while (order.remaining() >= 2 && (b = order.get() & UByte.MAX_VALUE) != 0) {
            int i = b - 1;
            byte b2 = order.get() & UByte.MAX_VALUE;
            if (order.remaining() < i) {
                break;
            }
            parseAdvertisementData(advertisementData, b2, i, order.slice().order(ByteOrder.LITTLE_ENDIAN));
            order.position(order.position() + i);
        }
        return advertisementData;
    }

    private static void parseAdvertisementData(AdvertisementData advertisementData, int i, int i2, ByteBuffer byteBuffer) {
        if (i != 255) {
            switch (i) {
                case 2:
                case 3:
                    parseServiceUUIDs(advertisementData, i2, byteBuffer, 2);
                    return;
                case 4:
                case 5:
                    parseServiceUUIDs(advertisementData, i2, byteBuffer, 4);
                    return;
                case 6:
                case 7:
                    parseServiceUUIDs(advertisementData, i2, byteBuffer, 16);
                    return;
                case 8:
                case 9:
                    parseLocalName(advertisementData, i, i2, byteBuffer);
                    return;
                case 10:
                    parseTxPowerLevel(advertisementData, i2, byteBuffer);
                    return;
                default:
                    switch (i) {
                        case 20:
                            parseSolicitedServiceUUIDs(advertisementData, i2, byteBuffer, 2);
                            return;
                        case 21:
                            parseSolicitedServiceUUIDs(advertisementData, i2, byteBuffer, 16);
                            return;
                        case 22:
                            parseServiceData(advertisementData, i2, byteBuffer, 2);
                            return;
                        default:
                            switch (i) {
                                case 31:
                                    parseSolicitedServiceUUIDs(advertisementData, i2, byteBuffer, 4);
                                    return;
                                case 32:
                                    parseServiceData(advertisementData, i2, byteBuffer, 4);
                                    return;
                                case 33:
                                    parseServiceData(advertisementData, i2, byteBuffer, 16);
                                    return;
                                default:
                                    return;
                            }
                    }
            }
        } else {
            parseManufacturerData(advertisementData, i2, byteBuffer);
        }
    }

    private static void parseLocalName(AdvertisementData advertisementData, int i, int i2, ByteBuffer byteBuffer) {
        if (advertisementData.localName == null || i == 9) {
            byte[] bArr = new byte[i2];
            byteBuffer.get(bArr, 0, i2);
            advertisementData.localName = new String(bArr, Charset.forName("UTF-8"));
        }
    }

    private static UUID parseUUID(ByteBuffer byteBuffer, int i) {
        long j;
        long j2;
        long j3 = BLUETOOTH_BASE_UUID_LSB;
        if (i == 2) {
            j2 = ((long) byteBuffer.getShort()) & 65535;
        } else if (i == 4) {
            j2 = (long) byteBuffer.getInt();
        } else if (i != 16) {
            byteBuffer.position(byteBuffer.position() + i);
            return null;
        } else {
            j3 = byteBuffer.getLong();
            j = byteBuffer.getLong();
            return new UUID(j, j3);
        }
        j = (j2 << 32) + PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        return new UUID(j, j3);
    }

    private static void parseSolicitedServiceUUIDs(AdvertisementData advertisementData, int i, ByteBuffer byteBuffer, int i2) {
        if (advertisementData.solicitedServiceUUIDs == null) {
            advertisementData.solicitedServiceUUIDs = new ArrayList();
        }
        while (byteBuffer.remaining() >= i2 && byteBuffer.position() < i) {
            advertisementData.solicitedServiceUUIDs.add(parseUUID(byteBuffer, i2));
        }
    }

    private static void parseServiceUUIDs(AdvertisementData advertisementData, int i, ByteBuffer byteBuffer, int i2) {
        if (advertisementData.serviceUUIDs == null) {
            advertisementData.serviceUUIDs = new ArrayList();
        }
        while (byteBuffer.remaining() >= i2 && byteBuffer.position() < i) {
            advertisementData.serviceUUIDs.add(parseUUID(byteBuffer, i2));
        }
    }

    private static void parseServiceData(AdvertisementData advertisementData, int i, ByteBuffer byteBuffer, int i2) {
        if (i >= i2) {
            if (advertisementData.serviceData == null) {
                advertisementData.serviceData = new HashMap();
            }
            UUID parseUUID = parseUUID(byteBuffer, i2);
            int i3 = i - i2;
            byte[] bArr = new byte[i3];
            byteBuffer.get(bArr, 0, i3);
            advertisementData.serviceData.put(parseUUID, bArr);
        }
    }

    private static void parseTxPowerLevel(AdvertisementData advertisementData, int i, ByteBuffer byteBuffer) {
        if (i == 1) {
            advertisementData.txPowerLevel = Integer.valueOf(byteBuffer.get());
        }
    }

    private static void parseManufacturerData(AdvertisementData advertisementData, int i, ByteBuffer byteBuffer) {
        if (i >= 2) {
            byte[] bArr = new byte[i];
            advertisementData.manufacturerData = bArr;
            byteBuffer.get(bArr, 0, i);
        }
    }
}
