package com.rxjavasample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rxjavasample.model.GitHubRepo;

import java.util.List;

/**
 * Created by saranraj.d on 3/6/2018.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestViewHolder> {
    private final Context context;
    private final List<GitHubRepo> gitHubRepos;

    public TestAdapter(Context context, List<GitHubRepo> gitHubRepos) {
        this.context = context;
        this.gitHubRepos = gitHubRepos;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.list_layout, null);
        TestViewHolder testViewHolder = new TestViewHolder(inflate);
        return testViewHolder;
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {

        holder.textView.setText(gitHubRepos.get(position).name);

    }

    @Override
    public int getItemCount() {
        return gitHubRepos.size();
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TestViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.id_name_tv);
        }
    }
}
