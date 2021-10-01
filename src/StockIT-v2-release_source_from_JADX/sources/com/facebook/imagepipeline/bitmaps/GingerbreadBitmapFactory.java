package com.facebook.imagepipeline.bitmaps;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;

public class GingerbreadBitmapFactory extends PlatformBitmapFactory {
    public CloseableReference<Bitmap> createBitmapInternal(int i, int i2, Bitmap.Config config) {
        return CloseableReference.m130of(Bitmap.createBitmap(i, i2, config), SimpleBitmapReleaser.getInstance());
    }
}
