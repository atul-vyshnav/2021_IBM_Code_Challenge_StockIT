package com.facebook.imagepipeline.image;

import com.facebook.common.logging.FLog;
import java.io.Closeable;

public abstract class CloseableImage implements Closeable, ImageInfo, HasImageMetadata {
    private static final String TAG = "CloseableImage";
    private OriginalEncodedImageInfo mOriginalEncodedImageInfo;

    public abstract void close();

    public abstract int getSizeInBytes();

    public abstract boolean isClosed();

    public boolean isStateful() {
        return false;
    }

    public QualityInfo getQualityInfo() {
        return ImmutableQualityInfo.FULL_QUALITY;
    }

    public OriginalEncodedImageInfo getOriginalEncodedImageInfo() {
        return this.mOriginalEncodedImageInfo;
    }

    public void setOriginalEncodedImageInfo(OriginalEncodedImageInfo originalEncodedImageInfo) {
        this.mOriginalEncodedImageInfo = originalEncodedImageInfo;
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        if (!isClosed()) {
            FLog.m106w(TAG, "finalize: %s %x still open.", getClass().getSimpleName(), Integer.valueOf(System.identityHashCode(this)));
            try {
                close();
            } finally {
                super.finalize();
            }
        }
    }
}
