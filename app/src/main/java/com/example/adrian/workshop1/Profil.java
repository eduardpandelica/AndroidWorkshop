package com.example.adrian.workshop1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.workshop1.model.GitHub;
import com.example.adrian.workshop1.model.ProfileData;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adrian on 3/28/2017.
 */

public class Profil extends AppCompatActivity implements View.OnClickListener {

    private TextView Location;
    private TextView Email;
    private TextView Description;
    private TextView Name;
    private TextView Employee;
    private TextView Created;
    private TextView Updated;
    private TextView PublicRepos;
    private TextView PrivateRepos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from profil.xml
        setContentView(R.layout.profil);

        Location = (TextView) findViewById(R.id.location);
        Email = (TextView) findViewById(R.id.email);
        Name = (TextView) findViewById(R.id.name);
        Employee = (TextView) findViewById(R.id.employee);
        Description = (TextView) findViewById(R.id.description);
        Created = (TextView) findViewById(R.id.created);
        Updated = (TextView) findViewById(R.id.updated);
        PublicRepos = (TextView) findViewById(R.id.public_repo);
        PrivateRepos = (TextView) findViewById(R.id.private_repo);

        findViewById(R.id.blog_button).setOnClickListener(this);

        getProfile();
    }

    private void getProfile() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Call<ProfileData> callable = GitHub.Service.Get().getProfile(pref.getString("login", null));

        callable.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                if(response.isSuccessful()) {
                    updateUI(response.body());
                } else {
                    switch(response.code()) {
                        case 401:
                            Toast.makeText(Profil.this, "Couldn't fetch profile", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Toast.makeText(Profil.this, "Page not found", Toast.LENGTH_LONG).show();
                            break;
                        case 500:
                            Toast.makeText(Profil.this, "Internal server error", Toast.LENGTH_LONG).show();
                            break;
                        case 503:
                            Toast.makeText(Profil.this, "Service unavailable", Toast.LENGTH_LONG).show();
                            break;
                        case 550:
                            Toast.makeText(Profil.this, "Permission denied", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                t.printStackTrace();
                if(t instanceof UnknownHostException)
                    Toast.makeText(Profil.this, "Internet problem", Toast.LENGTH_LONG).show();
                if(t instanceof TimeoutException)
                    Toast.makeText(Profil.this, "Connection time expired", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI(ProfileData data) {
        underlined_text(Location, data.getLocation());

        underlined_text(Email, data.getEmail());

        if(data.getName() != null)
            Name.setText(data.getName());

        underlined_text(Employee, data.getCompany());

        underlined_text(Description, data.getBio());

        underlined_text(Created, data.getCreatedAt());

        underlined_text(Updated, data.getUpdatedAt());

        underlined_text(PublicRepos, data.getPublicRepos().toString());

        underlined_text(PrivateRepos, data.getTotalPrivateRepos().toString());
    }

    private void underlined_text(TextView v , String string) {
        if (string != null) {
            SpannableString content = new SpannableString(string);
            content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            v.setText(content);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.blog_button:
                Intent intent = new Intent(Profil.this, RepoActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                pref.edit().putString("login", null).apply();
                Intent myIntent = new Intent(Profil.this, PrimulActivity.class);
                startActivity(myIntent);
                finish();
                return true;
        }
        return false;
    }

}
