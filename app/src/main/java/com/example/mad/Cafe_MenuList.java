package com.example.mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Cafe_MenuList extends AppCompatActivity {

    //define variables globally
    TextView resName,displayName,displayPrice,displayDiscount,displayAmount;
    Button myRestaurant34,calculateAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafe__menu_list);

        //define variables
        myRestaurant34=(Button)findViewById(R.id.buttonAddRest4);
        calculateAmount=(Button)findViewById(R.id.buttonCalDiscount);

        displayName=findViewById(R.id.textViewNameDisplay);
        //get the passed intent menu name
        String menuName= getIntent().getExtras().getString("MenuName","");
        displayName.setText(menuName);

        displayPrice=findViewById(R.id.textViewPriceDisplay);
        //get the passed menu price
        String menuPrice= getIntent().getExtras().getString("MenuPrice","");
        displayPrice.setText(menuPrice);

        displayDiscount=findViewById(R.id.textViewDiscountDisplay);
        //get the passed discount
        String menuDiscount= getIntent().getExtras().getString("MenuDiscount","");
        displayDiscount.setText(menuDiscount);

        //text view to display the calculated amount
        displayAmount=findViewById(R.id.textViewAmountDisplay);

        //text view to display "enjoy the day" sentence
        resName=findViewById(R.id.textViewResName);

        //calculate amount button
        calculateAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if the price and discount is empty
                if(displayPrice.getText().toString().equals("")||displayDiscount.getText().toString().equals("")){
                    //display a toast message
                    Toast toast1=Toast.makeText(Cafe_MenuList.this,"PLease enter values",Toast
                            .LENGTH_SHORT);
                    toast1.show();
                }
                //if the above condition is false
                else{
                    displayPrice.getText().toString();
                    displayDiscount.getText().toString();
                    //define variables to assign the price and discount
                    double value1=Double.valueOf(displayPrice.getText().toString());
                    double value2=Double.valueOf(displayDiscount.getText().toString());
                    //calculation for the discount - call the calculateAmount function and pass the parameters
                    double resultValue=calculateAmount(value1,value2);
                    //display the calculated amount in a text view
                    displayAmount.setText("price : Rs. "+value1 + "       discount : " +value2+ "\n\n\n" + "Amount " +  " = Rs. "+ String.valueOf(resultValue));
                }

            }
        });

        //My Restaurant button function redirect to the Cafe List page
        myRestaurant34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent6= new Intent(Cafe_MenuList.this,Cafe_MyRestaurant.class);
                startActivity(intent6);
            }
        });


    }

    public Double calculateAmount(Double price, Double discount) {
        double resultValue=price-((price*discount)/100.0);
        return resultValue;
    }
}