package com.RNFetchBlob;

import androidx.core.app.NotificationCompat;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import kotlin.UByte;
import okhttp3.OkHttpClient;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class RNFetchBlobUtils {
    public static String getMD5(String str) {
        try {
            MessageDigest instance = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            StringBuilder sb = new StringBuilder();
            int length = digest.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("%02x", new Object[]{Integer.valueOf(digest[i] & UByte.MAX_VALUE)}));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable unused) {
        }
        return null;
    }

    public static void emitWarningEvent(String str) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString(NotificationCompat.CATEGORY_EVENT, "warn");
        createMap.putString("detail", str);
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) C0626RNFetchBlob.RCTContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(RNFetchBlobConst.EVENT_MESSAGE, createMap);
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient(OkHttpClient okHttpClient) {
        try {
            C06471 r0 = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            SSLContext instance = SSLContext.getInstance("SSL");
            instance.init((KeyManager[]) null, new TrustManager[]{r0}, new SecureRandom());
            SSLSocketFactory socketFactory = instance.getSocketFactory();
            OkHttpClient.Builder newBuilder = okHttpClient.newBuilder();
            newBuilder.sslSocketFactory(socketFactory, r0);
            newBuilder.hostnameVerifier(new HostnameVerifier() {
                public boolean verify(String str, SSLSession sSLSession) {
                    return true;
                }
            });
            return newBuilder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
