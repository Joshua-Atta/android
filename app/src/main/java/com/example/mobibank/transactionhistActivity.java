package com.example.mobibank;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class transactionhistActivity extends AppCompatActivity {

    private TextView senderName, recipientName, transferAmount;
    private Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transactionhist);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        senderName = findViewById(R.id.senderName);
        recipientName = findViewById(R.id.recipientName);
        transferAmount = findViewById(R.id.transferAmount);
        database = new Database(this);

        // Initialize the database helper
        database = new Database(this);

        // Load transfer details from the database
        loadTransferDetails();
        
    }

    private void loadTransferDetails() {
        Cursor cursor = database.getLatestTransfer();

        // Check if data is available
        if (cursor != null && cursor.moveToFirst()) {
            // Fetch data from cursor
            int senderIndex = cursor.getColumnIndex("sender");
            int recipientIndex = cursor.getColumnIndex("recipient");
            int amountIndex = cursor.getColumnIndex("amount");

            if (senderIndex != -1 && recipientIndex != -1 && amountIndex != -1) {
                // Fetch the values
                String sender = cursor.getString(senderIndex);
                String recipient = cursor.getString(recipientIndex);
                double amount = cursor.getDouble(amountIndex);

                // Display data in the TextViews
                senderName.setText("Sender: " + sender);
                recipientName.setText("Recipient: " + recipient);
                transferAmount.setText("Amount: " + String.valueOf(amount));
            }

            cursor.close(); // Close the cursor
        } else {
            // No data available, show a message
            Toast.makeText(this, "No transfer data found", Toast.LENGTH_SHORT).show();
        }

}
}