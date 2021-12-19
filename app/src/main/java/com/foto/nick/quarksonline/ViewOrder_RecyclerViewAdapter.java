package com.foto.nick.quarksonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Title: ViewOrder_RecyclerViewAdapter.java
 * Author: Nicholas Fotinakes
 * Abstract: This is the adapter for the ViewOrder recycler view
 * Date: 12-10-2021
 */
public class ViewOrder_RecyclerViewAdapter extends RecyclerView.Adapter<ViewOrder_RecyclerViewAdapter.MyViewHolder> {

    // Context and list for recycler view
    private Context context;
    private List<Order> orderList;
    private OnItemClickListener itemListener;

    // Constructor
    public ViewOrder_RecyclerViewAdapter(Context context, List<Order> orders) {
        this.context = context;
        orderList = orders;
    }

    // Inflate view with the view orders layout
    @NonNull
    @Override
    public ViewOrder_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_orders, parent, false);
        return new ViewOrder_RecyclerViewAdapter.MyViewHolder(view, itemListener);
    }

    // Bind an order to current card
    @Override
    public void onBindViewHolder(@NonNull ViewOrder_RecyclerViewAdapter.MyViewHolder holder, int position) {
        Order currentOrder = orderList.get(position);
        holder.viewOrder.setText(currentOrder.toString());
    }

    // Get order list count
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Wire up card with text view for order
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView viewOrder;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            viewOrder = itemView.findViewById(R.id.viewOrder_textView);

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

    // Interface to set item click listener
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        itemListener = listener;
    }
}
