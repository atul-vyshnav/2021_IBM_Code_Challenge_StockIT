package expo.modules.p019av.video;

import android.content.Context;
import com.yqritc.scalablevideoview.ScalableType;
import java.util.ArrayList;
import java.util.List;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.ViewManager;
import org.unimodules.core.arguments.ReadableArguments;
import org.unimodules.core.interfaces.ExpoProp;

/* renamed from: expo.modules.av.video.VideoViewManager */
public class VideoViewManager extends ViewManager<VideoViewWrapper> {
    private static final String PROP_NATIVE_RESIZE_MODE = "resizeMode";
    private static final String PROP_SOURCE = "source";
    private static final String PROP_STATUS = "status";
    private static final String PROP_USE_NATIVE_CONTROLS = "useNativeControls";
    public static final String REACT_CLASS = "ExpoVideoView";
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return REACT_CLASS;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    /* renamed from: expo.modules.av.video.VideoViewManager$FullscreenPlayerUpdate */
    enum FullscreenPlayerUpdate {
        FULLSCREEN_PLAYER_WILL_PRESENT(0),
        FULLSCREEN_PLAYER_DID_PRESENT(1),
        FULLSCREEN_PLAYER_WILL_DISMISS(2),
        FULLSCREEN_PLAYER_DID_DISMISS(3);
        
        private final int mValue;

        private FullscreenPlayerUpdate(int i) {
            this.mValue = i;
        }

        public int getValue() {
            return this.mValue;
        }
    }

    /* renamed from: expo.modules.av.video.VideoViewManager$Events */
    enum Events {
        EVENT_STATUS_UPDATE("onStatusUpdate"),
        EVENT_LOAD_START("onLoadStart"),
        EVENT_LOAD("onLoad"),
        EVENT_ERROR("onError"),
        EVENT_READY_FOR_DISPLAY("onReadyForDisplay"),
        EVENT_FULLSCREEN_PLAYER_UPDATE("onFullscreenUpdate");
        
        private final String mName;

        private Events(String str) {
            this.mName = str;
        }

        public String toString() {
            return this.mName;
        }
    }

    public ViewManager.ViewManagerType getViewManagerType() {
        return ViewManager.ViewManagerType.SIMPLE;
    }

    public VideoViewWrapper createViewInstance(Context context) {
        return new VideoViewWrapper(context, this.mModuleRegistry);
    }

    public void onDropViewInstance(VideoViewWrapper videoViewWrapper) {
        super.onDropViewInstance(videoViewWrapper);
        videoViewWrapper.getVideoViewInstance().onDropViewInstance();
    }

    @ExpoProp(name = "status")
    public void setStatus(VideoViewWrapper videoViewWrapper, ReadableArguments readableArguments) {
        videoViewWrapper.getVideoViewInstance().setStatus(readableArguments, (Promise) null);
    }

    @ExpoProp(name = "useNativeControls")
    public void setUseNativeControls(VideoViewWrapper videoViewWrapper, boolean z) {
        videoViewWrapper.getVideoViewInstance().setUseNativeControls(z);
    }

    @ExpoProp(name = "source")
    public void setSource(VideoViewWrapper videoViewWrapper, ReadableArguments readableArguments) {
        videoViewWrapper.getVideoViewInstance().setSource(readableArguments);
    }

    @ExpoProp(name = "resizeMode")
    public void setNativeResizeMode(VideoViewWrapper videoViewWrapper, String str) {
        videoViewWrapper.getVideoViewInstance().setResizeMode(ScalableType.values()[Integer.parseInt(str)]);
    }

    public List<String> getExportedEventNames() {
        ArrayList arrayList = new ArrayList();
        for (Events events : Events.values()) {
            arrayList.add(events.toString());
        }
        return arrayList;
    }
}
