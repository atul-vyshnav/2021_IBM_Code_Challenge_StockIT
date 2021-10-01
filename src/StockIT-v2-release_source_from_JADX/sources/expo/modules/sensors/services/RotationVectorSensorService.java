package expo.modules.sensors.services;

import android.content.Context;
import java.util.Collections;
import java.util.List;
import org.unimodules.core.interfaces.InternalModule;

public class RotationVectorSensorService extends SubscribableSensorService implements InternalModule, org.unimodules.interfaces.sensors.services.RotationVectorSensorService {
    /* access modifiers changed from: package-private */
    public int getSensorType() {
        return 11;
    }

    public RotationVectorSensorService(Context context) {
        super(context);
    }

    public List<Class> getExportedInterfaces() {
        return Collections.singletonList(org.unimodules.interfaces.sensors.services.RotationVectorSensorService.class);
    }
}
