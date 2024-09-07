package com.example.mobibank;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class loginActivity extends AppCompatActivity {
    private EditText usernameditText, emailEditText, passwordEditText;
    private Button loginButton;
    private TextView loginsignupTextView, loginforgetpaswordTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initialize

        usernameditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.password);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loginsignupTextView = findViewById(R.id.loginsignup);
        loginforgetpaswordTextView = findViewById(R.id.loginforgotpassword);

        //on click listeners

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameditText.getText().toString();
                String password =passwordEditText.getText().toString();
                String email =  emailEditText.getText().toString();
                Database db = new Database(getApplicationContext());
                if(username.length()==0 || password.length()==0){
                    Toast.makeText(getApplicationContext(), "Fill required fields", Toast.LENGTH_SHORT).show();
                }else {
                    if (db.login(username, password) == 1) {
                        Toast.makeText(getApplicationContext(), "succesfully loged in", Toast.LENGTH_SHORT).show();
                        //create a local storage
                        //create an object and a string called shared prefrences
                        SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.putString("username",username);
                        //to save our data with key and values
                        editor.apply();

                        startActivity(new Intent(loginActivity.this, createaccountActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid username and password", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        loginforgetpaswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, passwordresetActivity.class));
            }
        });
        loginsignupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(loginActivity.this, signupActivity.class));
            }
        });
    }
}