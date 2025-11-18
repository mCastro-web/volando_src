package test.java;

import data_types.DtRutaVuelo;
import data_types.EstadoRuta;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static java.sql.Types.NULL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestDtRutaVuelo {

    @Test
    public void testDtRutaVueloCompleto() {
        List<String> vuelos = List.of("Vuelo001", "Vuelo002");

        DtRutaVuelo ruta = new DtRutaVuelo(
                "RutaTest",
                "Descripción completa de la ruta",
                LocalDate.of(2025, 10, 20),
                100f,
                200f,
                30f,
                "Montevideo",
                "Buenos Aires",
                "AeroTest",
                "Regional",
                vuelos,
                "https://img.com/ruta.jpg",
                "https://video.com/ruta.mp4",
                "Ruta corta",
                EstadoRuta.INGRESADA,
                NULL
        );

        assertEquals("RutaTest", ruta.getNombre());
        assertEquals("Descripción completa de la ruta", ruta.getDescripcion());
        assertEquals(LocalDate.of(2025, 10, 20), ruta.getFechaAlta());
        assertEquals(100f, ruta.getCostoBaseTurista());
        assertEquals(200f, ruta.getCostoBaseEjecutivo());
        assertEquals(30f, ruta.getCostoEquipajeExtra());
        assertEquals("Montevideo", ruta.getOrigen());
        assertEquals("Buenos Aires", ruta.getDestino());
        assertEquals("AeroTest", ruta.getAerolinea());
        assertEquals("Regional", ruta.getCategoria());
        assertEquals(2, ruta.getVuelos().size());
        assertTrue(ruta.getVuelos().contains("Vuelo001"));
        assertEquals("https://img.com/ruta.jpg", ruta.getUrlImagen());
        assertEquals("Ruta corta", ruta.getDescripcionCorta());
        assertEquals(EstadoRuta.INGRESADA, ruta.getEstado());
    }
}
