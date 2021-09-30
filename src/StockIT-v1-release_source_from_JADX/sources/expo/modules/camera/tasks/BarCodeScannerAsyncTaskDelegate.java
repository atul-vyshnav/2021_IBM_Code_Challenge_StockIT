package expo.modules.camera.tasks;

import org.unimodules.interfaces.barcodescanner.BarCodeScannerResult;

public interface BarCodeScannerAsyncTaskDelegate {
    void onBarCodeScanned(BarCodeScannerResult barCodeScannerResult);

    void onBarCodeScanningTaskCompleted();
}
