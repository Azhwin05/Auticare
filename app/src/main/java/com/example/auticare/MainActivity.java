package com.example.auticare;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DatabaseReference myDatabaseReference; // 🔥 Firebase reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // FrameLayout id must be fragment_container

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Load HomeFragment by default
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selectedFragment = new HomeFragment();
            } else if (id == R.id.nav_map) {
                selectedFragment = new MapFragment();
            } else if (id == R.id.nav_alerts) {
                selectedFragment = new AlertsFragment();
            } else if (id == R.id.nav_community) {
                selectedFragment = new CommunityFragment();
            } else if (id == R.id.nav_profile) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });

        // 🔥 Setup Firebase connection
        setupFirebase();
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private void setupFirebase() {
        // Initialize Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myDatabaseReference = database.getReference("main_activity_message");

        // Write a value
        myDatabaseReference.setValue("Hello from MainActivity!");

        // Listen to realtime updates
        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.getValue(String.class);
                Log.d("FirebaseMainActivity", "Value from Firebase: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FirebaseMainActivity", "Failed to read value.", error.toException());
            }
        });
    }
}