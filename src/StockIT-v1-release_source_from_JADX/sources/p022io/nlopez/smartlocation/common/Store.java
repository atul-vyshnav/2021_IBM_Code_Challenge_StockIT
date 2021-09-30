package p022io.nlopez.smartlocation.common;

/* renamed from: io.nlopez.smartlocation.common.Store */
public interface Store<T> {
    T get(String str);

    void put(String str, T t);

    void remove(String str);
}
