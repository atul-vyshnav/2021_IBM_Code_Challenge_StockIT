package bleshadow.dagger.internal;

import bleshadow.dagger.Lazy;
import bleshadow.javax.inject.Provider;

public final class DoubleCheck<T> implements Provider<T>, Lazy<T> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final Object UNINITIALIZED = new Object();
    private volatile Object instance = UNINITIALIZED;
    private volatile Provider<T> provider;

    private DoubleCheck(Provider<T> provider2) {
        this.provider = provider2;
    }

    public T get() {
        T t = this.instance;
        if (t == UNINITIALIZED) {
            synchronized (this) {
                t = this.instance;
                if (t == UNINITIALIZED) {
                    t = this.provider.get();
                    T t2 = this.instance;
                    if (t2 != UNINITIALIZED) {
                        if (t2 != t) {
                            throw new IllegalStateException("Scoped provider was invoked recursively returning different results: " + t2 + " & " + t + ". This is likely due to a circular dependency.");
                        }
                    }
                    this.instance = t;
                    this.provider = null;
                }
            }
        }
        return t;
    }

    public static <P extends Provider<T>, T> Provider<T> provider(P p) {
        Preconditions.checkNotNull(p);
        if (p instanceof DoubleCheck) {
            return p;
        }
        return new DoubleCheck(p);
    }

    public static <P extends Provider<T>, T> Lazy<T> lazy(P p) {
        if (p instanceof Lazy) {
            return (Lazy) p;
        }
        return new DoubleCheck((Provider) Preconditions.checkNotNull(p));
    }
}
