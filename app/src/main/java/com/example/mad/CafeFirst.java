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

    //object names
    EditText resName, addressLine1,addressLine2,city,contactNo,email;
    Button btnContinue,btnSave;
    Spinner spinner;

    //database reference
    DatabaseReference databaseCafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_first);

        //get a firebase instance and  reference
        databaseCafe = FirebaseDatabase.getInstance().getReference("cafes");

        //define ids
        resName = findViewById(R.id.editTextResName);
        addressLine1 = findViewById(R.id.editTextPostalAddress1);
        addressLine2 = findViewById(R.id.editTextPostalAddress2);
        city=findViewById(R.id.editTextPostalAddress3);
        contactNo=findViewById(R.id.editTextPhone);
        email=findViewById(R.id.editTextTextEmailAddress);

        //category list
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> myAdapter= new ArrayAdapter<String>(CafeFirst.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.categories));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(myAdapter);

        //Continue button declaration
        btnContinue =(Button)findViewById(R.id.buttonContinue);

        //Continue button function
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the continue button is clicked before the Save button then display a toast message
                if (!btnSave.isPressed()){
                    Toast.makeText(getApplicationContext(),"If you have fill the form Save the details first",Toast.LENGTH_SHORT).show();
                }

                //once the continue button is clicked then navigate from the current page to the Retrieval of data page
                Intent intent1 = new Intent(CafeFirst.this,Cafe_MyRestaurant.class);
                startActivity(intent1);
            }
        });

        //Save button function
        btnSave=(Button)findViewById(R.id.buttonSave);

        //Save button function
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check whether the contact number length is less than 10 or more than 10 digits
                //if true then set an error when Save button is clicked
                if(contactNo.getText().length()<10||contactNo.getText().length()>10){
                    contactNo.setError("Please check the phone number length");
                }else{
                    //if the condition is false save the cafe details
                    addCafe();
                }

            }
        });
    }

    //declaring the addCafe function
    private void addCafe(){
        String resname = resName.getText().toString().trim();
        String addressline1 = addressLine1.getText().toString().trim();
        String addressline2 = addressLine2.getText().toString().trim();
        String addcity=city.getText().toString().trim();
        String category = spinner.getSelectedItem().toString();
        Integer phone = Integer.parseInt(contactNo.getText().toString());
        String emailadd=email.getText().toString();

        //try catch block
        try{
            //check the edit text for name is empty
            if(TextUtils.isEmpty(resname)){
                Toast.makeText(this,"Please add the name",Toast.LENGTH_SHORT).show();
            }
            //check the edit text for address1 is empty
            else if(TextUtils.isEmpty(addressline1)){
                Toast.makeText(this,"Please add the address line 1",Toast.LENGTH_SHORT).show();
            }
            //check the edit text for address2 is empty
            else if (TextUtils.isEmpty(addressline2)){
                Toast.makeText(this,"Please add the address line 2",Toast.LENGTH_SHORT).show();
            }
            //check the edit text for city is empty
            else if (TextUtils.isEmpty(addcity)) {
                Toast.makeText(this, "Please add the city name", Toast.LENGTH_SHORT).show();
            }
            //check the email address text is empty
            else if (TextUtils.isEmpty(emailadd)){
                Toast.makeText(this,"Please add the email address",Toast.LENGTH_SHORT).show();
            }
            //if all above conditions are false
            else{
                //have a unique id for records in database
                String id = databaseCafe.push().getKey();

                //creating an object of class CafeRestaurant and pass the parameters
                CafeRestaurant cafeRestaurant = new CafeRestaurant(id,resname,addressline1,addressline2,addcity,category,phone,emailadd);

                //set the object attributes under the id
                databaseCafe.child(id).setValue(cafeRestaurant);

                //once the saving is successful display a toast message
                Toast.makeText(getApplicationContext(),"Added Successfully",Toast.LENGTH_SHORT).show();
                //after saving the form details clear the entered details of the form
                clearControls();
            }
            //if the contact number is invalid then catch the exception
        }catch (NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Invalid contact number",Toast.LENGTH_SHORT).show();
        }
    }

    //function to clear the form details
    private void clearControls(){
        resName.setText("");
        addressLine1.setText("");
        addressLine2.setText("");
        city.setText("");
        contactNo.setText("");
        email.setText("");
    }
}