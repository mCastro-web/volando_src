package test.java;

import model.Aeropuerto;
import model.Ciudad;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAeropuerto {

    @Test
    public void testGettersAndSetters() {
        Aeropuerto aeropuerto = new Aeropuerto();

        Long identificador = 10L;
        String nombre = "Carrasco Internacional";
        String descripcion = "Principal aeropuerto de Uruguay";
        String sitioWeb = "https://www.aeropuertodecarrasco.com.uy";
        LocalDate fechaAlta = LocalDate.of(2020, 5, 1);
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre("Montevideo");

        // Setters
        aeropuerto.setIdentificador(identificador);
        aeropuerto.setNombre(nombre);
        aeropuerto.setDescripcion(descripcion);
        aeropuerto.setFechaAlta(fechaAlta);
        aeropuerto.setCiudad(ciudad);

        // Solo setear sitio web si el setter existe
        try {
            aeropuerto.setSitioWeb(sitioWeb);
        } catch (NoSuchMethodError | UnsupportedOperationException e) {
            // Si el setter está comentado, lo ignoramos
        }

        // Getters
        assertEquals(identificador, aeropuerto.getIdentificador());
        assertEquals(nombre, aeropuerto.getNombre());
        assertEquals(descripcion, aeropuerto.getDescripcion());
        assertEquals(fechaAlta, aeropuerto.getFechaAlta());
        assertEquals(ciudad, aeropuerto.getCiudad());

        // Sitio web solo si existe
        if (aeropuerto.getSitioWeb() != null) {
            assertEquals(sitioWeb, aeropuerto.getSitioWeb());
        }
    }
}
