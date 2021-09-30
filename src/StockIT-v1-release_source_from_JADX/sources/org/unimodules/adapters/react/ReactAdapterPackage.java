package org.unimodules.adapters.react;

import android.content.Context;
import com.facebook.react.bridge.ReactContext;
import java.util.Arrays;
import java.util.List;
import org.unimodules.adapters.react.permissions.PermissionsService;
import org.unimodules.adapters.react.services.CookieManagerModule;
import org.unimodules.adapters.react.services.EventEmitterModule;
import org.unimodules.adapters.react.services.FontManagerModule;
import org.unimodules.adapters.react.services.RuntimeEnvironmentModule;
import org.unimodules.adapters.react.services.UIManagerModuleWrapper;
import org.unimodules.core.BasePackage;
import org.unimodules.core.interfaces.InternalModule;

public class ReactAdapterPackage extends BasePackage {
    public List<InternalModule> createInternalModules(Context context) {
        ReactContext reactContext = (ReactContext) context;
        return Arrays.asList(new InternalModule[]{new CookieManagerModule(reactContext), new UIManagerModuleWrapper(reactContext), new EventEmitterModule(reactContext), new FontManagerModule(), new RuntimeEnvironmentModule(), new PermissionsService(reactContext)});
    }
}
