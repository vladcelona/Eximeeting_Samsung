package com.application.vladcelona.eximeeting_samsung;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.application.vladcelona.eximeeting_samsung.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.mainActivityText.setText(Build.ID);

        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "Activity onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "Activity onRestart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "Activity onStop");
        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "Activity onDestroy");
        startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME));
    }
}