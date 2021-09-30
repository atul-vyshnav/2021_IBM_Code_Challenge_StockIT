package expo.modules.updates.p020db.dao;

import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import expo.modules.updates.p020db.enums.UpdateStatus;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/* renamed from: expo.modules.updates.db.dao.UpdateDao */
public abstract class UpdateDao {
    public abstract void _keepUpdate(UUID uuid);

    public abstract AssetEntity _loadLaunchAsset(UUID uuid);

    public abstract List<UpdateEntity> _loadUpdatesForProjectWithStatuses(String str, List<UpdateStatus> list);

    public abstract List<UpdateEntity> _loadUpdatesWithId(UUID uuid);

    public abstract void _markUpdateWithStatus(UpdateStatus updateStatus, UUID uuid);

    public abstract void _updateUpdate(UpdateEntity updateEntity);

    public abstract void deleteUpdates(List<UpdateEntity> list);

    public abstract void insertUpdate(UpdateEntity updateEntity);

    public abstract List<UpdateEntity> loadAllUpdatesForScope(String str);

    public List<UpdateEntity> loadLaunchableUpdatesForScope(String str) {
        return _loadUpdatesForProjectWithStatuses(str, Arrays.asList(new UpdateStatus[]{UpdateStatus.READY, UpdateStatus.EMBEDDED, UpdateStatus.DEVELOPMENT}));
    }

    public UpdateEntity loadUpdateWithId(UUID uuid) {
        List<UpdateEntity> _loadUpdatesWithId = _loadUpdatesWithId(uuid);
        if (_loadUpdatesWithId.size() > 0) {
            return _loadUpdatesWithId.get(0);
        }
        return null;
    }

    public AssetEntity loadLaunchAsset(UUID uuid) {
        AssetEntity _loadLaunchAsset = _loadLaunchAsset(uuid);
        _loadLaunchAsset.isLaunchAsset = true;
        return _loadLaunchAsset;
    }

    public void setUpdateScopeKey(UpdateEntity updateEntity, String str) {
        updateEntity.scopeKey = str;
        _updateUpdate(updateEntity);
    }

    public void markUpdateFinished(UpdateEntity updateEntity, boolean z) {
        UpdateStatus updateStatus = UpdateStatus.READY;
        if (updateEntity.status == UpdateStatus.DEVELOPMENT) {
            updateStatus = UpdateStatus.DEVELOPMENT;
        } else if (z) {
            updateStatus = UpdateStatus.EMBEDDED;
        }
        _markUpdateWithStatus(updateStatus, updateEntity.f1253id);
        _keepUpdate(updateEntity.f1253id);
    }

    public void markUpdateFinished(UpdateEntity updateEntity) {
        markUpdateFinished(updateEntity, false);
    }
}
