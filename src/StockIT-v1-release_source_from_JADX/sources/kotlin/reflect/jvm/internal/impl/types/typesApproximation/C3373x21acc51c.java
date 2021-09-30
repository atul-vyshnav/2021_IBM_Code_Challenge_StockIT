package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.resolve.calls.inference.CapturedTypeConstructorKt;
import kotlin.reflect.jvm.internal.impl.types.UnwrappedType;

/* renamed from: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$approximateCapturedTypesIfNecessary$1 */
/* compiled from: CapturedTypeApproximation.kt */
final class C3373x21acc51c extends Lambda implements Function1<UnwrappedType, Boolean> {
    public static final C3373x21acc51c INSTANCE = new C3373x21acc51c();

    C3373x21acc51c() {
        super(1);
    }

    public final Boolean invoke(UnwrappedType unwrappedType) {
        Intrinsics.checkNotNullExpressionValue(unwrappedType, "it");
        return Boolean.valueOf(CapturedTypeConstructorKt.isCaptured(unwrappedType));
    }
}
