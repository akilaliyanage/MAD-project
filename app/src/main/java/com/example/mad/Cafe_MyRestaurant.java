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

public class Cafe_MyRestaurant extends AppCompatActivity {

    public static final String CAFE_NAME="cafename";
    public static final String CAFE_ID="cafeid";

    ListView cafeList;
    List<CafeRestaurant> cafeRestaurantList;

    Button view,addRest,viewMenu;
    DatabaseReference databaseCafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe__my_restaurant);

        databaseCafe = FirebaseDatabase.getInstance().getReference("cafes");
        cafeList=(ListView)findViewById(R.id.ListViewCafe);
        cafeRestaurantList = new ArrayList<>();

        view=(Button)findViewById(R.id.buttonViewMore);
        addRest=(Button)findViewById(R.id.buttonAddRest4);
        //viewMenu=(Button)findViewById(R.id.buttonViewMenu);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7= new Intent(Cafe_MyRestaurant.this,CafeMain.class);
                startActivity(intent7);


            }
        });



        addRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8= new Intent(Cafe_MyRestaurant.this,CafeFirst.class);
                startActivity(intent8);
            }
        });

        cafeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CafeRestaurant cafes = cafeRestaurantList.get(i); //get position

                Intent intent = new Intent(Cafe_MyRestaurant.this,CafeSecond.class);
                intent.putExtra(CAFE_ID,cafes.getCafeID());
                intent.putExtra(CAFE_NAME,cafes.getName());

                startActivity(intent);

            }
        });
        // viewMenu.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //  public void onClick(View view) {
        //  Intent intent9= new Intent(FourthActivity.this,ThirdActivity.class);
        //     startActivity(intent9);
        //  }
        //  });

        cafeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                CafeRestaurant cafeRestaurant = cafeRestaurantList.get(i);
                showUpdateDialog(cafeRestaurant.getCafeID(),cafeRestaurant.getName());

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseCafe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cafeRestaurantList.clear();

                for (DataSnapshot cafeSnapshot : dataSnapshot.getChildren()){
                    CafeRestaurant cafeRestaurant = cafeSnapshot.getValue(CafeRestaurant.class);
                    cafeRestaurantList.add(cafeRestaurant);
                }
                CafeList adapter = new CafeList(Cafe_MyRestaurant.this,cafeRestaurantList);
                cafeList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String cafeId, final String cafeName){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_cafe_dialog,null);

        dialogBuilder.setView(dialogView);

        final EditText editTextName = (EditText)dialogView.findViewById(R.id.editTextUpdateResName);
        final EditText editTextAdd1 = (EditText)dialogView.findViewById(R.id.editTextUpdateAddL1);
        final EditText editTextAdd2 = (EditText)dialogView.findViewById(R.id.editTextUpdateAddL2);
        final EditText editTextCity = (EditText)dialogView.findViewById(R.id.editTextUpdateCity);
        final Spinner spinner2 = (Spinner)dialogView.findViewById(R.id.spinnerUpdateCategory);
        final EditText editTextPhone = (EditText)dialogView.findViewById(R.id.editTextUpdateContact);
        final EditText editTextEmail = (EditText)dialogView.findViewById(R.id.editTextUpdateEmail);
        final Button buttonUpdate = (Button)dialogView.findViewById(R.id.buttonCafeUpdate);
        final Button buttonDelete = (Button)dialogView.findViewById(R.id.buttonCafeDelete);


        dialogBuilder.setTitle("Updating Cafe   " + cafeName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String add1 = editTextAdd1.getText().toString().trim();
                String add2 = editTextAdd2.getText().toString().trim();
                String city = editTextCity.getText().toString().trim();
                String category = spinner2.getSelectedItem().toString();
                Integer phone = Integer.parseInt(editTextPhone.getText().toString());
                String email  =editTextEmail.getText().toString();

                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name required");
                    return;
                }
                updateCafe(cafeId,name,add1,add2,city,category,phone,email);
                alertDialog.dismiss();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder dialogDel=new MaterialAlertDialogBuilder(Cafe_MyRestaurant.this);

                dialogDel.setTitle("Confirm Delete");
                dialogDel.setMessage("Bye Bye World!");
                dialogDel.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteCafe(cafeId);
                        alertDialog.dismiss();
                    }
                });
                dialogDel.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialogDel.show();
            }
        });

    }

    private void deleteCafe(String cafeId){
        DatabaseReference drCafe = FirebaseDatabase.getInstance().getReference("cafes").child(cafeId);
        DatabaseReference drMenu = FirebaseDatabase.getInstance().getReference("menu").child(cafeId);

        drCafe.removeValue();
        drMenu.removeValue();

        Toast.makeText(this,"Cafe is deleted",Toast.LENGTH_SHORT).show();
    }

    private boolean updateCafe(String id,String name,String add1,String add2,String city,String category,Integer phone,String email){
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("cafes").child(id);

        CafeRestaurant cafeRestaurant = new CafeRestaurant(id,name,add1,add2,city,category,phone,email);
        databaseReference.setValue(cafeRestaurant);

        Toast.makeText(getApplicationContext(),"Cafe Updated Successfully",Toast.LENGTH_SHORT).show();
        return true;

    }
}