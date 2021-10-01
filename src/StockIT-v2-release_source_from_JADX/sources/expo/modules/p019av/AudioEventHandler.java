package expo.modules.p019av;

/* renamed from: expo.modules.av.AudioEventHandler */
public interface AudioEventHandler {
    void handleAudioFocusGained();

    void handleAudioFocusInterruptionBegan();

    void onPause();

    void onResume();

    void pauseImmediately();

    boolean requiresAudioFocus();

    void updateVolumeMuteAndDuck();
}
