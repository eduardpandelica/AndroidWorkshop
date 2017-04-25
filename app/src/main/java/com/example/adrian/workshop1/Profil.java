package com.example.adrian.workshop1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
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
    DialogFragment newFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from profil.xml
        setContentView(R.layout.profil);

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
                    Toast.makeText(Profil.this, "Couldn't fetch profile", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(Profil.this, "Internet problem", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI(ProfileData data) {
        Location = (TextView) findViewById(R.id.location);
        underlined_text(Location, data.getLocation());

        Email = (TextView) findViewById(R.id.email);
        underlined_text(Email, data.getEmail());

        Name = (TextView) findViewById(R.id.name);
        if(data.getName() != null)
            Name.setText(data.getName());

        Employee = (TextView) findViewById(R.id.employee);
        underlined_text(Employee, data.getCompany());

        Description = (TextView) findViewById(R.id.description);
        underlined_text(Description, data.getBio());

        Created = (TextView) findViewById(R.id.created);
        underlined_text(Created, data.getCreatedAt());

        Updated = (TextView) findViewById(R.id.updated);
        underlined_text(Updated, data.getUpdatedAt());

        PublicRepos = (TextView) findViewById(R.id.public_repo);
        underlined_text(PublicRepos, data.getPublicRepos().toString());

        PrivateRepos = (TextView) findViewById(R.id.private_repo);
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
                showDialog();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setNegativeButton(R.string.alert_dialog_cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((Profil)getActivity()).doNegativeClick();
                                }
                            }
                    )
                    .setPositiveButton(R.string.alert_dialog_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    ((Profil)getActivity()).doPositiveClick();
                                }
                            }
                    )
                    .create();
        }
    }

    void showDialog() {
        newFragment = MyAlertDialogFragment.newInstance(
                R.string.alert_dialog_two_buttons_title);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public void doPositiveClick() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        pref.edit().putString("login", null).apply();
        Intent myIntent = new Intent(Profil.this, PrimulActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void doNegativeClick() {
        newFragment.dismiss();
    }
}
