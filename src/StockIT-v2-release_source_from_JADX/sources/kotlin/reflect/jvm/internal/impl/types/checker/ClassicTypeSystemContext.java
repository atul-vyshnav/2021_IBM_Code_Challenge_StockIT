package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Collection;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.builtins.PrimitiveType;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ModalityUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.FqNameUnsafe;
import kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedType;
import kotlin.reflect.jvm.internal.impl.resolve.constants.IntegerLiteralTypeConstructor;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.AbstractStubType;
import kotlin.reflect.jvm.internal.impl.types.AbstractTypeCheckerContext;
import kotlin.reflect.jvm.internal.impl.types.DefinitelyNotNullType;
import kotlin.reflect.jvm.internal.impl.types.DynamicType;
import kotlin.reflect.jvm.internal.impl.types.FlexibleType;
import kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeFactory;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;
import kotlin.reflect.jvm.internal.impl.types.TypeConstructor;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.Variance;
import kotlin.reflect.jvm.internal.impl.types.model.CaptureStatus;
import kotlin.reflect.jvm.internal.impl.types.model.CapturedTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DefinitelyNotNullTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.DynamicTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.FlexibleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.SimpleTypeMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentListMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeArgumentMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeConstructorMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeParameterMarker;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemContextKt;
import kotlin.reflect.jvm.internal.impl.types.model.TypeSystemInferenceExtensionContext;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariance;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;

