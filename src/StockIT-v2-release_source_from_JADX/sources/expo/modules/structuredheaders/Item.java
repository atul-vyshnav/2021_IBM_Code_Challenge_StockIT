package expo.modules.structuredheaders;

public interface Item<T> extends ListElement<T>, Parametrizable<T> {
    Item<T> withParams(Parameters parameters);
}
