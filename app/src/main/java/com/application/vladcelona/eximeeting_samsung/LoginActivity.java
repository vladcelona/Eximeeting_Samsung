package com.application.vladcelona.eximeeting_samsung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private FirebaseAuth firebaseAuth;

    private EditText emailEditText, passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i(TAG, "LoginActivity started");

        emailEditText = findViewById(R.id.email_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton = findViewById(R.id.login_completed_button);
        loginButton.setOnClickListener(view -> userLogin());
    }

    private void userLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

//        if (email.isEmpty()) {
//            emailEditText.setError("Email is required!");
//            emailEditText.requestFocus(); return;
//        }
//        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            emailEditText.setError("Please provide valid email!");
//            emailEditText.requestFocus(); return;
//        }
//        if (password.isEmpty()) {
//            passwordEditText.setError("Password is required!");
//            passwordEditText.requestFocus(); return;
//        }

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