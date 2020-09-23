package com.example.mad;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class createThree extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private ChipGroup chipGroup;
    private MaterialButton addtag;
    private TextInputEditText tagVal;
    private CoordinatorLayout createTwoSnackbar;
    GoogleMap map;
    private TextInputEditText distance;
    private MaterialTextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_three);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);

        chipGroup = findViewById(R.id.tags);
        addtag = findViewById(R.id.Addtag);
        tagVal = findViewById(R.id.tagText);
        createTwoSnackbar = findViewById(R.id.createTwoSnackbar);
        distance = findViewById(R.id.loc2Dist);
        title = findViewById(R.id.loc2Title);
        title.setText(createOne.arrayList.get(1).getTitle());

        addtag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = tagVal.getText().toString();
                Chip chip = new Chip(createThree.this);
                chip.setText(value);

                chipGroup.addView(chip);
            }
        });

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

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map3);
        mapFragment.getMapAsync(createThree.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        moveCamera(createOne.arrayList.get(1).getLatLng(),createOne.arrayList.get(1).getDfeautlZoom(),createOne.arrayList.get(1).getTitle());
        LatLng latLng = createOne.arrayList.get(1).getLatLng();
        float results[] = new float[10];
        Location.distanceBetween(6.927079,79.861244,latLng.latitude,latLng.longitude,results);
        distance.setText(String.valueOf(results[0]/1000) + "KM");
       // route.setLoc1Distance(String.valueOf(results[0]));
    }

    private void moveCamera(LatLng latLng, float zoom,String title) {
        Log.d("TAG", "moveCamera: moving the camera ");
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        MarkerOptions options = new MarkerOptions().position(latLng).title(title);
        map.addMarker(options);

    }
}