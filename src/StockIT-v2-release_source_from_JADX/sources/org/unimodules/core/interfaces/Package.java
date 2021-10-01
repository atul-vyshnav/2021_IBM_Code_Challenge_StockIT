package org.unimodules.core.interfaces;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ViewManager;

public interface Package {
    List<? extends ExportedModule> createExportedModules(Context context);

    List<? extends InternalModule> createInternalModules(Context context);

    List<? extends SingletonModule> createSingletonModules(Context context);

    List<? extends ViewManager> createViewManagers(Context context);

    /* renamed from: org.unimodules.core.interfaces.Package$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static List $default$createInternalModules(Package _this, Context context) {
            return Collections.emptyList();
        }

        public static List $default$createExportedModules(Package _this, Context context) {
            return Collections.emptyList();
        }

        public static List $default$createViewManagers(Package _this, Context context) {
            return Collections.emptyList();
        }

        public static List $default$createSingletonModules(Package _this, Context context) {
            return Collections.emptyList();
        }
    }
}
