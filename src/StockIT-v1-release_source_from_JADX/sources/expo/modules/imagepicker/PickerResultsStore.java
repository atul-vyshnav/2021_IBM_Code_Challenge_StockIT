package expo.modules.imagepicker;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Base64;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\f2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000e\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000f\u001a\u00020\fH\u0002J\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\n0\u0011J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, mo40253d2 = {"Lexpo/modules/imagepicker/PickerResultsStore;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "sharedPreferences", "Landroid/content/SharedPreferences;", "addPendingResult", "", "bundle", "Landroid/os/Bundle;", "bundleToBytes", "", "kotlin.jvm.PlatformType", "bytesToBundle", "bytes", "getAllPendingResults", "", "readAsBase64", "", "path", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: PickerResultsStore.kt */
public final class PickerResultsStore {
    private final SharedPreferences sharedPreferences;

    public PickerResultsStore(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        SharedPreferences sharedPreferences2 = context.getSharedPreferences(PickerResultsStoreKt.SHARED_PREFERENCES_NAME, 0);
        Intrinsics.checkNotNullExpressionValue(sharedPreferences2, "context.getSharedPrefere…ME, Context.MODE_PRIVATE)");
        this.sharedPreferences = sharedPreferences2;
    }

    public final void addPendingResult(Bundle bundle) {
        Intrinsics.checkNotNullParameter(bundle, "bundle");
        bundle.putLong(PickerResultsStoreKt.EXPIRE_KEY, new Date().getTime() + ((long) PickerResultsStoreKt.EXPIRATION_TIME));
        String uuid = UUID.randomUUID().toString();
        Intrinsics.checkNotNullExpressionValue(uuid, "UUID.randomUUID().toString()");
        this.sharedPreferences.edit().putString(uuid, Base64.encodeToString(bundleToBytes(bundle), 0)).apply();
    }

    public final List<Bundle> getAllPendingResults() {
        String readAsBase64;
        ArrayList arrayList = new ArrayList();
        long time = new Date().getTime();
        Map<String, ?> all = this.sharedPreferences.getAll();
        Intrinsics.checkNotNullExpressionValue(all, "sharedPreferences.all");
        for (Map.Entry<String, ?> value : all.entrySet()) {
            Object value2 = value.getValue();
            if (value2 instanceof String) {
                byte[] decode = Base64.decode((String) value2, 0);
                Intrinsics.checkNotNullExpressionValue(decode, "Base64.decode(value, 0)");
                Bundle bytesToBundle = bytesToBundle(decode);
                if (bytesToBundle != null) {
                    if (bytesToBundle.containsKey("uri")) {
                        if (bytesToBundle.getLong(PickerResultsStoreKt.EXPIRE_KEY) >= time) {
                            Uri parse = Uri.parse(bytesToBundle.getString("uri"));
                            Intrinsics.checkNotNullExpressionValue(parse, "Uri.parse(decodedBundle.getString(\"uri\"))");
                            String path = parse.getPath();
                            Intrinsics.checkNotNull(path);
                            Intrinsics.checkNotNullExpressionValue(path, "Uri.parse(decodedBundle.getString(\"uri\")).path!!");
                            if (new File(path).exists()) {
                                if (bytesToBundle.getBoolean("base64", false) && (readAsBase64 = readAsBase64(path)) != null) {
                                    bytesToBundle.putString("base64", readAsBase64);
                                }
                            }
                        }
                    }
                    bytesToBundle.remove(PickerResultsStoreKt.EXPIRE_KEY);
                    arrayList.add(bytesToBundle);
                }
            }
        }
        this.sharedPreferences.edit().clear().apply();
        return arrayList;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x002f, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
        kotlin.p024io.CloseableKt.closeFinally(r1, r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0033, code lost:
        throw r2;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.lang.String readAsBase64(java.lang.String r6) {
        /*
            r5 = this;
            r0 = 0
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0034 }
            java.io.File r2 = new java.io.File     // Catch:{ IOException -> 0x0034 }
            r2.<init>(r6)     // Catch:{ IOException -> 0x0034 }
            r1.<init>(r2)     // Catch:{ IOException -> 0x0034 }
            java.io.Closeable r1 = (java.io.Closeable) r1     // Catch:{ IOException -> 0x0034 }
            r6 = r0
            java.lang.Throwable r6 = (java.lang.Throwable) r6     // Catch:{ IOException -> 0x0034 }
            r2 = r1
            java.io.FileInputStream r2 = (java.io.FileInputStream) r2     // Catch:{ all -> 0x002d }
            java.io.ByteArrayOutputStream r3 = new java.io.ByteArrayOutputStream     // Catch:{ all -> 0x002d }
            r3.<init>()     // Catch:{ all -> 0x002d }
            java.io.InputStream r2 = (java.io.InputStream) r2     // Catch:{ all -> 0x002d }
            r4 = r3
            java.io.OutputStream r4 = (java.io.OutputStream) r4     // Catch:{ all -> 0x002d }
            org.apache.commons.p029io.IOUtils.copy((java.io.InputStream) r2, (java.io.OutputStream) r4)     // Catch:{ all -> 0x002d }
            byte[] r2 = r3.toByteArray()     // Catch:{ all -> 0x002d }
            r3 = 2
            java.lang.String r2 = android.util.Base64.encodeToString(r2, r3)     // Catch:{ all -> 0x002d }
            kotlin.p024io.CloseableKt.closeFinally(r1, r6)     // Catch:{ IOException -> 0x0034 }
            return r2
        L_0x002d:
            r6 = move-exception
            throw r6     // Catch:{ all -> 0x002f }
        L_0x002f:
            r2 = move-exception
            kotlin.p024io.CloseableKt.closeFinally(r1, r6)     // Catch:{ IOException -> 0x0034 }
            throw r2     // Catch:{ IOException -> 0x0034 }
        L_0x0034:
            r6 = move-exception
            java.lang.String r1 = r6.getMessage()
            java.lang.Throwable r6 = (java.lang.Throwable) r6
            java.lang.String r2 = "ExponentImagePicker"
            android.util.Log.e(r2, r1, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.PickerResultsStore.readAsBase64(java.lang.String):java.lang.String");
    }

    private final byte[] bundleToBytes(Bundle bundle) {
        Parcel obtain = Parcel.obtain();
        obtain.writeBundle(bundle);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }

    private final Bundle bytesToBundle(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        Bundle readBundle = obtain.readBundle(PickerResultsStore.class.getClassLoader());
        obtain.recycle();
        return readBundle;
    }
}
