package org.unimodules.core.arguments;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ReadableArguments {
    boolean containsKey(String str);

    Object get(String str);

    ReadableArguments getArguments(String str);

    boolean getBoolean(String str);

    boolean getBoolean(String str, boolean z);

    double getDouble(String str);

    double getDouble(String str, double d);

    int getInt(String str);

    int getInt(String str, int i);

    List getList(String str);

    List getList(String str, List list);

    Map getMap(String str);

    Map getMap(String str, Map map);

    String getString(String str);

    String getString(String str, String str2);

    boolean isEmpty();

    Collection<String> keys();

    int size();

    Bundle toBundle();

    /* renamed from: org.unimodules.core.arguments.ReadableArguments$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static boolean $default$getBoolean(ReadableArguments _this, String str) {
            return _this.getBoolean(str, false);
        }

        public static double $default$getDouble(ReadableArguments _this, String str) {
            return _this.getDouble(str, 0.0d);
        }

        public static int $default$getInt(ReadableArguments _this, String str) {
            return _this.getInt(str, 0);
        }

        public static String $default$getString(ReadableArguments _this, String str) {
            return _this.getString(str, (String) null);
        }

        public static List $default$getList(ReadableArguments _this, String str) {
            return _this.getList(str, (List) null);
        }

        public static Map $default$getMap(ReadableArguments _this, String str) {
            return _this.getMap(str, (Map) null);
        }

        public static ReadableArguments $default$getArguments(ReadableArguments _this, String str) {
            Object obj = _this.get(str);
            if (obj instanceof Map) {
                return new MapArguments((Map) obj);
            }
            return null;
        }

        public static Bundle $default$toBundle(ReadableArguments _this) {
            Bundle bundle = new Bundle();
            for (String next : _this.keys()) {
                Object obj = _this.get(next);
                if (obj == null) {
                    bundle.putString(next, (String) null);
                } else if (obj instanceof String) {
                    bundle.putString(next, (String) obj);
                } else if (obj instanceof Integer) {
                    bundle.putInt(next, ((Integer) obj).intValue());
                } else if (obj instanceof Double) {
                    bundle.putDouble(next, ((Double) obj).doubleValue());
                } else if (obj instanceof Long) {
                    bundle.putLong(next, ((Long) obj).longValue());
                } else if (obj instanceof Boolean) {
                    bundle.putBoolean(next, ((Boolean) obj).booleanValue());
                } else if (obj instanceof ArrayList) {
                    bundle.putParcelableArrayList(next, (ArrayList) obj);
                } else if (obj instanceof Map) {
                    bundle.putBundle(next, new MapArguments((Map) obj).toBundle());
                } else if (obj instanceof Bundle) {
                    bundle.putBundle(next, (Bundle) obj);
                } else {
                    throw new UnsupportedOperationException("Could not put a value of " + obj.getClass() + " to bundle.");
                }
            }
            return bundle;
        }
    }
}
