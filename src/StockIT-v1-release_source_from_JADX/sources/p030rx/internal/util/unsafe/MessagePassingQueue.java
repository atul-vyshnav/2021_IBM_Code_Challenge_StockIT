package p030rx.internal.util.unsafe;

/* renamed from: rx.internal.util.unsafe.MessagePassingQueue */
public interface MessagePassingQueue<M> {
    boolean isEmpty();

    boolean offer(M m);

    M peek();

    M poll();

    int size();
}
