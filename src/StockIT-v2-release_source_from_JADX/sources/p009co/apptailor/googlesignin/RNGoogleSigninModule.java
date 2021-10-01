package p009co.apptailor.googlesignin;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.UiThreadUtil;
import com.facebook.react.bridge.WritableMap;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/* renamed from: co.apptailor.googlesignin.RNGoogleSigninModule */
public class RNGoogleSigninModule extends ReactContextBaseJavaModule {
    public static final String ERROR_USER_RECOVERABLE_AUTH = "ERROR_USER_RECOVERABLE_AUTH";
    public static final String MODULE_NAME = "RNGoogleSignin";
    public static final String PLAY_SERVICES_NOT_AVAILABLE = "PLAY_SERVICES_NOT_AVAILABLE";
    public static final int RC_SIGN_IN = 9001;
    public static final int REQUEST_CODE_RECOVER_AUTH = 53294;
    private static final String SHOULD_RECOVER = "SHOULD_RECOVER";
    /* access modifiers changed from: private */
    public GoogleSignInClient _apiClient;
    /* access modifiers changed from: private */
    public PendingAuthRecovery pendingAuthRecovery;
    /* access modifiers changed from: private */
    public PromiseWrapper promiseWrapper = new PromiseWrapper();

    public String getName() {
        return MODULE_NAME;
    }

    public PromiseWrapper getPromiseWrapper() {
        return this.promiseWrapper;
    }

