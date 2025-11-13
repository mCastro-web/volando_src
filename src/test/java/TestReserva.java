package test.java;

import model.Pasaje;
import model.Reserva;
import model.Cliente;
import model.Vuelo;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestReserva {

    @Test
    public void testGettersAndSetters() {
        Reserva reserva = new Reserva();

        LocalDate fecha = LocalDate.of(2025, 10, 20);
        Cliente cliente = new Cliente();
        Vuelo vuelo = new Vuelo();
        int equipaje = 2;
        float costo = 1500f;
        int cantPasajes = 3;
        LocalDate vencimiento = LocalDate.of(2025, 12, 31);

        reserva.setFecha(fecha);
        reserva.setCliente(cliente);
        reserva.setVuelo(vuelo);
        reserva.setEquipajeExtra(equipaje);
        reserva.setCosto(costo);
        reserva.setCantPasajes(cantPasajes);
        reserva.setVencimiento(vencimiento);

        assertEquals(fecha, reserva.getFecha());
        assertEquals(cliente, reserva.getCliente());
        assertEquals(vuelo, reserva.getVuelo());
        assertEquals(equipaje, reserva.getEquipajeExtra());
        assertEquals(costo, reserva.getCosto());
        assertEquals(cantPasajes, reserva.getCantPasajes());
        assertEquals(vencimiento, reserva.getVencimiento());
    }

    @Test
    public void testSetPasajesAddPasaje() {
        Reserva reserva = new Reserva(); // reserva base

        // Rama 1: setPasajes con lista no nula
        Pasaje pasaje1 = new Pasaje("Juan", "Perez", reserva);
        Pasaje pasaje2 = new Pasaje("Ana", "Gomez", reserva);

        reserva.setPasajes(List.of(pasaje1, pasaje2));

        assertEquals(2, reserva.getPasajes().size());
        assertTrue(reserva.getPasajes().contains(pasaje1));
        assertTrue(reserva.getPasajes().contains(pasaje2));

        // Rama 2: addPasaje con null
        reserva.addPasaje(null); // no debe lanzar excepción
        assertEquals(2, reserva.getPasajes().size()); // sin cambios
    }


}
