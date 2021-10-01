package expo.modules.permissions.requesters;

import kotlin.Metadata;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¨\u0006\u0006"}, mo40253d2 = {"parseBasicLocationPermissions", "Landroid/os/Bundle;", "permissionsResponse", "", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "expo-permissions_release"}, mo40254k = 2, mo40255mv = {1, 4, 2})
/* compiled from: RequestersHelper.kt */
public final class RequestersHelperKt {
    /* JADX WARNING: type inference failed for: r7v0, types: [java.util.Map, java.lang.Object, java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse>] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final android.os.Bundle parseBasicLocationPermissions(java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse> r7) {
        /*
            java.lang.String r0 = "permissionsResponse"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.lang.String r1 = "android.permission.ACCESS_FINE_LOCATION"
            java.lang.Object r1 = kotlin.collections.MapsKt.getValue(r7, r1)
            org.unimodules.interfaces.permissions.PermissionsResponse r1 = (org.unimodules.interfaces.permissions.PermissionsResponse) r1
            java.lang.String r2 = "android.permission.ACCESS_COARSE_LOCATION"
            java.lang.Object r7 = kotlin.collections.MapsKt.getValue(r7, r2)
            org.unimodules.interfaces.permissions.PermissionsResponse r7 = (org.unimodules.interfaces.permissions.PermissionsResponse) r7
            boolean r2 = r1.getCanAskAgain()
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x002a
            boolean r2 = r7.getCanAskAgain()
            if (r2 == 0) goto L_0x002a
            r2 = 1
            goto L_0x002b
        L_0x002a:
            r2 = 0
        L_0x002b:
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = r7.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r6 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r5 == r6) goto L_0x003d
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = r1.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r6 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r5 != r6) goto L_0x003c
            goto L_0x003d
        L_0x003c:
            r3 = 0
        L_0x003d:
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = r1.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r4 != r5) goto L_0x004c
            org.unimodules.interfaces.permissions.PermissionsStatus r7 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            java.lang.String r7 = r7.getStatus()
            goto L_0x0078
        L_0x004c:
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = r7.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r4 != r5) goto L_0x005b
            org.unimodules.interfaces.permissions.PermissionsStatus r7 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            java.lang.String r7 = r7.getStatus()
            goto L_0x0078
        L_0x005b:
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = r1.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            if (r1 != r4) goto L_0x0072
            org.unimodules.interfaces.permissions.PermissionsStatus r7 = r7.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            if (r7 != r1) goto L_0x0072
            org.unimodules.interfaces.permissions.PermissionsStatus r7 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            java.lang.String r7 = r7.getStatus()
            goto L_0x0078
        L_0x0072:
            org.unimodules.interfaces.permissions.PermissionsStatus r7 = org.unimodules.interfaces.permissions.PermissionsStatus.UNDETERMINED
            java.lang.String r7 = r7.getStatus()
        L_0x0078:
            java.lang.String r1 = "status"
            r0.putString(r1, r7)
            java.lang.String r7 = "expires"
            java.lang.String r1 = "never"
            r0.putString(r7, r1)
            java.lang.String r7 = "canAskAgain"
            r0.putBoolean(r7, r2)
            java.lang.String r7 = "granted"
            r0.putBoolean(r7, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.permissions.requesters.RequestersHelperKt.parseBasicLocationPermissions(java.util.Map):android.os.Bundle");
    }
}
