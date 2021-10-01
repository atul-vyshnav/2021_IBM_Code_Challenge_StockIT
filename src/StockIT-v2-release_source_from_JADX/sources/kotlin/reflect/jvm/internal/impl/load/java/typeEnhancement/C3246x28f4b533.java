package kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement;

import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: kotlin.reflect.jvm.internal.impl.load.java.typeEnhancement.SignatureEnhancement$SignatureParts$computeIndexedQualifiersForOverride$1 */
/* compiled from: signatureEnhancement.kt */
final class C3246x28f4b533 extends Lambda implements Function1<Integer, JavaTypeQualifiers> {
    final /* synthetic */ JavaTypeQualifiers[] $computedResult;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C3246x28f4b533(JavaTypeQualifiers[] javaTypeQualifiersArr) {
        super(1);
        this.$computedResult = javaTypeQualifiersArr;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return invoke(((Number) obj).intValue());
    }

    public final JavaTypeQualifiers invoke(int i) {
        JavaTypeQualifiers[] javaTypeQualifiersArr = this.$computedResult;
        return (i < 0 || i > ArraysKt.getLastIndex((T[]) javaTypeQualifiersArr)) ? JavaTypeQualifiers.Companion.getNONE() : javaTypeQualifiersArr[i];
    }
}
