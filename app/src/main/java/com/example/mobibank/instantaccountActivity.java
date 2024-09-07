package com.example.mobibank;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class instantaccountActivity extends AppCompatActivity {
    private EditText phoneNumber, emailField, ghanaCard, password;
    private CheckBox termsCheckBox;
    private Button submitButton;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_instantaccount);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Initialize the database
        // Initialize the database

       db = new Database(getApplicationContext());


        // Reference views
        phoneNumber = findViewById(R.id.phonenumber);
        emailField = findViewById(R.id.email_field);
        ghanaCard = findViewById(R.id.ghcard);
        password = findViewById(R.id.password);
        termsCheckBox = findViewById(R.id.terms_checkbox);
        submitButton = findViewById(R.id.submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectedToInternet()) {
                    String phone = phoneNumber.getText().toString();
                    String email = emailField.getText().toString();
                    String card = ghanaCard.getText().toString();
                    String pass = password.getText().toString();
                    if (validateInput(phone, email, card, pass)) {
                        if (termsCheckBox.isChecked()) {
                            //save to db
                            db.register(phone, email, pass);

                            // Show success message
                            Toast.makeText(instantaccountActivity.this, "Account created, wait for confirmation", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(instantaccountActivity.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(instantaccountActivity.this, "Please fill out all fields correctly", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(instantaccountActivity.this, "No internet connection. Please try again later.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



        // Method to check if connected to the internet
        private boolean isConnectedToInternet() {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }

        // Method to validate user input
        private boolean validateInput(String phone, String email, String card, String pass) {
            if (phone.isEmpty() || email.isEmpty() || card.isEmpty() || pass.isEmpty()) {
                return false;
            }
            // Add further validation if needed (e.g., regex for email, phone format)
            return true;



    }
}