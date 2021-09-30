package expo.modules.permissions;

import kotlin.Metadata;
import p022io.nlopez.smartlocation.geofencing.providers.GeofencingGooglePlayServicesProvider;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\u0003H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015¨\u0006\u0016"}, mo40253d2 = {"Lexpo/modules/permissions/PermissionsTypes;", "", "type", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getType", "()Ljava/lang/String;", "toString", "LOCATION", "LOCATION_FOREGROUND", "LOCATION_BACKGROUND", "CAMERA", "CONTACTS", "AUDIO_RECORDING", "MEDIA_LIBRARY_WRITE_ONLY", "MEDIA_LIBRARY", "CALENDAR", "SMS", "REMINDERS", "NOTIFICATIONS", "USER_FACING_NOTIFICATIONS", "SYSTEM_BRIGHTNESS", "expo-permissions_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: PermissionsTypes.kt */
public enum PermissionsTypes {
    LOCATION(GeofencingGooglePlayServicesProvider.LOCATION_EXTRA_ID),
    LOCATION_FOREGROUND("locationForeground"),
    LOCATION_BACKGROUND("locationBackground"),
    CAMERA("camera"),
    CONTACTS("contacts"),
    AUDIO_RECORDING("audioRecording"),
    MEDIA_LIBRARY_WRITE_ONLY("mediaLibraryWriteOnly"),
    MEDIA_LIBRARY("mediaLibrary"),
    CALENDAR("calendar"),
    SMS("sms"),
    REMINDERS("reminders"),
    NOTIFICATIONS("notifications"),
    USER_FACING_NOTIFICATIONS("userFacingNotifications"),
    SYSTEM_BRIGHTNESS("systemBrightness");
    
    private final String type;

    private PermissionsTypes(String str) {
        this.type = str;
    }

    public final String getType() {
        return this.type;
    }

    public String toString() {
        return this.type;
    }
}
