package com.example.mobibank;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class transfermoneyActivity extends AppCompatActivity {
    private EditText senderNumber;
    private EditText receiverNumber;
    private EditText amountToSend;
    private EditText confirmAmount;
    private Button transferButton;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transfermoney);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize views
        senderNumber = findViewById(R.id.sendernumber);
        receiverNumber = findViewById(R.id.receivernumber);
        amountToSend = findViewById(R.id.amounttosendt);
        confirmAmount = findViewById(R.id.confirmamt);
        transferButton = findViewById(R.id.buttontransfer);

        database = new Database(this);

        transferButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performTransfer();
            }

            private void performTransfer() {
                // Get input values
                String sender = senderNumber.getText().toString().trim();
                String receiver = receiverNumber.getText().toString().trim();
                String amountStr = amountToSend.getText().toString().trim();
                String confirmAmountStr = confirmAmount.getText().toString().trim();

                // Validate input
                if (TextUtils.isEmpty(sender)) {
                    Toast.makeText(transfermoneyActivity.this, "Enter your account number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(receiver)) {
                    Toast.makeText(transfermoneyActivity.this, "Enter recipient number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(amountStr)) {
                    Toast.makeText(transfermoneyActivity.this, "Enter amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmAmountStr)) {
                    Toast.makeText(transfermoneyActivity.this, "Confirm amount", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!amountStr.equals(confirmAmountStr)) {
                    Toast.makeText(transfermoneyActivity.this, "Amount does not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                double amount = Double.parseDouble(amountStr);

                // Check if connected to the internet before saving
                if (database.isConnectedToInternet(transfermoneyActivity.this)) {  // FIX: Added closing parenthesis here
                    // Save the transfer details to the database
                    database.transferMoney(sender, receiver, amount);
                    Toast.makeText(transfermoneyActivity.this, "Transfer is successful", Toast.LENGTH_SHORT).show();

                    // Clear the input fields after the transfer
                    clearFields();
                } else {
                    Toast.makeText(transfermoneyActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
                }
            }

            private void clearFields() {
                senderNumber.setText("");
                receiverNumber.setText("");
                amountToSend.setText("");
                confirmAmount.setText("");
            }
        });
    }
}
