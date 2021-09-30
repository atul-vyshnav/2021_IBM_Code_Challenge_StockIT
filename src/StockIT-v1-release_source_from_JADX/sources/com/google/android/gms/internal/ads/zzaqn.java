package com.google.android.gms.internal.ads;

import com.facebook.react.uimanager.ViewProps;
import com.onesignal.OneSignalDbContract;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: com.google.android.gms:play-services-ads@@19.4.0 */
public class zzaqn {
    private final zzbgj zzdgy;
    private final String zzdoa;

    public zzaqn(zzbgj zzbgj) {
        this(zzbgj, "");
    }

    public zzaqn(zzbgj zzbgj, String str) {
        this.zzdgy = zzbgj;
        this.zzdoa = str;
    }

    public final void zzdx(String str) {
        try {
            JSONObject put = new JSONObject().put(OneSignalDbContract.NotificationTable.COLUMN_NAME_MESSAGE, str).put("action", this.zzdoa);
            if (this.zzdgy != null) {
                this.zzdgy.zza("onError", put);
            }
        } catch (JSONException e) {
            zzayp.zzc("Error occurred while dispatching error event.", e);
        }
    }

    public final void zzdy(String str) {
        try {
            this.zzdgy.zza("onReadyEventReceived", new JSONObject().put("js", str));
        } catch (JSONException e) {
            zzayp.zzc("Error occurred while dispatching ready Event.", e);
        }
    }

    public final void zza(int i, int i2, int i3, int i4) {
        try {
            this.zzdgy.zza("onSizeChanged", new JSONObject().put("x", i).put("y", i2).put("width", i3).put("height", i4));
        } catch (JSONException e) {
            zzayp.zzc("Error occurred while dispatching size change.", e);
        }
    }

    public final void zzb(int i, int i2, int i3, int i4) {
        try {
            this.zzdgy.zza("onDefaultPositionReceived", new JSONObject().put("x", i).put("y", i2).put("width", i3).put("height", i4));
        } catch (JSONException e) {
            zzayp.zzc("Error occurred while dispatching default position.", e);
        }
    }

    public final void zzdz(String str) {
        try {
            this.zzdgy.zza("onStateChanged", new JSONObject().put("state", str));
        } catch (JSONException e) {
            zzayp.zzc("Error occurred while dispatching state change.", e);
        }
    }

    public final void zza(int i, int i2, int i3, int i4, float f, int i5) {
        try {
            this.zzdgy.zza("onScreenInfoChanged", new JSONObject().put("width", i).put("height", i2).put("maxSizeWidth", i3).put("maxSizeHeight", i4).put("density", (double) f).put(ViewProps.ROTATION, i5));
        } catch (JSONException e) {
            zzayp.zzc("Error occurred while obtaining screen information.", e);
        }
    }
}
