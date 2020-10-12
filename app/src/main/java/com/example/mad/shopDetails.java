package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopDetails extends AppCompatActivity {

    private TextView shopnamefetch,shoplocationfetch,shopbranchfetch,shopcatagoryfetch,shopdetfetch;
    private String shop;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        shopnamefetch = findViewById(R.id.namefetch);
        shoplocationfetch = findViewById(R.id.locfetch);
        shopbranchfetch = findViewById(R.id.brfetch);
        shopdetfetch = findViewById(R.id.shopdetfetch);
        shopcatagoryfetch = findViewById(R.id.shopcatagoryfetch);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        shop = getIntent().getStringExtra("shopname");
        reference = FirebaseDatabase.getInstance().getReference().child("Store").child(shop.toString());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                shopnamefetch.setText(snapshot.child("name").getValue().toString());
                shoplocationfetch.setText(snapshot.child("location").getValue().toString());
                shopbranchfetch.setText(snapshot.child("branch").getValue().toString());
                shopcatagoryfetch.setText(snapshot.child("category").getValue().toString());
                shopdetfetch.setText(snapshot.child("description").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(shopDetails.this,"Can not fetch data",Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.signout:
                signout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signout() {
        mAuth.signOut();
        if(mAuth.getCurrentUser() == null){
            Intent intent = new Intent(shopDetails.this,MainActivity.class);
            startActivity(intent);
        }
    }
}