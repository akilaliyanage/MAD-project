package com.example.miniprojectui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdActivity extends AppCompatActivity {

    Button home3,myRestaurant34;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        home3=(Button)findViewById(R.id.buttonHomeMenuList4);
        myRestaurant34=(Button)findViewById(R.id.buttonAddRest4);

        home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent5= new Intent(ThirdActivity.this,MainActivity.class);
                startActivity(intent5);
            }
        });

        myRestaurant34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6= new Intent(ThirdActivity.this,FourthActivity.class);
                startActivity(intent6);
            }
        });
    }
}