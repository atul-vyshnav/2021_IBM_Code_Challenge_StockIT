package kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.SourceElement;
import kotlin.reflect.jvm.internal.impl.descriptors.impl.EnumEntrySyntheticClassDescriptor;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;
import kotlin.reflect.jvm.internal.impl.name.Name;
import kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor;

/* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.descriptors.DeserializedClassDescriptor$EnumEntryClassDescriptors$enumEntryByName$1 */
/* compiled from: DeserializedClassDescriptor.kt */
final class C3344xaf8327b7 extends Lambda implements Function1<Name, ClassDescriptor> {
    final /* synthetic */ DeserializedClassDescriptor.EnumEntryClassDescriptors this$0;
    final /* synthetic */ DeserializedClassDescriptor this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3344xaf8327b7(DeserializedClassDescriptor.EnumEntryClassDescriptors enumEntryClassDescriptors, DeserializedClassDescriptor deserializedClassDescriptor) {
        super(1);
        this.this$0 = enumEntryClassDescriptors;
        this.this$1 = deserializedClassDescriptor;
    }

    public final ClassDescriptor invoke(Name name) {
        EnumEntrySyntheticClassDescriptor enumEntrySyntheticClassDescriptor;
        Intrinsics.checkNotNullParameter(name, "name");
        ProtoBuf.EnumEntry enumEntry = (ProtoBuf.EnumEntry) this.this$0.enumEntryProtos.get(name);
        if (enumEntry == null) {
            enumEntrySyntheticClassDescriptor = null;
        } else {
            DeserializedClassDescriptor deserializedClassDescriptor = this.this$1;
            Name name2 = name;
            enumEntrySyntheticClassDescriptor = EnumEntrySyntheticClassDescriptor.create(deserializedClassDescriptor.getC().getStorageManager(), deserializedClassDescriptor, name2, this.this$0.enumMemberNames, new DeserializedAnnotations(deserializedClassDescriptor.getC().getStorageManager(), new C3345x65bb8e11(deserializedClassDescriptor, enumEntry)), SourceElement.NO_SOURCE);
        }
        return enumEntrySyntheticClassDescriptor;
    }
}
