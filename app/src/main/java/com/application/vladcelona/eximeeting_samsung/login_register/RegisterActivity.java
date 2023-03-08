package com.application.vladcelona.eximeeting_samsung.login_register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.application.vladcelona.eximeeting_samsung.MainActivity;
import com.application.vladcelona.eximeeting_samsung.data_classes.User;
import com.application.vladcelona.eximeeting_samsung.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = "RegisterActivity";
    protected FirebaseAuth firebaseAuth;
    private ActivityRegisterBinding binding;

    // Here we create necessary variables for creating account on Firebase
    private String fullName, email, companyName, password;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "RegisterActivity started");
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());

        String deviceName = Secure.getString(getContentResolver(), Secure.ANDROID_ID);
        Log.i(TAG, "Devices name: " + deviceName);

        firebaseAuth = FirebaseAuth.getInstance();
        binding.registerCompletedButton.setOnClickListener(view -> registerUser());

        setContentView(binding.getRoot());
    }

    private void registerUser() {
        fullName = Objects.requireNonNull(binding.fullNameEdittext.getText()).toString().trim();
        email = Objects.requireNonNull(binding.emailEdittext.getText()).toString().trim();
        companyName = Objects.requireNonNull(binding.companyNameEdittext.getText()).toString().trim();
        password = Objects.requireNonNull(binding.passwordEdittext.getText()).toString().trim();

        // Here we apply the case when Views are empty (which is not acceptable)
        // We ask user to enter their data

        if (fullName.isEmpty()) {
            binding.fullNameEdittext.setError("Full name is required!");
            binding.fullNameEdittext.requestFocus(); return;
        } else {
            binding.fullNameEdittext.setError(null); binding.fullNameEdittext.requestFocus();
        }

        if (email.isEmpty()) {
            binding.emailEdittext.setError("Email is required!");
            binding.emailEdittext.requestFocus(); return;
        } else {
            binding.emailEdittext.setError(null); binding.emailEdittext.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEdittext.setError("Please provide valid email!");
            binding.emailEdittext.requestFocus(); return;
        } else {
            binding.emailEdittext.setError(null); binding.emailEdittext.requestFocus();
        }

        if (companyName.isEmpty()) {
            binding.companyNameEdittext.setError("Company name is required!");
            binding.companyNameEdittext.requestFocus(); return;
        } else {
            binding.companyNameEdittext.setError(null); binding.companyNameEdittext.requestFocus();
        }

        if (password.isEmpty()) {
            binding.passwordEdittext.setError("Password is required!");
            binding.passwordEdittext.requestFocus(); return;
        } else {
            binding.passwordEdittext.setError(null); binding.passwordEdittext.requestFocus();
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