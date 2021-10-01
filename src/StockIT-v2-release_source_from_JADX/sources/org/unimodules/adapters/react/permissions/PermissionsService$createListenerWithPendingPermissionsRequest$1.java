package org.unimodules.adapters.react.permissions;

import com.facebook.react.modules.core.PermissionListener;
import kotlin.Metadata;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032,\u0010\u0004\u001a(\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006 \u0007*\u0014\u0012\u000e\b\u0001\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\u00050\u00052\u000e\u0010\b\u001a\n \u0007*\u0004\u0018\u00010\t0\tH\n¢\u0006\u0004\b\n\u0010\u000b"}, mo40253d2 = {"<anonymous>", "", "requestCode", "", "receivePermissions", "", "", "kotlin.jvm.PlatformType", "grantResults", "", "onRequestPermissionsResult", "(I[Ljava/lang/String;[I)Z"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsService.kt */
final class PermissionsService$createListenerWithPendingPermissionsRequest$1 implements PermissionListener {
    final /* synthetic */ PermissionsService this$0;

    PermissionsService$createListenerWithPendingPermissionsRequest$1(PermissionsService permissionsService) {
        this.this$0 = permissionsService;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00df, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onRequestPermissionsResult(int r8, java.lang.String[] r9, int[] r10) {
        /*
            r7 = this;
            r0 = 13
            r1 = 0
            if (r8 != r0) goto L_0x00f2
            org.unimodules.adapters.react.permissions.PermissionsService r8 = r7.this$0
            monitor-enter(r8)
            org.unimodules.adapters.react.permissions.PermissionsService r2 = r7.this$0     // Catch:{ all -> 0x00ef }
            org.unimodules.interfaces.permissions.PermissionsResponseListener r2 = r2.mCurrentPermissionListener     // Catch:{ all -> 0x00ef }
            if (r2 == 0) goto L_0x00e1
            org.unimodules.adapters.react.permissions.PermissionsService r3 = r7.this$0     // Catch:{ all -> 0x00ef }
            java.lang.String r4 = "receivePermissions"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r4)     // Catch:{ all -> 0x00ef }
            java.lang.String r4 = "grantResults"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r4)     // Catch:{ all -> 0x00ef }
            java.util.Map r9 = r3.parseNativeResult(r9, r10)     // Catch:{ all -> 0x00ef }
            r2.onResult(r9)     // Catch:{ all -> 0x00ef }
            org.unimodules.adapters.react.permissions.PermissionsService r9 = r7.this$0     // Catch:{ all -> 0x00ef }
            r10 = 0
            r2 = r10
            org.unimodules.interfaces.permissions.PermissionsResponseListener r2 = (org.unimodules.interfaces.permissions.PermissionsResponseListener) r2     // Catch:{ all -> 0x00ef }
            r9.mCurrentPermissionListener = r2     // Catch:{ all -> 0x00ef }
            org.unimodules.adapters.react.permissions.PermissionsService r9 = r7.this$0     // Catch:{ all -> 0x00ef }
            java.util.Queue r9 = r9.mPendingPermissionCalls     // Catch:{ all -> 0x00ef }
            java.lang.Object r9 = r9.poll()     // Catch:{ all -> 0x00ef }
            kotlin.Pair r9 = (kotlin.Pair) r9     // Catch:{ all -> 0x00ef }
            if (r9 == 0) goto L_0x00de
            org.unimodules.adapters.react.permissions.PermissionsService r2 = r7.this$0     // Catch:{ all -> 0x00ef }
            org.unimodules.core.interfaces.ActivityProvider r2 = r2.mActivityProvider     // Catch:{ all -> 0x00ef }
            if (r2 == 0) goto L_0x0047
            android.app.Activity r2 = r2.getCurrentActivity()     // Catch:{ all -> 0x00ef }
            goto L_0x0048
        L_0x0047:
            r2 = r10
        L_0x0048:
            boolean r3 = r2 instanceof com.facebook.react.modules.core.PermissionAwareActivity     // Catch:{ all -> 0x00ef }
            if (r3 != 0) goto L_0x004d
            goto L_0x004e
        L_0x004d:
            r10 = r2
        L_0x004e:
            com.facebook.react.modules.core.PermissionAwareActivity r10 = (com.facebook.react.modules.core.PermissionAwareActivity) r10     // Catch:{ all -> 0x00ef }
            if (r10 != 0) goto L_0x00c2
            java.lang.Object r10 = r9.getSecond()     // Catch:{ all -> 0x00ef }
            org.unimodules.interfaces.permissions.PermissionsResponseListener r10 = (org.unimodules.interfaces.permissions.PermissionsResponseListener) r10     // Catch:{ all -> 0x00ef }
            org.unimodules.adapters.react.permissions.PermissionsService r0 = r7.this$0     // Catch:{ all -> 0x00ef }
            java.lang.Object r2 = r9.getFirst()     // Catch:{ all -> 0x00ef }
            java.lang.String[] r2 = (java.lang.String[]) r2     // Catch:{ all -> 0x00ef }
            java.lang.Object r9 = r9.getFirst()     // Catch:{ all -> 0x00ef }
            java.lang.Object[] r9 = (java.lang.Object[]) r9     // Catch:{ all -> 0x00ef }
            int r9 = r9.length     // Catch:{ all -> 0x00ef }
            int[] r3 = new int[r9]     // Catch:{ all -> 0x00ef }
            r4 = 0
        L_0x006a:
            r5 = -1
            if (r4 >= r9) goto L_0x0072
            r3[r4] = r5     // Catch:{ all -> 0x00ef }
            int r4 = r4 + 1
            goto L_0x006a
        L_0x0072:
            java.util.Map r9 = r0.parseNativeResult(r2, r3)     // Catch:{ all -> 0x00ef }
            r10.onResult(r9)     // Catch:{ all -> 0x00ef }
            org.unimodules.adapters.react.permissions.PermissionsService r9 = r7.this$0     // Catch:{ all -> 0x00ef }
            java.util.Queue r9 = r9.mPendingPermissionCalls     // Catch:{ all -> 0x00ef }
            java.lang.Iterable r9 = (java.lang.Iterable) r9     // Catch:{ all -> 0x00ef }
            java.util.Iterator r9 = r9.iterator()     // Catch:{ all -> 0x00ef }
        L_0x0085:
            boolean r10 = r9.hasNext()     // Catch:{ all -> 0x00ef }
            if (r10 == 0) goto L_0x00b8
            java.lang.Object r10 = r9.next()     // Catch:{ all -> 0x00ef }
            kotlin.Pair r10 = (kotlin.Pair) r10     // Catch:{ all -> 0x00ef }
            java.lang.Object r0 = r10.getSecond()     // Catch:{ all -> 0x00ef }
            org.unimodules.interfaces.permissions.PermissionsResponseListener r0 = (org.unimodules.interfaces.permissions.PermissionsResponseListener) r0     // Catch:{ all -> 0x00ef }
            org.unimodules.adapters.react.permissions.PermissionsService r2 = r7.this$0     // Catch:{ all -> 0x00ef }
            java.lang.Object r3 = r10.getFirst()     // Catch:{ all -> 0x00ef }
            java.lang.String[] r3 = (java.lang.String[]) r3     // Catch:{ all -> 0x00ef }
            java.lang.Object r10 = r10.getFirst()     // Catch:{ all -> 0x00ef }
            java.lang.Object[] r10 = (java.lang.Object[]) r10     // Catch:{ all -> 0x00ef }
            int r10 = r10.length     // Catch:{ all -> 0x00ef }
            int[] r4 = new int[r10]     // Catch:{ all -> 0x00ef }
            r6 = 0
        L_0x00a9:
            if (r6 >= r10) goto L_0x00b0
            r4[r6] = r5     // Catch:{ all -> 0x00ef }
            int r6 = r6 + 1
            goto L_0x00a9
        L_0x00b0:
            java.util.Map r10 = r2.parseNativeResult(r3, r4)     // Catch:{ all -> 0x00ef }
            r0.onResult(r10)     // Catch:{ all -> 0x00ef }
            goto L_0x0085
        L_0x00b8:
            org.unimodules.adapters.react.permissions.PermissionsService r9 = r7.this$0     // Catch:{ all -> 0x00ef }
            java.util.Queue r9 = r9.mPendingPermissionCalls     // Catch:{ all -> 0x00ef }
            r9.clear()     // Catch:{ all -> 0x00ef }
            goto L_0x00de
        L_0x00c2:
            org.unimodules.adapters.react.permissions.PermissionsService r2 = r7.this$0     // Catch:{ all -> 0x00ef }
            java.lang.Object r3 = r9.getSecond()     // Catch:{ all -> 0x00ef }
            org.unimodules.interfaces.permissions.PermissionsResponseListener r3 = (org.unimodules.interfaces.permissions.PermissionsResponseListener) r3     // Catch:{ all -> 0x00ef }
            r2.mCurrentPermissionListener = r3     // Catch:{ all -> 0x00ef }
            java.lang.Object r9 = r9.getFirst()     // Catch:{ all -> 0x00ef }
            java.lang.String[] r9 = (java.lang.String[]) r9     // Catch:{ all -> 0x00ef }
            org.unimodules.adapters.react.permissions.PermissionsService r2 = r7.this$0     // Catch:{ all -> 0x00ef }
            com.facebook.react.modules.core.PermissionListener r2 = r2.createListenerWithPendingPermissionsRequest()     // Catch:{ all -> 0x00ef }
            r10.requestPermissions(r9, r0, r2)     // Catch:{ all -> 0x00ef }
            monitor-exit(r8)
            return r1
        L_0x00de:
            monitor-exit(r8)
            r8 = 1
            return r8
        L_0x00e1:
            java.lang.String r9 = "Required value was null."
            java.lang.IllegalArgumentException r10 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x00ef }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x00ef }
            r10.<init>(r9)     // Catch:{ all -> 0x00ef }
            java.lang.Throwable r10 = (java.lang.Throwable) r10     // Catch:{ all -> 0x00ef }
            throw r10     // Catch:{ all -> 0x00ef }
        L_0x00ef:
            r9 = move-exception
            monitor-exit(r8)
            throw r9
        L_0x00f2:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.unimodules.adapters.react.permissions.PermissionsService$createListenerWithPendingPermissionsRequest$1.onRequestPermissionsResult(int, java.lang.String[], int[]):boolean");
    }
}
