package expo.modules.camera.tasks;

import java.util.ArrayList;
import org.unimodules.interfaces.facedetector.FaceDetectionError;
import org.unimodules.interfaces.facedetector.FaceDetectionSkipped;
import org.unimodules.interfaces.facedetector.FaceDetector;
import org.unimodules.interfaces.facedetector.FacesDetectionCompleted;

public class FaceDetectorTask {
    private FaceDetectorAsyncTaskDelegate mDelegate;
    private FaceDetector mFaceDetector;
    private int mHeight;
    private byte[] mImageData;
    private boolean mMirrored;
    private int mRotation;
    private double mScaleX;
    private double mScaleY;
    private int mWidth;

    public FaceDetectorTask(FaceDetectorAsyncTaskDelegate faceDetectorAsyncTaskDelegate, FaceDetector faceDetector, byte[] bArr, int i, int i2, int i3, boolean z, double d, double d2) {
        this.mImageData = bArr;
        this.mWidth = i;
        this.mHeight = i2;
        this.mMirrored = z;
        this.mScaleX = d;
        this.mScaleY = d2;
        this.mRotation = i3;
        this.mDelegate = faceDetectorAsyncTaskDelegate;
        this.mFaceDetector = faceDetector;
    }

    public void execute() {
        this.mFaceDetector.detectFaces(this.mImageData, this.mWidth, this.mHeight, this.mRotation, this.mMirrored, this.mScaleX, this.mScaleY, new FacesDetectionCompleted() {
            public final void detectionCompleted(ArrayList arrayList) {
                FaceDetectorTask.this.lambda$execute$0$FaceDetectorTask(arrayList);
            }
        }, new FaceDetectionError() {
            public final void onError(Throwable th) {
                FaceDetectorTask.this.lambda$execute$1$FaceDetectorTask(th);
            }
        }, new FaceDetectionSkipped() {
            public final void onSkipped(String str) {
                FaceDetectorTask.this.lambda$execute$2$FaceDetectorTask(str);
            }
        });
    }

    public /* synthetic */ void lambda$execute$0$FaceDetectorTask(ArrayList arrayList) {
        if (arrayList != null) {
            this.mDelegate.onFacesDetected(arrayList);
        } else {
            this.mDelegate.onFaceDetectionError(this.mFaceDetector);
        }
        this.mDelegate.onFaceDetectingTaskCompleted();
    }

    public /* synthetic */ void lambda$execute$1$FaceDetectorTask(Throwable th) {
        this.mDelegate.onFaceDetectionError(this.mFaceDetector);
        this.mDelegate.onFaceDetectingTaskCompleted();
    }

    public /* synthetic */ void lambda$execute$2$FaceDetectorTask(String str) {
        this.mDelegate.onFaceDetectingTaskCompleted();
    }
}
