package kotlin.reflect.jvm.internal.impl.serialization.deserialization;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf;

/* renamed from: kotlin.reflect.jvm.internal.impl.serialization.deserialization.TypeDeserializer$typeConstructor$notFoundClass$typeParametersCount$2 */
/* compiled from: TypeDeserializer.kt */
final class C3339x131ab843 extends Lambda implements Function1<ProtoBuf.Type, Integer> {
    public static final C3339x131ab843 INSTANCE = new C3339x131ab843();

    C3339x131ab843() {
        super(1);
    }

    public final int invoke(ProtoBuf.Type type) {
        Intrinsics.checkNotNullParameter(type, "it");
        return type.getArgumentCount();
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Integer.valueOf(invoke((ProtoBuf.Type) obj));
    }
}
