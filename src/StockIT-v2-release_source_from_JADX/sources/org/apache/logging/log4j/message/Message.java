package org.apache.logging.log4j.message;

import java.io.Serializable;

public interface Message extends Serializable {
    String getFormat();

    String getFormattedMessage();

    Object[] getParameters();

    Throwable getThrowable();
}
