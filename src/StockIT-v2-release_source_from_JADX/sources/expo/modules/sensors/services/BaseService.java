package expo.modules.sensors.services;

import android.content.Context;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.core.interfaces.services.UIManager;

abstract class BaseService implements LifecycleEventListener, RegistryLifecycleListener {
    private Context mContext;
    private boolean mIsForegrounded = false;
    private ModuleRegistry mModuleRegistry;

    public abstract void onExperienceBackgrounded();

    public abstract void onExperienceForegrounded();

    public void onHostDestroy() {
    }

    BaseService(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: protected */
    public Context getContext() {
        return this.mContext;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
        if (moduleRegistry != null && moduleRegistry.getModule(UIManager.class) != null) {
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).registerLifecycleEventListener(this);
        }
    }

    public void onDestroy() {
        ModuleRegistry moduleRegistry = this.mModuleRegistry;
        if (moduleRegistry != null && moduleRegistry.getModule(UIManager.class) != null) {
            ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).unregisterLifecycleEventListener(this);
        }
    }

    public void onHostResume() {
        this.mIsForegrounded = true;
        onExperienceForegrounded();
    }

    public void onHostPause() {
        this.mIsForegrounded = false;
        onExperienceBackgrounded();
    }

    /* access modifiers changed from: package-private */
    public boolean getExperienceIsForegrounded() {
        return this.mIsForegrounded;
    }
}
