package expo.modules.sensors;

import android.content.Context;
import expo.modules.sensors.modules.AccelerometerModule;
import expo.modules.sensors.modules.BarometerModule;
import expo.modules.sensors.modules.DeviceMotionModule;
import expo.modules.sensors.modules.GyroscopeModule;
import expo.modules.sensors.modules.MagnetometerModule;
import expo.modules.sensors.modules.MagnetometerUncalibratedModule;
import expo.modules.sensors.modules.PedometerModule;
import expo.modules.sensors.services.AccelerometerService;
import expo.modules.sensors.services.BarometerService;
import expo.modules.sensors.services.GravitySensorService;
import expo.modules.sensors.services.GyroscopeService;
import expo.modules.sensors.services.LinearAccelerationSensorService;
import expo.modules.sensors.services.MagnetometerService;
import expo.modules.sensors.services.MagnetometerUncalibratedService;
import expo.modules.sensors.services.PedometerService;
import expo.modules.sensors.services.RotationVectorSensorService;
import java.util.Arrays;
import java.util.List;
import org.unimodules.core.BasePackage;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.interfaces.InternalModule;

public class SensorsPackage extends BasePackage {
    public List<InternalModule> createInternalModules(Context context) {
        return Arrays.asList(new InternalModule[]{new AccelerometerService(context), new BarometerService(context), new GravitySensorService(context), new GyroscopeService(context), new LinearAccelerationSensorService(context), new MagnetometerService(context), new MagnetometerUncalibratedService(context), new RotationVectorSensorService(context), new PedometerService(context)});
    }

    public List<ExportedModule> createExportedModules(Context context) {
        return Arrays.asList(new ExportedModule[]{new AccelerometerModule(context), new BarometerModule(context), new GyroscopeModule(context), new DeviceMotionModule(context), new MagnetometerModule(context), new MagnetometerUncalibratedModule(context), new PedometerModule(context)});
    }
}
