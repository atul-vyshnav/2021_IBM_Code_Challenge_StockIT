package expo.modules.camera.events;

import android.os.Bundle;
import androidx.core.util.Pools;
import expo.modules.camera.CameraViewManager;
import org.unimodules.core.interfaces.services.EventEmitter;

public class CameraReadyEvent extends EventEmitter.BaseEvent {
    private static final Pools.SynchronizedPool<CameraReadyEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);

    private CameraReadyEvent() {
    }

    public static CameraReadyEvent obtain() {
        CameraReadyEvent acquire = EVENTS_POOL.acquire();
        return acquire == null ? new CameraReadyEvent() : acquire;
    }

    public String getEventName() {
        return CameraViewManager.Events.EVENT_CAMERA_READY.toString();
    }

    public Bundle getEventBody() {
        return Bundle.EMPTY;
    }
}
