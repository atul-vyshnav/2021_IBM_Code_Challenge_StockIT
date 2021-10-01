package expo.modules.p019av;

import android.content.Context;
import expo.modules.p019av.player.datasource.SharedCookiesDataSourceFactoryProvider;
import expo.modules.p019av.video.VideoManager;
import expo.modules.p019av.video.VideoViewManager;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ViewManager;
import org.unimodules.core.interfaces.InternalModule;

/* renamed from: expo.modules.av.AVPackage */
public class AVPackage extends BasePackage {
    public List<InternalModule> createInternalModules(Context context) {
        return Arrays.asList(new InternalModule[]{new AVManager(context), new SharedCookiesDataSourceFactoryProvider()});
    }

    public List<ExportedModule> createExportedModules(Context context) {
        return Arrays.asList(new ExportedModule[]{new AVModule(context), new VideoManager(context)});
    }

    public List<ViewManager> createViewManagers(Context context) {
        return Collections.singletonList(new VideoViewManager());
    }
}
