package com.facebook.imagepipeline.multiuri;

import com.facebook.common.internal.Supplier;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSources;
import com.facebook.datasource.FirstAvailableDataSourceSupplier;
import com.facebook.datasource.IncreasingQualityDataSourceSupplier;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import javax.annotation.Nullable;

public class MultiUri {
    private static final NullPointerException NO_REQUEST_EXCEPTION = new NullPointerException("No image request was specified!");
    @Nullable
    private ImageRequest mLowResImageRequest;
    @Nullable
    private ImageRequest[] mMultiImageRequests;

    private MultiUri(Builder builder) {
        this.mLowResImageRequest = builder.mLowResImageRequest;
        this.mMultiImageRequests = builder.mMultiImageRequests;
    }

    @Nullable
    public ImageRequest getLowResImageRequest() {
        return this.mLowResImageRequest;
    }

    @Nullable
    public ImageRequest[] getMultiImageRequests() {
        return this.mMultiImageRequests;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        /* access modifiers changed from: private */
        @Nullable
        public ImageRequest mLowResImageRequest;
        /* access modifiers changed from: private */
        @Nullable
        public ImageRequest[] mMultiImageRequests;

        private Builder() {
        }

        public MultiUri build() {
            return new MultiUri(this);
        }

        public Builder setLowResImageRequest(@Nullable ImageRequest imageRequest) {
            this.mLowResImageRequest = imageRequest;
            return this;
        }

        public Builder setImageRequests(@Nullable ImageRequest... imageRequestArr) {
            this.mMultiImageRequests = imageRequestArr;
            return this;
        }
    }

    public static Supplier<DataSource<CloseableReference<CloseableImage>>> getMultiUriDatasource(ImagePipeline imagePipeline, ImageRequest imageRequest, ImageRequest imageRequest2, Object obj) {
        return getMultiUriDatasourceSupplier(imagePipeline, create().setLowResImageRequest(imageRequest).setImageRequests(imageRequest2).build(), (ImageRequest) null, obj, (RequestListener) null, (String) null);
    }

    public static Supplier<DataSource<CloseableReference<CloseableImage>>> getMultiUriDatasourceSupplier(ImagePipeline imagePipeline, MultiUri multiUri, @Nullable ImageRequest imageRequest, Object obj, @Nullable RequestListener requestListener, @Nullable String str) {
        Supplier<DataSource<CloseableReference<CloseableImage>>> supplier;
        if (imageRequest != null) {
            supplier = getImageRequestDataSourceSupplier(imagePipeline, imageRequest, obj, requestListener, str);
        } else {
            supplier = multiUri.getMultiImageRequests() != null ? getFirstAvailableDataSourceSupplier(imagePipeline, obj, requestListener, multiUri.getMultiImageRequests(), true, str) : null;
        }
        if (!(supplier == null || multiUri.getLowResImageRequest() == null)) {
            LinkedList linkedList = new LinkedList();
            linkedList.add(supplier);
            linkedList.add(getImageRequestDataSourceSupplier(imagePipeline, multiUri.getLowResImageRequest(), obj, requestListener, str));
            supplier = IncreasingQualityDataSourceSupplier.create(linkedList, false);
        }
        return supplier == null ? DataSources.getFailedDataSourceSupplier(NO_REQUEST_EXCEPTION) : supplier;
    }

    public static DataSource<CloseableReference<CloseableImage>> getImageRequestDataSource(ImagePipeline imagePipeline, ImageRequest imageRequest, Object obj, @Nullable RequestListener requestListener, @Nullable String str) {
        return imagePipeline.fetchDecodedImage(imageRequest, obj, ImageRequest.RequestLevel.FULL_FETCH, requestListener, str);
    }

    private static Supplier<DataSource<CloseableReference<CloseableImage>>> getFirstAvailableDataSourceSupplier(ImagePipeline imagePipeline, Object obj, @Nullable RequestListener requestListener, ImageRequest[] imageRequestArr, boolean z, @Nullable String str) {
        ArrayList arrayList = new ArrayList(imageRequestArr.length * 2);
        if (z) {
            for (ImageRequest imageRequestDataSourceSupplier : imageRequestArr) {
                arrayList.add(getImageRequestDataSourceSupplier(imagePipeline, imageRequestDataSourceSupplier, obj, ImageRequest.RequestLevel.BITMAP_MEMORY_CACHE, requestListener, str));
            }
        }
        for (ImageRequest imageRequestDataSourceSupplier2 : imageRequestArr) {
            arrayList.add(getImageRequestDataSourceSupplier(imagePipeline, imageRequestDataSourceSupplier2, obj, requestListener, str));
        }
        return FirstAvailableDataSourceSupplier.create(arrayList);
    }

    private static Supplier<DataSource<CloseableReference<CloseableImage>>> getImageRequestDataSourceSupplier(ImagePipeline imagePipeline, ImageRequest imageRequest, Object obj, ImageRequest.RequestLevel requestLevel, RequestListener requestListener, @Nullable String str) {
        final ImagePipeline imagePipeline2 = imagePipeline;
        final ImageRequest imageRequest2 = imageRequest;
        final Object obj2 = obj;
        final RequestListener requestListener2 = requestListener;
        final String str2 = str;
        return new Supplier<DataSource<CloseableReference<CloseableImage>>>() {
            public DataSource<CloseableReference<CloseableImage>> get() {
                return MultiUri.getImageRequestDataSource(imagePipeline2, imageRequest2, obj2, requestListener2, str2);
            }
        };
    }

    private static Supplier<DataSource<CloseableReference<CloseableImage>>> getImageRequestDataSourceSupplier(ImagePipeline imagePipeline, ImageRequest imageRequest, Object obj, RequestListener requestListener, @Nullable String str) {
        return getImageRequestDataSourceSupplier(imagePipeline, imageRequest, obj, ImageRequest.RequestLevel.FULL_FETCH, requestListener, str);
    }
}
