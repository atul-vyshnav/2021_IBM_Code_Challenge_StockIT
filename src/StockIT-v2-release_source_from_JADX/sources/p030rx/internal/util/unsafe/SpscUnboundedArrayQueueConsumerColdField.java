package p030rx.internal.util.unsafe;

/* renamed from: rx.internal.util.unsafe.SpscUnboundedArrayQueueConsumerColdField */
/* compiled from: SpscUnboundedArrayQueue */
abstract class SpscUnboundedArrayQueueConsumerColdField<E> extends SpscUnboundedArrayQueueL2Pad<E> {
    protected E[] consumerBuffer;
    protected long consumerMask;

    SpscUnboundedArrayQueueConsumerColdField() {
    }
}
