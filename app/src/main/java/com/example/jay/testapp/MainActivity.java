package com.example.jay.testapp;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    EditText EmailET,NameET;
    String Email,Name;
    Button SubmitButton;

    // Constants
    // Content provider authority
    public static final String AUTHORITY = "com.example.jay.testapp.provider";
    // Account type
    public static final String ACCOUNT_TYPE = "testapp.example.com";
    // Account
    public static final String ACCOUNT = "default_account";
    // Instance fields
    Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * Create the dummy account. The code for CreateSyncAccount
         * is listed in the lesson Creating a Sync Adapter
         */
        mAccount = CreateSyncAccount(this);

        EmailET = (EditText)findViewById(R.id.EmailET);
        NameET = (EditText)findViewById(R.id.NameET);

        SubmitButton = (Button)findViewById(R.id.SubmitButton);

        Email = EmailET.getText().toString();
        Name = NameET.getText().toString();

        /**
         * Respond to a button click by calling requestSync(). This is an
         * asynchronous operation.
         *
         * This method is attached to the refresh button in the layout
         * XML file
         *
         * @param v The View associated with the method call,
         * in this case a Button
         */

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass the settings flags by inserting them in a bundle
                Bundle settingsBundle = new Bundle();
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
                settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);

                /*
                * Request the sync for the default account, authority, and
                * manual sync settings
                */
                ContentResolver.requestSync(mAccount, AUTHORITY, settingsBundle);
            }
        });
    }

    /**
     * Create a new dummy account for the sync adapter
     *
     * @param context The application context
     */
    public static Account CreateSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
        } else {
            Toast.makeText(context,"Error Occured",Toast.LENGTH_SHORT).show();
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }
        return newAccount;
    }
}