package com.google.android.play.core.assetpacks;

import com.google.android.play.core.internal.C1918af;
import com.google.android.play.core.internal.C1976cj;
import java.io.File;

/* renamed from: com.google.android.play.core.assetpacks.bt */
final class C1824bt {

    /* renamed from: a */
    private static final C1918af f512a = new C1918af("ExtractChunkTaskHandler");

    /* renamed from: b */
    private final byte[] f513b = new byte[8192];

    /* renamed from: c */
    private final C1806bb f514c;

    /* renamed from: d */
    private final C1976cj<C1902w> f515d;

    /* renamed from: e */
    private final C1976cj<C1800aw> f516e;

    /* renamed from: f */
    private final C1830bz f517f;

    C1824bt(C1806bb bbVar, C1976cj<C1902w> cjVar, C1976cj<C1800aw> cjVar2, C1830bz bzVar) {
        this.f514c = bbVar;
        this.f515d = cjVar;
        this.f516e = cjVar2;
        this.f517f = bzVar;
    }

    /* renamed from: b */
    private final File m425b(C1823bs bsVar) {
        File a = this.f514c.mo33964a(bsVar.f596k, bsVar.f503a, bsVar.f504b, bsVar.f505c);
        if (!a.exists()) {
            a.mkdirs();
        }
        return a;
    }

