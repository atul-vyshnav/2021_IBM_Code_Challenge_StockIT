package com.facebook.react.modules.camera;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.facebook.common.logging.FLog;
import com.facebook.fbreact.specs.NativeCameraRollManagerSpec;
import com.facebook.react.bridge.GuardedAsyncTask;
import com.facebook.react.bridge.JSApplicationIllegalArgumentException;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.module.annotations.ReactModule;
import com.google.android.exoplayer2.util.MimeTypes;
import com.thunkable.live.BuildConfig;
import java.io.IOException;
import java.util.ArrayList;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

@ReactModule(name = "CameraRollManager")
public class CameraRollManager extends NativeCameraRollManagerSpec {
    private static final String ASSET_TYPE_ALL = "All";
    private static final String ASSET_TYPE_PHOTOS = "Photos";
    private static final String ASSET_TYPE_VIDEOS = "Videos";
    private static final String ERROR_UNABLE_TO_FILTER = "E_UNABLE_TO_FILTER";
    private static final String ERROR_UNABLE_TO_LOAD = "E_UNABLE_TO_LOAD";
    private static final String ERROR_UNABLE_TO_LOAD_PERMISSION = "E_UNABLE_TO_LOAD_PERMISSION";
    private static final String ERROR_UNABLE_TO_SAVE = "E_UNABLE_TO_SAVE";
    public static final String NAME = "CameraRollManager";
    /* access modifiers changed from: private */
    public static final String[] PROJECTION = {"_id", "mime_type", "bucket_display_name", "datetaken", "width", "height", "longitude", "latitude", "_data"};
    private static final String SELECTION_BUCKET = "bucket_display_name = ?";
    private static final String SELECTION_DATE_TAKEN = "datetaken < ?";
    private static final String SELECTION_MEDIA_SIZE = "_size < ?";

    public void deletePhotos(ReadableArray readableArray, Promise promise) {
    }

    public String getName() {
        return NAME;
    }

