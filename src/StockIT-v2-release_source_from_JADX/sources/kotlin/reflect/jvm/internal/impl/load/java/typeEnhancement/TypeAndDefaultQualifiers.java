package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* compiled from: signatureEnhancement.kt */
final class TypeAndDefaultQualifiers {
    private final JavaDefaultQualifiers defaultQualifiers;
    private final boolean isFromStarProjection;
    private final KotlinType type;
    private final TypeParameterDescriptor typeParameterForArgument;

    public final KotlinType component1() {
        return this.type;
    }

    public final JavaDefaultQualifiers component2() {
        return this.defaultQualifiers;
    }

    public final TypeParameterDescriptor component3() {
        return this.typeParameterForArgument;
    }

    public final boolean component4() {
        return this.isFromStarProjection;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TypeAndDefaultQualifiers)) {
            return false;
        }
        TypeAndDefaultQualifiers typeAndDefaultQualifiers = (TypeAndDefaultQualifiers) obj;
        return Intrinsics.areEqual((Object) this.type, (Object) typeAndDefaultQualifiers.type) && Intrinsics.areEqual((Object) this.defaultQualifiers, (Object) typeAndDefaultQualifiers.defaultQualifiers) && Intrinsics.areEqual((Object) this.typeParameterForArgument, (Object) typeAndDefaultQualifiers.typeParameterForArgument) && this.isFromStarProjection == typeAndDefaultQualifiers.isFromStarProjection;
    }

    public int hashCode() {
        int hashCode = this.type.hashCode() * 31;
        JavaDefaultQualifiers javaDefaultQualifiers = this.defaultQualifiers;
        int i = 0;
        int hashCode2 = (hashCode + (javaDefaultQualifiers == null ? 0 : javaDefaultQualifiers.hashCode())) * 31;
        TypeParameterDescriptor typeParameterDescriptor = this.typeParameterForArgument;
        if (typeParameterDescriptor != null) {
            i = typeParameterDescriptor.hashCode();
        }
        int i2 = (hashCode2 + i) * 31;
        boolean z = this.isFromStarProjection;
        if (z) {
            z = true;
        }
        return i2 + (z ? 1 : 0);
    }

    public String toString() {
        return "TypeAndDefaultQualifiers(type=" + this.type + ", defaultQualifiers=" + this.defaultQualifiers + ", typeParameterForArgument=" + this.typeParameterForArgument + ", isFromStarProjection=" + this.isFromStarProjection + ')';
    }

    public TypeAndDefaultQualifiers(KotlinType kotlinType, JavaDefaultQualifiers javaDefaultQualifiers, TypeParameterDescriptor typeParameterDescriptor, boolean z) {
        Intrinsics.checkNotNullParameter(kotlinType, "type");
        this.type = kotlinType;
        this.defaultQualifiers = javaDefaultQualifiers;
        this.typeParameterForArgument = typeParameterDescriptor;
        this.isFromStarProjection = z;
    }

    public final KotlinType getType() {
        return this.type;
    }
}
