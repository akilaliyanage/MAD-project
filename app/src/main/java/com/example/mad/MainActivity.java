package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //private Button snackbar_button;
    private  Button signInBtn,signUpBtn;
    private FirebaseAuth mAuth;
    private TextInputEditText email,password;
    private String loginEmail,loginPassword;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            GotoUserMain(currentUser);
        }
    }

    private void GotoUserMain(FirebaseUser currentUser) {
        Intent intent = new Intent(MainActivity.this,userMain.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInBtn = findViewById(R.id.signinBtn);
        signUpBtn = findViewById(R.id.signUpBtn);
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserMain();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,sign_up.class);
                startActivity(intent);
            }
        });
    }

    private void openUserMain() {
        loginEmail = email.getText().toString().trim();
        loginPassword = password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(loginEmail, loginPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.print(task.getException());

                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {

        Intent intent = new Intent(MainActivity.this,userMain.class);
        startActivity(intent);
    }

}