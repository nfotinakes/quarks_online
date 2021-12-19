package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Title: AdminToolsActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This class handles the Admin Tools Activity
 * Date: 12-10-2021
 */
public class AdminToolsActivity extends AppCompatActivity {

    // Declare button and back button image
    private Button editUserButton;
    private Button editItemsButton;
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tools);

        // Wire up display
        editUserButton = findViewById(R.id.adminTools_editUsers_button);
        editItemsButton = findViewById(R.id.adminTools_editItems_button);
        backButton = findViewById(R.id.admin_tools_backButton);

        // Listener to go to admin edit user activity
        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminEditUsersActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // listener to go to admin edit items activity
        editItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminEditItemsActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Listener to go back to landing page
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    // factory method for this activity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminToolsActivity.class);
        return intent;
    }
}