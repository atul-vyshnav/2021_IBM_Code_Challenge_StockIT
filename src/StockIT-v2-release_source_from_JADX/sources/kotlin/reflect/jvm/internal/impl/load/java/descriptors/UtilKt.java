package kotlin.reflect.jvm.internal.impl.load.java.descriptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;
import kotlin._Assertions;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.CallableDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.ValueParameterDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.ValueParameterDescriptorImpl;
import kotlin.reflect.jvm.internal.impl.load.java.JvmAnnotationNames;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.resolve.constants.ConstantValue;
import kotlin.reflect.jvm.internal.impl.resolve.constants.StringValue;
import kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* compiled from: util.kt */
public final class UtilKt {
    public static final List<ValueParameterDescriptor> copyValueParameters(Collection<ValueParameterData> collection, Collection<? extends ValueParameterDescriptor> collection2, CallableDescriptor callableDescriptor) {
        Collection<ValueParameterData> collection3 = collection;
        Collection<? extends ValueParameterDescriptor> collection4 = collection2;
        CallableDescriptor callableDescriptor2 = callableDescriptor;
        Intrinsics.checkNotNullParameter(collection3, "newValueParametersTypes");
        Intrinsics.checkNotNullParameter(collection4, "oldValueParameters");
        Intrinsics.checkNotNullParameter(callableDescriptor2, "newOwner");
        boolean z = collection.size() == collection2.size();
        if (!_Assertions.ENABLED || z) {
            Iterable<Pair> zip = CollectionsKt.zip(collection3, collection4);
            Collection arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(zip, 10));
            for (Pair pair : zip) {
                ValueParameterData valueParameterData = (ValueParameterData) pair.component1();
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) pair.component2();
                int index = valueParameterDescriptor.getIndex();
                Annotations annotations = valueParameterDescriptor.getAnnotations();
                Name name = valueParameterDescriptor.getName();
                Intrinsics.checkNotNullExpressionValue(name, "oldParameter.name");
                KotlinType type = valueParameterData.getType();
                boolean hasDefaultValue = valueParameterData.getHasDefaultValue();
                boolean isCrossinline = valueParameterDescriptor.isCrossinline();
                boolean isNoinline = valueParameterDescriptor.isNoinline();
                KotlinType arrayElementType = valueParameterDescriptor.getVarargElementType() != null ? DescriptorUtilsKt.getModule(callableDescriptor2).getBuiltIns().getArrayElementType(valueParameterData.getType()) : null;
                SourceElement source = valueParameterDescriptor.getSource();
                Intrinsics.checkNotNullExpressionValue(source, "oldParameter.source");
                arrayList.add(new ValueParameterDescriptorImpl(callableDescriptor, (ValueParameterDescriptor) null, index, annotations, name, type, hasDefaultValue, isCrossinline, isNoinline, arrayElementType, source));
            }
            return (List) arrayList;
        }
        throw new AssertionError("Different value parameters sizes: Enhanced = " + collection.size() + ", Old = " + collection2.size());
    }

    /* JADX WARNING: type inference failed for: r1v0, types: [kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope getParentJavaStaticClassScope(kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r3) {
        /*
            java.lang.String r0 = "<this>"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r3, r0)
            kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor r3 = kotlin.reflect.jvm.internal.impl.resolve.descriptorUtil.DescriptorUtilsKt.getSuperClassNotAny(r3)
            r0 = 0
            if (r3 != 0) goto L_0x000d
            return r0
        L_0x000d:
            kotlin.reflect.jvm.internal.impl.resolve.scopes.MemberScope r1 = r3.getStaticScope()
            boolean r2 = r1 instanceof kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope
            if (r2 == 0) goto L_0x0018
            r0 = r1
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope r0 = (kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope) r0
        L_0x0018:
            if (r0 != 0) goto L_0x001e
            kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope r0 = getParentJavaStaticClassScope(r3)
        L_0x001e:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.reflect.jvm.internal.impl.load.java.descriptors.UtilKt.getParentJavaStaticClassScope(kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor):kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors.LazyJavaStaticClassScope");
    }

    public static final AnnotationDefaultValue getDefaultValueFromAnnotation(ValueParameterDescriptor valueParameterDescriptor) {
        StringValue stringValue;
        String str;
        ConstantValue<?> firstArgument;
        Intrinsics.checkNotNullParameter(valueParameterDescriptor, "<this>");
        Annotations annotations = valueParameterDescriptor.getAnnotations();
        FqName fqName = JvmAnnotationNames.DEFAULT_VALUE_FQ_NAME;
        Intrinsics.checkNotNullExpressionValue(fqName, "DEFAULT_VALUE_FQ_NAME");
        AnnotationDescriptor findAnnotation = annotations.findAnnotation(fqName);
        if (findAnnotation == null || (firstArgument = DescriptorUtilsKt.firstArgument(findAnnotation)) == null) {
            stringValue = null;
        } else {
            if (!(firstArgument instanceof StringValue)) {
                firstArgument = null;
            }
            stringValue = (StringValue) firstArgument;
        }
        if (stringValue != null && (str = (String) stringValue.getValue()) != null) {
            return new StringDefaultValue(str);
        }
        Annotations annotations2 = valueParameterDescriptor.getAnnotations();
        FqName fqName2 = JvmAnnotationNames.DEFAULT_NULL_FQ_NAME;
        Intrinsics.checkNotNullExpressionValue(fqName2, "DEFAULT_NULL_FQ_NAME");
        if (annotations2.hasAnnotation(fqName2)) {
            return NullDefaultValue.INSTANCE;
        }
        return null;
    }
}
