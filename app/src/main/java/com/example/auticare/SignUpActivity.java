package com.example.auticare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText phoneEditTextSignUp, passwordEditTextSignUp, confirmPasswordEditText;
    CheckBox termsCheckbox;
    FrameLayout signUpButton;
    TextView loginRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Find views
        phoneEditTextSignUp = findViewById(R.id.phoneEditTextSignUp);
        passwordEditTextSignUp = findViewById(R.id.passwordEditTextSignUp);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        termsCheckbox = findViewById(R.id.termsCheckbox);
        signUpButton = findViewById(R.id.signUpButton);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        // Sign Up Button Click
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneEditTextSignUp.getText().toString().trim();
                String password = passwordEditTextSignUp.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else if (!termsCheckbox.isChecked()) {
                    Toast.makeText(SignUpActivity.this, "Please accept Terms & Conditions", Toast.LENGTH_SHORT).show();
                } else {
                    // Successful Signup → Go to OTP screen
                    Intent intent = new Intent(SignUpActivity.this, OtpActivity.class);
                    startActivity(intent);
                }
            }
        });

        // Login Redirect Click
        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
