package com.example.veterinaria.controller;
import org.springframework.web.bind.annotation.*;
import com.example.veterinaria.service.FacturacionService;
import com.example.veterinaria.model.Factura;
import java.util.List;


@RestController
public class FacturacionController {
    private final FacturacionService facturacionService;

    public FacturacionController(FacturacionService facturacionService) {
        this.facturacionService = facturacionService;
    }
    @GetMapping("/facturas")
    public List<Factura> getFacturas() {
        return facturacionService.generacionFacturas();
    }
    @GetMapping("/facturas/{id}")
    public Factura getFactura(@PathVariable int id) {
        List<Factura> facturas = facturacionService.generacionFacturas();
        for (Factura factura : facturas) {
            if (factura.getId() == id) {
                return factura;
            }
        }
       return null; 
    }    

}
