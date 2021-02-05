package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class ZonaDTO {
    private Integer id_zona;
    private String nom_zona;

    public  ZonaDTO() {
    }

    public ZonaDTO(Integer id_zona, String nom_zona) {
        this.id_zona = id_zona;
        this.nom_zona = nom_zona;
    }

    public Integer getId_zona() {
        return id_zona;
    }

    public void setId_zona(Integer id_zona) {
        this.id_zona = id_zona;
    }

    public String getNom_zona() {
        return nom_zona;
    }

    public void setNom_zona(String nom_zona) {
        this.nom_zona = nom_zona;
    }

    @NonNull
    @Override
    public String toString() {
        return id_zona + " - " + nom_zona;
    }
}
