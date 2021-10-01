package expo.modules.permissions.requesters;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0016J\u001c\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\n0\tH\u0016¨\u0006\u000b"}, mo40253d2 = {"Lexpo/modules/permissions/requesters/ForegroundLocationRequester;", "Lexpo/modules/permissions/requesters/PermissionRequester;", "()V", "getAndroidPermissions", "", "", "parseAndroidPermissions", "Landroid/os/Bundle;", "permissionsResponse", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ForegroundLocationRequester.kt */
public final class ForegroundLocationRequester implements PermissionRequester {
    public List<String> getAndroidPermissions() {
        return CollectionsKt.listOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION");
    }

    /* JADX WARNING: type inference failed for: r4v0, types: [java.util.Map, java.lang.Object, java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle parseAndroidPermissions(java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse> r4) {
        /*
            r3 = this;
            java.lang.String r0 = "permissionsResponse"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r4, r0)
            android.os.Bundle r0 = expo.modules.permissions.requesters.RequestersHelperKt.parseBasicLocationPermissions(r4)
            java.lang.String r1 = "android.permission.ACCESS_FINE_LOCATION"
            java.lang.Object r1 = kotlin.collections.MapsKt.getValue(r4, r1)
            org.unimodules.interfaces.permissions.PermissionsResponse r1 = (org.unimodules.interfaces.permissions.PermissionsResponse) r1
            java.lang.String r2 = "android.permission.ACCESS_COARSE_LOCATION"
            java.lang.Object r4 = kotlin.collections.MapsKt.getValue(r4, r2)
            org.unimodules.interfaces.permissions.PermissionsResponse r4 = (org.unimodules.interfaces.permissions.PermissionsResponse) r4
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = r1.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r2 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r1 != r2) goto L_0x0024
            java.lang.String r4 = "fine"
            goto L_0x0031
        L_0x0024:
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = r4.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r4 != r1) goto L_0x002f
            java.lang.String r4 = "coarse"
            goto L_0x0031
        L_0x002f:
            java.lang.String r4 = "none"
        L_0x0031:
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            java.lang.String r2 = "accuracy"
            r1.putString(r2, r4)
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            java.lang.String r4 = "android"
            r0.putBundle(r4, r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.permissions.requesters.ForegroundLocationRequester.parseAndroidPermissions(java.util.Map):android.os.Bundle");
    }
}