/* compiled from: ClassicTypeSystemContext.kt */
public interface ClassicTypeSystemContext extends TypeSystemCommonBackendContext, TypeSystemInferenceExtensionContext {
    SimpleTypeMarker asSimpleType(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker createFlexibleType(SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2);

    SimpleTypeMarker lowerBound(FlexibleTypeMarker flexibleTypeMarker);

    TypeConstructorMarker typeConstructor(SimpleTypeMarker simpleTypeMarker);

    SimpleTypeMarker upperBound(FlexibleTypeMarker flexibleTypeMarker);

    SimpleTypeMarker withNullability(SimpleTypeMarker simpleTypeMarker, boolean z);

    /* compiled from: ClassicTypeSystemContext.kt */
    public static final class DefaultImpls {
        public static TypeArgumentMarker get(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentListMarker typeArgumentListMarker, int i) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.get(classicTypeSystemContext, typeArgumentListMarker, i);
        }

        public static boolean isMarkedNullable(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.isMarkedNullable(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static SimpleTypeMarker lowerBoundIfFlexible(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.lowerBoundIfFlexible(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static KotlinTypeMarker makeNullable(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemCommonBackendContext.DefaultImpls.makeNullable(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static int size(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentListMarker typeArgumentListMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.size(classicTypeSystemContext, typeArgumentListMarker);
        }

        public static TypeConstructorMarker typeConstructor(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.typeConstructor(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static SimpleTypeMarker upperBoundIfFlexible(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            return TypeSystemInferenceExtensionContext.DefaultImpls.upperBoundIfFlexible(classicTypeSystemContext, kotlinTypeMarker);
        }

        public static boolean isDenotable(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                return ((TypeConstructor) typeConstructorMarker).isDenotable();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static boolean isIntegerLiteralTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                return typeConstructorMarker instanceof IntegerLiteralTypeConstructor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static Collection<KotlinTypeMarker> possibleIntegerTypes(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            TypeConstructorMarker typeConstructor = classicTypeSystemContext.typeConstructor(simpleTypeMarker);
            if (typeConstructor instanceof IntegerLiteralTypeConstructor) {
                return ((IntegerLiteralTypeConstructor) typeConstructor).getPossibleTypes();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static SimpleTypeMarker withNullability(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker, boolean z) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                return ((SimpleType) simpleTypeMarker).makeNullableAsSpecified(z);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static boolean isError(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                return KotlinTypeKt.isError((KotlinType) kotlinTypeMarker);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static boolean isStubType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                return simpleTypeMarker instanceof AbstractStubType;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static KotlinTypeMarker lowerType(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "receiver");
            if (capturedTypeMarker instanceof NewCapturedType) {
                return ((NewCapturedType) capturedTypeMarker).getLowerType();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + capturedTypeMarker + ", " + Reflection.getOrCreateKotlinClass(capturedTypeMarker.getClass())).toString());
        }

        public static boolean isIntersection(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                return typeConstructorMarker instanceof IntersectionTypeConstructor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static boolean identicalArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, NotificationBundleProcessor.PUSH_ADDITIONAL_DATA_KEY);
            Intrinsics.checkNotNullParameter(simpleTypeMarker2, "b");
            if (!(simpleTypeMarker instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
            } else if (simpleTypeMarker2 instanceof SimpleType) {
                return ((SimpleType) simpleTypeMarker).getArguments() == ((SimpleType) simpleTypeMarker2).getArguments();
            } else {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker2 + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker2.getClass())).toString());
            }
        }

        public static SimpleTypeMarker asSimpleType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                UnwrappedType unwrap = ((KotlinType) kotlinTypeMarker).unwrap();
                return unwrap instanceof SimpleType ? (SimpleType) unwrap : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static FlexibleTypeMarker asFlexibleType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                UnwrappedType unwrap = ((KotlinType) kotlinTypeMarker).unwrap();
                return unwrap instanceof FlexibleType ? (FlexibleType) unwrap : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static DynamicTypeMarker asDynamicType(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker flexibleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(flexibleTypeMarker, "receiver");
            if (flexibleTypeMarker instanceof FlexibleType) {
                return flexibleTypeMarker instanceof DynamicType ? (DynamicType) flexibleTypeMarker : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + flexibleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(flexibleTypeMarker.getClass())).toString());
        }

        public static SimpleTypeMarker upperBound(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker flexibleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(flexibleTypeMarker, "receiver");
            if (flexibleTypeMarker instanceof FlexibleType) {
                return ((FlexibleType) flexibleTypeMarker).getUpperBound();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + flexibleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(flexibleTypeMarker.getClass())).toString());
        }

        public static SimpleTypeMarker lowerBound(ClassicTypeSystemContext classicTypeSystemContext, FlexibleTypeMarker flexibleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(flexibleTypeMarker, "receiver");
            if (flexibleTypeMarker instanceof FlexibleType) {
                return ((FlexibleType) flexibleTypeMarker).getLowerBound();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + flexibleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(flexibleTypeMarker.getClass())).toString());
        }

        public static CapturedTypeMarker asCapturedType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                return simpleTypeMarker instanceof NewCapturedType ? (NewCapturedType) simpleTypeMarker : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static DefinitelyNotNullTypeMarker asDefinitelyNotNullType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                return simpleTypeMarker instanceof DefinitelyNotNullType ? (DefinitelyNotNullType) simpleTypeMarker : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static boolean isMarkedNullable(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                return ((SimpleType) simpleTypeMarker).isMarkedNullable();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static TypeConstructorMarker typeConstructor(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                return ((SimpleType) simpleTypeMarker).getConstructor();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static int argumentsCount(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                return ((KotlinType) kotlinTypeMarker).getArguments().size();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static TypeArgumentMarker getArgument(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker, int i) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                return ((KotlinType) kotlinTypeMarker).getArguments().get(i);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static boolean isStarProjection(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker typeArgumentMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeArgumentMarker, "receiver");
            if (typeArgumentMarker instanceof TypeProjection) {
                return ((TypeProjection) typeArgumentMarker).isStarProjection();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeArgumentMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentMarker.getClass())).toString());
        }

        public static TypeVariance getVariance(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker typeArgumentMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeArgumentMarker, "receiver");
            if (typeArgumentMarker instanceof TypeProjection) {
                Variance projectionKind = ((TypeProjection) typeArgumentMarker).getProjectionKind();
                Intrinsics.checkNotNullExpressionValue(projectionKind, "this.projectionKind");
                return TypeSystemContextKt.convertVariance(projectionKind);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeArgumentMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentMarker.getClass())).toString());
        }

        public static KotlinTypeMarker getType(ClassicTypeSystemContext classicTypeSystemContext, TypeArgumentMarker typeArgumentMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeArgumentMarker, "receiver");
            if (typeArgumentMarker instanceof TypeProjection) {
                return ((TypeProjection) typeArgumentMarker).getType().unwrap();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeArgumentMarker + ", " + Reflection.getOrCreateKotlinClass(typeArgumentMarker.getClass())).toString());
        }

