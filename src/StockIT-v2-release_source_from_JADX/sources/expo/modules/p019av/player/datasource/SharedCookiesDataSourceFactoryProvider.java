package expo.modules.p019av.player.datasource;

import android.content.Context;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;

/* renamed from: expo.modules.av.player.datasource.SharedCookiesDataSourceFactoryProvider */
public class SharedCookiesDataSourceFactoryProvider implements InternalModule, DataSourceFactoryProvider {
    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(DataSourceFactoryProvider.class);
    }

    public DataSource.Factory createFactory(Context context, ModuleRegistry moduleRegistry, String str, Map<String, Object> map, TransferListener transferListener) {
        return new SharedCookiesDataSourceFactory(context, moduleRegistry, str, map, transferListener);
    }
}
