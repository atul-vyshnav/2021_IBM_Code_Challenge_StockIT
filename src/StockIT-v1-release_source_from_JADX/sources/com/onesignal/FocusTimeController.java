package com.onesignal;

import android.os.SystemClock;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalRestClient;
import com.onesignal.influence.model.OSInfluence;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

class FocusTimeController {
    private static FocusTimeController sInstance;
    private List<FocusTimeProcessorBase> focusTimeProcessors = Arrays.asList(new FocusTimeProcessorBase[]{new FocusTimeProcessorUnattributed(), new FocusTimeProcessorAttributed()});
    private Long timeFocusedAtMs;

    private enum FocusEventType {
        BACKGROUND,
        END_SESSION
    }

    private FocusTimeController() {
    }

    public static synchronized FocusTimeController getInstance() {
        FocusTimeController focusTimeController;
        synchronized (FocusTimeController.class) {
            if (sInstance == null) {
                sInstance = new FocusTimeController();
            }
            focusTimeController = sInstance;
        }
        return focusTimeController;
    }

    /* access modifiers changed from: package-private */
    public void appForegrounded() {
        this.timeFocusedAtMs = Long.valueOf(SystemClock.elapsedRealtime());
    }

    /* access modifiers changed from: package-private */
    public void appBackgrounded() {
        giveProcessorsValidFocusTime(OneSignal.getSessionManager().getSessionInfluences(), FocusEventType.BACKGROUND);
        this.timeFocusedAtMs = null;
    }

