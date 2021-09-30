package kotlin.reflect.jvm.internal.impl.resolve;

import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;

/* renamed from: kotlin.reflect.jvm.internal.impl.resolve.DescriptorEquivalenceForOverrides$areCallableDescriptorsEquivalent$overridingUtil$1 */
/* compiled from: DescriptorEquivalenceForOverrides.kt */
final class C3324x10bda471 implements KotlinTypeChecker.TypeConstructorEquality {

    /* renamed from: $a */
    final /* synthetic */ CallableDescriptor f1288$a;
    final /* synthetic */ boolean $allowCopiesFromTheSameDeclaration;

    /* renamed from: $b */
    final /* synthetic */ CallableDescriptor f1289$b;
    final /* synthetic */ DescriptorEquivalenceForOverrides this$0;

    C3324x10bda471(DescriptorEquivalenceForOverrides descriptorEquivalenceForOverrides, boolean z, CallableDescriptor callableDescriptor, CallableDescriptor callableDescriptor2) {
        this.this$0 = descriptorEquivalenceForOverrides;
        this.$allowCopiesFromTheSameDeclaration = z;
        this.f1288$a = callableDescriptor;
        this.f1289$b = callableDescriptor2;
    }

    public final boolean equals(TypeConstructor typeConstructor, TypeConstructor typeConstructor2) {
        Intrinsics.checkNotNullParameter(typeConstructor, "c1");
        Intrinsics.checkNotNullParameter(typeConstructor2, "c2");
        if (Intrinsics.areEqual((Object) typeConstructor, (Object) typeConstructor2)) {
            return true;
        }
        ClassifierDescriptor declarationDescriptor = typeConstructor.getDeclarationDescriptor();
        ClassifierDescriptor declarationDescriptor2 = typeConstructor2.getDeclarationDescriptor();
        if (!(declarationDescriptor instanceof TypeParameterDescriptor) || !(declarationDescriptor2 instanceof TypeParameterDescriptor)) {
            return false;
        }
        boolean z = this.$allowCopiesFromTheSameDeclaration;
        final CallableDescriptor callableDescriptor = this.f1288$a;
        final CallableDescriptor callableDescriptor2 = this.f1289$b;
        return this.this$0.areTypeParametersEquivalent((TypeParameterDescriptor) declarationDescriptor, (TypeParameterDescriptor) declarationDescriptor2, z, new Function2<DeclarationDescriptor, DeclarationDescriptor, Boolean>() {
            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return Boolean.valueOf(invoke((DeclarationDescriptor) obj, (DeclarationDescriptor) obj2));
            }

            public final boolean invoke(DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2) {
                return Intrinsics.areEqual((Object) declarationDescriptor, (Object) callableDescriptor) && Intrinsics.areEqual((Object) declarationDescriptor2, (Object) callableDescriptor2);
            }
        });
    }
}
