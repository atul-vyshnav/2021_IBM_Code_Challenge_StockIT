package expo.modules.sensors.services;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.interfaces.InternalModule;

public class MagnetometerService extends SubscribableSensorService implements InternalModule, org.unimodules.interfaces.sensors.services.MagnetometerService {
    /* access modifiers changed from: package-private */
    public int getSensorType() {
        return 2;
    }

    public MagnetometerService(Context context) {
        super(context);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(org.unimodules.interfaces.sensors.services.MagnetometerService.class);
    }
}
