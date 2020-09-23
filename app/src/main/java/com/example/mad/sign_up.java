package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {

    private TextInputEditText email;
    private TextInputEditText userName;
    private TextInputEditText password;
    private TextInputEditText confirmPassword;
    private Button signUp;
    private String chkPassword,chkPasswordConfirm,finEmail;
    DatabaseReference dRef;
    private CoordinatorLayout coordinatorLayout;
    NewUser user;
    private FirebaseAuth mAuth;
    private final String TAG = "message";
    private ProgressBar progressBar;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser);
        }
    }

    private void updateUI(FirebaseUser currentUser) {
//        Intent intent = new Intent(sign_up.this,userMain.class);
//        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.newUserEmail);
        userName = findViewById(R.id.newUserUsername);
        password = findViewById(R.id.newUserPassword);
        confirmPassword = findViewById(R.id.newUserConfirm);
        signUp = findViewById(R.id.createAcc);
        progressBar = findViewById(R.id.progressBar);
        user = new NewUser();
        mAuth = FirebaseAuth.getInstance();


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //fetching the values from the text fields
                chkPassword = password.getText().toString();
                chkPasswordConfirm = confirmPassword.getText().toString();
                finEmail = email.getText().toString();

                dRef = FirebaseDatabase.getInstance().getReference().child("Student");



               if(passwordCheck(chkPassword,chkPasswordConfirm)){
                    if(TextUtils.isEmpty(email.getText().toString()) && TextUtils.isEmpty(userName.getText().toString())){
                        Toast toast = Toast.makeText(getApplicationContext(),"One og your input fields are empty. Please fill out all the fielda and try again later 😊",Toast.LENGTH_LONG);
                        toast.show();
                    }else{

                        progressBar.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(finEmail,chkPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(sign_up.this);
                                    alertDialogBuilder.setTitle("Congratulations!!!");
                                    alertDialogBuilder.setMessage("You have successfully signed up for the HIKE 😊");
                                    alertDialogBuilder.setPositiveButton("Go to home", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(sign_up.this,userMain.class);
                                            startActivity(intent);
                                        }
                                    });

                                    alertDialogBuilder.show();
                                }
                                if (!task.isSuccessful()) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Log.e(TAG, "onComplete: Failed=" + task.getException().getMessage());
                                    MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(sign_up.this);
                                    alertDialogBuilder.setTitle("OOPZ!!!");
                                    alertDialogBuilder.setMessage(task.getException().getMessage());


                                    alertDialogBuilder.show();
                                }
                            }
                        });



//                        user.setEmaillAddress(email.getText().toString().trim());
//                        user.setUserName(userName.getText().toString().trim());
//                        user.setPassword(password.getText().toString().trim());
//                        dRef.child(email.getText().toString()).setValue(user);
//

                    }
               }else{
                   password.setError("Password Mismatch");
               }

            }
        });

    }

    private boolean passwordCheck(String chkPassword, String chkPasswordConfirm){
        Boolean isEqual = false;
        if(chkPassword.equals(chkPasswordConfirm)){
            isEqual = true;
        }else{
            isEqual = false;
        }
        return isEqual;
    }
}