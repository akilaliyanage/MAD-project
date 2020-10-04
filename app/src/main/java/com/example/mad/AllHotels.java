package com.example.mad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.add_hotel.User;
import com.example.mad.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class AllHotels extends AppCompatActivity {

    private RecyclerView itemRecyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference = database.getReference().child("Hotels");
    private FirebaseRecyclerOptions<com.example.add_hotel.User> option;
    private FirebaseRecyclerAdapter<com.example.add_hotel.User, HotelDisplayViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hotels);

        itemRecyclerView = findViewById(R.id.display_items_recycler_view);
        itemRecyclerView.setHasFixedSize(true);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        option = new FirebaseRecyclerOptions.Builder<com.example.add_hotel.User>().setQuery(reference, com.example.add_hotel.User.class).build();
        adapter = new FirebaseRecyclerAdapter<com.example.add_hotel.User, HotelDisplayViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull HotelDisplayViewHolder holder, int position, @NonNull final User model) {
                holder.hotelName.setText(model.getHotelName());
                holder.hotelLocation.setText(model.getLocation());
                holder.hotelEmail.setText(model.getEmail());
                Picasso.get().load(model.getImageUrl()).into(holder.hotelImage);

                holder.singleCardItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UpdateHotel.class);
                        intent.putExtra("hotelName", model.getHotelName());
                        intent.putExtra("hotelLocation", model.getLocation());
                        intent.putExtra("hotelEmail", model.getEmail());
                        intent.putExtra("hotelImage", model.getImageUrl());
                        intent.putExtra("hotelCharge", model.getServiceCharge());
                        intent.putExtra("hotelNumber", model.getPhoneNumber());
                        intent.putExtra("hotelId", model.getUserId());
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public HotelDisplayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hotel_item, parent, false);
                return new HotelDisplayViewHolder(v);
            }
        };

        adapter.startListening();
        itemRecyclerView.setAdapter(adapter);
    }
}
