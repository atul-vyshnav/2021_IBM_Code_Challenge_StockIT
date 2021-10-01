package kotlin.reflect.jvm.internal.impl.load.java.lazy.descriptors;

import java.util.Collection;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;
import kotlin.reflect.jvm.internal.impl.utils.DFS;
import kotlin.sequences.SequencesKt;

/* compiled from: LazyJavaStaticClassScope.kt */
final class LazyJavaStaticClassScope$flatMapJavaStaticSupertypesScopes$1 implements DFS.Neighbors<ClassDescriptor> {
    public static final LazyJavaStaticClassScope$flatMapJavaStaticSupertypesScopes$1 INSTANCE = new LazyJavaStaticClassScope$flatMapJavaStaticSupertypesScopes$1();

    LazyJavaStaticClassScope$flatMapJavaStaticSupertypesScopes$1() {
    }

    public final Iterable<ClassDescriptor> getNeighbors(ClassDescriptor classDescriptor) {
        Collection<KotlinType> supertypes = classDescriptor.getTypeConstructor().getSupertypes();
        Intrinsics.checkNotNullExpressionValue(supertypes, "it.typeConstructor.supertypes");
        return SequencesKt.asIterable(SequencesKt.mapNotNull(CollectionsKt.asSequence(supertypes), C32161.INSTANCE));
    }
}
