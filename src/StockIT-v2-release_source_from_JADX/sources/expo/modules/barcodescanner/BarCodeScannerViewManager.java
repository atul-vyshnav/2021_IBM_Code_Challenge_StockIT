package expo.modules.barcodescanner;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.ViewManager;
import org.unimodules.core.interfaces.ExpoProp;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerSettings;

public class BarCodeScannerViewManager extends ViewManager<BarCodeScannerView> {
    private static final String TAG = "ExpoBarCodeScannerView";
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return TAG;
    }

    public enum Events {
        EVENT_ON_BAR_CODE_SCANNED("onBarCodeScanned");
        
        private final String mName;

        private Events(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    public BarCodeScannerView createViewInstance(Context context) {
        return new BarCodeScannerView(context, this.mModuleRegistry);
    }

    public ViewManager.ViewManagerType getViewManagerType() {
        return ViewManager.ViewManagerType.GROUP;
    }

    public List<String> getExportedEventNames() {
        ArrayList arrayList = new ArrayList(Events.values().length);
        for (Events events : Events.values()) {
            arrayList.add(events.toString());
        }
        return arrayList;
    }

    @ExpoProp(name = "type")
    public void setType(BarCodeScannerView barCodeScannerView, int i) {
        barCodeScannerView.setCameraType(i);
    }

    @ExpoProp(name = "barCodeTypes")
    public void setBarCodeTypes(BarCodeScannerView barCodeScannerView, final ArrayList<Double> arrayList) {
        if (arrayList != null) {
            barCodeScannerView.setBarCodeScannerSettings(new BarCodeScannerSettings() {
                {
                    putTypes(arrayList);
                }
            });
        }
    }
}
