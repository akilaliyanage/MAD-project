package com.example.mad;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class createTwo extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private CoordinatorLayout createTwoSnackbar;
    private Button dest1;
    GoogleMap map;
    private MaterialTextView title;
    private Chip bus,train,flight,foot;
    private TextInputEditText distance,time,description,routeName;
    protected static Route route = new Route();
    private ArrayList<String> methods = new ArrayList<>();
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_two);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        createTwoSnackbar = findViewById(R.id.createTwoSnackbar);
        dest1 = findViewById(R.id.dest1);
        //initilizingnand setting the title
        title = findViewById(R.id.locationOneTitle);
        title.setText(createOne.arrayList.get(0).getTitle());

        distance = findViewById(R.id.loc1Distance);
        time = findViewById(R.id.loc1Time);
        description = findViewById(R.id.loc1Des);
        bus = findViewById(R.id.loc1bus);
        train = findViewById(R.id.loc1train);
        flight = findViewById(R.id.loc1train);
        foot = findViewById(R.id.loc1foot);

        routeName = findViewById(R.id.routename);

        Snackbar snackbar = Snackbar.make(createTwoSnackbar, "Please fill out the following details", Snackbar.LENGTH_INDEFINITE);
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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapOne);
        mapFragment.getMapAsync(createTwo.this);


        dest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(time.getText().toString().trim()) || TextUtils.isEmpty(description.getText().toString().trim())){
                    time.setError("Time is Required Field");
                    description.setError("Additional description is Required Field");
                }else{
                    if(bus.isChecked()){
                        methods.add("Bus");
                    }else if(train.isChecked()){
                        methods.add("Train");
                    }else if(flight.isChecked()){
                        methods.add("Flight");
                    }else if(foot.isChecked()){
                        methods.add("Foot");
                    }
                    route.setLoc1AddDet(description.getText().toString().trim());
                    route.setLoc1Time(time.getText().toString().trim());
                    route.setLoc1lat(createOne.arrayList.get(0).getLatLng().latitude);
                    route.setLoc1long(createOne.arrayList.get(0).getLatLng().longitude);
//                    route.setLoc1Latlng(createOne.arrayList.get(0).getLatLng());
                    route.setLoc1Title(createOne.arrayList.get(0).getTitle());
                    route.setLoc1TravelMethods(methods);

                    String routrName = routeName.getText().toString().trim();
                    route.setRouteName(routrName);

                    Intent intent = new Intent(createTwo.this,createThree.class);
                    startActivity(intent);
                }


            }
        });
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
            Intent intent = new Intent(createTwo.this,MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
       moveCamera(createOne.arrayList.get(0).getLatLng(),createOne.arrayList.get(0).getDfeautlZoom(),createOne.arrayList.get(0).getTitle());
       LatLng latLng = createOne.arrayList.get(0).getLatLng();
       float results[] = new float[10];
       Location.distanceBetween(6.927079,79.861244,latLng.latitude,latLng.longitude,results);
       distance.setText(String.valueOf(results[0]/1000) + "KM");
       route.setLoc1Distance(String.valueOf(results[0]/1000));
    }

    private void moveCamera(LatLng latLng, float zoom,String title) {
        Log.d("TAG", "moveCamera: moving the camera ");
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        map.addMarker(options);

    }


}

