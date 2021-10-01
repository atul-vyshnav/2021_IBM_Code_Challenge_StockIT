package expo.modules.camera.events;

import android.os.Bundle;
import androidx.core.util.Pools;
import com.facebook.react.uimanager.events.TouchesHelper;
import expo.modules.camera.CameraViewManager;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerResult;

public class BarCodeScannedEvent extends EventEmitter.BaseEvent {
    private static final Pools.SynchronizedPool<BarCodeScannedEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private BarCodeScannerResult mBarCode;
    private int mViewTag;

    private BarCodeScannedEvent() {
    }

    public static BarCodeScannedEvent obtain(int i, BarCodeScannerResult barCodeScannerResult) {
        BarCodeScannedEvent acquire = EVENTS_POOL.acquire();
        if (acquire == null) {
            acquire = new BarCodeScannedEvent();
        }
        acquire.init(i, barCodeScannerResult);
        return acquire;
    }

    private void init(int i, BarCodeScannerResult barCodeScannerResult) {
        this.mViewTag = i;
        this.mBarCode = barCodeScannerResult;
    }

    public short getCoalescingKey() {
        return (short) (this.mBarCode.getValue().hashCode() % 32767);
    }

    public String getEventName() {
        return CameraViewManager.Events.EVENT_ON_BAR_CODE_SCANNED.toString();
    }

    public Bundle getEventBody() {
        Bundle bundle = new Bundle();
        bundle.putInt(TouchesHelper.TARGET_KEY, this.mViewTag);
        bundle.putString("data", this.mBarCode.getValue());
        bundle.putInt("type", this.mBarCode.getType());
        return bundle;
    }
}
