package expo.modules.structuredheaders;

public interface NumberItem<T> extends Item<T>, LongSupplier {
    int getDivisor();

    NumberItem<T> withParams(Parameters parameters);
}
