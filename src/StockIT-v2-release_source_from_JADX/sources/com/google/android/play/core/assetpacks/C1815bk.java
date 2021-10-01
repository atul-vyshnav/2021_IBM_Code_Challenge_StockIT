package com.google.android.play.core.assetpacks;

import android.os.ParcelFileDescriptor;
import com.google.android.play.core.internal.C1976cj;
import com.google.android.play.core.tasks.Tasks;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/* renamed from: com.google.android.play.core.assetpacks.bk */
final class C1815bk {

    /* renamed from: a */
    private final C1976cj<C1902w> f467a;

    C1815bk(C1976cj<C1902w> cjVar) {
        this.f467a = cjVar;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final InputStream mo34007a(int i, String str, String str2, int i2) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = (ParcelFileDescriptor) Tasks.await(this.f467a.mo34195a().mo33951b(i, str, str2, i2));
            if (parcelFileDescriptor != null && parcelFileDescriptor.getFileDescriptor() != null) {
                return new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
            }
            throw new C1826bv(String.format("Corrupted ParcelFileDescriptor, session %s packName %s sliceId %s, chunkNumber %s", new Object[]{Integer.valueOf(i), str, str2, Integer.valueOf(i2)}), i);
        } catch (ExecutionException e) {
            throw new C1826bv(String.format("Error opening chunk file, session %s packName %s sliceId %s, chunkNumber %s", new Object[]{Integer.valueOf(i), str, str2, Integer.valueOf(i2)}), e, i);
        } catch (InterruptedException e2) {
            throw new C1826bv("Extractor was interrupted while waiting for chunk file.", e2, i);
        }
    }
}
