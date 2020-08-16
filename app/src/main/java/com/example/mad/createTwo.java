package com.example.mad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class createTwo extends AppCompatActivity {

    private Toolbar toolbar;
    private CoordinatorLayout createTwoSnackbar;
    private Button dest1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_two);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);

        createTwoSnackbar = findViewById(R.id.createTwoSnackbar);
        dest1 = findViewById(R.id.dest1);


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

        dest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createTwo.this,createThree.class);
                startActivity(intent);
            }
        });
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}

