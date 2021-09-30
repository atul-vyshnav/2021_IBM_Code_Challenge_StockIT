package org.unimodules.core;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.Package;
import org.unimodules.core.interfaces.SingletonModule;

public class BasePackage implements Package {
    public List<InternalModule> createInternalModules(Context context) {
        return Collections.emptyList();
    }

    public List<ExportedModule> createExportedModules(Context context) {
        return Collections.emptyList();
    }

    public List<ViewManager> createViewManagers(Context context) {
        return Collections.emptyList();
    }

    public List<SingletonModule> createSingletonModules(Context context) {
        return Collections.emptyList();
    }
}
