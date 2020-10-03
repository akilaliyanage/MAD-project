package com.example.mad;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class userMain extends AppCompatActivity {

    private Toolbar toolbar;
    private Button createBtn;
    private static final String TAG = "createOne";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    private Button myRts;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);

        createBtn = findViewById(R.id.createNewBtn);
        myRts = findViewById(R.id.myRoutesbtn);
        mAuth = FirebaseAuth.getInstance();

        createBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userMain.this,createOne.class);
                startActivity(intent);
            }
        });

        myRts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myroutes = new Intent(userMain.this,userDahsboard.class);
                startActivity(myroutes);
            }
        });

        isServicesOK();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main,menu);
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
            Intent intent = new Intent(userMain.this,MainActivity.class);
            startActivity(intent);
        }
    }


    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(userMain.this);

        if(available == ConnectionResult.SUCCESS){
            //everything is ok
            Log.d(TAG, "isServicesOK: Google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but can resolved
            Log.d(TAG, "isServicesOK: an error occured, but can fixed");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(userMain.this,available,ERROR_DIALOG_REQUEST);
            dialog.show();
        }
        else{
            Toast.makeText(this,"cant make map request",Toast.LENGTH_LONG).show();
        }
        return false;
    }
}