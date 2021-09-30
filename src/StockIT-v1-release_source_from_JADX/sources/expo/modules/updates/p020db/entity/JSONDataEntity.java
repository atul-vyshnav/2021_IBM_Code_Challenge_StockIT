package expo.modules.updates.p020db.entity;

import java.util.Date;

/* renamed from: expo.modules.updates.db.entity.JSONDataEntity */
public class JSONDataEntity {

    /* renamed from: id */
    public long f1252id = 0;
    public String key;
    public Date lastUpdated;
    public String scopeKey;
    public String value;

    public JSONDataEntity(String str, String str2, Date date, String str3) {
        this.key = str;
        this.value = str2;
        this.lastUpdated = date;
        this.scopeKey = str3;
    }
}
