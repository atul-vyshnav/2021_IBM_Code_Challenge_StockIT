package com.google.android.exoplayer2.offline;

import android.net.Uri;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.upstream.cache.CacheUtil;
import com.google.android.exoplayer2.util.Util;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public final class ProgressiveDownloadAction extends DownloadAction {
    public static final DownloadAction.Deserializer DESERIALIZER = new DownloadAction.Deserializer(TYPE, 0) {
        public ProgressiveDownloadAction readFromStream(int i, DataInputStream dataInputStream) throws IOException {
            Uri parse = Uri.parse(dataInputStream.readUTF());
            boolean readBoolean = dataInputStream.readBoolean();
            byte[] bArr = new byte[dataInputStream.readInt()];
            dataInputStream.readFully(bArr);
            return new ProgressiveDownloadAction(parse, readBoolean, bArr, dataInputStream.readBoolean() ? dataInputStream.readUTF() : null);
        }
    };
    private static final String TYPE = "progressive";
    private static final int VERSION = 0;
    private final String customCacheKey;

    public static ProgressiveDownloadAction createDownloadAction(Uri uri, byte[] bArr, String str) {
        return new ProgressiveDownloadAction(uri, false, bArr, str);
    }

    public static ProgressiveDownloadAction createRemoveAction(Uri uri, byte[] bArr, String str) {
        return new ProgressiveDownloadAction(uri, true, bArr, str);
    }

    @Deprecated
    public ProgressiveDownloadAction(Uri uri, boolean z, byte[] bArr, String str) {
        super(TYPE, 0, uri, z, bArr);
        this.customCacheKey = str;
    }

    public ProgressiveDownloader createDownloader(DownloaderConstructorHelper downloaderConstructorHelper) {
        return new ProgressiveDownloader(this.uri, this.customCacheKey, downloaderConstructorHelper);
    }

    /* access modifiers changed from: protected */
    public void writeToStream(DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(this.uri.toString());
        dataOutputStream.writeBoolean(this.isRemoveAction);
        dataOutputStream.writeInt(this.data.length);
        dataOutputStream.write(this.data);
        boolean z = this.customCacheKey != null;
        dataOutputStream.writeBoolean(z);
        if (z) {
            dataOutputStream.writeUTF(this.customCacheKey);
        }
    }

    public boolean isSameMedia(DownloadAction downloadAction) {
        return (downloadAction instanceof ProgressiveDownloadAction) && getCacheKey().equals(((ProgressiveDownloadAction) downloadAction).getCacheKey());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return Util.areEqual(this.customCacheKey, ((ProgressiveDownloadAction) obj).customCacheKey);
    }

    public int hashCode() {
        int hashCode = super.hashCode() * 31;
        String str = this.customCacheKey;
        return hashCode + (str != null ? str.hashCode() : 0);
    }

    private String getCacheKey() {
        String str = this.customCacheKey;
        return str != null ? str : CacheUtil.generateKey(this.uri);
    }
}
