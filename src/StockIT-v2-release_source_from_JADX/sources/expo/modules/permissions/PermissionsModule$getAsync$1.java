package expo.modules.permissions;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.interfaces.permissions.Permissions;
import org.unimodules.interfaces.permissions.PermissionsResponseListener;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u001c\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u00032\u0016\u0010\u0005\u001a\u0012\u0012\u000e\b\u0001\u0012\n \u0004*\u0004\u0018\u00010\u00070\u00070\u0006¢\u0006\u0004\b\b\u0010\t"}, mo40253d2 = {"<anonymous>", "", "p1", "Lorg/unimodules/interfaces/permissions/PermissionsResponseListener;", "kotlin.jvm.PlatformType", "p2", "", "", "invoke", "(Lorg/unimodules/interfaces/permissions/PermissionsResponseListener;[Ljava/lang/String;)V"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsModule.kt */
final /* synthetic */ class PermissionsModule$getAsync$1 extends FunctionReferenceImpl implements Function2<PermissionsResponseListener, String[], Unit> {
    PermissionsModule$getAsync$1(Permissions permissions) {
        super(2, permissions, Permissions.class, "getPermissions", "getPermissions(Lorg/unimodules/interfaces/permissions/PermissionsResponseListener;[Ljava/lang/String;)V", 2);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        invoke((PermissionsResponseListener) obj, (String[]) obj2);
        return Unit.INSTANCE;
    }

    public final void invoke(PermissionsResponseListener permissionsResponseListener, String[] strArr) {
        Intrinsics.checkNotNullParameter(strArr, "p2");
        ((Permissions) this.receiver).getPermissions(permissionsResponseListener, strArr);
    }
}
