package expo.modules.permissions;

import java.util.ArrayList;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.Promise;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0000\u0010\u0000\u001a\u00020\u00012F\u0010\u0002\u001aB\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00060\u0006 \u0005* \u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00040\u0004\u0012\f\u0012\n \u0005*\u0004\u0018\u00010\u00060\u0006\u0018\u00010\u00070\u0003H\n¢\u0006\u0002\b\b"}, mo40253d2 = {"<anonymous>", "", "permissionsNativeStatus", "", "", "kotlin.jvm.PlatformType", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "", "onResult"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsModule.kt */
final class PermissionsModule$createPermissionsResponseListener$1 implements PermissionsResponseListener {
    final /* synthetic */ Promise $promise;
    final /* synthetic */ ArrayList $requestedPermissionsTypes;
    final /* synthetic */ PermissionsModule this$0;

    PermissionsModule$createPermissionsResponseListener$1(PermissionsModule permissionsModule, Promise promise, ArrayList arrayList) {
        this.this$0 = permissionsModule;
        this.$promise = promise;
        this.$requestedPermissionsTypes = arrayList;
    }

    public final void onResult(Map<String, PermissionsResponse> map) {
        Promise promise = this.$promise;
        Intrinsics.checkNotNullExpressionValue(map, "permissionsNativeStatus");
        promise.resolve(this.this$0.parsePermissionsResponse(this.$requestedPermissionsTypes, map));
    }
}
