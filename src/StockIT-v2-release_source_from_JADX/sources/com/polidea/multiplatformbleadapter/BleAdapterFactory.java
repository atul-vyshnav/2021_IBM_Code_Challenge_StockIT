package com.polidea.multiplatformbleadapter;

import android.content.Context;

public class BleAdapterFactory {
    private static BleAdapterCreator bleAdapterCreator = new BleAdapterCreator() {
        public BleAdapter createAdapter(Context context) {
            return new BleModule(context);
        }
    };

    public static BleAdapter getNewAdapter(Context context) {
        return bleAdapterCreator.createAdapter(context);
    }

    public static void setBleAdapterCreator(BleAdapterCreator bleAdapterCreator2) {
        bleAdapterCreator = bleAdapterCreator2;
    }
}
