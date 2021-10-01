package kotlin.reflect.jvm.internal.impl.load.kotlin;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: SignatureBuildingComponents.kt */
final class SignatureBuildingComponents$jvmDescriptor$1 extends Lambda implements Function1<String, CharSequence> {
    final /* synthetic */ SignatureBuildingComponents this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SignatureBuildingComponents$jvmDescriptor$1(SignatureBuildingComponents signatureBuildingComponents) {
        super(1);
        this.this$0 = signatureBuildingComponents;
    }

    public final CharSequence invoke(String str) {
        Intrinsics.checkNotNullParameter(str, "it");
        return this.this$0.escapeClassName(str);
    }
}
