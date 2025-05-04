package com.example.veterinaria.hateoas;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
import com.example.veterinaria.controller.FacturacionController;
import com.example.veterinaria.model.Factura;

@Component
public class FacturacionModelAssembler implements RepresentationModelAssembler<Factura, EntityModel<Factura>> {
    @Override
    @NonNull
    public EntityModel<Factura> toModel(@NonNull Factura factura) {
        return EntityModel.of(factura,
                linkTo(methodOn(FacturacionController.class).getFactura(factura.getId())).withSelfRel(),
                linkTo(methodOn(FacturacionController.class).getFacturas()).withRel("all"),
                linkTo(methodOn(FacturacionController.class).actualizarFactura(factura.getId(), null)).withRel("update"),
                linkTo(methodOn(FacturacionController.class).eliminarFactura(factura.getId())).withRel("delete")
        );    
    }
}