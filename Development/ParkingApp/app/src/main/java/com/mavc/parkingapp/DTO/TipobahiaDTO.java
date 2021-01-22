package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class TipobahiaDTO {
    private String nom_tbahia;
    private Integer id_tbahia;

    public TipobahiaDTO() {
    }

    public TipobahiaDTO(Integer id_tbahia, String nom_tbahia) {
        this.id_tbahia = id_tbahia;
        this.nom_tbahia = nom_tbahia;
    }

    public Integer getId_tbahia() {
        return id_tbahia;
    }

    public void setId_tbahia(Integer id_tbahia) {
        this.id_tbahia = id_tbahia;
    }

    public String getNom_tbahia() {
        return nom_tbahia;
    }

    public void setNom_tbahia(String nom_tbahia) {
        this.nom_tbahia = nom_tbahia;
    }

    @NonNull
    @Override
    public String toString() {
        return id_tbahia + " - " + nom_tbahia;
    }
}