        public static int parametersCount(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                return ((TypeConstructor) typeConstructorMarker).getParameters().size();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static TypeParameterMarker getParameter(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker, int i) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                TypeParameterDescriptor typeParameterDescriptor = ((TypeConstructor) typeConstructorMarker).getParameters().get(i);
                Intrinsics.checkNotNullExpressionValue(typeParameterDescriptor, "this.parameters[index]");
                return typeParameterDescriptor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static Collection<KotlinTypeMarker> supertypes(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                Collection<KotlinType> supertypes = ((TypeConstructor) typeConstructorMarker).getSupertypes();
                Intrinsics.checkNotNullExpressionValue(supertypes, "this.supertypes");
                return supertypes;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static TypeVariance getVariance(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker typeParameterMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeParameterMarker, "receiver");
            if (typeParameterMarker instanceof TypeParameterDescriptor) {
                Variance variance = ((TypeParameterDescriptor) typeParameterMarker).getVariance();
                Intrinsics.checkNotNullExpressionValue(variance, "this.variance");
                return TypeSystemContextKt.convertVariance(variance);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeParameterMarker + ", " + Reflection.getOrCreateKotlinClass(typeParameterMarker.getClass())).toString());
        }

        public static boolean areEqualTypeConstructors(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker, TypeConstructorMarker typeConstructorMarker2) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "c1");
            Intrinsics.checkNotNullParameter(typeConstructorMarker2, "c2");
            if (!(typeConstructorMarker instanceof TypeConstructor)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
            } else if (typeConstructorMarker2 instanceof TypeConstructor) {
                return Intrinsics.areEqual((Object) typeConstructorMarker, (Object) typeConstructorMarker2);
            } else {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker2 + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker2.getClass())).toString());
            }
        }

        public static boolean isClassTypeConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                return ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor() instanceof ClassDescriptor;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static boolean isCommonFinalClassConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                ClassifierDescriptor declarationDescriptor = ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor();
                ClassDescriptor classDescriptor = declarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor : null;
                if (classDescriptor == null || !ModalityUtilsKt.isFinalClass(classDescriptor) || classDescriptor.getKind() == ClassKind.ENUM_ENTRY || classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) {
                    return false;
                }
                return true;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static TypeArgumentListMarker asArgumentList(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                return (TypeArgumentListMarker) simpleTypeMarker;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static SimpleTypeMarker captureFromArguments(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker, CaptureStatus captureStatus) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "type");
            Intrinsics.checkNotNullParameter(captureStatus, "status");
            if (simpleTypeMarker instanceof SimpleType) {
                return NewCapturedTypeKt.captureFromArguments((SimpleType) simpleTypeMarker, captureStatus);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static boolean isAnyConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor) typeConstructorMarker, StandardNames.FqNames.any);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static boolean isNothingConstructor(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                return KotlinBuiltIns.isTypeConstructorForGivenClass((TypeConstructor) typeConstructorMarker, StandardNames.FqNames.nothing);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static TypeArgumentMarker asTypeArgument(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                return TypeUtilsKt.asTypeProjection((KotlinType) kotlinTypeMarker);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static boolean isSingleClassifierType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof SimpleType) {
                if (!KotlinTypeKt.isError((KotlinType) simpleTypeMarker)) {
                    SimpleType simpleType = (SimpleType) simpleTypeMarker;
                    return !(simpleType.getConstructor().getDeclarationDescriptor() instanceof TypeAliasDescriptor) && (simpleType.getConstructor().getDeclarationDescriptor() != null || (simpleTypeMarker instanceof CapturedType) || (simpleTypeMarker instanceof NewCapturedType) || (simpleTypeMarker instanceof DefinitelyNotNullType) || (simpleType.getConstructor() instanceof IntegerLiteralTypeConstructor));
                }
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static KotlinTypeMarker intersectTypes(ClassicTypeSystemContext classicTypeSystemContext, List<? extends KotlinTypeMarker> list) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(list, "types");
            return IntersectionTypeKt.intersectTypes(list);
        }

        public static KotlinTypeMarker createFlexibleType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker, SimpleTypeMarker simpleTypeMarker2) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "lowerBound");
            Intrinsics.checkNotNullParameter(simpleTypeMarker2, "upperBound");
            if (!(simpleTypeMarker instanceof SimpleType)) {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            } else if (simpleTypeMarker2 instanceof SimpleType) {
                KotlinTypeFactory kotlinTypeFactory = KotlinTypeFactory.INSTANCE;
                return KotlinTypeFactory.flexibleType((SimpleType) simpleTypeMarker, (SimpleType) simpleTypeMarker2);
            } else {
                throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + classicTypeSystemContext + ", " + Reflection.getOrCreateKotlinClass(classicTypeSystemContext.getClass())).toString());
            }
        }

        public static KotlinTypeMarker withNullability(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker, boolean z) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof SimpleTypeMarker) {
                return classicTypeSystemContext.withNullability((SimpleTypeMarker) kotlinTypeMarker, z);
            }
            if (kotlinTypeMarker instanceof FlexibleTypeMarker) {
                FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
                return classicTypeSystemContext.createFlexibleType(classicTypeSystemContext.withNullability(classicTypeSystemContext.lowerBound(flexibleTypeMarker), z), classicTypeSystemContext.withNullability(classicTypeSystemContext.upperBound(flexibleTypeMarker), z));
            }
            throw new IllegalStateException("sealed".toString());
        }

        public static AbstractTypeCheckerContext newBaseTypeCheckerContext(ClassicTypeSystemContext classicTypeSystemContext, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            return new ClassicTypeCheckerContext(z, z2, false, (KotlinTypeRefiner) null, 12, (DefaultConstructorMarker) null);
        }

        public static KotlinTypeMarker makeDefinitelyNotNullOrNotNull(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof UnwrappedType) {
                return ClassicTypeSystemContextKt.makeDefinitelyNotNullOrNotNullInternal((UnwrappedType) kotlinTypeMarker);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static boolean isProjectionNotNull(ClassicTypeSystemContext classicTypeSystemContext, CapturedTypeMarker capturedTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(capturedTypeMarker, "receiver");
            if (capturedTypeMarker instanceof NewCapturedType) {
                return ((NewCapturedType) capturedTypeMarker).isProjectionNotNull();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + capturedTypeMarker + ", " + Reflection.getOrCreateKotlinClass(capturedTypeMarker.getClass())).toString());
        }

        public static boolean isNullableType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                return TypeUtils.isNullableType((KotlinType) kotlinTypeMarker);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static SimpleTypeMarker original(ClassicTypeSystemContext classicTypeSystemContext, DefinitelyNotNullTypeMarker definitelyNotNullTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(definitelyNotNullTypeMarker, "receiver");
            if (definitelyNotNullTypeMarker instanceof DefinitelyNotNullType) {
                return ((DefinitelyNotNullType) definitelyNotNullTypeMarker).getOriginal();
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + definitelyNotNullTypeMarker + ", " + Reflection.getOrCreateKotlinClass(definitelyNotNullTypeMarker.getClass())).toString());
        }

        public static boolean isPrimitiveType(ClassicTypeSystemContext classicTypeSystemContext, SimpleTypeMarker simpleTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(simpleTypeMarker, "receiver");
            if (simpleTypeMarker instanceof KotlinType) {
                return KotlinBuiltIns.isPrimitiveType((KotlinType) simpleTypeMarker);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + simpleTypeMarker + ", " + Reflection.getOrCreateKotlinClass(simpleTypeMarker.getClass())).toString());
        }

        public static boolean hasAnnotation(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker, FqName fqName) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            if (kotlinTypeMarker instanceof KotlinType) {
                return ((KotlinType) kotlinTypeMarker).getAnnotations().hasAnnotation(fqName);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static TypeParameterMarker getTypeParameterClassifier(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                ClassifierDescriptor declarationDescriptor = ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor();
                return declarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) declarationDescriptor : null;
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static boolean isInlineClass(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                ClassifierDescriptor declarationDescriptor = ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor();
                Boolean bool = null;
                ClassDescriptor classDescriptor = declarationDescriptor instanceof ClassDescriptor ? (ClassDescriptor) declarationDescriptor : null;
                if (classDescriptor != null) {
                    bool = Boolean.valueOf(InlineClassesUtilsKt.isInlineClass(classDescriptor));
                }
                return Intrinsics.areEqual((Object) bool, (Object) true);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static KotlinTypeMarker getRepresentativeUpperBound(ClassicTypeSystemContext classicTypeSystemContext, TypeParameterMarker typeParameterMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeParameterMarker, "receiver");
            if (typeParameterMarker instanceof TypeParameterDescriptor) {
                return TypeUtilsKt.getRepresentativeUpperBound((TypeParameterDescriptor) typeParameterMarker);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeParameterMarker + ", " + Reflection.getOrCreateKotlinClass(typeParameterMarker.getClass())).toString());
        }

        public static KotlinTypeMarker getSubstitutedUnderlyingType(ClassicTypeSystemContext classicTypeSystemContext, KotlinTypeMarker kotlinTypeMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(kotlinTypeMarker, "receiver");
            if (kotlinTypeMarker instanceof KotlinType) {
                return InlineClassesUtilsKt.substitutedUnderlyingType((KotlinType) kotlinTypeMarker);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + kotlinTypeMarker + ", " + Reflection.getOrCreateKotlinClass(kotlinTypeMarker.getClass())).toString());
        }

        public static PrimitiveType getPrimitiveType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                ClassifierDescriptor declarationDescriptor = ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor();
                if (declarationDescriptor != null) {
                    return KotlinBuiltIns.getPrimitiveType((ClassDescriptor) declarationDescriptor);
                }
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static PrimitiveType getPrimitiveArrayType(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                ClassifierDescriptor declarationDescriptor = ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor();
                if (declarationDescriptor != null) {
                    return KotlinBuiltIns.getPrimitiveArrayType((ClassDescriptor) declarationDescriptor);
                }
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static boolean isUnderKotlinPackage(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                ClassifierDescriptor declarationDescriptor = ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor();
                return Intrinsics.areEqual((Object) declarationDescriptor == null ? null : Boolean.valueOf(KotlinBuiltIns.isUnderKotlinPackage(declarationDescriptor)), (Object) true);
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }

        public static FqNameUnsafe getClassFqNameUnsafe(ClassicTypeSystemContext classicTypeSystemContext, TypeConstructorMarker typeConstructorMarker) {
            Intrinsics.checkNotNullParameter(classicTypeSystemContext, "this");
            Intrinsics.checkNotNullParameter(typeConstructorMarker, "receiver");
            if (typeConstructorMarker instanceof TypeConstructor) {
                ClassifierDescriptor declarationDescriptor = ((TypeConstructor) typeConstructorMarker).getDeclarationDescriptor();
                if (declarationDescriptor != null) {
                    return DescriptorUtilsKt.getFqNameUnsafe((ClassDescriptor) declarationDescriptor);
                }
                throw new NullPointerException("null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
            }
            throw new IllegalArgumentException(("ClassicTypeSystemContext couldn't handle: " + typeConstructorMarker + ", " + Reflection.getOrCreateKotlinClass(typeConstructorMarker.getClass())).toString());
        }
    }
}
