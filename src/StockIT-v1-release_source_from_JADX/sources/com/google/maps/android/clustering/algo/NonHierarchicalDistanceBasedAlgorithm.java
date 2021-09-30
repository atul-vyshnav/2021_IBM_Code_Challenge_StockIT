package com.google.maps.android.clustering.algo;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.geometry.Bounds;
import com.google.maps.android.geometry.Point;
import com.google.maps.android.projection.SphericalMercatorProjection;
import com.google.maps.android.quadtree.PointQuadTree;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class NonHierarchicalDistanceBasedAlgorithm<T extends ClusterItem> implements Algorithm<T> {
    public static final int MAX_DISTANCE_AT_ZOOM = 100;
    /* access modifiers changed from: private */
    public static final SphericalMercatorProjection PROJECTION = new SphericalMercatorProjection(1.0d);
    private final Collection<QuadItem<T>> mItems = new ArrayList();
    private final PointQuadTree<QuadItem<T>> mQuadTree = new PointQuadTree(0.0d, 1.0d, 0.0d, 1.0d);

    public void addItem(T t) {
        QuadItem quadItem = new QuadItem(t);
        synchronized (this.mQuadTree) {
            this.mItems.add(quadItem);
            this.mQuadTree.add(quadItem);
        }
    }

    public void addItems(Collection<T> collection) {
        for (T addItem : collection) {
            addItem(addItem);
        }
    }

    public void clearItems() {
        synchronized (this.mQuadTree) {
            this.mItems.clear();
            this.mQuadTree.clear();
        }
    }

    public void removeItem(T t) {
        QuadItem quadItem = new QuadItem(t);
        synchronized (this.mQuadTree) {
            this.mItems.remove(quadItem);
            this.mQuadTree.remove(quadItem);
        }
    }

    public Set<? extends Cluster<T>> getClusters(double d) {
        double pow = (100.0d / Math.pow(2.0d, (double) ((int) d))) / 256.0d;
        HashSet hashSet = new HashSet();
        HashSet hashSet2 = new HashSet();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        synchronized (this.mQuadTree) {
            for (QuadItem next : this.mItems) {
                if (!hashSet.contains(next)) {
                    Collection<QuadItem<T>> search = this.mQuadTree.search(createBoundsFromSpan(next.getPoint(), pow));
                    if (search.size() == 1) {
                        hashSet2.add(next);
                        hashSet.add(next);
                        hashMap.put(next, Double.valueOf(0.0d));
                    } else {
                        StaticCluster staticCluster = new StaticCluster(next.mClusterItem.getPosition());
                        hashSet2.add(staticCluster);
                        for (QuadItem next2 : search) {
                            Double d2 = (Double) hashMap.get(next2);
                            double d3 = pow;
                            double distanceSquared = distanceSquared(next2.getPoint(), next.getPoint());
                            if (d2 != null) {
                                if (d2.doubleValue() < distanceSquared) {
                                    pow = d3;
                                } else {
                                    ((StaticCluster) hashMap2.get(next2)).remove(next2.mClusterItem);
                                }
                            }
                            hashMap.put(next2, Double.valueOf(distanceSquared));
                            staticCluster.add(next2.mClusterItem);
                            hashMap2.put(next2, staticCluster);
                            pow = d3;
                        }
                        hashSet.addAll(search);
                        pow = pow;
                    }
                }
            }
        }
        return hashSet2;
    }

    public Collection<T> getItems() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mQuadTree) {
            for (QuadItem<T> access$100 : this.mItems) {
                arrayList.add(access$100.mClusterItem);
            }
        }
        return arrayList;
    }

    private double distanceSquared(Point point, Point point2) {
        return ((point.f1088x - point2.f1088x) * (point.f1088x - point2.f1088x)) + ((point.f1089y - point2.f1089y) * (point.f1089y - point2.f1089y));
    }

    private Bounds createBoundsFromSpan(Point point, double d) {
        double d2 = d / 2.0d;
        return new Bounds(point.f1088x - d2, point.f1088x + d2, point.f1089y - d2, point.f1089y + d2);
    }

    private static class QuadItem<T extends ClusterItem> implements PointQuadTree.Item, Cluster<T> {
        /* access modifiers changed from: private */
        public final T mClusterItem;
        private final Point mPoint;
        private final LatLng mPosition;
        private Set<T> singletonSet;

        public int getSize() {
            return 1;
        }

        private QuadItem(T t) {
            this.mClusterItem = t;
            this.mPosition = t.getPosition();
            this.mPoint = NonHierarchicalDistanceBasedAlgorithm.PROJECTION.toPoint(this.mPosition);
            this.singletonSet = Collections.singleton(this.mClusterItem);
        }

        public Point getPoint() {
            return this.mPoint;
        }

        public LatLng getPosition() {
            return this.mPosition;
        }

        public Set<T> getItems() {
            return this.singletonSet;
        }

        public int hashCode() {
            return this.mClusterItem.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof QuadItem)) {
                return false;
            }
            return ((QuadItem) obj).mClusterItem.equals(this.mClusterItem);
        }
    }
}
