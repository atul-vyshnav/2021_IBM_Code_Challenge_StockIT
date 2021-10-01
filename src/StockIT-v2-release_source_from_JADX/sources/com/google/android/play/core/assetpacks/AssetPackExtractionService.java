package com.google.android.play.core.assetpacks;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.play.core.internal.C1918af;
import com.onesignal.OneSignalDbContract;

public class AssetPackExtractionService extends Service {

    /* renamed from: a */
    Context f339a;

    /* renamed from: b */
    C1888j f340b;

    /* renamed from: c */
    C1806bb f341c;

    /* renamed from: d */
    private final C1918af f342d = new C1918af("AssetPackExtractionService");

    /* renamed from: e */
    private C1804b f343e;

    /* renamed from: f */
    private NotificationManager f344f;

    /* renamed from: b */
    private final synchronized void m281b(Bundle bundle) {
        String string = bundle.getString("notification_title");
        String string2 = bundle.getString("notification_subtext");
        long j = bundle.getLong("notification_timeout");
        PendingIntent pendingIntent = (PendingIntent) bundle.getParcelable("notification_on_click_intent");
        Notification.Builder timeoutAfter = Build.VERSION.SDK_INT >= 26 ? new Notification.Builder(this.f339a, "playcore-assetpacks-service-notification-channel").setTimeoutAfter(j) : new Notification.Builder(this.f339a).setPriority(-2);
        if (pendingIntent != null) {
            timeoutAfter.setContentIntent(pendingIntent);
        }
        timeoutAfter.setSmallIcon(17301633).setOngoing(false).setContentTitle(string).setSubText(string2);
        if (Build.VERSION.SDK_INT >= 21) {
            timeoutAfter.setColor(bundle.getInt("notification_color")).setVisibility(-1);
        }
        Notification build = timeoutAfter.build();
        this.f342d.mo34142c("Starting foreground service.", new Object[0]);
        this.f340b.mo34111a(true);
        if (Build.VERSION.SDK_INT >= 26) {
            this.f344f.createNotificationChannel(new NotificationChannel("playcore-assetpacks-service-notification-channel", bundle.getString("notification_channel_name"), 2));
        }
        startForeground(-1883842196, build);
    }

    /* renamed from: a */
    public final synchronized Bundle mo33899a(Bundle bundle) {
        int i = bundle.getInt("action_type");
        C1918af afVar = this.f342d;
        Integer valueOf = Integer.valueOf(i);
        afVar.mo34139a("updateServiceState: %d", valueOf);
        if (i == 1) {
            m281b(bundle);
        } else if (i == 2) {
            mo33900a();
        } else {
            this.f342d.mo34141b("Unknown action type received: %d", valueOf);
        }
        return new Bundle();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized void mo33900a() {
        this.f342d.mo34142c("Stopping service.", new Object[0]);
        this.f340b.mo34111a(false);
        stopForeground(true);
        stopSelf();
    }

    public final IBinder onBind(Intent intent) {
        return this.f343e;
    }

    public final void onCreate() {
        super.onCreate();
        this.f342d.mo34139a("onCreate", new Object[0]);
        C1860db.m494a(getApplicationContext()).mo33928a(this);
        this.f343e = new C1804b(this.f339a, this, this.f341c);
        this.f344f = (NotificationManager) this.f339a.getSystemService(OneSignalDbContract.NotificationTable.TABLE_NAME);
    }
}
