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

public class Store_List extends ArrayAdapter<Store> {
    private Activity context;
    private List<Store> storeList;

    public Store_List(Activity context, List<Store> storeList){
        super(context, R.layout.store_list, storeList);
        this.context = context;
        this.storeList = storeList;

    }

    //retrieve details of all the stores from the database
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewStores = inflater.inflate(R.layout.store_list, null, true);

        TextView textName = (TextView) listViewStores.findViewById(R.id.storename);
        TextView textCategory = (TextView) listViewStores.findViewById(R.id.category);
        TextView textDesc = (TextView) listViewStores.findViewById(R.id.description);
        TextView textLocation = (TextView) listViewStores.findViewById(R.id.location);
        TextView textBranch = (TextView) listViewStores.findViewById(R.id.branch);

        Store store = storeList.get(position);

        textName.setText(store.getName());
        textCategory.setText(store.getCategory());
        textDesc.setText(store.getDescription());
        textLocation.setText(store.getLocation());
        textBranch.setText(store.getBranch());

        return listViewStores;

    }



}
