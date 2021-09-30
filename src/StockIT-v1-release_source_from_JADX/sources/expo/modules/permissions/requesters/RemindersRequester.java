package expo.modules.permissions.requesters;

import android.os.Bundle;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsStatus;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u001c\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tH\u0016¨\u0006\u000b"}, mo40253d2 = {"Lexpo/modules/permissions/requesters/RemindersRequester;", "Lexpo/modules/permissions/requesters/PermissionRequester;", "()V", "getAndroidPermissions", "", "", "parseAndroidPermissions", "Landroid/os/Bundle;", "permissionsResponse", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: RemindersRequester.kt */
public final class RemindersRequester implements PermissionRequester {
    public List<String> getAndroidPermissions() {
        return CollectionsKt.emptyList();
    }

    public Bundle parseAndroidPermissions(Map<String, PermissionsResponse> map) {
        Intrinsics.checkNotNullParameter(map, "permissionsResponse");
        Bundle bundle = new Bundle();
        bundle.putString("status", PermissionsStatus.GRANTED.getStatus());
        bundle.putString(PermissionsResponse.EXPIRES_KEY, "never");
        bundle.putBoolean(PermissionsResponse.CAN_ASK_AGAIN_KEY, true);
        bundle.putBoolean(PermissionsResponse.GRANTED_KEY, true);
        return bundle;
    }
}
