package com.example.auticare;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment {

    private RecyclerView recyclerView;
    private CommunityAdapter adapter;
    private List<Community> communityList;
    private DatabaseReference communitiesRef;

    public CommunityFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        communityList = new ArrayList<>();
        adapter = new CommunityAdapter(getContext(), communityList);
        recyclerView.setAdapter(adapter);

        communitiesRef = FirebaseDatabase.getInstance().getReference("communities");

        loadCommunities(); // 🔥 Dynamic loading from Firebase

        return view;
    }

    private void loadCommunities() {
        communitiesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                communityList.clear();
                for (DataSnapshot communitySnapshot : snapshot.getChildren()) {
                    String name = communitySnapshot.getKey(); // Get community name
                    communityList.add(new Community(name, "Chat and discussions in " + name, R.drawable.ic_community));

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any error if needed
            }
        });
    }
}
