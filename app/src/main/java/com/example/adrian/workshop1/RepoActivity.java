package com.example.adrian.workshop1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adrian.workshop1.model.GitHub;
import com.example.adrian.workshop1.model.RepositoryData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adrian on 4/4/2017.
 */

public class RepoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private Adapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter();
        mRecyclerView.setAdapter(adapter);

        getRepositories();
    }

    private void getRepositories() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Call<List<RepositoryData>> callable = GitHub.Service.Get().getRepositories(pref.getString("login", null));

        callable.enqueue(new Callback<List<RepositoryData>>() {
            @Override
            public void onResponse(Call<List<RepositoryData>> call, Response<List<RepositoryData>> response) {
                if(response.isSuccessful()) {
                    adapter.setData(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(RepoActivity.this, "Couldn't fetch repos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<RepositoryData>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RepoActivity.this, "Internet problem", Toast.LENGTH_LONG).show();
            }
        });
    }

    static class Adapter extends RecyclerView.Adapter {
        List<RepositoryData> mData;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_element, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData != null ? mData.size() : 0;
        }

        public void setData( List<RepositoryData> mData) {
            this.mData = mData;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView mCountWatcher;
            private final LinearLayout mTopics;
            private final TextView mName;
            private final TextView mDescription;
            private final CheckBox mPublic;

            public ViewHolder(View itemView) {
                super(itemView);
                mCountWatcher = (TextView) itemView.findViewById(R.id.repo_number);
                mTopics = (LinearLayout) itemView.findViewById(R.id.Topics);
                mName = (TextView) itemView.findViewById(R.id.name);
                mPublic = (CheckBox) itemView.findViewById(R.id.check_public);
                mDescription = (TextView) itemView.findViewById(R.id.description_repo);
            }

            public void bind(RepositoryData repository) {
                mCountWatcher.setText(String.valueOf(repository.getWatchersCount()));
                mPublic.setChecked(!repository.getPrivate());
                if(repository.getDescription() != null) {
                    mDescription.setText(repository.getDescription());
                }
                mName.setText(itemView.getContext().getString(R.string.name_owner, repository.getName(), repository.getOwner().getLogin()));
            }
        }
    }
}
