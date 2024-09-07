package com.example.mobibank;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class policyActivity extends AppCompatActivity {

    private ImageView bankLogo;
    private TextView policiesTitle, policiesContent;
    private ScrollView scrollViewPolicies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_policy);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        bankLogo = findViewById(R.id.bankLogo);
        policiesTitle = findViewById(R.id.policiesTitle);
        policiesContent = findViewById(R.id.policiesContent);
        scrollViewPolicies = findViewById(R.id.scrollViewPolicies);
    }
}