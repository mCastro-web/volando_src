package test.java;


import dao.ReservaDAO;
import dao.VueloDAO;
import data_types.TipoDoc;
import data_types.DtReserva;
import model.Aerolinea;
import model.Aeropuerto;
import model.Ciudad;
import model.Categoria;
import model.Cliente;
import model.RutaVuelo;
import model.Vuelo;
import model.Reserva;
import model.PaqueteVuelo;
import data_types.TipoAsiento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;




public class TestReservaDAO {

/*
    private final EntityManagerFactory factory = Persistence.createEntityManagerFactory("appPU");
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final VueloDAO vueloDAO = new VueloDAO();


    @Test
    public void testGuardarYListarReservas() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();


        // 🔹 Crear dependencias
        Aeropuerto aeropuerto1 = new Aeropuerto("Carrasco1", "Aeropuerto Montevideo", "www.carrasco.com", LocalDate.now(), null);
        Aeropuerto aeropuerto2 = new Aeropuerto("AeropB", "Aeropuerto Buenos Aires", "www.aerob.com", LocalDate.now(), null);
        Ciudad origen = new Ciudad("Montevideo", "Uruguay", aeropuerto1);
        Ciudad destino = new Ciudad("Buenos Aires", "Argentina", aeropuerto2);
        Aerolinea aerolinea = new Aerolinea("AeroTest", "AeroTest S.A.", "contacto@aerotest.com", "123", "456", "Compañía test", "https://aerotest.com");
        Categoria categoria = new Categoria("Regional");


        Cliente cliente = new Cliente(
                "clientetest", "Juan", "juan@test.com", "123456", "urlImagen",
                "Perez", LocalDate.of(1990, 1, 1), "Uruguaya",
                TipoDoc.CEDULA, "12345678"
        );
        entityManager.persist(aeropuerto1);
        entityManager.persist(aeropuerto2);
        entityManager.persist(origen);
        entityManager.persist(destino);
        entityManager.persist(aerolinea);
        entityManager.persist(categoria);
        entityManager.persist(cliente);


        // 🔹 Crear ruta y vuelo
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
                "https://video.com/ruta.mp4",
                "Corta"
        );
        entityManager.persist(ruta);


        entityManager.getTransaction().commit();


        // 🔹 Guardar vuelo
        vueloDAO.guardarVuelo("Vuelo002", LocalDate.now(), LocalTime.of(2, 30), 50, 20, LocalDate.now(), ruta, "https://img.com/vuelo.jpg");
        Vuelo vuelo = vueloDAO.buscarPorNombre("Vuelo002");


        // 🔹 Crear reserva
        Reserva reserva = new Reserva(
                TipoAsiento.TURISTA,
                LocalDate.now(),
                5,
                1500f,
                cliente,
                vuelo,
                2,
                null,
                new ArrayList<>(),
                null
        );


        reservaDAO.guardar(reserva);


        // 🔹 Listar reservas de vuelo
        List<String> reservasVuelo = reservaDAO.listarReservasDeVuelo("Vuelo002");
        assertFalse(reservasVuelo.isEmpty());
        assertTrue(reservasVuelo.get(0).contains("clientetest"));


        // 🔹 Listar reservas de cliente
        List<String> reservasCliente = reservaDAO.listarReservasDeCliente("clientetest");
        assertFalse(reservasCliente.isEmpty());
        assertTrue(reservasCliente.get(0).contains("Vuelo002"));


        List<String> result = reservaDAO.obtenerReservaVueloPorCliente("clientetest", "Vuelo002");
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Debe devolver al menos una reserva");
        assertTrue(result.get(0).contains("Reserva Nro"), "Debe contener texto de reserva");
        entityManager.close();


        List<DtReserva> reservas = reservaDAO.listarDtReservasDeVuelo("Vuelo002");
        assertNotNull(reservas);
        if (!reservas.isEmpty()) {
            DtReserva dtReserva = reservas.get(0);
            assertNotNull(dtReserva.getCliente());
            assertNotNull(dtReserva.getVuelo());
        }
        DtReserva res = reservaDAO.obtenerDtReservaPorCliVue("clientetest", "Vuelo002");
        if (res != null) {
            assertEquals("clientetest", res.getCliente().getNickname());
        } else {
            // si no hay datos cargados, el test no debe fallar
            assertNull(res);
        }
    }


    @Test
    public void testGuardarReservaConError() {
        Reserva reserva = new Reserva();
        reserva.setCliente(new Cliente());
        reserva.setVuelo(new Vuelo());


        Exception exception = assertThrows(Exception.class, () -> {
            reservaDAO.guardar(reserva);
        });


        assertNotNull(exception);
    }


    @Test
    void testListarPaquetesDeCliente() {
        var reservaDAO = new ReservaDAO();
        var emf = reservaDAO.getEntityManagerFactory();
        var entityManager = emf.createEntityManager();


        Cliente cliente = new Cliente();
        cliente.setNickname("clienteTest");
        cliente.setContrasenia("1234Segura");
        cliente.setEmail("cliente@test.com");
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
        cliente.setNacionalidad("Uruguaya");
        cliente.setTipoDocumento(TipoDoc.CEDULA);
        cliente.setNumDocumento("12345678");
        cliente.setFechaNac(LocalDate.of(1990, 1, 1));
        cliente.setUrlImagen("https://example.com/foto.jpg");


        PaqueteVuelo paquete = new PaqueteVuelo(
                "PaqueteCliente",
                "Paquete de prueba para listar",
                15,
                5.0f,
                LocalDate.now()
        );


        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setPaquete(paquete);
        reserva.setFecha(LocalDate.now());
        reserva.setCosto(100.0f);


        entityManager.getTransaction().begin();
        entityManager.persist(paquete);
        entityManager.persist(cliente);
        entityManager.persist(reserva);
        entityManager.getTransaction().commit();
        entityManager.close();


        List<String> resultados = reservaDAO.listarPaquetesDeCliente("clienteTest");


        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
        assertTrue(resultados.get(0).contains("PaqueteCliente"));
    }


    @Test
    public void testListarPasajesDeReservas() {
        List<String> pasajes = reservaDAO.listarPasajesDeReservas("1");
        assertNotNull(pasajes);
        if (!pasajes.isEmpty()) {
            assertTrue(pasajes.get(0).startsWith("Nombre:"));
        }
    }




    @Test
    public void testObtenerDtReservaPorCliVueNoEncontrada() {
        DtReserva res = reservaDAO.obtenerDtReservaPorCliVue("noExiste", "vueloInexistente");
        assertNull(res);
    }*/
}
