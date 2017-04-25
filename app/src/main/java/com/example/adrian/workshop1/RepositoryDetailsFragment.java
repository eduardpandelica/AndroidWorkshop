package com.example.adrian.workshop1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.adrian.workshop1.model.RepositoryData;


/**
 * A simple {@link Fragment} subclass.
 */
public class RepositoryDetailsFragment extends Fragment {

    private TextView mDescription;
    private TextView mPublic_repo;
    private TextView mURL;
    private TextView mHTML_URL;

    public static Fragment New(RepositoryData repository) {
        Fragment f = new RepositoryDetailsFragment();
        Bundle args = new Bundle();
        args.putString("description", repository.getDescription());
        args.putBoolean("public_repo", !repository.getPrivate());
        args.putString("url", repository.getUrl());
        args.putString("html_url", repository.getHtmlUrl());
        f.setArguments(args);
        return f;
    }

    public RepositoryDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDescription = (TextView)view.findViewById(R.id.description);
        mPublic_repo = (TextView)view.findViewById(R.id.public_repo);
        mHTML_URL = (TextView)view.findViewById(R.id.html_url);
        mURL = (TextView)view.findViewById(R.id.url);

        mDescription.setText(getArguments().getString("description"));
        mPublic_repo.setText(getArguments().getBoolean("public_repo") ? "Public" : "Private");
        mHTML_URL.setText(getArguments().getString("html_url"));
        mURL.setText(getArguments().getString("url"));

    }
}
