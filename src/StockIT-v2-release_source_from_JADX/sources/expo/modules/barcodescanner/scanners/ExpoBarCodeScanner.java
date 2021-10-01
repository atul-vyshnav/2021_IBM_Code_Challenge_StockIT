package expo.modules.barcodescanner.scanners;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import org.unimodules.interfaces.barcodescanner.BarCodeScanner;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerSettings;

public abstract class ExpoBarCodeScanner implements BarCodeScanner {
    protected List<Integer> mBarCodeTypes;
    protected Context mContext;

    public abstract boolean isAvailable();

    ExpoBarCodeScanner(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: package-private */
    public boolean areNewAndOldBarCodeTypesEqual(List<Integer> list) {
        if (this.mBarCodeTypes == null) {
            return false;
        }
        HashSet hashSet = new HashSet(this.mBarCodeTypes);
        HashSet hashSet2 = new HashSet(list);
        if (hashSet.size() != hashSet2.size()) {
            return false;
        }
        hashSet.removeAll(hashSet2);
        return hashSet.isEmpty();
    }

    /* access modifiers changed from: package-private */
    public List<Integer> parseBarCodeTypesFromSettings(BarCodeScannerSettings barCodeScannerSettings) {
        Object types = barCodeScannerSettings.getTypes();
        if (types == null || !(types instanceof List)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Object next : (List) types) {
            if (next instanceof Number) {
                arrayList.add(Integer.valueOf(((Number) next).intValue()));
            }
        }
        return arrayList;
    }
}
