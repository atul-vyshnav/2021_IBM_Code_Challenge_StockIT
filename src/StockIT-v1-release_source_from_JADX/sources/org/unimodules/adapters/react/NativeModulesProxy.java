package org.unimodules.adapters.react;

import android.util.SparseArray;
import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableType;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import org.unimodules.core.ExportedModule;
import org.unimodules.core.ModuleRegistry;
import org.unimodules.core.ViewManager;

public class NativeModulesProxy extends ReactContextBaseJavaModule {
    private static final String ARGS_TYPES_MISMATCH_ERROR = "E_ARGS_TYPES_MISMATCH";
    private static final String EXPORTED_METHODS_KEY = "exportedMethods";
    private static final String METHOD_INFO_ARGUMENTS_COUNT = "argumentsCount";
    private static final String METHOD_INFO_KEY = "key";
    private static final String METHOD_INFO_NAME = "name";
    private static final String MODULES_CONSTANTS_KEY = "modulesConstants";
    private static final String NAME = "NativeUnimoduleProxy";
    private static final String UNDEFINED_METHOD_ERROR = "E_UNDEFINED_METHOD";
    private static final String UNEXPECTED_ERROR = "E_UNEXPECTED_ERROR";
    private static final String VIEW_MANAGERS_NAMES_KEY = "viewManagersNames";
    private Map<String, Map<String, Integer>> mExportedMethodsKeys = new HashMap();
    private Map<String, SparseArray<String>> mExportedMethodsReverseKeys = new HashMap();
    private ModuleRegistry mModuleRegistry;

    public String getName() {
        return NAME;
    }

    public NativeModulesProxy(ReactApplicationContext reactApplicationContext, ModuleRegistry moduleRegistry) {
        super(reactApplicationContext);
        this.mModuleRegistry = moduleRegistry;
    }

    @Nullable
    public Map<String, Object> getConstants() {
        this.mModuleRegistry.ensureIsInitialized();
        Collection<ExportedModule> allExportedModules = this.mModuleRegistry.getAllExportedModules();
        Collection<ViewManager> allViewManagers = this.mModuleRegistry.getAllViewManagers();
        HashMap hashMap = new HashMap(allExportedModules.size());
        HashMap hashMap2 = new HashMap(allExportedModules.size());
        ArrayList arrayList = new ArrayList(allViewManagers.size());
        for (ExportedModule next : allExportedModules) {
            String name = next.getName();
            hashMap.put(name, next.getConstants());
            List<Map<String, Object>> transformExportedMethodsMap = transformExportedMethodsMap(next.getExportedMethods());
            assignExportedMethodsKeys(name, transformExportedMethodsMap);
            hashMap2.put(name, transformExportedMethodsMap);
        }
        for (ViewManager name2 : allViewManagers) {
            arrayList.add(name2.getName());
        }
        HashMap hashMap3 = new HashMap(2);
        hashMap3.put(MODULES_CONSTANTS_KEY, hashMap);
        hashMap3.put(EXPORTED_METHODS_KEY, hashMap2);
        hashMap3.put(VIEW_MANAGERS_NAMES_KEY, arrayList);
        return hashMap3;
    }

    @ReactMethod
    public void callMethod(String str, Dynamic dynamic, ReadableArray readableArray, Promise promise) {
        String str2;
        if (dynamic.getType() == ReadableType.String) {
            str2 = dynamic.asString();
        } else if (dynamic.getType() == ReadableType.Number) {
            str2 = (String) this.mExportedMethodsReverseKeys.get(str).get(dynamic.asInt());
        } else {
            promise.reject(UNEXPECTED_ERROR, "Method key is neither a String nor an Integer -- don't know how to map it to method name.");
            return;
        }
        try {
            List<Object> nativeArgumentsForMethod = getNativeArgumentsForMethod(readableArray, this.mModuleRegistry.getExportedModule(str).getExportedMethodInfos().get(str2));
            nativeArgumentsForMethod.add(new PromiseWrapper(promise));
            this.mModuleRegistry.getExportedModule(str).invokeExportedMethod(str2, nativeArgumentsForMethod);
        } catch (IllegalArgumentException e) {
            promise.reject(ARGS_TYPES_MISMATCH_ERROR, e.getMessage(), (Throwable) e);
        } catch (RuntimeException e2) {
            promise.reject(UNEXPECTED_ERROR, "Encountered an exception while calling native method: " + e2.getMessage(), (Throwable) e2);
        } catch (NoSuchMethodException e3) {
            promise.reject(UNDEFINED_METHOD_ERROR, "Method " + str2 + " of Java module " + str + " is undefined.", (Throwable) e3);
        }
    }

    private static List<Object> getNativeArgumentsForMethod(ReadableArray readableArray, ExportedModule.MethodInfo methodInfo) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < readableArray.size(); i++) {
            arrayList.add(ArgumentsHelper.getNativeArgumentForExpectedClass(readableArray.getDynamic(i), methodInfo.getParameterTypes()[i]));
        }
        return arrayList;
    }

    private List<Map<String, Object>> transformExportedMethodsMap(Map<String, Method> map) {
        ArrayList arrayList = new ArrayList(map.size());
        for (Map.Entry next : map.entrySet()) {
            arrayList.add(getMethodInfo((String) next.getKey(), (Method) next.getValue()));
        }
        return arrayList;
    }

    private Map<String, Object> getMethodInfo(String str, Method method) {
        HashMap hashMap = new HashMap(1);
        hashMap.put("name", str);
        hashMap.put(METHOD_INFO_ARGUMENTS_COUNT, Integer.valueOf(method.getParameterTypes().length - 1));
        return hashMap;
    }

    private void assignExportedMethodsKeys(String str, List<Map<String, Object>> list) {
        if (this.mExportedMethodsKeys.get(str) == null) {
            this.mExportedMethodsKeys.put(str, new HashMap());
        }
        if (this.mExportedMethodsReverseKeys.get(str) == null) {
            this.mExportedMethodsReverseKeys.put(str, new SparseArray());
        }
        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            if (map.get("name") == null || !(map.get("name") instanceof String)) {
                throw new RuntimeException("No method name in MethodInfo - " + map.toString());
            }
            String str2 = (String) map.get("name");
            Integer num = (Integer) this.mExportedMethodsKeys.get(str).get(str2);
            if (num == null) {
                int size = this.mExportedMethodsKeys.get(str).values().size();
                map.put(METHOD_INFO_KEY, Integer.valueOf(size));
                this.mExportedMethodsKeys.get(str).put(str2, Integer.valueOf(size));
                this.mExportedMethodsReverseKeys.get(str).put(size, str2);
            } else {
                map.put(METHOD_INFO_KEY, Integer.valueOf(num.intValue()));
            }
        }
    }

    public void onCatalystInstanceDestroy() {
        this.mModuleRegistry.onDestroy();
    }
}
