package com.foto.nick.quarksonline;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * Title: CreateUserDialog.java
 * Author: Nicholas Fotinakes
 * Abstract: This was a practice class for creating a custom dialog. Credit to Practical Coding in which
 * I followed a tutorial for doing something like this. I ended up just handling dialogs within methods after this.
 * Date: 12-10-2021
 */
public class CreateUserDialog extends AppCompatDialogFragment {

    // Fields for dialog
    private EditText editTextUsername;
    private EditText editTextPassword;
    private CheckBox checkBox;
    private CreateUserDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Create builder and inflate based on our layout dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle("Create User")
                // Cancel do nothing
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                // Positive button to create user
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Get info from edit texts and admin checkbox
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        boolean admin = checkBox.isChecked();
                        listener.applyText(username, password, admin);

                    }
                });
        editTextUsername = view.findViewById(R.id.admin_username);
        editTextPassword = view.findViewById(R.id.admin_password);
        checkBox = view.findViewById(R.id.admin_checkBox_isAdmin);
        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CreateUserDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface CreateUserDialogListener{
        void applyText(String username, String password, boolean admin);
    }
}
