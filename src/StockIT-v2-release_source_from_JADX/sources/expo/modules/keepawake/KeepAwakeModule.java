package expo.modules.keepawake;

import android.content.Context;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.errors.CurrentActivityNotFoundException;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.services.KeepAwakeManager;

public class KeepAwakeModule extends ExportedModule {
    private static final String NAME = "ExpoKeepAwake";
    private static final String NO_ACTIVITY_ERROR_CODE = "NO_CURRENT_ACTIVITY";
    private KeepAwakeManager mKeepAwakeManager;

    public String getName() {
        return NAME;
    }

    public KeepAwakeModule(Context context) {
        super(context);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mKeepAwakeManager = (KeepAwakeManager) moduleRegistry.getModule(KeepAwakeManager.class);
    }

    @ExpoMethod
    public void activate(String str, Promise promise) {
        try {
            this.mKeepAwakeManager.activate(str, new Runnable() {
                public final void run() {
                    Promise.this.resolve(true);
                }
            });
        } catch (CurrentActivityNotFoundException unused) {
            promise.reject(NO_ACTIVITY_ERROR_CODE, "Unable to activate keep awake");
        }
    }

    @ExpoMethod
    public void deactivate(String str, Promise promise) {
        try {
            this.mKeepAwakeManager.deactivate(str, new Runnable() {
                public final void run() {
                    Promise.this.resolve(true);
                }
            });
        } catch (CurrentActivityNotFoundException unused) {
            promise.reject(NO_ACTIVITY_ERROR_CODE, "Unable to deactivate keep awake. However, it probably is deactivated already.");
        }
    }

    public boolean isActivated() {
        return this.mKeepAwakeManager.isActivated();
    }
}
