package org.apache.logging.log4j.message;

public final class ParameterizedMessageFactory extends AbstractMessageFactory {
    public static final ParameterizedMessageFactory INSTANCE = new ParameterizedMessageFactory();
    private static final long serialVersionUID = 1;

    public Message newMessage(String str, Object... objArr) {
        return new ParameterizedMessage(str, objArr);
    }
}
