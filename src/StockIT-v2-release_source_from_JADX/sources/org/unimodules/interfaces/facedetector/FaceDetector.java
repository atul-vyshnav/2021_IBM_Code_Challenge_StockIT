package org.unimodules.interfaces.facedetector;

import android.net.Uri;
import java.io.IOException;
import java.util.Map;

public interface FaceDetector {
    void detectFaces(Uri uri, FacesDetectionCompleted facesDetectionCompleted, FaceDetectionError faceDetectionError) throws IOException;

    void detectFaces(byte[] bArr, int i, int i2, int i3, boolean z, double d, double d2, FacesDetectionCompleted facesDetectionCompleted, FaceDetectionError faceDetectionError, FaceDetectionSkipped faceDetectionSkipped);

    void release();

    void setSettings(Map<String, Object> map);
}
