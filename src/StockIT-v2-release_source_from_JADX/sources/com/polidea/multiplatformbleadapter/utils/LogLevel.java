package com.polidea.multiplatformbleadapter.utils;

import com.polidea.multiplatformbleadapter.utils.Constants;

public class LogLevel {
    public static String fromLogLevel(int i) {
        return i != 2 ? i != 3 ? i != 4 ? i != 5 ? i != 6 ? Constants.BluetoothLogLevel.NONE : Constants.BluetoothLogLevel.ERROR : Constants.BluetoothLogLevel.WARNING : Constants.BluetoothLogLevel.INFO : Constants.BluetoothLogLevel.DEBUG : Constants.BluetoothLogLevel.VERBOSE;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int toLogLevel(java.lang.String r6) {
        /*
            int r0 = r6.hashCode()
            r1 = 5
            r2 = 1
            r3 = 4
            r4 = 3
            r5 = 2
            switch(r0) {
                case -1505867908: goto L_0x003f;
                case 2283726: goto L_0x0035;
                case 2433880: goto L_0x002b;
                case 65906227: goto L_0x0021;
                case 67232232: goto L_0x0017;
                case 2015760738: goto L_0x000d;
                default: goto L_0x000c;
            }
        L_0x000c:
            goto L_0x0049
        L_0x000d:
            java.lang.String r0 = "Verbose"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0049
            r6 = 0
            goto L_0x004a
        L_0x0017:
            java.lang.String r0 = "Error"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0049
            r6 = 4
            goto L_0x004a
        L_0x0021:
            java.lang.String r0 = "Debug"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0049
            r6 = 1
            goto L_0x004a
        L_0x002b:
            java.lang.String r0 = "None"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0049
            r6 = 5
            goto L_0x004a
        L_0x0035:
            java.lang.String r0 = "Info"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0049
            r6 = 2
            goto L_0x004a
        L_0x003f:
            java.lang.String r0 = "Warning"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L_0x0049
            r6 = 3
            goto L_0x004a
        L_0x0049:
            r6 = -1
        L_0x004a:
            if (r6 == 0) goto L_0x005d
            if (r6 == r2) goto L_0x005c
            if (r6 == r5) goto L_0x005b
            if (r6 == r4) goto L_0x005a
            if (r6 == r3) goto L_0x0058
            r6 = 2147483647(0x7fffffff, float:NaN)
            return r6
        L_0x0058:
            r6 = 6
            return r6
        L_0x005a:
            return r1
        L_0x005b:
            return r3
        L_0x005c:
            return r4
        L_0x005d:
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.polidea.multiplatformbleadapter.utils.LogLevel.toLogLevel(java.lang.String):int");
    }
}
