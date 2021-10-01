package okhttp3;

import java.io.IOException;
import javax.annotation.Nullable;

public interface Authenticator {
    public static final Authenticator NONE = $$Lambda$Authenticator$xBBU2iHkJpDKH0vhaB2vteUyEoc.INSTANCE;

    /* renamed from: okhttp3.Authenticator$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static /* synthetic */ Request lambda$static$0(Route route, C3401Response response) throws IOException {
            return null;
        }
    }

    @Nullable
    Request authenticate(@Nullable Route route, C3401Response response) throws IOException;
}
