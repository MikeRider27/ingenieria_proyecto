package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class VehiculoDTO {
    private String chapa;
    private String color;
    private TipovehiculoDTO tipovehiculoDTO;
    private MarcasDTO marcasDTO;
    private ClienteDTO cliente;

    public VehiculoDTO(String chapa) {
    }

    public VehiculoDTO(String chapa, String color, TipovehiculoDTO tipovehiculoDTO, MarcasDTO marcasDTO, ClienteDTO cliente) {
        this.chapa = chapa;
        this.color = color;
        this.tipovehiculoDTO = tipovehiculoDTO;
        this.marcasDTO = marcasDTO;
        this.cliente = cliente;
    }

    public String getChapa() {
        return chapa;
    }

    public void setChapa(String chapa) {
        this.chapa = chapa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TipovehiculoDTO getTipovehiculoDTO() {
        return tipovehiculoDTO;
    }

    public void setTipovehiculoDTO(TipovehiculoDTO tipovehiculoDTO) {
        this.tipovehiculoDTO = tipovehiculoDTO;
    }

    public MarcasDTO getMarcasDTO() {
        return marcasDTO;
    }

    public void setMarcasDTO(MarcasDTO marcasDTO) {
        this.marcasDTO = marcasDTO;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    @NonNull
    @Override
    public String toString() {
        return chapa+" - "+color+" - "+tipovehiculoDTO.getNom_tipvehiculo()+" - "+marcasDTO.getNom_marca()+" - "+cliente.getNombre();
    }
}
