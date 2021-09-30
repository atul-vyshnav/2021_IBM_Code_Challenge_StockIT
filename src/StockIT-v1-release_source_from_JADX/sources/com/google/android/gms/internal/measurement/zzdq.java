package com.google.android.gms.internal.measurement;

import java.io.IOException;
import kotlin.UByte;
import kotlin.jvm.internal.ByteCompanionObject;

/* compiled from: com.google.android.gms:play-services-measurement-base@@17.2.0 */
final class zzdq {
    static int zza(byte[] bArr, int i, zzdt zzdt) {
        int i2 = i + 1;
        byte b = bArr[i];
        if (b < 0) {
            return zza((int) b, bArr, i2, zzdt);
        }
        zzdt.zza = b;
        return i2;
    }

    static int zza(int i, byte[] bArr, int i2, zzdt zzdt) {
        int i3 = i & 127;
        int i4 = i2 + 1;
        byte b = bArr[i2];
        if (b >= 0) {
            zzdt.zza = i3 | (b << 7);
            return i4;
        }
        int i5 = i3 | ((b & ByteCompanionObject.MAX_VALUE) << 7);
        int i6 = i4 + 1;
        byte b2 = bArr[i4];
        if (b2 >= 0) {
            zzdt.zza = i5 | (b2 << 14);
            return i6;
        }
        int i7 = i5 | ((b2 & ByteCompanionObject.MAX_VALUE) << 14);
        int i8 = i6 + 1;
        byte b3 = bArr[i6];
        if (b3 >= 0) {
            zzdt.zza = i7 | (b3 << 21);
            return i8;
        }
        int i9 = i7 | ((b3 & ByteCompanionObject.MAX_VALUE) << 21);
        int i10 = i8 + 1;
        byte b4 = bArr[i8];
        if (b4 >= 0) {
            zzdt.zza = i9 | (b4 << 28);
            return i10;
        }
        int i11 = i9 | ((b4 & ByteCompanionObject.MAX_VALUE) << 28);
        while (true) {
            int i12 = i10 + 1;
            if (bArr[i10] >= 0) {
                zzdt.zza = i11;
                return i12;
            }
            i10 = i12;
        }
    }

    static int zzb(byte[] bArr, int i, zzdt zzdt) {
        int i2 = i + 1;
        long j = (long) bArr[i];
        if (j >= 0) {
            zzdt.zzb = j;
            return i2;
        }
        int i3 = i2 + 1;
        byte b = bArr[i2];
        long j2 = (j & 127) | (((long) (b & ByteCompanionObject.MAX_VALUE)) << 7);
        int i4 = 7;
        while (b < 0) {
            int i5 = i3 + 1;
            byte b2 = bArr[i3];
            i4 += 7;
            j2 |= ((long) (b2 & ByteCompanionObject.MAX_VALUE)) << i4;
            int i6 = i5;
            b = b2;
            i3 = i6;
        }
        zzdt.zzb = j2;
        return i3;
    }

