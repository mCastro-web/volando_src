package test.java;

import data_types.DtAeropuerto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtAeropuerto {

    @Test
    public void testConstructorYGetters() {
        String nombre = "Aeropuerto Carrasco";
        String descripcion = "Aeropuerto Internacional de Montevideo";
        String sitioWeb = "https://carrasco.com.uy";
        LocalDate fechaAlta = LocalDate.of(2020, 1, 1);

        DtAeropuerto dtAeropuerto = new DtAeropuerto(nombre, descripcion, sitioWeb, fechaAlta);

        // Validar constructor y getters
        assertEquals(nombre, dtAeropuerto.getNombre());
        assertEquals(descripcion, dtAeropuerto.getDescripcion());
        assertEquals(sitioWeb, dtAeropuerto.getSitioWeb());
        assertEquals(fechaAlta, dtAeropuerto.getFechaAlta());
    }
}
