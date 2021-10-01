package kotlin.collections.unsigned;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\f\n\u0000\n\u0002\u0010(\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001H\n¢\u0006\u0002\b\u0003"}, mo40253d2 = {"<anonymous>", "", "Lkotlin/UInt;", "invoke"}, mo40254k = 3, mo40255mv = {1, 4, 1})
/* compiled from: _UArrays.kt */
final class UArraysKt___UArraysKt$withIndex$1 extends Lambda implements Function0<Iterator<? extends UInt>> {
    final /* synthetic */ int[] $this_withIndex;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UArraysKt___UArraysKt$withIndex$1(int[] iArr) {
        super(0);
        this.$this_withIndex = iArr;
    }

    public final Iterator<UInt> invoke() {
        return UIntArray.m1348iteratorimpl(this.$this_withIndex);
    }
}
