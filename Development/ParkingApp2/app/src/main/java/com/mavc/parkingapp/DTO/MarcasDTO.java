package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class MarcasDTO {
    private Integer id_marca;
    private String nom_marca;

    public MarcasDTO() {
    }

    public MarcasDTO(Integer id_marca, String nom_marca) {
        this.id_marca = id_marca;
        this.nom_marca = nom_marca;
    }

    public MarcasDTO(Integer id_marca) {
        this.id_marca = id_marca;
    }

    public Integer getId_marca() {
        return id_marca;
    }

    public void setId_marca(Integer id_marca) {
        this.id_marca = id_marca;
    }

    public String getNom_marca() {
        return nom_marca;
    }

    public void setNom_marca(String nom_marca) {
        this.nom_marca = nom_marca;
    }

    @NonNull
    @Override
    public String toString() {
        return id_marca + " - " + nom_marca;
    }
}
