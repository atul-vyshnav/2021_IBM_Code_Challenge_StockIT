package com.google.android.gms.internal.vision;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.facebook.imagepipeline.common.RotationOptions;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
public final class zzq {
    public static Bitmap zzb(Bitmap bitmap, zzp zzp) {
        int i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (zzp.rotation != 0) {
            Matrix matrix = new Matrix();
            int i2 = zzp.rotation;
            if (i2 == 0) {
                i = 0;
            } else if (i2 == 1) {
                i = 90;
            } else if (i2 == 2) {
                i = RotationOptions.ROTATE_180;
            } else if (i2 == 3) {
                i = 270;
            } else {
                throw new IllegalArgumentException("Unsupported rotation degree.");
            }
            matrix.postRotate((float) i);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        }
        if (zzp.rotation == 1 || zzp.rotation == 3) {
            zzp.width = height;
            zzp.height = width;
        }
        return bitmap;
    }
}
