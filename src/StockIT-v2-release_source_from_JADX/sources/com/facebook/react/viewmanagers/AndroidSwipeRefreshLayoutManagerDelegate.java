package com.facebook.react.viewmanagers;

import android.view.View;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.BaseViewManagerDelegate;
import com.facebook.react.uimanager.BaseViewManagerInterface;
import com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface;

public class AndroidSwipeRefreshLayoutManagerDelegate<T extends View, U extends BaseViewManagerInterface<T> & AndroidSwipeRefreshLayoutManagerInterface<T>> extends BaseViewManagerDelegate<T, U> {
    public AndroidSwipeRefreshLayoutManagerDelegate(U u) {
        super(u);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v0, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: boolean} */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setProperty(T r8, java.lang.String r9, java.lang.Object r10) {
        /*
            r7 = this;
            int r0 = r9.hashCode()
            r1 = 0
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r0) {
                case -1609594047: goto L_0x0040;
                case -1354842768: goto L_0x0036;
                case -885150488: goto L_0x002c;
                case -416037467: goto L_0x0022;
                case -321826009: goto L_0x0018;
                case 3530753: goto L_0x000e;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x004a
        L_0x000e:
            java.lang.String r0 = "size"
            boolean r0 = r9.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 3
            goto L_0x004b
        L_0x0018:
            java.lang.String r0 = "refreshing"
            boolean r0 = r9.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 5
            goto L_0x004b
        L_0x0022:
            java.lang.String r0 = "progressViewOffset"
            boolean r0 = r9.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 4
            goto L_0x004b
        L_0x002c:
            java.lang.String r0 = "progressBackgroundColor"
            boolean r0 = r9.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 2
            goto L_0x004b
        L_0x0036:
            java.lang.String r0 = "colors"
            boolean r0 = r9.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 1
            goto L_0x004b
        L_0x0040:
            java.lang.String r0 = "enabled"
            boolean r0 = r9.equals(r0)
            if (r0 == 0) goto L_0x004a
            r0 = 0
            goto L_0x004b
        L_0x004a:
            r0 = -1
        L_0x004b:
            if (r0 == 0) goto L_0x00a9
            if (r0 == r6) goto L_0x009f
            if (r0 == r5) goto L_0x008f
            if (r0 == r4) goto L_0x007e
            if (r0 == r3) goto L_0x006c
            if (r0 == r2) goto L_0x005b
            super.setProperty(r8, r9, r10)
            goto L_0x00b9
        L_0x005b:
            com.facebook.react.uimanager.BaseViewManagerInterface r9 = r7.mViewManager
            com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface r9 = (com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface) r9
            if (r10 != 0) goto L_0x0062
            goto L_0x0068
        L_0x0062:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r1 = r10.booleanValue()
        L_0x0068:
            r9.setRefreshing(r8, r1)
            goto L_0x00b9
        L_0x006c:
            com.facebook.react.uimanager.BaseViewManagerInterface r9 = r7.mViewManager
            com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface r9 = (com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface) r9
            if (r10 != 0) goto L_0x0074
            r10 = 0
            goto L_0x007a
        L_0x0074:
            java.lang.Double r10 = (java.lang.Double) r10
            float r10 = r10.floatValue()
        L_0x007a:
            r9.setProgressViewOffset(r8, r10)
            goto L_0x00b9
        L_0x007e:
            com.facebook.react.uimanager.BaseViewManagerInterface r9 = r7.mViewManager
            com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface r9 = (com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface) r9
            if (r10 != 0) goto L_0x0085
            goto L_0x008b
        L_0x0085:
            java.lang.Double r10 = (java.lang.Double) r10
            int r6 = r10.intValue()
        L_0x008b:
            r9.setSize(r8, r6)
            goto L_0x00b9
        L_0x008f:
            com.facebook.react.uimanager.BaseViewManagerInterface r9 = r7.mViewManager
            com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface r9 = (com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface) r9
            android.content.Context r0 = r8.getContext()
            java.lang.Integer r10 = com.facebook.react.bridge.ColorPropConverter.getColor(r10, r0)
            r9.setProgressBackgroundColor(r8, r10)
            goto L_0x00b9
        L_0x009f:
            com.facebook.react.uimanager.BaseViewManagerInterface r9 = r7.mViewManager
            com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface r9 = (com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface) r9
            com.facebook.react.bridge.ReadableArray r10 = (com.facebook.react.bridge.ReadableArray) r10
            r9.setColors(r8, r10)
            goto L_0x00b9
        L_0x00a9:
            com.facebook.react.uimanager.BaseViewManagerInterface r9 = r7.mViewManager
            com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface r9 = (com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerInterface) r9
            if (r10 != 0) goto L_0x00b0
            goto L_0x00b6
        L_0x00b0:
            java.lang.Boolean r10 = (java.lang.Boolean) r10
            boolean r6 = r10.booleanValue()
        L_0x00b6:
            r9.setEnabled(r8, r6)
        L_0x00b9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.react.viewmanagers.AndroidSwipeRefreshLayoutManagerDelegate.setProperty(android.view.View, java.lang.String, java.lang.Object):void");
    }

    public void receiveCommand(AndroidSwipeRefreshLayoutManagerInterface<T> androidSwipeRefreshLayoutManagerInterface, T t, String str, ReadableArray readableArray) {
        if (((str.hashCode() == 513968928 && str.equals("setNativeRefreshing")) ? (char) 0 : 65535) == 0) {
            androidSwipeRefreshLayoutManagerInterface.setNativeRefreshing(t, readableArray.getBoolean(0));
        }
    }
}
