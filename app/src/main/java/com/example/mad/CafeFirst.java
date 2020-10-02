package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class CafeFirst extends AppCompatActivity {
    EditText resName, addressLine1,addressLine2,city,contactNo,email;
    Button btnContinue,btnSave;
    Spinner spinner;

    DatabaseReference databaseCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_first);

        databaseCafe = FirebaseDatabase.getInstance().getReference("cafes");

        resName = findViewById(R.id.editTextResName);
        addressLine1 = findViewById(R.id.editTextPostalAddress1);
        addressLine2 = findViewById(R.id.editTextPostalAddress2);
        city=findViewById(R.id.editTextPostalAddress3);
        contactNo=findViewById(R.id.editTextPhone);
        email=findViewById(R.id.editTextTextEmailAddress);

        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(CafeFirst.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(myAdapter);

        btnContinue =(Button)findViewById(R.id.buttonContinue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!btnSave.isPressed()){

                    Toast.makeText(getApplicationContext(),"If you have fill the form Save the details first",Toast.LENGTH_SHORT).show();

                }
                Intent intent1 = new Intent(CafeFirst.this,Cafe_MyRestaurant.class);
                startActivity(intent1);
            }
        });

        btnSave=(Button)findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(contactNo.getText().length()<10||contactNo.getText().length()>10){
                    contactNo.setError("Please check the phone number length");
                }else{
                    addCafe();
                }

            }
        });
    }

    private void addCafe(){
        String resname = resName.getText().toString().trim();
        String addressline1 = addressLine1.getText().toString().trim();
        String addressline2 = addressLine2.getText().toString().trim();
        String addcity=city.getText().toString().trim();
        String category = spinner.getSelectedItem().toString();
        Integer phone = Integer.parseInt(contactNo.getText().toString());
        String emailadd=email.getText().toString();

        try{
            if(TextUtils.isEmpty(resname)){
                Toast.makeText(this,"Please add the name",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(addressline1)){
                Toast.makeText(this,"Please add the address line 1",Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(addressline2)){
                Toast.makeText(this,"Please add the address line 2",Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(addcity)) {
                Toast.makeText(this, "Please add the city name", Toast.LENGTH_SHORT).show();
            }
            else if (TextUtils.isEmpty(emailadd)){
                Toast.makeText(this,"Please add the email address",Toast.LENGTH_SHORT).show();
            }
            else{
                String id = databaseCafe.push().getKey();

                CafeRestaurant cafeRestaurant = new CafeRestaurant(id,resname,addressline1,addressline2,addcity,category,phone,emailadd);

                databaseCafe.child(id).setValue(cafeRestaurant);

                Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                clearControls();
            }
        }catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Invalid contact number",Toast.LENGTH_SHORT).show();
        }
    }

    private void clearControls(){
        resName.setText("");
        addressLine1.setText("");
        addressLine2.setText("");
        city.setText("");
        contactNo.setText("");
        email.setText("");
    }
}