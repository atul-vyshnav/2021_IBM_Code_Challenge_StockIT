package expo.modules.updates.p020db.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.p007db.SupportSQLiteStatement;
import com.google.android.gms.common.internal.ImagesContract;
import expo.modules.updates.p020db.Converters;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateAssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* renamed from: expo.modules.updates.db.dao.AssetDao_Impl */
public final class AssetDao_Impl extends AssetDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter __insertionAdapterOfAssetEntity;
    private final EntityInsertionAdapter __insertionAdapterOfUpdateAssetEntity;
    private final SharedSQLiteStatement __preparedStmtOf_deleteAssetsMarkedForDeletion;
    private final SharedSQLiteStatement __preparedStmtOf_markAllAssetsForDeletion;
    private final SharedSQLiteStatement __preparedStmtOf_setUpdateLaunchAsset;
    private final SharedSQLiteStatement __preparedStmtOf_unmarkUsedAssetsFromDeletion;
    private final EntityDeletionOrUpdateAdapter __updateAdapterOfAssetEntity;

    public AssetDao_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfAssetEntity = new EntityInsertionAdapter<AssetEntity>(roomDatabase) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `assets`(`id`,`url`,`key`,`headers`,`type`,`metadata`,`download_time`,`relative_path`,`hash`,`hash_type`,`marked_for_deletion`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, AssetEntity assetEntity) {
                supportSQLiteStatement.bindLong(1, assetEntity.f1251id);
                String uriToString = Converters.uriToString(assetEntity.url);
                if (uriToString == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, uriToString);
                }
                if (assetEntity.key == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, assetEntity.key);
                }
                String jsonObjectToString = Converters.jsonObjectToString(assetEntity.headers);
                if (jsonObjectToString == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, jsonObjectToString);
                }
                if (assetEntity.type == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, assetEntity.type);
                }
                String jsonObjectToString2 = Converters.jsonObjectToString(assetEntity.metadata);
                if (jsonObjectToString2 == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, jsonObjectToString2);
                }
                Long dateToLong = Converters.dateToLong(assetEntity.downloadTime);
                if (dateToLong == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindLong(7, dateToLong.longValue());
                }
                if (assetEntity.relativePath == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, assetEntity.relativePath);
                }
                if (assetEntity.hash == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindBlob(9, assetEntity.hash);
                }
                supportSQLiteStatement.bindLong(10, (long) Converters.hashTypeToInt(assetEntity.hashType));
                supportSQLiteStatement.bindLong(11, assetEntity.markedForDeletion ? 1 : 0);
            }
        };
        this.__insertionAdapterOfUpdateAssetEntity = new EntityInsertionAdapter<UpdateAssetEntity>(roomDatabase) {
            public String createQuery() {
                return "INSERT OR REPLACE INTO `updates_assets`(`update_id`,`asset_id`) VALUES (?,?)";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, UpdateAssetEntity updateAssetEntity) {
                byte[] uuidToBytes = Converters.uuidToBytes(updateAssetEntity.updateId);
                if (uuidToBytes == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindBlob(1, uuidToBytes);
                }
                supportSQLiteStatement.bindLong(2, updateAssetEntity.assetId);
            }
        };
        this.__updateAdapterOfAssetEntity = new EntityDeletionOrUpdateAdapter<AssetEntity>(roomDatabase) {
            public String createQuery() {
                return "UPDATE OR ABORT `assets` SET `id` = ?,`url` = ?,`key` = ?,`headers` = ?,`type` = ?,`metadata` = ?,`download_time` = ?,`relative_path` = ?,`hash` = ?,`hash_type` = ?,`marked_for_deletion` = ? WHERE `id` = ?";
            }

            public void bind(SupportSQLiteStatement supportSQLiteStatement, AssetEntity assetEntity) {
                supportSQLiteStatement.bindLong(1, assetEntity.f1251id);
                String uriToString = Converters.uriToString(assetEntity.url);
                if (uriToString == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, uriToString);
                }
                if (assetEntity.key == null) {
                    supportSQLiteStatement.bindNull(3);
                } else {
                    supportSQLiteStatement.bindString(3, assetEntity.key);
                }
                String jsonObjectToString = Converters.jsonObjectToString(assetEntity.headers);
                if (jsonObjectToString == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindString(4, jsonObjectToString);
                }
                if (assetEntity.type == null) {
                    supportSQLiteStatement.bindNull(5);
                } else {
                    supportSQLiteStatement.bindString(5, assetEntity.type);
                }
                String jsonObjectToString2 = Converters.jsonObjectToString(assetEntity.metadata);
                if (jsonObjectToString2 == null) {
                    supportSQLiteStatement.bindNull(6);
                } else {
                    supportSQLiteStatement.bindString(6, jsonObjectToString2);
                }
                Long dateToLong = Converters.dateToLong(assetEntity.downloadTime);
                if (dateToLong == null) {
                    supportSQLiteStatement.bindNull(7);
                } else {
                    supportSQLiteStatement.bindLong(7, dateToLong.longValue());
                }
                if (assetEntity.relativePath == null) {
                    supportSQLiteStatement.bindNull(8);
                } else {
                    supportSQLiteStatement.bindString(8, assetEntity.relativePath);
                }
                if (assetEntity.hash == null) {
                    supportSQLiteStatement.bindNull(9);
                } else {
                    supportSQLiteStatement.bindBlob(9, assetEntity.hash);
                }
                supportSQLiteStatement.bindLong(10, (long) Converters.hashTypeToInt(assetEntity.hashType));
                supportSQLiteStatement.bindLong(11, assetEntity.markedForDeletion ? 1 : 0);
                supportSQLiteStatement.bindLong(12, assetEntity.f1251id);
            }
        };
        this.__preparedStmtOf_setUpdateLaunchAsset = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "UPDATE updates SET launch_asset_id = ? WHERE id = ?;";
            }
        };
        this.__preparedStmtOf_markAllAssetsForDeletion = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "UPDATE assets SET marked_for_deletion = 1;";
            }
        };
        this.__preparedStmtOf_unmarkUsedAssetsFromDeletion = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "UPDATE assets SET marked_for_deletion = 0 WHERE id IN ( SELECT asset_id FROM updates_assets INNER JOIN updates ON updates_assets.update_id = updates.id WHERE updates.keep);";
            }
        };
        this.__preparedStmtOf_deleteAssetsMarkedForDeletion = new SharedSQLiteStatement(roomDatabase) {
            public String createQuery() {
                return "DELETE FROM assets WHERE marked_for_deletion = 1;";
            }
        };
    }

    public long _insertAsset(AssetEntity assetEntity) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long insertAndReturnId = this.__insertionAdapterOfAssetEntity.insertAndReturnId(assetEntity);
            this.__db.setTransactionSuccessful();
            return insertAndReturnId;
        } finally {
            this.__db.endTransaction();
        }
    }

    public void _insertUpdateAsset(UpdateAssetEntity updateAssetEntity) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfUpdateAssetEntity.insert(updateAssetEntity);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void updateAsset(AssetEntity assetEntity) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__updateAdapterOfAssetEntity.handle(assetEntity);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public void insertAssets(List<AssetEntity> list, UpdateEntity updateEntity) {
        this.__db.beginTransaction();
        try {
            super.insertAssets(list, updateEntity);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public boolean addExistingAssetToUpdate(UpdateEntity updateEntity, AssetEntity assetEntity, boolean z) {
        this.__db.beginTransaction();
        try {
            boolean access$101 = super.addExistingAssetToUpdate(updateEntity, assetEntity, z);
            this.__db.setTransactionSuccessful();
            return access$101;
        } finally {
            this.__db.endTransaction();
        }
    }

    public List<AssetEntity> deleteUnusedAssets() {
        this.__db.beginTransaction();
        try {
            List<AssetEntity> access$201 = super.deleteUnusedAssets();
            this.__db.setTransactionSuccessful();
            return access$201;
        } finally {
            this.__db.endTransaction();
        }
    }

    public void _setUpdateLaunchAsset(long j, UUID uuid) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOf_setUpdateLaunchAsset.acquire();
        acquire.bindLong(1, j);
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
            this.__preparedStmtOf_setUpdateLaunchAsset.release(acquire);
        }
    }

    public void _markAllAssetsForDeletion() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOf_markAllAssetsForDeletion.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOf_markAllAssetsForDeletion.release(acquire);
        }
    }

    public void _unmarkUsedAssetsFromDeletion() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOf_unmarkUsedAssetsFromDeletion.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOf_unmarkUsedAssetsFromDeletion.release(acquire);
        }
    }

    public void _deleteAssetsMarkedForDeletion() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement acquire = this.__preparedStmtOf_deleteAssetsMarkedForDeletion.acquire();
        this.__db.beginTransaction();
        try {
            acquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOf_deleteAssetsMarkedForDeletion.release(acquire);
        }
    }

    public List<AssetEntity> _loadAssetsMarkedForDeletion() {
        Long l;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM assets WHERE marked_for_deletion = 1;", 0);
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, ImagesContract.URL);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "headers");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "type");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, TtmlNode.TAG_METADATA);
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "download_time");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "relative_path");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "hash");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "hash_type");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "marked_for_deletion");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                int i = columnIndexOrThrow3;
                AssetEntity assetEntity = new AssetEntity(query.getString(columnIndexOrThrow3), query.getString(columnIndexOrThrow5));
                assetEntity.f1251id = query.getLong(columnIndexOrThrow);
                assetEntity.url = Converters.stringToUri(query.getString(columnIndexOrThrow2));
                assetEntity.headers = Converters.stringToJsonObject(query.getString(columnIndexOrThrow4));
                assetEntity.metadata = Converters.stringToJsonObject(query.getString(columnIndexOrThrow6));
                if (query.isNull(columnIndexOrThrow7)) {
                    l = null;
                } else {
                    l = Long.valueOf(query.getLong(columnIndexOrThrow7));
                }
                assetEntity.downloadTime = Converters.longToDate(l);
                assetEntity.relativePath = query.getString(columnIndexOrThrow8);
                assetEntity.hash = query.getBlob(columnIndexOrThrow9);
                assetEntity.hashType = Converters.intToHashType(query.getInt(columnIndexOrThrow10));
                assetEntity.markedForDeletion = query.getInt(columnIndexOrThrow11) != 0;
                arrayList.add(assetEntity);
                columnIndexOrThrow3 = i;
            }
            return arrayList;
        } finally {
            query.close();
            acquire.release();
        }
    }

    public List<AssetEntity> _loadAssetWithKey(String str) {
        RoomSQLiteQuery roomSQLiteQuery;
        Long l;
        String str2 = str;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM assets WHERE `key` = ? LIMIT 1;", 1);
        if (str2 == null) {
            acquire.bindNull(1);
        } else {
            acquire.bindString(1, str2);
        }
        this.__db.assertNotSuspendingTransaction();
        Cursor query = DBUtil.query(this.__db, acquire, false);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, ImagesContract.URL);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "headers");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "type");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, TtmlNode.TAG_METADATA);
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "download_time");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "relative_path");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "hash");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "hash_type");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "marked_for_deletion");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                int i = columnIndexOrThrow3;
                AssetEntity assetEntity = new AssetEntity(query.getString(columnIndexOrThrow3), query.getString(columnIndexOrThrow5));
                roomSQLiteQuery = acquire;
                try {
                    assetEntity.f1251id = query.getLong(columnIndexOrThrow);
                    assetEntity.url = Converters.stringToUri(query.getString(columnIndexOrThrow2));
                    assetEntity.headers = Converters.stringToJsonObject(query.getString(columnIndexOrThrow4));
                    assetEntity.metadata = Converters.stringToJsonObject(query.getString(columnIndexOrThrow6));
                    if (query.isNull(columnIndexOrThrow7)) {
                        l = null;
                    } else {
                        l = Long.valueOf(query.getLong(columnIndexOrThrow7));
                    }
                    assetEntity.downloadTime = Converters.longToDate(l);
                    assetEntity.relativePath = query.getString(columnIndexOrThrow8);
                    assetEntity.hash = query.getBlob(columnIndexOrThrow9);
                    assetEntity.hashType = Converters.intToHashType(query.getInt(columnIndexOrThrow10));
                    assetEntity.markedForDeletion = query.getInt(columnIndexOrThrow11) != 0;
                    arrayList.add(assetEntity);
                    acquire = roomSQLiteQuery;
                    columnIndexOrThrow3 = i;
                } catch (Throwable th) {
                    th = th;
                    query.close();
                    roomSQLiteQuery.release();
                    throw th;
                }
            }
            query.close();
            acquire.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = acquire;
            query.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }

    public List<AssetEntity> loadAssetsForUpdate(UUID uuid) {
        RoomSQLiteQuery roomSQLiteQuery;
        Long l;
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT assets.id, url, `key`, headers, type, assets.metadata, download_time, relative_path, hash, hash_type, marked_for_deletion FROM assets INNER JOIN updates_assets ON updates_assets.asset_id = assets.id INNER JOIN updates ON updates_assets.update_id = updates.id WHERE updates.id = ?;", 1);
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
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, ImagesContract.URL);
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "key");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(query, "headers");
            int columnIndexOrThrow5 = CursorUtil.getColumnIndexOrThrow(query, "type");
            int columnIndexOrThrow6 = CursorUtil.getColumnIndexOrThrow(query, TtmlNode.TAG_METADATA);
            int columnIndexOrThrow7 = CursorUtil.getColumnIndexOrThrow(query, "download_time");
            int columnIndexOrThrow8 = CursorUtil.getColumnIndexOrThrow(query, "relative_path");
            int columnIndexOrThrow9 = CursorUtil.getColumnIndexOrThrow(query, "hash");
            int columnIndexOrThrow10 = CursorUtil.getColumnIndexOrThrow(query, "hash_type");
            int columnIndexOrThrow11 = CursorUtil.getColumnIndexOrThrow(query, "marked_for_deletion");
            ArrayList arrayList = new ArrayList(query.getCount());
            while (query.moveToNext()) {
                int i = columnIndexOrThrow3;
                AssetEntity assetEntity = new AssetEntity(query.getString(columnIndexOrThrow3), query.getString(columnIndexOrThrow5));
                roomSQLiteQuery = acquire;
                try {
                    assetEntity.f1251id = query.getLong(columnIndexOrThrow);
                    assetEntity.url = Converters.stringToUri(query.getString(columnIndexOrThrow2));
                    assetEntity.headers = Converters.stringToJsonObject(query.getString(columnIndexOrThrow4));
                    assetEntity.metadata = Converters.stringToJsonObject(query.getString(columnIndexOrThrow6));
                    if (query.isNull(columnIndexOrThrow7)) {
                        l = null;
                    } else {
                        l = Long.valueOf(query.getLong(columnIndexOrThrow7));
                    }
                    assetEntity.downloadTime = Converters.longToDate(l);
                    assetEntity.relativePath = query.getString(columnIndexOrThrow8);
                    assetEntity.hash = query.getBlob(columnIndexOrThrow9);
                    assetEntity.hashType = Converters.intToHashType(query.getInt(columnIndexOrThrow10));
                    assetEntity.markedForDeletion = query.getInt(columnIndexOrThrow11) != 0;
                    arrayList.add(assetEntity);
                    acquire = roomSQLiteQuery;
                    columnIndexOrThrow3 = i;
                } catch (Throwable th) {
                    th = th;
                    query.close();
                    roomSQLiteQuery.release();
                    throw th;
                }
            }
            query.close();
            acquire.release();
            return arrayList;
        } catch (Throwable th2) {
            th = th2;
            roomSQLiteQuery = acquire;
            query.close();
            roomSQLiteQuery.release();
            throw th;
        }
    }
}
