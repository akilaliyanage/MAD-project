package com.example.mad;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Route {
    private String routeName;
//    private LatLng loc1Latlng;
    private double loc1lat,loc1long;
    private String loc1Title;
    private String loc1Distance;
    private String loc1Time;
    private ArrayList<String> loc1TravelMethods = new ArrayList<>();
    private String loc1AddDet;
    private String routeId;

//    public Route(String routeName, LatLng loc1Latlng, String loc1Title, String loc1Distance, String loc1Time, ArrayList<String> loc1TravelMethods, String loc1AddDet, LatLng loc12Latlng, String loc12Title, String loc12Distance, String loc2Time, ArrayList<String> loc12TravelMethods, String loc2AddDet) {
//        this.routeName = routeName;
////        this.loc1Latlng = loc1Latlng;
//        this.loc1Title = loc1Title;
//        this.loc1Distance = loc1Distance;
//        this.loc1Time = loc1Time;
//        this.loc1TravelMethods = loc1TravelMethods;
//        this.loc1AddDet = loc1AddDet;
////        this.loc12Latlng = loc12Latlng;
//        this.loc12Title = loc12Title;
//        this.loc12Distance = loc12Distance;
//        this.loc2Time = loc2Time;
//        this.loc12TravelMethods = loc12TravelMethods;

    public double getLoc1lat() {
        return loc1lat;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setLoc1lat(double loc1lat) {
        this.loc1lat = loc1lat;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setLoc1long(double loc1long) {
        this.loc1long = loc1long;
    }

    public void setLoc2lat(double loc2lat) {
        this.loc2lat = loc2lat;
    }

    public void setLoc2long(double loc2long) {
        this.loc2long = loc2long;
    }

    public double getLoc1long() {
        return loc1long;
    }

    public double getLoc2lat() {
        return loc2lat;
    }

    public double getLoc2long() {
        return loc2long;
    }
//        this.loc2AddDet = loc2AddDet;
//    }

//    private LatLng loc12Latlng;
    private double loc2lat,loc2long;
    private String loc12Title;
    private String loc12Distance;
    private String loc2Time;
    private ArrayList<String> loc12TravelMethods = new ArrayList<>();
    private String loc2AddDet;



    public void setLoc1AddDet(String loc1AddDet) {
        this.loc1AddDet = loc1AddDet;
    }

    public void setLoc2AddDet(String loc2AddDet) {
        this.loc2AddDet = loc2AddDet;
    }

    public String getLoc1AddDet() {
        return loc1AddDet;
    }

    public String getLoc2AddDet() {
        return loc2AddDet;
    }

    public ArrayList<String> getLoc1TravelMethods() {
        return loc1TravelMethods;
    }

    public ArrayList<String> getLoc12TravelMethods() {
        return loc12TravelMethods;
    }

    public void setLoc1TravelMethods(ArrayList<String> loc1TravelMethods) {
        this.loc1TravelMethods = loc1TravelMethods;
    }

    public void setLoc12TravelMethods(ArrayList<String> loc12TravelMethods) {
        this.loc12TravelMethods = loc12TravelMethods;
    }

    public void setLoc1Time(String loc1Time) {
        this.loc1Time = loc1Time;
    }

    public void setLoc2Time(String loc2Time) {
        this.loc2Time = loc2Time;
    }

    public String getLoc1Time() {
        return loc1Time;
    }

    public String getLoc2Time() {
        return loc2Time;
    }

    public Route() {
    }

    public String getLoc1Distance() {
        return loc1Distance;
    }

    public void setLoc1Distance(String loc1Distance) {
        this.loc1Distance = loc1Distance;
    }

    public void setLoc12Distance(String loc12Distance) {
        this.loc12Distance = loc12Distance;
    }

    public String getLoc12Distance() {
        return loc12Distance;
    }

    public String getRouteName() {
        return routeName;
    }

//    public LatLng getLoc1Latlng() {
//        return loc1Latlng;
//    }

    public String getLoc1Title() {
        return loc1Title;
    }

//    public LatLng getLoc12Latlng() {
//        return loc12Latlng;
//    }

    public String getLoc12Title() {
        return loc12Title;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

//    public void setLoc1Latlng(LatLng loc1Latlng) {
//        this.loc1Latlng = loc1Latlng;
//    }

    public void setLoc1Title(String loc1Title) {
        this.loc1Title = loc1Title;
    }

//    public void setLoc12Latlng(LatLng loc12Latlng) {
//        this.loc12Latlng = loc12Latlng;
//    }

    public void setLoc12Title(String loc12Title) {
        this.loc12Title = loc12Title;
    }
}
