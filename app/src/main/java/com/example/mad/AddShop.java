package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class AddShop extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Spinner spinner;
    Button btn;
    EditText nameText, locationText, descText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        spinner = (Spinner) findViewById(R.id.dropdown);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        nameText=(EditText) findViewById(R.id.storeText);
        locationText=(EditText)findViewById(R.id.locationText);
        descText=(EditText)findViewById(R.id.descText);


        btn = (Button) findViewById(R.id.addstore_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextValidation();
            }
        });

    }

    Intent intent = getIntent();

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String item = (String) spinner.getItemAtPosition(position);
        if(item.isEmpty()){
            setSpinnerError(spinner);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    public void TextValidation(){
        if(nameText.getText().toString().length() == 0){
            nameText.requestFocus();
            nameText.setError("Store name cannot be empty");
        }
        else if(descText.getText().toString().length() == 0){
            descText.requestFocus();
            descText.setError("Description cannot be empty");
        }
        else if(locationText.getText().toString().length()==0){
            locationText.requestFocus();
            locationText.setError("Location cannot be empty");
        }
        else {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AddShop.this);
            builder.setTitle("Submit Details?");
            builder.setMessage("Don't worry babe.You can change your data later. wink wink");
            builder.setIcon(R.drawable.alert1);
            builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(AddShop.this, Store_Details.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Succesfully Added", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //something
                }
            });
            builder.show();

        }
    }

    private void setSpinnerError(Spinner spinner){
        View selectedView = spinner.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            spinner.requestFocus();
            TextView selectedTextView = (TextView) selectedView;
            selectedTextView.setError("error");
            selectedTextView.setText("Select Category");
            selectedTextView.setTextColor(Color.RED);
        }
    }

}