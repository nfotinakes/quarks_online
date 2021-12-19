package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Title: AdminEditItemsActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the Admin edit items activity
 * Date: 12-10-2021
 */
public class AdminEditItemsActivity extends AppCompatActivity {

    // Declare fields for activity
    private List<Item> itemList;
    private Button createItemButton;
    private ImageView backButton;
    private ImageView homeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_items);

        // Wire up display and get items
        itemList = MainActivity.userDao.getAllItems();
        createItemButton = findViewById(R.id.admin_editItem_createItemButton);
        backButton = findViewById(R.id.admin_editItem_backButton);
        homeButton = findViewById(R.id.admin_editItem_homeButton);

        // Wire up recycler view
        RecyclerView recyclerView = findViewById(R.id.admin_editItem_recyclerView);
        AdminEditItems_RecyclerViewAdapter adapter = new AdminEditItems_RecyclerViewAdapter(this, itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Listener for back button to return to AdminTools
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminToolsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Listener for creating a new item
        createItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start a alert dialog and inflater
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminEditItemsActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.layout_edit_item_dialog, null);
                // Wire up number picker for price
                NumberPicker numberPicker = v.findViewById(R.id.modifyItem_QuantityPicker);
                numberPicker.setMinValue(0);
                numberPicker.setMaxValue(20);

                // create alert dialog
                builder.setView(view)
                        .setTitle("Create Item")

                        // If cancel do nothing
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })

                        // Handle confirm button
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean nameExists = false;


                                // Wire up edit texts
                                EditText editItemName = v.findViewById(R.id.modifyItem_itemName);
                                EditText editItemDescription = v.findViewById(R.id.modifyItem_itemDescription);
                                EditText editItemPrice = v.findViewById(R.id.modifyItem_itemPrice);
                                // Get values from edit texts
                                String itemName = editItemName.getText().toString();
                                String itemDescription = editItemDescription.getText().toString();
                                double itemPrice = 0;
                                // Try to parse price
                                try {
                                    itemPrice = Double.parseDouble(editItemPrice.getText().toString());
                                } catch (NumberFormatException e) {
                                    System.out.println("this was an error");
                                }

                                // Check if item name exists
                                for (Item item : itemList) {
                                    if (editItemName.getText().toString().equals(item.getItemName())) {
                                        nameExists = true;
                                    }
                                }


                                // Handle if name exists, if fields are blank, if all good modify the item
                                if (nameExists) {
                                    Toast toast = Toast.makeText(view.getContext(), "An item with that name already exists, sorry :(", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                } else if (itemName.equals("") || itemDescription.equals("") || itemPrice == 0) {
                                    Toast toast = Toast.makeText(view.getContext(), "Required Field blank or price set to zero, try again", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                } else {

                                    int itemQuantity = numberPicker.getValue();

                                    Item item = new Item(itemName, itemPrice, itemQuantity, itemDescription, R.drawable.ferengi_logo_gold);
                                    MainActivity.userDao.insert(item);
                                    Item itemToAdd = MainActivity.userDao.getItemByName(itemName);
                                    itemList.add(itemToAdd);
                                    adapter.notifyDataSetChanged();

                                    Toast toast = Toast.makeText(AdminEditItemsActivity.this, "Item Added To Shop", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();
                                }
                            }
                        });
                builder.setView(v);
                builder.show();

            }
        });

        // Listener for home button to return to landing page
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    // Factory pattern to switch to AdminEditItemsActivity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminEditItemsActivity.class);
        return intent;
    }
}