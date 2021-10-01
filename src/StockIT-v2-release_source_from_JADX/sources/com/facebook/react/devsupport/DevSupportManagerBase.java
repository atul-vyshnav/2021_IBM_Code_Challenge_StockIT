package com.facebook.react.devsupport;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.amplitude.api.DeviceInfo;
import com.facebook.common.logging.FLog;
import com.facebook.debug.holder.PrinterHolder;
import com.facebook.debug.tags.ReactDebugOverlayTags;
import com.facebook.infer.annotation.Assertions;
import com.facebook.react.C1045R;
import com.facebook.react.bridge.DefaultNativeModuleCallExceptionHandler;
import com.facebook.react.bridge.JavaJSExecutor;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactMarker;
import com.facebook.react.bridge.ReactMarkerConstants;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.common.DebugServerException;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.common.ShakeDetector;
import com.facebook.react.common.futures.SimpleSettableFuture;
import com.facebook.react.devsupport.BundleDownloader;
import com.facebook.react.devsupport.DevInternalSettings;
import com.facebook.react.devsupport.DevServerHelper;
import com.facebook.react.devsupport.InspectorPackagerConnection;
import com.facebook.react.devsupport.JSCHeapCapture;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.devsupport.WebsocketJavaScriptExecutor;
import com.facebook.react.devsupport.interfaces.DevBundleDownloadListener;
import com.facebook.react.devsupport.interfaces.DevOptionHandler;
import com.facebook.react.devsupport.interfaces.DevSupportManager;
import com.facebook.react.devsupport.interfaces.ErrorCustomizer;
import com.facebook.react.devsupport.interfaces.PackagerStatusCallback;
import com.facebook.react.devsupport.interfaces.StackFrame;
import com.facebook.react.modules.core.RCTNativeAppEventEmitter;
import com.facebook.react.modules.debug.interfaces.DeveloperSettings;
import com.facebook.react.packagerconnection.RequestHandler;
import com.facebook.react.packagerconnection.Responder;
import com.google.android.exoplayer2.C1379C;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class DevSupportManagerBase implements DevSupportManager, DevServerHelper.PackagerCommandListener, DevInternalSettings.Listener {
    public static final String EMOJI_FACE_WITH_NO_GOOD_GESTURE = " 🙅";
    public static final String EMOJI_HUNDRED_POINTS_SYMBOL = " 💯";
    private static final String EXOPACKAGE_LOCATION_FORMAT = "/data/local/tmp/exopackage/%s//secondary-dex";
    private static final int JAVA_ERROR_COOKIE = -1;
    private static final int JSEXCEPTION_ERROR_COOKIE = -1;
    private static final String JS_BUNDLE_FILE_NAME = "ReactNativeDevBundle.js";
    private static final String RELOAD_APP_ACTION_SUFFIX = ".RELOAD_APP_ACTION";
    /* access modifiers changed from: private */
    public final Context mApplicationContext;
    /* access modifiers changed from: private */
    public DevBundleDownloadListener mBundleDownloadListener;
    /* access modifiers changed from: private */
    public InspectorPackagerConnection.BundleStatus mBundleStatus;
    /* access modifiers changed from: private */
    public ReactContext mCurrentContext;
    private final LinkedHashMap<String, DevOptionHandler> mCustomDevOptions;
    private Map<String, RequestHandler> mCustomPackagerCommandHandlers;
    private DebugOverlayController mDebugOverlayController;
    private final DefaultNativeModuleCallExceptionHandler mDefaultNativeModuleCallExceptionHandler;
    /* access modifiers changed from: private */
    public final DevLoadingViewController mDevLoadingViewController;
    /* access modifiers changed from: private */
    public boolean mDevLoadingViewVisible;
    /* access modifiers changed from: private */
    public AlertDialog mDevOptionsDialog;
    /* access modifiers changed from: private */
    public final DevServerHelper mDevServerHelper;
    /* access modifiers changed from: private */
    public DevInternalSettings mDevSettings;
    private List<ErrorCustomizer> mErrorCustomizers;
    private final List<ExceptionLogger> mExceptionLoggers;
    private boolean mIsDevSupportEnabled;
    private boolean mIsReceiverRegistered;
    private boolean mIsSamplingProfilerEnabled;
    private boolean mIsShakeDetectorStarted;
    private final String mJSAppBundleName;
    private final File mJSBundleTempFile;
    /* access modifiers changed from: private */
    public int mLastErrorCookie;
    private StackFrame[] mLastErrorStack;
    private String mLastErrorTitle;
    private ErrorType mLastErrorType;
    private DevSupportManager.PackagerLocationCustomizer mPackagerLocationCustomizer;
    /* access modifiers changed from: private */
    public final ReactInstanceManagerDevHelper mReactInstanceManagerHelper;
    /* access modifiers changed from: private */
    public RedBoxDialog mRedBoxDialog;
    /* access modifiers changed from: private */
    public RedBoxHandler mRedBoxHandler;
    private final BroadcastReceiver mReloadAppBroadcastReceiver;
    private final ShakeDetector mShakeDetector;

    protected interface BundleLoadCallback {
        void onSuccess();
    }

    private enum ErrorType {
        JS,
        NATIVE
    }

    private interface ExceptionLogger {
        void log(Exception exc);
    }

    public void onPackagerConnected() {
    }

    public void onPackagerDisconnected() {
    }

    public DevSupportManagerBase(Context context, ReactInstanceManagerDevHelper reactInstanceManagerDevHelper, String str, boolean z, int i) {
        this(context, reactInstanceManagerDevHelper, str, z, (RedBoxHandler) null, (DevBundleDownloadListener) null, i, (Map<String, RequestHandler>) null);
    }

    public DevSupportManagerBase(Context context, ReactInstanceManagerDevHelper reactInstanceManagerDevHelper, String str, boolean z, RedBoxHandler redBoxHandler, DevBundleDownloadListener devBundleDownloadListener, int i, Map<String, RequestHandler> map) {
        this.mIsSamplingProfilerEnabled = false;
        this.mExceptionLoggers = new ArrayList();
        this.mCustomDevOptions = new LinkedHashMap<>();
        this.mDevLoadingViewVisible = false;
        this.mIsReceiverRegistered = false;
        this.mIsShakeDetectorStarted = false;
        this.mIsDevSupportEnabled = false;
        this.mLastErrorCookie = 0;
        this.mReactInstanceManagerHelper = reactInstanceManagerDevHelper;
        this.mApplicationContext = context;
        this.mJSAppBundleName = str;
        this.mDevSettings = new DevInternalSettings(context, this);
        this.mBundleStatus = new InspectorPackagerConnection.BundleStatus();
        this.mDevServerHelper = new DevServerHelper(this.mDevSettings, this.mApplicationContext.getPackageName(), new InspectorPackagerConnection.BundleStatusProvider() {
            public InspectorPackagerConnection.BundleStatus getBundleStatus() {
                return DevSupportManagerBase.this.mBundleStatus;
            }
        });
        this.mBundleDownloadListener = devBundleDownloadListener;
        this.mShakeDetector = new ShakeDetector(new ShakeDetector.ShakeListener() {
            public void onShake() {
                DevSupportManagerBase.this.showDevOptionsDialog();
            }
        }, i);
        this.mCustomPackagerCommandHandlers = map;
        this.mReloadAppBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if (DevSupportManagerBase.getReloadAppAction(context).equals(intent.getAction())) {
                    if (intent.getBooleanExtra(DevServerHelper.RELOAD_APP_EXTRA_JS_PROXY, false)) {
                        DevSupportManagerBase.this.mDevSettings.setRemoteJSDebugEnabled(true);
                        DevSupportManagerBase.this.mDevServerHelper.launchJSDevtools();
                    } else {
                        DevSupportManagerBase.this.mDevSettings.setRemoteJSDebugEnabled(false);
                    }
                    DevSupportManagerBase.this.handleReloadJS();
                }
            }
        };
        this.mJSBundleTempFile = new File(context.getFilesDir(), JS_BUNDLE_FILE_NAME);
        this.mDefaultNativeModuleCallExceptionHandler = new DefaultNativeModuleCallExceptionHandler();
        setDevSupportEnabled(z);
        this.mRedBoxHandler = redBoxHandler;
        this.mDevLoadingViewController = new DevLoadingViewController(context, reactInstanceManagerDevHelper);
        this.mExceptionLoggers.add(new JSExceptionLogger());
        if (!this.mDevSettings.isStartSamplingProfilerOnInit()) {
            return;
        }
        if (!this.mIsSamplingProfilerEnabled) {
            toggleJSSamplingProfiler();
        } else {
            Toast.makeText(this.mApplicationContext, "JS Sampling Profiler was already running, so did not start the sampling profiler", 1).show();
        }
    }

    public void handleException(Exception exc) {
        if (this.mIsDevSupportEnabled) {
            for (ExceptionLogger log : this.mExceptionLoggers) {
                log.log(exc);
            }
            return;
        }
        this.mDefaultNativeModuleCallExceptionHandler.handleException(exc);
    }

    private class JSExceptionLogger implements ExceptionLogger {
        private JSExceptionLogger() {
        }

        public void log(Exception exc) {
            StringBuilder sb = new StringBuilder(exc.getMessage() == null ? "Exception in native call from JS" : exc.getMessage());
            for (Throwable cause = exc.getCause(); cause != null; cause = cause.getCause()) {
                sb.append("\n\n");
                sb.append(cause.getMessage());
            }
            if (exc instanceof JSException) {
                FLog.m65e(ReactConstants.TAG, "Exception in native call from JS", (Throwable) exc);
                String stack = ((JSException) exc).getStack();
                sb.append("\n\n");
                sb.append(stack);
                DevSupportManagerBase.this.showNewError(sb.toString(), new StackFrame[0], -1, ErrorType.JS);
                return;
            }
            DevSupportManagerBase.this.showNewJavaError(sb.toString(), exc);
        }
    }

    public void showNewJavaError(String str, Throwable th) {
        FLog.m65e(ReactConstants.TAG, "Exception in native call", th);
        showNewError(str, StackTraceHelper.convertJavaStackTrace(th), -1, ErrorType.NATIVE);
    }

    public void addCustomDevOption(String str, DevOptionHandler devOptionHandler) {
        this.mCustomDevOptions.put(str, devOptionHandler);
    }

    public void showNewJSError(String str, ReadableArray readableArray, int i) {
        showNewError(str, StackTraceHelper.convertJsStackTrace(readableArray), i, ErrorType.JS);
    }

    public void registerErrorCustomizer(ErrorCustomizer errorCustomizer) {
        if (this.mErrorCustomizers == null) {
            this.mErrorCustomizers = new ArrayList();
        }
        this.mErrorCustomizers.add(errorCustomizer);
    }

    /* access modifiers changed from: private */
    public Pair<String, StackFrame[]> processErrorCustomizers(Pair<String, StackFrame[]> pair) {
        List<ErrorCustomizer> list = this.mErrorCustomizers;
        if (list == null) {
            return pair;
        }
        for (ErrorCustomizer customizeErrorInfo : list) {
            Pair<String, StackFrame[]> customizeErrorInfo2 = customizeErrorInfo.customizeErrorInfo(pair);
            if (customizeErrorInfo2 != null) {
                pair = customizeErrorInfo2;
            }
        }
        return pair;
    }

    public void updateJSError(final String str, final ReadableArray readableArray, final int i) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                if (DevSupportManagerBase.this.mRedBoxDialog != null && DevSupportManagerBase.this.mRedBoxDialog.isShowing() && i == DevSupportManagerBase.this.mLastErrorCookie) {
                    StackFrame[] convertJsStackTrace = StackTraceHelper.convertJsStackTrace(readableArray);
                    Pair access$800 = DevSupportManagerBase.this.processErrorCustomizers(Pair.create(str, convertJsStackTrace));
                    DevSupportManagerBase.this.mRedBoxDialog.setExceptionDetails((String) access$800.first, (StackFrame[]) access$800.second);
                    DevSupportManagerBase.this.updateLastErrorInfo(str, convertJsStackTrace, i, ErrorType.JS);
                    if (DevSupportManagerBase.this.mRedBoxHandler != null) {
                        DevSupportManagerBase.this.mRedBoxHandler.handleRedbox(str, convertJsStackTrace, RedBoxHandler.ErrorType.JS);
                        DevSupportManagerBase.this.mRedBoxDialog.resetReporting();
                    }
                    DevSupportManagerBase.this.mRedBoxDialog.show();
                }
            }
        });
    }

    public void hideRedboxDialog() {
        RedBoxDialog redBoxDialog = this.mRedBoxDialog;
        if (redBoxDialog != null) {
            redBoxDialog.dismiss();
            this.mRedBoxDialog = null;
        }
    }

    public View createRootView(String str) {
        return this.mReactInstanceManagerHelper.createRootView(str);
    }

    public void destroyRootView(View view) {
        this.mReactInstanceManagerHelper.destroyRootView(view);
    }

    private void hideDevOptionsDialog() {
        AlertDialog alertDialog = this.mDevOptionsDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
            this.mDevOptionsDialog = null;
        }
    }

    /* access modifiers changed from: private */
    public void showNewError(String str, StackFrame[] stackFrameArr, int i, ErrorType errorType) {
        final String str2 = str;
        final StackFrame[] stackFrameArr2 = stackFrameArr;
        final int i2 = i;
        final ErrorType errorType2 = errorType;
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                if (DevSupportManagerBase.this.mRedBoxDialog == null) {
                    Activity currentActivity = DevSupportManagerBase.this.mReactInstanceManagerHelper.getCurrentActivity();
                    if (currentActivity == null || currentActivity.isFinishing()) {
                        FLog.m64e(ReactConstants.TAG, "Unable to launch redbox because react activity is not available, here is the error that redbox would've displayed: " + str2);
                        return;
                    }
                    DevSupportManagerBase devSupportManagerBase = DevSupportManagerBase.this;
                    DevSupportManagerBase devSupportManagerBase2 = DevSupportManagerBase.this;
                    RedBoxDialog unused = devSupportManagerBase.mRedBoxDialog = new RedBoxDialog(currentActivity, devSupportManagerBase2, devSupportManagerBase2.mRedBoxHandler);
                }
                if (!DevSupportManagerBase.this.mRedBoxDialog.isShowing()) {
                    Pair access$800 = DevSupportManagerBase.this.processErrorCustomizers(Pair.create(str2, stackFrameArr2));
                    DevSupportManagerBase.this.mRedBoxDialog.setExceptionDetails((String) access$800.first, (StackFrame[]) access$800.second);
                    DevSupportManagerBase.this.updateLastErrorInfo(str2, stackFrameArr2, i2, errorType2);
                    if (DevSupportManagerBase.this.mRedBoxHandler != null && errorType2 == ErrorType.NATIVE) {
                        DevSupportManagerBase.this.mRedBoxHandler.handleRedbox(str2, stackFrameArr2, RedBoxHandler.ErrorType.NATIVE);
                    }
                    DevSupportManagerBase.this.mRedBoxDialog.resetReporting();
                    DevSupportManagerBase.this.mRedBoxDialog.show();
                }
            }
        });
    }

    public void showDevOptionsDialog() {
        String str;
        String str2;
        String str3;
        String str4;
        if (this.mDevOptionsDialog == null && this.mIsDevSupportEnabled && !ActivityManager.isUserAMonkey()) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put(this.mApplicationContext.getString(C1045R.string.catalyst_reload), new DevOptionHandler() {
                public void onOptionSelected() {
                    if (!DevSupportManagerBase.this.mDevSettings.isJSDevModeEnabled() && DevSupportManagerBase.this.mDevSettings.isHotModuleReplacementEnabled()) {
                        Toast.makeText(DevSupportManagerBase.this.mApplicationContext, DevSupportManagerBase.this.mApplicationContext.getString(C1045R.string.catalyst_hot_reloading_auto_disable), 1).show();
                        DevSupportManagerBase.this.mDevSettings.setHotModuleReplacementEnabled(false);
                    }
                    DevSupportManagerBase.this.handleReloadJS();
                }
            });
            if (this.mDevSettings.isRemoteJSDebugEnabled()) {
                str = this.mApplicationContext.getString(C1045R.string.catalyst_debug_stop);
            } else {
                str = this.mApplicationContext.getString(C1045R.string.catalyst_debug);
            }
            linkedHashMap.put(str, new DevOptionHandler() {
                public void onOptionSelected() {
                    DevSupportManagerBase.this.mDevSettings.setRemoteJSDebugEnabled(!DevSupportManagerBase.this.mDevSettings.isRemoteJSDebugEnabled());
                    DevSupportManagerBase.this.handleReloadJS();
                }
            });
            linkedHashMap.put(this.mApplicationContext.getString(C1045R.string.catalyst_change_bundle_location), new DevOptionHandler() {
                public void onOptionSelected() {
                    Activity currentActivity = DevSupportManagerBase.this.mReactInstanceManagerHelper.getCurrentActivity();
                    if (currentActivity == null || currentActivity.isFinishing()) {
                        FLog.m64e(ReactConstants.TAG, "Unable to launch change bundle location because react activity is not available");
                        return;
                    }
                    final EditText editText = new EditText(currentActivity);
                    editText.setHint("localhost:8081");
                    new AlertDialog.Builder(currentActivity).setTitle(DevSupportManagerBase.this.mApplicationContext.getString(C1045R.string.catalyst_change_bundle_location)).setView(editText).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DevSupportManagerBase.this.mDevSettings.getPackagerConnectionSettings().setDebugServerHost(editText.getText().toString());
                            DevSupportManagerBase.this.handleReloadJS();
                        }
                    }).create().show();
                }
            });
            linkedHashMap.put(this.mApplicationContext.getString(C1045R.string.catalyst_inspector), new DevOptionHandler() {
                public void onOptionSelected() {
                    DevSupportManagerBase.this.mDevSettings.setElementInspectorEnabled(!DevSupportManagerBase.this.mDevSettings.isElementInspectorEnabled());
                    DevSupportManagerBase.this.mReactInstanceManagerHelper.toggleElementInspector();
                }
            });
            if (this.mDevSettings.isHotModuleReplacementEnabled()) {
                str2 = this.mApplicationContext.getString(C1045R.string.catalyst_hot_reloading_stop);
            } else {
                str2 = this.mApplicationContext.getString(C1045R.string.catalyst_hot_reloading);
            }
            linkedHashMap.put(str2, new DevOptionHandler() {
                public void onOptionSelected() {
                    boolean z = !DevSupportManagerBase.this.mDevSettings.isHotModuleReplacementEnabled();
                    DevSupportManagerBase.this.mDevSettings.setHotModuleReplacementEnabled(z);
                    if (DevSupportManagerBase.this.mCurrentContext != null) {
                        if (z) {
                            ((HMRClient) DevSupportManagerBase.this.mCurrentContext.getJSModule(HMRClient.class)).enable();
                        } else {
                            ((HMRClient) DevSupportManagerBase.this.mCurrentContext.getJSModule(HMRClient.class)).disable();
                        }
                    }
                    if (z && !DevSupportManagerBase.this.mDevSettings.isJSDevModeEnabled()) {
                        Toast.makeText(DevSupportManagerBase.this.mApplicationContext, DevSupportManagerBase.this.mApplicationContext.getString(C1045R.string.catalyst_hot_reloading_auto_enable), 1).show();
                        DevSupportManagerBase.this.mDevSettings.setJSDevModeEnabled(true);
                        DevSupportManagerBase.this.handleReloadJS();
                    }
                }
            });
            if (this.mIsSamplingProfilerEnabled) {
                str3 = this.mApplicationContext.getString(C1045R.string.catalyst_sample_profiler_disable);
            } else {
                str3 = this.mApplicationContext.getString(C1045R.string.catalyst_sample_profiler_enable);
            }
            linkedHashMap.put(str3, new DevOptionHandler() {
                public void onOptionSelected() {
                    DevSupportManagerBase.this.toggleJSSamplingProfiler();
                }
            });
            if (this.mDevSettings.isFpsDebugEnabled()) {
                str4 = this.mApplicationContext.getString(C1045R.string.catalyst_perf_monitor_stop);
            } else {
                str4 = this.mApplicationContext.getString(C1045R.string.catalyst_perf_monitor);
            }
            linkedHashMap.put(str4, new DevOptionHandler() {
                public void onOptionSelected() {
                    if (!DevSupportManagerBase.this.mDevSettings.isFpsDebugEnabled()) {
                        Activity currentActivity = DevSupportManagerBase.this.mReactInstanceManagerHelper.getCurrentActivity();
                        if (currentActivity == null) {
                            FLog.m64e(ReactConstants.TAG, "Unable to get reference to react activity");
                        } else {
                            DebugOverlayController.requestPermission(currentActivity);
                        }
                    }
                    DevSupportManagerBase.this.mDevSettings.setFpsDebugEnabled(!DevSupportManagerBase.this.mDevSettings.isFpsDebugEnabled());
                }
            });
            linkedHashMap.put(this.mApplicationContext.getString(C1045R.string.catalyst_settings), new DevOptionHandler() {
                public void onOptionSelected() {
                    Intent intent = new Intent(DevSupportManagerBase.this.mApplicationContext, DevSettingsActivity.class);
                    intent.setFlags(C1379C.ENCODING_PCM_MU_LAW);
                    DevSupportManagerBase.this.mApplicationContext.startActivity(intent);
                }
            });
            if (this.mCustomDevOptions.size() > 0) {
                linkedHashMap.putAll(this.mCustomDevOptions);
            }
            final DevOptionHandler[] devOptionHandlerArr = (DevOptionHandler[]) linkedHashMap.values().toArray(new DevOptionHandler[0]);
            Activity currentActivity = this.mReactInstanceManagerHelper.getCurrentActivity();
            if (currentActivity == null || currentActivity.isFinishing()) {
                FLog.m64e(ReactConstants.TAG, "Unable to launch dev options menu because react activity isn't available");
                return;
            }
            AlertDialog create = new AlertDialog.Builder(currentActivity).setItems((CharSequence[]) linkedHashMap.keySet().toArray(new String[0]), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    devOptionHandlerArr[i].onOptionSelected();
                    AlertDialog unused = DevSupportManagerBase.this.mDevOptionsDialog = null;
                }
            }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                public void onCancel(DialogInterface dialogInterface) {
                    AlertDialog unused = DevSupportManagerBase.this.mDevOptionsDialog = null;
                }
            }).create();
            this.mDevOptionsDialog = create;
            create.show();
            ReactContext reactContext = this.mCurrentContext;
            if (reactContext != null) {
                ((RCTNativeAppEventEmitter) reactContext.getJSModule(RCTNativeAppEventEmitter.class)).emit("RCTDevMenuShown", (Object) null);
            }
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Code restructure failed: missing block: B:5:0x001e, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
        r1 = r7.mApplicationContext;
        android.widget.Toast.makeText(r1, r0.toString() + " does not support Sampling Profiler", 1).show();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x003f, code lost:
        r7.mIsSamplingProfilerEnabled = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0041, code lost:
        throw r0;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0074 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x0020 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void toggleJSSamplingProfiler() {
        /*
            r7 = this;
            com.facebook.react.devsupport.ReactInstanceManagerDevHelper r0 = r7.mReactInstanceManagerHelper
            com.facebook.react.bridge.JavaScriptExecutorFactory r0 = r0.getJavaScriptExecutorFactory()
            boolean r1 = r7.mIsSamplingProfilerEnabled
            r2 = 0
            r3 = 1
            if (r1 != 0) goto L_0x0042
            r0.startSamplingProfiler()     // Catch:{ UnsupportedOperationException -> 0x0020 }
            android.content.Context r1 = r7.mApplicationContext     // Catch:{ UnsupportedOperationException -> 0x0020 }
            java.lang.String r4 = "Starting Sampling Profiler"
            android.widget.Toast r1 = android.widget.Toast.makeText(r1, r4, r2)     // Catch:{ UnsupportedOperationException -> 0x0020 }
            r1.show()     // Catch:{ UnsupportedOperationException -> 0x0020 }
        L_0x001a:
            r7.mIsSamplingProfilerEnabled = r3
            goto L_0x009c
        L_0x001e:
            r0 = move-exception
            goto L_0x003f
        L_0x0020:
            android.content.Context r1 = r7.mApplicationContext     // Catch:{ all -> 0x001e }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x001e }
            r2.<init>()     // Catch:{ all -> 0x001e }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x001e }
            r2.append(r0)     // Catch:{ all -> 0x001e }
            java.lang.String r0 = " does not support Sampling Profiler"
            r2.append(r0)     // Catch:{ all -> 0x001e }
            java.lang.String r0 = r2.toString()     // Catch:{ all -> 0x001e }
            android.widget.Toast r0 = android.widget.Toast.makeText(r1, r0, r3)     // Catch:{ all -> 0x001e }
            r0.show()     // Catch:{ all -> 0x001e }
            goto L_0x001a
        L_0x003f:
            r7.mIsSamplingProfilerEnabled = r3
            throw r0
        L_0x0042:
            java.lang.String r1 = "sampling-profiler-trace"
            java.lang.String r4 = ".cpuprofile"
            android.content.Context r5 = r7.mApplicationContext     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            java.io.File r5 = r5.getCacheDir()     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            java.io.File r1 = java.io.File.createTempFile(r1, r4, r5)     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            java.lang.String r1 = r1.getPath()     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            r0.stopSamplingProfiler(r1)     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            android.content.Context r4 = r7.mApplicationContext     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            r5.<init>()     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            java.lang.String r6 = "Saved results from Profiler to "
            r5.append(r6)     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            r5.append(r1)     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            java.lang.String r1 = r5.toString()     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            android.widget.Toast r1 = android.widget.Toast.makeText(r4, r1, r3)     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            r1.show()     // Catch:{ IOException -> 0x0093, UnsupportedOperationException -> 0x0074 }
            goto L_0x009a
        L_0x0072:
            r0 = move-exception
            goto L_0x009d
        L_0x0074:
            android.content.Context r1 = r7.mApplicationContext     // Catch:{ all -> 0x0072 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0072 }
            r4.<init>()     // Catch:{ all -> 0x0072 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0072 }
            r4.append(r0)     // Catch:{ all -> 0x0072 }
            java.lang.String r0 = "does not support Sampling Profiler"
            r4.append(r0)     // Catch:{ all -> 0x0072 }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x0072 }
            android.widget.Toast r0 = android.widget.Toast.makeText(r1, r0, r3)     // Catch:{ all -> 0x0072 }
            r0.show()     // Catch:{ all -> 0x0072 }
            goto L_0x009a
        L_0x0093:
            java.lang.String r0 = "ReactNative"
            java.lang.String r1 = "Could not create temporary file for saving results from Sampling Profiler"
            com.facebook.common.logging.FLog.m64e((java.lang.String) r0, (java.lang.String) r1)     // Catch:{ all -> 0x0072 }
        L_0x009a:
            r7.mIsSamplingProfilerEnabled = r2
        L_0x009c:
            return
        L_0x009d:
            r7.mIsSamplingProfilerEnabled = r2
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.devsupport.DevSupportManagerBase.toggleJSSamplingProfiler():void");
    }

    public void setDevSupportEnabled(boolean z) {
        this.mIsDevSupportEnabled = z;
        reloadSettings();
    }

    public boolean getDevSupportEnabled() {
        return this.mIsDevSupportEnabled;
    }

    public DeveloperSettings getDevSettings() {
        return this.mDevSettings;
    }

    public void onNewReactContextCreated(ReactContext reactContext) {
        resetCurrentContext(reactContext);
    }

    public void onReactInstanceDestroyed(ReactContext reactContext) {
        if (reactContext == this.mCurrentContext) {
            resetCurrentContext((ReactContext) null);
        }
    }

    public String getSourceMapUrl() {
        String str = this.mJSAppBundleName;
        if (str == null) {
            return "";
        }
        return this.mDevServerHelper.getSourceMapUrl((String) Assertions.assertNotNull(str));
    }

    public String getSourceUrl() {
        String str = this.mJSAppBundleName;
        if (str == null) {
            return "";
        }
        return this.mDevServerHelper.getSourceUrl((String) Assertions.assertNotNull(str));
    }

    public String getJSBundleURLForRemoteDebugging() {
        return this.mDevServerHelper.getJSBundleURLForRemoteDebugging((String) Assertions.assertNotNull(this.mJSAppBundleName));
    }

    public String getDownloadedJSBundleFile() {
        return this.mJSBundleTempFile.getAbsolutePath();
    }

    public boolean hasUpToDateJSBundleInCache() {
        if (this.mIsDevSupportEnabled && this.mJSBundleTempFile.exists()) {
            try {
                String packageName = this.mApplicationContext.getPackageName();
                if (this.mJSBundleTempFile.lastModified() > this.mApplicationContext.getPackageManager().getPackageInfo(packageName, 0).lastUpdateTime) {
                    File file = new File(String.format(Locale.US, EXOPACKAGE_LOCATION_FORMAT, new Object[]{packageName}));
                    if (!file.exists() || this.mJSBundleTempFile.lastModified() > file.lastModified()) {
                        return true;
                    }
                    return false;
                }
            } catch (PackageManager.NameNotFoundException unused) {
                FLog.m64e(ReactConstants.TAG, "DevSupport is unable to get current app info");
            }
        }
        return false;
    }

    public boolean hasBundleInAssets(String str) {
        try {
            String[] list = this.mApplicationContext.getAssets().list("");
            for (String equals : list) {
                if (equals.equals(str)) {
                    return true;
                }
            }
        } catch (IOException unused) {
            FLog.m64e(ReactConstants.TAG, "Error while loading assets list");
        }
        return false;
    }

    private void resetCurrentContext(ReactContext reactContext) {
        if (this.mCurrentContext != reactContext) {
            this.mCurrentContext = reactContext;
            DebugOverlayController debugOverlayController = this.mDebugOverlayController;
            if (debugOverlayController != null) {
                debugOverlayController.setFpsDebugViewVisible(false);
            }
            if (reactContext != null) {
                this.mDebugOverlayController = new DebugOverlayController(reactContext);
            }
            if (this.mCurrentContext != null) {
                try {
                    URL url = new URL(getSourceUrl());
                    ((HMRClient) this.mCurrentContext.getJSModule(HMRClient.class)).setup(DeviceInfo.OS_NAME, url.getPath().substring(1), url.getHost(), url.getPort(), this.mDevSettings.isHotModuleReplacementEnabled());
                } catch (MalformedURLException e) {
                    showNewJavaError(e.getMessage(), e);
                }
            }
            reloadSettings();
        }
    }

    public void reloadSettings() {
        if (UiThreadUtil.isOnUiThread()) {
            reload();
        } else {
            UiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    DevSupportManagerBase.this.reload();
                }
            });
        }
    }

    public void onInternalSettingsChanged() {
        reloadSettings();
    }

    public void handleReloadJS() {
        UiThreadUtil.assertOnUiThread();
        ReactMarker.logMarker(ReactMarkerConstants.RELOAD, this.mDevSettings.getPackagerConnectionSettings().getDebugServerHost());
        hideRedboxDialog();
        if (this.mDevSettings.isRemoteJSDebugEnabled()) {
            PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: load from Proxy");
            this.mDevLoadingViewController.showForRemoteJSEnabled();
            this.mDevLoadingViewVisible = true;
            reloadJSInProxyMode();
            return;
        }
        PrinterHolder.getPrinter().logMessage(ReactDebugOverlayTags.RN_CORE, "RNCore: load from Server");
        reloadJSFromServer(this.mDevServerHelper.getDevServerBundleURL((String) Assertions.assertNotNull(this.mJSAppBundleName)));
    }

    public void isPackagerRunning(final PackagerStatusCallback packagerStatusCallback) {
        C115917 r0 = new Runnable() {
            public void run() {
                DevSupportManagerBase.this.mDevServerHelper.isPackagerRunning(packagerStatusCallback);
            }
        };
        DevSupportManager.PackagerLocationCustomizer packagerLocationCustomizer = this.mPackagerLocationCustomizer;
        if (packagerLocationCustomizer != null) {
            packagerLocationCustomizer.run(r0);
        } else {
            r0.run();
        }
    }

    public File downloadBundleResourceFromUrlSync(String str, File file) {
        return this.mDevServerHelper.downloadBundleResourceFromUrlSync(str, file);
    }

    public String getLastErrorTitle() {
        return this.mLastErrorTitle;
    }

    public StackFrame[] getLastErrorStack() {
        return this.mLastErrorStack;
    }

    public void onPackagerReloadCommand() {
        this.mDevServerHelper.disableDebugger();
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                DevSupportManagerBase.this.handleReloadJS();
            }
        });
    }

    public void onPackagerDevMenuCommand() {
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                DevSupportManagerBase.this.showDevOptionsDialog();
            }
        });
    }

    public void onCaptureHeapCommand(final Responder responder) {
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                DevSupportManagerBase.this.handleCaptureHeap(responder);
            }
        });
    }

    public Map<String, RequestHandler> customCommandHandlers() {
        return this.mCustomPackagerCommandHandlers;
    }

    /* access modifiers changed from: private */
    public void handleCaptureHeap(final Responder responder) {
        ReactContext reactContext = this.mCurrentContext;
        if (reactContext != null) {
            ((JSCHeapCapture) reactContext.getNativeModule(JSCHeapCapture.class)).captureHeap(this.mApplicationContext.getCacheDir().getPath(), new JSCHeapCapture.CaptureCallback() {
                public void onSuccess(File file) {
                    responder.respond(file.toString());
                }

                public void onFailure(JSCHeapCapture.CaptureException captureException) {
                    responder.error(captureException.toString());
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void updateLastErrorInfo(String str, StackFrame[] stackFrameArr, int i, ErrorType errorType) {
        this.mLastErrorTitle = str;
        this.mLastErrorStack = stackFrameArr;
        this.mLastErrorCookie = i;
        this.mLastErrorType = errorType;
    }

    private void reloadJSInProxyMode() {
        this.mDevServerHelper.launchJSDevtools();
        this.mReactInstanceManagerHelper.onReloadWithJSDebugger(new JavaJSExecutor.Factory() {
            public JavaJSExecutor create() throws Exception {
                WebsocketJavaScriptExecutor websocketJavaScriptExecutor = new WebsocketJavaScriptExecutor();
                SimpleSettableFuture simpleSettableFuture = new SimpleSettableFuture();
                websocketJavaScriptExecutor.connect(DevSupportManagerBase.this.mDevServerHelper.getWebsocketProxyURL(), DevSupportManagerBase.this.getExecutorConnectCallback(simpleSettableFuture));
                try {
                    simpleSettableFuture.get(90, TimeUnit.SECONDS);
                    return websocketJavaScriptExecutor;
                } catch (ExecutionException e) {
                    throw ((Exception) e.getCause());
                } catch (InterruptedException | TimeoutException e2) {
                    throw new RuntimeException(e2);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public WebsocketJavaScriptExecutor.JSExecutorConnectCallback getExecutorConnectCallback(final SimpleSettableFuture<Boolean> simpleSettableFuture) {
        return new WebsocketJavaScriptExecutor.JSExecutorConnectCallback() {
            public void onSuccess() {
                simpleSettableFuture.set(true);
                DevSupportManagerBase.this.mDevLoadingViewController.hide();
                boolean unused = DevSupportManagerBase.this.mDevLoadingViewVisible = false;
            }

            public void onFailure(Throwable th) {
                DevSupportManagerBase.this.mDevLoadingViewController.hide();
                boolean unused = DevSupportManagerBase.this.mDevLoadingViewVisible = false;
                FLog.m65e(ReactConstants.TAG, "Failed to connect to debugger!", th);
                simpleSettableFuture.setException(new IOException(DevSupportManagerBase.this.mApplicationContext.getString(C1045R.string.catalyst_debug_error), th));
            }
        };
    }

    public void reloadJSFromServer(String str) {
        reloadJSFromServer(str, new BundleLoadCallback() {
            public void onSuccess() {
                UiThreadUtil.runOnUiThread(new Runnable() {
                    public void run() {
                        DevSupportManagerBase.this.mReactInstanceManagerHelper.onJSBundleLoadedFromServer();
                    }
                });
            }
        });
    }

    /* access modifiers changed from: protected */
    public void reloadJSFromServer(String str, final BundleLoadCallback bundleLoadCallback) {
        ReactMarker.logMarker(ReactMarkerConstants.DOWNLOAD_START);
        this.mDevLoadingViewController.showForUrl(str);
        this.mDevLoadingViewVisible = true;
        final BundleDownloader.BundleInfo bundleInfo = new BundleDownloader.BundleInfo();
        this.mDevServerHelper.downloadBundleFromURL(new DevBundleDownloadListener() {
            public void onSuccess() {
                DevSupportManagerBase.this.mDevLoadingViewController.hide();
                boolean unused = DevSupportManagerBase.this.mDevLoadingViewVisible = false;
                synchronized (DevSupportManagerBase.this) {
                    DevSupportManagerBase.this.mBundleStatus.isLastDownloadSucess = true;
                    DevSupportManagerBase.this.mBundleStatus.updateTimestamp = System.currentTimeMillis();
                }
                if (DevSupportManagerBase.this.mBundleDownloadListener != null) {
                    DevSupportManagerBase.this.mBundleDownloadListener.onSuccess();
                }
                ReactMarker.logMarker(ReactMarkerConstants.DOWNLOAD_END, bundleInfo.toJSONString());
                bundleLoadCallback.onSuccess();
            }

            public void onProgress(String str, Integer num, Integer num2) {
                DevSupportManagerBase.this.mDevLoadingViewController.updateProgress(str, num, num2);
                if (DevSupportManagerBase.this.mBundleDownloadListener != null) {
                    DevSupportManagerBase.this.mBundleDownloadListener.onProgress(str, num, num2);
                }
            }

            public void onFailure(final Exception exc) {
                DevSupportManagerBase.this.mDevLoadingViewController.hide();
                boolean unused = DevSupportManagerBase.this.mDevLoadingViewVisible = false;
                synchronized (DevSupportManagerBase.this) {
                    DevSupportManagerBase.this.mBundleStatus.isLastDownloadSucess = false;
                }
                if (DevSupportManagerBase.this.mBundleDownloadListener != null) {
                    DevSupportManagerBase.this.mBundleDownloadListener.onFailure(exc);
                }
                FLog.m65e(ReactConstants.TAG, "Unable to download JS bundle", (Throwable) exc);
                UiThreadUtil.runOnUiThread(new Runnable() {
                    public void run() {
                        Exception exc = exc;
                        if (exc instanceof DebugServerException) {
                            DevSupportManagerBase.this.showNewJavaError(((DebugServerException) exc).getMessage(), exc);
                        } else {
                            DevSupportManagerBase.this.showNewJavaError(DevSupportManagerBase.this.mApplicationContext.getString(C1045R.string.catalyst_reload_error), exc);
                        }
                    }
                });
            }
        }, this.mJSBundleTempFile, str, bundleInfo);
    }

    public void startInspector() {
        if (this.mIsDevSupportEnabled) {
            this.mDevServerHelper.openInspectorConnection();
        }
    }

    public void stopInspector() {
        this.mDevServerHelper.closeInspectorConnection();
    }

    public void setHotModuleReplacementEnabled(final boolean z) {
        if (this.mIsDevSupportEnabled) {
            UiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    DevSupportManagerBase.this.mDevSettings.setHotModuleReplacementEnabled(z);
                    DevSupportManagerBase.this.handleReloadJS();
                }
            });
        }
    }

    public void setRemoteJSDebugEnabled(final boolean z) {
        if (this.mIsDevSupportEnabled) {
            UiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    DevSupportManagerBase.this.mDevSettings.setRemoteJSDebugEnabled(z);
                    DevSupportManagerBase.this.handleReloadJS();
                }
            });
        }
    }

    public void setFpsDebugEnabled(final boolean z) {
        if (this.mIsDevSupportEnabled) {
            UiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    DevSupportManagerBase.this.mDevSettings.setFpsDebugEnabled(z);
                }
            });
        }
    }

    public void toggleElementInspector() {
        if (this.mIsDevSupportEnabled) {
            UiThreadUtil.runOnUiThread(new Runnable() {
                public void run() {
                    DevSupportManagerBase.this.mDevSettings.setElementInspectorEnabled(!DevSupportManagerBase.this.mDevSettings.isElementInspectorEnabled());
                    DevSupportManagerBase.this.mReactInstanceManagerHelper.toggleElementInspector();
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void reload() {
        UiThreadUtil.assertOnUiThread();
        if (this.mIsDevSupportEnabled) {
            DebugOverlayController debugOverlayController = this.mDebugOverlayController;
            if (debugOverlayController != null) {
                debugOverlayController.setFpsDebugViewVisible(this.mDevSettings.isFpsDebugEnabled());
            }
            if (!this.mIsShakeDetectorStarted) {
                this.mShakeDetector.start((SensorManager) this.mApplicationContext.getSystemService("sensor"));
                this.mIsShakeDetectorStarted = true;
            }
            if (!this.mIsReceiverRegistered) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(getReloadAppAction(this.mApplicationContext));
                this.mApplicationContext.registerReceiver(this.mReloadAppBroadcastReceiver, intentFilter);
                this.mIsReceiverRegistered = true;
            }
            if (this.mDevLoadingViewVisible) {
                this.mDevLoadingViewController.showMessage("Reloading...");
            }
            this.mDevServerHelper.openPackagerConnection(getClass().getSimpleName(), this);
            return;
        }
        DebugOverlayController debugOverlayController2 = this.mDebugOverlayController;
        if (debugOverlayController2 != null) {
            debugOverlayController2.setFpsDebugViewVisible(false);
        }
        if (this.mIsShakeDetectorStarted) {
            this.mShakeDetector.stop();
            this.mIsShakeDetectorStarted = false;
        }
        if (this.mIsReceiverRegistered) {
            this.mApplicationContext.unregisterReceiver(this.mReloadAppBroadcastReceiver);
            this.mIsReceiverRegistered = false;
        }
        hideRedboxDialog();
        hideDevOptionsDialog();
        this.mDevLoadingViewController.hide();
        this.mDevServerHelper.closePackagerConnection();
    }

    /* access modifiers changed from: private */
    public static String getReloadAppAction(Context context) {
        return context.getPackageName() + RELOAD_APP_ACTION_SUFFIX;
    }

    public void setPackagerLocationCustomizer(DevSupportManager.PackagerLocationCustomizer packagerLocationCustomizer) {
        this.mPackagerLocationCustomizer = packagerLocationCustomizer;
    }
}
