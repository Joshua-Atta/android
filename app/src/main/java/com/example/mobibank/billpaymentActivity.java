package com.example.mobibank;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class billpaymentActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_billpayment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void onElectricityBillClick(View view) {
        String url = "https://powerapp.ecg.com.gh";
        checkInternetAndOpenWebsite(url);
    }

    public void onWaterBillClick(View view) {
        String url = "https://www.businessghana.com";
        checkInternetAndOpenWebsite(url);
    }

    public void onInternetChargesClick(View view) {
        String url = "https://momo.mtn.com";
        checkInternetAndOpenWebsite(url);
    }

    // Handle the back button click
    public void onBackButtonClick(View view) {
        finish();
    }

    // Method to check internet connection and open website
    private void checkInternetAndOpenWebsite(String url) {
        if (isConnectedToInternet()) {
            openWebsite(url);
        } else {
            showNoInternetDialog(); // Show a pop-up if there's no internet
        }
    }
    // Method to open website
    // Method to open a website in the browser
    private void openWebsite(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(this, "Unable to open the website", Toast.LENGTH_SHORT).show();
        }
    }


    // Method to check if the device is connected to the internet
    private boolean isConnectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    // Method to display a pop-up when there's no internet
    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }
}





