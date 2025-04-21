package com.example.veterinaria.repository;
import com.example.veterinaria.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {  
    
}
