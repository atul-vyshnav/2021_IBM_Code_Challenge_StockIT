package kotlin.reflect.jvm.internal.impl.serialization.deserialization.builtins;

import com.RNFetchBlob.RNFetchBlobConst;
import java.io.InputStream;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: BuiltInsResourceLoader.kt */
public final class BuiltInsResourceLoader {
    public final InputStream loadResource(String str) {
        Intrinsics.checkNotNullParameter(str, RNFetchBlobConst.RNFB_RESPONSE_PATH);
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resourceAsStream = classLoader == null ? null : classLoader.getResourceAsStream(str);
        return resourceAsStream == null ? ClassLoader.getSystemResourceAsStream(str) : resourceAsStream;
    }
}
