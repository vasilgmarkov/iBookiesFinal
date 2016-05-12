package com.prueba.myapplication.controller.services;

import com.prueba.myapplication.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by usu27 on 11/4/16.
 */
public interface UserService {

    @GET("api/users/{login}")
    Call<User> getLoginUser(


            @Header("Authorization") String Authorization,
            @Path("login") String login);



    @PUT("api/user/modificarSaldo/{saldo}")
    Call<User> modificarSaldoUser(


            @Header("Authorization") String Authorization,
            @Path("saldo") Double saldo);
    @POST("api/register")
    Call<Void> createUser(



            @Body User user);

}


