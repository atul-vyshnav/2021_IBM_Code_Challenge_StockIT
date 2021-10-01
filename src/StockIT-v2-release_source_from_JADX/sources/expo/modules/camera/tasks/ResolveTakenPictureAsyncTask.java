package expo.modules.camera.tasks;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import androidx.exifinterface.media.ExifInterface;
import com.facebook.imagepipeline.common.RotationOptions;
import expo.modules.camera.CameraViewHelper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.unimodules.core.Promise;

public class ResolveTakenPictureAsyncTask extends AsyncTask<Void, Void, Bundle> {
    private static final String BASE64_KEY = "base64";
    private static final String DATA_KEY = "data";
    private static final int DEFAULT_QUALITY = 1;
    private static final String DIRECTORY_NAME = "Camera";
    private static final String DIRECTORY_NOT_FOUND_MSG = "Documents directory of the app could not be found.";
    private static final String ERROR_TAG = "E_TAKING_PICTURE_FAILED";
    private static final String EXIF_KEY = "exif";
    private static final String EXTENSION = ".jpg";
    private static final String FAST_MODE_KEY = "fastMode";
    private static final String HEIGHT_KEY = "height";
    private static final String ID_KEY = "id";
    private static final String QUALITY_KEY = "quality";
    private static final String SKIP_PROCESSING_KEY = "skipProcessing";
    private static final String UNKNOWN_EXCEPTION_MSG = "An unknown exception has occurred.";
    private static final String UNKNOWN_IO_EXCEPTION_MSG = "An unknown I/O exception has occurred.";
    private static final String URI_KEY = "uri";
    private static final String WIDTH_KEY = "width";
    private Bitmap mBitmap;
    private File mDirectory;
    private byte[] mImageData;
    private Map<String, Object> mOptions;
    private PictureSavedDelegate mPictureSavedDelegate;
    private Promise mPromise;

    private int getImageRotation(int i) {
        if (i == 3) {
            return RotationOptions.ROTATE_180;
        }
        if (i != 6) {
            return i != 8 ? 0 : 270;
        }
        return 90;
    }

    public ResolveTakenPictureAsyncTask(byte[] bArr, Promise promise, Map<String, Object> map, File file, PictureSavedDelegate pictureSavedDelegate) {
        this.mPromise = promise;
        this.mOptions = map;
        this.mImageData = bArr;
        this.mDirectory = file;
        this.mPictureSavedDelegate = pictureSavedDelegate;
    }

    public ResolveTakenPictureAsyncTask(Bitmap bitmap, Promise promise, Map<String, Object> map, File file, PictureSavedDelegate pictureSavedDelegate) {
        this.mPromise = promise;
        this.mBitmap = bitmap;
        this.mOptions = map;
        this.mDirectory = file;
        this.mPictureSavedDelegate = pictureSavedDelegate;
    }

    private int getQuality() {
        if (this.mOptions.get("quality") instanceof Number) {
            return (int) (((Number) this.mOptions.get("quality")).doubleValue() * 100.0d);
        }
        return 100;
    }

