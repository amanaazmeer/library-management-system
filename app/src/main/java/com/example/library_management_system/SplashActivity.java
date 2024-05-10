package com.example.library_management_system;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Duration of wait
    private static final long SPLASH_DELAY_TIME = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Post a delayed action to start the MainActivity after the specified delay
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the MainActivity
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_DELAY_TIME);
    }
}
