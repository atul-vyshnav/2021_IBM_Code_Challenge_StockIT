package com.google.android.exoplayer2.extractor.mp4;

import com.google.android.exoplayer2.C1379C;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;

public final class TrackEncryptionBox {
    private static final String TAG = "TrackEncryptionBox";
    public final TrackOutput.CryptoData cryptoData;
    public final byte[] defaultInitializationVector;
    public final boolean isEncrypted;
    public final int perSampleIvSize;
    public final String schemeType;

    public TrackEncryptionBox(boolean z, String str, int i, byte[] bArr, int i2, int i3, byte[] bArr2) {
        boolean z2 = true;
        Assertions.checkArgument((bArr2 != null ? false : z2) ^ (i == 0));
        this.isEncrypted = z;
        this.schemeType = str;
        this.perSampleIvSize = i;
        this.defaultInitializationVector = bArr2;
        this.cryptoData = new TrackOutput.CryptoData(schemeToCryptoMode(str), bArr, i2, i3);
    }

    private static int schemeToCryptoMode(String str) {
        if (str == null) {
            return 1;
        }
        char c = 65535;
        switch (str.hashCode()) {
            case 3046605:
                if (str.equals(C1379C.CENC_TYPE_cbc1)) {
                    c = 2;
                    break;
                }
                break;
            case 3046671:
                if (str.equals(C1379C.CENC_TYPE_cbcs)) {
                    c = 3;
                    break;
                }
                break;
            case 3049879:
                if (str.equals(C1379C.CENC_TYPE_cenc)) {
                    c = 0;
                    break;
                }
                break;
            case 3049895:
                if (str.equals(C1379C.CENC_TYPE_cens)) {
                    c = 1;
                    break;
                }
                break;
        }
        if (c == 0 || c == 1) {
            return 1;
        }
        if (c == 2 || c == 3) {
            return 2;
        }
        Log.m173w(TAG, "Unsupported protection scheme type '" + str + "'. Assuming AES-CTR crypto mode.");
        return 1;
    }
}
