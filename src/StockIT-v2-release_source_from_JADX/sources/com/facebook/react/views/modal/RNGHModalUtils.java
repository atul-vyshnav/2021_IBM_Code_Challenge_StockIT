package com.facebook.react.views.modal;

import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.facebook.react.views.modal.ReactModalHostView;

public class RNGHModalUtils {
    public static void dialogRootViewGroupOnChildStartedNativeGesture(ViewGroup viewGroup, MotionEvent motionEvent) {
        ((ReactModalHostView.DialogRootViewGroup) viewGroup).onChildStartedNativeGesture(motionEvent);
    }

    public static boolean isDialogRootViewGroup(ViewParent viewParent) {
        return viewParent instanceof ReactModalHostView.DialogRootViewGroup;
    }
}
