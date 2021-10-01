package expo.modules.barcodescanner;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.view.TextureView;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.interfaces.barcodescanner.BarCodeScanner;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerProvider;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerSettings;

class BarCodeScannerViewFinder extends TextureView implements TextureView.SurfaceTextureListener, Camera.PreviewCallback {
    public static volatile boolean barCodeScannerTaskLock = false;
    /* access modifiers changed from: private */
    public BarCodeScanner mBarCodeScanner;
    /* access modifiers changed from: private */
    public BarCodeScannerView mBarCodeScannerView;
    private Camera mCamera;
    /* access modifiers changed from: private */
    public int mCameraType;
    /* access modifiers changed from: private */
    public volatile boolean mIsChanging;
    private volatile boolean mIsStarting;
    private volatile boolean mIsStopping;
    private final ModuleRegistry mModuleRegistry;
    private SurfaceTexture mSurfaceTexture;

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public BarCodeScannerViewFinder(Context context, int i, BarCodeScannerView barCodeScannerView, ModuleRegistry moduleRegistry) {
        super(context);
        this.mModuleRegistry = moduleRegistry;
        this.mCameraType = i;
        this.mBarCodeScannerView = barCodeScannerView;
        setSurfaceTextureListener(this);
        initBarCodeScanner();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.mSurfaceTexture = surfaceTexture;
        startCamera();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = null;
        stopCamera();
        return true;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.mSurfaceTexture = surfaceTexture;
    }

    public double getRatio() {
        return (double) (((float) ExpoBarCodeScanner.getInstance().getPreviewWidth(this.mCameraType)) / ((float) ExpoBarCodeScanner.getInstance().getPreviewHeight(this.mCameraType)));
    }

    public void setCameraType(final int i) {
        if (this.mCameraType != i) {
            new Thread(new Runnable() {
                public void run() {
                    boolean unused = BarCodeScannerViewFinder.this.mIsChanging = true;
                    BarCodeScannerViewFinder.this.stopPreview();
                    int unused2 = BarCodeScannerViewFinder.this.mCameraType = i;
                    BarCodeScannerViewFinder.this.startPreview();
                    boolean unused3 = BarCodeScannerViewFinder.this.mIsChanging = false;
                }
            }).start();
        }
    }

    /* access modifiers changed from: private */
    public void startPreview() {
        if (this.mSurfaceTexture != null) {
            startCamera();
        }
    }

    /* access modifiers changed from: private */
    public void stopPreview() {
        if (this.mCamera != null) {
            stopCamera();
        }
    }

    private synchronized void startCamera() {
        if (!this.mIsStarting) {
            this.mIsStarting = true;
            try {
                Camera acquireCameraInstance = ExpoBarCodeScanner.getInstance().acquireCameraInstance(this.mCameraType);
                this.mCamera = acquireCameraInstance;
                Camera.Parameters parameters = acquireCameraInstance.getParameters();
                if (parameters.getSupportedFocusModes().contains("continuous-picture")) {
                    parameters.setFocusMode("continuous-picture");
                }
                Camera.Size bestSize = ExpoBarCodeScanner.getInstance().getBestSize(parameters.getSupportedPictureSizes(), Integer.MAX_VALUE, Integer.MAX_VALUE);
                parameters.setPictureSize(bestSize.width, bestSize.height);
                this.mCamera.setParameters(parameters);
                this.mCamera.setPreviewTexture(this.mSurfaceTexture);
                this.mCamera.startPreview();
                this.mCamera.setPreviewCallback(this);
                this.mBarCodeScannerView.layoutViewFinder();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                try {
                    e2.printStackTrace();
                    stopCamera();
                } catch (Throwable th) {
                    this.mIsStarting = false;
                    throw th;
                }
            }
            this.mIsStarting = false;
        }
    }

