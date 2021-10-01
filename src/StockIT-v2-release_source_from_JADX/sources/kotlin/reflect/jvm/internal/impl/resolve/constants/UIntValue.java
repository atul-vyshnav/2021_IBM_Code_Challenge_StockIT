package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

/* compiled from: constantValues.kt */
public final class UIntValue extends UnsignedValueConstant<Integer> {
    public UIntValue(int i) {
        super(Integer.valueOf(i));
    }

    public KotlinType getType(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "module");
        ClassDescriptor findClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(moduleDescriptor, StandardNames.FqNames.uInt);
        SimpleType defaultType = findClassAcrossModuleDependencies == null ? null : findClassAcrossModuleDependencies.getDefaultType();
        if (defaultType != null) {
            return defaultType;
        }
        SimpleType createErrorType = ErrorUtils.createErrorType("Unsigned type UInt not found");
        Intrinsics.checkNotNullExpressionValue(createErrorType, "createErrorType(\"Unsigned type UInt not found\")");
        return createErrorType;
    }

    public String toString() {
        return ((Number) getValue()).intValue() + ".toUInt()";
    }
}
