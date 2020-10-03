package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    private int itemCount;
    //TextView count;
    Button addmoreItems;
    ExtendedFloatingActionButton count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__details);

        Intent intent = getIntent();
        String storeID = intent.getStringExtra(Add_Store_Items.storeID);
        databaseReference = FirebaseDatabase.getInstance().getReference("Item").child(storeID);
        getItemCount = FirebaseDatabase.getInstance().getReference().child("Item");


        items = new ArrayList<>();
        listViewItems = (ListView) findViewById(R.id.item_ListView);

        addmoreItems = (Button) findViewById(R.id.addmoreItems);

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
                                if(itemCount < 5) {
                                    Intent intent = new Intent(Item_Details.this, Add_Store.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "please delete items if you want to add more", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

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
}