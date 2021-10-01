package org.apache.logging.log4j;

import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class MarkerManager {
    /* access modifiers changed from: private */
    public static final ConcurrentMap<String, Marker> MARKERS = new ConcurrentHashMap();

    private MarkerManager() {
    }

    public static void clear() {
        MARKERS.clear();
    }

    public static Marker getMarker(String str) {
        MARKERS.putIfAbsent(str, new Log4jMarker(str));
        return (Marker) MARKERS.get(str);
    }

    @Deprecated
    public static Marker getMarker(String str, String str2) {
        Marker marker = (Marker) MARKERS.get(str2);
        if (marker != null) {
            return getMarker(str, marker);
        }
        throw new IllegalArgumentException("Parent Marker " + str2 + " has not been defined");
    }

    @Deprecated
    public static Marker getMarker(String str, Marker marker) {
        MARKERS.putIfAbsent(str, new Log4jMarker(str));
        return ((Marker) MARKERS.get(str)).addParents(marker);
    }

    public static class Log4jMarker implements Marker {
        private static final long serialVersionUID = 100;
        private final String name;
        private volatile Marker[] parents;

        private Log4jMarker() {
            this.name = null;
            this.parents = null;
        }

        public Log4jMarker(String str) {
            if (str != null) {
                this.name = str;
                this.parents = null;
                return;
            }
            throw new IllegalArgumentException("Marker name cannot be null.");
        }

        public synchronized Marker addParents(Marker... markerArr) {
            int i;
            if (markerArr != null) {
                Marker[] markerArr2 = this.parents;
                int length = markerArr.length;
                if (markerArr2 != null) {
                    int i2 = 0;
                    for (Marker marker : markerArr) {
                        if (!contains(marker, markerArr2) && !marker.isInstanceOf((Marker) this)) {
                            i2++;
                        }
                    }
                    if (i2 == 0) {
                        return this;
                    }
                    length = markerArr2.length + i2;
                }
                Marker[] markerArr3 = new Marker[length];
                if (markerArr2 != null) {
                    System.arraycopy(markerArr2, 0, markerArr3, 0, markerArr2.length);
                }
                if (markerArr2 == null) {
                    i = 0;
                } else {
                    i = markerArr2.length;
                }
                for (Marker marker2 : markerArr) {
                    if (markerArr2 == null || (!contains(marker2, markerArr2) && !marker2.isInstanceOf((Marker) this))) {
                        markerArr3[i] = marker2;
                        i++;
                    }
                }
                this.parents = markerArr3;
                return this;
            }
            throw new IllegalArgumentException("A parent marker must be specified");
        }

        public synchronized boolean remove(Marker marker) {
            if (marker != null) {
                Marker[] markerArr = this.parents;
                if (markerArr == null) {
                    return false;
                }
                if (r2 != 1) {
                    int i = r2 - 1;
                    Marker[] markerArr2 = new Marker[i];
                    int i2 = 0;
                    for (Marker marker2 : markerArr) {
                        if (!marker2.equals(marker)) {
                            if (i2 == i) {
                                return false;
                            }
                            int i3 = i2 + 1;
                            markerArr2[i2] = marker2;
                            i2 = i3;
                        }
                    }
                    this.parents = markerArr2;
                    return true;
                } else if (!markerArr[0].equals(marker)) {
                    return false;
                } else {
                    this.parents = null;
                    return true;
                }
            } else {
                throw new IllegalArgumentException("A parent marker must be specified");
            }
        }

        public Marker setParents(Marker... markerArr) {
            if (markerArr == null || markerArr.length == 0) {
                this.parents = null;
            } else {
                Marker[] markerArr2 = new Marker[markerArr.length];
                System.arraycopy(markerArr, 0, markerArr2, 0, markerArr.length);
                this.parents = markerArr2;
            }
            return this;
        }

        public String getName() {
            return this.name;
        }

        public Marker[] getParents() {
            if (this.parents == null) {
                return null;
            }
            return (Marker[]) Arrays.copyOf(this.parents, this.parents.length);
        }

        public boolean hasParents() {
            return this.parents != null;
        }

        public boolean isInstanceOf(Marker marker) {
            if (marker == null) {
                throw new IllegalArgumentException("A marker parameter is required");
            } else if (this == marker) {
                return true;
            } else {
                Marker[] markerArr = this.parents;
                if (markerArr != null) {
                    if (r3 == 1) {
                        return checkParent(markerArr[0], marker);
                    }
                    if (r3 != 2) {
                        for (Marker checkParent : markerArr) {
                            if (checkParent(checkParent, marker)) {
                                return true;
                            }
                        }
                    } else if (checkParent(markerArr[0], marker) || checkParent(markerArr[1], marker)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        }

        public boolean isInstanceOf(String str) {
            Marker[] markerArr;
            if (str == null) {
                throw new IllegalArgumentException("A marker name is required");
            } else if (str.equals(getName())) {
                return true;
            } else {
                Marker marker = (Marker) MarkerManager.MARKERS.get(str);
                if (!(marker == null || (markerArr = this.parents) == null)) {
                    if (r3 == 1) {
                        return checkParent(markerArr[0], marker);
                    }
                    if (r3 != 2) {
                        for (Marker checkParent : markerArr) {
                            if (checkParent(checkParent, marker)) {
                                return true;
                            }
                        }
                    } else if (checkParent(markerArr[0], marker) || checkParent(markerArr[1], marker)) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        }

        private static boolean checkParent(Marker marker, Marker marker2) {
            if (marker == marker2) {
                return true;
            }
            Marker[] parents2 = marker instanceof Log4jMarker ? ((Log4jMarker) marker).parents : marker.getParents();
            if (parents2 != null) {
                if (r2 == 1) {
                    return checkParent(parents2[0], marker2);
                }
                if (r2 != 2) {
                    for (Marker checkParent : parents2) {
                        if (checkParent(checkParent, marker2)) {
                            return true;
                        }
                    }
                } else if (checkParent(parents2[0], marker2) || checkParent(parents2[1], marker2)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        private static boolean contains(Marker marker, Marker... markerArr) {
            for (Marker marker2 : markerArr) {
                if (marker2 == marker) {
                    return true;
                }
            }
            return false;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof Marker)) {
                return false;
            }
            return this.name.equals(((Marker) obj).getName());
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder(this.name);
            Marker[] markerArr = this.parents;
            if (markerArr != null) {
                addParentInfo(sb, markerArr);
            }
            return sb.toString();
        }

        private static void addParentInfo(StringBuilder sb, Marker... markerArr) {
            sb.append("[ ");
            int length = markerArr.length;
            boolean z = true;
            int i = 0;
            while (i < length) {
                Log4jMarker log4jMarker = markerArr[i];
                if (!z) {
                    sb.append(", ");
                }
                sb.append(log4jMarker.getName());
                Marker[] parents2 = log4jMarker instanceof Log4jMarker ? log4jMarker.parents : log4jMarker.getParents();
                if (parents2 != null) {
                    addParentInfo(sb, parents2);
                }
                i++;
                z = false;
            }
            sb.append(" ]");
        }
    }
}
