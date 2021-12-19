package com.foto.nick.quarksonline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Title: Order.java
 * Author: Nicholas Fotinakes
 * Abstract: This POJO represents an order
 * Date: 12-1-2010
 */
@Entity(tableName = "order_table")
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int orderId;

    private int userId;         // The user associated with the order
    private String itemName;    // The item ordered


    // Constructor to create order
    public Order(int userId, String itemName) {
        this.userId = userId;
        this.itemName = itemName;
    }

    // getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public String toString() {
        return "Order #" + orderId + ": " + itemName;
    }
}
