package com.google.android.gms.internal.vision;

import org.apache.commons.p029io.IOUtils;

/* compiled from: com.google.android.gms:play-services-vision-common@@19.0.0 */
final class zzjf {
    static String zzd(zzfh zzfh) {
        zzji zzji = new zzji(zzfh);
        StringBuilder sb = new StringBuilder(zzji.size());
        for (int i = 0; i < zzji.size(); i++) {
            byte zzan = zzji.zzan(i);
            if (zzan == 34) {
                sb.append("\\\"");
            } else if (zzan == 39) {
                sb.append("\\'");
            } else if (zzan != 92) {
                switch (zzan) {
                    case 7:
                        sb.append("\\a");
                        break;
                    case 8:
                        sb.append("\\b");
                        break;
                    case 9:
                        sb.append("\\t");
                        break;
                    case 10:
                        sb.append("\\n");
                        break;
                    case 11:
                        sb.append("\\v");
                        break;
                    case 12:
                        sb.append("\\f");
                        break;
                    case 13:
                        sb.append("\\r");
                        break;
                    default:
                        if (zzan >= 32 && zzan <= 126) {
                            sb.append((char) zzan);
                            break;
                        } else {
                            sb.append(IOUtils.DIR_SEPARATOR_WINDOWS);
                            sb.append((char) (((zzan >>> 6) & 3) + 48));
                            sb.append((char) (((zzan >>> 3) & 7) + 48));
                            sb.append((char) ((zzan & 7) + 48));
                            break;
                        }
                }
            } else {
                sb.append("\\\\");
            }
        }
        return sb.toString();
    }
}
