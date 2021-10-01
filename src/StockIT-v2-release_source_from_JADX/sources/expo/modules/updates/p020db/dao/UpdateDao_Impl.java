package expo.modules.updates.p020db.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.p007db.SupportSQLiteStatement;
import expo.modules.updates.p020db.Converters;
import expo.modules.updates.p020db.entity.UpdateEntity;
import expo.modules.updates.p020db.enums.UpdateStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* renamed from: expo.modules.updates.db.dao.UpdateDao_Impl */
public final class UpdateDao_Impl extends UpdateDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUpdateEntity;
    private final EntityInsertionAdapter __insertionAdapterOfUpdateEntity;
    private final SharedSQLiteStatement __preparedStmtOf_keepUpdate;
    private final SharedSQLiteStatement __preparedStmtOf_markUpdateWithStatus;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfUpdateEntity;

    public UpdateDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfUpdateEntity = new EntityInsertionAdapter<UpdateEntity>(roomDatabase) {
            public String createQuery() {
                return "INSERT OR ABORT INTO `updates`(`id`,`scope_key`,`commit_time`,`runtime_version`,`launch_asset_id`,`metadata`,`status`,`keep`) VALUES (?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, UpdateEntity updateEntity) {
                byte[] uuidToBytes = Converters.uuidToBytes(updateEntity.f1253id);
                if (uuidToBytes == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindBlob(1, uuidToBytes);
                }
                if (updateEntity.scopeKey == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, updateEntity.scopeKey);
                }
                Long dateToLong = Converters.dateToLong(updateEntity.commitTime);
                if (dateToLong == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindLong(3, dateToLong.longValue());
                }
                if (updateEntity.runtimeVersion == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, updateEntity.runtimeVersion);
                }
                if (updateEntity.launchAssetId == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindLong(5, updateEntity.launchAssetId.longValue());
                }
                String jsonObjectToString = Converters.jsonObjectToString(updateEntity.metadata);
                if (jsonObjectToString == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, jsonObjectToString);
                }
                supportSQLiteStatement.bindLong(7, (long) Converters.statusToInt(updateEntity.status));
                supportSQLiteStatement.bindLong(8, updateEntity.keep ? 1 : 0);
            }
        };
        this.__deletionAdapterOfUpdateEntity = new EntityDeletionOrUpdateAdapter<UpdateEntity>(roomDatabase) {
            public String createQuery() {
                return "DELETE FROM `updates` WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, UpdateEntity updateEntity) {
                byte[] uuidToBytes = Converters.uuidToBytes(updateEntity.f1253id);
                if (uuidToBytes == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindBlob(1, uuidToBytes);
                }
            }
        };
        this.__updateAdapterOfUpdateEntity = new EntityDeletionOrUpdateAdapter<UpdateEntity>(roomDatabase) {
            public String createQuery() {
                return "UPDATE OR ABORT `updates` SET `id` = ?,`scope_key` = ?,`commit_time` = ?,`runtime_version` = ?,`launch_asset_id` = ?,`metadata` = ?,`status` = ?,`keep` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, UpdateEntity updateEntity) {
                byte[] uuidToBytes = Converters.uuidToBytes(updateEntity.f1253id);
                if (uuidToBytes == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindBlob(1, uuidToBytes);
                }
                if (updateEntity.scopeKey == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, updateEntity.scopeKey);
                }
                Long dateToLong = Converters.dateToLong(updateEntity.commitTime);
                if (dateToLong == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindLong(3, dateToLong.longValue());
                }
                if (updateEntity.runtimeVersion == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, updateEntity.runtimeVersion);
                }
                if (updateEntity.launchAssetId == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindLong(5, updateEntity.launchAssetId.longValue());
                }
                String jsonObjectToString = Converters.jsonObjectToString(updateEntity.metadata);
                if (jsonObjectToString == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, jsonObjectToString);
                }
                supportSQLiteStatement.bindLong(7, (long) Converters.statusToInt(updateEntity.status));
                supportSQLiteStatement.bindLong(8, updateEntity.keep ? 1 : 0);
                byte[] uuidToBytes2 = Converters.uuidToBytes(updateEntity.f1253id);
                if (uuidToBytes2 == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindBlob(9, uuidToBytes2);
                }
            }
        };
        this.__preparedStmtOf_keepUpdate = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "UPDATE updates SET keep = 1 WHERE id = ?;";
            }
        };
        this.__preparedStmtOf_markUpdateWithStatus = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "UPDATE updates SET status = ? WHERE id = ?;";
            }
        };
    }

    public void insertUpdate(UpdateEntity updateEntity) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfUpdateEntity.insert(updateEntity);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void deleteUpdates(List<UpdateEntity> list) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfUpdateEntity.handleMultiple(list);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void _updateUpdate(UpdateEntity updateEntity) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfUpdateEntity.handle(updateEntity);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void markUpdateFinished(UpdateEntity updateEntity, boolean z) {
        this.__db.beginTransaction();
        try {
            super.markUpdateFinished(updateEntity, z);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void _keepUpdate(UUID uuid) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOf_keepUpdate.acquire();
        byte[] uuidToBytes = Converters.uuidToBytes(uuid);
        if (uuidToBytes == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindBlob(1, uuidToBytes);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOf_keepUpdate.release(acquire);
        }
    }

    public void _markUpdateWithStatus(UpdateStatus updateStatus, UUID uuid) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOf_markUpdateWithStatus.acquire();
        acquire.bindLong(1, (long) Converters.statusToInt(updateStatus));
        byte[] uuidToBytes = Converters.uuidToBytes(uuid);
        if (uuidToBytes == null) {
            acquire.bindNull(2);
        } else {
            acquire.bindBlob(2, uuidToBytes);
        }
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOf_markUpdateWithStatus.release(acquire);
        }
    }

    public List<UpdateEntity> _loadUpdatesForProjectWithStatuses(String str, List<UpdateStatus> list) {
        Long l;
        String str2 = str;
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT * FROM updates WHERE scope_key = ");
        newStringBuilder.append("?");
        newStringBuilder.append(" AND status IN (");
        int size = list.size();
        StringUtil.appendPlaceholders(newStringBuilder, size);
        newStringBuilder.append(");");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size + 1);
        if (str2 == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str2);
        }
        int i = 2;
        for (UpdateStatus statusToInt : list) {
            acquire.bindLong(i, (long) Converters.statusToInt(statusToInt));
            i++;
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "scope_key");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "commit_time");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "runtime_version");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "launch_asset_id");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, TtmlNode.TAG_METADATA);
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "status");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "keep");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                UUID bytesToUuid = Converters.bytesToUuid(query.getBlob(columnIndexOrThrow));
                String string = query.getString(columnIndexOrThrow2);
                if (query.isNull(columnIndexOrThrow3)) {
                    l = null;
                } else {
                    l = Long.valueOf(query.getLong(columnIndexOrThrow3));
                }
                int i2 = columnIndexOrThrow;
                UpdateEntity updateEntity = new UpdateEntity(bytesToUuid, Converters.longToDate(l), query.getString(columnIndexOrThrow4), string);
                if (query.isNull(columnIndexOrThrow5)) {
                    updateEntity.launchAssetId = null;
                } else {
                    updateEntity.launchAssetId = Long.valueOf(query.getLong(columnIndexOrThrow5));
                }
                updateEntity.metadata = Converters.stringToJsonObject(query.getString(columnIndexOrThrow6));
                updateEntity.status = Converters.intToStatus(query.getInt(columnIndexOrThrow7));
                updateEntity.keep = query.getInt(columnIndexOrThrow8) != 0;
                arrayList.add(updateEntity);
                columnIndexOrThrow = i2;
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    public List<UpdateEntity> _loadUpdatesWithId(UUID uuid) {
        Long l;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM updates WHERE id = ?;", 1);
        byte[] uuidToBytes = Converters.uuidToBytes(uuid);
        if (uuidToBytes == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindBlob(1, uuidToBytes);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "scope_key");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "commit_time");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "runtime_version");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "launch_asset_id");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, TtmlNode.TAG_METADATA);
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "status");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "keep");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                UUID bytesToUuid = Converters.bytesToUuid(query.getBlob(columnIndexOrThrow));
                String string = query.getString(columnIndexOrThrow2);
                if (query.isNull(columnIndexOrThrow3)) {
                    l = null;
                } else {
                    l = Long.valueOf(query.getLong(columnIndexOrThrow3));
                }
                int i = columnIndexOrThrow;
                UpdateEntity updateEntity = new UpdateEntity(bytesToUuid, Converters.longToDate(l), query.getString(columnIndexOrThrow4), string);
                if (query.isNull(columnIndexOrThrow5)) {
                    updateEntity.launchAssetId = null;
                } else {
                    updateEntity.launchAssetId = Long.valueOf(query.getLong(columnIndexOrThrow5));
                }
                updateEntity.metadata = Converters.stringToJsonObject(query.getString(columnIndexOrThrow6));
                updateEntity.status = Converters.intToStatus(query.getInt(columnIndexOrThrow7));
                updateEntity.keep = query.getInt(columnIndexOrThrow8) != 0;
                arrayList.add(updateEntity);
                columnIndexOrThrow = i;
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: expo.modules.updates.db.entity.AssetEntity} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: java.lang.Long} */
    /* JADX WARNING: type inference failed for: r17v0 */
    /* JADX WARNING: type inference failed for: r17v3 */
    /* JADX WARNING: type inference failed for: r17v5 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public expo.modules.updates.p020db.entity.AssetEntity _loadLaunchAsset(java.util.UUID r19) {
        /*
            r18 = this;
            r1 = r18
            java.lang.String r0 = "SELECT assets.* FROM assets INNER JOIN updates ON updates.launch_asset_id = assets.id WHERE updates.id = ?;"
            r2 = 1
            androidx.room.RoomSQLiteQuery r3 = androidx.room.RoomSQLiteQuery.acquire(r0, r2)
            byte[] r0 = expo.modules.updates.p020db.Converters.uuidToBytes(r19)
            if (r0 != 0) goto L_0x0013
            r3.bindNull(r2)
            goto L_0x0016
        L_0x0013:
            r3.bindBlob(r2, r0)
        L_0x0016:
            androidx.room.RoomDatabase r0 = r1.__db
            r0.assertNotSuspendingTransaction()
            androidx.room.RoomDatabase r0 = r1.__db
            r4 = 0
            android.database.Cursor r5 = androidx.room.util.DBUtil.query(r0, r3, r4)
            java.lang.String r0 = "id"
            int r0 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r0)     // Catch:{ all -> 0x00da }
            java.lang.String r6 = "url"
            int r6 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r6)     // Catch:{ all -> 0x00da }
            java.lang.String r7 = "key"
            int r7 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r7)     // Catch:{ all -> 0x00da }
            java.lang.String r8 = "headers"
            int r8 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r8)     // Catch:{ all -> 0x00da }
            java.lang.String r9 = "type"
            int r9 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r9)     // Catch:{ all -> 0x00da }
            java.lang.String r10 = "metadata"
            int r10 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r10)     // Catch:{ all -> 0x00da }
            java.lang.String r11 = "download_time"
            int r11 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r11)     // Catch:{ all -> 0x00da }
            java.lang.String r12 = "relative_path"
            int r12 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r12)     // Catch:{ all -> 0x00da }
            java.lang.String r13 = "hash"
            int r13 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r13)     // Catch:{ all -> 0x00da }
            java.lang.String r14 = "hash_type"
            int r14 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r14)     // Catch:{ all -> 0x00da }
            java.lang.String r15 = "marked_for_deletion"
            int r15 = androidx.room.util.CursorUtil.getColumnIndexOrThrow(r5, r15)     // Catch:{ all -> 0x00da }
            boolean r16 = r5.moveToFirst()     // Catch:{ all -> 0x00da }
            r17 = 0
            if (r16 == 0) goto L_0x00d3
            java.lang.String r7 = r5.getString(r7)     // Catch:{ all -> 0x00da }
            java.lang.String r9 = r5.getString(r9)     // Catch:{ all -> 0x00da }
            expo.modules.updates.db.entity.AssetEntity r2 = new expo.modules.updates.db.entity.AssetEntity     // Catch:{ all -> 0x00da }
            r2.<init>(r7, r9)     // Catch:{ all -> 0x00da }
            long r0 = r5.getLong(r0)     // Catch:{ all -> 0x00da }
            r2.f1251id = r0     // Catch:{ all -> 0x00da }
            java.lang.String r0 = r5.getString(r6)     // Catch:{ all -> 0x00da }
            android.net.Uri r0 = expo.modules.updates.p020db.Converters.stringToUri(r0)     // Catch:{ all -> 0x00da }
            r2.url = r0     // Catch:{ all -> 0x00da }
            java.lang.String r0 = r5.getString(r8)     // Catch:{ all -> 0x00da }
            org.json.JSONObject r0 = expo.modules.updates.p020db.Converters.stringToJsonObject(r0)     // Catch:{ all -> 0x00da }
            r2.headers = r0     // Catch:{ all -> 0x00da }
            java.lang.String r0 = r5.getString(r10)     // Catch:{ all -> 0x00da }
            org.json.JSONObject r0 = expo.modules.updates.p020db.Converters.stringToJsonObject(r0)     // Catch:{ all -> 0x00da }
            r2.metadata = r0     // Catch:{ all -> 0x00da }
            boolean r0 = r5.isNull(r11)     // Catch:{ all -> 0x00da }
            if (r0 == 0) goto L_0x00a4
            goto L_0x00ac
        L_0x00a4:
            long r0 = r5.getLong(r11)     // Catch:{ all -> 0x00da }
            java.lang.Long r17 = java.lang.Long.valueOf(r0)     // Catch:{ all -> 0x00da }
        L_0x00ac:
            java.util.Date r0 = expo.modules.updates.p020db.Converters.longToDate(r17)     // Catch:{ all -> 0x00da }
            r2.downloadTime = r0     // Catch:{ all -> 0x00da }
            java.lang.String r0 = r5.getString(r12)     // Catch:{ all -> 0x00da }
            r2.relativePath = r0     // Catch:{ all -> 0x00da }
            byte[] r0 = r5.getBlob(r13)     // Catch:{ all -> 0x00da }
            r2.hash = r0     // Catch:{ all -> 0x00da }
            int r0 = r5.getInt(r14)     // Catch:{ all -> 0x00da }
            expo.modules.updates.db.enums.HashType r0 = expo.modules.updates.p020db.Converters.intToHashType(r0)     // Catch:{ all -> 0x00da }
            r2.hashType = r0     // Catch:{ all -> 0x00da }
            int r0 = r5.getInt(r15)     // Catch:{ all -> 0x00da }
            if (r0 == 0) goto L_0x00cf
            r4 = 1
        L_0x00cf:
            r2.markedForDeletion = r4     // Catch:{ all -> 0x00da }
            r17 = r2
        L_0x00d3:
            r5.close()
            r3.release()
            return r17
        L_0x00da:
            r0 = move-exception
            r5.close()
            r3.release()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.updates.p020db.dao.UpdateDao_Impl._loadLaunchAsset(java.util.UUID):expo.modules.updates.db.entity.AssetEntity");
    }

    public List<UpdateEntity> loadAllUpdatesForScope(String str) {
        Long l;
        String str2 = str;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM updates WHERE scope_key = ?;", 1);
        if (str2 == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str2);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "scope_key");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "commit_time");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "runtime_version");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "launch_asset_id");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, TtmlNode.TAG_METADATA);
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "status");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "keep");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                UUID bytesToUuid = Converters.bytesToUuid(query.getBlob(columnIndexOrThrow));
                String string = query.getString(columnIndexOrThrow2);
                if (query.isNull(columnIndexOrThrow3)) {
                    l = null;
                } else {
                    l = Long.valueOf(query.getLong(columnIndexOrThrow3));
                }
                int i = columnIndexOrThrow;
                UpdateEntity updateEntity = new UpdateEntity(bytesToUuid, Converters.longToDate(l), query.getString(columnIndexOrThrow4), string);
                if (query.isNull(columnIndexOrThrow5)) {
                    updateEntity.launchAssetId = null;
                } else {
                    updateEntity.launchAssetId = Long.valueOf(query.getLong(columnIndexOrThrow5));
                }
                updateEntity.metadata = Converters.stringToJsonObject(query.getString(columnIndexOrThrow6));
                updateEntity.status = Converters.intToStatus(query.getInt(columnIndexOrThrow7));
                updateEntity.keep = query.getInt(columnIndexOrThrow8) != 0;
                arrayList.add(updateEntity);
                columnIndexOrThrow = i;
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }
}
