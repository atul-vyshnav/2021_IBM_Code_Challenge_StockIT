package kotlin.reflect.jvm.internal.impl.load.java;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.SpecialGenericSignatures;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;

/* compiled from: specialBuiltinMembers.kt */
public final class BuiltinMethodsWithSpecialGenericSignature extends SpecialGenericSignatures {
    public static final BuiltinMethodsWithSpecialGenericSignature INSTANCE = new BuiltinMethodsWithSpecialGenericSignature();

    private BuiltinMethodsWithSpecialGenericSignature() {
    }

    /* access modifiers changed from: private */
    public final boolean getHasErasedValueParametersInJava(CallableMemberDescriptor callableMemberDescriptor) {
        return CollectionsKt.contains(SpecialGenericSignatures.Companion.getERASED_VALUE_PARAMETERS_SIGNATURES(), MethodSignatureMappingKt.computeJvmSignature(callableMemberDescriptor));
    }

    @JvmStatic
    public static final FunctionDescriptor getOverriddenBuiltinFunctionWithErasedValueParametersInJava(FunctionDescriptor functionDescriptor) {
        Intrinsics.checkNotNullParameter(functionDescriptor, "functionDescriptor");
        BuiltinMethodsWithSpecialGenericSignature builtinMethodsWithSpecialGenericSignature = INSTANCE;
        Name name = functionDescriptor.getName();
        Intrinsics.checkNotNullExpressionValue(name, "functionDescriptor.name");
        if (!builtinMethodsWithSpecialGenericSignature.getSameAsBuiltinMethodWithErasedValueParameters(name)) {
            return null;
        }
        return (FunctionDescriptor) DescriptorUtilsKt.firstOverridden$default(functionDescriptor, false, new C3203x3e04c33b(INSTANCE), 1, (Object) null);
    }

    public final boolean getSameAsBuiltinMethodWithErasedValueParameters(Name name) {
        Intrinsics.checkNotNullParameter(name, "<this>");
        return SpecialGenericSignatures.Companion.getERASED_VALUE_PARAMETERS_SHORT_NAMES().contains(name);
    }

    @JvmStatic
    public static final SpecialGenericSignatures.SpecialSignatureInfo getSpecialSignatureInfo(CallableMemberDescriptor callableMemberDescriptor) {
        Intrinsics.checkNotNullParameter(callableMemberDescriptor, "<this>");
        if (!SpecialGenericSignatures.Companion.getERASED_VALUE_PARAMETERS_SHORT_NAMES().contains(callableMemberDescriptor.getName())) {
            return null;
        }
        CallableMemberDescriptor firstOverridden$default = DescriptorUtilsKt.firstOverridden$default(callableMemberDescriptor, false, new C3204xdb572182(INSTANCE), 1, (Object) null);
        String computeJvmSignature = firstOverridden$default == null ? null : MethodSignatureMappingKt.computeJvmSignature(firstOverridden$default);
        if (computeJvmSignature == null) {
            return null;
        }
        return SpecialGenericSignatures.Companion.getSpecialSignatureInfo(computeJvmSignature);
    }
}
