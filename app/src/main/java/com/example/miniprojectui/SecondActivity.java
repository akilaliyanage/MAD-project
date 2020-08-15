package com.example.miniprojectui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    Button addToMenu,home,myRestaurant24;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        addToMenu=(Button)findViewById(R.id.buttonAddMenu);
        home=(Button)findViewById(R.id.buttonHomeMenuList4);
        myRestaurant24=(Button)findViewById(R.id.buttonMyRest);


        addToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2= new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent2);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent3);
            }
        });

        myRestaurant24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4= new Intent(SecondActivity.this,FourthActivity.class);
                startActivity(intent4);
            }
        });

    }
}