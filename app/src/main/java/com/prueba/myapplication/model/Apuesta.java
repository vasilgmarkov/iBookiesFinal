package com.prueba.myapplication.model;

import java.io.Serializable;

/**
 * Created by Alfredo on 28/02/2016.
 */
public class Apuesta implements Serializable {
    String ligaName;
    String apuestaName;
    Double aApostarOdd;
    String aApostarName;
    String tipoDeporte;
    String partidoTime;
    String partidoStartDate;
    Long id;

    public Apuesta() {
    }

    public Apuesta(String liga, String apuestaName, Double aApostarOdd, String aApostarName, String tipoDeporte, String partidoTime, String partidoStartDate, Long id) {
        this.ligaName = liga;
        this.apuestaName = apuestaName;
        this.aApostarOdd = aApostarOdd;
        this.aApostarName = aApostarName;
        this.tipoDeporte = tipoDeporte;
        this.partidoTime = partidoTime;
        this.partidoStartDate = partidoStartDate;
        this.id=id;
    }

    public String getLigaName() {
        return ligaName;
    }

    public void setLigaName(String ligaName) {
        this.ligaName = ligaName;
    }

    public String getApuestaName() {
        return apuestaName;
    }

    public void setApuestaName(String apuestaName) {
        this.apuestaName = apuestaName;
    }

    public Double getaApostarOdd() {
        return aApostarOdd;
    }

    public void setaApostarOdd(Double aApostarOdd) {
        this.aApostarOdd = aApostarOdd;
    }

    public String getaApostarName() {
        return aApostarName;
    }

    public void setaApostarName(String aApostarName) {
        this.aApostarName = aApostarName;
    }

    public String getTipoDeporte() {
        return tipoDeporte;
    }

    public void setTipoDeporte(String tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartidoTime() {
        return partidoTime;
    }

    public void setPartidoTime(String partidoTime) {
        this.partidoTime = partidoTime;
    }

    public String getPartidoStartDate() {
        return partidoStartDate;
    }

    public void setPartidoStartDate(String partidoStartDate) {
        this.partidoStartDate = partidoStartDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Apuesta apuesta = (Apuesta) o;

        if (ligaName != null ? !ligaName.equals(apuesta.ligaName) : apuesta.ligaName != null)
            return false;
        if (apuestaName != null ? !apuestaName.equals(apuesta.apuestaName) : apuesta.apuestaName != null)
            return false;
        if (aApostarOdd != null ? !aApostarOdd.equals(apuesta.aApostarOdd) : apuesta.aApostarOdd != null)
            return false;
        if (aApostarName != null ? !aApostarName.equals(apuesta.aApostarName) : apuesta.aApostarName != null)
            return false;
        if (tipoDeporte != null ? !tipoDeporte.equals(apuesta.tipoDeporte) : apuesta.tipoDeporte != null)
            return false;
        if (partidoTime != null ? !partidoTime.equals(apuesta.partidoTime) : apuesta.partidoTime != null)
            return false;
        if (partidoStartDate != null ? !partidoStartDate.equals(apuesta.partidoStartDate) : apuesta.partidoStartDate != null)
            return false;
        return !(id != null ? !id.equals(apuesta.id) : apuesta.id != null);

    }

    @Override
    public int hashCode() {
        int result = ligaName != null ? ligaName.hashCode() : 0;
        result = 31 * result + (apuestaName != null ? apuestaName.hashCode() : 0);
        result = 31 * result + (aApostarOdd != null ? aApostarOdd.hashCode() : 0);
        result = 31 * result + (aApostarName != null ? aApostarName.hashCode() : 0);
        result = 31 * result + (tipoDeporte != null ? tipoDeporte.hashCode() : 0);
        result = 31 * result + (partidoTime != null ? partidoTime.hashCode() : 0);
        result = 31 * result + (partidoStartDate != null ? partidoStartDate.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Apuesta{" +
                "ligaName='" + ligaName + '\'' +
                ", apuestaName='" + apuestaName + '\'' +
                ", aApostarOdd=" + aApostarOdd +
                ", aApostarName='" + aApostarName + '\'' +
                ", tipoDeporte='" + tipoDeporte + '\'' +
                ", partidoTime='" + partidoTime + '\'' +
                ", partidoStartDate='" + partidoStartDate + '\'' +
                ", id=" + id +
                '}';
    }
}
