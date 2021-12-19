package com.foto.nick.quarksonline.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.foto.nick.quarksonline.Item;
import com.foto.nick.quarksonline.Order;
import com.foto.nick.quarksonline.User;

/**
 * Title: AppDatabase.java
 * Author: Nicholas Fotinakes
 * Abstract: This is the abstract class for our database that defines our entities
 * Date: 12-1-2021
 */
@Database(entities = {User.class, Item.class, Order.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
}
