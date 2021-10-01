package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancementBuilder;

/* renamed from: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.PredefinedEnhancementInfoKt$PREDEFINED_FUNCTION_ENHANCEMENT_INFO_BY_SIGNATURE$1$1$5$7 */
/* compiled from: predefinedEnhancementInfo.kt */
final class C3235xb2505177 extends Lambda implements Function1<SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder, Unit> {
    final /* synthetic */ String $JFFunction;
    final /* synthetic */ String $JLObject;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3235xb2505177(String str, String str2) {
        super(1);
        this.$JLObject = str;
        this.$JFFunction = str2;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(SignatureEnhancementBuilder.ClassEnhancementBuilder.FunctionEnhancementBuilder functionEnhancementBuilder) {
        Intrinsics.checkNotNullParameter(functionEnhancementBuilder, "<this>");
        functionEnhancementBuilder.parameter(this.$JLObject, PredefinedEnhancementInfoKt.NOT_PLATFORM);
        functionEnhancementBuilder.parameter(this.$JFFunction, PredefinedEnhancementInfoKt.NOT_PLATFORM, PredefinedEnhancementInfoKt.NOT_PLATFORM, PredefinedEnhancementInfoKt.NOT_PLATFORM);
        functionEnhancementBuilder.returns(this.$JLObject, PredefinedEnhancementInfoKt.NOT_PLATFORM);
    }
}
