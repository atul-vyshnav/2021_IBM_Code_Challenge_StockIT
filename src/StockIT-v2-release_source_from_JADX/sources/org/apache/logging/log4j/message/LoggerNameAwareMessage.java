package org.apache.logging.log4j.message;

public interface LoggerNameAwareMessage {
    String getLoggerName();

    void setLoggerName(String str);
}
