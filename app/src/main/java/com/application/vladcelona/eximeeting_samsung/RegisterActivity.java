package com.application.vladcelona.eximeeting_samsung;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    protected FirebaseAuth firebaseAuth;

    private EditText fullNameEditText, emailEditText, companyNameEditText, passwordEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullNameEditText = findViewById(R.id.full_name_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        companyNameEditText = findViewById(R.id.company_name_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        registerUser();

//        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String companyName = companyNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (fullName.isEmpty()) {
            fullNameEditText.setError("Full name is required!");
            fullNameEditText.requestFocus(); return;
        }
        if (email.isEmpty()) {
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus(); return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide valid email!");
            emailEditText.requestFocus(); return;
        }
        if (companyName.isEmpty()) {
            companyNameEditText.setError("Company name is required!");
            companyNameEditText.requestFocus(); return;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Password is required!");
            passwordEditText.requestFocus(); return;
        }
    }
}