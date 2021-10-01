package okhttp3.internal.http;

import java.io.IOException;
import javax.annotation.Nullable;
import okhttp3.C3401Response;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.internal.connection.RealConnection;
import okio.Sink;
import okio.Source;

public interface ExchangeCodec {
    public static final int DISCARD_STREAM_TIMEOUT_MILLIS = 100;

    void cancel();

    RealConnection connection();

    Sink createRequestBody(Request request, long j) throws IOException;

    void finishRequest() throws IOException;

    void flushRequest() throws IOException;

    Source openResponseBodySource(C3401Response response) throws IOException;

    @Nullable
    C3401Response.Builder readResponseHeaders(boolean z) throws IOException;

    long reportedContentLength(C3401Response response) throws IOException;

    Headers trailers() throws IOException;

    void writeRequestHeaders(Request request) throws IOException;
}
