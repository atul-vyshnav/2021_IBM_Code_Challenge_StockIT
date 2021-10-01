package expo.modules.keepawake;

import android.app.Activity;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.errors.CurrentActivityNotFoundException;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.core.interfaces.services.KeepAwakeManager;

public class ExpoKeepAwakeManager implements KeepAwakeManager, InternalModule {
    private ModuleRegistry mModuleRegistry;
    private Set<String> mTags = new HashSet();

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    private Activity getCurrentActivity() throws CurrentActivityNotFoundException {
        ActivityProvider activityProvider = (ActivityProvider) this.mModuleRegistry.getModule(ActivityProvider.class);
        if (activityProvider.getCurrentActivity() != null) {
            return activityProvider.getCurrentActivity();
        }
        throw new CurrentActivityNotFoundException();
    }

    public void activate(String str, Runnable runnable) throws CurrentActivityNotFoundException {
        Activity currentActivity = getCurrentActivity();
        if (!isActivated() && currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable(currentActivity) {
                public final /* synthetic */ Activity f$0;

                {
                    this.f$0 = r1;
                }

                public final void run() {
                    this.f$0.getWindow().addFlags(128);
                }
            });
        }
        this.mTags.add(str);
        runnable.run();
    }

    public void deactivate(String str, Runnable runnable) throws CurrentActivityNotFoundException {
        Activity currentActivity = getCurrentActivity();
        if (this.mTags.size() == 1 && this.mTags.contains(str) && currentActivity != null) {
            currentActivity.runOnUiThread(new Runnable(currentActivity) {
                public final /* synthetic */ Activity f$0;

                {
                    this.f$0 = r1;
                }

                public final void run() {
                    this.f$0.getWindow().clearFlags(128);
                }
            });
        }
        this.mTags.remove(str);
        runnable.run();
    }

    public boolean isActivated() {
        return this.mTags.size() > 0;
    }

    public List<? extends Class> getExportedInterfaces() {
        return Collections.singletonList(KeepAwakeManager.class);
    }
}
