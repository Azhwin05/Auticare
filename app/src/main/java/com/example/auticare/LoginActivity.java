package com.example.auticare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumberEditText, passwordEditText;
    Button loginButton;
    TextView signupRedirect, forgotPasswordText;
    ImageView googleIcon, facebookIcon, twitterIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Views
        phoneNumberEditText = findViewById(R.id.phoneEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        FrameLayout loginButton = findViewById(R.id.loginButton);
        signupRedirect = findViewById(R.id.signUpText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        googleIcon = findViewById(R.id.googleIcon);
        facebookIcon = findViewById(R.id.facebookIcon);
        twitterIcon = findViewById(R.id.twitterIcon);

        // Login Button Click
        loginButton.setOnClickListener(view -> {
            String phone = phoneNumberEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (TextUtils.isEmpty(phone)) {
                phoneNumberEditText.setError("Enter Phone Number");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                passwordEditText.setError("Enter Password");
                return;
            }

            // Validation successful, proceed to MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Sign Up Redirect
        signupRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Forgot Password (Optional Navigation)
        forgotPasswordText.setOnClickListener(view -> {
            Toast.makeText(LoginActivity.this, "Forgot Password clicked!", Toast.LENGTH_SHORT).show();
        });

        // Social Icon Clicks (Toast for now)
        googleIcon.setOnClickListener(view ->
                Toast.makeText(LoginActivity.this, "Google Login coming soon!", Toast.LENGTH_SHORT).show()
        );

        facebookIcon.setOnClickListener(view ->
                Toast.makeText(LoginActivity.this, "Facebook Login coming soon!", Toast.LENGTH_SHORT).show()
        );

        twitterIcon.setOnClickListener(view ->
                Toast.makeText(LoginActivity.this, "Twitter Login coming soon!", Toast.LENGTH_SHORT).show()
        );
    }
}
