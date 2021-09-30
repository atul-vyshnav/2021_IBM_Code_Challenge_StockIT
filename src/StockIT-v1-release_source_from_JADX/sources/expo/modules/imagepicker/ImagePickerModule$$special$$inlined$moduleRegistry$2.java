package expo.modules.imagepicker;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.services.UIManager;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0004\n\u0002\b\u0007\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u0006\b\u0000\u0010\u0001\u0018\u0001H\n¢\u0006\u0004\b\u0003\u0010\u0004¨\u0006\u0006"}, mo40253d2 = {"<anonymous>", "T", "kotlin.jvm.PlatformType", "invoke", "()Ljava/lang/Object;", "expo/modules/imagepicker/ModuleRegistryPropertyDelegate$getFromModuleRegistry$1", "expo/modules/imagepicker/ImagePickerModule$moduleRegistry$$inlined$getFromModuleRegistry$2"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: ModuleRegistryPropertyDelegate.kt */
public final class ImagePickerModule$$special$$inlined$moduleRegistry$2 extends Lambda implements Function0<UIManager> {
    final /* synthetic */ ModuleRegistryPropertyDelegate this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ImagePickerModule$$special$$inlined$moduleRegistry$2(ModuleRegistryPropertyDelegate moduleRegistryPropertyDelegate) {
        super(0);
        this.this$0 = moduleRegistryPropertyDelegate;
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [org.unimodules.core.interfaces.services.UIManager, java.lang.Object] */
    public final UIManager invoke() {
        ModuleRegistry moduleRegistry = this.this$0.getModuleRegistry();
        Intrinsics.checkNotNull(moduleRegistry);
        return moduleRegistry.getModule(UIManager.class);
    }
}
