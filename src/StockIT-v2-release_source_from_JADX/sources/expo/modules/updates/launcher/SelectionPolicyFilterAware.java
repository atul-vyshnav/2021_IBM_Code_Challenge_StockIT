package expo.modules.updates.launcher;

import android.util.Log;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class SelectionPolicyFilterAware implements SelectionPolicy {
    private static final String TAG = SelectionPolicyFilterAware.class.getSimpleName();
    private List<String> mRuntimeVersions;

    public SelectionPolicyFilterAware(List<String> list) {
        this.mRuntimeVersions = list;
    }

    public SelectionPolicyFilterAware(String str) {
        this((List<String>) Collections.singletonList(str));
    }

    public UpdateEntity selectUpdateToLaunch(List<UpdateEntity> list, JSONObject jSONObject) {
        UpdateEntity updateEntity = null;
        for (UpdateEntity next : list) {
            if (this.mRuntimeVersions.contains(next.runtimeVersion) && matchesFilters(next, jSONObject)) {
                if (updateEntity == null || updateEntity.commitTime.before(next.commitTime)) {
                    updateEntity = next;
                }
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
        UpdateEntity updateEntity3 = null;
        for (UpdateEntity next : list) {
            if (next.commitTime.before(updateEntity.commitTime)) {
                arrayList.add(next);
                if (updateEntity3 == null || updateEntity3.commitTime.before(next.commitTime)) {
                    updateEntity3 = next;
                }
                if (matchesFilters(next, jSONObject) && (updateEntity2 == null || updateEntity2.commitTime.before(next.commitTime))) {
                    updateEntity2 = next;
                }
            }
        }
        if (updateEntity2 != null) {
            arrayList.remove(updateEntity2);
        } else if (updateEntity3 != null) {
            arrayList.remove(updateEntity3);
        }
        return arrayList;
    }

    public boolean shouldLoadNewUpdate(UpdateEntity updateEntity, UpdateEntity updateEntity2, JSONObject jSONObject) {
        if (updateEntity == null || !matchesFilters(updateEntity, jSONObject)) {
            return false;
        }
        if (updateEntity2 != null && matchesFilters(updateEntity2, jSONObject)) {
            return updateEntity.commitTime.after(updateEntity2.commitTime);
        }
        return true;
    }

    public static boolean matchesFilters(UpdateEntity updateEntity, JSONObject jSONObject) {
        if (!(jSONObject == null || updateEntity.metadata == null || !updateEntity.metadata.has("updateMetadata"))) {
            try {
                JSONObject jSONObject2 = updateEntity.metadata.getJSONObject("updateMetadata");
                JSONObject jSONObject3 = new JSONObject();
                Iterator<String> keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String next = keys.next();
                    jSONObject3.put(next.toLowerCase(), jSONObject2.get(next));
                }
                Iterator<String> keys2 = jSONObject.keys();
                while (keys2.hasNext()) {
                    String next2 = keys2.next();
                    if (jSONObject3.has(next2) && !jSONObject.get(next2).equals(jSONObject3.get(next2))) {
                        return false;
                    }
                }
                return true;
            } catch (Exception e) {
                Log.e(TAG, "Error filtering manifest using server data", e);
            }
        }
        return true;
    }
}
