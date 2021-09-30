package org.unimodules.adapters.react.apploader;

import kotlin.Metadata;

@Metadata(mo40251bv = {1, 0, 3}, mo40252d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, mo40253d2 = {"Lorg/unimodules/adapters/react/apploader/HeadlessAppLoaderListener;", "", "appDestroyed", "", "appId", "", "appLoaded", "unimodules-react-native-adapter_release"}, mo40254k = 1, mo40255mv = {1, 4, 2})
/* compiled from: HeadlessAppLoaderNotifier.kt */
public interface HeadlessAppLoaderListener {
    void appDestroyed(String str);

    void appLoaded(String str);
}
