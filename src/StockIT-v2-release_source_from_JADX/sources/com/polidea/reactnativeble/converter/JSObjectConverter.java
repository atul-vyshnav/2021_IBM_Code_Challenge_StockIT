package com.polidea.reactnativeble.converter;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;

abstract class JSObjectConverter<T> {
    public abstract WritableMap toJSObject(T t);

    JSObjectConverter() {
    }

    public WritableArray toJSCallback(T t) {
        WritableArray createArray = Arguments.createArray();
        createArray.pushNull();
        createArray.pushMap(toJSObject(t));
        return createArray;
    }
}
