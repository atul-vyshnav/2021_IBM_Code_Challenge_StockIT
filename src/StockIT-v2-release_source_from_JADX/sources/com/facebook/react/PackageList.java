package com.facebook.react;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import com.RNFetchBlob.RNFetchBlobPackage;
import com.airbnb.android.react.lottie.LottiePackage;
import com.airbnb.android.react.maps.MapsPackage;
import com.bitgo.randombytes.RandomBytesPackage;
import com.clipsub.RNShake.RNShakeEventPackage;
import com.facebook.flipper.reactnative.FlipperPackage;
import com.facebook.react.shell.MainPackageConfig;
import com.facebook.react.shell.MainReactPackage;
import com.geektime.rnonesignalandroid.ReactNativeOneSignalPackage;
import com.horcrux.svg.SvgPackage;
import com.learnium.RNDeviceInfo.C2273RNDeviceInfo;
import com.lugg.ReactNativeConfig.ReactNativeConfigPackage;
import com.masteratul.exceptionhandler.ReactNativeExceptionHandlerPackage;
import com.oblador.performance.PerformancePackage;
import com.ocetnik.timer.BackgroundTimerPackage;
import com.polidea.reactnativeble.BlePackage;
import com.reactcommunity.rndatetimepicker.RNDateTimePickerPackage;
import com.reactnativecommunity.asyncstorage.AsyncStoragePackage;
import com.reactnativecommunity.netinfo.NetInfoPackage;
import com.reactnativecommunity.webview.RNCWebViewPackage;
import com.reactnativerate.RNRatePackage;
import com.rnfs.RNFSPackage;
import com.swmansion.gesturehandler.react.RNGestureHandlerPackage;
import com.swmansion.reanimated.ReanimatedPackage;
import com.swmansion.rnscreens.RNScreensPackage;
import com.wenkesj.voice.VoicePackage;
import com.zmxv.RNSound.RNSoundPackage;
import java.util.ArrayList;
import java.util.Arrays;
import net.no_mad.tts.TextToSpeechPackage;
import p008cl.json.RNSharePackage;
import p009co.apptailor.googlesignin.RNGoogleSigninPackage;
import p018de.innfactory.apiai.RNApiAiPackage;
import p021fr.bamlab.rnimageresizer.ImageResizerPackage;
import p022io.expo.appearance.RNCAppearancePackage;

public class PackageList {
    private Application application;
    private MainPackageConfig mConfig;
    private ReactNativeHost reactNativeHost;

    public PackageList(ReactNativeHost reactNativeHost2) {
        this(reactNativeHost2, (MainPackageConfig) null);
    }

    public PackageList(Application application2) {
        this(application2, (MainPackageConfig) null);
    }

    public PackageList(ReactNativeHost reactNativeHost2, MainPackageConfig mainPackageConfig) {
        this.reactNativeHost = reactNativeHost2;
        this.mConfig = mainPackageConfig;
    }

    public PackageList(Application application2, MainPackageConfig mainPackageConfig) {
        this.reactNativeHost = null;
        this.application = application2;
        this.mConfig = mainPackageConfig;
    }

    private ReactNativeHost getReactNativeHost() {
        return this.reactNativeHost;
    }

    private Resources getResources() {
        return getApplication().getResources();
    }

    private Application getApplication() {
        ReactNativeHost reactNativeHost2 = this.reactNativeHost;
        if (reactNativeHost2 == null) {
            return this.application;
        }
        return reactNativeHost2.getApplication();
    }

    private Context getApplicationContext() {
        return getApplication().getApplicationContext();
    }

    public ArrayList<ReactPackage> getPackages() {
        return new ArrayList<>(Arrays.asList(new ReactPackage[]{new MainReactPackage(this.mConfig), new AsyncStoragePackage(), new RNDateTimePickerPackage(), new RNGoogleSigninPackage(), new NetInfoPackage(), new VoicePackage(), new LottiePackage(), new RNCAppearancePackage(), new BackgroundTimerPackage(), new BlePackage(), new ReactNativeConfigPackage(), new C2273RNDeviceInfo(), new RNApiAiPackage(), new ReactNativeExceptionHandlerPackage(), new FlipperPackage(), new RNFSPackage(), new RNGestureHandlerPackage(), new ImageResizerPackage(), new MapsPackage(), new ReactNativeOneSignalPackage(), new PerformancePackage(), new RandomBytesPackage(), new RNRatePackage(), new ReanimatedPackage(), new RNScreensPackage(), new RNShakeEventPackage(), new RNSharePackage(), new RNSoundPackage(), new SvgPackage(), new TextToSpeechPackage(), new RNCWebViewPackage(), new RNFetchBlobPackage()}));
    }
}
