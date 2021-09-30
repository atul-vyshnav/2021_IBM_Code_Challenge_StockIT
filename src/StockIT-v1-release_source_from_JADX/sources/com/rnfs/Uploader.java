package com.rnfs;

import android.os.AsyncTask;
import android.webkit.MimeTypeMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Uploader extends AsyncTask<UploadParams, int[], UploadResult> {
    private AtomicBoolean mAbort = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public UploadParams mParams;
    /* access modifiers changed from: private */
    public UploadResult res;

    /* access modifiers changed from: protected */
    public UploadResult doInBackground(UploadParams... uploadParamsArr) {
        this.mParams = uploadParamsArr[0];
        this.res = new UploadResult();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Uploader.this.upload(Uploader.this.mParams, Uploader.this.res);
                    Uploader.this.mParams.onUploadComplete.onUploadComplete(Uploader.this.res);
                } catch (Exception e) {
                    Uploader.this.res.exception = e;
                    Uploader.this.mParams.onUploadComplete.onUploadComplete(Uploader.this.res);
                }
            }
        }).start();
        return this.res;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Can't wrap try/catch for region: R(2:38|39) */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        r23 = r15.getString(r3);
        r26 = r15.getString(r2);
        r8 = r1.getMimeType(r15.getString(r4));
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x0123 */
    /* JADX WARNING: Removed duplicated region for block: B:123:0x0368  */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x036d  */
    /* JADX WARNING: Removed duplicated region for block: B:127:0x0372  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x0377  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void upload(com.rnfs.UploadParams r37, com.rnfs.UploadResult r38) throws java.lang.Exception {
        /*
            r36 = this;
            r1 = r36
            r0 = r37
            java.lang.String r2 = "filename"
            java.lang.String r3 = "name"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "\r\n"
            r4.append(r5)
            java.lang.String r6 = "--"
            r4.append(r6)
            java.lang.String r7 = "*****"
            r4.append(r7)
            r4.append(r6)
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            java.util.ArrayList<com.facebook.react.bridge.ReadableMap> r9 = r0.files     // Catch:{ all -> 0x0361 }
            java.lang.Object[] r9 = r9.toArray()     // Catch:{ all -> 0x0361 }
            boolean r10 = r0.binaryStreamOnly     // Catch:{ all -> 0x0361 }
            java.net.URL r11 = r0.src     // Catch:{ all -> 0x0361 }
            java.net.URLConnection r11 = r11.openConnection()     // Catch:{ all -> 0x0361 }
            java.net.HttpURLConnection r11 = (java.net.HttpURLConnection) r11     // Catch:{ all -> 0x0361 }
            r12 = 1
            r11.setDoOutput(r12)     // Catch:{ all -> 0x035b }
            com.facebook.react.bridge.ReadableMap r13 = r0.headers     // Catch:{ all -> 0x035b }
            com.facebook.react.bridge.ReadableMapKeySetIterator r13 = r13.keySetIterator()     // Catch:{ all -> 0x035b }
            java.lang.String r14 = r0.method     // Catch:{ all -> 0x035b }
            r11.setRequestMethod(r14)     // Catch:{ all -> 0x035b }
            if (r10 != 0) goto L_0x005d
            java.lang.String r14 = "Content-Type"
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x035b }
            r15.<init>()     // Catch:{ all -> 0x035b }
            java.lang.String r8 = "multipart/form-data;boundary="
            r15.append(r8)     // Catch:{ all -> 0x035b }
            r15.append(r7)     // Catch:{ all -> 0x035b }
            java.lang.String r8 = r15.toString()     // Catch:{ all -> 0x035b }
            r11.setRequestProperty(r14, r8)     // Catch:{ all -> 0x035b }
        L_0x005d:
            boolean r8 = r13.hasNextKey()     // Catch:{ all -> 0x035b }
            if (r8 == 0) goto L_0x0071
            java.lang.String r8 = r13.nextKey()     // Catch:{ all -> 0x035b }
            com.facebook.react.bridge.ReadableMap r14 = r0.headers     // Catch:{ all -> 0x035b }
            java.lang.String r14 = r14.getString(r8)     // Catch:{ all -> 0x035b }
            r11.setRequestProperty(r8, r14)     // Catch:{ all -> 0x035b }
            goto L_0x005d
        L_0x0071:
            com.facebook.react.bridge.ReadableMap r8 = r0.fields     // Catch:{ all -> 0x035b }
            com.facebook.react.bridge.ReadableMapKeySetIterator r8 = r8.keySetIterator()     // Catch:{ all -> 0x035b }
            java.lang.String r13 = ""
            r14 = r13
        L_0x007a:
            boolean r15 = r8.hasNextKey()     // Catch:{ all -> 0x035b }
            java.lang.String r12 = "\""
            r17 = r11
            java.lang.String r11 = "Content-Disposition: form-data; name=\""
            if (r15 == 0) goto L_0x00cb
            java.lang.String r15 = r8.nextKey()     // Catch:{ all -> 0x00c6 }
            r18 = r8
            com.facebook.react.bridge.ReadableMap r8 = r0.fields     // Catch:{ all -> 0x00c6 }
            java.lang.String r8 = r8.getString(r15)     // Catch:{ all -> 0x00c6 }
            r19 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x00c6 }
            r4.<init>()     // Catch:{ all -> 0x00c6 }
            r4.append(r14)     // Catch:{ all -> 0x00c6 }
            r4.append(r6)     // Catch:{ all -> 0x00c6 }
            r4.append(r7)     // Catch:{ all -> 0x00c6 }
            r4.append(r5)     // Catch:{ all -> 0x00c6 }
            r4.append(r11)     // Catch:{ all -> 0x00c6 }
            r4.append(r15)     // Catch:{ all -> 0x00c6 }
            r4.append(r12)     // Catch:{ all -> 0x00c6 }
            r4.append(r5)     // Catch:{ all -> 0x00c6 }
            r4.append(r5)     // Catch:{ all -> 0x00c6 }
            r4.append(r8)     // Catch:{ all -> 0x00c6 }
            r4.append(r5)     // Catch:{ all -> 0x00c6 }
            java.lang.String r14 = r4.toString()     // Catch:{ all -> 0x00c6 }
            r11 = r17
            r8 = r18
            r4 = r19
            r12 = 1
            goto L_0x007a
        L_0x00c6:
            r0 = move-exception
            r8 = r17
            goto L_0x035d
        L_0x00cb:
            r19 = r4
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0357 }
            r4.<init>()     // Catch:{ all -> 0x0357 }
            r4.append(r13)     // Catch:{ all -> 0x0357 }
            r4.append(r14)     // Catch:{ all -> 0x0357 }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x0357 }
            int r8 = r9.length     // Catch:{ all -> 0x0357 }
            java.lang.String[] r8 = new java.lang.String[r8]     // Catch:{ all -> 0x0357 }
            java.util.ArrayList<com.facebook.react.bridge.ReadableMap> r15 = r0.files     // Catch:{ all -> 0x0357 }
            java.util.Iterator r15 = r15.iterator()     // Catch:{ all -> 0x0357 }
            r20 = 0
            r18 = r4
            r22 = r14
            r0 = 0
            r18 = r13
            r13 = r20
        L_0x00f0:
            boolean r23 = r15.hasNext()     // Catch:{ all -> 0x0357 }
            r24 = r4
            java.lang.String r4 = "filepath"
            if (r23 == 0) goto L_0x01e6
            java.lang.Object r23 = r15.next()     // Catch:{ all -> 0x01dc }
            r25 = r15
            r15 = r23
            com.facebook.react.bridge.ReadableMap r15 = (com.facebook.react.bridge.ReadableMap) r15     // Catch:{ all -> 0x01dc }
            java.lang.String r23 = r15.getString(r3)     // Catch:{ NoSuchKeyException -> 0x0121 }
            java.lang.String r26 = r15.getString(r2)     // Catch:{ NoSuchKeyException -> 0x0121 }
            r27 = r8
            java.lang.String r8 = "filetype"
            java.lang.String r8 = r15.getString(r8)     // Catch:{ NoSuchKeyException -> 0x0123 }
        L_0x0114:
            r34 = r23
            r23 = r2
            r2 = r34
            r35 = r26
            r26 = r3
            r3 = r35
            goto L_0x0134
        L_0x0121:
            r27 = r8
        L_0x0123:
            java.lang.String r23 = r15.getString(r3)     // Catch:{ all -> 0x01dc }
            java.lang.String r26 = r15.getString(r2)     // Catch:{ all -> 0x01dc }
            java.lang.String r8 = r15.getString(r4)     // Catch:{ all -> 0x01dc }
            java.lang.String r8 = r1.getMimeType(r8)     // Catch:{ all -> 0x01dc }
            goto L_0x0114
        L_0x0134:
            java.io.File r1 = new java.io.File     // Catch:{ all -> 0x01dc }
            java.lang.String r4 = r15.getString(r4)     // Catch:{ all -> 0x01dc }
            r1.<init>(r4)     // Catch:{ all -> 0x01dc }
            r15 = r0
            long r0 = r1.length()     // Catch:{ all -> 0x01dc }
            long r13 = r13 + r0
            if (r10 != 0) goto L_0x01c7
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dc }
            r4.<init>()     // Catch:{ all -> 0x01dc }
            r4.append(r6)     // Catch:{ all -> 0x01dc }
            r4.append(r7)     // Catch:{ all -> 0x01dc }
            r4.append(r5)     // Catch:{ all -> 0x01dc }
            r4.append(r11)     // Catch:{ all -> 0x01dc }
            r4.append(r2)     // Catch:{ all -> 0x01dc }
            java.lang.String r2 = "\"; filename=\""
            r4.append(r2)     // Catch:{ all -> 0x01dc }
            r4.append(r3)     // Catch:{ all -> 0x01dc }
            r4.append(r12)     // Catch:{ all -> 0x01dc }
            r4.append(r5)     // Catch:{ all -> 0x01dc }
            java.lang.String r2 = "Content-Type: "
            r4.append(r2)     // Catch:{ all -> 0x01dc }
            r4.append(r8)     // Catch:{ all -> 0x01dc }
            r4.append(r5)     // Catch:{ all -> 0x01dc }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x01dc }
            int r3 = r9.length     // Catch:{ all -> 0x01dc }
            r4 = 1
            int r3 = r3 - r4
            r8 = r15
            if (r3 != r8) goto L_0x0184
            int r3 = r19.length()     // Catch:{ all -> 0x01dc }
            r15 = r5
            long r4 = (long) r3     // Catch:{ all -> 0x01dc }
            long r13 = r13 + r4
            goto L_0x0185
        L_0x0184:
            r15 = r5
        L_0x0185:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dc }
            r3.<init>()     // Catch:{ all -> 0x01dc }
            java.lang.String r4 = "Content-length: "
            r3.append(r4)     // Catch:{ all -> 0x01dc }
            r3.append(r0)     // Catch:{ all -> 0x01dc }
            r0 = r15
            r3.append(r0)     // Catch:{ all -> 0x01dc }
            java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x01dc }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dc }
            r3.<init>()     // Catch:{ all -> 0x01dc }
            r3.append(r2)     // Catch:{ all -> 0x01dc }
            r3.append(r1)     // Catch:{ all -> 0x01dc }
            r3.append(r0)     // Catch:{ all -> 0x01dc }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01dc }
            r27[r8] = r3     // Catch:{ all -> 0x01dc }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01dc }
            r3.<init>()     // Catch:{ all -> 0x01dc }
            r5 = r24
            r3.append(r5)     // Catch:{ all -> 0x01dc }
            r3.append(r2)     // Catch:{ all -> 0x01dc }
            r3.append(r1)     // Catch:{ all -> 0x01dc }
            r3.append(r0)     // Catch:{ all -> 0x01dc }
            java.lang.String r1 = r3.toString()     // Catch:{ all -> 0x01dc }
            r4 = r1
            goto L_0x01cc
        L_0x01c7:
            r0 = r5
            r8 = r15
            r5 = r24
            r4 = r5
        L_0x01cc:
            int r1 = r8 + 1
            r5 = r0
            r0 = r1
            r2 = r23
            r15 = r25
            r3 = r26
            r8 = r27
            r1 = r36
            goto L_0x00f0
        L_0x01dc:
            r0 = move-exception
            r2 = 0
            r3 = 0
            r4 = 0
            r1 = r36
            r8 = r17
            goto L_0x0366
        L_0x01e6:
            r0 = r5
            r27 = r8
            r5 = r24
            com.rnfs.UploadParams r2 = r1.mParams     // Catch:{ all -> 0x0357 }
            com.rnfs.UploadParams$onUploadBegin r2 = r2.onUploadBegin     // Catch:{ all -> 0x0357 }
            if (r2 == 0) goto L_0x01f8
            com.rnfs.UploadParams r2 = r1.mParams     // Catch:{ all -> 0x00c6 }
            com.rnfs.UploadParams$onUploadBegin r2 = r2.onUploadBegin     // Catch:{ all -> 0x00c6 }
            r2.onUploadBegin()     // Catch:{ all -> 0x00c6 }
        L_0x01f8:
            if (r10 != 0) goto L_0x0221
            int r2 = r5.length()     // Catch:{ all -> 0x0357 }
            int r3 = r9.length     // Catch:{ all -> 0x0357 }
            int r3 = r3 * 2
            int r2 = r2 + r3
            long r2 = (long) r2     // Catch:{ all -> 0x0357 }
            long r2 = r2 + r13
            java.lang.String r5 = "Content-length"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0357 }
            r6.<init>()     // Catch:{ all -> 0x0357 }
            r7 = r18
            r6.append(r7)     // Catch:{ all -> 0x0357 }
            int r3 = (int) r2     // Catch:{ all -> 0x0357 }
            r6.append(r3)     // Catch:{ all -> 0x0357 }
            java.lang.String r2 = r6.toString()     // Catch:{ all -> 0x0357 }
            r11 = r17
            r11.setRequestProperty(r5, r2)     // Catch:{ all -> 0x035b }
            r11.setFixedLengthStreamingMode(r3)     // Catch:{ all -> 0x035b }
            goto L_0x0223
        L_0x0221:
            r11 = r17
        L_0x0223:
            r11.connect()     // Catch:{ all -> 0x035b }
            java.io.DataOutputStream r2 = new java.io.DataOutputStream     // Catch:{ all -> 0x035b }
            java.io.OutputStream r3 = r11.getOutputStream()     // Catch:{ all -> 0x035b }
            r2.<init>(r3)     // Catch:{ all -> 0x035b }
            java.nio.channels.WritableByteChannel r3 = java.nio.channels.Channels.newChannel(r2)     // Catch:{ all -> 0x0354 }
            if (r10 != 0) goto L_0x023a
            r5 = r22
            r2.writeBytes(r5)     // Catch:{ all -> 0x0354 }
        L_0x023a:
            r5 = r37
            java.util.ArrayList<com.facebook.react.bridge.ReadableMap> r5 = r5.files     // Catch:{ all -> 0x0354 }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x0354 }
            r6 = 0
            r7 = 0
        L_0x0244:
            boolean r8 = r5.hasNext()     // Catch:{ all -> 0x0354 }
            if (r8 == 0) goto L_0x02c4
            java.lang.Object r8 = r5.next()     // Catch:{ all -> 0x0354 }
            com.facebook.react.bridge.ReadableMap r8 = (com.facebook.react.bridge.ReadableMap) r8     // Catch:{ all -> 0x0354 }
            if (r10 != 0) goto L_0x0257
            r9 = r27[r6]     // Catch:{ all -> 0x0354 }
            r2.writeBytes(r9)     // Catch:{ all -> 0x0354 }
        L_0x0257:
            java.io.File r9 = new java.io.File     // Catch:{ all -> 0x0354 }
            java.lang.String r8 = r8.getString(r4)     // Catch:{ all -> 0x0354 }
            r9.<init>(r8)     // Catch:{ all -> 0x0354 }
            r8 = r4
            r37 = r5
            long r4 = r9.length()     // Catch:{ all -> 0x0354 }
            float r12 = (float) r4     // Catch:{ all -> 0x0354 }
            r15 = 1120403456(0x42c80000, float:100.0)
            float r12 = r12 / r15
            r15 = r7
            r16 = r8
            double r7 = (double) r12     // Catch:{ all -> 0x0354 }
            double r7 = java.lang.Math.ceil(r7)     // Catch:{ all -> 0x0354 }
            long r7 = (long) r7     // Catch:{ all -> 0x0354 }
            java.io.FileInputStream r12 = new java.io.FileInputStream     // Catch:{ all -> 0x0354 }
            r12.<init>(r9)     // Catch:{ all -> 0x0354 }
            java.nio.channels.FileChannel r9 = r12.getChannel()     // Catch:{ all -> 0x0354 }
            r17 = r20
        L_0x027f:
            int r22 = (r17 > r4 ? 1 : (r17 == r4 ? 0 : -1))
            if (r22 >= 0) goto L_0x02b0
            r28 = r9
            r29 = r17
            r31 = r7
            r33 = r3
            long r22 = r28.transferTo(r29, r31, r33)     // Catch:{ all -> 0x0354 }
            long r17 = r17 + r22
            r24 = r3
            com.rnfs.UploadParams r3 = r1.mParams     // Catch:{ all -> 0x0354 }
            com.rnfs.UploadParams$onUploadProgress r3 = r3.onUploadProgress     // Catch:{ all -> 0x0354 }
            if (r3 == 0) goto L_0x02a9
            r25 = r4
            long r3 = (long) r15     // Catch:{ all -> 0x0354 }
            long r3 = r3 + r22
            int r4 = (int) r3     // Catch:{ all -> 0x0354 }
            com.rnfs.UploadParams r3 = r1.mParams     // Catch:{ all -> 0x0354 }
            com.rnfs.UploadParams$onUploadProgress r3 = r3.onUploadProgress     // Catch:{ all -> 0x0354 }
            int r5 = (int) r13     // Catch:{ all -> 0x0354 }
            r3.onUploadProgress(r5, r4)     // Catch:{ all -> 0x0354 }
            r15 = r4
            goto L_0x02ab
        L_0x02a9:
            r25 = r4
        L_0x02ab:
            r3 = r24
            r4 = r25
            goto L_0x027f
        L_0x02b0:
            r24 = r3
            if (r10 != 0) goto L_0x02b7
            r2.writeBytes(r0)     // Catch:{ all -> 0x0354 }
        L_0x02b7:
            int r6 = r6 + 1
            r12.close()     // Catch:{ all -> 0x0354 }
            r5 = r37
            r7 = r15
            r4 = r16
            r3 = r24
            goto L_0x0244
        L_0x02c4:
            if (r10 != 0) goto L_0x02cb
            r0 = r19
            r2.writeBytes(r0)     // Catch:{ all -> 0x0354 }
        L_0x02cb:
            r2.flush()     // Catch:{ all -> 0x0354 }
            r2.close()     // Catch:{ all -> 0x0354 }
            java.io.BufferedInputStream r3 = new java.io.BufferedInputStream     // Catch:{ all -> 0x0354 }
            java.io.InputStream r0 = r11.getInputStream()     // Catch:{ all -> 0x0354 }
            r3.<init>(r0)     // Catch:{ all -> 0x0354 }
            java.io.BufferedReader r4 = new java.io.BufferedReader     // Catch:{ all -> 0x0351 }
            java.io.InputStreamReader r0 = new java.io.InputStreamReader     // Catch:{ all -> 0x0351 }
            r0.<init>(r3)     // Catch:{ all -> 0x0351 }
            r4.<init>(r0)     // Catch:{ all -> 0x0351 }
            com.facebook.react.bridge.WritableMap r0 = com.facebook.react.bridge.Arguments.createMap()     // Catch:{ all -> 0x034e }
            java.util.Map r5 = r11.getHeaderFields()     // Catch:{ all -> 0x034e }
            java.util.Set r5 = r5.entrySet()     // Catch:{ all -> 0x034e }
            java.util.Iterator r5 = r5.iterator()     // Catch:{ all -> 0x034e }
        L_0x02f4:
            boolean r6 = r5.hasNext()     // Catch:{ all -> 0x034e }
            if (r6 == 0) goto L_0x0317
            java.lang.Object r6 = r5.next()     // Catch:{ all -> 0x034e }
            java.util.Map$Entry r6 = (java.util.Map.Entry) r6     // Catch:{ all -> 0x034e }
            java.lang.Object r7 = r6.getKey()     // Catch:{ all -> 0x034e }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ all -> 0x034e }
            java.lang.Object r6 = r6.getValue()     // Catch:{ all -> 0x034e }
            java.util.List r6 = (java.util.List) r6     // Catch:{ all -> 0x034e }
            r8 = 0
            java.lang.Object r6 = r6.get(r8)     // Catch:{ all -> 0x034e }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x034e }
            r0.putString(r7, r6)     // Catch:{ all -> 0x034e }
            goto L_0x02f4
        L_0x0317:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x034e }
            r5.<init>()     // Catch:{ all -> 0x034e }
        L_0x031c:
            java.lang.String r6 = r4.readLine()     // Catch:{ all -> 0x034e }
            if (r6 == 0) goto L_0x032b
            r5.append(r6)     // Catch:{ all -> 0x034e }
            java.lang.String r6 = "\n"
            r5.append(r6)     // Catch:{ all -> 0x034e }
            goto L_0x031c
        L_0x032b:
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x034e }
            int r6 = r11.getResponseCode()     // Catch:{ all -> 0x034e }
            com.rnfs.UploadResult r7 = r1.res     // Catch:{ all -> 0x034e }
            r7.headers = r0     // Catch:{ all -> 0x034e }
            com.rnfs.UploadResult r0 = r1.res     // Catch:{ all -> 0x034e }
            r0.body = r5     // Catch:{ all -> 0x034e }
            com.rnfs.UploadResult r0 = r1.res     // Catch:{ all -> 0x034e }
            r0.statusCode = r6     // Catch:{ all -> 0x034e }
            if (r11 == 0) goto L_0x0344
            r11.disconnect()
        L_0x0344:
            r2.close()
            r3.close()
            r4.close()
            return
        L_0x034e:
            r0 = move-exception
            r8 = r11
            goto L_0x0366
        L_0x0351:
            r0 = move-exception
            r8 = r11
            goto L_0x035f
        L_0x0354:
            r0 = move-exception
            r8 = r11
            goto L_0x035e
        L_0x0357:
            r0 = move-exception
            r11 = r17
            goto L_0x035c
        L_0x035b:
            r0 = move-exception
        L_0x035c:
            r8 = r11
        L_0x035d:
            r2 = 0
        L_0x035e:
            r3 = 0
        L_0x035f:
            r4 = 0
            goto L_0x0366
        L_0x0361:
            r0 = move-exception
            r2 = 0
            r3 = 0
            r4 = 0
            r8 = 0
        L_0x0366:
            if (r8 == 0) goto L_0x036b
            r8.disconnect()
        L_0x036b:
            if (r2 == 0) goto L_0x0370
            r2.close()
        L_0x0370:
            if (r3 == 0) goto L_0x0375
            r3.close()
        L_0x0375:
            if (r4 == 0) goto L_0x037a
            r4.close()
        L_0x037a:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rnfs.Uploader.upload(com.rnfs.UploadParams, com.rnfs.UploadResult):void");
    }

    /* access modifiers changed from: protected */
    public String getMimeType(String str) {
        String fileExtensionFromUrl = MimeTypeMap.getFileExtensionFromUrl(str);
        String mimeTypeFromExtension = fileExtensionFromUrl != null ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtensionFromUrl.toLowerCase()) : null;
        return mimeTypeFromExtension == null ? "*/*" : mimeTypeFromExtension;
    }

    /* access modifiers changed from: protected */
    public void stop() {
        this.mAbort.set(true);
    }
}
