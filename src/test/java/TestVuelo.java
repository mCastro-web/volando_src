package test.java;

import model.Reserva;
import model.RutaVuelo;
import model.Vuelo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVuelo {

    @Test
    void testSettersAndGetters() {
        Vuelo vuelo = new Vuelo();

        LocalDate fecha = LocalDate.now();
        LocalTime duracion = LocalTime.of(2, 30);
        RutaVuelo ruta = new RutaVuelo();
        List<Reserva> reservas = new ArrayList<>();

        vuelo.setNombre("Vuelo Test");
        vuelo.setFecha(fecha);
        vuelo.setDuracion(duracion);
        vuelo.setAsientosTurista(100);
        vuelo.setAsientosEjecutivo(20);
        vuelo.setFechaAlta(LocalDate.of(2025, 1, 1));
        vuelo.setRuta(ruta);
        vuelo.setReservaVuelo(reservas);
        vuelo.setUrlImagen("imagen.jpg");

        assertEquals("Vuelo Test", vuelo.getNombre());
        assertEquals(fecha, vuelo.getFecha());
        assertEquals(duracion, vuelo.getDuracion());
        assertEquals(100, vuelo.getAsientosTurista());
        assertEquals(20, vuelo.getAsientosEjecutivo());
        assertEquals(LocalDate.of(2025, 1, 1), vuelo.getFechaAlta());
        assertEquals(ruta, vuelo.getRuta());
        assertEquals(reservas, vuelo.getReservaVuelo());
        assertEquals("imagen.jpg", vuelo.getUrlImagen());
    }

}
