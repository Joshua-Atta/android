package com.example.mobibank;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signupActivity extends AppCompatActivity {
    private EditText UsernameditText, EmailEditText, PasswordEditText, PasswordconfirmEditText;
    private Button signupButton;
    private TextView signupalreadyTextView, signuppTextView;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //iniatalize

        UsernameditText = findViewById(R.id.usernamesignup);
        EmailEditText = findViewById(R.id.signupemail);
        PasswordEditText = findViewById(R.id.signuppassword);
        PasswordconfirmEditText = findViewById(R.id.signupconfirmpassword);
        signupButton = findViewById(R.id.buttonsignup);
        signupalreadyTextView = findViewById(R.id.alreadyuser);




        //onclick listeners
        signupalreadyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signupActivity.this, loginActivity.class));
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = UsernameditText.getText().toString();
                String email = EmailEditText.getText().toString();
                String password = PasswordEditText.getText().toString();
                String confirmpassword = PasswordconfirmEditText.getText().toString();

                Database db = new Database(getApplicationContext());

                if (username.length() == 0 || password.length() == 0 || email.length() == 0 || confirmpassword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Fill in details", Toast.LENGTH_SHORT).show();
                } else {
                    if (password.compareTo(confirmpassword) == 0) {
                        if (isValid(password)) {
                            db.register(username,email,password);

                            Toast.makeText(getApplicationContext(), "Record Valadated", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signupActivity.this, loginActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(), "Password should contain numbers and symbols", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    }

                }
            }


        });
    }


    //validating password

    public static boolean isValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordhere.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < passwordhere.length(); p++) {
                if (Character.isDigit(passwordhere.charAt(p))) {
                    f1 = 1;


                }
            }
            for (int r = 0; r < passwordhere.length(); r++) {
                if (Character.isLetter(passwordhere.charAt(r))) {
                    f2 = 1;

                }
            }
            for (int s = 0; s < passwordhere.length(); s++) {
                char c = passwordhere.charAt(s);
                if (c >= 33 && c <= 46 || c == 64) {
                    f3 = 1;

                }
            }
            if (f1 == 1 && f2 == 2 && f3 == 1)
                return true;
            return false;
        }


    }
}
