package p030rx.internal.util.unsafe;

import java.util.AbstractQueue;

/* renamed from: rx.internal.util.unsafe.SpscUnboundedArrayQueueProducerFields */
/* compiled from: SpscUnboundedArrayQueue */
abstract class SpscUnboundedArrayQueueProducerFields<E> extends AbstractQueue<E> {
    protected long producerIndex;

    SpscUnboundedArrayQueueProducerFields() {
    }
}
