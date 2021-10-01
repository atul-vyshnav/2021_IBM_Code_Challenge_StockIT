package com.polidea.rxandroidble.internal.scan;

import bleshadow.javax.inject.Inject;
import bleshadow.javax.inject.Named;
import com.polidea.rxandroidble.exceptions.BleScanException;
import java.util.Date;
import p030rx.Scheduler;

public class ScanPreconditionsVerifierApi24 implements ScanPreconditionsVerifier {
    private static final long EXCESSIVE_SCANNING_PERIOD = 30000;
    private static final int SCANS_LENGTH = 5;
    private final long[] previousChecks = new long[5];
    private final ScanPreconditionsVerifierApi18 scanPreconditionVerifierApi18;
    private final Scheduler timeScheduler;

    @Inject
    public ScanPreconditionsVerifierApi24(ScanPreconditionsVerifierApi18 scanPreconditionsVerifierApi18, @Named("computation") Scheduler scheduler) {
        this.scanPreconditionVerifierApi18 = scanPreconditionsVerifierApi18;
        this.timeScheduler = scheduler;
    }

    public void verify() {
        this.scanPreconditionVerifierApi18.verify();
        int oldestCheckTimestampIndex = getOldestCheckTimestampIndex();
        long j = this.previousChecks[oldestCheckTimestampIndex];
        long now = this.timeScheduler.now();
        if (now - j >= 30000) {
            this.previousChecks[oldestCheckTimestampIndex] = now;
            return;
        }
        throw new BleScanException((int) BleScanException.UNDOCUMENTED_SCAN_THROTTLE, new Date(j + 30000));
    }

    private int getOldestCheckTimestampIndex() {
        long j = Long.MAX_VALUE;
        int i = -1;
        for (int i2 = 0; i2 < 5; i2++) {
            long j2 = this.previousChecks[i2];
            if (j2 < j) {
                i = i2;
                j = j2;
            }
        }
        return i;
    }
}
