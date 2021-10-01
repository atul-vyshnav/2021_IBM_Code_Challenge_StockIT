package org.apache.logging.log4j.message;

public final class StringFormatterMessageFactory extends AbstractMessageFactory {
    public static final StringFormatterMessageFactory INSTANCE = new StringFormatterMessageFactory();
    private static final long serialVersionUID = 1;

    public Message newMessage(String str, Object... objArr) {
        return new StringFormattedMessage(str, objArr);
    }
}
