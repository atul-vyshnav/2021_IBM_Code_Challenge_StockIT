package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.logging.log4j.status.StatusLogger;

public class LocalizedMessage implements Message, LoggerNameAwareMessage {
    private static final long serialVersionUID = 3893703791567290742L;
    private transient Object[] argArray;
    private String baseName;
    private String formattedMessage;
    private String key;
    private final Locale locale;
    private transient StatusLogger logger;
    private String loggerName;
    private transient ResourceBundle resourceBundle;
    private String[] stringArgs;
    private transient Throwable throwable;

    public LocalizedMessage(String str, Object[] objArr) {
        this((ResourceBundle) null, (Locale) null, str, objArr);
    }

    public LocalizedMessage(String str, String str2, Object[] objArr) {
        this(str, (Locale) null, str2, objArr);
    }

    public LocalizedMessage(ResourceBundle resourceBundle2, String str, Object[] objArr) {
        this(resourceBundle2, (Locale) null, str, objArr);
    }

    public LocalizedMessage(String str, Locale locale2, String str2, Object[] objArr) {
        this.logger = StatusLogger.getLogger();
        this.key = str2;
        this.argArray = objArr;
        this.throwable = null;
        this.baseName = str;
        this.resourceBundle = null;
        this.locale = locale2;
    }

    public LocalizedMessage(ResourceBundle resourceBundle2, Locale locale2, String str, Object[] objArr) {
        this.logger = StatusLogger.getLogger();
        this.key = str;
        this.argArray = objArr;
        this.throwable = null;
        this.baseName = null;
        this.resourceBundle = resourceBundle2;
        this.locale = locale2;
    }

    public LocalizedMessage(Locale locale2, String str, Object[] objArr) {
        this((ResourceBundle) null, locale2, str, objArr);
    }

    public LocalizedMessage(String str, Object obj) {
        this((ResourceBundle) null, (Locale) null, str, new Object[]{obj});
    }

    public LocalizedMessage(String str, String str2, Object obj) {
        this(str, (Locale) null, str2, new Object[]{obj});
    }

    public LocalizedMessage(ResourceBundle resourceBundle2, String str, Object obj) {
        this(resourceBundle2, (Locale) null, str, new Object[]{obj});
    }

    public LocalizedMessage(String str, Locale locale2, String str2, Object obj) {
        this(str, locale2, str2, new Object[]{obj});
    }

    public LocalizedMessage(ResourceBundle resourceBundle2, Locale locale2, String str, Object obj) {
        this(resourceBundle2, locale2, str, new Object[]{obj});
    }

    public LocalizedMessage(Locale locale2, String str, Object obj) {
        this((ResourceBundle) null, locale2, str, new Object[]{obj});
    }

    public LocalizedMessage(String str, Object obj, Object obj2) {
        this((ResourceBundle) null, (Locale) null, str, new Object[]{obj, obj2});
    }

    public LocalizedMessage(String str, String str2, Object obj, Object obj2) {
        this(str, (Locale) null, str2, new Object[]{obj, obj2});
    }

    public LocalizedMessage(ResourceBundle resourceBundle2, String str, Object obj, Object obj2) {
        this(resourceBundle2, (Locale) null, str, new Object[]{obj, obj2});
    }

    public LocalizedMessage(String str, Locale locale2, String str2, Object obj, Object obj2) {
        this(str, locale2, str2, new Object[]{obj, obj2});
    }

    public LocalizedMessage(ResourceBundle resourceBundle2, Locale locale2, String str, Object obj, Object obj2) {
        this(resourceBundle2, locale2, str, new Object[]{obj, obj2});
    }

    public LocalizedMessage(Locale locale2, String str, Object obj, Object obj2) {
        this((ResourceBundle) null, locale2, str, new Object[]{obj, obj2});
    }

    public void setLoggerName(String str) {
        this.loggerName = str;
    }

    public String getLoggerName() {
        return this.loggerName;
    }

