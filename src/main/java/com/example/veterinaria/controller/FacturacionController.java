package com.example.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.veterinaria.service.FacturacionService;
import com.example.veterinaria.model.Factura;
import com.example.veterinaria.model.ResponseWrapper;

import java.util.List;


@RestController
@RequestMapping("/facturas")    
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;
   
    @GetMapping
    public List<Factura> getFacturas() {
        return facturacionService.obtenerTodas();
    }
    @GetMapping("/{id}")
    public Factura getFactura(@PathVariable int id) {
       return facturacionService.obtenerPorId(id);
    }    

    @PostMapping
    public ResponseEntity<ResponseWrapper<Factura>> crearPelicula(@RequestBody Factura factura) {
        Factura nuevaFactura = facturacionService.guardar(factura);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(new ResponseWrapper<>("Factura Creada", 1, List.of(nuevaFactura)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Factura>> actualizarFactura(@PathVariable Long id, @RequestBody Factura factura) {
        Factura facturaActualizada = facturacionService.actualizar(id, factura);
        return ResponseEntity.ok(new ResponseWrapper<>("Factura Actualizada", 1, List.of(facturaActualizada)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> eliminarFactura(@PathVariable Long id) {
        facturacionService.eliminar(id);

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseWrapper<>("Factura Eliminada", 1, null));
    }
   
}
