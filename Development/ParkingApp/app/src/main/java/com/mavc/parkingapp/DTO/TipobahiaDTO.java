package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class TipobahiaDTO {
    private Integer id_tipbahia;
    private String nom_tipbahia;

    public  TipobahiaDTO() {
    }

    public TipobahiaDTO(Integer id_tipbahia, String nom_tipbahia) {
        this.id_tipbahia = id_tipbahia;
        this.nom_tipbahia = nom_tipbahia;
    }

    public Integer getId_tipbahia() {
        return id_tipbahia;
    }

    public void setId_tipbahia(Integer id_tipbahia) {
        this.id_tipbahia = id_tipbahia;
    }

    public String getNom_tipbahia() {
        return nom_tipbahia;
    }

    public void setNom_tipbahia(String nom_tipbahia) {
        this.nom_tipbahia = nom_tipbahia;
    }

    @NonNull
    @Override
    public String toString() {
        return id_tipbahia + " - " + nom_tipbahia;
    }
}
