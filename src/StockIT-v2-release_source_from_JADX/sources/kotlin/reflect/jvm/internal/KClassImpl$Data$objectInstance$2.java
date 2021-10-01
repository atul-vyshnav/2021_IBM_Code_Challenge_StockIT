package kotlin.reflect.jvm.internal;

import java.lang.reflect.Field;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.KClassImpl;
import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMapping;
import kotlin.reflect.jvm.internal.impl.builtins.CompanionObjectMappingUtilsKt;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassDescriptor;
import kotlin.reflect.jvm.internal.impl.descriptors.ClassKind;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002H\n¢\u0006\u0004\b\u0003\u0010\u0004"}, mo40253d2 = {"<anonymous>", "T", "", "invoke", "()Ljava/lang/Object;"}, mo40254k = 3, mo40255mv = {1, 4, 1})
/* compiled from: KClassImpl.kt */
final class KClassImpl$Data$objectInstance$2 extends Lambda implements Function0<T> {
    final /* synthetic */ KClassImpl.Data this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KClassImpl$Data$objectInstance$2(KClassImpl.Data data) {
        super(0);
        this.this$0 = data;
    }

    public final T invoke() {
        Field field;
        ClassDescriptor descriptor = this.this$0.getDescriptor();
        if (descriptor.getKind() != ClassKind.OBJECT) {
            return null;
        }
        if (!descriptor.isCompanionObject() || CompanionObjectMappingUtilsKt.isMappedIntrinsicCompanionObject(CompanionObjectMapping.INSTANCE, descriptor)) {
            field = KClassImpl.this.getJClass().getDeclaredField("INSTANCE");
        } else {
            field = KClassImpl.this.getJClass().getEnclosingClass().getDeclaredField(descriptor.getName().asString());
        }
        T t = field.get((Object) null);
        if (t != null) {
            return t;
        }
        throw new NullPointerException("null cannot be cast to non-null type T");
    }
}
