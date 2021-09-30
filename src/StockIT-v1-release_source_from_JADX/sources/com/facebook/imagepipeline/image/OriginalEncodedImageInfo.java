package com.facebook.imagepipeline.image;

import android.net.Uri;
import javax.annotation.Nullable;

public class OriginalEncodedImageInfo {
    @Nullable
    private final Object mCallerContext;
    private final int mHeight;
    @Nullable
    private final EncodedImageOrigin mOrigin;
    private final int mSize;
    private final Uri mUri;
    private final int mWidth;

    public OriginalEncodedImageInfo(Uri uri, EncodedImageOrigin encodedImageOrigin, @Nullable Object obj, int i, int i2, int i3) {
        this.mUri = uri;
        this.mOrigin = encodedImageOrigin;
        this.mCallerContext = obj;
        this.mWidth = i;
        this.mHeight = i2;
        this.mSize = i3;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getSize() {
        return this.mSize;
    }

    public Uri getUri() {
        return this.mUri;
    }

    @Nullable
    public Object getCallerContext() {
        return this.mCallerContext;
    }

    public EncodedImageOrigin getOrigin() {
        return this.mOrigin;
    }
}
