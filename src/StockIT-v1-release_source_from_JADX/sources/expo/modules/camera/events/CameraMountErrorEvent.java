package expo.modules.camera.events;

import android.os.Bundle;
import androidx.core.util.Pools;
import com.onesignal.OneSignalDbContract;
import expo.modules.camera.CameraViewManager;
import org.unimodules.core.interfaces.services.EventEmitter;

public class CameraMountErrorEvent extends EventEmitter.BaseEvent {
    private static final Pools.SynchronizedPool<CameraMountErrorEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private String mMessage;

    private CameraMountErrorEvent() {
    }

    public static CameraMountErrorEvent obtain(String str) {
        CameraMountErrorEvent acquire = EVENTS_POOL.acquire();
        if (acquire == null) {
            acquire = new CameraMountErrorEvent();
        }
        acquire.init(str);
        return acquire;
    }

    private void init(String str) {
        this.mMessage = str;
    }

    public String getEventName() {
        return CameraViewManager.Events.EVENT_ON_MOUNT_ERROR.toString();
    }

    public Bundle getEventBody() {
        Bundle bundle = new Bundle();
        bundle.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, this.mMessage);
        return bundle;
    }
}
