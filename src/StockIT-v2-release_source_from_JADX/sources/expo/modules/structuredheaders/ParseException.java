package expo.modules.structuredheaders;

import java.nio.CharBuffer;

public class ParseException extends IllegalArgumentException {
    private static final long serialVersionUID = -5222947525946866985L;
    private final String data;
    private final int position;

    public ParseException(String str, String str2, int i, Throwable th) {
        super(str, th);
        this.position = i;
        this.data = str2;
    }

    public ParseException(String str, String str2, int i) {
        this(str, str2, i, (Throwable) null);
    }

    public ParseException(String str, CharBuffer charBuffer, Throwable th) {
        this(str, asString(charBuffer), charBuffer.position(), th);
    }

    public ParseException(String str, CharBuffer charBuffer) {
        this(str, asString(charBuffer), charBuffer.position(), (Throwable) null);
    }

    public String getData() {
        return this.data;
    }

    public int getPosition() {
        return this.position;
    }

    public String getDiagnostics() {
        StringBuilder sb = new StringBuilder();
        sb.append(">>");
        sb.append(this.data);
        sb.append("<<");
        sb.append(10);
        sb.append("  ");
        for (int i = 0; i < this.position; i++) {
            sb.append('-');
        }
        sb.append("^ ");
        if (this.position < this.data.length()) {
            sb.append(String.format("(0x%02x) ", new Object[]{Integer.valueOf(this.data.charAt(this.position))}));
        }
        sb.append(super.getMessage());
        sb.append(10);
        return sb.toString();
    }

    private static String asString(CharBuffer charBuffer) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charBuffer.position() + charBuffer.remaining(); i++) {
            sb.append(charBuffer.get(i));
        }
        return sb.toString();
    }
}
