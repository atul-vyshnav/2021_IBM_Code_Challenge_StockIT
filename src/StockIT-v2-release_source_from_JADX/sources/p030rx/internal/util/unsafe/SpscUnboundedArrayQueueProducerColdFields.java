package p030rx.internal.util.unsafe;

/* renamed from: rx.internal.util.unsafe.SpscUnboundedArrayQueueProducerColdFields */
/* compiled from: SpscUnboundedArrayQueue */
abstract class SpscUnboundedArrayQueueProducerColdFields<E> extends SpscUnboundedArrayQueueProducerFields<E> {
    protected E[] producerBuffer;
    protected long producerLookAhead;
    protected int producerLookAheadStep;
    protected long producerMask;

    SpscUnboundedArrayQueueProducerColdFields() {
    }
}
