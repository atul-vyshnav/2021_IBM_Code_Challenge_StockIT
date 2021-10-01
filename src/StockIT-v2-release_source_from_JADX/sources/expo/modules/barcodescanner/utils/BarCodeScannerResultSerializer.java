package expo.modules.barcodescanner.utils;

import android.os.Bundle;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerResult;

public class BarCodeScannerResultSerializer {
    public static Bundle toBundle(BarCodeScannerResult barCodeScannerResult, float f) {
        Bundle bundle = new Bundle();
        bundle.putString("data", barCodeScannerResult.getValue());
        bundle.putInt("type", barCodeScannerResult.getType());
        if (!barCodeScannerResult.getCornerPoints().isEmpty()) {
            Pair<ArrayList<Bundle>, Bundle> cornerPointsAndBoundingBox = getCornerPointsAndBoundingBox(barCodeScannerResult.getCornerPoints(), f);
            bundle.putParcelableArrayList("cornerPoints", (ArrayList) cornerPointsAndBoundingBox.first);
            bundle.putBundle("bounds", (Bundle) cornerPointsAndBoundingBox.second);
        }
        return bundle;
    }

    private static Pair<ArrayList<Bundle>, Bundle> getCornerPointsAndBoundingBox(List<Integer> list, float f) {
        ArrayList arrayList = new ArrayList();
        Bundle bundle = new Bundle();
        float f2 = Float.MIN_VALUE;
        float f3 = Float.MIN_VALUE;
        float f4 = Float.MAX_VALUE;
        float f5 = Float.MAX_VALUE;
        for (int i = 0; i < list.size(); i += 2) {
            float intValue = ((float) list.get(i).intValue()) / f;
            float intValue2 = ((float) list.get(i + 1).intValue()) / f;
            f4 = Math.min(f4, intValue);
            f5 = Math.min(f5, intValue2);
            f2 = Math.max(f2, intValue);
            f3 = Math.max(f3, intValue2);
            arrayList.add(getPoint(intValue, intValue2));
        }
        bundle.putParcelable("origin", getPoint(f4, f5));
        bundle.putParcelable("size", getSize(f2 - f4, f3 - f5));
        return new Pair<>(arrayList, bundle);
    }

    private static Bundle getSize(float f, float f2) {
        Bundle bundle = new Bundle();
        bundle.putFloat("width", f);
        bundle.putFloat("height", f2);
        return bundle;
    }

    private static Bundle getPoint(float f, float f2) {
        Bundle bundle = new Bundle();
        bundle.putFloat("x", f);
        bundle.putFloat("y", f2);
        return bundle;
    }
}
