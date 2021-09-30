package expo.modules.imagepicker;

import java.lang.ref.WeakReference;
import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference0Impl;

@Metadata(mo40251bv = {1, 0, 3}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: ImagePickerModule.kt */
final /* synthetic */ class ImagePickerModule$experienceActivity$1 extends MutablePropertyReference0Impl {
    ImagePickerModule$experienceActivity$1(ImagePickerModule imagePickerModule) {
        super(imagePickerModule, ImagePickerModule.class, "_experienceActivity", "get_experienceActivity()Ljava/lang/ref/WeakReference;", 0);
    }

    public Object get() {
        return ImagePickerModule.access$get_experienceActivity$p((ImagePickerModule) this.receiver);
    }

    public void set(Object obj) {
        ((ImagePickerModule) this.receiver)._experienceActivity = (WeakReference) obj;
    }
}
