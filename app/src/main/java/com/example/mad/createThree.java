package com.example.mad;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class createThree extends AppCompatActivity {

    private Toolbar toolbar;
    private ChipGroup chipGroup;
    private MaterialButton addtag;
    private TextInputEditText tagVal;
    private CoordinatorLayout createTwoSnackbar;

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}