package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class TipovehiculoDTO {
    private Integer id_tipvehiculo;
    private String nom_tipvehiculo;
    private String tarifa_hora;
    private String tarifa_dia;

    public  TipovehiculoDTO() {
    }

    public TipovehiculoDTO(Integer id_tipvehiculo, String nom_tipvehiculo) {
        this.id_tipvehiculo = id_tipvehiculo;
        this.nom_tipvehiculo = nom_tipvehiculo;
    }

    public TipovehiculoDTO(Integer id_tipvehiculo, String nom_tipvehiculo, String tarifa_hora, String tarifa_dia) {
        this.id_tipvehiculo = id_tipvehiculo;
        this.nom_tipvehiculo = nom_tipvehiculo;
        this.tarifa_hora = tarifa_hora;
        this.tarifa_dia = tarifa_dia;
    }

    public TipovehiculoDTO(Integer id_tipvehiculo) {
        this.id_tipvehiculo = id_tipvehiculo;
    }

    public Integer getId_tipvehiculo() {
        return id_tipvehiculo;
    }

    public void setId_tipvehiculo(Integer id_tipvehiculo) {
        this.id_tipvehiculo = id_tipvehiculo;
    }

    public String getNom_tipvehiculo() {
        return nom_tipvehiculo;
    }

    public void setNom_tipvehiculo(String nom_tipvehiculo) {
        this.nom_tipvehiculo = nom_tipvehiculo;
    }

    public String getTarifa_hora() {
        return tarifa_hora;
    }

    public void setTarifa_hora(String tarifa_hora) {
        this.tarifa_hora = tarifa_hora;
    }

    public String getTarifa_dia() {
        return tarifa_dia;
    }

    public void setTarifa_dia(String tarifa_dia) {
        this.tarifa_dia = tarifa_dia;
    }

    @NonNull
    @Override
    public String toString() {
        return id_tipvehiculo + " - " + nom_tipvehiculo+ " - " + tarifa_hora+ " - " + tarifa_dia;
    }
}
