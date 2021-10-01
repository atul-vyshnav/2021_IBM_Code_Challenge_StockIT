package org.apache.logging.log4j.message;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParameterizedMessage implements Message {
    private static final char DELIM_START = '{';
    private static final char DELIM_STOP = '}';
    public static final String ERROR_MSG_SEPARATOR = ":";
    public static final String ERROR_PREFIX = "[!!!";
    public static final String ERROR_SEPARATOR = "=>";
    public static final String ERROR_SUFFIX = "!!!]";
    private static final char ESCAPE_CHAR = '\\';
    private static final int HASHVAL = 31;
    public static final String RECURSION_PREFIX = "[...";
    public static final String RECURSION_SUFFIX = "...]";
    private static final long serialVersionUID = -665975803997290697L;
    private transient Object[] argArray;
    private transient String formattedMessage;
    private final String messagePattern;
    private final String[] stringArgs;
    private transient Throwable throwable;

    public ParameterizedMessage(String str, String[] strArr, Throwable th) {
        this.messagePattern = str;
        this.stringArgs = strArr;
        this.throwable = th;
    }

    public ParameterizedMessage(String str, Object[] objArr, Throwable th) {
        this.messagePattern = str;
        this.throwable = th;
        this.stringArgs = argumentsToStrings(objArr);
    }

    public ParameterizedMessage(String str, Object[] objArr) {
        this.messagePattern = str;
        this.stringArgs = argumentsToStrings(objArr);
    }

    public ParameterizedMessage(String str, Object obj) {
        this(str, new Object[]{obj});
    }

    public ParameterizedMessage(String str, Object obj, Object obj2) {
        this(str, new Object[]{obj, obj2});
    }

    private String[] argumentsToStrings(Object[] objArr) {
        if (objArr == null) {
            return null;
        }
        int countArgumentPlaceholders = countArgumentPlaceholders(this.messagePattern);
        int length = objArr.length;
        if (countArgumentPlaceholders < objArr.length && this.throwable == null && (objArr[objArr.length - 1] instanceof Throwable)) {
            this.throwable = objArr[objArr.length - 1];
            length--;
        }
        Object[] objArr2 = new Object[length];
        this.argArray = objArr2;
        System.arraycopy(objArr, 0, objArr2, 0, length);
        if (countArgumentPlaceholders == 1 && this.throwable == null && objArr.length > 1) {
            return new String[]{deepToString(objArr)};
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = deepToString(objArr[i]);
        }
        return strArr;
    }

    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            this.formattedMessage = formatMessage(this.messagePattern, this.stringArgs);
        }
        return this.formattedMessage;
    }

    public String getFormat() {
        return this.messagePattern;
    }

    public Object[] getParameters() {
        Object[] objArr = this.argArray;
        if (objArr != null) {
            return objArr;
        }
        return this.stringArgs;
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    /* access modifiers changed from: protected */
    public String formatMessage(String str, String[] strArr) {
        return format(str, strArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ParameterizedMessage parameterizedMessage = (ParameterizedMessage) obj;
        String str = this.messagePattern;
        if (str == null ? parameterizedMessage.messagePattern == null : str.equals(parameterizedMessage.messagePattern)) {
            return Arrays.equals(this.stringArgs, parameterizedMessage.stringArgs);
        }
        return false;
    }

    public int hashCode() {
        String str = this.messagePattern;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String[] strArr = this.stringArgs;
        if (strArr != null) {
            i = Arrays.hashCode(strArr);
        }
        return hashCode + i;
    }

    public static String format(String str, Object[] objArr) {
        if (str == null || objArr == null || objArr.length == 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '\\') {
                i2++;
            } else {
                if (charAt == '{' && i < str.length() - 1) {
                    int i4 = i + 1;
                    if (str.charAt(i4) == '}') {
                        int i5 = i2 / 2;
                        for (int i6 = 0; i6 < i5; i6++) {
                            sb.append('\\');
                        }
                        if (i2 % 2 == 1) {
                            sb.append(DELIM_START);
                            sb.append(DELIM_STOP);
                        } else {
                            if (i3 < objArr.length) {
                                sb.append(objArr[i3]);
                            } else {
                                sb.append(DELIM_START);
                                sb.append(DELIM_STOP);
                            }
                            i3++;
                        }
                        i = i4;
                        i2 = 0;
                    }
                }
                if (i2 > 0) {
                    for (int i7 = 0; i7 < i2; i7++) {
                        sb.append('\\');
                    }
                    i2 = 0;
                }
                sb.append(charAt);
            }
            i++;
        }
        return sb.toString();
    }

    public static int countArgumentPlaceholders(String str) {
        if (str == null || str.indexOf(123) == -1) {
            return 0;
        }
        int i = 0;
        int i2 = 0;
        boolean z = false;
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '\\') {
                z = !z;
            } else {
                if (charAt == '{' && !z && i < str.length() - 1) {
                    int i3 = i + 1;
                    if (str.charAt(i3) == '}') {
                        i2++;
                        i = i3;
                    }
                }
                z = false;
            }
            i++;
        }
        return i2;
    }

    public static String deepToString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        StringBuilder sb = new StringBuilder();
        recursiveDeepToString(obj, sb, new HashSet());
        return sb.toString();
    }

    private static void recursiveDeepToString(Object obj, StringBuilder sb, Set<String> set) {
        if (obj == null) {
            sb.append("null");
        } else if (obj instanceof String) {
            sb.append(obj);
        } else {
            Class<?> cls = obj.getClass();
            boolean z = true;
            if (cls.isArray()) {
                if (cls == byte[].class) {
                    sb.append(Arrays.toString((byte[]) obj));
                } else if (cls == short[].class) {
                    sb.append(Arrays.toString((short[]) obj));
                } else if (cls == int[].class) {
                    sb.append(Arrays.toString((int[]) obj));
                } else if (cls == long[].class) {
                    sb.append(Arrays.toString((long[]) obj));
                } else if (cls == float[].class) {
                    sb.append(Arrays.toString((float[]) obj));
                } else if (cls == double[].class) {
                    sb.append(Arrays.toString((double[]) obj));
                } else if (cls == boolean[].class) {
                    sb.append(Arrays.toString((boolean[]) obj));
                } else if (cls == char[].class) {
                    sb.append(Arrays.toString((char[]) obj));
                } else {
                    String identityToString = identityToString(obj);
                    if (set.contains(identityToString)) {
                        sb.append(RECURSION_PREFIX);
                        sb.append(identityToString);
                        sb.append(RECURSION_SUFFIX);
                        return;
                    }
                    set.add(identityToString);
                    sb.append('[');
                    for (Object obj2 : (Object[]) obj) {
                        if (z) {
                            z = false;
                        } else {
                            sb.append(", ");
                        }
                        recursiveDeepToString(obj2, sb, new HashSet(set));
                    }
                    sb.append(']');
                }
            } else if (obj instanceof Map) {
                String identityToString2 = identityToString(obj);
                if (set.contains(identityToString2)) {
                    sb.append(RECURSION_PREFIX);
                    sb.append(identityToString2);
                    sb.append(RECURSION_SUFFIX);
                    return;
                }
                set.add(identityToString2);
                sb.append(DELIM_START);
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    recursiveDeepToString(key, sb, new HashSet(set));
                    sb.append('=');
                    recursiveDeepToString(value, sb, new HashSet(set));
                }
                sb.append(DELIM_STOP);
            } else if (obj instanceof Collection) {
                String identityToString3 = identityToString(obj);
                if (set.contains(identityToString3)) {
                    sb.append(RECURSION_PREFIX);
                    sb.append(identityToString3);
                    sb.append(RECURSION_SUFFIX);
                    return;
                }
                set.add(identityToString3);
                sb.append('[');
                for (Object next : (Collection) obj) {
                    if (z) {
                        z = false;
                    } else {
                        sb.append(", ");
                    }
                    recursiveDeepToString(next, sb, new HashSet(set));
                }
                sb.append(']');
            } else if (obj instanceof Date) {
                sb.append(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format((Date) obj));
            } else {
                try {
                    sb.append(obj.toString());
                } catch (Throwable th) {
                    sb.append(ERROR_PREFIX);
                    sb.append(identityToString(obj));
                    sb.append(ERROR_SEPARATOR);
                    String message = th.getMessage();
                    String name = th.getClass().getName();
                    sb.append(name);
                    if (!name.equals(message)) {
                        sb.append(ERROR_MSG_SEPARATOR);
                        sb.append(message);
                    }
                    sb.append(ERROR_SUFFIX);
                }
            }
        }
    }

    public static String identityToString(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(obj));
    }

    public String toString() {
        return "ParameterizedMessage[messagePattern=" + this.messagePattern + ", stringArgs=" + Arrays.toString(this.stringArgs) + ", throwable=" + this.throwable + ']';
    }
}
