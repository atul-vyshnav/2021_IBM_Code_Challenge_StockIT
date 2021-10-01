package expo.modules.barcodescanner.scanners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import expo.modules.barcodescanner.utils.Frame;
import expo.modules.barcodescanner.utils.FrameFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerResult;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerSettings;

public class GMVBarCodeScanner extends ExpoBarCodeScanner {
    private String TAG = GMVBarCodeScanner.class.getSimpleName();
    private BarcodeDetector mBarcodeDetector = new BarcodeDetector.Builder(this.mContext).setBarcodeFormats(0).build();

    public GMVBarCodeScanner(Context context) {
        super(context);
    }

    public BarCodeScannerResult scan(byte[] bArr, int i, int i2, int i3) {
        try {
            List<BarCodeScannerResult> scan = scan(FrameFactory.buildFrame(bArr, i, i2, i3));
            if (scan.size() > 0) {
                return scan.get(0);
            }
            return null;
        } catch (Exception e) {
            String str = this.TAG;
            Log.e(str, "Failed to detect barcode: " + e.getMessage());
            return null;
        }
    }

    public List<BarCodeScannerResult> scanMultiple(Bitmap bitmap) {
        return scan(FrameFactory.buildFrame(bitmap));
    }

    private List<BarCodeScannerResult> scan(Frame frame) {
        try {
            SparseArray<Barcode> detect = this.mBarcodeDetector.detect(frame.getFrame());
            ArrayList arrayList = new ArrayList();
            int width = frame.getDimensions().getWidth();
            int height = frame.getDimensions().getHeight();
            for (int i = 0; i < detect.size(); i++) {
                Barcode barcode = detect.get(detect.keyAt(i));
                ArrayList arrayList2 = new ArrayList();
                for (Point point : barcode.cornerPoints) {
                    arrayList2.addAll(Arrays.asList(new Integer[]{Integer.valueOf(point.x), Integer.valueOf(point.y)}));
                }
                arrayList.add(new BarCodeScannerResult(barcode.format, barcode.rawValue, arrayList2, height, width));
            }
            return arrayList;
        } catch (Exception e) {
            Log.e(this.TAG, "Failed to detect barcode: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void setSettings(BarCodeScannerSettings barCodeScannerSettings) {
        List<Integer> parseBarCodeTypesFromSettings = parseBarCodeTypesFromSettings(barCodeScannerSettings);
        if (!areNewAndOldBarCodeTypesEqual(parseBarCodeTypesFromSettings)) {
            int i = 0;
            for (Integer intValue : parseBarCodeTypesFromSettings) {
                i |= intValue.intValue();
            }
            this.mBarCodeTypes = parseBarCodeTypesFromSettings;
            BarcodeDetector barcodeDetector = this.mBarcodeDetector;
            if (barcodeDetector != null) {
                barcodeDetector.release();
            }
            this.mBarcodeDetector = new BarcodeDetector.Builder(this.mContext).setBarcodeFormats(i).build();
        }
    }

    public boolean isAvailable() {
        return this.mBarcodeDetector.isOperational();
    }
}
