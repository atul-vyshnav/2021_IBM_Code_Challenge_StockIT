package expo.modules.structuredheaders;

import androidx.core.util.Supplier;

public interface Type<T> extends Supplier<T> {
    String serialize();

    StringBuilder serializeTo(StringBuilder sb);
}