    private synchronized void stopCamera() {
        if (!this.mIsStopping) {
            this.mIsStopping = true;
            try {
                if (this.mCamera != null) {
                    this.mCamera.stopPreview();
                    this.mCamera.setPreviewCallback((Camera.PreviewCallback) null);
                    ExpoBarCodeScanner.getInstance().releaseCameraInstance();
                    this.mCamera = null;
                }
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                } catch (Throwable th) {
                    this.mIsStopping = false;
                    throw th;
                }
            }
            this.mIsStopping = false;
        }
    }

    private void initBarCodeScanner() {
        BarCodeScannerProvider barCodeScannerProvider = (BarCodeScannerProvider) this.mModuleRegistry.getModule(BarCodeScannerProvider.class);
        if (barCodeScannerProvider != null) {
            this.mBarCodeScanner = barCodeScannerProvider.createBarCodeDetectorWithContext(getContext());
        }
    }

    public void onPreviewFrame(byte[] bArr, Camera camera) {
        if (!barCodeScannerTaskLock) {
            barCodeScannerTaskLock = true;
            new BarCodeScannerAsyncTask(camera, bArr, this.mBarCodeScannerView).execute(new Void[0]);
        }
    }

    public void setBarCodeScannerSettings(BarCodeScannerSettings barCodeScannerSettings) {
        this.mBarCodeScanner.setSettings(barCodeScannerSettings);
    }

    private class BarCodeScannerAsyncTask extends AsyncTask<Void, Void, Void> {
        private final Camera mCamera;
        private byte[] mImageData;

        BarCodeScannerAsyncTask(Camera camera, byte[] bArr, BarCodeScannerView barCodeScannerView) {
            this.mCamera = camera;
            this.mImageData = bArr;
            BarCodeScannerView unused = BarCodeScannerViewFinder.this.mBarCodeScannerView = barCodeScannerView;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:7:0x0014, code lost:
            r6 = r6.getParameters().getPreviewSize();
            r6 = expo.modules.barcodescanner.BarCodeScannerViewFinder.access$500(r5.this$0).scan(r5.mImageData, r6.width, r6.height, expo.modules.barcodescanner.ExpoBarCodeScanner.getInstance().getRotation());
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.lang.Void doInBackground(java.lang.Void... r6) {
            /*
                r5 = this;
                boolean r6 = r5.isCancelled()
                r0 = 0
                if (r6 == 0) goto L_0x0008
                return r0
            L_0x0008:
                expo.modules.barcodescanner.BarCodeScannerViewFinder r6 = expo.modules.barcodescanner.BarCodeScannerViewFinder.this
                boolean r6 = r6.mIsChanging
                if (r6 != 0) goto L_0x0047
                android.hardware.Camera r6 = r5.mCamera
                if (r6 == 0) goto L_0x0047
                android.hardware.Camera$Parameters r6 = r6.getParameters()
                android.hardware.Camera$Size r6 = r6.getPreviewSize()
                int r1 = r6.width
                int r6 = r6.height
                expo.modules.barcodescanner.ExpoBarCodeScanner r2 = expo.modules.barcodescanner.ExpoBarCodeScanner.getInstance()
                int r2 = r2.getRotation()
                expo.modules.barcodescanner.BarCodeScannerViewFinder r3 = expo.modules.barcodescanner.BarCodeScannerViewFinder.this
                org.unimodules.interfaces.barcodescanner.BarCodeScanner r3 = r3.mBarCodeScanner
                byte[] r4 = r5.mImageData
                org.unimodules.interfaces.barcodescanner.BarCodeScannerResult r6 = r3.scan(r4, r1, r6, r2)
                if (r6 == 0) goto L_0x0047
                android.os.Handler r1 = new android.os.Handler
                android.os.Looper r2 = android.os.Looper.getMainLooper()
                r1.<init>(r2)
                expo.modules.barcodescanner.BarCodeScannerViewFinder$BarCodeScannerAsyncTask$1 r2 = new expo.modules.barcodescanner.BarCodeScannerViewFinder$BarCodeScannerAsyncTask$1
                r2.<init>(r6)
                r1.post(r2)
            L_0x0047:
                r6 = 0
                expo.modules.barcodescanner.BarCodeScannerViewFinder.barCodeScannerTaskLock = r6
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: expo.modules.barcodescanner.BarCodeScannerViewFinder.BarCodeScannerAsyncTask.doInBackground(java.lang.Void[]):java.lang.Void");
        }
    }
}
