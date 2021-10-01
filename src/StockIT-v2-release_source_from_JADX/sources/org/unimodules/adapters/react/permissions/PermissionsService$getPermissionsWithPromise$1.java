package org.unimodules.adapters.react.permissions;

import kotlin.Metadata;
import org.unimodules.core.Promise;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\n¢\u0006\u0002\b\u0006"}, mo40253d2 = {"<anonymous>", "", "permissionsMap", "", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "onResult"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsService.kt */
final class PermissionsService$getPermissionsWithPromise$1 implements PermissionsResponseListener {
    final /* synthetic */ Promise $promise;

    PermissionsService$getPermissionsWithPromise$1(Promise promise) {
        this.$promise = promise;
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x009f  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00a6  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onResult(java.util.Map<java.lang.String, org.unimodules.interfaces.permissions.PermissionsResponse> r7) {
        /*
            r6 = this;
            java.lang.String r0 = "permissionsMap"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r7, r0)
            boolean r0 = r7.isEmpty()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L_0x000f
        L_0x000d:
            r0 = 1
            goto L_0x0037
        L_0x000f:
            java.util.Set r0 = r7.entrySet()
            java.util.Iterator r0 = r0.iterator()
        L_0x0017:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x000d
            java.lang.Object r3 = r0.next()
            java.util.Map$Entry r3 = (java.util.Map.Entry) r3
            java.lang.Object r3 = r3.getValue()
            org.unimodules.interfaces.permissions.PermissionsResponse r3 = (org.unimodules.interfaces.permissions.PermissionsResponse) r3
            org.unimodules.interfaces.permissions.PermissionsStatus r3 = r3.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            if (r3 != r4) goto L_0x0033
            r3 = 1
            goto L_0x0034
        L_0x0033:
            r3 = 0
        L_0x0034:
            if (r3 != 0) goto L_0x0017
            r0 = 0
        L_0x0037:
            boolean r3 = r7.isEmpty()
            if (r3 == 0) goto L_0x003f
        L_0x003d:
            r3 = 1
            goto L_0x0067
        L_0x003f:
            java.util.Set r3 = r7.entrySet()
            java.util.Iterator r3 = r3.iterator()
        L_0x0047:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x003d
            java.lang.Object r4 = r3.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r4 = r4.getValue()
            org.unimodules.interfaces.permissions.PermissionsResponse r4 = (org.unimodules.interfaces.permissions.PermissionsResponse) r4
            org.unimodules.interfaces.permissions.PermissionsStatus r4 = r4.getStatus()
            org.unimodules.interfaces.permissions.PermissionsStatus r5 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            if (r4 != r5) goto L_0x0063
            r4 = 1
            goto L_0x0064
        L_0x0063:
            r4 = 0
        L_0x0064:
            if (r4 != 0) goto L_0x0047
            r3 = 0
        L_0x0067:
            boolean r4 = r7.isEmpty()
            if (r4 == 0) goto L_0x006f
        L_0x006d:
            r1 = 1
            goto L_0x008f
        L_0x006f:
            java.util.Set r7 = r7.entrySet()
            java.util.Iterator r7 = r7.iterator()
        L_0x0077:
            boolean r4 = r7.hasNext()
            if (r4 == 0) goto L_0x006d
            java.lang.Object r4 = r7.next()
            java.util.Map$Entry r4 = (java.util.Map.Entry) r4
            java.lang.Object r4 = r4.getValue()
            org.unimodules.interfaces.permissions.PermissionsResponse r4 = (org.unimodules.interfaces.permissions.PermissionsResponse) r4
            boolean r4 = r4.getCanAskAgain()
            if (r4 != 0) goto L_0x0077
        L_0x008f:
            org.unimodules.core.Promise r7 = r6.$promise
            android.os.Bundle r2 = new android.os.Bundle
            r2.<init>()
            java.lang.String r4 = "expires"
            java.lang.String r5 = "never"
            r2.putString(r4, r5)
            if (r0 == 0) goto L_0x00a6
            org.unimodules.interfaces.permissions.PermissionsStatus r3 = org.unimodules.interfaces.permissions.PermissionsStatus.GRANTED
            java.lang.String r3 = r3.getStatus()
            goto L_0x00b5
        L_0x00a6:
            if (r3 == 0) goto L_0x00af
            org.unimodules.interfaces.permissions.PermissionsStatus r3 = org.unimodules.interfaces.permissions.PermissionsStatus.DENIED
            java.lang.String r3 = r3.getStatus()
            goto L_0x00b5
        L_0x00af:
            org.unimodules.interfaces.permissions.PermissionsStatus r3 = org.unimodules.interfaces.permissions.PermissionsStatus.UNDETERMINED
            java.lang.String r3 = r3.getStatus()
        L_0x00b5:
            java.lang.String r4 = "status"
            r2.putString(r4, r3)
            java.lang.String r3 = "canAskAgain"
            r2.putBoolean(r3, r1)
            java.lang.String r1 = "granted"
            r2.putBoolean(r1, r0)
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            r7.resolve(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.unimodules.adapters.react.permissions.PermissionsService$getPermissionsWithPromise$1.onResult(java.util.Map):void");
    }
}
