package org.unimodules.adapters.react.permissions;

import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\u0010\u0000\u001a\u00020\u00012F\u0010\u0002\u001aB\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00060\u0006 \u0005* \u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\u00070\u0003H\n¢\u0006\u0002\b\b"}, mo40253d2 = {"<anonymous>", "", "it", "", "", "kotlin.jvm.PlatformType", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "", "onResult"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsService.kt */
final class PermissionsService$askForPermissions$newListener$1 implements PermissionsResponseListener {
    final /* synthetic */ PermissionsResponseListener $responseListener;
    final /* synthetic */ PermissionsService this$0;

    PermissionsService$askForPermissions$newListener$1(PermissionsService permissionsService, PermissionsResponseListener permissionsResponseListener) {
        this.this$0 = permissionsService;
        this.$responseListener = permissionsResponseListener;
    }

    public final void onResult(Map<String, PermissionsResponse> map) {
        int i = this.this$0.hasWriteSettingsPermission() ? 0 : -1;
        Intrinsics.checkNotNullExpressionValue(map, "it");
        map.put("android.permission.WRITE_SETTINGS", this.this$0.getPermissionResponseFromNativeResponse("android.permission.WRITE_SETTINGS", i));
        this.$responseListener.onResult(map);
    }
}
