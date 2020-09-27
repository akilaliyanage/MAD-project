package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Cafe_MenuList extends AppCompatActivity {

    // ListView listViewMenu;
    //  List<Menu> menuList;

    TextView resName,displayName,displayPrice,displayDiscount,displayAmount;
    Button myRestaurant34,calculateAmount;

    // DatabaseReference databaseMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe__menu_list);

        myRestaurant34=(Button)findViewById(R.id.buttonAddRest4);
        calculateAmount=(Button)findViewById(R.id.buttonCalDiscount);

        displayName=findViewById(R.id.textViewNameDisplay);
        String menuName= getIntent().getExtras().getString("MenuName","");
        displayName.setText(menuName);

        displayPrice=findViewById(R.id.textViewPriceDisplay);
        String menuPrice= getIntent().getExtras().getString("MenuPrice","");
        displayPrice.setText(menuPrice);

        displayDiscount=findViewById(R.id.textViewDiscountDisplay);
        String menuDiscount= getIntent().getExtras().getString("MenuDiscount","");
        displayDiscount.setText(menuDiscount);

        displayAmount=findViewById(R.id.textViewAmountDisplay);



        resName=findViewById(R.id.textViewResName);

        //  Intent intent =getIntent();

        //newly added
        //    menuList=new ArrayList<>();

        //   String id = intent.getStringExtra(FourthActivity.CAFE_ID);
        //
        //   resName.setText(name);

        //  if (id != null) {
        //  databaseMenu = FirebaseDatabase.getInstance().getReference("menu").child(id);

        //   }

        //   menuList = new ArrayList<>();

        calculateAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(displayPrice.getText().toString().equals("")||displayDiscount.getText().toString().equals("")){
                    Toast toast1=Toast.makeText(Cafe_MenuList.this,"PLz enter values",Toast
                            .LENGTH_SHORT);
                    toast1.show();
                }
                else{
                    displayPrice.getText().toString();
                    displayDiscount.getText().toString();
                    double value1=Double.valueOf(displayPrice.getText().toString());
                    double value2=Double.valueOf(displayDiscount.getText().toString());
                    double resultValue=value1-((value1*value2)/100.0);
                    displayAmount.setText("price : "+value1 + "       discount : " +value2+ "\n\n\n" + "Amount " +  " = "+ String.valueOf(resultValue));
                }

            }
        });




        myRestaurant34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6= new Intent(Cafe_MenuList.this,Cafe_MyRestaurant.class);
                startActivity(intent6);
            }
        });


    }

/*
    @Override
    protected void onStart() {
        super.onStart();
        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               menuList.clear();

                for (DataSnapshot menuSnapShot : dataSnapshot.getChildren()){
                    Menu menu= menuSnapShot.getValue(Menu.class);
                    menuList.add(menu); //add to the list
                }

                MenuList menuListAdapter = new MenuList(ThirdActivity.this,menuList);
                listViewMenu.setAdapter(menuListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/
}