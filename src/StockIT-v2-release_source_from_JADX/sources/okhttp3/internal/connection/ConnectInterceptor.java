package okhttp3.internal.connection;

import java.io.IOException;
import okhttp3.C3401Response;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.http.RealInterceptorChain;

public final class ConnectInterceptor implements Interceptor {
    public final OkHttpClient client;

    public ConnectInterceptor(OkHttpClient okHttpClient) {
        this.client = okHttpClient;
    }

    public C3401Response intercept(Interceptor.Chain chain) throws IOException {
        RealInterceptorChain realInterceptorChain = (RealInterceptorChain) chain;
        Request request = realInterceptorChain.request();
        Transmitter transmitter = realInterceptorChain.transmitter();
        return realInterceptorChain.proceed(request, transmitter, transmitter.newExchange(chain, !request.method().equals("GET")));
    }
}
