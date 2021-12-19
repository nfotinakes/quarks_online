package com.foto.nick.quarksonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

/**
 * Title: Item_RecyclerViewAdapter.java
 * Author: Nicholas Fotinakes
 * Abstract: This is the adapter for the Shop recycler view to display items in the shop. Followed a Coding in Flow
 * to create this recycler with a interface for a listener on item click.
 * Date: 12-10-2021
 */
public class Item_RecyclerViewAdapter extends RecyclerView.Adapter<Item_RecyclerViewAdapter.MyViewHolder> {
    // Declare fields for recycler view
    private Context context;
    private List<Item> items = new ArrayList<Item>();
    private OnItemClickListener itemListener;

    // Constructor
    public Item_RecyclerViewAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    // Inflate based on our item recycler_view_row
    @NonNull
    @Override
    public Item_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new Item_RecyclerViewAdapter.MyViewHolder(view, itemListener);

    }

    // holder to set current item information
    @Override
    public void onBindViewHolder(@NonNull Item_RecyclerViewAdapter.MyViewHolder holder, int position) {
        Item currentItem = items.get(position);

        holder.itemName.setText(currentItem.getItemName());
        holder.itemDescription.setText(currentItem.getItemDescription());
        holder.inventory.setText("Qty: " + Integer.toString(currentItem.getInventory()));
        holder.id.setText("ID: " + Integer.toString(currentItem.getItemId()));
        holder.imageView.setImageResource(currentItem.getImage());
        holder.itemPrice.setText("Latinum: $" + Double.toString(currentItem.getPrice()));

    }

    // size of item list
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Wire up items for recycler view
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView itemName, itemDescription, inventory, id, itemPrice;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.shop_imageView);
            itemName = itemView.findViewById(R.id.shop_itemName_textView);
            itemDescription = itemView.findViewById(R.id.shop_itemDescription_textView);
            inventory = itemView.findViewById(R.id.shop_itemQuantity_textview);
            id = itemView.findViewById(R.id.shop_itemId_textView);
            itemPrice = itemView.findViewById(R.id.shop_itemPrice);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }
}
