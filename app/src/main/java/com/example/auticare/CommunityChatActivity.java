package com.example.auticare;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CommunityChatActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private ImageButton sendButton;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private DatabaseReference chatRef;
    private String communityName = "General"; // Default value (safe fallback)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chat);

        // Initialize UI components
        recyclerView = findViewById(R.id.recycler_view_messages);
        editTextMessage = findViewById(R.id.edit_text_message);
        sendButton = findViewById(R.id.button_send_message);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        // 🆕 Get the community name from Intent
        if (getIntent() != null && getIntent().hasExtra("communityName")) {
            communityName = getIntent().getStringExtra("communityName");
            if (communityName == null || communityName.isEmpty()) {
                communityName = "General"; // fallback
            }
        }

        // 🆕 Setup correct database path
        chatRef = FirebaseDatabase.getInstance().getReference("communities").child(communityName);

        // Load existing messages
        loadMessages();

        // Send message button
        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void loadMessages() {
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message);
                    }
                }
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CommunityChatActivity.this, "Failed to load messages.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendMessage() {
        String text = editTextMessage.getText().toString().trim();
        if (!text.isEmpty()) {
            String senderId = "User1"; // 🔥 Later can be replaced with dynamic UserID

            // 🆕 Create Message object
            Message message = new Message(senderId, text, System.currentTimeMillis());

            chatRef.push().setValue(message)
                    .addOnSuccessListener(aVoid -> {
                        editTextMessage.setText(""); // Clear after send
                        recyclerView.scrollToPosition(messageList.size() - 1);
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(CommunityChatActivity.this, "Failed to send message.", Toast.LENGTH_SHORT).show()
                    );
        } else {
            Toast.makeText(this, "Please enter a message.", Toast.LENGTH_SHORT).show();
        }
    }
}
