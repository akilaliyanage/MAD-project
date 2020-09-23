package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Home extends AppCompatActivity {

    Button userBtn,cafeBtn, shopbtn;
    ImageButton userimgBtn,cafeimgBtn, shopimgBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //User button function
        userBtn=(Button) findViewById(R.id.buttonUser);
        userBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome= new Intent(Home.this,MainActivity.class);
                startActivity(intentHome);
            }
        });

        userimgBtn=findViewById(R.id.imageButtonUser);
        userimgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome= new Intent(Home.this,MainActivity.class);
                startActivity(intentHome);
            }
        });

        //Cafe button function
        cafeBtn=findViewById(R.id.buttonCafe);
        cafeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome= new Intent(Home.this,CafeMain.class);
                startActivity(intentHome);
            }
        });

        cafeimgBtn=findViewById(R.id.imageButtonCafe);
        cafeimgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome= new Intent(Home.this,CafeMain.class);
                startActivity(intentHome);
            }
        });

        shopimgBtn= findViewById(R.id.shopimgbtn);
        shopimgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(Home.this, shoppingmall_dashboard.class);
                startActivity(intentHome);
            }
        });

        shopbtn = findViewById(R.id.shopbtn);
        shopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentHome = new Intent(Home.this, shoppingmall_dashboard.class);
                startActivity(intentHome);
            }
        });

    }
}