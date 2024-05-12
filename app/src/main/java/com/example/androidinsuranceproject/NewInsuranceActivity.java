package com.example.androidinsuranceproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class NewInsuranceActivity extends AppCompatActivity {

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_insurance);

        db = FirebaseFirestore.getInstance();

        EditText insuranceNameEditText = findViewById(R.id.insurance_name);
        EditText insuranceCarModelEditText = findViewById(R.id.insurance_car_model);
        EditText insuranceCarYearEditText = findViewById(R.id.insurance_car_year);
        Button saveButton = findViewById(R.id.save_button);
        Button cancelButton = findViewById(R.id.cancel_button);

        saveButton.setOnClickListener(v -> {
            String name = insuranceNameEditText.getText().toString();
            String carModel = insuranceCarModelEditText.getText().toString();
            String carYear = insuranceCarYearEditText.getText().toString();

            Map<String, Object> insurance = new HashMap<>();
            insurance.put("name", name);
            insurance.put("carModel", carModel);
            insurance.put("carYear", carYear);

            db.collection("insurances").add(insurance)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(NewInsuranceActivity.this, "Biztosítás sikeresen mentve.",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NewInsuranceActivity.this, LoggedInActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(NewInsuranceActivity.this, "Hiba történt a biztosítás mentése közben.",
                                Toast.LENGTH_SHORT).show();
                    });
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(NewInsuranceActivity.this, LoggedInActivity.class);
            startActivity(intent);
            finish();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyChannel";
            String description = "Channel for My App";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("MY_CHANNEL_ID", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        EditText insuranceNameEditText = findViewById(R.id.insurance_name);
        EditText insuranceCarModelEditText = findViewById(R.id.insurance_car_model);
        EditText insuranceCarYearEditText = findViewById(R.id.insurance_car_year);

        String name = insuranceNameEditText.getText().toString();
        String carModel = insuranceCarModelEditText.getText().toString();
        String carYear = insuranceCarYearEditText.getText().toString();

        if (!name.isEmpty() || !carModel.isEmpty() || !carYear.isEmpty()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MY_CHANNEL_ID")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Biztosítás kötés")
                    .setContentText("Nem fejezte be a biztosítás kötését!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(1, builder.build());
        }
    }
}
