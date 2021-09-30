package expo.modules.screenorientation;

import kotlin.Metadata;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, mo40253d2 = {"Lexpo/modules/screenorientation/Orientation;", "", "value", "", "(Ljava/lang/String;II)V", "getValue", "()I", "UNKNOWN", "PORTRAIT_UP", "PORTRAIT_DOWN", "LANDSCAPE_LEFT", "LANDSCAPE_RIGHT", "expo-screen-orientation_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: Orientation.kt */
public enum Orientation {
    UNKNOWN(0),
    PORTRAIT_UP(1),
    PORTRAIT_DOWN(2),
    LANDSCAPE_LEFT(3),
    LANDSCAPE_RIGHT(4);
    
    private final int value;

    private Orientation(int i) {
        this.value = i;
    }

    public final int getValue() {
        return this.value;
    }
}
