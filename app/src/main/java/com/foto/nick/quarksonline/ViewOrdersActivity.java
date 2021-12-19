package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: ViewOrdersActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the user view order activity to view and cancel orders
 * Date: 12-10-2021
 */
public class ViewOrdersActivity extends AppCompatActivity {

    // Declare fields for view orders activity
    private List<Order> orderList;
    private ImageView backButton;
    private TextView emptyOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);

        // Wire up display
        backButton = findViewById(R.id.recyclerview_orderPage_backButton);
        emptyOrder = findViewById(R.id.orderPage_noOrders_textView);

        // Initialize orderList and fill with orders associated with userId
        orderList = new ArrayList<>();
        for(Order o : MainActivity.userDao.getAllOrders()) {
            if(o.getUserId() == LandingPage.loggedInUser.getUserId()) {
                orderList.add(o);
            }
        }
        // This will check if user has any orders, if not a text will display
        checkIfEmpty();

        // Wire up recycler view
        RecyclerView recyclerView = findViewById(R.id.recyclerview_user_viewOrders);
        ViewOrder_RecyclerViewAdapter adapter = new ViewOrder_RecyclerViewAdapter(this, orderList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Listener for clicking on an order
        adapter.setOnItemClickListener(new ViewOrder_RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                String name = orderList.get(position).getItemName();
                Item item = MainActivity.userDao.getItemByName(name);
                int inventory = item.getInventory();

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ViewOrdersActivity.this);
                alertBuilder.setMessage("Cancel This Order?\n");

                // Handle canceling an prder
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // update inventory and database
                        item.setInventory(inventory + 1);
                        MainActivity.userDao.update(item);
                        MainActivity.userDao.delete(orderList.get(position));
                        orderList.remove(position);
                        adapter.notifyDataSetChanged();
                        checkIfEmpty();
                        Toast toast = Toast.makeText(ViewOrdersActivity.this, "Order Canceled\nItem Returned To Stock", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                });

                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
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

        // Listener for back button to return to landing page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    // Check if user has any orders to set text
    public void checkIfEmpty() {
        if(orderList.size() > 0 ) {
            emptyOrder.setVisibility(View.INVISIBLE);
        } else {
            emptyOrder.setVisibility(View.VISIBLE);
        }
    }

    // factory pattern for this activity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, ViewOrdersActivity.class);
        return intent;
    }
}