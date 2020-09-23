import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Route {
    private String routeName;
    private LatLng loc1Latlng;
    private String loc1Title;
    private String loc1Distance;
    private String loc1Time;
    private ArrayList<String> loc1TravelMethods = new ArrayList<>();
    private String loc1AddDet;

    private LatLng loc12Latlng;
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

    public LatLng getLoc1Latlng() {
        return loc1Latlng;
    }

    public String getLoc1Title() {
        return loc1Title;
    }

    public LatLng getLoc12Latlng() {
        return loc12Latlng;
    }

    public String getLoc12Title() {
        return loc12Title;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setLoc1Latlng(LatLng loc1Latlng) {
        this.loc1Latlng = loc1Latlng;
    }

    public void setLoc1Title(String loc1Title) {
        this.loc1Title = loc1Title;
    }

    public void setLoc12Latlng(LatLng loc12Latlng) {
        this.loc12Latlng = loc12Latlng;
    }

    public void setLoc12Title(String loc12Title) {
        this.loc12Title = loc12Title;
    }
}
