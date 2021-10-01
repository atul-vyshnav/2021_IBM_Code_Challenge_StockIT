package expo.modules.splashscreen;

import android.content.Context;
import expo.modules.splashscreen.singletons.SplashScreen;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.interfaces.SingletonModule;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0018\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007H\u0016¨\u0006\n"}, mo40253d2 = {"Lexpo/modules/splashscreen/SplashScreenPackage;", "Lorg/unimodules/core/BasePackage;", "()V", "createExportedModules", "", "Lorg/unimodules/core/ExportedModule;", "context", "Landroid/content/Context;", "createSingletonModules", "Lorg/unimodules/core/interfaces/SingletonModule;", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenPackage.kt */
public final class SplashScreenPackage extends BasePackage {
    public List<ExportedModule> createExportedModules(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return CollectionsKt.listOf(new SplashScreenModule(context));
    }

    public List<SingletonModule> createSingletonModules(Context context) {
        return CollectionsKt.listOf(SplashScreen.INSTANCE);
    }
}
