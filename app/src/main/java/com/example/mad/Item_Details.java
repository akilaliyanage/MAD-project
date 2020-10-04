package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Item_Details extends AppCompatActivity {

    List<Item> items;
    DatabaseReference databaseReference, getItemCount;
    ListView listViewItems;
    private int itemCount = 0;
    Button addmoreItems;
    ExtendedFloatingActionButton count;
    String storeID;
    String status;
    public static final String store_ID = "Store id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__details);

        Intent intent = getIntent();
        storeID = intent.getStringExtra(Add_Store_Items.storeID);

        databaseReference = FirebaseDatabase.getInstance().getReference("Item").child(storeID);
        getItemCount = FirebaseDatabase.getInstance().getReference().child("Item");

        items = new ArrayList<>();
        listViewItems = (ListView) findViewById(R.id.item_ListView);

        addmoreItems = (Button) findViewById(R.id.addmoreItems);

        //get the number of items in each store
        count = (ExtendedFloatingActionButton)findViewById(R.id.count);
        getItemCount.child(storeID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    itemCount = (int) snapshot.getChildrenCount();
                    count.setText("Number of Items " + Integer.toString(itemCount));

                    addmoreItems.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //add more items to the store
                                if(checkItemCount(itemCount)) {
                                    Intent intent = new Intent(Item_Details.this, Add_Store_Items.class);
                                    intent.putExtra(store_ID, storeID);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Please delete items if you want to add more", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //get the id of the item user updates
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item = items.get(i);
                updateItemClick(item.getItemID(), item.getItemName());
            }
        });

    }

    //check if the items are below 50 (user is allowed to add up to 50 items only)
    public boolean checkItemCount(int count){
        if(count < 5){ return  true; }
        else{ return false; }
    }

    //retrieve a list of items in each store
    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    items.add(item);
                }

                Item_List item_list = new Item_List(Item_Details.this, items);
                listViewItems.setAdapter(item_list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //fetch data to update item details
    public void updateItemClick(final String itemID, final String itemName){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.update_str_item_data, null);
        builder.setView(view);

        final EditText iName = (EditText)view.findViewById(R.id.update_i_name);
        final EditText iPrice = (EditText) view.findViewById(R.id.update_i_price);
        final Spinner iStyle = (Spinner) view.findViewById(R.id.update_i_cate);
        final EditText iDesc = (EditText) view.findViewById(R.id.update_i_desc);
        final CheckBox iOffer = (CheckBox) view.findViewById(R.id.update_i_offers);
        final Button update = (Button) view.findViewById(R.id.upadate_item);
        Button delete = (Button) view.findViewById(R.id.delete_item);

        builder.setTitle("Edit " + itemName + " Details");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String itemName = iName.getText().toString().trim();
                final Float itemPrice = Float.parseFloat(iPrice.getText().toString().trim());
                final String itemCat = iStyle.getSelectedItem().toString();
                final String itemDesc = iDesc.getText().toString().trim();
                final String itemOffer;

                if(iOffer.isChecked()){
                    itemOffer = "Available";
                }else{
                    itemOffer =  "Unavailable";
                }

                if(TextUtils.isEmpty(itemName)){
                    iName.setError("Cannot be empty");
                } else if (TextUtils.isEmpty(itemPrice.toString())) {
                    iPrice.setError("Cannot be empty");
                }else if(TextUtils.isEmpty(itemDesc)){
                    iDesc.setError("Cannot be empty");
                }else{
                    final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Item_Details.this);
                    builder.setTitle("Update Details?");
                    builder.setMessage("This item will be deleted permanently");
                    builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updateItem(itemID, itemName, itemPrice, itemCat, itemDesc, itemOffer);
                            alertDialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){}
                    });
                    builder.show();

                }
            }
        });

        //fetch data to delete item details
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Item_Details.this);
                builder.setTitle("Delete Store!?");
                builder.setMessage("thank god");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(itemID);
                        alertDialog.dismiss();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){}
                });
                builder.show();
            }
        });

    }

    //update item data
    public boolean updateItem(String id, String name, Float price, String cate, String desc, String offer){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Item").child(storeID).child(id);

        Item item = new Item(id, name, price, cate, desc, offer);
        dbref.setValue(item);

        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
        return true;
    }

    //delete item data
    public void deleteItem(String id){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference("Item").child(storeID).child(id);

        dbref.removeValue();
        Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
    }
}