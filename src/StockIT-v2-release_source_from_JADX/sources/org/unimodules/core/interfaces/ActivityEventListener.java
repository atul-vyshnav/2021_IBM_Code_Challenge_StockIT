package org.unimodules.core.interfaces;

import android.app.Activity;
import android.content.Intent;

public interface ActivityEventListener {
    void onActivityResult(Activity activity, int i, int i2, Intent intent);

    void onNewIntent(Intent intent);
}
