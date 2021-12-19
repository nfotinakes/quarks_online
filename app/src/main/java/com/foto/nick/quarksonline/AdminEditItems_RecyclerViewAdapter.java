package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Title: AdminEditItems_RecyclerViewAdapter.java
 * Author: Nicholas Fotinakes
 * Abstract: This is the adapter for the AdminEditItems recycler view
 * Date: 12-10-2021
 */
public class AdminEditItems_RecyclerViewAdapter extends RecyclerView.Adapter<AdminEditItems_RecyclerViewAdapter.MyViewHolder> {

    // Context and list for recycler view
    private Context context;
    private List<Item> itemList;

    // Constructor to initialize fields
    public AdminEditItems_RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    // This method will inflate based on the layout recycler_view_item
    @NonNull
    @Override
    public AdminEditItems_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
        return new AdminEditItems_RecyclerViewAdapter.MyViewHolder(view);
    }

    // This will bind the values to the cards in recycler view based on itemList
    @Override
    public void onBindViewHolder(@NonNull AdminEditItems_RecyclerViewAdapter.MyViewHolder holder, int position) {
        Item currentItem = itemList.get(position);

        holder.itemName.setText(currentItem.getItemName());
        holder.itemDescription.setText(currentItem.getItemDescription());
        holder.itemInventory.setText("Qty: " + Integer.toString(currentItem.getInventory()));
        holder.itemId.setText("Item Id: " + Integer.toString(currentItem.getItemId()));
        holder.itemImage.setImageResource(currentItem.getImage());
        holder.itemPrice.setText("$" + Double.toString(currentItem.getPrice()));

        // If admin clicks delete button, alert dialog displays
        holder.itemDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getRootView().getContext());
                alertBuilder.setMessage("Delete Item?\n");

                // If confirm clicked, remove item from database and update
                alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.userDao.delete(currentItem);
                        itemList.remove(currentItem);
                        notifyItemRemoved(holder.getAdapterPosition());
                        Toast toast = Toast.makeText(view.getContext(), "Item Deleted", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });
                // Otherwise do nothing
                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                });

                // Show alert
                AlertDialog goodAlert = alertBuilder.create();
                goodAlert.show();
            }
        });

        // Set listener for modify button, have a custom dialog appear
        holder.itemModifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create builder and view
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View v = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.layout_edit_item_dialog, null);
                // Set number picker
                NumberPicker numberPicker = v.findViewById(R.id.modifyItem_QuantityPicker);
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(20);

                // Wire up editTexts
                EditText editItemName = v.findViewById(R.id.modifyItem_itemName);
                EditText editItemDescription = v.findViewById(R.id.modifyItem_itemDescription);
                EditText editItemPrice = v.findViewById(R.id.modifyItem_itemPrice);

                // Set text to the item selected
                editItemName.setText(currentItem.getItemName());
                editItemDescription.setText(currentItem.getItemDescription());
                editItemPrice.setText(Double.toString(currentItem.getPrice()));
                numberPicker.setValue(currentItem.getInventory());


                // Create the builder
                builder.setView(view)
                        .setTitle("Modify Item")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        // if Confirm we update the item
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Initialize fields
                                boolean nameExists = false;
                                String itemName = editItemName.getText().toString();
                                String itemDescription = editItemDescription.getText().toString();
                                double itemPrice = 0;
                                int itemQuantity = numberPicker.getValue();

                                // Try to parse the price
                                try {
                                    itemPrice = Double.parseDouble(editItemPrice.getText().toString());
                                } catch (NumberFormatException e) {
                                    System.out.println("Can't Parse Double");
                                }

                                // Check for item name already existing
                                for (Item item : itemList) {
                                    if (currentItem.getItemId() != item.getItemId() && editItemName.getText().toString().equals(item.getItemName())) {
                                        nameExists = true;
                                    }
                                }

                                // Display proper toasts if itemm exists, or blank fields otherwise update item
                                if (nameExists) {
                                    Toast toast = Toast.makeText(view.getContext(), "An item with this name exists already, sorry :(", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else if (itemName.equals("") || itemDescription.equals("") || itemPrice == 0) {
                                    Toast toast = Toast.makeText(view.getContext(), "Required field blank or price set to zero, try again", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                } else {
                                    currentItem.setItemName(itemName);
                                    currentItem.setItemDescription(itemDescription);
                                    currentItem.setPrice(itemPrice);
                                    currentItem.setInventory(itemQuantity);
                                    MainActivity.userDao.update(currentItem);
                                    notifyDataSetChanged();
                                    Toast toast = Toast.makeText(view.getContext(), "Item Modified", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        });
                builder.setView(v);
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView itemImage;
        public TextView itemName, itemDescription, itemInventory, itemId, itemPrice;
        public Button itemDeleteButton, itemModifyButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.admin_editItem_cardImage);
            itemName = itemView.findViewById(R.id.admin_editItem_cardItemName);
            itemDescription = itemView.findViewById(R.id.admin_editItem_cardItemDescription);
            itemInventory = itemView.findViewById(R.id.admin_editItem_cardItemQuantity);
            itemId = itemView.findViewById(R.id.admin_editItem_cardItemId);
            itemDeleteButton = itemView.findViewById(R.id.admin_editItem_cardDeleteItemButton);
            itemModifyButton = itemView.findViewById(R.id.admin_editItem_cardModifyItemButton);
            itemPrice = itemView.findViewById(R.id.admin_editItem_cardItemPrice);
        }
    }
}
