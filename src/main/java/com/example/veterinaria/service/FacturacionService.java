package com.example.veterinaria.service;
import com.example.veterinaria.model.Factura;
import com.example.veterinaria.model.Servicio;  
import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;

@Service
public class FacturacionService {
    private List<Factura> facturas;
    private List<Servicio> servicios;

    public FacturacionService() {
        generacionFacturas();
    }

    private void generarServicios(){
        servicios = new ArrayList<>();
        servicios.add(new Servicio("Consulta", "Consulta general", 15000));
        servicios.add(new Servicio("Vacunacion", "Vacuna contra la rabia", 12000));
        servicios.add(new Servicio("Desparacitacion", "Desparacitacion general", 8000));        
        servicios.add(new Servicio("Radiografía", "Radiografía general", 30000));
        servicios.add(new Servicio("Examen de Sangre", "Exámenes de sangre", 12000));
        servicios.add(new Servicio("Peluquería", "Baño y Peluquería", 15000));
        servicios.add(new Servicio("Esterilización", "Esterilización de mascotas", 25000));
        servicios.add(new Servicio("Hospitalización", "Resultados de exámenes", 55000));
    }  

    public List<Factura> generacionFacturas(){        
        facturas = new ArrayList<>();

        List<Servicio> servicioFactura1 = generarServiciosAleatorios(); 
        Factura factura1 = new Factura(1, "Veterinaria Cachupín", "Juan Perez", servicioFactura1, calcularTotal(servicioFactura1));
        facturas.add(factura1);
        
        List<Servicio> servicioFactura2 = generarServiciosAleatorios();
        Factura factura2 = new Factura(2, "Veterinaria Cachupín", "Maria Lopez", servicioFactura2, calcularTotal(servicioFactura2));
        facturas.add(factura2);

        List<Servicio> servicioFactura3 = generarServiciosAleatorios();
        Factura factura3 = new Factura(3, "Veterinaria Cachupín", "Carlos Sanchez", servicioFactura3, calcularTotal(servicioFactura3));
        facturas.add(factura3);

        return facturas;
   }

   private List<Servicio> generarServiciosAleatorios() {
        generarServicios();
        List<Servicio> serviciosAleatorios = new ArrayList<>();        
        serviciosAleatorios.add(servicios.get(0)); 
        while(serviciosAleatorios.size() < 3){
            Servicio servicioAleatorio = servicios.get((int) (Math.random() * servicios.size()));
            if (!serviciosAleatorios.contains(servicioAleatorio) && !servicioAleatorio.getNombre().contains("Consulta")) {
                serviciosAleatorios.add(servicioAleatorio); 
            }
        }
        return serviciosAleatorios;
    }

   private int calcularTotal(List<Servicio> servicios) {
       int subtotal = 0;
       for (Servicio servicio : servicios) {
           subtotal += servicio.getPrecio();
       }
       return (int)(subtotal * 1.19); // Aplicar IVA del 19%
   }

}
