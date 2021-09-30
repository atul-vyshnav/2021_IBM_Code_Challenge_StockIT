package org.apache.commons.lang3.builder;

final class IDKey {

    /* renamed from: id */
    private final int f1324id;
    private final Object value;

    IDKey(Object obj) {
        this.f1324id = System.identityHashCode(obj);
        this.value = obj;
    }

    public int hashCode() {
        return this.f1324id;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof IDKey)) {
            return false;
        }
        IDKey iDKey = (IDKey) obj;
        if (this.f1324id == iDKey.f1324id && this.value == iDKey.value) {
            return true;
        }
        return false;
    }
}
