package expo.modules.imagepicker;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u001a#\u0010\u0000\u001a\u0010\u0012\f\u0012\n \u0003*\u0004\u0018\u0001H\u0002H\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0004H\b¨\u0006\u0005"}, mo40253d2 = {"moduleRegistry", "Lkotlin/Lazy;", "T", "kotlin.jvm.PlatformType", "Lexpo/modules/imagepicker/ImagePickerModule;", "expo-image-picker_release"}, mo40254k = 2, mo40255mv = {1, 4, 2})
/* compiled from: ModuleRegistryPropertyDelegate.kt */
public final class ModuleRegistryPropertyDelegateKt {
    public static final /* synthetic */ <T> Lazy<T> moduleRegistry(ImagePickerModule imagePickerModule) {
        Intrinsics.checkNotNullParameter(imagePickerModule, "$this$moduleRegistry");
        ModuleRegistryPropertyDelegate moduleRegistryPropertyDelegate = imagePickerModule.getModuleRegistryPropertyDelegate();
        Intrinsics.needClassReification();
        return LazyKt.lazy(new C2999x5459f21c(moduleRegistryPropertyDelegate));
    }
}
