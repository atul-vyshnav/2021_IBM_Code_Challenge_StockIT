package expo.modules.keepawake;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ViewManager;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.Package;
import org.unimodules.core.interfaces.SingletonModule;

public class KeepAwakePackage implements Package {
    public /* synthetic */ List<? extends SingletonModule> createSingletonModules(Context context) {
        return Package.CC.$default$createSingletonModules(this, context);
    }

    public /* synthetic */ List<? extends ViewManager> createViewManagers(Context context) {
        return Package.CC.$default$createViewManagers(this, context);
    }

    public List<ExportedModule> createExportedModules(Context context) {
        return Collections.singletonList(new KeepAwakeModule(context));
    }

    public List<? extends InternalModule> createInternalModules(Context context) {
        return Collections.singletonList(new ExpoKeepAwakeManager());
    }
}
