package com.example.mad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class userDahsboard extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialButton dltRte;
    private MaterialButton edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dahsboard);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);

        dltRte = findViewById(R.id.dltRoute);
        edit = findViewById(R.id.edit);

        dltRte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(userDahsboard.this);
                alertDialogBuilder.setTitle("DELETING PREVIOULST SAVED ROUTE FOM THE DATABASE");
                alertDialogBuilder.setIcon(R.drawable.dltrte);
                alertDialogBuilder.setMessage("Do you want to delete this route from the entire database. Once you tap on the 'YES' changes can not be revert");
                alertDialogBuilder.setPositiveButton("YES I WANT TO DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialogBuilder.show();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userDahsboard.this,EditPage.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}