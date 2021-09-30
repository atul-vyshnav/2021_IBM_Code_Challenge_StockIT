package com.reactnativecommunity.asyncstorage;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Iterator;

public class AsyncStorageExpoMigration {
    static final String LOG_TAG = "AsyncStorageExpoMigration";

    public static void migrate(Context context) {
        if (!isAsyncStorageDatabaseCreated(context)) {
            ArrayList<File> expoDatabases = getExpoDatabases(context);
            File lastModifiedFile = getLastModifiedFile(expoDatabases);
            if (lastModifiedFile == null) {
                Log.v(LOG_TAG, "No scoped database found");
                return;
            }
            try {
                ReactDatabaseSupplier.getInstance(context).get();
                copyFile(new FileInputStream(lastModifiedFile), new FileOutputStream(context.getDatabasePath("RKStorage")));
                Log.v(LOG_TAG, "Migrated most recently modified database " + lastModifiedFile.getName() + " to RKStorage");
                try {
                    Iterator<File> it = expoDatabases.iterator();
                    while (it.hasNext()) {
                        File next = it.next();
                        if (next.delete()) {
                            Log.v(LOG_TAG, "Deleted scoped database " + next.getName());
                        } else {
                            Log.v(LOG_TAG, "Failed to delete scoped database " + next.getName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.v(LOG_TAG, "Completed the scoped AsyncStorage migration");
            } catch (Exception e2) {
                Log.v(LOG_TAG, "Failed to migrate scoped database " + lastModifiedFile.getName());
                e2.printStackTrace();
            }
        }
    }

    private static boolean isAsyncStorageDatabaseCreated(Context context) {
        return context.getDatabasePath("RKStorage").exists();
    }

    private static ArrayList<File> getExpoDatabases(Context context) {
        ArrayList<File> arrayList = new ArrayList<>();
        try {
            File[] listFiles = context.getDatabasePath("noop").getParentFile().listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    if (file.getName().startsWith("RKStorage-scoped-experience-") && !file.getName().endsWith("-journal")) {
                        arrayList.add(file);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private static File getLastModifiedFile(ArrayList<File> arrayList) {
        File file = null;
        if (arrayList.size() == 0) {
            return null;
        }
        long j = -1;
        Iterator<File> it = arrayList.iterator();
        while (it.hasNext()) {
            File next = it.next();
            long lastModifiedTimeInMillis = getLastModifiedTimeInMillis(next);
            if (lastModifiedTimeInMillis > j) {
                file = next;
                j = lastModifiedTimeInMillis;
            }
        }
        if (file != null) {
            return file;
        }
        return arrayList.get(0);
    }

    private static long getLastModifiedTimeInMillis(File file) {
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                return getLastModifiedTimeFromBasicFileAttrs(file);
            }
            return file.lastModified();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static long getLastModifiedTimeFromBasicFileAttrs(File file) {
        try {
            return Files.readAttributes(file.toPath(), BasicFileAttributes.class, new LinkOption[0]).creationTime().toMillis();
        } catch (Exception unused) {
            return -1;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0030 A[SYNTHETIC, Splitter:B:19:0x0030] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x003d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void copyFile(java.io.FileInputStream r8, java.io.FileOutputStream r9) throws java.io.IOException {
        /*
            r0 = 0
            java.nio.channels.FileChannel r8 = r8.getChannel()     // Catch:{ all -> 0x002c }
            java.nio.channels.FileChannel r0 = r9.getChannel()     // Catch:{ all -> 0x0027 }
            r2 = 0
            long r4 = r8.size()     // Catch:{ all -> 0x0027 }
            r1 = r8
            r6 = r0
            r1.transferTo(r2, r4, r6)     // Catch:{ all -> 0x0027 }
            if (r8 == 0) goto L_0x0021
            r8.close()     // Catch:{ all -> 0x001a }
            goto L_0x0021
        L_0x001a:
            r8 = move-exception
            if (r0 == 0) goto L_0x0020
            r0.close()
        L_0x0020:
            throw r8
        L_0x0021:
            if (r0 == 0) goto L_0x0026
            r0.close()
        L_0x0026:
            return
        L_0x0027:
            r9 = move-exception
            r7 = r0
            r0 = r8
            r8 = r7
            goto L_0x002e
        L_0x002c:
            r9 = move-exception
            r8 = r0
        L_0x002e:
            if (r0 == 0) goto L_0x003b
            r0.close()     // Catch:{ all -> 0x0034 }
            goto L_0x003b
        L_0x0034:
            r9 = move-exception
            if (r8 == 0) goto L_0x003a
            r8.close()
        L_0x003a:
            throw r9
        L_0x003b:
            if (r8 == 0) goto L_0x0040
            r8.close()
        L_0x0040:
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.reactnativecommunity.asyncstorage.AsyncStorageExpoMigration.copyFile(java.io.FileInputStream, java.io.FileOutputStream):void");
    }
}
