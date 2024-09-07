package com.example.mobibank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class createaccountActivity extends AppCompatActivity {
    private TextView accountGhanaCard, accountBankCard, accountGMoney, instantAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createaccount);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        // Initialize the LinearLayout options
        accountGhanaCard = findViewById(R.id.accountghanacard);
        accountBankCard = findViewById(R.id.accountbankcard);
        accountGMoney = findViewById(R.id.accountgmoney);
        instantAccount = findViewById(R.id.instantaccount);

        accountGhanaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(createaccountActivity.this, "Please visit our main office for registration.", Toast.LENGTH_SHORT).show();

            }
        });

        accountBankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(createaccountActivity.this, "Please visit our main office for registration.", Toast.LENGTH_SHORT).show();
            }
        });

        accountGMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(createaccountActivity.this, "Please visit our main office for registration.", Toast.LENGTH_SHORT).show();
            }
        });
        instantAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(createaccountActivity.this, instantaccountActivity.class);
                startActivity(intent);
            }
        });



    }
}