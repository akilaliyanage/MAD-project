package com.example.mad;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

public class EditPage extends AppCompatActivity {
    private CoordinatorLayout snackLay;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);

        snackLay = findViewById(R.id.editSnak);

       Snackbar snackbar =  Snackbar.make(snackLay,"Please select the desired location to edit.",Snackbar.LENGTH_INDEFINITE);
       snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}