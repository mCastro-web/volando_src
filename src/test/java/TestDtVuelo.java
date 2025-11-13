package test.java;

import data_types.DtVuelo;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDtVuelo {

    @Test
    public void testDtVuelo() {
        DtVuelo vuelo = new DtVuelo(
                "Vuelo001",
                LocalDate.of(2025, 10, 20),
                LocalTime.of(2, 30),
                50,
                20,
                LocalDate.of(2025, 10, 1),
                "RutaTest01",
                //List.of(),
                "https://img.com/vuelo.jpg"
        );

        assertEquals("Vuelo001", vuelo.getNombre());
        assertEquals(LocalDate.of(2025, 10, 20), vuelo.getFecha());
        assertEquals(LocalTime.of(2, 30), vuelo.getDuracion());
        assertEquals(50, vuelo.getAsientosTurista());
        assertEquals(20, vuelo.getAsientosEjecutivo());
        assertEquals(LocalDate.of(2025, 10, 1), vuelo.getFechaAlta());
        assertEquals("RutaTest01", vuelo.getRuta());
        assertEquals("https://img.com/vuelo.jpg", vuelo.getUrlImagen());
    }
}