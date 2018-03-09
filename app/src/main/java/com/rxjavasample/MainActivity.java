package com.rxjavasample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.rxjavasample.model.GitHubRepo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TestAdapter testAdapter;
    private Subscription subscription;
    private String TAG = "Main_D";
    private List<GitHubRepo> gitHubReposn = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.id_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        testAdapter = new TestAdapter(MainActivity.this, gitHubReposn);
        recyclerView.setAdapter(testAdapter);
        getStarredRepos("saranraj");


    }

    private void getStarredRepos(String username) {
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<ResponseBody>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                        HttpException error = (HttpException) e;
                        Log.d(TAG, "onError: " + error.response().toString());
                    }


                    @Override
                    public void onNext(Response<ResponseBody> gitHubRepos) {

                        Log.d(TAG, "onNext: " + gitHubRepos.code());
                        try {
                            gitHubReposn = Arrays.asList(new Gson().fromJson(gitHubRepos.body().string(),GitHubRepo[].class));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "In onNext()" + gitHubReposn.get(0).name);
                        testAdapter = new TestAdapter(MainActivity.this, gitHubReposn);
                        recyclerView.setAdapter(testAdapter);
                    }
                });
    }
/*    private void getStarredRepos(String username) {
        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override public void onCompleted() {
                        Log.d(TAG, "In onCompleted()");
                    }

                    @Override public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, "In onError()");
                        HttpException error = (HttpException)e;
                        Log.d(TAG, "onError: "+error.response().toString());
                    }

                    @Override public void onNext(ResponseBody responseBody) {

                        responseBody.
                        Log.d(TAG, "In onNext()"+gitHubRepos.get(0).name);
                        testAdapter = new TestAdapter(MainActivity.this,gitHubRepos);
                        recyclerView.setAdapter(testAdapter);
                    }
                });
    }*/
}
