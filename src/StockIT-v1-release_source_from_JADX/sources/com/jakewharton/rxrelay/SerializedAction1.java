package com.jakewharton.rxrelay;

import p030rx.functions.Action1;

final class SerializedAction1<T> implements Action1<T> {
    private static final int MAX_DRAIN_ITERATION = 1024;
    private final Action1<? super T> actual;
    private boolean emitting;
    private FastList<T> queue;

    private static final class FastList<T> {
        T[] array;
        int size;

        FastList() {
        }

        /* access modifiers changed from: package-private */
        public void add(T t) {
            int i = this.size;
            T[] tArr = this.array;
            if (tArr == null) {
                tArr = (Object[]) new Object[16];
                this.array = tArr;
            } else if (i == tArr.length) {
                T[] tArr2 = (Object[]) new Object[((i >> 2) + i)];
                System.arraycopy(tArr, 0, tArr2, 0, i);
                this.array = tArr2;
                tArr = tArr2;
            }
            tArr[i] = t;
            this.size = i + 1;
        }
    }

    SerializedAction1(Action1<? super T> action1) {
        this.actual = action1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0019, code lost:
        r6.actual.call(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x001e, code lost:
        r0 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x0022, code lost:
        if (r0 >= 1024) goto L_0x001e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0024, code lost:
        monitor-enter(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:?, code lost:
        r1 = r6.queue;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0027, code lost:
        if (r1 != null) goto L_0x002d;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0029, code lost:
        r6.emitting = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x002b, code lost:
        monitor-exit(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x002c, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:23:0x002d, code lost:
        r6.queue = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0030, code lost:
        monitor-exit(r6);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0031, code lost:
        r1 = r1.array;
        r2 = r1.length;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0035, code lost:
        if (r3 >= r2) goto L_0x0044;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0037, code lost:
        r4 = r1[r3];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0039, code lost:
        if (r4 != null) goto L_0x003c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x003c, code lost:
        r6.actual.call(r4);
        r3 = r3 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0044, code lost:
        r0 = r0 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void call(T r7) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r6.emitting     // Catch:{ all -> 0x004a }
            if (r0 == 0) goto L_0x0015
            com.jakewharton.rxrelay.SerializedAction1$FastList<T> r0 = r6.queue     // Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x0010
            com.jakewharton.rxrelay.SerializedAction1$FastList r0 = new com.jakewharton.rxrelay.SerializedAction1$FastList     // Catch:{ all -> 0x004a }
            r0.<init>()     // Catch:{ all -> 0x004a }
            r6.queue = r0     // Catch:{ all -> 0x004a }
        L_0x0010:
            r0.add(r7)     // Catch:{ all -> 0x004a }
            monitor-exit(r6)     // Catch:{ all -> 0x004a }
            return
        L_0x0015:
            r0 = 1
            r6.emitting = r0     // Catch:{ all -> 0x004a }
            monitor-exit(r6)     // Catch:{ all -> 0x004a }
            rx.functions.Action1<? super T> r0 = r6.actual
            r0.call(r7)
        L_0x001e:
            r7 = 0
            r0 = 0
        L_0x0020:
            r1 = 1024(0x400, float:1.435E-42)
            if (r0 >= r1) goto L_0x001e
            monitor-enter(r6)
            com.jakewharton.rxrelay.SerializedAction1$FastList<T> r1 = r6.queue     // Catch:{ all -> 0x0047 }
            if (r1 != 0) goto L_0x002d
            r6.emitting = r7     // Catch:{ all -> 0x0047 }
            monitor-exit(r6)     // Catch:{ all -> 0x0047 }
            return
        L_0x002d:
            r2 = 0
            r6.queue = r2     // Catch:{ all -> 0x0047 }
            monitor-exit(r6)     // Catch:{ all -> 0x0047 }
            T[] r1 = r1.array
            int r2 = r1.length
            r3 = 0
        L_0x0035:
            if (r3 >= r2) goto L_0x0044
            r4 = r1[r3]
            if (r4 != 0) goto L_0x003c
            goto L_0x0044
        L_0x003c:
            rx.functions.Action1<? super T> r5 = r6.actual
            r5.call(r4)
            int r3 = r3 + 1
            goto L_0x0035
        L_0x0044:
            int r0 = r0 + 1
            goto L_0x0020
        L_0x0047:
            r7 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0047 }
            throw r7
        L_0x004a:
            r7 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x004a }
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.jakewharton.rxrelay.SerializedAction1.call(java.lang.Object):void");
    }
}
