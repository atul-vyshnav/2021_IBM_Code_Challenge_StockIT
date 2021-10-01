package p030rx.functions;

import java.util.concurrent.Callable;

/* renamed from: rx.functions.Func0 */
public interface Func0<R> extends Function, Callable<R> {
    R call();
}
