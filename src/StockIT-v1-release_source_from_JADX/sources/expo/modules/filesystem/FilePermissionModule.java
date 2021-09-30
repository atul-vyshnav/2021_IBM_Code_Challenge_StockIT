package expo.modules.filesystem;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.interfaces.InternalModule;
import org.unimodules.core.interfaces.RegistryLifecycleListener;
import org.unimodules.interfaces.filesystem.FilePermissionModuleInterface;
import org.unimodules.interfaces.filesystem.Permission;

public class FilePermissionModule implements FilePermissionModuleInterface, InternalModule {
    public /* synthetic */ void onCreate(ModuleRegistry moduleRegistry) {
        RegistryLifecycleListener.CC.$default$onCreate(this, moduleRegistry);
    }

    public /* synthetic */ void onDestroy() {
        RegistryLifecycleListener.CC.$default$onDestroy(this);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(FilePermissionModuleInterface.class);
    }

    public EnumSet<Permission> getPathPermissions(Context context, String str) {
        EnumSet<Permission> internalPathPermissions = getInternalPathPermissions(str, context);
        return internalPathPermissions == null ? getExternalPathPermissions(str) : internalPathPermissions;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:4:0x0017 A[Catch:{ IOException -> 0x0045 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.EnumSet<org.unimodules.interfaces.filesystem.Permission> getInternalPathPermissions(java.lang.String r4, android.content.Context r5) {
        /*
            r3 = this;
            java.io.File r0 = new java.io.File     // Catch:{ IOException -> 0x0045 }
            r0.<init>(r4)     // Catch:{ IOException -> 0x0045 }
            java.lang.String r4 = r0.getCanonicalPath()     // Catch:{ IOException -> 0x0045 }
            java.util.List r5 = r3.getInternalPaths(r5)     // Catch:{ IOException -> 0x0045 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ IOException -> 0x0045 }
        L_0x0011:
            boolean r0 = r5.hasNext()     // Catch:{ IOException -> 0x0045 }
            if (r0 == 0) goto L_0x0043
            java.lang.Object r0 = r5.next()     // Catch:{ IOException -> 0x0045 }
            java.lang.String r0 = (java.lang.String) r0     // Catch:{ IOException -> 0x0045 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0045 }
            r1.<init>()     // Catch:{ IOException -> 0x0045 }
            r1.append(r0)     // Catch:{ IOException -> 0x0045 }
            java.lang.String r2 = "/"
            r1.append(r2)     // Catch:{ IOException -> 0x0045 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x0045 }
            boolean r1 = r4.startsWith(r1)     // Catch:{ IOException -> 0x0045 }
            if (r1 != 0) goto L_0x003a
            boolean r0 = r0.equals(r4)     // Catch:{ IOException -> 0x0045 }
            if (r0 == 0) goto L_0x0011
        L_0x003a:
            org.unimodules.interfaces.filesystem.Permission r4 = org.unimodules.interfaces.filesystem.Permission.READ     // Catch:{ IOException -> 0x0045 }
            org.unimodules.interfaces.filesystem.Permission r5 = org.unimodules.interfaces.filesystem.Permission.WRITE     // Catch:{ IOException -> 0x0045 }
            java.util.EnumSet r4 = java.util.EnumSet.of(r4, r5)     // Catch:{ IOException -> 0x0045 }
            return r4
        L_0x0043:
            r4 = 0
            return r4
        L_0x0045:
            java.lang.Class<org.unimodules.interfaces.filesystem.Permission> r4 = org.unimodules.interfaces.filesystem.Permission.class
            java.util.EnumSet r4 = java.util.EnumSet.noneOf(r4)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.filesystem.FilePermissionModule.getInternalPathPermissions(java.lang.String, android.content.Context):java.util.EnumSet");
    }

    /* access modifiers changed from: protected */
    public EnumSet<Permission> getExternalPathPermissions(String str) {
        File file = new File(str);
        if (file.canWrite() && file.canRead()) {
            return EnumSet.of(Permission.READ, Permission.WRITE);
        }
        if (file.canWrite()) {
            return EnumSet.of(Permission.WRITE);
        }
        if (file.canRead()) {
            return EnumSet.of(Permission.READ);
        }
        return EnumSet.noneOf(Permission.class);
    }

    /* access modifiers changed from: protected */
    public List<String> getInternalPaths(Context context) throws IOException {
        return Arrays.asList(new String[]{context.getFilesDir().getCanonicalPath(), context.getCacheDir().getCanonicalPath()});
    }
}
