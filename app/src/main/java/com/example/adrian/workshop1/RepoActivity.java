package com.example.adrian.workshop1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.adrian.workshop1.model.Repository;

import java.util.List;

/**
 * Created by Adrian on 4/4/2017.
 */

public class RepoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repository);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Adapter adapter = new Adapter();
        adapter.setData(Repository.getsMockRepository());
        mRecyclerView.setAdapter(adapter);
    }

    static class Adapter extends RecyclerView.Adapter {
        List<Repository> mData;

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

        public void setData( List<Repository> mData) {
            this.mData = mData;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView mCountWatcher;
            private final LinearLayout mTopics;
            private final TextView mName;

            public ViewHolder(View itemView) {
                super(itemView);
                mCountWatcher = (TextView) itemView.findViewById(R.id.repo_number);
                mTopics = (LinearLayout) itemView.findViewById(R.id.Topics);
                mName = (TextView) itemView.findViewById(R.id.name);
            }

            public void bind(Repository repository) {
                mCountWatcher.setText(String.valueOf(repository.getmWatchersCount()));
                mName.setText(itemView.getContext().getString(R.string.name_owner, repository.getmName(), repository.getmOwner()));
            }
        }
    }
}
