package expo.modules.constants;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;

public class ExponentInstallationId {
    public static final String LEGACY_UUID_KEY = "uuid";
    private static final String PREFERENCES_FILE_NAME = "host.exp.exponent.SharedPreferences";
    private static final String TAG = ExponentInstallationId.class.getSimpleName();
    public static final String UUID_FILE_NAME = "expo_installation_uuid.txt";
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private String mUuid;

    ExponentInstallationId(Context context) {
        this.mContext = context;
        this.mSharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
        r4 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x002f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        r3.addSuppressed(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0033, code lost:
        throw r4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0036, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x003f, code lost:
        throw r3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0062, code lost:
        r1 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x006b, code lost:
        throw r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getUUID() {
        /*
            r6 = this;
            java.lang.String r0 = r6.mUuid
            if (r0 == 0) goto L_0x0005
            return r0
        L_0x0005:
            java.io.File r0 = r6.getNonBackedUpUuidFile()
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ IOException | IllegalArgumentException -> 0x0040 }
            r1.<init>(r0)     // Catch:{ IOException | IllegalArgumentException -> 0x0040 }
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ all -> 0x0034 }
            r2.<init>(r1)     // Catch:{ all -> 0x0034 }
            java.lang.String r3 = r2.readLine()     // Catch:{ all -> 0x0028 }
            java.util.UUID r3 = java.util.UUID.fromString(r3)     // Catch:{ all -> 0x0028 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0028 }
            r6.mUuid = r3     // Catch:{ all -> 0x0028 }
            r2.close()     // Catch:{ all -> 0x0034 }
            r1.close()     // Catch:{ IOException | IllegalArgumentException -> 0x0040 }
            goto L_0x0041
        L_0x0028:
            r3 = move-exception
            throw r3     // Catch:{ all -> 0x002a }
        L_0x002a:
            r4 = move-exception
            r2.close()     // Catch:{ all -> 0x002f }
            goto L_0x0033
        L_0x002f:
            r2 = move-exception
            r3.addSuppressed(r2)     // Catch:{ all -> 0x0034 }
        L_0x0033:
            throw r4     // Catch:{ all -> 0x0034 }
        L_0x0034:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x0036 }
        L_0x0036:
            r3 = move-exception
            r1.close()     // Catch:{ all -> 0x003b }
            goto L_0x003f
        L_0x003b:
            r1 = move-exception
            r2.addSuppressed(r1)     // Catch:{ IOException | IllegalArgumentException -> 0x0040 }
        L_0x003f:
            throw r3     // Catch:{ IOException | IllegalArgumentException -> 0x0040 }
        L_0x0040:
        L_0x0041:
            java.lang.String r1 = r6.mUuid
            if (r1 == 0) goto L_0x0046
            return r1
        L_0x0046:
            android.content.SharedPreferences r1 = r6.mSharedPreferences
            r2 = 0
            java.lang.String r3 = "uuid"
            java.lang.String r1 = r1.getString(r3, r2)
            if (r1 == 0) goto L_0x0093
            r6.mUuid = r1
            r2 = 1
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ IOException -> 0x006c }
            r4.<init>(r0)     // Catch:{ IOException -> 0x006c }
            r4.write(r1)     // Catch:{ all -> 0x0060 }
            r4.close()     // Catch:{ IOException -> 0x006c }
            goto L_0x0084
        L_0x0060:
            r0 = move-exception
            throw r0     // Catch:{ all -> 0x0062 }
        L_0x0062:
            r1 = move-exception
            r4.close()     // Catch:{ all -> 0x0067 }
            goto L_0x006b
        L_0x0067:
            r2 = move-exception
            r0.addSuppressed(r2)     // Catch:{ IOException -> 0x006c }
        L_0x006b:
            throw r1     // Catch:{ IOException -> 0x006c }
        L_0x006c:
            r0 = move-exception
            r2 = 0
            java.lang.String r1 = TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Error while migrating UUID from legacy storage. "
            r4.append(r5)
            r4.append(r0)
            java.lang.String r0 = r4.toString()
            android.util.Log.e(r1, r0)
        L_0x0084:
            if (r2 == 0) goto L_0x0093
            android.content.SharedPreferences r0 = r6.mSharedPreferences
            android.content.SharedPreferences$Editor r0 = r0.edit()
            android.content.SharedPreferences$Editor r0 = r0.remove(r3)
            r0.apply()
        L_0x0093:
            java.lang.String r0 = r6.mUuid
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.constants.ExponentInstallationId.getUUID():java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0025, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r0.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x002e, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String getOrCreateUUID() {
        /*
            r4 = this;
            java.lang.String r0 = r4.getUUID()
            if (r0 == 0) goto L_0x0007
            return r0
        L_0x0007:
            java.util.UUID r0 = java.util.UUID.randomUUID()
            java.lang.String r0 = r0.toString()
            r4.mUuid = r0
            java.io.FileWriter r0 = new java.io.FileWriter     // Catch:{ IOException -> 0x002f }
            java.io.File r1 = r4.getNonBackedUpUuidFile()     // Catch:{ IOException -> 0x002f }
            r0.<init>(r1)     // Catch:{ IOException -> 0x002f }
            java.lang.String r1 = r4.mUuid     // Catch:{ all -> 0x0023 }
            r0.write(r1)     // Catch:{ all -> 0x0023 }
            r0.close()     // Catch:{ IOException -> 0x002f }
            goto L_0x0046
        L_0x0023:
            r1 = move-exception
            throw r1     // Catch:{ all -> 0x0025 }
        L_0x0025:
            r2 = move-exception
            r0.close()     // Catch:{ all -> 0x002a }
            goto L_0x002e
        L_0x002a:
            r0 = move-exception
            r1.addSuppressed(r0)     // Catch:{ IOException -> 0x002f }
        L_0x002e:
            throw r2     // Catch:{ IOException -> 0x002f }
        L_0x002f:
            r0 = move-exception
            java.lang.String r1 = TAG
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = "Error while writing new UUID. "
            r2.append(r3)
            r2.append(r0)
            java.lang.String r0 = r2.toString()
            android.util.Log.e(r1, r0)
        L_0x0046:
            java.lang.String r0 = r4.mUuid
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.constants.ExponentInstallationId.getOrCreateUUID():java.lang.String");
    }

    private File getNonBackedUpUuidFile() {
        return new File(this.mContext.getNoBackupFilesDir(), UUID_FILE_NAME);
    }
}
