package com.example.mad;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CafeList extends ArrayAdapter<CafeRestaurant>{
    //for the retrieval of cafe list
    private Activity context;
    private List<CafeRestaurant> cafeList;

    //constructor
    public CafeList(Activity context, List<CafeRestaurant> cafeList){
        super(context,R.layout.cafe_list,cafeList);
        this.context=context;
        this.cafeList=cafeList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.cafe_list,null,true);

        //declaration of ids
        TextView cafeName = (TextView)listViewItem.findViewById(R.id.textViewRestName);
        TextView cafeLocation = (TextView)listViewItem.findViewById(R.id.textViewLocation);
        TextView cafeCategory = (TextView)listViewItem.findViewById(R.id.textViewCategory);
        TextView cafeEmail = (TextView)listViewItem.findViewById(R.id.textViewEmailAddress);
        TextView cafePhone = (TextView)listViewItem.findViewById(R.id.textViewPhone);

        //define an object of CafeRestaurant class and get the position
        CafeRestaurant cafeRestaurant = cafeList.get(position);

        //for the list, for the cafe name text set the values
        cafeName.setText(cafeRestaurant.getName());
        //concatenate the address1, address2 ,city to the text view
        cafeLocation.setText(cafeRestaurant.getAddressLine1()+ " " +cafeRestaurant.getAddressLine2()+" "+cafeRestaurant.getCity());
        //set category to the text view
        cafeCategory.setText(cafeRestaurant.getCategory());
        //set the email
        cafeEmail.setText(cafeRestaurant.getEmail());
        //set the phone number to te list
        cafePhone.setText(String.valueOf(cafeRestaurant.getContact()));

        return listViewItem;
    }

}
