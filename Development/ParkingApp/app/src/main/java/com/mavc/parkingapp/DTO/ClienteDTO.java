package com.mavc.parkingapp.DTO;

import androidx.annotation.NonNull;

public class ClienteDTO {
    private Integer id_cliente;
    private String cedula;
    private String nombre;
    private String celular;
    private String direccion;
    private String email;

    public  ClienteDTO() {
    }

    public ClienteDTO(Integer id_cliente, String nombre) {
        this.id_cliente = id_cliente;
        this.nombre = nombre;
    }

    public ClienteDTO(Integer id_cliente, String cedula, String nombre, String celular, String direccion, String email) {
        this.id_cliente = id_cliente;
        this.cedula = cedula;
        this.nombre = nombre;
        this.celular = celular;
        this.direccion = direccion;
        this.email = email;
    }

    public ClienteDTO(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return id_cliente + " - " + cedula+ " - " + nombre+ " - " + celular+ " - " + direccion+ " - " + email;
    }
}
