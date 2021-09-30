package expo.modules.imagepicker;

import com.facebook.react.bridge.BaseJavaModule;
import expo.modules.imagepicker.MediaTypes;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.unimodules.core.Promise;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0017\n\u0002\u0010\u000e\n\u0002\b\u0002\b\b\u0018\u0000 #2\u00020\u0001:\u0001#BC\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0006\u0010\u000b\u001a\u00020\u0005\u0012\u0006\u0010\f\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\nHÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003JU\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001e\u001a\u00020\u00052\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\u0003HÖ\u0001J\t\u0010!\u001a\u00020\"HÖ\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0010R\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\f\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014¨\u0006$"}, mo40253d2 = {"Lexpo/modules/imagepicker/ImagePickerOptions;", "", "quality", "", "isAllowsEditing", "", "forceAspect", "", "isBase64", "mediaTypes", "Lexpo/modules/imagepicker/MediaTypes;", "isExif", "videoMaxDuration", "(IZLjava/util/List;ZLexpo/modules/imagepicker/MediaTypes;ZI)V", "getForceAspect", "()Ljava/util/List;", "()Z", "getMediaTypes", "()Lexpo/modules/imagepicker/MediaTypes;", "getQuality", "()I", "getVideoMaxDuration", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "toString", "", "Companion", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ImagePickerOptions.kt */
public final class ImagePickerOptions {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private final List<?> forceAspect;
    private final boolean isAllowsEditing;
    private final boolean isBase64;
    private final boolean isExif;
    private final MediaTypes mediaTypes;
    private final int quality;
    private final int videoMaxDuration;

