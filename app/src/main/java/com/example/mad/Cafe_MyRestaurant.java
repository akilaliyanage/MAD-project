package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cafe_MyRestaurant extends AppCompatActivity {

    Button home4,addRest,viewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe__my_restaurant);

        home4=(Button)findViewById(R.id.buttonHomeMenuList4);
        addRest=(Button)findViewById(R.id.buttonAddRest4);
        viewMenu=(Button)findViewById(R.id.buttonViewMenu1);

        home4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7= new Intent(Cafe_MyRestaurant.this,CafeMain.class);
                startActivity(intent7);
            }
        });

        addRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8= new Intent(Cafe_MyRestaurant.this,CafeFirst.class);
                startActivity(intent8);
            }
        });

        viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent9= new Intent(Cafe_MyRestaurant.this,Cafe_MenuList.class);
                startActivity(intent9);
            }
        });
    }
}