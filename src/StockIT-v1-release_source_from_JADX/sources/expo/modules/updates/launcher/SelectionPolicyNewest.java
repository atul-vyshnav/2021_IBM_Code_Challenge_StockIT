package expo.modules.updates.launcher;

import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;

public class SelectionPolicyNewest implements SelectionPolicy {
    private List<String> mRuntimeVersions;

    public SelectionPolicyNewest(List<String> list) {
        this.mRuntimeVersions = list;
    }

    public SelectionPolicyNewest(String str) {
        this.mRuntimeVersions = Arrays.asList(new String[]{str});
    }

    public UpdateEntity selectUpdateToLaunch(List<UpdateEntity> list, JSONObject jSONObject) {
        UpdateEntity updateEntity = null;
        for (UpdateEntity next : list) {
            if (this.mRuntimeVersions.contains(next.runtimeVersion) && (updateEntity == null || updateEntity.commitTime.before(next.commitTime))) {
                updateEntity = next;
            }
        }
        return updateEntity;
    }

    public List<UpdateEntity> selectUpdatesToDelete(List<UpdateEntity> list, UpdateEntity updateEntity, JSONObject jSONObject) {
        if (updateEntity == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        UpdateEntity updateEntity2 = null;
        for (UpdateEntity next : list) {
            if (next.commitTime.before(updateEntity.commitTime)) {
                arrayList.add(next);
                if (updateEntity2 == null || updateEntity2.commitTime.before(next.commitTime)) {
                    updateEntity2 = next;
                }
            }
        }
        if (updateEntity2 != null) {
            arrayList.remove(updateEntity2);
        }
        return arrayList;
    }

    public boolean shouldLoadNewUpdate(UpdateEntity updateEntity, UpdateEntity updateEntity2, JSONObject jSONObject) {
        if (updateEntity2 == null) {
            return true;
        }
        if (updateEntity == null) {
            return false;
        }
        return updateEntity.commitTime.after(updateEntity2.commitTime);
    }
}
