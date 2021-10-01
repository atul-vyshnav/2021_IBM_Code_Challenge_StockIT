package expo.modules.p019av.progress;

import android.os.Handler;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\"\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00042\u0010\u0010\n\u001a\f\u0012\u0004\u0012\u00020\b0\u000bj\u0002`\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r"}, mo40253d2 = {"Lexpo/modules/av/progress/AndroidLooperTimeMachine;", "Lexpo/modules/av/progress/TimeMachine;", "()V", "time", "", "getTime", "()J", "scheduleAt", "", "intervalMillis", "callback", "Lkotlin/Function0;", "Lexpo/modules/av/progress/TimeMachineTick;", "expo-av_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* renamed from: expo.modules.av.progress.AndroidLooperTimeMachine */
/* compiled from: AndroidLooperTimeMachine.kt */
public final class AndroidLooperTimeMachine implements TimeMachine {
    public void scheduleAt(long j, Function0<Unit> function0) {
        Intrinsics.checkNotNullParameter(function0, "callback");
        new Handler().postDelayed(new AndroidLooperTimeMachine$sam$java_lang_Runnable$0(function0), j);
    }

    public long getTime() {
        return System.currentTimeMillis();
    }
}
