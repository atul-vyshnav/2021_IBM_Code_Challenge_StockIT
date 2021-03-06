package com.bumptech.glide.load.model.stream;

import android.net.Uri;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.facebook.common.util.UriUtil;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class HttpUriLoader implements ModelLoader<Uri, InputStream> {
    private static final Set<String> SCHEMES = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{UriUtil.HTTP_SCHEME, UriUtil.HTTPS_SCHEME})));
    private final ModelLoader<GlideUrl, InputStream> urlLoader;

    public HttpUriLoader(ModelLoader<GlideUrl, InputStream> modelLoader) {
        this.urlLoader = modelLoader;
    }

    public ModelLoader.LoadData<InputStream> buildLoadData(Uri uri, int i, int i2, Options options) {
        return this.urlLoader.buildLoadData(new GlideUrl(uri.toString()), i, i2, options);
    }

    public boolean handles(Uri uri) {
        return SCHEMES.contains(uri.getScheme());
    }

    public static class Factory implements ModelLoaderFactory<Uri, InputStream> {
        public void teardown() {
        }

        public ModelLoader<Uri, InputStream> build(MultiModelLoaderFactory multiModelLoaderFactory) {
            return new HttpUriLoader(multiModelLoaderFactory.build(GlideUrl.class, InputStream.class));
        }
    }
}
