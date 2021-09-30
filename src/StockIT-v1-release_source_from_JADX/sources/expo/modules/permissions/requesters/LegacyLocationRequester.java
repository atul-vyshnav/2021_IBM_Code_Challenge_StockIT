package expo.modules.permissions.requesters;

import android.os.Build;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u001c\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\f0\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo40253d2 = {"Lexpo/modules/permissions/requesters/LegacyLocationRequester;", "Lexpo/modules/permissions/requesters/PermissionRequester;", "includeBackgroundPermission", "", "(Z)V", "getAndroidPermissions", "", "", "parseAndroidPermissions", "Landroid/os/Bundle;", "permissionsResponse", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: LegacyLocationRequester.kt */
public final class LegacyLocationRequester implements PermissionRequester {
    private final boolean includeBackgroundPermission;

    public LegacyLocationRequester() {
        this(false, 1, (DefaultConstructorMarker) null);
    }

    public LegacyLocationRequester(boolean z) {
        this.includeBackgroundPermission = z;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ LegacyLocationRequester(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    public List<String> getAndroidPermissions() {
        List<String> mutableListOf = CollectionsKt.mutableListOf("android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION");
        if (this.includeBackgroundPermission && Build.VERSION.SDK_INT >= 29) {
            mutableListOf.add(0, "android.permission.ACCESS_BACKGROUND_LOCATION");
        }
        return mutableListOf;
    }

    /* JADX WARNING: type inference failed for: r9v0, types: [java.util.Map, java.lang.Object, java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse>] */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0060, code lost:
        if (r1.getStatus() != org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED) goto L_0x0063;
     */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle parseAndroidPermissions(java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse> r9) {
        /*
            r8 = this;
            java.lang.String r0 = "permissionsResponse"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r9, r0)
            android.os.Bundle r0 = expo.modules.permissions.requesters.RequestersHelperKt.parseBasicLocationPermissions(r9)
            java.lang.String r1 = "android.permission.ACCESS_FINE_LOCATION"
            java.lang.Object r1 = kotlin.collections.MapsKt.getValue(r9, r1)
            org.unimodules.interfaces.permissions.PermissionsResponse r1 = (org.unimodules.interfaces.permissions.PermissionsResponse) r1
            java.lang.String r2 = "android.permission.ACCESS_COARSE_LOCATION"
            java.lang.Object r2 = kotlin.collections.MapsKt.getValue(r9, r2)
            org.unimodules.interfaces.permissions.PermissionsResponse r2 = (org.unimodules.interfaces.permissions.PermissionsResponse) r2
            org.unimodules.interfaces.permissions.PermissionsStatus r3 = r1.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            java.lang.String r5 = "none"
            if (r3 != r4) goto L_0x0026
            java.lang.String r3 = "fine"
            goto L_0x0032
        L_0x0026:
            org.unimodules.interfaces.permissions.PermissionsStatus r3 = r2.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r3 != r4) goto L_0x0031
            java.lang.String r3 = "coarse"
            goto L_0x0032
        L_0x0031:
            r3 = r5
        L_0x0032:
            boolean r4 = r8.includeBackgroundPermission
            java.lang.String r6 = "always"
            if (r4 == 0) goto L_0x0052
            int r4 = android.os.Build.VERSION.SDK_INT
            r7 = 29
            if (r4 < r7) goto L_0x0052
            java.lang.String r1 = "android.permission.ACCESS_BACKGROUND_LOCATION"
            java.lang.Object r9 = kotlin.collections.MapsKt.getValue(r9, r1)
            org.unimodules.interfaces.permissions.PermissionsResponse r9 = (org.unimodules.interfaces.permissions.PermissionsResponse) r9
            org.unimodules.interfaces.permissions.PermissionsStatus r9 = r9.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r9 != r1) goto L_0x004f
            goto L_0x0062
        L_0x004f:
            java.lang.String r5 = "whenInUse"
            goto L_0x0063
        L_0x0052:
            org.unimodules.interfaces.permissions.PermissionsStatus r9 = r2.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r2 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r9 == r2) goto L_0x0062
            org.unimodules.interfaces.permissions.PermissionsStatus r9 = r1.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r9 != r1) goto L_0x0063
        L_0x0062:
            r5 = r6
        L_0x0063:
            java.lang.String r9 = "scope"
            r0.putString(r9, r5)
            android.os.Bundle r9 = new android.os.Bundle
            r9.<init>()
            java.lang.String r1 = "accuracy"
            r9.putString(r1, r3)
            kotlin.Unit r1 = kotlin.Unit.INSTANCE
            java.lang.String r1 = "android"
            r0.putBundle(r1, r9)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.permissions.requesters.LegacyLocationRequester.parseAndroidPermissions(java.util.Map):android.os.Bundle");
    }
}
