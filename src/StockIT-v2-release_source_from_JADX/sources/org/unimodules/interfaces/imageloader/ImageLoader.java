package org.unimodules.interfaces.imageloader;

import android.graphics.Bitmap;
import java.util.concurrent.Future;

public interface ImageLoader {

    public interface ResultListener {
        void onFailure(Throwable th);

        void onSuccess(Bitmap bitmap);
    }

    Future<Bitmap> loadImageForDisplayFromURL(String str);

    void loadImageForDisplayFromURL(String str, ResultListener resultListener);

    Future<Bitmap> loadImageForManipulationFromURL(String str);

    void loadImageForManipulationFromURL(String str, ResultListener resultListener);
}
