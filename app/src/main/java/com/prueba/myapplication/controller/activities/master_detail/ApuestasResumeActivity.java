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
        TextView team,cuota;
        SeekBar pasta;
        EditText pastaEdit;
        Button placeBet;
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

        team.setText("Result "+a.getaApostarName());
        cuota.setText("Odd : "+a.getaApostarOdd().toString());
        placeBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Double  saldoActual  = saldo-Double.valueOf(String.valueOf(pastaEdit.getText()));
                UserManager.getInstance(v.getContext()).modificarSaldoUser(ApuestasResumeActivity.this, saldoActual);
                ApuestaRealizada apuestaRealizada = new ApuestaRealizada();
                apuestaRealizada.setCantidadApostada(Double.valueOf(String.valueOf(pastaEdit.getText())));
                apuestaRealizada.setCuota(a.getaApostarOdd());
                apuestaRealizada.setEventoApostado(a.getApuestaName());
                apuestaRealizada.setGanadorApuesta(a.getaApostarName());
                apuestaRealizada.setEstado(false);
                PlayerManager.getInstance(v.getContext()).createApuesta(ApuestasResumeActivity.this, apuestaRealizada);
                Intent i = new Intent(v.getContext(), MainActivity.class); // intent en fragments
                i.putExtra("nombreLeague",a.getLigaName());
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
}
