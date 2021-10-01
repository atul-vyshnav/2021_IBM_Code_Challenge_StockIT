package expo.modules.p019av.progress;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\b\n\u0000\n\u0002\u0010\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo40253d2 = {"<anonymous>", "", "invoke"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* renamed from: expo.modules.av.progress.ProgressLooper$scheduleNextTick$1 */
/* compiled from: ProgressLooper.kt */
final class ProgressLooper$scheduleNextTick$1 extends Lambda implements Function0<Unit> {
    final /* synthetic */ ProgressLooper this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    ProgressLooper$scheduleNextTick$1(ProgressLooper progressLooper) {
        super(0);
        this.this$0 = progressLooper;
    }

    public final void invoke() {
        this.this$0.waiting = false;
        Function0 access$getListener$p = this.this$0.listener;
        if (access$getListener$p != null) {
            Unit unit = (Unit) access$getListener$p.invoke();
        }
        this.this$0.scheduleNextTick();
    }
}
