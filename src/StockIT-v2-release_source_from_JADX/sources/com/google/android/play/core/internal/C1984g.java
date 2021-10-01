package com.google.android.play.core.internal;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.g */
class C1984g extends X509Certificate {

    /* renamed from: a */
    private final X509Certificate f832a;

    public C1984g(X509Certificate x509Certificate) {
        this.f832a = x509Certificate;
    }

    public final void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
        this.f832a.checkValidity();
    }

    public final void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
        this.f832a.checkValidity(date);
    }

    public final int getBasicConstraints() {
        return this.f832a.getBasicConstraints();
    }

    public final Set<String> getCriticalExtensionOIDs() {
        return this.f832a.getCriticalExtensionOIDs();
    }

    public byte[] getEncoded() throws CertificateEncodingException {
        return this.f832a.getEncoded();
    }

    public final byte[] getExtensionValue(String str) {
        return this.f832a.getExtensionValue(str);
    }

    public final Principal getIssuerDN() {
        return this.f832a.getIssuerDN();
    }

    public final boolean[] getIssuerUniqueID() {
        return this.f832a.getIssuerUniqueID();
    }

    public final boolean[] getKeyUsage() {
        return this.f832a.getKeyUsage();
    }

    public final Set<String> getNonCriticalExtensionOIDs() {
        return this.f832a.getNonCriticalExtensionOIDs();
    }

    public final Date getNotAfter() {
        return this.f832a.getNotAfter();
    }

    public final Date getNotBefore() {
        return this.f832a.getNotBefore();
    }

    public final PublicKey getPublicKey() {
        return this.f832a.getPublicKey();
    }

    public final BigInteger getSerialNumber() {
        return this.f832a.getSerialNumber();
    }

    public final String getSigAlgName() {
        return this.f832a.getSigAlgName();
    }

    public final String getSigAlgOID() {
        return this.f832a.getSigAlgOID();
    }

    public final byte[] getSigAlgParams() {
        return this.f832a.getSigAlgParams();
    }

    public final byte[] getSignature() {
        return this.f832a.getSignature();
    }

    public final Principal getSubjectDN() {
        return this.f832a.getSubjectDN();
    }

    public final boolean[] getSubjectUniqueID() {
        return this.f832a.getSubjectUniqueID();
    }

    public final byte[] getTBSCertificate() throws CertificateEncodingException {
        return this.f832a.getTBSCertificate();
    }

    public final int getVersion() {
        return this.f832a.getVersion();
    }

    public final boolean hasUnsupportedCriticalExtension() {
        return this.f832a.hasUnsupportedCriticalExtension();
    }

    public final String toString() {
        return this.f832a.toString();
    }

    public final void verify(PublicKey publicKey) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.f832a.verify(publicKey);
    }

    public final void verify(PublicKey publicKey, String str) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        this.f832a.verify(publicKey, str);
    }
}
