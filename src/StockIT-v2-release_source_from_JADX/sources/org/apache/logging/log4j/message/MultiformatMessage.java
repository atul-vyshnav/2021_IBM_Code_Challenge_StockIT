package org.apache.logging.log4j.message;

public interface MultiformatMessage extends Message {
    String[] getFormats();

    String getFormattedMessage(String[] strArr);
}
