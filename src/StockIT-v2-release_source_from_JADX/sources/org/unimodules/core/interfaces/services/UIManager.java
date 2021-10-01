package org.unimodules.core.interfaces.services;

import android.view.View;
import org.unimodules.core.interfaces.ActivityEventListener;
import org.unimodules.core.interfaces.LifecycleEventListener;

public interface UIManager {

    public interface GroupUIBlock {
        void execute(ViewHolder viewHolder);
    }

    public interface UIBlock<T> {
        void reject(Throwable th);

        void resolve(T t);
    }

    public interface ViewHolder {
        View get(Object obj);
    }

    <T> void addUIBlock(int i, UIBlock<T> uIBlock, Class<T> cls);

    void addUIBlock(GroupUIBlock groupUIBlock);

    void registerActivityEventListener(ActivityEventListener activityEventListener);

    void registerLifecycleEventListener(LifecycleEventListener lifecycleEventListener);

    void runOnClientCodeQueueThread(Runnable runnable);

    void runOnUiQueueThread(Runnable runnable);

    void unregisterActivityEventListener(ActivityEventListener activityEventListener);

    void unregisterLifecycleEventListener(LifecycleEventListener lifecycleEventListener);
}
