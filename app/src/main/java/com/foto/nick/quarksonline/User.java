package com.foto.nick.quarksonline;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: User.java
 * Author: Nicholas Fotinakes
 * Abstract: This POJO represents a User and holds relevant User info
 * Date: 12-1-2021
 */
@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int userId;        // Primary key

    // User fields
    private String username;
    private String password;
    private boolean isAdmin;

    /**
     * Constructor for a basic User
     * @param username value for username
     * @param password value for users password
     * @param isAdmin to set if User is admin or not
     */
    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    /**
     * The getUserId gets the value of a users id
     * @return the value stored in userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * The setUserId sets the userId field
     * @param userId the value to set for userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * The getUsername method returns the username
     * @return the value stored in username
     */
    public String getUsername() {
        return username;
    }

    /**
     * the setUsername method sets the username field
     * @param username the value to set for username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * The getPassword returns the users password
     * @return the value stored in password
     */
    public String getPassword() {
        return password;
    }

    /**
     * The setPassword method sets the password field
     * @param password the value to set for password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The isAdmin method returns whether a user is an admin or not
     * @return true or false if user is an admin
     */
    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * The setAdmin method sets the isAdmin field
     * @param admin the true or false value for isAdmin
     */
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
