package com.polidea.rxandroidble.internal.connection;

class ConstantPayloadSizeLimit implements PayloadSizeLimitProvider {
    private final int limit;

    ConstantPayloadSizeLimit(int i) {
        this.limit = i;
    }

    public int getPayloadSizeLimit() {
        return this.limit;
    }
}
