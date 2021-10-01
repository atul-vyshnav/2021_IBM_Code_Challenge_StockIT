package expo.modules.imagepicker;

import android.os.Bundle;
import com.onesignal.OneSignalDbContract;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.Promise;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\"\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0016J\u0012\u0010\u000e\u001a\u00020\b2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011"}, mo40253d2 = {"Lexpo/modules/imagepicker/PendingPromise;", "Lorg/unimodules/core/Promise;", "pickerResultsStore", "Lexpo/modules/imagepicker/PickerResultsStore;", "isBase64", "", "(Lexpo/modules/imagepicker/PickerResultsStore;Z)V", "reject", "", "code", "", "message", "e", "", "resolve", "value", "", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: PendingPromise.kt */
public final class PendingPromise implements Promise {
    private final boolean isBase64;
    private final PickerResultsStore pickerResultsStore;

    public /* synthetic */ void reject(String str, String str2) {
        Promise.CC.$default$reject((Promise) this, str, str2);
    }

    public /* synthetic */ void reject(String str, Throwable th) {
        Promise.CC.$default$reject((Promise) this, str, th);
    }

    public /* synthetic */ void reject(Throwable th) {
        Promise.CC.$default$reject(this, th);
    }

    public PendingPromise(PickerResultsStore pickerResultsStore2, boolean z) {
        Intrinsics.checkNotNullParameter(pickerResultsStore2, "pickerResultsStore");
        this.pickerResultsStore = pickerResultsStore2;
        this.isBase64 = z;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ PendingPromise(PickerResultsStore pickerResultsStore2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(pickerResultsStore2, (i & 2) != 0 ? false : z);
    }

    public void resolve(Object obj) throws IllegalArgumentException {
        if (obj instanceof Bundle) {
            if (this.isBase64) {
                Bundle bundle = (Bundle) obj;
                if (bundle.containsKey("type")) {
                    bundle.putBoolean("base64", Intrinsics.areEqual((Object) bundle.getString("type"), (Object) "image"));
                }
            }
            this.pickerResultsStore.addPendingResult((Bundle) obj);
            return;
        }
        throw new IllegalArgumentException("Can not resolve 'DestroyedPromise' with anything else then 'Bundle'.");
    }

    public void reject(String str, String str2, Throwable th) {
        Intrinsics.checkNotNullParameter(str, "code");
        Intrinsics.checkNotNullParameter(str2, OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE);
        PickerResultsStore pickerResultsStore2 = this.pickerResultsStore;
        Bundle bundle = new Bundle();
        bundle.putString("code", str);
        bundle.putString(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, str2);
        if (th != null) {
            bundle.putString("exception", th.toString());
        }
        Unit unit = Unit.INSTANCE;
        pickerResultsStore2.addPendingResult(bundle);
    }
}
