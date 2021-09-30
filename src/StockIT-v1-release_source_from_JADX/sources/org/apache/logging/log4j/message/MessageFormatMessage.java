package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.IllegalFormatException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;

public class MessageFormatMessage implements Message {
    private static final int HASHVAL = 31;
    private static final Logger LOGGER = StatusLogger.getLogger();
    private static final long serialVersionUID = 1;
    private transient String formattedMessage;
    private String messagePattern;
    private transient Object[] parameters;
    private String[] serializedParameters;
    private transient Throwable throwable;

    public MessageFormatMessage(String str, Object... objArr) {
        int i;
        this.messagePattern = str;
        this.parameters = objArr;
        if (objArr == null) {
            i = 0;
        } else {
            i = objArr.length;
        }
        if (i > 0) {
            int i2 = i - 1;
            if (objArr[i2] instanceof Throwable) {
                this.throwable = objArr[i2];
            }
        }
    }

    public String getFormattedMessage() {
        if (this.formattedMessage == null) {
            this.formattedMessage = formatMessage(this.messagePattern, this.parameters);
        }
        return this.formattedMessage;
    }

    public String getFormat() {
        return this.messagePattern;
    }

    public Object[] getParameters() {
        Object[] objArr = this.parameters;
        if (objArr != null) {
            return objArr;
        }
        return this.serializedParameters;
    }

    /* access modifiers changed from: protected */
    public String formatMessage(String str, Object... objArr) {
        try {
            return MessageFormat.format(str, objArr);
        } catch (IllegalFormatException e) {
            Logger logger = LOGGER;
            logger.error("Unable to format msg: " + str, (Throwable) e);
            return str;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MessageFormatMessage messageFormatMessage = (MessageFormatMessage) obj;
        String str = this.messagePattern;
        if (str == null ? messageFormatMessage.messagePattern == null : str.equals(messageFormatMessage.messagePattern)) {
            return Arrays.equals(this.serializedParameters, messageFormatMessage.serializedParameters);
        }
        return false;
    }

    public int hashCode() {
        String str = this.messagePattern;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String[] strArr = this.serializedParameters;
        if (strArr != null) {
            i = Arrays.hashCode(strArr);
        }
        return hashCode + i;
    }

    public String toString() {
        return "StringFormatMessage[messagePattern=" + this.messagePattern + ", args=" + Arrays.toString(this.parameters) + ']';
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        getFormattedMessage();
        objectOutputStream.writeUTF(this.formattedMessage);
        objectOutputStream.writeUTF(this.messagePattern);
        Object[] objArr = this.parameters;
        int length = objArr == null ? 0 : objArr.length;
        objectOutputStream.writeInt(length);
        this.serializedParameters = new String[length];
        if (length > 0) {
            for (int i = 0; i < length; i++) {
                this.serializedParameters[i] = String.valueOf(this.parameters[i]);
                objectOutputStream.writeUTF(this.serializedParameters[i]);
            }
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        this.parameters = null;
        this.throwable = null;
        this.formattedMessage = objectInputStream.readUTF();
        this.messagePattern = objectInputStream.readUTF();
        int readInt = objectInputStream.readInt();
        this.serializedParameters = new String[readInt];
        for (int i = 0; i < readInt; i++) {
            this.serializedParameters[i] = objectInputStream.readUTF();
        }
    }

    public Throwable getThrowable() {
        return this.throwable;
    }
}
