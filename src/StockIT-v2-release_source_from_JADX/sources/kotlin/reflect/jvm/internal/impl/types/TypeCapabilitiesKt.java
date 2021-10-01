package kotlin.reflect.jvm.internal.impl.types;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: TypeCapabilities.kt */
public final class TypeCapabilitiesKt {
    public static final boolean isCustomTypeVariable(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrap = kotlinType.unwrap();
        CustomTypeVariable customTypeVariable = unwrap instanceof CustomTypeVariable ? (CustomTypeVariable) unwrap : null;
        if (customTypeVariable == null) {
            return false;
        }
        return customTypeVariable.isTypeVariable();
    }

    public static final CustomTypeVariable getCustomTypeVariable(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "<this>");
        UnwrappedType unwrap = kotlinType.unwrap();
        CustomTypeVariable customTypeVariable = unwrap instanceof CustomTypeVariable ? (CustomTypeVariable) unwrap : null;
        if (customTypeVariable != null && customTypeVariable.isTypeVariable()) {
            return customTypeVariable;
        }
        return null;
    }
}
