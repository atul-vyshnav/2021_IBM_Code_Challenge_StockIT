package org.unimodules.interfaces.permissions;

import org.unimodules.core.Promise;

public interface Permissions {
    void askForPermissions(PermissionsResponseListener permissionsResponseListener, String... strArr);

    void askForPermissionsWithPromise(Promise promise, String... strArr);

    void getPermissions(PermissionsResponseListener permissionsResponseListener, String... strArr);

    void getPermissionsWithPromise(Promise promise, String... strArr);

    boolean hasGrantedPermissions(String... strArr);

    boolean isPermissionPresentInManifest(String str);

    /* renamed from: org.unimodules.interfaces.permissions.Permissions$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void getPermissionsWithPermissionsManager(Permissions permissions, Promise promise, String... strArr) {
            if (permissions == null) {
                promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
            } else {
                permissions.getPermissionsWithPromise(promise, strArr);
            }
        }

        public static void askForPermissionsWithPermissionsManager(Permissions permissions, Promise promise, String... strArr) {
            if (permissions == null) {
                promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
            } else {
                permissions.askForPermissionsWithPromise(promise, strArr);
            }
        }
    }
}
