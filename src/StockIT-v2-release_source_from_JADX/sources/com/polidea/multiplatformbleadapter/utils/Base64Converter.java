package com.polidea.multiplatformbleadapter.utils;

import android.util.Base64;

public class Base64Converter {
    public static String encode(byte[] bArr) {
        return Base64.encodeToString(bArr, 2);
    }

    public static byte[] decode(String str) {
        return Base64.decode(str, 2);
    }
}
