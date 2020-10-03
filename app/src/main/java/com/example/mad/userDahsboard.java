package com.example.mad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class userDahsboard extends AppCompatActivity {

    private Toolbar toolbar;
    private MaterialButton dltRte;
    private MaterialButton edit;
    RelativeLayout layout;
    ListView myListView;
    DatabaseReference reference;
    FirebaseListAdapter adapter;

    ArrayList<String> myArrayList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dahsboard);

        toolbar = findViewById(R.id.toolabr);
        setSupportActionBar(toolbar);


        edit = findViewById(R.id.edit);
        reference = FirebaseDatabase.getInstance().getReference().child("route");

        myListView = findViewById(R.id.ListView);
        Query query = FirebaseDatabase.getInstance().getReference().child("route");
        FirebaseListOptions<Route> options = new FirebaseListOptions.Builder<Route>().setLayout(R.layout.row).setQuery(query,Route.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView title = v.findViewById(R.id.routemainname);
                TextView second = v.findViewById(R.id.routesecondname);
                final Route route = (Route) model;
                title.setText(route.getLoc1Title());
                second.setText(route.getLoc12Title());
                dltRte = v.findViewById(R.id.dltRoute);

                dltRte.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                  MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(userDahsboard.this);
                  alertDialogBuilder.setTitle("DELETING PREVIOULST SAVED ROUTE FOM THE DATABASE");
                  alertDialogBuilder.setIcon(R.drawable.dltrte);
                  alertDialogBuilder.setMessage("Do you want to delete " + route.getLoc1Title() + " route from the entire database. Once you tap on the 'YES' changes can not be revert");
                  alertDialogBuilder.setPositiveButton("YES I WANT TO DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("route");
                        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.hasChild(route.getRouteId())){
                                    reference = FirebaseDatabase.getInstance().getReference().child("route").child(route.getRouteId());
                                    reference.removeValue();

                                }
                                else{
                                    Toast.makeText(userDahsboard.this,"no sourse",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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
            }
        };
        myListView.setAdapter(adapter);
//        reference = FirebaseDatabase.getInstance().getReference().child("route");

//        myArrayList.add("a");
//        myArrayList.add("b");
//        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,myArrayList);
//        myListView.setAdapter(myAdapter);
//
//        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(userDahsboard.this, myArrayList.get(position),Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                String title = snapshot.getValue(String.class);
//                myArrayList.add(title);
//                myAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });





//        layout = findViewById(R.id.routelayout);


//        MaterialCardView materialCardView1 = new MaterialCardView(userDahsboard.this);
//
//        materialCardView1.setLayoutParams(new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.MATCH_PARENT,
//                RelativeLayout.LayoutParams.MATCH_PARENT
//        ));
//
//        layout.addView(materialCardView1);



//        dltRte.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(userDahsboard.this);
//                alertDialogBuilder.setTitle("DELETING PREVIOULST SAVED ROUTE FOM THE DATABASE");
//                alertDialogBuilder.setIcon(R.drawable.dltrte);
//                alertDialogBuilder.setMessage("Do you want to delete this route from the entire database. Once you tap on the 'YES' changes can not be revert");
//                alertDialogBuilder.setPositiveButton("YES I WANT TO DELETE", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                alertDialogBuilder.show();
//            }
//        });
//
//        edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(userDahsboard.this,EditPage.class);
//                startActivity(intent);
//            }
//        });




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}