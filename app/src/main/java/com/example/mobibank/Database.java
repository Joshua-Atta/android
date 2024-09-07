package com.example.mobibank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Database extends SQLiteOpenHelper {

    // Database constructor
    public Database(Context context) {
        super(context, "MoBIBANK", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create users table
        String qry1 = "CREATE TABLE users(username TEXT, email TEXT, password TEXT)";
        sqLiteDatabase.execSQL(qry1);

        // Create transfers table to store deposits and transfers
        String qry2 = "CREATE TABLE transfers(sender TEXT, recipient TEXT, amount REAL)";
        sqLiteDatabase.execSQL(qry2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Handle database upgrade logic if needed in the future
    }

    // Register new user
    public void register(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);

        SQLiteDatabase db = getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    // Login functionality
    public int login(String username, String password) {
        int result = 0;
        String[] str = {username, password};
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        c.close();
        db.close();
        return result;
    }

    // Transfer money between users (sender to recipient)
    public void transferMoney(String sender, String recipient, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Add transfer details to ContentValues
        contentValues.put("sender", sender);
        contentValues.put("recipient", recipient);
        contentValues.put("amount", amount);

        // Insert into the transfers table
        long result = db.insert("transfers", null, contentValues);
        db.close();

        // Optionally, check if the insert was successful
        if (result == -1) {
            System.out.println("Transfer failed");
        } else {
            System.out.println("Transfer successful");
        }
    }

    // Deposit money functionality
    public void depositMoney(String recipient, double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        // Set the sender as 'Bank' (or another placeholder for the sender)
        contentValues.put("sender", "Bank");
        contentValues.put("recipient", recipient);
        contentValues.put("amount", amount);

        // Insert into the transfers table
        long result = db.insert("transfers", null, contentValues);
        db.close();

        //check if the insert was successful
        if (result == -1) {
            System.out.println("Deposit failed");
        } else {
            System.out.println("Deposit successful");
        }
    }

    // Check if device is connected to the internet
    public boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public Cursor getLatestTransfer() {

        SQLiteDatabase db = this.getReadableDatabase();

        // Query to get the latest transfer
        String query = "SELECT * FROM transfers ORDER BY ROWID DESC LIMIT 1";

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        return cursor; // Return the cursor containing the latest transfer data

    }
}
