package com.prueba.myapplication.controller.managers;

import android.content.Context;
import android.util.Log;

import com.prueba.myapplication.controller.activities.master_detail.PlayerCallback;
import com.prueba.myapplication.controller.services.PlayerService;
import com.prueba.myapplication.model.Apuesta;
import com.prueba.myapplication.model.ApuestaRealizada;
import com.prueba.myapplication.util.CustomProperties;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlayerManager {
    private static PlayerManager ourInstance;
    private List<Apuesta> apuestas,apuestas1x2,apuestasByleagueName;
    private List<ApuestaRealizada>topApuestas;
    private Retrofit retrofit;
    private Context context;
    private PlayerService playerService;

    private PlayerManager(Context cntxt) {
        context = cntxt;
        retrofit = new Retrofit.Builder()
                .baseUrl(CustomProperties.getInstance(context).get("app.baseUrl"))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        playerService = retrofit.create(PlayerService.class);
    }

    public static PlayerManager getInstance(Context cntxt) {
        if (ourInstance == null) {
            ourInstance = new PlayerManager(cntxt);
        }

        ourInstance.context = cntxt;

        return ourInstance;
    }

    public synchronized void getTopApuestas(final PlayerCallback playerCallback) {
        Call<List<ApuestaRealizada>> call = playerService.getTopApuestas(UserLoginManager.getInstance(context).getBearerToken());
        //Call<List<Apuesta>> call1 = playerService.getApuesta1x2(UserLoginManager.getInstance(context).getBearerToken(), "Wolfsberger v Salzburg - Match Betting");
        call.enqueue(new Callback<List<ApuestaRealizada>>() {
            @Override
            public void onResponse(Call<List<ApuestaRealizada>> call, Response<List<ApuestaRealizada>> response) {
                topApuestas = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    playerCallback.onSuccessTop(topApuestas);
                } else {
                    playerCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<ApuestaRealizada>> call, Throwable t) {
                Log.e("PlayerManager->", "getAllPlayers()->ERROR: " + t);

                playerCallback.onFailure(t);
            }
        });
    }
    //Pol y Vasil

    public synchronized void getApuesta1x2(final PlayerCallback playerCallback,String nameApuesta) {
       // Call<List<Apuesta>> call = playerService.getAllPlayer(UserLoginManager.getInstance(context).getBearerToken());
        Call<List<Apuesta>> call = playerService.getApuesta1x2(UserLoginManager.getInstance(context).getBearerToken(), nameApuesta);
        call.enqueue(new Callback<List<Apuesta>>() {
            @Override
            public void onResponse(Call<List<Apuesta>> call, Response<List<Apuesta>> response) {
                apuestas1x2 = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    playerCallback.onSuccess1(apuestas1x2);

                } else {
                    playerCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Apuesta>> call, Throwable t) {
                Log.e("PlayerManager->", "getAllPlayers()->ERROR: " + t);

                playerCallback.onFailure(t);
            }
        });
    }

    public synchronized void createApuesta(final PlayerCallback playerCallback,ApuestaRealizada apuesta) {
        // Call<List<Apuesta>> call = playerService.getAllPlayer(UserLoginManager.getInstance(context).getBearerToken());
        Call <ApuestaRealizada> call = playerService.createApuesta(UserLoginManager.getInstance(context).getBearerToken(), apuesta);
        call.enqueue(new Callback<ApuestaRealizada>() {
            @Override
            public void onResponse(Call<ApuestaRealizada> call, Response<ApuestaRealizada> response) {
              //  apuestas1x2 = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    //playerCallback.onSuccess1(apuestas1x2);
                    Log.e("Apuesta->", "Realizada: OOK" + 100);

                } else {
                    playerCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ApuestaRealizada> call, Throwable t) {
                Log.e("PlayerManager->", "getAllPlayers()->ERROR: " + t);

                playerCallback.onFailure(t);
            }
        });
    }

    public synchronized void getApuestasByleagueName(final PlayerCallback playerCallback,String leagueName) {
        // Call<List<Apuesta>> call = playerService.getAllPlayer(UserLoginManager.getInstance(context).getBearerToken());
        Call<List<Apuesta>> call = playerService.getApuestasByLeagueName(UserLoginManager.getInstance(context).getBearerToken(), leagueName);
        call.enqueue(new Callback<List<Apuesta>>() {
            @Override
            public void onResponse(Call<List<Apuesta>> call, Response<List<Apuesta>> response) {
                apuestasByleagueName = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    playerCallback.onSuccess(apuestasByleagueName);

                } else {
                    playerCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Apuesta>> call, Throwable t) {
                Log.e("PlayerManager->", "getAllPlayers()->ERROR: " + t);

                playerCallback.onFailure(t);
            }
        });
    }

    public synchronized void getApuestasByleagueNameBasket(final PlayerCallback playerCallback,String leagueName) {
        // Call<List<Apuesta>> call = playerService.getAllPlayer(UserLoginManager.getInstance(context).getBearerToken());
        Call<List<Apuesta>> call = playerService.getApuestasByLeagueNameBasket(UserLoginManager.getInstance(context).getBearerToken(), leagueName);
        call.enqueue(new Callback<List<Apuesta>>() {
            @Override
            public void onResponse(Call<List<Apuesta>> call, Response<List<Apuesta>> response) {
                apuestasByleagueName = response.body();

                int code = response.code();

                if (code == 200 || code == 201) {
                    playerCallback.onSuccess(apuestasByleagueName);

                } else {
                    playerCallback.onFailure(new Throwable("ERROR" + code + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<List<Apuesta>> call, Throwable t) {
                Log.e("PlayerManager->", "getAllPlayers()->ERROR: " + t);

                playerCallback.onFailure(t);
            }
        });
    }

    public Apuesta getPlayer(String id) {
        for (Apuesta apuesta : apuestasByleagueName) {
            if (apuesta.getId().toString().equals(id)) {
                return apuesta;
            }
        }

        return null;
    }

    public Apuesta getApuesta1x2func(int pos){



        return apuestas1x2.get(pos);

    }

}
