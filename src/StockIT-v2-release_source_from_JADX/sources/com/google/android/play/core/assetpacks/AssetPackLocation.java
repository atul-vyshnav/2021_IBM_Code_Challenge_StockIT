package com.google.android.play.core.assetpacks;

public abstract class AssetPackLocation {

    /* renamed from: a */
    private static final AssetPackLocation f345a = new C1811bg(1, (String) null, (String) null);

    /* renamed from: a */
    static AssetPackLocation m284a() {
        return f345a;
    }

    /* renamed from: a */
    static AssetPackLocation m285a(String str, String str2) {
        return new C1811bg(0, str, str2);
    }

    public abstract String assetsPath();

    public abstract int packStorageMethod();

    public abstract String path();
}
