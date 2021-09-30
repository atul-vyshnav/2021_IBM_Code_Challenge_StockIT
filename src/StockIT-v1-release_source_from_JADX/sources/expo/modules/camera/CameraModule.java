package expo.modules.camera;

import android.content.Context;
import android.os.Build;
import androidx.exifinterface.media.ExifInterface;
import com.facebook.react.uimanager.ViewProps;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.Size;
import expo.modules.camera.tasks.PictureSavedDelegate;
import expo.modules.camera.tasks.ResolveTakenPictureAsyncTask;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.permissions.Permissions;

public class CameraModule extends ExportedModule {
    private static final String ERROR_TAG = "E_CAMERA";
    private static final String TAG = "ExponentCameraModule";
    static final int VIDEO_1080P = 1;
    static final int VIDEO_2160P = 0;
    static final int VIDEO_480P = 3;
    static final int VIDEO_4x3 = 4;
    static final int VIDEO_720P = 2;
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return TAG;
    }

    public CameraModule(Context context) {
        super(context);
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("Type", getTypeConstants());
                put("FlashMode", getFlashModeConstants());
                put("AutoFocus", getAutoFocusConstants());
                put(ExifInterface.TAG_WHITE_BALANCE, getWhiteBalanceConstants());
                put("VideoQuality", getVideoQualityConstants());
                put("FaceDetection", Collections.unmodifiableMap(new HashMap()));
            }

            private Map<String, Object> getTypeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("front", 1);
                        put("back", 0);
                    }
                });
            }

            private Map<String, Object> getFlashModeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("off", 0);
                        put(ViewProps.f109ON, 1);
                        put("auto", 3);
                        put("torch", 2);
                    }
                });
            }

            private Map<String, Object> getAutoFocusConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put(ViewProps.f109ON, true);
                        put("off", false);
                    }
                });
            }

            private Map<String, Object> getWhiteBalanceConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("auto", 0);
                        put("cloudy", 1);
                        put("sunny", 2);
                        put("shadow", 3);
                        put("fluorescent", 4);
                        put("incandescent", 5);
                    }
                });
            }

            private Map<String, Object> getVideoQualityConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("2160p", 0);
                        put("1080p", 1);
                        put("720p", 2);
                        put("480p", 3);
                        put("4:3", 4);
                    }
                });
            }
        });
    }

    @ExpoMethod
    public void pausePreview(int i, final Promise promise) {
        addUIBlock(i, new UIManager.UIBlock<ExpoCameraView>() {
            public void resolve(ExpoCameraView expoCameraView) {
                try {
                    if (expoCameraView.isCameraOpened()) {
                        expoCameraView.pausePreview();
                    }
                } catch (Exception e) {
                    Promise promise = promise;
                    promise.reject(CameraModule.ERROR_TAG, "pausePreview -- exception occurred -- " + e.getMessage(), e);
                }
            }

            public void reject(Throwable th) {
                promise.reject(CameraModule.ERROR_TAG, th);
            }
        });
    }

    @ExpoMethod
    public void resumePreview(int i, final Promise promise) {
        addUIBlock(i, new UIManager.UIBlock<ExpoCameraView>() {
            public void resolve(ExpoCameraView expoCameraView) {
                try {
                    if (expoCameraView.isCameraOpened()) {
                        expoCameraView.resumePreview();
                    }
                } catch (Exception e) {
                    Promise promise = promise;
                    promise.reject(CameraModule.ERROR_TAG, "resumePreview -- exception occurred -- " + e.getMessage(), e);
                }
            }

            public void reject(Throwable th) {
                promise.reject(CameraModule.ERROR_TAG, th);
            }
        });
    }

    @ExpoMethod
    public void takePicture(final Map<String, Object> map, int i, final Promise promise) {
        final File cacheDir = getContext().getCacheDir();
        addUIBlock(i, new UIManager.UIBlock<ExpoCameraView>() {
            public void resolve(ExpoCameraView expoCameraView) {
                if (Build.FINGERPRINT.contains("generic")) {
                    new ResolveTakenPictureAsyncTask(CameraViewHelper.generateSimulatorPhoto(expoCameraView.getWidth(), expoCameraView.getHeight()), promise, (Map<String, Object>) map, cacheDir, (PictureSavedDelegate) expoCameraView).execute(new Void[0]);
                } else if (expoCameraView.isCameraOpened()) {
                    expoCameraView.takePicture(map, promise, cacheDir);
                } else {
                    promise.reject("E_CAMERA_UNAVAILABLE", "Camera is not running");
                }
            }

            public void reject(Throwable th) {
                promise.reject(CameraModule.ERROR_TAG, th);
            }
        });
    }

    @ExpoMethod
    public void record(final Map<String, Object> map, int i, final Promise promise) {
        Permissions permissions = (Permissions) this.mModuleRegistry.getModule(Permissions.class);
        if (permissions == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else if (permissions.hasGrantedPermissions("android.permission.RECORD_AUDIO")) {
            final File cacheDir = getContext().getCacheDir();
            addUIBlock(i, new UIManager.UIBlock<ExpoCameraView>() {
                public void resolve(ExpoCameraView expoCameraView) {
                    if (expoCameraView.isCameraOpened()) {
                        expoCameraView.record(map, promise, cacheDir);
                    } else {
                        promise.reject("E_CAMERA_UNAVAILABLE", "Camera is not running");
                    }
                }

                public void reject(Throwable th) {
                    promise.reject(CameraModule.ERROR_TAG, th);
                }
            });
        } else {
            promise.reject(new SecurityException("User rejected audio permissions"));
        }
    }

    @ExpoMethod
    public void stopRecording(int i, final Promise promise) {
        addUIBlock(i, new UIManager.UIBlock<ExpoCameraView>() {
            public void resolve(ExpoCameraView expoCameraView) {
                if (expoCameraView.isCameraOpened()) {
                    expoCameraView.stopRecording();
                    promise.resolve(true);
                    return;
                }
                promise.reject(CameraModule.ERROR_TAG, "Camera is not open");
            }

            public void reject(Throwable th) {
                promise.reject(CameraModule.ERROR_TAG, th);
            }
        });
    }

    @ExpoMethod
    public void getSupportedRatios(int i, final Promise promise) {
        addUIBlock(i, new UIManager.UIBlock<ExpoCameraView>() {
            public void resolve(ExpoCameraView expoCameraView) {
                if (expoCameraView.isCameraOpened()) {
                    Set<AspectRatio> supportedAspectRatios = expoCameraView.getSupportedAspectRatios();
                    ArrayList arrayList = new ArrayList(supportedAspectRatios.size());
                    for (AspectRatio aspectRatio : supportedAspectRatios) {
                        arrayList.add(aspectRatio.toString());
                    }
                    promise.resolve(arrayList);
                    return;
                }
                promise.reject(CameraModule.ERROR_TAG, "Camera is not running");
            }

            public void reject(Throwable th) {
                promise.reject(CameraModule.ERROR_TAG, th);
            }
        });
    }

    @ExpoMethod
    public void getAvailablePictureSizes(final String str, int i, final Promise promise) {
        addUIBlock(i, new UIManager.UIBlock<ExpoCameraView>() {
            public void resolve(ExpoCameraView expoCameraView) {
                if (expoCameraView.isCameraOpened()) {
                    try {
                        SortedSet<Size> availablePictureSizes = expoCameraView.getAvailablePictureSizes(AspectRatio.parse(str));
                        ArrayList arrayList = new ArrayList(availablePictureSizes.size());
                        for (Size size : availablePictureSizes) {
                            arrayList.add(size.toString());
                        }
                        promise.resolve(arrayList);
                    } catch (Exception e) {
                        Promise promise = promise;
                        promise.reject(CameraModule.ERROR_TAG, "getAvailablePictureSizes -- unexpected error -- " + e.getMessage(), e);
                    }
                } else {
                    promise.reject(CameraModule.ERROR_TAG, "Camera is not running");
                }
            }

            public void reject(Throwable th) {
                promise.reject(CameraModule.ERROR_TAG, th);
            }
        });
    }

    private void addUIBlock(int i, UIManager.UIBlock<ExpoCameraView> uIBlock) {
        UIManager uIManager = (UIManager) this.mModuleRegistry.getModule(UIManager.class);
        if (uIManager == null) {
            uIBlock.reject(new IllegalStateException("Implementation of " + UIManager.class.getName() + " is null. Are you sure you've included a proper Expo adapter for your platform?"));
            return;
        }
        uIManager.addUIBlock(i, uIBlock, ExpoCameraView.class);
    }

    @ExpoMethod
    public void requestPermissionsAsync(Promise promise) {
        Permissions permissions = (Permissions) this.mModuleRegistry.getModule(Permissions.class);
        if (permissions == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else {
            permissions.askForPermissionsWithPromise(promise, "android.permission.CAMERA");
        }
    }

    @ExpoMethod
    public void getPermissionsAsync(Promise promise) {
        Permissions permissions = (Permissions) this.mModuleRegistry.getModule(Permissions.class);
        if (permissions == null) {
            promise.reject("E_NO_PERMISSIONS", "Permissions module is null. Are you sure all the installed Expo modules are properly linked?");
        } else {
            permissions.getPermissionsWithPromise(promise, "android.permission.CAMERA");
        }
    }
}
