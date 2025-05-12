package com.example.veterinaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import com.example.veterinaria.service.FacturacionService;
import com.example.veterinaria.repository.ServicioRepository;

import com.example.veterinaria.model.Factura;
import com.example.veterinaria.model.Servicio;
import com.example.veterinaria.repository.FacturaRepository;

public class FacturacionServiceTest {
    private FacturacionService facturacionService;
    private FacturaRepository facturaRepository;
    private ServicioRepository servicioRepository;

    @BeforeEach
    public void setUp() {      
        facturaRepository = mock(FacturaRepository.class);  
        servicioRepository = mock(ServicioRepository.class);  
        facturacionService = new FacturacionService(facturaRepository, servicioRepository);
    }

    @Test
    public void testObtenerTodas() {
       Factura factura1 = new Factura();
        factura1.setId(1L);
        factura1.setRazon_social("Empresa A");
        factura1.setCliente("Cliente A");
        factura1.setServicios(Arrays.asList(
            new Servicio(1L, "Servicio A", "Descripcion A", 50),
            new Servicio(2L, "Servicio B", "Descripcion B", 75),
            new Servicio(3L, "Servicio C", "Descripcion C", 100)
            ));
        factura1.setTotal(facturacionService.calcularTotal(factura1.getServicios()));

        Factura factura2 = new Factura();
        factura2.setId(2L);
        factura2.setRazon_social("Empresa B");
        factura2.setCliente("Cliente B");
        factura2.setServicios(Arrays.asList(
            new Servicio(4L, "Servicio D", "Descripcion D", 200),
            new Servicio(5L, "Servicio E", "Descripcion E", 300),
            new Servicio(6L, "Servicio F", "Descripcion F", 400)
            ));
        factura2.setTotal(facturacionService.calcularTotal(factura2.getServicios()));

        when(facturaRepository.findAll()).thenReturn(List.of(factura1, factura2));
        List<Factura> resultado = facturacionService.obtenerTodas();

        assertEquals(2, resultado.size());
        assertEquals("Empresa A", resultado.get(0).getRazon_social());  
        assertEquals("Empresa B", resultado.get(1).getRazon_social());
        assertEquals(267, resultado.get(0).getTotal());
        assertEquals(1071, resultado.get(1).getTotal());
    }

    @Test
    public void testObtenerPorId() {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setRazon_social("Empresa A");
        factura.setCliente("Cliente A");
        factura.setServicios(Arrays.asList(
            new Servicio(1L, "Servicio A", "Descripcion A", 50),
            new Servicio(2L, "Servicio B", "Descripcion B", 75),
            new Servicio(3L, "Servicio C", "Descripcion C", 100)
            ));
        factura.setTotal(facturacionService.calcularTotal(factura.getServicios()));
            
        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura));
        Factura resultado = facturacionService.obtenerPorId(1L);

        assertEquals("Empresa A", resultado.getRazon_social());
        assertEquals("Cliente A", resultado.getCliente());
        assertEquals(3, resultado.getServicios().size());
        assertEquals(267, resultado.getTotal());      
    }   
    
    @Test
    public void testGuardarFactura() {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setRazon_social("Empresa A");
        factura.setCliente("Cliente A");
        List<Servicio> servicios = Arrays.asList(
            new Servicio(1L, "Servicio A", "Descripcion A", 50),
            new Servicio(2L, "Servicio B", "Descripcion B", 75),
            new Servicio(3L, "Servicio C", "Descripcion C", 100)
            );
        factura.setServicios(servicios);        
        factura.setTotal(facturacionService.calcularTotal(factura.getServicios()));

        when(servicioRepository.findAllById(any())).thenReturn(servicios);
        when(facturaRepository.save(factura)).thenReturn(factura);
        Factura resultado = facturacionService.guardar(factura);

        assertEquals("Empresa A", resultado.getRazon_social());
        assertEquals("Cliente A", resultado.getCliente());
        assertEquals(3, resultado.getServicios().size());
        assertEquals(267, resultado.getTotal());      
    }

    @Test
    public void testEliminarFactura() {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setRazon_social("Empresa A");
        factura.setCliente("Cliente A");
        factura.setServicios(Arrays.asList(
            new Servicio(1L, "Servicio A", "Descripcion A", 50),
            new Servicio(2L, "Servicio B", "Descripcion B", 75),
            new Servicio(3L, "Servicio C", "Descripcion C", 100)
            ));
        factura.setTotal(facturacionService.calcularTotal(factura.getServicios()));

        when(facturaRepository.findById(1L)).thenReturn(Optional.of(factura));
        doNothing().when(facturaRepository).delete(factura);

        facturacionService.eliminar(1L);

        verify(facturaRepository, times(1)).delete(factura);
    }
}
