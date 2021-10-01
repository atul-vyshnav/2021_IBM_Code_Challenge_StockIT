package com.polidea.rxandroidble.exceptions;

public class BleException extends RuntimeException {
    public BleException() {
    }

    public BleException(String str) {
        super(str);
    }

    public BleException(Throwable th) {
        super(th);
    }

    public BleException(String str, Throwable th) {
        super(str, th);
    }
}
