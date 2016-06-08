package com.prueba.myapplication.model;

import java.io.Serializable;

/**
 * Created by usu27 on 10/3/16.
 */
public class ApuestaRealizada implements Serializable {
            private Double  cantidadApostada;
            private Double  cuota;
            private String eventoApostado;
            private String ganadorApuesta;
            private Boolean estado;

    public ApuestaRealizada() {
    }

    public ApuestaRealizada(Double cantidadApostada, Double cuota, String eventoApostado, String ganadorApuesta) {
        this.cantidadApostada = cantidadApostada;
        this.cuota = cuota;
        this.eventoApostado = eventoApostado;
        this.ganadorApuesta = ganadorApuesta;
        this.estado=false;
    }

    public Double getCantidadApostada() {
        return cantidadApostada;
    }

    public void setCantidadApostada(Double cantidadApostada) {
        this.cantidadApostada = cantidadApostada;
    }

    public Double getCuota() {
        return cuota;
    }

    public void setCuota(Double cuota) {
        this.cuota = cuota;
    }

    public String getEventoApostado() {
        return eventoApostado;
    }

    public void setEventoApostado(String eventoApostado) {
        this.eventoApostado = eventoApostado;
    }

    public String getGanadorApuesta() {
        return ganadorApuesta;
    }

    public void setGanadorApuesta(String ganadorApuesta) {
        this.ganadorApuesta = ganadorApuesta;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApuestaRealizada that = (ApuestaRealizada) o;

        if (cantidadApostada != null ? !cantidadApostada.equals(that.cantidadApostada) : that.cantidadApostada != null)
            return false;
        if (cuota != null ? !cuota.equals(that.cuota) : that.cuota != null) return false;
        if (eventoApostado != null ? !eventoApostado.equals(that.eventoApostado) : that.eventoApostado != null)
            return false;
        return !(ganadorApuesta != null ? !ganadorApuesta.equals(that.ganadorApuesta) : that.ganadorApuesta != null);

    }

    @Override
    public int hashCode() {
        int result = cantidadApostada != null ? cantidadApostada.hashCode() : 0;
        result = 31 * result + (cuota != null ? cuota.hashCode() : 0);
        result = 31 * result + (eventoApostado != null ? eventoApostado.hashCode() : 0);
        result = 31 * result + (ganadorApuesta != null ? ganadorApuesta.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ApuestaRealizada{" +
                "cantidadApostada=" + cantidadApostada +
                ", cuota=" + cuota +
                ", eventoApostado='" + eventoApostado + '\'' +
                ", ganadorApuesta='" + ganadorApuesta + '\'' +
                '}';
    }
}
