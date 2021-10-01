package com.google.android.exoplayer2.extractor.wav;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.WavUtil;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

final class WavHeaderReader {
    private static final String TAG = "WavHeaderReader";

    public static WavHeader peek(ExtractorInput extractorInput) throws IOException, InterruptedException {
        Assertions.checkNotNull(extractorInput);
        ParsableByteArray parsableByteArray = new ParsableByteArray(16);
        if (ChunkHeader.peek(extractorInput, parsableByteArray).f126id != WavUtil.RIFF_FOURCC) {
            return null;
        }
        extractorInput.peekFully(parsableByteArray.data, 0, 4);
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        if (readInt != WavUtil.WAVE_FOURCC) {
            Log.m169e(TAG, "Unsupported RIFF format: " + readInt);
            return null;
        }
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (peek.f126id != WavUtil.FMT_FOURCC) {
            extractorInput.advancePeekPosition((int) peek.size);
            peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        }
        Assertions.checkState(peek.size >= 16);
        extractorInput.peekFully(parsableByteArray.data, 0, 16);
        parsableByteArray.setPosition(0);
        int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort2 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedShort3 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort4 = parsableByteArray.readLittleEndianUnsignedShort();
        int i = (readLittleEndianUnsignedShort2 * readLittleEndianUnsignedShort4) / 8;
        if (readLittleEndianUnsignedShort3 == i) {
            int encodingForType = WavUtil.getEncodingForType(readLittleEndianUnsignedShort, readLittleEndianUnsignedShort4);
            if (encodingForType == 0) {
                Log.m169e(TAG, "Unsupported WAV format: " + readLittleEndianUnsignedShort4 + " bit/sample, type " + readLittleEndianUnsignedShort);
                return null;
            }
            extractorInput.advancePeekPosition(((int) peek.size) - 16);
            return new WavHeader(readLittleEndianUnsignedShort2, readLittleEndianUnsignedIntToInt, readLittleEndianUnsignedIntToInt2, readLittleEndianUnsignedShort3, readLittleEndianUnsignedShort4, encodingForType);
        }
        throw new ParserException("Expected block alignment: " + i + "; got: " + readLittleEndianUnsignedShort3);
    }

    public static void skipToData(ExtractorInput extractorInput, WavHeader wavHeader) throws IOException, InterruptedException {
        Assertions.checkNotNull(extractorInput);
        Assertions.checkNotNull(wavHeader);
        extractorInput.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (peek.f126id != Util.getIntegerCodeForString("data")) {
            Log.m173w(TAG, "Ignoring unknown WAV chunk: " + peek.f126id);
            long j = peek.size + 8;
            if (peek.f126id == Util.getIntegerCodeForString("RIFF")) {
                j = 12;
            }
            if (j <= 2147483647L) {
                extractorInput.skipFully((int) j);
                peek = ChunkHeader.peek(extractorInput, parsableByteArray);
            } else {
                throw new ParserException("Chunk is too large (~2GB+) to skip; id: " + peek.f126id);
            }
        }
        extractorInput.skipFully(8);
        wavHeader.setDataBounds(extractorInput.getPosition(), peek.size);
    }

    private WavHeaderReader() {
    }

    private static final class ChunkHeader {
        public static final int SIZE_IN_BYTES = 8;

        /* renamed from: id */
        public final int f126id;
        public final long size;

        private ChunkHeader(int i, long j) {
            this.f126id = i;
            this.size = j;
        }

        public static ChunkHeader peek(ExtractorInput extractorInput, ParsableByteArray parsableByteArray) throws IOException, InterruptedException {
            extractorInput.peekFully(parsableByteArray.data, 0, 8);
            parsableByteArray.setPosition(0);
            return new ChunkHeader(parsableByteArray.readInt(), parsableByteArray.readLittleEndianUnsignedInt());
        }
    }
}
