package kotlin.reflect.jvm.internal.impl.types.typesApproximation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.renderer.ClassifierNamePolicy;
import kotlin.reflect.jvm.internal.impl.renderer.DescriptorRendererOptions;

/* renamed from: kotlin.reflect.jvm.internal.impl.types.typesApproximation.CapturedTypeApproximationKt$toTypeProjection$1$descriptorRenderer$1 */
/* compiled from: CapturedTypeApproximation.kt */
final class C3375x8e300b6 extends Lambda implements Function1<DescriptorRendererOptions, Unit> {
    public static final C3375x8e300b6 INSTANCE = new C3375x8e300b6();

    C3375x8e300b6() {
        super(1);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((DescriptorRendererOptions) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(DescriptorRendererOptions descriptorRendererOptions) {
        Intrinsics.checkNotNullParameter(descriptorRendererOptions, "<this>");
        descriptorRendererOptions.setClassifierNamePolicy(ClassifierNamePolicy.FULLY_QUALIFIED.INSTANCE);
    }
}
