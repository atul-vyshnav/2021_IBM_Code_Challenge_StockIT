package kotlin.reflect.jvm.internal;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.KPropertyImpl;
import kotlin.reflect.jvm.internal.impl.types.TypeUtils;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u000b\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo40253d2 = {"isNotNullProperty", "", "invoke"}, mo40254k = 3, mo40255mv = {1, 4, 1})
/* compiled from: KPropertyImpl.kt */
final class KPropertyImplKt$computeCallerForAccessor$2 extends Lambda implements Function0<Boolean> {
    final /* synthetic */ KPropertyImpl.Accessor $this_computeCallerForAccessor;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    KPropertyImplKt$computeCallerForAccessor$2(KPropertyImpl.Accessor accessor) {
        super(0);
        this.$this_computeCallerForAccessor = accessor;
    }

    public final boolean invoke() {
        return !TypeUtils.isNullableType(this.$this_computeCallerForAccessor.getProperty().getDescriptor().getType());
    }
}
