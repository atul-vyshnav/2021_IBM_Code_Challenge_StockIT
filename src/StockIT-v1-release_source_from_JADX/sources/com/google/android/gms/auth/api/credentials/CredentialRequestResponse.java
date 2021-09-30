package com.google.android.gms.auth.api.credentials;

import com.google.android.gms.common.api.C1527Response;

public class CredentialRequestResponse extends C1527Response<CredentialRequestResult> {
    public Credential getCredential() {
        return ((CredentialRequestResult) getResult()).getCredential();
    }
}
