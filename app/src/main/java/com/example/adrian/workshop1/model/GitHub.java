package com.example.adrian.workshop1.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Adrian on 4/6/2017.
 */

public interface GitHub {
    @GET("/")
    Call<LoginData> checkAuth(@Header("Authorization") String auth);

    @GET("/user")
    Call<ProfileData> getProfile(@Header("Authorization") String auth);

    @GET("/user/repos")
    Call<List<RepositoryData>> getRepositories(@Header("Authorization") String auth);

    class Service {
        private static GitHub sInstance;

        public static GitHub Get() {
            if(sInstance == null) {
                sInstance = new Retrofit.Builder().baseUrl("https://api.github.com")
                        .addConverterFactory(GsonConverterFactory.create()).build().create(GitHub.class);
            }
            return sInstance;
        }
    }
}
