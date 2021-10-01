package expo.modules.permissions.requesters;

import android.content.Context;
import android.os.Bundle;
import androidx.core.app.NotificationManagerCompat;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsStatus;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u001c\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo40253d2 = {"Lexpo/modules/permissions/requesters/NotificationRequester;", "Lexpo/modules/permissions/requesters/PermissionRequester;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getAndroidPermissions", "", "", "parseAndroidPermissions", "Landroid/os/Bundle;", "permissionsResponse", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: NotificationRequester.kt */
public final class NotificationRequester implements PermissionRequester {
    private final Context context;

    public NotificationRequester(Context context2) {
        Intrinsics.checkNotNullParameter(context2, "context");
        this.context = context2;
    }

    public List<String> getAndroidPermissions() {
        return CollectionsKt.emptyList();
    }

    public Bundle parseAndroidPermissions(Map<String, PermissionsResponse> map) {
        Intrinsics.checkNotNullParameter(map, "permissionsResponse");
        Bundle bundle = new Bundle();
        boolean areNotificationsEnabled = NotificationManagerCompat.from(this.context).areNotificationsEnabled();
        bundle.putString("status", (areNotificationsEnabled ? PermissionsStatus.GRANTED : PermissionsStatus.DENIED).getStatus());
        bundle.putString(PermissionsResponse.EXPIRES_KEY, "never");
        bundle.putBoolean(PermissionsResponse.CAN_ASK_AGAIN_KEY, areNotificationsEnabled);
        bundle.putBoolean(PermissionsResponse.GRANTED_KEY, areNotificationsEnabled);
        return bundle;
    }
}
