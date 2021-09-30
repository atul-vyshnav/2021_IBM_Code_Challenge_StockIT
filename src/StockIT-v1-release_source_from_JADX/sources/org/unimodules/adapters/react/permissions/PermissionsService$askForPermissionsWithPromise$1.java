package org.unimodules.adapters.react.permissions;

import java.util.Arrays;
import java.util.Map;
import kotlin.Metadata;
import org.unimodules.core.Promise;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\u0010\u0000\u001a\u00020\u00012F\u0010\u0002\u001aB\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00060\u0006 \u0005* \u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\u00070\u0003H\n¢\u0006\u0002\b\b"}, mo40253d2 = {"<anonymous>", "", "it", "", "", "kotlin.jvm.PlatformType", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "", "onResult"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsService.kt */
final class PermissionsService$askForPermissionsWithPromise$1 implements PermissionsResponseListener {
    final /* synthetic */ String[] $permissions;
    final /* synthetic */ Promise $promise;
    final /* synthetic */ PermissionsService this$0;

    PermissionsService$askForPermissionsWithPromise$1(PermissionsService permissionsService, Promise promise, String[] strArr) {
        this.this$0 = permissionsService;
        this.$promise = promise;
        this.$permissions = strArr;
    }

    public final void onResult(Map<String, PermissionsResponse> map) {
        PermissionsService permissionsService = this.this$0;
        Promise promise = this.$promise;
        String[] strArr = this.$permissions;
        permissionsService.getPermissionsWithPromise(promise, (String[]) Arrays.copyOf(strArr, strArr.length));
    }
}
