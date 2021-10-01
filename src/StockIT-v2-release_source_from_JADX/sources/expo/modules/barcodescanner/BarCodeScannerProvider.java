package expo.modules.barcodescanner;

import android.content.Context;
import expo.modules.barcodescanner.scanners.GMVBarCodeScanner;
import expo.modules.barcodescanner.scanners.ZxingBarCodeScanner;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.interfaces.barcodescanner.BarCodeScanner;

public class BarCodeScannerProvider implements InternalModule, org.unimodules.interfaces.barcodescanner.BarCodeScannerProvider {
    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(org.unimodules.interfaces.barcodescanner.BarCodeScannerProvider.class);
    }

    public BarCodeScanner createBarCodeDetectorWithContext(Context context) {
        GMVBarCodeScanner gMVBarCodeScanner = new GMVBarCodeScanner(context);
        return !gMVBarCodeScanner.isAvailable() ? new ZxingBarCodeScanner(context) : gMVBarCodeScanner;
    }
}
