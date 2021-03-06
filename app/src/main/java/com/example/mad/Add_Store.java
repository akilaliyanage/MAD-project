package com.example.mad;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Add_Store extends AppCompatActivity {

    Spinner spinner;
    Button btn;
    EditText nameText, locationText, descText, branchText;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__store);

        spinner = (Spinner) findViewById(R.id.dropdown);
        ArrayAdapter<CharSequence> adapter =  ArrayAdapter.createFromResource(this, R.array.category_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);

        nameText=(EditText) findViewById(R.id.storeText);
        locationText=(EditText)findViewById(R.id.locationText);
        descText=(EditText)findViewById(R.id.descText);
        branchText = (EditText) findViewById(R.id.branchText);

        btn = (Button) findViewById(R.id.addstore_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextValidation();
            }
        });

    }

    Intent intent = getIntent();


    public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
        String item = (String) spinner.getItemAtPosition(position);
        if(item.isEmpty()){
            setSpinnerError(spinner);
        }
    }


    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    //validate text inputs
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
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Add_Store.this);
            builder.setTitle("Submit Details?");
            builder.setMessage("Your store will be added to our system. You can modify your details later");
            builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    createStore();
                    Intent intent = new Intent(Add_Store.this, Store_Details.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(),"Succesfully Added", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            builder.show(); } }

    //create new store
    public void createStore(){
        String name = nameText.getText().toString().trim();
        String location = locationText.getText().toString().trim();
        String description = descText.getText().toString().trim();
        String branch = branchText.getText().toString().trim();
        String category = spinner.getSelectedItem().toString();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Store");
        String id = databaseReference.push().getKey();
        Store store = new Store(id, name, category, description, location, branch);
        databaseReference.child(id).setValue(store);
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