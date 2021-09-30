package kotlin.reflect.jvm.internal.impl.descriptors;

import com.onesignal.OneSignalDbContract;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: InvalidModuleException.kt */
public final class InvalidModuleException extends IllegalStateException {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public InvalidModuleException(String str) {
        super(str);
        Intrinsics.checkNotNullParameter(str, OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE);
    }
}
