package expo.modules.camera.tasks;

import android.os.AsyncTask;
import org.unimodules.interfaces.barcodescanner.BarCodeScanner;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerResult;

public class BarCodeScannerAsyncTask extends AsyncTask<Void, Void, BarCodeScannerResult> {
    private final BarCodeScanner mBarCodeScanner;
    private BarCodeScannerAsyncTaskDelegate mDelegate;
    private int mHeight;
    private byte[] mImageData;
    private int mRotation;
    private int mWidth;

    public BarCodeScannerAsyncTask(BarCodeScannerAsyncTaskDelegate barCodeScannerAsyncTaskDelegate, BarCodeScanner barCodeScanner, byte[] bArr, int i, int i2, int i3) {
        this.mImageData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mDelegate = barCodeScannerAsyncTaskDelegate;
        this.mBarCodeScanner = barCodeScanner;
        this.mRotation = i3;
    }

    /* access modifiers changed from: protected */
    public BarCodeScannerResult doInBackground(Void... voidArr) {
        if (isCancelled() || this.mDelegate == null) {
            return null;
        }
        return this.mBarCodeScanner.scan(this.mImageData, this.mWidth, this.mHeight, this.mRotation);
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(BarCodeScannerResult barCodeScannerResult) {
        super.onPostExecute(barCodeScannerResult);
        if (barCodeScannerResult != null) {
            this.mDelegate.onBarCodeScanned(barCodeScannerResult);
        }
        this.mDelegate.onBarCodeScanningTaskCompleted();
    }
}
