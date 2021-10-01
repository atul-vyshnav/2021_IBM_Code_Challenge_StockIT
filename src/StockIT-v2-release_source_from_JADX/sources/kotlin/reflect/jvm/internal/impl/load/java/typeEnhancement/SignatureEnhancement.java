package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType;
import kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver;
import kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers;
import kotlin.reflect.jvm.internal.impl.load.java.JavaTypeQualifiersByElementType;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.AnnotationDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.NullDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.PossiblyExternalAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.StringDefaultValue;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaAnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaTypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.EnumValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.KotlinTypeKt;
import kotlin.reflect.jvm.internal.impl.types.TypeProjection;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt;
import kotlin.reflect.jvm.internal.impl.utils.JavaTypeEnhancementState;
import kotlin.reflect.jvm.internal.impl.utils.ReportLevel;

/* compiled from: signatureEnhancement.kt */
public final class SignatureEnhancement {
    /* access modifiers changed from: private */
    public final AnnotationTypeQualifierResolver annotationTypeQualifierResolver;
    private final JavaTypeEnhancementState javaTypeEnhancementState;
    /* access modifiers changed from: private */
    public final JavaTypeEnhancement typeEnhancement;

    public SignatureEnhancement(AnnotationTypeQualifierResolver annotationTypeQualifierResolver2, JavaTypeEnhancementState javaTypeEnhancementState2, JavaTypeEnhancement javaTypeEnhancement) {
        Intrinsics.checkNotNullParameter(annotationTypeQualifierResolver2, "annotationTypeQualifierResolver");
        Intrinsics.checkNotNullParameter(javaTypeEnhancementState2, "javaTypeEnhancementState");
        Intrinsics.checkNotNullParameter(javaTypeEnhancement, "typeEnhancement");
        this.annotationTypeQualifierResolver = annotationTypeQualifierResolver2;
        this.javaTypeEnhancementState = javaTypeEnhancementState2;
        this.typeEnhancement = javaTypeEnhancement;
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityTypeFromArgument(AnnotationDescriptor annotationDescriptor, boolean z) {
        ConstantValue<?> firstArgument = DescriptorUtilsKt.firstArgument(annotationDescriptor);
        EnumValue enumValue = firstArgument instanceof EnumValue ? (EnumValue) firstArgument : null;
        if (enumValue == null) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
        }
        String asString = enumValue.getEnumEntryName().asString();
        switch (asString.hashCode()) {
            case 73135176:
                if (!asString.equals("MAYBE")) {
                    return null;
                }
                break;
            case 74175084:
                if (!asString.equals("NEVER")) {
                    return null;
                }
                break;
            case 433141802:
                if (!asString.equals("UNKNOWN")) {
                    return null;
                }
                return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, z);
            case 1933739535:
                if (!asString.equals("ALWAYS")) {
                    return null;
                }
                return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
            default:
                return null;
        }
        return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
    }

    public final NullabilityQualifierWithMigrationStatus extractNullability(AnnotationDescriptor annotationDescriptor, boolean z, boolean z2) {
        NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations;
        Intrinsics.checkNotNullParameter(annotationDescriptor, "annotationDescriptor");
        NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations2 = extractNullabilityFromKnownAnnotations(annotationDescriptor, z, z2);
        if (extractNullabilityFromKnownAnnotations2 != null) {
            return extractNullabilityFromKnownAnnotations2;
        }
        AnnotationDescriptor resolveTypeQualifierAnnotation = this.annotationTypeQualifierResolver.resolveTypeQualifierAnnotation(annotationDescriptor);
        if (resolveTypeQualifierAnnotation == null) {
            return null;
        }
        ReportLevel resolveJsr305AnnotationState = this.annotationTypeQualifierResolver.resolveJsr305AnnotationState(annotationDescriptor);
        if (!resolveJsr305AnnotationState.isIgnore() && (extractNullabilityFromKnownAnnotations = extractNullabilityFromKnownAnnotations(resolveTypeQualifierAnnotation, z, z2)) != null) {
            return NullabilityQualifierWithMigrationStatus.copy$default(extractNullabilityFromKnownAnnotations, (NullabilityQualifier) null, resolveJsr305AnnotationState.isWarning(), 1, (Object) null);
        }
        return null;
    }

    private final NullabilityQualifierWithMigrationStatus extractNullabilityFromKnownAnnotations(AnnotationDescriptor annotationDescriptor, boolean z, boolean z2) {
        FqName fqName = annotationDescriptor.getFqName();
        if (fqName == null) {
            return null;
        }
        boolean z3 = (annotationDescriptor instanceof LazyJavaAnnotationDescriptor) && (((LazyJavaAnnotationDescriptor) annotationDescriptor).isFreshlySupportedTypeUseAnnotation() || z2) && !z;
        NullabilityQualifierWithMigrationStatus jspecifyMigrationStatus = jspecifyMigrationStatus(fqName);
        if (jspecifyMigrationStatus == null && (jspecifyMigrationStatus = commonMigrationStatus(fqName, annotationDescriptor, z3)) == null) {
            return null;
        }
        return (jspecifyMigrationStatus.isForWarningOnly() || !(annotationDescriptor instanceof PossiblyExternalAnnotationDescriptor) || !((PossiblyExternalAnnotationDescriptor) annotationDescriptor).isIdeExternalAnnotation()) ? jspecifyMigrationStatus : NullabilityQualifierWithMigrationStatus.copy$default(jspecifyMigrationStatus, (NullabilityQualifier) null, true, 1, (Object) null);
    }

    private final NullabilityQualifierWithMigrationStatus jspecifyMigrationStatus(FqName fqName) {
        if (this.javaTypeEnhancementState.getJspecifyReportLevel() == ReportLevel.IGNORE) {
            return null;
        }
        boolean z = this.javaTypeEnhancementState.getJspecifyReportLevel() == ReportLevel.WARN;
        if (Intrinsics.areEqual((Object) fqName, (Object) JvmAnnotationNamesKt.getJSPECIFY_NULLABLE())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
        }
        if (Intrinsics.areEqual((Object) fqName, (Object) JvmAnnotationNamesKt.getJSPECIFY_NULLNESS_UNKNOWN())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.FORCE_FLEXIBILITY, z);
        }
        return null;
    }

    private final NullabilityQualifierWithMigrationStatus commonMigrationStatus(FqName fqName, AnnotationDescriptor annotationDescriptor, boolean z) {
        if (JvmAnnotationNamesKt.getNULLABLE_ANNOTATIONS().contains(fqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
        }
        if (JvmAnnotationNamesKt.getNOT_NULL_ANNOTATIONS().contains(fqName)) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
        }
        if (Intrinsics.areEqual((Object) fqName, (Object) JvmAnnotationNamesKt.getJAVAX_NONNULL_ANNOTATION())) {
            return extractNullabilityTypeFromArgument(annotationDescriptor, z);
        }
        if (Intrinsics.areEqual((Object) fqName, (Object) JvmAnnotationNamesKt.getCOMPATQUAL_NULLABLE_ANNOTATION()) && this.javaTypeEnhancementState.getEnableCompatqualCheckerFrameworkAnnotations()) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, z);
        }
        if (Intrinsics.areEqual((Object) fqName, (Object) JvmAnnotationNamesKt.getCOMPATQUAL_NONNULL_ANNOTATION()) && this.javaTypeEnhancementState.getEnableCompatqualCheckerFrameworkAnnotations()) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, z);
        }
        if (Intrinsics.areEqual((Object) fqName, (Object) JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NON_NULL_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, true);
        }
        if (Intrinsics.areEqual((Object) fqName, (Object) JvmAnnotationNamesKt.getANDROIDX_RECENTLY_NULLABLE_ANNOTATION())) {
            return new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NULLABLE, true);
        }
        return null;
    }

    public final <D extends CallableMemberDescriptor> Collection<D> enhanceSignatures(LazyJavaResolverContext lazyJavaResolverContext, Collection<? extends D> collection) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "c");
        Intrinsics.checkNotNullParameter(collection, "platformSignatures");
        Iterable<CallableMemberDescriptor> iterable = collection;
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (CallableMemberDescriptor enhanceSignature : iterable) {
            arrayList.add(enhanceSignature(enhanceSignature, lazyJavaResolverContext));
        }
        return (List) arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x021a  */
    /* JADX WARNING: Removed duplicated region for block: B:105:0x022a  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x022f  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x0231  */
    /* JADX WARNING: Removed duplicated region for block: B:122:0x0265  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x020e A[EDGE_INSN: B:143:0x020e->B:97:0x020e ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0259 A[EDGE_INSN: B:144:0x0259->B:118:0x0259 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0062  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0085  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x008e  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0091  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00a9  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0133  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x01a8  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x01aa  */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x01af  */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x01b1  */
    /* JADX WARNING: Removed duplicated region for block: B:79:0x01c3  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:83:0x01d8  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:87:0x01e4  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01e6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final <D extends kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor> D enhanceSignature(D r17, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r18) {
        /*
            r16 = this;
            r7 = r16
            r8 = r17
            boolean r0 = r8 instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor
            if (r0 != 0) goto L_0x0009
            return r8
        L_0x0009:
            r9 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor r9 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor) r9
            kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor$Kind r0 = r9.getKind()
            kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor$Kind r1 = kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor.Kind.FAKE_OVERRIDE
            r10 = 1
            if (r0 != r1) goto L_0x0024
            kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor r0 = r9.getOriginal()
            java.util.Collection r0 = r0.getOverriddenDescriptors()
            int r0 = r0.size()
            if (r0 != r10) goto L_0x0024
            return r8
        L_0x0024:
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r0 = r17.getAnnotations()
            r1 = r18
            kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r4 = kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.copyWithNewDefaultTypeQualifiers(r1, r0)
            boolean r0 = r8 instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor
            r11 = 0
            r12 = 0
            if (r0 == 0) goto L_0x005b
            r0 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor r0 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor) r0
            kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl r1 = r0.getGetter()
            if (r1 != 0) goto L_0x003f
            r1 = r12
            goto L_0x0047
        L_0x003f:
            boolean r1 = r1.isDefault()
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
        L_0x0047:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r11)
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1, (java.lang.Object) r2)
            if (r1 == 0) goto L_0x005b
            kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl r0 = r0.getGetter()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor) r0
            goto L_0x005c
        L_0x005b:
            r0 = r8
        L_0x005c:
            kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor r1 = r9.getExtensionReceiverParameter()
            if (r1 == 0) goto L_0x0085
            boolean r1 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor
            if (r1 != 0) goto L_0x0068
            r1 = r12
            goto L_0x0069
        L_0x0068:
            r1 = r0
        L_0x0069:
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r1 = (kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor) r1
            if (r1 != 0) goto L_0x006f
            r1 = r12
            goto L_0x0077
        L_0x006f:
            kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor$UserDataKey<kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor> r2 = kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER
            java.lang.Object r1 = r1.getUserData(r2)
            kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r1 = (kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor) r1
        L_0x0077:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$receiverTypeEnhancement$1 r2 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$receiverTypeEnhancement$1.INSTANCE
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts r1 = r7.partsForValueParameter(r8, r1, r4, r2)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$PartEnhancementResult r1 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.enhance$default(r1, r12, r10, r12)
            r13 = r1
            goto L_0x0086
        L_0x0085:
            r13 = r12
        L_0x0086:
            boolean r1 = r8 instanceof kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor
            if (r1 == 0) goto L_0x008e
            r1 = r8
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor r1 = (kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor) r1
            goto L_0x008f
        L_0x008e:
            r1 = r12
        L_0x008f:
            if (r1 != 0) goto L_0x0093
        L_0x0091:
            r14 = r12
            goto L_0x00b4
        L_0x0093:
            kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents r2 = kotlin.reflect.jvm.internal.impl.load.kotlin.SignatureBuildingComponents.INSTANCE
            kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor r3 = r1.getContainingDeclaration()
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r3 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor) r3
            kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r1 = (kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor) r1
            r5 = 3
            java.lang.String r1 = kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt.computeJvmDescriptor$default(r1, r11, r11, r5, r12)
            java.lang.String r1 = kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureBuildingUtilsKt.signature(r2, r3, r1)
            if (r1 != 0) goto L_0x00a9
            goto L_0x0091
        L_0x00a9:
            java.util.Map r2 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt.getPREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE()
            java.lang.Object r1 = r2.get(r1)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedFunctionEnhancementInfo r1 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedFunctionEnhancementInfo) r1
            r14 = r1
        L_0x00b4:
            if (r14 != 0) goto L_0x00b7
            goto L_0x0111
        L_0x00b7:
            java.util.List r1 = r14.getParametersInfo()
            int r1 = r1.size()
            java.util.List r2 = r9.getValueParameters()
            int r2 = r2.size()
            if (r1 != r2) goto L_0x00cb
            r1 = 1
            goto L_0x00cc
        L_0x00cb:
            r1 = 0
        L_0x00cc:
            boolean r2 = kotlin._Assertions.ENABLED
            if (r2 == 0) goto L_0x0111
            if (r1 == 0) goto L_0x00d3
            goto L_0x0111
        L_0x00d3:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "Predefined enhancement info for "
            r0.append(r1)
            r0.append(r8)
            java.lang.String r1 = " has "
            r0.append(r1)
            java.util.List r1 = r14.getParametersInfo()
            int r1 = r1.size()
            r0.append(r1)
            java.lang.String r1 = ", but "
            r0.append(r1)
            java.util.List r1 = r9.getValueParameters()
            int r1 = r1.size()
            r0.append(r1)
            java.lang.String r1 = " expected"
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>(r0)
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            throw r1
        L_0x0111:
            java.util.List r1 = r0.getValueParameters()
            java.lang.String r2 = "annotationOwnerForMember.valueParameters"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r1, r2)
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.ArrayList r2 = new java.util.ArrayList
            r15 = 10
            int r3 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r1, r15)
            r2.<init>(r3)
            java.util.Collection r2 = (java.util.Collection) r2
            java.util.Iterator r1 = r1.iterator()
        L_0x012d:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x019d
            java.lang.Object r3 = r1.next()
            kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r3 = (kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor) r3
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$valueParameterEnhancements$1$enhancementResult$1 r5 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$valueParameterEnhancements$1$enhancementResult$1
            r5.<init>(r3)
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts r5 = r7.partsForValueParameter(r8, r3, r4, r5)
            if (r14 != 0) goto L_0x0148
        L_0x0146:
            r6 = r12
            goto L_0x0159
        L_0x0148:
            java.util.List r6 = r14.getParametersInfo()
            if (r6 != 0) goto L_0x014f
            goto L_0x0146
        L_0x014f:
            int r11 = r3.getIndex()
            java.lang.Object r6 = kotlin.collections.CollectionsKt.getOrNull(r6, r11)
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementInfo r6 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementInfo) r6
        L_0x0159:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$PartEnhancementResult r5 = r5.enhance(r6)
            boolean r6 = r5.getWereChanges()
            if (r6 == 0) goto L_0x0168
            kotlin.reflect.jvm.internal.impl.types.KotlinType r6 = r5.getType()
            goto L_0x0171
        L_0x0168:
            kotlin.reflect.jvm.internal.impl.types.KotlinType r6 = r3.getType()
            java.lang.String r11 = "p.type"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r11)
        L_0x0171:
            java.lang.String r11 = "p"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r3, r11)
            boolean r6 = r7.hasDefaultValueInAnnotation(r3, r6)
            boolean r11 = r5.getWereChanges()
            if (r11 != 0) goto L_0x0189
            boolean r3 = r3.declaresDefaultValue()
            if (r6 == r3) goto L_0x0187
            goto L_0x0189
        L_0x0187:
            r3 = 0
            goto L_0x018a
        L_0x0189:
            r3 = 1
        L_0x018a:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$ValueParameterEnhancementResult r11 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$ValueParameterEnhancementResult
            kotlin.reflect.jvm.internal.impl.types.KotlinType r12 = r5.getType()
            boolean r5 = r5.getContainsFunctionN()
            r11.<init>(r12, r6, r3, r5)
            r2.add(r11)
            r11 = 0
            r12 = 0
            goto L_0x012d
        L_0x019d:
            r11 = r2
            java.util.List r11 = (java.util.List) r11
            r2 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated r2 = (kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated) r2
            r3 = 1
            boolean r0 = r8 instanceof kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor
            if (r0 != 0) goto L_0x01aa
            r0 = 0
            goto L_0x01ab
        L_0x01aa:
            r0 = r8
        L_0x01ab:
            kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor) r0
            if (r0 != 0) goto L_0x01b1
            r0 = 0
            goto L_0x01b9
        L_0x01b1:
            boolean r0 = kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.JavaDescriptorUtilKt.isJavaField(r0)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
        L_0x01b9:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r10)
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0, (java.lang.Object) r1)
            if (r0 == 0) goto L_0x01c6
            kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType r0 = kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType.FIELD
            goto L_0x01c8
        L_0x01c6:
            kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType r0 = kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType.METHOD_RETURN_TYPE
        L_0x01c8:
            r5 = r0
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$returnTypeEnhancement$1 r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$enhanceSignature$returnTypeEnhancement$1.INSTANCE
            r6 = r0
            kotlin.jvm.functions.Function1 r6 = (kotlin.jvm.functions.Function1) r6
            r0 = r16
            r1 = r17
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts r0 = r0.parts(r1, r2, r3, r4, r5, r6)
            if (r14 != 0) goto L_0x01da
            r1 = 0
            goto L_0x01de
        L_0x01da:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnhancementInfo r1 = r14.getReturnTypeInfo()
        L_0x01de:
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$PartEnhancementResult r0 = r0.enhance(r1)
            if (r13 != 0) goto L_0x01e6
            r1 = 0
            goto L_0x01ee
        L_0x01e6:
            boolean r1 = r13.getContainsFunctionN()
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
        L_0x01ee:
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r10)
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1, (java.lang.Object) r2)
            if (r1 != 0) goto L_0x022c
            boolean r1 = r0.getContainsFunctionN()
            if (r1 != 0) goto L_0x022c
            r1 = r11
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            boolean r2 = r1 instanceof java.util.Collection
            if (r2 == 0) goto L_0x0210
            r2 = r1
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0210
        L_0x020e:
            r1 = 0
            goto L_0x0227
        L_0x0210:
            java.util.Iterator r1 = r1.iterator()
        L_0x0214:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x020e
            java.lang.Object r2 = r1.next()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$ValueParameterEnhancementResult r2 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.ValueParameterEnhancementResult) r2
            boolean r2 = r2.getContainsFunctionN()
            if (r2 == 0) goto L_0x0214
            r1 = 1
        L_0x0227:
            if (r1 == 0) goto L_0x022a
            goto L_0x022c
        L_0x022a:
            r1 = 0
            goto L_0x022d
        L_0x022c:
            r1 = 1
        L_0x022d:
            if (r13 != 0) goto L_0x0231
            r2 = 0
            goto L_0x0239
        L_0x0231:
            boolean r2 = r13.getWereChanges()
            java.lang.Boolean r2 = java.lang.Boolean.valueOf(r2)
        L_0x0239:
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r10)
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r2, (java.lang.Object) r3)
            if (r2 != 0) goto L_0x0277
            boolean r2 = r0.getWereChanges()
            if (r2 != 0) goto L_0x0277
            r2 = r11
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            boolean r3 = r2 instanceof java.util.Collection
            if (r3 == 0) goto L_0x025b
            r3 = r2
            java.util.Collection r3 = (java.util.Collection) r3
            boolean r3 = r3.isEmpty()
            if (r3 == 0) goto L_0x025b
        L_0x0259:
            r10 = 0
            goto L_0x0271
        L_0x025b:
            java.util.Iterator r2 = r2.iterator()
        L_0x025f:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x0259
            java.lang.Object r3 = r2.next()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$ValueParameterEnhancementResult r3 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.ValueParameterEnhancementResult) r3
            boolean r3 = r3.getWereChanges()
            if (r3 == 0) goto L_0x025f
        L_0x0271:
            if (r10 != 0) goto L_0x0277
            if (r1 == 0) goto L_0x0276
            goto L_0x0277
        L_0x0276:
            return r8
        L_0x0277:
            if (r1 == 0) goto L_0x028a
            kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor$UserDataKey r1 = kotlin.reflect.jvm.internal.impl.resolve.deprecation.DeprecationKt.getDEPRECATED_FUNCTION_KEY()
            kotlin.reflect.jvm.internal.impl.load.java.DeprecationCausedByFunctionN r2 = new kotlin.reflect.jvm.internal.impl.load.java.DeprecationCausedByFunctionN
            r3 = r8
            kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor r3 = (kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor) r3
            r2.<init>(r3)
            kotlin.Pair r1 = kotlin.TuplesKt.m1160to(r1, r2)
            goto L_0x028b
        L_0x028a:
            r1 = 0
        L_0x028b:
            if (r13 != 0) goto L_0x028f
            r12 = 0
            goto L_0x0293
        L_0x028f:
            kotlin.reflect.jvm.internal.impl.types.KotlinType r12 = r13.getType()
        L_0x0293:
            java.lang.Iterable r11 = (java.lang.Iterable) r11
            java.util.ArrayList r2 = new java.util.ArrayList
            int r3 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r11, r15)
            r2.<init>(r3)
            java.util.Collection r2 = (java.util.Collection) r2
            java.util.Iterator r3 = r11.iterator()
        L_0x02a4:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x02c1
            java.lang.Object r4 = r3.next()
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$ValueParameterEnhancementResult r4 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.ValueParameterEnhancementResult) r4
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData r5 = new kotlin.reflect.jvm.internal.impl.load.java.descriptors.ValueParameterData
            kotlin.reflect.jvm.internal.impl.types.KotlinType r6 = r4.getType()
            boolean r4 = r4.getHasDefaultValue()
            r5.<init>(r6, r4)
            r2.add(r5)
            goto L_0x02a4
        L_0x02c1:
            java.util.List r2 = (java.util.List) r2
            kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = r0.getType()
            kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaCallableMemberDescriptor r0 = r9.enhance(r12, r2, r0, r1)
            kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.enhanceSignature(kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext):kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor");
    }

    public final List<KotlinType> enhanceTypeParameterBounds(TypeParameterDescriptor typeParameterDescriptor, List<? extends KotlinType> list, LazyJavaResolverContext lazyJavaResolverContext) {
        Intrinsics.checkNotNullParameter(typeParameterDescriptor, "typeParameter");
        Intrinsics.checkNotNullParameter(list, "bounds");
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "context");
        Iterable<KotlinType> iterable = list;
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (KotlinType kotlinType : iterable) {
            if (!TypeUtilsKt.contains(kotlinType, SignatureEnhancement$enhanceTypeParameterBounds$1$1.INSTANCE)) {
                kotlinType = SignatureParts.enhance$default(new SignatureParts(this, typeParameterDescriptor, kotlinType, CollectionsKt.emptyList(), false, lazyJavaResolverContext, AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS, true), (TypeEnhancementInfo) null, 1, (Object) null).getType();
            }
            arrayList.add(kotlinType);
        }
        return (List) arrayList;
    }

    public final KotlinType enhanceSuperType(KotlinType kotlinType, LazyJavaResolverContext lazyJavaResolverContext) {
        Intrinsics.checkNotNullParameter(kotlinType, "type");
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "context");
        return SignatureParts.enhance$default(new SignatureParts((Annotated) null, kotlinType, CollectionsKt.emptyList(), false, lazyJavaResolverContext, AnnotationQualifierApplicabilityType.TYPE_USE, false, 64, (DefaultConstructorMarker) null), (TypeEnhancementInfo) null, 1, (Object) null).getType();
    }

    private final boolean hasDefaultValueInAnnotation(ValueParameterDescriptor valueParameterDescriptor, KotlinType kotlinType) {
        boolean z;
        AnnotationDefaultValue defaultValueFromAnnotation = UtilKt.getDefaultValueFromAnnotation(valueParameterDescriptor);
        if (defaultValueFromAnnotation instanceof StringDefaultValue) {
            z = UtilsKt.lexicalCastFrom(kotlinType, ((StringDefaultValue) defaultValueFromAnnotation).getValue()) != null;
        } else if (Intrinsics.areEqual((Object) defaultValueFromAnnotation, (Object) NullDefaultValue.INSTANCE)) {
            z = TypeUtils.acceptsNullable(kotlinType);
        } else if (defaultValueFromAnnotation == null) {
            z = valueParameterDescriptor.declaresDefaultValue();
        } else {
            throw new NoWhenBranchMatchedException();
        }
        if (!z || !valueParameterDescriptor.getOverriddenDescriptors().isEmpty()) {
            return false;
        }
        return true;
    }

    /* compiled from: signatureEnhancement.kt */
    private final class SignatureParts {
        private final AnnotationQualifierApplicabilityType containerApplicabilityType;
        private final LazyJavaResolverContext containerContext;
        private final Collection<KotlinType> fromOverridden;
        private final KotlinType fromOverride;
        private final boolean isCovariant;
        private final Annotated typeContainer;
        private final boolean typeParameterBounds;

        public SignatureParts(SignatureEnhancement signatureEnhancement, Annotated annotated, KotlinType kotlinType, Collection<? extends KotlinType> collection, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, boolean z2) {
            Intrinsics.checkNotNullParameter(signatureEnhancement, "this$0");
            Intrinsics.checkNotNullParameter(kotlinType, "fromOverride");
            Intrinsics.checkNotNullParameter(collection, "fromOverridden");
            Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "containerContext");
            Intrinsics.checkNotNullParameter(annotationQualifierApplicabilityType, "containerApplicabilityType");
            SignatureEnhancement.this = signatureEnhancement;
            this.typeContainer = annotated;
            this.fromOverride = kotlinType;
            this.fromOverridden = collection;
            this.isCovariant = z;
            this.containerContext = lazyJavaResolverContext;
            this.containerApplicabilityType = annotationQualifierApplicabilityType;
            this.typeParameterBounds = z2;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        public /* synthetic */ SignatureParts(Annotated annotated, KotlinType kotlinType, Collection collection, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(SignatureEnhancement.this, annotated, kotlinType, collection, z, lazyJavaResolverContext, annotationQualifierApplicabilityType, (i & 64) != 0 ? false : z2);
        }

        private final boolean isForVarargParameter() {
            Annotated annotated = this.typeContainer;
            KotlinType kotlinType = null;
            if (!(annotated instanceof ValueParameterDescriptor)) {
                annotated = null;
            }
            ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) annotated;
            if (valueParameterDescriptor != null) {
                kotlinType = valueParameterDescriptor.getVarargElementType();
            }
            return kotlinType != null;
        }

        public static /* synthetic */ PartEnhancementResult enhance$default(SignatureParts signatureParts, TypeEnhancementInfo typeEnhancementInfo, int i, Object obj) {
            if ((i & 1) != 0) {
                typeEnhancementInfo = null;
            }
            return signatureParts.enhance(typeEnhancementInfo);
        }

        public final PartEnhancementResult enhance(TypeEnhancementInfo typeEnhancementInfo) {
            Function1<Integer, JavaTypeQualifiers> function1;
            Function1<Integer, JavaTypeQualifiers> computeIndexedQualifiersForOverride = computeIndexedQualifiersForOverride();
            PartEnhancementResult partEnhancementResult = null;
            if (typeEnhancementInfo == null) {
                function1 = null;
            } else {
                function1 = new C3247x3450d111(typeEnhancementInfo, computeIndexedQualifiersForOverride);
            }
            boolean contains = TypeUtils.contains(this.fromOverride, SignatureEnhancement$SignatureParts$enhance$containsFunctionN$1.INSTANCE);
            JavaTypeEnhancement access$getTypeEnhancement$p = SignatureEnhancement.this.typeEnhancement;
            KotlinType kotlinType = this.fromOverride;
            if (function1 != null) {
                computeIndexedQualifiersForOverride = function1;
            }
            KotlinType enhance = access$getTypeEnhancement$p.enhance(kotlinType, computeIndexedQualifiersForOverride);
            if (enhance != null) {
                partEnhancementResult = new PartEnhancementResult(enhance, true, contains);
            }
            return partEnhancementResult == null ? new PartEnhancementResult(this.fromOverride, false, contains) : partEnhancementResult;
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0048  */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x004b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers extractQualifiers(kotlin.reflect.jvm.internal.impl.types.KotlinType r12) {
            /*
                r11 = this;
                boolean r0 = kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt.isFlexible(r12)
                if (r0 == 0) goto L_0x0018
                kotlin.reflect.jvm.internal.impl.types.FlexibleType r0 = kotlin.reflect.jvm.internal.impl.types.FlexibleTypesKt.asFlexibleType(r12)
                kotlin.Pair r1 = new kotlin.Pair
                kotlin.reflect.jvm.internal.impl.types.SimpleType r2 = r0.getLowerBound()
                kotlin.reflect.jvm.internal.impl.types.SimpleType r0 = r0.getUpperBound()
                r1.<init>(r2, r0)
                goto L_0x001d
            L_0x0018:
                kotlin.Pair r1 = new kotlin.Pair
                r1.<init>(r12, r12)
            L_0x001d:
                java.lang.Object r0 = r1.component1()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r0
                java.lang.Object r1 = r1.component2()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r1
                kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMapper r2 = kotlin.reflect.jvm.internal.impl.builtins.jvm.JavaToKotlinClassMapper.INSTANCE
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r10 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers
                boolean r3 = r0.isMarkedNullable()
                r4 = 0
                if (r3 == 0) goto L_0x0038
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NULLABLE
            L_0x0036:
                r5 = r3
                goto L_0x0042
            L_0x0038:
                boolean r3 = r1.isMarkedNullable()
                if (r3 != 0) goto L_0x0041
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NOT_NULL
                goto L_0x0036
            L_0x0041:
                r5 = r4
            L_0x0042:
                boolean r0 = r2.isReadOnly((kotlin.reflect.jvm.internal.impl.types.KotlinType) r0)
                if (r0 == 0) goto L_0x004b
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.READ_ONLY
                goto L_0x0055
            L_0x004b:
                boolean r0 = r2.isMutable((kotlin.reflect.jvm.internal.impl.types.KotlinType) r1)
                if (r0 == 0) goto L_0x0054
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r0 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.MUTABLE
                goto L_0x0055
            L_0x0054:
                r0 = r4
            L_0x0055:
                kotlin.reflect.jvm.internal.impl.types.UnwrappedType r12 = r12.unwrap()
                boolean r6 = r12 instanceof kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NotNullTypeParameter
                r7 = 0
                r8 = 8
                r9 = 0
                r3 = r10
                r4 = r5
                r5 = r0
                r3.<init>(r4, r5, r6, r7, r8, r9)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.extractQualifiers(kotlin.reflect.jvm.internal.impl.types.KotlinType):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:38:0x00ae, code lost:
            if ((r13.getAffectsTypeParameterBasedTypes() || !kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt.isTypeParameter(r11)) != false) goto L_0x00b0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:48:0x00e0, code lost:
            if (r0.getQualifier() == kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NOT_NULL) goto L_0x00f9;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x00f7, code lost:
            if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r12, (java.lang.Object) true) != false) goto L_0x00f9;
         */
        /* JADX WARNING: Removed duplicated region for block: B:59:0x00fe  */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x0100  */
        /* JADX WARNING: Removed duplicated region for block: B:67:0x012a  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers extractQualifiersFromAnnotations(kotlin.reflect.jvm.internal.impl.types.KotlinType r11, boolean r12, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers r13, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r14, boolean r15) {
            /*
                r10 = this;
                kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r0 = r10.containerContext
                kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents r0 = r0.getComponents()
                kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverSettings r0 = r0.getSettings()
                boolean r0 = r0.getTypeEnhancementImprovements()
                r1 = 0
                r2 = 1
                java.lang.Boolean r3 = java.lang.Boolean.valueOf(r2)
                if (r12 == 0) goto L_0x0070
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated r4 = r10.typeContainer
                if (r4 == 0) goto L_0x0070
                boolean r5 = r4 instanceof kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor
                if (r5 != 0) goto L_0x0070
                if (r0 == 0) goto L_0x0070
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4 = r4.getAnnotations()
                java.lang.Iterable r4 = (java.lang.Iterable) r4
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement r5 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.this
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
                java.util.Collection r6 = (java.util.Collection) r6
                java.util.Iterator r4 = r4.iterator()
            L_0x0033:
                boolean r7 = r4.hasNext()
                if (r7 == 0) goto L_0x005f
                java.lang.Object r7 = r4.next()
                r8 = r7
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor r8 = (kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor) r8
                kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver r9 = r5.annotationTypeQualifierResolver
                kotlin.reflect.jvm.internal.impl.load.java.AnnotationTypeQualifierResolver$TypeQualifierWithApplicability r8 = r9.resolveAnnotation(r8)
                if (r8 != 0) goto L_0x004c
            L_0x004a:
                r8 = 0
                goto L_0x0059
            L_0x004c:
                java.util.List r8 = r8.component2()
                kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType r9 = kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType.TYPE_USE
                boolean r8 = r8.contains(r9)
                if (r8 != 0) goto L_0x004a
                r8 = 1
            L_0x0059:
                if (r8 == 0) goto L_0x0033
                r6.add(r7)
                goto L_0x0033
            L_0x005f:
                java.util.List r6 = (java.util.List) r6
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations$Companion r4 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations.Companion
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4 = r4.create(r6)
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r5 = r11.getAnnotations()
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt.composeAnnotations(r4, r5)
                goto L_0x0087
            L_0x0070:
                if (r12 == 0) goto L_0x0083
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated r4 = r10.typeContainer
                if (r4 == 0) goto L_0x0083
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4 = r4.getAnnotations()
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r5 = r11.getAnnotations()
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4 = kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationsKt.composeAnnotations(r4, r5)
                goto L_0x0087
            L_0x0083:
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4 = r11.getAnnotations()
            L_0x0087:
                r5 = 0
                if (r12 == 0) goto L_0x009a
                kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r12 = r10.containerContext
                kotlin.reflect.jvm.internal.impl.load.java.JavaTypeQualifiersByElementType r12 = r12.getDefaultTypeQualifiers()
                if (r12 != 0) goto L_0x0094
                r13 = r5
                goto L_0x009a
            L_0x0094:
                kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType r13 = r10.containerApplicabilityType
                kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers r13 = r12.get(r13)
            L_0x009a:
                if (r13 != 0) goto L_0x009e
            L_0x009c:
                r13 = r5
                goto L_0x00b0
            L_0x009e:
                boolean r12 = r13.getAffectsTypeParameterBasedTypes()
                if (r12 != 0) goto L_0x00ad
                boolean r12 = kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt.isTypeParameter(r11)
                if (r12 != 0) goto L_0x00ab
                goto L_0x00ad
            L_0x00ab:
                r12 = 0
                goto L_0x00ae
            L_0x00ad:
                r12 = 1
            L_0x00ae:
                if (r12 == 0) goto L_0x009c
            L_0x00b0:
                kotlin.Pair r12 = r10.nullabilityInfoBoundsForTypeParameterUsage(r11)
                java.lang.Object r6 = r12.component1()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus r6 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus) r6
                java.lang.Object r12 = r12.component2()
                java.lang.Boolean r12 = (java.lang.Boolean) r12
                boolean r12 = r12.booleanValue()
                boolean r7 = r10.typeParameterBounds
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus r0 = r10.extractNullability(r4, r0, r7)
                if (r0 != 0) goto L_0x00ce
            L_0x00cc:
                r0 = r5
                goto L_0x00d0
            L_0x00ce:
                if (r15 != 0) goto L_0x00cc
            L_0x00d0:
                if (r0 != 0) goto L_0x00d7
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifierWithMigrationStatus r14 = r10.computeNullabilityInfoInTheAbsenceOfExplicitAnnotation(r6, r13, r14)
                goto L_0x00d8
            L_0x00d7:
                r14 = r0
            L_0x00d8:
                if (r0 == 0) goto L_0x00e5
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r12 = r0.getQualifier()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r13 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NOT_NULL
                if (r12 != r13) goto L_0x00e3
                goto L_0x00f9
            L_0x00e3:
                r12 = 0
                goto L_0x00fa
            L_0x00e5:
                if (r12 != 0) goto L_0x00f9
                if (r13 != 0) goto L_0x00eb
                r12 = r5
                goto L_0x00f3
            L_0x00eb:
                boolean r12 = r13.getMakesTypeParameterNotNull()
                java.lang.Boolean r12 = java.lang.Boolean.valueOf(r12)
            L_0x00f3:
                boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r12, (java.lang.Object) r3)
                if (r12 == 0) goto L_0x00e3
            L_0x00f9:
                r12 = 1
            L_0x00fa:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r13 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers
                if (r14 != 0) goto L_0x0100
                r15 = r5
                goto L_0x0104
            L_0x0100:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r15 = r14.getQualifier()
            L_0x0104:
                java.util.List r0 = kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt.getREAD_ONLY_ANNOTATIONS()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r6 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.READ_ONLY
                java.lang.Object r0 = extractQualifiersFromAnnotations$ifPresent(r0, r4, r6)
                java.util.List r6 = kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNamesKt.getMUTABLE_ANNOTATIONS()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r7 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.MUTABLE
                java.lang.Object r4 = extractQualifiersFromAnnotations$ifPresent(r6, r4, r7)
                java.lang.Object r0 = extractQualifiersFromAnnotations$uniqueNotNull(r0, r4)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r0 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier) r0
                if (r12 == 0) goto L_0x0127
                boolean r11 = kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt.isTypeParameter(r11)
                if (r11 == 0) goto L_0x0127
                r1 = 1
            L_0x0127:
                if (r14 != 0) goto L_0x012a
                goto L_0x0132
            L_0x012a:
                boolean r11 = r14.isForWarningOnly()
                java.lang.Boolean r5 = java.lang.Boolean.valueOf(r11)
            L_0x0132:
                boolean r11 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r5, (java.lang.Object) r3)
                r13.<init>(r15, r0, r1, r11)
                return r13
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.extractQualifiersFromAnnotations(kotlin.reflect.jvm.internal.impl.types.KotlinType, boolean, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor, boolean):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }

        /* JADX WARNING: Removed duplicated region for block: B:14:0x0031 A[ORIG_RETURN, RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:19:? A[RETURN, SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static final <T> T extractQualifiersFromAnnotations$ifPresent(java.util.List<kotlin.reflect.jvm.internal.impl.name.FqName> r3, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r4, T r5) {
            /*
                java.lang.Iterable r3 = (java.lang.Iterable) r3
                boolean r0 = r3 instanceof java.util.Collection
                r1 = 1
                r2 = 0
                if (r0 == 0) goto L_0x0013
                r0 = r3
                java.util.Collection r0 = (java.util.Collection) r0
                boolean r0 = r0.isEmpty()
                if (r0 == 0) goto L_0x0013
            L_0x0011:
                r1 = 0
                goto L_0x002e
            L_0x0013:
                java.util.Iterator r3 = r3.iterator()
            L_0x0017:
                boolean r0 = r3.hasNext()
                if (r0 == 0) goto L_0x0011
                java.lang.Object r0 = r3.next()
                kotlin.reflect.jvm.internal.impl.name.FqName r0 = (kotlin.reflect.jvm.internal.impl.name.FqName) r0
                kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor r0 = r4.findAnnotation(r0)
                if (r0 == 0) goto L_0x002b
                r0 = 1
                goto L_0x002c
            L_0x002b:
                r0 = 0
            L_0x002c:
                if (r0 == 0) goto L_0x0017
            L_0x002e:
                if (r1 == 0) goto L_0x0031
                goto L_0x0032
            L_0x0031:
                r5 = 0
            L_0x0032:
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.extractQualifiersFromAnnotations$ifPresent(java.util.List, kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations, java.lang.Object):java.lang.Object");
        }

        private static final <T> T extractQualifiersFromAnnotations$uniqueNotNull(T t, T t2) {
            if (t == null || t2 == null || Intrinsics.areEqual((Object) t, (Object) t2)) {
                return t == null ? t2 : t;
            }
            return null;
        }

        private final NullabilityQualifierWithMigrationStatus computeNullabilityInfoInTheAbsenceOfExplicitAnnotation(NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus, JavaDefaultQualifiers javaDefaultQualifiers, TypeParameterDescriptor typeParameterDescriptor) {
            NullabilityQualifier nullabilityQualifier;
            NullabilityQualifierWithMigrationStatus nullabilityQualifier2;
            if (nullabilityQualifierWithMigrationStatus == null) {
                if (javaDefaultQualifiers == null || (nullabilityQualifier2 = javaDefaultQualifiers.getNullabilityQualifier()) == null) {
                    nullabilityQualifierWithMigrationStatus = null;
                } else {
                    nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(nullabilityQualifier2.getQualifier(), nullabilityQualifier2.isForWarningOnly());
                }
            }
            if (typeParameterDescriptor == null) {
                nullabilityQualifier = null;
            } else {
                nullabilityQualifier = boundsNullability(typeParameterDescriptor);
            }
            if (nullabilityQualifier == null) {
                return nullabilityQualifierWithMigrationStatus;
            }
            if (nullabilityQualifierWithMigrationStatus == null) {
                return new NullabilityQualifierWithMigrationStatus(nullabilityQualifier, false, 2, (DefaultConstructorMarker) null);
            }
            return new NullabilityQualifierWithMigrationStatus(mostSpecific(nullabilityQualifier, nullabilityQualifierWithMigrationStatus.getQualifier()), false, 2, (DefaultConstructorMarker) null);
        }

        private final NullabilityQualifier mostSpecific(NullabilityQualifier nullabilityQualifier, NullabilityQualifier nullabilityQualifier2) {
            if (nullabilityQualifier == NullabilityQualifier.FORCE_FLEXIBILITY) {
                return nullabilityQualifier2;
            }
            if (nullabilityQualifier2 == NullabilityQualifier.FORCE_FLEXIBILITY) {
                return nullabilityQualifier;
            }
            if (nullabilityQualifier == NullabilityQualifier.NULLABLE) {
                return nullabilityQualifier2;
            }
            if (nullabilityQualifier2 == NullabilityQualifier.NULLABLE) {
                return nullabilityQualifier;
            }
            boolean z = nullabilityQualifier == nullabilityQualifier2 && nullabilityQualifier == NullabilityQualifier.NOT_NULL;
            if (!_Assertions.ENABLED || z) {
                return NullabilityQualifier.NOT_NULL;
            }
            throw new AssertionError("Expected everything is NOT_NULL, but " + nullabilityQualifier + " and " + nullabilityQualifier2 + " are found");
        }

        private final Pair<NullabilityQualifierWithMigrationStatus, Boolean> nullabilityInfoBoundsForTypeParameterUsage(KotlinType kotlinType) {
            ClassifierDescriptor declarationDescriptor = kotlinType.getConstructor().getDeclarationDescriptor();
            TypeParameterDescriptor typeParameterDescriptor = declarationDescriptor instanceof TypeParameterDescriptor ? (TypeParameterDescriptor) declarationDescriptor : null;
            NullabilityQualifier boundsNullability = typeParameterDescriptor == null ? null : boundsNullability(typeParameterDescriptor);
            boolean z = false;
            if (boundsNullability == null) {
                return new Pair<>(null, false);
            }
            NullabilityQualifierWithMigrationStatus nullabilityQualifierWithMigrationStatus = new NullabilityQualifierWithMigrationStatus(NullabilityQualifier.NOT_NULL, false, 2, (DefaultConstructorMarker) null);
            if (boundsNullability == NullabilityQualifier.NOT_NULL) {
                z = true;
            }
            return new Pair<>(nullabilityQualifierWithMigrationStatus, Boolean.valueOf(z));
        }

        private final NullabilityQualifier boundsNullability(TypeParameterDescriptor typeParameterDescriptor) {
            boolean z;
            boolean z2;
            if (!(typeParameterDescriptor instanceof LazyJavaTypeParameterDescriptor)) {
                return null;
            }
            LazyJavaTypeParameterDescriptor lazyJavaTypeParameterDescriptor = (LazyJavaTypeParameterDescriptor) typeParameterDescriptor;
            List<KotlinType> upperBounds = lazyJavaTypeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds, "upperBounds");
            Iterable iterable = upperBounds;
            boolean z3 = false;
            if (!(iterable instanceof Collection) || !((Collection) iterable).isEmpty()) {
                Iterator it = iterable.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!KotlinTypeKt.isError((KotlinType) it.next())) {
                            z = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            z = true;
            if (z) {
                return null;
            }
            List<KotlinType> upperBounds2 = lazyJavaTypeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds2, "upperBounds");
            Iterable iterable2 = upperBounds2;
            if (!(iterable2 instanceof Collection) || !((Collection) iterable2).isEmpty()) {
                Iterator it2 = iterable2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (!SignatureEnhancementKt.isNullabilityFlexible((KotlinType) it2.next())) {
                            z2 = false;
                            break;
                        }
                    } else {
                        break;
                    }
                }
            }
            z2 = true;
            if (z2) {
                return null;
            }
            List<KotlinType> upperBounds3 = lazyJavaTypeParameterDescriptor.getUpperBounds();
            Intrinsics.checkNotNullExpressionValue(upperBounds3, "upperBounds");
            Iterable iterable3 = upperBounds3;
            if (!(iterable3 instanceof Collection) || !((Collection) iterable3).isEmpty()) {
                Iterator it3 = iterable3.iterator();
                while (true) {
                    if (!it3.hasNext()) {
                        break;
                    }
                    KotlinType kotlinType = (KotlinType) it3.next();
                    Intrinsics.checkNotNullExpressionValue(kotlinType, "it");
                    if (!KotlinTypeKt.isNullable(kotlinType)) {
                        z3 = true;
                        break;
                    }
                }
            }
            if (z3) {
                return NullabilityQualifier.NOT_NULL;
            }
            return NullabilityQualifier.NULLABLE;
        }

        private final NullabilityQualifierWithMigrationStatus extractNullability(Annotations annotations, boolean z, boolean z2) {
            SignatureEnhancement signatureEnhancement = SignatureEnhancement.this;
            Iterator it = annotations.iterator();
            while (it.hasNext()) {
                NullabilityQualifierWithMigrationStatus extractNullability = signatureEnhancement.extractNullability((AnnotationDescriptor) it.next(), z, z2);
                if (extractNullability != null) {
                    return extractNullability;
                }
            }
            return null;
        }

        /* JADX WARNING: Removed duplicated region for block: B:18:0x006a  */
        /* JADX WARNING: Removed duplicated region for block: B:21:0x006f  */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x0071  */
        /* JADX WARNING: Removed duplicated region for block: B:25:0x007b  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.jvm.functions.Function1<java.lang.Integer, kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers> computeIndexedQualifiersForOverride() {
            /*
                r17 = this;
                r7 = r17
                java.util.Collection<kotlin.reflect.jvm.internal.impl.types.KotlinType> r0 = r7.fromOverridden
                java.lang.Iterable r0 = (java.lang.Iterable) r0
                java.util.ArrayList r1 = new java.util.ArrayList
                r2 = 10
                int r2 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r0, r2)
                r1.<init>(r2)
                java.util.Collection r1 = (java.util.Collection) r1
                java.util.Iterator r0 = r0.iterator()
            L_0x0017:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x002b
                java.lang.Object r2 = r0.next()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r2 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r2
                java.util.List r2 = r7.toIndexed(r2)
                r1.add(r2)
                goto L_0x0017
            L_0x002b:
                r8 = r1
                java.util.List r8 = (java.util.List) r8
                kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = r7.fromOverride
                java.util.List r9 = r7.toIndexed(r0)
                boolean r0 = r7.isCovariant
                r11 = 1
                if (r0 == 0) goto L_0x006c
                java.util.Collection<kotlin.reflect.jvm.internal.impl.types.KotlinType> r0 = r7.fromOverridden
                java.lang.Iterable r0 = (java.lang.Iterable) r0
                boolean r1 = r0 instanceof java.util.Collection
                if (r1 == 0) goto L_0x004c
                r1 = r0
                java.util.Collection r1 = (java.util.Collection) r1
                boolean r1 = r1.isEmpty()
                if (r1 == 0) goto L_0x004c
            L_0x004a:
                r0 = 0
                goto L_0x0068
            L_0x004c:
                java.util.Iterator r0 = r0.iterator()
            L_0x0050:
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x004a
                java.lang.Object r1 = r0.next()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r1
                kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker r2 = kotlin.reflect.jvm.internal.impl.types.checker.KotlinTypeChecker.DEFAULT
                kotlin.reflect.jvm.internal.impl.types.KotlinType r3 = r7.fromOverride
                boolean r1 = r2.equalTypes(r1, r3)
                r1 = r1 ^ r11
                if (r1 == 0) goto L_0x0050
                r0 = 1
            L_0x0068:
                if (r0 == 0) goto L_0x006c
                r12 = 1
                goto L_0x006d
            L_0x006c:
                r12 = 0
            L_0x006d:
                if (r12 == 0) goto L_0x0071
                r13 = 1
                goto L_0x0076
            L_0x0071:
                int r0 = r9.size()
                r13 = r0
            L_0x0076:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers[] r14 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers[r13]
                r15 = 0
            L_0x0079:
                if (r15 >= r13) goto L_0x00ee
                if (r15 != 0) goto L_0x007f
                r4 = 1
                goto L_0x0080
            L_0x007f:
                r4 = 0
            L_0x0080:
                if (r4 != 0) goto L_0x0087
                if (r12 != 0) goto L_0x0085
                goto L_0x0087
            L_0x0085:
                r0 = 0
                goto L_0x0088
            L_0x0087:
                r0 = 1
            L_0x0088:
                boolean r1 = kotlin._Assertions.ENABLED
                if (r1 == 0) goto L_0x0099
                if (r0 == 0) goto L_0x008f
                goto L_0x0099
            L_0x008f:
                java.lang.AssertionError r0 = new java.lang.AssertionError
                java.lang.String r1 = "Only head type constructors should be computed"
                r0.<init>(r1)
                java.lang.Throwable r0 = (java.lang.Throwable) r0
                throw r0
            L_0x0099:
                java.lang.Object r0 = r9.get(r15)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers r0 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers) r0
                kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = r0.component1()
                kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers r3 = r0.component2()
                kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r5 = r0.component3()
                boolean r6 = r0.component4()
                r0 = r8
                java.lang.Iterable r0 = (java.lang.Iterable) r0
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                java.util.Collection r2 = (java.util.Collection) r2
                java.util.Iterator r0 = r0.iterator()
            L_0x00bd:
                boolean r16 = r0.hasNext()
                if (r16 == 0) goto L_0x00df
                java.lang.Object r16 = r0.next()
                r10 = r16
                java.util.List r10 = (java.util.List) r10
                java.lang.Object r10 = kotlin.collections.CollectionsKt.getOrNull(r10, r15)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers r10 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeAndDefaultQualifiers) r10
                if (r10 != 0) goto L_0x00d5
                r10 = 0
                goto L_0x00d9
            L_0x00d5:
                kotlin.reflect.jvm.internal.impl.types.KotlinType r10 = r10.getType()
            L_0x00d9:
                if (r10 == 0) goto L_0x00bd
                r2.add(r10)
                goto L_0x00bd
            L_0x00df:
                java.util.List r2 = (java.util.List) r2
                java.util.Collection r2 = (java.util.Collection) r2
                r0 = r17
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r0 = r0.computeQualifiersForOverride(r1, r2, r3, r4, r5, r6)
                r14[r15] = r0
                int r15 = r15 + 1
                goto L_0x0079
            L_0x00ee:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$1 r0 = new kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$1
                r0.<init>(r14)
                kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.computeIndexedQualifiersForOverride():kotlin.jvm.functions.Function1");
        }

        private final List<TypeAndDefaultQualifiers> toIndexed(KotlinType kotlinType) {
            ArrayList arrayList = new ArrayList(1);
            toIndexed$add(this, arrayList, kotlinType, this.containerContext, (TypeParameterDescriptor) null);
            return arrayList;
        }

        private static final void toIndexed$add(SignatureParts signatureParts, ArrayList<TypeAndDefaultQualifiers> arrayList, KotlinType kotlinType, LazyJavaResolverContext lazyJavaResolverContext, TypeParameterDescriptor typeParameterDescriptor) {
            JavaDefaultQualifiers javaDefaultQualifiers;
            AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType;
            LazyJavaResolverContext copyWithNewDefaultTypeQualifiers = ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, kotlinType.getAnnotations());
            JavaTypeQualifiersByElementType defaultTypeQualifiers = copyWithNewDefaultTypeQualifiers.getDefaultTypeQualifiers();
            if (defaultTypeQualifiers == null) {
                javaDefaultQualifiers = null;
            } else {
                if (signatureParts.typeParameterBounds) {
                    annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.TYPE_PARAMETER_BOUNDS;
                } else {
                    annotationQualifierApplicabilityType = AnnotationQualifierApplicabilityType.TYPE_USE;
                }
                javaDefaultQualifiers = defaultTypeQualifiers.get(annotationQualifierApplicabilityType);
            }
            arrayList.add(new TypeAndDefaultQualifiers(kotlinType, javaDefaultQualifiers, typeParameterDescriptor, false));
            List<TypeParameterDescriptor> parameters = kotlinType.getConstructor().getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "type.constructor.parameters");
            for (Pair pair : CollectionsKt.zip(kotlinType.getArguments(), parameters)) {
                TypeProjection typeProjection = (TypeProjection) pair.component1();
                TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor) pair.component2();
                if (typeProjection.isStarProjection()) {
                    KotlinType type = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type, "arg.type");
                    arrayList.add(new TypeAndDefaultQualifiers(type, javaDefaultQualifiers, typeParameterDescriptor2, true));
                } else {
                    KotlinType type2 = typeProjection.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "arg.type");
                    toIndexed$add(signatureParts, arrayList, type2, copyWithNewDefaultTypeQualifiers, typeParameterDescriptor2);
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:72:0x014f  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers computeQualifiersForOverride(kotlin.reflect.jvm.internal.impl.types.KotlinType r10, java.util.Collection<? extends kotlin.reflect.jvm.internal.impl.types.KotlinType> r11, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers r12, boolean r13, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r14, boolean r15) {
            /*
                r9 = this;
                java.lang.Iterable r11 = (java.lang.Iterable) r11
                java.util.ArrayList r0 = new java.util.ArrayList
                r1 = 10
                int r1 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r11, r1)
                r0.<init>(r1)
                java.util.Collection r0 = (java.util.Collection) r0
                java.util.Iterator r1 = r11.iterator()
            L_0x0013:
                boolean r2 = r1.hasNext()
                if (r2 == 0) goto L_0x0027
                java.lang.Object r2 = r1.next()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r2 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r2
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r2 = r9.extractQualifiers(r2)
                r0.add(r2)
                goto L_0x0013
            L_0x0027:
                java.util.List r0 = (java.util.List) r0
                java.lang.Iterable r0 = (java.lang.Iterable) r0
                java.util.ArrayList r1 = new java.util.ArrayList
                r1.<init>()
                java.util.Collection r1 = (java.util.Collection) r1
                java.util.Iterator r2 = r0.iterator()
            L_0x0036:
                boolean r3 = r2.hasNext()
                if (r3 == 0) goto L_0x004c
                java.lang.Object r3 = r2.next()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r3 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers) r3
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r3 = r3.getMutability()
                if (r3 == 0) goto L_0x0036
                r1.add(r3)
                goto L_0x0036
            L_0x004c:
                java.util.List r1 = (java.util.List) r1
                java.lang.Iterable r1 = (java.lang.Iterable) r1
                java.util.Set r1 = kotlin.collections.CollectionsKt.toSet(r1)
                java.util.ArrayList r2 = new java.util.ArrayList
                r2.<init>()
                java.util.Collection r2 = (java.util.Collection) r2
                java.util.Iterator r3 = r0.iterator()
            L_0x005f:
                boolean r4 = r3.hasNext()
                if (r4 == 0) goto L_0x0075
                java.lang.Object r4 = r3.next()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r4 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers) r4
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r4 = r4.getNullability()
                if (r4 == 0) goto L_0x005f
                r2.add(r4)
                goto L_0x005f
            L_0x0075:
                java.util.List r2 = (java.util.List) r2
                java.lang.Iterable r2 = (java.lang.Iterable) r2
                java.util.Set r2 = kotlin.collections.CollectionsKt.toSet(r2)
                java.util.ArrayList r3 = new java.util.ArrayList
                r3.<init>()
                java.util.Collection r3 = (java.util.Collection) r3
                java.util.Iterator r11 = r11.iterator()
            L_0x0088:
                boolean r4 = r11.hasNext()
                if (r4 == 0) goto L_0x00a6
                java.lang.Object r4 = r11.next()
                kotlin.reflect.jvm.internal.impl.types.KotlinType r4 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r4
                kotlin.reflect.jvm.internal.impl.types.KotlinType r4 = kotlin.reflect.jvm.internal.impl.types.TypeWithEnhancementKt.unwrapEnhancement(r4)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r4 = r9.extractQualifiers(r4)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r4 = r4.getNullability()
                if (r4 == 0) goto L_0x0088
                r3.add(r4)
                goto L_0x0088
            L_0x00a6:
                java.util.List r3 = (java.util.List) r3
                java.lang.Iterable r3 = (java.lang.Iterable) r3
                java.util.Set r11 = kotlin.collections.CollectionsKt.toSet(r3)
                r3 = r9
                r4 = r10
                r5 = r13
                r6 = r12
                r7 = r14
                r8 = r15
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r10 = r3.extractQualifiersFromAnnotations(r4, r5, r6, r7, r8)
                boolean r12 = r10.isNullabilityQualifierForWarning()
                r14 = 1
                r12 = r12 ^ r14
                r15 = 0
                if (r12 == 0) goto L_0x00c3
                r12 = r10
                goto L_0x00c4
            L_0x00c3:
                r12 = r15
            L_0x00c4:
                if (r12 != 0) goto L_0x00c8
                r12 = r15
                goto L_0x00cc
            L_0x00c8:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r12 = r12.getNullability()
            L_0x00cc:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r3 = r10.getNullability()
                boolean r4 = r9.isCovariant
                r5 = 0
                if (r4 == 0) goto L_0x00d9
                if (r13 == 0) goto L_0x00d9
                r4 = 1
                goto L_0x00da
            L_0x00d9:
                r4 = 0
            L_0x00da:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r6 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnchancementUtilsKt.select(r2, r12, r4)
                if (r6 != 0) goto L_0x00e1
                goto L_0x00f3
            L_0x00e1:
                boolean r7 = r9.isForVarargParameter()
                if (r7 == 0) goto L_0x00ef
                if (r13 == 0) goto L_0x00ef
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r13 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier.NULLABLE
                if (r6 != r13) goto L_0x00ef
                r13 = 1
                goto L_0x00f0
            L_0x00ef:
                r13 = 0
            L_0x00f0:
                if (r13 != 0) goto L_0x00f3
                r15 = r6
            L_0x00f3:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r13 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.MUTABLE
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r6 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier.READ_ONLY
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r7 = r10.getMutability()
                java.lang.Object r13 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnchancementUtilsKt.select(r1, r13, r6, r7, r4)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier r13 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.MutabilityQualifier) r13
                if (r3 != r12) goto L_0x010c
                boolean r12 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r11, (java.lang.Object) r2)
                if (r12 != 0) goto L_0x010a
                goto L_0x010c
            L_0x010a:
                r12 = 0
                goto L_0x010d
            L_0x010c:
                r12 = 1
            L_0x010d:
                boolean r10 = r10.isNotNullTypeParameter()
                if (r10 != 0) goto L_0x013e
                boolean r10 = r0 instanceof java.util.Collection
                if (r10 == 0) goto L_0x0122
                r10 = r0
                java.util.Collection r10 = (java.util.Collection) r10
                boolean r10 = r10.isEmpty()
                if (r10 == 0) goto L_0x0122
            L_0x0120:
                r10 = 0
                goto L_0x0139
            L_0x0122:
                java.util.Iterator r10 = r0.iterator()
            L_0x0126:
                boolean r0 = r10.hasNext()
                if (r0 == 0) goto L_0x0120
                java.lang.Object r0 = r10.next()
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r0 = (kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers) r0
                boolean r0 = r0.isNotNullTypeParameter()
                if (r0 == 0) goto L_0x0126
                r10 = 1
            L_0x0139:
                if (r10 == 0) goto L_0x013c
                goto L_0x013e
            L_0x013c:
                r10 = 0
                goto L_0x013f
            L_0x013e:
                r10 = 1
            L_0x013f:
                if (r15 != 0) goto L_0x014c
                if (r12 == 0) goto L_0x014c
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.NullabilityQualifier r11 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnchancementUtilsKt.select(r11, r3, r4)
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r10 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnchancementUtilsKt.createJavaTypeQualifiers(r11, r13, r14, r10)
                return r10
            L_0x014c:
                if (r15 != 0) goto L_0x014f
                goto L_0x0150
            L_0x014f:
                r14 = 0
            L_0x0150:
                kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers r10 = kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.TypeEnchancementUtilsKt.createJavaTypeQualifiers(r15, r13, r14, r10)
                return r10
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts.computeQualifiersForOverride(kotlin.reflect.jvm.internal.impl.types.KotlinType, java.util.Collection, kotlin.reflect.jvm.internal.impl.load.java.JavaDefaultQualifiers, boolean, kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor, boolean):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.JavaTypeQualifiers");
        }
    }

    /* compiled from: signatureEnhancement.kt */
    private static class PartEnhancementResult {
        private final boolean containsFunctionN;
        private final KotlinType type;
        private final boolean wereChanges;

        public PartEnhancementResult(KotlinType kotlinType, boolean z, boolean z2) {
            Intrinsics.checkNotNullParameter(kotlinType, "type");
            this.type = kotlinType;
            this.wereChanges = z;
            this.containsFunctionN = z2;
        }

        public final KotlinType getType() {
            return this.type;
        }

        public final boolean getWereChanges() {
            return this.wereChanges;
        }

        public final boolean getContainsFunctionN() {
            return this.containsFunctionN;
        }
    }

    /* compiled from: signatureEnhancement.kt */
    private static final class ValueParameterEnhancementResult extends PartEnhancementResult {
        private final boolean hasDefaultValue;

        public final boolean getHasDefaultValue() {
            return this.hasDefaultValue;
        }

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public ValueParameterEnhancementResult(KotlinType kotlinType, boolean z, boolean z2, boolean z3) {
            super(kotlinType, z2, z3);
            Intrinsics.checkNotNullParameter(kotlinType, "type");
            this.hasDefaultValue = z;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r9 = kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.copyWithNewDefaultTypeQualifiers(r10, r9.getAnnotations());
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.SignatureParts partsForValueParameter(kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor r8, kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor r9, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r10, kotlin.jvm.functions.Function1<? super kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, ? extends kotlin.reflect.jvm.internal.impl.types.KotlinType> r11) {
        /*
            r7 = this;
            r2 = r9
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated r2 = (kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotated) r2
            if (r9 != 0) goto L_0x0006
            goto L_0x0010
        L_0x0006:
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r9 = r9.getAnnotations()
            kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r9 = kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt.copyWithNewDefaultTypeQualifiers(r10, r9)
            if (r9 != 0) goto L_0x0012
        L_0x0010:
            r4 = r10
            goto L_0x0013
        L_0x0012:
            r4 = r9
        L_0x0013:
            kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType r5 = kotlin.reflect.jvm.internal.impl.load.java.AnnotationQualifierApplicabilityType.VALUE_PARAMETER
            r3 = 0
            r0 = r7
            r1 = r8
            r6 = r11
            kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts r8 = r0.parts(r1, r2, r3, r4, r5, r6)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement.partsForValueParameter(kotlin.reflect.jvm.internal.impl.descriptors.CallableMemberDescriptor, kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor, kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext, kotlin.jvm.functions.Function1):kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts");
    }

    private final SignatureParts parts(CallableMemberDescriptor callableMemberDescriptor, Annotated annotated, boolean z, LazyJavaResolverContext lazyJavaResolverContext, AnnotationQualifierApplicabilityType annotationQualifierApplicabilityType, Function1<? super CallableMemberDescriptor, ? extends KotlinType> function1) {
        CallableMemberDescriptor callableMemberDescriptor2 = callableMemberDescriptor;
        Function1<? super CallableMemberDescriptor, ? extends KotlinType> function12 = function1;
        KotlinType kotlinType = (KotlinType) function12.invoke(callableMemberDescriptor);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "this.overriddenDescriptors");
        Iterable<CallableMemberDescriptor> iterable = overriddenDescriptors;
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
        for (CallableMemberDescriptor callableMemberDescriptor3 : iterable) {
            Intrinsics.checkNotNullExpressionValue(callableMemberDescriptor3, "it");
            arrayList.add((KotlinType) function12.invoke(callableMemberDescriptor3));
        }
        return new SignatureParts(annotated, kotlinType, (List) arrayList, z, ContextKt.copyWithNewDefaultTypeQualifiers(lazyJavaResolverContext, ((KotlinType) function12.invoke(callableMemberDescriptor)).getAnnotations()), annotationQualifierApplicabilityType, false, 64, (DefaultConstructorMarker) null);
    }
}
