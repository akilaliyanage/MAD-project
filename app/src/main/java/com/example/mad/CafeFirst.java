package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CafeFirst extends AppCompatActivity {

    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_first);

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(CafeFirst.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(myAdapter);

        btnContinue =(Button)findViewById(R.id.buttonContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent1 = new Intent(CafeFirst.this,CafeSecond.class);
               startActivity(intent1);
            }
        });
    }
}