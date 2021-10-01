package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.Unit;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ConstructorDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PackageFragmentDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyGetterDescriptor;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.SpecialNames;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import kotlin.reflect.jvm.internal.impl.utils.FunctionsKt;
import kotlin.text.StringsKt;
import org.apache.commons.p029io.IOUtils;

/* compiled from: descriptorBasedTypeSignatureMapping.kt */
public final class DescriptorBasedTypeSignatureMappingKt {
    public static /* synthetic */ Object mapType$default(KotlinType kotlinType, JvmTypeFactory jvmTypeFactory, TypeMappingMode typeMappingMode, TypeMappingConfiguration typeMappingConfiguration, JvmDescriptorTypeWriter jvmDescriptorTypeWriter, Function3<Object, Object, Object, Unit> function3, int i, Object obj) {
        if ((i & 32) != 0) {
            function3 = FunctionsKt.getDO_NOTHING_3();
        }
        return mapType(kotlinType, jvmTypeFactory, typeMappingMode, typeMappingConfiguration, jvmDescriptorTypeWriter, function3);
    }

    /* JADX WARNING: type inference failed for: r12v6, types: [kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> T mapType(kotlin.reflect.jvm.internal.impl.types.KotlinType r8, kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory<T> r9, kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode r10, kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingConfiguration<? extends T> r11, kotlin.reflect.jvm.internal.impl.load.kotlin.JvmDescriptorTypeWriter<T> r12, kotlin.jvm.functions.Function3<? super kotlin.reflect.jvm.internal.impl.types.KotlinType, ? super T, ? super kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode, kotlin.Unit> r13) {
        /*
            java.lang.String r0 = "kotlinType"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r8, r0)
            java.lang.String r0 = "factory"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            java.lang.String r0 = "mode"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r10, r0)
            java.lang.String r0 = "typeMappingConfiguration"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r11, r0)
            java.lang.String r0 = "writeGenericType"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r13, r0)
            kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = r11.preprocessType(r8)
            if (r1 != 0) goto L_0x01e6
            boolean r0 = kotlin.reflect.jvm.internal.impl.builtins.FunctionTypesKt.isSuspendFunctionType(r8)
            if (r0 == 0) goto L_0x003b
            boolean r0 = r11.releaseCoroutines()
            kotlin.reflect.jvm.internal.impl.types.SimpleType r8 = kotlin.reflect.jvm.internal.impl.builtins.SuspendFunctionTypesKt.transformSuspendFunctionToRuntimeFunctionType(r8, r0)
            r0 = r8
            kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r0
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            java.lang.Object r8 = mapType(r0, r1, r2, r3, r4, r5)
            return r8
        L_0x003b:
            kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext r0 = kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext.INSTANCE
            kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext r0 = (kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext) r0
            r1 = r8
            kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker r1 = (kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker) r1
            java.lang.Object r0 = kotlin.reflect.jvm.internal.impl.load.kotlin.TypeSignatureMappingKt.mapBuiltInType(r0, r1, r9, r10)
            if (r0 != 0) goto L_0x01da
            kotlin.reflect.jvm.internal.impl.types.TypeConstructor r0 = r8.getConstructor()
            boolean r2 = r0 instanceof kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor
            if (r2 == 0) goto L_0x006e
            kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor r0 = (kotlin.reflect.jvm.internal.impl.types.IntersectionTypeConstructor) r0
            kotlin.reflect.jvm.internal.impl.types.KotlinType r8 = r0.getAlternativeType()
            if (r8 != 0) goto L_0x0060
            java.util.Collection r8 = r0.getSupertypes()
            kotlin.reflect.jvm.internal.impl.types.KotlinType r8 = r11.commonSupertype(r8)
        L_0x0060:
            kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt.replaceArgumentsWithStarProjections(r8)
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            java.lang.Object r8 = mapType(r0, r1, r2, r3, r4, r5)
            return r8
        L_0x006e:
            kotlin.reflect.jvm.internal.impl.descriptors.ClassifierDescriptor r0 = r0.getDeclarationDescriptor()
            if (r0 == 0) goto L_0x01ce
            r2 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor r2 = (kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor) r2
            boolean r3 = kotlin.reflect.jvm.internal.impl.types.ErrorUtils.isError(r2)
            if (r3 == 0) goto L_0x008f
            java.lang.String r10 = "error/NonExistentClass"
            java.lang.Object r9 = r9.createObjectType(r10)
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor) r0
            r11.processErrorType(r8, r0)
            if (r12 != 0) goto L_0x008b
            goto L_0x008e
        L_0x008b:
            r12.writeClass(r9)
        L_0x008e:
            return r9
        L_0x008f:
            boolean r3 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor
            if (r3 == 0) goto L_0x010b
            boolean r4 = kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.isArray(r8)
            if (r4 == 0) goto L_0x010b
            java.util.List r0 = r8.getArguments()
            int r0 = r0.size()
            r1 = 1
            if (r0 != r1) goto L_0x0103
            java.util.List r8 = r8.getArguments()
            r0 = 0
            java.lang.Object r8 = r8.get(r0)
            kotlin.reflect.jvm.internal.impl.types.TypeProjection r8 = (kotlin.reflect.jvm.internal.impl.types.TypeProjection) r8
            kotlin.reflect.jvm.internal.impl.types.KotlinType r2 = r8.getType()
            java.lang.String r0 = "memberProjection.type"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r2, r0)
            kotlin.reflect.jvm.internal.impl.types.Variance r0 = r8.getProjectionKind()
            kotlin.reflect.jvm.internal.impl.types.Variance r3 = kotlin.reflect.jvm.internal.impl.types.Variance.IN_VARIANCE
            if (r0 != r3) goto L_0x00d3
            java.lang.String r8 = "java/lang/Object"
            java.lang.Object r8 = r9.createObjectType(r8)
            if (r12 != 0) goto L_0x00c9
            goto L_0x00f4
        L_0x00c9:
            r12.writeArrayType()
            r12.writeClass(r8)
            r12.writeArrayEnd()
            goto L_0x00f4
        L_0x00d3:
            if (r12 != 0) goto L_0x00d6
            goto L_0x00d9
        L_0x00d6:
            r12.writeArrayType()
        L_0x00d9:
            kotlin.reflect.jvm.internal.impl.types.Variance r8 = r8.getProjectionKind()
            java.lang.String r0 = "memberProjection.projectionKind"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r8, r0)
            kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode r4 = r10.toGenericArgumentMode(r8, r1)
            r3 = r9
            r5 = r11
            r6 = r12
            r7 = r13
            java.lang.Object r8 = mapType(r2, r3, r4, r5, r6, r7)
            if (r12 != 0) goto L_0x00f1
            goto L_0x00f4
        L_0x00f1:
            r12.writeArrayEnd()
        L_0x00f4:
            java.lang.String r8 = r9.toString(r8)
            java.lang.String r10 = "["
            java.lang.String r8 = kotlin.jvm.internal.Intrinsics.stringPlus(r10, r8)
            java.lang.Object r8 = r9.createFromString(r8)
            return r8
        L_0x0103:
            java.lang.UnsupportedOperationException r8 = new java.lang.UnsupportedOperationException
            java.lang.String r9 = "arrays must have one type argument"
            r8.<init>(r9)
            throw r8
        L_0x010b:
            if (r3 == 0) goto L_0x017e
            boolean r2 = kotlin.reflect.jvm.internal.impl.resolve.InlineClassesUtilsKt.isInlineClass(r2)
            if (r2 == 0) goto L_0x0133
            boolean r2 = r10.getNeedInlineClassWrapping()
            if (r2 != 0) goto L_0x0133
            kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext r2 = kotlin.reflect.jvm.internal.impl.types.checker.SimpleClassicTypeSystemContext.INSTANCE
            kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext r2 = (kotlin.reflect.jvm.internal.impl.types.TypeSystemCommonBackendContext) r2
            kotlin.reflect.jvm.internal.impl.types.model.KotlinTypeMarker r1 = kotlin.reflect.jvm.internal.impl.types.ExpandedTypeUtilsKt.computeExpandedTypeForInlineClass(r2, r1)
            r2 = r1
            kotlin.reflect.jvm.internal.impl.types.KotlinType r2 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r2
            if (r2 == 0) goto L_0x0133
            kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode r4 = r10.wrapInlineClassesMode()
            r3 = r9
            r5 = r11
            r6 = r12
            r7 = r13
            java.lang.Object r8 = mapType(r2, r3, r4, r5, r6, r7)
            return r8
        L_0x0133:
            boolean r12 = r10.isForAnnotationParameter()
            if (r12 == 0) goto L_0x0147
            r12 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r12 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor) r12
            boolean r12 = kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns.isKClass(r12)
            if (r12 == 0) goto L_0x0147
            java.lang.Object r9 = r9.getJavaLangClassType()
            goto L_0x017a
        L_0x0147:
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor) r0
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r12 = r0.getOriginal()
            java.lang.String r1 = "descriptor.original"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, r1)
            java.lang.Object r12 = r11.getPredefinedTypeForClass(r12)
            if (r12 != 0) goto L_0x0179
            kotlin.reflect.jvm.internal.impl.descriptors.ClassKind r12 = r0.getKind()
            kotlin.reflect.jvm.internal.impl.descriptors.ClassKind r1 = kotlin.reflect.jvm.internal.impl.descriptors.ClassKind.ENUM_ENTRY
            if (r12 != r1) goto L_0x0167
            kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor r12 = r0.getContainingDeclaration()
            r0 = r12
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor) r0
        L_0x0167:
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r12 = r0.getOriginal()
            java.lang.String r0 = "enumClassIfEnumEntry.original"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r12, r0)
            java.lang.String r11 = computeInternalName(r12, r11)
            java.lang.Object r9 = r9.createObjectType(r11)
            goto L_0x017a
        L_0x0179:
            r9 = r12
        L_0x017a:
            r13.invoke(r8, r9, r10)
            return r9
        L_0x017e:
            boolean r1 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor
            if (r1 == 0) goto L_0x01a5
            r8 = r0
            kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor r8 = (kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor) r8
            kotlin.reflect.jvm.internal.impl.types.KotlinType r1 = kotlin.reflect.jvm.internal.impl.types.typeUtil.TypeUtilsKt.getRepresentativeUpperBound(r8)
            kotlin.jvm.functions.Function3 r6 = kotlin.reflect.jvm.internal.impl.utils.FunctionsKt.getDO_NOTHING_3()
            r5 = 0
            r2 = r9
            r3 = r10
            r4 = r11
            java.lang.Object r8 = mapType(r1, r2, r3, r4, r5, r6)
            if (r12 != 0) goto L_0x0198
            goto L_0x01a4
        L_0x0198:
            kotlin.reflect.jvm.internal.impl.name.Name r9 = r0.getName()
            java.lang.String r10 = "descriptor.getName()"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r10)
            r12.writeTypeVariable(r9, r8)
        L_0x01a4:
            return r8
        L_0x01a5:
            boolean r1 = r0 instanceof kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor
            if (r1 == 0) goto L_0x01c2
            boolean r1 = r10.getMapTypeAliases()
            if (r1 == 0) goto L_0x01c2
            kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor r0 = (kotlin.reflect.jvm.internal.impl.descriptors.TypeAliasDescriptor) r0
            kotlin.reflect.jvm.internal.impl.types.SimpleType r8 = r0.getExpandedType()
            r0 = r8
            kotlin.reflect.jvm.internal.impl.types.KotlinType r0 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r0
            r1 = r9
            r2 = r10
            r3 = r11
            r4 = r12
            r5 = r13
            java.lang.Object r8 = mapType(r0, r1, r2, r3, r4, r5)
            return r8
        L_0x01c2:
            java.lang.UnsupportedOperationException r9 = new java.lang.UnsupportedOperationException
            java.lang.String r10 = "Unknown type "
            java.lang.String r8 = kotlin.jvm.internal.Intrinsics.stringPlus(r10, r8)
            r9.<init>(r8)
            throw r9
        L_0x01ce:
            java.lang.UnsupportedOperationException r9 = new java.lang.UnsupportedOperationException
            java.lang.String r10 = "no descriptor for type constructor of "
            java.lang.String r8 = kotlin.jvm.internal.Intrinsics.stringPlus(r10, r8)
            r9.<init>(r8)
            throw r9
        L_0x01da:
            boolean r11 = r10.getNeedPrimitiveBoxing()
            java.lang.Object r9 = kotlin.reflect.jvm.internal.impl.load.kotlin.TypeSignatureMappingKt.boxTypeIfNeeded(r9, r0, r11)
            r13.invoke(r8, r9, r10)
            return r9
        L_0x01e6:
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            java.lang.Object r8 = mapType(r1, r2, r3, r4, r5, r6)     // Catch:{ all -> 0x01f0 }
            return r8
        L_0x01f0:
            r8 = move-exception
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.kotlin.DescriptorBasedTypeSignatureMappingKt.mapType(kotlin.reflect.jvm.internal.impl.types.KotlinType, kotlin.reflect.jvm.internal.impl.load.kotlin.JvmTypeFactory, kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingMode, kotlin.reflect.jvm.internal.impl.load.kotlin.TypeMappingConfiguration, kotlin.reflect.jvm.internal.impl.load.kotlin.JvmDescriptorTypeWriter, kotlin.jvm.functions.Function3):java.lang.Object");
    }

    public static final boolean hasVoidReturnType(CallableDescriptor callableDescriptor) {
        Intrinsics.checkNotNullParameter(callableDescriptor, "descriptor");
        if (callableDescriptor instanceof ConstructorDescriptor) {
            return true;
        }
        KotlinType returnType = callableDescriptor.getReturnType();
        Intrinsics.checkNotNull(returnType);
        if (KotlinBuiltIns.isUnit(returnType)) {
            KotlinType returnType2 = callableDescriptor.getReturnType();
            Intrinsics.checkNotNull(returnType2);
            if (TypeUtils.isNullableType(returnType2) || (callableDescriptor instanceof PropertyGetterDescriptor)) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static /* synthetic */ String computeInternalName$default(ClassDescriptor classDescriptor, TypeMappingConfiguration typeMappingConfiguration, int i, Object obj) {
        if ((i & 2) != 0) {
            typeMappingConfiguration = TypeMappingConfigurationImpl.INSTANCE;
        }
        return computeInternalName(classDescriptor, typeMappingConfiguration);
    }

