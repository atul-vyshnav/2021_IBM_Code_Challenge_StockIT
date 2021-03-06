package com.RNFetchBlob;

import android.net.Uri;
import android.util.Base64;
import com.RNFetchBlob.RNFetchBlobReq;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;
import org.apache.commons.p029io.IOUtils;

class RNFetchBlobBody extends RequestBody {
    private File bodyCache;
    private Boolean chunkedEncoding = false;
    private long contentLength = 0;
    private ReadableArray form;
    private String mTaskId;
    private MediaType mime;
    private String rawBody;
    int reported = 0;
    private InputStream requestStream;
    private RNFetchBlobReq.RequestType requestType;

    RNFetchBlobBody(String str) {
        this.mTaskId = str;
    }

    /* access modifiers changed from: package-private */
    public RNFetchBlobBody chunkedEncoding(boolean z) {
        this.chunkedEncoding = Boolean.valueOf(z);
        return this;
    }

    /* access modifiers changed from: package-private */
    public RNFetchBlobBody setMIME(MediaType mediaType) {
        this.mime = mediaType;
        return this;
    }

    /* access modifiers changed from: package-private */
    public RNFetchBlobBody setRequestType(RNFetchBlobReq.RequestType requestType2) {
        this.requestType = requestType2;
        return this;
    }

