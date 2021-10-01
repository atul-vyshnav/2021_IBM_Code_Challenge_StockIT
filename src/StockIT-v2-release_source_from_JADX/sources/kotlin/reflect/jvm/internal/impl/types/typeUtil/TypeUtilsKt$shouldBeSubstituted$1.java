package kotlin.reflect.jvm.internal.impl.types.typeUtil;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.types.StubType;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;
import kotlin.reflect.jvm.internal.impl.types.model.TypeVariableTypeConstructorMarker;

/* compiled from: TypeUtils.kt */
final class TypeUtilsKt$shouldBeSubstituted$1 extends Lambda implements Function1<UnwrappedType, Boolean> {
    public static final TypeUtilsKt$shouldBeSubstituted$1 INSTANCE = new TypeUtilsKt$shouldBeSubstituted$1();

    TypeUtilsKt$shouldBeSubstituted$1() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return Boolean.valueOf(invoke((UnwrappedType) obj));
    }

    public final boolean invoke(UnwrappedType unwrappedType) {
        Intrinsics.checkNotNullParameter(unwrappedType, "it");
        return (unwrappedType instanceof StubType) || (unwrappedType.getConstructor() instanceof TypeVariableTypeConstructorMarker);
    }
}
