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
    TextView textViewCafeName;
    EditText menuName,menuPrice,menuDiscount;

    DatabaseReference databaseMenu;

    //newly added
    ListView listViewMenuItems;
    List<Menu> menuItems;

    Button addToMenu,calculate,myRestaurant24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe_second);

        addToMenu=(Button)findViewById(R.id.buttonAddMenu);
        //  home=(Button)findViewById(R.id.buttonHomeMenuList4);
        myRestaurant24=(Button)findViewById(R.id.buttonMyRest);
        calculate=(Button)findViewById(R.id.buttonCalculate);
        textViewCafeName=(TextView)findViewById(R.id.textViewCafeName);
        menuName=(EditText)findViewById(R.id.editTextMenuName);
        menuPrice=(EditText)findViewById(R.id.editTextMenuPrice);
        menuDiscount=(EditText)findViewById(R.id.editTextDiscount);
        listViewMenuItems = (ListView)findViewById(R.id.listViewMenu);


        Intent intent =getIntent();

        //newly added
        menuItems=new ArrayList<>();

        final String cafe_id = intent.getStringExtra(Cafe_MyRestaurant.CAFE_ID);
        String name = intent.getStringExtra(Cafe_MyRestaurant.CAFE_NAME);

        textViewCafeName.setText(name);

        if (cafe_id != null) {
            databaseMenu = FirebaseDatabase.getInstance().getReference("menu").child(cafe_id);
        }

        addToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMenu();
                // Intent intent2= new Intent(SecondActivity.this,ThirdActivity.class);

                // startActivity(intent2);

            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CafeSecond.this,Cafe_MenuList.class);
                intent.putExtra("MenuName",menuName.getText().toString());
                intent.putExtra("MenuPrice",menuPrice.getText().toString());
                intent.putExtra("MenuDiscount",menuDiscount.getText().toString());

                startActivity(intent);
            }
        });


        listViewMenuItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Menu menu = menuItems.get(i);
                showMenuUpdateDialog(cafe_id,menu.getMenuID(),menu.getMenuName());
                return false;
            }
        });

    /*    home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3= new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent3);
            }
        });*/

        myRestaurant24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent4= new Intent(CafeSecond.this,Cafe_MyRestaurant.class);
                startActivity(intent4);

            }
        });

    }

    //newly added

    @Override
    protected void onStart() {
        super.onStart();

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

        dialogBuilder.setTitle("Updating Menu  "+ menuName );

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnMenuUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menuName = editTextMenuName.getText().toString().trim();
                Double menuPrice = Double.parseDouble(editTextMenuPrice.getText().toString());
                Double menuDiscount = Double.parseDouble(editTextMenuDiscount.getText().toString());

                if(TextUtils.isEmpty(menuName)){
                    editTextMenuName.setError("Name required");
                    return;
                }
                updateMenu(cafe_id,menuId,menuName,menuPrice,menuDiscount);
                alertDialog.dismiss();

            }
        });

        btnMenuDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder dialogDelMenu= new MaterialAlertDialogBuilder(CafeSecond.this);
                dialogDelMenu.setTitle("Delete Confirmation");
                dialogDelMenu.setMessage("Do You Want To Delete This Menu?");
                dialogDelMenu.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteMenu(cafe_id,menuId);
                        alertDialog.dismiss();
                    }
                });
                dialogDelMenu.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                dialogDelMenu.show();
            }
        });

    }

    private boolean updateMenu(String cafeId,String id,String name,Double price,Double discount){
        DatabaseReference databaseMenu=FirebaseDatabase.getInstance().getReference("menu").child(cafeId).child(id);

        Menu menu = new Menu(id,name,price,discount);
        databaseMenu.setValue(menu);

        Toast.makeText(getApplicationContext(),"Menu Updated Successfully",Toast.LENGTH_SHORT).show();
        return true;

    }

    private void deleteMenu(String cafeId,String menuId){

        DatabaseReference drMenu = FirebaseDatabase.getInstance().getReference("menu").child(cafeId).child(menuId);

        drMenu.removeValue();

        Toast.makeText(this,"Menu is deleted",Toast.LENGTH_SHORT).show();
    }

    private void saveMenu(){

        String menu_name=menuName.getText().toString().trim();
        Double price=Double.parseDouble(menuPrice.getText().toString());
        Double discount=Double.parseDouble(menuDiscount.getText().toString());

        try{
            if(TextUtils.isEmpty(menu_name)){
                Toast.makeText(this,"Please add the name",Toast.LENGTH_SHORT).show();
            }
            //else if(TextUtils.isEmpty(price)){
            //  Toast.makeText(this,"Please add the address line 1",Toast.LENGTH_SHORT).show();
            //}else if (TextUtils.isEmpty(discount)){
            //     Toast.makeText(this,"Please add the address line 2",Toast.LENGTH_SHORT).show();
            // }
            else {
                String menu_id = databaseMenu.push().getKey();

                Menu menu = new Menu(menu_id,menu_name,price,discount);

                if (menu_id != null) {
                    databaseMenu.child(menu_id).setValue(menu);
                }

                Toast.makeText(getApplicationContext(),"Menu Added Successfully",Toast.LENGTH_SHORT).show();
                clearControls();
            }
        }catch(NumberFormatException e){
            Toast.makeText(getApplicationContext(),"Invalid price",Toast.LENGTH_SHORT).show();
        }
    }

    private void clearControls(){
        menuName.setText("");
        menuPrice.setText("");
        menuDiscount.setText("");

    }
}