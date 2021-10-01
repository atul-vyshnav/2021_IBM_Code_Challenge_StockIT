package kotlin.reflect.jvm.internal.impl.types;

import kotlin.NoWhenBranchMatchedException;
import kotlin._Assertions;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRenderer;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker;
import kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeRefiner;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* compiled from: flexibleTypes.kt */
public final class FlexibleTypeImpl extends FlexibleType implements CustomTypeVariable {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    public static boolean RUN_SLOW_ASSERTIONS;
    private boolean assertionsDone;

    /* compiled from: flexibleTypes.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlexibleTypeImpl(SimpleType simpleType, SimpleType simpleType2) {
        super(simpleType, simpleType2);
        Intrinsics.checkNotNullParameter(simpleType, "lowerBound");
        Intrinsics.checkNotNullParameter(simpleType2, "upperBound");
    }

    private final void runAssertions() {
        if (RUN_SLOW_ASSERTIONS && !this.assertionsDone) {
            this.assertionsDone = true;
            boolean z = !FlexibleTypesKt.isFlexible(getLowerBound());
            if (!_Assertions.ENABLED || z) {
                boolean z2 = !FlexibleTypesKt.isFlexible(getUpperBound());
                if (!_Assertions.ENABLED || z2) {
                    boolean areEqual = true ^ Intrinsics.areEqual((Object) getLowerBound(), (Object) getUpperBound());
                    if (!_Assertions.ENABLED || areEqual) {
                        boolean isSubtypeOf = KotlinTypeChecker.DEFAULT.isSubtypeOf(getLowerBound(), getUpperBound());
                        if (_Assertions.ENABLED && !isSubtypeOf) {
                            throw new AssertionError("Lower bound " + getLowerBound() + " of a flexible type must be a subtype of the upper bound " + getUpperBound());
                        }
                        return;
                    }
                    throw new AssertionError("Lower and upper bounds are equal: " + getLowerBound() + " == " + getUpperBound());
                }
                throw new AssertionError(Intrinsics.stringPlus("Upper bound of a flexible type can not be flexible: ", getUpperBound()));
            }
            throw new AssertionError(Intrinsics.stringPlus("Lower bound of a flexible type can not be flexible: ", getLowerBound()));
        }
    }

    public SimpleType getDelegate() {
        runAssertions();
        return getLowerBound();
    }

    public boolean isTypeVariable() {
        return (getLowerBound().getConstructor().getDeclarationDescriptor() instanceof TypeParameterDescriptor) && Intrinsics.areEqual((Object) getLowerBound().getConstructor(), (Object) getUpperBound().getConstructor());
    }

    public KotlinType substitutionResult(KotlinType kotlinType) {
        UnwrappedType unwrappedType;
        Intrinsics.checkNotNullParameter(kotlinType, "replacement");
        UnwrappedType unwrap = kotlinType.unwrap();
        if (unwrap instanceof FlexibleType) {
            unwrappedType = unwrap;
        } else if (unwrap instanceof SimpleType) {
            KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
            SimpleType simpleType = (SimpleType) unwrap;
            unwrappedType = KotlinTypeFactory.flexibleType(simpleType, simpleType.makeNullableAsSpecified(true));
        } else {
            throw new NoWhenBranchMatchedException();
        }
        return TypeWithEnhancementKt.inheritEnhancement(unwrappedType, unwrap);
    }

    public UnwrappedType replaceAnnotations(Annotations annotations) {
        Intrinsics.checkNotNullParameter(annotations, "newAnnotations");
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.flexibleType(getLowerBound().replaceAnnotations(annotations), getUpperBound().replaceAnnotations(annotations));
    }

    public String render(DescriptorRenderer descriptorRenderer, DescriptorRendererOptions descriptorRendererOptions) {
        Intrinsics.checkNotNullParameter(descriptorRenderer, "renderer");
        Intrinsics.checkNotNullParameter(descriptorRendererOptions, "options");
        if (!descriptorRendererOptions.getDebugMode()) {
            return descriptorRenderer.renderFlexibleType(descriptorRenderer.renderType(getLowerBound()), descriptorRenderer.renderType(getUpperBound()), TypeUtilsKt.getBuiltIns(this));
        }
        return '(' + descriptorRenderer.renderType(getLowerBound()) + ".." + descriptorRenderer.renderType(getUpperBound()) + ')';
    }

    public UnwrappedType makeNullableAsSpecified(boolean z) {
        KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
        return KotlinTypeFactory.flexibleType(getLowerBound().makeNullableAsSpecified(z), getUpperBound().makeNullableAsSpecified(z));
    }

    public FlexibleType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new FlexibleTypeImpl((SimpleType) kotlinTypeRefiner.refineType(getLowerBound()), (SimpleType) kotlinTypeRefiner.refineType(getUpperBound()));
    }
}
