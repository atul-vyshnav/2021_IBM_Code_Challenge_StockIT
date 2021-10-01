package com.google.android.gms.vision;

import android.graphics.Bitmap;
import android.graphics.Color;
import java.nio.ByteBuffer;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
public class Frame {
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    private Metadata zzao;
    /* access modifiers changed from: private */
    public ByteBuffer zzap;
    /* access modifiers changed from: private */
    public Bitmap zzaq;

    public Metadata getMetadata() {
        return this.zzao;
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
    public static class Builder {
        private Frame zzas = new Frame();

        public Builder setBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Bitmap unused = this.zzas.zzaq = bitmap;
            Metadata metadata = this.zzas.getMetadata();
            int unused2 = metadata.width = width;
            int unused3 = metadata.height = height;
            return this;
        }

        public Builder setImageData(ByteBuffer byteBuffer, int i, int i2, int i3) {
            if (byteBuffer == null) {
                throw new IllegalArgumentException("Null image data supplied.");
            } else if (byteBuffer.capacity() < i * i2) {
                throw new IllegalArgumentException("Invalid image data size.");
            } else if (i3 == 16 || i3 == 17 || i3 == 842094169) {
                ByteBuffer unused = this.zzas.zzap = byteBuffer;
                Metadata metadata = this.zzas.getMetadata();
                int unused2 = metadata.width = i;
                int unused3 = metadata.height = i2;
                int unused4 = metadata.format = i3;
                return this;
            } else {
                StringBuilder sb = new StringBuilder(37);
                sb.append("Unsupported image format: ");
                sb.append(i3);
                throw new IllegalArgumentException(sb.toString());
            }
        }

        public Builder setId(int i) {
            int unused = this.zzas.getMetadata().f235id = i;
            return this;
        }

        public Builder setTimestampMillis(long j) {
            long unused = this.zzas.getMetadata().zzar = j;
            return this;
        }

        public Builder setRotation(int i) {
            int unused = this.zzas.getMetadata().rotation = i;
            return this;
        }

        public Frame build() {
            if (this.zzas.zzap != null || this.zzas.zzaq != null) {
                return this.zzas;
            }
            throw new IllegalStateException("Missing image data.  Call either setBitmap or setImageData to specify the image");
        }
    }

    public ByteBuffer getGrayscaleImageData() {
        Bitmap bitmap = this.zzaq;
        if (bitmap == null) {
            return this.zzap;
        }
        int width = bitmap.getWidth();
        int height = this.zzaq.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        this.zzaq.getPixels(iArr, 0, width, 0, 0, width, height);
        byte[] bArr = new byte[i];
        for (int i2 = 0; i2 < i; i2++) {
            bArr[i2] = (byte) ((int) ((((float) Color.red(iArr[i2])) * 0.299f) + (((float) Color.green(iArr[i2])) * 0.587f) + (((float) Color.blue(iArr[i2])) * 0.114f)));
        }
        return ByteBuffer.wrap(bArr);
    }

    /* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
    public static class Metadata {
        /* access modifiers changed from: private */
        public int format = -1;
        /* access modifiers changed from: private */
        public int height;
        /* access modifiers changed from: private */

        /* renamed from: id */
        public int f235id;
        /* access modifiers changed from: private */
        public int rotation;
        /* access modifiers changed from: private */
        public int width;
        /* access modifiers changed from: private */
        public long zzar;

        public Metadata() {
        }

        public Metadata(Metadata metadata) {
            this.width = metadata.getWidth();
            this.height = metadata.getHeight();
            this.f235id = metadata.getId();
            this.zzar = metadata.getTimestampMillis();
            this.rotation = metadata.getRotation();
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getId() {
            return this.f235id;
        }

        public long getTimestampMillis() {
            return this.zzar;
        }

        public int getRotation() {
            return this.rotation;
        }

        public int getFormat() {
            return this.format;
        }

        public final void zzd() {
            if (this.rotation % 2 != 0) {
                int i = this.width;
                this.width = this.height;
                this.height = i;
            }
            this.rotation = 0;
        }
    }

    public Bitmap getBitmap() {
        return this.zzaq;
    }

    private Frame() {
        this.zzao = new Metadata();
        this.zzap = null;
        this.zzaq = null;
    }
}
