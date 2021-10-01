package org.unimodules.interfaces.barcodescanner;

import android.content.Context;

public interface BarCodeScannerProvider {
    BarCodeScanner createBarCodeDetectorWithContext(Context context);
}
