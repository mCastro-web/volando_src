package test.java;

import dao.CiudadDAO;
import model.Ciudad;
import model.Aeropuerto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TestCiudadDAO {

    /*private static CiudadDAO dao;

    @BeforeAll
    public static void setup() {
        dao = new CiudadDAO(); // que use la PU de test
    }

    @Test
    public void testGuardarYObtenerCiudad() {
        String nombreUnico = "Montevideo_" + UUID.randomUUID();
        Aeropuerto aeropuerto = new Aeropuerto(
                "Carrasco",
                "Aeropuerto internacional de Montevideo",
                "www.aeropuertocarrasco.com.uy",
                LocalDate.now(),
                null
        );
        Ciudad ciudad = new Ciudad(nombreUnico, "Uruguay", aeropuerto);
        dao.guardarCiudad(ciudad);

        Ciudad obt = dao.obtenerCiudadPorNombre(nombreUnico);
        assertNotNull(obt, "La ciudad no debería ser nula");
        assertEquals(nombreUnico, obt.getNombre());
        assertEquals("Uruguay", obt.getPais());
        assertNotNull(obt.getAeropuerto(), "El aeropuerto no debería ser nulo");
        assertEquals("Carrasco", obt.getAeropuerto().getNombre());
    }

    @Test
    public void testGuardarCiudadDuplicada() {
        String nombreUnico = "CiudadDuplicada_" + UUID.randomUUID();
        Aeropuerto aeropuerto1 = new Aeropuerto(
                "Aeropuerto1",
                "Descripcion1",
                "www.aeropuerto1.com",
                LocalDate.now(),
                null
        );
        Ciudad ciudad = new Ciudad(nombreUnico, "Uruguay", aeropuerto1);
        dao.guardarCiudad(ciudad);

        // Intento de guardar otra ciudad con mismo nombre y país
        Aeropuerto aeropuerto2 = new Aeropuerto(
                "Aeropuerto2",
                "Descripcion2",
                "www.aeropuerto2.com",
                LocalDate.now(),
                null
        );
        Ciudad duplicada = new Ciudad(nombreUnico, "Uruguay", aeropuerto2);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.guardarCiudad(duplicada);
        });
        assertTrue(exception.getMessage().contains("Ya existe una ciudad"));
    }

    @Test
    public void testListarNombresCiudades() {
        String nombreUnico = "CiudadListar_" + UUID.randomUUID();
        Aeropuerto aeropuerto = new Aeropuerto(
                "AeropuertoListar",
                "Descripcion Listar",
                "www.aeropuertolistar.com",
                LocalDate.now(),
                null
        );
        Ciudad ciudad = new Ciudad(nombreUnico, "Uruguay", aeropuerto);
        dao.guardarCiudad(ciudad);

        List<String> nombres = dao.listarNombresCiudades();
        assertNotNull(nombres, "La lista de nombres no debería ser nula");
        assertTrue(nombres.contains(nombreUnico), "La lista debería contener la ciudad recién creada");
    }*/
}
