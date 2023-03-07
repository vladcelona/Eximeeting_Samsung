package com.application.vladcelona.eximeeting_samsung;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class StartActivity extends AppCompatActivity {

    private final String TAG = "StartActivity";
    private TextView topText;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Log.i(TAG, "StartActivity started");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

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

        TextView appName = findViewById(R.id.start_screen_app_name);
        TextView description = findViewById(R.id.start_screen_description);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        // Apply animations to Views
        appName.startAnimation(appNameAnimation);
        description.startAnimation(descriptionAnimation);
        loginButton.startAnimation(buttonsAnimation);
        registerButton.startAnimation(buttonsAnimation);

        loginButton.setOnClickListener(view -> {
            Log.i(TAG, "Starting new Intent: LoginActivity");
            startActivity(new Intent(StartActivity.this, LoginActivity.class));
        });

        registerButton.setOnClickListener(view -> {
            Log.i(TAG, "Starting new Intent: RegisterActivity");
            startActivity(new Intent(StartActivity.this, RegisterActivity.class));
        });
    }
}