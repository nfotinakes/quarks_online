package com.foto.nick.quarksonline;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Title: AdminEditUsers_RecyclerViewAdapter.java
 * Author: Nicholas Fotinakes
 * Abstract: This is the adapter for the AdminEditUsers recycler view
 * Date: 12-10-2021
 */
public class AdminEditUsers_RecyclerViewAdapter extends RecyclerView.Adapter<AdminEditUsers_RecyclerViewAdapter.MyViewHolder> {

    // Context and list for recycler view
    private Context context;
    private List<User> userList;

    public AdminEditUsers_RecyclerViewAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    // This method handles the inflater based on the layout for admin users recycler view
    @NonNull
    @Override
    public AdminEditUsers_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_admin_users, parent, false);
        return new AdminEditUsers_RecyclerViewAdapter.MyViewHolder(view);
    }

    // This method handles binding proper info to from the user list
    @Override
    public void onBindViewHolder(@NonNull AdminEditUsers_RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Get the user from current card
        User currentUser = userList.get(position);

        // Set the username and id
        holder.usernameTextView.setText(currentUser.getUsername());
        holder.userIdTextView.setText("Id: #" + Integer.toString(currentUser.getUserId()));

        // Listener for delete user button
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean isUser = false;

                // Check if user selected is user logged in
                for(User u : userList) {
                    if (currentUser.getUsername().equals(LandingPage.loggedInUser.getUsername())) {
                        isUser = true;
                        break;
                    }
                }

                // Display proper output if current user is deleting self
                if(isUser) {
                    Toast toast = Toast.makeText(view.getContext(), "Can't Delete Yourself, Sorry", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    // Other wise display alert and delete the user from database and update
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(view.getRootView().getContext());
                    alertBuilder.setMessage("Delete User?\n");
                    alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            MainActivity.userDao.delete(currentUser);
                            userList.remove(currentUser);
                            notifyItemRemoved(holder.getAdapterPosition());
                            Toast toast = Toast.makeText(view.getContext(), "User Deleted", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
            }
        });

        // Listener for the modify button
        holder.modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start alert builder and view
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                View v = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.layout_edit_user_dialog, null);

                // Wire up text and check box
                TextView editUsername = v.findViewById(R.id.modifyUser_username);
                TextView editPassword = v.findViewById(R.id.modifyUser_password);
                CheckBox editAdmin = v.findViewById(R.id.modifyUser_isAdmin);

                // Get the current values
                editUsername.setText(currentUser.getUsername());
                editPassword.setText(currentUser.getPassword());
                editAdmin.setChecked(currentUser.isAdmin());

                builder.setView(view)
                        .setTitle("Modify User")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean isUser = false;
                                boolean nameMatch = false;


                                // Check if we are trying to modify ourselves
                                for(User u : userList) {
                                    if(currentUser.getUsername().equals(LandingPage.loggedInUser.getUsername())) {
                                        isUser = true;
                                    }
                                    // Check for same username
                                    if(currentUser.getUserId() != u.getUserId() && editUsername.getText().toString().equals(u.getUsername())) {
                                        nameMatch = true;
                                    }
                                }

                                // Handle output for modifying self or trying to pass same username, otherwise update the user in database
                                if(isUser){
                                    Toast toast = Toast.makeText(view.getContext(), "Can't modify yourself, sorry :(", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                } else if(nameMatch){
                                    Toast toast = Toast.makeText(view.getContext(), "That username already exists, sorry :(", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 0);
                                    toast.show();

                                } else {
                                    String username = editUsername.getText().toString();
                                    String password = editPassword.getText().toString();
                                    boolean admin = editAdmin.isChecked();
                                    currentUser.setUsername(username);
                                    currentUser.setPassword(password);
                                    currentUser.setAdmin(admin);

                                    MainActivity.userDao.update(currentUser);
                                    notifyDataSetChanged();
                                    Toast toast = Toast.makeText(view.getContext(), "User Updated", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0 , 0);
                                    toast.show();
                                }
                            }
                        });

                builder.setView(v);
                builder.show();



            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView usernameTextView;
        public TextView userIdTextView;
        public Button deleteButton;
        public Button modifyButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            usernameTextView = itemView.findViewById(R.id.adminUserView_username_textView);
            userIdTextView = itemView.findViewById(R.id.adminUserView_userId_textView);
            deleteButton = itemView.findViewById(R.id.adminUserView_deleteUserButton);
            modifyButton = itemView.findViewById(R.id.adminUserView_EditUserButton);
        }
    }

}
