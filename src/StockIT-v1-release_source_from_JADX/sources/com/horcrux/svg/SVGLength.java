package com.horcrux.svg;

import com.facebook.react.bridge.Dynamic;
import com.facebook.react.bridge.ReadableArray;
import java.util.ArrayList;

class SVGLength {
    final UnitType unit;
    final double value;

    public enum UnitType {
        UNKNOWN,
        NUMBER,
        PERCENTAGE,
        EMS,
        EXS,
        PX,
        CM,
        MM,
        IN,
        PT,
        PC
    }

    private SVGLength() {
        this.value = 0.0d;
        this.unit = UnitType.UNKNOWN;
    }

    SVGLength(double d) {
        this.value = d;
        this.unit = UnitType.NUMBER;
    }

    private SVGLength(String str) {
        String trim = str.trim();
        int length = trim.length();
        int i = length - 1;
        if (length == 0 || trim.equals("normal")) {
            this.unit = UnitType.UNKNOWN;
            this.value = 0.0d;
        } else if (trim.codePointAt(i) == 37) {
            this.unit = UnitType.PERCENTAGE;
            this.value = Double.valueOf(trim.substring(0, i)).doubleValue();
        } else {
            int i2 = length - 2;
            if (i2 > 0) {
                String substring = trim.substring(i2);
                char c = 65535;
                int hashCode = substring.hashCode();
                if (hashCode != 3178) {
                    if (hashCode != 3240) {
                        if (hashCode != 3251) {
                            if (hashCode != 3365) {
                                if (hashCode != 3488) {
                                    if (hashCode != 3571) {
                                        if (hashCode != 3588) {
                                            if (hashCode == 3592 && substring.equals("px")) {
                                                c = 0;
                                            }
                                        } else if (substring.equals("pt")) {
                                            c = 3;
                                        }
                                    } else if (substring.equals("pc")) {
                                        c = 4;
                                    }
                                } else if (substring.equals("mm")) {
                                    c = 5;
                                }
                            } else if (substring.equals("in")) {
                                c = 7;
                            }
                        } else if (substring.equals("ex")) {
                            c = 2;
                        }
                    } else if (substring.equals("em")) {
                        c = 1;
                    }
                } else if (substring.equals("cm")) {
                    c = 6;
                }
                switch (c) {
                    case 0:
                        this.unit = UnitType.NUMBER;
                        break;
                    case 1:
                        this.unit = UnitType.EMS;
                        break;
                    case 2:
                        this.unit = UnitType.EXS;
                        break;
                    case 3:
                        this.unit = UnitType.PT;
                        break;
                    case 4:
                        this.unit = UnitType.PC;
                        break;
                    case 5:
                        this.unit = UnitType.MM;
                        break;
                    case 6:
                        this.unit = UnitType.CM;
                        break;
                    case 7:
                        this.unit = UnitType.IN;
                        break;
                    default:
                        this.unit = UnitType.NUMBER;
                        break;
                }
                length = i2;
                this.value = Double.valueOf(trim.substring(0, length)).doubleValue();
                return;
            }
            this.unit = UnitType.NUMBER;
            this.value = Double.valueOf(trim).doubleValue();
        }
    }

    /* renamed from: com.horcrux.svg.SVGLength$1 */
    static /* synthetic */ class C22501 {
        static final /* synthetic */ int[] $SwitchMap$com$facebook$react$bridge$ReadableType;

        /* JADX WARNING: Can't wrap try/catch for region: R(6:0|1|2|3|4|(3:5|6|8)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
        static {
            /*
                com.facebook.react.bridge.ReadableType[] r0 = com.facebook.react.bridge.ReadableType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$com$facebook$react$bridge$ReadableType = r0
                com.facebook.react.bridge.ReadableType r1 = com.facebook.react.bridge.ReadableType.Number     // Catch:{ NoSuchFieldError -> 0x0012 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0012 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0012 }
            L_0x0012:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReadableType     // Catch:{ NoSuchFieldError -> 0x001d }
                com.facebook.react.bridge.ReadableType r1 = com.facebook.react.bridge.ReadableType.String     // Catch:{ NoSuchFieldError -> 0x001d }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001d }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001d }
            L_0x001d:
                int[] r0 = $SwitchMap$com$facebook$react$bridge$ReadableType     // Catch:{ NoSuchFieldError -> 0x0028 }
                com.facebook.react.bridge.ReadableType r1 = com.facebook.react.bridge.ReadableType.Array     // Catch:{ NoSuchFieldError -> 0x0028 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0028 }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0028 }
            L_0x0028:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.horcrux.svg.SVGLength.C22501.<clinit>():void");
        }
    }

    static SVGLength from(Dynamic dynamic) {
        int i = C22501.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
        if (i == 1) {
            return new SVGLength(dynamic.asDouble());
        }
        if (i != 2) {
            return new SVGLength();
        }
        return new SVGLength(dynamic.asString());
    }

    static String toString(Dynamic dynamic) {
        int i = C22501.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
        if (i == 1) {
            return String.valueOf(dynamic.asDouble());
        }
        if (i != 2) {
            return null;
        }
        return dynamic.asString();
    }

    static ArrayList<SVGLength> arrayFrom(Dynamic dynamic) {
        int i = C22501.$SwitchMap$com$facebook$react$bridge$ReadableType[dynamic.getType().ordinal()];
        if (i == 1) {
            ArrayList<SVGLength> arrayList = new ArrayList<>(1);
            arrayList.add(new SVGLength(dynamic.asDouble()));
            return arrayList;
        } else if (i == 2) {
            ArrayList<SVGLength> arrayList2 = new ArrayList<>(1);
            arrayList2.add(new SVGLength(dynamic.asString()));
            return arrayList2;
        } else if (i != 3) {
            return null;
        } else {
            ReadableArray asArray = dynamic.asArray();
            int size = asArray.size();
            ArrayList<SVGLength> arrayList3 = new ArrayList<>(size);
            for (int i2 = 0; i2 < size; i2++) {
                arrayList3.add(from(asArray.getDynamic(i2)));
            }
            return arrayList3;
        }
    }
}
