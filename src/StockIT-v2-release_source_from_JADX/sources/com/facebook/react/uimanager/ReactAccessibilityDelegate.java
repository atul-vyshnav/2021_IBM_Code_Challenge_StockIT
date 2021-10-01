package com.facebook.react.uimanager;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.URLSpan;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.facebook.react.C1045R;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactNoCrashSoftException;
import com.facebook.react.bridge.ReactSoftException;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import java.util.HashMap;

public class ReactAccessibilityDelegate extends AccessibilityDelegateCompat {
    private static final int SEND_EVENT = 1;
    private static final String STATE_CHECKED = "checked";
    private static final String STATE_DISABLED = "disabled";
    private static final String STATE_SELECTED = "selected";
    private static final String TAG = "ReactAccessibilityDelegate";
    private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;
    public static final HashMap<String, Integer> sActionIdMap;
    private static int sCounter = 1056964608;
    private final HashMap<Integer, String> mAccessibilityActionsMap = new HashMap<>();
    private Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            ((View) message.obj).sendAccessibilityEvent(4);
        }
    };

    static {
        HashMap<String, Integer> hashMap = new HashMap<>();
        sActionIdMap = hashMap;
        hashMap.put("activate", Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK.getId()));
        sActionIdMap.put("longpress", Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_LONG_CLICK.getId()));
        sActionIdMap.put("increment", Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId()));
        sActionIdMap.put("decrement", Integer.valueOf(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId()));
    }

    private void scheduleAccessibilityEventSender(View view) {
        if (this.mHandler.hasMessages(1, view)) {
            this.mHandler.removeMessages(1, view);
        }
        this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1, view), 200);
    }

    /* renamed from: com.facebook.react.uimanager.ReactAccessibilityDelegate$2 */
    static /* synthetic */ class C12802 {

        /* renamed from: $SwitchMap$com$facebook$react$uimanager$ReactAccessibilityDelegate$AccessibilityRole */
        static final /* synthetic */ int[] f106x27e8253f;

        /* JADX WARNING: Can't wrap try/catch for region: R(54:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|(3:53|54|56)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(56:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|56) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x003e */
        /* JADX WARNING: Missing exception handler attribute for start block: B:13:0x0049 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x0054 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:17:0x0060 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:19:0x006c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0078 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x0084 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x0090 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:27:0x009c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x00a8 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x00b4 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:33:0x00c0 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:35:0x00cc */
        /* JADX WARNING: Missing exception handler attribute for start block: B:37:0x00d8 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:39:0x00e4 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:41:0x00f0 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:43:0x00fc */
        /* JADX WARNING: Missing exception handler attribute for start block: B:45:0x0108 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:47:0x0114 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:49:0x0120 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:51:0x012c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:53:0x0138 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole[] r0 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f106x27e8253f = r0
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.BUTTON     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x001d }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.SEARCH     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.IMAGE     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.IMAGEBUTTON     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x003e }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.KEYBOARDKEY     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.TEXT     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.ADJUSTABLE     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.CHECKBOX     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x006c }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.RADIO     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.SPINBUTTON     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.SWITCH     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0090 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.NONE     // Catch:{ NoSuchFieldError -> 0x0090 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0090 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0090 }
            L_0x0090:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x009c }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.LINK     // Catch:{ NoSuchFieldError -> 0x009c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009c }
                r2 = 13
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009c }
            L_0x009c:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00a8 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.SUMMARY     // Catch:{ NoSuchFieldError -> 0x00a8 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a8 }
                r2 = 14
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00a8 }
            L_0x00a8:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00b4 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.HEADER     // Catch:{ NoSuchFieldError -> 0x00b4 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00b4 }
                r2 = 15
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00b4 }
            L_0x00b4:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00c0 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.ALERT     // Catch:{ NoSuchFieldError -> 0x00c0 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00c0 }
                r2 = 16
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00c0 }
            L_0x00c0:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00cc }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.COMBOBOX     // Catch:{ NoSuchFieldError -> 0x00cc }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00cc }
                r2 = 17
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00cc }
            L_0x00cc:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00d8 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.MENU     // Catch:{ NoSuchFieldError -> 0x00d8 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00d8 }
                r2 = 18
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00d8 }
            L_0x00d8:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00e4 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.MENUBAR     // Catch:{ NoSuchFieldError -> 0x00e4 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00e4 }
                r2 = 19
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00e4 }
            L_0x00e4:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00f0 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.MENUITEM     // Catch:{ NoSuchFieldError -> 0x00f0 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00f0 }
                r2 = 20
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00f0 }
            L_0x00f0:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x00fc }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.PROGRESSBAR     // Catch:{ NoSuchFieldError -> 0x00fc }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00fc }
                r2 = 21
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00fc }
            L_0x00fc:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0108 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.RADIOGROUP     // Catch:{ NoSuchFieldError -> 0x0108 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0108 }
                r2 = 22
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0108 }
            L_0x0108:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0114 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.SCROLLBAR     // Catch:{ NoSuchFieldError -> 0x0114 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0114 }
                r2 = 23
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0114 }
            L_0x0114:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0120 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.TAB     // Catch:{ NoSuchFieldError -> 0x0120 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0120 }
                r2 = 24
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0120 }
            L_0x0120:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x012c }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.TABLIST     // Catch:{ NoSuchFieldError -> 0x012c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x012c }
                r2 = 25
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x012c }
            L_0x012c:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0138 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.TIMER     // Catch:{ NoSuchFieldError -> 0x0138 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0138 }
                r2 = 26
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0138 }
            L_0x0138:
                int[] r0 = f106x27e8253f     // Catch:{ NoSuchFieldError -> 0x0144 }
                com.facebook.react.uimanager.ReactAccessibilityDelegate$AccessibilityRole r1 = com.facebook.react.uimanager.ReactAccessibilityDelegate.AccessibilityRole.TOOLBAR     // Catch:{ NoSuchFieldError -> 0x0144 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0144 }
                r2 = 27
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0144 }
            L_0x0144:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.uimanager.ReactAccessibilityDelegate.C12802.<clinit>():void");
        }
    }

    public enum AccessibilityRole {
        NONE,
        BUTTON,
        LINK,
        SEARCH,
        IMAGE,
        IMAGEBUTTON,
        KEYBOARDKEY,
        TEXT,
        ADJUSTABLE,
        SUMMARY,
        HEADER,
        ALERT,
        CHECKBOX,
        COMBOBOX,
        MENU,
        MENUBAR,
        MENUITEM,
        PROGRESSBAR,
        RADIO,
        RADIOGROUP,
        SCROLLBAR,
        SPINBUTTON,
        SWITCH,
        TAB,
        TABLIST,
        TIMER,
        TOOLBAR;

        public static String getValue(AccessibilityRole accessibilityRole) {
            switch (C12802.f106x27e8253f[accessibilityRole.ordinal()]) {
                case 1:
                    return "android.widget.Button";
                case 2:
                    return "android.widget.EditText";
                case 3:
                    return "android.widget.ImageView";
                case 4:
                    return "android.widget.ImageButon";
                case 5:
                    return "android.inputmethodservice.Keyboard$Key";
                case 6:
                    return "android.widget.TextView";
                case 7:
                    return "android.widget.SeekBar";
                case 8:
                    return "android.widget.CheckBox";
                case 9:
                    return "android.widget.RadioButton";
                case 10:
                    return "android.widget.SpinButton";
                case 11:
                    return "android.widget.Switch";
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                case 20:
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 26:
                case 27:
                    return "android.view.View";
                default:
                    throw new IllegalArgumentException("Invalid accessibility role value: " + accessibilityRole);
            }
        }

        public static AccessibilityRole fromValue(String str) {
            for (AccessibilityRole accessibilityRole : values()) {
                if (accessibilityRole.name().equalsIgnoreCase(str)) {
                    return accessibilityRole;
                }
            }
            throw new IllegalArgumentException("Invalid accessibility role value: " + str);
        }
    }

    public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
        AccessibilityRole accessibilityRole = (AccessibilityRole) view.getTag(C1045R.C1048id.accessibility_role);
        if (accessibilityRole != null) {
            setRole(accessibilityNodeInfoCompat, accessibilityRole, view.getContext());
        }
        ReadableMap readableMap = (ReadableMap) view.getTag(C1045R.C1048id.accessibility_state);
        if (readableMap != null) {
            setState(accessibilityNodeInfoCompat, readableMap, view.getContext());
        }
        ReadableArray readableArray = (ReadableArray) view.getTag(C1045R.C1048id.accessibility_actions);
        if (readableArray != null) {
            int i = 0;
            while (i < readableArray.size()) {
                ReadableMap map = readableArray.getMap(i);
                if (map.hasKey("name")) {
                    int i2 = sCounter;
                    String string = map.hasKey("label") ? map.getString("label") : null;
                    if (sActionIdMap.containsKey(map.getString("name"))) {
                        i2 = sActionIdMap.get(map.getString("name")).intValue();
                    } else {
                        sCounter++;
                    }
                    this.mAccessibilityActionsMap.put(Integer.valueOf(i2), map.getString("name"));
                    accessibilityNodeInfoCompat.addAction(new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i2, string));
                    i++;
                } else {
                    throw new IllegalArgumentException("Unknown accessibility action.");
                }
            }
        }
        ReadableMap readableMap2 = (ReadableMap) view.getTag(C1045R.C1048id.accessibility_value);
        if (readableMap2 != null && readableMap2.hasKey("min") && readableMap2.hasKey("now") && readableMap2.hasKey("max")) {
            Dynamic dynamic = readableMap2.getDynamic("min");
            Dynamic dynamic2 = readableMap2.getDynamic("now");
            Dynamic dynamic3 = readableMap2.getDynamic("max");
            if (dynamic != null && dynamic.getType() == ReadableType.Number && dynamic2 != null && dynamic2.getType() == ReadableType.Number && dynamic3 != null && dynamic3.getType() == ReadableType.Number) {
                int asInt = dynamic.asInt();
                int asInt2 = dynamic2.asInt();
                int asInt3 = dynamic3.asInt();
                if (asInt3 > asInt && asInt2 >= asInt && asInt3 >= asInt2) {
                    accessibilityNodeInfoCompat.setRangeInfo(AccessibilityNodeInfoCompat.RangeInfoCompat.obtain(0, (float) asInt, (float) asInt3, (float) asInt2));
                }
            }
        }
    }

    public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(view, accessibilityEvent);
        ReadableMap readableMap = (ReadableMap) view.getTag(C1045R.C1048id.accessibility_value);
        if (readableMap != null && readableMap.hasKey("min") && readableMap.hasKey("now") && readableMap.hasKey("max")) {
            Dynamic dynamic = readableMap.getDynamic("min");
            Dynamic dynamic2 = readableMap.getDynamic("now");
            Dynamic dynamic3 = readableMap.getDynamic("max");
            if (dynamic != null && dynamic.getType() == ReadableType.Number && dynamic2 != null && dynamic2.getType() == ReadableType.Number && dynamic3 != null && dynamic3.getType() == ReadableType.Number) {
                int asInt = dynamic.asInt();
                int asInt2 = dynamic2.asInt();
                int asInt3 = dynamic3.asInt();
                if (asInt3 > asInt && asInt2 >= asInt && asInt3 >= asInt2) {
                    accessibilityEvent.setItemCount(asInt3 - asInt);
                    accessibilityEvent.setCurrentItemIndex(asInt2);
                }
            }
        }
    }

    public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        if (!this.mAccessibilityActionsMap.containsKey(Integer.valueOf(i))) {
            return super.performAccessibilityAction(view, i, bundle);
        }
        WritableMap createMap = Arguments.createMap();
        createMap.putString("actionName", this.mAccessibilityActionsMap.get(Integer.valueOf(i)));
        ReactContext reactContext = (ReactContext) view.getContext();
        if (reactContext.hasActiveCatalystInstance()) {
            ((RCTEventEmitter) reactContext.getJSModule(RCTEventEmitter.class)).receiveEvent(view.getId(), "topAccessibilityAction", createMap);
        } else {
            ReactSoftException.logSoftException(TAG, new ReactNoCrashSoftException("Cannot get RCTEventEmitter, no CatalystInstance"));
        }
        AccessibilityRole accessibilityRole = (AccessibilityRole) view.getTag(C1045R.C1048id.accessibility_role);
        ReadableMap readableMap = (ReadableMap) view.getTag(C1045R.C1048id.accessibility_value);
        if (accessibilityRole != AccessibilityRole.ADJUSTABLE) {
            return true;
        }
        if (i != AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD.getId() && i != AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD.getId()) {
            return true;
        }
        if (readableMap != null && !readableMap.hasKey("text")) {
            scheduleAccessibilityEventSender(view);
        }
        return super.performAccessibilityAction(view, i, bundle);
    }

    private static void setState(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, ReadableMap readableMap, Context context) {
        ReadableMapKeySetIterator keySetIterator = readableMap.keySetIterator();
        while (keySetIterator.hasNextKey()) {
            String nextKey = keySetIterator.nextKey();
            Dynamic dynamic = readableMap.getDynamic(nextKey);
            if (nextKey.equals(STATE_SELECTED) && dynamic.getType() == ReadableType.Boolean) {
                accessibilityNodeInfoCompat.setSelected(dynamic.asBoolean());
            } else if (nextKey.equals(STATE_DISABLED) && dynamic.getType() == ReadableType.Boolean) {
                accessibilityNodeInfoCompat.setEnabled(!dynamic.asBoolean());
            } else if (nextKey.equals(STATE_CHECKED) && dynamic.getType() == ReadableType.Boolean) {
                boolean asBoolean = dynamic.asBoolean();
                accessibilityNodeInfoCompat.setCheckable(true);
                accessibilityNodeInfoCompat.setChecked(asBoolean);
                if (accessibilityNodeInfoCompat.getClassName().equals(AccessibilityRole.getValue(AccessibilityRole.SWITCH))) {
                    accessibilityNodeInfoCompat.setText(context.getString(asBoolean ? C1045R.string.state_on_description : C1045R.string.state_off_description));
                }
            }
        }
    }

    public static void setRole(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat, AccessibilityRole accessibilityRole, Context context) {
        if (accessibilityRole == null) {
            accessibilityRole = AccessibilityRole.NONE;
        }
        accessibilityNodeInfoCompat.setClassName(AccessibilityRole.getValue(accessibilityRole));
        if (accessibilityRole.equals(AccessibilityRole.LINK)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.link_description));
            if (accessibilityNodeInfoCompat.getContentDescription() != null) {
                SpannableString spannableString = new SpannableString(accessibilityNodeInfoCompat.getContentDescription());
                spannableString.setSpan(new URLSpan(""), 0, spannableString.length(), 0);
                accessibilityNodeInfoCompat.setContentDescription(spannableString);
            }
            if (accessibilityNodeInfoCompat.getText() != null) {
                SpannableString spannableString2 = new SpannableString(accessibilityNodeInfoCompat.getText());
                spannableString2.setSpan(new URLSpan(""), 0, spannableString2.length(), 0);
                accessibilityNodeInfoCompat.setText(spannableString2);
            }
        } else if (accessibilityRole.equals(AccessibilityRole.SEARCH)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.search_description));
        } else if (accessibilityRole.equals(AccessibilityRole.IMAGE)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.image_description));
        } else if (accessibilityRole.equals(AccessibilityRole.IMAGEBUTTON)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.imagebutton_description));
            accessibilityNodeInfoCompat.setClickable(true);
        } else if (accessibilityRole.equals(AccessibilityRole.BUTTON)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.button_description));
            accessibilityNodeInfoCompat.setClickable(true);
        } else if (accessibilityRole.equals(AccessibilityRole.SUMMARY)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.summary_description));
        } else if (accessibilityRole.equals(AccessibilityRole.HEADER)) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(0, 1, 0, 1, true));
        } else if (accessibilityRole.equals(AccessibilityRole.ALERT)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.alert_description));
        } else if (accessibilityRole.equals(AccessibilityRole.COMBOBOX)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.combobox_description));
        } else if (accessibilityRole.equals(AccessibilityRole.MENU)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.menu_description));
        } else if (accessibilityRole.equals(AccessibilityRole.MENUBAR)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.menubar_description));
        } else if (accessibilityRole.equals(AccessibilityRole.MENUITEM)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.menuitem_description));
        } else if (accessibilityRole.equals(AccessibilityRole.PROGRESSBAR)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.progressbar_description));
        } else if (accessibilityRole.equals(AccessibilityRole.RADIOGROUP)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.radiogroup_description));
        } else if (accessibilityRole.equals(AccessibilityRole.SCROLLBAR)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.scrollbar_description));
        } else if (accessibilityRole.equals(AccessibilityRole.SPINBUTTON)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.spinbutton_description));
        } else if (accessibilityRole.equals(AccessibilityRole.TAB)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.rn_tab_description));
        } else if (accessibilityRole.equals(AccessibilityRole.TABLIST)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.tablist_description));
        } else if (accessibilityRole.equals(AccessibilityRole.TIMER)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.timer_description));
        } else if (accessibilityRole.equals(AccessibilityRole.TOOLBAR)) {
            accessibilityNodeInfoCompat.setRoleDescription(context.getString(C1045R.string.toolbar_description));
        }
    }

    public static void setDelegate(View view) {
        if (ViewCompat.hasAccessibilityDelegate(view)) {
            return;
        }
        if (view.getTag(C1045R.C1048id.accessibility_role) != null || view.getTag(C1045R.C1048id.accessibility_state) != null || view.getTag(C1045R.C1048id.accessibility_actions) != null) {
            ViewCompat.setAccessibilityDelegate(view, new ReactAccessibilityDelegate());
        }
    }
}
