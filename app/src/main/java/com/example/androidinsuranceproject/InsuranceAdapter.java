package com.example.androidinsuranceproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class InsuranceAdapter extends RecyclerView.Adapter<InsuranceAdapter.InsuranceViewHolder> {

    private Context context;
    private List<Insurance> insuranceList;
    private FirebaseFirestore db;

    public InsuranceAdapter(Context context, List<Insurance> insuranceList) {
        this.context = context;
        this.insuranceList = insuranceList;
        this.db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public InsuranceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.insurance_item_layout, parent, false);
        return new InsuranceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsuranceViewHolder holder, int position) {
        Insurance insurance = insuranceList.get(position);
        holder.nameTextView.setText("Név: " + insurance.getName());
        holder.carModelTextView.setText("Autó modell: " + insurance.getCarModel());
        holder.carYearTextView.setText("Évjárat: " + String.valueOf(insurance.getCarYear()));

        holder.deleteButton.setOnClickListener(v -> {
            // Törlés a Firestore adatbázisból
            db.collection("insurances").document(insurance.getId()).delete()
                    .addOnSuccessListener(aVoid -> {
                        // Törlés a listából
                        insuranceList.remove(position);
                        notifyItemRemoved(position);

                        // Visszajelzés a felhasználónak
                        Toast.makeText(context, "A biztosítás sikeresen törölve!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Hiba kezelése
                    });
        });
    }

    @Override
    public int getItemCount() {
        return insuranceList.size();
    }

    class InsuranceViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView carModelTextView;
        TextView carYearTextView;
        Button deleteButton;

        public InsuranceViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            carModelTextView = itemView.findViewById(R.id.carModelTextView);
            carYearTextView = itemView.findViewById(R.id.carYearTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
