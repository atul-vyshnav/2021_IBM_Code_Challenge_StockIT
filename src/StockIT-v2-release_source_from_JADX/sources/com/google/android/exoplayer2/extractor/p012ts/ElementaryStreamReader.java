package com.google.android.exoplayer2.extractor.p012ts;

import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.extractor.ExtractorOutput;
import com.google.android.exoplayer2.extractor.p012ts.TsPayloadReader;
import com.google.android.exoplayer2.util.ParsableByteArray;

/* renamed from: com.google.android.exoplayer2.extractor.ts.ElementaryStreamReader */
public interface ElementaryStreamReader {
    void consume(ParsableByteArray parsableByteArray) throws ParserException;

    void createTracks(ExtractorOutput extractorOutput, TsPayloadReader.TrackIdGenerator trackIdGenerator);

    void packetFinished();

    void packetStarted(long j, boolean z);

    void seek();
}
