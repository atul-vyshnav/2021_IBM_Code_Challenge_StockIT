package expo.modules.camera;

import android.content.Context;
import com.google.android.cameraview.AspectRatio;
import com.google.android.cameraview.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.ViewManager;
import org.unimodules.core.interfaces.ExpoProp;
import org.unimodules.core.interfaces.services.UIManager;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerSettings;

public class CameraViewManager extends ViewManager<ExpoCameraView> {
    private static final String REACT_CLASS = "ExponentCamera";
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return REACT_CLASS;
    }

    public enum Events {
        EVENT_CAMERA_READY("onCameraReady"),
        EVENT_ON_MOUNT_ERROR("onMountError"),
        EVENT_ON_BAR_CODE_SCANNED("onBarCodeScanned"),
        EVENT_ON_FACES_DETECTED("onFacesDetected"),
        EVENT_ON_FACE_DETECTION_ERROR("onFaceDetectionError"),
        EVENT_ON_PICTURE_SAVED("onPictureSaved");
        
        private final String mName;

        private Events(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    public void onDropViewInstance(ExpoCameraView expoCameraView) {
        ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).unregisterLifecycleEventListener(expoCameraView);
        expoCameraView.stop();
    }

    public ViewManager.ViewManagerType getViewManagerType() {
        return ViewManager.ViewManagerType.GROUP;
    }

    public ExpoCameraView createViewInstance(Context context) {
        return new ExpoCameraView(context, this.mModuleRegistry);
    }

    public List<String> getExportedEventNames() {
        ArrayList arrayList = new ArrayList(Events.values().length);
        for (Events events : Events.values()) {
            arrayList.add(events.toString());
        }
        return arrayList;
    }

    @ExpoProp(name = "type")
    public void setType(ExpoCameraView expoCameraView, int i) {
        expoCameraView.setFacing(i);
    }

    @ExpoProp(name = "ratio")
    public void setRatio(ExpoCameraView expoCameraView, String str) {
        expoCameraView.setAspectRatio(AspectRatio.parse(str));
    }

    @ExpoProp(name = "flashMode")
    public void setFlashMode(ExpoCameraView expoCameraView, int i) {
        expoCameraView.setFlash(i);
    }

    @ExpoProp(name = "autoFocus")
    public void setAutoFocus(ExpoCameraView expoCameraView, boolean z) {
        expoCameraView.setAutoFocus(z);
    }

    @ExpoProp(name = "focusDepth")
    public void setFocusDepth(ExpoCameraView expoCameraView, float f) {
        expoCameraView.setFocusDepth(f);
    }

    @ExpoProp(name = "zoom")
    public void setZoom(ExpoCameraView expoCameraView, float f) {
        expoCameraView.setZoom(f);
    }

    @ExpoProp(name = "whiteBalance")
    public void setWhiteBalance(ExpoCameraView expoCameraView, int i) {
        expoCameraView.setWhiteBalance(i);
    }

    @ExpoProp(name = "pictureSize")
    public void setPictureSize(ExpoCameraView expoCameraView, String str) {
        expoCameraView.setPictureSize(Size.parse(str));
    }

    @ExpoProp(name = "barCodeScannerSettings")
    public void setBarCodeScannerSettings(ExpoCameraView expoCameraView, Map<String, Object> map) {
        expoCameraView.setBarCodeScannerSettings(new BarCodeScannerSettings(map));
    }

    @ExpoProp(name = "useCamera2Api")
    public void setUseCamera2Api(ExpoCameraView expoCameraView, boolean z) {
        expoCameraView.setUsingCamera2Api(z);
    }

    @ExpoProp(name = "barCodeScannerEnabled")
    public void setBarCodeScanning(ExpoCameraView expoCameraView, boolean z) {
        expoCameraView.setShouldScanBarCodes(z);
    }

    @ExpoProp(name = "faceDetectorEnabled")
    public void setFaceDetectorEnabled(ExpoCameraView expoCameraView, boolean z) {
        expoCameraView.setShouldDetectFaces(z);
    }

    @ExpoProp(name = "faceDetectorSettings")
    public void setFaceDetectorSettings(ExpoCameraView expoCameraView, Map<String, Object> map) {
        expoCameraView.setFaceDetectorSettings(map);
    }
}
