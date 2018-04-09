package com.ahmedelbossily.chatapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class MainChatActivity extends AppCompatActivity {

    private String displayName;
    private ListView chat_list_view;
    private EditText messageInput;
    private ImageButton sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference

        chat_list_view = findViewById(R.id.chat_list_view);
        messageInput = findViewById(R.id.messageInput);
        sendButton = findViewById(R.id.sendButton);

        // TODO: Send the message when the "enter" button is pressed

        // TODO: Add an OnClickListener to the sendButton to send a message
    }

    // TODO: Retrieve the display name from the Shared Preferences

    private void sendMessage() {
        // TODO: Grab the text the user typed in and push the message to Firebase
    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.


    @Override
    protected void onStop() {
        super.onStop();
        // TODO: Remove the Firebase event listener on the adapter.
    }
}
