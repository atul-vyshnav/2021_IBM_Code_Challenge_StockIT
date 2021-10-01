package com.facebook.imagepipeline.datasource;

import com.facebook.common.internal.Preconditions;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.imagepipeline.listener.RequestListener2;
import com.facebook.imagepipeline.producers.BaseConsumer;
import com.facebook.imagepipeline.producers.Consumer;
import com.facebook.imagepipeline.producers.Producer;
import com.facebook.imagepipeline.producers.SettableProducerContext;
import com.facebook.imagepipeline.request.HasImageRequest;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import javax.annotation.Nullable;

public abstract class AbstractProducerToDataSourceAdapter<T> extends AbstractDataSource<T> implements HasImageRequest {
    private final RequestListener2 mRequestListener;
    private final SettableProducerContext mSettableProducerContext;

    protected AbstractProducerToDataSourceAdapter(Producer<T> producer, SettableProducerContext settableProducerContext, RequestListener2 requestListener2) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("AbstractProducerToDataSourceAdapter()");
        }
        this.mSettableProducerContext = settableProducerContext;
        this.mRequestListener = requestListener2;
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("AbstractProducerToDataSourceAdapter()->onRequestStart");
        }
        this.mRequestListener.onRequestStart(this.mSettableProducerContext);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("AbstractProducerToDataSourceAdapter()->produceResult");
        }
        producer.produceResults(createConsumer(), settableProducerContext);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
    }

    private Consumer<T> createConsumer() {
        return new BaseConsumer<T>() {
            /* access modifiers changed from: protected */
            public void onNewResultImpl(@Nullable T t, int i) {
                AbstractProducerToDataSourceAdapter.this.onNewResultImpl(t, i);
            }

            /* access modifiers changed from: protected */
            public void onFailureImpl(Throwable th) {
                AbstractProducerToDataSourceAdapter.this.onFailureImpl(th);
            }

            /* access modifiers changed from: protected */
            public void onCancellationImpl() {
                AbstractProducerToDataSourceAdapter.this.onCancellationImpl();
            }

            /* access modifiers changed from: protected */
            public void onProgressUpdateImpl(float f) {
                boolean unused = AbstractProducerToDataSourceAdapter.this.setProgress(f);
            }
        };
    }

    /* access modifiers changed from: protected */
    public void onNewResultImpl(@Nullable T t, int i) {
        boolean isLast = BaseConsumer.isLast(i);
        if (super.setResult(t, isLast) && isLast) {
            this.mRequestListener.onRequestSuccess(this.mSettableProducerContext);
        }
    }

    /* access modifiers changed from: private */
    public void onFailureImpl(Throwable th) {
        if (super.setFailure(th)) {
            this.mRequestListener.onRequestFailure(this.mSettableProducerContext, th);
        }
    }

    /* access modifiers changed from: private */
    public synchronized void onCancellationImpl() {
        Preconditions.checkState(isClosed());
    }

    public ImageRequest getImageRequest() {
        return this.mSettableProducerContext.getImageRequest();
    }

    public boolean close() {
        if (!super.close()) {
            return false;
        }
        if (super.isFinished()) {
            return true;
        }
        this.mRequestListener.onRequestCancellation(this.mSettableProducerContext);
        this.mSettableProducerContext.cancel();
        return true;
    }
}
