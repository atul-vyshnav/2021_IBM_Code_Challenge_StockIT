package com.google.android.gms.internal.ads;

import com.facebook.imagepipeline.memory.BitmapCounterConfig;
import com.facebook.imageutils.JfifUtil;
import com.google.android.exoplayer2.util.MimeTypes;
import java.nio.ByteBuffer;
import java.util.List;
import p000ai.api.util.VoiceActivityDetector;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public final class zzic {
    private static final int[] zzaik = {1, 2, 3, 6};
    private static final int[] zzail = {48000, 44100, 32000};
    private static final int[] zzaim = {24000, 22050, 16000};
    private static final int[] zzain = {2, 1, 2, 3, 3, 4, 4, 5};
    private static final int[] zzaio = {32, 40, 48, 56, 64, 80, 96, 112, 128, 160, 192, 224, 256, VoiceActivityDetector.FRAME_SIZE_IN_BYTES, BitmapCounterConfig.DEFAULT_MAX_BITMAP_COUNT, 448, 512, 576, 640};
    private static final int[] zzaip = {69, 87, 104, 121, 139, 174, JfifUtil.MARKER_RST0, 243, 278, 348, 417, 487, 557, 696, 835, 975, 1114, 1253, 1393};

    public static zzho zza(zzpk zzpk, String str, String str2, zzjn zzjn) {
        int i = zzail[(zzpk.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = zzpk.readUnsignedByte();
        int i2 = zzain[(readUnsignedByte & 56) >> 3];
        if ((readUnsignedByte & 4) != 0) {
            i2++;
        }
        return zzho.zza(str, MimeTypes.AUDIO_AC3, (String) null, -1, -1, i2, i, (List<byte[]>) null, zzjn, 0, str2);
    }

    public static int zzfi() {
        return 1536;
    }

    public static zzho zzb(zzpk zzpk, String str, String str2, zzjn zzjn) {
        zzpk zzpk2 = zzpk;
        zzpk.zzbp(2);
        int i = zzail[(zzpk.readUnsignedByte() & 192) >> 6];
        int readUnsignedByte = zzpk.readUnsignedByte();
        int i2 = zzain[(readUnsignedByte & 14) >> 1];
        if ((readUnsignedByte & 1) != 0) {
            i2++;
        }
        return zzho.zza(str, MimeTypes.AUDIO_E_AC3, (String) null, -1, -1, i2, i, (List<byte[]>) null, zzjn, 0, str2);
    }

    public static int zzm(ByteBuffer byteBuffer) {
        int i = 6;
        if (((byteBuffer.get(byteBuffer.position() + 4) & 192) >> 6) != 3) {
            i = zzaik[(byteBuffer.get(byteBuffer.position() + 4) & 48) >> 4];
        }
        return i * 256;
    }
}
