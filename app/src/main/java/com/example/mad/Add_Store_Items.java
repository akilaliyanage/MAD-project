package com.example.mad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Store_Items extends AppCompatActivity {

    EditText itemName;
    EditText itemPrice;
    Spinner itemStyle;
    CheckBox checkOffers;
    EditText itemDesc;

    Button addItem, loadDetails;
    ImageButton addImage;
    public  static final int CHOOSE_AN_IMAGE = 1;
    public static final String storeID = "ID";
    TextView storeName;
    private Uri imageURI;
    String id;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__store__items);

        Intent intent = getIntent();

        itemName = (EditText) findViewById(R.id.i_name);
        itemPrice = (EditText) findViewById(R.id.i_price);
        itemDesc = (EditText) findViewById(R.id.i_description);
        itemStyle = (Spinner) findViewById(R.id.style_list);
        checkOffers = (CheckBox) findViewById(R.id.checkOffers);

        storeName = (TextView) findViewById(R.id.i_store_name);
        addItem = (Button) findViewById(R.id.i_addItem);
        addImage = (ImageButton) findViewById(R.id.i_image);

        id = intent.getStringExtra(Store_Details.store_id);
        String name = intent.getStringExtra(Store_Details.store_name);

        databaseReference = FirebaseDatabase.getInstance().getReference("Item").child(id);

        storeName.setText(name);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageFile();
            }
        });

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
        loadDetails = (Button) findViewById(R.id.goto_item_list);
        loadDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMyItemDetails();
            }
        });
    }

    public void goToMyItemDetails(){
        Intent intent = new Intent(Add_Store_Items.this, Item_Details.class);
        intent.putExtra(storeID, id);
        startActivity(intent);
    }

    //create items for the selected store
    public void createItem(){
    String item_name = itemName.getText().toString().trim();
    Float item_price = Float.parseFloat(itemPrice.getText().toString().trim());
    String item_description = itemDesc.getText().toString().trim();
    String item_style = itemStyle.getSelectedItem().toString();
    String item_offers;
    String itemID = databaseReference.push().getKey();

    if(checkOffers.isChecked()){
        item_offers = "Available";
    }else{
        item_offers =  "Unavailable";
    }

    Item item = new Item(itemID,item_name, item_price, item_description, item_style, item_offers);
    databaseReference.child(itemID).setValue(item);
    Toast.makeText(getApplicationContext(), "Item added succesfully", Toast.LENGTH_SHORT).show();

    goToMyItemDetails();
}

//validation for user inputs
    public void validation(){
        if(itemName.getText().toString().length()==0){
            itemName.setError("Please enter item name");
        }else if(itemPrice.getText().toString().length()==0){
            itemPrice.setError("Please enter price");
        }else if(itemDesc.getText().toString().length()==0){
            itemDesc.setError("Please enter description");
        }else{
            createItem();
        }
    }


    public void openImageFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, CHOOSE_AN_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_AN_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null){
            imageURI = data.getData();
        }
    }

    public void uploadImage(){

    }
}