    /* access modifiers changed from: package-private */
    public void onSessionEnded(List<OSInfluence> list) {
        FocusEventType focusEventType = FocusEventType.END_SESSION;
        if (!giveProcessorsValidFocusTime(list, focusEventType)) {
            for (FocusTimeProcessorBase access$000 : this.focusTimeProcessors) {
                access$000.sendUnsentTimeNow(focusEventType);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void doBlockingBackgroundSyncOfUnsentTime() {
        if (!OneSignal.isForeground()) {
            for (FocusTimeProcessorBase access$100 : this.focusTimeProcessors) {
                access$100.syncUnsentTimeFromSyncJob();
            }
        }
    }

    private boolean giveProcessorsValidFocusTime(List<OSInfluence> list, FocusEventType focusEventType) {
        Long timeFocusedElapsed = getTimeFocusedElapsed();
        if (timeFocusedElapsed == null) {
            return false;
        }
        for (FocusTimeProcessorBase access$200 : this.focusTimeProcessors) {
            access$200.addTime(timeFocusedElapsed.longValue(), list, focusEventType);
        }
        return true;
    }

    private Long getTimeFocusedElapsed() {
        if (this.timeFocusedAtMs == null) {
            return null;
        }
        long elapsedRealtime = (long) ((((double) (SystemClock.elapsedRealtime() - this.timeFocusedAtMs.longValue())) / 1000.0d) + 0.5d);
        if (elapsedRealtime < 1 || elapsedRealtime > 86400) {
            return null;
        }
        return Long.valueOf(elapsedRealtime);
    }

    private static class FocusTimeProcessorUnattributed extends FocusTimeProcessorBase {
        /* access modifiers changed from: protected */
        public void saveInfluences(List<OSInfluence> list) {
        }

        FocusTimeProcessorUnattributed() {
            super();
            this.MIN_ON_FOCUS_TIME_SEC = 60;
            this.PREF_KEY_FOR_UNSENT_TIME = OneSignalPrefs.PREFS_GT_UNSENT_ACTIVE_TIME;
        }

        /* access modifiers changed from: protected */
        public boolean timeTypeApplies(List<OSInfluence> list) {
            for (OSInfluence influenceType : list) {
                if (influenceType.getInfluenceType().isAttributed()) {
                    return false;
                }
            }
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            OneSignal.Log(log_level, getClass().getSimpleName() + ":timeTypeApplies for influences: " + list.toString() + " true");
            return true;
        }

        /* access modifiers changed from: protected */
        public void sendTime(FocusEventType focusEventType) {
            if (!focusEventType.equals(FocusEventType.END_SESSION)) {
                syncUnsentTimeOnBackgroundEvent();
            }
        }
    }

    private static class FocusTimeProcessorAttributed extends FocusTimeProcessorBase {
        FocusTimeProcessorAttributed() {
            super();
            this.MIN_ON_FOCUS_TIME_SEC = 1;
            this.PREF_KEY_FOR_UNSENT_TIME = OneSignalPrefs.PREFS_OS_UNSENT_ATTRIBUTED_ACTIVE_TIME;
        }

        private List<OSInfluence> getInfluences() {
            ArrayList arrayList = new ArrayList();
            for (String oSInfluence : OneSignalPrefs.getStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_ATTRIBUTED_INFLUENCES, new HashSet())) {
                try {
                    arrayList.add(new OSInfluence(oSInfluence));
                } catch (JSONException e) {
                    OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
                    OneSignal.Log(log_level, getClass().getSimpleName() + ": error generation OSInfluence from json object: " + e);
                }
            }
            return arrayList;
        }

        /* access modifiers changed from: protected */
        public void saveInfluences(List<OSInfluence> list) {
            HashSet hashSet = new HashSet();
            for (OSInfluence jSONString : list) {
                try {
                    hashSet.add(jSONString.toJSONString());
                } catch (JSONException e) {
                    OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.ERROR;
                    OneSignal.Log(log_level, getClass().getSimpleName() + ": error generation json object OSInfluence: " + e);
                }
            }
            OneSignalPrefs.saveStringSet(OneSignalPrefs.PREFS_ONESIGNAL, OneSignalPrefs.PREFS_OS_ATTRIBUTED_INFLUENCES, hashSet);
        }

        /* access modifiers changed from: protected */
        public boolean timeTypeApplies(List<OSInfluence> list) {
            for (OSInfluence influenceType : list) {
                if (influenceType.getInfluenceType().isAttributed()) {
                    OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                    OneSignal.Log(log_level, getClass().getSimpleName() + ":timeTypeApplies for influences: " + list.toString() + " true");
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: protected */
        public void additionalFieldsToAddToOnFocusPayload(JSONObject jSONObject) {
            OneSignal.getSessionManager().addSessionIds(jSONObject, getInfluences());
        }

        /* access modifiers changed from: protected */
        public void sendTime(FocusEventType focusEventType) {
            if (focusEventType.equals(FocusEventType.END_SESSION)) {
                syncOnFocusTime();
            } else {
                OneSignalSyncServiceUtils.scheduleSyncTask(OneSignal.appContext);
            }
        }
    }

    private static abstract class FocusTimeProcessorBase {
        protected long MIN_ON_FOCUS_TIME_SEC;
        protected String PREF_KEY_FOR_UNSENT_TIME;
        private final AtomicBoolean runningOnFocusTime;
        private Long unsentActiveTime;

        /* access modifiers changed from: protected */
        public void additionalFieldsToAddToOnFocusPayload(JSONObject jSONObject) {
        }

        /* access modifiers changed from: protected */
        public abstract void saveInfluences(List<OSInfluence> list);

        /* access modifiers changed from: protected */
        public abstract void sendTime(FocusEventType focusEventType);

        /* access modifiers changed from: protected */
        public abstract boolean timeTypeApplies(List<OSInfluence> list);

        private FocusTimeProcessorBase() {
            this.unsentActiveTime = null;
            this.runningOnFocusTime = new AtomicBoolean();
        }

        private long getUnsentActiveTime() {
            if (this.unsentActiveTime == null) {
                this.unsentActiveTime = Long.valueOf(OneSignalPrefs.getLong(OneSignalPrefs.PREFS_ONESIGNAL, this.PREF_KEY_FOR_UNSENT_TIME, 0));
            }
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            OneSignal.Log(log_level, getClass().getSimpleName() + ":getUnsentActiveTime: " + this.unsentActiveTime);
            return this.unsentActiveTime.longValue();
        }

        /* access modifiers changed from: private */
        public void saveUnsentActiveTime(long j) {
            this.unsentActiveTime = Long.valueOf(j);
            OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
            OneSignal.Log(log_level, getClass().getSimpleName() + ":saveUnsentActiveTime: " + this.unsentActiveTime);
            OneSignalPrefs.saveLong(OneSignalPrefs.PREFS_ONESIGNAL, this.PREF_KEY_FOR_UNSENT_TIME, j);
        }

        /* access modifiers changed from: private */
        public void addTime(long j, List<OSInfluence> list, FocusEventType focusEventType) {
            if (timeTypeApplies(list)) {
                saveInfluences(list);
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                OneSignal.Log(log_level, getClass().getSimpleName() + ":addTime with lastFocusTimeInfluences: " + list.toString());
                saveUnsentActiveTime(getUnsentActiveTime() + j);
                sendUnsentTimeNow(focusEventType);
            }
        }

        /* access modifiers changed from: private */
        public void sendUnsentTimeNow(FocusEventType focusEventType) {
            if (OneSignal.hasUserId()) {
                sendTime(focusEventType);
            }
        }

        private boolean hasMinSyncTime() {
            return getUnsentActiveTime() >= this.MIN_ON_FOCUS_TIME_SEC;
        }

        /* access modifiers changed from: protected */
        public void syncUnsentTimeOnBackgroundEvent() {
            if (hasMinSyncTime()) {
                OneSignalSyncServiceUtils.scheduleSyncTask(OneSignal.appContext);
                syncOnFocusTime();
            }
        }

        /* access modifiers changed from: private */
        public void syncUnsentTimeFromSyncJob() {
            if (hasMinSyncTime()) {
                syncOnFocusTime();
            }
        }

        /* access modifiers changed from: protected */
        public void syncOnFocusTime() {
            if (!this.runningOnFocusTime.get()) {
                synchronized (this.runningOnFocusTime) {
                    this.runningOnFocusTime.set(true);
                    if (hasMinSyncTime()) {
                        sendOnFocus(getUnsentActiveTime());
                    }
                    this.runningOnFocusTime.set(false);
                }
            }
        }

        private void sendOnFocusToPlayer(String str, JSONObject jSONObject) {
            C23041 r0 = new OneSignalRestClient.ResponseHandler() {
                /* access modifiers changed from: package-private */
                public void onFailure(int i, String str, Throwable th) {
                    OneSignal.logHttpError("sending on_focus Failed", i, th, str);
                }

                /* access modifiers changed from: package-private */
                public void onSuccess(String str) {
                    FocusTimeProcessorBase.this.saveUnsentActiveTime(0);
                }
            };
            OneSignalRestClient.postSync("players/" + str + "/on_focus", jSONObject, r0);
        }

        private JSONObject generateOnFocusPayload(long j) throws JSONException {
            JSONObject put = new JSONObject().put("app_id", OneSignal.getSavedAppId()).put("type", 1).put("state", "ping").put("active_time", j).put("device_type", new OSUtils().getDeviceType());
            OneSignal.addNetType(put);
            return put;
        }

        private void sendOnFocus(long j) {
            try {
                OneSignal.LOG_LEVEL log_level = OneSignal.LOG_LEVEL.DEBUG;
                OneSignal.Log(log_level, getClass().getSimpleName() + ":sendOnFocus with totalTimeActive: " + j);
                JSONObject generateOnFocusPayload = generateOnFocusPayload(j);
                additionalFieldsToAddToOnFocusPayload(generateOnFocusPayload);
                sendOnFocusToPlayer(OneSignal.getUserId(), generateOnFocusPayload);
                if (OneSignal.hasEmailId()) {
                    sendOnFocusToPlayer(OneSignal.getEmailId(), generateOnFocusPayload(j));
                }
            } catch (JSONException e) {
                OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Generating on_focus:JSON Failed.", e);
            }
        }
    }
}
