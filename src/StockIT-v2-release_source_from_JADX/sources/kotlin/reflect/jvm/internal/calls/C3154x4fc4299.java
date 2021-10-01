package kotlin.reflect.jvm.internal.calls;

import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\u0010\u0000\u001a\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo40253d2 = {"<anonymous>", "", "T", "", "invoke"}, mo40254k = 3, mo40255mv = {1, 4, 1})
/* renamed from: kotlin.reflect.jvm.internal.calls.AnnotationConstructorCallerKt$createAnnotationInstance$toString$2 */
/* compiled from: AnnotationConstructorCaller.kt */
final class C3154x4fc4299 extends Lambda implements Function0<String> {
    final /* synthetic */ Class $annotationClass;
    final /* synthetic */ Map $values;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3154x4fc4299(Class cls, Map map) {
        super(0);
        this.$annotationClass = cls;
        this.$values = map;
    }

    public final String invoke() {
        StringBuilder sb = new StringBuilder();
        sb.append('@');
        sb.append(this.$annotationClass.getCanonicalName());
        CollectionsKt.joinTo$default(this.$values.entrySet(), sb, ", ", "(", ")", 0, (CharSequence) null, C3155xd18867f3.INSTANCE, 48, (Object) null);
        String sb2 = sb.toString();
        Intrinsics.checkNotNullExpressionValue(sb2, "StringBuilder().apply(builderAction).toString()");
        return sb2;
    }
}
