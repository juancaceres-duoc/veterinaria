package com.example.veterinaria.service;

import com.example.veterinaria.model.Factura;
import com.example.veterinaria.model.Servicio;
import com.example.veterinaria.repository.FacturaRepository;
import com.example.veterinaria.repository.ServicioRepository;
import com.example.veterinaria.exception.FacturaNotFoundException;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class FacturacionService {
    
    private FacturaRepository facturaRepository;    
    private ServicioRepository servicioRepository;

    public FacturacionService(FacturaRepository facturaRepository, ServicioRepository servicioRepository) { 
        this.facturaRepository = facturaRepository;    
        this.servicioRepository = servicioRepository;   
    }

    // Obtener todas las facturas
    public List<Factura> obtenerTodas() {
        return facturaRepository.findAll();
    }

    // Obtener una factura por ID
    public Factura obtenerPorId(Long id) {
        return facturaRepository.findById(id).orElseThrow(() -> new FacturaNotFoundException(id));
    }

    // Guardar una factura (con cálculo del total)
    public Factura guardar(Factura factura) {
        // Si no se proporcionan servicios, generarlos aleatoriamente
        if (factura.getServicios() == null || factura.getServicios().isEmpty()) {
            factura.setServicios(generarServiciosAleatorios());
        }        

        List<Long> servicioIds = new ArrayList<>();
        for (Servicio servicio : factura.getServicios()) {
            servicioIds.add(servicio.getId());
        }

        List<Servicio> servicios = servicioRepository.findAllById(servicioIds);
        
        // Calcular el total de la factura
        int total = calcularTotal(servicios);
        factura.setTotal(total);  // Establecer el total de la factura calculado
        
        return facturaRepository.save(factura);
    }

    // Eliminar una factura
    public void eliminar(long id) {
        Factura factura = facturaRepository.findById(id).orElseThrow(() -> new FacturaNotFoundException(id));
        facturaRepository.delete(factura);
    }

    // Actualizar una factura
    public Factura actualizar(Long id, Factura factura) {
        Factura facturaExistente = facturaRepository.findById(id).orElseThrow(() -> new FacturaNotFoundException(id));
        facturaExistente.setRazon_social(factura.getRazon_social());
        facturaExistente.setCliente(factura.getCliente());

        // Si se proporcionan servicios, actualizarlos
        if (factura.getServicios() != null) {
            List<Long> servicioIds = new ArrayList<>();
            for (Servicio servicio : factura.getServicios()) {
                servicioIds.add(servicio.getId());
            }

            // Obtener los servicios completos desde la base de datos (incluyendo precios)
            List<Servicio> serviciosActualizados = servicioRepository.findAllById(servicioIds);
            facturaExistente.setServicios(serviciosActualizados);
        }

        // Recalcular el total después de actualizar los servicios
        int total = calcularTotal(facturaExistente.getServicios());
        facturaExistente.setTotal(total);

        // Guardar la factura actualizada
        return facturaRepository.save(facturaExistente);
    }

    // Método para generar servicios aleatorios
    private List<Servicio> generarServiciosAleatorios() {
        List<Servicio> todosLosServicios = servicioRepository.findAll();
        List<Servicio> serviciosFiltrados = new ArrayList<>();
        List<Servicio> candidatos = new ArrayList<>();
        
        // Agregar "Consulta" si existe
        for (Servicio s : todosLosServicios) {
            if (s.getNombre().equalsIgnoreCase("Consulta")) {
                serviciosFiltrados.add(s);
            } else {
                candidatos.add(s);
            }
        }

        // Seleccionar aleatoriamente hasta 2 servicios más
        Random rand = new Random();
        while (serviciosFiltrados.size() < 3 && !candidatos.isEmpty()) {
            int randomIndex = rand.nextInt(candidatos.size());
            Servicio servicioAleatorio = candidatos.remove(randomIndex);
            serviciosFiltrados.add(servicioAleatorio);
        }

        return serviciosFiltrados;
    }

    // Método para calcular el total (con IVA del 19%)
    public int calcularTotal(List<Servicio> servicios) {
    int subtotal = 0;
    for (Servicio servicio : servicios) {
        //System.out.println("Precio del servicio " + servicio.getNombre() + ": " + servicio.getPrecio());
        subtotal += servicio.getPrecio();
    }
    // Aplicar IVA del 19%
    int totalConIva = (int) (subtotal * 1.19);
    //System.out.println("Subtotal: " + subtotal);
    //System.out.println("Total con IVA: " + totalConIva);
    return totalConIva;
}
}