    public static /* synthetic */ ImagePickerOptions copy$default(ImagePickerOptions imagePickerOptions, int i, boolean z, List<?> list, boolean z2, MediaTypes mediaTypes2, boolean z3, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = imagePickerOptions.quality;
        }
        if ((i3 & 2) != 0) {
            z = imagePickerOptions.isAllowsEditing;
        }
        boolean z4 = z;
        if ((i3 & 4) != 0) {
            list = imagePickerOptions.forceAspect;
        }
        List<?> list2 = list;
        if ((i3 & 8) != 0) {
            z2 = imagePickerOptions.isBase64;
        }
        boolean z5 = z2;
        if ((i3 & 16) != 0) {
            mediaTypes2 = imagePickerOptions.mediaTypes;
        }
        MediaTypes mediaTypes3 = mediaTypes2;
        if ((i3 & 32) != 0) {
            z3 = imagePickerOptions.isExif;
        }
        boolean z6 = z3;
        if ((i3 & 64) != 0) {
            i2 = imagePickerOptions.videoMaxDuration;
        }
        return imagePickerOptions.copy(i, z4, list2, z5, mediaTypes3, z6, i2);
    }

    public final int component1() {
        return this.quality;
    }

    public final boolean component2() {
        return this.isAllowsEditing;
    }

    public final List<?> component3() {
        return this.forceAspect;
    }

    public final boolean component4() {
        return this.isBase64;
    }

    public final MediaTypes component5() {
        return this.mediaTypes;
    }

    public final boolean component6() {
        return this.isExif;
    }

    public final int component7() {
        return this.videoMaxDuration;
    }

    public final ImagePickerOptions copy(int i, boolean z, List<?> list, boolean z2, MediaTypes mediaTypes2, boolean z3, int i2) {
        Intrinsics.checkNotNullParameter(mediaTypes2, ImagePickerConstants.OPTION_MEDIA_TYPES);
        return new ImagePickerOptions(i, z, list, z2, mediaTypes2, z3, i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImagePickerOptions)) {
            return false;
        }
        ImagePickerOptions imagePickerOptions = (ImagePickerOptions) obj;
        return this.quality == imagePickerOptions.quality && this.isAllowsEditing == imagePickerOptions.isAllowsEditing && Intrinsics.areEqual((Object) this.forceAspect, (Object) imagePickerOptions.forceAspect) && this.isBase64 == imagePickerOptions.isBase64 && Intrinsics.areEqual((Object) this.mediaTypes, (Object) imagePickerOptions.mediaTypes) && this.isExif == imagePickerOptions.isExif && this.videoMaxDuration == imagePickerOptions.videoMaxDuration;
    }

    public int hashCode() {
        int i = this.quality * 31;
        boolean z = this.isAllowsEditing;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i2 = (i + (z ? 1 : 0)) * 31;
        List<?> list = this.forceAspect;
        int i3 = 0;
        int hashCode = (i2 + (list != null ? list.hashCode() : 0)) * 31;
        boolean z3 = this.isBase64;
        if (z3) {
            z3 = true;
        }
        int i4 = (hashCode + (z3 ? 1 : 0)) * 31;
        MediaTypes mediaTypes2 = this.mediaTypes;
        if (mediaTypes2 != null) {
            i3 = mediaTypes2.hashCode();
        }
        int i5 = (i4 + i3) * 31;
        boolean z4 = this.isExif;
        if (!z4) {
            z2 = z4;
        }
        return ((i5 + (z2 ? 1 : 0)) * 31) + this.videoMaxDuration;
    }

    public String toString() {
        return "ImagePickerOptions(quality=" + this.quality + ", isAllowsEditing=" + this.isAllowsEditing + ", forceAspect=" + this.forceAspect + ", isBase64=" + this.isBase64 + ", mediaTypes=" + this.mediaTypes + ", isExif=" + this.isExif + ", videoMaxDuration=" + this.videoMaxDuration + ")";
    }

    public ImagePickerOptions(int i, boolean z, List<?> list, boolean z2, MediaTypes mediaTypes2, boolean z3, int i2) {
        Intrinsics.checkNotNullParameter(mediaTypes2, ImagePickerConstants.OPTION_MEDIA_TYPES);
        this.quality = i;
        this.isAllowsEditing = z;
        this.forceAspect = list;
        this.isBase64 = z2;
        this.mediaTypes = mediaTypes2;
        this.isExif = z3;
        this.videoMaxDuration = i2;
    }

    public final int getQuality() {
        return this.quality;
    }

    public final boolean isAllowsEditing() {
        return this.isAllowsEditing;
    }

    public final List<?> getForceAspect() {
        return this.forceAspect;
    }

    public final boolean isBase64() {
        return this.isBase64;
    }

    public final MediaTypes getMediaTypes() {
        return this.mediaTypes;
    }

    public final boolean isExif() {
        return this.isExif;
    }

    @Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\u0006\u0010\b\u001a\u00020\t¨\u0006\n"}, mo40253d2 = {"Lexpo/modules/imagepicker/ImagePickerOptions$Companion;", "", "()V", "optionsFromMap", "Lexpo/modules/imagepicker/ImagePickerOptions;", "options", "", "", "promise", "Lorg/unimodules/core/Promise;", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
    /* compiled from: ImagePickerOptions.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ImagePickerOptions optionsFromMap(Map<String, ? extends Object> map, Promise promise) {
            int i;
            List list;
            int i2;
            Intrinsics.checkNotNullParameter(map, "options");
            Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
            Object obj = map.get(ImagePickerConstants.OPTION_QUALITY);
            if (obj == null) {
                i = 100;
            } else if (obj instanceof Number) {
                i = (int) (((Number) obj).doubleValue() * ((double) 100));
            } else {
                promise.reject(ImagePickerConstants.ERR_INVALID_OPTION, "Quality can not be `null`.");
                return null;
            }
            Object obj2 = map.get(ImagePickerConstants.OPTION_ALLOWS_EDITING);
            if (!(obj2 instanceof Boolean)) {
                obj2 = null;
            }
            Boolean bool = (Boolean) obj2;
            boolean booleanValue = bool != null ? bool.booleanValue() : false;
            Object obj3 = map.get(ImagePickerConstants.OPTION_ASPECT);
            if (obj3 != null) {
                if (obj3 instanceof List) {
                    List list2 = (List) obj3;
                    if (list2.size() == 2 && (list2.get(0) instanceof Number) && (list2.get(1) instanceof Number)) {
                        list = list2;
                    }
                }
                promise.reject(ImagePickerConstants.ERR_INVALID_OPTION, "'Aspect option must be of form [Number, Number]");
                return null;
            }
            list = null;
            Object obj4 = map.get("base64");
            if (!(obj4 instanceof Boolean)) {
                obj4 = null;
            }
            Boolean bool2 = (Boolean) obj4;
            boolean booleanValue2 = bool2 != null ? bool2.booleanValue() : false;
            MediaTypes.Companion companion = MediaTypes.Companion;
            Object obj5 = map.get(ImagePickerConstants.OPTION_MEDIA_TYPES);
            if (!(obj5 instanceof String)) {
                obj5 = null;
            }
            String str = (String) obj5;
            if (str == null) {
                str = "Images";
            }
            MediaTypes fromString = companion.fromString(str);
            if (fromString == null) {
                promise.reject(ImagePickerConstants.ERR_INVALID_OPTION, "Unknown media types: " + map.get(ImagePickerConstants.OPTION_MEDIA_TYPES));
                return null;
            }
            Object obj6 = map.get(ImagePickerConstants.OPTION_EXIF);
            if (!(obj6 instanceof Boolean)) {
                obj6 = null;
            }
            Boolean bool3 = (Boolean) obj6;
            boolean booleanValue3 = bool3 != null ? bool3.booleanValue() : false;
            Object obj7 = map.get(ImagePickerConstants.OPTION_VIDEO_MAX_DURATION);
            if (obj7 != null) {
                if (obj7 instanceof Number) {
                    Number number = (Number) obj7;
                    if (number.intValue() >= 0) {
                        i2 = number.intValue();
                    }
                }
                promise.reject(ImagePickerConstants.ERR_INVALID_OPTION, "videoMaxDuration must be a non-negative integer");
                return null;
            }
            i2 = 0;
            return new ImagePickerOptions(i, booleanValue, list, booleanValue2, fromString, booleanValue3, i2);
        }
    }

    public final int getVideoMaxDuration() {
        return this.videoMaxDuration;
    }
}
