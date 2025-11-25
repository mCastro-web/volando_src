package test.java;

import dao.RutaVueloDAO;
import data_types.DtRutaVuelo;
import data_types.EstadoRuta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import model.RutaVuelo;
import model.Aerolinea;
import model.Ciudad;
import model.Categoria;
import model.Aeropuerto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestRutaVueloDAO {
/*
    private static RutaVueloDAO rutaVueloDAO;
    private static EntityManagerFactory factory;

    @BeforeAll
    public static void inicializar() {
        rutaVueloDAO = new RutaVueloDAO();
        factory = Persistence.createEntityManagerFactory("appPU");
    }

    @AfterAll
    public static void cerrarFactory() {
        if (factory != null) factory.close();
    }

    @Test
    public void testFlujoNormalYCatch() {
        EntityManager entityManager = factory.createEntityManager();
        try {
            // --- Datos base ---
            Aeropuerto aeropuerto1 = new Aeropuerto("Aer1", "Aeropuerto 1", "www.aer1.com", LocalDate.now(), null);
            Aeropuerto aeropuerto2 = new Aeropuerto("Aer2", "Aeropuerto 2", "www.aer2.com", LocalDate.now(), null);
            Ciudad origen = new Ciudad("Montevideo1", "Uruguay", aeropuerto1);
            Ciudad destino = new Ciudad("Buenos Aires1", "Argentina", aeropuerto2);
            Aerolinea aerolinea = new Aerolinea("AeroRuta12", "AeroRuta S.A.1", "contacto@aeroruta.com12", "12345", "1234", "Compañía de prueba", "https://aeroruta.com");
            Categoria categoria = new Categoria("Regional");


            // --- Guardar dependencias ---
            entityManager.getTransaction().begin();
            entityManager.persist(aeropuerto1);
            entityManager.persist(aeropuerto2);
            entityManager.persist(origen);
            entityManager.persist(destino);
            entityManager.persist(aerolinea);
            entityManager.persist(categoria);
            entityManager.getTransaction().commit();

            // --- Crear ruta ---
            RutaVuelo ruta = new RutaVuelo(
                    "RutaTest001",
                    "Ruta de prueba Montevideo - Buenos Aires",
                    LocalDate.now(),
                    100f,
                    200f,
                    30f,
                    origen,
                    destino,
                    aerolinea,
                    categoria,
                    "https://img.com/ruta.jpg",
                    "https://video.com/ruta.mp4",
                    "Ruta corta"
            );

            // --- Guardar ruta con DAO (flujo normal) ---
            rutaVueloDAO.guardarRutaVuelo(ruta);

            // --- Obtener ruta ---
            RutaVuelo recuperada = rutaVueloDAO.obtenerRutaPorNombre("RutaTest001");
            assertNotNull(recuperada);
            assertEquals("RutaTest001", recuperada.getNombre());

            // --- Obtener DTO ---
            DtRutaVuelo dto = rutaVueloDAO.obtenerDtRutaPorNombre("RutaTest001");
            assertNotNull(dto);
            assertEquals("RutaTest001", dto.getNombre());
            assertEquals("Regional", dto.getCategoria());

            // --- Listar rutas por aerolínea ---
            List<String> rutas = rutaVueloDAO.listarRutasPorAerolinea("AeroRuta12");
            assertNotNull(rutas);
            assertTrue(rutas.contains("RutaTest001"));

            List<String> rutasIngresadas = rutaVueloDAO.listarRutasIngresadasPorAerolinea("AeroRuta12");
            assertNotNull(rutasIngresadas);
            assertTrue(rutasIngresadas.contains("RutaTest001"));

            // --- Actualizar estado ---
            rutaVueloDAO.actualizarEstado("RutaTest001", EstadoRuta.CONFIRMADA);
            RutaVuelo rutaConfirmada = rutaVueloDAO.obtenerRutaPorNombre("RutaTest001");
            assertEquals(EstadoRuta.CONFIRMADA, rutaConfirmada.getEstado());

            List<String> rutasConfirmadas = rutaVueloDAO.listarRutasConfirmadasPorAerolinea("AeroRuta12");
            assertTrue(rutasConfirmadas.contains("RutaTest001"));

            // --- Probar catch de persistencia ---
            RutaVuelo rutaDuplicada = new RutaVuelo(
                    "RutaTest001", "Duplicada", LocalDate.now(), 50f, 100f, 10f,
                    origen, destino, aerolinea, categoria, null, null, null
            );
            PersistenceException persistEx = assertThrows(PersistenceException.class, () -> {
                rutaVueloDAO.guardarRutaVuelo(rutaDuplicada); // debería fallar por PK duplicada
            });
            assertNotNull(persistEx);

            // --- Probar obtener DTO de ruta inexistente ---
            DtRutaVuelo dtoNulo = rutaVueloDAO.obtenerDtRutaPorNombre("RutaInexistente");
            assertNull(dtoNulo, "DTO de ruta inexistente debe ser null");

        } finally {
            entityManager.close();
        }
    }

    @Test
    public void testRollbackEnCatch() {
        EntityManager entityManager = factory.createEntityManager();

        // 🔹 Persistimos las dependencias correctas
        entityManager.getTransaction().begin();
        Aerolinea aerolinea = new Aerolinea("AeroTest", "Aero Test S.A.", "contacto@test.com", "111", "222", "Compañía test", "https://aero.com");
        Ciudad origen = new Ciudad("Montevideo", "Uruguay", null);
        Ciudad destino = new Ciudad("Buenos Aires", "Argentina", null);
        Categoria categoria = new Categoria("Internacional");

        entityManager.persist(aerolinea);
        entityManager.persist(origen);
        entityManager.persist(destino);
        entityManager.persist(categoria);
        entityManager.getTransaction().commit();

        // 🔹 Creamos una ruta con un valor nulo obligatorio para forzar el catch
        RutaVuelo rutaErronea = new RutaVuelo(
                null,  // nombre nulo, causará PersistenceException
                "Ruta con error",
                LocalDate.now(),
                100f,
                200f,
                50f,
                origen,
                destino,
                aerolinea,
                categoria,
                "url",
                "url video",
                "desc corta"
        );

        boolean lanzoExcepcion = false;
        try {
            rutaVueloDAO.guardarRutaVuelo(rutaErronea);
        } catch (PersistenceException e) {
            lanzoExcepcion = true;
            //  Comprobamos que la transacción fue rollback
            assertTrue(e instanceof jakarta.persistence.PersistenceException || e.getCause() instanceof org.hibernate.PropertyValueException);
        }

        assertTrue(lanzoExcepcion, "El DAO debería lanzar excepción y hacer rollback al persistir entidad inválida");

        entityManager.close();
    }
*/
}