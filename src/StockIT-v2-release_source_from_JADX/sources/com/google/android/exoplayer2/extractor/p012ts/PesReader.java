package com.google.android.exoplayer2.extractor.p012ts;

import com.google.android.exoplayer2.C1379C;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.p012ts.TsPayloadReader;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableBitArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.TimestampAdjuster;

/* renamed from: com.google.android.exoplayer2.extractor.ts.PesReader */
public final class PesReader implements TsPayloadReader {
    private static final int HEADER_SIZE = 9;
    private static final int MAX_HEADER_EXTENSION_SIZE = 10;
    private static final int PES_SCRATCH_SIZE = 10;
    private static final int STATE_FINDING_HEADER = 0;
    private static final int STATE_READING_BODY = 3;
    private static final int STATE_READING_HEADER = 1;
    private static final int STATE_READING_HEADER_EXTENSION = 2;
    private static final String TAG = "PesReader";
    private int bytesRead;
    private boolean dataAlignmentIndicator;
    private boolean dtsFlag;
    private int extendedHeaderLength;
    private int payloadSize;
    private final ParsableBitArray pesScratch = new ParsableBitArray(new byte[10]);
    private boolean ptsFlag;
    private final ElementaryStreamReader reader;
    private boolean seenFirstDts;
    private int state = 0;
    private long timeUs;
    private TimestampAdjuster timestampAdjuster;

    public PesReader(ElementaryStreamReader elementaryStreamReader) {
        this.reader = elementaryStreamReader;
    }

    public void init(TimestampAdjuster timestampAdjuster2, ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator) {
        this.timestampAdjuster = timestampAdjuster2;
        this.reader.createTracks(extractorOutput, trackIdGenerator);
    }

    public final void seek() {
        this.state = 0;
        this.bytesRead = 0;
        this.seenFirstDts = false;
        this.reader.seek();
    }

    public final void consume(ParsableByteArray parsableByteArray, boolean z) throws ParserException {
        if (z) {
            int i = this.state;
            if (!(i == 0 || i == 1)) {
                if (i == 2) {
                    Log.m173w(TAG, "Unexpected start indicator reading extended header");
                } else if (i == 3) {
                    if (this.payloadSize != -1) {
                        Log.m173w(TAG, "Unexpected start indicator: expected " + this.payloadSize + " more bytes");
                    }
                    this.reader.packetFinished();
                } else {
                    throw new IllegalStateException();
                }
            }
            setState(1);
        }
        while (parsableByteArray.bytesLeft() > 0) {
            int i2 = this.state;
            if (i2 != 0) {
                int i3 = 0;
                if (i2 != 1) {
                    if (i2 == 2) {
                        if (continueRead(parsableByteArray, this.pesScratch.data, Math.min(10, this.extendedHeaderLength)) && continueRead(parsableByteArray, (byte[]) null, this.extendedHeaderLength)) {
                            parseHeaderExtension();
                            this.reader.packetStarted(this.timeUs, this.dataAlignmentIndicator);
                            setState(3);
                        }
                    } else if (i2 == 3) {
                        int bytesLeft = parsableByteArray.bytesLeft();
                        int i4 = this.payloadSize;
                        if (i4 != -1) {
                            i3 = bytesLeft - i4;
                        }
                        if (i3 > 0) {
                            bytesLeft -= i3;
                            parsableByteArray.setLimit(parsableByteArray.getPosition() + bytesLeft);
                        }
                        this.reader.consume(parsableByteArray);
                        int i5 = this.payloadSize;
                        if (i5 != -1) {
                            int i6 = i5 - bytesLeft;
                            this.payloadSize = i6;
                            if (i6 == 0) {
                                this.reader.packetFinished();
                                setState(1);
                            }
                        }
                    } else {
                        throw new IllegalStateException();
                    }
                } else if (continueRead(parsableByteArray, this.pesScratch.data, 9)) {
                    if (parseHeader()) {
                        i3 = 2;
                    }
                    setState(i3);
                }
            } else {
                parsableByteArray.skipBytes(parsableByteArray.bytesLeft());
            }
        }
    }

    private void setState(int i) {
        this.state = i;
        this.bytesRead = 0;
    }

    private boolean continueRead(ParsableByteArray parsableByteArray, byte[] bArr, int i) {
        int min = Math.min(parsableByteArray.bytesLeft(), i - this.bytesRead);
        if (min <= 0) {
            return true;
        }
        if (bArr == null) {
            parsableByteArray.skipBytes(min);
        } else {
            parsableByteArray.readBytes(bArr, this.bytesRead, min);
        }
        int i2 = this.bytesRead + min;
        this.bytesRead = i2;
        if (i2 == i) {
            return true;
        }
        return false;
    }

    private boolean parseHeader() {
        this.pesScratch.setPosition(0);
        int readBits = this.pesScratch.readBits(24);
        if (readBits != 1) {
            Log.m173w(TAG, "Unexpected start code prefix: " + readBits);
            this.payloadSize = -1;
            return false;
        }
        this.pesScratch.skipBits(8);
        int readBits2 = this.pesScratch.readBits(16);
        this.pesScratch.skipBits(5);
        this.dataAlignmentIndicator = this.pesScratch.readBit();
        this.pesScratch.skipBits(2);
        this.ptsFlag = this.pesScratch.readBit();
        this.dtsFlag = this.pesScratch.readBit();
        this.pesScratch.skipBits(6);
        int readBits3 = this.pesScratch.readBits(8);
        this.extendedHeaderLength = readBits3;
        if (readBits2 == 0) {
            this.payloadSize = -1;
        } else {
            this.payloadSize = ((readBits2 + 6) - 9) - readBits3;
        }
        return true;
    }

    private void parseHeaderExtension() {
        this.pesScratch.setPosition(0);
        this.timeUs = C1379C.TIME_UNSET;
        if (this.ptsFlag) {
            this.pesScratch.skipBits(4);
            this.pesScratch.skipBits(1);
            this.pesScratch.skipBits(1);
            long readBits = (((long) this.pesScratch.readBits(3)) << 30) | ((long) (this.pesScratch.readBits(15) << 15)) | ((long) this.pesScratch.readBits(15));
            this.pesScratch.skipBits(1);
            if (!this.seenFirstDts && this.dtsFlag) {
                this.pesScratch.skipBits(4);
                this.pesScratch.skipBits(1);
                this.pesScratch.skipBits(1);
                this.pesScratch.skipBits(1);
                this.timestampAdjuster.adjustTsTimestamp((((long) this.pesScratch.readBits(3)) << 30) | ((long) (this.pesScratch.readBits(15) << 15)) | ((long) this.pesScratch.readBits(15)));
                this.seenFirstDts = true;
            }
            this.timeUs = this.timestampAdjuster.adjustTsTimestamp(readBits);
        }
    }
}
