package kotlin.reflect.jvm.internal.impl.descriptors;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* renamed from: kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterUtilsKt$computeConstructorTypeParameters$parametersFromContainingFunctions$1 */
/* compiled from: typeParameterUtils.kt */
final class C3181x246a49e2 extends Lambda implements Function1<DeclarationDescriptor, Boolean> {
    public static final C3181x246a49e2 INSTANCE = new C3181x246a49e2();

    C3181x246a49e2() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((DeclarationDescriptor) obj));
    }

    public final boolean invoke(DeclarationDescriptor declarationDescriptor) {
        Intrinsics.checkNotNullParameter(declarationDescriptor, "it");
        return declarationDescriptor instanceof CallableDescriptor;
    }
}
