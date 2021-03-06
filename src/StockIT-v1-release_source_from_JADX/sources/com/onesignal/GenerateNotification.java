package com.onesignal;

import android.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.widget.RemoteViews;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.onesignal.AndroidSupportV4Compat;
import com.onesignal.OneSignal;
import com.onesignal.OneSignalDbContract;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class GenerateNotification {
    public static final String BUNDLE_KEY_ACTION_ID = "actionId";
    public static final String BUNDLE_KEY_ANDROID_NOTIFICATION_ID = "androidNotificationId";
    public static final String BUNDLE_KEY_ONESIGNAL_DATA = "onesignalData";
    private static Resources contextResources;
    private static Context currentContext;
    private static Class<?> notificationOpenedClass;
    private static boolean openerIsBroadcast;
    private static String packageName;

    private static int convertOSToAndroidPriority(int i) {
        if (i > 9) {
            return 2;
        }
        if (i > 7) {
            return 1;
        }
        if (i > 4) {
            return 0;
        }
        return i > 2 ? -1 : -2;
    }

    GenerateNotification() {
    }

    private static class OneSignalNotificationBuilder {
        NotificationCompat.Builder compatBuilder;
        boolean hasLargeIcon;

        private OneSignalNotificationBuilder() {
        }
    }

    private static void setStatics(Context context) {
        currentContext = context;
        packageName = context.getPackageName();
        contextResources = currentContext.getResources();
        PackageManager packageManager = currentContext.getPackageManager();
        Intent intent = new Intent(currentContext, NotificationOpenedReceiver.class);
        intent.setPackage(currentContext.getPackageName());
        if (packageManager.queryBroadcastReceivers(intent, 0).size() > 0) {
            openerIsBroadcast = true;
            notificationOpenedClass = NotificationOpenedReceiver.class;
            return;
        }
        notificationOpenedClass = NotificationOpenedActivity.class;
    }

    static void fromJsonPayload(NotificationGenerationJob notificationGenerationJob) {
        setStatics(notificationGenerationJob.context);
        if (notificationGenerationJob.restoring || !notificationGenerationJob.showAsAlert || ActivityLifecycleHandler.curActivity == null) {
            showNotification(notificationGenerationJob);
        } else {
            showNotificationAsAlert(notificationGenerationJob.jsonPayload, ActivityLifecycleHandler.curActivity, notificationGenerationJob.getAndroidId().intValue());
        }
    }

    private static void showNotificationAsAlert(final JSONObject jSONObject, final Activity activity, final int i) {
        activity.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(GenerateNotification.getTitle(jSONObject));
                builder.setMessage(jSONObject.optString("alert"));
                ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                GenerateNotification.addAlertButtons(activity, jSONObject, arrayList, arrayList2);
                final Intent access$200 = GenerateNotification.getNewBaseIntent(i);
                access$200.putExtra("action_button", true);
                access$200.putExtra("from_alert", true);
                access$200.putExtra(GenerateNotification.BUNDLE_KEY_ONESIGNAL_DATA, jSONObject.toString());
                if (jSONObject.has("grp")) {
                    access$200.putExtra("grp", jSONObject.optString("grp"));
                }
                C23071 r4 = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int i2 = i + 3;
                        if (arrayList2.size() > 1) {
                            try {
                                JSONObject jSONObject = new JSONObject(jSONObject.toString());
                                jSONObject.put(GenerateNotification.BUNDLE_KEY_ACTION_ID, arrayList2.get(i2));
                                access$200.putExtra(GenerateNotification.BUNDLE_KEY_ONESIGNAL_DATA, jSONObject.toString());
                                NotificationOpenedProcessor.processIntent(activity, access$200);
                            } catch (Throwable unused) {
                            }
                        } else {
                            NotificationOpenedProcessor.processIntent(activity, access$200);
                        }
                    }
                };
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialogInterface) {
                        NotificationOpenedProcessor.processIntent(activity, access$200);
                    }
                });
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i == 0) {
                        builder.setNeutralButton((CharSequence) arrayList.get(i), r4);
                    } else if (i == 1) {
                        builder.setNegativeButton((CharSequence) arrayList.get(i), r4);
                    } else if (i == 2) {
                        builder.setPositiveButton((CharSequence) arrayList.get(i), r4);
                    }
                }
                AlertDialog create = builder.create();
                create.setCanceledOnTouchOutside(false);
                create.show();
            }
        });
    }

    /* access modifiers changed from: private */
    public static CharSequence getTitle(JSONObject jSONObject) {
        String optString = jSONObject.optString(OneSignalDbContract.NotificationTable.COLUMN_NAME_TITLE, (String) null);
        if (optString != null) {
            return optString;
        }
        return currentContext.getPackageManager().getApplicationLabel(currentContext.getApplicationInfo());
    }

    private static PendingIntent getNewActionPendingIntent(int i, Intent intent) {
        if (openerIsBroadcast) {
            return PendingIntent.getBroadcast(currentContext, i, intent, 134217728);
        }
        return PendingIntent.getActivity(currentContext, i, intent, 134217728);
    }

    /* access modifiers changed from: private */
    public static Intent getNewBaseIntent(int i) {
        Intent putExtra = new Intent(currentContext, notificationOpenedClass).putExtra(BUNDLE_KEY_ANDROID_NOTIFICATION_ID, i);
        if (openerIsBroadcast) {
            return putExtra;
        }
        return putExtra.addFlags(603979776);
    }

    private static Intent getNewBaseDeleteIntent(int i) {
        Intent putExtra = new Intent(currentContext, notificationOpenedClass).putExtra(BUNDLE_KEY_ANDROID_NOTIFICATION_ID, i).putExtra("dismissed", true);
        if (openerIsBroadcast) {
            return putExtra;
        }
        return putExtra.addFlags(402718720);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(20:0|1|2|5|(1:9)|10|11|(1:13)|14|15|(1:17)(1:18)|19|21|(1:23)|24|(1:26)|27|(2:29|30)|31|33) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x006c */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0072 A[Catch:{ all -> 0x0080 }] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x007b A[Catch:{ all -> 0x0080 }] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0087  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0098  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x00ac A[SYNTHETIC, Splitter:B:29:0x00ac] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.onesignal.GenerateNotification.OneSignalNotificationBuilder getBaseOneSignalNotificationBuilder(com.onesignal.NotificationGenerationJob r9) {
        /*
            java.lang.String r0 = "vis"
            org.json.JSONObject r1 = r9.jsonPayload
            com.onesignal.GenerateNotification$OneSignalNotificationBuilder r2 = new com.onesignal.GenerateNotification$OneSignalNotificationBuilder
            r3 = 0
            r2.<init>()
            java.lang.String r4 = com.onesignal.NotificationChannelManager.createNotificationChannel(r9)     // Catch:{ all -> 0x0016 }
            androidx.core.app.NotificationCompat$Builder r5 = new androidx.core.app.NotificationCompat$Builder     // Catch:{ all -> 0x0016 }
            android.content.Context r6 = currentContext     // Catch:{ all -> 0x0016 }
            r5.<init>(r6, r4)     // Catch:{ all -> 0x0016 }
            goto L_0x001d
        L_0x0016:
            androidx.core.app.NotificationCompat$Builder r5 = new androidx.core.app.NotificationCompat$Builder
            android.content.Context r4 = currentContext
            r5.<init>(r4)
        L_0x001d:
            java.lang.String r4 = "alert"
            java.lang.String r4 = r1.optString(r4, r3)
            r6 = 1
            androidx.core.app.NotificationCompat$Builder r7 = r5.setAutoCancel(r6)
            int r8 = getSmallIconId(r1)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setSmallIcon(r8)
            androidx.core.app.NotificationCompat$BigTextStyle r8 = new androidx.core.app.NotificationCompat$BigTextStyle
            r8.<init>()
            androidx.core.app.NotificationCompat$BigTextStyle r8 = r8.bigText(r4)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setStyle(r8)
            androidx.core.app.NotificationCompat$Builder r7 = r7.setContentText(r4)
            r7.setTicker(r4)
            int r7 = android.os.Build.VERSION.SDK_INT
            r8 = 24
            if (r7 < r8) goto L_0x0058
            java.lang.String r7 = "title"
            java.lang.String r7 = r1.optString(r7)
            java.lang.String r8 = ""
            boolean r7 = r7.equals(r8)
            if (r7 != 0) goto L_0x005f
        L_0x0058:
            java.lang.CharSequence r7 = getTitle(r1)
            r5.setContentTitle(r7)
        L_0x005f:
            java.math.BigInteger r7 = getAccentColor(r1)     // Catch:{ all -> 0x006c }
            if (r7 == 0) goto L_0x006c
            int r7 = r7.intValue()     // Catch:{ all -> 0x006c }
            r5.setColor(r7)     // Catch:{ all -> 0x006c }
        L_0x006c:
            boolean r7 = r1.has(r0)     // Catch:{ all -> 0x0080 }
            if (r7 == 0) goto L_0x007b
            java.lang.String r0 = r1.optString(r0)     // Catch:{ all -> 0x0080 }
            int r0 = java.lang.Integer.parseInt(r0)     // Catch:{ all -> 0x0080 }
            goto L_0x007c
        L_0x007b:
            r0 = 1
        L_0x007c:
            r5.setVisibility(r0)     // Catch:{ all -> 0x0080 }
            goto L_0x0081
        L_0x0080:
        L_0x0081:
            android.graphics.Bitmap r0 = getLargeIcon(r1)
            if (r0 == 0) goto L_0x008c
            r2.hasLargeIcon = r6
            r5.setLargeIcon(r0)
        L_0x008c:
            java.lang.String r0 = "bicon"
            java.lang.String r0 = r1.optString(r0, r3)
            android.graphics.Bitmap r0 = getBitmap(r0)
            if (r0 == 0) goto L_0x00a8
            androidx.core.app.NotificationCompat$BigPictureStyle r3 = new androidx.core.app.NotificationCompat$BigPictureStyle
            r3.<init>()
            androidx.core.app.NotificationCompat$BigPictureStyle r0 = r3.bigPicture(r0)
            androidx.core.app.NotificationCompat$BigPictureStyle r0 = r0.setSummaryText(r4)
            r5.setStyle(r0)
        L_0x00a8:
            java.lang.Long r0 = r9.shownTimeStamp
            if (r0 == 0) goto L_0x00b9
            java.lang.Long r9 = r9.shownTimeStamp     // Catch:{ all -> 0x00b9 }
            long r3 = r9.longValue()     // Catch:{ all -> 0x00b9 }
            r6 = 1000(0x3e8, double:4.94E-321)
            long r3 = r3 * r6
            r5.setWhen(r3)     // Catch:{ all -> 0x00b9 }
        L_0x00b9:
            setAlertnessOptions(r1, r5)
            r2.compatBuilder = r5
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.GenerateNotification.getBaseOneSignalNotificationBuilder(com.onesignal.NotificationGenerationJob):com.onesignal.GenerateNotification$OneSignalNotificationBuilder");
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0057  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0061  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0069  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void setAlertnessOptions(org.json.JSONObject r6, androidx.core.app.NotificationCompat.Builder r7) {
        /*
            java.lang.String r0 = "pri"
            r1 = 6
            int r0 = r6.optInt(r0, r1)
            int r0 = convertOSToAndroidPriority(r0)
            r7.setPriority(r0)
            r1 = 0
            r2 = 1
            if (r0 >= 0) goto L_0x0014
            r0 = 1
            goto L_0x0015
        L_0x0014:
            r0 = 0
        L_0x0015:
            if (r0 == 0) goto L_0x0018
            return
        L_0x0018:
            java.lang.String r0 = "ledc"
            boolean r3 = r6.has(r0)
            r4 = 4
            if (r3 == 0) goto L_0x0040
            java.lang.String r3 = "led"
            int r3 = r6.optInt(r3, r2)
            if (r3 != r2) goto L_0x0040
            java.math.BigInteger r3 = new java.math.BigInteger     // Catch:{ all -> 0x0040 }
            java.lang.String r0 = r6.optString(r0)     // Catch:{ all -> 0x0040 }
            r5 = 16
            r3.<init>(r0, r5)     // Catch:{ all -> 0x0040 }
            int r0 = r3.intValue()     // Catch:{ all -> 0x0040 }
            r3 = 2000(0x7d0, float:2.803E-42)
            r5 = 5000(0x1388, float:7.006E-42)
            r7.setLights(r0, r3, r5)     // Catch:{ all -> 0x0040 }
            goto L_0x0041
        L_0x0040:
            r1 = 4
        L_0x0041:
            boolean r0 = com.onesignal.OneSignal.getVibrate()
            if (r0 == 0) goto L_0x0063
            java.lang.String r0 = "vib"
            int r0 = r6.optInt(r0, r2)
            if (r0 != r2) goto L_0x0063
            java.lang.String r0 = "vib_pt"
            boolean r0 = r6.has(r0)
            if (r0 == 0) goto L_0x0061
            long[] r0 = com.onesignal.OSUtils.parseVibrationPattern(r6)
            if (r0 == 0) goto L_0x0063
            r7.setVibrate(r0)
            goto L_0x0063
        L_0x0061:
            r1 = r1 | 2
        L_0x0063:
            boolean r0 = isSoundEnabled(r6)
            if (r0 == 0) goto L_0x007e
            android.content.Context r0 = currentContext
            r2 = 0
            java.lang.String r3 = "sound"
            java.lang.String r6 = r6.optString(r3, r2)
            android.net.Uri r6 = com.onesignal.OSUtils.getSoundUri(r0, r6)
            if (r6 == 0) goto L_0x007c
            r7.setSound(r6)
            goto L_0x007e
        L_0x007c:
            r1 = r1 | 1
        L_0x007e:
            r7.setDefaults(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.GenerateNotification.setAlertnessOptions(org.json.JSONObject, androidx.core.app.NotificationCompat$Builder):void");
    }

    private static void removeNotifyOptions(NotificationCompat.Builder builder) {
        builder.setOnlyAlertOnce(true).setDefaults(0).setSound((Uri) null).setVibrate((long[]) null).setTicker((CharSequence) null);
    }

    private static void showNotification(NotificationGenerationJob notificationGenerationJob) {
        Notification notification;
        int intValue = notificationGenerationJob.getAndroidId().intValue();
        JSONObject jSONObject = notificationGenerationJob.jsonPayload;
        String optString = jSONObject.optString("grp", (String) null);
        ArrayList<StatusBarNotification> arrayList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 24) {
            arrayList = OneSignalNotificationManager.getActiveGrouplessNotifications(currentContext);
            if (optString == null && arrayList.size() >= 3) {
                optString = OneSignalNotificationManager.getGrouplessSummaryKey();
                OneSignalNotificationManager.assignGrouplessNotifications(currentContext, arrayList);
            }
        }
        OneSignalNotificationBuilder baseOneSignalNotificationBuilder = getBaseOneSignalNotificationBuilder(notificationGenerationJob);
        NotificationCompat.Builder builder = baseOneSignalNotificationBuilder.compatBuilder;
        addNotificationActionButtons(jSONObject, builder, intValue, (String) null);
        try {
            addBackgroundImage(jSONObject, builder);
        } catch (Throwable th) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Could not set background notification image!", th);
        }
        applyNotificationExtender(notificationGenerationJob, builder);
        if (notificationGenerationJob.restoring) {
            removeNotifyOptions(builder);
        }
        NotificationLimitManager.clearOldestOverLimit(currentContext, optString != null ? 2 : 1);
        if (optString != null) {
            createGenericPendingIntentsForGroup(builder, jSONObject, optString, intValue);
            notification = createSingleNotificationBeforeSummaryBuilder(notificationGenerationJob, builder);
            if (Build.VERSION.SDK_INT < 24 || !optString.equals(OneSignalNotificationManager.getGrouplessSummaryKey())) {
                createSummaryNotification(notificationGenerationJob, baseOneSignalNotificationBuilder);
            } else {
                createGrouplessSummaryNotification(notificationGenerationJob, arrayList.size() + 1);
            }
        } else {
            notification = createGenericPendingIntentsForNotif(builder, jSONObject, intValue);
        }
        if (optString == null || Build.VERSION.SDK_INT > 17) {
            addXiaomiSettings(baseOneSignalNotificationBuilder, notification);
            NotificationManagerCompat.from(currentContext).notify(intValue, notification);
        }
    }

    private static Notification createGenericPendingIntentsForNotif(NotificationCompat.Builder builder, JSONObject jSONObject, int i) {
        SecureRandom secureRandom = new SecureRandom();
        builder.setContentIntent(getNewActionPendingIntent(secureRandom.nextInt(), getNewBaseIntent(i).putExtra(BUNDLE_KEY_ONESIGNAL_DATA, jSONObject.toString())));
        builder.setDeleteIntent(getNewActionPendingIntent(secureRandom.nextInt(), getNewBaseDeleteIntent(i)));
        return builder.build();
    }

    private static void createGenericPendingIntentsForGroup(NotificationCompat.Builder builder, JSONObject jSONObject, String str, int i) {
        SecureRandom secureRandom = new SecureRandom();
        builder.setContentIntent(getNewActionPendingIntent(secureRandom.nextInt(), getNewBaseIntent(i).putExtra(BUNDLE_KEY_ONESIGNAL_DATA, jSONObject.toString()).putExtra("grp", str)));
        builder.setDeleteIntent(getNewActionPendingIntent(secureRandom.nextInt(), getNewBaseDeleteIntent(i).putExtra("grp", str)));
        builder.setGroup(str);
        try {
            builder.setGroupAlertBehavior(1);
        } catch (Throwable unused) {
        }
    }

    private static void applyNotificationExtender(NotificationGenerationJob notificationGenerationJob, NotificationCompat.Builder builder) {
        if (notificationGenerationJob.overrideSettings != null && notificationGenerationJob.overrideSettings.extender != null) {
            try {
                Field declaredField = NotificationCompat.Builder.class.getDeclaredField("mNotification");
                declaredField.setAccessible(true);
                Notification notification = (Notification) declaredField.get(builder);
                notificationGenerationJob.orgFlags = Integer.valueOf(notification.flags);
                notificationGenerationJob.orgSound = notification.sound;
                builder.extend(notificationGenerationJob.overrideSettings.extender);
                Notification notification2 = (Notification) declaredField.get(builder);
                Field declaredField2 = NotificationCompat.Builder.class.getDeclaredField("mContentText");
                declaredField2.setAccessible(true);
                Field declaredField3 = NotificationCompat.Builder.class.getDeclaredField("mContentTitle");
                declaredField3.setAccessible(true);
                notificationGenerationJob.overriddenBodyFromExtender = (CharSequence) declaredField2.get(builder);
                notificationGenerationJob.overriddenTitleFromExtender = (CharSequence) declaredField3.get(builder);
                if (!notificationGenerationJob.restoring) {
                    notificationGenerationJob.overriddenFlags = Integer.valueOf(notification2.flags);
                    notificationGenerationJob.overriddenSound = notification2.sound;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
    }

    private static Notification createSingleNotificationBeforeSummaryBuilder(NotificationGenerationJob notificationGenerationJob, NotificationCompat.Builder builder) {
        boolean z = Build.VERSION.SDK_INT > 17 && Build.VERSION.SDK_INT < 24 && !notificationGenerationJob.restoring;
        if (z && notificationGenerationJob.overriddenSound != null && !notificationGenerationJob.overriddenSound.equals(notificationGenerationJob.orgSound)) {
            builder.setSound((Uri) null);
        }
        Notification build = builder.build();
        if (z) {
            builder.setSound(notificationGenerationJob.overriddenSound);
        }
        return build;
    }

    private static void addXiaomiSettings(OneSignalNotificationBuilder oneSignalNotificationBuilder, Notification notification) {
        if (oneSignalNotificationBuilder.hasLargeIcon) {
            try {
                Object newInstance = Class.forName("android.app.MiuiNotification").newInstance();
                Field declaredField = newInstance.getClass().getDeclaredField("customizedIcon");
                declaredField.setAccessible(true);
                declaredField.set(newInstance, true);
                Field field = notification.getClass().getField("extraNotification");
                field.setAccessible(true);
                field.set(notification, newInstance);
            } catch (Throwable unused) {
            }
        }
    }

    static void updateSummaryNotification(NotificationGenerationJob notificationGenerationJob) {
        setStatics(notificationGenerationJob.context);
        createSummaryNotification(notificationGenerationJob, (OneSignalNotificationBuilder) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x008b A[SYNTHETIC, Splitter:B:22:0x008b] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x0134  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0146  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void createSummaryNotification(com.onesignal.NotificationGenerationJob r24, com.onesignal.GenerateNotification.OneSignalNotificationBuilder r25) {
        /*
            r1 = r24
            r2 = r25
            java.lang.String r0 = "message"
            java.lang.String r3 = "title"
            java.lang.String r4 = "is_summary"
            java.lang.String r5 = "full_data"
            java.lang.String r6 = "android_notification_id"
            boolean r7 = r1.restoring
            org.json.JSONObject r8 = r1.jsonPayload
            java.lang.String r9 = "grp"
            r10 = 0
            java.lang.String r9 = r8.optString(r9, r10)
            java.security.SecureRandom r11 = new java.security.SecureRandom
            r11.<init>()
            int r12 = r11.nextInt()
            r13 = 0
            android.content.Intent r14 = getNewBaseDeleteIntent(r13)
            java.lang.String r15 = "summary"
            android.content.Intent r14 = r14.putExtra(r15, r9)
            android.app.PendingIntent r12 = getNewActionPendingIntent(r12, r14)
            android.content.Context r14 = currentContext
            com.onesignal.OneSignalDbHelper r14 = com.onesignal.OneSignalDbHelper.getInstance(r14)
            java.lang.String[] r17 = new java.lang.String[]{r6, r5, r4, r3, r0}     // Catch:{ all -> 0x02e3 }
            java.lang.String r15 = "group_id = ? AND dismissed = 0 AND opened = 0"
            r10 = 1
            r23 = r8
            java.lang.String[] r8 = new java.lang.String[r10]     // Catch:{ all -> 0x02df }
            r8[r13] = r9     // Catch:{ all -> 0x02df }
            if (r7 != 0) goto L_0x0070
            java.lang.Integer r16 = r24.getAndroidId()     // Catch:{ all -> 0x006c }
            int r13 = r16.intValue()     // Catch:{ all -> 0x006c }
            r10 = -1
            if (r13 == r10) goto L_0x0070
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x006c }
            r10.<init>()     // Catch:{ all -> 0x006c }
            r10.append(r15)     // Catch:{ all -> 0x006c }
            java.lang.String r13 = " AND android_notification_id <> "
            r10.append(r13)     // Catch:{ all -> 0x006c }
            java.lang.Integer r13 = r24.getAndroidId()     // Catch:{ all -> 0x006c }
            r10.append(r13)     // Catch:{ all -> 0x006c }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x006c }
            r18 = r10
            goto L_0x0072
        L_0x006c:
            r0 = move-exception
            r10 = 0
            goto L_0x02e5
        L_0x0070:
            r18 = r15
        L_0x0072:
            java.lang.String r16 = "notification"
            r20 = 0
            r21 = 0
            java.lang.String r22 = "_id DESC"
            r15 = r14
            r19 = r8
            android.database.Cursor r8 = r15.query(r16, r17, r18, r19, r20, r21, r22)     // Catch:{ all -> 0x02df }
            boolean r10 = r8.moveToFirst()     // Catch:{ all -> 0x02dc }
            java.lang.String r13 = " "
            java.lang.String r15 = ""
            if (r10 == 0) goto L_0x0134
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ all -> 0x02dc }
            r10.<init>()     // Catch:{ all -> 0x02dc }
            r16 = 0
            r17 = 0
        L_0x0094:
            int r2 = r8.getColumnIndex(r4)     // Catch:{ all -> 0x02dc }
            int r2 = r8.getInt(r2)     // Catch:{ all -> 0x02dc }
            r18 = r4
            r4 = 1
            if (r2 != r4) goto L_0x00b4
            int r2 = r8.getColumnIndex(r6)     // Catch:{ all -> 0x02dc }
            int r2 = r8.getInt(r2)     // Catch:{ all -> 0x02dc }
            java.lang.Integer r17 = java.lang.Integer.valueOf(r2)     // Catch:{ all -> 0x02dc }
            r19 = r0
            r20 = r3
        L_0x00b1:
            r0 = r16
            goto L_0x0111
        L_0x00b4:
            int r2 = r8.getColumnIndex(r3)     // Catch:{ all -> 0x02dc }
            java.lang.String r2 = r8.getString(r2)     // Catch:{ all -> 0x02dc }
            if (r2 != 0) goto L_0x00c0
            r2 = r15
            goto L_0x00cf
        L_0x00c0:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x02dc }
            r4.<init>()     // Catch:{ all -> 0x02dc }
            r4.append(r2)     // Catch:{ all -> 0x02dc }
            r4.append(r13)     // Catch:{ all -> 0x02dc }
            java.lang.String r2 = r4.toString()     // Catch:{ all -> 0x02dc }
        L_0x00cf:
            int r4 = r8.getColumnIndex(r0)     // Catch:{ all -> 0x02dc }
            java.lang.String r4 = r8.getString(r4)     // Catch:{ all -> 0x02dc }
            r19 = r0
            android.text.SpannableString r0 = new android.text.SpannableString     // Catch:{ all -> 0x02dc }
            r20 = r3
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x02dc }
            r3.<init>()     // Catch:{ all -> 0x02dc }
            r3.append(r2)     // Catch:{ all -> 0x02dc }
            r3.append(r4)     // Catch:{ all -> 0x02dc }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x02dc }
            r0.<init>(r3)     // Catch:{ all -> 0x02dc }
            int r3 = r2.length()     // Catch:{ all -> 0x02dc }
            if (r3 <= 0) goto L_0x0103
            android.text.style.StyleSpan r3 = new android.text.style.StyleSpan     // Catch:{ all -> 0x02dc }
            r4 = 1
            r3.<init>(r4)     // Catch:{ all -> 0x02dc }
            int r2 = r2.length()     // Catch:{ all -> 0x02dc }
            r4 = 0
            r0.setSpan(r3, r4, r2, r4)     // Catch:{ all -> 0x02dc }
        L_0x0103:
            r10.add(r0)     // Catch:{ all -> 0x02dc }
            if (r16 != 0) goto L_0x00b1
            int r0 = r8.getColumnIndex(r5)     // Catch:{ all -> 0x02dc }
            java.lang.String r16 = r8.getString(r0)     // Catch:{ all -> 0x02dc }
            goto L_0x00b1
        L_0x0111:
            boolean r2 = r8.moveToNext()     // Catch:{ all -> 0x02dc }
            if (r2 != 0) goto L_0x0128
            if (r7 == 0) goto L_0x0125
            if (r0 == 0) goto L_0x0125
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0121 }
            r2.<init>(r0)     // Catch:{ JSONException -> 0x0121 }
            goto L_0x0139
        L_0x0121:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x02dc }
        L_0x0125:
            r2 = r23
            goto L_0x0139
        L_0x0128:
            r2 = r25
            r16 = r0
            r4 = r18
            r0 = r19
            r3 = r20
            goto L_0x0094
        L_0x0134:
            r2 = r23
            r10 = 0
            r17 = 0
        L_0x0139:
            if (r8 == 0) goto L_0x0144
            boolean r0 = r8.isClosed()
            if (r0 != 0) goto L_0x0144
            r8.close()
        L_0x0144:
            if (r17 != 0) goto L_0x0155
            int r0 = r11.nextInt()
            java.lang.Integer r17 = java.lang.Integer.valueOf(r0)
            int r0 = r17.intValue()
            createSummaryIdDatabaseEntry(r14, r9, r0)
        L_0x0155:
            int r0 = r11.nextInt()
            int r3 = r17.intValue()
            android.content.Intent r3 = createBaseSummaryIntent(r3, r2, r9)
            android.app.PendingIntent r0 = getNewActionPendingIntent(r0, r3)
            if (r10 == 0) goto L_0x029b
            if (r7 == 0) goto L_0x0170
            int r3 = r10.size()
            r4 = 1
            if (r3 > r4) goto L_0x0178
        L_0x0170:
            if (r7 != 0) goto L_0x029b
            int r3 = r10.size()
            if (r3 <= 0) goto L_0x029b
        L_0x0178:
            int r3 = r10.size()
            r4 = r7 ^ 1
            int r3 = r3 + r4
            java.lang.String r4 = "grp_msg"
            r5 = 0
            java.lang.String r2 = r2.optString(r4, r5)
            if (r2 != 0) goto L_0x019a
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r3)
            java.lang.String r4 = " new messages"
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            goto L_0x01af
        L_0x019a:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r15)
            r4.append(r3)
            java.lang.String r4 = r4.toString()
            java.lang.String r6 = "$[notif_count]"
            java.lang.String r2 = r2.replace(r6, r4)
        L_0x01af:
            com.onesignal.GenerateNotification$OneSignalNotificationBuilder r4 = getBaseOneSignalNotificationBuilder(r24)
            androidx.core.app.NotificationCompat$Builder r4 = r4.compatBuilder
            if (r7 == 0) goto L_0x01bb
            removeNotifyOptions(r4)
            goto L_0x01d1
        L_0x01bb:
            android.net.Uri r6 = r1.overriddenSound
            if (r6 == 0) goto L_0x01c4
            android.net.Uri r6 = r1.overriddenSound
            r4.setSound(r6)
        L_0x01c4:
            java.lang.Integer r6 = r1.overriddenFlags
            if (r6 == 0) goto L_0x01d1
            java.lang.Integer r6 = r1.overriddenFlags
            int r6 = r6.intValue()
            r4.setDefaults(r6)
        L_0x01d1:
            androidx.core.app.NotificationCompat$Builder r0 = r4.setContentIntent(r0)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setDeleteIntent(r12)
            android.content.Context r6 = currentContext
            android.content.pm.PackageManager r6 = r6.getPackageManager()
            android.content.Context r8 = currentContext
            android.content.pm.ApplicationInfo r8 = r8.getApplicationInfo()
            java.lang.CharSequence r6 = r6.getApplicationLabel(r8)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setContentTitle(r6)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setContentText(r2)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setNumber(r3)
            int r3 = getDefaultSmallIconId()
            androidx.core.app.NotificationCompat$Builder r0 = r0.setSmallIcon(r3)
            android.graphics.Bitmap r3 = getDefaultLargeIcon()
            androidx.core.app.NotificationCompat$Builder r0 = r0.setLargeIcon(r3)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setOnlyAlertOnce(r7)
            r3 = 0
            androidx.core.app.NotificationCompat$Builder r0 = r0.setAutoCancel(r3)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setGroup(r9)
            r3 = 1
            r0.setGroupSummary(r3)
            r4.setGroupAlertBehavior(r3)     // Catch:{ all -> 0x021a }
            goto L_0x021b
        L_0x021a:
        L_0x021b:
            if (r7 != 0) goto L_0x0220
            r4.setTicker(r2)
        L_0x0220:
            androidx.core.app.NotificationCompat$InboxStyle r0 = new androidx.core.app.NotificationCompat$InboxStyle
            r0.<init>()
            if (r7 != 0) goto L_0x027c
            java.lang.CharSequence r3 = r24.getTitle()
            if (r3 == 0) goto L_0x0236
            java.lang.CharSequence r3 = r24.getTitle()
            java.lang.String r3 = r3.toString()
            goto L_0x0237
        L_0x0236:
            r3 = r5
        L_0x0237:
            if (r3 != 0) goto L_0x023a
            goto L_0x0249
        L_0x023a:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r3)
            r5.append(r13)
            java.lang.String r15 = r5.toString()
        L_0x0249:
            java.lang.CharSequence r1 = r24.getBody()
            java.lang.String r1 = r1.toString()
            android.text.SpannableString r3 = new android.text.SpannableString
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r5.append(r15)
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            r3.<init>(r1)
            int r1 = r15.length()
            if (r1 <= 0) goto L_0x0279
            android.text.style.StyleSpan r1 = new android.text.style.StyleSpan
            r5 = 1
            r1.<init>(r5)
            int r5 = r15.length()
            r6 = 0
            r3.setSpan(r1, r6, r5, r6)
        L_0x0279:
            r0.addLine(r3)
        L_0x027c:
            java.util.Iterator r1 = r10.iterator()
        L_0x0280:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0290
            java.lang.Object r3 = r1.next()
            android.text.SpannableString r3 = (android.text.SpannableString) r3
            r0.addLine(r3)
            goto L_0x0280
        L_0x0290:
            r0.setBigContentTitle(r2)
            r4.setStyle(r0)
            android.app.Notification r0 = r4.build()
            goto L_0x02ce
        L_0x029b:
            r1 = r25
            androidx.core.app.NotificationCompat$Builder r3 = r1.compatBuilder
            java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r4 = r3.mActions
            r4.clear()
            int r4 = r17.intValue()
            addNotificationActionButtons(r2, r3, r4, r9)
            androidx.core.app.NotificationCompat$Builder r0 = r3.setContentIntent(r0)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setDeleteIntent(r12)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setOnlyAlertOnce(r7)
            r2 = 0
            androidx.core.app.NotificationCompat$Builder r0 = r0.setAutoCancel(r2)
            androidx.core.app.NotificationCompat$Builder r0 = r0.setGroup(r9)
            r2 = 1
            r0.setGroupSummary(r2)
            r3.setGroupAlertBehavior(r2)     // Catch:{ all -> 0x02c7 }
        L_0x02c7:
            android.app.Notification r0 = r3.build()
            addXiaomiSettings(r1, r0)
        L_0x02ce:
            android.content.Context r1 = currentContext
            androidx.core.app.NotificationManagerCompat r1 = androidx.core.app.NotificationManagerCompat.from(r1)
            int r2 = r17.intValue()
            r1.notify(r2, r0)
            return
        L_0x02dc:
            r0 = move-exception
            r10 = r8
            goto L_0x02e5
        L_0x02df:
            r0 = move-exception
            r5 = 0
            r10 = r5
            goto L_0x02e5
        L_0x02e3:
            r0 = move-exception
            r5 = r10
        L_0x02e5:
            if (r10 == 0) goto L_0x02f0
            boolean r1 = r10.isClosed()
            if (r1 != 0) goto L_0x02f0
            r10.close()
        L_0x02f0:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.GenerateNotification.createSummaryNotification(com.onesignal.NotificationGenerationJob, com.onesignal.GenerateNotification$OneSignalNotificationBuilder):void");
    }

    private static void createGrouplessSummaryNotification(NotificationGenerationJob notificationGenerationJob, int i) {
        JSONObject jSONObject = notificationGenerationJob.jsonPayload;
        SecureRandom secureRandom = new SecureRandom();
        String grouplessSummaryKey = OneSignalNotificationManager.getGrouplessSummaryKey();
        String str = i + " new messages";
        int grouplessSummaryId = OneSignalNotificationManager.getGrouplessSummaryId();
        PendingIntent newActionPendingIntent = getNewActionPendingIntent(secureRandom.nextInt(), createBaseSummaryIntent(grouplessSummaryId, jSONObject, grouplessSummaryKey));
        PendingIntent newActionPendingIntent2 = getNewActionPendingIntent(secureRandom.nextInt(), getNewBaseDeleteIntent(0).putExtra("summary", grouplessSummaryKey));
        NotificationCompat.Builder builder = getBaseOneSignalNotificationBuilder(notificationGenerationJob).compatBuilder;
        if (notificationGenerationJob.overriddenSound != null) {
            builder.setSound(notificationGenerationJob.overriddenSound);
        }
        if (notificationGenerationJob.overriddenFlags != null) {
            builder.setDefaults(notificationGenerationJob.overriddenFlags.intValue());
        }
        builder.setContentIntent(newActionPendingIntent).setDeleteIntent(newActionPendingIntent2).setContentTitle(currentContext.getPackageManager().getApplicationLabel(currentContext.getApplicationInfo())).setContentText(str).setNumber(i).setSmallIcon(getDefaultSmallIconId()).setLargeIcon(getDefaultLargeIcon()).setOnlyAlertOnce(true).setAutoCancel(false).setGroup(grouplessSummaryKey).setGroupSummary(true);
        try {
            builder.setGroupAlertBehavior(1);
        } catch (Throwable unused) {
        }
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(str);
        builder.setStyle(inboxStyle);
        NotificationManagerCompat.from(currentContext).notify(grouplessSummaryId, builder.build());
    }

    private static Intent createBaseSummaryIntent(int i, JSONObject jSONObject, String str) {
        return getNewBaseIntent(i).putExtra(BUNDLE_KEY_ONESIGNAL_DATA, jSONObject.toString()).putExtra("summary", str);
    }

    private static void createSummaryIdDatabaseEntry(OneSignalDbHelper oneSignalDbHelper, String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(OneSignalDbContract.NotificationTable.COLUMN_NAME_ANDROID_NOTIFICATION_ID, Integer.valueOf(i));
        contentValues.put(OneSignalDbContract.NotificationTable.COLUMN_NAME_GROUP_ID, str);
        contentValues.put(OneSignalDbContract.NotificationTable.COLUMN_NAME_IS_SUMMARY, 1);
        oneSignalDbHelper.insertOrThrow(OneSignalDbContract.NotificationTable.TABLE_NAME, (String) null, contentValues);
    }

    private static void addBackgroundImage(JSONObject jSONObject, NotificationCompat.Builder builder) throws Throwable {
        JSONObject jSONObject2;
        Bitmap bitmap;
        String str;
        if (Build.VERSION.SDK_INT >= 16) {
            String optString = jSONObject.optString("bg_img", (String) null);
            if (optString != null) {
                jSONObject2 = new JSONObject(optString);
                bitmap = getBitmap(jSONObject2.optString("img", (String) null));
            } else {
                bitmap = null;
                jSONObject2 = null;
            }
            if (bitmap == null) {
                bitmap = getBitmapFromAssetsOrResourceName("onesignal_bgimage_default_image");
            }
            if (bitmap != null) {
                RemoteViews remoteViews = new RemoteViews(currentContext.getPackageName(), C2410R.layout.onesignal_bgimage_notif_layout);
                remoteViews.setTextViewText(C2410R.C2413id.os_bgimage_notif_title, getTitle(jSONObject));
                remoteViews.setTextViewText(C2410R.C2413id.os_bgimage_notif_body, jSONObject.optString("alert"));
                setTextColor(remoteViews, jSONObject2, C2410R.C2413id.os_bgimage_notif_title, "tc", "onesignal_bgimage_notif_title_color");
                setTextColor(remoteViews, jSONObject2, C2410R.C2413id.os_bgimage_notif_body, "bc", "onesignal_bgimage_notif_body_color");
                if (jSONObject2 == null || !jSONObject2.has("img_align")) {
                    int identifier = contextResources.getIdentifier("onesignal_bgimage_notif_image_align", "string", packageName);
                    str = identifier != 0 ? contextResources.getString(identifier) : null;
                } else {
                    str = jSONObject2.getString("img_align");
                }
                if ("right".equals(str)) {
                    remoteViews.setViewPadding(C2410R.C2413id.os_bgimage_notif_bgimage_align_layout, -5000, 0, 0, 0);
                    remoteViews.setImageViewBitmap(C2410R.C2413id.os_bgimage_notif_bgimage_right_aligned, bitmap);
                    remoteViews.setViewVisibility(C2410R.C2413id.os_bgimage_notif_bgimage_right_aligned, 0);
                    remoteViews.setViewVisibility(C2410R.C2413id.os_bgimage_notif_bgimage, 2);
                } else {
                    remoteViews.setImageViewBitmap(C2410R.C2413id.os_bgimage_notif_bgimage, bitmap);
                }
                builder.setContent(remoteViews);
                builder.setStyle((NotificationCompat.Style) null);
            }
        }
    }

    private static void setTextColor(RemoteViews remoteViews, JSONObject jSONObject, int i, String str, String str2) {
        Integer safeGetColorFromHex = safeGetColorFromHex(jSONObject, str);
        if (safeGetColorFromHex != null) {
            remoteViews.setTextColor(i, safeGetColorFromHex.intValue());
            return;
        }
        int identifier = contextResources.getIdentifier(str2, "color", packageName);
        if (identifier != 0) {
            remoteViews.setTextColor(i, AndroidSupportV4Compat.ContextCompat.getColor(currentContext, identifier));
        }
    }

    private static Integer safeGetColorFromHex(JSONObject jSONObject, String str) {
        if (jSONObject == null) {
            return null;
        }
        try {
            if (jSONObject.has(str)) {
                return Integer.valueOf(new BigInteger(jSONObject.optString(str), 16).intValue());
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    private static Bitmap getLargeIcon(JSONObject jSONObject) {
        Bitmap bitmap = getBitmap(jSONObject.optString("licon"));
        if (bitmap == null) {
            bitmap = getBitmapFromAssetsOrResourceName("ic_onesignal_large_icon_default");
        }
        if (bitmap == null) {
            return null;
        }
        return resizeBitmapForLargeIconArea(bitmap);
    }

    private static Bitmap getDefaultLargeIcon() {
        return resizeBitmapForLargeIconArea(getBitmapFromAssetsOrResourceName("ic_onesignal_large_icon_default"));
    }

    private static Bitmap resizeBitmapForLargeIconArea(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            int dimension = (int) contextResources.getDimension(17104902);
            int dimension2 = (int) contextResources.getDimension(17104901);
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            if (width <= dimension2 && height <= dimension) {
                return bitmap;
            }
            if (height > width) {
                dimension2 = (int) (((float) dimension) * (((float) width) / ((float) height)));
            } else if (width > height) {
                dimension = (int) (((float) dimension2) * (((float) height) / ((float) width)));
            }
            return Bitmap.createScaledBitmap(bitmap, dimension2, dimension, true);
        } catch (Throwable unused) {
            return bitmap;
        }
    }

    private static Bitmap getBitmapFromAssetsOrResourceName(String str) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(currentContext.getAssets().open(str));
        } catch (Throwable unused) {
            bitmap = null;
        }
        if (bitmap != null) {
            return bitmap;
        }
        try {
            for (String str2 : Arrays.asList(new String[]{".png", ".webp", ".jpg", ".gif", ".bmp"})) {
                try {
                    bitmap = BitmapFactory.decodeStream(currentContext.getAssets().open(str + str2));
                    continue;
                } catch (Throwable unused2) {
                }
                if (bitmap != null) {
                    return bitmap;
                }
            }
            int resourceIcon = getResourceIcon(str);
            if (resourceIcon != 0) {
                return BitmapFactory.decodeResource(contextResources, resourceIcon);
            }
        } catch (Throwable unused3) {
        }
        return null;
    }

    private static Bitmap getBitmapFromURL(String str) {
        try {
            return BitmapFactory.decodeStream(new URL(str).openConnection().getInputStream());
        } catch (Throwable th) {
            OneSignal.Log(OneSignal.LOG_LEVEL.WARN, "Could not download image!", th);
            return null;
        }
    }

    private static Bitmap getBitmap(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.startsWith("http://") || trim.startsWith("https://")) {
            return getBitmapFromURL(trim);
        }
        return getBitmapFromAssetsOrResourceName(str);
    }

    private static int getResourceIcon(String str) {
        if (str == null) {
            return 0;
        }
        String trim = str.trim();
        if (!OSUtils.isValidResourceName(trim)) {
            return 0;
        }
        int drawableId = getDrawableId(trim);
        if (drawableId != 0) {
            return drawableId;
        }
        try {
            return R.drawable.class.getField(str).getInt((Object) null);
        } catch (Throwable unused) {
            return 0;
        }
    }

    private static int getSmallIconId(JSONObject jSONObject) {
        int resourceIcon = getResourceIcon(jSONObject.optString("sicon", (String) null));
        if (resourceIcon != 0) {
            return resourceIcon;
        }
        return getDefaultSmallIconId();
    }

    private static int getDefaultSmallIconId() {
        int drawableId = getDrawableId("ic_stat_onesignal_default");
        if (drawableId != 0) {
            return drawableId;
        }
        int drawableId2 = getDrawableId("corona_statusbar_icon_default");
        if (drawableId2 != 0) {
            return drawableId2;
        }
        int drawableId3 = getDrawableId("ic_os_notification_fallback_white_24dp");
        if (drawableId3 != 0) {
            return drawableId3;
        }
        return 17301598;
    }

    private static int getDrawableId(String str) {
        return contextResources.getIdentifier(str, "drawable", packageName);
    }

    private static boolean isSoundEnabled(JSONObject jSONObject) {
        String optString = jSONObject.optString("sound", (String) null);
        if ("null".equals(optString) || "nil".equals(optString)) {
            return false;
        }
        return OneSignal.getSoundEnabled();
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0015 */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001f A[Catch:{ all -> 0x0025 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.math.BigInteger getAccentColor(org.json.JSONObject r4) {
        /*
            java.lang.String r0 = "bgac"
            r1 = 16
            r2 = 0
            boolean r3 = r4.has(r0)     // Catch:{ all -> 0x0015 }
            if (r3 == 0) goto L_0x0015
            java.math.BigInteger r3 = new java.math.BigInteger     // Catch:{ all -> 0x0015 }
            java.lang.String r4 = r4.optString(r0, r2)     // Catch:{ all -> 0x0015 }
            r3.<init>(r4, r1)     // Catch:{ all -> 0x0015 }
            return r3
        L_0x0015:
            android.content.Context r4 = currentContext     // Catch:{ all -> 0x0025 }
            java.lang.String r0 = "com.onesignal.NotificationAccentColor.DEFAULT"
            java.lang.String r4 = com.onesignal.OSUtils.getManifestMeta(r4, r0)     // Catch:{ all -> 0x0025 }
            if (r4 == 0) goto L_0x0025
            java.math.BigInteger r0 = new java.math.BigInteger     // Catch:{ all -> 0x0025 }
            r0.<init>(r4, r1)     // Catch:{ all -> 0x0025 }
            return r0
        L_0x0025:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.onesignal.GenerateNotification.getAccentColor(org.json.JSONObject):java.math.BigInteger");
    }

    private static void addNotificationActionButtons(JSONObject jSONObject, NotificationCompat.Builder builder, int i, String str) {
        try {
            JSONObject jSONObject2 = new JSONObject(jSONObject.optString(OSNotificationFormatHelper.PAYLOAD_OS_ROOT_CUSTOM));
            if (jSONObject2.has(NotificationBundleProcessor.PUSH_ADDITIONAL_DATA_KEY)) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject(NotificationBundleProcessor.PUSH_ADDITIONAL_DATA_KEY);
                if (jSONObject3.has("actionButtons")) {
                    JSONArray jSONArray = jSONObject3.getJSONArray("actionButtons");
                    for (int i2 = 0; i2 < jSONArray.length(); i2++) {
                        JSONObject optJSONObject = jSONArray.optJSONObject(i2);
                        JSONObject jSONObject4 = new JSONObject(jSONObject.toString());
                        Intent newBaseIntent = getNewBaseIntent(i);
                        newBaseIntent.setAction("" + i2);
                        newBaseIntent.putExtra("action_button", true);
                        jSONObject4.put(BUNDLE_KEY_ACTION_ID, optJSONObject.optString("id"));
                        newBaseIntent.putExtra(BUNDLE_KEY_ONESIGNAL_DATA, jSONObject4.toString());
                        if (str != null) {
                            newBaseIntent.putExtra("summary", str);
                        } else if (jSONObject.has("grp")) {
                            newBaseIntent.putExtra("grp", jSONObject.optString("grp"));
                        }
                        builder.addAction(optJSONObject.has("icon") ? getResourceIcon(optJSONObject.optString("icon")) : 0, optJSONObject.optString("text"), getNewActionPendingIntent(i, newBaseIntent));
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public static void addAlertButtons(Context context, JSONObject jSONObject, List<String> list, List<String> list2) {
        try {
            addCustomAlertButtons(context, jSONObject, list, list2);
        } catch (Throwable th) {
            OneSignal.Log(OneSignal.LOG_LEVEL.ERROR, "Failed to parse JSON for custom buttons for alert dialog.", th);
        }
        if (list.size() == 0 || list.size() < 3) {
            list.add(OSUtils.getResourceString(context, "onesignal_in_app_alert_ok_button_text", "Ok"));
            list2.add("__DEFAULT__");
        }
    }

    private static void addCustomAlertButtons(Context context, JSONObject jSONObject, List<String> list, List<String> list2) throws JSONException {
        JSONObject jSONObject2 = new JSONObject(jSONObject.optString(OSNotificationFormatHelper.PAYLOAD_OS_ROOT_CUSTOM));
        if (jSONObject2.has(NotificationBundleProcessor.PUSH_ADDITIONAL_DATA_KEY)) {
            JSONObject jSONObject3 = jSONObject2.getJSONObject(NotificationBundleProcessor.PUSH_ADDITIONAL_DATA_KEY);
            if (jSONObject3.has("actionButtons")) {
                JSONArray optJSONArray = jSONObject3.optJSONArray("actionButtons");
                for (int i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject4 = optJSONArray.getJSONObject(i);
                    list.add(jSONObject4.optString("text"));
                    list2.add(jSONObject4.optString("id"));
                }
            }
        }
    }
}
