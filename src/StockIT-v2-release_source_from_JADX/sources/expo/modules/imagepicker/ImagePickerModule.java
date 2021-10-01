package expo.modules.imagepicker;

import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import com.facebook.react.bridge.BaseJavaModule;
import com.theartofdev.edmodo.cropper.CropImage;
import expo.modules.imagepicker.exporters.CompressionImageExporter;
import expo.modules.imagepicker.exporters.CropImageExporter;
import expo.modules.imagepicker.exporters.ImageExporter;
import expo.modules.imagepicker.exporters.RawImageExporter;
import expo.modules.imagepicker.fileproviders.CacheFileProvider;
import expo.modules.imagepicker.fileproviders.CropFileProvider;
import expo.modules.imagepicker.tasks.ImageResultTask;
import expo.modules.imagepicker.tasks.VideoResultTask;
import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ActivityEventListener;
import org.unimodules.core.interfaces.ActivityProvider;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.LifecycleEventListener;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.imageloader.ImageLoader;
import org.unimodules.interfaces.permissions.Permissions;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B!\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u00020&H\u0007J\u001b\u00103\u001a\b\u0012\u0004\u0012\u000205042\u0006\u00106\u001a\u00020-H\u0002¢\u0006\u0002\u00107J\u0018\u00108\u001a\u0002012\u0006\u00106\u001a\u00020-2\u0006\u00102\u001a\u00020&H\u0007J\b\u00109\u001a\u000205H\u0016J\u0010\u0010:\u001a\u0002012\u0006\u00102\u001a\u00020&H\u0007J:\u0010;\u001a\u0002012\u0006\u00102\u001a\u00020&2\u0006\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020>2\b\u0010@\u001a\u0004\u0018\u00010A2\u0006\u0010B\u001a\u00020$H\u0002J&\u0010C\u001a\u0002012\u0014\u0010D\u001a\u0010\u0012\u0004\u0012\u000205\u0012\u0006\u0012\u0004\u0018\u00010F0E2\u0006\u00102\u001a\u00020&H\u0007J \u0010G\u001a\u0002012\u0006\u00102\u001a\u00020&2\u0006\u0010H\u001a\u00020A2\u0006\u0010B\u001a\u00020$H\u0002J&\u0010I\u001a\u0002012\u0014\u0010D\u001a\u0010\u0012\u0004\u0012\u000205\u0012\u0006\u0012\u0004\u0018\u00010F0E2\u0006\u00102\u001a\u00020&H\u0007J*\u0010J\u001a\u0002012\u0006\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020>2\b\u0010K\u001a\u0004\u0018\u00010AH\u0016J\u0010\u0010L\u001a\u0002012\u0006\u0010M\u001a\u00020NH\u0016J\b\u0010O\u001a\u000201H\u0016J\b\u0010P\u001a\u000201H\u0016J\b\u0010Q\u001a\u000201H\u0016J\u0010\u0010R\u001a\u0002012\u0006\u0010@\u001a\u00020AH\u0016J\u0010\u0010S\u001a\u0002012\u0006\u00102\u001a\u00020&H\u0007J\u0018\u0010T\u001a\u0002012\u0006\u00106\u001a\u00020-2\u0006\u00102\u001a\u00020&H\u0007J\u0018\u0010U\u001a\u00020-2\u0006\u0010<\u001a\u00020\r2\u0006\u0010=\u001a\u00020>H\u0002J(\u0010V\u001a\u0002012\u0006\u0010@\u001a\u00020A2\u0006\u0010=\u001a\u00020>2\u0006\u00102\u001a\u00020&2\u0006\u0010B\u001a\u00020$H\u0002J0\u0010W\u001a\u0002012\u0006\u00102\u001a\u00020&2\u0006\u0010X\u001a\u00020\u00182\u0006\u0010Y\u001a\u0002052\u0006\u0010Z\u001a\u00020-2\u0006\u0010B\u001a\u00020$H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX.¢\u0006\u0002\n\u0000R\u0016\u0010\u000e\u001a\u0004\u0018\u00010\r8BX\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u001b\u0010\u0011\u001a\u00020\u00128BX\u0002¢\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0013\u0010\u0014R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000R\u001b\u0010\u0019\u001a\u00020\u001a8BX\u0002¢\u0006\f\n\u0004\b\u001d\u0010\u0016\u001a\u0004\b\u001b\u0010\u001cR\u001b\u0010\u001e\u001a\u00020\u001f8BX\u0002¢\u0006\f\n\u0004\b\"\u0010\u0016\u001a\u0004\b \u0010!R\u0010\u0010#\u001a\u0004\u0018\u00010$X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010%\u001a\u0004\u0018\u00010&X\u000e¢\u0006\u0002\n\u0000R\u001b\u0010'\u001a\u00020(8BX\u0002¢\u0006\f\n\u0004\b+\u0010\u0016\u001a\u0004\b)\u0010*R\u000e\u0010,\u001a\u00020-X\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/R\u000e\u0010\b\u001a\u00020\tX\u0004¢\u0006\u0002\n\u0000¨\u0006["}, mo40253d2 = {"Lexpo/modules/imagepicker/ImagePickerModule;", "Lorg/unimodules/core/ExportedModule;", "Lorg/unimodules/core/interfaces/ActivityEventListener;", "Lorg/unimodules/core/interfaces/LifecycleEventListener;", "mContext", "Landroid/content/Context;", "moduleRegistryPropertyDelegate", "Lexpo/modules/imagepicker/ModuleRegistryPropertyDelegate;", "pickerResultStore", "Lexpo/modules/imagepicker/PickerResultsStore;", "(Landroid/content/Context;Lexpo/modules/imagepicker/ModuleRegistryPropertyDelegate;Lexpo/modules/imagepicker/PickerResultsStore;)V", "_experienceActivity", "Ljava/lang/ref/WeakReference;", "Landroid/app/Activity;", "experienceActivity", "getExperienceActivity", "()Landroid/app/Activity;", "mActivityProvider", "Lorg/unimodules/core/interfaces/ActivityProvider;", "getMActivityProvider", "()Lorg/unimodules/core/interfaces/ActivityProvider;", "mActivityProvider$delegate", "Lkotlin/Lazy;", "mCameraCaptureURI", "Landroid/net/Uri;", "mImageLoader", "Lorg/unimodules/interfaces/imageloader/ImageLoader;", "getMImageLoader", "()Lorg/unimodules/interfaces/imageloader/ImageLoader;", "mImageLoader$delegate", "mPermissions", "Lorg/unimodules/interfaces/permissions/Permissions;", "getMPermissions", "()Lorg/unimodules/interfaces/permissions/Permissions;", "mPermissions$delegate", "mPickerOptions", "Lexpo/modules/imagepicker/ImagePickerOptions;", "mPromise", "Lorg/unimodules/core/Promise;", "mUIManager", "Lorg/unimodules/core/interfaces/services/UIManager;", "getMUIManager", "()Lorg/unimodules/core/interfaces/services/UIManager;", "mUIManager$delegate", "mWasDestroyed", "", "getModuleRegistryPropertyDelegate", "()Lexpo/modules/imagepicker/ModuleRegistryPropertyDelegate;", "getCameraPermissionsAsync", "", "promise", "getMediaLibraryPermissions", "", "", "writeOnly", "(Z)[Ljava/lang/String;", "getMediaLibraryPermissionsAsync", "getName", "getPendingResultAsync", "handleOnActivityResult", "activity", "requestCode", "", "resultCode", "intent", "Landroid/content/Intent;", "pickerOptions", "launchCameraAsync", "options", "", "", "launchCameraWithPermissionsGranted", "cameraIntent", "launchImageLibraryAsync", "onActivityResult", "data", "onCreate", "moduleRegistry", "Lorg/unimodules/core/ModuleRegistry;", "onHostDestroy", "onHostPause", "onHostResume", "onNewIntent", "requestCameraPermissionsAsync", "requestMediaLibraryPermissionsAsync", "shouldHandleOnActivityResult", "startActivityOnResult", "startCropIntent", "uri", "type", "needGenerateFile", "expo-image-picker_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: ImagePickerModule.kt */
public final class ImagePickerModule extends ExportedModule implements ActivityEventListener, LifecycleEventListener {
    /* access modifiers changed from: private */
    public WeakReference<Activity> _experienceActivity;
    private final Lazy mActivityProvider$delegate;
    private Uri mCameraCaptureURI;
    private final Context mContext;
    private final Lazy mImageLoader$delegate;
    private final Lazy mPermissions$delegate;
    private ImagePickerOptions mPickerOptions;
    private Promise mPromise;
    private final Lazy mUIManager$delegate;
    private boolean mWasDestroyed;
    private final ModuleRegistryPropertyDelegate moduleRegistryPropertyDelegate;
    private final PickerResultsStore pickerResultStore;

    @Metadata(mo40251bv = {1, 0, 3}, mo40254k = 3, mo40255mv = {1, 4, 2})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[MediaTypes.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[MediaTypes.IMAGES.ordinal()] = 1;
            $EnumSwitchMapping$0[MediaTypes.VIDEOS.ordinal()] = 2;
            $EnumSwitchMapping$0[MediaTypes.ALL.ordinal()] = 3;
        }
    }

    private final ActivityProvider getMActivityProvider() {
        return (ActivityProvider) this.mActivityProvider$delegate.getValue();
    }

    private final ImageLoader getMImageLoader() {
        return (ImageLoader) this.mImageLoader$delegate.getValue();
    }

    private final Permissions getMPermissions() {
        return (Permissions) this.mPermissions$delegate.getValue();
    }

    private final UIManager getMUIManager() {
        return (UIManager) this.mUIManager$delegate.getValue();
    }

    public String getName() {
        return ImagePickerConstants.TAG;
    }

    public void onHostPause() {
    }

    public void onHostResume() {
    }

    public void onNewIntent(Intent intent) {
        Intrinsics.checkNotNullParameter(intent, "intent");
    }

    public static final /* synthetic */ WeakReference access$get_experienceActivity$p(ImagePickerModule imagePickerModule) {
        WeakReference<Activity> weakReference = imagePickerModule._experienceActivity;
        if (weakReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_experienceActivity");
        }
        return weakReference;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ ImagePickerModule(Context context, ModuleRegistryPropertyDelegate moduleRegistryPropertyDelegate2, PickerResultsStore pickerResultsStore, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? new ModuleRegistryPropertyDelegate() : moduleRegistryPropertyDelegate2, (i & 4) != 0 ? new PickerResultsStore(context) : pickerResultsStore);
    }

    public final ModuleRegistryPropertyDelegate getModuleRegistryPropertyDelegate() {
        return this.moduleRegistryPropertyDelegate;
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ImagePickerModule(Context context, ModuleRegistryPropertyDelegate moduleRegistryPropertyDelegate2, PickerResultsStore pickerResultsStore) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "mContext");
        Intrinsics.checkNotNullParameter(moduleRegistryPropertyDelegate2, "moduleRegistryPropertyDelegate");
        Intrinsics.checkNotNullParameter(pickerResultsStore, "pickerResultStore");
        this.mContext = context;
        this.moduleRegistryPropertyDelegate = moduleRegistryPropertyDelegate2;
        this.pickerResultStore = pickerResultsStore;
        this.mImageLoader$delegate = LazyKt.lazy(new ImagePickerModule$$special$$inlined$moduleRegistry$1(getModuleRegistryPropertyDelegate()));
        this.mUIManager$delegate = LazyKt.lazy(new ImagePickerModule$$special$$inlined$moduleRegistry$2(getModuleRegistryPropertyDelegate()));
        this.mPermissions$delegate = LazyKt.lazy(new ImagePickerModule$$special$$inlined$moduleRegistry$3(getModuleRegistryPropertyDelegate()));
        this.mActivityProvider$delegate = LazyKt.lazy(new ImagePickerModule$$special$$inlined$moduleRegistry$4(getModuleRegistryPropertyDelegate()));
    }

    private final Activity getExperienceActivity() {
        if (this._experienceActivity == null) {
            this._experienceActivity = new WeakReference<>(getMActivityProvider().getCurrentActivity());
        }
        WeakReference<Activity> weakReference = this._experienceActivity;
        if (weakReference == null) {
            Intrinsics.throwUninitializedPropertyAccessException("_experienceActivity");
        }
        return (Activity) weakReference.get();
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        Intrinsics.checkNotNullParameter(moduleRegistry, "moduleRegistry");
        this.moduleRegistryPropertyDelegate.onCreate(moduleRegistry);
        getMUIManager().registerLifecycleEventListener(this);
    }

    @ExpoMethod
    public final void requestMediaLibraryPermissionsAsync(boolean z, Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        Permissions mPermissions = getMPermissions();
        String[] mediaLibraryPermissions = getMediaLibraryPermissions(z);
        Permissions.CC.askForPermissionsWithPermissionsManager(mPermissions, promise, (String[]) Arrays.copyOf(mediaLibraryPermissions, mediaLibraryPermissions.length));
    }

    @ExpoMethod
    public final void getMediaLibraryPermissionsAsync(boolean z, Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        Permissions mPermissions = getMPermissions();
        String[] mediaLibraryPermissions = getMediaLibraryPermissions(z);
        Permissions.CC.getPermissionsWithPermissionsManager(mPermissions, promise, (String[]) Arrays.copyOf(mediaLibraryPermissions, mediaLibraryPermissions.length));
    }

    @ExpoMethod
    public final void requestCameraPermissionsAsync(Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        Permissions.CC.askForPermissionsWithPermissionsManager(getMPermissions(), promise, "android.permission.CAMERA");
    }

    @ExpoMethod
    public final void getCameraPermissionsAsync(Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        Permissions.CC.getPermissionsWithPermissionsManager(getMPermissions(), promise, "android.permission.CAMERA");
    }

    @ExpoMethod
    public final void getPendingResultAsync(Promise promise) {
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        promise.resolve(this.pickerResultStore.getAllPendingResults());
    }

    @ExpoMethod
    public final void launchCameraAsync(Map<String, ? extends Object> map, Promise promise) {
        Intrinsics.checkNotNullParameter(map, "options");
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        ImagePickerOptions optionsFromMap = ImagePickerOptions.Companion.optionsFromMap(map, promise);
        if (optionsFromMap != null) {
            Activity experienceActivity = getExperienceActivity();
            if (experienceActivity == null) {
                promise.reject(ImagePickerConstants.ERR_MISSING_ACTIVITY, ImagePickerConstants.MISSING_ACTIVITY_MESSAGE);
                return;
            }
            Intent intent = new Intent(optionsFromMap.getMediaTypes() == MediaTypes.VIDEOS ? "android.media.action.VIDEO_CAPTURE" : "android.media.action.IMAGE_CAPTURE");
            Application application = experienceActivity.getApplication();
            Intrinsics.checkNotNullExpressionValue(application, "activity.application");
            if (intent.resolveActivity(application.getPackageManager()) == null) {
                promise.reject(new IllegalStateException("Error resolving activity"));
            } else {
                getMPermissions().askForPermissions(new ImagePickerModule$launchCameraAsync$permissionsResponseHandler$1(this, promise, intent, optionsFromMap), "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA");
            }
        }
    }

    @ExpoMethod
    public final void launchImageLibraryAsync(Map<String, ? extends Object> map, Promise promise) {
        Intrinsics.checkNotNullParameter(map, "options");
        Intrinsics.checkNotNullParameter(promise, BaseJavaModule.METHOD_TYPE_PROMISE);
        ImagePickerOptions optionsFromMap = ImagePickerOptions.Companion.optionsFromMap(map, promise);
        if (optionsFromMap != null) {
            Intent intent = new Intent();
            int i = WhenMappings.$EnumSwitchMapping$0[optionsFromMap.getMediaTypes().ordinal()];
            if (i == 1) {
                intent.setType("image/*");
            } else if (i == 2) {
                intent.setType("video/*");
            } else if (i == 3) {
                intent.setType("*/*");
                Intrinsics.checkNotNullExpressionValue(intent.putExtra("android.intent.extra.MIME_TYPES", new String[]{"image/*", "video/*"}), "putExtra(Intent.EXTRA_MI…Of(\"image/*\", \"video/*\"))");
            }
            intent.setAction("android.intent.action.GET_CONTENT");
            startActivityOnResult(intent, 2, promise, optionsFromMap);
        }
    }

    private final String[] getMediaLibraryPermissions(boolean z) {
        if (z) {
            return new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"};
        }
        return new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"};
    }

    /* access modifiers changed from: private */
    public final void launchCameraWithPermissionsGranted(Promise promise, Intent intent, ImagePickerOptions imagePickerOptions) {
        File cacheDir = this.mContext.getCacheDir();
        Intrinsics.checkNotNullExpressionValue(cacheDir, "mContext.cacheDir");
        File createOutputFile = ImagePickerUtilsKt.createOutputFile(cacheDir, imagePickerOptions.getMediaTypes() == MediaTypes.VIDEOS ? ".mp4" : ".jpg");
        if (createOutputFile == null) {
            promise.reject(new IOException("Could not create image file."));
            return;
        }
        this.mCameraCaptureURI = ImagePickerUtilsKt.uriFromFile(createOutputFile);
        Activity experienceActivity = getExperienceActivity();
        if (experienceActivity == null) {
            promise.reject(ImagePickerConstants.ERR_MISSING_ACTIVITY, ImagePickerConstants.MISSING_ACTIVITY_MESSAGE);
            return;
        }
        this.mPromise = promise;
        this.mPickerOptions = imagePickerOptions;
        if (imagePickerOptions.getVideoMaxDuration() > 0) {
            intent.putExtra("android.intent.extra.durationLimit", imagePickerOptions.getVideoMaxDuration());
        }
        Application application = experienceActivity.getApplication();
        Intrinsics.checkNotNullExpressionValue(application, "activity.application");
        intent.putExtra("output", ImagePickerUtilsKt.contentUriFromFile(createOutputFile, application));
        startActivityOnResult(intent, 1, promise, imagePickerOptions);
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0046 A[SYNTHETIC, Splitter:B:14:0x0046] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0067  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0072  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void startCropIntent(org.unimodules.core.Promise r8, android.net.Uri r9, java.lang.String r10, boolean r11, expo.modules.imagepicker.ImagePickerOptions r12) {
        /*
            r7 = this;
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.JPEG
            java.lang.CharSequence r10 = (java.lang.CharSequence) r10
            java.lang.String r1 = "png"
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            r2 = 0
            r3 = 2
            r4 = 0
            boolean r1 = kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r10, (java.lang.CharSequence) r1, (boolean) r2, (int) r3, (java.lang.Object) r4)
            java.lang.String r5 = ".jpg"
            java.lang.String r6 = ".png"
            if (r1 == 0) goto L_0x0019
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.PNG
        L_0x0017:
            r5 = r6
            goto L_0x0044
        L_0x0019:
            java.lang.String r1 = "gif"
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            boolean r1 = kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r10, (java.lang.CharSequence) r1, (boolean) r2, (int) r3, (java.lang.Object) r4)
            if (r1 == 0) goto L_0x0026
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.PNG
            goto L_0x0017
        L_0x0026:
            java.lang.String r1 = "bmp"
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            boolean r1 = kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r10, (java.lang.CharSequence) r1, (boolean) r2, (int) r3, (java.lang.Object) r4)
            if (r1 == 0) goto L_0x0033
            android.graphics.Bitmap$CompressFormat r0 = android.graphics.Bitmap.CompressFormat.PNG
            goto L_0x0017
        L_0x0033:
            java.lang.String r1 = "jpeg"
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            boolean r10 = kotlin.text.StringsKt.contains$default((java.lang.CharSequence) r10, (java.lang.CharSequence) r1, (boolean) r2, (int) r3, (java.lang.Object) r4)
            if (r10 != 0) goto L_0x0044
            java.lang.String r10 = "ExponentImagePicker"
            java.lang.String r1 = "Image type not supported. Falling back to JPEG instead."
            android.util.Log.w(r10, r1)
        L_0x0044:
            if (r11 == 0) goto L_0x0067
            android.content.Context r10 = r7.mContext     // Catch:{ IOException -> 0x005c }
            java.io.File r10 = r10.getCacheDir()     // Catch:{ IOException -> 0x005c }
            java.lang.String r11 = "ImagePicker"
            java.lang.String r10 = org.unimodules.core.utilities.FileUtilities.generateOutputPath(r10, r11, r5)     // Catch:{ IOException -> 0x005c }
            java.lang.String r11 = "generateOutputPath(mCont…ACHE_DIR_NAME, extension)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r10, r11)     // Catch:{ IOException -> 0x005c }
            android.net.Uri r10 = expo.modules.imagepicker.ImagePickerUtilsKt.uriFromFilePath(r10)     // Catch:{ IOException -> 0x005c }
            goto L_0x0068
        L_0x005c:
            r9 = move-exception
            java.lang.Throwable r9 = (java.lang.Throwable) r9
            java.lang.String r10 = "ERR_CAN_NOT_OPEN_CROP"
            java.lang.String r11 = "Can not open the crop tool."
            r8.reject(r10, r11, r9)
            return
        L_0x0067:
            r10 = r9
        L_0x0068:
            com.theartofdev.edmodo.cropper.CropImage$ActivityBuilder r9 = com.theartofdev.edmodo.cropper.CropImage.activity(r9)
            java.util.List r11 = r12.getForceAspect()
            if (r11 == 0) goto L_0x00a4
            java.lang.Object r1 = r11.get(r2)
            r2 = 1
            java.lang.Object r11 = r11.get(r2)
            java.lang.String r3 = "null cannot be cast to non-null type kotlin.Number"
            if (r1 == 0) goto L_0x009e
            java.lang.Number r1 = (java.lang.Number) r1
            int r1 = r1.intValue()
            if (r11 == 0) goto L_0x0098
            java.lang.Number r11 = (java.lang.Number) r11
            int r11 = r11.intValue()
            r9.setAspectRatio(r1, r11)
            r9.setFixAspectRatio(r2)
            r11 = 0
            r9.setInitialCropWindowPaddingRatio(r11)
            goto L_0x00a4
        L_0x0098:
            java.lang.NullPointerException r8 = new java.lang.NullPointerException
            r8.<init>(r3)
            throw r8
        L_0x009e:
            java.lang.NullPointerException r8 = new java.lang.NullPointerException
            r8.<init>(r3)
            throw r8
        L_0x00a4:
            r9.setOutputUri(r10)
            r9.setOutputCompressFormat(r0)
            int r10 = r12.getQuality()
            r9.setOutputCompressQuality(r10)
            android.content.Context r10 = r7.getContext()
            android.content.Intent r9 = r9.getIntent(r10)
            java.lang.String r10 = "cropImageBuilder.getIntent(context)"
            kotlin.jvm.internal.Intrinsics.checkNotNullExpressionValue(r9, r10)
            r10 = 203(0xcb, float:2.84E-43)
            r7.startActivityOnResult(r9, r10, r8, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.imagepicker.ImagePickerModule.startCropIntent(org.unimodules.core.Promise, android.net.Uri, java.lang.String, boolean, expo.modules.imagepicker.ImagePickerOptions):void");
    }

    public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
        ImagePickerOptions imagePickerOptions;
        Promise promise;
        ImagePickerOptions imagePickerOptions2;
        PendingPromise pendingPromise;
        Intrinsics.checkNotNullParameter(activity, "activity");
        if (shouldHandleOnActivityResult(activity, i)) {
            getMUIManager().unregisterActivityEventListener(this);
            ImagePickerOptions imagePickerOptions3 = this.mPickerOptions;
            Intrinsics.checkNotNull(imagePickerOptions3);
            if (!this.mWasDestroyed || (this.mPromise instanceof PendingPromise)) {
                promise = this.mPromise;
                Intrinsics.checkNotNull(promise);
                imagePickerOptions = imagePickerOptions3;
            } else {
                if (imagePickerOptions3.isBase64()) {
                    imagePickerOptions2 = new ImagePickerOptions(imagePickerOptions3.getQuality(), imagePickerOptions3.isAllowsEditing(), imagePickerOptions3.getForceAspect(), false, imagePickerOptions3.getMediaTypes(), imagePickerOptions3.isExif(), imagePickerOptions3.getVideoMaxDuration());
                    pendingPromise = new PendingPromise(this.pickerResultStore, true);
                } else {
                    imagePickerOptions2 = imagePickerOptions3;
                    pendingPromise = new PendingPromise(this.pickerResultStore, false, 2, (DefaultConstructorMarker) null);
                }
                imagePickerOptions = imagePickerOptions2;
                promise = pendingPromise;
            }
            this.mPromise = null;
            this.mPickerOptions = null;
            handleOnActivityResult(promise, activity, i, i2, intent, imagePickerOptions);
        }
    }

    private final void startActivityOnResult(Intent intent, int i, Promise promise, ImagePickerOptions imagePickerOptions) {
        Activity experienceActivity = getExperienceActivity();
        if (experienceActivity == null) {
            promise.reject(ImagePickerConstants.ERR_MISSING_ACTIVITY, ImagePickerConstants.MISSING_ACTIVITY_MESSAGE);
            return;
        }
        getMUIManager().registerActivityEventListener(this);
        this.mPromise = promise;
        this.mPickerOptions = imagePickerOptions;
        experienceActivity.startActivityForResult(intent, i);
    }

    private final boolean shouldHandleOnActivityResult(Activity activity, int i) {
        return (getExperienceActivity() == null || this.mPromise == null || this.mPickerOptions == null || (activity != getExperienceActivity() && (!this.mWasDestroyed || i != 203))) ? false : true;
    }

    private final void handleOnActivityResult(Promise promise, Activity activity, int i, int i2, Intent intent, ImagePickerOptions imagePickerOptions) {
        ImageExporter imageExporter;
        Promise promise2 = promise;
        int i3 = i;
        if (i2 != -1) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("cancelled", true);
            Unit unit = Unit.INSTANCE;
            promise2.resolve(bundle);
            return;
        }
        Application application = activity.getApplication();
        Intrinsics.checkNotNullExpressionValue(application, "activity.application");
        ContentResolver contentResolver = application.getContentResolver();
        boolean z = false;
        if (i3 == 203) {
            CropImage.ActivityResult activityResult = CropImage.getActivityResult(intent);
            Intrinsics.checkNotNullExpressionValue(activityResult, "result");
            int rotation = activityResult.getRotation();
            Rect cropRect = activityResult.getCropRect();
            Intrinsics.checkNotNullExpressionValue(cropRect, "result.cropRect");
            CropImageExporter cropImageExporter = new CropImageExporter(rotation, cropRect, imagePickerOptions.isBase64());
            Uri uri = activityResult.getUri();
            Intrinsics.checkNotNullExpressionValue(uri, "result.uri");
            Intrinsics.checkNotNullExpressionValue(contentResolver, "contentResolver");
            Uri uri2 = activityResult.getUri();
            Intrinsics.checkNotNullExpressionValue(uri2, "result.uri");
            new ImageResultTask(promise, uri, contentResolver, new CropFileProvider(uri2), imagePickerOptions.isExif(), cropImageExporter).execute(new Void[0]);
            return;
        }
        Uri data = i3 == 1 ? this.mCameraCaptureURI : intent != null ? intent.getData() : null;
        if (data == null) {
            promise2.reject(ImagePickerConstants.ERR_MISSING_URL, ImagePickerConstants.MISSING_URL_MESSAGE);
            return;
        }
        Intrinsics.checkNotNullExpressionValue(contentResolver, "contentResolver");
        String type = ImagePickerUtilsKt.getType(contentResolver, data);
        if (type == null) {
            promise2.reject(ImagePickerConstants.ERR_CAN_NOT_DEDUCE_TYPE, ImagePickerConstants.CAN_NOT_DEDUCE_TYPE_MESSAGE);
        } else if (!StringsKt.contains$default((CharSequence) type, (CharSequence) "image", false, 2, (Object) null)) {
            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(this.mContext, data);
                File cacheDir = this.mContext.getCacheDir();
                Intrinsics.checkNotNullExpressionValue(cacheDir, "mContext.cacheDir");
                new VideoResultTask(promise, data, contentResolver, new CacheFileProvider(cacheDir, ".mp4"), mediaMetadataRetriever).execute(new Void[0]);
            } catch (RuntimeException e) {
                e.printStackTrace();
                promise2.reject(ImagePickerConstants.ERR_CAN_NOT_EXTRACT_METADATA, ImagePickerConstants.CAN_NOT_EXTRACT_METADATA_MESSAGE, e);
            }
        } else if (imagePickerOptions.isAllowsEditing()) {
            if (i3 != 1) {
                z = true;
            }
            startCropIntent(promise, data, type, z, imagePickerOptions);
        } else {
            if (imagePickerOptions.getQuality() == 100) {
                imageExporter = new RawImageExporter(contentResolver, imagePickerOptions.isBase64());
            } else {
                imageExporter = new CompressionImageExporter(getMImageLoader(), imagePickerOptions.getQuality(), imagePickerOptions.isBase64());
            }
            File cacheDir2 = this.mContext.getCacheDir();
            Intrinsics.checkNotNullExpressionValue(cacheDir2, "mContext.cacheDir");
            new ImageResultTask(promise, data, contentResolver, new CacheFileProvider(cacheDir2, ImagePickerUtilsKt.deduceExtension(type)), imagePickerOptions.isExif(), imageExporter).execute(new Void[0]);
        }
    }

    public void onHostDestroy() {
        this.mWasDestroyed = true;
        getMUIManager().unregisterLifecycleEventListener(this);
    }
}
