package com.prueba.myapplication.controller.activities.master_detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.prueba.myapplication.R;
import com.prueba.myapplication.controller.activities.main.MainActivity;
import com.prueba.myapplication.controller.managers.PlayerManager;
import com.prueba.myapplication.controller.managers.UserManager;
import com.prueba.myapplication.model.Apuesta;
import com.prueba.myapplication.model.ApuestaRealizada;
import com.prueba.myapplication.model.User;

import java.util.List;

/**
 * Created by usu27 on 14/3/16.
 */
public class ApuestasResumeActivity extends AppCompatActivity implements PlayerCallback, UserCallBack {
    TextView team, cuota;
    SeekBar pasta;
    EditText pastaEdit;
    Button placeBet;
    static String nameGame, game, hTeamName, aTeamName, hTeamNameB, aTeamNameB;
    static Integer hTeam = 0, aTeam = 0, hTeamB = 0, aTeamB = 0, aTeamBV = 0;

    Integer saldo = MainActivity.userInfos.getSaldo().intValue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apuestaresume);

        final Apuesta a = (Apuesta) getIntent().getSerializableExtra("apuesta");
        pasta = (SeekBar) findViewById(R.id.seekBar);
        pasta.setMax(saldo);
        pasta.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                pastaEdit.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        placeBet = (Button) findViewById(R.id.apostaR);
        pastaEdit = (EditText) findViewById(R.id.pastaEdit);

        team = (TextView) findViewById(R.id.team);
        cuota = (TextView) findViewById(R.id.cuota);

        team.setText("Result " + a.getaApostarName());
        cuota.setText("Odd : " + a.getaApostarOdd().toString());
        placeBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = a.getApuestaName().length() - 1; i > 0; i--) {

                    if (a.getApuestaName().charAt(i) == '-') {

                        a.setApuestaName(a.getApuestaName().substring(0, i - 1));

                        break;
                    }

                }

                if (a.getApuestaName().contains("@")) {


                    game = a.getApuestaName();

                    for (int i = 1; i < game.length(); i++) {
                        if (game.charAt(i) == '(' && game.charAt(i - 1) == ' ') {
                            hTeamB = i;
                            break;
                        }
                    }
                    for (int i = game.length() - 1; i > 0; i--) {

                        if (game.charAt(i) == '(' && game.charAt(i - 1) == ' ') {
                            aTeamBV = i;

                        }
                        if (game.charAt(i) == '@' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                            aTeamB = i;
                            break;
                        }

                    }


                    hTeamNameB = game.substring(0, hTeamB - 1);
                    aTeamNameB = game.substring(aTeamB + 2, aTeamBV - 1);

                    a.setApuestaName(aTeamNameB + " v " + hTeamNameB);

                    if (a.getLigaName().contains("MLB")) {


                        for (int i = 0; i < hTeamNameB.length() - 1; i++) {
                            if (hTeamNameB.charAt(i) == ' ') {
                                hTeamB = i + 1;
                                break;
                            }
                        }

                        for (int i = 0; i < aTeamNameB.length() - 1; i++) {
                            if (aTeamNameB.charAt(i) == ' ') {
                                aTeamB = i + 1;
                                break;
                            }
                        }


                        hTeamNameB = hTeamNameB.substring(hTeamB, hTeamNameB.length());
                        aTeamNameB = aTeamNameB.substring(aTeamB, aTeamNameB.length());

                        a.setApuestaName(aTeamNameB + " v " + hTeamNameB);

                        if (a.getaApostarName().contains(hTeamNameB)) {


                            a.setaApostarName(hTeamNameB);
                        } else {


                            a.setaApostarName(aTeamNameB);
                        }


                    }


                    for (int i = a.getApuestaName().length() - 1; i > 0; i--) {

                        if (a.getApuestaName().charAt(i) == '@') {
                            StringBuilder usBets = new StringBuilder(a.getApuestaName());
                            usBets.setCharAt(i, 'v');
                            a.setApuestaName(String.valueOf(usBets));

                            break;
                        }

                    }


                }

                if (a.getTipoDeporte().contains("Tennis")) {
                    String p1 = "";
                    String p2 = "";
                    boolean si = true;
                    int firstGap = 0;
                    String apName = a.getApuestaName().toString();
                    for (int i = apName.length() - 1; i > 0; i--) {

                        if (apName.charAt(i) == ' ') {
                            p2 = apName.substring(i, apName.length());

                            break;
                        }

                    }
                    for (int i = apName.length() - 1; i > 0; i--) {

                        if (apName.charAt(i) == ' ') {
                            p2 = (apName.substring(i + 1, apName.length()));

                            break;
                        }

                    }

                    for (int i = 1; i < apName.length() - 1; i++) {


                        if (apName.charAt(i) == ' ' && si == true) {
                            firstGap = i;
                            si = false;
                            System.out.println(i + "o");
                            System.out.println(si);
                        }

                        if (apName.charAt(i - 1) == ' ' && apName.charAt(i) == 'v' && apName.charAt(i + 1) == ' ') {
                            System.out.println(i + "h" + firstGap);

                            p1 = (apName.substring(firstGap + 1, i - 1));
                            break;

                        }
                    }
                    apName = p1 + " v " + p2;
                    a.setApuestaName(apName);

                    if (a.getaApostarName().contains(p1)) {
                        if (a.getLigaName().contains("MLB")) {
                            p1 = hTeamNameB;
                        }

                        a.setaApostarName(p1);
                    } else {
                        if (a.getLigaName().contains("MLB")) {
                            p2 = aTeamNameB;
                        }

                        a.setaApostarName(p2);
                    }


                }

                if (a.getTipoDeporte().contains("US") || a.getTipoDeporte().contains("NHL")) {

                    game = a.getApuestaName();

                    for (int i = 1; i < game.length(); i++) {
                        if (game.charAt(i) == 'v' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                            hTeam = i;
                            break;
                        }
                    }
                    for (int i = game.length() - 1; i > 0; i--) {
                        if (game.charAt(i) == 'v' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                            aTeam = i;
                            break;
                        }
                    }


                    hTeamName = game.substring(0, hTeam - 1);
                    aTeamName = game.substring(aTeam + 2, game.length());

                    a.setApuestaName(aTeamName + " v " + hTeamName);
                }


                Double saldoActual = saldo - Double.valueOf(String.valueOf(pastaEdit.getText()));
                UserManager.getInstance(v.getContext()).modificarSaldoUser(ApuestasResumeActivity.this, saldoActual);
                ApuestaRealizada apuestaRealizada = new ApuestaRealizada();
                apuestaRealizada.setCantidadApostada(Double.valueOf(String.valueOf(pastaEdit.getText())));
                apuestaRealizada.setCuota(a.getaApostarOdd());
                apuestaRealizada.setEventoApostado(a.getApuestaName());
                apuestaRealizada.setGanadorApuesta(a.getaApostarName());
                apuestaRealizada.setEstado(false);
                PlayerManager.getInstance(v.getContext()).createApuesta(ApuestasResumeActivity.this, apuestaRealizada);
                Intent i = new Intent(v.getContext(), MainActivity.class); // intent en fragments
                i.putExtra("nombreLeague", a.getLigaName());
                startActivity(i);
            }
        });
    }


    @Override
    public void onSuccess(List<Apuesta> apuestaList) {

    }

    @Override
    public void onSuccessTop(List<ApuestaRealizada> apuestaList) {

    }

    @Override
    public void onSuccess1(List<Apuesta> apuestaList) {

    }

    @Override
    public void onSuccess(User userInfo) {

        MainActivity.userInfos.setSaldo(userInfo.getSaldo());

    }


    @Override
    public void onFailure(Throwable t) {

    }

    public static Apuesta modificarApuesta(Apuesta a) {
        for (int i = a.getApuestaName().length() - 1; i > 0; i--) {

            if (a.getApuestaName().charAt(i) == '-') {

                a.setApuestaName(a.getApuestaName().substring(0, i - 1));

                break;
            }

        }

        if (a.getApuestaName().contains("@")) {


            game = a.getApuestaName();
            if (a.getLigaName().contains("MLB")) {
                for (int i = 1; i < game.length(); i++) {
                    if (game.charAt(i) == '(' && game.charAt(i - 1) == ' ') {
                        hTeamB = i;
                        break;
                    }
                }
                for (int i = game.length() - 1; i > 0; i--) {

                    if (game.charAt(i) == '(' && game.charAt(i - 1) == ' ') {
                        aTeamBV = i;

                    }
                    if (game.charAt(i) == '@' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                        aTeamB = i;
                        break;
                    }

                }


                hTeamNameB = game.substring(0, hTeamB - 1);
                aTeamNameB = game.substring(aTeamB + 2, aTeamBV - 1);

                a.setApuestaName(aTeamNameB + " v " + hTeamNameB);


                for (int i = 0; i < hTeamNameB.length() - 1; i++) {
                    if (hTeamNameB.charAt(i) == ' ') {
                        hTeamB = i + 1;
                        break;
                    }
                }

                for (int i = 0; i < aTeamNameB.length() - 1; i++) {
                    if (aTeamNameB.charAt(i) == ' ') {
                        aTeamB = i + 1;
                        break;
                    }
                }


                hTeamNameB = hTeamNameB.substring(hTeamB, hTeamNameB.length());
                aTeamNameB = aTeamNameB.substring(aTeamB, aTeamNameB.length());

                a.setApuestaName(aTeamNameB + " v " + hTeamNameB);

                if (a.getaApostarName().contains(hTeamNameB)) {


                    a.setaApostarName(hTeamNameB);
                } else {


                    a.setaApostarName(aTeamNameB);
                }


            }


            for (int i = a.getApuestaName().length() - 1; i > 0; i--) {

                if (a.getApuestaName().charAt(i) == '@') {
                    StringBuilder usBets = new StringBuilder(a.getApuestaName());
                    usBets.setCharAt(i, 'v');
                    a.setApuestaName(String.valueOf(usBets));

                    break;
                }

            }


        }

        if (a.getTipoDeporte().contains("Tennis")) {
            String p1 = "";
            String p2 = "";
            boolean si = true;
            int firstGap = 0;
            String apName = a.getApuestaName().toString();
            for (int i = apName.length() - 1; i > 0; i--) {

                if (apName.charAt(i) == ' ') {
                    p2 = apName.substring(i, apName.length());

                    break;
                }

            }
            for (int i = apName.length() - 1; i > 0; i--) {

                if (apName.charAt(i) == ' ') {
                    p2 = (apName.substring(i + 1, apName.length()));

                    break;
                }

            }

            for (int i = 1; i < apName.length() - 1; i++) {


                if (apName.charAt(i) == ' ' && si == true) {
                    firstGap = i;
                    si = false;
                    System.out.println(i + "o");
                    System.out.println(si);
                }

                if (apName.charAt(i - 1) == ' ' && apName.charAt(i) == 'v' && apName.charAt(i + 1) == ' ') {
                    System.out.println(i + "h" + firstGap);

                    p1 = (apName.substring(firstGap + 1, i - 1));
                    break;

                }
            }
            apName = p1 + " v " + p2;
            a.setApuestaName(apName);

            if (a.getaApostarName().contains(p1)) {
                if (a.getLigaName().contains("MLB")) {
                    p1 = hTeamNameB;
                }

                a.setaApostarName(p1);
            } else {
                if (a.getLigaName().contains("MLB")) {
                    p2 = aTeamNameB;
                }

                a.setaApostarName(p2);
            }


        }

        if (a.getTipoDeporte().contains("US") || a.getTipoDeporte().contains("NHL")) {

            game = a.getApuestaName();

            for (int i = 1; i < game.length(); i++) {
                if (game.charAt(i) == 'v' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                    hTeam = i;
                    break;
                }
            }
            for (int i = game.length() - 1; i > 0; i--) {
                if (game.charAt(i) == 'v' && game.charAt(i + 1) == ' ' && game.charAt(i - 1) == ' ') {
                    aTeam = i;
                    break;
                }
            }


            hTeamName = game.substring(0, hTeam - 1);
            aTeamName = game.substring(aTeam + 2, game.length());

            a.setApuestaName(aTeamName + " v " + hTeamName);
        }
        return a;
    }

}
