package com.example.androidinsuranceproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class LoggedInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logged_in);

        mAuth = FirebaseAuth.getInstance();

        Button viewInsuranceButton = findViewById(R.id.view_insurance);
        Button newInsuranceButton = findViewById(R.id.new_insurance);
        Button logoutButton = findViewById(R.id.logout);

        viewInsuranceButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoggedInActivity.this, ListInsuranceActivity.class);
            startActivity(intent);
        });

        newInsuranceButton.setOnClickListener(v -> {
            // Redirect to NewInsuranceActivity
            Intent intent = new Intent(LoggedInActivity.this, NewInsuranceActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            // Sign out the user
            mAuth.signOut();

            // Redirect to MainActivity with a transition
            Intent intent = new Intent(LoggedInActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
