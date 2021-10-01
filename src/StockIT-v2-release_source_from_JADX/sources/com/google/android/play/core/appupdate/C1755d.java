package com.google.android.play.core.appupdate;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import com.google.android.play.core.common.IntentSenderForResultStarter;

/* renamed from: com.google.android.play.core.appupdate.d */
final class C1755d implements IntentSenderForResultStarter {

    /* renamed from: a */
    final /* synthetic */ Activity f265a;

    C1755d(Activity activity) {
        this.f265a = activity;
    }

    public final void startIntentSenderForResult(IntentSender intentSender, int i, Intent intent, int i2, int i3, int i4, Bundle bundle) throws IntentSender.SendIntentException {
        this.f265a.startIntentSenderForResult(intentSender, i, intent, i2, i3, i4, bundle);
    }
}