    static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & UByte.MAX_VALUE) << 24) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i + 2] & UByte.MAX_VALUE) << 16);
    }

    static long zzb(byte[] bArr, int i) {
        return ((((long) bArr[i + 7]) & 255) << 56) | (((long) bArr[i]) & 255) | ((((long) bArr[i + 1]) & 255) << 8) | ((((long) bArr[i + 2]) & 255) << 16) | ((((long) bArr[i + 3]) & 255) << 24) | ((((long) bArr[i + 4]) & 255) << 32) | ((((long) bArr[i + 5]) & 255) << 40) | ((((long) bArr[i + 6]) & 255) << 48);
    }

    static double zzc(byte[] bArr, int i) {
        return Double.longBitsToDouble(zzb(bArr, i));
    }

    static float zzd(byte[] bArr, int i) {
        return Float.intBitsToFloat(zza(bArr, i));
    }

    static int zzc(byte[] bArr, int i, zzdt zzdt) throws zzfo {
        int zza = zza(bArr, i, zzdt);
        int i2 = zzdt.zza;
        if (i2 < 0) {
            throw zzfo.zzb();
        } else if (i2 == 0) {
            zzdt.zzc = "";
            return zza;
        } else {
            zzdt.zzc = new String(bArr, zza, i2, zzff.zza);
            return zza + i2;
        }
    }

    static int zzd(byte[] bArr, int i, zzdt zzdt) throws zzfo {
        int zza = zza(bArr, i, zzdt);
        int i2 = zzdt.zza;
        if (i2 < 0) {
            throw zzfo.zzb();
        } else if (i2 == 0) {
            zzdt.zzc = "";
            return zza;
        } else {
            zzdt.zzc = zzie.zzb(bArr, zza, i2);
            return zza + i2;
        }
    }

    static int zze(byte[] bArr, int i, zzdt zzdt) throws zzfo {
        int zza = zza(bArr, i, zzdt);
        int i2 = zzdt.zza;
        if (i2 < 0) {
            throw zzfo.zzb();
        } else if (i2 > bArr.length - zza) {
            throw zzfo.zza();
        } else if (i2 == 0) {
            zzdt.zzc = zzdu.zza;
            return zza;
        } else {
            zzdt.zzc = zzdu.zza(bArr, zza, i2);
            return zza + i2;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v2, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v0, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v6, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int zza(com.google.android.gms.internal.measurement.zzhd r6, byte[] r7, int r8, int r9, com.google.android.gms.internal.measurement.zzdt r10) throws java.io.IOException {
        /*
            int r0 = r8 + 1
            byte r8 = r7[r8]
            if (r8 >= 0) goto L_0x000c
            int r0 = zza((int) r8, (byte[]) r7, (int) r0, (com.google.android.gms.internal.measurement.zzdt) r10)
            int r8 = r10.zza
        L_0x000c:
            r3 = r0
            if (r8 < 0) goto L_0x0025
            int r9 = r9 - r3
            if (r8 > r9) goto L_0x0025
            java.lang.Object r9 = r6.zza()
            int r8 = r8 + r3
            r0 = r6
            r1 = r9
            r2 = r7
            r4 = r8
            r5 = r10
            r0.zza(r1, r2, r3, r4, r5)
            r6.zzc(r9)
            r10.zzc = r9
            return r8
        L_0x0025:
            com.google.android.gms.internal.measurement.zzfo r6 = com.google.android.gms.internal.measurement.zzfo.zza()
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.measurement.zzdq.zza(com.google.android.gms.internal.measurement.zzhd, byte[], int, int, com.google.android.gms.internal.measurement.zzdt):int");
    }

    static int zza(zzhd zzhd, byte[] bArr, int i, int i2, int i3, zzdt zzdt) throws IOException {
        zzgs zzgs = (zzgs) zzhd;
        Object zza = zzgs.zza();
        int zza2 = zzgs.zza(zza, bArr, i, i2, i3, zzdt);
        zzgs.zzc(zza);
        zzdt.zzc = zza;
        return zza2;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzfl<?> zzfl, zzdt zzdt) {
        zzfg zzfg = (zzfg) zzfl;
        int zza = zza(bArr, i2, zzdt);
        zzfg.zzd(zzdt.zza);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzdt);
            if (i != zzdt.zza) {
                break;
            }
            zza = zza(bArr, zza2, zzdt);
            zzfg.zzd(zzdt.zza);
        }
        return zza;
    }

    static int zza(byte[] bArr, int i, zzfl<?> zzfl, zzdt zzdt) throws IOException {
        zzfg zzfg = (zzfg) zzfl;
        int zza = zza(bArr, i, zzdt);
        int i2 = zzdt.zza + zza;
        while (zza < i2) {
            zza = zza(bArr, zza, zzdt);
            zzfg.zzd(zzdt.zza);
        }
        if (zza == i2) {
            return zza;
        }
        throw zzfo.zza();
    }

    static int zza(zzhd<?> zzhd, int i, byte[] bArr, int i2, int i3, zzfl<?> zzfl, zzdt zzdt) throws IOException {
        int zza = zza((zzhd) zzhd, bArr, i2, i3, zzdt);
        zzfl.add(zzdt.zzc);
        while (zza < i3) {
            int zza2 = zza(bArr, zza, zzdt);
            if (i != zzdt.zza) {
                break;
            }
            zza = zza((zzhd) zzhd, bArr, zza2, i3, zzdt);
            zzfl.add(zzdt.zzc);
        }
        return zza;
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzhy zzhy, zzdt zzdt) throws zzfo {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                int zzb = zzb(bArr, i2, zzdt);
                zzhy.zza(i, (Object) Long.valueOf(zzdt.zzb));
                return zzb;
            } else if (i4 == 1) {
                zzhy.zza(i, (Object) Long.valueOf(zzb(bArr, i2)));
                return i2 + 8;
            } else if (i4 == 2) {
                int zza = zza(bArr, i2, zzdt);
                int i5 = zzdt.zza;
                if (i5 < 0) {
                    throw zzfo.zzb();
                } else if (i5 <= bArr.length - zza) {
                    if (i5 == 0) {
                        zzhy.zza(i, (Object) zzdu.zza);
                    } else {
                        zzhy.zza(i, (Object) zzdu.zza(bArr, zza, i5));
                    }
                    return zza + i5;
                } else {
                    throw zzfo.zza();
                }
            } else if (i4 == 3) {
                zzhy zzb2 = zzhy.zzb();
                int i6 = (i & -8) | 4;
                int i7 = 0;
                while (true) {
                    if (i2 >= i3) {
                        break;
                    }
                    int zza2 = zza(bArr, i2, zzdt);
                    int i8 = zzdt.zza;
                    i7 = i8;
                    if (i8 == i6) {
                        i2 = zza2;
                        break;
                    }
                    int zza3 = zza(i7, bArr, zza2, i3, zzb2, zzdt);
                    i7 = i8;
                    i2 = zza3;
                }
                if (i2 > i3 || i7 != i6) {
                    throw zzfo.zzg();
                }
                zzhy.zza(i, (Object) zzb2);
                return i2;
            } else if (i4 == 5) {
                zzhy.zza(i, (Object) Integer.valueOf(zza(bArr, i2)));
                return i2 + 4;
            } else {
                throw zzfo.zzd();
            }
        } else {
            throw zzfo.zzd();
        }
    }

    static int zza(int i, byte[] bArr, int i2, int i3, zzdt zzdt) throws zzfo {
        if ((i >>> 3) != 0) {
            int i4 = i & 7;
            if (i4 == 0) {
                return zzb(bArr, i2, zzdt);
            }
            if (i4 == 1) {
                return i2 + 8;
            }
            if (i4 == 2) {
                return zza(bArr, i2, zzdt) + zzdt.zza;
            }
            if (i4 == 3) {
                int i5 = (i & -8) | 4;
                int i6 = 0;
                while (i2 < i3) {
                    i2 = zza(bArr, i2, zzdt);
                    i6 = zzdt.zza;
                    if (i6 == i5) {
                        break;
                    }
                    i2 = zza(i6, bArr, i2, i3, zzdt);
                }
                if (i2 <= i3 && i6 == i5) {
                    return i2;
                }
                throw zzfo.zzg();
            } else if (i4 == 5) {
                return i2 + 4;
            } else {
                throw zzfo.zzd();
            }
        } else {
            throw zzfo.zzd();
        }
    }
}
