package expo.modules.sms;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;

public class SMSPackage extends BasePackage {
    public List<ExportedModule> createExportedModules(Context context) {
        return Collections.singletonList(new SMSModule(context));
    }
}
