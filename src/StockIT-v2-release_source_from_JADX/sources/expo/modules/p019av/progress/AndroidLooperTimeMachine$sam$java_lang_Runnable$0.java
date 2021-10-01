package expo.modules.p019av.progress;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* renamed from: expo.modules.av.progress.AndroidLooperTimeMachine$sam$java_lang_Runnable$0 */
/* compiled from: AndroidLooperTimeMachine.kt */
final class AndroidLooperTimeMachine$sam$java_lang_Runnable$0 implements Runnable {
    private final /* synthetic */ Function0 function;

    AndroidLooperTimeMachine$sam$java_lang_Runnable$0(Function0 function0) {
        this.function = function0;
    }

    public final /* synthetic */ void run() {
        Intrinsics.checkNotNullExpressionValue(this.function.invoke(), "invoke(...)");
    }
}
