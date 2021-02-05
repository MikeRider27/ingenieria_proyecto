package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class BahiaDTO {
    private Integer id_bahia;
    private String nom_bahia;
    private TipobahiaDTO tipobahiaDTO;
    private ZonaDTO zonaDTO;

    public BahiaDTO() {
    }

    public BahiaDTO(Integer id_bahia, String nom_bahia, TipobahiaDTO tipobahiaDTO, ZonaDTO zonaDTO) {
        this.id_bahia = id_bahia;
        this.nom_bahia = nom_bahia;
        this.tipobahiaDTO = tipobahiaDTO;
        this.zonaDTO = zonaDTO;
    }

    public Integer getId_bahia() {
        return id_bahia;
    }

    public void setId_bahia(Integer id_bahia) {
        this.id_bahia = id_bahia;
    }

    public String getNom_bahia() {
        return nom_bahia;
    }

    public void setNom_bahia(String nom_bahia) {
        this.nom_bahia = nom_bahia;
    }

    public TipobahiaDTO getTipobahiaDTO() {
        return tipobahiaDTO;
    }

    public void setTipobahiaDTO(TipobahiaDTO tipobahiaDTO) {
        this.tipobahiaDTO = tipobahiaDTO;
    }

    public ZonaDTO getZonaDTO() {
        return zonaDTO;
    }

    public void setZonaDTO(ZonaDTO zonaDTO) {
        this.zonaDTO = zonaDTO;
    }

    @NonNull
    @Override
    public String toString() {
        return id_bahia+" - "+nom_bahia+" - "+tipobahiaDTO.getNom_tbahia()+" - "+zonaDTO.getNom_zona();
    }
}
