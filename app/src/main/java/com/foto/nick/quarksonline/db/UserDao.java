package com.foto.nick.quarksonline.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.foto.nick.quarksonline.Item;
import com.foto.nick.quarksonline.Order;
import com.foto.nick.quarksonline.User;

import java.util.List;

/**
 * Title: UserDao.java
 * Author: Nicholas Fotinakes
 * Abstract: The DAO interface for the database
 * Date: 12-1-2021
 */
@Dao
public interface UserDao {

    // Insert a user into database
    @Insert
    void insert(User user);

    // Update a user in the database
    @Update
    void update(User user);

    // Delete a user from the database
    @Delete
    void delete(User user);

    // Return a List of all users in database
    @Query("SELECT * FROM user_table")
    List<User> getAllUsers();

    // Return a user from the database by their userId value
    @Query("SELECT * FROM user_table WHERE userId = :userId")
    User getUserById(int userId);

    @Query("SELECT * FROM user_table WHERE username = :username")
    User getUserByUsername(String username);

    // New Stuff

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM item_table")
    List<Item> getAllItems();

    @Query("SELECT * FROM item_table WHERE itemName = :itemName")
    Item getItemByName(String itemName);


    // ORDERS
    @Insert
    void insert(Order order);

    @Update
    void update(Order order);

    @Delete
    void delete(Order order);

    @Query("SELECT * FROM order_table")
    List<Order> getAllOrders();

    @Query("SELECT * FROM order_table WHERE itemName = :itemName")
    Order getOrderByItemName(String itemName);



}
