package expo.modules.imagepicker.tasks;

import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import expo.modules.imagepicker.ImagePickerConstants;
import expo.modules.imagepicker.exporters.ImageExporter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import kotlin.Metadata;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000'\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u00020\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0016J\"\u0010\u0006\u001a\u00020\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0016¨\u0006\f"}, mo40253d2 = {"expo/modules/imagepicker/tasks/ImageResultTask$doInBackground$imageExporterHandler$1", "Lexpo/modules/imagepicker/exporters/ImageExporter$Listener;", "onFailure", "", "cause", "", "onResult", "out", "Ljava/io/ByteArrayOutputStream;", "width", "", "height", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ImageResultTask.kt */
public final class ImageResultTask$doInBackground$imageExporterHandler$1 implements ImageExporter.Listener {
    final /* synthetic */ Bundle $exif;
    final /* synthetic */ File $outputFile;
    final /* synthetic */ ImageResultTask this$0;

    ImageResultTask$doInBackground$imageExporterHandler$1(ImageResultTask imageResultTask, File file, Bundle bundle) {
        this.this$0 = imageResultTask;
        this.$outputFile = file;
        this.$exif = bundle;
    }

    public void onResult(ByteArrayOutputStream byteArrayOutputStream, int i, int i2) {
        Bundle bundle = new Bundle();
        bundle.putString("uri", Uri.fromFile(this.$outputFile).toString());
        bundle.putInt("width", i);
        bundle.putInt("height", i2);
        bundle.putBoolean("cancelled", false);
        bundle.putString("type", "image");
        if (byteArrayOutputStream != null) {
            bundle.putString("base64", Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2));
        }
        Bundle bundle2 = this.$exif;
        if (bundle2 != null) {
            bundle.putBundle(ImagePickerConstants.OPTION_EXIF, bundle2);
        }
        this.this$0.promise.resolve(bundle);
    }

    public void onFailure(Throwable th) {
        this.this$0.promise.reject(ImagePickerConstants.ERR_CAN_NOT_SAVE_RESULT, ImagePickerConstants.CAN_NOT_SAVE_RESULT_MESSAGE, th);
    }
}
