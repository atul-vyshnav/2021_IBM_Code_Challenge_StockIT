package expo.modules.sensors.services;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.interfaces.InternalModule;

public class AccelerometerService extends SubscribableSensorService implements InternalModule, org.unimodules.interfaces.sensors.services.AccelerometerService {
    /* access modifiers changed from: package-private */
    public int getSensorType() {
        return 1;
    }

    public AccelerometerService(Context context) {
        super(context);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(org.unimodules.interfaces.sensors.services.AccelerometerService.class);
    }
}
