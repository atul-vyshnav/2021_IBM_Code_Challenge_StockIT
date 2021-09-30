package p009co.apptailor.googlesignin;

import android.view.View;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.android.gms.common.SignInButton;

/* renamed from: co.apptailor.googlesignin.RNGoogleSigninButtonViewManager */
public class RNGoogleSigninButtonViewManager extends SimpleViewManager<SignInButton> {
    public String getName() {
        return "RNGoogleSigninButton";
    }

    /* access modifiers changed from: protected */
    public SignInButton createViewInstance(final ThemedReactContext themedReactContext) {
        SignInButton signInButton = new SignInButton(themedReactContext);
        signInButton.setSize(0);
        signInButton.setColorScheme(2);
        signInButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((DeviceEventManagerModule.RCTDeviceEventEmitter) themedReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)).emit("RNGoogleSigninButtonClicked", (Object) null);
            }
        });
        return signInButton;
    }

    @ReactProp(name = "size")
    public void setSize(SignInButton signInButton, int i) {
        signInButton.setSize(i);
    }

    @ReactProp(name = "color")
    public void setColor(SignInButton signInButton, int i) {
        signInButton.setColorScheme(i);
    }

    @ReactProp(name = "disabled")
    public void setDisabled(SignInButton signInButton, boolean z) {
        signInButton.setEnabled(!z);
    }
}
