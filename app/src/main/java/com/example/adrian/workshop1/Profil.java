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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adrian on 3/28/2017.
 */

public class Profil extends AppCompatActivity implements View.OnClickListener {

    private TextView Location;
    private TextView Email;
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
        String htmlString="<u>Bucharest</u>";
        Location.setText(Html.fromHtml(htmlString));

        Email = (TextView) findViewById(R.id.email);
        htmlString="<u>adrian.pandelica95@gmail.com</u>";
        Email.setText(Html.fromHtml(htmlString));

        Created = (TextView) findViewById(R.id.created);
        SpannableString content = new SpannableString("Thu, May 26, 2015");
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        Created.setText(content);

        Updated = (TextView) findViewById(R.id.updated);
        content = new SpannableString("Wed, Jun 03, 2016");
        content.setSpan(new UnderlineSpan(), 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        Updated.setText(content);

        PublicRepos = (TextView) findViewById(R.id.public_repo);
        htmlString="<u>0</u>";
        PublicRepos.setText(Html.fromHtml(htmlString));

        PrivateRepos = (TextView) findViewById(R.id.private_repo);
        htmlString="<u>0</u>";
        PrivateRepos.setText(Html.fromHtml(htmlString));

        findViewById(R.id.blog_button).setOnClickListener(this);
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
                pref.edit().putBoolean("login", false).apply();
                Intent myIntent = new Intent(Profil.this, PrimulActivity.class);
                startActivity(myIntent);
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

}
