package com.google.android.exoplayer2.text.subrip;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.LongArray;
import com.google.android.exoplayer2.util.ParsableByteArray;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SubripDecoder extends SimpleSubtitleDecoder {
    private static final String ALIGN_BOTTOM_LEFT = "{\\an1}";
    private static final String ALIGN_BOTTOM_MID = "{\\an2}";
    private static final String ALIGN_BOTTOM_RIGHT = "{\\an3}";
    private static final String ALIGN_MID_LEFT = "{\\an4}";
    private static final String ALIGN_MID_MID = "{\\an5}";
    private static final String ALIGN_MID_RIGHT = "{\\an6}";
    private static final String ALIGN_TOP_LEFT = "{\\an7}";
    private static final String ALIGN_TOP_MID = "{\\an8}";
    private static final String ALIGN_TOP_RIGHT = "{\\an9}";
    static final float END_FRACTION = 0.92f;
    static final float MID_FRACTION = 0.5f;
    static final float START_FRACTION = 0.08f;
    private static final String SUBRIP_ALIGNMENT_TAG = "\\{\\\\an[1-9]\\}";
    private static final Pattern SUBRIP_TAG_PATTERN = Pattern.compile("\\{\\\\.*?\\}");
    private static final String SUBRIP_TIMECODE = "(?:(\\d+):)?(\\d+):(\\d+),(\\d+)";
    private static final Pattern SUBRIP_TIMING_LINE = Pattern.compile("\\s*((?:(\\d+):)?(\\d+):(\\d+),(\\d+))\\s*-->\\s*((?:(\\d+):)?(\\d+):(\\d+),(\\d+))?\\s*");
    private static final String TAG = "SubripDecoder";
    private final ArrayList<String> tags = new ArrayList<>();
    private final StringBuilder textBuilder = new StringBuilder();

    static float getFractionalPositionForAnchorType(int i) {
        if (i != 0) {
            return i != 1 ? END_FRACTION : MID_FRACTION;
        }
        return 0.08f;
    }

    public SubripDecoder() {
        super(TAG);
    }

    /* access modifiers changed from: protected */
    public SubripSubtitle decode(byte[] bArr, int i, boolean z) {
        String str;
        ArrayList arrayList = new ArrayList();
        LongArray longArray = new LongArray();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                break;
            } else if (readLine.length() != 0) {
                try {
                    Integer.parseInt(readLine);
                    String readLine2 = parsableByteArray.readLine();
                    if (readLine2 == null) {
                        Log.m173w(TAG, "Unexpected end");
                        break;
                    }
                    Matcher matcher = SUBRIP_TIMING_LINE.matcher(readLine2);
                    if (matcher.matches()) {
                        boolean z2 = true;
                        longArray.add(parseTimecode(matcher, 1));
                        int i2 = 0;
                        if (!TextUtils.isEmpty(matcher.group(6))) {
                            longArray.add(parseTimecode(matcher, 6));
                        } else {
                            z2 = false;
                        }
                        this.textBuilder.setLength(0);
                        this.tags.clear();
                        while (true) {
                            String readLine3 = parsableByteArray.readLine();
                            if (TextUtils.isEmpty(readLine3)) {
                                break;
                            }
                            if (this.textBuilder.length() > 0) {
                                this.textBuilder.append("<br>");
                            }
                            this.textBuilder.append(processLine(readLine3, this.tags));
                        }
                        Spanned fromHtml = Html.fromHtml(this.textBuilder.toString());
                        while (true) {
                            if (i2 >= this.tags.size()) {
                                str = null;
                                break;
                            }
                            str = this.tags.get(i2);
                            if (str.matches(SUBRIP_ALIGNMENT_TAG)) {
                                break;
                            }
                            i2++;
                        }
                        arrayList.add(buildCue(fromHtml, str));
                        if (z2) {
                            arrayList.add((Object) null);
                        }
                    } else {
                        Log.m173w(TAG, "Skipping invalid timing: " + readLine2);
                    }
                } catch (NumberFormatException unused) {
                    Log.m173w(TAG, "Skipping invalid index: " + readLine);
                }
            }
        }
        Cue[] cueArr = new Cue[arrayList.size()];
        arrayList.toArray(cueArr);
        return new SubripSubtitle(cueArr, longArray.toArray());
    }

    private String processLine(String str, ArrayList<String> arrayList) {
        String trim = str.trim();
        StringBuilder sb = new StringBuilder(trim);
        Matcher matcher = SUBRIP_TAG_PATTERN.matcher(trim);
        int i = 0;
        while (matcher.find()) {
            String group = matcher.group();
            arrayList.add(group);
            int start = matcher.start() - i;
            int length = group.length();
            sb.replace(start, start + length, "");
            i += length;
        }
        return sb.toString();
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.exoplayer2.text.Cue buildCue(android.text.Spanned r19, java.lang.String r20) {
        /*
            r18 = this;
            r0 = r20
            if (r0 != 0) goto L_0x000c
            com.google.android.exoplayer2.text.Cue r0 = new com.google.android.exoplayer2.text.Cue
            r2 = r19
            r0.<init>(r2)
            return r0
        L_0x000c:
            r2 = r19
            int r1 = r20.hashCode()
            java.lang.String r6 = "{\\an9}"
            java.lang.String r7 = "{\\an8}"
            java.lang.String r8 = "{\\an7}"
            java.lang.String r9 = "{\\an6}"
            java.lang.String r10 = "{\\an5}"
            java.lang.String r11 = "{\\an4}"
            java.lang.String r12 = "{\\an3}"
            java.lang.String r13 = "{\\an2}"
            java.lang.String r14 = "{\\an1}"
            r16 = 0
            r4 = 4
            r5 = 3
            r15 = 2
            r3 = 1
            switch(r1) {
                case -685620710: goto L_0x0078;
                case -685620679: goto L_0x0070;
                case -685620648: goto L_0x0068;
                case -685620617: goto L_0x0060;
                case -685620586: goto L_0x0058;
                case -685620555: goto L_0x0050;
                case -685620524: goto L_0x0048;
                case -685620493: goto L_0x003f;
                case -685620462: goto L_0x0037;
                default: goto L_0x0036;
            }
        L_0x0036:
            goto L_0x0080
        L_0x0037:
            boolean r1 = r0.equals(r6)
            if (r1 == 0) goto L_0x0080
            r1 = 5
            goto L_0x0081
        L_0x003f:
            boolean r1 = r0.equals(r7)
            if (r1 == 0) goto L_0x0080
            r1 = 8
            goto L_0x0081
        L_0x0048:
            boolean r1 = r0.equals(r8)
            if (r1 == 0) goto L_0x0080
            r1 = 2
            goto L_0x0081
        L_0x0050:
            boolean r1 = r0.equals(r9)
            if (r1 == 0) goto L_0x0080
            r1 = 4
            goto L_0x0081
        L_0x0058:
            boolean r1 = r0.equals(r10)
            if (r1 == 0) goto L_0x0080
            r1 = 7
            goto L_0x0081
        L_0x0060:
            boolean r1 = r0.equals(r11)
            if (r1 == 0) goto L_0x0080
            r1 = 1
            goto L_0x0081
        L_0x0068:
            boolean r1 = r0.equals(r12)
            if (r1 == 0) goto L_0x0080
            r1 = 3
            goto L_0x0081
        L_0x0070:
            boolean r1 = r0.equals(r13)
            if (r1 == 0) goto L_0x0080
            r1 = 6
            goto L_0x0081
        L_0x0078:
            boolean r1 = r0.equals(r14)
            if (r1 == 0) goto L_0x0080
            r1 = 0
            goto L_0x0081
        L_0x0080:
            r1 = -1
        L_0x0081:
            if (r1 == 0) goto L_0x0094
            if (r1 == r3) goto L_0x0094
            if (r1 == r15) goto L_0x0094
            if (r1 == r5) goto L_0x0091
            if (r1 == r4) goto L_0x0091
            r4 = 5
            if (r1 == r4) goto L_0x0091
            r17 = 1
            goto L_0x0096
        L_0x0091:
            r17 = 2
            goto L_0x0096
        L_0x0094:
            r17 = 0
        L_0x0096:
            int r1 = r20.hashCode()
            switch(r1) {
                case -685620710: goto L_0x00df;
                case -685620679: goto L_0x00d7;
                case -685620648: goto L_0x00cf;
                case -685620617: goto L_0x00c7;
                case -685620586: goto L_0x00bf;
                case -685620555: goto L_0x00b6;
                case -685620524: goto L_0x00ae;
                case -685620493: goto L_0x00a6;
                case -685620462: goto L_0x009e;
                default: goto L_0x009d;
            }
        L_0x009d:
            goto L_0x00e7
        L_0x009e:
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x00e7
            r0 = 5
            goto L_0x00e8
        L_0x00a6:
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L_0x00e7
            r0 = 4
            goto L_0x00e8
        L_0x00ae:
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x00e7
            r0 = 3
            goto L_0x00e8
        L_0x00b6:
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x00e7
            r0 = 8
            goto L_0x00e8
        L_0x00bf:
            boolean r0 = r0.equals(r10)
            if (r0 == 0) goto L_0x00e7
            r0 = 7
            goto L_0x00e8
        L_0x00c7:
            boolean r0 = r0.equals(r11)
            if (r0 == 0) goto L_0x00e7
            r0 = 6
            goto L_0x00e8
        L_0x00cf:
            boolean r0 = r0.equals(r12)
            if (r0 == 0) goto L_0x00e7
            r0 = 2
            goto L_0x00e8
        L_0x00d7:
            boolean r0 = r0.equals(r13)
            if (r0 == 0) goto L_0x00e7
            r0 = 1
            goto L_0x00e8
        L_0x00df:
            boolean r0 = r0.equals(r14)
            if (r0 == 0) goto L_0x00e7
            r0 = 0
            goto L_0x00e8
        L_0x00e7:
            r0 = -1
        L_0x00e8:
            if (r0 == 0) goto L_0x00fa
            if (r0 == r3) goto L_0x00fa
            if (r0 == r15) goto L_0x00fa
            if (r0 == r5) goto L_0x00f8
            r1 = 4
            if (r0 == r1) goto L_0x00f8
            r1 = 5
            if (r0 == r1) goto L_0x00f8
            r6 = 1
            goto L_0x00fb
        L_0x00f8:
            r6 = 0
            goto L_0x00fb
        L_0x00fa:
            r6 = 2
        L_0x00fb:
            com.google.android.exoplayer2.text.Cue r0 = new com.google.android.exoplayer2.text.Cue
            r3 = 0
            float r4 = getFractionalPositionForAnchorType(r6)
            r5 = 0
            float r7 = getFractionalPositionForAnchorType(r17)
            r9 = 1
            r1 = r0
            r2 = r19
            r8 = r17
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.exoplayer2.text.subrip.SubripDecoder.buildCue(android.text.Spanned, java.lang.String):com.google.android.exoplayer2.text.Cue");
    }

    private static long parseTimecode(Matcher matcher, int i) {
        return ((Long.parseLong(matcher.group(i + 1)) * 60 * 60 * 1000) + (Long.parseLong(matcher.group(i + 2)) * 60 * 1000) + (Long.parseLong(matcher.group(i + 3)) * 1000) + Long.parseLong(matcher.group(i + 4))) * 1000;
    }
}
