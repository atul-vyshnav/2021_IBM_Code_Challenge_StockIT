package com.onesignal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalDbContract;
import com.onesignal.outcomes.OSOutcomeTableProvider;
import java.util.ArrayList;

class OneSignalDbHelper extends SQLiteOpenHelper implements OneSignalDb {
    private static final String COMMA_SEP = ",";
    private static final String DATABASE_NAME = "OneSignal.db";
    static final int DATABASE_VERSION = 8;
    private static final int DB_OPEN_RETRY_BACKOFF = 400;
    private static final int DB_OPEN_RETRY_MAX = 5;
    private static final String FLOAT_TYPE = " FLOAT";
    private static final String INTEGER_PRIMARY_KEY_TYPE = " INTEGER PRIMARY KEY";
    private static final String INT_TYPE = " INTEGER";
    private static final Object LOCK = new Object();
    protected static final String SQL_CREATE_ENTRIES = "CREATE TABLE notification (_id INTEGER PRIMARY KEY,notification_id TEXT,android_notification_id INTEGER,group_id TEXT,collapse_id TEXT,is_summary INTEGER DEFAULT 0,opened INTEGER DEFAULT 0,dismissed INTEGER DEFAULT 0,title TEXT,message TEXT,full_data TEXT,created_time TIMESTAMP DEFAULT (strftime('%s', 'now')),expire_time TIMESTAMP);";
    private static final String SQL_CREATE_IN_APP_MESSAGE_ENTRIES = "CREATE TABLE in_app_message (_id INTEGER PRIMARY KEY,display_quantity INTEGER,last_display INTEGER,message_id TEXT,displayed_in_session INTEGER,click_ids TEXT);";
    protected static final String[] SQL_INDEX_ENTRIES = {OneSignalDbContract.NotificationTable.INDEX_CREATE_NOTIFICATION_ID, OneSignalDbContract.NotificationTable.INDEX_CREATE_ANDROID_NOTIFICATION_ID, OneSignalDbContract.NotificationTable.INDEX_CREATE_GROUP_ID, OneSignalDbContract.NotificationTable.INDEX_CREATE_COLLAPSE_ID, OneSignalDbContract.NotificationTable.INDEX_CREATE_CREATED_TIME, OneSignalDbContract.NotificationTable.INDEX_CREATE_EXPIRE_TIME};
    private static final String TEXT_TYPE = " TEXT";
    private static final String TIMESTAMP_TYPE = " TIMESTAMP";
    private static OSLogger logger = new OSLogWrapper();
    private static OSOutcomeTableProvider outcomeTableProvider = new OSOutcomeTableProvider();
    private static OneSignalDbHelper sInstance;

    private static int getDbVersion() {
        return 8;
    }

    /* access modifiers changed from: package-private */
    public void setOutcomeTableProvider(OSOutcomeTableProvider oSOutcomeTableProvider) {
        outcomeTableProvider = oSOutcomeTableProvider;
    }

    OneSignalDbHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, getDbVersion());
    }

    public static OneSignalDbHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new OneSignalDbHelper(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private SQLiteDatabase getSQLiteDatabase() {
        SQLiteDatabase writableDatabase;
        synchronized (LOCK) {
            try {
                writableDatabase = getWritableDatabase();
            } catch (SQLiteCantOpenDatabaseException e) {
                e = e;
                throw e;
            } catch (SQLiteDatabaseLockedException e2) {
                e = e2;
                throw e;
            } catch (Throwable th) {
                throw th;
            }
        }
        return writableDatabase;
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0014 A[Catch:{ SQLiteCantOpenDatabaseException -> 0x000e, SQLiteDatabaseLockedException -> 0x000c, all -> 0x000a }, LOOP:0: B:3:0x0004->B:15:0x0014, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x001b A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.database.sqlite.SQLiteDatabase getSQLiteDatabaseWithRetries() {
        /*
            r4 = this;
            java.lang.Object r0 = LOCK
            monitor-enter(r0)
            r1 = 0
        L_0x0004:
            android.database.sqlite.SQLiteDatabase r1 = r4.getSQLiteDatabase()     // Catch:{ SQLiteCantOpenDatabaseException -> 0x000e, SQLiteDatabaseLockedException -> 0x000c }
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            return r1
        L_0x000a:
            r1 = move-exception
            goto L_0x001c
        L_0x000c:
            r2 = move-exception
            goto L_0x000f
        L_0x000e:
            r2 = move-exception
        L_0x000f:
            int r1 = r1 + 1
            r3 = 5
            if (r1 >= r3) goto L_0x001b
            int r2 = r1 * 400
            long r2 = (long) r2     // Catch:{ all -> 0x000a }
            android.os.SystemClock.sleep(r2)     // Catch:{ all -> 0x000a }
            goto L_0x0004
        L_0x001b:
            throw r2     // Catch:{ all -> 0x000a }
        L_0x001c:
            monitor-exit(r0)     // Catch:{ all -> 0x000a }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignalDbHelper.getSQLiteDatabaseWithRetries():android.database.sqlite.SQLiteDatabase");
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5) {
        Cursor query;
        synchronized (LOCK) {
            query = getSQLiteDatabaseWithRetries().query(str, strArr, str2, strArr2, str3, str4, str5);
        }
        return query;
    }

    public Cursor query(String str, String[] strArr, String str2, String[] strArr2, String str3, String str4, String str5, String str6) {
        Cursor query;
        synchronized (LOCK) {
            query = getSQLiteDatabaseWithRetries().query(str, strArr, str2, strArr2, str3, str4, str5, str6);
        }
        return query;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a2, code lost:
        if (r1 != null) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r1.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a8, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b1, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b2, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b9, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r7 = e;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:7:0x0012, B:18:0x002f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void insert(java.lang.String r7, java.lang.String r8, android.content.ContentValues r9) {
        /*
            r6 = this;
            java.lang.Object r0 = LOCK
            monitor-enter(r0)
            android.database.sqlite.SQLiteDatabase r1 = r6.getSQLiteDatabaseWithRetries()     // Catch:{ all -> 0x00ba }
            r1.beginTransaction()     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            r1.insert(r7, r8, r9)     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            r1.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x0021, SQLiteException -> 0x0017 }
            goto L_0x00a0
        L_0x0017:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
        L_0x001c:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ba }
            goto L_0x00a0
        L_0x0021:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
        L_0x0026:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ba }
            goto L_0x00a0
        L_0x002b:
            r7 = move-exception
            goto L_0x00a2
        L_0x002e:
            r2 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x002b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            r4.<init>()     // Catch:{ all -> 0x002b }
            java.lang.String r5 = "Error under inserting transaction under table: "
            r4.append(r5)     // Catch:{ all -> 0x002b }
            r4.append(r7)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " with nullColumnHack: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r8)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " and values: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r9)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x002b }
            r3.error(r7, r2)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x0061, SQLiteException -> 0x005b }
            goto L_0x00a0
        L_0x005b:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x001c
        L_0x0061:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0026
        L_0x0067:
            r2 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x002b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            r4.<init>()     // Catch:{ all -> 0x002b }
            java.lang.String r5 = "Error inserting on table: "
            r4.append(r5)     // Catch:{ all -> 0x002b }
            r4.append(r7)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " with nullColumnHack: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r8)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " and values: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r9)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x002b }
            r3.error(r7, r2)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x009a, SQLiteException -> 0x0094 }
            goto L_0x00a0
        L_0x0094:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x001c
        L_0x009a:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0026
        L_0x00a0:
            monitor-exit(r0)     // Catch:{ all -> 0x00ba }
            return
        L_0x00a2:
            if (r1 == 0) goto L_0x00b9
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x00b1, SQLiteException -> 0x00a8 }
            goto L_0x00b9
        L_0x00a8:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r1 = "Error closing transaction! "
            r9.error(r1, r8)     // Catch:{ all -> 0x00ba }
            goto L_0x00b9
        L_0x00b1:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r1 = "Error closing transaction! "
            r9.error(r1, r8)     // Catch:{ all -> 0x00ba }
        L_0x00b9:
            throw r7     // Catch:{ all -> 0x00ba }
        L_0x00ba:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ba }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignalDbHelper.insert(java.lang.String, java.lang.String, android.content.ContentValues):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a2, code lost:
        if (r1 != null) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r1.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a8, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b1, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b2, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b9, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r7 = e;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:7:0x0012, B:18:0x002f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void insertOrThrow(java.lang.String r7, java.lang.String r8, android.content.ContentValues r9) throws android.database.SQLException {
        /*
            r6 = this;
            java.lang.Object r0 = LOCK
            monitor-enter(r0)
            android.database.sqlite.SQLiteDatabase r1 = r6.getSQLiteDatabaseWithRetries()     // Catch:{ all -> 0x00ba }
            r1.beginTransaction()     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            r1.insertOrThrow(r7, r8, r9)     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            r1.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x0021, SQLiteException -> 0x0017 }
            goto L_0x00a0
        L_0x0017:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
        L_0x001c:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ba }
            goto L_0x00a0
        L_0x0021:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
        L_0x0026:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ba }
            goto L_0x00a0
        L_0x002b:
            r7 = move-exception
            goto L_0x00a2
        L_0x002e:
            r2 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x002b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            r4.<init>()     // Catch:{ all -> 0x002b }
            java.lang.String r5 = "Error under inserting or throw transaction under table: "
            r4.append(r5)     // Catch:{ all -> 0x002b }
            r4.append(r7)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " with nullColumnHack: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r8)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " and values: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r9)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x002b }
            r3.error(r7, r2)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x0061, SQLiteException -> 0x005b }
            goto L_0x00a0
        L_0x005b:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x001c
        L_0x0061:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0026
        L_0x0067:
            r2 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x002b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            r4.<init>()     // Catch:{ all -> 0x002b }
            java.lang.String r5 = "Error inserting or throw on table: "
            r4.append(r5)     // Catch:{ all -> 0x002b }
            r4.append(r7)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " with nullColumnHack: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r8)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " and values: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r9)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x002b }
            r3.error(r7, r2)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x009a, SQLiteException -> 0x0094 }
            goto L_0x00a0
        L_0x0094:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x001c
        L_0x009a:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0026
        L_0x00a0:
            monitor-exit(r0)     // Catch:{ all -> 0x00ba }
            return
        L_0x00a2:
            if (r1 == 0) goto L_0x00b9
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x00b1, SQLiteException -> 0x00a8 }
            goto L_0x00b9
        L_0x00a8:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r1 = "Error closing transaction! "
            r9.error(r1, r8)     // Catch:{ all -> 0x00ba }
            goto L_0x00b9
        L_0x00b1:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r1 = "Error closing transaction! "
            r9.error(r1, r8)     // Catch:{ all -> 0x00ba }
        L_0x00b9:
            throw r7     // Catch:{ all -> 0x00ba }
        L_0x00ba:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ba }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignalDbHelper.insertOrThrow(java.lang.String, java.lang.String, android.content.ContentValues):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0027, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:?, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0031, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0032, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003b, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00b2, code lost:
        if (r2 != null) goto L_0x00b4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:?, code lost:
        r2.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b8, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:?, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00c1, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00c2, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x00c9, code lost:
        throw r7;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:11:0x0022, B:22:0x003f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int update(java.lang.String r7, android.content.ContentValues r8, java.lang.String r9, java.lang.String[] r10) {
        /*
            r6 = this;
            r0 = 0
            if (r8 == 0) goto L_0x00cd
            java.lang.String r1 = r8.toString()
            boolean r1 = r1.isEmpty()
            if (r1 == 0) goto L_0x000f
            goto L_0x00cd
        L_0x000f:
            java.lang.Object r1 = LOCK
            monitor-enter(r1)
            android.database.sqlite.SQLiteDatabase r2 = r6.getSQLiteDatabaseWithRetries()     // Catch:{ all -> 0x00ca }
            r2.beginTransaction()     // Catch:{ SQLiteException -> 0x0077, IllegalStateException -> 0x003e }
            int r0 = r2.update(r7, r8, r9, r10)     // Catch:{ SQLiteException -> 0x0077, IllegalStateException -> 0x003e }
            r2.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x0077, IllegalStateException -> 0x003e }
            if (r2 == 0) goto L_0x00b0
            r2.endTransaction()     // Catch:{ IllegalStateException -> 0x0031, SQLiteException -> 0x0027 }
            goto L_0x00b0
        L_0x0027:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r9 = "Error closing transaction! "
        L_0x002c:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ca }
            goto L_0x00b0
        L_0x0031:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r9 = "Error closing transaction! "
        L_0x0036:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ca }
            goto L_0x00b0
        L_0x003b:
            r7 = move-exception
            goto L_0x00b2
        L_0x003e:
            r8 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x003b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x003b }
            r4.<init>()     // Catch:{ all -> 0x003b }
            java.lang.String r5 = "Error under update transaction under table: "
            r4.append(r5)     // Catch:{ all -> 0x003b }
            r4.append(r7)     // Catch:{ all -> 0x003b }
            java.lang.String r7 = " with whereClause: "
            r4.append(r7)     // Catch:{ all -> 0x003b }
            r4.append(r9)     // Catch:{ all -> 0x003b }
            java.lang.String r7 = " and whereArgs: "
            r4.append(r7)     // Catch:{ all -> 0x003b }
            r4.append(r10)     // Catch:{ all -> 0x003b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x003b }
            r3.error(r7, r8)     // Catch:{ all -> 0x003b }
            if (r2 == 0) goto L_0x00b0
            r2.endTransaction()     // Catch:{ IllegalStateException -> 0x0071, SQLiteException -> 0x006b }
            goto L_0x00b0
        L_0x006b:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x002c
        L_0x0071:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0036
        L_0x0077:
            r8 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x003b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x003b }
            r4.<init>()     // Catch:{ all -> 0x003b }
            java.lang.String r5 = "Error updating on table: "
            r4.append(r5)     // Catch:{ all -> 0x003b }
            r4.append(r7)     // Catch:{ all -> 0x003b }
            java.lang.String r7 = " with whereClause: "
            r4.append(r7)     // Catch:{ all -> 0x003b }
            r4.append(r9)     // Catch:{ all -> 0x003b }
            java.lang.String r7 = " and whereArgs: "
            r4.append(r7)     // Catch:{ all -> 0x003b }
            r4.append(r10)     // Catch:{ all -> 0x003b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x003b }
            r3.error(r7, r8)     // Catch:{ all -> 0x003b }
            if (r2 == 0) goto L_0x00b0
            r2.endTransaction()     // Catch:{ IllegalStateException -> 0x00aa, SQLiteException -> 0x00a4 }
            goto L_0x00b0
        L_0x00a4:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x002c
        L_0x00aa:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0036
        L_0x00b0:
            monitor-exit(r1)     // Catch:{ all -> 0x00ca }
            return r0
        L_0x00b2:
            if (r2 == 0) goto L_0x00c9
            r2.endTransaction()     // Catch:{ IllegalStateException -> 0x00c1, SQLiteException -> 0x00b8 }
            goto L_0x00c9
        L_0x00b8:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r10 = "Error closing transaction! "
            r9.error(r10, r8)     // Catch:{ all -> 0x00ca }
            goto L_0x00c9
        L_0x00c1:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ca }
            java.lang.String r10 = "Error closing transaction! "
            r9.error(r10, r8)     // Catch:{ all -> 0x00ca }
        L_0x00c9:
            throw r7     // Catch:{ all -> 0x00ca }
        L_0x00ca:
            r7 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x00ca }
            throw r7
        L_0x00cd:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignalDbHelper.update(java.lang.String, android.content.ContentValues, java.lang.String, java.lang.String[]):int");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:?, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0021, code lost:
        r7 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0022, code lost:
        r8 = logger;
        r9 = "Error closing transaction! ";
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x002b, code lost:
        r7 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a2, code lost:
        if (r1 != null) goto L_0x00a4;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
        r1.endTransaction();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a8, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00b1, code lost:
        r8 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:48:0x00b2, code lost:
        logger.error("Error closing transaction! ", r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00b9, code lost:
        throw r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0017, code lost:
        r7 = e;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [B:7:0x0012, B:18:0x002f] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void delete(java.lang.String r7, java.lang.String r8, java.lang.String[] r9) {
        /*
            r6 = this;
            java.lang.Object r0 = LOCK
            monitor-enter(r0)
            android.database.sqlite.SQLiteDatabase r1 = r6.getSQLiteDatabaseWithRetries()     // Catch:{ all -> 0x00ba }
            r1.beginTransaction()     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            r1.delete(r7, r8, r9)     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            r1.setTransactionSuccessful()     // Catch:{ SQLiteException -> 0x0067, IllegalStateException -> 0x002e }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x0021, SQLiteException -> 0x0017 }
            goto L_0x00a0
        L_0x0017:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
        L_0x001c:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ba }
            goto L_0x00a0
        L_0x0021:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
        L_0x0026:
            r8.error(r9, r7)     // Catch:{ all -> 0x00ba }
            goto L_0x00a0
        L_0x002b:
            r7 = move-exception
            goto L_0x00a2
        L_0x002e:
            r2 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x002b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            r4.<init>()     // Catch:{ all -> 0x002b }
            java.lang.String r5 = "Error under delete transaction under table: "
            r4.append(r5)     // Catch:{ all -> 0x002b }
            r4.append(r7)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " with whereClause: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r8)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " and whereArgs: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r9)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x002b }
            r3.error(r7, r2)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x0061, SQLiteException -> 0x005b }
            goto L_0x00a0
        L_0x005b:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x001c
        L_0x0061:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0026
        L_0x0067:
            r2 = move-exception
            com.onesignal.OSLogger r3 = logger     // Catch:{ all -> 0x002b }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            r4.<init>()     // Catch:{ all -> 0x002b }
            java.lang.String r5 = "Error deleting on table: "
            r4.append(r5)     // Catch:{ all -> 0x002b }
            r4.append(r7)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " with whereClause: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r8)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = " and whereArgs: "
            r4.append(r7)     // Catch:{ all -> 0x002b }
            r4.append(r9)     // Catch:{ all -> 0x002b }
            java.lang.String r7 = r4.toString()     // Catch:{ all -> 0x002b }
            r3.error(r7, r2)     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x00a0
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x009a, SQLiteException -> 0x0094 }
            goto L_0x00a0
        L_0x0094:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x001c
        L_0x009a:
            r7 = move-exception
            com.onesignal.OSLogger r8 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r9 = "Error closing transaction! "
            goto L_0x0026
        L_0x00a0:
            monitor-exit(r0)     // Catch:{ all -> 0x00ba }
            return
        L_0x00a2:
            if (r1 == 0) goto L_0x00b9
            r1.endTransaction()     // Catch:{ IllegalStateException -> 0x00b1, SQLiteException -> 0x00a8 }
            goto L_0x00b9
        L_0x00a8:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r1 = "Error closing transaction! "
            r9.error(r1, r8)     // Catch:{ all -> 0x00ba }
            goto L_0x00b9
        L_0x00b1:
            r8 = move-exception
            com.onesignal.OSLogger r9 = logger     // Catch:{ all -> 0x00ba }
            java.lang.String r1 = "Error closing transaction! "
            r9.error(r1, r8)     // Catch:{ all -> 0x00ba }
        L_0x00b9:
            throw r7     // Catch:{ all -> 0x00ba }
        L_0x00ba:
            r7 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00ba }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.OneSignalDbHelper.delete(java.lang.String, java.lang.String, java.lang.String[]):void");
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sQLiteDatabase.execSQL(OSOutcomeTableProvider.SQL_CREATE_OUTCOME_ENTRIES_V3);
        sQLiteDatabase.execSQL(OSOutcomeTableProvider.SQL_CREATE_UNIQUE_OUTCOME_ENTRIES_V2);
        sQLiteDatabase.execSQL(SQL_CREATE_IN_APP_MESSAGE_ENTRIES);
        for (String execSQL : SQL_INDEX_ENTRIES) {
            sQLiteDatabase.execSQL(execSQL);
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
        OneSignal.Log(log_level, "OneSignal Database onUpgrade from: " + i + " to: " + i2);
        try {
            internalOnUpgrade(sQLiteDatabase, i);
        } catch (SQLiteException e) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Error in upgrade, migration may have already run! Skipping!", e);
        }
    }

    private synchronized void internalOnUpgrade(SQLiteDatabase sQLiteDatabase, int i) {
        if (i < 2) {
            try {
                upgradeToV2(sQLiteDatabase);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (i < 3) {
            upgradeToV3(sQLiteDatabase);
        }
        if (i < 4) {
            upgradeToV4(sQLiteDatabase);
        }
        if (i < 5) {
            upgradeToV5(sQLiteDatabase);
        }
        if (i == 5) {
            upgradeFromV5ToV6(sQLiteDatabase);
        }
        if (i < 7) {
            upgradeToV7(sQLiteDatabase);
        }
        if (i < 8) {
            upgradeToV8(sQLiteDatabase);
        }
    }

    private static void upgradeToV2(SQLiteDatabase sQLiteDatabase) {
        safeExecSQL(sQLiteDatabase, "ALTER TABLE notification ADD COLUMN collapse_id TEXT;");
        safeExecSQL(sQLiteDatabase, OneSignalDbContract.NotificationTable.INDEX_CREATE_GROUP_ID);
    }

    private static void upgradeToV3(SQLiteDatabase sQLiteDatabase) {
        safeExecSQL(sQLiteDatabase, "ALTER TABLE notification ADD COLUMN expire_time TIMESTAMP;");
        safeExecSQL(sQLiteDatabase, "UPDATE notification SET expire_time = created_time + 259200;");
        safeExecSQL(sQLiteDatabase, OneSignalDbContract.NotificationTable.INDEX_CREATE_EXPIRE_TIME);
    }

    private static void upgradeToV4(SQLiteDatabase sQLiteDatabase) {
        safeExecSQL(sQLiteDatabase, OSOutcomeTableProvider.SQL_CREATE_OUTCOME_ENTRIES_V1);
    }

    private static void upgradeToV5(SQLiteDatabase sQLiteDatabase) {
        safeExecSQL(sQLiteDatabase, OSOutcomeTableProvider.SQL_CREATE_UNIQUE_OUTCOME_ENTRIES_V1);
        upgradeFromV5ToV6(sQLiteDatabase);
    }

    private static void upgradeFromV5ToV6(SQLiteDatabase sQLiteDatabase) {
        outcomeTableProvider.upgradeOutcomeTableRevision1To2(sQLiteDatabase);
    }

    private static void upgradeToV7(SQLiteDatabase sQLiteDatabase) {
        safeExecSQL(sQLiteDatabase, SQL_CREATE_IN_APP_MESSAGE_ENTRIES);
    }

    private synchronized void upgradeToV8(SQLiteDatabase sQLiteDatabase) {
        outcomeTableProvider.upgradeOutcomeTableRevision2To3(sQLiteDatabase);
        outcomeTableProvider.upgradeCacheOutcomeTableRevision1To2(sQLiteDatabase);
    }

    private static void safeExecSQL(SQLiteDatabase sQLiteDatabase, String str) {
        try {
            sQLiteDatabase.execSQL(str);
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: finally extract failed */
    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        OneSignal.Log(OneSignal.LOG_LEVEL.WARN, "SDK version rolled back! Clearing OneSignal.db as it could be in an unexpected state.");
        Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", (String[]) null);
        try {
            ArrayList<String> arrayList = new ArrayList<>(rawQuery.getCount());
            while (rawQuery.moveToNext()) {
                arrayList.add(rawQuery.getString(0));
            }
            for (String str : arrayList) {
                if (!str.startsWith("sqlite_")) {
                    sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + str);
                }
            }
            rawQuery.close();
            onCreate(sQLiteDatabase);
        } catch (Throwable th) {
            rawQuery.close();
            throw th;
        }
    }

    static StringBuilder recentUninteractedWithNotificationsWhere() {
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        StringBuilder sb = new StringBuilder("created_time > " + (currentTimeMillis - 604800) + " AND " + "dismissed" + " = 0 AND " + OneSignalDbContract.NotificationTable.COLUMN_NAME_OPENED + " = 0 AND " + OneSignalDbContract.NotificationTable.COLUMN_NAME_IS_SUMMARY + " = 0");
        if (OneSignalPrefs.getBool(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_RESTORE_TTL_FILTER, true)) {
            sb.append(" AND expire_time > " + currentTimeMillis);
        }
        return sb;
    }

    static void cleanOutcomeDatabaseTable(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.delete(OSOutcomeTableProvider.OUTCOME_EVENT_TABLE, (String) null, (String[]) null);
    }
}
