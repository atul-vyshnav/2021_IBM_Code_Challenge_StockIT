package expo.modules.camera.tasks;

import android.os.Bundle;
import java.util.List;
import org.unimodules.interfaces.facedetector.FaceDetector;

public interface FaceDetectorAsyncTaskDelegate {
    void onFaceDetectingTaskCompleted();

    void onFaceDetectionError(FaceDetector faceDetector);

    void onFacesDetected(List<Bundle> list);
}
