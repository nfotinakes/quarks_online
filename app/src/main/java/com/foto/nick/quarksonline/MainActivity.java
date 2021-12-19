package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.foto.nick.quarksonline.db.AppDatabase;
import com.foto.nick.quarksonline.db.UserDao;

import java.util.List;

/**
 * Title: MainActivity.java
 * Author: Nicholas Fotinakes
 * Abstract: This is the starting activity for the app when opening. Used to login and create a user.
 * For this project I referenced Coding in Flow and Practical Coding on YouTube for creation of recyclerviews
 * and other elements used in this application.
 * Date: 12-10-2021
 */
public class MainActivity extends AppCompatActivity {

    // Declare buttons
    private Button signInButton;
    private Button createAccountButton;

    // Static field for our DAO
    public static UserDao userDao;
    // List for users and items
    private List<User> userList;
    private List<Item> itemList;

    // Fields for SharedPreferences
    public static int userId = -1;
    private SharedPreferences sharedPreferences = null;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Wire up Main Page buttons
        signInButton = findViewById(R.id.sign_in_button);
        createAccountButton = findViewById(R.id.create_account_in_button);

        // Call this method to initialize userDao
        checkDatabase();


        // Check if database is already populated
        // if not call populateUsers to add predefined users
        // and update userList
        userList = userDao.getAllUsers();
        if (userList.size() == 0) {
            populateUsers();
            userList = userDao.getAllUsers();
        }

        // Check if database is populated with items yet
        // if not call populateItems to add predefined items
        itemList = userDao.getAllItems();
        if (itemList.size() == 0) {
            populateItems();
            itemList = userDao.getAllItems();
        }


        // Check user using SharedPreferences to see if a user is logged in
        // if so, skip this activity and go to landing page
        checkUser();


        // Set onClick listener to go to LoginActivity
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        // Set onClick listener to go to CreateAccountActivity
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = CreateAccountActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

    }

    /**
     * checkDatabase method will check if userDao has been created yet, if not create it
     */
    private void checkDatabase() {
        if (userDao == null) {
            userDao = Room.databaseBuilder(this, AppDatabase.class, "user_database")
                    .allowMainThreadQueries()
                    .build()
                    .getUserDao();
        }
    }

    /**
     * The populateUsers method can be called to add Predefined users to database
     */
    private void populateUsers() {
        User user1 = new User("testuser1", "testuser1", false);
        User user2 = new User("admin2", "admin2", true);
        userDao.insert(user1);
        userDao.insert(user2);
    }


    /**
     * The populateItems method can be called to add predefined items to database
     */
    private void populateItems() {
        Item item1 = new Item("Phaser", 100.0, 5, "A standard federation issue handheld phaser weapon.", R.drawable.item_phaser_hand);
        Item item2 = new Item("Kanar", 40.0, 2, "Cardassian liquor. It can take a bit of getting used to.", R.drawable.item_kanar);
        Item item3 = new Item("Replicator", 500.0, 4, "Molecular synthesizer. Make yourself food...or anything really.", R.drawable.item_replicator);
        Item item4 = new Item("Transporter", 1000.0, 6, "A matter-stream converter. Beam me up!", R.drawable.item_transporter);
        Item item5 = new Item("Bat'leth", 75.0, 8, "Traditional Klingon bladed weapon.", R.drawable.item_batleth);
        Item item6 = new Item("Communicator", 10.0, 12, "Federation issue communicator.", R.drawable.item_communicator);
        Item item7 = new Item("Dabo Wheel", 50.0, 3, "Your own dabo wheel for gambling.", R.drawable.item_dabo_wheel);
        Item item8 = new Item("Dilithium Crystal", 450.0, 5, "Power your warp drive!", R.drawable.item_dilithium_crystal);
        Item item9 = new Item("Gagh", 10.0, 15, "A Klingon delicacy. Made from serpent worms. Served live!", R.drawable.item_gagh);
        Item item10 = new Item("Holosuite Tickets", 35.0, 15, "A pair of tickets for one hour in a holosuite! Your program of choice.", R.drawable.item_holosuite);
        Item item11 = new Item("Tea", 3.0, 8, "Earl Gray, Hot!", R.drawable.item_tea);
        Item item12 = new Item("Tricorder", 75.0, 5, "Federation issue tricorder. For science.", R.drawable.item_tricorder);
        Item item13 = new Item("Ressikan Flute", 15.0, 1, "Remember a past life with this flute.", R.drawable.item_ressikan);
        Item item14 = new Item("Horga'hn", 5.0, 5, "For that trip to Risa ;)", R.drawable.item_horgahn);
        userDao.insert(item1);
        userDao.insert(item2);
        userDao.insert(item3);
        userDao.insert(item4);
        userDao.insert(item5);
        userDao.insert(item6);
        userDao.insert(item7);
        userDao.insert(item8);
        userDao.insert(item9);
        userDao.insert(item10);
        userDao.insert(item11);
        userDao.insert(item12);
        userDao.insert(item13);
        userDao.insert(item14);
    }

    // Factory method to return to this MainActivity
    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }


    /**
     * The checkUser will check if we have a user logged in
     */
    private void checkUser() {
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userId = sessionManagement.getSession();

        if (userId != -1) {
            user = userDao.getUserById(userId);
            LandingPage.loggedInUser = user;
            Intent intent = LandingPage.getIntent(getApplicationContext());
            startActivity(intent);
        }

    }
}