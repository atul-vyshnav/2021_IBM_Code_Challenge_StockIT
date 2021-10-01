package org.unimodules.adapters.react.services;

import android.os.Bundle;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.core.interfaces.services.EventEmitter;

public class EventEmitterModule implements EventEmitter, InternalModule {
    private ReactContext mReactContext;

    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public EventEmitterModule(ReactContext reactContext) {
        this.mReactContext = reactContext;
    }

    public void emit(String str, Bundle bundle) {
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) this.mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, Arguments.fromBundle(bundle));
    }

    public void emit(int i, EventEmitter.Event event) {
        ((UIManagerModule) this.mReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(getReactEventFromEvent(i, event));
    }

    public void emit(int i, String str, Bundle bundle) {
        final String str2 = str;
        final int i2 = i;
        final Bundle bundle2 = bundle;
        ((UIManagerModule) this.mReactContext.getNativeModule(UIManagerModule.class)).getEventDispatcher().dispatchEvent(new Event(i) {
            public boolean canCoalesce() {
                return false;
            }

            public short getCoalescingKey() {
                return 0;
            }

            public String getEventName() {
                return str2;
            }

            public void dispatch(RCTEventEmitter rCTEventEmitter) {
                int i = i2;
                String str = str2;
                Bundle bundle = bundle2;
                rCTEventEmitter.receiveEvent(i, str, bundle != null ? Arguments.fromBundle(bundle) : null);
            }
        });
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(EventEmitter.class);
    }

    private static Event getReactEventFromEvent(final int i, final EventEmitter.Event event) {
        return new Event(i) {
            public String getEventName() {
                return event.getEventName();
            }

            public void dispatch(RCTEventEmitter rCTEventEmitter) {
                rCTEventEmitter.receiveEvent(i, event.getEventName(), Arguments.fromBundle(event.getEventBody()));
            }

            public boolean canCoalesce() {
                return event.canCoalesce();
            }

            public short getCoalescingKey() {
                return event.getCoalescingKey();
            }
        };
    }
}
