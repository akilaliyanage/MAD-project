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
    private Activity context;
    private List<CafeRestaurant> cafeList;

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

        TextView cafeName = (TextView)listViewItem.findViewById(R.id.textViewRestName);
        TextView cafeLocation = (TextView)listViewItem.findViewById(R.id.textViewLocation);
        TextView cafeCategory = (TextView)listViewItem.findViewById(R.id.textViewCategory);
        TextView cafeEmail = (TextView)listViewItem.findViewById(R.id.textViewEmailAddress);


        CafeRestaurant cafeRestaurant = cafeList.get(position);

        cafeName.setText(cafeRestaurant.getName());
        cafeLocation.setText(cafeRestaurant.getAddressLine1());
        cafeCategory.setText(cafeRestaurant.getCategory());
        cafeEmail.setText(cafeRestaurant.getEmail());

        return listViewItem;
    }

}
