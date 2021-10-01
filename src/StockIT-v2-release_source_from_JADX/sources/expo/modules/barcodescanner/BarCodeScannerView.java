package expo.modules.barcodescanner;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerResult;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerSettings;

public class BarCodeScannerView extends ViewGroup {
    private int mActualDeviceOrientation = -1;
    private final Context mContext;
    private int mLeftPadding = 0;
    private final ModuleRegistry mModuleRegistry;
    private final OrientationEventListener mOrientationListener;
    private int mTopPadding = 0;
    private int mType = 0;
    private BarCodeScannerViewFinder mViewFinder = null;

    public BarCodeScannerView(final Context context, ModuleRegistry moduleRegistry) {
        super(context);
        this.mContext = context;
        this.mModuleRegistry = moduleRegistry;
        ExpoBarCodeScanner.createInstance(getDeviceOrientation(context));
        C29511 r3 = new OrientationEventListener(3, context) {
            public void onOrientationChanged(int i) {
                if (BarCodeScannerView.this.setActualDeviceOrientation(context)) {
                    BarCodeScannerView.this.layoutViewFinder();
                }
            }
        };
        this.mOrientationListener = r3;
        if (r3.canDetectOrientation()) {
            this.mOrientationListener.enable();
        } else {
            this.mOrientationListener.disable();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OrientationEventListener orientationEventListener = this.mOrientationListener;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutViewFinder(i, i2, i3, i4);
    }

    public void onViewAdded(View view) {
        BarCodeScannerViewFinder barCodeScannerViewFinder = this.mViewFinder;
        if (barCodeScannerViewFinder != view) {
            removeView(barCodeScannerViewFinder);
            addView(this.mViewFinder, 0);
        }
    }

    public void onBarCodeScanned(BarCodeScannerResult barCodeScannerResult) {
        transformBarCodeScannerResultToViewCoordinates(barCodeScannerResult);
        ((EventEmitter) this.mModuleRegistry.getModule(EventEmitter.class)).emit(getId(), (EventEmitter.Event) BarCodeScannedEvent.obtain(getId(), barCodeScannerResult, getDisplayDensity()));
    }

    private float getDisplayDensity() {
        return getResources().getDisplayMetrics().density;
    }

    private void transformBarCodeScannerResultToViewCoordinates(BarCodeScannerResult barCodeScannerResult) {
        List<Integer> cornerPoints = barCodeScannerResult.getCornerPoints();
        int width = getWidth() - (this.mLeftPadding * 2);
        int height = getHeight() - (this.mTopPadding * 2);
        if (this.mType == 1 && getDeviceOrientation(this.mContext) % 2 == 0) {
            for (int i = 1; i < cornerPoints.size(); i += 2) {
                cornerPoints.set(i, Integer.valueOf(barCodeScannerResult.getReferenceImageHeight() - cornerPoints.get(i).intValue()));
            }
        }
        if (this.mType == 1 && getDeviceOrientation(this.mContext) % 2 != 0) {
            for (int i2 = 0; i2 < cornerPoints.size(); i2 += 2) {
                cornerPoints.set(i2, Integer.valueOf(barCodeScannerResult.getReferenceImageWidth() - cornerPoints.get(i2).intValue()));
            }
        }
        for (int i3 = 0; i3 < cornerPoints.size(); i3 += 2) {
            cornerPoints.set(i3, Integer.valueOf(Math.round((((float) (cornerPoints.get(i3).intValue() * width)) / ((float) barCodeScannerResult.getReferenceImageWidth())) + ((float) this.mLeftPadding))));
        }
        for (int i4 = 1; i4 < cornerPoints.size(); i4 += 2) {
            cornerPoints.set(i4, Integer.valueOf(Math.round((((float) (cornerPoints.get(i4).intValue() * height)) / ((float) barCodeScannerResult.getReferenceImageHeight())) + ((float) this.mTopPadding))));
        }
        barCodeScannerResult.setReferenceImageHeight(getHeight());
        barCodeScannerResult.setReferenceImageWidth(getWidth());
        barCodeScannerResult.setCornerPoints(cornerPoints);
    }

    public void setCameraType(int i) {
        this.mType = i;
        BarCodeScannerViewFinder barCodeScannerViewFinder = this.mViewFinder;
        if (barCodeScannerViewFinder != null) {
            barCodeScannerViewFinder.setCameraType(i);
            ExpoBarCodeScanner.getInstance().adjustPreviewLayout(i);
            return;
        }
        BarCodeScannerViewFinder barCodeScannerViewFinder2 = new BarCodeScannerViewFinder(this.mContext, i, this, this.mModuleRegistry);
        this.mViewFinder = barCodeScannerViewFinder2;
        addView(barCodeScannerViewFinder2);
    }

    public void setBarCodeScannerSettings(BarCodeScannerSettings barCodeScannerSettings) {
        this.mViewFinder.setBarCodeScannerSettings(barCodeScannerSettings);
    }

    /* access modifiers changed from: private */
    public boolean setActualDeviceOrientation(Context context) {
        int deviceOrientation = getDeviceOrientation(context);
        if (this.mActualDeviceOrientation == deviceOrientation) {
            return false;
        }
        this.mActualDeviceOrientation = deviceOrientation;
        ExpoBarCodeScanner.getInstance().setActualDeviceOrientation(this.mActualDeviceOrientation);
        return true;
    }

    private int getDeviceOrientation(Context context) {
        return ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getRotation();
    }

    public void layoutViewFinder() {
        layoutViewFinder(getLeft(), getTop(), getRight(), getBottom());
    }

    private void layoutViewFinder(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        BarCodeScannerViewFinder barCodeScannerViewFinder = this.mViewFinder;
        if (barCodeScannerViewFinder != null) {
            float f = (float) (i3 - i);
            float f2 = (float) (i4 - i2);
            double ratio = barCodeScannerViewFinder.getRatio();
            double d = ((double) f2) * ratio;
            double d2 = (double) f;
            if (d < d2) {
                i6 = (int) d;
                i5 = (int) f2;
            } else {
                i5 = (int) (d2 / ratio);
                i6 = (int) f;
            }
            int i7 = (int) ((f - ((float) i6)) / 2.0f);
            int i8 = (int) ((f2 - ((float) i5)) / 2.0f);
            this.mLeftPadding = i7;
            this.mTopPadding = i8;
            this.mViewFinder.layout(i7, i8, i6 + i7, i5 + i8);
            postInvalidate(getLeft(), getTop(), getRight(), getBottom());
        }
    }
}
