package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

public abstract class MultiplexProducer<K, T extends Closeable> implements Producer<T> {
    /* access modifiers changed from: private */
    public final Producer<T> mInputProducer;
    /* access modifiers changed from: private */
    public final boolean mKeepCancelledFetchAsLowPriority;
    final Map<K, MultiplexProducer<K, T>.Multiplexer> mMultiplexers;
    /* access modifiers changed from: private */
    public final String mProducerName;

    /* access modifiers changed from: protected */
    public abstract T cloneOrNull(T t);

    /* access modifiers changed from: protected */
    public abstract K getKey(ProducerContext producerContext);

    protected MultiplexProducer(Producer<T> producer, String str) {
        this(producer, str, false);
    }

    protected MultiplexProducer(Producer<T> producer, String str, boolean z) {
        this.mInputProducer = producer;
        this.mMultiplexers = new HashMap();
        this.mKeepCancelledFetchAsLowPriority = z;
        this.mProducerName = str;
    }

    public void produceResults(Consumer<T> consumer, ProducerContext producerContext) {
        boolean z;
        MultiplexProducer<K, T>.Multiplexer existingMultiplexer;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("MultiplexProducer#produceResults");
            }
            producerContext.getProducerListener().onProducerStart(producerContext, this.mProducerName);
            Object key = getKey(producerContext);
            do {
                z = false;
                synchronized (this) {
                    existingMultiplexer = getExistingMultiplexer(key);
                    if (existingMultiplexer == null) {
                        existingMultiplexer = createAndPutNewMultiplexer(key);
                        z = true;
                    }
                }
            } while (!existingMultiplexer.addNewConsumer(consumer, producerContext));
            if (z) {
                existingMultiplexer.startInputProducerIfHasAttachedConsumers();
            }
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        } catch (Throwable th) {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public synchronized MultiplexProducer<K, T>.Multiplexer getExistingMultiplexer(K k) {
        return this.mMultiplexers.get(k);
    }

    private synchronized MultiplexProducer<K, T>.Multiplexer createAndPutNewMultiplexer(K k) {
        MultiplexProducer<K, T>.Multiplexer multiplexer;
        multiplexer = new Multiplexer(k);
        this.mMultiplexers.put(k, multiplexer);
        return multiplexer;
    }

    /* access modifiers changed from: protected */
    public synchronized void removeMultiplexer(K k, MultiplexProducer<K, T>.Multiplexer multiplexer) {
        if (this.mMultiplexers.get(k) == multiplexer) {
            this.mMultiplexers.remove(k);
        }
    }

    class Multiplexer {
        /* access modifiers changed from: private */
        public final CopyOnWriteArraySet<Pair<Consumer<T>, ProducerContext>> mConsumerContextPairs = Sets.newCopyOnWriteArraySet();
        @Nullable
        private MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer mForwardingConsumer;
        private final K mKey;
        @Nullable
        private T mLastIntermediateResult;
        private float mLastProgress;
        private int mLastStatus;
        /* access modifiers changed from: private */
        @Nullable
        public BaseProducerContext mMultiplexProducerContext;

        public Multiplexer(K k) {
            this.mKey = k;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsPrefetchChanged(r1);
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnPriorityChanged(r2);
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsIntermediateResultExpectedChanged(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0033, code lost:
            monitor-enter(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            monitor-enter(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0037, code lost:
            if (r4 == r7.mLastIntermediateResult) goto L_0x003b;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0039, code lost:
            r4 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003b, code lost:
            if (r4 == null) goto L_0x0043;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x003d, code lost:
            r4 = r7.this$0.cloneOrNull(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0043, code lost:
            monitor-exit(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
            if (r4 == null) goto L_0x0054;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x0049, code lost:
            if (r5 <= 0.0f) goto L_0x004e;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r8.onProgressUpdate(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x004e, code lost:
            r8.onNewResult(r4, r6);
            closeSafely(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0054, code lost:
            monitor-exit(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0055, code lost:
            addCallbacks(r0, r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x0059, code lost:
            return true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean addNewConsumer(com.facebook.imagepipeline.producers.Consumer<T> r8, com.facebook.imagepipeline.producers.ProducerContext r9) {
            /*
                r7 = this;
                android.util.Pair r0 = android.util.Pair.create(r8, r9)
                monitor-enter(r7)
                com.facebook.imagepipeline.producers.MultiplexProducer r1 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0060 }
                K r2 = r7.mKey     // Catch:{ all -> 0x0060 }
                com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer r1 = r1.getExistingMultiplexer(r2)     // Catch:{ all -> 0x0060 }
                if (r1 == r7) goto L_0x0012
                r8 = 0
                monitor-exit(r7)     // Catch:{ all -> 0x0060 }
                return r8
            L_0x0012:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r1 = r7.mConsumerContextPairs     // Catch:{ all -> 0x0060 }
                r1.add(r0)     // Catch:{ all -> 0x0060 }
                java.util.List r1 = r7.updateIsPrefetch()     // Catch:{ all -> 0x0060 }
                java.util.List r2 = r7.updatePriority()     // Catch:{ all -> 0x0060 }
                java.util.List r3 = r7.updateIsIntermediateResultExpected()     // Catch:{ all -> 0x0060 }
                T r4 = r7.mLastIntermediateResult     // Catch:{ all -> 0x0060 }
                float r5 = r7.mLastProgress     // Catch:{ all -> 0x0060 }
                int r6 = r7.mLastStatus     // Catch:{ all -> 0x0060 }
                monitor-exit(r7)     // Catch:{ all -> 0x0060 }
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsPrefetchChanged(r1)
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnPriorityChanged(r2)
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsIntermediateResultExpectedChanged(r3)
                monitor-enter(r0)
                monitor-enter(r7)     // Catch:{ all -> 0x005d }
                T r1 = r7.mLastIntermediateResult     // Catch:{ all -> 0x005a }
                if (r4 == r1) goto L_0x003b
                r4 = 0
                goto L_0x0043
            L_0x003b:
                if (r4 == 0) goto L_0x0043
                com.facebook.imagepipeline.producers.MultiplexProducer r1 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x005a }
                java.io.Closeable r4 = r1.cloneOrNull(r4)     // Catch:{ all -> 0x005a }
            L_0x0043:
                monitor-exit(r7)     // Catch:{ all -> 0x005a }
                if (r4 == 0) goto L_0x0054
                r1 = 0
                int r1 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
                if (r1 <= 0) goto L_0x004e
                r8.onProgressUpdate(r5)     // Catch:{ all -> 0x005d }
            L_0x004e:
                r8.onNewResult(r4, r6)     // Catch:{ all -> 0x005d }
                r7.closeSafely(r4)     // Catch:{ all -> 0x005d }
            L_0x0054:
                monitor-exit(r0)     // Catch:{ all -> 0x005d }
                r7.addCallbacks(r0, r9)
                r8 = 1
                return r8
            L_0x005a:
                r8 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x005a }
                throw r8     // Catch:{ all -> 0x005d }
            L_0x005d:
                r8 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x005d }
                throw r8
            L_0x0060:
                r8 = move-exception
                monitor-exit(r7)     // Catch:{ all -> 0x0060 }
                throw r8
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.addNewConsumer(com.facebook.imagepipeline.producers.Consumer, com.facebook.imagepipeline.producers.ProducerContext):boolean");
        }

        private void addCallbacks(final Pair<Consumer<T>, ProducerContext> pair, ProducerContext producerContext) {
            producerContext.addCallbacks(new BaseProducerContextCallbacks() {
                public void onCancellationRequested() {
                    boolean remove;
                    List list;
                    List list2;
                    List list3;
                    BaseProducerContext baseProducerContext;
                    synchronized (Multiplexer.this) {
                        remove = Multiplexer.this.mConsumerContextPairs.remove(pair);
                        list = null;
                        if (!remove) {
                            baseProducerContext = null;
                            list3 = null;
                        } else if (Multiplexer.this.mConsumerContextPairs.isEmpty()) {
                            baseProducerContext = Multiplexer.this.mMultiplexProducerContext;
                            list3 = null;
                        } else {
                            List access$300 = Multiplexer.this.updateIsPrefetch();
                            list3 = Multiplexer.this.updatePriority();
                            list2 = Multiplexer.this.updateIsIntermediateResultExpected();
                            List list4 = access$300;
                            baseProducerContext = null;
                            list = list4;
                        }
                        list2 = list3;
                    }
                    BaseProducerContext.callOnIsPrefetchChanged(list);
                    BaseProducerContext.callOnPriorityChanged(list3);
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(list2);
                    if (baseProducerContext != null) {
                        if (!MultiplexProducer.this.mKeepCancelledFetchAsLowPriority || baseProducerContext.isPrefetch()) {
                            baseProducerContext.cancel();
                        } else {
                            BaseProducerContext.callOnPriorityChanged(baseProducerContext.setPriorityNoCallbacks(Priority.LOW));
                        }
                    }
                    if (remove) {
                        ((Consumer) pair.first).onCancellation();
                    }
                }

                public void onIsPrefetchChanged() {
                    BaseProducerContext.callOnIsPrefetchChanged(Multiplexer.this.updateIsPrefetch());
                }

                public void onIsIntermediateResultExpectedChanged() {
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(Multiplexer.this.updateIsIntermediateResultExpected());
                }

                public void onPriorityChanged() {
                    BaseProducerContext.callOnPriorityChanged(Multiplexer.this.updatePriority());
                }
            });
        }

        /* access modifiers changed from: private */
        public void startInputProducerIfHasAttachedConsumers() {
            synchronized (this) {
                boolean z = true;
                Preconditions.checkArgument(this.mMultiplexProducerContext == null);
                if (this.mForwardingConsumer != null) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                if (this.mConsumerContextPairs.isEmpty()) {
                    MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                    return;
                }
                ProducerContext producerContext = (ProducerContext) this.mConsumerContextPairs.iterator().next().second;
                this.mMultiplexProducerContext = new BaseProducerContext(producerContext.getImageRequest(), producerContext.getId(), producerContext.getProducerListener(), producerContext.getCallerContext(), producerContext.getLowestPermittedRequestLevel(), computeIsPrefetch(), computeIsIntermediateResultExpected(), computePriority(), producerContext.getImagePipelineConfig());
                ForwardingConsumer forwardingConsumer = new ForwardingConsumer();
                this.mForwardingConsumer = forwardingConsumer;
                BaseProducerContext baseProducerContext = this.mMultiplexProducerContext;
                MultiplexProducer.this.mInputProducer.produceResults(forwardingConsumer, baseProducerContext);
            }
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updateIsPrefetch() {
            if (this.mMultiplexProducerContext == null) {
                return null;
            }
            return this.mMultiplexProducerContext.setIsPrefetchNoCallbacks(computeIsPrefetch());
        }

        private synchronized boolean computeIsPrefetch() {
            Iterator<Pair<Consumer<T>, ProducerContext>> it = this.mConsumerContextPairs.iterator();
            while (it.hasNext()) {
                if (!((ProducerContext) it.next().second).isPrefetch()) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updateIsIntermediateResultExpected() {
            if (this.mMultiplexProducerContext == null) {
                return null;
            }
            return this.mMultiplexProducerContext.setIsIntermediateResultExpectedNoCallbacks(computeIsIntermediateResultExpected());
        }

        private synchronized boolean computeIsIntermediateResultExpected() {
            Iterator<Pair<Consumer<T>, ProducerContext>> it = this.mConsumerContextPairs.iterator();
            while (it.hasNext()) {
                if (((ProducerContext) it.next().second).isIntermediateResultExpected()) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updatePriority() {
            if (this.mMultiplexProducerContext == null) {
                return null;
            }
            return this.mMultiplexProducerContext.setPriorityNoCallbacks(computePriority());
        }

        private synchronized Priority computePriority() {
            Priority priority;
            priority = Priority.LOW;
            Iterator<Pair<Consumer<T>, ProducerContext>> it = this.mConsumerContextPairs.iterator();
            while (it.hasNext()) {
                priority = Priority.getHigherPriority(priority, ((ProducerContext) it.next().second).getPriority());
            }
            return priority;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0028, code lost:
            r1 = r6.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            ((com.facebook.imagepipeline.producers.ProducerContext) r1.second).getProducerListener().onProducerFinishWithFailure((com.facebook.imagepipeline.producers.ProducerContext) r1.second, com.facebook.imagepipeline.producers.MultiplexProducer.access$900(r5.this$0), r7, (java.util.Map<java.lang.String, java.lang.String>) null);
            ((com.facebook.imagepipeline.producers.Consumer) r1.first).onFailure(r7);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004b, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0026, code lost:
            if (r6.hasNext() == false) goto L_0x0050;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFailure(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r6, java.lang.Throwable r7) {
            /*
                r5 = this;
                monitor-enter(r5)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r0 = r5.mForwardingConsumer     // Catch:{ all -> 0x0051 }
                if (r0 == r6) goto L_0x0007
                monitor-exit(r5)     // Catch:{ all -> 0x0051 }
                return
            L_0x0007:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r6 = r5.mConsumerContextPairs     // Catch:{ all -> 0x0051 }
                java.util.Iterator r6 = r6.iterator()     // Catch:{ all -> 0x0051 }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r0 = r5.mConsumerContextPairs     // Catch:{ all -> 0x0051 }
                r0.clear()     // Catch:{ all -> 0x0051 }
                com.facebook.imagepipeline.producers.MultiplexProducer r0 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0051 }
                K r1 = r5.mKey     // Catch:{ all -> 0x0051 }
                r0.removeMultiplexer(r1, r5)     // Catch:{ all -> 0x0051 }
                T r0 = r5.mLastIntermediateResult     // Catch:{ all -> 0x0051 }
                r5.closeSafely(r0)     // Catch:{ all -> 0x0051 }
                r0 = 0
                r5.mLastIntermediateResult = r0     // Catch:{ all -> 0x0051 }
                monitor-exit(r5)     // Catch:{ all -> 0x0051 }
            L_0x0022:
                boolean r1 = r6.hasNext()
                if (r1 == 0) goto L_0x0050
                java.lang.Object r1 = r6.next()
                android.util.Pair r1 = (android.util.Pair) r1
                monitor-enter(r1)
                java.lang.Object r2 = r1.second     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.ProducerContext r2 = (com.facebook.imagepipeline.producers.ProducerContext) r2     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.ProducerListener2 r2 = r2.getProducerListener()     // Catch:{ all -> 0x004d }
                java.lang.Object r3 = r1.second     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.ProducerContext r3 = (com.facebook.imagepipeline.producers.ProducerContext) r3     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.MultiplexProducer r4 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x004d }
                java.lang.String r4 = r4.mProducerName     // Catch:{ all -> 0x004d }
                r2.onProducerFinishWithFailure(r3, r4, r7, r0)     // Catch:{ all -> 0x004d }
                java.lang.Object r2 = r1.first     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.Consumer r2 = (com.facebook.imagepipeline.producers.Consumer) r2     // Catch:{ all -> 0x004d }
                r2.onFailure(r7)     // Catch:{ all -> 0x004d }
                monitor-exit(r1)     // Catch:{ all -> 0x004d }
                goto L_0x0022
            L_0x004d:
                r6 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x004d }
                throw r6
            L_0x0050:
                return
            L_0x0051:
                r6 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x0051 }
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onFailure(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, java.lang.Throwable):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
            if (r0.hasNext() == false) goto L_0x0079;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
            r1 = r0.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0044, code lost:
            if (com.facebook.imagepipeline.producers.BaseConsumer.isLast(r8) == false) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
            ((com.facebook.imagepipeline.producers.ProducerContext) r1.second).getProducerListener().onProducerFinishWithSuccess((com.facebook.imagepipeline.producers.ProducerContext) r1.second, com.facebook.imagepipeline.producers.MultiplexProducer.access$900(r5.this$0), (java.util.Map<java.lang.String, java.lang.String>) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x005d, code lost:
            if (r5.mMultiplexProducerContext == null) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
            ((com.facebook.imagepipeline.producers.ProducerContext) r1.second).setExtra(1, r5.mMultiplexProducerContext.getExtra(1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x006d, code lost:
            ((com.facebook.imagepipeline.producers.Consumer) r1.first).onNewResult(r7, r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0074, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0079, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onNextResult(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r6, T r7, int r8) {
            /*
                r5 = this;
                monitor-enter(r5)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r0 = r5.mForwardingConsumer     // Catch:{ all -> 0x007a }
                if (r0 == r6) goto L_0x0007
                monitor-exit(r5)     // Catch:{ all -> 0x007a }
                return
            L_0x0007:
                T r6 = r5.mLastIntermediateResult     // Catch:{ all -> 0x007a }
                r5.closeSafely(r6)     // Catch:{ all -> 0x007a }
                r6 = 0
                r5.mLastIntermediateResult = r6     // Catch:{ all -> 0x007a }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r0 = r5.mConsumerContextPairs     // Catch:{ all -> 0x007a }
                java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x007a }
                boolean r1 = com.facebook.imagepipeline.producers.BaseConsumer.isNotLast(r8)     // Catch:{ all -> 0x007a }
                if (r1 == 0) goto L_0x0026
                com.facebook.imagepipeline.producers.MultiplexProducer r1 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x007a }
                java.io.Closeable r1 = r1.cloneOrNull(r7)     // Catch:{ all -> 0x007a }
                r5.mLastIntermediateResult = r1     // Catch:{ all -> 0x007a }
                r5.mLastStatus = r8     // Catch:{ all -> 0x007a }
                goto L_0x0032
            L_0x0026:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r1 = r5.mConsumerContextPairs     // Catch:{ all -> 0x007a }
                r1.clear()     // Catch:{ all -> 0x007a }
                com.facebook.imagepipeline.producers.MultiplexProducer r1 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x007a }
                K r2 = r5.mKey     // Catch:{ all -> 0x007a }
                r1.removeMultiplexer(r2, r5)     // Catch:{ all -> 0x007a }
            L_0x0032:
                monitor-exit(r5)     // Catch:{ all -> 0x007a }
            L_0x0033:
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x0079
                java.lang.Object r1 = r0.next()
                android.util.Pair r1 = (android.util.Pair) r1
                monitor-enter(r1)
                boolean r2 = com.facebook.imagepipeline.producers.BaseConsumer.isLast(r8)     // Catch:{ all -> 0x0076 }
                if (r2 == 0) goto L_0x006d
                java.lang.Object r2 = r1.second     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerContext r2 = (com.facebook.imagepipeline.producers.ProducerContext) r2     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerListener2 r2 = r2.getProducerListener()     // Catch:{ all -> 0x0076 }
                java.lang.Object r3 = r1.second     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerContext r3 = (com.facebook.imagepipeline.producers.ProducerContext) r3     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.MultiplexProducer r4 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0076 }
                java.lang.String r4 = r4.mProducerName     // Catch:{ all -> 0x0076 }
                r2.onProducerFinishWithSuccess(r3, r4, r6)     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.BaseProducerContext r2 = r5.mMultiplexProducerContext     // Catch:{ all -> 0x0076 }
                if (r2 == 0) goto L_0x006d
                java.lang.Object r2 = r1.second     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerContext r2 = (com.facebook.imagepipeline.producers.ProducerContext) r2     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.BaseProducerContext r3 = r5.mMultiplexProducerContext     // Catch:{ all -> 0x0076 }
                r4 = 1
                java.lang.String r3 = r3.getExtra(r4)     // Catch:{ all -> 0x0076 }
                r2.setExtra(r4, r3)     // Catch:{ all -> 0x0076 }
            L_0x006d:
                java.lang.Object r2 = r1.first     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.Consumer r2 = (com.facebook.imagepipeline.producers.Consumer) r2     // Catch:{ all -> 0x0076 }
                r2.onNewResult(r7, r8)     // Catch:{ all -> 0x0076 }
                monitor-exit(r1)     // Catch:{ all -> 0x0076 }
                goto L_0x0033
            L_0x0076:
                r6 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0076 }
                throw r6
            L_0x0079:
                return
            L_0x007a:
                r6 = move-exception
                monitor-exit(r5)     // Catch:{ all -> 0x007a }
                throw r6
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onNextResult(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, java.io.Closeable, int):void");
        }

        public void onCancelled(MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer) {
            synchronized (this) {
                if (this.mForwardingConsumer == forwardingConsumer) {
                    this.mForwardingConsumer = null;
                    this.mMultiplexProducerContext = null;
                    closeSafely(this.mLastIntermediateResult);
                    this.mLastIntermediateResult = null;
                    startInputProducerIfHasAttachedConsumers();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
            r0 = r3.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
            monitor-enter(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            ((com.facebook.imagepipeline.producers.Consumer) r0.first).onProgressUpdate(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0024, code lost:
            monitor-exit(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0029, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
            if (r3.hasNext() == false) goto L_0x0029;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onProgressUpdate(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r3, float r4) {
            /*
                r2 = this;
                monitor-enter(r2)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r0 = r2.mForwardingConsumer     // Catch:{ all -> 0x002a }
                if (r0 == r3) goto L_0x0007
                monitor-exit(r2)     // Catch:{ all -> 0x002a }
                return
            L_0x0007:
                r2.mLastProgress = r4     // Catch:{ all -> 0x002a }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r3 = r2.mConsumerContextPairs     // Catch:{ all -> 0x002a }
                java.util.Iterator r3 = r3.iterator()     // Catch:{ all -> 0x002a }
                monitor-exit(r2)     // Catch:{ all -> 0x002a }
            L_0x0010:
                boolean r0 = r3.hasNext()
                if (r0 == 0) goto L_0x0029
                java.lang.Object r0 = r3.next()
                android.util.Pair r0 = (android.util.Pair) r0
                monitor-enter(r0)
                java.lang.Object r1 = r0.first     // Catch:{ all -> 0x0026 }
                com.facebook.imagepipeline.producers.Consumer r1 = (com.facebook.imagepipeline.producers.Consumer) r1     // Catch:{ all -> 0x0026 }
                r1.onProgressUpdate(r4)     // Catch:{ all -> 0x0026 }
                monitor-exit(r0)     // Catch:{ all -> 0x0026 }
                goto L_0x0010
            L_0x0026:
                r3 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0026 }
                throw r3
            L_0x0029:
                return
            L_0x002a:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x002a }
                throw r3
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onProgressUpdate(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, float):void");
        }

        private void closeSafely(Closeable closeable) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private class ForwardingConsumer extends BaseConsumer<T> {
            private ForwardingConsumer() {
            }

            /* access modifiers changed from: protected */
            public void onNewResultImpl(T t, int i) {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onNewResult");
                    }
                    Multiplexer.this.onNextResult(this, t, i);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onFailureImpl(Throwable th) {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onFailure");
                    }
                    Multiplexer.this.onFailure(this, th);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onCancellationImpl() {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onCancellation");
                    }
                    Multiplexer.this.onCancelled(this);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onProgressUpdateImpl(float f) {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onProgressUpdate");
                    }
                    Multiplexer.this.onProgressUpdate(this, f);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }
        }
    }
}
