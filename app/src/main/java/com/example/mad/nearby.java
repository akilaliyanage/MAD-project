package com.example.mad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class nearby extends AppCompatActivity {

   FirebaseListAdapter adapter;
    ListView listView;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        mAuth = FirebaseAuth.getInstance();
        Query query = FirebaseDatabase.getInstance().getReference().child("Store");
        listView = findViewById(R.id.ListView2);
        FirebaseListOptions<Store> options = new FirebaseListOptions.Builder<Store>().setLayout(R.layout.layoutlist).setQuery(query,Store.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView storename = v.findViewById(R.id.image_name);
                Store store = (Store) model;
                storename.setText(store.getName());
            }
        };

        listView.setAdapter(adapter);

        };
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
            Intent intent = new Intent(nearby.this,MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }



}