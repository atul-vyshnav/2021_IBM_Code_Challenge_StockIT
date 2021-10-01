package com.airbnb.android.react.maps;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.Gradient;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import java.util.Arrays;
import java.util.List;

public class AirMapHeatmap extends AirMapFeature {
    private Gradient gradient;
    private TileOverlay heatmap;
    private TileOverlayOptions heatmapOptions;
    private HeatmapTileProvider heatmapTileProvider;
    private Double opacity;
    private List<WeightedLatLng> points;
    private Integer radius;

    public AirMapHeatmap(Context context) {
        super(context);
    }

    public void setPoints(WeightedLatLng[] weightedLatLngArr) {
        List<WeightedLatLng> asList = Arrays.asList(weightedLatLngArr);
        this.points = asList;
        HeatmapTileProvider heatmapTileProvider2 = this.heatmapTileProvider;
        if (heatmapTileProvider2 != null) {
            heatmapTileProvider2.setWeightedData(asList);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public void setGradient(Gradient gradient2) {
        this.gradient = gradient2;
        HeatmapTileProvider heatmapTileProvider2 = this.heatmapTileProvider;
        if (heatmapTileProvider2 != null) {
            heatmapTileProvider2.setGradient(gradient2);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public void setOpacity(double d) {
        this.opacity = new Double(d);
        HeatmapTileProvider heatmapTileProvider2 = this.heatmapTileProvider;
        if (heatmapTileProvider2 != null) {
            heatmapTileProvider2.setOpacity(d);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public void setRadius(int i) {
        this.radius = new Integer(i);
        HeatmapTileProvider heatmapTileProvider2 = this.heatmapTileProvider;
        if (heatmapTileProvider2 != null) {
            heatmapTileProvider2.setRadius(i);
        }
        TileOverlay tileOverlay = this.heatmap;
        if (tileOverlay != null) {
            tileOverlay.clearTileCache();
        }
    }

    public TileOverlayOptions getHeatmapOptions() {
        if (this.heatmapOptions == null) {
            this.heatmapOptions = createHeatmapOptions();
        }
        return this.heatmapOptions;
    }

    private TileOverlayOptions createHeatmapOptions() {
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        if (this.heatmapTileProvider == null) {
            HeatmapTileProvider.Builder weightedData = new HeatmapTileProvider.Builder().weightedData(this.points);
            Integer num = this.radius;
            if (num != null) {
                weightedData.radius(num.intValue());
            }
            Double d = this.opacity;
            if (d != null) {
                weightedData.opacity(d.doubleValue());
            }
            Gradient gradient2 = this.gradient;
            if (gradient2 != null) {
                weightedData.gradient(gradient2);
            }
            this.heatmapTileProvider = weightedData.build();
        }
        tileOverlayOptions.tileProvider(this.heatmapTileProvider);
        return tileOverlayOptions;
    }

    public Object getFeature() {
        return this.heatmap;
    }

    public void addToMap(GoogleMap googleMap) {
        Log.d("AirMapHeatmap", "ADD TO MAP");
        this.heatmap = googleMap.addTileOverlay(getHeatmapOptions());
    }

    public void removeFromMap(GoogleMap googleMap) {
        this.heatmap.remove();
    }
}
