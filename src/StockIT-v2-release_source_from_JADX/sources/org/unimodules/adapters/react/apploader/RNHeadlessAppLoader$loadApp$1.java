package org.unimodules.adapters.react.apploader;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import kotlin.Metadata;
import org.unimodules.apploader.HeadlessAppLoader;
import org.unimodules.core.interfaces.Consumer;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, mo40253d2 = {"<anonymous>", "", "it", "Lcom/facebook/react/bridge/ReactContext;", "kotlin.jvm.PlatformType", "onReactContextInitialized"}, mo40254k = 3, mo40255mv = {1, 4, 2})
/* compiled from: RNHeadlessAppLoader.kt */
final class RNHeadlessAppLoader$loadApp$1 implements ReactInstanceManager.ReactInstanceEventListener {
    final /* synthetic */ Consumer $callback;
    final /* synthetic */ HeadlessAppLoader.Params $params;

    RNHeadlessAppLoader$loadApp$1(HeadlessAppLoader.Params params, Consumer consumer) {
        this.$params = params;
        this.$callback = consumer;
    }

    public final void onReactContextInitialized(ReactContext reactContext) {
        HeadlessAppLoaderNotifier.INSTANCE.notifyAppLoaded(this.$params.getAppId());
        Consumer consumer = this.$callback;
        if (consumer != null) {
            consumer.apply(true);
        }
    }
}
