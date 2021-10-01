package com.reactcommunity.rndatetimepicker;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.facebook.react.bridge.Promise;

public class Common {
    public static void dismissDialog(FragmentActivity fragmentActivity, String str, Promise promise) {
        if (fragmentActivity == null) {
            promise.reject(RNConstants.ERROR_NO_ACTIVITY, "Tried to close a " + str + " dialog while not attached to an Activity");
            return;
        }
        DialogFragment dialogFragment = (DialogFragment) fragmentActivity.getSupportFragmentManager().findFragmentByTag(str);
        boolean z = dialogFragment != null;
        if (z) {
            dialogFragment.dismiss();
        }
        promise.resolve(Boolean.valueOf(z));
    }
}
