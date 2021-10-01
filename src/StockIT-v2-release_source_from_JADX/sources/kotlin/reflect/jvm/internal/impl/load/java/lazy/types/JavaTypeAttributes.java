package kotlin.reflect.jvm.internal.impl.load.java.lazy.types;

import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;

/* compiled from: JavaTypeResolver.kt */
public final class JavaTypeAttributes {
    private final JavaTypeFlexibility flexibility;
    private final TypeUsage howThisTypeIsUsed;
    private final boolean isForAnnotationParameter;
    private final TypeParameterDescriptor upperBoundOfTypeParameter;

    public static /* synthetic */ JavaTypeAttributes copy$default(JavaTypeAttributes javaTypeAttributes, TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z, TypeParameterDescriptor typeParameterDescriptor, int i, Object obj) {
        if ((i & 1) != 0) {
            typeUsage = javaTypeAttributes.howThisTypeIsUsed;
        }
        if ((i & 2) != 0) {
            javaTypeFlexibility = javaTypeAttributes.flexibility;
        }
        if ((i & 4) != 0) {
            z = javaTypeAttributes.isForAnnotationParameter;
        }
        if ((i & 8) != 0) {
            typeParameterDescriptor = javaTypeAttributes.upperBoundOfTypeParameter;
        }
        return javaTypeAttributes.copy(typeUsage, javaTypeFlexibility, z, typeParameterDescriptor);
    }

    public final JavaTypeAttributes copy(TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeUsage, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter(javaTypeFlexibility, "flexibility");
        return new JavaTypeAttributes(typeUsage, javaTypeFlexibility, z, typeParameterDescriptor);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof JavaTypeAttributes)) {
            return false;
        }
        JavaTypeAttributes javaTypeAttributes = (JavaTypeAttributes) obj;
        return this.howThisTypeIsUsed == javaTypeAttributes.howThisTypeIsUsed && this.flexibility == javaTypeAttributes.flexibility && this.isForAnnotationParameter == javaTypeAttributes.isForAnnotationParameter && Intrinsics.areEqual((Object) this.upperBoundOfTypeParameter, (Object) javaTypeAttributes.upperBoundOfTypeParameter);
    }

    public int hashCode() {
        int hashCode = ((this.howThisTypeIsUsed.hashCode() * 31) + this.flexibility.hashCode()) * 31;
        boolean z = this.isForAnnotationParameter;
        if (z) {
            z = true;
        }
        int i = (hashCode + (z ? 1 : 0)) * 31;
        TypeParameterDescriptor typeParameterDescriptor = this.upperBoundOfTypeParameter;
        return i + (typeParameterDescriptor == null ? 0 : typeParameterDescriptor.hashCode());
    }

    public String toString() {
        return "JavaTypeAttributes(howThisTypeIsUsed=" + this.howThisTypeIsUsed + ", flexibility=" + this.flexibility + ", isForAnnotationParameter=" + this.isForAnnotationParameter + ", upperBoundOfTypeParameter=" + this.upperBoundOfTypeParameter + ')';
    }

    public JavaTypeAttributes(TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z, TypeParameterDescriptor typeParameterDescriptor) {
        Intrinsics.checkNotNullParameter(typeUsage, "howThisTypeIsUsed");
        Intrinsics.checkNotNullParameter(javaTypeFlexibility, "flexibility");
        this.howThisTypeIsUsed = typeUsage;
        this.flexibility = javaTypeFlexibility;
        this.isForAnnotationParameter = z;
        this.upperBoundOfTypeParameter = typeParameterDescriptor;
    }

    public final TypeUsage getHowThisTypeIsUsed() {
        return this.howThisTypeIsUsed;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ JavaTypeAttributes(TypeUsage typeUsage, JavaTypeFlexibility javaTypeFlexibility, boolean z, TypeParameterDescriptor typeParameterDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeUsage, (i & 2) != 0 ? JavaTypeFlexibility.INFLEXIBLE : javaTypeFlexibility, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : typeParameterDescriptor);
    }

    public final JavaTypeFlexibility getFlexibility() {
        return this.flexibility;
    }

    public final boolean isForAnnotationParameter() {
        return this.isForAnnotationParameter;
    }

    public final TypeParameterDescriptor getUpperBoundOfTypeParameter() {
        return this.upperBoundOfTypeParameter;
    }

    public final JavaTypeAttributes withFlexibility(JavaTypeFlexibility javaTypeFlexibility) {
        Intrinsics.checkNotNullParameter(javaTypeFlexibility, "flexibility");
        return copy$default(this, (TypeUsage) null, javaTypeFlexibility, false, (TypeParameterDescriptor) null, 13, (Object) null);
    }
}
