package expo.modules.updates.p020db.entity;

import expo.modules.updates.p020db.enums.UpdateStatus;
import java.util.Date;
import java.util.UUID;
import org.json.JSONObject;

/* renamed from: expo.modules.updates.db.entity.UpdateEntity */
public class UpdateEntity {
    public Date commitTime;

    /* renamed from: id */
    public UUID f1253id;
    public boolean keep = false;
    public Long launchAssetId = null;
    public JSONObject metadata = null;
    public String runtimeVersion;
    public String scopeKey;
    public UpdateStatus status = UpdateStatus.PENDING;

    public UpdateEntity(UUID uuid, Date date, String str, String str2) {
        this.f1253id = uuid;
        this.commitTime = date;
        this.runtimeVersion = str;
        this.scopeKey = str2;
    }
}
