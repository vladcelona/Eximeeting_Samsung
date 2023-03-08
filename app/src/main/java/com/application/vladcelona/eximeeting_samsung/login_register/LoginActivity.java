package com.application.vladcelona.eximeeting_samsung.login_register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.application.vladcelona.eximeeting_samsung.MainActivity;
import com.application.vladcelona.eximeeting_samsung.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "LoginActivity started");
        
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        firebaseAuth = FirebaseAuth.getInstance();

        binding.loginCompletedButton.setOnClickListener(view -> userLogin());
        setContentView(binding.getRoot());
    }

    private void userLogin() {
        String email = Objects.requireNonNull(binding.emailEdittext.getText()).toString().trim();
        String password = Objects.requireNonNull(binding.passwordEdittext.getText()).toString().trim();

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

        if (password.isEmpty()) {
            binding.passwordEdittext.setError("Password is required!");
            binding.passwordEdittext.requestFocus(); return;
        } else {
            binding.passwordEdittext.setError(null); binding.passwordEdittext.requestFocus();
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "Starting new Intent: MainActivity");
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(this,
                                "Failed to login! Please check your credentials!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}