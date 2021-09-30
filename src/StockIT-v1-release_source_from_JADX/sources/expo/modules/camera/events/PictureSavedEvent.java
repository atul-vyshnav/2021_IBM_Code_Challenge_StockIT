package expo.modules.camera.events;

import android.os.Bundle;
import androidx.core.util.Pools;
import expo.modules.camera.CameraViewManager;
import org.unimodules.core.interfaces.services.EventEmitter;

public class PictureSavedEvent extends EventEmitter.BaseEvent {
    private static final Pools.SynchronizedPool<PictureSavedEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private Bundle mResponse;

    private PictureSavedEvent() {
    }

    public static PictureSavedEvent obtain(Bundle bundle) {
        PictureSavedEvent acquire = EVENTS_POOL.acquire();
        if (acquire == null) {
            acquire = new PictureSavedEvent();
        }
        acquire.init(bundle);
        return acquire;
    }

    private void init(Bundle bundle) {
        this.mResponse = bundle;
    }

    public short getCoalescingKey() {
        String string;
        Bundle bundle = this.mResponse.getBundle("data");
        if (bundle == null || !bundle.containsKey("uri") || (string = bundle.getString("uri")) == null) {
            return -1;
        }
        return (short) (string.hashCode() % 32767);
    }

    public String getEventName() {
        return CameraViewManager.Events.EVENT_ON_PICTURE_SAVED.toString();
    }

    public Bundle getEventBody() {
        return this.mResponse;
    }
}
