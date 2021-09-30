package org.unimodules.interfaces.camera;

import android.graphics.SurfaceTexture;

public interface CameraViewInterface {
    int[] getPreviewSizeAsArray();

    void setPreviewTexture(SurfaceTexture surfaceTexture);
}