    /* JADX WARNING: Removed duplicated region for block: B:105:0x030f  */
    /* JADX WARNING: Removed duplicated region for block: B:124:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0180 A[Catch:{ all -> 0x032f, all -> 0x0335, IOException -> 0x033b }] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x01a5 A[Catch:{ all -> 0x032f, all -> 0x0335, IOException -> 0x033b }] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01d9 A[Catch:{ all -> 0x032f, all -> 0x0335, IOException -> 0x033b }] */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x0294 A[SYNTHETIC, Splitter:B:93:0x0294] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void mo34019a(com.google.android.play.core.assetpacks.C1823bs r23) {
        /*
            r22 = this;
            r1 = r22
            r2 = r23
            com.google.android.play.core.assetpacks.dr r0 = new com.google.android.play.core.assetpacks.dr
            com.google.android.play.core.assetpacks.bb r4 = r1.f514c
            java.lang.String r5 = r2.f596k
            int r6 = r2.f503a
            long r7 = r2.f504b
            java.lang.String r9 = r2.f505c
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r9)
            com.google.android.play.core.assetpacks.bb r10 = r1.f514c
            java.lang.String r11 = r2.f596k
            int r12 = r2.f503a
            long r13 = r2.f504b
            java.lang.String r15 = r2.f505c
            java.io.File r3 = r10.mo33986f(r11, r12, r13, r15)
            boolean r4 = r3.exists()
            if (r4 != 0) goto L_0x002b
            r3.mkdirs()
        L_0x002b:
            r11 = 3
            r12 = 2
            r13 = 1
            r14 = 0
            java.io.InputStream r3 = r2.f511i     // Catch:{ IOException -> 0x033b }
            int r4 = r2.f506d     // Catch:{ IOException -> 0x033b }
            if (r4 == r13) goto L_0x0037
            r15 = r3
            goto L_0x0040
        L_0x0037:
            java.util.zip.GZIPInputStream r4 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x033b }
            byte[] r5 = r1.f513b     // Catch:{ IOException -> 0x033b }
            int r5 = r5.length     // Catch:{ IOException -> 0x033b }
            r4.<init>(r3, r5)     // Catch:{ IOException -> 0x033b }
            r15 = r4
        L_0x0040:
            int r3 = r2.f507e     // Catch:{ all -> 0x032f }
            r16 = 0
            if (r3 <= 0) goto L_0x017d
            com.google.android.play.core.assetpacks.dq r3 = r0.mo34077a()     // Catch:{ all -> 0x032f }
            int r4 = r3.mo34073e()     // Catch:{ all -> 0x032f }
            int r5 = r2.f507e     // Catch:{ all -> 0x032f }
            int r6 = r5 + -1
            if (r4 != r6) goto L_0x015d
            int r4 = r3.mo34069a()     // Catch:{ all -> 0x032f }
            if (r4 == r13) goto L_0x00d9
            if (r4 == r12) goto L_0x009c
            if (r4 != r11) goto L_0x0082
            com.google.android.play.core.internal.af r4 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r5 = "Resuming central directory from last chunk."
            java.lang.Object[] r6 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r4.mo34139a(r5, r6)     // Catch:{ all -> 0x032f }
            long r3 = r3.mo34071c()     // Catch:{ all -> 0x032f }
            r0.mo34080a((java.io.InputStream) r15, (long) r3)     // Catch:{ all -> 0x032f }
            boolean r3 = r23.mo34018a()     // Catch:{ all -> 0x032f }
            if (r3 == 0) goto L_0x0078
        L_0x0074:
            r4 = r16
            goto L_0x017e
        L_0x0078:
            com.google.android.play.core.assetpacks.bv r0 = new com.google.android.play.core.assetpacks.bv     // Catch:{ all -> 0x032f }
            java.lang.String r3 = "Chunk has ended twice during central directory. This should not be possible with chunk sizes of 50MB."
            int r4 = r2.f595j     // Catch:{ all -> 0x032f }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x032f }
            throw r0     // Catch:{ all -> 0x032f }
        L_0x0082:
            com.google.android.play.core.assetpacks.bv r0 = new com.google.android.play.core.assetpacks.bv     // Catch:{ all -> 0x032f }
            java.lang.Object[] r4 = new java.lang.Object[r13]     // Catch:{ all -> 0x032f }
            int r3 = r3.mo34069a()     // Catch:{ all -> 0x032f }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x032f }
            r4[r14] = r3     // Catch:{ all -> 0x032f }
            java.lang.String r3 = "Slice checkpoint file corrupt. Unexpected FileExtractionStatus %s."
            java.lang.String r3 = java.lang.String.format(r3, r4)     // Catch:{ all -> 0x032f }
            int r4 = r2.f595j     // Catch:{ all -> 0x032f }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x032f }
            throw r0     // Catch:{ all -> 0x032f }
        L_0x009c:
            com.google.android.play.core.internal.af r3 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r4 = "Resuming zip entry from last chunk during local file header."
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r3.mo34139a(r4, r5)     // Catch:{ all -> 0x032f }
            com.google.android.play.core.assetpacks.bb r3 = r1.f514c     // Catch:{ all -> 0x032f }
            java.lang.String r4 = r2.f596k     // Catch:{ all -> 0x032f }
            int r5 = r2.f503a     // Catch:{ all -> 0x032f }
            long r6 = r2.f504b     // Catch:{ all -> 0x032f }
            java.lang.String r8 = r2.f505c     // Catch:{ all -> 0x032f }
            r16 = r3
            r17 = r4
            r18 = r5
            r19 = r6
            r21 = r8
            java.io.File r3 = r16.mo33978d(r17, r18, r19, r21)     // Catch:{ all -> 0x032f }
            boolean r4 = r3.exists()     // Catch:{ all -> 0x032f }
            if (r4 == 0) goto L_0x00cf
            java.io.SequenceInputStream r4 = new java.io.SequenceInputStream     // Catch:{ all -> 0x032f }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ all -> 0x032f }
            r5.<init>(r3)     // Catch:{ all -> 0x032f }
            r4.<init>(r5, r15)     // Catch:{ all -> 0x032f }
            goto L_0x017e
        L_0x00cf:
            com.google.android.play.core.assetpacks.bv r0 = new com.google.android.play.core.assetpacks.bv     // Catch:{ all -> 0x032f }
            java.lang.String r3 = "Checkpoint extension file not found."
            int r4 = r2.f595j     // Catch:{ all -> 0x032f }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x032f }
            throw r0     // Catch:{ all -> 0x032f }
        L_0x00d9:
            com.google.android.play.core.internal.af r4 = f512a     // Catch:{ all -> 0x032f }
            java.lang.Object[] r5 = new java.lang.Object[r13]     // Catch:{ all -> 0x032f }
            java.lang.String r6 = r3.mo34070b()     // Catch:{ all -> 0x032f }
            r5[r14] = r6     // Catch:{ all -> 0x032f }
            java.lang.String r6 = "Resuming zip entry from last chunk during file %s."
            r4.mo34139a(r6, r5)     // Catch:{ all -> 0x032f }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x032f }
            java.lang.String r5 = r3.mo34070b()     // Catch:{ all -> 0x032f }
            r4.<init>(r5)     // Catch:{ all -> 0x032f }
            boolean r5 = r4.exists()     // Catch:{ all -> 0x032f }
            if (r5 == 0) goto L_0x0153
            java.io.RandomAccessFile r5 = new java.io.RandomAccessFile     // Catch:{ all -> 0x032f }
            java.lang.String r6 = "rw"
            r5.<init>(r4, r6)     // Catch:{ all -> 0x032f }
            long r6 = r3.mo34071c()     // Catch:{ all -> 0x032f }
            r5.seek(r6)     // Catch:{ all -> 0x032f }
            long r6 = r3.mo34072d()     // Catch:{ all -> 0x032f }
        L_0x0109:
            byte[] r3 = r1.f513b     // Catch:{ all -> 0x032f }
            int r3 = r3.length     // Catch:{ all -> 0x032f }
            long r8 = (long) r3     // Catch:{ all -> 0x032f }
            long r8 = java.lang.Math.min(r6, r8)     // Catch:{ all -> 0x032f }
            int r3 = (int) r8     // Catch:{ all -> 0x032f }
            byte[] r8 = r1.f513b     // Catch:{ all -> 0x032f }
            int r8 = r15.read(r8, r14, r3)     // Catch:{ all -> 0x032f }
            int r8 = java.lang.Math.max(r8, r14)     // Catch:{ all -> 0x032f }
            if (r8 <= 0) goto L_0x0123
            byte[] r9 = r1.f513b     // Catch:{ all -> 0x032f }
            r5.write(r9, r14, r8)     // Catch:{ all -> 0x032f }
        L_0x0123:
            long r10 = (long) r8     // Catch:{ all -> 0x032f }
            long r9 = r6 - r10
            r6 = 0
            int r11 = (r9 > r6 ? 1 : (r9 == r6 ? 0 : -1))
            if (r11 <= 0) goto L_0x0132
            if (r8 > 0) goto L_0x012f
            goto L_0x0132
        L_0x012f:
            r6 = r9
            r11 = 3
            goto L_0x0109
        L_0x0132:
            long r6 = r5.length()     // Catch:{ all -> 0x032f }
            r5.close()     // Catch:{ all -> 0x032f }
            if (r8 == r3) goto L_0x017d
            com.google.android.play.core.internal.af r3 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r5 = "Chunk has ended while resuming the previous chunks file content."
            java.lang.Object[] r8 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r3.mo34139a(r5, r8)     // Catch:{ all -> 0x032f }
            java.lang.String r4 = r4.getCanonicalPath()     // Catch:{ all -> 0x032f }
            int r11 = r2.f507e     // Catch:{ all -> 0x032f }
            r3 = r0
            r5 = r6
            r7 = r9
            r9 = r11
            r3.mo34081a((java.lang.String) r4, (long) r5, (long) r7, (int) r9)     // Catch:{ all -> 0x032f }
            goto L_0x0074
        L_0x0153:
            com.google.android.play.core.assetpacks.bv r0 = new com.google.android.play.core.assetpacks.bv     // Catch:{ all -> 0x032f }
            java.lang.String r3 = "Partial file specified in checkpoint does not exist. Corrupt directory."
            int r4 = r2.f595j     // Catch:{ all -> 0x032f }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x032f }
            throw r0     // Catch:{ all -> 0x032f }
        L_0x015d:
            com.google.android.play.core.assetpacks.bv r0 = new com.google.android.play.core.assetpacks.bv     // Catch:{ all -> 0x032f }
            java.lang.String r4 = "Trying to resume with chunk number %s when previously processed chunk was number %s."
            java.lang.Object[] r6 = new java.lang.Object[r12]     // Catch:{ all -> 0x032f }
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ all -> 0x032f }
            r6[r14] = r5     // Catch:{ all -> 0x032f }
            int r3 = r3.mo34073e()     // Catch:{ all -> 0x032f }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ all -> 0x032f }
            r6[r13] = r3     // Catch:{ all -> 0x032f }
            java.lang.String r3 = java.lang.String.format(r4, r6)     // Catch:{ all -> 0x032f }
            int r4 = r2.f595j     // Catch:{ all -> 0x032f }
            r0.<init>((java.lang.String) r3, (int) r4)     // Catch:{ all -> 0x032f }
            throw r0     // Catch:{ all -> 0x032f }
        L_0x017d:
            r4 = r15
        L_0x017e:
            if (r4 == 0) goto L_0x028b
            com.google.android.play.core.assetpacks.bm r3 = new com.google.android.play.core.assetpacks.bm     // Catch:{ all -> 0x032f }
            r3.<init>(r4)     // Catch:{ all -> 0x032f }
            java.io.File r5 = r22.m425b(r23)     // Catch:{ all -> 0x032f }
        L_0x0189:
            com.google.android.play.core.assetpacks.dx r6 = r3.mo34008a()     // Catch:{ all -> 0x032f }
            boolean r7 = r6.mo34098f()     // Catch:{ all -> 0x032f }
            if (r7 != 0) goto L_0x01e0
            boolean r7 = r3.mo34010c()     // Catch:{ all -> 0x032f }
            if (r7 != 0) goto L_0x01e0
            boolean r7 = r6.mo34093b()     // Catch:{ all -> 0x032f }
            if (r7 == 0) goto L_0x01d9
            boolean r7 = r6.mo34092a()     // Catch:{ all -> 0x032f }
            if (r7 != 0) goto L_0x01d9
            byte[] r7 = r6.mo34100h()     // Catch:{ all -> 0x032f }
            r0.mo34082a((byte[]) r7)     // Catch:{ all -> 0x032f }
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x032f }
            java.lang.String r8 = r6.mo34094c()     // Catch:{ all -> 0x032f }
            r7.<init>(r5, r8)     // Catch:{ all -> 0x032f }
            java.io.File r8 = r7.getParentFile()     // Catch:{ all -> 0x032f }
            r8.mkdirs()     // Catch:{ all -> 0x032f }
            java.io.FileOutputStream r8 = new java.io.FileOutputStream     // Catch:{ all -> 0x032f }
            r8.<init>(r7)     // Catch:{ all -> 0x032f }
            byte[] r7 = r1.f513b     // Catch:{ all -> 0x032f }
            int r7 = r3.read(r7)     // Catch:{ all -> 0x032f }
        L_0x01c7:
            if (r7 <= 0) goto L_0x01d5
            byte[] r9 = r1.f513b     // Catch:{ all -> 0x032f }
            r8.write(r9, r14, r7)     // Catch:{ all -> 0x032f }
            byte[] r7 = r1.f513b     // Catch:{ all -> 0x032f }
            int r7 = r3.read(r7)     // Catch:{ all -> 0x032f }
            goto L_0x01c7
        L_0x01d5:
            r8.close()     // Catch:{ all -> 0x032f }
            goto L_0x01e0
        L_0x01d9:
            byte[] r7 = r6.mo34100h()     // Catch:{ all -> 0x032f }
            r0.mo34084a((byte[]) r7, (java.io.InputStream) r3)     // Catch:{ all -> 0x032f }
        L_0x01e0:
            boolean r7 = r3.mo34009b()     // Catch:{ all -> 0x032f }
            if (r7 != 0) goto L_0x01ec
            boolean r7 = r3.mo34010c()     // Catch:{ all -> 0x032f }
            if (r7 == 0) goto L_0x0189
        L_0x01ec:
            boolean r5 = r3.mo34010c()     // Catch:{ all -> 0x032f }
            if (r5 == 0) goto L_0x0202
            com.google.android.play.core.internal.af r5 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r7 = "Writing central directory metadata."
            java.lang.Object[] r8 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r5.mo34139a(r7, r8)     // Catch:{ all -> 0x032f }
            byte[] r5 = r6.mo34100h()     // Catch:{ all -> 0x032f }
            r0.mo34084a((byte[]) r5, (java.io.InputStream) r4)     // Catch:{ all -> 0x032f }
        L_0x0202:
            boolean r4 = r23.mo34018a()     // Catch:{ all -> 0x032f }
            if (r4 != 0) goto L_0x028b
            boolean r4 = r6.mo34098f()     // Catch:{ all -> 0x032f }
            if (r4 == 0) goto L_0x0221
            com.google.android.play.core.internal.af r3 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r4 = "Writing slice checkpoint for partial local file header."
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r3.mo34139a(r4, r5)     // Catch:{ all -> 0x032f }
            byte[] r3 = r6.mo34100h()     // Catch:{ all -> 0x032f }
            int r4 = r2.f507e     // Catch:{ all -> 0x032f }
            r0.mo34083a((byte[]) r3, (int) r4)     // Catch:{ all -> 0x032f }
            goto L_0x028b
        L_0x0221:
            boolean r4 = r3.mo34010c()     // Catch:{ all -> 0x032f }
            if (r4 == 0) goto L_0x0236
            com.google.android.play.core.internal.af r3 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r4 = "Writing slice checkpoint for central directory."
            java.lang.Object[] r5 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r3.mo34139a(r4, r5)     // Catch:{ all -> 0x032f }
            int r3 = r2.f507e     // Catch:{ all -> 0x032f }
            r0.mo34078a((int) r3)     // Catch:{ all -> 0x032f }
            goto L_0x028b
        L_0x0236:
            int r4 = r6.mo34096e()     // Catch:{ all -> 0x032f }
            if (r4 != 0) goto L_0x026c
            com.google.android.play.core.internal.af r4 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r5 = "Writing slice checkpoint for partial file."
            java.lang.Object[] r7 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r4.mo34139a(r5, r7)     // Catch:{ all -> 0x032f }
            java.io.File r4 = new java.io.File     // Catch:{ all -> 0x032f }
            java.io.File r5 = r22.m425b(r23)     // Catch:{ all -> 0x032f }
            java.lang.String r7 = r6.mo34094c()     // Catch:{ all -> 0x032f }
            r4.<init>(r5, r7)     // Catch:{ all -> 0x032f }
            long r5 = r6.mo34095d()     // Catch:{ all -> 0x032f }
            long r7 = r3.mo34011d()     // Catch:{ all -> 0x032f }
            long r5 = r5 - r7
            long r7 = r4.length()     // Catch:{ all -> 0x032f }
            int r9 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r9 != 0) goto L_0x0264
            goto L_0x027d
        L_0x0264:
            com.google.android.play.core.assetpacks.bv r0 = new com.google.android.play.core.assetpacks.bv     // Catch:{ all -> 0x032f }
            java.lang.String r3 = "Partial file is of unexpected size."
            r0.<init>(r3)     // Catch:{ all -> 0x032f }
            throw r0     // Catch:{ all -> 0x032f }
        L_0x026c:
            com.google.android.play.core.internal.af r4 = f512a     // Catch:{ all -> 0x032f }
            java.lang.String r5 = "Writing slice checkpoint for partial unextractable file."
            java.lang.Object[] r6 = new java.lang.Object[r14]     // Catch:{ all -> 0x032f }
            r4.mo34139a(r5, r6)     // Catch:{ all -> 0x032f }
            java.io.File r4 = r0.mo34085b()     // Catch:{ all -> 0x032f }
            long r5 = r4.length()     // Catch:{ all -> 0x032f }
        L_0x027d:
            java.lang.String r4 = r4.getCanonicalPath()     // Catch:{ all -> 0x032f }
            long r7 = r3.mo34011d()     // Catch:{ all -> 0x032f }
            int r9 = r2.f507e     // Catch:{ all -> 0x032f }
            r3 = r0
            r3.mo34081a((java.lang.String) r4, (long) r5, (long) r7, (int) r9)     // Catch:{ all -> 0x032f }
        L_0x028b:
            r15.close()     // Catch:{ IOException -> 0x033b }
            boolean r3 = r23.mo34018a()
            if (r3 == 0) goto L_0x02b4
            int r3 = r2.f507e     // Catch:{ IOException -> 0x029a }
            r0.mo34086b(r3)     // Catch:{ IOException -> 0x029a }
            goto L_0x02b4
        L_0x029a:
            r0 = move-exception
            com.google.android.play.core.internal.af r3 = f512a
            java.lang.Object[] r4 = new java.lang.Object[r13]
            java.lang.String r5 = r0.getMessage()
            r4[r14] = r5
            java.lang.String r5 = "Writing extraction finished checkpoint failed with %s."
            r3.mo34141b(r5, r4)
            com.google.android.play.core.assetpacks.bv r3 = new com.google.android.play.core.assetpacks.bv
            int r2 = r2.f595j
            java.lang.String r4 = "Writing extraction finished checkpoint failed."
            r3.<init>(r4, r0, r2)
            throw r3
        L_0x02b4:
            com.google.android.play.core.internal.af r0 = f512a
            r3 = 4
            java.lang.Object[] r3 = new java.lang.Object[r3]
            int r4 = r2.f507e
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r3[r14] = r4
            java.lang.String r4 = r2.f505c
            r3[r13] = r4
            java.lang.String r4 = r2.f596k
            r3[r12] = r4
            int r4 = r2.f595j
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)
            r5 = 3
            r3[r5] = r4
            java.lang.String r4 = "Extraction finished for chunk %s of slice %s of pack %s of session %s."
            r0.mo34142c(r4, r3)
            com.google.android.play.core.internal.cj<com.google.android.play.core.assetpacks.w> r0 = r1.f515d
            java.lang.Object r0 = r0.mo34195a()
            com.google.android.play.core.assetpacks.w r0 = (com.google.android.play.core.assetpacks.C1902w) r0
            int r3 = r2.f595j
            java.lang.String r4 = r2.f596k
            java.lang.String r5 = r2.f505c
            int r6 = r2.f507e
            r0.mo33948a(r3, r4, r5, r6)
            java.io.InputStream r0 = r2.f511i     // Catch:{ IOException -> 0x02f0 }
            r0.close()     // Catch:{ IOException -> 0x02f0 }
            goto L_0x030a
        L_0x02f0:
            com.google.android.play.core.internal.af r0 = f512a
            r3 = 3
            java.lang.Object[] r4 = new java.lang.Object[r3]
            int r3 = r2.f507e
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r4[r14] = r3
            java.lang.String r3 = r2.f505c
            r4[r13] = r3
            java.lang.String r3 = r2.f596k
            r4[r12] = r3
            java.lang.String r3 = "Could not close file for chunk %s of slice %s of pack %s."
            r0.mo34143d(r3, r4)
        L_0x030a:
            int r0 = r2.f510h
            r3 = 3
            if (r0 != r3) goto L_0x032e
            com.google.android.play.core.internal.cj<com.google.android.play.core.assetpacks.aw> r0 = r1.f516e
            java.lang.Object r0 = r0.mo34195a()
            com.google.android.play.core.assetpacks.aw r0 = (com.google.android.play.core.assetpacks.C1800aw) r0
            java.lang.String r3 = r2.f596k
            long r7 = r2.f509g
            r4 = 3
            r5 = 0
            com.google.android.play.core.assetpacks.bz r6 = r1.f517f
            double r9 = r6.mo34024a(r3, r2)
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r7
            com.google.android.play.core.assetpacks.AssetPackState r2 = com.google.android.play.core.assetpacks.AssetPackState.m287a(r2, r3, r4, r5, r7, r9)
            r0.mo33957a((com.google.android.play.core.assetpacks.AssetPackState) r2)
        L_0x032e:
            return
        L_0x032f:
            r0 = move-exception
            r3 = r0
            r15.close()     // Catch:{ all -> 0x0335 }
            goto L_0x033a
        L_0x0335:
            r0 = move-exception
            r4 = r0
            com.google.android.play.core.internal.C1975ci.m765a(r3, r4)     // Catch:{ IOException -> 0x033b }
        L_0x033a:
            throw r3     // Catch:{ IOException -> 0x033b }
        L_0x033b:
            r0 = move-exception
            com.google.android.play.core.internal.af r3 = f512a
            java.lang.Object[] r4 = new java.lang.Object[r13]
            java.lang.String r5 = r0.getMessage()
            r4[r14] = r5
            java.lang.String r5 = "IOException during extraction %s."
            r3.mo34141b(r5, r4)
            com.google.android.play.core.assetpacks.bv r3 = new com.google.android.play.core.assetpacks.bv
            r4 = 4
            java.lang.Object[] r4 = new java.lang.Object[r4]
            int r5 = r2.f507e
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r4[r14] = r5
            java.lang.String r5 = r2.f505c
            r4[r13] = r5
            java.lang.String r5 = r2.f596k
            r4[r12] = r5
            int r5 = r2.f595j
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)
            r6 = 3
            r4[r6] = r5
            java.lang.String r5 = "Error extracting chunk %s of slice %s of pack %s of session %s."
            java.lang.String r4 = java.lang.String.format(r5, r4)
            int r2 = r2.f595j
            r3.<init>(r4, r0, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.assetpacks.C1824bt.mo34019a(com.google.android.play.core.assetpacks.bs):void");
    }
}
