package com.example.add_hotel;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

class HotelDisplayViewHolder extends RecyclerView.ViewHolder {

    public ImageView hotelImage;
    public TextView hotelName;
    public TextView hotelLocation;
    public TextView hotelEmail;
    public CardView singleCardItem;

    public HotelDisplayViewHolder(@NonNull View itemView) {
        super(itemView);
        hotelImage = itemView.findViewById(R.id.single_hotel_image);
        hotelName = itemView.findViewById(R.id.single_item_hotel_name);
        hotelLocation = itemView.findViewById(R.id.single_item_hotel_location);
        hotelEmail = itemView.findViewById(R.id.single_item_hotel_email);
        singleCardItem = itemView.findViewById(R.id.single_card_item);
    }
}
