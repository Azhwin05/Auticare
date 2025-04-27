package com.example.auticare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private DatabaseReference messagesRef;
    private EditText editTextMessage;
    private Button sendButton;

    public CommunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the fragment layout
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);

        // Connect to Firebase "messages" node
        messagesRef = FirebaseDatabase.getInstance().getReference("messages");

        // Initialize UI elements
        editTextMessage = view.findViewById(R.id.edit_text_message);
        sendButton = view.findViewById(R.id.button_send_message);

        loadMessages(); // Load existing messages from Firebase

        // Send message logic
        sendButton.setOnClickListener(v -> sendMessage());

        return view;
    }

    private void loadMessages() {
        // Retrieve data from Firebase
        messagesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message message = dataSnapshot.getValue(Message.class);
                    if (message != null) {
                        messageList.add(message); // Add message to the list
                    }
                }
                messageAdapter.notifyDataSetChanged(); // Notify the adapter that data has changed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error (optional)
            }
        });
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            String senderId = "User1";  // Replace with actual sender ID logic

            // Create Message object
            Message message = new Message(senderId, messageText); // Use the constructor with two parameters

            // Send message to Firebase Realtime Database
            messagesRef.push().setValue(message)
                    .addOnSuccessListener(aVoid -> {
                        editTextMessage.setText("");  // Clear input field
                        Toast.makeText(getContext(), "Message sent!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Failed to send message", Toast.LENGTH_SHORT).show();
                    });
        } else {
            Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
        }
    }
}