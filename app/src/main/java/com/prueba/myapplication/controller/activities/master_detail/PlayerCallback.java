package com.prueba.myapplication.controller.activities.master_detail;

import com.prueba.myapplication.model.Apuesta;
import com.prueba.myapplication.model.ApuestaRealizada;

import java.util.List;

public interface PlayerCallback {

    void onSuccess(List<Apuesta> apuestaList);
    void onSuccessTop(List<ApuestaRealizada> apuestaList);
    void onSuccess1(List<Apuesta> apuestaList);
    void onFailure(Throwable t);
}
