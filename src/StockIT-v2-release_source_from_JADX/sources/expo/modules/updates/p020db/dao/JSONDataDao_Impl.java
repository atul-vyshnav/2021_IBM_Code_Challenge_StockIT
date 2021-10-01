package expo.modules.updates.p020db.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.p007db.SupportSQLiteStatement;
import expo.modules.updates.p020db.Converters;
import expo.modules.updates.p020db.entity.JSONDataEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: expo.modules.updates.db.dao.JSONDataDao_Impl */
public final class JSONDataDao_Impl extends JSONDataDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfJSONDataEntity;
    private final SharedSQLiteStatement __preparedStmtOf_deleteJSONDataForKey;

    public JSONDataDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfJSONDataEntity = new EntityInsertionAdapter<JSONDataEntity>(roomDatabase) {
            public String createQuery() {
                return "INSERT OR ABORT INTO `json_data`(`id`,`key`,`value`,`last_updated`,`scope_key`) VALUES (nullif(?, 0),?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, JSONDataEntity jSONDataEntity) {
                supportSQLiteStatement.bindLong(1, jSONDataEntity.f1252id);
                if (jSONDataEntity.key == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, jSONDataEntity.key);
                }
                if (jSONDataEntity.value == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, jSONDataEntity.value);
                }
                Long dateToLong = Converters.dateToLong(jSONDataEntity.lastUpdated);
                if (dateToLong == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindLong(4, dateToLong.longValue());
                }
                if (jSONDataEntity.scopeKey == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, jSONDataEntity.scopeKey);
                }
            }
        };
        this.__preparedStmtOf_deleteJSONDataForKey = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "DELETE FROM json_data WHERE `key` = ? AND scope_key = ?;";
            }
        };
    }

    public void _insertJSONData(JSONDataEntity jSONDataEntity) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfJSONDataEntity.insert(jSONDataEntity);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void setJSONStringForKey(String str, String str2, String str3) {
        this.__db.beginTransaction();
        try {
            super.setJSONStringForKey(str, str2, str3);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void setMultipleFields(Map<String, String> map, String str) {
        this.__db.beginTransaction();
        try {
            super.setMultipleFields(map, str);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void _deleteJSONDataForKey(String str, String str2) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOf_deleteJSONDataForKey.acquire();
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        if (str2 == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, str2);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOf_deleteJSONDataForKey.release(acquire);
        }
    }

    public List<JSONDataEntity> _loadJSONDataForKey(String str, String str2) {
        Long l;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM json_data WHERE `key` = ? AND scope_key = ? ORDER BY last_updated DESC LIMIT 1;", 2);
        if (str == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str);
        }
        if (str2 == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindString(2, str2);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "value");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "last_updated");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "scope_key");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                String string = query.getString(columnIndexOrThrow2);
                String string2 = query.getString(columnIndexOrThrow3);
                if (query.isNull(columnIndexOrThrow4)) {
                    l = null;
                } else {
                    l = Long.valueOf(query.getLong(columnIndexOrThrow4));
                }
                JSONDataEntity jSONDataEntity = new JSONDataEntity(string, string2, Converters.longToDate(l), query.getString(columnIndexOrThrow5));
                jSONDataEntity.f1252id = query.getLong(columnIndexOrThrow);
                arrayList.add(jSONDataEntity);
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }
}
