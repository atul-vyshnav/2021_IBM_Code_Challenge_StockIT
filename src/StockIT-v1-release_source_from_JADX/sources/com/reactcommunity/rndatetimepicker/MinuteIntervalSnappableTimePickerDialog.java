package com.reactcommunity.rndatetimepicker;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TimePicker;
import com.amplitude.api.DeviceInfo;
import java.util.ArrayList;

class MinuteIntervalSnappableTimePickerDialog extends TimePickerDialog {
    private Handler handler = new Handler();
    private Context mContext;
    private RNTimePickerDisplay mDisplay;
    private TimePicker mTimePicker;
    private int mTimePickerInterval;
    private final TimePickerDialog.OnTimeSetListener mTimeSetListener;
    private Runnable runnable;

    public MinuteIntervalSnappableTimePickerDialog(Context context, TimePickerDialog.OnTimeSetListener onTimeSetListener, int i, int i2, int i3, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        super(context, onTimeSetListener, i, i2, z);
        this.mTimePickerInterval = i3;
        this.mTimeSetListener = onTimeSetListener;
        this.mDisplay = rNTimePickerDisplay;
        this.mContext = context;
    }

    public MinuteIntervalSnappableTimePickerDialog(Context context, int i, TimePickerDialog.OnTimeSetListener onTimeSetListener, int i2, int i3, int i4, boolean z, RNTimePickerDisplay rNTimePickerDisplay) {
        super(context, i, onTimeSetListener, i2, i3, z);
        this.mTimePickerInterval = i4;
        this.mTimeSetListener = onTimeSetListener;
        this.mDisplay = rNTimePickerDisplay;
        this.mContext = context;
    }

    public static boolean isValidMinuteInterval(int i) {
        return i >= 1 && i <= 30 && 60 % i == 0;
    }

    private boolean timePickerHasCustomMinuteInterval() {
        return this.mTimePickerInterval != 1;
    }

    private boolean isSpinner() {
        return this.mDisplay == RNTimePickerDisplay.SPINNER;
    }

    private int getRealMinutes(int i) {
        return this.mDisplay == RNTimePickerDisplay.SPINNER ? i * this.mTimePickerInterval : i;
    }

    private int getRealMinutes() {
        return getRealMinutes(this.mTimePicker.getCurrentMinute().intValue());
    }

    private int snapRealMinutesToInterval(int i) {
        int round = Math.round(((float) i) / ((float) this.mTimePickerInterval));
        int i2 = this.mTimePickerInterval;
        int i3 = round * i2;
        return i3 == 60 ? i3 - i2 : i3;
    }

    private void assertNotSpinner(String str) {
        if (isSpinner()) {
            throw new RuntimeException(str);
        }
    }

    private boolean minutesNeedCorrection(int i) {
        assertNotSpinner("minutesNeedCorrection is not intended to be used with spinner, spinner won't allow picking invalid values");
        return timePickerHasCustomMinuteInterval() && i != snapRealMinutesToInterval(i);
    }

    /* access modifiers changed from: private */
    public boolean pickerIsInTextInputMode() {
        View findViewById = findViewById(this.mContext.getResources().getIdentifier("input_mode", "id", DeviceInfo.OS_NAME));
        return findViewById != null && findViewById.hasFocus();
    }

    private void correctEnteredMinutes(TimePicker timePicker, int i, int i2) {
        assertNotSpinner("spinner never needs to be corrected because wrong values are not offered to user (both in scrolling and textInput mode)!");
        final EditText editText = (EditText) timePicker.findFocus();
        final TimePicker timePicker2 = timePicker;
        final int i3 = i;
        final int i4 = i2;
        C27121 r1 = new Runnable() {
            public void run() {
                if (MinuteIntervalSnappableTimePickerDialog.this.pickerIsInTextInputMode()) {
                    timePicker2.setCurrentHour(Integer.valueOf(i3));
                    timePicker2.setCurrentMinute(Integer.valueOf(i4));
                    EditText editText = editText;
                    editText.setSelection(editText.getText().length());
                    return;
                }
                timePicker2.setCurrentHour(Integer.valueOf(i3));
                timePicker2.setCurrentMinute(0);
                timePicker2.setCurrentMinute(Integer.valueOf(i4));
            }
        };
        this.runnable = r1;
        this.handler.postDelayed(r1, 500);
    }

    public void onTimeChanged(TimePicker timePicker, int i, int i2) {
        int realMinutes = getRealMinutes(i2);
        this.handler.removeCallbacks(this.runnable);
        if (isSpinner() || !minutesNeedCorrection(realMinutes)) {
            super.onTimeChanged(timePicker, i, i2);
        } else {
            correctEnteredMinutes(timePicker, i, snapRealMinutesToInterval(realMinutes));
        }
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        if (this.mTimePicker == null || i != -1 || !timePickerHasCustomMinuteInterval()) {
            super.onClick(dialogInterface, i);
            return;
        }
        int intValue = this.mTimePicker.getCurrentHour().intValue();
        int realMinutes = getRealMinutes();
        if (!isSpinner()) {
            realMinutes = snapRealMinutesToInterval(realMinutes);
        }
        TimePickerDialog.OnTimeSetListener onTimeSetListener = this.mTimeSetListener;
        if (onTimeSetListener != null) {
            onTimeSetListener.onTimeSet(this.mTimePicker, intValue, realMinutes);
        }
    }

    public void updateTime(int i, int i2) {
        if (!timePickerHasCustomMinuteInterval()) {
            super.updateTime(i, i2);
        } else if (isSpinner()) {
            super.updateTime(i, snapRealMinutesToInterval(getRealMinutes()) / this.mTimePickerInterval);
        } else {
            super.updateTime(i, snapRealMinutesToInterval(i2));
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (timePickerHasCustomMinuteInterval()) {
            setupPickerDialog();
        }
    }

    private void setupPickerDialog() {
        TimePicker timePicker = (TimePicker) findViewById(this.mContext.getResources().getIdentifier("timePicker", "id", DeviceInfo.OS_NAME));
        this.mTimePicker = timePicker;
        int intValue = timePicker.getCurrentMinute().intValue();
        if (isSpinner()) {
            setSpinnerDisplayedValues();
            this.mTimePicker.setCurrentMinute(Integer.valueOf(snapRealMinutesToInterval(intValue) / this.mTimePickerInterval));
            return;
        }
        this.mTimePicker.setCurrentMinute(Integer.valueOf(snapRealMinutesToInterval(intValue)));
    }

    private void setSpinnerDisplayedValues() {
        NumberPicker numberPicker = (NumberPicker) findViewById(this.mContext.getResources().getIdentifier("minute", "id", DeviceInfo.OS_NAME));
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue((60 / this.mTimePickerInterval) - 1);
        ArrayList arrayList = new ArrayList(60 / this.mTimePickerInterval);
        int i = 0;
        while (i < 60) {
            arrayList.add(String.format("%02d", new Object[]{Integer.valueOf(i)}));
            i += this.mTimePickerInterval;
        }
        numberPicker.setDisplayedValues((String[]) arrayList.toArray(new String[0]));
    }

    public void onDetachedFromWindow() {
        this.handler.removeCallbacks(this.runnable);
        super.onDetachedFromWindow();
    }
}
