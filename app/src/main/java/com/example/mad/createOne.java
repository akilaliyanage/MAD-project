package com.example.mad;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class createOne extends AppCompatActivity implements OnMapReadyCallback {




    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    protected static ArrayList<FetchedLocation> arrayList = new ArrayList<>();
    private Toolbar toolbar;
    private Button createTwoBtn;
    private TextView nullTxt;
    private Button serchBtn;
    private CardView card;
    private CoordinatorLayout coordinatorLayout;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String CORSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private EditText mSearch;
    private static final String TAG = "createOne";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private boolean locationPermissionGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private GoogleMap mMAp;
    private FusedLocationProviderClient locationProviderClient;
    private static final float  DFEAUTL_ZOOM = 15f;
    private FetchedLocation fetchedLocation;


    //widgets

    private EditText mapSearchTxt;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_one);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);
        serchBtn = findViewById(R.id.containedButton);
        createTwoBtn = findViewById(R.id.createTwoBtn);

        card = findViewById(R.id.card);
        mSearch = findViewById(R.id.mapSearchTxt);
        coordinatorLayout = findViewById(R.id.cordnatorLay);
        CharSequence sequence = "Welcome to 'Create a Route' option 😊❤ Please turn on your device's Location";

        Snackbar snackbar = Snackbar.make(coordinatorLayout, sequence, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("OKEY", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do nothing
            }
        });
        View snackBarView = snackbar.getView();
        snackbar.setBackgroundTint(Color.parseColor("#206a5d"));
        snackbar.setActionTextColor(Color.parseColor("#d6efc7"));
        snackbar.show();

        getLocationPermission();

        serchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoLocate();
            }
        });



        createTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createOne.this,createTwo.class);
                startActivity(intent);
            }
        });

    }




    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting device current location");
        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (locationPermissionGranted) {
                final Task Location = locationProviderClient.getLastLocation();
                Location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: Found Location");
                            android.location.Location currentLocation = (android.location.Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DFEAUTL_ZOOM,"My Location");


                        } else {
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(createOne.this, "Unable to find the location😢", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        } catch (SecurityException ex) {
            Log.d(TAG, "getDeviceLocation: SecurityException" + ex.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_LONG);
        mMAp = googleMap;

        if (locationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMAp.setMyLocationEnabled(true);
            mMAp.getUiSettings().setMyLocationButtonEnabled(false);
            init();
        }
    }
    private void init(){
        Log.d(TAG, "init: initilalizing");
        hideKeybard();

    }

    private void moveCamera(LatLng latLng, float zoom,String title) {
        Log.d(TAG, "moveCamera: moving the camera ");
        mMAp.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        mMAp.addMarker(options);
        hideKeybard();
    }
    
    private void geoLocate() {
        Log.d(TAG, "geoLocate: geolocate");
        String searhString = mSearch.getText().toString().trim();
        Geocoder geocoder = new Geocoder(createOne.this);
        List<Address> list = new ArrayList<>();

        try {
            list = geocoder.getFromLocationName(searhString,1);

        }catch (Exception ex){
            Log.d(TAG, "geoLocate: Exception" + ex.getMessage());
        }

        if(list.size() > 0){
            Address address = list.get(0);

            fetchedLocation = new FetchedLocation(new LatLng(address.getLatitude(),address.getLongitude()),address.getAddressLine(0));
            arrayList.add(fetchedLocation);


            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),DFEAUTL_ZOOM,address.getAddressLine(0));
        }else {
            Toast toast = Toast.makeText(this,"Sorry we did not find anythin similer 😢",Toast.LENGTH_LONG);
            toast.show();
        }




    }

    private void initMap(){
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(createOne.this);
    }

    private void getLocationPermission(){
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),CORSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                locationPermissionGranted = true;
                initMap();
            }
            else {
                ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
        else {
            ActivityCompat.requestPermissions(this,permission,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        locationPermissionGranted = false;

        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            locationPermissionGranted = false;
                            return;
                        }
                    }
                    locationPermissionGranted = true;

                    //initilize the map

                    initMap();
                }
            }
        }
    }

    private void hideKeybard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }



}