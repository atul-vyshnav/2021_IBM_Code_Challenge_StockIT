package expo.modules.securestore;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.stats.CodePackage;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;
import kotlin.text.Typography;
import org.json.JSONException;
import org.json.JSONObject;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.ExpoMethod;

public class SecureStoreModule extends ExportedModule {
    private static final String ALIAS_PROPERTY = "keychainService";
    private static final String KEYSTORE_PROVIDER = "AndroidKeyStore";
    private static final String SCHEME_PROPERTY = "scheme";
    private static final String SHARED_PREFERENCES_NAME = "SecureStore";
    private static final String TAG = "ExpoSecureStore";
    private AESEncrypter mAESEncrypter;
    private HybridAESEncrypter mHybridAESEncrypter;
    private KeyStore mKeyStore;

    private interface KeyBasedEncrypter<E extends KeyStore.Entry> {
        JSONObject createEncryptedItem(String str, KeyStore keyStore, E e) throws GeneralSecurityException, JSONException;

        String decryptItem(JSONObject jSONObject, E e) throws GeneralSecurityException, JSONException;

        String getKeyStoreAlias(ReadableArguments readableArguments);

        E initializeKeyStoreEntry(KeyStore keyStore, ReadableArguments readableArguments) throws GeneralSecurityException;
    }

    public String getName() {
        return TAG;
    }

    public SecureStoreModule(Context context) {
        super(context);
        AESEncrypter aESEncrypter = new AESEncrypter();
        this.mAESEncrypter = aESEncrypter;
        this.mHybridAESEncrypter = new HybridAESEncrypter(context, aESEncrypter);
    }

    @ExpoMethod
    public void setValueWithKeyAsync(String str, String str2, ReadableArguments readableArguments, Promise promise) {
        try {
            setItemImpl(str2, str, readableArguments, promise);
        } catch (Exception e) {
            Log.e(TAG, "Caught unexpected exception when writing to SecureStore", e);
            promise.reject("E_SECURESTORE_WRITE_ERROR", "An unexpected error occurred when writing to SecureStore", e);
        }
    }

    private void setItemImpl(String str, String str2, ReadableArguments readableArguments, Promise promise) {
        JSONObject jSONObject;
        if (str == null) {
            promise.reject("E_SECURESTORE_NULL_KEY", "SecureStore keys must not be null");
            return;
        }
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
        if (str2 != null) {
            try {
                KeyStore keyStore = getKeyStore();
                if (Build.VERSION.SDK_INT >= 23) {
                    Class<KeyStore.SecretKeyEntry> cls = KeyStore.SecretKeyEntry.class;
                    jSONObject = this.mAESEncrypter.createEncryptedItem(str2, keyStore, (KeyStore.SecretKeyEntry) getKeyEntry(cls, this.mAESEncrypter, readableArguments));
                    jSONObject.put(SCHEME_PROPERTY, AESEncrypter.NAME);
                } else {
                    jSONObject = this.mHybridAESEncrypter.createEncryptedItem(str2, keyStore, (KeyStore.PrivateKeyEntry) getKeyEntry(KeyStore.PrivateKeyEntry.class, this.mHybridAESEncrypter, readableArguments));
                    jSONObject.put(SCHEME_PROPERTY, HybridAESEncrypter.NAME);
                }
                String jSONObject2 = jSONObject.toString();
                if (jSONObject2 == null) {
                    promise.reject("E_SECURESTORE_JSON_ERROR", "Could not JSON-encode the encrypted item for SecureStore");
                } else if (sharedPreferences.edit().putString(str, jSONObject2).commit()) {
                    promise.resolve((Object) null);
                } else {
                    promise.reject("E_SECURESTORE_WRITE_ERROR", "Could not write encrypted JSON to SecureStore");
                }
            } catch (IOException e) {
                Log.w(TAG, e);
                promise.reject("E_SECURESTORE_IO_ERROR", "There was an I/O error loading the keystore for SecureStore", e);
            } catch (GeneralSecurityException e2) {
                Log.w(TAG, e2);
                promise.reject("E_SECURESTORE_ENCRYPT_ERROR", "Could not encrypt the value for SecureStore", e2);
            } catch (JSONException e3) {
                Log.w(TAG, e3);
                promise.reject("E_SECURESTORE_ENCODE_ERROR", "Could not create an encrypted JSON item for SecureStore", e3);
            }
        } else if (sharedPreferences.edit().putString(str, (String) null).commit()) {
            promise.resolve((Object) null);
        } else {
            promise.reject("E_SECURESTORE_WRITE_ERROR", "Could not write a null value to SecureStore");
        }
    }

