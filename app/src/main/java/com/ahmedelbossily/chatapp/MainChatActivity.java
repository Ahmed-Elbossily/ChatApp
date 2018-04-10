package com.ahmedelbossily.chatapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainChatActivity extends AppCompatActivity {

    private String displayName;
    private ListView chat_list_view;
    private EditText messageInput;
    private ImageButton sendButton;
    private ChatListAdapter adapter;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
        setupDisplayName();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        chat_list_view = findViewById(R.id.chat_list_view);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // TODO: Send the message when the "enter" button is pressed
        messageInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                sendMessage();
                return true;
            }
        });

        // TODO: Add an OnClickListener to the sendButton to send a message
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    // TODO: Retrieve the display name from the Shared Preferences
    private void setupDisplayName() {
        SharedPreferences preferences = getSharedPreferences(RegisterActivity.CHAT_PREFS, MODE_PRIVATE);
        displayName = preferences.getString(RegisterActivity.DISPLAY_NAME_KEY, null);
        if (displayName == null)
            displayName = "Anonymous";
    }

    private void sendMessage() {
        Log.d("ChatApp", "I send something");
        // TODO: Grab the text the user typed in and push the message to Firebase
        String input = messageInput.getText().toString();
        if (!input.equals("")) {
            InstantMessage chat = new InstantMessage(input, displayName);
            databaseReference.child("messages").push().setValue(chat);
            messageInput.setText("");
        }
    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
    @Override
    protected void onStart() {
        super.onStart();
        adapter = new ChatListAdapter(this, databaseReference, displayName);
        chat_list_view.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // TODO: Remove the Firebase event listener on the adapter.
        adapter.cleanUp();
    }
}
