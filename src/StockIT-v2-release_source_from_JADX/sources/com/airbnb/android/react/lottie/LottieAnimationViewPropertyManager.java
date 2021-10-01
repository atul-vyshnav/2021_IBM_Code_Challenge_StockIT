package com.airbnb.android.react.lottie;

import android.graphics.Color;
import android.widget.ImageView;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.RenderMode;
import com.airbnb.lottie.SimpleColorFilter;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieValueCallback;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import java.lang.ref.WeakReference;
import java.util.regex.Pattern;

public class LottieAnimationViewPropertyManager {
    private String animationJson;
    private String animationName;
    private boolean animationNameDirty;
    private ReadableArray colorFilters;
    private Boolean enableMergePaths;
    private String imageAssetsFolder;
    private Boolean loop;
    private Float progress;
    private RenderMode renderMode;
    private ImageView.ScaleType scaleType;
    private Float speed;
    private final WeakReference<LottieAnimationView> viewWeakReference;

    public LottieAnimationViewPropertyManager(LottieAnimationView lottieAnimationView) {
        this.viewWeakReference = new WeakReference<>(lottieAnimationView);
    }

    public void setAnimationName(String str) {
        this.animationName = str;
        this.animationNameDirty = true;
    }

    public void setAnimationJson(String str) {
        this.animationJson = str;
    }

    public void setProgress(Float f) {
        this.progress = f;
    }

    public void setSpeed(float f) {
        this.speed = Float.valueOf(f);
    }

    public void setLoop(boolean z) {
        this.loop = Boolean.valueOf(z);
    }

    public void setScaleType(ImageView.ScaleType scaleType2) {
        this.scaleType = scaleType2;
    }

    public void setRenderMode(RenderMode renderMode2) {
        this.renderMode = renderMode2;
    }

    public void setImageAssetsFolder(String str) {
        this.imageAssetsFolder = str;
    }

    public void setEnableMergePaths(boolean z) {
        this.enableMergePaths = Boolean.valueOf(z);
    }

    public void setColorFilters(ReadableArray readableArray) {
        this.colorFilters = readableArray;
    }

    public void commitChanges() {
        LottieAnimationView lottieAnimationView = (LottieAnimationView) this.viewWeakReference.get();
        if (lottieAnimationView != null) {
            String str = this.animationJson;
            if (str != null) {
                lottieAnimationView.setAnimationFromJson(str, Integer.toString(str.hashCode()));
                this.animationJson = null;
            }
            if (this.animationNameDirty) {
                lottieAnimationView.setAnimation(this.animationName);
                this.animationNameDirty = false;
            }
            Float f = this.progress;
            if (f != null) {
                lottieAnimationView.setProgress(f.floatValue());
                this.progress = null;
            }
            Boolean bool = this.loop;
            if (bool != null) {
                lottieAnimationView.setRepeatCount(bool.booleanValue() ? -1 : 0);
                this.loop = null;
            }
            Float f2 = this.speed;
            if (f2 != null) {
                lottieAnimationView.setSpeed(f2.floatValue());
                this.speed = null;
            }
            ImageView.ScaleType scaleType2 = this.scaleType;
            if (scaleType2 != null) {
                lottieAnimationView.setScaleType(scaleType2);
                this.scaleType = null;
            }
            RenderMode renderMode2 = this.renderMode;
            if (renderMode2 != null) {
                lottieAnimationView.setRenderMode(renderMode2);
                this.renderMode = null;
            }
            String str2 = this.imageAssetsFolder;
            if (str2 != null) {
                lottieAnimationView.setImageAssetsFolder(str2);
                this.imageAssetsFolder = null;
            }
            Boolean bool2 = this.enableMergePaths;
            if (bool2 != null) {
                lottieAnimationView.enableMergePathsForKitKatAndAbove(bool2.booleanValue());
                this.enableMergePaths = null;
            }
            ReadableArray readableArray = this.colorFilters;
            if (readableArray != null && readableArray.size() > 0) {
                for (int i = 0; i < this.colorFilters.size(); i++) {
                    ReadableMap map = this.colorFilters.getMap(i);
                    String string = map.getString("color");
                    String string2 = map.getString("keypath");
                    SimpleColorFilter simpleColorFilter = new SimpleColorFilter(Color.parseColor(string));
                    lottieAnimationView.addValueCallback(new KeyPath((string2 + ".**").split(Pattern.quote("."))), LottieProperty.COLOR_FILTER, new LottieValueCallback(simpleColorFilter));
                }
            }
        }
    }
}
