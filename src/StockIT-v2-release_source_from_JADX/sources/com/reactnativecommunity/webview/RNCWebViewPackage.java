package com.reactnativecommunity.webview;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.ReactApplicationContext;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00042\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\n"}, mo40253d2 = {"Lcom/reactnativecommunity/webview/RNCWebViewPackage;", "Lcom/facebook/react/ReactPackage;", "()V", "createNativeModules", "", "Lcom/reactnativecommunity/webview/RNCWebViewModule;", "reactContext", "Lcom/facebook/react/bridge/ReactApplicationContext;", "createViewManagers", "Lcom/reactnativecommunity/webview/RNCWebViewManager;", "react-native-webview_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: RNCWebViewPackage.kt */
public final class RNCWebViewPackage implements ReactPackage {
    public List<RNCWebViewModule> createNativeModules(ReactApplicationContext reactApplicationContext) {
        Intrinsics.checkNotNullParameter(reactApplicationContext, "reactContext");
        return CollectionsKt.listOf(new RNCWebViewModule(reactApplicationContext));
    }

    public List<RNCWebViewManager> createViewManagers(ReactApplicationContext reactApplicationContext) {
        Intrinsics.checkNotNullParameter(reactApplicationContext, "reactContext");
        return CollectionsKt.listOf(new RNCWebViewManager());
    }
}
