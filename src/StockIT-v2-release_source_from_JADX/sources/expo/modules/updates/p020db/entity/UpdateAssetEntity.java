package expo.modules.updates.p020db.entity;

import java.util.UUID;

/* renamed from: expo.modules.updates.db.entity.UpdateAssetEntity */
public class UpdateAssetEntity {
    public long assetId;
    public UUID updateId;

    public UpdateAssetEntity(UUID uuid, long j) {
        this.updateId = uuid;
        this.assetId = j;
    }
}
