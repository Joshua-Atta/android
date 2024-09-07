package com.example.mobibank;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class passwordresetActivity extends AppCompatActivity {

    private EditText emailEditText ,usernameresetEditTex,resetpasswordEditTex;
    private Button resetPasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_passwordreset);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String[] recipients = {"ghamhe6@gmail.com"};

        usernameresetEditTex = findViewById(R.id.resetusername);
        emailEditText = findViewById(R.id.resetemail);
        resetpasswordEditTex = findViewById(R.id.resetpassword);

        resetPasswordButton= findViewById(R.id.resetsubmit);

        //set on click listner

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the email entered by the user
                String email = emailEditText.getText().toString().trim();

                // Validate the email input
                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    // Create an email intent to open an email client
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:")); // Only email apps should handle this
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email}); // User's email
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Password Reset Request");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Please send me a password reset link.");

                    // Verify if there’s any app that can handle this intent
                    if (emailIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(emailIntent);
                    } else {

                        Toast.makeText(passwordresetActivity.this, "NO Email App found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show an error if the email is invalid
                    emailEditText.setError("Please enter a valid email address");
                }

            }
        });




    }
}