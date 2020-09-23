package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Store_Details extends AppCompatActivity {

    Button btn_del;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__details);

        btn_del=(Button)findViewById(R.id.del_store);
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDeletion();
            }
        });

    }
    Intent intent = getIntent();

    public void confirmDeletion(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Store_Details.this);
        builder.setTitle("Delete Store?!");
        builder.setMessage("Just delete it");
        //builder.setIcon(R.drawable.alert1);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(),"Store Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //something
            }
        });
        builder.show();
    }
}