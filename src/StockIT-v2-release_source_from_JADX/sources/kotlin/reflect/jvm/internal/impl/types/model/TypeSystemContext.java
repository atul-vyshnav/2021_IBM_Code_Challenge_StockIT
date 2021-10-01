package kotlin.reflect.jvm.internal.impl.types.model;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemOptimizationContext;

/* compiled from: TypeSystemContext.kt */
public interface TypeSystemContext extends TypeSystemOptimizationContext {
    boolean areEqualTypeConstructors(TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2);

    int argumentsCount(KotlinTypeMarker kotlinTypeMarker);

    TypeArgumentListMarker asArgumentList(SimpleTypeMarker simpleTypeMarker);

    CapturedTypeMarker asCapturedType(SimpleTypeMarker simpleTypeMarker);

    DefinitelyNotNullTypeMarker asDefinitelyNotNullType(SimpleTypeMarker simpleTypeMarker);

    DynamicTypeMarker asDynamicType(FlexibleTypeMarker flexibleTypeMarker);

    FlexibleTypeMarker asFlexibleType(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker);

    TypeArgumentMarker asTypeArgument(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker captureFromArguments(SimpleTypeMarker simpleTypeMarker, CaptureStatus captureStatus);

    TypeArgumentMarker get(TypeArgumentListMarker typeArgumentListMarker, int i);

    TypeArgumentMarker getArgument(KotlinTypeMarker kotlinTypeMarker, int i);

    TypeParameterMarker getParameter(TypeConstructorMarker typeConstructorMarker, int i);

    KotlinTypeMarker getType(TypeArgumentMarker typeArgumentMarker);

    TypeVariance getVariance(TypeArgumentMarker typeArgumentMarker);

    TypeVariance getVariance(TypeParameterMarker typeParameterMarker);

    KotlinTypeMarker intersectTypes(List<? extends KotlinTypeMarker> list);

    boolean isAnyConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isClassTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isCommonFinalClassConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isDenotable(TypeConstructorMarker typeConstructorMarker);

    boolean isError(KotlinTypeMarker kotlinTypeMarker);

    boolean isIntegerLiteralTypeConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isIntersection(TypeConstructorMarker typeConstructorMarker);

    boolean isMarkedNullable(KotlinTypeMarker kotlinTypeMarker);

    boolean isMarkedNullable(SimpleTypeMarker simpleTypeMarker);

    boolean isNothingConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isNullableType(KotlinTypeMarker kotlinTypeMarker);

    boolean isPrimitiveType(SimpleTypeMarker simpleTypeMarker);

    boolean isProjectionNotNull(CapturedTypeMarker capturedTypeMarker);

    boolean isSingleClassifierType(SimpleTypeMarker simpleTypeMarker);

    boolean isStarProjection(TypeArgumentMarker typeArgumentMarker);

    boolean isStubType(SimpleTypeMarker simpleTypeMarker);

    SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker);

    SimpleTypeMarker lowerBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker lowerType(CapturedTypeMarker capturedTypeMarker);

    KotlinTypeMarker makeDefinitelyNotNullOrNotNull(KotlinTypeMarker kotlinTypeMarker);

    SimpleTypeMarker original(DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker);

    int parametersCount(TypeConstructorMarker typeConstructorMarker);

    Collection<KotlinTypeMarker> possibleIntegerTypes(SimpleTypeMarker simpleTypeMarker);

    int size(TypeArgumentListMarker typeArgumentListMarker);

    Collection<KotlinTypeMarker> supertypes(TypeConstructorMarker typeConstructorMarker);

    TypeConstructorMarker typeConstructor(KotlinTypeMarker kotlinTypeMarker);

    TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker);

    SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker);

    SimpleTypeMarker upperBoundIfFlexible(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker withNullability(KotlinTypeMarker kotlinTypeMarker, boolean z);

    SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z);

    /* compiled from: TypeSystemContext.kt */
    public static final class DefaultImpls {
        public static List<SimpleTypeMarker> fastCorrespondingSupertypes(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "constructor");
            return null;
        }

        public static boolean identicalArguments(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
            return TypeSystemOptimizationContext.DefaultImpls.identicalArguments(typeSystemContext, simpleTypeMarker, simpleTypeMarker2);
        }

        public static boolean isMarkedNullable(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            return (kotlinTypeMarker instanceof SimpleTypeMarker) && typeSystemContext.isMarkedNullable((SimpleTypeMarker) kotlinTypeMarker);
        }

        public static TypeArgumentMarker getArgumentOrNull(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker, int i) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            boolean z = false;
            if (i >= 0 && i < typeSystemContext.argumentsCount(simpleTypeMarker)) {
                z = true;
            }
            if (z) {
                return typeSystemContext.getArgument(simpleTypeMarker, i);
            }
            return null;
        }

        public static SimpleTypeMarker lowerBoundIfFlexible(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            FlexibleTypeMarker asFlexibleType = typeSystemContext.asFlexibleType(kotlinTypeMarker);
            if (asFlexibleType != null) {
                return typeSystemContext.lowerBound(asFlexibleType);
            }
            SimpleTypeMarker asSimpleType = typeSystemContext.asSimpleType(kotlinTypeMarker);
            Intrinsics.checkNotNull(asSimpleType);
            return asSimpleType;
        }

        public static SimpleTypeMarker upperBoundIfFlexible(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            FlexibleTypeMarker asFlexibleType = typeSystemContext.asFlexibleType(kotlinTypeMarker);
            if (asFlexibleType != null) {
                return typeSystemContext.upperBound(asFlexibleType);
            }
            SimpleTypeMarker asSimpleType = typeSystemContext.asSimpleType(kotlinTypeMarker);
            Intrinsics.checkNotNull(asSimpleType);
            return asSimpleType;
        }

        public static boolean isDynamic(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            FlexibleTypeMarker asFlexibleType = typeSystemContext.asFlexibleType(kotlinTypeMarker);
            return (asFlexibleType == null ? null : typeSystemContext.asDynamicType(asFlexibleType)) != null;
        }

        public static boolean isDefinitelyNotNullType(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            SimpleTypeMarker asSimpleType = typeSystemContext.asSimpleType(kotlinTypeMarker);
            return (asSimpleType == null ? null : typeSystemContext.asDefinitelyNotNullType(asSimpleType)) != null;
        }

        public static boolean hasFlexibleNullability(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            return typeSystemContext.isMarkedNullable(typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarker)) != typeSystemContext.isMarkedNullable(typeSystemContext.upperBoundIfFlexible(kotlinTypeMarker));
        }

        public static TypeConstructorMarker typeConstructor(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            SimpleTypeMarker asSimpleType = typeSystemContext.asSimpleType(kotlinTypeMarker);
            if (asSimpleType == null) {
                asSimpleType = typeSystemContext.lowerBoundIfFlexible(kotlinTypeMarker);
            }
            return typeSystemContext.typeConstructor(asSimpleType);
        }

        public static boolean isNothing(TypeSystemContext typeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            return typeSystemContext.isNothingConstructor(typeSystemContext.typeConstructor(kotlinTypeMarker)) && !typeSystemContext.isNullableType(kotlinTypeMarker);
        }

        public static boolean isClassType(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            return typeSystemContext.isClassTypeConstructor(typeSystemContext.typeConstructor(simpleTypeMarker));
        }

        public static boolean isIntegerLiteralType(TypeSystemContext typeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            return typeSystemContext.isIntegerLiteralTypeConstructor(typeSystemContext.typeConstructor(simpleTypeMarker));
        }

        public static TypeArgumentMarker get(TypeSystemContext typeSystemContext, TypeArgumentListMarker typeArgumentListMarker, int i) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeArgumentListMarker, "receiver");
            if (typeArgumentListMarker instanceof SimpleTypeMarker) {
                return typeSystemContext.getArgument((KotlinTypeMarker) typeArgumentListMarker, i);
            }
            if (typeArgumentListMarker instanceof ArgumentList) {
                Object obj = ((ArgumentList) typeArgumentListMarker).get(i);
                Intrinsics.checkNotNullExpressionValue(obj, "get(index)");
                return (TypeArgumentMarker) obj;
            }
            throw new IllegalStateException(("unknown type argument list type: " + typeArgumentListMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentListMarker.getClass())).toString());
        }

        public static int size(TypeSystemContext typeSystemContext, TypeArgumentListMarker typeArgumentListMarker) {
            Intrinsics.checkNotNullParameter(typeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeArgumentListMarker, "receiver");
            if (typeArgumentListMarker instanceof SimpleTypeMarker) {
                return typeSystemContext.argumentsCount((KotlinTypeMarker) typeArgumentListMarker);
            }
            if (typeArgumentListMarker instanceof ArgumentList) {
                return ((ArgumentList) typeArgumentListMarker).size();
            }
            throw new IllegalStateException(("unknown type argument list type: " + typeArgumentListMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentListMarker.getClass())).toString());
        }
    }
}
