package org.apache.logging.log4j.message;

public class SimpleMessage implements Message {
    private static final long serialVersionUID = -8398002534962715992L;
    private final String message;

    public Object[] getParameters() {
        return null;
    }

    public Throwable getThrowable() {
        return null;
    }

    public SimpleMessage() {
        this((String) null);
    }

    public SimpleMessage(String str) {
        this.message = str;
    }

    public String getFormattedMessage() {
        return this.message;
    }

    public String getFormat() {
        return this.message;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        String str = this.message;
        String str2 = ((SimpleMessage) obj).message;
        if (str != null) {
            if (!str.equals(str2)) {
                return false;
            }
            return true;
        } else if (str2 == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.message;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "SimpleMessage[message=" + this.message + ']';
    }
}
