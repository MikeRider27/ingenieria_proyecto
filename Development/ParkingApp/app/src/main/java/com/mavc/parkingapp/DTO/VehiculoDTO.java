package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class VehiculoDTO {
    private String chapa;
    private String color;
    private TipovehiculoDTO tipoVehiculo;
    private MarcasDTO marca;
    private ClienteDTO cliente;

    public VehiculoDTO() {
    }

    public VehiculoDTO(String chapa, String color, TipovehiculoDTO tipoVehiculo, MarcasDTO marca, ClienteDTO cliente) {
        this.chapa = chapa;
        this.color = color;
        this.tipoVehiculo = tipoVehiculo;
        this.marca = marca;
        this.cliente = cliente;
    }

    public VehiculoDTO(String chapa) {
        this.chapa = chapa;
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

    public TipovehiculoDTO getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(TipovehiculoDTO tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public MarcasDTO getMarca() {
        return marca;
    }

    public void setMarca(MarcasDTO marca) {
        this.marca = marca;
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
        return chapa+" - "+color+" - "+tipoVehiculo.getNom_tipvehiculo()+" - "+marca.getNom_marca()+" - "+cliente.getNombre();
    }
}
