package expo.modules.errorrecovery;

import android.content.Context;
import android.content.SharedPreferences;
import com.facebook.react.bridge.BaseJavaModule;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0014J\u0016\u0010\u000b\u001a\u0010\u0012\u0004\u0012\u00020\n\u0012\u0006\u0012\u0004\u0018\u00010\r0\fH\u0016J\b\u0010\u000e\u001a\u00020\nH\u0016J\u001a\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\nH\u0014R\u0014\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0015"}, mo40253d2 = {"Lexpo/modules/errorrecovery/ErrorRecoveryModule;", "Lorg/unimodules/core/ExportedModule;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "mSharedPreferences", "Landroid/content/SharedPreferences;", "getMSharedPreferences", "()Landroid/content/SharedPreferences;", "consumeRecoveryProps", "", "getConstants", "", "", "getName", "saveRecoveryProps", "", "props", "promise", "Lorg/unimodules/core/Promise;", "setRecoveryProps", "expo-error-recovery_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ErrorRecoveryModule.kt */
public class ErrorRecoveryModule extends ExportedModule {
    private final SharedPreferences mSharedPreferences;

    public String getName() {
        return "ExpoErrorRecovery";
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ErrorRecoveryModule(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(ErrorRecoveryModuleKt.RECOVERY_STORE, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences, "context.applicationConte…RE, Context.MODE_PRIVATE)");
        this.mSharedPreferences = sharedPreferences;
    }

    /* access modifiers changed from: protected */
    public SharedPreferences getMSharedPreferences() {
        return this.mSharedPreferences;
    }

    @ExpoMethod
    public final void saveRecoveryProps(String str, Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        if (str != null) {
            setRecoveryProps(str);
        }
        promise.resolve((Object) null);
    }

    public Map<String, Object> getConstants() {
        return MapsKt.mapOf(TuplesKt.m1160to("recoveredProps", consumeRecoveryProps()));
    }

    /* access modifiers changed from: protected */
    public void setRecoveryProps(String str) {
        Intrinsics.checkNotNullParameter(str, "props");
        getMSharedPreferences().edit().putString("recoveredProps", str).commit();
    }

    /* access modifiers changed from: protected */
    public String consumeRecoveryProps() {
        String string = getMSharedPreferences().getString("recoveredProps", (String) null);
        if (string == null) {
            return null;
        }
        getMSharedPreferences().edit().remove("recoveredProps").commit();
        return string;
    }
}
