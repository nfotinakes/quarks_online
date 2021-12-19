package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Title: AdminEditUsersActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the Edit Users activity for an admin
 * Date: 12-10-2021
 */
public class AdminEditUsersActivity extends AppCompatActivity implements CreateUserDialog.CreateUserDialogListener {

    // Declare fields for Edit Users Activity
    private List<User> userList;
    private Button createUserButton;
    private AdminEditUsers_RecyclerViewAdapter EditUserAdapter;
    private ImageView backButton;
    private ImageView homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_users);

        // Wire up display
        createUserButton = findViewById(R.id.adminTools_createUserButton);
        userList = MainActivity.userDao.getAllUsers();
        backButton = findViewById(R.id.adminEditUsers_backButton);
        homeButton = findViewById(R.id.admin_editUsers_homeButton);

        // Wire up recycler
        RecyclerView recyclerView = findViewById(R.id.adminEditUsers_recyclerView);
        EditUserAdapter = new AdminEditUsers_RecyclerViewAdapter(this, userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(EditUserAdapter);

        // Use CreateUserDialog class for create user button
        createUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        // Back button to return to Admin Tools
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminToolsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Listener to return to landing page
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    // Method used to create user dialog
    public void openDialog() {
        CreateUserDialog createUserDialog = new CreateUserDialog();
        createUserDialog.show(getSupportFragmentManager(), "create user dialog");

    }

    // Used to apply text for user dialog
    @Override
    public void applyText(String username, String password, boolean admin) {
        User user = new User(username, password, admin);
        boolean nameExists = false;

        // Check if username exists
        for(User u : userList) {
            if(user.getUsername().equals(u.getUsername())) {
                nameExists = true;
            }
        }

        // If null, handle blank fields or if name exists, otherwise create new user
        if(username.equals("") || password.equals("")) {
            Toast toast = Toast.makeText(AdminEditUsersActivity.this, "Required Field is blank, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else if(nameExists) {
            Toast toast = Toast.makeText(AdminEditUsersActivity.this, "Username exists, try again", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            MainActivity.userDao.insert(user);
            User userToAdd = MainActivity.userDao.getUserByUsername(user.getUsername());
            userList.add(userToAdd);
            Toast toast = Toast.makeText(AdminEditUsersActivity.this, "User Created", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            EditUserAdapter.notifyDataSetChanged();
        }
    }

    // factory method to for this activity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminEditUsersActivity.class);
        return intent;
    }

}