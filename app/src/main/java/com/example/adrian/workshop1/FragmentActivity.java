package com.example.adrian.workshop1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.example.adrian.workshop1.model.RepositoryData;

/**
 * Created by Adrian on 4/25/2017.
 */

public class FragmentActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from profil.xml
        setContentView(R.layout.fragment_portret);

        RepositoryData repository = new RepositoryData();
        repository.setDescription(this.getIntent().getStringExtra("description"));
        repository.setPrivate(!this.getIntent().getBooleanExtra("public_repo", true));
        repository.setUrl(this.getIntent().getStringExtra("url"));
        repository.setHtmlUrl(this.getIntent().getStringExtra("html_url"));
        Fragment details = RepositoryDetailsFragment.New(repository);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_portret, details).commit();
    }
}