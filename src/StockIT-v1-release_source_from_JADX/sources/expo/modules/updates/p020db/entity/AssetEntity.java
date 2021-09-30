package expo.modules.updates.p020db.entity;

import android.net.Uri;
import expo.modules.updates.p020db.enums.HashType;
import java.util.Date;
import org.json.JSONObject;

/* renamed from: expo.modules.updates.db.entity.AssetEntity */
public class AssetEntity {
    public Date downloadTime = null;
    public String embeddedAssetFilename = null;
    public byte[] hash = null;
    public HashType hashType = HashType.SHA256;
    public JSONObject headers = null;

    /* renamed from: id */
    public long f1251id = 0;
    public boolean isLaunchAsset = false;
    public String key;
    public boolean markedForDeletion = false;
    public JSONObject metadata = null;
    public String relativePath = null;
    public String resourcesFilename = null;
    public String resourcesFolder = null;
    public Float scale = null;
    public Float[] scales = null;
    public String type;
    public Uri url = null;

    public AssetEntity(String str, String str2) {
        this.key = str;
        this.type = str2;
    }
}
