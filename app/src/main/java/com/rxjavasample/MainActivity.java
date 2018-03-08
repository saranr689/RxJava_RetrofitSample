package com.rxjavasample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.rxjavasample.model.GitHubRepo;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TestAdapter testAdapter;
    private Subscription subscription;
    private String TAG ="Main_D";
    private List<GitHubRepo> gitHubRepos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.id_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        testAdapter = new TestAdapter(MainActivity.this, gitHubRepos);
        recyclerView.setAdapter(testAdapter);
        getStarredRepos("saranraj");


    }

    private void getStarredRepos(String username) {
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GitHubRepo>>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                    }

                    @Override public void onNext(List<GitHubRepo> gitHubRepos) {
                        Log.d(TAG, "In onNext()"+gitHubRepos.get(0).name);
                        testAdapter = new TestAdapter(MainActivity.this,gitHubRepos);
                        recyclerView.setAdapter(testAdapter);
                    }
                });
    }
}
