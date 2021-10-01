package com.rnfs;

import android.os.AsyncTask;
import android.os.Build;
import java.net.HttpURLConnection;
import java.util.concurrent.atomic.AtomicBoolean;

public class Downloader extends AsyncTask<DownloadParams, long[], DownloadResult> {
    private AtomicBoolean mAbort = new AtomicBoolean(false);
    /* access modifiers changed from: private */
    public DownloadParams mParam;
    DownloadResult res;

    /* access modifiers changed from: protected */
    public void onPostExecute(Exception exc) {
    }

    /* access modifiers changed from: protected */
    public DownloadResult doInBackground(DownloadParams... downloadParamsArr) {
        this.mParam = downloadParamsArr[0];
        this.res = new DownloadResult();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Downloader.this.download(Downloader.this.mParam, Downloader.this.res);
                    Downloader.this.mParam.onTaskCompleted.onTaskCompleted(Downloader.this.res);
                } catch (Exception e) {
                    Downloader.this.res.exception = e;
                    Downloader.this.mParam.onTaskCompleted.onTaskCompleted(Downloader.this.res);
                }
            }
        }).start();
        return this.res;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x0221  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x0226  */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x022b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void download(com.rnfs.DownloadParams r30, com.rnfs.DownloadResult r31) throws java.lang.Exception {
        /*
            r29 = this;
            r1 = r29
            r0 = r30
            r2 = r31
            r3 = 0
            java.net.URL r4 = r0.src     // Catch:{ all -> 0x021b }
            java.net.URLConnection r4 = r4.openConnection()     // Catch:{ all -> 0x021b }
            java.net.HttpURLConnection r4 = (java.net.HttpURLConnection) r4     // Catch:{ all -> 0x021b }
            com.facebook.react.bridge.ReadableMap r5 = r0.headers     // Catch:{ all -> 0x0216 }
            com.facebook.react.bridge.ReadableMapKeySetIterator r5 = r5.keySetIterator()     // Catch:{ all -> 0x0216 }
        L_0x0015:
            boolean r6 = r5.hasNextKey()     // Catch:{ all -> 0x0216 }
            if (r6 == 0) goto L_0x0029
            java.lang.String r6 = r5.nextKey()     // Catch:{ all -> 0x0216 }
            com.facebook.react.bridge.ReadableMap r7 = r0.headers     // Catch:{ all -> 0x0216 }
            java.lang.String r7 = r7.getString(r6)     // Catch:{ all -> 0x0216 }
            r4.setRequestProperty(r6, r7)     // Catch:{ all -> 0x0216 }
            goto L_0x0015
        L_0x0029:
            int r5 = r0.connectionTimeout     // Catch:{ all -> 0x0216 }
            r4.setConnectTimeout(r5)     // Catch:{ all -> 0x0216 }
            int r5 = r0.readTimeout     // Catch:{ all -> 0x0216 }
            r4.setReadTimeout(r5)     // Catch:{ all -> 0x0216 }
            r4.connect()     // Catch:{ all -> 0x0216 }
            int r5 = r4.getResponseCode()     // Catch:{ all -> 0x0216 }
            long r6 = r1.getContentLength(r4)     // Catch:{ all -> 0x0216 }
            r8 = 200(0xc8, float:2.8E-43)
            r10 = 0
            if (r5 == r8) goto L_0x0055
            r11 = 301(0x12d, float:4.22E-43)
            if (r5 == r11) goto L_0x0053
            r11 = 302(0x12e, float:4.23E-43)
            if (r5 == r11) goto L_0x0053
            r11 = 307(0x133, float:4.3E-43)
            if (r5 == r11) goto L_0x0053
            r11 = 308(0x134, float:4.32E-43)
            if (r5 != r11) goto L_0x0055
        L_0x0053:
            r11 = 1
            goto L_0x0056
        L_0x0055:
            r11 = 0
        L_0x0056:
            if (r11 == 0) goto L_0x0088
            java.lang.String r5 = "Location"
            java.lang.String r5 = r4.getHeaderField(r5)     // Catch:{ all -> 0x0216 }
            r4.disconnect()     // Catch:{ all -> 0x0216 }
            java.net.URL r6 = new java.net.URL     // Catch:{ all -> 0x0216 }
            r6.<init>(r5)     // Catch:{ all -> 0x0216 }
            java.net.URLConnection r5 = r6.openConnection()     // Catch:{ all -> 0x0216 }
            java.net.HttpURLConnection r5 = (java.net.HttpURLConnection) r5     // Catch:{ all -> 0x0216 }
            r4 = 5000(0x1388, float:7.006E-42)
            r5.setConnectTimeout(r4)     // Catch:{ all -> 0x0082 }
            r5.connect()     // Catch:{ all -> 0x0082 }
            int r4 = r5.getResponseCode()     // Catch:{ all -> 0x0082 }
            long r6 = r1.getContentLength(r5)     // Catch:{ all -> 0x0082 }
            r28 = r5
            r5 = r4
            r4 = r28
            goto L_0x0088
        L_0x0082:
            r0 = move-exception
            r22 = r3
            r10 = r5
            goto L_0x021f
        L_0x0088:
            if (r5 < r8) goto L_0x01fb
            r8 = 300(0x12c, float:4.2E-43)
            if (r5 >= r8) goto L_0x01fb
            java.util.Map r8 = r4.getHeaderFields()     // Catch:{ all -> 0x01f6 }
            java.util.HashMap r11 = new java.util.HashMap     // Catch:{ all -> 0x01f6 }
            r11.<init>()     // Catch:{ all -> 0x01f6 }
            java.util.Set r8 = r8.entrySet()     // Catch:{ all -> 0x01f6 }
            java.util.Iterator r8 = r8.iterator()     // Catch:{ all -> 0x01f6 }
        L_0x009f:
            boolean r12 = r8.hasNext()     // Catch:{ all -> 0x01f6 }
            if (r12 == 0) goto L_0x00c5
            java.lang.Object r12 = r8.next()     // Catch:{ all -> 0x0216 }
            java.util.Map$Entry r12 = (java.util.Map.Entry) r12     // Catch:{ all -> 0x0216 }
            java.lang.Object r13 = r12.getKey()     // Catch:{ all -> 0x0216 }
            java.lang.String r13 = (java.lang.String) r13     // Catch:{ all -> 0x0216 }
            java.lang.Object r12 = r12.getValue()     // Catch:{ all -> 0x0216 }
            java.util.List r12 = (java.util.List) r12     // Catch:{ all -> 0x0216 }
            java.lang.Object r12 = r12.get(r10)     // Catch:{ all -> 0x0216 }
            java.lang.String r12 = (java.lang.String) r12     // Catch:{ all -> 0x0216 }
            if (r13 == 0) goto L_0x009f
            if (r12 == 0) goto L_0x009f
            r11.put(r13, r12)     // Catch:{ all -> 0x0216 }
            goto L_0x009f
        L_0x00c5:
            com.rnfs.DownloadParams r8 = r1.mParam     // Catch:{ all -> 0x01f6 }
            com.rnfs.DownloadParams$OnDownloadBegin r8 = r8.onDownloadBegin     // Catch:{ all -> 0x01f6 }
            if (r8 == 0) goto L_0x00d2
            com.rnfs.DownloadParams r8 = r1.mParam     // Catch:{ all -> 0x0216 }
            com.rnfs.DownloadParams$OnDownloadBegin r8 = r8.onDownloadBegin     // Catch:{ all -> 0x0216 }
            r8.onDownloadBegin(r5, r6, r11)     // Catch:{ all -> 0x0216 }
        L_0x00d2:
            java.io.BufferedInputStream r8 = new java.io.BufferedInputStream     // Catch:{ all -> 0x01f6 }
            java.io.InputStream r11 = r4.getInputStream()     // Catch:{ all -> 0x01f6 }
            r12 = 8192(0x2000, float:1.14794E-41)
            r8.<init>(r11, r12)     // Catch:{ all -> 0x01f6 }
            java.io.FileOutputStream r11 = new java.io.FileOutputStream     // Catch:{ all -> 0x01f1 }
            java.io.File r13 = r0.dest     // Catch:{ all -> 0x01f1 }
            r11.<init>(r13)     // Catch:{ all -> 0x01f1 }
            byte[] r3 = new byte[r12]     // Catch:{ all -> 0x01eb }
            com.rnfs.DownloadParams r12 = r1.mParam     // Catch:{ all -> 0x01eb }
            com.rnfs.DownloadParams$OnDownloadProgress r12 = r12.onDownloadProgress     // Catch:{ all -> 0x01eb }
            if (r12 == 0) goto L_0x00ee
            r12 = 1
            goto L_0x00ef
        L_0x00ee:
            r12 = 0
        L_0x00ef:
            r15 = 0
            r13 = r15
            r19 = 0
        L_0x00f4:
            int r10 = r8.read(r3)     // Catch:{ all -> 0x01eb }
            r9 = -1
            if (r10 == r9) goto L_0x01dd
            java.util.concurrent.atomic.AtomicBoolean r9 = r1.mAbort     // Catch:{ all -> 0x01eb }
            boolean r9 = r9.get()     // Catch:{ all -> 0x01eb }
            if (r9 != 0) goto L_0x01d2
            r22 = r8
            long r8 = (long) r10
            long r13 = r13 + r8
            if (r12 == 0) goto L_0x01bb
            int r8 = r0.progressInterval     // Catch:{ all -> 0x01b8 }
            if (r8 <= 0) goto L_0x0139
            long r23 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x01b8 }
            long r25 = r23 - r15
            int r8 = r0.progressInterval     // Catch:{ all -> 0x01b8 }
            r27 = r10
            long r9 = (long) r8     // Catch:{ all -> 0x01b8 }
            int r8 = (r25 > r9 ? 1 : (r25 == r9 ? 0 : -1))
            if (r8 <= 0) goto L_0x012e
            r8 = 1
            long[][] r9 = new long[r8][]     // Catch:{ all -> 0x01b8 }
            r10 = 2
            long[] r10 = new long[r10]     // Catch:{ all -> 0x01b8 }
            r15 = 0
            r10[r15] = r6     // Catch:{ all -> 0x01b8 }
            r10[r8] = r13     // Catch:{ all -> 0x01b8 }
            r9[r15] = r10     // Catch:{ all -> 0x01b8 }
            r1.publishProgress(r9)     // Catch:{ all -> 0x01b8 }
            r15 = r23
        L_0x012e:
            r10 = r4
            r23 = r5
            r4 = r27
            r0 = 1
            r5 = 0
            r17 = 0
            goto L_0x01c6
        L_0x0139:
            r27 = r10
            float r8 = r0.progressDivider     // Catch:{ all -> 0x01b8 }
            r9 = 0
            int r8 = (r8 > r9 ? 1 : (r8 == r9 ? 0 : -1))
            if (r8 > 0) goto L_0x015a
            r8 = 1
            long[][] r9 = new long[r8][]     // Catch:{ all -> 0x01b8 }
            r10 = 2
            long[] r10 = new long[r10]     // Catch:{ all -> 0x01b8 }
            r21 = 0
            r10[r21] = r6     // Catch:{ all -> 0x01b8 }
            r10[r8] = r13     // Catch:{ all -> 0x01b8 }
            r9[r21] = r10     // Catch:{ all -> 0x01b8 }
            r1.publishProgress(r9)     // Catch:{ all -> 0x01b8 }
            r10 = r4
            r23 = r5
            r0 = 1
            r17 = 0
            goto L_0x01c3
        L_0x015a:
            double r8 = (double) r13
            r23 = 4636737291354636288(0x4059000000000000, double:100.0)
            double r8 = r8 * r23
            r10 = r4
            r23 = r5
            double r4 = (double) r6
            double r8 = r8 / r4
            long r4 = java.lang.Math.round(r8)     // Catch:{ all -> 0x01e9 }
            double r4 = (double) r4     // Catch:{ all -> 0x01e9 }
            float r8 = r0.progressDivider     // Catch:{ all -> 0x01e9 }
            double r8 = (double) r8     // Catch:{ all -> 0x01e9 }
            double r8 = r4 % r8
            r17 = 0
            int r24 = (r8 > r17 ? 1 : (r8 == r17 ? 0 : -1))
            if (r24 != 0) goto L_0x01b6
            int r8 = (r4 > r19 ? 1 : (r4 == r19 ? 0 : -1))
            if (r8 != 0) goto L_0x017c
            int r8 = (r13 > r6 ? 1 : (r13 == r6 ? 0 : -1))
            if (r8 != 0) goto L_0x01b6
        L_0x017c:
            java.lang.String r8 = "Downloader"
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e9 }
            r9.<init>()     // Catch:{ all -> 0x01e9 }
            java.lang.String r0 = "EMIT: "
            r9.append(r0)     // Catch:{ all -> 0x01e9 }
            java.lang.String r0 = java.lang.String.valueOf(r4)     // Catch:{ all -> 0x01e9 }
            r9.append(r0)     // Catch:{ all -> 0x01e9 }
            java.lang.String r0 = ", TOTAL:"
            r9.append(r0)     // Catch:{ all -> 0x01e9 }
            java.lang.String r0 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x01e9 }
            r9.append(r0)     // Catch:{ all -> 0x01e9 }
            java.lang.String r0 = r9.toString()     // Catch:{ all -> 0x01e9 }
            android.util.Log.d(r8, r0)     // Catch:{ all -> 0x01e9 }
            r0 = 1
            long[][] r8 = new long[r0][]     // Catch:{ all -> 0x01e9 }
            r9 = 2
            long[] r9 = new long[r9]     // Catch:{ all -> 0x01e9 }
            r19 = 0
            r9[r19] = r6     // Catch:{ all -> 0x01e9 }
            r9[r0] = r13     // Catch:{ all -> 0x01e9 }
            r8[r19] = r9     // Catch:{ all -> 0x01e9 }
            r1.publishProgress(r8)     // Catch:{ all -> 0x01e9 }
            r19 = r4
            goto L_0x01c3
        L_0x01b6:
            r0 = 1
            goto L_0x01c3
        L_0x01b8:
            r0 = move-exception
            r10 = r4
            goto L_0x01ef
        L_0x01bb:
            r23 = r5
            r27 = r10
            r0 = 1
            r17 = 0
            r10 = r4
        L_0x01c3:
            r4 = r27
            r5 = 0
        L_0x01c6:
            r11.write(r3, r5, r4)     // Catch:{ all -> 0x01e9 }
            r0 = r30
            r4 = r10
            r8 = r22
            r5 = r23
            goto L_0x00f4
        L_0x01d2:
            r10 = r4
            r22 = r8
            java.lang.Exception r0 = new java.lang.Exception     // Catch:{ all -> 0x01e9 }
            java.lang.String r2 = "Download has been aborted"
            r0.<init>(r2)     // Catch:{ all -> 0x01e9 }
            throw r0     // Catch:{ all -> 0x01e9 }
        L_0x01dd:
            r10 = r4
            r23 = r5
            r22 = r8
            r11.flush()     // Catch:{ all -> 0x01e9 }
            r2.bytesWritten = r13     // Catch:{ all -> 0x01e9 }
            r3 = r11
            goto L_0x0200
        L_0x01e9:
            r0 = move-exception
            goto L_0x01ef
        L_0x01eb:
            r0 = move-exception
            r10 = r4
            r22 = r8
        L_0x01ef:
            r3 = r11
            goto L_0x021f
        L_0x01f1:
            r0 = move-exception
            r10 = r4
            r22 = r8
            goto L_0x021f
        L_0x01f6:
            r0 = move-exception
            r10 = r4
            r22 = r3
            goto L_0x021f
        L_0x01fb:
            r10 = r4
            r23 = r5
            r22 = r3
        L_0x0200:
            r4 = r23
            r2.statusCode = r4     // Catch:{ all -> 0x0214 }
            if (r3 == 0) goto L_0x0209
            r3.close()
        L_0x0209:
            if (r22 == 0) goto L_0x020e
            r22.close()
        L_0x020e:
            if (r10 == 0) goto L_0x0213
            r10.disconnect()
        L_0x0213:
            return
        L_0x0214:
            r0 = move-exception
            goto L_0x021f
        L_0x0216:
            r0 = move-exception
            r22 = r3
            r10 = r4
            goto L_0x021f
        L_0x021b:
            r0 = move-exception
            r10 = r3
            r22 = r10
        L_0x021f:
            if (r3 == 0) goto L_0x0224
            r3.close()
        L_0x0224:
            if (r22 == 0) goto L_0x0229
            r22.close()
        L_0x0229:
            if (r10 == 0) goto L_0x022e
            r10.disconnect()
        L_0x022e:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.rnfs.Downloader.download(com.rnfs.DownloadParams, com.rnfs.DownloadResult):void");
    }

    private long getContentLength(HttpURLConnection httpURLConnection) {
        if (Build.VERSION.SDK_INT >= 24) {
            return httpURLConnection.getContentLengthLong();
        }
        return (long) httpURLConnection.getContentLength();
    }

    /* access modifiers changed from: protected */
    public void stop() {
        this.mAbort.set(true);
    }

    /* access modifiers changed from: protected */
    public void onProgressUpdate(long[]... jArr) {
        super.onProgressUpdate(jArr);
        if (this.mParam.onDownloadProgress != null) {
            this.mParam.onDownloadProgress.onDownloadProgress(jArr[0][0], jArr[0][1]);
        }
    }
}