    @ExpoMethod
    public void getValueWithKeyAsync(String str, ReadableArguments readableArguments, Promise promise) {
        try {
            getItemImpl(str, readableArguments, promise);
        } catch (Exception e) {
            Log.e(TAG, "Caught unexpected exception when reading from SecureStore", e);
            promise.reject("E_SECURESTORE_READ_ERROR", "An unexpected error occurred when reading from SecureStore", e);
        }
    }

    private void getItemImpl(String str, ReadableArguments readableArguments, Promise promise) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        if (sharedPreferences.contains(str)) {
            readJSONEncodedItem(str, sharedPreferences, readableArguments, promise);
        } else {
            readLegacySDK20Item(str, readableArguments, promise);
        }
    }

    private void readJSONEncodedItem(String str, SharedPreferences sharedPreferences, ReadableArguments readableArguments, Promise promise) {
        String str2;
        String string = sharedPreferences.getString(str, (String) null);
        try {
            JSONObject jSONObject = new JSONObject(string);
            String optString = jSONObject.optString(SCHEME_PROPERTY);
            if (optString == null) {
                Log.e(TAG, String.format("Stored JSON object is missing a scheme (key = %s, value = %s)", new Object[]{str, string}));
                promise.reject("E_SECURESTORE_DECODE_ERROR", "Could not find the encryption scheme used for SecureStore item");
                return;
            }
            char c = 65535;
            try {
                int hashCode = optString.hashCode();
                if (hashCode != -1202757124) {
                    if (hashCode == 96463) {
                        if (optString.equals(AESEncrypter.NAME)) {
                            c = 0;
                        }
                    }
                } else if (optString.equals(HybridAESEncrypter.NAME)) {
                    c = 1;
                }
                if (c == 0) {
                    str2 = this.mAESEncrypter.decryptItem(jSONObject, (KeyStore.SecretKeyEntry) getKeyEntry(KeyStore.SecretKeyEntry.class, this.mAESEncrypter, readableArguments));
                } else if (c != 1) {
                    String format = String.format("The item for key \"%s\" in SecureStore has an unknown encoding scheme (%s)", new Object[]{str, optString});
                    Log.e(TAG, format);
                    promise.reject("E_SECURESTORE_DECODE_ERROR", format);
                    return;
                } else {
                    str2 = this.mHybridAESEncrypter.decryptItem(jSONObject, (KeyStore.PrivateKeyEntry) getKeyEntry(KeyStore.PrivateKeyEntry.class, this.mHybridAESEncrypter, readableArguments));
                }
                promise.resolve(str2);
            } catch (IOException e) {
                Log.w(TAG, e);
                promise.reject("E_SECURESTORE_IO_ERROR", "There was an I/O error loading the keystore for SecureStore", e);
            } catch (GeneralSecurityException e2) {
                Log.w(TAG, e2);
                promise.reject("E_SECURESTORE_DECRYPT_ERROR", "Could not decrypt the item in SecureStore", e2);
            } catch (JSONException e3) {
                Log.w(TAG, e3);
                promise.reject("E_SECURESTORE_DECODE_ERROR", "Could not decode the encrypted JSON item in SecureStore", e3);
            }
        } catch (JSONException e4) {
            Log.e(TAG, String.format("Could not parse stored value as JSON (key = %s, value = %s)", new Object[]{str, string}), e4);
            promise.reject("E_SECURESTORE_JSON_ERROR", "Could not parse the encrypted JSON item in SecureStore");
        }
    }

    private void readLegacySDK20Item(String str, ReadableArguments readableArguments, Promise promise) {
        String string = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(str, (String) null);
        if (TextUtils.isEmpty(string)) {
            promise.resolve((Object) null);
            return;
        }
        LegacySDK20Encrypter legacySDK20Encrypter = new LegacySDK20Encrypter();
        try {
            KeyStore keyStore = getKeyStore();
            String keyStoreAlias = legacySDK20Encrypter.getKeyStoreAlias(readableArguments);
            if (!keyStore.containsAlias(keyStoreAlias)) {
                promise.reject("E_SECURESTORE_DECRYPT_ERROR", "Could not find the keystore entry to decrypt the legacy item in SecureStore");
                return;
            }
            KeyStore.Entry entry = keyStore.getEntry(keyStoreAlias, (KeyStore.ProtectionParameter) null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                promise.reject("E_SECURESTORE_DECRYPT_ERROR", "The keystore entry for the legacy item is not a private key entry");
            } else {
                promise.resolve(legacySDK20Encrypter.decryptItem(string, (KeyStore.PrivateKeyEntry) entry));
            }
        } catch (IOException e) {
            Log.w(TAG, e);
            promise.reject("E_SECURESTORE_IO_ERROR", "There was an I/O error loading the keystore for SecureStore", e);
        } catch (GeneralSecurityException e2) {
            Log.w(TAG, e2);
            promise.reject("E_SECURESTORE_DECRYPT_ERROR", "Could not decrypt the item in SecureStore", e2);
        }
    }

    @ExpoMethod
    public void deleteValueWithKeyAsync(String str, ReadableArguments readableArguments, Promise promise) {
        try {
            deleteItemImpl(str, promise);
        } catch (Exception e) {
            Log.e(TAG, "Caught unexpected exception when deleting from SecureStore", e);
            promise.reject("E_SECURESTORE_DELETE_ERROR", "An unexpected error occurred when deleting item from SecureStore", e);
        }
    }

    private void deleteItemImpl(String str, Promise promise) {
        SharedPreferences sharedPreferences = getSharedPreferences();
        boolean z = true;
        boolean commit = sharedPreferences.contains(str) ? sharedPreferences.edit().remove(str).commit() : true;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (defaultSharedPreferences.contains(str)) {
            if (!defaultSharedPreferences.edit().remove(str).commit() || !commit) {
                z = false;
            }
            commit = z;
        }
        if (commit) {
            promise.resolve((Object) null);
        } else {
            promise.reject("E_SECURESTORE_DELETE_ERROR", "Could not delete the item from SecureStore");
        }
    }

    /* access modifiers changed from: protected */
    public SharedPreferences getSharedPreferences() {
        return getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, 0);
    }

    private KeyStore getKeyStore() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
        if (this.mKeyStore == null) {
            KeyStore instance = KeyStore.getInstance(KEYSTORE_PROVIDER);
            instance.load((KeyStore.LoadStoreParameter) null);
            this.mKeyStore = instance;
        }
        return this.mKeyStore;
    }

    private <E extends KeyStore.Entry> E getKeyEntry(Class<E> cls, KeyBasedEncrypter<E> keyBasedEncrypter, ReadableArguments readableArguments) throws IOException, GeneralSecurityException {
        KeyStore keyStore = getKeyStore();
        String keyStoreAlias = keyBasedEncrypter.getKeyStoreAlias(readableArguments);
        if (!keyStore.containsAlias(keyStoreAlias)) {
            return keyBasedEncrypter.initializeKeyStoreEntry(keyStore, readableArguments);
        }
        KeyStore.Entry entry = keyStore.getEntry(keyStoreAlias, (KeyStore.ProtectionParameter) null);
        if (cls.isInstance(entry)) {
            return (KeyStore.Entry) cls.cast(entry);
        }
        throw new KeyStoreException(String.format("The entry for the keystore alias \"%s\" is not a %s", new Object[]{keyStoreAlias, cls.getSimpleName()}));
    }

    protected static class AESEncrypter implements KeyBasedEncrypter<KeyStore.SecretKeyEntry> {
        private static final String AES_CIPHER = "AES/GCM/NoPadding";
        private static final int AES_KEY_SIZE_BITS = 256;
        private static final String CIPHERTEXT_PROPERTY = "ct";
        private static final String DEFAULT_ALIAS = "key_v1";
        private static final String GCM_AUTHENTICATION_TAG_LENGTH_PROPERTY = "tlen";
        private static final String IV_PROPERTY = "iv";
        public static final String NAME = "aes";

        protected AESEncrypter() {
        }

        public String getKeyStoreAlias(ReadableArguments readableArguments) {
            String string = readableArguments.containsKey(SecureStoreModule.ALIAS_PROPERTY) ? readableArguments.getString(SecureStoreModule.ALIAS_PROPERTY) : DEFAULT_ALIAS;
            return "AES/GCM/NoPadding:" + string;
        }

        public KeyStore.SecretKeyEntry initializeKeyStoreEntry(KeyStore keyStore, ReadableArguments readableArguments) throws GeneralSecurityException {
            String keyStoreAlias = getKeyStoreAlias(readableArguments);
            KeyGenParameterSpec build = new KeyGenParameterSpec.Builder(keyStoreAlias, 3).setKeySize(256).setBlockModes(new String[]{CodePackage.GCM}).setEncryptionPaddings(new String[]{"NoPadding"}).build();
            KeyGenerator instance = KeyGenerator.getInstance("AES", keyStore.getProvider());
            instance.init(build);
            instance.generateKey();
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(keyStoreAlias, (KeyStore.ProtectionParameter) null);
            if (secretKeyEntry != null) {
                return secretKeyEntry;
            }
            throw new UnrecoverableEntryException("Could not retrieve the newly generated secret key entry");
        }

        public JSONObject createEncryptedItem(String str, KeyStore keyStore, KeyStore.SecretKeyEntry secretKeyEntry) throws GeneralSecurityException, JSONException {
            SecretKey secretKey = secretKeyEntry.getSecretKey();
            Cipher instance = Cipher.getInstance(AES_CIPHER);
            instance.init(1, secretKey);
            return createEncryptedItem(str, instance, (GCMParameterSpec) instance.getParameters().getParameterSpec(GCMParameterSpec.class));
        }

        /* access modifiers changed from: package-private */
        public JSONObject createEncryptedItem(String str, Cipher cipher, GCMParameterSpec gCMParameterSpec) throws GeneralSecurityException, JSONException {
            String encodeToString = Base64.encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)), 2);
            String encodeToString2 = Base64.encodeToString(gCMParameterSpec.getIV(), 2);
            return new JSONObject().put(CIPHERTEXT_PROPERTY, encodeToString).put(IV_PROPERTY, encodeToString2).put(GCM_AUTHENTICATION_TAG_LENGTH_PROPERTY, gCMParameterSpec.getTLen());
        }

        public String decryptItem(JSONObject jSONObject, KeyStore.SecretKeyEntry secretKeyEntry) throws GeneralSecurityException, JSONException {
            String string = jSONObject.getString(CIPHERTEXT_PROPERTY);
            String string2 = jSONObject.getString(IV_PROPERTY);
            int i = jSONObject.getInt(GCM_AUTHENTICATION_TAG_LENGTH_PROPERTY);
            byte[] decode = Base64.decode(string, 0);
            GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(i, Base64.decode(string2, 0));
            Cipher instance = Cipher.getInstance(AES_CIPHER);
            instance.init(2, secretKeyEntry.getSecretKey(), gCMParameterSpec);
            return new String(instance.doFinal(decode), StandardCharsets.UTF_8);
        }
    }

    protected static class HybridAESEncrypter implements KeyBasedEncrypter<KeyStore.PrivateKeyEntry> {
        private static final String DEFAULT_ALIAS = "key_v1";
        private static final String ENCRYPTED_SECRET_KEY_PROPERTY = "esk";
        private static final int GCM_AUTHENTICATION_TAG_LENGTH_BITS = 128;
        private static final int GCM_IV_LENGTH_BYTES = 12;
        public static final String NAME = "hybrid";
        private static final String RSA_CIPHER = "RSA/None/PKCS1Padding";
        private static final String RSA_CIPHER_LEGACY_PROVIDER = "AndroidOpenSSL";
        private static final int X509_SERIAL_NUMBER_LENGTH_BITS = 160;
        private AESEncrypter mAESEncrypter;
        protected Context mContext;
        private SecureRandom mSecureRandom = new SecureRandom();

        HybridAESEncrypter(Context context, AESEncrypter aESEncrypter) {
            this.mContext = context;
            this.mAESEncrypter = aESEncrypter;
        }

        public String getKeyStoreAlias(ReadableArguments readableArguments) {
            String string = readableArguments.containsKey(SecureStoreModule.ALIAS_PROPERTY) ? readableArguments.getString(SecureStoreModule.ALIAS_PROPERTY) : DEFAULT_ALIAS;
            return "RSA/None/PKCS1Padding:" + string;
        }

        public KeyStore.PrivateKeyEntry initializeKeyStoreEntry(KeyStore keyStore, ReadableArguments readableArguments) throws GeneralSecurityException {
            String keyStoreAlias = getKeyStoreAlias(readableArguments);
            KeyPairGeneratorSpec.Builder alias = new KeyPairGeneratorSpec.Builder(this.mContext).setAlias(keyStoreAlias);
            KeyPairGeneratorSpec build = alias.setSubject(new X500Principal("CN=" + (Typography.quote + keyStoreAlias.replace("\\", "\\\\").replace("\"", "\\\"") + Typography.quote) + ", OU=SecureStore")).setSerialNumber(new BigInteger(X509_SERIAL_NUMBER_LENGTH_BITS, this.mSecureRandom)).setStartDate(new Date(0)).setEndDate(new Date(Long.MAX_VALUE)).build();
            KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA", keyStore.getProvider());
            instance.initialize(build);
            instance.generateKeyPair();
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(keyStoreAlias, (KeyStore.ProtectionParameter) null);
            if (privateKeyEntry != null) {
                return privateKeyEntry;
            }
            throw new UnrecoverableEntryException("Could not retrieve the newly generated private key entry");
        }

        public JSONObject createEncryptedItem(String str, KeyStore keyStore, KeyStore.PrivateKeyEntry privateKeyEntry) throws GeneralSecurityException, JSONException {
            byte[] bArr = new byte[12];
            this.mSecureRandom.nextBytes(bArr);
            KeyGenerator instance = KeyGenerator.getInstance("AES");
            instance.init(256);
            SecretKey generateKey = instance.generateKey();
            GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(128, bArr);
            Cipher instance2 = Cipher.getInstance("AES/GCM/NoPadding");
            instance2.init(1, generateKey, gCMParameterSpec);
            try {
                gCMParameterSpec = (GCMParameterSpec) instance2.getParameters().getParameterSpec(GCMParameterSpec.class);
            } catch (InvalidParameterSpecException unused) {
                if (!CodePackage.GCM.equals(instance2.getParameters().getAlgorithm())) {
                    throw new InvalidAlgorithmParameterException("Algorithm chosen by the cipher (" + instance2.getParameters().getAlgorithm() + ") doesn't match requested (GCM).");
                }
            }
            JSONObject createEncryptedItem = this.mAESEncrypter.createEncryptedItem(str, instance2, gCMParameterSpec);
            String string = createEncryptedItem.getString("iv");
            String encodeToString = Base64.encodeToString(bArr, 2);
            if (string.equals(encodeToString)) {
                byte[] encoded = generateKey.getEncoded();
                Cipher rSACipher = getRSACipher();
                rSACipher.init(1, privateKeyEntry.getCertificate());
                return createEncryptedItem.put(ENCRYPTED_SECRET_KEY_PROPERTY, Base64.encodeToString(rSACipher.doFinal(encoded), 2));
            }
            Log.e(SecureStoreModule.TAG, String.format("HybridAESEncrypter generated two different IVs: %s and %s", new Object[]{encodeToString, string}));
            throw new IllegalStateException("HybridAESEncrypter must store the same IV as the one used to parameterize the secret key");
        }

        public String decryptItem(JSONObject jSONObject, KeyStore.PrivateKeyEntry privateKeyEntry) throws GeneralSecurityException, JSONException {
            byte[] decode = Base64.decode(jSONObject.getString(ENCRYPTED_SECRET_KEY_PROPERTY), 0);
            Cipher rSACipher = getRSACipher();
            rSACipher.init(2, privateKeyEntry.getPrivateKey());
            return this.mAESEncrypter.decryptItem(jSONObject, new KeyStore.SecretKeyEntry(new SecretKeySpec(rSACipher.doFinal(decode), "AES")));
        }

        private Cipher getRSACipher() throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
            if (Build.VERSION.SDK_INT < 23) {
                return Cipher.getInstance(RSA_CIPHER, RSA_CIPHER_LEGACY_PROVIDER);
            }
            return Cipher.getInstance(RSA_CIPHER);
        }
    }

    private static class LegacySDK20Encrypter {
        private static final String DEFAULT_ALIAS = "MY_APP";
        private static final String RSA_CIPHER = "RSA/ECB/PKCS1Padding";

        private LegacySDK20Encrypter() {
        }

        /* access modifiers changed from: package-private */
        public String getKeyStoreAlias(ReadableArguments readableArguments) {
            return readableArguments.containsKey(SecureStoreModule.ALIAS_PROPERTY) ? readableArguments.getString(SecureStoreModule.ALIAS_PROPERTY) : DEFAULT_ALIAS;
        }

        /* access modifiers changed from: package-private */
        public String decryptItem(String str, KeyStore.PrivateKeyEntry privateKeyEntry) throws GeneralSecurityException {
            byte[] decode = Base64.decode(str, 0);
            Cipher instance = Cipher.getInstance(RSA_CIPHER);
            instance.init(2, privateKeyEntry.getPrivateKey());
            return new String(instance.doFinal(decode), StandardCharsets.UTF_8);
        }
    }
}
