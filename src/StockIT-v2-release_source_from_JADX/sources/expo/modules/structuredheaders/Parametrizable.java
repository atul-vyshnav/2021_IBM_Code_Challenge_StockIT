package expo.modules.structuredheaders;

public interface Parametrizable<T> extends Type<T> {
    Parameters getParams();

    Parametrizable<T> withParams(Parameters parameters);
}
