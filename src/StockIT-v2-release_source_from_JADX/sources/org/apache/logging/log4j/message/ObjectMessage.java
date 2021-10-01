package org.apache.logging.log4j.message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ObjectMessage implements Message {
    private static final long serialVersionUID = -5903272448334166185L;
    private transient Object obj;
    private transient String objectString;

    public ObjectMessage(String str) {
        this.obj = str == null ? "null" : str;
    }

    public String getFormattedMessage() {
        if (this.objectString == null) {
            this.objectString = String.valueOf(this.obj);
        }
        return this.objectString;
    }

    public String getFormat() {
        return getFormattedMessage();
    }

    public Object[] getParameters() {
        return new Object[]{this.obj};
    }

    public boolean equals(Object obj2) {
        if (this == obj2) {
            return true;
        }
        if (obj2 == null || getClass() != obj2.getClass()) {
            return false;
        }
        Object obj3 = this.obj;
        Object obj4 = ((ObjectMessage) obj2).obj;
        if (obj3 != null) {
            return equalObjectsOrStrings(obj3, obj4);
        }
        if (obj4 == null) {
            return true;
        }
        return false;
    }

    private boolean equalObjectsOrStrings(Object obj2, Object obj3) {
        return obj2.equals(obj3) || String.valueOf(obj2).equals(String.valueOf(obj3));
    }

    public int hashCode() {
        Object obj2 = this.obj;
        if (obj2 != null) {
            return obj2.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "ObjectMessage[obj=" + getFormattedMessage() + ']';
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Object obj2 = this.obj;
        if (obj2 instanceof Serializable) {
            objectOutputStream.writeObject(obj2);
        } else {
            objectOutputStream.writeObject(String.valueOf(obj2));
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.obj = objectInputStream.readObject();
    }

    public Throwable getThrowable() {
        Object obj2 = this.obj;
        if (obj2 instanceof Throwable) {
            return (Throwable) obj2;
        }
        return null;
    }
}
