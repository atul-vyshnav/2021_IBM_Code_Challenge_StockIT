package expo.modules.splashscreen;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.unimodules.core.Promise;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, mo40253d2 = {"<anonymous>", "", "m", "", "invoke"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: SplashScreenModule.kt */
final class SplashScreenModule$hideAsync$2 extends Lambda implements Function1<String, Unit> {
    final /* synthetic */ Promise $promise;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SplashScreenModule$hideAsync$2(Promise promise) {
        super(1);
        this.$promise = promise;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        invoke((String) obj);
        return Unit.INSTANCE;
    }

    public final void invoke(String str) {
        Intrinsics.checkNotNullParameter(str, "m");
        this.$promise.reject("ERR_SPLASH_SCREEN", str);
    }
}
