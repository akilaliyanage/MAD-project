package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UpdateHotel extends AppCompatActivity {

    private Intent intent;
    private EditText hotelName;
    private EditText hotelLocation;
    private EditText hotelCharge;
    private EditText hotelPhoneNumber;
    private EditText hotelEmail;
    private ImageView hotelImage;
    private Button updateButton;
    private Button deleteButton;
    private String hotelId;
    private ProgressDialog dialog;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Hotels");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__delete);

        intent = getIntent();
        dialog = new ProgressDialog(this);
        hotelImage = findViewById(R.id.update_hotel_image);
        hotelName = findViewById(R.id.update_hotel_name);
        hotelPhoneNumber = findViewById(R.id.update_hotel_phone_number);
        hotelLocation = findViewById(R.id.update_hotel_location);
        hotelCharge = findViewById(R.id.update_hotel_service_charge);
        hotelEmail = findViewById(R.id.update_hotel_email);
        updateButton = findViewById(R.id.update_hotel_button);
        deleteButton = findViewById(R.id.delete_hotel_button);
        hotelId = intent.getStringExtra("hotelId");

        displayHotelData();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateHotelDetails();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteHotelDetails();
            }
        });
    }

    private void displayHotelData() {
        Picasso.get().load(intent.getStringExtra("hotelImage")).into(hotelImage);
        hotelName.setText(intent.getStringExtra("hotelName"));
        hotelLocation.setText(intent.getStringExtra("hotelLocation"));
        hotelEmail.setText(intent.getStringExtra("hotelEmail"));
        hotelCharge.setText(intent.getStringExtra("hotelCharge"));
        hotelPhoneNumber.setText(intent.getStringExtra("hotelNumber"));
    }

    private void updateHotelDetails() {
        String name = hotelName.getText().toString().trim();
        String email = hotelEmail.getText().toString().trim();
        String location = hotelLocation.getText().toString().trim();
        String phone = hotelPhoneNumber.getText().toString().trim();
        String charge = hotelCharge.getText().toString().trim();
        String image = intent.getStringExtra("hotelImage");

        if (TextUtils.isEmpty(name)) {
            hotelName.setError("Hotel name is required");
            return;
        }
        if (TextUtils.isEmpty(email)) {
            hotelEmail.setError("Hotel email is required");
            return;
        }
        if (TextUtils.isEmpty(location)) {
            hotelLocation.setError("Location is required");
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            hotelPhoneNumber.setError("Phone number is required");
            return;
        }

        dialog.setMessage("Updating Hotel...");
        dialog.show();

        com.example.add_hotel.User updatedUser = new com.example.add_hotel.User(name, location, charge, email, phone, hotelId, image);
        reference.child(hotelId).setValue(updatedUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Hotel Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AllHotels.class));
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Hotel Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteHotelDetails() {
        reference.child(hotelId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Hotel Deleted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), AllHotels.class));
                } else {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Hotel Delete Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
