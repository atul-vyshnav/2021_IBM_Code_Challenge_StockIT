package com.onesignal;

import com.onesignal.OneSignal;
import com.onesignal.OneSignalRestClient;
import com.onesignal.OneSignalStateSynchronizer;
import com.onesignal.UserStateSynchronizer;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class UserStatePushSynchronizer extends UserStateSynchronizer {
    /* access modifiers changed from: private */
    public static boolean serverSuccess;

    /* access modifiers changed from: protected */
    public void addOnSessionOrCreateExtras(JSONObject jSONObject) {
    }

    UserStatePushSynchronizer() {
        super(OneSignalStateSynchronizer.UserStateSynchronizerType.PUSH);
    }

    /* access modifiers changed from: protected */
    public UserState newUserState(String str, boolean z) {
        return new UserStatePush(str, z);
    }

    /* access modifiers changed from: protected */
    public OneSignal.LOG_LEVEL getLogLevel() {
        return OneSignal.LOG_LEVEL.ERROR;
    }

    /* access modifiers changed from: package-private */
    public boolean getSubscribed() {
        return getToSyncUserState().isSubscribed();
    }

    /* access modifiers changed from: package-private */
    public UserStateSynchronizer.GetTagsResult getTags(boolean z) {
        UserStateSynchronizer.GetTagsResult getTagsResult;
        if (z) {
            String userId = OneSignal.getUserId();
            String savedAppId = OneSignal.getSavedAppId();
            OneSignalRestClient.getSync("players/" + userId + "?app_id=" + savedAppId, new OneSignalRestClient.ResponseHandler() {
                /* access modifiers changed from: package-private */
                public void onSuccess(String str) {
                    boolean unused = UserStatePushSynchronizer.serverSuccess = true;
                    if (str == null || str.isEmpty()) {
                        str = "{}";
                    }
                    try {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.has(UserState.TAGS)) {
                            synchronized (UserStatePushSynchronizer.this.LOCK) {
                                JSONObject generateJsonDiff = UserStatePushSynchronizer.this.generateJsonDiff(UserStatePushSynchronizer.this.currentUserState.getSyncValues().optJSONObject(UserState.TAGS), UserStatePushSynchronizer.this.getToSyncUserState().getSyncValues().optJSONObject(UserState.TAGS), (JSONObject) null, (Set<String>) null);
                                UserStatePushSynchronizer.this.currentUserState.putOnSyncValues(UserState.TAGS, jSONObject.optJSONObject(UserState.TAGS));
                                UserStatePushSynchronizer.this.currentUserState.persistState();
                                UserStatePushSynchronizer.this.getToSyncUserState().mergeTags(jSONObject, generateJsonDiff);
                                UserStatePushSynchronizer.this.getToSyncUserState().persistState();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, "CACHE_KEY_GET_TAGS");
        }
        synchronized (this.LOCK) {
            getTagsResult = new UserStateSynchronizer.GetTagsResult(serverSuccess, JSONUtils.getJSONObjectWithoutBlankValues(this.toSyncUserState.getSyncValues(), UserState.TAGS));
        }
        return getTagsResult;
    }

    /* access modifiers changed from: package-private */
    public String getExternalId(boolean z) {
        String optString;
        synchronized (this.LOCK) {
            optString = this.toSyncUserState.getSyncValues().optString("external_user_id", (String) null);
        }
        return optString;
    }

    /* access modifiers changed from: protected */
    public void scheduleSyncToServer() {
        getNetworkHandlerThread(0).runNewJobDelayed();
    }

    /* access modifiers changed from: package-private */
    public void updateState(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.putOpt("identifier", jSONObject.optString("identifier", (String) null));
            if (jSONObject.has("device_type")) {
                jSONObject2.put("device_type", jSONObject.optInt("device_type"));
            }
            jSONObject2.putOpt("parent_player_id", jSONObject.optString("parent_player_id", (String) null));
            getUserStateForModification().generateJsonDiffFromIntoSyncValued(jSONObject2, (Set<String>) null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jSONObject3 = new JSONObject();
            if (jSONObject.has("subscribableStatus")) {
                jSONObject3.put("subscribableStatus", jSONObject.optInt("subscribableStatus"));
            }
            if (jSONObject.has("androidPermission")) {
                jSONObject3.put("androidPermission", jSONObject.optBoolean("androidPermission"));
            }
            getUserStateForModification().generateJsonDiffFromIntoDependValues(jSONObject3, (Set<String>) null);
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void setEmail(String str, String str2) {
        try {
            UserState userStateForModification = getUserStateForModification();
            userStateForModification.putOnDependValues("email_auth_hash", str2);
            userStateForModification.generateJsonDiffFromIntoSyncValued(new JSONObject().put("email", str), (Set<String>) null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void setSubscription(boolean z) {
        try {
            getUserStateForModification().putOnDependValues("userSubscribePref", Boolean.valueOf(z));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean getUserSubscribePreference() {
        return getToSyncUserState().getDependValues().optBoolean("userSubscribePref", true);
    }

    public void setPermission(boolean z) {
        try {
            getUserStateForModification().putOnDependValues("androidPermission", Boolean.valueOf(z));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public String getId() {
        return OneSignal.getUserId();
    }

    /* access modifiers changed from: package-private */
    public void updateIdDependents(String str) {
        OneSignal.updateUserIdDependents(str);
    }

    /* access modifiers changed from: package-private */
    public void logoutEmail() {
        try {
            getUserStateForModification().putOnDependValues("logoutEmail", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: protected */
    public void fireEventsForUpdateFailure(JSONObject jSONObject) {
        if (jSONObject.has("email")) {
            OneSignal.fireEmailUpdateFailure();
        }
    }

    /* access modifiers changed from: protected */
    public void onSuccessfulSync(JSONObject jSONObject) {
        if (jSONObject.has("email")) {
            OneSignal.fireEmailUpdateSuccess();
        }
        if (jSONObject.has("identifier")) {
            OneSignal.fireIdsAvailableCallback();
        }
    }
}
