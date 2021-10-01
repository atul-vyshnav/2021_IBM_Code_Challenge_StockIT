package org.apache.logging.log4j;

import java.io.Serializable;

public interface Marker extends Serializable {
    Marker addParents(Marker... markerArr);

    String getName();

    Marker[] getParents();

    boolean hasParents();

    boolean isInstanceOf(String str);

    boolean isInstanceOf(Marker marker);

    boolean remove(Marker marker);

    Marker setParents(Marker... markerArr);
}
