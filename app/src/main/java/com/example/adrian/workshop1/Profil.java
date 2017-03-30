package com.example.adrian.workshop1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Adrian on 3/28/2017.
 */

class Profil extends Activity {

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
        htmlString="<u>Thu</u>, <u>May</u> <u>26</u>, <u>2015</u>";
        Created.setText(Html.fromHtml(htmlString));

        Updated = (TextView) findViewById(R.id.updated);
        htmlString="<u>Wed</u>, <u>Jun</u> <u>03</u>, <u>2016</u>";
        Updated.setText(Html.fromHtml(htmlString));

        PublicRepos = (TextView) findViewById(R.id.public_repo);
        htmlString="<u>0</u>";
        PublicRepos.setText(Html.fromHtml(htmlString));

        PrivateRepos = (TextView) findViewById(R.id.private_repo);
        htmlString="<u>0</u>";
        PrivateRepos.setText(Html.fromHtml(htmlString));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return true;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
