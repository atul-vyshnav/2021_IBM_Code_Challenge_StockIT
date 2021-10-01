package com.facebook.fresco.animation.bitmap.cache;

import android.net.Uri;
import com.facebook.cache.common.CacheKey;

public class AnimationFrameCacheKey implements CacheKey {
    private static final String URI_PREFIX = "anim://";
    private final String mAnimationUriString;

    public boolean isResourceIdForDebugging() {
        return false;
    }

    public AnimationFrameCacheKey(int i) {
        this.mAnimationUriString = URI_PREFIX + i;
    }

    public boolean containsUri(Uri uri) {
        return uri.toString().startsWith(this.mAnimationUriString);
    }

    public String getUriString() {
        return this.mAnimationUriString;
    }
}
