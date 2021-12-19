package com.foto.nick.quarksonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Title: LandingPage.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the main page when a user is logged in
 * Date: 12-1-2021
 */
public class LandingPage extends AppCompatActivity {

    // Declare fields for landing page
    private Button adminButton;
    private Button logOutButton;
    private Button shopButton;
    private Button ordersButton;
    private TextView usernameDisplay;
    private Button randomRulesButton;
    // Hold the logged in user
    public static User loggedInUser;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        // Wire up display items
        logOutButton = findViewById(R.id.landing_page_logout_button);
        shopButton = findViewById(R.id.landing_page_shop_button);
        usernameDisplay = findViewById(R.id.landing_page_user);
        randomRulesButton = findViewById(R.id.landingPage_randomRules_button);
        ordersButton = findViewById(R.id.landing_page_vieworders_button);

        // Check if logged in user is an admin to display admin button
        adminButton = findViewById(R.id.landing_page_admin_button);
        if(loggedInUser.isAdmin()) {
            adminButton.setVisibility(View.VISIBLE);
        }

        // Set the logged in user display
        usernameDisplay.setText(loggedInUser.getUsername());

        // Listener to log user out
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedInUser = null;
                Toast toast = Toast.makeText(LandingPage.this, "Logged Out Successfully", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                // Update Shared Preferences
                SessionManagement sessionManagement = new SessionManagement(LandingPage.this);
                sessionManagement.removeSession();

                // Return to MainActivity
                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Listener to go to Shop
        shopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ShopActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Listener to display a random rule of acquisition
        randomRulesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RulesOfAcquisition ruleList = new RulesOfAcquisition();
                String str = ruleList.getRandomRule();
                Toast toast = Toast.makeText(LandingPage.this, str, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0 , 0);
                toast.show();
            }
        });

        // Listener to go to ViewOrders activity
        ordersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ViewOrdersActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Listener to go to user account info when click on username
        usernameDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = UserAccountActivity.getIntent(getApplicationContext());
                startActivity(intent);

            }
        });

        // If admin button visible, go to admin tools
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminToolsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });


    }

    // Disable phone back button to stay on landing page
    @Override
    public void onBackPressed() {
    }

    // Factory pattern for LandingPage
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, LandingPage.class);
        return intent;
    }
}