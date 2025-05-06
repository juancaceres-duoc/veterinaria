package com.example.veterinaria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import com.example.veterinaria.hateoas.FacturacionModelAssembler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.veterinaria.service.FacturacionService;
import com.example.veterinaria.model.Factura;
import com.example.veterinaria.model.ResponseWrapper;

import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/facturas")    
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService;

    @Autowired
    private FacturacionModelAssembler facturacionModelAssembler;
   
    @GetMapping
    public  ResponseEntity<ResponseWrapper<CollectionModel<EntityModel<Factura>>>> getFacturas() {
        List<Factura> facturas = facturacionService.obtenerTodas();
 
         if (facturas.isEmpty()) {
             return ResponseEntity
                     .status(HttpStatus.NO_CONTENT)
                     .body(null);
         }else {
            List<EntityModel<Factura>> facturaModel = facturas.stream()
                .map(facturacionModelAssembler::toModel)
                .toList();

            CollectionModel<EntityModel<Factura>> collection = CollectionModel.of(facturaModel,
                linkTo(methodOn(FacturacionController.class).getFacturas()).withSelfRel());

            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper<>("Facturas obtenidas exitosamente", facturas.size(), List.of(collection)));
        }        
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<EntityModel<Factura>>> getFactura(@PathVariable Long id) {
       Factura factura = facturacionService.obtenerPorId(id);

        if (factura == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseWrapper<>("Factura no encontrada", 0, null));
        }else {
            EntityModel<Factura> facturaModel = facturacionModelAssembler.toModel(factura);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper<>("Factura obtenida exitosamente", 1, List.of(facturaModel)));
        }
    }    

    @PostMapping
    public ResponseEntity<ResponseWrapper<EntityModel<Factura>>> crearPelicula(@RequestBody Factura factura) {
        Factura facturaCreada = facturacionService.guardar(factura);

        if (facturaCreada == null) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseWrapper<>("Error al crear la factura", 0, null));
        }else {
            EntityModel<Factura> facturaModel = facturacionModelAssembler.toModel(facturaCreada);
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper<>("Factura creada exitosamente", 1, List.of(facturaModel)));
        }   
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<EntityModel<Factura>>> actualizarFactura(@PathVariable Long id, @RequestBody Factura factura) {
        Factura facturaActualizada = facturacionService.actualizar(id, factura);
        if (facturaActualizada == null) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ResponseWrapper<>("Factura no encontrada", 0, null));
        } else {
            EntityModel<Factura> facturaModel = facturacionModelAssembler.toModel(facturaActualizada);
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseWrapper<>("Factura actualizada exitosamente", 1, List.of(facturaModel)));            
        }
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> eliminarFactura(@PathVariable Long id) {
        facturacionService.eliminar(id); 
        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(new ResponseWrapper<>("Factura eliminada exitosamente", 1, null));
        
        
    }
   
}
