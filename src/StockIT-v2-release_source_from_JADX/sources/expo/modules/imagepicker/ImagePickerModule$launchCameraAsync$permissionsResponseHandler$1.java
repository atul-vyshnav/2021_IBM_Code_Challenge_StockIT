package expo.modules.imagepicker;

import android.content.Intent;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.Promise;
import org.unimodules.interfaces.permissions.PermissionsResponse;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;
import org.unimodules.interfaces.permissions.PermissionsStatus;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\n¢\u0006\u0002\b\u0006"}, mo40253d2 = {"<anonymous>", "", "permissionsResponse", "", "", "Lorg/unimodules/interfaces/permissions/PermissionsResponse;", "onResult"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: ImagePickerModule.kt */
final class ImagePickerModule$launchCameraAsync$permissionsResponseHandler$1 implements PermissionsResponseListener {
    final /* synthetic */ Intent $cameraIntent;
    final /* synthetic */ ImagePickerOptions $pickerOptions;
    final /* synthetic */ Promise $promise;
    final /* synthetic */ ImagePickerModule this$0;

    ImagePickerModule$launchCameraAsync$permissionsResponseHandler$1(ImagePickerModule imagePickerModule, Promise promise, Intent intent, ImagePickerOptions imagePickerOptions) {
        this.this$0 = imagePickerModule;
        this.$promise = promise;
        this.$cameraIntent = intent;
        this.$pickerOptions = imagePickerOptions;
    }

    public final void onResult(Map<String, PermissionsResponse> map) {
        Intrinsics.checkNotNullParameter(map, "permissionsResponse");
        PermissionsResponse permissionsResponse = map.get("android.permission.WRITE_EXTERNAL_STORAGE");
        PermissionsStatus permissionsStatus = null;
        if ((permissionsResponse != null ? permissionsResponse.getStatus() : null) == PermissionsStatus.GRANTED) {
            PermissionsResponse permissionsResponse2 = map.get("android.permission.CAMERA");
            if (permissionsResponse2 != null) {
                permissionsStatus = permissionsResponse2.getStatus();
            }
            if (permissionsStatus == PermissionsStatus.GRANTED) {
                this.this$0.launchCameraWithPermissionsGranted(this.$promise, this.$cameraIntent, this.$pickerOptions);
                return;
            }
        }
        this.$promise.reject(new SecurityException("User rejected permissions"));
    }
}
