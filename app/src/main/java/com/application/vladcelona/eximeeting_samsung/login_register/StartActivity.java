package com.application.vladcelona.eximeeting_samsung.login_register;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.application.vladcelona.eximeeting_samsung.MainActivity;
import com.application.vladcelona.eximeeting_samsung.R;
import com.application.vladcelona.eximeeting_samsung.databinding.ActivityStartBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    private final String TAG = "StartActivity";
    private ActivityStartBinding binding;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "StartActivity started");
        binding = ActivityStartBinding.inflate(getLayoutInflater());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users");

        // Check if the user has been logged in before
        databaseReference.child(Objects.requireNonNull(
                        FirebaseAuth.getInstance().getCurrentUser()).getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult().exists()) {
                        Toast.makeText(StartActivity.this, "You have been logged in successfully",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                    }
                });

        // Set up animation for StartActivity Views
        Animation appNameAnimation = AnimationUtils.loadAnimation(
                this, R.anim.app_name_animation);
        Animation descriptionAnimation = AnimationUtils.loadAnimation(
                this, R.anim.description_animation);
        Animation buttonsAnimation = AnimationUtils.loadAnimation(this,
                R.anim.buttons_animation);

        // Apply animations to Views
        binding.startScreenAppName.startAnimation(appNameAnimation);
        binding.startScreenDescription.startAnimation(descriptionAnimation);
        binding.loginButton.startAnimation(buttonsAnimation);
        binding.registerButton.startAnimation(buttonsAnimation);

        binding.loginButton.setOnClickListener(view -> {
            Log.i(TAG, "Starting new Intent: LoginActivity");
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        });

        binding.registerButton.setOnClickListener(view -> {
            Log.i(TAG, "Starting new Intent: RegisterActivity");
            startActivity(new Intent(StartActivity.this, RegisterActivity.class));
        });

        setContentView(binding.getRoot());
    }
}