package org.unimodules.adapters.react.services;

import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.network.ForwardingCookieHandler;
import java.net.CookieHandler;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;

public class CookieManagerModule extends ForwardingCookieHandler implements InternalModule, NativeModule {
    private static final String TAG = "CookieManagerModule";

    public boolean canOverrideExistingModule() {
        return false;
    }

    public String getName() {
        return null;
    }

    public void initialize() {
    }

    public void onCatalystInstanceDestroy() {
    }

    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public CookieManagerModule(ReactContext reactContext) {
        super(reactContext);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(CookieHandler.class);
    }
}
