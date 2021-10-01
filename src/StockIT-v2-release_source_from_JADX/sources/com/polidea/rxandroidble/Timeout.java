package com.polidea.rxandroidble;

import java.util.concurrent.TimeUnit;

public class Timeout {
    public final TimeUnit timeUnit;
    public final long timeout;

    public Timeout(long j, TimeUnit timeUnit2) {
        this.timeUnit = timeUnit2;
        this.timeout = j;
    }
}
