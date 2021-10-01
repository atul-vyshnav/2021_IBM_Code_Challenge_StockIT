package expo.modules.updates.p020db;

import android.util.Log;

/* renamed from: expo.modules.updates.db.DatabaseHolder */
public class DatabaseHolder {
    private static final String TAG = DatabaseHolder.class.getSimpleName();
    private boolean isInUse = false;
    private UpdatesDatabase mDatabase;

    public DatabaseHolder(UpdatesDatabase updatesDatabase) {
        this.mDatabase = updatesDatabase;
    }

    public synchronized UpdatesDatabase getDatabase() {
        while (this.isInUse) {
            try {
                wait();
            } catch (InterruptedException e) {
                Log.e(TAG, "Interrupted while waiting for database", e);
            }
        }
        this.isInUse = true;
        return this.mDatabase;
    }

    public synchronized void releaseDatabase() {
        this.isInUse = false;
        notify();
    }
}
