package p009co.apptailor.googlesignin;

import com.facebook.react.bridge.WritableMap;

/* renamed from: co.apptailor.googlesignin.PendingAuthRecovery */
public class PendingAuthRecovery {
    private WritableMap userProperties;

    public PendingAuthRecovery(WritableMap writableMap) {
        this.userProperties = writableMap;
    }

    public WritableMap getUserProperties() {
        return this.userProperties;
    }
}
