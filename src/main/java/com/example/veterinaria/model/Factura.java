package com.example.veterinaria.model;
import java.util.List;

public class Factura {
    private int id;
    private String razon_social;
    private String cliente;
    private List<Servicio> servicios;
    private int total;

    public Factura(int id, String razon_social, String cliente, List<Servicio> servicios, int total) {
        this.id = id;
        this.razon_social = razon_social;
        this.cliente = cliente;
        this.servicios = servicios;
        this.total = total;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getRazon_social() {
        return razon_social;
    }
    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }
    public String getCliente() {
        return cliente;
    }
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    public List<Servicio> getServicios() {
        return servicios;
    }
    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    
}
