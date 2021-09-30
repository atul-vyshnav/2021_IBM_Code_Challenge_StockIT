package expo.modules.updates.p020db.dao;

import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateAssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.List;
import java.util.UUID;

/* renamed from: expo.modules.updates.db.dao.AssetDao */
public abstract class AssetDao {
    public abstract void _deleteAssetsMarkedForDeletion();

    public abstract long _insertAsset(AssetEntity assetEntity);

    public abstract void _insertUpdateAsset(UpdateAssetEntity updateAssetEntity);

    public abstract List<AssetEntity> _loadAssetWithKey(String str);

    public abstract List<AssetEntity> _loadAssetsMarkedForDeletion();

    public abstract void _markAllAssetsForDeletion();

    public abstract void _setUpdateLaunchAsset(long j, UUID uuid);

    public abstract void _unmarkUsedAssetsFromDeletion();

    public abstract List<AssetEntity> loadAssetsForUpdate(UUID uuid);

    public abstract void updateAsset(AssetEntity assetEntity);

    public void insertAssets(List<AssetEntity> list, UpdateEntity updateEntity) {
        for (AssetEntity next : list) {
            long _insertAsset = _insertAsset(next);
            _insertUpdateAsset(new UpdateAssetEntity(updateEntity.f1253id, _insertAsset));
            if (next.isLaunchAsset) {
                _setUpdateLaunchAsset(_insertAsset, updateEntity.f1253id);
            }
        }
    }

    public AssetEntity loadAssetWithKey(String str) {
        if (str == null) {
            return null;
        }
        List<AssetEntity> _loadAssetWithKey = _loadAssetWithKey(str);
        if (_loadAssetWithKey.size() > 0) {
            return _loadAssetWithKey.get(0);
        }
        return null;
    }

    public void mergeAndUpdateAsset(AssetEntity assetEntity, AssetEntity assetEntity2) {
        if (assetEntity2.url != null && assetEntity.url == null) {
            assetEntity.url = assetEntity2.url;
            updateAsset(assetEntity);
        }
        assetEntity.isLaunchAsset = assetEntity2.isLaunchAsset;
    }

    public boolean addExistingAssetToUpdate(UpdateEntity updateEntity, AssetEntity assetEntity, boolean z) {
        AssetEntity loadAssetWithKey = loadAssetWithKey(assetEntity.key);
        if (loadAssetWithKey == null) {
            return false;
        }
        long j = loadAssetWithKey.f1251id;
        _insertUpdateAsset(new UpdateAssetEntity(updateEntity.f1253id, j));
        if (!z) {
            return true;
        }
        _setUpdateLaunchAsset(j, updateEntity.f1253id);
        return true;
    }

    public List<AssetEntity> deleteUnusedAssets() {
        _markAllAssetsForDeletion();
        _unmarkUsedAssetsFromDeletion();
        List<AssetEntity> _loadAssetsMarkedForDeletion = _loadAssetsMarkedForDeletion();
        _deleteAssetsMarkedForDeletion();
        return _loadAssetsMarkedForDeletion;
    }
}
