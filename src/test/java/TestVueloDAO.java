package test.java;

import dao.VueloDAO;
import model.RutaVuelo;
import model.Aerolinea;
import model.Ciudad;
import model.Categoria;
import model.Aeropuerto;
import model.Vuelo;
import data_types.DtDatosVueloR;
import data_types.DtVuelo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestVueloDAO {

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("appPU");
    private final VueloDAO vueloDAO = new VueloDAO();

    @Test
    public void testGuardarYListarVuelos() {
        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();

        // 🔹 Crear dependencias
        Aeropuerto aeropuerto1 = new Aeropuerto("Carrasco1", "Aeropuerto Montevideo", "www.carrasco.com", LocalDate.now(), null);
        Aeropuerto aeropuerto2 = new Aeropuerto("AeropB", "Aeropuerto Buenos Aires", "www.aerob.com", LocalDate.now(), null);
        Ciudad origen = new Ciudad("Montevideo", "Uruguay", aeropuerto1);
        Ciudad destino = new Ciudad("Buenos Aires", "Argentina", aeropuerto2);
        Aerolinea aerolinea = new Aerolinea("AeroTest", "AeroTest S.A.", "contacto@aerotest.com", "123", "456", "Compañía test", "https://aerotest.com");
        Categoria categoria = new Categoria("Regional");

        entityManager.persist(aeropuerto1);
        entityManager.persist(aeropuerto2);
        entityManager.persist(origen);
        entityManager.persist(destino);
        entityManager.persist(aerolinea);
        entityManager.persist(categoria);

        // 🔹 Crear ruta
        RutaVuelo ruta = new RutaVuelo(
                "RutaTest01",
                "Ruta de prueba",
                LocalDate.now(),
                100f,
                200f,
                30f,
                origen,
                destino,
                aerolinea,
                categoria,
                "https://img.com/ruta.jpg",
                "Corta"
        );
        entityManager.persist(ruta);

        entityManager.getTransaction().commit();

        // 🔹 Guardar vuelo
        vueloDAO.guardarVuelo("Vuelo001", LocalDate.now(), LocalTime.of(2, 30), 50, 20, LocalDate.now(), ruta, "https://img.com/vuelo.jpg");

        // 🔹 Buscar vuelo
        Vuelo vueloRecuperado = vueloDAO.buscarPorNombre("Vuelo001");
        assertNotNull(vueloRecuperado);
        assertEquals("Vuelo001", vueloRecuperado.getNombre());
        assertEquals(ruta.getNombre(), vueloRecuperado.getRuta().getNombre());

        // 🔹 Listar nombres de vuelos por ruta
        List<String> nombresVuelos = vueloDAO.listarNombresVuelosPorRuta(ruta.getNombre());
        assertTrue(nombresVuelos.contains("Vuelo001"));

        // 🔹 Listar vuelos de ruta como DTO
        List<DtDatosVueloR> dtVuelos = vueloDAO.listarVuelosDeRuta(ruta.getNombre());
        assertFalse(dtVuelos.isEmpty());
        assertEquals("Vuelo001", dtVuelos.get(0).getNombre());

        // 🔹 Obtener DTO de vuelo por nombre
        DtVuelo dtVuelo = vueloDAO.obtenerDtVueloPorNombre("Vuelo001");
        assertNotNull(dtVuelo);
        assertEquals("Vuelo001", dtVuelo.getNombre());

        entityManager.close();
    }
}
