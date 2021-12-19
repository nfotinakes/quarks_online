package com.foto.nick.quarksonline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Title: Item.java
 * Author: Nicholas Fotinakes
 * Abstract: This POJO represents a item to store in database
 * Date: 12-10-2021
 */
@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    // Fields related to the item
    private String itemName;
    private double price;
    private int inventory;
    private String itemDescription;

    // The image associated with item, default unless specified
    private int image;

    // Construct new item
    public Item(String itemName, double price, int inventory, String itemDescription, int image) {
        this.itemName = itemName;
        this.price = price;
        this.inventory = inventory;
        this.itemDescription = itemDescription;
        this.image = image;
    }

    // Standard getters and setters
    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Item: " + itemName + "\n" +
                "Price: " + price + " \n" +
                "Description: " + itemDescription + "\n" +
                "Qty: " + inventory + "\n" +
                "ItemCode: " + itemId;
    }
}
