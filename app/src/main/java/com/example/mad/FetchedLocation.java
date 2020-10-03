package com.example.mad;

import com.google.android.gms.maps.model.LatLng;

public class FetchedLocation {

    private LatLng latLng;
    private String title;
    private static final float  DFEAUTL_ZOOM = 15f;

    public FetchedLocation() {
    }

    public FetchedLocation(LatLng latLng, String title) {
        this.latLng = latLng;
        this.title = title;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getTitle() {
        return title;
    }

    public static float getDfeautlZoom() {
        return DFEAUTL_ZOOM;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
