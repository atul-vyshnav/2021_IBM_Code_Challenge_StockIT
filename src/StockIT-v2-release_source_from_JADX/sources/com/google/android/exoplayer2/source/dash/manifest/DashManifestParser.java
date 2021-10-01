package com.google.android.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Pair;
import android.util.Xml;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.exoplayer2.C1379C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.google.android.exoplayer2.source.dash.manifest.SegmentBase;
import com.google.android.exoplayer2.upstream.ParsingLoadable;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.UriUtil;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.util.XmlPullParserUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

public class DashManifestParser extends DefaultHandler implements ParsingLoadable.Parser<DashManifest> {
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final String TAG = "MpdParser";
    private final String contentId;
    private final XmlPullParserFactory xmlParserFactory;

    public DashManifestParser() {
        this((String) null);
    }

    public DashManifestParser(String str) {
        this.contentId = str;
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    public DashManifest parse(Uri uri, InputStream inputStream) throws IOException {
        try {
            XmlPullParser newPullParser = this.xmlParserFactory.newPullParser();
            newPullParser.setInput(inputStream, (String) null);
            if (newPullParser.next() == 2 && "MPD".equals(newPullParser.getName())) {
                return parseMediaPresentationDescription(newPullParser, uri.toString());
            }
            throw new ParserException("inputStream does not contain a valid media presentation description");
        } catch (XmlPullParserException e) {
            throw new ParserException((Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x017e A[LOOP:0: B:20:0x0071->B:68:0x017e, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x0143 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifest parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser r33, java.lang.String r34) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r32 = this;
            r0 = r33
            java.lang.String r1 = "availabilityStartTime"
            r2 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            long r5 = parseDateTime(r0, r1, r2)
            java.lang.String r1 = "mediaPresentationDuration"
            long r7 = parseDuration(r0, r1, r2)
            java.lang.String r1 = "minBufferTime"
            long r9 = parseDuration(r0, r1, r2)
            r1 = 0
            java.lang.String r4 = "type"
            java.lang.String r4 = r0.getAttributeValue(r1, r4)
            r12 = 0
            if (r4 == 0) goto L_0x002e
            java.lang.String r13 = "dynamic"
            boolean r4 = r13.equals(r4)
            if (r4 == 0) goto L_0x002e
            r13 = 1
            goto L_0x002f
        L_0x002e:
            r13 = 0
        L_0x002f:
            if (r13 == 0) goto L_0x0038
            java.lang.String r4 = "minimumUpdatePeriod"
            long r14 = parseDuration(r0, r4, r2)
            goto L_0x0039
        L_0x0038:
            r14 = r2
        L_0x0039:
            if (r13 == 0) goto L_0x0043
            java.lang.String r4 = "timeShiftBufferDepth"
            long r16 = parseDuration(r0, r4, r2)
            goto L_0x0045
        L_0x0043:
            r16 = r2
        L_0x0045:
            if (r13 == 0) goto L_0x004f
            java.lang.String r4 = "suggestedPresentationDelay"
            long r18 = parseDuration(r0, r4, r2)
            goto L_0x0051
        L_0x004f:
            r18 = r2
        L_0x0051:
            java.lang.String r4 = "publishTime"
            long r20 = parseDateTime(r0, r4, r2)
            java.util.ArrayList r4 = new java.util.ArrayList
            r4.<init>()
            if (r13 == 0) goto L_0x0061
            r22 = r2
            goto L_0x0063
        L_0x0061:
            r22 = 0
        L_0x0063:
            r11 = r22
            r24 = 0
            r25 = 0
            r22 = r1
            r23 = r22
            r1 = r34
            r34 = r23
        L_0x0071:
            r33.next()
            java.lang.String r2 = "BaseURL"
            boolean r2 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r2)
            if (r2 == 0) goto L_0x008e
            if (r24 != 0) goto L_0x0088
            java.lang.String r1 = parseBaseUrl(r0, r1)
            r2 = r34
            r24 = 1
            goto L_0x0136
        L_0x0088:
            r28 = r1
            r29 = r11
            goto L_0x0130
        L_0x008e:
            java.lang.String r2 = "ProgramInformation"
            boolean r2 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r2)
            if (r2 == 0) goto L_0x009c
            com.google.android.exoplayer2.source.dash.manifest.ProgramInformation r2 = r32.parseProgramInformation(r33)
            goto L_0x0136
        L_0x009c:
            java.lang.String r2 = "UTCTiming"
            boolean r2 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r2)
            if (r2 == 0) goto L_0x00ae
            com.google.android.exoplayer2.source.dash.manifest.UtcTimingElement r2 = r32.parseUtcTiming(r33)
            r22 = r2
        L_0x00aa:
            r2 = r34
            goto L_0x0136
        L_0x00ae:
            java.lang.String r2 = "Location"
            boolean r2 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r2)
            if (r2 == 0) goto L_0x00c1
            java.lang.String r2 = r33.nextText()
            android.net.Uri r2 = android.net.Uri.parse(r2)
            r23 = r2
            goto L_0x00aa
        L_0x00c1:
            java.lang.String r2 = "Period"
            boolean r2 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r2)
            if (r2 == 0) goto L_0x0129
            if (r25 != 0) goto L_0x0129
            r2 = r32
            android.util.Pair r3 = r2.parsePeriod(r0, r1, r11)
            r28 = r1
            java.lang.Object r1 = r3.first
            com.google.android.exoplayer2.source.dash.manifest.Period r1 = (com.google.android.exoplayer2.source.dash.manifest.Period) r1
            r29 = r11
            long r11 = r1.startMs
            r26 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r31 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r31 != 0) goto L_0x0106
            if (r13 == 0) goto L_0x00eb
            r11 = r29
            r25 = 1
            goto L_0x0124
        L_0x00eb:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r3 = "Unable to determine start of period "
            r1.append(r3)
            int r3 = r4.size()
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x0106:
            java.lang.Object r3 = r3.second
            java.lang.Long r3 = (java.lang.Long) r3
            long r11 = r3.longValue()
            r26 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            int r3 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r3 != 0) goto L_0x011d
            r11 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            goto L_0x0121
        L_0x011d:
            long r2 = r1.startMs
            long r2 = r2 + r11
            r11 = r2
        L_0x0121:
            r4.add(r1)
        L_0x0124:
            r2 = r34
            r1 = r28
            goto L_0x0136
        L_0x0129:
            r28 = r1
            r29 = r11
            maybeSkipTag(r33)
        L_0x0130:
            r2 = r34
            r1 = r28
            r11 = r29
        L_0x0136:
            java.lang.String r3 = "MPD"
            boolean r3 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r3)
            r26 = -9223372036854775807(0x8000000000000001, double:-4.9E-324)
            if (r3 == 0) goto L_0x017e
            int r0 = (r7 > r26 ? 1 : (r7 == r26 ? 0 : -1))
            if (r0 != 0) goto L_0x0158
            int r0 = (r11 > r26 ? 1 : (r11 == r26 ? 0 : -1))
            if (r0 == 0) goto L_0x014d
            r7 = r11
            goto L_0x0158
        L_0x014d:
            if (r13 == 0) goto L_0x0150
            goto L_0x0158
        L_0x0150:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "Unable to determine duration of static manifest."
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x0158:
            boolean r0 = r4.isEmpty()
            if (r0 != 0) goto L_0x0176
            r3 = r4
            r4 = r32
            r11 = r13
            r12 = r14
            r14 = r16
            r16 = r18
            r18 = r20
            r20 = r2
            r21 = r22
            r22 = r23
            r23 = r3
            com.google.android.exoplayer2.source.dash.manifest.DashManifest r0 = r4.buildMediaPresentationDescription(r5, r7, r9, r11, r12, r14, r16, r18, r20, r21, r22, r23)
            return r0
        L_0x0176:
            com.google.android.exoplayer2.ParserException r0 = new com.google.android.exoplayer2.ParserException
            java.lang.String r1 = "No periods found."
            r0.<init>((java.lang.String) r1)
            throw r0
        L_0x017e:
            r34 = r2
            r2 = r26
            goto L_0x0071
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseMediaPresentationDescription(org.xmlpull.v1.XmlPullParser, java.lang.String):com.google.android.exoplayer2.source.dash.manifest.DashManifest");
    }

    /* access modifiers changed from: protected */
    public DashManifest buildMediaPresentationDescription(long j, long j2, long j3, boolean z, long j4, long j5, long j6, long j7, ProgramInformation programInformation, UtcTimingElement utcTimingElement, Uri uri, List<Period> list) {
        return new DashManifest(j, j2, j3, z, j4, j5, j6, j7, programInformation, utcTimingElement, uri, list);
    }

    /* access modifiers changed from: protected */
    public UtcTimingElement parseUtcTiming(XmlPullParser xmlPullParser) {
        return buildUtcTimingElement(xmlPullParser.getAttributeValue((String) null, "schemeIdUri"), xmlPullParser.getAttributeValue((String) null, "value"));
    }

    /* access modifiers changed from: protected */
    public UtcTimingElement buildUtcTimingElement(String str, String str2) {
        return new UtcTimingElement(str, str2);
    }

    /* access modifiers changed from: protected */
    public Pair<Period, Long> parsePeriod(XmlPullParser xmlPullParser, String str, long j) throws XmlPullParserException, IOException {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, "id");
        long parseDuration = parseDuration(xmlPullParser, "start", j);
        long parseDuration2 = parseDuration(xmlPullParser, "duration", C1379C.TIME_UNSET);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        boolean z = false;
        SegmentBase segmentBase = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (!z) {
                    str = parseBaseUrl(xmlPullParser, str);
                    z = true;
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "AdaptationSet")) {
                arrayList.add(parseAdaptationSet(xmlPullParser, str, segmentBase));
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "EventStream")) {
                arrayList2.add(parseEventStream(xmlPullParser));
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                segmentBase = parseSegmentBase(xmlPullParser, (SegmentBase.SingleSegmentBase) null);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                segmentBase = parseSegmentList(xmlPullParser, (SegmentBase.SegmentList) null);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                segmentBase = parseSegmentTemplate(xmlPullParser, (SegmentBase.SegmentTemplate) null);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Period"));
        return Pair.create(buildPeriod(attributeValue, parseDuration, arrayList, arrayList2), Long.valueOf(parseDuration2));
    }

    /* access modifiers changed from: protected */
    public Period buildPeriod(String str, long j, List<AdaptationSet> list, List<EventStream> list2) {
        return new Period(str, j, list, list2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v19, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v5, resolved type: java.lang.String} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0236 A[LOOP:0: B:1:0x006a->B:59:0x0236, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01fd A[EDGE_INSN: B:60:0x01fd->B:53:0x01fd ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.AdaptationSet parseAdaptationSet(org.xmlpull.v1.XmlPullParser r41, java.lang.String r42, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r43) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r40 = this;
            r15 = r40
            r14 = r41
            java.lang.String r0 = "id"
            r1 = -1
            int r16 = parseInt(r14, r0, r1)
            int r0 = r40.parseContentType(r41)
            r13 = 0
            java.lang.String r2 = "mimeType"
            java.lang.String r17 = r14.getAttributeValue(r13, r2)
            java.lang.String r2 = "codecs"
            java.lang.String r18 = r14.getAttributeValue(r13, r2)
            java.lang.String r2 = "width"
            int r19 = parseInt(r14, r2, r1)
            java.lang.String r2 = "height"
            int r20 = parseInt(r14, r2, r1)
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            float r21 = parseFrameRate(r14, r2)
            java.lang.String r2 = "audioSamplingRate"
            int r22 = parseInt(r14, r2, r1)
            java.lang.String r12 = "lang"
            java.lang.String r2 = r14.getAttributeValue(r13, r12)
            java.lang.String r3 = "label"
            java.lang.String r23 = r14.getAttributeValue(r13, r3)
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.ArrayList r10 = new java.util.ArrayList
            r10.<init>()
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            java.util.ArrayList r8 = new java.util.ArrayList
            r8.<init>()
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            r24 = 0
            r6 = r42
            r25 = r43
            r5 = r0
            r4 = r2
            r29 = r13
            r26 = -1
            r27 = 0
            r28 = 0
        L_0x006a:
            r41.next()
            java.lang.String r0 = "BaseURL"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00a3
            if (r27 != 0) goto L_0x008f
            java.lang.String r0 = parseBaseUrl(r14, r6)
            r27 = 1
            r32 = r0
        L_0x007f:
            r6 = r7
            r34 = r8
            r35 = r9
            r7 = r10
            r37 = r11
            r38 = r12
            r39 = r13
            r1 = r14
            r8 = r5
            goto L_0x01f5
        L_0x008f:
            r30 = r4
            r2 = r5
            r32 = r6
            r6 = r7
            r34 = r8
            r35 = r9
            r7 = r10
            r37 = r11
            r38 = r12
            r39 = r13
            r1 = r14
            goto L_0x01f2
        L_0x00a3:
            java.lang.String r0 = "ContentProtection"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00c3
            android.util.Pair r0 = r40.parseContentProtection(r41)
            java.lang.Object r1 = r0.first
            if (r1 == 0) goto L_0x00b9
            java.lang.Object r1 = r0.first
            r29 = r1
            java.lang.String r29 = (java.lang.String) r29
        L_0x00b9:
            java.lang.Object r1 = r0.second
            if (r1 == 0) goto L_0x00fc
            java.lang.Object r0 = r0.second
            r11.add(r0)
            goto L_0x00fc
        L_0x00c3:
            java.lang.String r0 = "ContentComponent"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00ee
            java.lang.String r0 = r14.getAttributeValue(r13, r12)
            java.lang.String r0 = checkLanguageConsistency(r4, r0)
            int r1 = r40.parseContentType(r41)
            int r1 = checkContentTypeConsistency(r5, r1)
            r4 = r0
            r32 = r6
            r6 = r7
            r34 = r8
            r35 = r9
            r7 = r10
            r37 = r11
            r38 = r12
            r39 = r13
            r8 = r1
            r1 = r14
            goto L_0x01f5
        L_0x00ee:
            java.lang.String r0 = "Role"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x00ff
            int r0 = r40.parseRole(r41)
            r28 = r28 | r0
        L_0x00fc:
            r32 = r6
            goto L_0x007f
        L_0x00ff:
            java.lang.String r0 = "AudioChannelConfiguration"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x010e
            int r0 = r40.parseAudioChannelConfiguration(r41)
            r26 = r0
            goto L_0x00fc
        L_0x010e:
            java.lang.String r0 = "Accessibility"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x011f
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r9.add(r0)
            goto L_0x008f
        L_0x011f:
            java.lang.String r0 = "SupplementalProperty"
            boolean r1 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r1 == 0) goto L_0x0130
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r14, r0)
            r8.add(r0)
            goto L_0x008f
        L_0x0130:
            java.lang.String r0 = "Representation"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r14, r0)
            if (r0 == 0) goto L_0x0187
            r0 = r40
            r1 = r41
            r2 = r6
            r3 = r23
            r30 = r4
            r4 = r17
            r31 = r5
            r5 = r18
            r32 = r6
            r6 = r19
            r33 = r7
            r7 = r20
            r34 = r8
            r8 = r21
            r35 = r9
            r9 = r26
            r36 = r10
            r10 = r22
            r37 = r11
            r11 = r30
            r38 = r12
            r12 = r28
            r39 = r13
            r13 = r35
            r14 = r25
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r0 = r0.parseRepresentation(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            com.google.android.exoplayer2.Format r1 = r0.format
            int r1 = r15.getContentType(r1)
            r2 = r31
            int r1 = checkContentTypeConsistency(r2, r1)
            r6 = r33
            r6.add(r0)
            r8 = r1
            r4 = r30
            r7 = r36
            r1 = r41
            goto L_0x01f5
        L_0x0187:
            r30 = r4
            r2 = r5
            r32 = r6
            r6 = r7
            r34 = r8
            r35 = r9
            r36 = r10
            r37 = r11
            r38 = r12
            r39 = r13
            java.lang.String r0 = "SegmentBase"
            r1 = r41
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r1, r0)
            if (r0 == 0) goto L_0x01b3
            r0 = r25
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r0 = r15.parseSegmentBase(r1, r0)
        L_0x01ab:
            r25 = r0
            r8 = r2
            r4 = r30
            r7 = r36
            goto L_0x01f5
        L_0x01b3:
            java.lang.String r0 = "SegmentList"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r1, r0)
            if (r0 == 0) goto L_0x01c4
            r0 = r25
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r0 = r15.parseSegmentList(r1, r0)
            goto L_0x01ab
        L_0x01c4:
            java.lang.String r0 = "SegmentTemplate"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r1, r0)
            if (r0 == 0) goto L_0x01d5
            r0 = r25
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r0 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate) r0
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r0 = r15.parseSegmentTemplate(r1, r0)
            goto L_0x01ab
        L_0x01d5:
            java.lang.String r0 = "InbandEventStream"
            boolean r3 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r1, r0)
            if (r3 == 0) goto L_0x01e7
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r0 = parseDescriptor(r1, r0)
            r7 = r36
            r7.add(r0)
            goto L_0x01f2
        L_0x01e7:
            r7 = r36
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r41)
            if (r0 == 0) goto L_0x01f2
            r40.parseAdaptationSetChild(r41)
        L_0x01f2:
            r8 = r2
            r4 = r30
        L_0x01f5:
            java.lang.String r0 = "AdaptationSet"
            boolean r0 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r1, r0)
            if (r0 == 0) goto L_0x0236
            java.util.ArrayList r9 = new java.util.ArrayList
            int r0 = r6.size()
            r9.<init>(r0)
            r10 = 0
        L_0x0207:
            int r0 = r6.size()
            if (r10 >= r0) goto L_0x0227
            java.lang.Object r0 = r6.get(r10)
            r1 = r0
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r1 = (com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo) r1
            java.lang.String r2 = r15.contentId
            r0 = r40
            r3 = r29
            r4 = r37
            r5 = r7
            com.google.android.exoplayer2.source.dash.manifest.Representation r0 = r0.buildRepresentation(r1, r2, r3, r4, r5)
            r9.add(r0)
            int r10 = r10 + 1
            goto L_0x0207
        L_0x0227:
            r0 = r40
            r1 = r16
            r2 = r8
            r3 = r9
            r4 = r35
            r5 = r34
            com.google.android.exoplayer2.source.dash.manifest.AdaptationSet r0 = r0.buildAdaptationSet(r1, r2, r3, r4, r5)
            return r0
        L_0x0236:
            r14 = r1
            r10 = r7
            r5 = r8
            r8 = r34
            r9 = r35
            r11 = r37
            r12 = r38
            r13 = r39
            r7 = r6
            r6 = r32
            goto L_0x006a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseAdaptationSet(org.xmlpull.v1.XmlPullParser, java.lang.String, com.google.android.exoplayer2.source.dash.manifest.SegmentBase):com.google.android.exoplayer2.source.dash.manifest.AdaptationSet");
    }

    /* access modifiers changed from: protected */
    public AdaptationSet buildAdaptationSet(int i, int i2, List<Representation> list, List<Descriptor> list2, List<Descriptor> list3) {
        return new AdaptationSet(i, i2, list, list2, list3);
    }

    /* access modifiers changed from: protected */
    public int parseContentType(XmlPullParser xmlPullParser) {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, "contentType");
        if (TextUtils.isEmpty(attributeValue)) {
            return -1;
        }
        if (MimeTypes.BASE_TYPE_AUDIO.equals(attributeValue)) {
            return 1;
        }
        if (MimeTypes.BASE_TYPE_VIDEO.equals(attributeValue)) {
            return 2;
        }
        if ("text".equals(attributeValue)) {
            return 3;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int getContentType(Format format) {
        String str = format.sampleMimeType;
        if (TextUtils.isEmpty(str)) {
            return -1;
        }
        if (MimeTypes.isVideo(str)) {
            return 2;
        }
        if (MimeTypes.isAudio(str)) {
            return 1;
        }
        if (mimeTypeIsRawText(str)) {
            return 3;
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b9  */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x0136  */
    /* JADX WARNING: Removed duplicated region for block: B:74:0x0144 A[LOOP:1: B:35:0x00a2->B:74:0x0144, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:76:0x0134 A[EDGE_INSN: B:76:0x0134->B:70:0x0134 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.util.Pair<java.lang.String, com.google.android.exoplayer2.drm.DrmInitData.SchemeData> parseContentProtection(org.xmlpull.v1.XmlPullParser r17) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r16 = this;
            r0 = r17
            r1 = 0
            java.lang.String r2 = "schemeIdUri"
            java.lang.String r2 = r0.getAttributeValue(r1, r2)
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x009d
            java.lang.String r2 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r2)
            r5 = -1
            int r6 = r2.hashCode()
            r7 = 489446379(0x1d2c5beb, float:2.281153E-21)
            r8 = 2
            if (r6 == r7) goto L_0x003d
            r7 = 755418770(0x2d06c692, float:7.66111E-12)
            if (r6 == r7) goto L_0x0032
            r7 = 1812765994(0x6c0c9d2a, float:6.799672E26)
            if (r6 == r7) goto L_0x0027
            goto L_0x0047
        L_0x0027:
            java.lang.String r6 = "urn:mpeg:dash:mp4protection:2011"
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x0047
            r5 = 0
            goto L_0x0047
        L_0x0032:
            java.lang.String r6 = "urn:uuid:edef8ba9-79d6-4ace-a3c8-27dcd51d21ed"
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x0047
            r5 = 2
            goto L_0x0047
        L_0x003d:
            java.lang.String r6 = "urn:uuid:9a04f079-9840-4286-ab92-e65be0885f95"
            boolean r2 = r2.equals(r6)
            if (r2 == 0) goto L_0x0047
            r5 = 1
        L_0x0047:
            if (r5 == 0) goto L_0x0055
            if (r5 == r3) goto L_0x0051
            if (r5 == r8) goto L_0x004e
            goto L_0x009d
        L_0x004e:
            java.util.UUID r2 = com.google.android.exoplayer2.C1379C.WIDEVINE_UUID
            goto L_0x0053
        L_0x0051:
            java.util.UUID r2 = com.google.android.exoplayer2.C1379C.PLAYREADY_UUID
        L_0x0053:
            r5 = r1
            goto L_0x009f
        L_0x0055:
            java.lang.String r2 = "value"
            java.lang.String r2 = r0.getAttributeValue(r1, r2)
            java.lang.String r5 = "default_KID"
            java.lang.String r5 = com.google.android.exoplayer2.util.XmlPullParserUtil.getAttributeValueIgnorePrefix(r0, r5)
            boolean r6 = android.text.TextUtils.isEmpty(r5)
            if (r6 != 0) goto L_0x0097
            java.lang.String r6 = "00000000-0000-0000-0000-000000000000"
            boolean r6 = r6.equals(r5)
            if (r6 != 0) goto L_0x0097
            java.lang.String r6 = "\\s+"
            java.lang.String[] r5 = r5.split(r6)
            int r6 = r5.length
            java.util.UUID[] r6 = new java.util.UUID[r6]
            r7 = 0
        L_0x007a:
            int r8 = r5.length
            if (r7 >= r8) goto L_0x0088
            r8 = r5[r7]
            java.util.UUID r8 = java.util.UUID.fromString(r8)
            r6[r7] = r8
            int r7 = r7 + 1
            goto L_0x007a
        L_0x0088:
            java.util.UUID r5 = com.google.android.exoplayer2.C1379C.COMMON_PSSH_UUID
            byte[] r5 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r5, r6, r1)
            java.util.UUID r6 = com.google.android.exoplayer2.C1379C.COMMON_PSSH_UUID
            r7 = r1
            r8 = 0
            r15 = r5
            r5 = r2
            r2 = r6
            r6 = r15
            goto L_0x00a2
        L_0x0097:
            r6 = r1
            r7 = r6
            r5 = r2
            r8 = 0
            r2 = r7
            goto L_0x00a2
        L_0x009d:
            r2 = r1
            r5 = r2
        L_0x009f:
            r6 = r5
            r7 = r6
            r8 = 0
        L_0x00a2:
            r17.next()
            java.lang.String r9 = "ms:laurl"
            boolean r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r9)
            if (r9 == 0) goto L_0x00b9
            java.lang.String r7 = "licenseUrl"
            java.lang.String r7 = r0.getAttributeValue(r1, r7)
        L_0x00b3:
            r10 = r2
            r13 = r6
        L_0x00b5:
            r11 = r7
            r14 = r8
            goto L_0x012c
        L_0x00b9:
            java.lang.String r9 = "widevine:license"
            boolean r9 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r9)
            if (r9 == 0) goto L_0x00d6
            java.lang.String r8 = "robustness_level"
            java.lang.String r8 = r0.getAttributeValue(r1, r8)
            if (r8 == 0) goto L_0x00d4
            java.lang.String r9 = "HW"
            boolean r8 = r8.startsWith(r9)
            if (r8 == 0) goto L_0x00d4
            r8 = 1
            goto L_0x00b3
        L_0x00d4:
            r8 = 0
            goto L_0x00b3
        L_0x00d6:
            r9 = 4
            if (r6 != 0) goto L_0x0101
            java.lang.String r10 = "pssh"
            boolean r10 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTagIgnorePrefix(r0, r10)
            if (r10 == 0) goto L_0x0101
            int r10 = r17.next()
            if (r10 != r9) goto L_0x0101
            java.lang.String r2 = r17.getText()
            byte[] r2 = android.util.Base64.decode(r2, r4)
            java.util.UUID r6 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.parseUuid(r2)
            if (r6 != 0) goto L_0x00fe
            java.lang.String r2 = "MpdParser"
            java.lang.String r9 = "Skipping malformed cenc:pssh data"
            com.google.android.exoplayer2.util.Log.m173w(r2, r9)
            r13 = r1
            goto L_0x00ff
        L_0x00fe:
            r13 = r2
        L_0x00ff:
            r10 = r6
            goto L_0x00b5
        L_0x0101:
            if (r6 != 0) goto L_0x0128
            java.util.UUID r10 = com.google.android.exoplayer2.C1379C.PLAYREADY_UUID
            boolean r10 = r10.equals(r2)
            if (r10 == 0) goto L_0x0128
            java.lang.String r10 = "mspr:pro"
            boolean r10 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r10)
            if (r10 == 0) goto L_0x0128
            int r10 = r17.next()
            if (r10 != r9) goto L_0x0128
            java.util.UUID r6 = com.google.android.exoplayer2.C1379C.PLAYREADY_UUID
            java.lang.String r9 = r17.getText()
            byte[] r9 = android.util.Base64.decode(r9, r4)
            byte[] r6 = com.google.android.exoplayer2.extractor.mp4.PsshAtomUtil.buildPsshAtom(r6, r9)
            goto L_0x00b3
        L_0x0128:
            maybeSkipTag(r17)
            goto L_0x00b3
        L_0x012c:
            java.lang.String r2 = "ContentProtection"
            boolean r2 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r2)
            if (r2 == 0) goto L_0x0144
            if (r10 == 0) goto L_0x013f
            com.google.android.exoplayer2.drm.DrmInitData$SchemeData r1 = new com.google.android.exoplayer2.drm.DrmInitData$SchemeData
            java.lang.String r12 = "video/mp4"
            r9 = r1
            r9.<init>(r10, r11, r12, r13, r14)
        L_0x013f:
            android.util.Pair r0 = android.util.Pair.create(r5, r1)
            return r0
        L_0x0144:
            r2 = r10
            r7 = r11
            r6 = r13
            r8 = r14
            goto L_0x00a2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseContentProtection(org.xmlpull.v1.XmlPullParser):android.util.Pair");
    }

    /* access modifiers changed from: protected */
    public int parseRole(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", (String) null);
        String parseString2 = parseString(xmlPullParser, "value", (String) null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Role"));
        return (!"urn:mpeg:dash:role:2011".equals(parseString) || !"main".equals(parseString2)) ? 0 : 1;
    }

    /* access modifiers changed from: protected */
    public void parseAdaptationSetChild(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        maybeSkipTag(xmlPullParser);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v18, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v8, resolved type: java.lang.String} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x015c A[LOOP:0: B:1:0x005b->B:44:0x015c, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0123 A[EDGE_INSN: B:45:0x0123->B:38:0x0123 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.RepresentationInfo parseRepresentation(org.xmlpull.v1.XmlPullParser r23, java.lang.String r24, java.lang.String r25, java.lang.String r26, java.lang.String r27, int r28, int r29, float r30, int r31, int r32, java.lang.String r33, int r34, java.util.List<com.google.android.exoplayer2.source.dash.manifest.Descriptor> r35, com.google.android.exoplayer2.source.dash.manifest.SegmentBase r36) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /*
            r22 = this;
            r15 = r22
            r0 = r23
            r1 = 0
            java.lang.String r2 = "id"
            java.lang.String r2 = r0.getAttributeValue(r1, r2)
            java.lang.String r3 = "bandwidth"
            r4 = -1
            int r9 = parseInt(r0, r3, r4)
            java.lang.String r3 = "mimeType"
            r4 = r26
            java.lang.String r3 = parseString(r0, r3, r4)
            java.lang.String r4 = "codecs"
            r5 = r27
            java.lang.String r13 = parseString(r0, r4, r5)
            java.lang.String r4 = "width"
            r5 = r28
            int r4 = parseInt(r0, r4, r5)
            java.lang.String r5 = "height"
            r6 = r29
            int r5 = parseInt(r0, r5, r6)
            r6 = r30
            float r6 = parseFrameRate(r0, r6)
            java.lang.String r7 = "audioSamplingRate"
            r8 = r32
            int r8 = parseInt(r0, r7, r8)
            java.util.ArrayList r14 = new java.util.ArrayList
            r14.<init>()
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>()
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            r7 = 0
            r7 = r31
            r10 = r36
            r16 = r1
            r17 = 0
            r1 = r24
        L_0x005b:
            r23.next()
            r24 = r7
            java.lang.String r7 = "BaseURL"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x007e
            if (r17 != 0) goto L_0x007a
            java.lang.String r1 = parseBaseUrl(r0, r1)
            r7 = 1
            r7 = r24
            r17 = r10
            r18 = r16
            r16 = r1
            r1 = 1
            goto L_0x011b
        L_0x007a:
            r26 = r1
            goto L_0x0111
        L_0x007e:
            java.lang.String r7 = "AudioChannelConfiguration"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x0092
            int r7 = r22.parseAudioChannelConfiguration(r23)
            r18 = r16
            r16 = r1
            r1 = r17
            goto L_0x0119
        L_0x0092:
            java.lang.String r7 = "SegmentBase"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x00ac
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r10 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase) r10
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r7 = r15.parseSegmentBase(r0, r10)
        L_0x00a0:
            r18 = r16
            r16 = r1
            r1 = r17
            r17 = r7
            r7 = r24
            goto L_0x011b
        L_0x00ac:
            java.lang.String r7 = "SegmentList"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x00bb
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r10 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentList) r10
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentList r7 = r15.parseSegmentList(r0, r10)
            goto L_0x00a0
        L_0x00bb:
            java.lang.String r7 = "SegmentTemplate"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x00ca
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r10 = (com.google.android.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate) r10
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SegmentTemplate r7 = r15.parseSegmentTemplate(r0, r10)
            goto L_0x00a0
        L_0x00ca:
            java.lang.String r7 = "ContentProtection"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r7)
            if (r7 == 0) goto L_0x00ec
            android.util.Pair r7 = r22.parseContentProtection(r23)
            r26 = r1
            java.lang.Object r1 = r7.first
            if (r1 == 0) goto L_0x00e2
            java.lang.Object r1 = r7.first
            r16 = r1
            java.lang.String r16 = (java.lang.String) r16
        L_0x00e2:
            java.lang.Object r1 = r7.second
            if (r1 == 0) goto L_0x0111
            java.lang.Object r1 = r7.second
            r14.add(r1)
            goto L_0x0111
        L_0x00ec:
            r26 = r1
            java.lang.String r1 = "InbandEventStream"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r1)
            if (r7 == 0) goto L_0x00fe
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r1 = parseDescriptor(r0, r1)
            r12.add(r1)
            goto L_0x0111
        L_0x00fe:
            java.lang.String r1 = "SupplementalProperty"
            boolean r7 = com.google.android.exoplayer2.util.XmlPullParserUtil.isStartTag(r0, r1)
            if (r7 == 0) goto L_0x010e
            com.google.android.exoplayer2.source.dash.manifest.Descriptor r1 = parseDescriptor(r0, r1)
            r11.add(r1)
            goto L_0x0111
        L_0x010e:
            maybeSkipTag(r23)
        L_0x0111:
            r7 = r24
            r18 = r16
            r1 = r17
            r16 = r26
        L_0x0119:
            r17 = r10
        L_0x011b:
            java.lang.String r10 = "Representation"
            boolean r10 = com.google.android.exoplayer2.util.XmlPullParserUtil.isEndTag(r0, r10)
            if (r10 == 0) goto L_0x015c
            r0 = r22
            r1 = r2
            r2 = r25
            r10 = r33
            r19 = r11
            r11 = r34
            r20 = r12
            r12 = r35
            r21 = r14
            r14 = r19
            com.google.android.exoplayer2.Format r0 = r0.buildFormat(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14)
            if (r17 == 0) goto L_0x013f
            r1 = r17
            goto L_0x0144
        L_0x013f:
            com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase r1 = new com.google.android.exoplayer2.source.dash.manifest.SegmentBase$SingleSegmentBase
            r1.<init>()
        L_0x0144:
            com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo r2 = new com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo
            r3 = -1
            r23 = r2
            r24 = r0
            r25 = r16
            r26 = r1
            r27 = r18
            r28 = r21
            r29 = r20
            r30 = r3
            r23.<init>(r24, r25, r26, r27, r28, r29, r30)
            return r2
        L_0x015c:
            r10 = r17
            r17 = r1
            r1 = r16
            r16 = r18
            goto L_0x005b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseRepresentation(org.xmlpull.v1.XmlPullParser, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, int, float, int, int, java.lang.String, int, java.util.List, com.google.android.exoplayer2.source.dash.manifest.SegmentBase):com.google.android.exoplayer2.source.dash.manifest.DashManifestParser$RepresentationInfo");
    }

    /* access modifiers changed from: protected */
    public Format buildFormat(String str, String str2, String str3, int i, int i2, float f, int i3, int i4, int i5, String str4, int i6, List<Descriptor> list, String str5, List<Descriptor> list2) {
        String str6;
        int i7;
        int parseCea708AccessibilityChannel;
        String str7 = str3;
        String sampleMimeType = getSampleMimeType(str3, str5);
        if (sampleMimeType != null) {
            if (MimeTypes.AUDIO_E_AC3.equals(sampleMimeType)) {
                sampleMimeType = parseEac3SupplementalProperties(list2);
            }
            str6 = sampleMimeType;
            if (MimeTypes.isVideo(str6)) {
                return Format.createVideoContainerFormat(str, str2, str3, str6, str5, i5, i, i2, f, (List<byte[]>) null, i6);
            }
            if (MimeTypes.isAudio(str6)) {
                return Format.createAudioContainerFormat(str, str2, str3, str6, str5, i5, i3, i4, (List<byte[]>) null, i6, str4);
            }
            if (mimeTypeIsRawText(str6)) {
                if (MimeTypes.APPLICATION_CEA608.equals(str6)) {
                    parseCea708AccessibilityChannel = parseCea608AccessibilityChannel(list);
                } else if (MimeTypes.APPLICATION_CEA708.equals(str6)) {
                    parseCea708AccessibilityChannel = parseCea708AccessibilityChannel(list);
                } else {
                    i7 = -1;
                    return Format.createTextContainerFormat(str, str2, str3, str6, str5, i5, i6, str4, i7);
                }
                i7 = parseCea708AccessibilityChannel;
                return Format.createTextContainerFormat(str, str2, str3, str6, str5, i5, i6, str4, i7);
            }
        } else {
            str6 = sampleMimeType;
        }
        return Format.createContainerFormat(str, str2, str3, str6, str5, i5, i6, str4);
    }

    /* access modifiers changed from: protected */
    public Representation buildRepresentation(RepresentationInfo representationInfo, String str, String str2, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2) {
        Format format = representationInfo.format;
        if (representationInfo.drmSchemeType != null) {
            str2 = representationInfo.drmSchemeType;
        }
        ArrayList<DrmInitData.SchemeData> arrayList3 = representationInfo.drmSchemeDatas;
        arrayList3.addAll(arrayList);
        if (!arrayList3.isEmpty()) {
            filterRedundantIncompleteSchemeDatas(arrayList3);
            format = format.copyWithDrmInitData(new DrmInitData(str2, (List<DrmInitData.SchemeData>) arrayList3));
        }
        ArrayList<Descriptor> arrayList4 = representationInfo.inbandEventStreams;
        arrayList4.addAll(arrayList2);
        return Representation.newInstance(str, representationInfo.revisionId, format, representationInfo.baseUrl, representationInfo.segmentBase, arrayList4);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SingleSegmentBase parseSegmentBase(XmlPullParser xmlPullParser, SegmentBase.SingleSegmentBase singleSegmentBase) throws XmlPullParserException, IOException {
        long j;
        long j2;
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SegmentBase.SingleSegmentBase singleSegmentBase2 = singleSegmentBase;
        long parseLong = parseLong(xmlPullParser2, "timescale", singleSegmentBase2 != null ? singleSegmentBase2.timescale : 1);
        long j3 = 0;
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", singleSegmentBase2 != null ? singleSegmentBase2.presentationTimeOffset : 0);
        long j4 = singleSegmentBase2 != null ? singleSegmentBase2.indexStart : 0;
        if (singleSegmentBase2 != null) {
            j3 = singleSegmentBase2.indexLength;
        }
        RangedUri rangedUri = null;
        String attributeValue = xmlPullParser2.getAttributeValue((String) null, "indexRange");
        if (attributeValue != null) {
            String[] split = attributeValue.split("-");
            long parseLong3 = Long.parseLong(split[0]);
            j = (Long.parseLong(split[1]) - parseLong3) + 1;
            j2 = parseLong3;
        } else {
            j = j3;
            j2 = j4;
        }
        if (singleSegmentBase2 != null) {
            rangedUri = singleSegmentBase2.initialization;
        }
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentBase"));
        return buildSingleSegmentBase(rangedUri, parseLong, parseLong2, j2, j);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SingleSegmentBase buildSingleSegmentBase(RangedUri rangedUri, long j, long j2, long j3, long j4) {
        return new SegmentBase.SingleSegmentBase(rangedUri, j, j2, j3, j4);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentList parseSegmentList(XmlPullParser xmlPullParser, SegmentBase.SegmentList segmentList) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SegmentBase.SegmentList segmentList2 = segmentList;
        long j = 1;
        long parseLong = parseLong(xmlPullParser2, "timescale", segmentList2 != null ? segmentList2.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", segmentList2 != null ? segmentList2.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser2, "duration", segmentList2 != null ? segmentList2.duration : C1379C.TIME_UNSET);
        if (segmentList2 != null) {
            j = segmentList2.startNumber;
        }
        long parseLong4 = parseLong(xmlPullParser2, "startNumber", j);
        List list = null;
        RangedUri rangedUri = null;
        List<SegmentBase.SegmentTimelineElement> list2 = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTimeline")) {
                list2 = parseSegmentTimeline(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentURL")) {
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(parseSegmentUrl(xmlPullParser));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentList"));
        if (segmentList2 != null) {
            if (rangedUri == null) {
                rangedUri = segmentList2.initialization;
            }
            if (list2 == null) {
                list2 = segmentList2.segmentTimeline;
            }
            if (list == null) {
                list = segmentList2.mediaSegments;
            }
        }
        return buildSegmentList(rangedUri, parseLong, parseLong2, parseLong4, parseLong3, list2, list);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentList buildSegmentList(RangedUri rangedUri, long j, long j2, long j3, long j4, List<SegmentBase.SegmentTimelineElement> list, List<RangedUri> list2) {
        return new SegmentBase.SegmentList(rangedUri, j, j2, j3, j4, list, list2);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentTemplate parseSegmentTemplate(XmlPullParser xmlPullParser, SegmentBase.SegmentTemplate segmentTemplate) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        SegmentBase.SegmentTemplate segmentTemplate2 = segmentTemplate;
        long j = 1;
        long parseLong = parseLong(xmlPullParser2, "timescale", segmentTemplate2 != null ? segmentTemplate2.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser2, "presentationTimeOffset", segmentTemplate2 != null ? segmentTemplate2.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser2, "duration", segmentTemplate2 != null ? segmentTemplate2.duration : C1379C.TIME_UNSET);
        if (segmentTemplate2 != null) {
            j = segmentTemplate2.startNumber;
        }
        long parseLong4 = parseLong(xmlPullParser2, "startNumber", j);
        RangedUri rangedUri = null;
        UrlTemplate parseUrlTemplate = parseUrlTemplate(xmlPullParser2, "media", segmentTemplate2 != null ? segmentTemplate2.mediaTemplate : null);
        UrlTemplate parseUrlTemplate2 = parseUrlTemplate(xmlPullParser2, "initialization", segmentTemplate2 != null ? segmentTemplate2.initializationTemplate : null);
        List<SegmentBase.SegmentTimelineElement> list = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser2, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser2, "SegmentTimeline")) {
                list = parseSegmentTimeline(xmlPullParser);
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser2, "SegmentTemplate"));
        if (segmentTemplate2 != null) {
            if (rangedUri == null) {
                rangedUri = segmentTemplate2.initialization;
            }
            if (list == null) {
                list = segmentTemplate2.segmentTimeline;
            }
        }
        return buildSegmentTemplate(rangedUri, parseLong, parseLong2, parseLong4, parseLong3, list, parseUrlTemplate2, parseUrlTemplate);
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentTemplate buildSegmentTemplate(RangedUri rangedUri, long j, long j2, long j3, long j4, List<SegmentBase.SegmentTimelineElement> list, UrlTemplate urlTemplate, UrlTemplate urlTemplate2) {
        return new SegmentBase.SegmentTemplate(rangedUri, j, j2, j3, j4, list, urlTemplate, urlTemplate2);
    }

    /* access modifiers changed from: protected */
    public EventStream parseEventStream(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", "");
        String parseString2 = parseString(xmlPullParser, "value", "");
        long parseLong = parseLong(xmlPullParser, "timescale", 1);
        ArrayList arrayList = new ArrayList();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Event")) {
                arrayList.add(parseEvent(xmlPullParser, parseString, parseString2, parseLong, byteArrayOutputStream));
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "EventStream"));
        long[] jArr = new long[arrayList.size()];
        EventMessage[] eventMessageArr = new EventMessage[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            EventMessage eventMessage = (EventMessage) arrayList.get(i);
            jArr[i] = eventMessage.presentationTimeUs;
            eventMessageArr[i] = eventMessage;
        }
        return buildEventStream(parseString, parseString2, parseLong, jArr, eventMessageArr);
    }

    /* access modifiers changed from: protected */
    public EventStream buildEventStream(String str, String str2, long j, long[] jArr, EventMessage[] eventMessageArr) {
        return new EventStream(str, str2, j, jArr, eventMessageArr);
    }

    /* access modifiers changed from: protected */
    public EventMessage parseEvent(XmlPullParser xmlPullParser, String str, String str2, long j, ByteArrayOutputStream byteArrayOutputStream) throws IOException, XmlPullParserException {
        XmlPullParser xmlPullParser2 = xmlPullParser;
        long parseLong = parseLong(xmlPullParser2, "id", 0);
        long parseLong2 = parseLong(xmlPullParser2, "duration", C1379C.TIME_UNSET);
        long parseLong3 = parseLong(xmlPullParser2, "presentationTime", 0);
        long scaleLargeTimestamp = Util.scaleLargeTimestamp(parseLong2, 1000, j);
        long scaleLargeTimestamp2 = Util.scaleLargeTimestamp(parseLong3, 1000000, j);
        String parseString = parseString(xmlPullParser2, "messageData", (String) null);
        byte[] parseEventObject = parseEventObject(xmlPullParser2, byteArrayOutputStream);
        if (parseString != null) {
            parseEventObject = Util.getUtf8Bytes(parseString);
        }
        return buildEvent(str, str2, parseLong, scaleLargeTimestamp, parseEventObject, scaleLargeTimestamp2);
    }

    /* access modifiers changed from: protected */
    public byte[] parseEventObject(XmlPullParser xmlPullParser, ByteArrayOutputStream byteArrayOutputStream) throws XmlPullParserException, IOException {
        byteArrayOutputStream.reset();
        XmlSerializer newSerializer = Xml.newSerializer();
        newSerializer.setOutput(byteArrayOutputStream, "UTF-8");
        xmlPullParser.nextToken();
        while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Event")) {
            switch (xmlPullParser.getEventType()) {
                case 0:
                    newSerializer.startDocument((String) null, false);
                    break;
                case 1:
                    newSerializer.endDocument();
                    break;
                case 2:
                    newSerializer.startTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
                        newSerializer.attribute(xmlPullParser.getAttributeNamespace(i), xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
                    }
                    break;
                case 3:
                    newSerializer.endTag(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    break;
                case 4:
                    newSerializer.text(xmlPullParser.getText());
                    break;
                case 5:
                    newSerializer.cdsect(xmlPullParser.getText());
                    break;
                case 6:
                    newSerializer.entityRef(xmlPullParser.getText());
                    break;
                case 7:
                    newSerializer.ignorableWhitespace(xmlPullParser.getText());
                    break;
                case 8:
                    newSerializer.processingInstruction(xmlPullParser.getText());
                    break;
                case 9:
                    newSerializer.comment(xmlPullParser.getText());
                    break;
                case 10:
                    newSerializer.docdecl(xmlPullParser.getText());
                    break;
            }
            xmlPullParser.nextToken();
        }
        newSerializer.flush();
        return byteArrayOutputStream.toByteArray();
    }

    /* access modifiers changed from: protected */
    public EventMessage buildEvent(String str, String str2, long j, long j2, byte[] bArr, long j3) {
        return new EventMessage(str, str2, j2, j, bArr, j3);
    }

    /* access modifiers changed from: protected */
    public List<SegmentBase.SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        ArrayList arrayList = new ArrayList();
        long j = 0;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.LATITUDE_SOUTH)) {
                j = parseLong(xmlPullParser, "t", j);
                long parseLong = parseLong(xmlPullParser, "d", C1379C.TIME_UNSET);
                int parseInt = parseInt(xmlPullParser, "r", 0) + 1;
                for (int i = 0; i < parseInt; i++) {
                    arrayList.add(buildSegmentTimelineElement(j, parseLong));
                    j += parseLong;
                }
            } else {
                maybeSkipTag(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTimeline"));
        return arrayList;
    }

    /* access modifiers changed from: protected */
    public SegmentBase.SegmentTimelineElement buildSegmentTimelineElement(long j, long j2) {
        return new SegmentBase.SegmentTimelineElement(j, j2);
    }

    /* access modifiers changed from: protected */
    public UrlTemplate parseUrlTemplate(XmlPullParser xmlPullParser, String str, UrlTemplate urlTemplate) {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, str);
        return attributeValue != null ? UrlTemplate.compile(attributeValue) : urlTemplate;
    }

    /* access modifiers changed from: protected */
    public RangedUri parseInitialization(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "sourceURL", "range");
    }

    /* access modifiers changed from: protected */
    public RangedUri parseSegmentUrl(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "media", "mediaRange");
    }

    /* access modifiers changed from: protected */
    public RangedUri parseRangedUrl(XmlPullParser xmlPullParser, String str, String str2) {
        long j;
        long j2;
        String attributeValue = xmlPullParser.getAttributeValue((String) null, str);
        String attributeValue2 = xmlPullParser.getAttributeValue((String) null, str2);
        if (attributeValue2 != null) {
            String[] split = attributeValue2.split("-");
            j2 = Long.parseLong(split[0]);
            if (split.length == 2) {
                j = (Long.parseLong(split[1]) - j2) + 1;
                return buildRangedUri(attributeValue, j2, j);
            }
        } else {
            j2 = 0;
        }
        j = -1;
        return buildRangedUri(attributeValue, j2, j);
    }

    /* access modifiers changed from: protected */
    public RangedUri buildRangedUri(String str, long j, long j2) {
        return new RangedUri(str, j, j2);
    }

    /* access modifiers changed from: protected */
    public ProgramInformation parseProgramInformation(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        String str = null;
        String parseString = parseString(xmlPullParser, "moreInformationURL", (String) null);
        String parseString2 = parseString(xmlPullParser, "lang", (String) null);
        String str2 = null;
        String str3 = null;
        while (true) {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Title")) {
                str = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "Source")) {
                str2 = xmlPullParser.nextText();
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, ExifInterface.TAG_COPYRIGHT)) {
                str3 = xmlPullParser.nextText();
            } else {
                maybeSkipTag(xmlPullParser);
            }
            String str4 = str3;
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "ProgramInformation")) {
                return new ProgramInformation(str, str2, str4, parseString, parseString2);
            }
            str3 = str4;
        }
    }

    /* access modifiers changed from: protected */
    public int parseAudioChannelConfiguration(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", (String) null);
        int i = -1;
        if ("urn:mpeg:dash:23003:3:audio_channel_configuration:2011".equals(parseString)) {
            i = parseInt(xmlPullParser, "value", -1);
        } else if ("tag:dolby.com,2014:dash:audio_channel_configuration:2011".equals(parseString)) {
            i = parseDolbyChannelConfiguration(xmlPullParser);
        }
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "AudioChannelConfiguration"));
        return i;
    }

    public static void maybeSkipTag(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
            int i = 1;
            while (i != 0) {
                xmlPullParser.next();
                if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                    i++;
                } else if (XmlPullParserUtil.isEndTag(xmlPullParser)) {
                    i--;
                }
            }
        }
    }

    private static void filterRedundantIncompleteSchemeDatas(ArrayList<DrmInitData.SchemeData> arrayList) {
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            DrmInitData.SchemeData schemeData = arrayList.get(size);
            if (!schemeData.hasData()) {
                int i = 0;
                while (true) {
                    if (i >= arrayList.size()) {
                        break;
                    } else if (arrayList.get(i).canReplace(schemeData)) {
                        arrayList.remove(size);
                        break;
                    } else {
                        i++;
                    }
                }
            }
        }
    }

    private static String getSampleMimeType(String str, String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (mimeTypeIsRawText(str)) {
            return str;
        }
        if (MimeTypes.APPLICATION_MP4.equals(str)) {
            if (str2 != null) {
                if (str2.startsWith("stpp")) {
                    return MimeTypes.APPLICATION_TTML;
                }
                if (str2.startsWith("wvtt")) {
                    return MimeTypes.APPLICATION_MP4VTT;
                }
            }
        } else if (MimeTypes.APPLICATION_RAWCC.equals(str) && str2 != null) {
            if (str2.contains("cea708")) {
                return MimeTypes.APPLICATION_CEA708;
            }
            if (str2.contains("eia608") || str2.contains("cea608")) {
                return MimeTypes.APPLICATION_CEA608;
            }
        }
        return null;
    }

    private static boolean mimeTypeIsRawText(String str) {
        return MimeTypes.isText(str) || MimeTypes.APPLICATION_TTML.equals(str) || MimeTypes.APPLICATION_MP4VTT.equals(str) || MimeTypes.APPLICATION_CEA708.equals(str) || MimeTypes.APPLICATION_CEA608.equals(str);
    }

    private static String checkLanguageConsistency(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static int checkContentTypeConsistency(int i, int i2) {
        if (i == -1) {
            return i2;
        }
        if (i2 == -1) {
            return i;
        }
        Assertions.checkState(i == i2);
        return i;
    }

    protected static Descriptor parseDescriptor(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        String parseString = parseString(xmlPullParser, "schemeIdUri", "");
        String parseString2 = parseString(xmlPullParser, "value", (String) null);
        String parseString3 = parseString(xmlPullParser, "id", (String) null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(parseString, parseString2, parseString3);
    }

    protected static int parseCea608AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                Log.m173w(TAG, "Unable to parse CEA-608 channel number from: " + descriptor.value);
            }
        }
        return -1;
    }

    protected static int parseCea708AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                Log.m173w(TAG, "Unable to parse CEA-708 service block number from: " + descriptor.value);
            }
        }
        return -1;
    }

    protected static String parseEac3SupplementalProperties(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = list.get(i);
            if ("tag:dolby.com,2014:dash:DolbyDigitalPlusExtensionType:2014".equals(descriptor.schemeIdUri) && "ec+3".equals(descriptor.value)) {
                return MimeTypes.AUDIO_E_AC3_JOC;
            }
        }
        return MimeTypes.AUDIO_E_AC3;
    }

    protected static float parseFrameRate(XmlPullParser xmlPullParser, float f) {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, "frameRate");
        if (attributeValue == null) {
            return f;
        }
        Matcher matcher = FRAME_RATE_PATTERN.matcher(attributeValue);
        if (!matcher.matches()) {
            return f;
        }
        int parseInt = Integer.parseInt(matcher.group(1));
        String group = matcher.group(2);
        return !TextUtils.isEmpty(group) ? ((float) parseInt) / ((float) Integer.parseInt(group)) : (float) parseInt;
    }

    protected static long parseDuration(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, str);
        if (attributeValue == null) {
            return j;
        }
        return Util.parseXsDuration(attributeValue);
    }

    protected static long parseDateTime(XmlPullParser xmlPullParser, String str, long j) throws ParserException {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, str);
        if (attributeValue == null) {
            return j;
        }
        return Util.parseXsDateTime(attributeValue);
    }

    protected static String parseBaseUrl(XmlPullParser xmlPullParser, String str) throws XmlPullParserException, IOException {
        xmlPullParser.next();
        return UriUtil.resolve(str, xmlPullParser.getText());
    }

    protected static int parseInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, str);
        return attributeValue == null ? i : Integer.parseInt(attributeValue);
    }

    protected static long parseLong(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, str);
        return attributeValue == null ? j : Long.parseLong(attributeValue);
    }

    protected static String parseString(XmlPullParser xmlPullParser, String str, String str2) {
        String attributeValue = xmlPullParser.getAttributeValue((String) null, str);
        return attributeValue == null ? str2 : attributeValue;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static int parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser r5) {
        /*
            r0 = 0
            java.lang.String r1 = "value"
            java.lang.String r5 = r5.getAttributeValue(r0, r1)
            java.lang.String r5 = com.google.android.exoplayer2.util.Util.toLowerInvariant(r5)
            r0 = -1
            if (r5 != 0) goto L_0x0010
            return r0
        L_0x0010:
            int r1 = r5.hashCode()
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r1) {
                case 1596796: goto L_0x0039;
                case 2937391: goto L_0x002f;
                case 3094035: goto L_0x0025;
                case 3133436: goto L_0x001b;
                default: goto L_0x001a;
            }
        L_0x001a:
            goto L_0x0043
        L_0x001b:
            java.lang.String r1 = "fa01"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0043
            r5 = 3
            goto L_0x0044
        L_0x0025:
            java.lang.String r1 = "f801"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0043
            r5 = 2
            goto L_0x0044
        L_0x002f:
            java.lang.String r1 = "a000"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0043
            r5 = 1
            goto L_0x0044
        L_0x0039:
            java.lang.String r1 = "4000"
            boolean r5 = r5.equals(r1)
            if (r5 == 0) goto L_0x0043
            r5 = 0
            goto L_0x0044
        L_0x0043:
            r5 = -1
        L_0x0044:
            if (r5 == 0) goto L_0x0053
            if (r5 == r4) goto L_0x0052
            if (r5 == r3) goto L_0x0050
            if (r5 == r2) goto L_0x004d
            return r0
        L_0x004d:
            r5 = 8
            return r5
        L_0x0050:
            r5 = 6
            return r5
        L_0x0052:
            return r3
        L_0x0053:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.source.dash.manifest.DashManifestParser.parseDolbyChannelConfiguration(org.xmlpull.v1.XmlPullParser):int");
    }

    protected static final class RepresentationInfo {
        public final String baseUrl;
        public final ArrayList<DrmInitData.SchemeData> drmSchemeDatas;
        public final String drmSchemeType;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final long revisionId;
        public final SegmentBase segmentBase;

        public RepresentationInfo(Format format2, String str, SegmentBase segmentBase2, String str2, ArrayList<DrmInitData.SchemeData> arrayList, ArrayList<Descriptor> arrayList2, long j) {
            this.format = format2;
            this.baseUrl = str;
            this.segmentBase = segmentBase2;
            this.drmSchemeType = str2;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
            this.revisionId = j;
        }
    }
}
