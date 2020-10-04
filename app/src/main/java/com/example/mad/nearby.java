package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class nearby extends AppCompatActivity {

    ListView myListView2;
    DatabaseReference shopref;
    FirebaseListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);

        myListView2 = findViewById(R.id.ListView2);
        shopref = FirebaseDatabase.getInstance().getReference().child("Store");

        Query query = FirebaseDatabase.getInstance().getReference().child("Store");
        FirebaseListOptions<Store> options = new FirebaseListOptions.Builder<Store>().setLayout(R.layout.shops).setQuery(query,Store.class).build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Object model, int position) {
                TextView shopname = v.findViewById(R.id.shopname);

                final Store store = (Store) model;
                shopname.setText(store.getName());
//                dltRte = v.findViewById(R.id.dltRoute);
//                edit = v.findViewById(R.id.edit);

                // Toast.makeText(userDahsboard.this,route.getRouteId(),Toast.LENGTH_LONG).show();

//                edit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(userDahsboard.this,routeDetails.class);
//                        intent.putExtra("routeId",route.getRouteId());
//                        intent.putExtra("loc1long",route.getLoc1long());
//                        intent.putExtra("loc1lat",route.getLoc1lat());
//                        intent.putExtra("loc2long",route.getLoc2long());
//                        intent.putExtra("loc2lat",route.getLoc2lat());
//                        startActivity(intent);
//                    }
//                });
//
//                dltRte.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(userDahsboard.this);
//                        alertDialogBuilder.setTitle("DELETING PREVIOULST SAVED ROUTE FOM THE DATABASE");
//                        alertDialogBuilder.setIcon(R.drawable.dltrte);
//                        alertDialogBuilder.setMessage("Do you want to delete " + route.getLoc1Title() + " route from the entire database. Once you tap on the 'YES' changes can not be revert");
//                        alertDialogBuilder.setPositiveButton("YES I WANT TO DELETE", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("route");
//                                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        if(snapshot.hasChild(route.getRouteId())){
//                                            reference = FirebaseDatabase.getInstance().getReference().child("route").child(route.getRouteId());
//                                            reference.removeValue();
//
//                                        }
//                                        else{
//                                            Toast.makeText(userDahsboard.this,"no sourse",Toast.LENGTH_LONG).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//
//                                    }
//                                });
//                            }
//                        });
//                        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        });
//                        alertDialogBuilder.show();
//                    }
            //});
            }
        };
        myListView2.setAdapter(adapter);
    }
}