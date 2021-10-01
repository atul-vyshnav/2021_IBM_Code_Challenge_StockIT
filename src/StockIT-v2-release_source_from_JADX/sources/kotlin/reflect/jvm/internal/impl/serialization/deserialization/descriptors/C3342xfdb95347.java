package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import java.util.Collection;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import kotlin.reflect.jvm.internal.impl.types.KotlinType;

/* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor$DeserializedClassMemberScope$refinedSupertypes$1 */
/* compiled from: DeserializedClassDescriptor.kt */
final class C3342xfdb95347 extends Lambda implements Function0<Collection<? extends KotlinType>> {
    final /* synthetic */ DeserializedClassDescriptor.DeserializedClassMemberScope this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3342xfdb95347(DeserializedClassDescriptor.DeserializedClassMemberScope deserializedClassMemberScope) {
        super(0);
        this.this$0 = deserializedClassMemberScope;
    }

    public final Collection<KotlinType> invoke() {
        return this.this$0.kotlinTypeRefiner.refineSupertypes(this.this$0.getClassDescriptor());
    }
}
