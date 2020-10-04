package com.example.mad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mad.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class Add_hotel extends AppCompatActivity {

    private EditText hotelName;
    private EditText locationName;
    private EditText charge;
    private EditText phoneNumber;
    private EditText hotelEmail;
    private Button addHotelButton;
    private ImageView hotelImage;
    private static final int GALARY_INTENT = 2;
    private Uri imageUri;
    private ProgressDialog dialog;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Hotels");
    private StorageReference imageStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hotel);

        dialog = new ProgressDialog(this);
        imageStorage = FirebaseStorage.getInstance().getReference("Hotel_Images");
        hotelName = findViewById(R.id.update_hotel_name);
        locationName = findViewById(R.id.update_hotel_location);
        charge = findViewById(R.id.update_hotel_service_charge);
        phoneNumber = findViewById(R.id.update_hotel_phone_number);
        hotelEmail = findViewById(R.id.update_hotel_email);
        addHotelButton = findViewById(R.id.update_hotel_button);
        hotelImage = findViewById(R.id.update_hotel_image);

        hotelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "ProductImage"), GALARY_INTENT);
            }
        });

        addHotelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createHotel();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALARY_INTENT && resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                hotelImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String GetFileExtension(Uri uri) {
        ContentResolver contentResolver = this.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void createHotel() {
        if (imageUri != null) {
            dialog.setMessage("Sending Data...");
            dialog.show();

            final StorageReference imageReference = imageStorage.child(System.currentTimeMillis() + "." + GetFileExtension(imageUri));
            imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final String name = hotelName.getText().toString().trim();
                    final String location = locationName.getText().toString().trim();
                    final String hotelCharge = charge.getText().toString().trim();
                    final String phone = phoneNumber.getText().toString().trim();
                    final String email = hotelEmail.getText().toString().trim();
                    final String userId = reference.push().getKey();

                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Hotel Added", Toast.LENGTH_SHORT).show();

                    imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String image = uri.toString();
                            com.example.add_hotel.User newUser = new com.example.add_hotel.User(name, location, hotelCharge, email, phone, userId, image);
                            reference.child(userId).setValue(newUser);
                            startActivity(new Intent(getApplicationContext(), AllHotels.class));
                        }
                    });
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please select hotel image", Toast.LENGTH_SHORT).show();
        }
    }
}
