package p000ai.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/* renamed from: ai.api.util.ParametersConverter */
public final class ParametersConverter {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat(PROTOCOL_DATE_FORMAT, Locale.US);
    private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat(PROTOCOL_DATE_TIME_FORMAT, Locale.US);
    public static final String PROTOCOL_DATE_FORMAT = "yyyy-MM-dd";
    public static final String PROTOCOL_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String PROTOCOL_TIME_FORMAT = "HH:mm:ss";
    private static final DateFormat TIME_FORMAT = new SimpleDateFormat(PROTOCOL_TIME_FORMAT, Locale.US);

    private ParametersConverter() {
    }

    public static Date parseDateTime(String str) throws ParseException {
        if (str != null) {
            return DATE_TIME_FORMAT.parse(str);
        }
        throw new IllegalArgumentException("Parameter must not be null");
    }

    public static Date parseDate(String str) throws ParseException {
        if (str != null) {
            return DATE_FORMAT.parse(str);
        }
        throw new IllegalArgumentException("Parameter must not be null");
    }

    public static Date parseTime(String str) throws ParseException {
        if (str != null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(TIME_FORMAT.parse(str));
            Calendar instance2 = Calendar.getInstance();
            instance2.set(11, instance.get(11));
            instance2.set(12, instance.get(12));
            instance2.set(13, instance.get(13));
            return instance2.getTime();
        }
        throw new IllegalArgumentException("Parameter must not be null");
    }

    public static PartialDate parsePartialDate(String str) throws ParseException {
        if (str == null) {
            throw new IllegalArgumentException("Parameter must not be empty");
        } else if (str.length() == 0) {
            throw new ParseException("Parameter must not be empty", 0);
        } else if (!str.contains("u")) {
            return new PartialDate(DATE_FORMAT.parse(str));
        } else {
            String[] split = str.split("-");
            if (split.length == 3) {
                Integer parsePart = parsePart(split[0]);
                Integer parsePart2 = parsePart(split[1]);
                Integer parsePart3 = parsePart(split[2]);
                PartialDate partialDate = new PartialDate();
                partialDate.set(1, parsePart);
                partialDate.set(2, parsePart2);
                partialDate.set(5, parsePart3);
                return partialDate;
            }
            throw new ParseException(String.format("Partial date must have 3 parts, but have %s: %s", new Object[]{Integer.valueOf(split.length), str}), 0);
        }
    }

    public static int parseInteger(String str) throws NumberFormatException {
        return Integer.parseInt(str);
    }

    public static float parseFloat(String str) {
        if (str != null) {
            return Float.parseFloat(str);
        }
        throw new IllegalArgumentException("Parameter must not be empty");
    }

    private static Integer parsePart(String str) {
        if (str == null) {
            throw new IllegalArgumentException("part");
        } else if (str.indexOf(117) >= 0) {
            return PartialDate.UNSPECIFIED_VALUE;
        } else {
            return Integer.valueOf(Integer.parseInt(str));
        }
    }
}
