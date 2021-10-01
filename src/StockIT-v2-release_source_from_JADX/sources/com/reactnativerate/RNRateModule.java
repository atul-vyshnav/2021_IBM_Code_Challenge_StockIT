package com.reactnativerate;

import android.app.Activity;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.OnCompleteListener;
import com.google.android.play.core.tasks.Task;

public class RNRateModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;

    public String getName() {
        return "RNRate";
    }

    public RNRateModule(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        this.reactContext = reactApplicationContext;
    }

    @ReactMethod
    public void rate(ReadableMap readableMap, final Callback callback) {
        final ReviewManager create = ReviewManagerFactory.create(this.reactContext);
        create.requestReviewFlow().addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
            public void onComplete(final Task<ReviewInfo> task) {
                if (task.isSuccessful()) {
                    ReviewInfo result = task.getResult();
                    Activity access$000 = RNRateModule.this.getCurrentActivity();
                    if (access$000 != null) {
                        create.launchReviewFlow(access$000, result).addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    callback.invoke(true);
                                    return;
                                }
                                callback.invoke(false);
                            }
                        });
                        return;
                    }
                    return;
                }
                callback.invoke(false);
            }
        });
    }
}
