package com.oblador.performance;

import android.os.Process;
import android.os.SystemClock;
import android.system.Os;
import android.system.OsConstants;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class PerformanceModule extends ReactContextBaseJavaModule {
    public static final String BRIDGE_SETUP_START = "bridgeSetupStart";
    private static final long MODULE_INITIALIZED_AT = SystemClock.uptimeMillis();
    public static final String PERFORMANCE_MODULE = "RNPerformanceManager";
    private static Map<String, Long> markBuffer = new HashMap();
    private boolean eventsBuffered = true;

    public String getName() {
        return PERFORMANCE_MODULE;
    }

    public PerformanceModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        setupMarkerListener();
    }

    public static void setupListener() {
        ReactMarker.addListener($$Lambda$PerformanceModule$q4gdaM6FhHjHavRn8k5uiGkXl9Y.INSTANCE);
    }

    static /* synthetic */ void lambda$setupListener$0(ReactMarkerConstants reactMarkerConstants, String str, int i) {
        switch (C22881.$SwitchMap$com$facebook$react$bridge$ReactMarkerConstants[reactMarkerConstants.ordinal()]) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
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
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
                markBuffer.put(getMarkName(reactMarkerConstants), Long.valueOf(SystemClock.uptimeMillis()));
                return;
            case 2:
                markBuffer.clear();
                markBuffer.put(BRIDGE_SETUP_START, Long.valueOf(SystemClock.uptimeMillis()));
                return;
            default:
                return;
        }
    }

    private static String getMarkName(ReactMarkerConstants reactMarkerConstants) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : reactMarkerConstants.toString().toLowerCase().split("_")) {
            if (stringBuffer.length() == 0) {
                stringBuffer.append(str);
            } else {
                stringBuffer.append(Character.toUpperCase(str.charAt(0)));
                if (str.length() > 1) {
                    stringBuffer.append(str.substring(1, str.length()));
                }
            }
        }
        return stringBuffer.toString();
    }

    private void emitNativeStartupTime() {
        try {
            safelyEmitMark("nativeLaunchStart", getStartTime(Process.myPid()) - (SystemClock.elapsedRealtime() - SystemClock.uptimeMillis()));
            safelyEmitMark("nativeLaunchEnd", MODULE_INITIALIZED_AT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupMarkerListener() {
        ReactMarker.addListener(new ReactMarker.MarkerListener() {
            public final void logMarker(ReactMarkerConstants reactMarkerConstants, String str, int i) {
                PerformanceModule.this.lambda$setupMarkerListener$1$PerformanceModule(reactMarkerConstants, str, i);
            }
        });
    }

    /* renamed from: com.oblador.performance.PerformanceModule$1 */
    static /* synthetic */ class C22881 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants;

        /* JADX WARNING: Can't wrap try/catch for region: R(64:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|(3:63|64|66)) */
        /* JADX WARNING: Can't wrap try/catch for region: R(66:0|1|2|3|4|5|6|7|8|9|10|11|12|13|14|15|16|17|18|19|20|21|22|23|24|25|26|27|28|29|30|31|32|33|34|35|36|37|38|39|40|41|42|43|44|45|46|47|48|49|50|51|52|53|54|55|56|57|58|59|60|61|62|63|64|66) */
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
        /* JADX WARNING: Missing exception handler attribute for start block: B:55:0x0144 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:57:0x0150 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x015c */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        /* JADX WARNING: Missing exception handler attribute for start block: B:61:0x0168 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:63:0x0174 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0033 */
        static {
            /*
                com.facebook.react.bridge.ReactMarkerConstants[] r0 = com.facebook.react.bridge.ReactMarkerConstants.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants = r0
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CONTENT_APPEARED     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x001d }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.RELOAD     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.ATTACH_MEASURED_ROOT_VIEWS_END     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0033 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.ATTACH_MEASURED_ROOT_VIEWS_START     // Catch:{ NoSuchFieldError -> 0x0033 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0033 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0033 }
            L_0x0033:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x003e }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.BUILD_NATIVE_MODULE_REGISTRY_END     // Catch:{ NoSuchFieldError -> 0x003e }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x003e }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x003e }
            L_0x003e:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0049 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.BUILD_NATIVE_MODULE_REGISTRY_START     // Catch:{ NoSuchFieldError -> 0x0049 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0049 }
                r2 = 6
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0049 }
            L_0x0049:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0054 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_CATALYST_INSTANCE_END     // Catch:{ NoSuchFieldError -> 0x0054 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0054 }
                r2 = 7
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0054 }
            L_0x0054:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0060 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_CATALYST_INSTANCE_START     // Catch:{ NoSuchFieldError -> 0x0060 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0060 }
                r2 = 8
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0060 }
            L_0x0060:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x006c }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_REACT_CONTEXT_END     // Catch:{ NoSuchFieldError -> 0x006c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x006c }
                r2 = 9
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x006c }
            L_0x006c:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0078 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_REACT_CONTEXT_START     // Catch:{ NoSuchFieldError -> 0x0078 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0078 }
                r2 = 10
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0078 }
            L_0x0078:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0084 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_END     // Catch:{ NoSuchFieldError -> 0x0084 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0084 }
                r2 = 11
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0084 }
            L_0x0084:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0090 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_CONSTANTS_START     // Catch:{ NoSuchFieldError -> 0x0090 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0090 }
                r2 = 12
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0090 }
            L_0x0090:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x009c }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_END     // Catch:{ NoSuchFieldError -> 0x009c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x009c }
                r2 = 13
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x009c }
            L_0x009c:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00a8 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_UI_MANAGER_MODULE_START     // Catch:{ NoSuchFieldError -> 0x00a8 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00a8 }
                r2 = 14
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00a8 }
            L_0x00a8:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00b4 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_VIEW_MANAGERS_END     // Catch:{ NoSuchFieldError -> 0x00b4 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00b4 }
                r2 = 15
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00b4 }
            L_0x00b4:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00c0 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.CREATE_VIEW_MANAGERS_START     // Catch:{ NoSuchFieldError -> 0x00c0 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00c0 }
                r2 = 16
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00c0 }
            L_0x00c0:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00cc }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.DOWNLOAD_END     // Catch:{ NoSuchFieldError -> 0x00cc }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00cc }
                r2 = 17
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00cc }
            L_0x00cc:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00d8 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.DOWNLOAD_START     // Catch:{ NoSuchFieldError -> 0x00d8 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00d8 }
                r2 = 18
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00d8 }
            L_0x00d8:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00e4 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.LOAD_REACT_NATIVE_SO_FILE_END     // Catch:{ NoSuchFieldError -> 0x00e4 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00e4 }
                r2 = 19
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00e4 }
            L_0x00e4:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00f0 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.LOAD_REACT_NATIVE_SO_FILE_START     // Catch:{ NoSuchFieldError -> 0x00f0 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00f0 }
                r2 = 20
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00f0 }
            L_0x00f0:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x00fc }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.PRE_RUN_JS_BUNDLE_START     // Catch:{ NoSuchFieldError -> 0x00fc }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x00fc }
                r2 = 21
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x00fc }
            L_0x00fc:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0108 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.PRE_SETUP_REACT_CONTEXT_END     // Catch:{ NoSuchFieldError -> 0x0108 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0108 }
                r2 = 22
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0108 }
            L_0x0108:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0114 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.PRE_SETUP_REACT_CONTEXT_START     // Catch:{ NoSuchFieldError -> 0x0114 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0114 }
                r2 = 23
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0114 }
            L_0x0114:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0120 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_END     // Catch:{ NoSuchFieldError -> 0x0120 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0120 }
                r2 = 24
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0120 }
            L_0x0120:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x012c }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.PROCESS_CORE_REACT_PACKAGE_START     // Catch:{ NoSuchFieldError -> 0x012c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x012c }
                r2 = 25
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x012c }
            L_0x012c:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0138 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.REACT_CONTEXT_THREAD_END     // Catch:{ NoSuchFieldError -> 0x0138 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0138 }
                r2 = 26
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0138 }
            L_0x0138:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0144 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.REACT_CONTEXT_THREAD_START     // Catch:{ NoSuchFieldError -> 0x0144 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0144 }
                r2 = 27
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0144 }
            L_0x0144:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0150 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.RUN_JS_BUNDLE_END     // Catch:{ NoSuchFieldError -> 0x0150 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0150 }
                r2 = 28
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0150 }
            L_0x0150:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x015c }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.RUN_JS_BUNDLE_START     // Catch:{ NoSuchFieldError -> 0x015c }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x015c }
                r2 = 29
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x015c }
            L_0x015c:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0168 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.SETUP_REACT_CONTEXT_END     // Catch:{ NoSuchFieldError -> 0x0168 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0168 }
                r2 = 30
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0168 }
            L_0x0168:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0174 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.SETUP_REACT_CONTEXT_START     // Catch:{ NoSuchFieldError -> 0x0174 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0174 }
                r2 = 31
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0174 }
            L_0x0174:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReactMarkerConstants     // Catch:{ NoSuchFieldError -> 0x0180 }
                com.facebook.react.bridge.ReactMarkerConstants r1 = com.facebook.react.bridge.ReactMarkerConstants.VM_INIT     // Catch:{ NoSuchFieldError -> 0x0180 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0180 }
                r2 = 32
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0180 }
            L_0x0180:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.oblador.performance.PerformanceModule.C22881.<clinit>():void");
        }
    }

    public /* synthetic */ void lambda$setupMarkerListener$1$PerformanceModule(ReactMarkerConstants reactMarkerConstants, String str, int i) {
        int i2 = C22881.$SwitchMap$com$facebook$react$bridge$ReactMarkerConstants[reactMarkerConstants.ordinal()];
        if (i2 == 1) {
            this.eventsBuffered = false;
            emitNativeStartupTime();
            emitBufferedMarks();
        } else if (i2 == 2) {
            this.eventsBuffered = true;
        }
    }

    private void safelyEmitMark(String str, long j) {
        if (this.eventsBuffered) {
            markBuffer.put(str, Long.valueOf(j));
        } else {
            emitMark(str, j);
        }
    }

    private void emitBufferedMarks() {
        for (Map.Entry next : markBuffer.entrySet()) {
            emitMark((String) next.getKey(), ((Long) next.getValue()).longValue());
        }
    }

    /* JADX INFO: finally extract failed */
    private static long getStartTime(int i) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/" + i + "/stat"));
        try {
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            try {
                return (Long.parseLong(readLine.substring(readLine.lastIndexOf(") ")).split(StringUtils.SPACE)[20]) * 1000) / Os.sysconf(OsConstants._SC_CLK_TCK);
            } catch (Exception e) {
                throw new IOException(e);
            }
        } catch (Throwable th) {
            bufferedReader.close();
            throw th;
        }
    }

    private void emitMark(String str, long j) {
        emit("mark", str, j);
    }

    private void emit(String str, String str2, long j) {
        WritableMap createMap = Arguments.createMap();
        createMap.putString("name", str2);
        createMap.putInt("startTime", (int) j);
        ((DeviceEventManagerModule.RCTDeviceEventEmitter) getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit(str, createMap);
    }
}
