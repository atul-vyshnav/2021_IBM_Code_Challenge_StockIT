package expo.modules.splashscreen.exceptions;

import kotlin.Metadata;
import org.unimodules.core.errors.CodedException;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016¨\u0006\u0005"}, mo40253d2 = {"Lexpo/modules/splashscreen/exceptions/NoContentViewException;", "Lorg/unimodules/core/errors/CodedException;", "()V", "getCode", "", "expo-splash-screen_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: NoContentViewException.kt */
public final class NoContentViewException extends CodedException {
    public String getCode() {
        return "ERR_NO_CONTENT_VIEW_FOUND";
    }

    public NoContentViewException() {
        super("ContentView is not yet available. Call 'SplashScreen.show(...)' once 'setContentView()' is called.");
    }
}
