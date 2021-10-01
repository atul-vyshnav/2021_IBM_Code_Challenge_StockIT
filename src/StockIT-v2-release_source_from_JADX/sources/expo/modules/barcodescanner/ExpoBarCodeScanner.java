package expo.modules.barcodescanner;

import android.hardware.Camera;
import android.util.Log;
import com.facebook.imagepipeline.common.RotationOptions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpoBarCodeScanner {
    static final int CAMERA_TYPE_BACK = 2;
    static final int CAMERA_TYPE_FRONT = 1;
    private static ExpoBarCodeScanner ourInstance;
    private int mActualDeviceOrientation;
    private Camera mCamera = null;
    private final HashMap<Integer, CameraInfoWrapper> mCameraInfos;
    private int mCameraType;
    private final HashMap<Integer, Integer> mCameraTypeToIndex;
    private final Set<Number> mCameras;
    private int mRotation;

    public static ExpoBarCodeScanner getInstance() {
        return ourInstance;
    }

    public static void createInstance(int i) {
        ourInstance = new ExpoBarCodeScanner(i);
    }

    public Camera acquireCameraInstance(int i) {
        if (this.mCamera == null && this.mCameras.contains(Integer.valueOf(i)) && this.mCameraTypeToIndex.get(Integer.valueOf(i)) != null) {
            try {
                this.mCamera = Camera.open(this.mCameraTypeToIndex.get(Integer.valueOf(i)).intValue());
                this.mCameraType = i;
                adjustPreviewLayout(i);
            } catch (Exception e) {
                Log.e("ExpoBarCodeScanner", "acquireCameraInstance failed", e);
            }
        }
        return this.mCamera;
    }

    public void releaseCameraInstance() {
        Camera camera = this.mCamera;
        if (camera != null) {
            camera.release();
            this.mCamera = null;
        }
    }

    public int getPreviewWidth(int i) {
        CameraInfoWrapper cameraInfoWrapper = this.mCameraInfos.get(Integer.valueOf(i));
        if (cameraInfoWrapper == null) {
            return 0;
        }
        return cameraInfoWrapper.previewWidth;
    }

    public int getPreviewHeight(int i) {
        CameraInfoWrapper cameraInfoWrapper = this.mCameraInfos.get(Integer.valueOf(i));
        if (cameraInfoWrapper == null) {
            return 0;
        }
        return cameraInfoWrapper.previewHeight;
    }

    public Camera.Size getBestSize(List<Camera.Size> list, int i, int i2) {
        Camera.Size size = null;
        for (Camera.Size next : list) {
            if (next.width <= i && next.height <= i2) {
                if (size == null || next.width * next.height > size.width * size.height) {
                    size = next;
                }
            }
        }
        return size;
    }

    public int getActualDeviceOrientation() {
        return this.mActualDeviceOrientation;
    }

    public void setActualDeviceOrientation(int i) {
        this.mActualDeviceOrientation = i;
        adjustPreviewLayout(this.mCameraType);
    }

    public void adjustPreviewLayout(int i) {
        CameraInfoWrapper cameraInfoWrapper;
        if (this.mCamera != null && (cameraInfoWrapper = this.mCameraInfos.get(Integer.valueOf(i))) != null) {
            int i2 = this.mActualDeviceOrientation;
            int i3 = 0;
            if (i2 != 0) {
                if (i2 == 1) {
                    i3 = 90;
                } else if (i2 == 2) {
                    i3 = RotationOptions.ROTATE_180;
                } else if (i2 == 3) {
                    i3 = 270;
                }
            }
            if (cameraInfoWrapper.info.facing == 1) {
                int i4 = (cameraInfoWrapper.info.orientation + i3) % 360;
                this.mRotation = i4;
                this.mRotation = (360 - i4) % 360;
            } else {
                this.mRotation = ((cameraInfoWrapper.info.orientation - i3) + 360) % 360;
            }
            this.mCamera.setDisplayOrientation(this.mRotation);
            Camera.Parameters parameters = this.mCamera.getParameters();
            parameters.setRotation(this.mRotation);
            Camera.Size bestSize = getBestSize(parameters.getSupportedPreviewSizes(), 1920, 1920);
            int i5 = bestSize.width;
            int i6 = bestSize.height;
            parameters.setPreviewSize(i5, i6);
            try {
                this.mCamera.setParameters(parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cameraInfoWrapper.previewHeight = i6;
            cameraInfoWrapper.previewWidth = i5;
            int i7 = this.mRotation;
            if (i7 == 90 || i7 == 270) {
                cameraInfoWrapper.previewHeight = i5;
                cameraInfoWrapper.previewWidth = i6;
            }
        }
    }

    private ExpoBarCodeScanner(int i) {
        this.mCameraType = 0;
        this.mActualDeviceOrientation = 0;
        this.mRotation = 0;
        this.mCameras = new HashSet();
        this.mCameraInfos = new HashMap<>();
        this.mCameraTypeToIndex = new HashMap<>();
        this.mActualDeviceOrientation = i;
        for (int i2 = 0; i2 < Camera.getNumberOfCameras(); i2++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i2, cameraInfo);
            if (cameraInfo.facing == 1 && this.mCameraInfos.get(1) == null) {
                this.mCameraInfos.put(1, new CameraInfoWrapper(cameraInfo));
                this.mCameraTypeToIndex.put(1, Integer.valueOf(i2));
                this.mCameras.add(1);
            } else if (cameraInfo.facing == 0 && this.mCameraInfos.get(2) == null) {
                this.mCameraInfos.put(2, new CameraInfoWrapper(cameraInfo));
                this.mCameraTypeToIndex.put(2, Integer.valueOf(i2));
                this.mCameras.add(2);
            }
        }
    }

    public int getRotation() {
        return this.mRotation;
    }

    private class CameraInfoWrapper {
        public final Camera.CameraInfo info;
        public int previewHeight = -1;
        public int previewWidth = -1;
        public int rotation = 0;

        public CameraInfoWrapper(Camera.CameraInfo cameraInfo) {
            this.info = cameraInfo;
        }
    }
}
