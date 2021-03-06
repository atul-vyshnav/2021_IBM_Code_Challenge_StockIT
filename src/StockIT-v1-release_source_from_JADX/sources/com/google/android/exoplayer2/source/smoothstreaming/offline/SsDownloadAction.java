package com.google.android.exoplayer2.source.smoothstreaming.offline;

import android.net.Uri;
import com.google.android.exoplayer2.offline.DownloadAction;
import com.google.android.exoplayer2.offline.DownloaderConstructorHelper;
import com.google.android.exoplayer2.offline.SegmentDownloadAction;
import com.google.android.exoplayer2.offline.StreamKey;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public final class SsDownloadAction extends SegmentDownloadAction {
    public static final DownloadAction.Deserializer DESERIALIZER = new SegmentDownloadAction.SegmentDownloadActionDeserializer(TYPE, 1) {
        /* access modifiers changed from: protected */
        public StreamKey readKey(int i, DataInputStream dataInputStream) throws IOException {
            if (i > 0) {
                return super.readKey(i, dataInputStream);
            }
            return new StreamKey(dataInputStream.readInt(), dataInputStream.readInt());
        }

        /* access modifiers changed from: protected */
        public DownloadAction createDownloadAction(Uri uri, boolean z, byte[] bArr, List<StreamKey> list) {
            return new SsDownloadAction(uri, z, bArr, list);
        }
    };
    private static final String TYPE = "ss";
    private static final int VERSION = 1;

    public static SsDownloadAction createDownloadAction(Uri uri, byte[] bArr, List<StreamKey> list) {
        return new SsDownloadAction(uri, false, bArr, list);
    }

    public static SsDownloadAction createRemoveAction(Uri uri, byte[] bArr) {
        return new SsDownloadAction(uri, true, bArr, Collections.emptyList());
    }

    @Deprecated
    public SsDownloadAction(Uri uri, boolean z, byte[] bArr, List<StreamKey> list) {
        super(TYPE, 1, uri, z, bArr, list);
    }

    public SsDownloader createDownloader(DownloaderConstructorHelper downloaderConstructorHelper) {
        return new SsDownloader(this.uri, this.keys, downloaderConstructorHelper);
    }
}
