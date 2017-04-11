package com.example.adrian.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.workshop1.model.GitHub;
import com.example.adrian.workshop1.model.LoginData;

import okhttp3.Credentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        if(pref.getString("login", null) != null) {
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
    }

    private void performLogin(String username, String password) {
        //  TODO: make a network call and authenticate the user
        final String authHash = Credentials.basic(username, password);
        Call<LoginData> callable = GitHub.Service.Get().checkAuth(authHash);

        callable.enqueue(new Callback<LoginData>() {
            @Override
            public void onResponse(Call<LoginData> call, Response<LoginData> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(PrimulActivity.this, "Login Succesfull!", Toast.LENGTH_SHORT).show();

                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(PrimulActivity.this);
                    pref.edit().putString("login", authHash).apply();
                    // Start Profil.class
                    Intent myIntent = new Intent(PrimulActivity.this, Profil.class);
                    startActivity(myIntent);
                    finish();
                } else {
                    Toast.makeText(PrimulActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                    mPassword.setError("Invalid Password");
                    mUsername.setError("Invalid Username");
                }
            }

            @Override
            public void onFailure(Call<LoginData> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PrimulActivity.this, "Internet problem", Toast.LENGTH_LONG).show();
            }
        });
    }
}
