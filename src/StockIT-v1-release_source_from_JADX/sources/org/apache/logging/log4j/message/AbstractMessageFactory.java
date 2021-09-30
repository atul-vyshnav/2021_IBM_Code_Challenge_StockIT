package org.apache.logging.log4j.message;

import java.io.Serializable;

public abstract class AbstractMessageFactory implements MessageFactory, Serializable {
    private static final long serialVersionUID = 1;

    public abstract Message newMessage(String str, Object... objArr);

    public Message newMessage(Object obj) {
        return new ObjectMessage(obj);
    }

    public Message newMessage(String str) {
        return new SimpleMessage(str);
    }
}
