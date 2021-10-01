package expo.modules.font;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import java.io.File;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.interfaces.constants.ConstantsInterface;
import org.unimodules.interfaces.font.FontManager;

public class FontLoaderModule extends ExportedModule {
    private static final String ASSET_SCHEME = "asset://";
    private static final String EXPORTED_NAME = "ExpoFontLoader";
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return EXPORTED_NAME;
    }

    public FontLoaderModule(Context context) {
        super(context);
    }

    @ExpoMethod
    public void loadAsync(String str, String str2, Promise promise) {
        Typeface typeface;
        String str3 = "";
        try {
            if (isScoped()) {
                str3 = "ExpoFont-";
            }
            if (str2.startsWith(ASSET_SCHEME)) {
                typeface = Typeface.createFromAsset(getContext().getAssets(), str2.substring(9));
            } else {
                typeface = Typeface.createFromFile(new File(Uri.parse(str2).getPath()));
            }
            FontManager fontManager = (FontManager) this.mModuleRegistry.getModule(FontManager.class);
            if (fontManager == null) {
                promise.reject("E_NO_FONT_MANAGER", "There is no FontManager in module registry. Are you sure all the dependencies of expo-font are installed and linked?");
                return;
            }
            fontManager.setTypeface(str3 + str, 0, typeface);
            promise.resolve((Object) null);
        } catch (Exception e) {
            promise.reject("E_UNEXPECTED", "Font.loadAsync unexpected exception: " + e.getMessage(), e);
        }
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    private boolean isScoped() {
        ConstantsInterface constantsInterface = (ConstantsInterface) this.mModuleRegistry.getModule(ConstantsInterface.class);
        return constantsInterface != null && "expo".equals(constantsInterface.getAppOwnership());
    }
}
