package expo.modules.p019av.video;

import android.content.Context;
import com.yqritc.scalablevideoview.ScalableType;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.core.interfaces.services.UIManager;

/* renamed from: expo.modules.av.video.VideoManager */
public class VideoManager extends ExportedModule {
    private static final String NAME = "ExpoVideoManager";
    private ModuleRegistry mModuleRegistry;

    /* renamed from: expo.modules.av.video.VideoManager$VideoViewCallback */
    private interface VideoViewCallback {
        void runWithVideoView(VideoView videoView);
    }

    public String getName() {
        return NAME;
    }

    public VideoManager(Context context) {
        super(context);
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("ScaleNone", Integer.toString(ScalableType.LEFT_TOP.ordinal()));
        hashMap.put("ScaleToFill", Integer.toString(ScalableType.FIT_XY.ordinal()));
        hashMap.put("ScaleAspectFit", Integer.toString(ScalableType.FIT_CENTER.ordinal()));
        hashMap.put("ScaleAspectFill", Integer.toString(ScalableType.CENTER_CROP.ordinal()));
        return hashMap;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    @ExpoMethod
    public void setFullscreen(Integer num, final Boolean bool, final Promise promise) {
        tryRunWithVideoView(num, new VideoViewCallback() {
            public void runWithVideoView(final VideoView videoView) {
                C29381 r0 = new FullscreenVideoPlayerPresentationChangeProgressListener() {
                    public void onFullscreenPlayerDidDismiss() {
                        promise.resolve(videoView.getStatus());
                    }

                    public void onFullscreenPlayerDidPresent() {
                        promise.resolve(videoView.getStatus());
                    }

                    public void onFullscreenPlayerPresentationTriedToInterrupt() {
                        promise.reject("E_FULLSCREEN_VIDEO_PLAYER", "This presentation change tries to interrupt an older request. Await the old request and then try again.");
                    }

                    public void onFullscreenPlayerPresentationInterrupted() {
                        promise.reject("E_FULLSCREEN_VIDEO_PLAYER", "This presentation change has been interrupted by a newer change request.");
                    }

                    /* access modifiers changed from: package-private */
                    public void onFullscreenPlayerPresentationError(String str) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("This presentation change has been interrupted by an error.");
                        if (str != null) {
                            sb.append(StringUtils.SPACE);
                            sb.append(str);
                        }
                        promise.reject("E_FULLSCREEN_VIDEO_PLAYER", sb.toString());
                    }
                };
                if (bool.booleanValue()) {
                    videoView.ensureFullscreenPlayerIsPresented(r0);
                } else {
                    videoView.ensureFullscreenPlayerIsDismissed(r0);
                }
            }
        }, promise);
    }

    private void tryRunWithVideoView(Integer num, final VideoViewCallback videoViewCallback, final Promise promise) {
        ((UIManager) this.mModuleRegistry.getModule(UIManager.class)).addUIBlock(num.intValue(), new UIManager.UIBlock<VideoViewWrapper>() {
            public void resolve(VideoViewWrapper videoViewWrapper) {
                videoViewCallback.runWithVideoView(videoViewWrapper.getVideoViewInstance());
            }

            public void reject(Throwable th) {
                promise.reject("E_VIDEO_TAGINCORRECT", "Invalid view returned from registry.");
            }
        }, VideoViewWrapper.class);
    }
}
