package org.apache.logging.log4j.simple;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public class SimpleLogger extends AbstractLogger {
    private static final char SPACE = ' ';
    private static final long serialVersionUID = 1;
    private DateFormat dateFormatter;
    private Level level;
    private final String logName;
    private final boolean showContextMap;
    private final boolean showDateTime;
    private PrintStream stream;

    public SimpleLogger(String str, Level level2, boolean z, boolean z2, boolean z3, boolean z4, String str2, MessageFactory messageFactory, PropertiesUtil propertiesUtil, PrintStream printStream) {
        super(str, messageFactory);
        this.level = Level.toLevel(propertiesUtil.getStringProperty("org.apache.logging.log4j.simplelog." + str + ".level"), level2);
        if (z2) {
            int lastIndexOf = str.lastIndexOf(".");
            if (lastIndexOf <= 0 || lastIndexOf >= str.length()) {
                this.logName = str;
            } else {
                this.logName = str.substring(lastIndexOf + 1);
            }
        } else if (z) {
            this.logName = str;
        } else {
            this.logName = null;
        }
        this.showDateTime = z3;
        this.showContextMap = z4;
        this.stream = printStream;
        if (z3) {
            try {
                this.dateFormatter = new SimpleDateFormat(str2);
            } catch (IllegalArgumentException unused) {
                this.dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss:SSS zzz");
            }
        }
    }

    public Level getLevel() {
        return this.level;
    }

    public boolean isEnabled(Level level2, Marker marker, Message message, Throwable th) {
        return this.level.intLevel() >= level2.intLevel();
    }

    public boolean isEnabled(Level level2, Marker marker, Object obj, Throwable th) {
        return this.level.intLevel() >= level2.intLevel();
    }

    public boolean isEnabled(Level level2, Marker marker, String str) {
        return this.level.intLevel() >= level2.intLevel();
    }

    public boolean isEnabled(Level level2, Marker marker, String str, Object... objArr) {
        return this.level.intLevel() >= level2.intLevel();
    }

    public boolean isEnabled(Level level2, Marker marker, String str, Throwable th) {
        return this.level.intLevel() >= level2.intLevel();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: java.lang.Throwable} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void logMessage(java.lang.String r4, org.apache.logging.log4j.Level r5, org.apache.logging.log4j.Marker r6, org.apache.logging.log4j.message.Message r7, java.lang.Throwable r8) {
        /*
            r3 = this;
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            boolean r6 = r3.showDateTime
            r0 = 32
            if (r6 == 0) goto L_0x0024
            java.util.Date r6 = new java.util.Date
            r6.<init>()
            java.text.DateFormat r1 = r3.dateFormatter
            monitor-enter(r1)
            java.text.DateFormat r2 = r3.dateFormatter     // Catch:{ all -> 0x0021 }
            java.lang.String r6 = r2.format(r6)     // Catch:{ all -> 0x0021 }
            monitor-exit(r1)     // Catch:{ all -> 0x0021 }
            r4.append(r6)
            r4.append(r0)
            goto L_0x0024
        L_0x0021:
            r4 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0021 }
            throw r4
        L_0x0024:
            java.lang.String r5 = r5.toString()
            r4.append(r5)
            r4.append(r0)
            java.lang.String r5 = r3.logName
            if (r5 == 0) goto L_0x0040
            int r5 = r5.length()
            if (r5 <= 0) goto L_0x0040
            java.lang.String r5 = r3.logName
            r4.append(r5)
            r4.append(r0)
        L_0x0040:
            java.lang.String r5 = r7.getFormattedMessage()
            r4.append(r5)
            boolean r5 = r3.showContextMap
            if (r5 == 0) goto L_0x0062
            java.util.Map r5 = org.apache.logging.log4j.ThreadContext.getContext()
            int r6 = r5.size()
            if (r6 <= 0) goto L_0x0062
            r4.append(r0)
            java.lang.String r5 = r5.toString()
            r4.append(r5)
            r4.append(r0)
        L_0x0062:
            java.lang.Object[] r5 = r7.getParameters()
            if (r8 != 0) goto L_0x007e
            if (r5 == 0) goto L_0x007e
            int r6 = r5.length
            if (r6 <= 0) goto L_0x007e
            int r6 = r5.length
            int r6 = r6 + -1
            r6 = r5[r6]
            boolean r6 = r6 instanceof java.lang.Throwable
            if (r6 == 0) goto L_0x007e
            int r6 = r5.length
            int r6 = r6 + -1
            r5 = r5[r6]
            r8 = r5
            java.lang.Throwable r8 = (java.lang.Throwable) r8
        L_0x007e:
            if (r8 == 0) goto L_0x0097
            r4.append(r0)
            java.io.ByteArrayOutputStream r5 = new java.io.ByteArrayOutputStream
            r5.<init>()
            java.io.PrintStream r6 = new java.io.PrintStream
            r6.<init>(r5)
            r8.printStackTrace(r6)
            java.lang.String r5 = r5.toString()
            r4.append(r5)
        L_0x0097:
            java.io.PrintStream r5 = r3.stream
            java.lang.String r4 = r4.toString()
            r5.println(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.logging.log4j.simple.SimpleLogger.logMessage(java.lang.String, org.apache.logging.log4j.Level, org.apache.logging.log4j.Marker, org.apache.logging.log4j.message.Message, java.lang.Throwable):void");
    }

    public void setLevel(Level level2) {
        if (level2 != null) {
            this.level = level2;
        }
    }

    public void setStream(PrintStream printStream) {
        this.stream = printStream;
    }
}
