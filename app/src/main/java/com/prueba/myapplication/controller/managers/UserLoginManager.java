package com.prueba.myapplication.controller.managers;

import android.content.Context;
import android.util.Log;

import com.prueba.myapplication.controller.activities.login.LoginCallback;
import com.prueba.myapplication.controller.services.UserService;
import com.prueba.myapplication.model.User;
import com.prueba.myapplication.model.UserToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginManager {
    private static UserLoginManager ourInstance;
    private UserToken userToken;
    private UserService userService;
    private Context context;
    private String bearerToken;
    List<User> user;

    private UserLoginManager(Context context) {
        this.context = context;
    }

    public static UserLoginManager getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = new UserLoginManager(context);
        }

        ourInstance.context = context;
        return ourInstance;
    }

    public synchronized void performLogin(String username, String password, final LoginCallback loginCallback){
        Call<UserToken> call =  UserTokenManager.getInstance(context).getUserToken(username, password);

        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                Log.i("UserLoginManager ", " performtaks->call.enqueue->onResponse res: " + response.body());
                userToken = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    bearerToken = "Bearer " + userToken.getAccessToken();
                    loginCallback.onSuccess(userToken);
                } else {
                    loginCallback.onFailure(new Throwable("ERROR " + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                Log.e("UserLoginManager ", " performtaks->call.enqueue->onResponse err: " + t.toString());
                loginCallback.onFailure(t);
            }
        });
    }







    public UserToken getUserToken(){
        return userToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }
}