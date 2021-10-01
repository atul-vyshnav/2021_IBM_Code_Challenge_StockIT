package p008cl.json.social;

import android.content.ActivityNotFoundException;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;

/* renamed from: cl.json.social.GenericShare */
public class GenericShare extends ShareIntent {
    /* access modifiers changed from: protected */
    public String getDefaultWebLink() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String getPackage() {
        return null;
    }

    /* access modifiers changed from: protected */
    public String getPlayStoreLink() {
        return null;
    }

    public GenericShare(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
    }

    public void open(ReadableMap readableMap) throws ActivityNotFoundException {
        super.open(readableMap);
        openIntentChooser();
    }
}
