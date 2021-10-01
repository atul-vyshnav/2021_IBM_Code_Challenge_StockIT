package com.google.android.play.core.internal;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;

/* renamed from: com.google.android.play.core.internal.f */
final class C1983f extends C1984g {

    /* renamed from: a */
    private final byte[] f831a;

    public C1983f(X509Certificate x509Certificate, byte[] bArr) {
        super(x509Certificate);
        this.f831a = bArr;
    }

    public final byte[] getEncoded() throws CertificateEncodingException {
        return this.f831a;
    }
}
