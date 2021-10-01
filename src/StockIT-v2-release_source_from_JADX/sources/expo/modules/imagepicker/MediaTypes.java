package expo.modules.imagepicker;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0001\u0018\u0000 \u00062\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0006B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0007"}, mo40253d2 = {"Lexpo/modules/imagepicker/MediaTypes;", "", "(Ljava/lang/String;I)V", "IMAGES", "VIDEOS", "ALL", "Companion", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: MediaTypes.kt */
public enum MediaTypes {
    IMAGES,
    VIDEOS,
    ALL;
    
    public static final Companion Companion = null;

    static {
        Companion = new Companion((DefaultConstructorMarker) null);
    }

    @Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, mo40253d2 = {"Lexpo/modules/imagepicker/MediaTypes$Companion;", "", "()V", "fromString", "Lexpo/modules/imagepicker/MediaTypes;", "type", "", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
    /* compiled from: MediaTypes.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final MediaTypes fromString(String str) {
            Intrinsics.checkNotNullParameter(str, "type");
            int hashCode = str.hashCode();
            if (hashCode != -2101383528) {
                if (hashCode != -1732810888) {
                    if (hashCode == 65921 && str.equals("All")) {
                        return MediaTypes.ALL;
                    }
                } else if (str.equals("Videos")) {
                    return MediaTypes.VIDEOS;
                }
            } else if (str.equals("Images")) {
                return MediaTypes.IMAGES;
            }
            return null;
        }
    }
}
