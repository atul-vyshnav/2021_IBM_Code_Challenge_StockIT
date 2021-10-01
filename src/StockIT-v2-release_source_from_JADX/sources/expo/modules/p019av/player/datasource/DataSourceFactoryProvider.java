package expo.modules.p019av.player.datasource;

import android.content.Context;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.util.Map;
import org.unimodules.core.ModuleRegistry;

/* renamed from: expo.modules.av.player.datasource.DataSourceFactoryProvider */
public interface DataSourceFactoryProvider {
    DataSource.Factory createFactory(Context context, ModuleRegistry moduleRegistry, String str, Map<String, Object> map, TransferListener transferListener);
}
