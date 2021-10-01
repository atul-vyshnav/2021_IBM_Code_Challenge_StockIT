package expo.modules.p019av.video;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import org.unimodules.core.ModuleRegistry;

/* renamed from: expo.modules.av.video.VideoViewWrapper */
public class VideoViewWrapper extends FrameLayout {
    private final Runnable mLayoutRunnable = new Runnable() {
        public void run() {
            VideoViewWrapper videoViewWrapper = VideoViewWrapper.this;
            videoViewWrapper.measure(View.MeasureSpec.makeMeasureSpec(videoViewWrapper.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(VideoViewWrapper.this.getHeight(), 1073741824));
            VideoViewWrapper videoViewWrapper2 = VideoViewWrapper.this;
            videoViewWrapper2.layout(videoViewWrapper2.getLeft(), VideoViewWrapper.this.getTop(), VideoViewWrapper.this.getRight(), VideoViewWrapper.this.getBottom());
        }
    };
    private VideoView mVideoView = null;

    public VideoViewWrapper(Context context, ModuleRegistry moduleRegistry) {
        super(context);
        VideoView videoView = new VideoView(context, this, moduleRegistry);
        this.mVideoView = videoView;
        addView(videoView, generateDefaultLayoutParams());
    }

    public VideoView getVideoViewInstance() {
        return this.mVideoView;
    }

    public void requestLayout() {
        super.requestLayout();
        post(this.mLayoutRunnable);
    }
}
