package expo.modules.structuredheaders;

import android.util.Base64;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class Parser {
    private static char EOD = '￿';
    private final CharBuffer input;
    private final List<Integer> startPositions;

    public Parser(String str) {
        this((Iterable<String>) Collections.singletonList(Objects.requireNonNull(str, "input must not be null")));
    }

    public Parser(String... strArr) {
        this((Iterable<String>) Arrays.asList(strArr));
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v0, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v1, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: java.lang.StringBuilder} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v8, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v9, resolved type: java.lang.String} */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x003d, code lost:
        r0 = new java.util.ArrayList<>();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0042, code lost:
        r0.add(java.lang.Integer.valueOf(r2.length()));
        r2.append(",");
        r2.append(checkASCII(r3));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0015, code lost:
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:6:0x002d, code lost:
        if (r2 != null) goto L_0x0037;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x002f, code lost:
        r2 = new java.lang.StringBuilder();
        r2.append(r1);
        r2 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x003b, code lost:
        if (r0.size() != 0) goto L_0x0042;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Parser(java.lang.Iterable<java.lang.String> r6) {
        /*
            r5 = this;
            r5.<init>()
            java.util.List r0 = java.util.Collections.emptyList()
            java.lang.String r1 = "fieldLines must not be null"
            java.lang.Object r6 = java.util.Objects.requireNonNull(r6, r1)
            java.lang.Iterable r6 = (java.lang.Iterable) r6
            java.util.Iterator r6 = r6.iterator()
            r1 = 0
            r2 = r1
        L_0x0015:
            boolean r3 = r6.hasNext()
            if (r3 == 0) goto L_0x005a
            java.lang.Object r3 = r6.next()
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "field line must not be null"
            java.util.Objects.requireNonNull(r4, r3)
            if (r1 != 0) goto L_0x002d
            java.lang.String r1 = checkASCII(r3)
            goto L_0x0015
        L_0x002d:
            if (r2 != 0) goto L_0x0037
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r1)
        L_0x0037:
            int r4 = r0.size()
            if (r4 != 0) goto L_0x0042
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
        L_0x0042:
            int r4 = r2.length()
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r0.add(r4)
            java.lang.String r4 = ","
            r2.append(r4)
            java.lang.String r3 = checkASCII(r3)
            r2.append(r3)
            goto L_0x0015
        L_0x005a:
            if (r1 != 0) goto L_0x006a
            if (r2 == 0) goto L_0x005f
            goto L_0x006a
        L_0x005f:
            expo.modules.structuredheaders.ParseException r6 = new expo.modules.structuredheaders.ParseException
            r0 = 0
            java.lang.String r1 = "Empty input"
            java.lang.String r2 = ""
            r6.<init>((java.lang.String) r1, (java.lang.String) r2, (int) r0)
            throw r6
        L_0x006a:
            if (r2 == 0) goto L_0x006d
            r1 = r2
        L_0x006d:
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            java.nio.CharBuffer r6 = java.nio.CharBuffer.wrap(r1)
            r5.input = r6
            r5.startPositions = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: expo.modules.structuredheaders.Parser.<init>(java.lang.Iterable):void");
    }

    private static String checkASCII(String str) {
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt < 0 || charAt > 127) {
                throw new ParseException(String.format("Invalid character in field line at position %d: '%c' (0x%04x) (input: %s)", new Object[]{Integer.valueOf(i), Character.valueOf(charAt), Integer.valueOf(charAt), str}), str, i);
            }
        }
        return str;
    }

    private NumberItem<? extends Object> internalParseBareIntegerOrDecimal() {
        int i;
        int i2;
        StringBuilder sb = new StringBuilder(20);
        if (checkNextChar('-')) {
            i = -1;
            advance();
        } else {
            i = 1;
        }
        if (checkNextChar("0123456789")) {
            boolean z = false;
            boolean z2 = false;
            while (hasRemaining() && !z) {
                char peek = peek();
                if (C3057Utils.isDigit(peek)) {
                    sb.append(peek);
                    advance();
                } else if (z2 || peek != '.') {
                    z = true;
                } else if (sb.length() <= 12) {
                    sb.append(peek);
                    advance();
                    z2 = true;
                } else {
                    throw complaint("Illegal position for decimal point in Decimal after '" + sb + "'");
                }
                int length = sb.length();
                if (z2) {
                    i2 = 16;
                    continue;
                } else {
                    i2 = 15;
                    continue;
                }
                if (length > i2) {
                    backout();
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(z2 ? "Decimal" : "Integer");
                    sb2.append(" too long: ");
                    sb2.append(sb.length());
                    sb2.append(" characters");
                    throw complaint(sb2.toString());
                }
            }
            if (!z2) {
                return IntegerItem.valueOf(((long) i) * Long.parseLong(sb.toString()));
            }
            int indexOf = sb.indexOf(".");
            int length2 = (sb.length() - indexOf) - 1;
            if (length2 >= 1) {
                if (length2 == 1) {
                    sb.append("00");
                } else if (length2 == 2) {
                    sb.append("0");
                } else if (length2 > 3) {
                    backout();
                    throw complaint("Maximum number of fractional digits is 3, found: " + length2 + ", in: " + sb);
                }
                sb.deleteCharAt(indexOf);
                return DecimalItem.valueOf(((long) i) * Long.parseLong(sb.toString()));
            }
            backout();
            throw complaint("Decimal must not end in '.'");
        }
        throw complaint("Illegal start for Integer or Decimal: '" + this.input + "'");
    }

    private NumberItem<? extends Object> internalParseIntegerOrDecimal() {
        return internalParseBareIntegerOrDecimal().withParams(internalParseParameters());
    }

    private StringItem internalParseBareString() {
        if (getOrEOD() == '\"') {
            StringBuilder sb = new StringBuilder(length());
            while (hasRemaining()) {
                if (!this.startPositions.contains(Integer.valueOf(position()))) {
                    char c = get();
                    if (c == '\\') {
                        char orEOD = getOrEOD();
                        if (orEOD == EOD) {
                            throw complaint("Incomplete escape sequence at position " + position());
                        } else if (orEOD == '\"' || orEOD == '\\') {
                            sb.append(orEOD);
                        } else {
                            backout();
                            throw complaint("Invalid escape sequence character '" + orEOD + "' at position " + position());
                        }
                    } else if (c == '\"') {
                        return StringItem.valueOf(sb.toString());
                    } else {
                        if (c < ' ' || c >= 127) {
                            throw complaint("Invalid character in String at position " + position());
                        }
                        sb.append(c);
                    }
                } else {
                    throw complaint("String crosses field line boundary at position " + position());
                }
            }
            throw complaint("Closing DQUOTE missing");
        }
        throw complaint("String must start with double quote: '" + this.input + "'");
    }

    private StringItem internalParseString() {
        return internalParseBareString().withParams(internalParseParameters());
    }

    private TokenItem internalParseBareToken() {
        char orEOD = getOrEOD();
        if (orEOD == '*' || C3057Utils.isAlpha(orEOD)) {
            StringBuilder sb = new StringBuilder(length());
            sb.append(orEOD);
            boolean z = false;
            while (hasRemaining() && !z) {
                char peek = peek();
                if (peek <= ' ' || peek >= 127 || "\"(),;<=>?@[\\]{}".indexOf(peek) >= 0) {
                    z = true;
                } else {
                    advance();
                    sb.append(peek);
                }
            }
            return TokenItem.valueOf(sb.toString());
        }
        throw complaint("Token must start with ALPHA or *: '" + this.input + "'");
    }

    private TokenItem internalParseToken() {
        return internalParseBareToken().withParams(internalParseParameters());
    }

    private static boolean isBase64Char(char c) {
        return C3057Utils.isAlpha(c) || C3057Utils.isDigit(c) || c == '+' || c == '/' || c == '=';
    }

    private ByteSequenceItem internalParseBareByteSequence() {
        if (getOrEOD() == ':') {
            StringBuilder sb = new StringBuilder(length());
            boolean z = false;
            while (hasRemaining() && !z) {
                char c = get();
                if (c == ':') {
                    z = true;
                } else if (isBase64Char(c)) {
                    sb.append(c);
                } else {
                    throw complaint("Invalid Byte Sequence Character '" + c + "' at position " + position());
                }
            }
            if (z) {
                try {
                    return ByteSequenceItem.valueOf(Base64.decode(sb.toString(), 0));
                } catch (IllegalArgumentException e) {
                    throw complaint(e.getMessage(), e);
                }
            } else {
                throw complaint("Byte Sequence must end with COLON: '" + sb + "'");
            }
        } else {
            throw complaint("Byte Sequence must start with colon: " + this.input);
        }
    }

    private ByteSequenceItem internalParseByteSequence() {
        return internalParseBareByteSequence().withParams(internalParseParameters());
    }

    private BooleanItem internalParseBareBoolean() {
        char orEOD = getOrEOD();
        if (orEOD != EOD) {
            boolean z = false;
            if (orEOD == '?') {
                char orEOD2 = getOrEOD();
                if (orEOD2 == EOD) {
                    throw complaint("Missing data in Boolean");
                } else if (orEOD2 == '0' || orEOD2 == '1') {
                    if (orEOD2 == '1') {
                        z = true;
                    }
                    return BooleanItem.valueOf(z);
                } else {
                    backout();
                    throw complaint(String.format("Expected '0' or '1' in Boolean, found '%c'", new Object[]{Character.valueOf(orEOD2)}));
                }
            } else {
                backout();
                throw complaint(String.format("Boolean must start with question mark, got '%c'", new Object[]{Character.valueOf(orEOD)}));
            }
        } else {
            throw complaint("Missing data in Boolean");
        }
    }

    private BooleanItem internalParseBoolean() {
        return internalParseBareBoolean().withParams(internalParseParameters());
    }

    private String internalParseKey() {
        char orEOD = getOrEOD();
        if (orEOD == EOD) {
            throw complaint("Missing data in Key");
        } else if (orEOD == '*' || C3057Utils.isLcAlpha(orEOD)) {
            StringBuilder sb = new StringBuilder();
            sb.append(orEOD);
            boolean z = false;
            while (hasRemaining() && !z) {
                char peek = peek();
                if (C3057Utils.isLcAlpha(peek) || C3057Utils.isDigit(peek) || peek == '_' || peek == '-' || peek == '.' || peek == '*') {
                    sb.append(peek);
                    advance();
                } else {
                    z = true;
                }
            }
            return sb.toString();
        } else {
            backout();
            throw complaint("Key must start with LCALPHA or '*': " + format(orEOD));
        }
    }

    private Parameters internalParseParameters() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        boolean z = false;
        while (hasRemaining() && !z) {
            if (peek() != ';') {
                z = true;
            } else {
                advance();
                removeLeadingSP();
                String internalParseKey = internalParseKey();
                Object valueOf = BooleanItem.valueOf(true);
                if (peek() == '=') {
                    advance();
                    valueOf = internalParseBareItem();
                }
                linkedHashMap.put(internalParseKey, valueOf);
            }
        }
        return Parameters.valueOf(linkedHashMap);
    }

    private Item<? extends Object> internalParseBareItem() {
        if (hasRemaining()) {
            char peek = peek();
            if (C3057Utils.isDigit(peek) || peek == '-') {
                return internalParseBareIntegerOrDecimal();
            }
            if (peek == '\"') {
                return internalParseBareString();
            }
            if (peek == '?') {
                return internalParseBareBoolean();
            }
            if (peek == '*' || C3057Utils.isAlpha(peek)) {
                return internalParseBareToken();
            }
            if (peek == ':') {
                return internalParseBareByteSequence();
            }
            throw complaint("Unexpected start character in Bare Item: " + format(peek));
        }
        throw complaint("Empty string found when parsing Bare Item");
    }

    private Item<? extends Object> internalParseItem() {
        return internalParseBareItem().withParams(internalParseParameters());
    }

    private ListElement<? extends Object> internalParseItemOrInnerList() {
        return peek() == '(' ? internalParseInnerList() : internalParseItem();
    }

    private List<ListElement<? extends Object>> internalParseOuterList() {
        ArrayList arrayList = new ArrayList();
        while (hasRemaining()) {
            arrayList.add(internalParseItemOrInnerList());
            removeLeadingOWS();
            if (!hasRemaining()) {
                return arrayList;
            }
            char c = get();
            if (c == ',') {
                removeLeadingOWS();
                if (!hasRemaining()) {
                    throw complaint("Found trailing COMMA in List");
                }
            } else {
                backout();
                throw complaint("Expected COMMA in List, got: " + format(c));
            }
        }
        return arrayList;
    }

    private List<Item<? extends Object>> internalParseBareInnerList() {
        if (getOrEOD() == '(') {
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            while (hasRemaining() && !z) {
                removeLeadingSP();
                if (peek() == ')') {
                    advance();
                    z = true;
                } else {
                    arrayList.add(internalParseItem());
                    char peek = peek();
                    if (peek == EOD) {
                        throw complaint("Missing data in Inner List");
                    } else if (!(peek == ' ' || peek == ')')) {
                        throw complaint("Expected SP or ')' in Inner List, got: " + format(peek));
                    }
                }
            }
            if (z) {
                return arrayList;
            }
            throw complaint("Inner List must end with ')': " + this.input);
        }
        throw complaint("Inner List must start with '(': " + this.input);
    }

    private InnerList internalParseInnerList() {
        List<Item<? extends Object>> internalParseBareInnerList = internalParseBareInnerList();
        return InnerList.valueOf(internalParseBareInnerList).withParams(internalParseParameters());
    }

    private Dictionary internalParseDictionary() {
        Object obj;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        boolean z = false;
        while (hasRemaining() && !z) {
            String internalParseKey = internalParseKey();
            if (peek() == '=') {
                advance();
                obj = internalParseItemOrInnerList();
            } else {
                obj = BooleanItem.valueOf(true).withParams(internalParseParameters());
            }
            linkedHashMap.put(internalParseKey, obj);
            removeLeadingOWS();
            if (hasRemaining()) {
                char c = get();
                if (c == ',') {
                    removeLeadingOWS();
                    if (!hasRemaining()) {
                        throw complaint("Found trailing COMMA in Dictionary");
                    }
                } else {
                    backout();
                    throw complaint("Expected COMMA in Dictionary, found: " + format(c));
                }
            } else {
                z = true;
            }
        }
        return Dictionary.valueOf(linkedHashMap);
    }

    protected static IntegerItem parseInteger(String str) {
        Parser parser = new Parser(str);
        NumberItem<? extends Object> internalParseIntegerOrDecimal = parser.internalParseIntegerOrDecimal();
        if (internalParseIntegerOrDecimal instanceof IntegerItem) {
            parser.assertEmpty("Extra characters in string parsed as Integer");
            return (IntegerItem) internalParseIntegerOrDecimal;
        }
        throw parser.complaint("String parsed as Integer '" + str + "' is a Decimal");
    }

    protected static DecimalItem parseDecimal(String str) {
        Parser parser = new Parser(str);
        NumberItem<? extends Object> internalParseIntegerOrDecimal = parser.internalParseIntegerOrDecimal();
        if (internalParseIntegerOrDecimal instanceof DecimalItem) {
            parser.assertEmpty("Extra characters in string parsed as Decimal");
            return (DecimalItem) internalParseIntegerOrDecimal;
        }
        throw parser.complaint("String parsed as Decimal '" + str + "' is an Integer");
    }

    public OuterList parseList() {
        removeLeadingSP();
        List<ListElement<? extends Object>> internalParseOuterList = internalParseOuterList();
        removeLeadingSP();
        assertEmpty("Extra characters in string parsed as List");
        return OuterList.valueOf(internalParseOuterList);
    }

    public Dictionary parseDictionary() {
        removeLeadingSP();
        Dictionary internalParseDictionary = internalParseDictionary();
        removeLeadingSP();
        assertEmpty("Extra characters in string parsed as Dictionary");
        return internalParseDictionary;
    }

    public Item<? extends Object> parseItem() {
        removeLeadingSP();
        Item<? extends Object> internalParseItem = internalParseItem();
        removeLeadingSP();
        assertEmpty("Extra characters in string parsed as Item");
        return internalParseItem;
    }

    public static OuterList parseList(String str) {
        Parser parser = new Parser(str);
        List<ListElement<? extends Object>> internalParseOuterList = parser.internalParseOuterList();
        parser.assertEmpty("Extra characters in string parsed as List");
        return OuterList.valueOf(internalParseOuterList);
    }

    public static Parametrizable<? extends Object> parseItemOrInnerList(String str) {
        Parser parser = new Parser(str);
        ListElement<? extends Object> internalParseItemOrInnerList = parser.internalParseItemOrInnerList();
        parser.assertEmpty("Extra characters in string parsed as Item or Inner List");
        return internalParseItemOrInnerList;
    }

    public static InnerList parseInnerList(String str) {
        Parser parser = new Parser(str);
        InnerList internalParseInnerList = parser.internalParseInnerList();
        parser.assertEmpty("Extra characters in string parsed as Inner List");
        return internalParseInnerList;
    }

    public static Dictionary parseDictionary(String str) {
        Parser parser = new Parser(str);
        Dictionary internalParseDictionary = parser.internalParseDictionary();
        parser.assertEmpty("Extra characters in string parsed as Dictionary");
        return internalParseDictionary;
    }

    public static Item<? extends Object> parseItem(String str) {
        Parser parser = new Parser(str);
        Item<? extends Object> parseItem = parser.parseItem();
        parser.assertEmpty("Extra characters in string parsed as Item");
        return parseItem;
    }

    public static Item<? extends Object> parseBareItem(String str) {
        Parser parser = new Parser(str);
        Item<? extends Object> internalParseBareItem = parser.internalParseBareItem();
        parser.assertEmpty("Extra characters in string parsed as Bare Item");
        return internalParseBareItem;
    }

    public static Parameters parseParameters(String str) {
        Parser parser = new Parser(str);
        Parameters internalParseParameters = parser.internalParseParameters();
        parser.assertEmpty("Extra characters in string parsed as Parameters");
        return internalParseParameters;
    }

    public static String parseKey(String str) {
        Parser parser = new Parser(str);
        String internalParseKey = parser.internalParseKey();
        parser.assertEmpty("Extra characters in string parsed as Key");
        return internalParseKey;
    }

    public static NumberItem<? extends Object> parseIntegerOrDecimal(String str) {
        Parser parser = new Parser(str);
        NumberItem<? extends Object> internalParseIntegerOrDecimal = parser.internalParseIntegerOrDecimal();
        parser.assertEmpty("Extra characters in string parsed as Integer or Decimal");
        return internalParseIntegerOrDecimal;
    }

    public static StringItem parseString(String str) {
        Parser parser = new Parser(str);
        StringItem internalParseString = parser.internalParseString();
        parser.assertEmpty("Extra characters in string parsed as String");
        return internalParseString;
    }

    public static TokenItem parseToken(String str) {
        Parser parser = new Parser(str);
        TokenItem internalParseToken = parser.internalParseToken();
        parser.assertEmpty("Extra characters in string parsed as Token");
        return internalParseToken;
    }

    public static ByteSequenceItem parseByteSequence(String str) {
        Parser parser = new Parser(str);
        ByteSequenceItem internalParseByteSequence = parser.internalParseByteSequence();
        parser.assertEmpty("Extra characters in string parsed as Byte Sequence");
        return internalParseByteSequence;
    }

    public static BooleanItem parseBoolean(String str) {
        Parser parser = new Parser(str);
        BooleanItem internalParseBoolean = parser.internalParseBoolean();
        parser.assertEmpty("Extra characters at position %d in string parsed as Boolean: '%s'");
        return internalParseBoolean;
    }

    private void assertEmpty(String str) {
        if (hasRemaining()) {
            throw complaint(String.format(str, new Object[]{Integer.valueOf(position()), this.input}));
        }
    }

    private void advance() {
        CharBuffer charBuffer = this.input;
        charBuffer.position(charBuffer.position() + 1);
    }

    private void backout() {
        CharBuffer charBuffer = this.input;
        charBuffer.position(charBuffer.position() - 1);
    }

    private boolean checkNextChar(char c) {
        return hasRemaining() && this.input.charAt(0) == c;
    }

    private boolean checkNextChar(String str) {
        return hasRemaining() && str.indexOf(this.input.charAt(0)) >= 0;
    }

    private char get() {
        return this.input.get();
    }

    private char getOrEOD() {
        return hasRemaining() ? get() : EOD;
    }

    private boolean hasRemaining() {
        return this.input.hasRemaining();
    }

    private int length() {
        return this.input.length();
    }

    private char peek() {
        return hasRemaining() ? this.input.charAt(0) : EOD;
    }

    private int position() {
        return this.input.position();
    }

    private void removeLeadingSP() {
        while (checkNextChar(' ')) {
            advance();
        }
    }

    private void removeLeadingOWS() {
        while (checkNextChar(" \t")) {
            advance();
        }
    }

    private ParseException complaint(String str) {
        return new ParseException(str, this.input);
    }

    private ParseException complaint(String str, Throwable th) {
        return new ParseException(str, this.input, th);
    }

    private static String format(char c) {
        String str;
        if (c == 9) {
            str = "HTAB";
        } else {
            str = "'" + c + "'";
        }
        return String.format("%s (\\u%04x)", new Object[]{str, Integer.valueOf(c)});
    }
}
