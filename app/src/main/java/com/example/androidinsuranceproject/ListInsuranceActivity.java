package com.example.androidinsuranceproject;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ListInsuranceActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InsuranceAdapter adapter;
    private List<Insurance> insuranceList;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_insurance);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        insuranceList = new ArrayList<>();
        adapter = new InsuranceAdapter(this, insuranceList);
        recyclerView.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();
        db.collection("insurances")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Insurance insurance = document.toObject(Insurance.class);
                            insurance.setId(document.getId());  // Feltételezve, hogy az Insurance osztályban van setId() metódus
                            insuranceList.add(insurance);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        // Log the error
                    }
                });
    }
}
