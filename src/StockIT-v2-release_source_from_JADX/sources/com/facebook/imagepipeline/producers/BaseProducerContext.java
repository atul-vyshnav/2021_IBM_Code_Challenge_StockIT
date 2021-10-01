package com.facebook.imagepipeline.producers;

import android.util.SparseArray;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.image.EncodedImageOrigin;
import com.facebook.imagepipeline.request.ImageRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

public class BaseProducerContext implements ProducerContext {
    private final List<ProducerContextCallbacks> mCallbacks;
    private final Object mCallerContext;
    private EncodedImageOrigin mEncodedImageOrigin;
    private final SparseArray<String> mExtras;
    private final String mId;
    private final ImagePipelineConfig mImagePipelineConfig;
    private final ImageRequest mImageRequest;
    private boolean mIsCancelled;
    private boolean mIsIntermediateResultExpected;
    private boolean mIsPrefetch;
    private final ImageRequest.RequestLevel mLowestPermittedRequestLevel;
    private Priority mPriority;
    private final ProducerListener2 mProducerListener;
    @Nullable
    private final String mUiComponentId;

    public BaseProducerContext(ImageRequest imageRequest, String str, ProducerListener2 producerListener2, Object obj, ImageRequest.RequestLevel requestLevel, boolean z, boolean z2, Priority priority, ImagePipelineConfig imagePipelineConfig) {
        this(imageRequest, str, (String) null, producerListener2, obj, requestLevel, z, z2, priority, imagePipelineConfig);
    }

    public BaseProducerContext(ImageRequest imageRequest, String str, @Nullable String str2, ProducerListener2 producerListener2, Object obj, ImageRequest.RequestLevel requestLevel, boolean z, boolean z2, Priority priority, ImagePipelineConfig imagePipelineConfig) {
        this.mExtras = new SparseArray<>();
        this.mEncodedImageOrigin = EncodedImageOrigin.NOT_SET;
        this.mImageRequest = imageRequest;
        this.mId = str;
        this.mUiComponentId = str2;
        this.mProducerListener = producerListener2;
        this.mCallerContext = obj;
        this.mLowestPermittedRequestLevel = requestLevel;
        this.mIsPrefetch = z;
        this.mPriority = priority;
        this.mIsIntermediateResultExpected = z2;
        this.mIsCancelled = false;
        this.mCallbacks = new ArrayList();
        this.mImagePipelineConfig = imagePipelineConfig;
    }

    public ImageRequest getImageRequest() {
        return this.mImageRequest;
    }

    public String getId() {
        return this.mId;
    }

    @Nullable
    public String getUiComponentId() {
        return this.mUiComponentId;
    }

    public ProducerListener2 getProducerListener() {
        return this.mProducerListener;
    }

    public Object getCallerContext() {
        return this.mCallerContext;
    }

    public ImageRequest.RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    public synchronized boolean isPrefetch() {
        return this.mIsPrefetch;
    }

    public synchronized Priority getPriority() {
        return this.mPriority;
    }

    public synchronized boolean isIntermediateResultExpected() {
        return this.mIsIntermediateResultExpected;
    }

    public synchronized boolean isCancelled() {
        return this.mIsCancelled;
    }

    public void addCallbacks(ProducerContextCallbacks producerContextCallbacks) {
        boolean z;
        synchronized (this) {
            this.mCallbacks.add(producerContextCallbacks);
            z = this.mIsCancelled;
        }
        if (z) {
            producerContextCallbacks.onCancellationRequested();
        }
    }

    public ImagePipelineConfig getImagePipelineConfig() {
        return this.mImagePipelineConfig;
    }

    public EncodedImageOrigin getEncodedImageOrigin() {
        return this.mEncodedImageOrigin;
    }

    public void setEncodedImageOrigin(EncodedImageOrigin encodedImageOrigin) {
        this.mEncodedImageOrigin = encodedImageOrigin;
    }

    public void cancel() {
        callOnCancellationRequested(cancelNoCallbacks());
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setIsPrefetchNoCallbacks(boolean z) {
        if (z == this.mIsPrefetch) {
            return null;
        }
        this.mIsPrefetch = z;
        return new ArrayList(this.mCallbacks);
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setPriorityNoCallbacks(Priority priority) {
        if (priority == this.mPriority) {
            return null;
        }
        this.mPriority = priority;
        return new ArrayList(this.mCallbacks);
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> setIsIntermediateResultExpectedNoCallbacks(boolean z) {
        if (z == this.mIsIntermediateResultExpected) {
            return null;
        }
        this.mIsIntermediateResultExpected = z;
        return new ArrayList(this.mCallbacks);
    }

    @Nullable
    public synchronized List<ProducerContextCallbacks> cancelNoCallbacks() {
        if (this.mIsCancelled) {
            return null;
        }
        this.mIsCancelled = true;
        return new ArrayList(this.mCallbacks);
    }

    public static void callOnCancellationRequested(@Nullable List<ProducerContextCallbacks> list) {
        if (list != null) {
            for (ProducerContextCallbacks onCancellationRequested : list) {
                onCancellationRequested.onCancellationRequested();
            }
        }
    }

    public static void callOnIsPrefetchChanged(@Nullable List<ProducerContextCallbacks> list) {
        if (list != null) {
            for (ProducerContextCallbacks onIsPrefetchChanged : list) {
                onIsPrefetchChanged.onIsPrefetchChanged();
            }
        }
    }

    public static void callOnIsIntermediateResultExpectedChanged(@Nullable List<ProducerContextCallbacks> list) {
        if (list != null) {
            for (ProducerContextCallbacks onIsIntermediateResultExpectedChanged : list) {
                onIsIntermediateResultExpectedChanged.onIsIntermediateResultExpectedChanged();
            }
        }
    }

    public static void callOnPriorityChanged(@Nullable List<ProducerContextCallbacks> list) {
        if (list != null) {
            for (ProducerContextCallbacks onPriorityChanged : list) {
                onPriorityChanged.onPriorityChanged();
            }
        }
    }

    public void setExtra(int i, String str) {
        this.mExtras.put(i, str);
    }

    public String getExtra(int i) {
        return this.mExtras.get(i, "");
    }
}
