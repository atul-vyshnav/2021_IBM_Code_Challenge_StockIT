package com.facebook.react.modules.blob;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public final class BlobProvider extends ContentProvider {
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public String getType(Uri uri) {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public boolean onCreate() {
        return true;
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:22:0x004f, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0054, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        r4.addSuppressed(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0058, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.ParcelFileDescriptor openFile(android.net.Uri r4, java.lang.String r5) throws java.io.FileNotFoundException {
        /*
            r3 = this;
            java.lang.String r0 = "r"
            boolean r0 = r5.equals(r0)
            java.lang.String r1 = "Cannot open "
            if (r0 == 0) goto L_0x0080
            android.content.Context r5 = r3.getContext()
            android.content.Context r5 = r5.getApplicationContext()
            boolean r0 = r5 instanceof com.facebook.react.ReactApplication
            r2 = 0
            if (r0 == 0) goto L_0x002e
            com.facebook.react.ReactApplication r5 = (com.facebook.react.ReactApplication) r5
            com.facebook.react.ReactNativeHost r5 = r5.getReactNativeHost()
            com.facebook.react.ReactInstanceManager r5 = r5.getReactInstanceManager()
            com.facebook.react.bridge.ReactContext r5 = r5.getCurrentReactContext()
            java.lang.Class<com.facebook.react.modules.blob.BlobModule> r0 = com.facebook.react.modules.blob.BlobModule.class
            com.facebook.react.bridge.NativeModule r5 = r5.getNativeModule(r0)
            com.facebook.react.modules.blob.BlobModule r5 = (com.facebook.react.modules.blob.BlobModule) r5
            goto L_0x002f
        L_0x002e:
            r5 = r2
        L_0x002f:
            if (r5 == 0) goto L_0x0078
            byte[] r5 = r5.resolve((android.net.Uri) r4)
            if (r5 == 0) goto L_0x005a
            android.os.ParcelFileDescriptor[] r4 = android.os.ParcelFileDescriptor.createPipe()     // Catch:{ IOException -> 0x0059 }
            r0 = 0
            r0 = r4[r0]
            r1 = 1
            r4 = r4[r1]
            android.os.ParcelFileDescriptor$AutoCloseOutputStream r1 = new android.os.ParcelFileDescriptor$AutoCloseOutputStream     // Catch:{  }
            r1.<init>(r4)     // Catch:{  }
            r1.write(r5)     // Catch:{ all -> 0x004d }
            r1.close()     // Catch:{  }
            return r0
        L_0x004d:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x004f }
        L_0x004f:
            r5 = move-exception
            r1.close()     // Catch:{ all -> 0x0054 }
            goto L_0x0058
        L_0x0054:
            r0 = move-exception
            r4.addSuppressed(r0)     // Catch:{  }
        L_0x0058:
            throw r5     // Catch:{  }
        L_0x0059:
            return r2
        L_0x005a:
            java.io.FileNotFoundException r5 = new java.io.FileNotFoundException
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r0.append(r1)
            java.lang.String r4 = r4.toString()
            r0.append(r4)
            java.lang.String r4 = ", blob not found."
            r0.append(r4)
            java.lang.String r4 = r0.toString()
            r5.<init>(r4)
            throw r5
        L_0x0078:
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.String r5 = "No blob module associated with BlobProvider"
            r4.<init>(r5)
            throw r4
        L_0x0080:
            java.io.FileNotFoundException r0 = new java.io.FileNotFoundException
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            java.lang.String r4 = r4.toString()
            r2.append(r4)
            java.lang.String r4 = " in mode '"
            r2.append(r4)
            r2.append(r5)
            java.lang.String r4 = "'"
            r2.append(r4)
            java.lang.String r4 = r2.toString()
            r0.<init>(r4)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.modules.blob.BlobProvider.openFile(android.net.Uri, java.lang.String):android.os.ParcelFileDescriptor");
    }
}
