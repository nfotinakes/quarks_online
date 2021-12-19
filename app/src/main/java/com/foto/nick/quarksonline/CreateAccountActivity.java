package com.foto.nick.quarksonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Title: CreateAccountActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the create account activity to create a new user account
 * Date: 12-10-2021
 */
public class CreateAccountActivity extends AppCompatActivity {

    // Declare fields for activity
    private EditText createUsername;
    private EditText password;
    private EditText passwordReenter;
    private Button createAccount;
    private String username;
    private String finalPassword;
    private ImageView backButton;
    private List<User> userList;
    boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Wire up display
        createUsername = findViewById(R.id.create_username_edittext);
        password = findViewById(R.id.create_password_edittext);
        passwordReenter = findViewById(R.id.create_reenter_password_edittext);
        createAccount = findViewById(R.id.create_button);
        backButton = findViewById(R.id.create_backButton);
        userList = MainActivity.userDao.getAllUsers();

        // Listener for user to create new account button
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get info from fields entered
                username = createUsername.getText().toString();
                finalPassword = password.getText().toString();
                User user = null;
                flag = false;

                // Check if fields are blank
                if(createUsername.getText().toString().isEmpty() || password.getText().toString().isEmpty() || passwordReenter.getText().toString().isEmpty()) {
                    flag = true;
                    Toast toast = Toast.makeText(CreateAccountActivity.this, "A required field is blank", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0 , 0);
                    toast.show();
                // check if passwords match
                } else if (!password.getText().toString().equals(passwordReenter.getText().toString())) {
                    flag = true;
                    Toast toast = Toast.makeText(CreateAccountActivity.this, "Passwords Don't Match, Re-enter", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0 , 0);
                    toast.show();
                // Check for existing username
                } else {
                    user = new User(username, finalPassword, false);
                    for(User u : userList ) {
                        if (u.getUsername().equals(username)) {
                            Toast toast = Toast.makeText(CreateAccountActivity.this, "Username already exists, try again", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0 , 0);
                            toast.show();
                            flag = true;
                            break;
                        }
                    }
                }
                // If conditions pass, create a new user and log in
                if(!flag) {
                    MainActivity.userDao.insert(user);
                    User userToCheck = MainActivity.userDao.getUserByUsername(user.getUsername());
                    LandingPage.loggedInUser = userToCheck;
                    SessionManagement sessionManagement = new SessionManagement(CreateAccountActivity.this);
                    sessionManagement.saveSession(userToCheck);
                    Toast toast = Toast.makeText(CreateAccountActivity.this, "Account created! You are now logged in.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Intent intent = LandingPage.getIntent(getApplicationContext());
                    startActivity(intent);
                }

            }
        });

        // Return to main activity from back button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    // Factory pattern for this activity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CreateAccountActivity.class);
        return intent;
    }


}