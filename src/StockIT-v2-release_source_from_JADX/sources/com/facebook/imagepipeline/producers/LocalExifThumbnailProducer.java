package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.util.Pair;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteBufferInputStream;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.DefaultImageFormats;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.BitmapUtil;
import com.facebook.imageutils.JfifUtil;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalExifThumbnailProducer implements ThumbnailProducer<EncodedImage> {
    private static final int COMMON_EXIF_THUMBNAIL_MAX_DIMENSION = 512;
    static final String CREATED_THUMBNAIL = "createdThumbnail";
    public static final String PRODUCER_NAME = "LocalExifThumbnailProducer";
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;
    /* access modifiers changed from: private */
    public final PooledByteBufferFactory mPooledByteBufferFactory;

    public LocalExifThumbnailProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mContentResolver = contentResolver;
    }

    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        return ThumbnailSizeChecker.isImageBigEnough(512, 512, resizeOptions);
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ProducerListener2 producerListener = producerContext.getProducerListener();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final C09991 r0 = new StatefulProducerRunnable<EncodedImage>(consumer, producerListener, producerContext, PRODUCER_NAME) {
            /* access modifiers changed from: protected */
            @Nullable
            public EncodedImage getResult() throws Exception {
                ExifInterface exifInterface = LocalExifThumbnailProducer.this.getExifInterface(imageRequest.getSourceUri());
                if (exifInterface == null || !exifInterface.hasThumbnail()) {
                    return null;
                }
                return LocalExifThumbnailProducer.this.buildEncodedImage(LocalExifThumbnailProducer.this.mPooledByteBufferFactory.newByteBuffer(exifInterface.getThumbnail()), exifInterface);
            }

            /* access modifiers changed from: protected */
            public void disposeResult(EncodedImage encodedImage) {
                EncodedImage.closeSafely(encodedImage);
            }

            /* access modifiers changed from: protected */
            public Map<String, String> getExtraMapOnSuccess(EncodedImage encodedImage) {
                return ImmutableMap.m36of(LocalExifThumbnailProducer.CREATED_THUMBNAIL, Boolean.toString(encodedImage != null));
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                r0.cancel();
            }
        });
        this.mExecutor.execute(r0);
    }

    /* access modifiers changed from: package-private */
    @Nullable
    public ExifInterface getExifInterface(Uri uri) {
        String realPathFromUri = UriUtil.getRealPathFromUri(this.mContentResolver, uri);
        try {
            if (canReadAsFile(realPathFromUri)) {
                return new ExifInterface(realPathFromUri);
            }
            AssetFileDescriptor assetFileDescriptor = UriUtil.getAssetFileDescriptor(this.mContentResolver, uri);
            if (assetFileDescriptor != null && Build.VERSION.SDK_INT >= 24) {
                ExifInterface exifInterface = new Api24Utils().getExifInterface(assetFileDescriptor.getFileDescriptor());
                assetFileDescriptor.close();
                return exifInterface;
            }
            return null;
        } catch (IOException unused) {
        } catch (StackOverflowError unused2) {
            FLog.m60e((Class<?>) LocalExifThumbnailProducer.class, "StackOverflowError in ExifInterface constructor");
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: private */
    public EncodedImage buildEncodedImage(PooledByteBuffer pooledByteBuffer, ExifInterface exifInterface) {
        Pair<Integer, Integer> decodeDimensions = BitmapUtil.decodeDimensions((InputStream) new PooledByteBufferInputStream(pooledByteBuffer));
        int rotationAngle = getRotationAngle(exifInterface);
        int i = -1;
        int intValue = decodeDimensions != null ? ((Integer) decodeDimensions.first).intValue() : -1;
        if (decodeDimensions != null) {
            i = ((Integer) decodeDimensions.second).intValue();
        }
        CloseableReference of = CloseableReference.m128of(pooledByteBuffer);
        try {
            EncodedImage encodedImage = new EncodedImage((CloseableReference<PooledByteBuffer>) of);
            CloseableReference.closeSafely((CloseableReference<?>) of);
            encodedImage.setImageFormat(DefaultImageFormats.JPEG);
            encodedImage.setRotationAngle(rotationAngle);
            encodedImage.setWidth(intValue);
            encodedImage.setHeight(i);
            return encodedImage;
        } catch (Throwable th) {
            CloseableReference.closeSafely((CloseableReference<?>) of);
            throw th;
        }
    }

    private int getRotationAngle(ExifInterface exifInterface) {
        return JfifUtil.getAutoRotateAngleFromOrientation(Integer.parseInt(exifInterface.getAttribute(androidx.exifinterface.media.ExifInterface.TAG_ORIENTATION)));
    }

    /* access modifiers changed from: package-private */
    public boolean canReadAsFile(String str) throws IOException {
        if (str == null) {
            return false;
        }
        File file = new File(str);
        if (!file.exists() || !file.canRead()) {
            return false;
        }
        return true;
    }

    private class Api24Utils {
        private Api24Utils() {
        }

        /* access modifiers changed from: package-private */
        public ExifInterface getExifInterface(FileDescriptor fileDescriptor) throws IOException {
            if (Build.VERSION.SDK_INT >= 24) {
                return new ExifInterface(fileDescriptor);
            }
            return null;
        }
    }
}
