package org.apache.logging.log4j.message;

public class FormattedMessageFactory extends AbstractMessageFactory {
    private static final long serialVersionUID = 1;

    public Message newMessage(String str, Object... objArr) {
        return new FormattedMessage(str, objArr);
    }
}
