package com.example.adrian.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PrimulActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";
    private TextView mUsername;
    private TextView mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primul);

        Button button = (Button) findViewById(R.id.login_button);
        button.setOnClickListener(this);

        mUsername = (TextView) findViewById(R.id.username_text);
        mPassword = (TextView) findViewById(R.id.password_text);

        //  Login screen should have no action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        if(pref.getBoolean("login", false)) {
            Intent myIntent = new Intent(PrimulActivity.this, Profil.class);
            startActivity(myIntent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button:
                //  When the user tapped the button, retrieve the username and password and perform the login
                performLogin(mUsername.getText().toString(), mPassword.getText().toString());
                break;
        }
        Toast.makeText(this, "Login Succesfull!", Toast.LENGTH_LONG).show();
    }

    private void performLogin(String username, String password) {
        //  TODO: make a network call and authenticate the user
        if (!("".equals(password) || "".equals(username))) {
            Toast.makeText(this, "Login Succesfull!", Toast.LENGTH_SHORT).show();
            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
            pref.edit().putBoolean("login", true).apply();
            // Start Profil.class
            Intent myIntent = new Intent(PrimulActivity.this, Profil.class);
            startActivity(myIntent);
            finish();
        } else {
            if ("".equals(password))
                mPassword.setError("Invalid Password");
            if ("".equals(username))
                mUsername.setError("Invalid Username");
            Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
        }
    }
}
