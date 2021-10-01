package com.google.android.play.core.splitcompat;

import java.io.File;

/* renamed from: com.google.android.play.core.splitcompat.q */
final class C2033q {

    /* renamed from: a */
    private final File f896a;

    /* renamed from: b */
    private final String f897b;

    C2033q() {
    }

    C2033q(File file, String str) {
        this();
        if (file != null) {
            this.f896a = file;
            if (str != null) {
                this.f897b = str;
                return;
            }
            throw new NullPointerException("Null splitId");
        }
        throw new NullPointerException("Null splitFile");
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public File mo34293a() {
        return this.f896a;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public String mo34294b() {
        return this.f897b;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C2033q) {
            C2033q qVar = (C2033q) obj;
            return this.f896a.equals(qVar.mo34293a()) && this.f897b.equals(qVar.mo34294b());
        }
    }

    public int hashCode() {
        return ((this.f896a.hashCode() ^ 1000003) * 1000003) ^ this.f897b.hashCode();
    }

    public String toString() {
        String valueOf = String.valueOf(this.f896a);
        String str = this.f897b;
        StringBuilder sb = new StringBuilder(String.valueOf(valueOf).length() + 35 + str.length());
        sb.append("SplitFileInfo{splitFile=");
        sb.append(valueOf);
        sb.append(", splitId=");
        sb.append(str);
        sb.append("}");
        return sb.toString();
    }
}
