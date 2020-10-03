package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CafeMain extends AppCompatActivity {

    //declare FloatingActionButton object
    FloatingActionButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_main);

        //giving the id for the button
        btn=(FloatingActionButton)findViewById(R.id.floating_action_button);

        //set the floating button click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //pass from CafeMain activity to CafeFirst activity
                Intent intent = new Intent(CafeMain.this,CafeFirst.class);
                startActivity(intent);

                //Display a toast message "Clicked" when navigating to the next activity
                Toast.makeText(CafeMain.this, "Clicked...", Toast.LENGTH_SHORT).show();
            }
        });

    }
}