package com.example.veterinaria.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FacturaNotFoundException extends RuntimeException {
    public FacturaNotFoundException(Long id) {
        super("La factura con id " + id + " no fue encontrada.");
    }
    
}
