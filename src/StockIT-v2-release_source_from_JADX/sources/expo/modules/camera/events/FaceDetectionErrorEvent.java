package expo.modules.camera.events;

import android.os.Bundle;
import androidx.core.util.Pools;
import expo.modules.camera.CameraViewManager;
import org.unimodules.core.interfaces.services.EventEmitter;
import org.unimodules.interfaces.facedetector.FaceDetector;

public class FaceDetectionErrorEvent extends EventEmitter.BaseEvent {
    private static final Pools.SynchronizedPool<FaceDetectionErrorEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private FaceDetector mFaceDetector;

    public short getCoalescingKey() {
        return 0;
    }

    private FaceDetectionErrorEvent() {
    }

    public static FaceDetectionErrorEvent obtain(FaceDetector faceDetector) {
        FaceDetectionErrorEvent acquire = EVENTS_POOL.acquire();
        if (acquire == null) {
            acquire = new FaceDetectionErrorEvent();
        }
        acquire.init(faceDetector);
        return acquire;
    }

    private void init(FaceDetector faceDetector) {
        this.mFaceDetector = faceDetector;
    }

    public String getEventName() {
        return CameraViewManager.Events.EVENT_ON_MOUNT_ERROR.toString();
    }

    public Bundle getEventBody() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isOperational", isFaceDetectorOperational());
        return bundle;
    }

    private boolean isFaceDetectorOperational() {
        return this.mFaceDetector != null;
    }
}
