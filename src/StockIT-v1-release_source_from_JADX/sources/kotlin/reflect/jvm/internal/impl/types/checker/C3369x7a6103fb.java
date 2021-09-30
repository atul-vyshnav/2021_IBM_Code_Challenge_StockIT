package kotlin.reflect.jvm.internal.impl.types.checker;

import java.util.Set;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.types.SimpleType;

/* renamed from: kotlin.reflect.jvm.internal.impl.types.checker.TypeIntersector$intersectTypesWithoutIntersectionType$errorMessage$1 */
/* compiled from: IntersectionType.kt */
final class C3369x7a6103fb extends Lambda implements Function0<String> {
    final /* synthetic */ Set<SimpleType> $inputTypes;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3369x7a6103fb(Set<? extends SimpleType> set) {
        super(0);
        this.$inputTypes = set;
    }

    public final String invoke() {
        return Intrinsics.stringPlus("This collections cannot be empty! input types: ", CollectionsKt.joinToString$default(this.$inputTypes, (CharSequence) null, (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 63, (Object) null));
    }
}
