package expo.modules.p019av;

import android.content.Context;
import expo.modules.p019av.video.VideoView;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.arguments.ReadableArguments;

/* renamed from: expo.modules.av.AVManagerInterface */
public interface AVManagerInterface {
    void abandonAudioFocusIfUnused();

    void acquireAudioFocus() throws AudioFocusNotAcquiredException;

    void getAudioRecordingStatus(Promise promise);

    Context getContext();

    ModuleRegistry getModuleRegistry();

    void getStatusForSound(Integer num, Promise promise);

    void getStatusForVideo(Integer num, Promise promise);

    float getVolumeForDuckAndFocus(boolean z, float f);

    void loadForSound(ReadableArguments readableArguments, ReadableArguments readableArguments2, Promise promise);

    void loadForVideo(Integer num, ReadableArguments readableArguments, ReadableArguments readableArguments2, Promise promise);

    void pauseAudioRecording(Promise promise);

    void prepareAudioRecorder(ReadableArguments readableArguments, Promise promise);

    void registerVideoViewForAudioLifecycle(VideoView videoView);

    void replaySound(Integer num, ReadableArguments readableArguments, Promise promise);

    void replayVideo(Integer num, ReadableArguments readableArguments, Promise promise);

    void setAudioIsEnabled(Boolean bool);

    void setAudioMode(ReadableArguments readableArguments);

    void setStatusForSound(Integer num, ReadableArguments readableArguments, Promise promise);

    void setStatusForVideo(Integer num, ReadableArguments readableArguments, Promise promise);

    void startAudioRecording(Promise promise);

    void stopAudioRecording(Promise promise);

    void unloadAudioRecorder(Promise promise);

    void unloadForSound(Integer num, Promise promise);

    void unloadForVideo(Integer num, Promise promise);

    void unregisterVideoViewForAudioLifecycle(VideoView videoView);
}
