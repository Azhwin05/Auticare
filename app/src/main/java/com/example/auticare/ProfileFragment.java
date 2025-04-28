package com.example.auticare;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private ImageView profileImage;
    private Button logoutButton;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileImage = view.findViewById(R.id.profileImage);
        logoutButton = view.findViewById(R.id.logoutButton);

        // Setup Click Listeners
        view.findViewById(R.id.editProfile).setOnClickListener(v -> Toast.makeText(getContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.changePicture).setOnClickListener(v -> Toast.makeText(getContext(), "Change Profile Picture clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.viewStats).setOnClickListener(v -> Toast.makeText(getContext(), "View App Usage Stats clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.viewReports).setOnClickListener(v -> Toast.makeText(getContext(), "View Generated Reports clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.notificationSettings).setOnClickListener(v -> Toast.makeText(getContext(), "Notification Settings clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.changePassword).setOnClickListener(v -> Toast.makeText(getContext(), "Change Password clicked", Toast.LENGTH_SHORT).show());
        view.findViewById(R.id.aboutApp).setOnClickListener(v -> Toast.makeText(getContext(), "About Auticare clicked", Toast.LENGTH_SHORT).show());

        view.findViewById(R.id.contactSupport).setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:support@auticare.com"));
            startActivity(emailIntent);
        });

        logoutButton.setOnClickListener(v -> Toast.makeText(getContext(), "Logged Out!", Toast.LENGTH_SHORT).show());
    }
}