    public RNGoogleSigninModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        reactApplicationContext.addActivityEventListener(new RNGoogleSigninActivityEventListener());
    }

    public Map<String, Object> getConstants() {
        HashMap hashMap = new HashMap();
        hashMap.put("BUTTON_SIZE_ICON", 2);
        hashMap.put("BUTTON_SIZE_STANDARD", 0);
        hashMap.put("BUTTON_SIZE_WIDE", 1);
        hashMap.put("BUTTON_COLOR_AUTO", 2);
        hashMap.put("BUTTON_COLOR_LIGHT", 1);
        hashMap.put("BUTTON_COLOR_DARK", 0);
        hashMap.put("SIGN_IN_CANCELLED", String.valueOf(GoogleSignInStatusCodes.SIGN_IN_CANCELLED));
        hashMap.put("SIGN_IN_REQUIRED", String.valueOf(4));
        hashMap.put("IN_PROGRESS", PromiseWrapper.ASYNC_OP_IN_PROGRESS);
        hashMap.put(PLAY_SERVICES_NOT_AVAILABLE, PLAY_SERVICES_NOT_AVAILABLE);
        return hashMap;
    }

    @ReactMethod
    public void playServicesAvailable(boolean z, Promise promise) {
        Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            Log.w(MODULE_NAME, "could not determine playServicesAvailable, activity is null");
            promise.reject(MODULE_NAME, "activity is null");
            return;
        }
        GoogleApiAvailability instance = GoogleApiAvailability.getInstance();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(currentActivity);
        if (isGooglePlayServicesAvailable != 0) {
            if (z && instance.isUserResolvableError(isGooglePlayServicesAvailable)) {
                instance.getErrorDialog(currentActivity, isGooglePlayServicesAvailable, 2404).show();
            }
            promise.reject(PLAY_SERVICES_NOT_AVAILABLE, "Play services not available");
            return;
        }
        promise.resolve(true);
    }

    @ReactMethod
    public void configure(ReadableMap readableMap, Promise promise) {
        this._apiClient = GoogleSignIn.getClient((Context) getReactApplicationContext(), C0621Utils.getSignInOptions(C0621Utils.createScopesArray(readableMap.hasKey("scopes") ? readableMap.getArray("scopes") : Arguments.createArray()), readableMap.hasKey("webClientId") ? readableMap.getString("webClientId") : null, readableMap.hasKey("offlineAccess") && readableMap.getBoolean("offlineAccess"), readableMap.hasKey("forceCodeForRefreshToken") && readableMap.getBoolean("forceCodeForRefreshToken"), readableMap.hasKey("accountName") ? readableMap.getString("accountName") : null, readableMap.hasKey("hostedDomain") ? readableMap.getString("hostedDomain") : null));
        promise.resolve((Object) null);
    }

    @ReactMethod
    public void signInSilently(Promise promise) {
        if (this._apiClient == null) {
            rejectWithNullClientError(promise);
            return;
        }
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "signInSilently");
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                Task<GoogleSignInAccount> silentSignIn = RNGoogleSigninModule.this._apiClient.silentSignIn();
                if (silentSignIn.isSuccessful()) {
                    RNGoogleSigninModule.this.handleSignInTaskResult(silentSignIn);
                } else {
                    silentSignIn.addOnCompleteListener(new OnCompleteListener() {
                        public void onComplete(Task task) {
                            RNGoogleSigninModule.this.handleSignInTaskResult(task);
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void handleSignInTaskResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount result = task.getResult(ApiException.class);
            if (result == null) {
                this.promiseWrapper.reject(MODULE_NAME, "GoogleSignInAccount instance was null");
                return;
            }
            this.promiseWrapper.resolve(C0621Utils.getUserProperties(result));
        } catch (ApiException e) {
            int statusCode = e.getStatusCode();
            this.promiseWrapper.reject(String.valueOf(statusCode), GoogleSignInStatusCodes.getStatusCodeString(statusCode));
        }
    }

    @ReactMethod
    public void signIn(Promise promise) {
        if (this._apiClient == null) {
            rejectWithNullClientError(promise);
            return;
        }
        final Activity currentActivity = getCurrentActivity();
        if (currentActivity == null) {
            promise.reject(MODULE_NAME, "activity is null");
            return;
        }
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "signIn");
        UiThreadUtil.runOnUiThread(new Runnable() {
            public void run() {
                currentActivity.startActivityForResult(RNGoogleSigninModule.this._apiClient.getSignInIntent(), RNGoogleSigninModule.RC_SIGN_IN);
            }
        });
    }

    /* renamed from: co.apptailor.googlesignin.RNGoogleSigninModule$RNGoogleSigninActivityEventListener */
    private class RNGoogleSigninActivityEventListener extends BaseActivityEventListener {
        private RNGoogleSigninActivityEventListener() {
        }

        public void onActivityResult(Activity activity, int i, int i2, Intent intent) {
            if (i == 9001) {
                RNGoogleSigninModule.this.handleSignInTaskResult(GoogleSignIn.getSignedInAccountFromIntent(intent));
            } else if (i != 53294) {
            } else {
                if (i2 == -1) {
                    RNGoogleSigninModule.this.rerunFailedAuthTokenTask();
                } else {
                    RNGoogleSigninModule.this.promiseWrapper.reject(RNGoogleSigninModule.MODULE_NAME, "Failed authentication recovery attempt, probably user-rejected.");
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void rerunFailedAuthTokenTask() {
        WritableMap userProperties = this.pendingAuthRecovery.getUserProperties();
        if (userProperties != null) {
            new AccessTokenRetrievalTask(this).execute(new WritableMap[]{userProperties, null});
            return;
        }
        this.promiseWrapper.reject(MODULE_NAME, "rerunFailedAuthTokenTask: recovery failed");
    }

    @ReactMethod
    public void signOut(final Promise promise) {
        GoogleSignInClient googleSignInClient = this._apiClient;
        if (googleSignInClient == null) {
            rejectWithNullClientError(promise);
        } else {
            googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    RNGoogleSigninModule.this.handleSignOutOrRevokeAccessTask(task, promise);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void handleSignOutOrRevokeAccessTask(Task<Void> task, Promise promise) {
        if (task.isSuccessful()) {
            promise.resolve((Object) null);
            return;
        }
        int exceptionCode = C0621Utils.getExceptionCode(task);
        promise.reject(String.valueOf(exceptionCode), GoogleSignInStatusCodes.getStatusCodeString(exceptionCode));
    }

    @ReactMethod
    public void revokeAccess(final Promise promise) {
        GoogleSignInClient googleSignInClient = this._apiClient;
        if (googleSignInClient == null) {
            rejectWithNullClientError(promise);
        } else {
            googleSignInClient.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
                public void onComplete(Task<Void> task) {
                    RNGoogleSigninModule.this.handleSignOutOrRevokeAccessTask(task, promise);
                }
            });
        }
    }

    @ReactMethod
    public void isSignedIn(Promise promise) {
        promise.resolve(Boolean.valueOf(GoogleSignIn.getLastSignedInAccount(getReactApplicationContext()) != null));
    }

    @ReactMethod
    public void getCurrentUser(Promise promise) {
        WritableMap writableMap;
        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(getReactApplicationContext());
        if (lastSignedInAccount == null) {
            writableMap = null;
        } else {
            writableMap = C0621Utils.getUserProperties(lastSignedInAccount);
        }
        promise.resolve(writableMap);
    }

    @ReactMethod
    public void clearCachedAccessToken(String str, Promise promise) {
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "clearCachedAccessToken");
        new TokenClearingTask(this).execute(new String[]{str});
    }

    @ReactMethod
    public void getTokens(Promise promise) {
        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(getReactApplicationContext());
        if (lastSignedInAccount == null) {
            promise.reject(MODULE_NAME, "getTokens requires a user to be signed in");
            return;
        }
        this.promiseWrapper.setPromiseWithInProgressCheck(promise, "getTokens");
        startTokenRetrievalTaskWithRecovery(lastSignedInAccount);
    }

    private void startTokenRetrievalTaskWithRecovery(GoogleSignInAccount googleSignInAccount) {
        WritableMap userProperties = C0621Utils.getUserProperties(googleSignInAccount);
        WritableMap createMap = Arguments.createMap();
        createMap.putBoolean(SHOULD_RECOVER, true);
        new AccessTokenRetrievalTask(this).execute(new WritableMap[]{userProperties, createMap});
    }

    /* renamed from: co.apptailor.googlesignin.RNGoogleSigninModule$AccessTokenRetrievalTask */
    private static class AccessTokenRetrievalTask extends AsyncTask<WritableMap, Void, Void> {
        private WeakReference<RNGoogleSigninModule> weakModuleRef;

        AccessTokenRetrievalTask(RNGoogleSigninModule rNGoogleSigninModule) {
            this.weakModuleRef = new WeakReference<>(rNGoogleSigninModule);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(WritableMap... writableMapArr) {
            WritableMap writableMap = writableMapArr[0];
            RNGoogleSigninModule rNGoogleSigninModule = (RNGoogleSigninModule) this.weakModuleRef.get();
            if (rNGoogleSigninModule == null) {
                return null;
            }
            try {
                insertAccessTokenIntoUserProperties(rNGoogleSigninModule, writableMap);
                rNGoogleSigninModule.getPromiseWrapper().resolve(writableMap);
            } catch (Exception e) {
                handleException(rNGoogleSigninModule, e, writableMap, writableMapArr.length >= 2 ? writableMapArr[1] : null);
            }
            return null;
        }

        private void insertAccessTokenIntoUserProperties(RNGoogleSigninModule rNGoogleSigninModule, WritableMap writableMap) throws IOException, GoogleAuthException {
            writableMap.putString("accessToken", GoogleAuthUtil.getToken((Context) rNGoogleSigninModule.getReactApplicationContext(), new Account(writableMap.getMap("user").getString("email"), "com.google"), C0621Utils.scopesToString(writableMap.getArray("scopes"))));
        }

        private void handleException(RNGoogleSigninModule rNGoogleSigninModule, Exception exc, WritableMap writableMap, WritableMap writableMap2) {
            if (exc instanceof UserRecoverableAuthException) {
                if (writableMap2 != null && writableMap2.hasKey(RNGoogleSigninModule.SHOULD_RECOVER) && writableMap2.getBoolean(RNGoogleSigninModule.SHOULD_RECOVER)) {
                    attemptRecovery(rNGoogleSigninModule, exc, writableMap);
                } else {
                    rNGoogleSigninModule.promiseWrapper.reject(RNGoogleSigninModule.ERROR_USER_RECOVERABLE_AUTH, (Throwable) exc);
                }
            } else {
                rNGoogleSigninModule.promiseWrapper.reject(RNGoogleSigninModule.MODULE_NAME, (Throwable) exc);
            }
        }

        private void attemptRecovery(RNGoogleSigninModule rNGoogleSigninModule, Exception exc, WritableMap writableMap) {
            Activity access$700 = rNGoogleSigninModule.getCurrentActivity();
            if (access$700 == null) {
                PendingAuthRecovery unused = rNGoogleSigninModule.pendingAuthRecovery = null;
                PromiseWrapper access$400 = rNGoogleSigninModule.promiseWrapper;
                access$400.reject(RNGoogleSigninModule.MODULE_NAME, "Cannot attempt recovery auth because app is not in foreground. " + exc.getLocalizedMessage());
                return;
            }
            PendingAuthRecovery unused2 = rNGoogleSigninModule.pendingAuthRecovery = new PendingAuthRecovery(writableMap);
            access$700.startActivityForResult(((UserRecoverableAuthException) exc).getIntent(), RNGoogleSigninModule.REQUEST_CODE_RECOVER_AUTH);
        }
    }

    /* renamed from: co.apptailor.googlesignin.RNGoogleSigninModule$TokenClearingTask */
    private static class TokenClearingTask extends AsyncTask<String, Void, Void> {
        private WeakReference<RNGoogleSigninModule> weakModuleRef;

        TokenClearingTask(RNGoogleSigninModule rNGoogleSigninModule) {
            this.weakModuleRef = new WeakReference<>(rNGoogleSigninModule);
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(String... strArr) {
            RNGoogleSigninModule rNGoogleSigninModule = (RNGoogleSigninModule) this.weakModuleRef.get();
            if (rNGoogleSigninModule == null) {
                return null;
            }
            try {
                GoogleAuthUtil.clearToken(rNGoogleSigninModule.getReactApplicationContext(), strArr[0]);
                rNGoogleSigninModule.getPromiseWrapper().resolve((Object) null);
            } catch (Exception e) {
                rNGoogleSigninModule.promiseWrapper.reject(RNGoogleSigninModule.MODULE_NAME, (Throwable) e);
            }
            return null;
        }
    }

    private void rejectWithNullClientError(Promise promise) {
        promise.reject(MODULE_NAME, "apiClient is null - call configure first");
    }
}
