package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.message.MessageFactory;

public interface LoggerContext {
    Object getExternalContext();

    ExtendedLogger getLogger(String str);

    ExtendedLogger getLogger(String str, MessageFactory messageFactory);

    boolean hasLogger(String str);
}
