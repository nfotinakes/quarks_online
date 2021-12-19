package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

/**
 * Title: LoginActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the login page
 * Date: 12-1-2021
 */
public class LoginActivity extends AppCompatActivity {

    // Declare fields for login page
    private EditText username;
    private EditText password;
    private TextView forgotPass;
    private Button login;
    private ImageView backButton;
    private String usernameInput;
    private String passwordInput;
    private boolean flag = false;
    private List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Wire up buttons and text fields
        username = findViewById(R.id.username_edittext);
        password = findViewById(R.id.password_edittext);
        login = findViewById(R.id.login_button);
        backButton = findViewById(R.id.login_backButoon);
        forgotPass = findViewById(R.id.forgot_password);
        userList = MainActivity.userDao.getAllUsers();

        // Set listener for login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get username and password from fields
                usernameInput = username.getText().toString();
                passwordInput = password.getText().toString();

                // Check that username and password match an existing user
                for(User u : userList) {
                    if(usernameInput.equals(u.getUsername()) && passwordInput.equals(u.getPassword())) {
                        // If so display toast, log the user in, and go to LandingPage
                        login(u);
                    }
                }

                // Handle missing field or incorrect username or password
                if (!flag) {
                    Toast toast;
                    if (usernameInput.equals("") || passwordInput.equals("") ) {
                        toast = Toast.makeText(LoginActivity.this, "Missing field, try again", Toast.LENGTH_LONG);
                    } else {
                        toast = Toast.makeText(LoginActivity.this, "Username or password incorrect, please try again", Toast.LENGTH_LONG);
                    }
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                }
            }
        });

        // Listener to return to MainActivity via back arrow
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Display toast for fogot password text
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(view.getContext(), "Contact an oracle or something...idk", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
    }

    // This method will handle logging in a user if password correct
    public void login(User u) {
        Toast toast = Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        LandingPage.loggedInUser = u;
        flag = true;

        // Set shared preferences
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        sessionManagement.saveSession(u);
        // Go to landing page
        Intent intent = LandingPage.getIntent(getApplicationContext());
        startActivity(intent);
    }

    // Factory pattern for this activity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}