    public static final String computeInternalName(ClassDescriptor classDescriptor, TypeMappingConfiguration<?> typeMappingConfiguration) {
        Intrinsics.checkNotNullParameter(classDescriptor, "klass");
        Intrinsics.checkNotNullParameter(typeMappingConfiguration, "typeMappingConfiguration");
        String predefinedFullInternalNameForClass = typeMappingConfiguration.getPredefinedFullInternalNameForClass(classDescriptor);
        if (predefinedFullInternalNameForClass != null) {
            return predefinedFullInternalNameForClass;
        }
        DeclarationDescriptor containingDeclaration = classDescriptor.getContainingDeclaration();
        Intrinsics.checkNotNullExpressionValue(containingDeclaration, "klass.containingDeclaration");
        String identifier = SpecialNames.safeIdentifier(classDescriptor.getName()).getIdentifier();
        Intrinsics.checkNotNullExpressionValue(identifier, "safeIdentifier(klass.name).identifier");
        if (containingDeclaration instanceof PackageFragmentDescriptor) {
            FqName fqName = ((PackageFragmentDescriptor) containingDeclaration).getFqName();
            if (fqName.isRoot()) {
                return identifier;
            }
            StringBuilder sb = new StringBuilder();
            String asString = fqName.asString();
            Intrinsics.checkNotNullExpressionValue(asString, "fqName.asString()");
            sb.append(StringsKt.replace$default(asString, '.', (char) IOUtils.DIR_SEPARATOR_UNIX, false, 4, (Object) null));
            sb.append(IOUtils.DIR_SEPARATOR_UNIX);
            sb.append(identifier);
            return sb.toString();
        }
        ClassDescriptor classDescriptor2 = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
        if (classDescriptor2 != null) {
            String predefinedInternalNameForClass = typeMappingConfiguration.getPredefinedInternalNameForClass(classDescriptor2);
            if (predefinedInternalNameForClass == null) {
                predefinedInternalNameForClass = computeInternalName(classDescriptor2, typeMappingConfiguration);
            }
            return predefinedInternalNameForClass + '$' + identifier;
        }
        throw new IllegalArgumentException("Unexpected container: " + containingDeclaration + " for " + classDescriptor);
    }
}
