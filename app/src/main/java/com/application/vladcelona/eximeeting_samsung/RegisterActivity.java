package com.application.vladcelona.eximeeting_samsung;

import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";

    protected FirebaseAuth firebaseAuth;

    private EditText fullNameEditText, emailEditText, companyNameEditText, passwordEditText;

    // Here we create necessary variables for creating account on Firebase
    private String deviceName, fullName, email, companyName, password;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.i(TAG, "RegisterActivity started");

        fullNameEditText = findViewById(R.id.full_name_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        companyNameEditText = findViewById(R.id.company_name_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        deviceName = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        Log.i(TAG, "Devices name: " + deviceName);

        firebaseAuth = FirebaseAuth.getInstance();

        Button registerButton = findViewById(R.id.register_completed_button);
        registerButton.setOnClickListener(view -> {
            registerUser();
        });
    }

    private void registerUser() {
        fullName = fullNameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        companyName = companyNameEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();

        // Here we apply the case when Views are empty (which is not acceptable)
        // We ask user to enter their data

        if (fullName.isEmpty()) {
            fullNameEditText.setError("Full name is required!");
            fullNameEditText.requestFocus(); return;
        } else {
            fullNameEditText.setError(null); fullNameEditText.requestFocus();
        }

        if (email.isEmpty()) {
            emailEditText.setError("Email is required!");
            emailEditText.requestFocus(); return;
        } else {
            emailEditText.setError(null); emailEditText.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please provide valid email!");
            emailEditText.requestFocus(); return;
        } else {
            emailEditText.setError(null); emailEditText.requestFocus();
        }

        if (companyName.isEmpty()) {
            companyNameEditText.setError("Company name is required!");
            companyNameEditText.requestFocus(); return;
        } else {
            companyNameEditText.setError(null); companyNameEditText.requestFocus();
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required!");
            passwordEditText.requestFocus(); return;
        } else {
            passwordEditText.setError(null); passwordEditText.requestFocus();
        }

        createAccount();
    }

    // Here we apply the logic for creating account in Firebase database
    private void createAccount() {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        User user = new User(fullName, email, companyName);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(Objects.requireNonNull(
                                        FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this,
                                                "You been registered successfully!",
                                                Toast.LENGTH_SHORT).show();
                                        Log.i(TAG, "Creating new Intent: MainActivity");
                                        startActivity(new Intent(RegisterActivity.this,
                                                MainActivity.class));
                                    } else {
                                        Toast.makeText(RegisterActivity.this,
                                                "Failed ot register. Try again!",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(RegisterActivity.this, "Failed to register",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}