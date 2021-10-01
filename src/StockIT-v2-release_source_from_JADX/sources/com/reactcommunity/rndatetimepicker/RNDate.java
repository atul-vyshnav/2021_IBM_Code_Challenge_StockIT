package com.reactcommunity.rndatetimepicker;

import android.os.Bundle;
import java.util.Calendar;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;

public class RNDate {
    private Calendar now = Calendar.getInstance();

    public RNDate(Bundle bundle) {
        if (bundle != null && bundle.containsKey(RNConstants.ARG_TZOFFSET_MIN)) {
            this.now.setTimeZone(TimeZone.getTimeZone(TimeZones.GMT_ID));
            this.now.add(14, Integer.valueOf(bundle.getInt(RNConstants.ARG_TZOFFSET_MIN)).intValue() * 60000);
        }
        if (bundle != null && bundle.containsKey("value")) {
            set(bundle.getLong("value"));
        }
    }

    public void set(long j) {
        this.now.setTimeInMillis(j);
    }

    public int year() {
        return this.now.get(1);
    }

    public int month() {
        return this.now.get(2);
    }

    public int day() {
        return this.now.get(5);
    }

    public int hour() {
        return this.now.get(11);
    }

    public int minute() {
        return this.now.get(12);
    }
}
