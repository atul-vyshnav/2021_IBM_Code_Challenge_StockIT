package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt$computeConstructorTypeParameters$parametersFromContainingFunctions$2 */
/* compiled from: typeParameterUtils.kt */
final class C3182x246a49e3 extends Lambda implements Function1<DeclarationDescriptor, Boolean> {
    public static final C3182x246a49e3 INSTANCE = new C3182x246a49e3();

    C3182x246a49e3() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((DeclarationDescriptor) obj));
    }

    public final boolean invoke(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "it");
        return !(declarationDescriptor instanceof ConstructorDescriptor);
    }
}
