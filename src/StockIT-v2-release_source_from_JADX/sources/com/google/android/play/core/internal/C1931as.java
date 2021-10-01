package com.google.android.play.core.internal;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import com.google.android.play.core.splitcompat.C2019c;
import com.google.android.play.core.splitcompat.C2032p;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.splitinstall.C2063d;
import com.google.android.play.core.splitinstall.C2065f;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.List;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.internal.as */
public final class C1931as implements C2065f {

    /* renamed from: a */
    private final Context f798a;

    /* renamed from: b */
    private final C2019c f799b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final C1932at f800c;

    /* renamed from: d */
    private final Executor f801d;

    /* renamed from: e */
    private final C2032p f802e;

    public C1931as(Context context, Executor executor, C1932at atVar, C2019c cVar, C2032p pVar) {
        this.f798a = context;
        this.f799b = cVar;
        this.f800c = atVar;
        this.f801d = executor;
        this.f802e = pVar;
    }

    /* renamed from: a */
    private final Integer m645a(List<Intent> list) {
        FileChannel channel;
        FileLock fileLock;
        FileOutputStream fileOutputStream;
        try {
            channel = new RandomAccessFile(this.f799b.mo34275b(), "rw").getChannel();
            Integer num = null;
            try {
                fileLock = channel.tryLock();
            } catch (OverlappingFileLockException unused) {
                fileLock = null;
            }
            if (fileLock != null) {
                int i = 0;
                try {
                    Log.i("SplitCompat", "Copying splits.");
                    for (Intent next : list) {
                        String stringExtra = next.getStringExtra("split_id");
                        AssetFileDescriptor openAssetFileDescriptor = this.f798a.getContentResolver().openAssetFileDescriptor(next.getData(), "r");
                        File a = this.f799b.mo34272a(stringExtra);
                        if (!a.exists() || a.length() == openAssetFileDescriptor.getLength()) {
                            if (a.exists()) {
                            }
                        }
                        if (!this.f799b.mo34276b(stringExtra).exists()) {
                            BufferedInputStream bufferedInputStream = new BufferedInputStream(openAssetFileDescriptor.createInputStream());
                            try {
                                fileOutputStream = new FileOutputStream(a);
                                byte[] bArr = new byte[4096];
                                while (true) {
                                    int read = bufferedInputStream.read(bArr);
                                    if (read <= 0) {
                                        break;
                                    }
                                    fileOutputStream.write(bArr, 0, read);
                                }
                                fileOutputStream.close();
                                bufferedInputStream.close();
                            } catch (Throwable th) {
                                bufferedInputStream.close();
                                throw th;
                            }
                        }
                    }
                    Log.i("SplitCompat", "Splits copied.");
                    try {
                        if (!this.f800c.mo34156a()) {
                            Log.e("SplitCompat", "Split verification failed.");
                            i = -11;
                            num = Integer.valueOf(i);
                            fileLock.release();
                        } else {
                            Log.i("SplitCompat", "Splits verified.");
                            num = Integer.valueOf(i);
                            fileLock.release();
                        }
                    } catch (Exception e) {
                        Log.e("SplitCompat", "Error verifying splits.", e);
                    }
                } catch (Exception e2) {
                    Log.e("SplitCompat", "Error copying splits.", e2);
                    i = -13;
                } catch (Throwable th2) {
                    C1975ci.m765a(th, th2);
                }
            }
            if (channel != null) {
                channel.close();
            }
            return num;
            throw th;
            throw th;
        } catch (Exception e3) {
            Log.e("SplitCompat", "Error locking files.", e3);
            return -13;
        } catch (Throwable th3) {
            C1975ci.m765a(th, th3);
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m646a(C1931as asVar, C2063d dVar) {
        try {
            if (!SplitCompat.m889a(C2032p.m933a(asVar.f798a))) {
                Log.e("SplitCompat", "Emulating splits failed.");
                dVar.mo34349a(-12);
                return;
            }
            Log.i("SplitCompat", "Splits installed.");
            dVar.mo34348a();
        } catch (Exception e) {
            Log.e("SplitCompat", "Error emulating splits.", e);
            dVar.mo34349a(-12);
        }
    }

    /* renamed from: a */
    static /* synthetic */ void m647a(C1931as asVar, List list, C2063d dVar) {
        Integer a = asVar.m645a((List<Intent>) list);
        if (a != null) {
            if (a.intValue() == 0) {
                dVar.mo34350b();
            } else {
                dVar.mo34349a(a.intValue());
            }
        }
    }

    /* renamed from: a */
    public final void mo34155a(List<Intent> list, C2063d dVar) {
        if (SplitCompat.m888a()) {
            this.f801d.execute(new C1930ar(this, list, dVar));
            return;
        }
        throw new IllegalStateException("Ingestion should only be called in SplitCompat mode.");
    }
}
