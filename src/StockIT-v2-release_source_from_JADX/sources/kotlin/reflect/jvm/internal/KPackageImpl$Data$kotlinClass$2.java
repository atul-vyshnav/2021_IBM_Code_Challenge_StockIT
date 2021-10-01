package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.KPackageImpl;
import kotlin.reflect.jvm.internal.impl.descriptors.runtime.components.ReflectKotlinClass;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u0004\u0018\u00010\u0001H\n¢\u0006\u0002\b\u0002"}, mo40253d2 = {"<anonymous>", "Lkotlin/reflect/jvm/internal/impl/descriptors/runtime/components/ReflectKotlinClass;", "invoke"}, mo40254k = 3, mo40255mv = {1, 4, 1})
/* compiled from: KPackageImpl.kt */
final class KPackageImpl$Data$kotlinClass$2 extends Lambda implements Function0<ReflectKotlinClass> {
    final /* synthetic */ KPackageImpl.Data this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KPackageImpl$Data$kotlinClass$2(KPackageImpl.Data data) {
        super(0);
        this.this$0 = data;
    }

    public final ReflectKotlinClass invoke() {
        return ReflectKotlinClass.Factory.create(KPackageImpl.this.getJClass());
    }
}
