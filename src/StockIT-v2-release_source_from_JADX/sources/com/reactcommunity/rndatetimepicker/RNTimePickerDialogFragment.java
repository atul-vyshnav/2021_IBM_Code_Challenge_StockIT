package com.reactcommunity.rndatetimepicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import androidx.fragment.app.DialogFragment;
import java.util.Locale;

public class RNTimePickerDialogFragment extends DialogFragment {
    private static DialogInterface.OnClickListener mOnNeutralButtonActionListener;
    private TimePickerDialog instance;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;

    public Dialog onCreateDialog(Bundle bundle) {
        TimePickerDialog createDialog = createDialog(getArguments(), getActivity(), this.mOnTimeSetListener);
        this.instance = createDialog;
        return createDialog;
    }

    public void update(Bundle bundle) {
        RNDate rNDate = new RNDate(bundle);
        this.instance.updateTime(rNDate.hour(), rNDate.minute());
    }

    static TimePickerDialog getDialog(Bundle bundle, Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        int i;
        RNDate rNDate = new RNDate(bundle);
        int hour = rNDate.hour();
        int minute = rNDate.minute();
        boolean is24HourFormat = DateFormat.is24HourFormat(context);
        int i2 = (bundle == null || !MinuteIntervalSnappableTimePickerDialog.isValidMinuteInterval(bundle.getInt(RNConstants.ARG_INTERVAL))) ? 1 : bundle.getInt(RNConstants.ARG_INTERVAL);
        RNTimePickerDisplay rNTimePickerDisplay = RNTimePickerDisplay.DEFAULT;
        if (!(bundle == null || bundle.getString("display", (String) null) == null)) {
            rNTimePickerDisplay = RNTimePickerDisplay.valueOf(bundle.getString("display").toUpperCase(Locale.US));
        }
        RNTimePickerDisplay rNTimePickerDisplay2 = rNTimePickerDisplay;
        boolean z = bundle != null ? bundle.getBoolean(RNConstants.ARG_IS24HOUR, DateFormat.is24HourFormat(context)) : is24HourFormat;
        if (Build.VERSION.SDK_INT < 21 || ((i = C27191.f1188xab2c7a23[rNTimePickerDisplay2.ordinal()]) != 1 && i != 2)) {
            return new RNDismissableTimePickerDialog(context, onTimeSetListener, hour, minute, i2, z, rNTimePickerDisplay2);
        }
        return new RNDismissableTimePickerDialog(context, context.getResources().getIdentifier(rNTimePickerDisplay2 == RNTimePickerDisplay.CLOCK ? "ClockTimePickerDialog" : "SpinnerTimePickerDialog", TtmlNode.TAG_STYLE, context.getPackageName()), onTimeSetListener, hour, minute, i2, z, rNTimePickerDisplay2);
    }

    /* renamed from: com.reactcommunity.rndatetimepicker.RNTimePickerDialogFragment$1 */
    static /* synthetic */ class C27191 {

        /* renamed from: $SwitchMap$com$reactcommunity$rndatetimepicker$RNTimePickerDisplay */
        static final /* synthetic */ int[] f1188xab2c7a23;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|6) */
        /* JADX WARNING: Code restructure failed: missing block: B:7:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        static {
            /*
                com.reactcommunity.rndatetimepicker.RNTimePickerDisplay[] r0 = com.reactcommunity.rndatetimepicker.RNTimePickerDisplay.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                f1188xab2c7a23 = r0
                com.reactcommunity.rndatetimepicker.RNTimePickerDisplay r1 = com.reactcommunity.rndatetimepicker.RNTimePickerDisplay.CLOCK     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = f1188xab2c7a23     // Catch:{ NoSuchFieldError -> 0x001d }
                com.reactcommunity.rndatetimepicker.RNTimePickerDisplay r1 = com.reactcommunity.rndatetimepicker.RNTimePickerDisplay.SPINNER     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.reactcommunity.rndatetimepicker.RNTimePickerDialogFragment.C27191.<clinit>():void");
        }
    }

    static TimePickerDialog createDialog(Bundle bundle, Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        TimePickerDialog dialog = getDialog(bundle, context, onTimeSetListener);
        if (bundle != null && bundle.containsKey(RNConstants.ARG_NEUTRAL_BUTTON_LABEL)) {
            dialog.setButton(-3, bundle.getString(RNConstants.ARG_NEUTRAL_BUTTON_LABEL), mOnNeutralButtonActionListener);
        }
        return dialog;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        DialogInterface.OnDismissListener onDismissListener = this.mOnDismissListener;
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialogInterface);
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.mOnTimeSetListener = onTimeSetListener;
    }

    /* access modifiers changed from: package-private */
    public void setOnNeutralButtonActionListener(DialogInterface.OnClickListener onClickListener) {
        mOnNeutralButtonActionListener = onClickListener;
    }
}
