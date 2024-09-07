package com.example.mobibank;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class depositActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 1;
    private EditText recipientNumber;
    private EditText depositAmount;
    private Button depositButton;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_deposit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recipientNumber = findViewById(R.id.recivernumber);
        depositAmount = findViewById(R.id.amounttosend);
        depositButton = findViewById(R.id.buttondeposit);
        database = new Database(this);

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performDeposit();
            }
        });
    }

    private void performDeposit() {
        String phoneNumber = recipientNumber.getText().toString().trim();
        String amountText = depositAmount.getText().toString().trim();

        if (phoneNumber.isEmpty() || amountText.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountText);

        if (database.isConnectedToInternet(this)) {
            // Save deposit to the database
            database.depositMoney(phoneNumber, amount);  // Corrected method name

            // Send SMS
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
            } else {
                sendSms(phoneNumber, amount);
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendSms(String phoneNumber, double amount) {
        String message = "Payment of $" + amount + " to " + phoneNumber + ". Please confirm.";

        // Use an intent to open the SMS app
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phoneNumber));
        smsIntent.putExtra("sms_body", message);
        try {
            startActivity(smsIntent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to open SMS app", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with sending SMS
                String phoneNumber = recipientNumber.getText().toString().trim();
                String amountText = depositAmount.getText().toString().trim();
                double amount = Double.parseDouble(amountText);
                sendSms(phoneNumber, amount);
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
