package com.rxjavasample;

import com.rxjavasample.model.GitHubRepo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saranraj.d on 3/6/2018.
 */

public interface GitHubService {
    @GET("users/{user}/starred")
//    Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);
    Observable<Response<ResponseBody>> getStarredRepositories(@Path("user") String userName);
}
