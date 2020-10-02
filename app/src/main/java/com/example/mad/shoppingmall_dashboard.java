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

public class shoppingmall_dashboard extends AppCompatActivity implements View.OnClickListener {


    Button btn1, btn2, btn3, btn4, addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingmall_dashboard);

        btn1= (Button) findViewById(R.id.containedButton1);
        btn2= (Button) findViewById(R.id.containedButton2);
        btn3= (Button) findViewById(R.id.containedButton3);
        btn4= (Button) findViewById(R.id.containedButton4);

        addBtn= (Button) findViewById(R.id.addShopbtn);

        btn1.setOnClickListener(this);
        //btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        addBtn.setOnClickListener(this);

    }

    public void onClick(View view) {
        switch (view.getId()){
            /*case R.id.containedButton4:
                Intent intent1 = new Intent(this, AddShop.class);
                startActivity(intent1);
                break;
            case R.id.containedButton1:
                Intent intent2 = new Intent(this, AddShop.class);
                startActivity(intent2);
                break;*/
            case R.id.addShopbtn:
                Intent intent4 = new Intent(this, Add_Store.class);
                startActivity(intent4);
            default:
                break;
        }}
};