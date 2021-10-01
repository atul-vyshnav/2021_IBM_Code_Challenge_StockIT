package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns;
import kotlin.reflect.jvm.internal.impl.descriptors.DeclarationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.DescriptorVisibility;
import kotlin.reflect.jvm.internal.impl.descriptors.FieldDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.Modality;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.PropertySetterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ReceiverParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SimpleFunctionDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.TypeParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.PropertyGetterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.incremental.components.LookupLocation;
import kotlin.reflect.jvm.internal.impl.incremental.components.NoLookupLocation;
import kotlin.reflect.jvm.internal.impl.load.java.UtilsKt;
import kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaMethodDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.descriptors.JavaPropertyDescriptor;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.ContextKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext;
import kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaField;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaMethod;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaTypeParameter;
import kotlin.reflect.jvm.internal.impl.load.kotlin.MethodSignatureMappingKt;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorFactory;
import kotlin.reflect.jvm.internal.impl.resolve.DescriptorUtils;
import kotlin.reflect.jvm.internal.impl.resolve.OverridingUtilsKt;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindExclude;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.DescriptorKindFilter;
import kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScopeImpl;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNotNull;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.reflect.jvm.internal.impl.storage.NotNullLazyValue;
import kotlin.reflect.jvm.internal.impl.storage.StorageKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

/* compiled from: LazyJavaScope.kt */
public abstract class LazyJavaScope extends MemberScopeImpl {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties;
    private final NotNullLazyValue<Collection<DeclarationDescriptor>> allDescriptors;

    /* renamed from: c */
    private final LazyJavaResolverContext f1281c;
    private final NotNullLazyValue classNamesLazy$delegate;
    /* access modifiers changed from: private */
    public final MemoizedFunctionToNullable<Name, PropertyDescriptor> declaredField;
    /* access modifiers changed from: private */
    public final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> declaredFunctions;
    private final NotNullLazyValue<DeclaredMemberIndex> declaredMemberIndex;
    private final NotNullLazyValue functionNamesLazy$delegate;
    private final MemoizedFunctionToNotNull<Name, Collection<SimpleFunctionDescriptor>> functions;
    private final LazyJavaScope mainScope;
    private final MemoizedFunctionToNotNull<Name, List<PropertyDescriptor>> properties;
    private final NotNullLazyValue propertyNamesLazy$delegate;

    /* access modifiers changed from: protected */
    public abstract Set<Name> computeClassNames(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1);

    /* access modifiers changed from: protected */
    public abstract Set<Name> computeFunctionNames(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1);

    /* access modifiers changed from: protected */
    public void computeImplicitlyDeclaredFunctions(Collection<SimpleFunctionDescriptor> collection, Name name) {
        Intrinsics.checkNotNullParameter(collection, "result");
        Intrinsics.checkNotNullParameter(name, "name");
    }

    /* access modifiers changed from: protected */
    public abstract DeclaredMemberIndex computeMemberIndex();

    /* access modifiers changed from: protected */
    public abstract void computeNonDeclaredFunctions(Collection<SimpleFunctionDescriptor> collection, Name name);

    /* access modifiers changed from: protected */
    public abstract void computeNonDeclaredProperties(Name name, Collection<PropertyDescriptor> collection);

    /* access modifiers changed from: protected */
    public abstract Set<Name> computePropertyNames(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1);

    /* access modifiers changed from: protected */
    public abstract ReceiverParameterDescriptor getDispatchReceiverParameter();

    /* access modifiers changed from: protected */
    public abstract DeclarationDescriptor getOwnerDescriptor();

    /* access modifiers changed from: protected */
    public boolean isVisibleAsFunction(JavaMethodDescriptor javaMethodDescriptor) {
        Intrinsics.checkNotNullParameter(javaMethodDescriptor, "<this>");
        return true;
    }

