package p000ai.api.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

/* renamed from: ai.api.util.PartialDate */
public class PartialDate {
    private static final String UNSPECIFIED_DATE = "uu";
    private static final String UNSPECIFIED_HOUR = "uu";
    private static final String UNSPECIFIED_MINUTE = "uu";
    private static final String UNSPECIFIED_MONTH = "uu";
    public static final Integer UNSPECIFIED_VALUE = null;
    private static final String UNSPECIFIED_YEAR = "uuuu";

    /* renamed from: c */
    private final Calendar f2c;
    private final Set<Integer> unspecifiedFields;

    public PartialDate() {
        this.unspecifiedFields = new HashSet();
        this.f2c = Calendar.getInstance();
    }

    public PartialDate(Calendar calendar) {
        this.unspecifiedFields = new HashSet();
        this.f2c = calendar;
    }

    public PartialDate(Date date) {
        this.unspecifiedFields = new HashSet();
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        this.f2c = gregorianCalendar;
        gregorianCalendar.setTime(date);
    }

    public void set(int i, Integer num) {
        if (num != UNSPECIFIED_VALUE) {
            this.unspecifiedFields.remove(Integer.valueOf(i));
            this.f2c.set(i, num.intValue());
        } else if (i == 1) {
            this.unspecifiedFields.add(1);
        } else if (i == 2) {
            this.unspecifiedFields.add(2);
        } else if (i >= 3 && i <= 8) {
            this.unspecifiedFields.add(5);
        } else if (i >= 10 && i <= 11) {
            this.unspecifiedFields.add(11);
        } else if (i == 12) {
            this.unspecifiedFields.add(12);
        }
    }

    public Integer get(int i) {
        if (i == 1) {
            if (!this.unspecifiedFields.contains(1)) {
                return Integer.valueOf(this.f2c.get(i));
            }
            return UNSPECIFIED_VALUE;
        } else if (i == 2) {
            if (!this.unspecifiedFields.contains(2)) {
                return Integer.valueOf(this.f2c.get(i));
            }
            return UNSPECIFIED_VALUE;
        } else if (i < 3 || i > 8) {
            if (i < 10 || i > 11) {
                if (i != 12) {
                    return Integer.valueOf(this.f2c.get(i));
                }
                if (!this.unspecifiedFields.contains(12)) {
                    return Integer.valueOf(this.f2c.get(12));
                }
                return UNSPECIFIED_VALUE;
            } else if (!this.unspecifiedFields.contains(11)) {
                return Integer.valueOf(this.f2c.get(i));
            } else {
                return UNSPECIFIED_VALUE;
            }
        } else if (!this.unspecifiedFields.contains(5)) {
            return Integer.valueOf(this.f2c.get(i));
        } else {
            return UNSPECIFIED_VALUE;
        }
    }

    private String getFieldAsString(int i) {
        if (i == 1) {
            if (this.unspecifiedFields.contains(1)) {
                return UNSPECIFIED_YEAR;
            }
            return String.format("%4d", new Object[]{Integer.valueOf(this.f2c.get(i))});
        } else if (i == 2) {
            if (this.unspecifiedFields.contains(2)) {
                return "uu";
            }
            return String.format("%02d", new Object[]{Integer.valueOf(this.f2c.get(i))});
        } else if (i < 3 || i > 8) {
            if (i < 10 || i > 11) {
                if (i != 12) {
                    return String.format("%s", new Object[]{Integer.valueOf(this.f2c.get(i))});
                } else if (this.unspecifiedFields.contains(12)) {
                    return "uu";
                } else {
                    return String.format("%02d", new Object[]{Integer.valueOf(this.f2c.get(i))});
                }
            } else if (this.unspecifiedFields.contains(11)) {
                return "uu";
            } else {
                return String.format("%02d", new Object[]{Integer.valueOf(this.f2c.get(i))});
            }
        } else if (this.unspecifiedFields.contains(5)) {
            return "uu";
        } else {
            return String.format("%02d", new Object[]{Integer.valueOf(this.f2c.get(i))});
        }
    }

    public String toString() {
        return String.format("%s-%s-%s", new Object[]{getFieldAsString(1), getFieldAsString(2), getFieldAsString(5)});
    }
}