    public String getFormattedMessage() {
        String str = this.formattedMessage;
        if (str != null) {
            return str;
        }
        ResourceBundle resourceBundle2 = this.resourceBundle;
        if (resourceBundle2 == null) {
            String str2 = this.baseName;
            if (str2 != null) {
                resourceBundle2 = getResourceBundle(str2, this.locale, false);
            } else {
                resourceBundle2 = getResourceBundle(this.loggerName, this.locale, true);
            }
        }
        String format = getFormat();
        if (resourceBundle2 != null && resourceBundle2.containsKey(format)) {
            format = resourceBundle2.getString(format);
        }
        Object[] objArr = this.argArray;
        if (objArr == null) {
            objArr = this.stringArgs;
        }
        FormattedMessage formattedMessage2 = new FormattedMessage(format, objArr);
        this.formattedMessage = formattedMessage2.getFormattedMessage();
        this.throwable = formattedMessage2.getThrowable();
        return this.formattedMessage;
    }

    public String getFormat() {
        return this.key;
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

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002d  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x005b A[EDGE_INSN: B:26:0x005b->B:23:0x005b ?: BREAK  , SYNTHETIC] */
    protected java.util.ResourceBundle getResourceBundle(java.lang.String r4, java.util.Locale r5, boolean r6) {
        /*
            r3 = this;
            r0 = 0
            if (r4 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.lang.String r1 = "Unable to locate ResourceBundle "
            if (r5 == 0) goto L_0x000d
            java.util.ResourceBundle r6 = java.util.ResourceBundle.getBundle(r4, r5)     // Catch:{ MissingResourceException -> 0x0013 }
            goto L_0x0011
        L_0x000d:
            java.util.ResourceBundle r6 = java.util.ResourceBundle.getBundle(r4)     // Catch:{ MissingResourceException -> 0x0013 }
        L_0x0011:
            r0 = r6
            goto L_0x002b
        L_0x0013:
            if (r6 != 0) goto L_0x002b
            org.apache.logging.log4j.status.StatusLogger r5 = r3.logger
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            r6.append(r1)
            r6.append(r4)
            java.lang.String r4 = r6.toString()
            r5.debug((java.lang.String) r4)
            return r0
        L_0x002b:
            if (r0 != 0) goto L_0x005b
            r6 = 46
            int r6 = r4.lastIndexOf(r6)
            if (r6 <= 0) goto L_0x005b
            r2 = 0
            java.lang.String r4 = r4.substring(r2, r6)
            if (r5 == 0) goto L_0x0041
            java.util.ResourceBundle r6 = java.util.ResourceBundle.getBundle(r4, r5)     // Catch:{ MissingResourceException -> 0x0046 }
            goto L_0x0011
        L_0x0041:
            java.util.ResourceBundle r6 = java.util.ResourceBundle.getBundle(r4)     // Catch:{ MissingResourceException -> 0x0046 }
            goto L_0x0011
        L_0x0046:
            org.apache.logging.log4j.status.StatusLogger r6 = r3.logger
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
            r2.append(r4)
            java.lang.String r2 = r2.toString()
            r6.debug((java.lang.String) r2)
            goto L_0x002b
        L_0x005b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.logging.log4j.message.LocalizedMessage.getResourceBundle(java.lang.String, java.util.Locale, boolean):java.util.ResourceBundle");
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        getFormattedMessage();
        objectOutputStream.writeUTF(this.formattedMessage);
        objectOutputStream.writeUTF(this.key);
        objectOutputStream.writeUTF(this.baseName);
        objectOutputStream.writeInt(this.argArray.length);
        Object[] objArr = this.argArray;
        this.stringArgs = new String[objArr.length];
        int i = 0;
        for (Object obj : objArr) {
            this.stringArgs[i] = obj.toString();
            i++;
        }
        objectOutputStream.writeObject(this.stringArgs);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.formattedMessage = objectInputStream.readUTF();
        this.key = objectInputStream.readUTF();
        this.baseName = objectInputStream.readUTF();
        objectInputStream.readInt();
        this.stringArgs = (String[]) objectInputStream.readObject();
        this.logger = StatusLogger.getLogger();
        this.resourceBundle = null;
        this.argArray = null;
    }
}
