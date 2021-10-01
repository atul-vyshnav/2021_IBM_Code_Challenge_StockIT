package expo.modules.updates.p020db.dao;

import expo.modules.updates.p020db.entity.JSONDataEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

/* renamed from: expo.modules.updates.db.dao.JSONDataDao */
public abstract class JSONDataDao {
    public abstract void _deleteJSONDataForKey(String str, String str2);

    public abstract void _insertJSONData(JSONDataEntity jSONDataEntity);

    public abstract List<JSONDataEntity> _loadJSONDataForKey(String str, String str2);

    @Nullable
    public String loadJSONStringForKey(String str, String str2) {
        List<JSONDataEntity> _loadJSONDataForKey = _loadJSONDataForKey(str, str2);
        if (_loadJSONDataForKey == null || _loadJSONDataForKey.size() == 0) {
            return null;
        }
        return _loadJSONDataForKey.get(0).value;
    }

    public void setJSONStringForKey(String str, String str2, String str3) {
        _deleteJSONDataForKey(str, str3);
        _insertJSONData(new JSONDataEntity(str, str2, new Date(), str3));
    }

    public void setMultipleFields(Map<String, String> map, String str) {
        for (Map.Entry next : map.entrySet()) {
            _deleteJSONDataForKey((String) next.getKey(), str);
            _insertJSONData(new JSONDataEntity((String) next.getKey(), (String) next.getValue(), new Date(), str));
        }
    }
}
