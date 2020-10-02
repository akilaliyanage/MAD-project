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
public class MenuList extends ArrayAdapter<Menu>{
    //Menu list view
    private Activity context;
    private List<Menu> menuList;

    public MenuList(Activity context,List<Menu> menuList){
        super(context,R.layout.menu_list,menuList);
        this.context=context;
        this.menuList=menuList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.menu_list,null,true);

        //declare text views
        TextView txtMenuName = (TextView)listViewItem.findViewById(R.id.textViewMenuName);
        TextView txtMenuPrice = (TextView)listViewItem.findViewById(R.id.textViewPrice);
        TextView txtMenuDiscount = (TextView)listViewItem.findViewById(R.id.textViewDiscount);

        //menu object from Menu class
        Menu menu = menuList.get(position);

        //get text and assign them to the text views
        txtMenuName.setText(menu.getMenuName());
        txtMenuPrice.setText(String.valueOf(menu.getMenuPrice()));
        txtMenuDiscount.setText(String.valueOf(menu.getMenuDiscount()));

        return listViewItem;
    }

}
