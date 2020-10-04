package com.example.mad;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;

        import android.content.DialogInterface;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ListView;
        import android.widget.Spinner;
        import android.widget.Toast;

        import com.google.android.material.dialog.MaterialAlertDialogBuilder;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class Store_Details extends AppCompatActivity {

    ListView listViewStores;
    DatabaseReference databaseReference;
    List<Store> stores;
    Button btn;
    Spinner spinner;

    public static final String store_id = "store id";
    public static final String store_name = "name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store__details);

        btn = (Button)findViewById(R.id.addmore_stores);
        spinner = (Spinner) findViewById(R.id.s_category);
        listViewStores = (ListView)findViewById(R.id.list_stores);
        stores = new ArrayList<Store>();

        //directs user to add more stores
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Store_Details.this, Add_Store.class);
                startActivity(intent);
            }
        });

        //fetch the selected store's id
        listViewStores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Store store = stores.get(i);
                updateClick(store.getStore_id(), store.getName());
            }
        });
    }

    Intent intent = getIntent();

    //retrieve list of stores from the database
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Store");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stores.clear();
                for(DataSnapshot snapshotStore : dataSnapshot.getChildren()){
                    Store store = snapshotStore.getValue(Store.class);
                    stores.add(store);
                }
                Store_List storeList = new Store_List(Store_Details.this, stores);
                listViewStores.setAdapter(storeList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //dialog box to handle multiple operations
    public void updateClick(final String id, final String name){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View view = inflater.inflate(R.layout.update_storedata, null);
        builder.setView(view);

        final EditText editName = (EditText) view.findViewById(R.id.s_name);
        final Spinner editCat = (Spinner) view.findViewById(R.id.s_category);
        final EditText editDesc = (EditText) view.findViewById(R.id.s_desc);
        final EditText editLoc = (EditText) view.findViewById(R.id.s_location);
        final EditText editBranch = (EditText ) view.findViewById(R.id.s_branch);
        Button edit = (Button) view.findViewById(R.id.s_edit);
        Button delete = (Button) view.findViewById(R.id.s_del);
        Button addStoreItem = (Button) view.findViewById(R.id.s_addItem);

        builder.setTitle("Update " + name +" Store Details");
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //fetch data to update the store then update
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = editName.getText().toString().trim();
                final String cat = editCat.getSelectedItem().toString().trim();
                final String desc = editDesc.getText().toString().trim();
                final String loc = editLoc.getText().toString().trim();
                final String branch = editBranch.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    editName.setError("cannot be empty");
                }
                else if (TextUtils.isEmpty(desc)){
                    editDesc.setError("cannot be empty");
                }
                else if(TextUtils.isEmpty(loc)){
                    editLoc.setError("cannot be empty");
                }
                else {
                    final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Store_Details.this);
                    builder.setTitle("Update Details?");
                    builder.setMessage("Store data will be updated");
                    builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            updateStore(id, name, cat, desc, loc, branch);
                            alertDialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i){}
                    });
                    builder.show();
                }
            }
        });

        //fetch data to delete the store and delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Store_Details.this);
                builder.setTitle("Delete Store!?");
                builder.setMessage("This store will be deleted permanently");
                builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteStore(id);
                        alertDialog.dismiss();
                    }
                });
                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){}
                });
                builder.show();
            }
        });

        //add items to the selected store
        addStoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Store_Details.this, Add_Store_Items.class);
                intent.putExtra(store_id, id);
                intent.putExtra(store_name, name);
                startActivity(intent);
            }
        });
    }

    //update store
    public boolean updateStore(String id, String name, String cate, String desc, String loc, String branch){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Store").child(id);

        Store store = new Store(id, name, cate, desc, loc, branch);
        dbref.setValue(store);

        Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_SHORT).show();
        return true;
    }

    //delete store
    public void deleteStore(String id){
        DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Store").child(id);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Item").child(id);

        dbref.removeValue();
        databaseReference.removeValue();
        Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
    }
}