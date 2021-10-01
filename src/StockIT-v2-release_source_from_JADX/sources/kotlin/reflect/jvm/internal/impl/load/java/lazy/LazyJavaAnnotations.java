package kotlin.reflect.jvm.internal.impl.load.java.lazy;

import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.builtins.StandardNames;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.AnnotationDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.annotations.Annotations;
import kotlin.reflect.jvm.internal.impl.load.java.components.JavaAnnotationMapper;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotation;
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaAnnotationOwner;
import kotlin.reflect.jvm.internal.impl.name.FqName;
import kotlin.reflect.jvm.internal.impl.storage.MemoizedFunctionToNullable;
import kotlin.sequences.SequencesKt;

/* compiled from: LazyJavaAnnotations.kt */
public final class LazyJavaAnnotations implements Annotations {
    private final MemoizedFunctionToNullable<JavaAnnotation, AnnotationDescriptor> annotationDescriptors;
    private final JavaAnnotationOwner annotationOwner;
    /* access modifiers changed from: private */
    public final boolean areAnnotationsFreshlySupported;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final LazyJavaResolverContext f1270c;

    public LazyJavaAnnotations(LazyJavaResolverContext lazyJavaResolverContext, JavaAnnotationOwner javaAnnotationOwner, boolean z) {
        Intrinsics.checkNotNullParameter(lazyJavaResolverContext, "c");
        Intrinsics.checkNotNullParameter(javaAnnotationOwner, "annotationOwner");
        this.f1270c = lazyJavaResolverContext;
        this.annotationOwner = javaAnnotationOwner;
        this.areAnnotationsFreshlySupported = z;
        this.annotationDescriptors = lazyJavaResolverContext.getComponents().getStorageManager().createMemoizedFunctionWithNullableValues(new LazyJavaAnnotations$annotationDescriptors$1(this));
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ LazyJavaAnnotations(LazyJavaResolverContext lazyJavaResolverContext, JavaAnnotationOwner javaAnnotationOwner, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyJavaResolverContext, javaAnnotationOwner, (i & 4) != 0 ? false : z);
    }

    public boolean hasAnnotation(FqName fqName) {
        return Annotations.DefaultImpls.hasAnnotation(this, fqName);
    }

    public AnnotationDescriptor findAnnotation(FqName fqName) {
        Intrinsics.checkNotNullParameter(fqName, "fqName");
        JavaAnnotation findAnnotation = this.annotationOwner.findAnnotation(fqName);
        AnnotationDescriptor annotationDescriptor = findAnnotation == null ? null : (AnnotationDescriptor) this.annotationDescriptors.invoke(findAnnotation);
        return annotationDescriptor == null ? JavaAnnotationMapper.INSTANCE.findMappedJavaAnnotation(fqName, this.annotationOwner, this.f1270c) : annotationDescriptor;
    }

    public Iterator<AnnotationDescriptor> iterator() {
        return SequencesKt.filterNotNull(SequencesKt.plus(SequencesKt.map(CollectionsKt.asSequence(this.annotationOwner.getAnnotations()), this.annotationDescriptors), JavaAnnotationMapper.INSTANCE.findMappedJavaAnnotation(StandardNames.FqNames.deprecated, this.annotationOwner, this.f1270c))).iterator();
    }

    public boolean isEmpty() {
        return this.annotationOwner.getAnnotations().isEmpty() && !this.annotationOwner.isDeprecatedInJavaDoc();
    }
}
