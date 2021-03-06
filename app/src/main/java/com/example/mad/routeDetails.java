package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class routeDetails extends AppCompatActivity implements OnMapReadyCallback,TaskLoadedCallback {

    GoogleMap map;
    private MarkerOptions place1,place2;
    private Polyline polyline;
    private String routeId,routename,loc1name,loc2name,adddet;
    private DatabaseReference reference;
    Route route = new Route();
    private double loc1lon,loc1lat,loc2lon,loc2lat;
    private MaterialButton searchnear;
    private TextView loc1,loc2,details,id;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;



    List<MarkerOptions> markerOptions = new ArrayList<>();



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details);

        loc1 = findViewById(R.id.shoplocationfetch);
        loc2 = findViewById(R.id.shopbranchfetch);
        details = findViewById(R.id.routeadddetfetch);
        id = findViewById(R.id.shopnamefetch);
        mAuth = FirebaseAuth.getInstance();



        loc1lon = getIntent().getDoubleExtra("loc1long",0);
        loc1lat = getIntent().getDoubleExtra("loc1lat",0);
        loc2lon = getIntent().getDoubleExtra("loc2long",0);
        loc2lat = getIntent().getDoubleExtra("loc2lat",0);
        routename = getIntent().getStringExtra("routename");
        loc1name = getIntent().getStringExtra("loc1name");
        loc2name= getIntent().getStringExtra("loc2name");
        adddet = getIntent().getStringExtra("locadddet");

        loc1.setText(loc1name);
        loc2.setText(loc2name);
        details.setText(adddet);
        id.setText(routename);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);



        //new FetchURL(routeDetails.this).execute(getUrl(place1.getPosition(),place2.getPosition(),"driving"));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.detailsmap);
        mapFragment.getMapAsync(routeDetails.this);

        place1 = new MarkerOptions().position(new LatLng(loc1lat,loc1lon)).title("route.getLoc1Title()");
        place2 = new MarkerOptions().position(new LatLng(loc2lat,loc2lon)).title("route.getLoc12Title()");

        markerOptions.add(place1);
        markerOptions.add(place2);

        String url = getUrl(place1.getPosition(),place2.getPosition(),"driving");
        new FetchURL(routeDetails.this).execute(url,"driving");
    }

    private String getUrl(LatLng position, LatLng position1, String driving) {
        String str_origin = "origin=" + position.latitude + "," + position.longitude;
        String str_dest = "destination="+ position1.latitude + "," + position1.longitude;
        String mode = "mode="+driving;
        String parameter = str_origin + "&" + str_dest + "&" + mode;
        String format = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/" + format + "?" + parameter + "&key=AIzaSyCCfziUDCJPEtwUbnqE0-aBHJyyu4wYIbc";
        return url;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        map.addMarker(place1);
        map.addMarker(place2);

        showAllMarkers();
    }



    private void showAllMarkers() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        for(MarkerOptions op : markerOptions){
            builder.include(op.getPosition());
        }

        LatLngBounds bounds = builder.build();
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.30);

        CameraUpdate update = CameraUpdateFactory.newLatLngBounds(bounds,width,height,padding);
        map.animateCamera(update);
    }

    @Override
    public void onTaskDone(Object... values) {
        if(polyline != null){
            polyline.remove();

            polyline = map.addPolyline((PolylineOptions) values[1]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.signout:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signout() {
        mAuth.signOut();
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(routeDetails.this,MainActivity.class);
            startActivity(intent);
        }
    }
}