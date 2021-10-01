package kotlin.reflect.jvm.internal.impl.resolve.constants;

import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.FindClassInModuleKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor;
import kotlin.reflect.jvm.internal.impl.name.ClassId;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.types.ErrorUtils;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

/* compiled from: constantValues.kt */
public final class EnumValue extends ConstantValue<Pair<? extends ClassId, ? extends Name>> {
    private final ClassId enumClassId;
    private final Name enumEntryName;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public EnumValue(ClassId classId, Name name) {
        super(TuplesKt.m1160to(classId, name));
        Intrinsics.checkNotNullParameter(classId, "enumClassId");
        Intrinsics.checkNotNullParameter(name, "enumEntryName");
        this.enumClassId = classId;
        this.enumEntryName = name;
    }

    public final Name getEnumEntryName() {
        return this.enumEntryName;
    }

    public KotlinType getType(ModuleDescriptor moduleDescriptor) {
        Intrinsics.checkNotNullParameter(moduleDescriptor, "module");
        ClassDescriptor findClassAcrossModuleDependencies = FindClassInModuleKt.findClassAcrossModuleDependencies(moduleDescriptor, this.enumClassId);
        SimpleType simpleType = null;
        if (findClassAcrossModuleDependencies != null) {
            if (!DescriptorUtils.isEnumClass(findClassAcrossModuleDependencies)) {
                findClassAcrossModuleDependencies = null;
            }
            if (findClassAcrossModuleDependencies != null) {
                simpleType = findClassAcrossModuleDependencies.getDefaultType();
            }
        }
        if (simpleType != null) {
            return simpleType;
        }
        SimpleType createErrorType = ErrorUtils.createErrorType("Containing class for error-class based enum entry " + this.enumClassId + '.' + this.enumEntryName);
        Intrinsics.checkNotNullExpressionValue(createErrorType, "createErrorType(\"Containing class for error-class based enum entry $enumClassId.$enumEntryName\")");
        return createErrorType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.enumClassId.getShortClassName());
        sb.append('.');
        sb.append(this.enumEntryName);
        return sb.toString();
    }
}
