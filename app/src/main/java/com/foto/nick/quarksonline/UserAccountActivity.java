package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Title: UserAccountActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the User Account Activity that displays user information
 * Date: 12-12-2021
 */
public class UserAccountActivity extends AppCompatActivity {

    // Fields for activity
    private TextView username;
    private TextView password;
    private Button deleteUserButton;
    private ImageView backButton;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        // The current user
        user = LandingPage.loggedInUser;
        // Wire up display
        username = findViewById(R.id.userAccount_usernameField_textView);
        password = findViewById(R.id.userAccount_passwordField_textView);
        deleteUserButton = findViewById(R.id.userAccount_deleteAccount_button);
        backButton = findViewById(R.id.userAccount_backButton);

        // Set text to display username and password
        username.setText(LandingPage.loggedInUser.getUsername());
        password.setText(LandingPage.loggedInUser.getPassword());

        // Listener for delete user button
        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Start dialog
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(UserAccountActivity.this);
                alertBuilder.setMessage("Delete Account?\n");

                // If yes, delete user
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // Remove user from database and update shared preferences
                        MainActivity.userDao.delete(user);
                        SessionManagement sessionManagement = new SessionManagement(UserAccountActivity.this);
                        sessionManagement.removeSession();

                        // Display toast and return to main activity
                        Toast toast = Toast.makeText(UserAccountActivity.this, "Account Deleted. Sorry to see you go.", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Intent intent = MainActivity.getIntent(getApplicationContext());
                        startActivity(intent);

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

        // Listener for back button arrow to return to LandingPage
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    // Factory pattern for this activity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, UserAccountActivity.class);
        return intent;
    }
}