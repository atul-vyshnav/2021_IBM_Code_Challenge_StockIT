package expo.modules.imagepicker;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.ModuleRegistry;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001f\u0010\b\u001a\u0010\u0012\f\u0012\n \u000b*\u0004\u0018\u0001H\nH\n0\t\"\u0006\b\u0000\u0010\n\u0018\u0001H\bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0004R\"\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004@BX\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u000e"}, mo40253d2 = {"Lexpo/modules/imagepicker/ModuleRegistryPropertyDelegate;", "", "()V", "<set-?>", "Lorg/unimodules/core/ModuleRegistry;", "moduleRegistry", "getModuleRegistry", "()Lorg/unimodules/core/ModuleRegistry;", "getFromModuleRegistry", "Lkotlin/Lazy;", "T", "kotlin.jvm.PlatformType", "onCreate", "", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ModuleRegistryPropertyDelegate.kt */
public final class ModuleRegistryPropertyDelegate {
    /* access modifiers changed from: private */
    public ModuleRegistry moduleRegistry;

    public final ModuleRegistry getModuleRegistry() {
        return this.moduleRegistry;
    }

    public final void onCreate(ModuleRegistry moduleRegistry2) {
        Intrinsics.checkNotNullParameter(moduleRegistry2, "moduleRegistry");
        this.moduleRegistry = moduleRegistry2;
    }

    public final /* synthetic */ <T> Lazy<T> getFromModuleRegistry() {
        Intrinsics.needClassReification();
        return LazyKt.lazy(new ModuleRegistryPropertyDelegate$getFromModuleRegistry$1(this));
    }
}
