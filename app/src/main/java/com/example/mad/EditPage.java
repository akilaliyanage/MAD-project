package com.example.mad;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPage extends AppCompatActivity {
    private CoordinatorLayout snackLay;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private String routeId;
    private DatabaseReference reference;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent();
        routeId = intent.getStringExtra("routeId");

        reference = FirebaseDatabase.getInstance().getReference().child("route").child(routeId);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);

        snackLay = findViewById(R.id.editSnak);
        mAuth = FirebaseAuth.getInstance();

       Snackbar snackbar =  Snackbar.make(snackLay,"Please select the desired location to edit.",Snackbar.LENGTH_INDEFINITE);
       snackbar.show();
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
            Intent intent = new Intent(EditPage.this,MainActivity.class);
            startActivity(intent);
        }
    }


}