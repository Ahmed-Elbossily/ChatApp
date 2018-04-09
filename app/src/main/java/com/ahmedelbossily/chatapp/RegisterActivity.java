package com.ahmedelbossily.chatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    public static final String CHAT_PREFS = "ChatPrefs";
    public static final String DISPLAY_NAME_KEY = "username";

    private AutoCompleteTextView register_username;
    private AutoCompleteTextView register_email;
    private EditText register_password;
    private EditText register_confirm_password;

    // Firebase instance variables
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_username = findViewById(R.id.register_username);
        register_email = findViewById(R.id.register_email);
        register_password = findViewById(R.id.register_password);
        register_confirm_password = findViewById(R.id.register_confirm_password);

        // Keyboard sign in action
        register_confirm_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.integer.register_form_finished || actionId == EditorInfo.IME_NULL) {
                    attemptRegistration();
                    return true;
                }
                return false;
            }
        });

        // TODO: Get hold of an instance of FirebaseAuth
        auth = FirebaseAuth.getInstance();
    }

    // Executed when Sign Up button is pressed.
    public void signUp(View view) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        register_email.setError(null);
        register_password.setError(null);

        // Store values at the time of the login attempt.
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password) || !isPasswordValid(password)) {
            register_password.setError(getString(R.string.error_invalid_password));
            focusView = register_password;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            register_email.setError(getString(R.string.error_field_required));
            focusView = register_email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            register_email.setError(getString(R.string.error_invalid_email));
            focusView = register_email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // TODO: Call create FirebaseUser() here
            createFirebaseUser();
        }
    }

    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Add own logic to check for a valid password (minimum 6 characters)
        String confirmPassowrd = register_confirm_password.getText().toString();
        return confirmPassowrd.equals(password) && password.length() > 4;
    }

    // TODO: Create a Firebase user
    private void createFirebaseUser() {
        String email = register_email.getText().toString();
        String password = register_password.getText().toString();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("ChatApp", "CreateUser onComplete: " + task.isSuccessful());
                if (!task.isSuccessful()) {
                    Log.d("ChatApp", "User creation failed");
                    showErrorDialog("Registration attempt failed");
                } else {
                    saveDisplayName();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    // TODO: Save the display name to Shared Preferences
    private void saveDisplayName() {
        String displayName = register_username.getText().toString();
        SharedPreferences preferences = getSharedPreferences(CHAT_PREFS, 0);
        preferences.edit().putString(DISPLAY_NAME_KEY, displayName).apply();
    }

    // TODO: Create an alert dialog to show in case registration failed
    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
