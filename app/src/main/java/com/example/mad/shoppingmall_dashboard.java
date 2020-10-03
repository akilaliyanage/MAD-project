package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class shoppingmall_dashboard extends AppCompatActivity{


    Button addBtn, myStores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingmall_dashboard);

        addBtn= (Button) findViewById(R.id.addShopbtn);
        myStores = (Button) findViewById(R.id.mystore);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shoppingmall_dashboard.this, Add_Store.class);
                startActivity(intent);
            }
        });

        myStores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shoppingmall_dashboard.this, Store_Details.class);
                startActivity(intent);
            }
        });

    }


};