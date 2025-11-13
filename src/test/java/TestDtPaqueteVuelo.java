package test.java;

import data_types.DtPaqueteVuelo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDtPaqueteVuelo {

    @Test
    public void testConstructorPrincipal() {
        DtPaqueteVuelo paquete = new DtPaqueteVuelo(
                "Paquete1",
                "Descripción test",
                10,
                15f,
                500f,
                LocalDate.of(2025, 10, 20),
                List.of(), // items
                List.of()  // reservas
        );

        assertEquals("Paquete1", paquete.nombre());
        assertEquals("Descripción test", paquete.descripcion());
        assertEquals(10, paquete.diasValidez());
        assertEquals(15f, paquete.descuento());
        assertEquals(500f, paquete.costo());
        assertEquals(LocalDate.of(2025, 10, 20), paquete.altaFecha());
        assertTrue(paquete.items().isEmpty());
        assertTrue(paquete.reservas().isEmpty());
    }

    @Test
    public void testConstructorSecundario() {
        DtPaqueteVuelo paquete = new DtPaqueteVuelo(
                "Paquete2",
                "Desc corta",
                5,
                10f,
                LocalDate.of(2025, 10, 20)
        );

        assertEquals("Paquete2", paquete.nombre());
        assertEquals("Desc corta", paquete.descripcion());
        assertEquals(5, paquete.diasValidez());
        assertEquals(10f, paquete.descuento());
        assertEquals(0f, paquete.costo()); // Constructor secundario asigna 0
        assertEquals(LocalDate.of(2025, 10, 20), paquete.altaFecha());
        assertTrue(paquete.items().isEmpty());
        assertTrue(paquete.reservas().isEmpty());
    }
}
