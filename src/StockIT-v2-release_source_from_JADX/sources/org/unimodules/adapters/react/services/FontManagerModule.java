package org.unimodules.adapters.react.services;

import android.graphics.Typeface;
import com.facebook.react.views.text.ReactFontManager;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.interfaces.font.FontManager;

public class FontManagerModule implements FontManager, InternalModule {
    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(FontManager.class);
    }

    public void setTypeface(String str, int i, Typeface typeface) {
        ReactFontManager.getInstance().setTypeface(str, i, typeface);
    }
}