    /* access modifiers changed from: package-private */
    public RNFetchBlobBody setBody(String str) {
        this.rawBody = str;
        if (str == null) {
            this.rawBody = "";
            this.requestType = RNFetchBlobReq.RequestType.AsIs;
        }
        try {
            int i = C06391.$SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType[this.requestType.ordinal()];
            if (i == 1) {
                InputStream requestStream2 = getRequestStream();
                this.requestStream = requestStream2;
                this.contentLength = (long) requestStream2.available();
            } else if (i == 2) {
                this.contentLength = (long) this.rawBody.getBytes().length;
                this.requestStream = new ByteArrayInputStream(this.rawBody.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            RNFetchBlobUtils.emitWarningEvent("RNFetchBlob failed to create single content request body :" + e.getLocalizedMessage() + IOUtils.LINE_SEPARATOR_WINDOWS);
        }
        return this;
    }

    /* renamed from: com.RNFetchBlob.RNFetchBlobBody$1 */
    static /* synthetic */ class C06391 {
        static final /* synthetic */ int[] $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.RNFetchBlob.RNFetchBlobReq$RequestType[] r0 = com.RNFetchBlob.RNFetchBlobReq.RequestType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType = r0
                com.RNFetchBlob.RNFetchBlobReq$RequestType r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.SingleFile     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.RNFetchBlob.RNFetchBlobReq$RequestType r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.AsIs     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$RNFetchBlob$RNFetchBlobReq$RequestType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.RNFetchBlob.RNFetchBlobReq$RequestType r1 = com.RNFetchBlob.RNFetchBlobReq.RequestType.Others     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobBody.C06391.<clinit>():void");
        }
    }

    /* access modifiers changed from: package-private */
    public RNFetchBlobBody setBody(ReadableArray readableArray) {
        this.form = readableArray;
        try {
            this.bodyCache = createMultipartBodyCache();
            this.requestStream = new FileInputStream(this.bodyCache);
            this.contentLength = this.bodyCache.length();
        } catch (Exception e) {
            e.printStackTrace();
            RNFetchBlobUtils.emitWarningEvent("RNFetchBlob failed to create request multipart body :" + e.getLocalizedMessage());
        }
        return this;
    }

    public long contentLength() {
        if (this.chunkedEncoding.booleanValue()) {
            return -1;
        }
        return this.contentLength;
    }

    public MediaType contentType() {
        return this.mime;
    }

    public void writeTo(BufferedSink bufferedSink) {
        try {
            pipeStreamToSink(this.requestStream, bufferedSink);
        } catch (Exception e) {
            RNFetchBlobUtils.emitWarningEvent(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean clearRequestBody() {
        try {
            if (this.bodyCache == null || !this.bodyCache.exists()) {
                return true;
            }
            this.bodyCache.delete();
            return true;
        } catch (Exception e) {
            RNFetchBlobUtils.emitWarningEvent(e.getLocalizedMessage());
            return false;
        }
    }

    private InputStream getRequestStream() throws Exception {
        if (this.rawBody.startsWith(RNFetchBlobConst.FILE_PREFIX)) {
            String normalizePath = RNFetchBlobFS.normalizePath(this.rawBody.substring(19));
            if (RNFetchBlobFS.isAsset(normalizePath)) {
                try {
                    return C0626RNFetchBlob.RCTContext.getAssets().open(normalizePath.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, ""));
                } catch (Exception e) {
                    throw new Exception("error when getting request stream from asset : " + e.getLocalizedMessage());
                }
            } else {
                File file = new File(RNFetchBlobFS.normalizePath(normalizePath));
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    return new FileInputStream(file);
                } catch (Exception e2) {
                    throw new Exception("error when getting request stream: " + e2.getLocalizedMessage());
                }
            }
        } else if (this.rawBody.startsWith(RNFetchBlobConst.CONTENT_PREFIX)) {
            String substring = this.rawBody.substring(22);
            try {
                return C0626RNFetchBlob.RCTContext.getContentResolver().openInputStream(Uri.parse(substring));
            } catch (Exception e3) {
                throw new Exception("error when getting request stream for content URI: " + substring, e3);
            }
        } else {
            try {
                return new ByteArrayInputStream(Base64.decode(this.rawBody, 0));
            } catch (Exception e4) {
                throw new Exception("error when getting request stream: " + e4.getLocalizedMessage());
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0148, code lost:
        if (r10 != null) goto L_0x014a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x014a, code lost:
        r10.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x016f, code lost:
        if (r10 == null) goto L_0x01bd;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.io.File createMultipartBodyCache() throws java.io.IOException {
        /*
            r17 = this;
            r1 = r17
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "RNFetchBlob-"
            r0.append(r2)
            java.lang.String r2 = r1.mTaskId
            r0.append(r2)
            java.lang.String r2 = r0.toString()
            com.facebook.react.bridge.ReactApplicationContext r0 = com.RNFetchBlob.C0626RNFetchBlob.RCTContext
            java.io.File r0 = r0.getCacheDir()
            java.lang.String r3 = "rnfb-form-tmp"
            java.lang.String r4 = ""
            java.io.File r3 = java.io.File.createTempFile(r3, r4, r0)
            java.io.FileOutputStream r5 = new java.io.FileOutputStream
            r5.<init>(r3)
            java.util.ArrayList r0 = r17.countFormDataLength()
            com.facebook.react.bridge.ReactApplicationContext r6 = com.RNFetchBlob.C0626RNFetchBlob.RCTContext
            java.util.Iterator r7 = r0.iterator()
        L_0x0032:
            boolean r0 = r7.hasNext()
            java.lang.String r8 = "--"
            if (r0 == 0) goto L_0x01c8
            java.lang.Object r0 = r7.next()
            com.RNFetchBlob.RNFetchBlobBody$FormField r0 = (com.RNFetchBlob.RNFetchBlobBody.FormField) r0
            java.lang.String r9 = r0.data
            java.lang.String r10 = r0.name
            if (r10 == 0) goto L_0x0032
            if (r9 != 0) goto L_0x0049
            goto L_0x0032
        L_0x0049:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            r11.append(r8)
            r11.append(r2)
            java.lang.String r8 = "\r\n"
            r11.append(r8)
            java.lang.String r11 = r11.toString()
            java.lang.String r12 = r0.filename
            java.lang.String r13 = "\r\n\r\n"
            java.lang.String r14 = "Content-Type: "
            java.lang.String r15 = "\"\r\n"
            r16 = r7
            java.lang.String r7 = "Content-Disposition: form-data; name=\""
            if (r12 == 0) goto L_0x0181
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            r12.append(r11)
            r12.append(r7)
            r12.append(r10)
            java.lang.String r7 = "\"; filename=\""
            r12.append(r7)
            java.lang.String r7 = r0.filename
            r12.append(r7)
            r12.append(r15)
            java.lang.String r7 = r12.toString()
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            r10.append(r7)
            r10.append(r14)
            java.lang.String r0 = r0.mime
            r10.append(r0)
            r10.append(r13)
            java.lang.String r0 = r10.toString()
            byte[] r0 = r0.getBytes()
            r5.write(r0)
            java.lang.String r0 = "RNFetchBlob-file://"
            boolean r0 = r9.startsWith(r0)
            java.lang.String r7 = ", "
            if (r0 == 0) goto L_0x012a
            r0 = 19
            java.lang.String r0 = r9.substring(r0)
            java.lang.String r9 = com.RNFetchBlob.RNFetchBlobFS.normalizePath(r0)
            boolean r0 = com.RNFetchBlob.RNFetchBlobFS.isAsset(r9)
            if (r0 == 0) goto L_0x00f6
            java.lang.String r0 = "bundle-assets://"
            java.lang.String r0 = r9.replace(r0, r4)     // Catch:{ IOException -> 0x00d5 }
            android.content.res.AssetManager r10 = r6.getAssets()     // Catch:{ IOException -> 0x00d5 }
            java.io.InputStream r0 = r10.open(r0)     // Catch:{ IOException -> 0x00d5 }
            r1.pipeStreamToFileStream(r0, r5)     // Catch:{ IOException -> 0x00d5 }
            goto L_0x01bd
        L_0x00d5:
            r0 = move-exception
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            r10.<init>()
            java.lang.String r11 = "Failed to create form data asset :"
            r10.append(r11)
            r10.append(r9)
            r10.append(r7)
            java.lang.String r0 = r0.getLocalizedMessage()
            r10.append(r0)
            java.lang.String r0 = r10.toString()
            com.RNFetchBlob.RNFetchBlobUtils.emitWarningEvent(r0)
            goto L_0x01bd
        L_0x00f6:
            java.io.File r0 = new java.io.File
            java.lang.String r7 = com.RNFetchBlob.RNFetchBlobFS.normalizePath(r9)
            r0.<init>(r7)
            boolean r7 = r0.exists()
            if (r7 == 0) goto L_0x010f
            java.io.FileInputStream r7 = new java.io.FileInputStream
            r7.<init>(r0)
            r1.pipeStreamToFileStream(r7, r5)
            goto L_0x01bd
        L_0x010f:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r7 = "Failed to create form data from path :"
            r0.append(r7)
            r0.append(r9)
            java.lang.String r7 = ", file not exists."
            r0.append(r7)
            java.lang.String r0 = r0.toString()
            com.RNFetchBlob.RNFetchBlobUtils.emitWarningEvent(r0)
            goto L_0x01bd
        L_0x012a:
            java.lang.String r0 = "RNFetchBlob-content://"
            boolean r0 = r9.startsWith(r0)
            if (r0 == 0) goto L_0x0178
            r0 = 22
            java.lang.String r9 = r9.substring(r0)
            r10 = 0
            android.content.ContentResolver r0 = r6.getContentResolver()     // Catch:{ Exception -> 0x0150 }
            android.net.Uri r11 = android.net.Uri.parse(r9)     // Catch:{ Exception -> 0x0150 }
            java.io.InputStream r10 = r0.openInputStream(r11)     // Catch:{ Exception -> 0x0150 }
            r1.pipeStreamToFileStream(r10, r5)     // Catch:{ Exception -> 0x0150 }
            if (r10 == 0) goto L_0x01bd
        L_0x014a:
            r10.close()
            goto L_0x01bd
        L_0x014e:
            r0 = move-exception
            goto L_0x0172
        L_0x0150:
            r0 = move-exception
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x014e }
            r11.<init>()     // Catch:{ all -> 0x014e }
            java.lang.String r12 = "Failed to create form data from content URI:"
            r11.append(r12)     // Catch:{ all -> 0x014e }
            r11.append(r9)     // Catch:{ all -> 0x014e }
            r11.append(r7)     // Catch:{ all -> 0x014e }
            java.lang.String r0 = r0.getLocalizedMessage()     // Catch:{ all -> 0x014e }
            r11.append(r0)     // Catch:{ all -> 0x014e }
            java.lang.String r0 = r11.toString()     // Catch:{ all -> 0x014e }
            com.RNFetchBlob.RNFetchBlobUtils.emitWarningEvent(r0)     // Catch:{ all -> 0x014e }
            if (r10 == 0) goto L_0x01bd
            goto L_0x014a
        L_0x0172:
            if (r10 == 0) goto L_0x0177
            r10.close()
        L_0x0177:
            throw r0
        L_0x0178:
            r0 = 0
            byte[] r0 = android.util.Base64.decode(r9, r0)
            r5.write(r0)
            goto L_0x01bd
        L_0x0181:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r11)
            r9.append(r7)
            r9.append(r10)
            r9.append(r15)
            java.lang.String r7 = r9.toString()
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            r9.append(r7)
            r9.append(r14)
            java.lang.String r7 = r0.mime
            r9.append(r7)
            r9.append(r13)
            java.lang.String r7 = r9.toString()
            byte[] r7 = r7.getBytes()
            r5.write(r7)
            java.lang.String r0 = r0.data
            byte[] r0 = r0.getBytes()
            r5.write(r0)
        L_0x01bd:
            byte[] r0 = r8.getBytes()
            r5.write(r0)
            r7 = r16
            goto L_0x0032
        L_0x01c8:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r8)
            r0.append(r2)
            java.lang.String r2 = "--\r\n"
            r0.append(r2)
            java.lang.String r0 = r0.toString()
            byte[] r0 = r0.getBytes()
            r5.write(r0)
            r5.flush()
            r5.close()
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.RNFetchBlob.RNFetchBlobBody.createMultipartBodyCache():java.io.File");
    }

    private void pipeStreamToSink(InputStream inputStream, BufferedSink bufferedSink) throws IOException {
        byte[] bArr = new byte[10240];
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr, 0, 10240);
            if (read > 0) {
                bufferedSink.write(bArr, 0, read);
                j += (long) read;
                emitUploadProgress(j);
            } else {
                inputStream.close();
                return;
            }
        }
    }

    private void pipeStreamToFileStream(InputStream inputStream, FileOutputStream fileOutputStream) throws IOException {
        byte[] bArr = new byte[10240];
        while (true) {
            int read = inputStream.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                inputStream.close();
                return;
            }
        }
    }

    private ArrayList<FormField> countFormDataLength() throws IOException {
        long j;
        int i;
        ArrayList<FormField> arrayList = new ArrayList<>();
        ReactApplicationContext reactApplicationContext = C0626RNFetchBlob.RCTContext;
        long j2 = 0;
        for (int i2 = 0; i2 < this.form.size(); i2++) {
            FormField formField = new FormField(this.form.getMap(i2));
            arrayList.add(formField);
            if (formField.data == null) {
                RNFetchBlobUtils.emitWarningEvent("RNFetchBlob multipart request builder has found a field without `data` property, the field `" + formField.name + "` will be removed implicitly.");
            } else {
                if (formField.filename != null) {
                    String str = formField.data;
                    if (str.startsWith(RNFetchBlobConst.FILE_PREFIX)) {
                        String normalizePath = RNFetchBlobFS.normalizePath(str.substring(19));
                        if (RNFetchBlobFS.isAsset(normalizePath)) {
                            try {
                                i = reactApplicationContext.getAssets().open(normalizePath.replace(RNFetchBlobConst.FILE_PREFIX_BUNDLE_ASSET, "")).available();
                            } catch (IOException e) {
                                RNFetchBlobUtils.emitWarningEvent(e.getLocalizedMessage());
                            }
                        } else {
                            j = new File(RNFetchBlobFS.normalizePath(normalizePath)).length();
                            j2 += j;
                        }
                    } else if (str.startsWith(RNFetchBlobConst.CONTENT_PREFIX)) {
                        String substring = str.substring(22);
                        InputStream inputStream = null;
                        try {
                            inputStream = reactApplicationContext.getContentResolver().openInputStream(Uri.parse(substring));
                            j2 += (long) inputStream.available();
                            if (inputStream == null) {
                            }
                        } catch (Exception e2) {
                            RNFetchBlobUtils.emitWarningEvent("Failed to estimate form data length from content URI:" + substring + ", " + e2.getLocalizedMessage());
                            if (inputStream == null) {
                            }
                        } catch (Throwable th) {
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            throw th;
                        }
                        inputStream.close();
                    } else {
                        i = Base64.decode(str, 0).length;
                    }
                } else {
                    i = formField.data.getBytes().length;
                }
                j = (long) i;
                j2 += j;
            }
        }
        this.contentLength = j2;
        return arrayList;
    }

    private class FormField {
        public String data;
        String filename;
        String mime;
        public String name;

        FormField(ReadableMap readableMap) {
            if (readableMap.hasKey("name")) {
                this.name = readableMap.getString("name");
            }
            if (readableMap.hasKey("filename")) {
                this.filename = readableMap.getString("filename");
            }
            if (readableMap.hasKey("type")) {
                this.mime = readableMap.getString("type");
            } else {
                this.mime = this.filename == null ? "text/plain" : "application/octet-stream";
            }
            if (readableMap.hasKey("data")) {
                this.data = readableMap.getString("data");
            }
        }
    }

    private void emitUploadProgress(long j) {
        RNFetchBlobProgressConfig reportUploadProgress = RNFetchBlobReq.getReportUploadProgress(this.mTaskId);
        if (reportUploadProgress != null) {
            long j2 = this.contentLength;
            if (j2 != 0 && reportUploadProgress.shouldReport(((float) j) / ((float) j2))) {
                WritableMap createMap = Arguments.createMap();
                createMap.putString("taskId", this.mTaskId);
                createMap.putString("written", String.valueOf(j));
                createMap.putString("total", String.valueOf(this.contentLength));
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) C0626RNFetchBlob.RCTContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(RNFetchBlobConst.EVENT_UPLOAD_PROGRESS, createMap);
            }
        }
    }
}
