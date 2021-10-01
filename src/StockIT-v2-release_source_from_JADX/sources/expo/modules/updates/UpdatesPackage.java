package expo.modules.updates;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.interfaces.InternalModule;

public class UpdatesPackage extends BasePackage {
    public List<InternalModule> createInternalModules(Context context) {
        return Collections.singletonList(new UpdatesService(context));
    }

    public List<ExportedModule> createExportedModules(Context context) {
        return Collections.singletonList(new UpdatesModule(context));
    }
}
