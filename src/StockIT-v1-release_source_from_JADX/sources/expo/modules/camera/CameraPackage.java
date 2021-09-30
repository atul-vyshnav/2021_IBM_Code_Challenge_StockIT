package expo.modules.camera;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ViewManager;

public class CameraPackage extends BasePackage {
    public List<ExportedModule> createExportedModules(Context context) {
        return Collections.singletonList(new CameraModule(context));
    }

    public List<ViewManager> createViewManagers(Context context) {
        return Collections.singletonList(new CameraViewManager());
    }
}
