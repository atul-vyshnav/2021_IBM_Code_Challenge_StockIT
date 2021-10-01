package com.google.android.exoplayer2.extractor.mp3;

import android.util.Pair;
import com.google.android.exoplayer2.C1379C;
import com.google.android.exoplayer2.extractor.SeekMap;
import com.google.android.exoplayer2.extractor.SeekPoint;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.metadata.id3.MlltFrame;
import com.google.android.exoplayer2.util.Util;

final class MlltSeeker implements Mp3Extractor.Seeker {
    private final long durationUs;
    private final long[] referencePositions;
    private final long[] referenceTimesMs;

    public long getDataEndPosition() {
        return -1;
    }

    public boolean isSeekable() {
        return true;
    }

    public static MlltSeeker create(long j, MlltFrame mlltFrame) {
        int length = mlltFrame.bytesDeviations.length;
        int i = length + 1;
        long[] jArr = new long[i];
        long[] jArr2 = new long[i];
        jArr[0] = j;
        long j2 = 0;
        jArr2[0] = 0;
        for (int i2 = 1; i2 <= length; i2++) {
            int i3 = i2 - 1;
            j += (long) (mlltFrame.bytesBetweenReference + mlltFrame.bytesDeviations[i3]);
            j2 += (long) (mlltFrame.millisecondsBetweenReference + mlltFrame.millisecondsDeviations[i3]);
            jArr[i2] = j;
            jArr2[i2] = j2;
        }
        return new MlltSeeker(jArr, jArr2);
    }

    private MlltSeeker(long[] jArr, long[] jArr2) {
        this.referencePositions = jArr;
        this.referenceTimesMs = jArr2;
        this.durationUs = C1379C.msToUs(jArr2[jArr2.length - 1]);
    }

    public SeekMap.SeekPoints getSeekPoints(long j) {
        Pair<Long, Long> linearlyInterpolate = linearlyInterpolate(C1379C.usToMs(Util.constrainValue(j, 0, this.durationUs)), this.referenceTimesMs, this.referencePositions);
        return new SeekMap.SeekPoints(new SeekPoint(C1379C.msToUs(((Long) linearlyInterpolate.first).longValue()), ((Long) linearlyInterpolate.second).longValue()));
    }

    public long getTimeUs(long j) {
        return C1379C.msToUs(((Long) linearlyInterpolate(j, this.referencePositions, this.referenceTimesMs).second).longValue());
    }

    public long getDurationUs() {
        return this.durationUs;
    }

    private static Pair<Long, Long> linearlyInterpolate(long j, long[] jArr, long[] jArr2) {
        int binarySearchFloor = Util.binarySearchFloor(jArr, j, true, true);
        long j2 = jArr[binarySearchFloor];
        long j3 = jArr2[binarySearchFloor];
        int i = binarySearchFloor + 1;
        if (i == jArr.length) {
            return Pair.create(Long.valueOf(j2), Long.valueOf(j3));
        }
        long j4 = jArr[i];
        return Pair.create(Long.valueOf(j), Long.valueOf(((long) ((j4 == j2 ? 0.0d : (((double) j) - ((double) j2)) / ((double) (j4 - j2))) * ((double) (jArr2[i] - j3)))) + j3));
    }
}
