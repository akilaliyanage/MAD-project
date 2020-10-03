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

public class Item_List extends ArrayAdapter<Item> {
    private Activity context;
    private List<Item> itemList;

    public Item_List(Activity context, List<Item> itemList){
        super(context, R.layout.items_list, itemList);
        this.context = context;
        this.itemList = itemList;

    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewStoreItems = inflater.inflate(R.layout.items_list, null, true);

        TextView itemName = (TextView) listViewStoreItems.findViewById(R.id.item_name);
        TextView itemPrice = (TextView) listViewStoreItems.findViewById(R.id.item_price);
        TextView itemDesc = (TextView) listViewStoreItems.findViewById(R.id.item_desc);
        TextView itemOffers = (TextView) listViewStoreItems.findViewById(R.id.item_offers);
        TextView itemStyle = (TextView) listViewStoreItems.findViewById(R.id.item_style);

        Item item = itemList.get(position);

        itemName.setText(item.getItemName());
        itemPrice.setText(String.valueOf(item.getItemPrize()));
        itemDesc.setText(item.getItemDescription());
        itemOffers.setText(item.getItemOffers());
        itemStyle.setText(item.getItemStyle());

        return listViewStoreItems;

    }

}
