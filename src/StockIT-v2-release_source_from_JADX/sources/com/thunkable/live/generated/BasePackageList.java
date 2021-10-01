package com.thunkable.live.generated;

import expo.modules.ads.admob.AdMobPackage;
import expo.modules.analytics.amplitude.AmplitudePackage;
import expo.modules.application.ApplicationPackage;
import expo.modules.barcodescanner.BarCodeScannerPackage;
import expo.modules.camera.CameraPackage;
import expo.modules.constants.ConstantsPackage;
import expo.modules.errorrecovery.ErrorRecoveryPackage;
import expo.modules.filesystem.FileSystemPackage;
import expo.modules.font.FontLoaderPackage;
import expo.modules.imageloader.ImageLoaderPackage;
import expo.modules.imagepicker.ImagePickerPackage;
import expo.modules.inapppurchases.InAppPurchasesPackage;
import expo.modules.keepawake.KeepAwakePackage;
import expo.modules.location.LocationPackage;
import expo.modules.p019av.AVPackage;
import expo.modules.permissions.PermissionsPackage;
import expo.modules.screenorientation.ScreenOrientationPackage;
import expo.modules.securestore.SecureStorePackage;
import expo.modules.sensors.SensorsPackage;
import expo.modules.sms.SMSPackage;
import expo.modules.splashscreen.SplashScreenPackage;
import expo.modules.updates.UpdatesPackage;
import java.util.Arrays;
import java.util.List;
import org.unimodules.core.interfaces.Package;

public class BasePackageList {
    public List<Package> getPackageList() {
        return Arrays.asList(new Package[]{new AdMobPackage(), new AmplitudePackage(), new ApplicationPackage(), new AVPackage(), new BarCodeScannerPackage(), new CameraPackage(), new ConstantsPackage(), new ErrorRecoveryPackage(), new FileSystemPackage(), new FontLoaderPackage(), new ImageLoaderPackage(), new ImagePickerPackage(), new InAppPurchasesPackage(), new KeepAwakePackage(), new LocationPackage(), new PermissionsPackage(), new ScreenOrientationPackage(), new SecureStorePackage(), new SensorsPackage(), new SMSPackage(), new SplashScreenPackage(), new UpdatesPackage()});
    }
}
