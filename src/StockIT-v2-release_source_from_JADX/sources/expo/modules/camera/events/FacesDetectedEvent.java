package expo.modules.camera.events;

import android.os.Bundle;
import androidx.core.util.Pools;
import com.facebook.react.uimanager.events.TouchesHelper;
import expo.modules.camera.CameraViewManager;
import java.util.List;
import kotlin.jvm.internal.ShortCompanionObject;
import org.unimodules.core.interfaces.services.EventEmitter;

public class FacesDetectedEvent extends EventEmitter.BaseEvent {
    private static final Pools.SynchronizedPool<FacesDetectedEvent> EVENTS_POOL = new Pools.SynchronizedPool<>(3);
    private List<Bundle> mFaces;
    private int mViewTag;

    private FacesDetectedEvent() {
    }

    public static FacesDetectedEvent obtain(int i, List<Bundle> list) {
        FacesDetectedEvent acquire = EVENTS_POOL.acquire();
        if (acquire == null) {
            acquire = new FacesDetectedEvent();
        }
        acquire.init(i, list);
        return acquire;
    }

    private void init(int i, List<Bundle> list) {
        this.mViewTag = i;
        this.mFaces = list;
    }

    public short getCoalescingKey() {
        if (this.mFaces.size() > 32767) {
            return ShortCompanionObject.MAX_VALUE;
        }
        return (short) this.mFaces.size();
    }

    public String getEventName() {
        return CameraViewManager.Events.EVENT_ON_FACES_DETECTED.toString();
    }

    public Bundle getEventBody() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "face");
        Bundle[] bundleArr = new Bundle[this.mFaces.size()];
        this.mFaces.toArray(bundleArr);
        bundle.putParcelableArray("faces", bundleArr);
        bundle.putInt(TouchesHelper.TARGET_KEY, this.mViewTag);
        return bundle;
    }
}
