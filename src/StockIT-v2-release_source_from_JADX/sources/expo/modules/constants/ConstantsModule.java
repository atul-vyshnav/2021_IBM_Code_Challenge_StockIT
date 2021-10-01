package expo.modules.constants;

import android.content.Context;
import java.util.Map;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.interfaces.constants.ConstantsInterface;

public class ConstantsModule extends ExportedModule {
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return "ExponentConstants";
    }

    public ConstantsModule(Context context) {
        super(context);
    }

    public Map<String, Object> getConstants() {
        return ((ConstantsInterface) this.mModuleRegistry.getModule(ConstantsInterface.class)).getConstants();
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    @ExpoMethod
    public void getWebViewUserAgentAsync(Promise promise) {
        promise.resolve(System.getProperty("http.agent"));
    }
}
