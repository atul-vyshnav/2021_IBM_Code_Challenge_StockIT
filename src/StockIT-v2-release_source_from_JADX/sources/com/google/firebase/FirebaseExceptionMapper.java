package com.google.firebase;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.StatusExceptionMapper;

/* compiled from: com.google.android.gms:play-services-basement@@17.3.0 */
public class FirebaseExceptionMapper implements StatusExceptionMapper {
    public Exception getException(Status status) {
        if (status.getStatusCode() == 8) {
            return new FirebaseException(status.zza());
        }
        return new FirebaseApiNotAvailableException(status.zza());
    }
}
