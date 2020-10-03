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

    //declare final variables
    public static final String CAFE_NAME="cafename";
    public static final String CAFE_ID="cafeid";

    //define the List view
    ListView cafeList;
    List<CafeRestaurant> cafeRestaurantList;

    //Button objects
    Button view,addRest;
    //database reference
    DatabaseReference databaseCafe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe__my_restaurant);

         //get a database instance and a reference of it
        databaseCafe = FirebaseDatabase.getInstance().getReference("cafes");

        //declare cafeList variable
        cafeList=(ListView)findViewById(R.id.ListViewCafe);
        //define an array
        cafeRestaurantList = new ArrayList<>();
        //Button objects
        view=(Button)findViewById(R.id.buttonViewMore);
        addRest=(Button)findViewById(R.id.buttonAddRest4);

        //view button click function
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent7= new Intent(Cafe_MyRestaurant.this,CafeMain.class);
                startActivity(intent7);
            }
        });

        //add restaurant button function redirect to the form
        addRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent8= new Intent(Cafe_MyRestaurant.this,CafeFirst.class);
                startActivity(intent8);
            }
        });

        //Item click
        cafeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CafeRestaurant cafes = cafeRestaurantList.get(i); //get position

                //redirect to the add menu page
                Intent intent = new Intent(Cafe_MyRestaurant.this,CafeSecond.class);
                //pass the cafe ID and cafe name to the next page
                intent.putExtra(CAFE_ID,cafes.getCafeID());
                intent.putExtra(CAFE_NAME,cafes.getName());

                startActivity(intent);

            }
        });

        //Clicking the item for a long time will pop up the update dialog
        cafeList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                CafeRestaurant cafeRestaurant = cafeRestaurantList.get(i);
                showUpdateDialog(cafeRestaurant.getCafeID(),cafeRestaurant.getName());

                return false;
            }
        });
    }

    //Retrieval of data
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

    //update dialog function
    private void showUpdateDialog(final String cafeId, final String cafeName){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_cafe_dialog,null);

        dialogBuilder.setView(dialogView);

        //declare the update form plain texts and buttons with the ids
        final EditText editTextName = (EditText)dialogView.findViewById(R.id.editTextUpdateResName);
        final EditText editTextAdd1 = (EditText)dialogView.findViewById(R.id.editTextUpdateAddL1);
        final EditText editTextAdd2 = (EditText)dialogView.findViewById(R.id.editTextUpdateAddL2);
        final EditText editTextCity = (EditText)dialogView.findViewById(R.id.editTextUpdateCity);
        final Spinner spinner2 = (Spinner)dialogView.findViewById(R.id.spinnerUpdateCategory);
        final EditText editTextPhone = (EditText)dialogView.findViewById(R.id.editTextUpdateContact);
        final EditText editTextEmail = (EditText)dialogView.findViewById(R.id.editTextUpdateEmail);
        final Button buttonUpdate = (Button)dialogView.findViewById(R.id.buttonCafeUpdate);
        final Button buttonDelete = (Button)dialogView.findViewById(R.id.buttonCafeDelete);

        //text at the top of the update dialog
        dialogBuilder.setTitle("Updating Cafe   " + cafeName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        //Update button function
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

                //set validations for the update form
                if(TextUtils.isEmpty(name)){
                    editTextName.setError("Name required");
                    return;
                }
                updateCafe(cafeId,name,add1,add2,city,category,phone,email);
                alertDialog.dismiss();
            }
        });

        //delete button function
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // a dialog box display the confirmation to delete
                MaterialAlertDialogBuilder dialogDel=new MaterialAlertDialogBuilder(Cafe_MyRestaurant.this);

                dialogDel.setTitle("Confirm Delete");
                dialogDel.setMessage("Do You Want To Delete?");
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
                //to display the dialog box
                dialogDel.show();
            }
        });

    }

    //delete function passing the cafe ID
    private void deleteCafe(String cafeId){
        //delete the cafe ID and the menu of the particular cafe
        DatabaseReference drCafe = FirebaseDatabase.getInstance().getReference("cafes").child(cafeId);
        DatabaseReference drMenu = FirebaseDatabase.getInstance().getReference("menu").child(cafeId);
        //delete a particular cafe record
        drCafe.removeValue();
        //delete the menu related to the cafe
        drMenu.removeValue();
        //display a toast message of successful deletion of record
        Toast.makeText(this,"Cafe is deleted",Toast.LENGTH_SHORT).show();
    }

    //update function
    private boolean updateCafe(String id,String name,String add1,String add2,String city,String category,Integer phone,String email){
        //to update the relevant cafe need to get the required cafe id
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("cafes").child(id);

        CafeRestaurant cafeRestaurant = new CafeRestaurant(id,name,add1,add2,city,category,phone,email);
        databaseReference.setValue(cafeRestaurant);
        //Toast to display successful update of the record
        Toast.makeText(getApplicationContext(),"Cafe Updated Successfully",Toast.LENGTH_SHORT).show();
        return true;

    }
}