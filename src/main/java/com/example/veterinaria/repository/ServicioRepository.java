package com.example.veterinaria.repository;
import com.example.veterinaria.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository  extends JpaRepository<Servicio, Long> {    
    
}
