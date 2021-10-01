package expo.modules.barcodescanner.utils;

public class Frame {
    private ImageDimensions mDimensions;
    private com.google.android.gms.vision.Frame mFrame;

    public Frame(com.google.android.gms.vision.Frame frame, ImageDimensions imageDimensions) {
        this.mFrame = frame;
        this.mDimensions = imageDimensions;
    }

    public com.google.android.gms.vision.Frame getFrame() {
        return this.mFrame;
    }

    public ImageDimensions getDimensions() {
        return this.mDimensions;
    }
}
