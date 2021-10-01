package kotlin.reflect.jvm.internal.impl.types;

import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.types.checker.NewCapturedType;
import kotlin.reflect.jvm.internal.impl.types.checker.NewTypeVariableConstructor;
import kotlin.reflect.jvm.internal.impl.types.checker.NullabilityChecker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;

/* compiled from: SpecialTypes.kt */
public final class DefinitelyNotNullType extends DelegatingSimpleType implements CustomTypeVariable, DefinitelyNotNullTypeMarker {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final SimpleType original;
    private final boolean useCorrectedNullabilityForTypeParameters;

    public /* synthetic */ DefinitelyNotNullType(SimpleType simpleType, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(simpleType, z);
    }

    public boolean isMarkedNullable() {
        return false;
    }

    public final SimpleType getOriginal() {
        return this.original;
    }

    private DefinitelyNotNullType(SimpleType simpleType, boolean z) {
        this.original = simpleType;
        this.useCorrectedNullabilityForTypeParameters = z;
    }

    /* compiled from: SpecialTypes.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DefinitelyNotNullType makeDefinitelyNotNull$descriptors(UnwrappedType unwrappedType, boolean z) {
            Intrinsics.checkNotNullParameter(unwrappedType, "type");
            if (unwrappedType instanceof DefinitelyNotNullType) {
                return (DefinitelyNotNullType) unwrappedType;
            }
            if (!makesSenseToBeDefinitelyNotNull(unwrappedType, z)) {
                return null;
            }
            if (unwrappedType instanceof FlexibleType) {
                FlexibleType flexibleType = (FlexibleType) unwrappedType;
                boolean areEqual = Intrinsics.areEqual((Object) flexibleType.getLowerBound().getConstructor(), (Object) flexibleType.getUpperBound().getConstructor());
                if (_Assertions.ENABLED && !areEqual) {
                    throw new AssertionError("DefinitelyNotNullType for flexible type (" + unwrappedType + ") can be created only from type variable with the same constructor for bounds");
                }
            }
            return new DefinitelyNotNullType(FlexibleTypesKt.lowerIfFlexible(unwrappedType), z, (DefaultConstructorMarker) null);
        }

        private final boolean makesSenseToBeDefinitelyNotNull(UnwrappedType unwrappedType, boolean z) {
            if (!canHaveUndefinedNullability(unwrappedType)) {
                return false;
            }
            if (!z || !(unwrappedType.getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor)) {
                return !NullabilityChecker.INSTANCE.isSubtypeOfAny(unwrappedType);
            }
            return TypeUtils.isNullableType(unwrappedType);
        }

        private final boolean canHaveUndefinedNullability(UnwrappedType unwrappedType) {
            return (unwrappedType.getConstructor() instanceof NewTypeVariableConstructor) || (unwrappedType.getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor) || (unwrappedType instanceof NewCapturedType);
        }
    }

    /* access modifiers changed from: protected */
    public SimpleType getDelegate() {
        return this.original;
    }

    public boolean isTypeVariable() {
        return (getDelegate().getConstructor() instanceof NewTypeVariableConstructor) || (getDelegate().getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor);
    }

    public KotlinType substitutionResult(KotlinType kotlinType) {
        Intrinsics.checkNotNullParameter(kotlinType, "replacement");
        return SpecialTypesKt.makeDefinitelyNotNullOrNotNull(kotlinType.unwrap(), this.useCorrectedNullabilityForTypeParameters);
    }

    public DefinitelyNotNullType replaceAnnotations(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "newAnnotations");
        return new DefinitelyNotNullType(getDelegate().replaceAnnotations(annotations), this.useCorrectedNullabilityForTypeParameters);
    }

    public SimpleType makeNullableAsSpecified(boolean z) {
        return z ? getDelegate().makeNullableAsSpecified(z) : this;
    }

    public String toString() {
        return getDelegate() + "!!";
    }

    public DefinitelyNotNullType replaceDelegate(SimpleType simpleType) {
        Intrinsics.checkNotNullParameter(simpleType, "delegate");
        return new DefinitelyNotNullType(simpleType, this.useCorrectedNullabilityForTypeParameters);
    }
}