    /* access modifiers changed from: protected */
    public Bundle doInBackground(Void... voidArr) {
        if (this.mImageData != null && isOptionEnabled(SKIP_PROCESSING_KEY)) {
            return handleSkipProcessing();
        }
        Bundle bundle = new Bundle();
        if (this.mBitmap == null) {
            byte[] bArr = this.mImageData;
            this.mBitmap = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.mImageData);
        try {
            ExifInterface exifInterface = new ExifInterface((InputStream) byteArrayInputStream);
            int attributeInt = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
            if (attributeInt != 0) {
                this.mBitmap = rotateBitmap(this.mBitmap, getImageRotation(attributeInt));
            }
            if (isOptionEnabled("exif")) {
                bundle.putBundle("exif", CameraViewHelper.getExifData(exifInterface));
            }
            bundle.putInt("width", this.mBitmap.getWidth());
            bundle.putInt("height", this.mBitmap.getHeight());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            this.mBitmap.compress(Bitmap.CompressFormat.JPEG, getQuality(), byteArrayOutputStream);
            String writeStreamToFile = writeStreamToFile(byteArrayOutputStream);
            if (isOptionEnabled("exif")) {
                CameraViewHelper.addExifData(new ExifInterface(writeStreamToFile), exifInterface);
            }
            bundle.putString("uri", Uri.fromFile(new File(writeStreamToFile)).toString());
            if (isOptionEnabled("base64")) {
                bundle.putString("base64", Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2));
            }
            byteArrayOutputStream.close();
            byteArrayInputStream.close();
            return bundle;
        } catch (Resources.NotFoundException e) {
            this.mPromise.reject(ERROR_TAG, DIRECTORY_NOT_FOUND_MSG, e);
            e.printStackTrace();
            byteArrayInputStream.close();
            return null;
        } catch (IOException e2) {
            this.mPromise.reject(ERROR_TAG, UNKNOWN_IO_EXCEPTION_MSG, e2);
            e2.printStackTrace();
            byteArrayInputStream.close();
            return null;
        } catch (Exception e3) {
            this.mPromise.reject(ERROR_TAG, UNKNOWN_EXCEPTION_MSG, e3);
            e3.printStackTrace();
            try {
                byteArrayInputStream.close();
                return null;
            } catch (IOException e4) {
                e4.printStackTrace();
                return null;
            }
        } catch (Throwable th) {
            try {
                byteArrayInputStream.close();
            } catch (IOException e5) {
                e5.printStackTrace();
            }
            throw th;
        }
    }

    private Bundle handleSkipProcessing() {
        Bundle bundle = new Bundle();
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(this.mImageData);
            String writeStreamToFile = writeStreamToFile(byteArrayOutputStream);
            bundle.putString("uri", Uri.fromFile(new File(writeStreamToFile)).toString());
            ExifInterface exifInterface = new ExifInterface(writeStreamToFile);
            bundle.putInt("width", exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, -1));
            bundle.putInt("height", exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, -1));
            if (isOptionEnabled("exif")) {
                bundle.putBundle("exif", CameraViewHelper.getExifData(exifInterface));
            }
            if (isOptionEnabled("base64")) {
                bundle.putString("base64", Base64.encodeToString(this.mImageData, 2));
            }
            return bundle;
        } catch (IOException e) {
            this.mPromise.reject(ERROR_TAG, UNKNOWN_IO_EXCEPTION_MSG, e);
            e.printStackTrace();
            return null;
        } catch (Exception e2) {
            this.mPromise.reject(ERROR_TAG, UNKNOWN_EXCEPTION_MSG, e2);
            e2.printStackTrace();
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(Bundle bundle) {
        super.onPostExecute(bundle);
        if (bundle == null) {
            return;
        }
        if (isOptionEnabled(FAST_MODE_KEY)) {
            Bundle bundle2 = new Bundle();
            bundle2.putInt("id", ((Double) this.mOptions.get("id")).intValue());
            bundle2.putBundle("data", bundle);
            this.mPictureSavedDelegate.onPictureSaved(bundle2);
            return;
        }
        this.mPromise.resolve(bundle);
    }

    /* JADX WARNING: type inference failed for: r0v0 */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.io.FileOutputStream] */
    /* JADX WARNING: type inference failed for: r0v3, types: [java.lang.Throwable] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: type inference failed for: r0v8 */
    /* JADX WARNING: type inference failed for: r0v9 */
    /* JADX WARNING: type inference failed for: r0v10 */
    /* JADX WARNING: type inference failed for: r0v11 */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001e, code lost:
        r5 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001f, code lost:
        r2 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0021, code lost:
        r5 = th;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0021 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:1:0x0001] */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x002c A[SYNTHETIC, Splitter:B:21:0x002c] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0031 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0032  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0037 A[SYNTHETIC, Splitter:B:29:0x0037] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String writeStreamToFile(java.io.ByteArrayOutputStream r5) throws java.lang.Exception {
        /*
            r4 = this;
            r0 = 0
            java.io.File r1 = r4.mDirectory     // Catch:{ IOException -> 0x0023, all -> 0x0021 }
            java.lang.String r2 = "Camera"
            java.lang.String r3 = ".jpg"
            java.lang.String r1 = expo.modules.camera.utils.FileSystemUtils.generateOutputPath(r1, r2, r3)     // Catch:{ IOException -> 0x0023, all -> 0x0021 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x001e, all -> 0x0021 }
            r2.<init>(r1)     // Catch:{ IOException -> 0x001e, all -> 0x0021 }
            r5.writeTo(r2)     // Catch:{ IOException -> 0x001c }
            r2.close()     // Catch:{ IOException -> 0x0017 }
            goto L_0x002f
        L_0x0017:
            r5 = move-exception
            r5.printStackTrace()
            goto L_0x002f
        L_0x001c:
            r5 = move-exception
            goto L_0x0026
        L_0x001e:
            r5 = move-exception
            r2 = r0
            goto L_0x0026
        L_0x0021:
            r5 = move-exception
            goto L_0x0035
        L_0x0023:
            r5 = move-exception
            r1 = r0
            r2 = r1
        L_0x0026:
            r0 = r5
            r0.printStackTrace()     // Catch:{ all -> 0x0033 }
            if (r2 == 0) goto L_0x002f
            r2.close()     // Catch:{ IOException -> 0x0017 }
        L_0x002f:
            if (r0 != 0) goto L_0x0032
            return r1
        L_0x0032:
            throw r0
        L_0x0033:
            r5 = move-exception
            r0 = r2
        L_0x0035:
            if (r0 == 0) goto L_0x003f
            r0.close()     // Catch:{ IOException -> 0x003b }
            goto L_0x003f
        L_0x003b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x003f:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.camera.tasks.ResolveTakenPictureAsyncTask.writeStreamToFile(java.io.ByteArrayOutputStream):java.lang.String");
    }

    private Bitmap rotateBitmap(Bitmap bitmap, int i) {
        Matrix matrix = new Matrix();
        matrix.postRotate((float) i);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private boolean isOptionEnabled(String str) {
        return this.mOptions.get(str) != null && ((Boolean) this.mOptions.get(str)).booleanValue();
    }
}
