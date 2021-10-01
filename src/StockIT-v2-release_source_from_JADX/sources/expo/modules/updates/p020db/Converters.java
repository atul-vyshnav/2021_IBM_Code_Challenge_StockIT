package expo.modules.updates.p020db;

import android.net.Uri;
import android.util.Log;
import expo.modules.updates.p020db.enums.HashType;
import expo.modules.updates.p020db.enums.UpdateStatus;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: expo.modules.updates.db.Converters */
public class Converters {
    private static final String TAG = Converters.class.getSimpleName();

    public static int hashTypeToInt(HashType hashType) {
        return 0;
    }

    public static Date longToDate(Long l) {
        if (l == null) {
            return null;
        }
        return new Date(l.longValue());
    }

    public static Long dateToLong(Date date) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(date.getTime());
    }

    public static Uri stringToUri(String str) {
        if (str == null) {
            return null;
        }
        return Uri.parse(str);
    }

    public static String uriToString(Uri uri) {
        if (uri == null) {
            return null;
        }
        return uri.toString();
    }

    public static JSONObject stringToJsonObject(String str) {
        if (str == null) {
            return null;
        }
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            Log.e(TAG, "Could not convert string to JSONObject", e);
            return new JSONObject();
        }
    }

    public static String jsonObjectToString(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        return jSONObject.toString();
    }

    public static UUID bytesToUuid(byte[] bArr) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    public static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer wrap = ByteBuffer.wrap(new byte[16]);
        wrap.putLong(uuid.getMostSignificantBits());
        wrap.putLong(uuid.getLeastSignificantBits());
        return wrap.array();
    }

    public static UpdateStatus intToStatus(int i) {
        if (i == 0) {
            return UpdateStatus.FAILED;
        }
        if (i == 1) {
            return UpdateStatus.READY;
        }
        if (i == 2) {
            return UpdateStatus.LAUNCHABLE;
        }
        if (i == 3) {
            return UpdateStatus.PENDING;
        }
        if (i == 5) {
            return UpdateStatus.EMBEDDED;
        }
        if (i != 6) {
            return UpdateStatus.UNUSED;
        }
        return UpdateStatus.DEVELOPMENT;
    }

    /* renamed from: expo.modules.updates.db.Converters$1 */
    static /* synthetic */ class C30691 {
        static final /* synthetic */ int[] $SwitchMap$expo$modules$updates$db$enums$UpdateStatus;

        /* JADX WARNING: Can't wrap try/catch for region: R(14:0|1|2|3|4|5|6|7|8|9|10|11|12|(3:13|14|16)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(16:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|16) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                expo.modules.updates.db.enums.UpdateStatus[] r0 = expo.modules.updates.p020db.enums.UpdateStatus.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$expo$modules$updates$db$enums$UpdateStatus = r0
                expo.modules.updates.db.enums.UpdateStatus r1 = expo.modules.updates.p020db.enums.UpdateStatus.FAILED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$expo$modules$updates$db$enums$UpdateStatus     // Catch:{ NoSuchFieldError -> 0x001d }
                expo.modules.updates.db.enums.UpdateStatus r1 = expo.modules.updates.p020db.enums.UpdateStatus.READY     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$expo$modules$updates$db$enums$UpdateStatus     // Catch:{ NoSuchFieldError -> 0x0028 }
                expo.modules.updates.db.enums.UpdateStatus r1 = expo.modules.updates.p020db.enums.UpdateStatus.LAUNCHABLE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$expo$modules$updates$db$enums$UpdateStatus     // Catch:{ NoSuchFieldError -> 0x0033 }
                expo.modules.updates.db.enums.UpdateStatus r1 = expo.modules.updates.p020db.enums.UpdateStatus.PENDING     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$expo$modules$updates$db$enums$UpdateStatus     // Catch:{ NoSuchFieldError -> 0x003e }
                expo.modules.updates.db.enums.UpdateStatus r1 = expo.modules.updates.p020db.enums.UpdateStatus.EMBEDDED     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$expo$modules$updates$db$enums$UpdateStatus     // Catch:{ NoSuchFieldError -> 0x0049 }
                expo.modules.updates.db.enums.UpdateStatus r1 = expo.modules.updates.p020db.enums.UpdateStatus.DEVELOPMENT     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$expo$modules$updates$db$enums$UpdateStatus     // Catch:{ NoSuchFieldError -> 0x0054 }
                expo.modules.updates.db.enums.UpdateStatus r1 = expo.modules.updates.p020db.enums.UpdateStatus.UNUSED     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.p020db.Converters.C30691.<clinit>():void");
        }
    }

    public static int statusToInt(UpdateStatus updateStatus) {
        switch (C30691.$SwitchMap$expo$modules$updates$db$enums$UpdateStatus[updateStatus.ordinal()]) {
            case 1:
                return 0;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 5;
            case 6:
                return 6;
            default:
                return 4;
        }
    }

    public static HashType intToHashType(int i) {
        return HashType.SHA256;
    }
}
