package expo.modules.p019av.player.datasource;

import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Predicate;
import java.util.Map;
import okhttp3.CacheControl;
import okhttp3.Call;

/* renamed from: expo.modules.av.player.datasource.CustomHeadersOkHttpDataSourceFactory */
public class CustomHeadersOkHttpDataSourceFactory extends HttpDataSource.BaseFactory {
    private final CacheControl mCacheControl = null;
    private final Call.Factory mCallFactory;
    private final String mUserAgent;

    public CustomHeadersOkHttpDataSourceFactory(Call.Factory factory, String str, Map<String, Object> map) {
        this.mCallFactory = factory;
        this.mUserAgent = str;
        updateRequestProperties(getDefaultRequestProperties(), map);
    }

    /* access modifiers changed from: protected */
    public void updateRequestProperties(HttpDataSource.RequestProperties requestProperties, Map<String, Object> map) {
        if (map != null) {
            for (Map.Entry next : map.entrySet()) {
                if (next.getValue() instanceof String) {
                    requestProperties.set((String) next.getKey(), (String) next.getValue());
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public OkHttpDataSource createDataSourceInternal(HttpDataSource.RequestProperties requestProperties) {
        return new OkHttpDataSource(this.mCallFactory, this.mUserAgent, (Predicate<String>) null, this.mCacheControl, requestProperties);
    }
}
