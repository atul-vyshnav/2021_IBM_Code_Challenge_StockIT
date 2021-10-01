package expo.modules.font;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;

public class FontLoaderPackage extends BasePackage {
    public List<ExportedModule> createExportedModules(Context context) {
        return Collections.singletonList(new FontLoaderModule(context));
    }
}
