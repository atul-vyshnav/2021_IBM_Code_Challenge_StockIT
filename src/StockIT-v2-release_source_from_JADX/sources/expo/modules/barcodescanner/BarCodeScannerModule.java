package expo.modules.barcodescanner;

import android.content.Context;
import android.graphics.Bitmap;
import expo.modules.barcodescanner.utils.BarCodeScannerResultSerializer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.Promise;
import org.unimodules.core.interfaces.ExpoMethod;
import org.unimodules.interfaces.barcodescanner.BarCodeScanner;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerResult;
import org.unimodules.interfaces.barcodescanner.BarCodeScannerSettings;
import org.unimodules.interfaces.imageloader.ImageLoader;
import org.unimodules.interfaces.permissions.Permissions;

public class BarCodeScannerModule extends ExportedModule {
    private static final String ERROR_TAG = "E_BARCODE_SCANNER";
    private static final String TAG = "ExpoBarCodeScannerModule";
    /* access modifiers changed from: private */
    public static final Map<String, Object> VALID_BARCODE_TYPES = Collections.unmodifiableMap(new HashMap<String, Object>() {
        {
            put("aztec", 4096);
            put("ean13", 32);
            put("ean8", 64);
            put("qr", 256);
            put("pdf417", 2048);
            put("upc_e", 1024);
            put("datamatrix", 16);
            put("code39", 2);
            put("code93", 4);
            put("itf14", 128);
            put("codabar", 8);
            put("code128", 1);
            put("upc_a", 512);
        }
    });
    /* access modifiers changed from: private */
    public final BarCodeScannerProvider mBarCodeScannerProvider = new BarCodeScannerProvider();
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return TAG;
    }

    public void onCreate(ModuleRegistry moduleRegistry) {
        this.mModuleRegistry = moduleRegistry;
    }

    public BarCodeScannerModule(Context context) {
        super(context);
    }

    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("BarCodeType", getBarCodeConstants());
                put("Type", getTypeConstants());
            }

            private Map<String, Object> getBarCodeConstants() {
                return BarCodeScannerModule.VALID_BARCODE_TYPES;
            }

            private Map<String, Object> getTypeConstants() {
                return Collections.unmodifiableMap(new HashMap<String, Object>() {
                    {
                        put("front", 1);
                        put("back", 2);
                    }
                });
            }
        });
    }

    @ExpoMethod
    public void requestPermissionsAsync(Promise promise) {
        Permissions.CC.askForPermissionsWithPermissionsManager((Permissions) this.mModuleRegistry.getModule(Permissions.class), promise, "android.permission.CAMERA");
    }

    @ExpoMethod
    public void getPermissionsAsync(Promise promise) {
        Permissions.CC.getPermissionsWithPermissionsManager((Permissions) this.mModuleRegistry.getModule(Permissions.class), promise, "android.permission.CAMERA");
    }

    @ExpoMethod
    public void scanFromURLAsync(final String str, List<Double> list, final Promise promise) {
        final ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(Integer.valueOf(list.get(i).intValue()));
            }
        }
        ((ImageLoader) this.mModuleRegistry.getModule(ImageLoader.class)).loadImageForManipulationFromURL(str, new ImageLoader.ResultListener() {
            public void onSuccess(Bitmap bitmap) {
                BarCodeScanner createBarCodeDetectorWithContext = BarCodeScannerModule.this.mBarCodeScannerProvider.createBarCodeDetectorWithContext(BarCodeScannerModule.this.getContext());
                createBarCodeDetectorWithContext.setSettings(new BarCodeScannerSettings() {
                    {
                        putTypes(arrayList);
                    }
                });
                List<BarCodeScannerResult> scanMultiple = createBarCodeDetectorWithContext.scanMultiple(bitmap);
                ArrayList arrayList = new ArrayList();
                for (BarCodeScannerResult next : scanMultiple) {
                    if (arrayList.contains(Integer.valueOf(next.getType()))) {
                        arrayList.add(BarCodeScannerResultSerializer.toBundle(next, 1.0f));
                    }
                }
                promise.resolve(arrayList);
            }

            public void onFailure(Throwable th) {
                Promise promise = promise;
                promise.reject("E_BARCODE_SCANNER_IMAGE_RETRIEVAL_ERROR", "Could not get the image from given url: '" + str + "'", th);
            }
        });
    }
}
