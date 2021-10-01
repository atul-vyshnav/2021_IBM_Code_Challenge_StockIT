package expo.modules.barcodescanner;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ViewManager;
import org.unimodules.core.interfaces.InternalModule;

public class BarCodeScannerPackage extends BasePackage {
    public List<InternalModule> createInternalModules(Context context) {
        return Collections.singletonList(new BarCodeScannerProvider());
    }

    public List<ExportedModule> createExportedModules(Context context) {
        return Collections.singletonList(new BarCodeScannerModule(context));
    }

    public List<ViewManager> createViewManagers(Context context) {
        return Collections.singletonList(new BarCodeScannerViewManager());
    }
}