    public CameraRollManager(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void saveToCameraRoll(String str, String str2, Promise promise) {
        new SaveToCameraRoll(getReactApplicationContext(), Uri.parse(str), promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    private static class SaveToCameraRoll extends GuardedAsyncTask<Void, Void> {
        private static final int SAVE_BUFFER_SIZE = 1048576;
        private final Context mContext;
        /* access modifiers changed from: private */
        public final Promise mPromise;
        private final Uri mUri;

        public SaveToCameraRoll(ReactContext reactContext, Uri uri, Promise promise) {
            super(reactContext);
            this.mContext = reactContext;
            this.mUri = uri;
            this.mPromise = promise;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:110:? A[RETURN, SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0055 A[Catch:{ IOException -> 0x0131, all -> 0x012c }] */
        /* JADX WARNING: Removed duplicated region for block: B:20:0x006f A[SYNTHETIC, Splitter:B:20:0x006f] */
        /* JADX WARNING: Removed duplicated region for block: B:79:0x0158 A[SYNTHETIC, Splitter:B:79:0x0158] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void doInBackgroundGuarded(java.lang.Void... r14) {
            /*
                r13 = this;
                java.lang.String r14 = "Could not close output channel"
                java.lang.String r0 = "Could not close input channel"
                java.lang.String r1 = "ReactNative"
                java.io.File r2 = new java.io.File
                android.net.Uri r3 = r13.mUri
                java.lang.String r3 = r3.getPath()
                r2.<init>(r3)
                r3 = 0
                android.net.Uri r4 = r13.mUri     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                java.lang.String r4 = r4.getScheme()     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                java.lang.String r5 = "http"
                boolean r5 = r4.equals(r5)     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                if (r5 != 0) goto L_0x0033
                java.lang.String r5 = "https"
                boolean r4 = r4.equals(r5)     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                if (r4 == 0) goto L_0x0029
                goto L_0x0033
            L_0x0029:
                java.io.FileInputStream r4 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                r4.<init>(r2)     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                java.nio.channels.FileChannel r4 = r4.getChannel()     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                goto L_0x0046
            L_0x0033:
                java.net.URL r4 = new java.net.URL     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                android.net.Uri r5 = r13.mUri     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                java.lang.String r5 = r5.toString()     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                r4.<init>(r5)     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                java.io.InputStream r4 = r4.openStream()     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
                java.nio.channels.ReadableByteChannel r4 = java.nio.channels.Channels.newChannel(r4)     // Catch:{ IOException -> 0x0139, all -> 0x0136 }
            L_0x0046:
                java.lang.String r5 = android.os.Environment.DIRECTORY_DCIM     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.io.File r5 = android.os.Environment.getExternalStoragePublicDirectory(r5)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r5.mkdirs()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                boolean r6 = r5.isDirectory()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                if (r6 != 0) goto L_0x006f
                com.facebook.react.bridge.Promise r2 = r13.mPromise     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.String r5 = "E_UNABLE_TO_LOAD"
                java.lang.String r6 = "External media storage directory not available"
                r2.reject((java.lang.String) r5, (java.lang.String) r6)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                if (r4 == 0) goto L_0x006e
                boolean r14 = r4.isOpen()
                if (r14 == 0) goto L_0x006e
                r4.close()     // Catch:{ IOException -> 0x006a }
                goto L_0x006e
            L_0x006a:
                r14 = move-exception
                com.facebook.common.logging.FLog.m65e((java.lang.String) r1, (java.lang.String) r0, (java.lang.Throwable) r14)
            L_0x006e:
                return
            L_0x006f:
                java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.String r7 = r2.getName()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r6.<init>(r5, r7)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.String r2 = r2.getName()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r7 = 46
                int r8 = r2.indexOf(r7)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r9 = 0
                if (r8 < 0) goto L_0x0098
                int r8 = r2.lastIndexOf(r7)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.String r8 = r2.substring(r9, r8)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                int r7 = r2.lastIndexOf(r7)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.String r2 = r2.substring(r7)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r7 = r2
                r2 = r8
                goto L_0x009a
            L_0x0098:
                java.lang.String r7 = ""
            L_0x009a:
                r8 = 0
            L_0x009b:
                boolean r10 = r6.createNewFile()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                if (r10 != 0) goto L_0x00c1
                java.io.File r6 = new java.io.File     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r10.<init>()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r10.append(r2)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.String r11 = "_"
                r10.append(r11)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                int r11 = r8 + 1
                r10.append(r8)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r10.append(r7)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.lang.String r8 = r10.toString()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r6.<init>(r5, r8)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r8 = r11
                goto L_0x009b
            L_0x00c1:
                java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r2.<init>(r6)     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                java.nio.channels.FileChannel r2 = r2.getChannel()     // Catch:{ IOException -> 0x0131, all -> 0x012c }
                r5 = 1048576(0x100000, float:1.469368E-39)
                java.nio.ByteBuffer r5 = java.nio.ByteBuffer.allocate(r5)     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
            L_0x00d0:
                int r7 = r4.read(r5)     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                if (r7 <= 0) goto L_0x00e0
                r5.flip()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                r2.write(r5)     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                r5.compact()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                goto L_0x00d0
            L_0x00e0:
                r5.flip()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
            L_0x00e3:
                boolean r7 = r5.hasRemaining()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                if (r7 == 0) goto L_0x00ed
                r2.write(r5)     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                goto L_0x00e3
            L_0x00ed:
                r4.close()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                r2.close()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                android.content.Context r5 = r13.mContext     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                r7 = 1
                java.lang.String[] r7 = new java.lang.String[r7]     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                java.lang.String r6 = r6.getAbsolutePath()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                r7[r9] = r6     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                com.facebook.react.modules.camera.CameraRollManager$SaveToCameraRoll$1 r6 = new com.facebook.react.modules.camera.CameraRollManager$SaveToCameraRoll$1     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                r6.<init>()     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                android.media.MediaScannerConnection.scanFile(r5, r7, r3, r6)     // Catch:{ IOException -> 0x0127, all -> 0x0122 }
                if (r4 == 0) goto L_0x0116
                boolean r3 = r4.isOpen()
                if (r3 == 0) goto L_0x0116
                r4.close()     // Catch:{ IOException -> 0x0112 }
                goto L_0x0116
            L_0x0112:
                r3 = move-exception
                com.facebook.common.logging.FLog.m65e((java.lang.String) r1, (java.lang.String) r0, (java.lang.Throwable) r3)
            L_0x0116:
                if (r2 == 0) goto L_0x0160
                boolean r0 = r2.isOpen()
                if (r0 == 0) goto L_0x0160
                r2.close()     // Catch:{ IOException -> 0x015c }
                goto L_0x0160
            L_0x0122:
                r3 = move-exception
                r12 = r4
                r4 = r2
                r2 = r3
                goto L_0x012f
            L_0x0127:
                r3 = move-exception
                r12 = r4
                r4 = r2
                r2 = r3
                goto L_0x0134
            L_0x012c:
                r2 = move-exception
                r12 = r4
                r4 = r3
            L_0x012f:
                r3 = r12
                goto L_0x0162
            L_0x0131:
                r2 = move-exception
                r12 = r4
                r4 = r3
            L_0x0134:
                r3 = r12
                goto L_0x013b
            L_0x0136:
                r2 = move-exception
                r4 = r3
                goto L_0x0162
            L_0x0139:
                r2 = move-exception
                r4 = r3
            L_0x013b:
                com.facebook.react.bridge.Promise r5 = r13.mPromise     // Catch:{ all -> 0x0161 }
                r5.reject((java.lang.Throwable) r2)     // Catch:{ all -> 0x0161 }
                if (r3 == 0) goto L_0x0150
                boolean r2 = r3.isOpen()
                if (r2 == 0) goto L_0x0150
                r3.close()     // Catch:{ IOException -> 0x014c }
                goto L_0x0150
            L_0x014c:
                r2 = move-exception
                com.facebook.common.logging.FLog.m65e((java.lang.String) r1, (java.lang.String) r0, (java.lang.Throwable) r2)
            L_0x0150:
                if (r4 == 0) goto L_0x0160
                boolean r0 = r4.isOpen()
                if (r0 == 0) goto L_0x0160
                r4.close()     // Catch:{ IOException -> 0x015c }
                goto L_0x0160
            L_0x015c:
                r0 = move-exception
                com.facebook.common.logging.FLog.m65e((java.lang.String) r1, (java.lang.String) r14, (java.lang.Throwable) r0)
            L_0x0160:
                return
            L_0x0161:
                r2 = move-exception
            L_0x0162:
                if (r3 == 0) goto L_0x0172
                boolean r5 = r3.isOpen()
                if (r5 == 0) goto L_0x0172
                r3.close()     // Catch:{ IOException -> 0x016e }
                goto L_0x0172
            L_0x016e:
                r3 = move-exception
                com.facebook.common.logging.FLog.m65e((java.lang.String) r1, (java.lang.String) r0, (java.lang.Throwable) r3)
            L_0x0172:
                if (r4 == 0) goto L_0x0182
                boolean r0 = r4.isOpen()
                if (r0 == 0) goto L_0x0182
                r4.close()     // Catch:{ IOException -> 0x017e }
                goto L_0x0182
            L_0x017e:
                r0 = move-exception
                com.facebook.common.logging.FLog.m65e((java.lang.String) r1, (java.lang.String) r14, (java.lang.Throwable) r0)
            L_0x0182:
                throw r2
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.camera.CameraRollManager.SaveToCameraRoll.doInBackgroundGuarded(java.lang.Void[]):void");
        }
    }

    public void getPhotos(ReadableMap readableMap, Promise promise) {
        int i = readableMap.getInt("first");
        String string = readableMap.hasKey("after") ? readableMap.getString("after") : null;
        String string2 = readableMap.hasKey("groupName") ? readableMap.getString("groupName") : null;
        String string3 = readableMap.hasKey("assetType") ? readableMap.getString("assetType") : ASSET_TYPE_PHOTOS;
        Integer valueOf = readableMap.hasKey("maxSize") ? Integer.valueOf(readableMap.getInt("maxSize")) : null;
        ReadableArray array = readableMap.hasKey("mimeTypes") ? readableMap.getArray("mimeTypes") : null;
        if (!readableMap.hasKey("groupTypes")) {
            new GetMediaTask(getReactApplicationContext(), i, string, string2, array, string3, valueOf, promise).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            return;
        }
        throw new JSApplicationIllegalArgumentException("groupTypes is not supported on Android");
    }

    private static class GetMediaTask extends GuardedAsyncTask<Void, Void> {
        private final String mAfter;
        private final String mAssetType;
        private final Context mContext;
        private final int mFirst;
        private final String mGroupName;
        private final Integer mMaxSize;
        private final ReadableArray mMimeTypes;
        private final Promise mPromise;

        private GetMediaTask(ReactContext reactContext, int i, String str, String str2, ReadableArray readableArray, String str3, Integer num, Promise promise) {
            super(reactContext);
            this.mContext = reactContext;
            this.mFirst = i;
            this.mAfter = str;
            this.mGroupName = str2;
            this.mMimeTypes = readableArray;
            this.mPromise = promise;
            this.mAssetType = str3;
            this.mMaxSize = num;
        }

        /* access modifiers changed from: protected */
        public void doInBackgroundGuarded(Void... voidArr) {
            Cursor query;
            StringBuilder sb = new StringBuilder(BuildConfig.VERSION_NAME);
            ArrayList arrayList = new ArrayList();
            if (!TextUtils.isEmpty(this.mAfter)) {
                sb.append(" AND datetaken < ?");
                arrayList.add(this.mAfter);
            }
            if (!TextUtils.isEmpty(this.mGroupName)) {
                sb.append(" AND bucket_display_name = ?");
                arrayList.add(this.mGroupName);
            }
            if (this.mMaxSize != null) {
                sb.append(" AND _size < ?");
                arrayList.add(this.mMaxSize.toString());
            }
            String str = this.mAssetType;
            char c = 65535;
            int hashCode = str.hashCode();
            if (hashCode != -1905167199) {
                if (hashCode != -1732810888) {
                    if (hashCode == 65921 && str.equals(CameraRollManager.ASSET_TYPE_ALL)) {
                        c = 2;
                    }
                } else if (str.equals(CameraRollManager.ASSET_TYPE_VIDEOS)) {
                    c = 1;
                }
            } else if (str.equals(CameraRollManager.ASSET_TYPE_PHOTOS)) {
                c = 0;
            }
            if (c == 0) {
                sb.append(" AND media_type = 1");
            } else if (c == 1) {
                sb.append(" AND media_type = 3");
            } else if (c != 2) {
                Promise promise = this.mPromise;
                promise.reject(CameraRollManager.ERROR_UNABLE_TO_FILTER, "Invalid filter option: '" + this.mAssetType + "'. Expected one of '" + CameraRollManager.ASSET_TYPE_PHOTOS + "', '" + CameraRollManager.ASSET_TYPE_VIDEOS + "' or '" + CameraRollManager.ASSET_TYPE_ALL + "'.");
                return;
            } else {
                sb.append(" AND media_type IN (3,1)");
            }
            ReadableArray readableArray = this.mMimeTypes;
            if (readableArray != null && readableArray.size() > 0) {
                sb.append(" AND mime_type IN (");
                for (int i = 0; i < this.mMimeTypes.size(); i++) {
                    sb.append("?,");
                    arrayList.add(this.mMimeTypes.getString(i));
                }
                sb.replace(sb.length() - 1, sb.length(), ")");
            }
            WritableNativeMap writableNativeMap = new WritableNativeMap();
            ContentResolver contentResolver = this.mContext.getContentResolver();
            try {
                query = contentResolver.query(MediaStore.Files.getContentUri("external"), CameraRollManager.PROJECTION, sb.toString(), (String[]) arrayList.toArray(new String[arrayList.size()]), "datetaken DESC, date_modified DESC LIMIT " + (this.mFirst + 1));
                if (query == null) {
                    this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD, "Could not get media");
                    return;
                }
                CameraRollManager.putEdges(contentResolver, query, writableNativeMap, this.mFirst);
                CameraRollManager.putPageInfo(query, writableNativeMap, this.mFirst);
                query.close();
                this.mPromise.resolve(writableNativeMap);
            } catch (SecurityException e) {
                this.mPromise.reject(CameraRollManager.ERROR_UNABLE_TO_LOAD_PERMISSION, "Could not get media: need READ_EXTERNAL_STORAGE permission", (Throwable) e);
            } catch (Throwable th) {
                query.close();
                this.mPromise.resolve(writableNativeMap);
                throw th;
            }
        }
    }

    /* access modifiers changed from: private */
    public static void putPageInfo(Cursor cursor, WritableMap writableMap, int i) {
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        writableNativeMap.putBoolean("has_next_page", i < cursor.getCount());
        if (i < cursor.getCount()) {
            cursor.moveToPosition(i - 1);
            writableNativeMap.putString("end_cursor", cursor.getString(cursor.getColumnIndex("datetaken")));
        }
        writableMap.putMap("page_info", writableNativeMap);
    }

    /* access modifiers changed from: private */
    public static void putEdges(ContentResolver contentResolver, Cursor cursor, WritableMap writableMap, int i) {
        int i2;
        WritableNativeArray writableNativeArray;
        Cursor cursor2 = cursor;
        WritableNativeArray writableNativeArray2 = new WritableNativeArray();
        cursor.moveToFirst();
        int columnIndex = cursor2.getColumnIndex("_id");
        int columnIndex2 = cursor2.getColumnIndex("mime_type");
        int columnIndex3 = cursor2.getColumnIndex("bucket_display_name");
        int columnIndex4 = cursor2.getColumnIndex("datetaken");
        int columnIndex5 = cursor2.getColumnIndex("width");
        int columnIndex6 = cursor2.getColumnIndex("height");
        int columnIndex7 = cursor2.getColumnIndex("longitude");
        int columnIndex8 = cursor2.getColumnIndex("latitude");
        int columnIndex9 = cursor2.getColumnIndex("_data");
        int i3 = i;
        int i4 = 0;
        while (i4 < i3 && !cursor.isAfterLast()) {
            WritableNativeMap writableNativeMap = new WritableNativeMap();
            WritableNativeMap writableNativeMap2 = new WritableNativeMap();
            WritableNativeMap writableNativeMap3 = writableNativeMap2;
            WritableNativeArray writableNativeArray3 = writableNativeArray2;
            WritableNativeMap writableNativeMap4 = writableNativeMap;
            int i5 = i4;
            int i6 = columnIndex;
            int i7 = columnIndex8;
            int i8 = columnIndex5;
            int i9 = columnIndex7;
            if (putImageInfo(contentResolver, cursor, writableNativeMap2, columnIndex, columnIndex5, columnIndex6, columnIndex9, columnIndex2)) {
                WritableNativeMap writableNativeMap5 = writableNativeMap3;
                putBasicNodeInfo(cursor2, writableNativeMap5, columnIndex2, columnIndex3, columnIndex4);
                putLocationInfo(cursor2, writableNativeMap5, i9, i7);
                writableNativeMap4.putMap("node", writableNativeMap5);
                writableNativeArray = writableNativeArray3;
                writableNativeArray.pushMap(writableNativeMap4);
                i2 = i5;
            } else {
                writableNativeArray = writableNativeArray3;
                i2 = i5 - 1;
            }
            cursor.moveToNext();
            i4 = i2 + 1;
            i3 = i;
            writableNativeArray2 = writableNativeArray;
            columnIndex8 = i7;
            columnIndex7 = i9;
            columnIndex = i6;
            columnIndex5 = i8;
        }
        writableMap.putArray("edges", writableNativeArray2);
    }

    private static void putBasicNodeInfo(Cursor cursor, WritableMap writableMap, int i, int i2, int i3) {
        writableMap.putString("type", cursor.getString(i));
        writableMap.putString("group_name", cursor.getString(i2));
        writableMap.putDouble("timestamp", ((double) cursor.getLong(i3)) / 1000.0d);
    }

    private static boolean putImageInfo(ContentResolver contentResolver, Cursor cursor, WritableMap writableMap, int i, int i2, int i3, int i4, int i5) {
        AssetFileDescriptor openAssetFileDescriptor;
        MediaMetadataRetriever mediaMetadataRetriever;
        WritableNativeMap writableNativeMap = new WritableNativeMap();
        Uri parse = Uri.parse("file://" + cursor.getString(i4));
        writableNativeMap.putString("uri", parse.toString());
        float f = (float) cursor.getInt(i2);
        float f2 = (float) cursor.getInt(i3);
        String string = cursor.getString(i5);
        if (string != null && string.startsWith(MimeTypes.BASE_TYPE_VIDEO)) {
            try {
                openAssetFileDescriptor = contentResolver.openAssetFileDescriptor(parse, "r");
                mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(openAssetFileDescriptor.getFileDescriptor());
                if (f <= 0.0f || f2 <= 0.0f) {
                    try {
                        f = (float) Integer.parseInt(mediaMetadataRetriever.extractMetadata(18));
                        f2 = (float) Integer.parseInt(mediaMetadataRetriever.extractMetadata(19));
                    } catch (NumberFormatException e) {
                        FLog.m65e(ReactConstants.TAG, "Number format exception occurred while trying to fetch video metadata for " + parse.toString(), (Throwable) e);
                        mediaMetadataRetriever.release();
                        openAssetFileDescriptor.close();
                        return false;
                    }
                }
                writableNativeMap.putInt("playableDuration", Integer.parseInt(mediaMetadataRetriever.extractMetadata(9)) / 1000);
                mediaMetadataRetriever.release();
                openAssetFileDescriptor.close();
            } catch (Exception e2) {
                FLog.m65e(ReactConstants.TAG, "Could not get video metadata for " + parse.toString(), (Throwable) e2);
                return false;
            } catch (Throwable th) {
                mediaMetadataRetriever.release();
                openAssetFileDescriptor.close();
                throw th;
            }
        }
        if (f <= 0.0f || f2 <= 0.0f) {
            try {
                AssetFileDescriptor openAssetFileDescriptor2 = contentResolver.openAssetFileDescriptor(parse, "r");
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFileDescriptor(openAssetFileDescriptor2.getFileDescriptor(), (Rect) null, options);
                float f3 = (float) options.outWidth;
                float f4 = (float) options.outHeight;
                openAssetFileDescriptor2.close();
                float f5 = f3;
                f2 = f4;
                f = f5;
            } catch (IOException e3) {
                FLog.m65e(ReactConstants.TAG, "Could not get width/height for " + parse.toString(), (Throwable) e3);
                return false;
            }
        }
        writableNativeMap.putDouble("width", (double) f);
        writableNativeMap.putDouble("height", (double) f2);
        writableMap.putMap("image", writableNativeMap);
        return true;
    }

    private static void putLocationInfo(Cursor cursor, WritableMap writableMap, int i, int i2) {
        double d = cursor.getDouble(i);
        double d2 = cursor.getDouble(i2);
        if (d > 0.0d || d2 > 0.0d) {
            WritableNativeMap writableNativeMap = new WritableNativeMap();
            writableNativeMap.putDouble("longitude", d);
            writableNativeMap.putDouble("latitude", d2);
            writableMap.putMap(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID, writableNativeMap);
        }
    }
}
