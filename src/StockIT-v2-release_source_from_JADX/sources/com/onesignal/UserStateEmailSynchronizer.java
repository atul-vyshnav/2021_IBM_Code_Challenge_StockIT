package com.onesignal;

import com.onesignal.OneSignal;
import com.onesignal.OneSignalStateSynchronizer;
import com.onesignal.UserStateSynchronizer;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

class UserStateEmailSynchronizer extends UserStateSynchronizer {
    /* access modifiers changed from: package-private */
    public String getExternalId(boolean z) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean getSubscribed() {
        return false;
    }

    /* access modifiers changed from: package-private */
    public UserStateSynchronizer.GetTagsResult getTags(boolean z) {
        return null;
    }

    public boolean getUserSubscribePreference() {
        return false;
    }

    public void setPermission(boolean z) {
    }

    /* access modifiers changed from: package-private */
    public void setSubscription(boolean z) {
    }

    /* access modifiers changed from: package-private */
    public void updateState(JSONObject jSONObject) {
    }

    UserStateEmailSynchronizer() {
        super(OneSignalStateSynchronizer.UserStateSynchronizerType.EMAIL);
    }

    /* access modifiers changed from: protected */
    public UserState newUserState(String str, boolean z) {
        return new UserStateEmail(str, z);
    }

    /* access modifiers changed from: protected */
    public OneSignal.LOG_LEVEL getLogLevel() {
        return OneSignal.LOG_LEVEL.INFO;
    }

    /* access modifiers changed from: package-private */
    public void refresh() {
        scheduleSyncToServer();
    }

    /* access modifiers changed from: protected */
    public void scheduleSyncToServer() {
        if (!(getId() == null && getRegistrationId() == null) && OneSignal.getUserId() != null) {
            getNetworkHandlerThread(0).runNewJobDelayed();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x002c  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0030  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setEmail(java.lang.String r8, java.lang.String r9) {
        /*
            r7 = this;
            com.onesignal.UserState r0 = r7.getUserStateForModification()
            com.onesignal.ImmutableJSONObject r1 = r0.getSyncValues()
            java.lang.String r2 = "identifier"
            java.lang.String r3 = r1.optString(r2)
            boolean r3 = r8.equals(r3)
            java.lang.String r4 = ""
            java.lang.String r5 = "email_auth_hash"
            if (r3 == 0) goto L_0x0029
            java.lang.String r3 = r1.optString(r5)
            if (r9 != 0) goto L_0x0020
            r6 = r4
            goto L_0x0021
        L_0x0020:
            r6 = r9
        L_0x0021:
            boolean r3 = r3.equals(r6)
            if (r3 == 0) goto L_0x0029
            r3 = 1
            goto L_0x002a
        L_0x0029:
            r3 = 0
        L_0x002a:
            if (r3 == 0) goto L_0x0030
            com.onesignal.OneSignal.fireEmailUpdateSuccess()
            return
        L_0x0030:
            r3 = 0
            java.lang.String r1 = r1.optString(r2, r3)
            if (r1 != 0) goto L_0x003a
            r7.setNewSession()
        L_0x003a:
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0061 }
            r6.<init>()     // Catch:{ JSONException -> 0x0061 }
            r6.put(r2, r8)     // Catch:{ JSONException -> 0x0061 }
            if (r9 == 0) goto L_0x0047
            r6.put(r5, r9)     // Catch:{ JSONException -> 0x0061 }
        L_0x0047:
            if (r9 != 0) goto L_0x005a
            if (r1 == 0) goto L_0x005a
            boolean r8 = r1.equals(r8)     // Catch:{ JSONException -> 0x0061 }
            if (r8 != 0) goto L_0x005a
            com.onesignal.OneSignal.saveEmailId(r4)     // Catch:{ JSONException -> 0x0061 }
            r7.resetCurrentState()     // Catch:{ JSONException -> 0x0061 }
            r7.setNewSession()     // Catch:{ JSONException -> 0x0061 }
        L_0x005a:
            r0.generateJsonDiffFromIntoSyncValued(r6, r3)     // Catch:{ JSONException -> 0x0061 }
            r7.scheduleSyncToServer()     // Catch:{ JSONException -> 0x0061 }
            goto L_0x0065
        L_0x0061:
            r8 = move-exception
            r8.printStackTrace()
        L_0x0065:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.UserStateEmailSynchronizer.setEmail(java.lang.String, java.lang.String):void");
    }

    /* access modifiers changed from: protected */
    public String getId() {
        return OneSignal.getEmailId();
    }

    /* access modifiers changed from: package-private */
    public void updateIdDependents(String str) {
        OneSignal.updateEmailIdDependents(str);
    }

    /* access modifiers changed from: protected */
    public void addOnSessionOrCreateExtras(JSONObject jSONObject) {
        try {
            jSONObject.put("device_type", 11);
            jSONObject.putOpt("device_player_id", OneSignal.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* access modifiers changed from: package-private */
    public void logoutEmail() {
        OneSignal.saveEmailId("");
        resetCurrentState();
        getToSyncUserState().removeFromSyncValues("identifier");
        ArrayList arrayList = new ArrayList();
        arrayList.add("email_auth_hash");
        arrayList.add("device_player_id");
        arrayList.add("external_user_id");
        this.toSyncUserState.removeFromSyncValues((List<String>) arrayList);
        this.toSyncUserState.persistState();
        OneSignal.getPermissionSubscriptionState().emailSubscriptionStatus.clearEmailAndId();
    }

    /* access modifiers changed from: protected */
    public void fireEventsForUpdateFailure(JSONObject jSONObject) {
        if (jSONObject.has("identifier")) {
            OneSignal.fireEmailUpdateFailure();
        }
    }

    /* access modifiers changed from: protected */
    public void onSuccessfulSync(JSONObject jSONObject) {
        if (jSONObject.has("identifier")) {
            OneSignal.fireEmailUpdateSuccess();
        }
    }
}