    /* access modifiers changed from: protected */
    public abstract MethodSignatureData resolveMethodSignature(JavaMethod javaMethod, List<? extends TypeParameterDescriptor> list, KotlinType kotlinType, List<? extends ValueParameterDescriptor> list2);

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ LazyJavaScope(LazyJavaResolverContext lazyJavaResolverContext, LazyJavaScope lazyJavaScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyJavaResolverContext, (i & 2) != 0 ? null : lazyJavaScope);
    }

    /* access modifiers changed from: protected */
    public final LazyJavaResolverContext getC() {
        return this.f1281c;
    }

    /* access modifiers changed from: protected */
    public final LazyJavaScope getMainScope() {
        return this.mainScope;
    }

    public LazyJavaScope(LazyJavaResolverContext lazyJavaResolverContext, LazyJavaScope lazyJavaScope) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "c");
        this.f1281c = lazyJavaResolverContext;
        this.mainScope = lazyJavaScope;
        this.allDescriptors = lazyJavaResolverContext.getStorageManager().createRecursionTolerantLazyValue(new LazyJavaScope$allDescriptors$1(this), CollectionsKt.emptyList());
        this.declaredMemberIndex = this.f1281c.getStorageManager().createLazyValue(new LazyJavaScope$declaredMemberIndex$1(this));
        this.declaredFunctions = this.f1281c.getStorageManager().createMemoizedFunction(new LazyJavaScope$declaredFunctions$1(this));
        this.declaredField = this.f1281c.getStorageManager().createMemoizedFunctionWithNullableValues(new LazyJavaScope$declaredField$1(this));
        this.functions = this.f1281c.getStorageManager().createMemoizedFunction(new LazyJavaScope$functions$1(this));
        this.functionNamesLazy$delegate = this.f1281c.getStorageManager().createLazyValue(new LazyJavaScope$functionNamesLazy$2(this));
        this.propertyNamesLazy$delegate = this.f1281c.getStorageManager().createLazyValue(new LazyJavaScope$propertyNamesLazy$2(this));
        this.classNamesLazy$delegate = this.f1281c.getStorageManager().createLazyValue(new LazyJavaScope$classNamesLazy$2(this));
        this.properties = this.f1281c.getStorageManager().createMemoizedFunction(new LazyJavaScope$properties$1(this));
    }

    /* access modifiers changed from: protected */
    public final NotNullLazyValue<Collection<DeclarationDescriptor>> getAllDescriptors() {
        return this.allDescriptors;
    }

    /* access modifiers changed from: protected */
    public final NotNullLazyValue<DeclaredMemberIndex> getDeclaredMemberIndex() {
        return this.declaredMemberIndex;
    }

    /* access modifiers changed from: private */
    public final void retainMostSpecificMethods(Set<SimpleFunctionDescriptor> set) {
        Map linkedHashMap = new LinkedHashMap();
        for (Object next : set) {
            String computeJvmDescriptor$default = MethodSignatureMappingKt.computeJvmDescriptor$default((SimpleFunctionDescriptor) next, false, false, 2, (Object) null);
            Object obj = linkedHashMap.get(computeJvmDescriptor$default);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(computeJvmDescriptor$default, obj);
            }
            ((List) obj).add(next);
        }
        for (List list : linkedHashMap.values()) {
            if (list.size() != 1) {
                Collection collection = list;
                Collection selectMostSpecificInEachOverridableGroup = OverridingUtilsKt.selectMostSpecificInEachOverridableGroup(collection, LazyJavaScope$retainMostSpecificMethods$mostSpecificMethods$1.INSTANCE);
                set.removeAll(collection);
                set.addAll(selectMostSpecificInEachOverridableGroup);
            }
        }
    }

    /* compiled from: LazyJavaScope.kt */
    protected static final class MethodSignatureData {
        private final List<String> errors;
        private final boolean hasStableParameterNames;
        private final KotlinType receiverType;
        private final KotlinType returnType;
        private final List<TypeParameterDescriptor> typeParameters;
        private final List<ValueParameterDescriptor> valueParameters;

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MethodSignatureData)) {
                return false;
            }
            MethodSignatureData methodSignatureData = (MethodSignatureData) obj;
            return Intrinsics.areEqual((Object) this.returnType, (Object) methodSignatureData.returnType) && Intrinsics.areEqual((Object) this.receiverType, (Object) methodSignatureData.receiverType) && Intrinsics.areEqual((Object) this.valueParameters, (Object) methodSignatureData.valueParameters) && Intrinsics.areEqual((Object) this.typeParameters, (Object) methodSignatureData.typeParameters) && this.hasStableParameterNames == methodSignatureData.hasStableParameterNames && Intrinsics.areEqual((Object) this.errors, (Object) methodSignatureData.errors);
        }

        public int hashCode() {
            int hashCode = this.returnType.hashCode() * 31;
            KotlinType kotlinType = this.receiverType;
            int hashCode2 = (((((hashCode + (kotlinType == null ? 0 : kotlinType.hashCode())) * 31) + this.valueParameters.hashCode()) * 31) + this.typeParameters.hashCode()) * 31;
            boolean z = this.hasStableParameterNames;
            if (z) {
                z = true;
            }
            return ((hashCode2 + (z ? 1 : 0)) * 31) + this.errors.hashCode();
        }

        public String toString() {
            return "MethodSignatureData(returnType=" + this.returnType + ", receiverType=" + this.receiverType + ", valueParameters=" + this.valueParameters + ", typeParameters=" + this.typeParameters + ", hasStableParameterNames=" + this.hasStableParameterNames + ", errors=" + this.errors + ')';
        }

        public MethodSignatureData(KotlinType kotlinType, KotlinType kotlinType2, List<? extends ValueParameterDescriptor> list, List<? extends TypeParameterDescriptor> list2, boolean z, List<String> list3) {
            Intrinsics.checkNotNullParameter(kotlinType, "returnType");
            Intrinsics.checkNotNullParameter(list, "valueParameters");
            Intrinsics.checkNotNullParameter(list2, "typeParameters");
            Intrinsics.checkNotNullParameter(list3, "errors");
            this.returnType = kotlinType;
            this.receiverType = kotlinType2;
            this.valueParameters = list;
            this.typeParameters = list2;
            this.hasStableParameterNames = z;
            this.errors = list3;
        }

        public final KotlinType getReturnType() {
            return this.returnType;
        }

        public final KotlinType getReceiverType() {
            return this.receiverType;
        }

        public final List<ValueParameterDescriptor> getValueParameters() {
            return this.valueParameters;
        }

        public final List<TypeParameterDescriptor> getTypeParameters() {
            return this.typeParameters;
        }

        public final boolean getHasStableParameterNames() {
            return this.hasStableParameterNames;
        }

        public final List<String> getErrors() {
            return this.errors;
        }
    }

    /* access modifiers changed from: protected */
    public final JavaMethodDescriptor resolveMethodToFunctionDescriptor(JavaMethod javaMethod) {
        ReceiverParameterDescriptor receiverParameterDescriptor;
        Map<K, V> map;
        JavaMethod javaMethod2 = javaMethod;
        Intrinsics.checkNotNullParameter(javaMethod2, "method");
        JavaMethodDescriptor createJavaMethod = JavaMethodDescriptor.createJavaMethod(getOwnerDescriptor(), LazyJavaAnnotationsKt.resolveAnnotations(this.f1281c, javaMethod2), javaMethod.getName(), this.f1281c.getComponents().getSourceElementFactory().source(javaMethod2), ((DeclaredMemberIndex) this.declaredMemberIndex.invoke()).findRecordComponentByName(javaMethod.getName()) != null && javaMethod.getValueParameters().isEmpty());
        Intrinsics.checkNotNullExpressionValue(createJavaMethod, "createJavaMethod(\n            ownerDescriptor, annotations, method.name, c.components.sourceElementFactory.source(method),\n            declaredMemberIndex().findRecordComponentByName(method.name) != null && method.valueParameters.isEmpty()\n        )");
        LazyJavaResolverContext childForMethod$default = ContextKt.childForMethod$default(this.f1281c, createJavaMethod, javaMethod2, 0, 4, (Object) null);
        Iterable<JavaTypeParameter> typeParameters = javaMethod.getTypeParameters();
        Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(typeParameters, 10));
        for (JavaTypeParameter resolveTypeParameter : typeParameters) {
            TypeParameterDescriptor resolveTypeParameter2 = childForMethod$default.getTypeParameterResolver().resolveTypeParameter(resolveTypeParameter);
            Intrinsics.checkNotNull(resolveTypeParameter2);
            arrayList.add(resolveTypeParameter2);
        }
        ResolvedValueParameters resolveValueParameters = resolveValueParameters(childForMethod$default, createJavaMethod, javaMethod.getValueParameters());
        MethodSignatureData resolveMethodSignature = resolveMethodSignature(javaMethod2, (List) arrayList, computeMethodReturnType(javaMethod2, childForMethod$default), resolveValueParameters.getDescriptors());
        KotlinType receiverType = resolveMethodSignature.getReceiverType();
        if (receiverType == null) {
            receiverParameterDescriptor = null;
        } else {
            receiverParameterDescriptor = DescriptorFactory.createExtensionReceiverParameterForCallable(createJavaMethod, receiverType, Annotations.Companion.getEMPTY());
        }
        ReceiverParameterDescriptor receiverParameterDescriptor2 = receiverParameterDescriptor;
        ReceiverParameterDescriptor dispatchReceiverParameter = getDispatchReceiverParameter();
        List<TypeParameterDescriptor> typeParameters2 = resolveMethodSignature.getTypeParameters();
        List<ValueParameterDescriptor> valueParameters = resolveMethodSignature.getValueParameters();
        KotlinType returnType = resolveMethodSignature.getReturnType();
        Modality convertFromFlags = Modality.Companion.convertFromFlags(false, javaMethod.isAbstract(), !javaMethod.isFinal());
        DescriptorVisibility descriptorVisibility = UtilsKt.toDescriptorVisibility(javaMethod.getVisibility());
        if (resolveMethodSignature.getReceiverType() != null) {
            map = MapsKt.mapOf(TuplesKt.m1160to(JavaMethodDescriptor.ORIGINAL_VALUE_PARAMETER_FOR_EXTENSION_RECEIVER, CollectionsKt.first(resolveValueParameters.getDescriptors())));
        } else {
            map = MapsKt.emptyMap();
        }
        createJavaMethod.initialize(receiverParameterDescriptor2, dispatchReceiverParameter, typeParameters2, valueParameters, returnType, convertFromFlags, descriptorVisibility, map);
        createJavaMethod.setParameterNamesStatus(resolveMethodSignature.getHasStableParameterNames(), resolveValueParameters.getHasSynthesizedNames());
        if (!resolveMethodSignature.getErrors().isEmpty()) {
            childForMethod$default.getComponents().getSignaturePropagator().reportSignatureErrors(createJavaMethod, resolveMethodSignature.getErrors());
        }
        return createJavaMethod;
    }

    /* access modifiers changed from: protected */
    public final KotlinType computeMethodReturnType(JavaMethod javaMethod, LazyJavaResolverContext lazyJavaResolverContext) {
        Intrinsics.checkNotNullParameter(javaMethod, "method");
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "c");
        return lazyJavaResolverContext.getTypeResolver().transformJavaType(javaMethod.getReturnType(), JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, javaMethod.getContainingClass().isAnnotationType(), (TypeParameterDescriptor) null, 2, (Object) null));
    }

    /* compiled from: LazyJavaScope.kt */
    protected static final class ResolvedValueParameters {
        private final List<ValueParameterDescriptor> descriptors;
        private final boolean hasSynthesizedNames;

        public ResolvedValueParameters(List<? extends ValueParameterDescriptor> list, boolean z) {
            Intrinsics.checkNotNullParameter(list, "descriptors");
            this.descriptors = list;
            this.hasSynthesizedNames = z;
        }

        public final List<ValueParameterDescriptor> getDescriptors() {
            return this.descriptors;
        }

        public final boolean getHasSynthesizedNames() {
            return this.hasSynthesizedNames;
        }
    }

    /* JADX WARNING: type inference failed for: r14v6, types: [kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType] */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0089  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0102  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0109  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope.ResolvedValueParameters resolveValueParameters(kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext r22, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor r23, java.util.List<? extends kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter> r24) {
        /*
            r21 = this;
            r0 = r22
            r1 = r23
            r2 = r24
            java.lang.String r3 = "c"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r0, r3)
            java.lang.String r3 = "function"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r3)
            java.lang.String r3 = "jValueParameters"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r2, r3)
            java.util.LinkedHashSet r3 = new java.util.LinkedHashSet
            r3.<init>()
            java.util.Set r3 = (java.util.Set) r3
            r4 = r2
            java.lang.Iterable r4 = (java.lang.Iterable) r4
            java.lang.Iterable r4 = kotlin.collections.CollectionsKt.withIndex(r4)
            java.util.ArrayList r5 = new java.util.ArrayList
            r6 = 10
            int r6 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r4, r6)
            r5.<init>(r6)
            java.util.Collection r5 = (java.util.Collection) r5
            java.util.Iterator r4 = r4.iterator()
            r6 = 0
            r7 = 0
        L_0x0036:
            boolean r8 = r4.hasNext()
            if (r8 == 0) goto L_0x0171
            java.lang.Object r8 = r4.next()
            kotlin.collections.IndexedValue r8 = (kotlin.collections.IndexedValue) r8
            int r12 = r8.component1()
            java.lang.Object r8 = r8.component2()
            kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter r8 = (kotlin.reflect.jvm.internal.impl.load.java.structure.JavaValueParameter) r8
            r9 = r8
            kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner r9 = (kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner) r9
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations r13 = kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaAnnotationsKt.resolveAnnotations(r0, r9)
            kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage r9 = kotlin.reflect.jvm.internal.impl.load.java.components.TypeUsage.COMMON
            r10 = 3
            r11 = 0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeAttributes r9 = kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolverKt.toAttributes$default(r9, r6, r11, r10, r11)
            kotlin.reflect.jvm.internal.impl.name.FqName r10 = kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames.PARAMETER_NAME_FQ_NAME
            java.lang.String r14 = "PARAMETER_NAME_FQ_NAME"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r14)
            kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor r10 = r13.findAnnotation(r10)
            if (r10 != 0) goto L_0x006a
            r10 = r11
            goto L_0x006e
        L_0x006a:
            kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue r10 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.firstArgument(r10)
        L_0x006e:
            if (r10 != 0) goto L_0x0072
        L_0x0070:
            r10 = r11
            goto L_0x0082
        L_0x0072:
            boolean r14 = r10 instanceof kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue
            if (r14 != 0) goto L_0x0077
            r10 = r11
        L_0x0077:
            kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue r10 = (kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue) r10
            if (r10 != 0) goto L_0x007c
            goto L_0x0070
        L_0x007c:
            java.lang.Object r10 = r10.getValue()
            java.lang.String r10 = (java.lang.String) r10
        L_0x0082:
            boolean r14 = r8.isVararg()
            r15 = 1
            if (r14 == 0) goto L_0x00bb
            kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType r14 = r8.getType()
            boolean r6 = r14 instanceof kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType
            if (r6 == 0) goto L_0x0094
            r11 = r14
            kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType r11 = (kotlin.reflect.jvm.internal.impl.load.java.structure.JavaArrayType) r11
        L_0x0094:
            if (r11 == 0) goto L_0x00af
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver r6 = r22.getTypeResolver()
            kotlin.reflect.jvm.internal.impl.types.KotlinType r6 = r6.transformArrayType(r11, r9, r15)
            kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor r9 = r22.getModule()
            kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns r9 = r9.getBuiltIns()
            kotlin.reflect.jvm.internal.impl.types.KotlinType r9 = r9.getArrayElementType(r6)
            kotlin.Pair r6 = kotlin.TuplesKt.m1160to(r6, r9)
            goto L_0x00cb
        L_0x00af:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            java.lang.String r1 = "Vararg parameter should be an array: "
            java.lang.String r1 = kotlin.jvm.internal.Intrinsics.stringPlus(r1, r8)
            r0.<init>(r1)
            throw r0
        L_0x00bb:
            kotlin.reflect.jvm.internal.impl.load.java.lazy.types.JavaTypeResolver r6 = r22.getTypeResolver()
            kotlin.reflect.jvm.internal.impl.load.java.structure.JavaType r14 = r8.getType()
            kotlin.reflect.jvm.internal.impl.types.KotlinType r6 = r6.transformJavaType(r14, r9)
            kotlin.Pair r6 = kotlin.TuplesKt.m1160to(r6, r11)
        L_0x00cb:
            java.lang.Object r9 = r6.component1()
            r14 = r9
            kotlin.reflect.jvm.internal.impl.types.KotlinType r14 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r14
            java.lang.Object r6 = r6.component2()
            r19 = r6
            kotlin.reflect.jvm.internal.impl.types.KotlinType r19 = (kotlin.reflect.jvm.internal.impl.types.KotlinType) r19
            kotlin.reflect.jvm.internal.impl.name.Name r6 = r23.getName()
            java.lang.String r6 = r6.asString()
            java.lang.String r9 = "equals"
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r6, (java.lang.Object) r9)
            if (r6 == 0) goto L_0x0109
            int r6 = r24.size()
            if (r6 != r15) goto L_0x0109
            kotlin.reflect.jvm.internal.impl.descriptors.ModuleDescriptor r6 = r22.getModule()
            kotlin.reflect.jvm.internal.impl.builtins.KotlinBuiltIns r6 = r6.getBuiltIns()
            kotlin.reflect.jvm.internal.impl.types.SimpleType r6 = r6.getNullableAnyType()
            boolean r6 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r6, (java.lang.Object) r14)
            if (r6 == 0) goto L_0x0109
            java.lang.String r6 = "other"
            kotlin.reflect.jvm.internal.impl.name.Name r6 = kotlin.reflect.jvm.internal.impl.name.Name.identifier(r6)
            goto L_0x0140
        L_0x0109:
            if (r10 == 0) goto L_0x0124
            r6 = r10
            java.lang.CharSequence r6 = (java.lang.CharSequence) r6
            int r6 = r6.length()
            if (r6 <= 0) goto L_0x0116
            r6 = 1
            goto L_0x0117
        L_0x0116:
            r6 = 0
        L_0x0117:
            if (r6 == 0) goto L_0x0124
            boolean r6 = r3.add(r10)
            if (r6 == 0) goto L_0x0124
            kotlin.reflect.jvm.internal.impl.name.Name r6 = kotlin.reflect.jvm.internal.impl.name.Name.identifier(r10)
            goto L_0x0140
        L_0x0124:
            kotlin.reflect.jvm.internal.impl.name.Name r6 = r8.getName()
            if (r6 != 0) goto L_0x012b
            r7 = 1
        L_0x012b:
            if (r6 != 0) goto L_0x0140
            java.lang.Integer r6 = java.lang.Integer.valueOf(r12)
            java.lang.String r9 = "p"
            java.lang.String r6 = kotlin.jvm.internal.Intrinsics.stringPlus(r9, r6)
            kotlin.reflect.jvm.internal.impl.name.Name r6 = kotlin.reflect.jvm.internal.impl.name.Name.identifier(r6)
            java.lang.String r9 = "identifier(\"p$index\")"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r9)
        L_0x0140:
            java.lang.String r9 = "if (function.name.asString() == \"equals\" &&\n                jValueParameters.size == 1 &&\n                c.module.builtIns.nullableAnyType == outType\n            ) {\n                // This is a hack to prevent numerous warnings on Kotlin classes that inherit Java classes: if you override \"equals\" in such\n                // class without this hack, you'll be warned that in the superclass the name is \"p0\" (regardless of the fact that it's\n                // \"other\" in Any)\n                // TODO: fix Java parameter name loading logic somehow (don't always load \"p0\", \"p1\", etc.)\n                Name.identifier(\"other\")\n            } else if (parameterName != null && parameterName.isNotEmpty() && usedNames.add(parameterName)) {\n                Name.identifier(parameterName)\n            } else {\n                // TODO: parameter names may be drawn from attached sources, which is slow; it's better to make them lazy\n                val javaName = javaParameter.name\n                if (javaName == null) synthesizedNames = true\n                javaName ?: Name.identifier(\"p$index\")\n            }"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r6, r9)
            kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl r15 = new kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl
            r10 = r1
            kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor r10 = (kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor) r10
            r11 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            kotlin.reflect.jvm.internal.impl.load.java.lazy.JavaResolverComponents r9 = r22.getComponents()
            kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElementFactory r9 = r9.getSourceElementFactory()
            kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement r8 = (kotlin.reflect.jvm.internal.impl.load.java.structure.JavaElement) r8
            kotlin.reflect.jvm.internal.impl.load.java.sources.JavaSourceElement r8 = r9.source(r8)
            r20 = r8
            kotlin.reflect.jvm.internal.impl.descriptors.SourceElement r20 = (kotlin.reflect.jvm.internal.impl.descriptors.SourceElement) r20
            r9 = r15
            r8 = r14
            r14 = r6
            r6 = r15
            r15 = r8
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20)
            r5.add(r6)
            r6 = 0
            goto L_0x0036
        L_0x0171:
            java.util.List r5 = (java.util.List) r5
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.util.List r0 = kotlin.collections.CollectionsKt.toList(r5)
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$ResolvedValueParameters r1 = new kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$ResolvedValueParameters
            r1.<init>(r0, r7)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope.resolveValueParameters(kotlin.reflect.jvm.internal.impl.load.java.lazy.LazyJavaResolverContext, kotlin.reflect.jvm.internal.impl.descriptors.FunctionDescriptor, java.util.List):kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaScope$ResolvedValueParameters");
    }

    static {
        Class<LazyJavaScope> cls = LazyJavaScope.class;
        $$delegatedProperties = new KProperty[]{Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "functionNamesLazy", "getFunctionNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "propertyNamesLazy", "getPropertyNamesLazy()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(cls), "classNamesLazy", "getClassNamesLazy()Ljava/util/Set;"))};
    }

    private final Set<Name> getFunctionNamesLazy() {
        return (Set) StorageKt.getValue(this.functionNamesLazy$delegate, (Object) this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final Set<Name> getPropertyNamesLazy() {
        return (Set) StorageKt.getValue(this.propertyNamesLazy$delegate, (Object) this, (KProperty<?>) $$delegatedProperties[1]);
    }

    private final Set<Name> getClassNamesLazy() {
        return (Set) StorageKt.getValue(this.classNamesLazy$delegate, (Object) this, (KProperty<?>) $$delegatedProperties[2]);
    }

    public Set<Name> getFunctionNames() {
        return getFunctionNamesLazy();
    }

    public Set<Name> getVariableNames() {
        return getPropertyNamesLazy();
    }

    public Set<Name> getClassifierNames() {
        return getClassNamesLazy();
    }

    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(lookupLocation, GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        if (!getFunctionNames().contains(name)) {
            return CollectionsKt.emptyList();
        }
        return this.functions.invoke(name);
    }

    /* access modifiers changed from: private */
    public final PropertyDescriptor resolveProperty(JavaField javaField) {
        PropertyDescriptorImpl createPropertyDescriptor = createPropertyDescriptor(javaField);
        createPropertyDescriptor.initialize((PropertyGetterDescriptorImpl) null, (PropertySetterDescriptor) null, (FieldDescriptor) null, (FieldDescriptor) null);
        createPropertyDescriptor.setType(getPropertyType(javaField), CollectionsKt.emptyList(), getDispatchReceiverParameter(), (ReceiverParameterDescriptor) null);
        if (DescriptorUtils.shouldRecordInitializerForProperty(createPropertyDescriptor, createPropertyDescriptor.getType())) {
            createPropertyDescriptor.setCompileTimeInitializer(this.f1281c.getStorageManager().createNullableLazyValue(new LazyJavaScope$resolveProperty$1(this, javaField, createPropertyDescriptor)));
        }
        PropertyDescriptor propertyDescriptor = createPropertyDescriptor;
        this.f1281c.getComponents().getJavaResolverCache().recordField(javaField, propertyDescriptor);
        return propertyDescriptor;
    }

    private final PropertyDescriptorImpl createPropertyDescriptor(JavaField javaField) {
        JavaPropertyDescriptor create = JavaPropertyDescriptor.create(getOwnerDescriptor(), LazyJavaAnnotationsKt.resolveAnnotations(this.f1281c, javaField), Modality.FINAL, UtilsKt.toDescriptorVisibility(javaField.getVisibility()), !javaField.isFinal(), javaField.getName(), this.f1281c.getComponents().getSourceElementFactory().source(javaField), isFinalStatic(javaField));
        Intrinsics.checkNotNullExpressionValue(create, "create(\n            ownerDescriptor, annotations, Modality.FINAL, field.visibility.toDescriptorVisibility(), isVar, field.name,\n            c.components.sourceElementFactory.source(field), /* isConst = */ field.isFinalStatic\n        )");
        return create;
    }

    private final boolean isFinalStatic(JavaField javaField) {
        return javaField.isFinal() && javaField.isStatic();
    }

    private final KotlinType getPropertyType(JavaField javaField) {
        boolean z = false;
        KotlinType transformJavaType = this.f1281c.getTypeResolver().transformJavaType(javaField.getType(), JavaTypeResolverKt.toAttributes$default(TypeUsage.COMMON, false, (TypeParameterDescriptor) null, 3, (Object) null));
        if ((KotlinBuiltIns.isPrimitiveType(transformJavaType) || KotlinBuiltIns.isString(transformJavaType)) && isFinalStatic(javaField) && javaField.getHasConstantNotNullInitializer()) {
            z = true;
        }
        if (!z) {
            return transformJavaType;
        }
        KotlinType makeNotNullable = TypeUtils.makeNotNullable(transformJavaType);
        Intrinsics.checkNotNullExpressionValue(makeNotNullable, "makeNotNullable(propertyType)");
        return makeNotNullable;
    }

    public Collection<PropertyDescriptor> getContributedVariables(Name name, LookupLocation lookupLocation) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(lookupLocation, GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID);
        if (!getVariableNames().contains(name)) {
            return CollectionsKt.emptyList();
        }
        return this.properties.invoke(name);
    }

    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(descriptorKindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(function1, "nameFilter");
        return (Collection) this.allDescriptors.invoke();
    }

    /* access modifiers changed from: protected */
    public final List<DeclarationDescriptor> computeDescriptors(DescriptorKindFilter descriptorKindFilter, Function1<? super Name, Boolean> function1) {
        Intrinsics.checkNotNullParameter(descriptorKindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(function1, "nameFilter");
        NoLookupLocation noLookupLocation = NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (descriptorKindFilter.acceptsKinds(DescriptorKindFilter.Companion.getCLASSIFIERS_MASK())) {
            for (Name next : computeClassNames(descriptorKindFilter, function1)) {
                if (function1.invoke(next).booleanValue()) {
                    kotlin.reflect.jvm.internal.impl.utils.CollectionsKt.addIfNotNull(linkedHashSet, getContributedClassifier(next, noLookupLocation));
                }
            }
        }
        if (descriptorKindFilter.acceptsKinds(DescriptorKindFilter.Companion.getFUNCTIONS_MASK()) && !descriptorKindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name next2 : computeFunctionNames(descriptorKindFilter, function1)) {
                if (function1.invoke(next2).booleanValue()) {
                    linkedHashSet.addAll(getContributedFunctions(next2, noLookupLocation));
                }
            }
        }
        if (descriptorKindFilter.acceptsKinds(DescriptorKindFilter.Companion.getVARIABLES_MASK()) && !descriptorKindFilter.getExcludes().contains(DescriptorKindExclude.NonExtensions.INSTANCE)) {
            for (Name next3 : computePropertyNames(descriptorKindFilter, function1)) {
                if (function1.invoke(next3).booleanValue()) {
                    linkedHashSet.addAll(getContributedVariables(next3, noLookupLocation));
                }
            }
        }
        return CollectionsKt.toList(linkedHashSet);
    }

    public String toString() {
        return Intrinsics.stringPlus("Lazy scope for ", getOwnerDescriptor());
    }
}
