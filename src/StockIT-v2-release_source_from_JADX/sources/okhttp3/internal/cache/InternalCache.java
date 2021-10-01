package okhttp3.internal.cache;

import java.io.IOException;
import javax.annotation.Nullable;
import okhttp3.C3401Response;
import okhttp3.Request;

public interface InternalCache {
    @Nullable
    C3401Response get(Request request) throws IOException;

    @Nullable
    CacheRequest put(C3401Response response) throws IOException;

    void remove(Request request) throws IOException;

    void trackConditionalCacheHit();

    void trackResponse(CacheStrategy cacheStrategy);

    void update(C3401Response response, C3401Response response2);
}
