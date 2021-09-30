package expo.modules.updates.launcher;

import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.List;
import org.json.JSONObject;

public interface SelectionPolicy {
    UpdateEntity selectUpdateToLaunch(List<UpdateEntity> list, JSONObject jSONObject);

    List<UpdateEntity> selectUpdatesToDelete(List<UpdateEntity> list, UpdateEntity updateEntity, JSONObject jSONObject);

    boolean shouldLoadNewUpdate(UpdateEntity updateEntity, UpdateEntity updateEntity2, JSONObject jSONObject);
}
