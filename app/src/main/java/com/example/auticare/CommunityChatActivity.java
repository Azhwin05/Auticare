package com.example.auticare;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CommunityChatActivity extends AppCompatActivity {

    private TextView chatHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chat);

        chatHeader = findViewById(R.id.chatHeader);

        String communityName = getIntent().getStringExtra("communityName");
        chatHeader.setText(communityName + " Chat Room");

        // Later: Add chat messages, input box here
    }
}
