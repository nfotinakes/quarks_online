package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Title: ShopActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the shop activity for users
 * Date: 12-10-2021
 */
public class ShopActivity extends AppCompatActivity {

    private List<Item> itemList;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        // Wire up back button
        backButton = findViewById(R.id.recyclerview_shop_backButton);

        //itemList = MainActivity.itemList;
        itemList = MainActivity.userDao.getAllItems();

        // Wire up recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_shop);
        Item_RecyclerViewAdapter adapter = new Item_RecyclerViewAdapter(this, itemList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Listener for when an item card is clicked
        adapter.setOnItemClickListener(new Item_RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Start dialog for item clicked on
                int inventory = itemList.get(position).getInventory();
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ShopActivity.this);
                alertBuilder.setMessage("Confirm Order?\n");

                // For when confirm button clicked
                alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Check for inventory
                        if (inventory == 0) {
                            Toast toast = Toast.makeText(ShopActivity.this, "Out of Stock, Sorry :(", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        // Otherwise handle order, update inventory and display
                        } else {
                            Order order = new Order(LandingPage.loggedInUser.getUserId(), itemList.get(position).getItemName());
                            MainActivity.userDao.insert(order);
                            itemList.get(position).setInventory(inventory - 1);
                            MainActivity.userDao.update(itemList.get(position));
                            adapter.notifyDataSetChanged();
                            Toast toast = Toast.makeText(ShopActivity.this, "Order Successful: " + itemList.get(position).getItemName(), Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }
                });

                // For cancel or click off dialog do nothing
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

                AlertDialog goodAlert = alertBuilder.create();
                goodAlert.show();

            }
        });

        // Button to return to Landing Page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    // Factory pattern for ShopActivity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ShopActivity.class);
        return intent;
    }
}