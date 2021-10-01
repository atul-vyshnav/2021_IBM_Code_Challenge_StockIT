package expo.modules.permissions.requesters;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0019\u0012\u0012\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003\"\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00040\nH\u0016J\u001c\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000f0\u000eH\u0016R\u001b\u0010\u0002\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00040\u0003¢\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, mo40253d2 = {"Lexpo/modules/permissions/requesters/SimpleRequester;", "Lexpo/modules/permissions/requesters/PermissionRequester;", "permission", "", "", "([Ljava/lang/String;)V", "getPermission", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getAndroidPermissions", "", "parseAndroidPermissions", "Landroid/os/Bundle;", "permissionsResponse", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: SimpleRequester.kt */
public final class SimpleRequester implements PermissionRequester {
    private final String[] permission;

    public SimpleRequester(String... strArr) {
        Intrinsics.checkNotNullParameter(strArr, "permission");
        this.permission = strArr;
    }

    public final String[] getPermission() {
        return this.permission;
    }

    public List<String> getAndroidPermissions() {
        return ArraysKt.toList((T[]) this.permission);
    }

    /* JADX WARNING: type inference failed for: r7v0, types: [java.util.Map, java.lang.Object, java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse>] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00d5  */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle parseAndroidPermissions(java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse> r7) {
        /*
            r6 = this;
            java.lang.String r0 = "permissionsResponse"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            java.util.List r1 = r6.getAndroidPermissions()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            boolean r2 = r1 instanceof java.util.Collection
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L_0x0021
            r2 = r1
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x0021
        L_0x001f:
            r1 = 1
            goto L_0x0045
        L_0x0021:
            java.util.Iterator r1 = r1.iterator()
        L_0x0025:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x001f
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r2 = kotlin.collections.MapsKt.getValue(r7, r2)
            org.unimodules.interfaces.permissions.PermissionsResponse r2 = (org.unimodules.interfaces.permissions.PermissionsResponse) r2
            org.unimodules.interfaces.permissions.PermissionsStatus r2 = r2.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r2 != r5) goto L_0x0041
            r2 = 1
            goto L_0x0042
        L_0x0041:
            r2 = 0
        L_0x0042:
            if (r2 != 0) goto L_0x0025
            r1 = 0
        L_0x0045:
            if (r1 == 0) goto L_0x004a
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            goto L_0x008a
        L_0x004a:
            java.util.List r1 = r6.getAndroidPermissions()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            boolean r2 = r1 instanceof java.util.Collection
            if (r2 == 0) goto L_0x005f
            r2 = r1
            java.util.Collection r2 = (java.util.Collection) r2
            boolean r2 = r2.isEmpty()
            if (r2 == 0) goto L_0x005f
        L_0x005d:
            r1 = 1
            goto L_0x0083
        L_0x005f:
            java.util.Iterator r1 = r1.iterator()
        L_0x0063:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L_0x005d
            java.lang.Object r2 = r1.next()
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r2 = kotlin.collections.MapsKt.getValue(r7, r2)
            org.unimodules.interfaces.permissions.PermissionsResponse r2 = (org.unimodules.interfaces.permissions.PermissionsResponse) r2
            org.unimodules.interfaces.permissions.PermissionsStatus r2 = r2.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            if (r2 != r5) goto L_0x007f
            r2 = 1
            goto L_0x0080
        L_0x007f:
            r2 = 0
        L_0x0080:
            if (r2 != 0) goto L_0x0063
            r1 = 0
        L_0x0083:
            if (r1 == 0) goto L_0x0088
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            goto L_0x008a
        L_0x0088:
            org.unimodules.interfaces.permissions.PermissionsStatus r1 = org.unimodules.interfaces.permissions.PermissionsStatus.UNDETERMINED
        L_0x008a:
            java.lang.String r2 = r1.getStatus()
            java.lang.String r5 = "status"
            r0.putString(r5, r2)
            java.lang.String r2 = "expires"
            java.lang.String r5 = "never"
            r0.putString(r2, r5)
            java.util.List r2 = r6.getAndroidPermissions()
            java.lang.Iterable r2 = (java.lang.Iterable) r2
            boolean r5 = r2 instanceof java.util.Collection
            if (r5 == 0) goto L_0x00af
            r5 = r2
            java.util.Collection r5 = (java.util.Collection) r5
            boolean r5 = r5.isEmpty()
            if (r5 == 0) goto L_0x00af
        L_0x00ad:
            r7 = 1
            goto L_0x00cc
        L_0x00af:
            java.util.Iterator r2 = r2.iterator()
        L_0x00b3:
            boolean r5 = r2.hasNext()
            if (r5 == 0) goto L_0x00ad
            java.lang.Object r5 = r2.next()
            java.lang.String r5 = (java.lang.String) r5
            java.lang.Object r5 = kotlin.collections.MapsKt.getValue(r7, r5)
            org.unimodules.interfaces.permissions.PermissionsResponse r5 = (org.unimodules.interfaces.permissions.PermissionsResponse) r5
            boolean r5 = r5.getCanAskAgain()
            if (r5 != 0) goto L_0x00b3
            r7 = 0
        L_0x00cc:
            java.lang.String r2 = "canAskAgain"
            r0.putBoolean(r2, r7)
            org.unimodules.interfaces.permissions.PermissionsStatus r7 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r1 != r7) goto L_0x00d6
            r3 = 1
        L_0x00d6:
            java.lang.String r7 = "granted"
            r0.putBoolean(r7, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.permissions.requesters.SimpleRequester.parseAndroidPermissions(java.util.Map):android.os.Bundle");
    }
}
