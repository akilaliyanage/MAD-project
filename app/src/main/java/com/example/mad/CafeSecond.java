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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CafeSecond extends AppCompatActivity {
    //define variables
    TextView textViewCafeName;
    EditText menuName,menuPrice,menuDiscount;
    //database reference
    DatabaseReference databaseMenu;

    //newly added
    ListView listViewMenuItems;
    List<Menu> menuItems;

    //button
    Button addToMenu,calculate,myRestaurant24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_second);

        //deifine variables with ids
        addToMenu=(Button)findViewById(R.id.buttonAddMenu);
        myRestaurant24=(Button)findViewById(R.id.buttonMyRest);
        calculate=(Button)findViewById(R.id.buttonCalculate);
        textViewCafeName=(TextView)findViewById(R.id.textViewCafeName);
        menuName=(EditText)findViewById(R.id.editTextMenuName);
        menuPrice=(EditText)findViewById(R.id.editTextMenuPrice);
        menuDiscount=(EditText)findViewById(R.id.editTextDiscount);
        listViewMenuItems = (ListView)findViewById(R.id.listViewMenu);

        //get the passed intent from Cafe_MyRestaurant.java
        Intent intent =getIntent();

        menuItems=new ArrayList<>();

        //assign the passed intent to variables
        final String cafe_id = intent.getStringExtra(Cafe_MyRestaurant.CAFE_ID);
        String name = intent.getStringExtra(Cafe_MyRestaurant.CAFE_NAME);

        //set the text view with the cafe name
        textViewCafeName.setText(name);

        //check if the passed cafe id is null
        if (cafe_id != null) {
            databaseMenu = FirebaseDatabase.getInstance().getReference("menu").child(cafe_id);
        }

        //add to menu function
        addToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the menu name field is empty
                if(menuName.getText().toString().isEmpty()){
                    menuName.setError("Please add a menu name");
                }
                //check if the menu price length is 0
                else if(menuPrice.getText().length()==0){
                    menuPrice.setError("Enter a price");
                }
                //check if the discount field is null
                else if(menuDiscount.getText().toString().length()==0){
                    menuDiscount.setError("Add a discount");
                }
                //if all above conditions are false then save meu function is called
                else{
                    saveMenu();
                }

            }
        });

        //calculate button function
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the price of the menu is empty
                if(menuPrice.getText().toString().isEmpty()){
                    //display an error
                    menuPrice.setError("Enter a price");
                }
                //check whether the discount field is empty
                else if(menuDiscount.getText().toString().isEmpty()){
                    //display an error
                    menuDiscount.setError("Enter a discount");
                }
                //if the above conditions are false then pass the price and discount to the calculation page
                else{
                    Intent intent=new Intent(CafeSecond.this,Cafe_MenuList.class);
                    //pass values to the calculation page
                    intent.putExtra("MenuName",menuName.getText().toString());
                    intent.putExtra("MenuPrice",menuPrice.getText().toString());
                    intent.putExtra("MenuDiscount",menuDiscount.getText().toString());

                    startActivity(intent);
                }
            }
        });

        //list of menu - when pressing it for a long time will pop up the update dialog
        listViewMenuItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Menu menu = menuItems.get(i);
                showMenuUpdateDialog(cafe_id,menu.getMenuID(),menu.getMenuName());
                return false;
            }
        });

        //Button My Restaurant will redirect to the retrieval cafe list activity
        myRestaurant24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4= new Intent(CafeSecond.this,Cafe_MyRestaurant.class);
                startActivity(intent4);

            }
        });

    }

    //menu retrieval life cycle
    @Override
    protected void onStart() {
        super.onStart();

        //data retrieval
        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                menuItems.clear();
                for (DataSnapshot menuSnapshots : dataSnapshot.getChildren()){
                    Menu menu = menuSnapshots.getValue(Menu.class);
                    menuItems.add(menu);
                }

                MenuList menuListAdapter = new MenuList(CafeSecond.this,menuItems);
                listViewMenuItems.setAdapter(menuListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //menu update dialog
    private void showMenuUpdateDialog(final String cafe_id, final String menuId, final String menuName){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.update_menu_dialog,null);
        dialogBuilder.setView(dialogView);

        final EditText editTextMenuName = (EditText)dialogView.findViewById(R.id.editTextUpdateMenuName);
        final EditText editTextMenuPrice = (EditText)dialogView.findViewById(R.id.editTextUpdatePrice);
        final EditText editTextMenuDiscount = (EditText)dialogView.findViewById(R.id.editTextUpdateDiscount);
        final Button btnMenuUpdate = (Button)dialogView.findViewById(R.id.buttonMenuUpdate);
        final Button btnMenuDelete = (Button)dialogView.findViewById(R.id.buttonMenuDelete);

        //set the title to the text view
        dialogBuilder.setTitle("Updating Menu  "+ menuName );

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        //update menu button function
        btnMenuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menuName = editTextMenuName.getText().toString().trim();
                //convert the string values to Double
                Double menuPrice = Double.parseDouble(editTextMenuPrice.getText().toString());
                Double menuDiscount = Double.parseDouble(editTextMenuDiscount.getText().toString());

                //check if the menu name is empty in update dialog
                if(TextUtils.isEmpty(menuName)){
                    editTextMenuName.setError("Name required");
                    return;
                }
                //pass parameters to the update menu function
                updateMenu(cafe_id,menuId,menuName,menuPrice,menuDiscount);
                //make the dialog box dismiss
                alertDialog.dismiss();

            }
        });

        //delete button function
        btnMenuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dialog to display the confirmation message to delete menu
                MaterialAlertDialogBuilder dialogDelMenu= new MaterialAlertDialogBuilder(CafeSecond.this);
                dialogDelMenu.setTitle("Delete Confirmation");
                dialogDelMenu.setMessage("Do You Want To Delete This Menu?");
                dialogDelMenu.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //to delete the relevant menu of the cafe need to pass the menu id
                        deleteMenu(cafe_id,menuId);
                        alertDialog.dismiss();
                    }
                });
                dialogDelMenu.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                //to display the deletion dialog
                dialogDelMenu.show();
            }
        });

    }

    //update function
    private boolean updateMenu(String cafeId,String id,String name,Double price,Double discount){
        //refer the path menu in the database and then under the cafe id delete the menu record
        DatabaseReference databaseMenu=FirebaseDatabase.getInstance().getReference("menu").child(cafeId).child(id);

        Menu menu = new Menu(id,name,price,discount);
        databaseMenu.setValue(menu);

        //display a toast message for successful update
        Toast.makeText(getApplicationContext(),"Menu Updated Successfully",Toast.LENGTH_SHORT).show();
        return true;

    }

    //delete function
    private void deleteMenu(String cafeId,String menuId){
        //a reference to the path menu -> cafeId -> menuId
        DatabaseReference drMenu = FirebaseDatabase.getInstance().getReference("menu").child(cafeId).child(menuId);
        drMenu.removeValue();
        //delete the menu
        Toast.makeText(this,"Menu is deleted",Toast.LENGTH_SHORT).show();
    }

    //save menu function
    private void saveMenu(){

        String menu_name=menuName.getText().toString().trim();
        Double price=Double.parseDouble(menuPrice.getText().toString());
        Double discount=Double.parseDouble(menuDiscount.getText().toString());

        try{
            //check if the menu name field is empty
            if(TextUtils.isEmpty(menu_name)){
                Toast.makeText(this,"Please add the name",Toast.LENGTH_SHORT).show();
            }
            //if not empty then save the menu
            else {
                //with a unique id
                String menu_id = databaseMenu.push().getKey();
                //create a object from the Menu class
                Menu menu = new Menu(menu_id,menu_name,price,discount);
                //check if the menu id is null
                if (menu_id != null) {
                    databaseMenu.child(menu_id).setValue(menu);
                }
                //display a toast message to inform successful save
                Toast.makeText(getApplicationContext(),"Menu Added Successfully",Toast.LENGTH_SHORT).show();
                //clear the form after the saving of details
                clearControls();
            }
        }catch(NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Invalid price",Toast.LENGTH_SHORT).show();
        }
    }

    //function to clear the menu form
    private void clearControls(){
        menuName.setText("");
        menuPrice.setText("");
        menuDiscount.setText("");

    }
}