package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;

/* renamed from: kotlin.reflect.jvm.internal.impl.load.java.BuiltinMethodsWithSpecialGenericSignature$getOverriddenBuiltinFunctionWithErasedValueParametersInJava$1 */
/* compiled from: specialBuiltinMembers.kt */
final class C3203x3e04c33b extends Lambda implements Function1<CallableMemberDescriptor, Boolean> {
    final /* synthetic */ BuiltinMethodsWithSpecialGenericSignature this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3203x3e04c33b(BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature) {
        super(1);
        this.this$0 = builtinMethodsWithSpecialGenericSignature;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((CallableMemberDescriptor) obj));
    }

    public final boolean invoke(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "it");
        return this.this$0.getHasErasedValueParametersInJava(callableMemberDescriptor);
    }
}
