package p009co.apptailor.googlesignin;

import android.util.Log;
import com.facebook.react.bridge.Promise;

/* renamed from: co.apptailor.googlesignin.PromiseWrapper */
public class PromiseWrapper {
    public static final String ASYNC_OP_IN_PROGRESS = "ASYNC_OP_IN_PROGRESS";
    private String nameOfCallInProgress;
    private Promise promise;

    public void setPromiseWithInProgressCheck(Promise promise2, String str) {
        Promise promise3 = this.promise;
        if (promise3 != null) {
            rejectPreviousPromiseBecauseNewOneIsInProgress(promise3, str);
        }
        this.promise = promise2;
        this.nameOfCallInProgress = str;
    }

    public void resolve(Object obj) {
        Promise promise2 = this.promise;
        if (promise2 == null) {
            Log.w(RNGoogleSigninModule.MODULE_NAME, "cannot resolve promise because it's null");
            return;
        }
        resetMembers();
        promise2.resolve(obj);
    }

    public void reject(String str, Throwable th) {
        Promise promise2 = this.promise;
        if (promise2 == null) {
            Log.w(RNGoogleSigninModule.MODULE_NAME, "cannot reject promise because it's null");
            return;
        }
        resetMembers();
        promise2.reject(str, th.getLocalizedMessage(), th);
    }

    public void reject(String str, String str2) {
        Promise promise2 = this.promise;
        if (promise2 == null) {
            Log.w(RNGoogleSigninModule.MODULE_NAME, "cannot reject promise because it's null");
            return;
        }
        resetMembers();
        promise2.reject(str, str2);
    }

    public String getNameOfCallInProgress() {
        return this.nameOfCallInProgress;
    }

    private void resetMembers() {
        this.promise = null;
        this.nameOfCallInProgress = null;
    }

    private void rejectPreviousPromiseBecauseNewOneIsInProgress(Promise promise2, String str) {
        promise2.reject(ASYNC_OP_IN_PROGRESS, "Warning: previous promise did not settle and was overwritten. You've called \"" + str + "\" while \"" + getNameOfCallInProgress() + "\" was already in progress and has not completed yet.");
    }
}
