package expo.modules.updates.p020db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.p007db.SupportSQLiteDatabase;
import expo.modules.updates.p020db.dao.AssetDao;
import expo.modules.updates.p020db.dao.JSONDataDao;
import expo.modules.updates.p020db.dao.UpdateDao;

/* renamed from: expo.modules.updates.db.UpdatesDatabase */
public abstract class UpdatesDatabase extends RoomDatabase {
    private static final String DB_NAME = "updates.db";
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        public void migrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("PRAGMA foreign_keys=OFF");
            supportSQLiteDatabase.beginTransaction();
            try {
                supportSQLiteDatabase.execSQL("CREATE TABLE `new_assets` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `url` TEXT, `key` TEXT, `headers` TEXT, `type` TEXT NOT NULL, `metadata` TEXT, `download_time` INTEGER, `relative_path` TEXT, `hash` BLOB, `hash_type` INTEGER NOT NULL, `marked_for_deletion` INTEGER NOT NULL)");
                supportSQLiteDatabase.execSQL("INSERT INTO `new_assets` (`id`, `url`, `key`, `headers`, `type`, `metadata`, `download_time`, `relative_path`, `hash`, `hash_type`, `marked_for_deletion`) SELECT `id`, `url`, `key`, `headers`, `type`, `metadata`, `download_time`, `relative_path`, `hash`, `hash_type`, `marked_for_deletion` FROM `assets`");
                supportSQLiteDatabase.execSQL("DROP TABLE `assets`");
                supportSQLiteDatabase.execSQL("ALTER TABLE `new_assets` RENAME TO `assets`");
                supportSQLiteDatabase.execSQL("CREATE UNIQUE INDEX `index_assets_key` ON `assets` (`key`)");
                supportSQLiteDatabase.setTransactionSuccessful();
                supportSQLiteDatabase.endTransaction();
                supportSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
            } catch (Throwable th) {
                supportSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
                throw th;
            }
        }
    };
    private static final String TAG = UpdatesDatabase.class.getSimpleName();
    private static UpdatesDatabase sInstance;

    public abstract AssetDao assetDao();

    public abstract JSONDataDao jsonDataDao();

    public abstract UpdateDao updateDao();

    public static synchronized UpdatesDatabase getInstance(Context context) {
        UpdatesDatabase updatesDatabase;
        Class<UpdatesDatabase> cls = UpdatesDatabase.class;
        synchronized (cls) {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(context, cls, DB_NAME).addMigrations(MIGRATION_4_5).fallbackToDestructiveMigration().allowMainThreadQueries().build();
            }
            updatesDatabase = sInstance;
        }
        return updatesDatabase;
    }
}
