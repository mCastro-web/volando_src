package test.java;


import dao.PaqueteVueloDAO;
import data_types.DtPaqueteVuelo;
import data_types.TipoAsiento;
import model.Aeropuerto;
import model.Cliente;
import model.Reserva;
import model.PaqueteVuelo;
import model.Ciudad;
import model.Categoria;
import model.Aerolinea;
import model.RutaVuelo;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;




public class TestPaqueteVueloDAO {
/*

    private static PaqueteVueloDAO paqueteDAO;


    @BeforeAll
    public static void inicializar() {
        paqueteDAO = new PaqueteVueloDAO();
    }


    @Test
    public void testRegistrarYBuscarPaquete() {
        PaqueteVuelo paquete = new PaqueteVuelo(
                "PaqueteTest",
                "Descripción del paquete de prueba",
                30,
                15.5f,
                LocalDate.now()
        );
        paqueteDAO.registrarPaqueteVuelo(paquete);


        PaqueteVuelo paqueteBuscado = paqueteDAO.buscarPaqueteVuelo("PaqueteTest");
        assertNotNull(paqueteBuscado, "El paquete debería existir luego de ser guardado");
        assertEquals("PaqueteTest", paqueteBuscado.getNombre());
        assertEquals(30, paqueteBuscado.getDiasValidez());
    }
    // 🔹 Caso 2: Paquete nulo — debería lanzar IllegalArgumentException
    @Test
    public void testRegistrarPaqueteVueloPaqueteNulo() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paqueteDAO.registrarPaqueteVuelo(null);
        });
        assertEquals("El paquete no puede ser nulo", exception.getMessage());
    }


    // 🔹 Caso 3: Nombre nulo — debería lanzar IllegalArgumentException
    @Test
    public void testRegistrarPaqueteVueloNombreNulo() {
        PaqueteVuelo paquete = new PaqueteVuelo(
                null,
                "Paquete sin nombre",
                5,
                10.0f,
                LocalDate.now()
        );


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            paqueteDAO.registrarPaqueteVuelo(paquete);
        });
        assertEquals("El nombre es obligatorio", exception.getMessage());
    }
    @Test
    public void testRegistrarPaqueteVueloNombreInvalido() {
        PaqueteVuelo paqueteVuelo = new PaqueteVuelo(" ", "desc", 10, 5f, LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> paqueteDAO.registrarPaqueteVuelo(paqueteVuelo));
    }




    @Test
    public void testRegistrarPaqueteDuplicado() {
        PaqueteVuelo primerPaquete = new PaqueteVuelo(
                "PaqueteDuplicado",
                "Primera versión",
                20,
                10.0f,
                LocalDate.now()
        );
        paqueteDAO.registrarPaqueteVuelo(primerPaquete);


        PaqueteVuelo segundoPaquete = new PaqueteVuelo(
                "PaqueteDuplicado",
                "Segunda versión duplicada",
                15,
                5.0f,
                LocalDate.now()
        );


        Exception excepcion = assertThrows(IllegalArgumentException.class, () -> {
            paqueteDAO.registrarPaqueteVuelo(segundoPaquete);
        });
        assertTrue(excepcion.getMessage().contains("Ya existe un paquete"));
    }


    @Test
    public void testListarNombresPaquetes() {
        // Registrar un paquete para asegurar datos
        PaqueteVuelo paquete = new PaqueteVuelo(
                "PaqueteParaListar",
                "desc",
                5,
                2.0f,
                LocalDate.now()
        );
        paqueteDAO.registrarPaqueteVuelo(paquete);


        List<String> nombresPaquetes = paqueteDAO.listarNombresPaquetes();
        assertNotNull(nombresPaquetes, "La lista de nombres no debería ser nula");
        assertTrue(nombresPaquetes.contains("PaqueteParaListar"),
                "Debe contener el nombre del paquete registrado");
    }


    @Test
    public void testConsultaPaquete() {
        // Crear y registrar el paquete dentro del mismo test
        PaqueteVuelo paquete = new PaqueteVuelo(
                "PaqueteTestConsulta",
                "Descripción del paquete de prueba",
                30,
                15.5f,
                LocalDate.now()
        );
        paqueteDAO.registrarPaqueteVuelo(paquete);


        // Consultar el DTO
        DtPaqueteVuelo datosPaquete = paqueteDAO.consultaPaquete("PaqueteTestConsulta");
        assertNotNull(datosPaquete, "El DTO del paquete no debería ser nulo");
        assertEquals("PaqueteTestConsulta", datosPaquete.getNombre());
    }


    @Test
    public void testCalcularCostoPaquete() {
        Aeropuerto aeropuerto = new Aeropuerto(
                "Carrasco",
                "Aeropuerto internacional de Montevideo",
                "www.aeropuertocarrasco.com.uy",
                LocalDate.now(),
                null
        );
        Ciudad origen = new Ciudad("Montevideo", "Uruguay", aeropuerto);
        Ciudad destino = new Ciudad("Madrid", "España", aeropuerto);
        Aerolinea aerolinea = new Aerolinea("AeroTest", "AeroTest", "aeroTest@gmail.com", "123456", "1234", "descripcion", "www.test.com");
        Categoria categoria = new Categoria("Internacional");


        RutaVuelo ruta = new RutaVuelo(
                "Montevideo - Madrid",
                "Vuelo internacional desde Montevideo a Madrid",
                LocalDate.of(2024, 1, 1),
                500.0f,           // costoBaseTurista
                900.0f,           // costoBaseEjecutivo
                50.0f,            // costoEquipajeExtra
                origen,           // objeto Ciudad
                destino,          // objeto Ciudad
                aerolinea,        // objeto Aerolinea
                categoria,        // objeto Categoria
                "urlImagen.jpg",  // urlImagen
                "urlVideo.mp4",   // urlVideo
                "Vuelo directo"   // descripcionCorta
        );


        ruta.setCostoBaseTurista(100);
        ruta.setCostoBaseEjecutivo(200);


        float costoTurista = paqueteDAO.calcularCostoPaquete(ruta, TipoAsiento.TURISTA, 3);
        float costoEjecutivo = paqueteDAO.calcularCostoPaquete(ruta, TipoAsiento.EJECUTIVO, 2);


        assertEquals(300, costoTurista);
        assertEquals(400, costoEjecutivo);
    }


    @Test
    public void testClienteYaComproPaqueteTrueyFalse() {
        PaqueteVuelo paqueteComprado = new PaqueteVuelo();
        paqueteComprado.setIdentificador(1L);


        PaqueteVuelo paqueteNoComprado = new PaqueteVuelo();
        paqueteNoComprado.setIdentificador(2L);


        // Reserva que apunta al paqueteComprado
        Reserva reserva = new Reserva();
        reserva.setPaquete(paqueteComprado);


        Cliente cliente = new Cliente();
        cliente.getReserva().add(reserva); // Simula una compra previa


        // DAO
        PaqueteVueloDAO dao = new PaqueteVueloDAO();


        // Caso positivo: ya lo compró
        assertTrue(dao.clienteYaComproPaquete(cliente, paqueteComprado));


        // Caso negativo: no lo compró
        assertFalse(dao.clienteYaComproPaquete(cliente, paqueteNoComprado));
    }


    @Test
    public void testClienteYaComproPaquetePaqueteInvalido() {
        PaqueteVueloDAO dao = new PaqueteVueloDAO();
        Cliente cliente = new Cliente();


        // Paquete nulo
        assertThrows(IllegalArgumentException.class, () -> dao.clienteYaComproPaquete(cliente, null));


        // Paquete sin ID
        PaqueteVuelo paqueteSinId = new PaqueteVuelo();
        assertThrows(IllegalArgumentException.class, () -> dao.clienteYaComproPaquete(cliente, paqueteSinId));
    }


    @Test
    public void testObtenerDtPaquetePorNombre() {
        // Creamos y registramos un paquete real en la BD de pruebas
        PaqueteVuelo paquete = new PaqueteVuelo(
                "PaqueteDTO",
                "Descripción DTO",
                10,
                5.0f,
                LocalDate.now()
        );
        paqueteDAO.registrarPaqueteVuelo(paquete);


        // Ejecutamos el método
        var dto = paqueteDAO.obtenerDtPaquetePorNombre("PaqueteDTO");


        // Verificaciones
        assertNotNull(dto);
        assertEquals("PaqueteDTO", dto.getNombre());
        assertEquals("Descripción DTO", dto.descripcion());
        assertEquals(10, dto.diasValidez());
        assertEquals(5.0f, dto.descuento());
    }


    @Test
    public void testObtenerDtPaquetePorNombreNoExiste() {
        // Si el paquete no existe, el método debería devolver null (cae en catch)
        var dto = paqueteDAO.obtenerDtPaquetePorNombre("Inexistente");
        assertNull(dto);
    }


    @Test
    void testAgregarRutaPaqueteValidaciones() {
        var service = new PaqueteVueloDAO(); // donde esté tu método
        PaqueteVuelo paquete = new PaqueteVuelo();
        Aerolinea aerolinea = new Aerolinea();
        RutaVuelo ruta = new RutaVuelo();
        TipoAsiento tipoAsiento = TipoAsiento.TURISTA;


        // Paquete nulo
        Exception ex1 = assertThrows(IllegalArgumentException.class,
                () -> service.agregarRutaPaquete(null, aerolinea, ruta, 1, tipoAsiento));
        assertEquals("Debe indicar un Paquete de Vuelo", ex1.getMessage());


        // Aerolinea nula
        Exception ex2 = assertThrows(IllegalArgumentException.class,
                () -> service.agregarRutaPaquete(paquete, null, ruta, 1, tipoAsiento));
        assertEquals("Debe indicar una Aerolínea", ex2.getMessage());


        // Ruta nula
        Exception ex3 = assertThrows(IllegalArgumentException.class,
                () -> service.agregarRutaPaquete(paquete, aerolinea, null, 1, tipoAsiento));
        assertEquals("Debe indicar una Ruta de Vuelo", ex3.getMessage());


        // TipoAsiento nulo
        Exception ex4 = assertThrows(IllegalArgumentException.class,
                () -> service.agregarRutaPaquete(paquete, aerolinea, ruta, 1, null));
        assertEquals("Debe indicar el tipo de asiento", ex4.getMessage());


        // Cantidad nula
        Exception ex5 = assertThrows(IllegalArgumentException.class,
                () -> service.agregarRutaPaquete(paquete, aerolinea, ruta, null, tipoAsiento));
        assertEquals("La cantidad debe ser mayor a 0", ex5.getMessage());


        // Cantidad <= 0
        Exception ex6 = assertThrows(IllegalArgumentException.class,
                () -> service.agregarRutaPaquete(paquete, aerolinea, ruta, 0, tipoAsiento));
        assertEquals("La cantidad debe ser mayor a 0", ex6.getMessage());
    }
    @Test
    public void testListarPaquetesConPaquetes() {
        // 🔹 Agregar algunos paquetes para listar
        PaqueteVuelo paqueteVuelo = new PaqueteVuelo("PackListar1", "desc", 10, 5f, LocalDate.now());
        PaqueteVuelo paqueteVuelo1 = new PaqueteVuelo("PackListar2", "desc2", 20, 8f, LocalDate.now());
        paqueteDAO.registrarPaqueteVuelo(paqueteVuelo);
        paqueteDAO.registrarPaqueteVuelo(paqueteVuelo1);


        // 🔹 Verificar que la lista los contenga
        List<PaqueteVuelo> lista = paqueteDAO.listarPaquetes();
        assertNotNull(lista, "La lista no debe ser nula");
        assertFalse(lista.isEmpty(), "Debe devolver paquetes existentes");
        assertTrue(lista.stream().anyMatch(p -> p.getNombre().equals("PackListar1")));
        assertTrue(lista.stream().anyMatch(p -> p.getNombre().equals("PackListar2")));
    }


    @Test
    void testBuscarNombresRutasPorNombrePaqueteSinResultados() {
        var dao = new PaqueteVueloDAO();


        List<String> rutas = dao.buscarNombresRutasPorNombrePaquete("NoExiste");


        assertNotNull(rutas);
        assertTrue(rutas.isEmpty(), "Si no hay coincidencias, la lista debe estar vacía");
    }


    @Test
    void testBuscarNombresRutasPorNombrePaqueteInvalido() {
        var dao = new PaqueteVueloDAO();


        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> dao.buscarNombresRutasPorNombrePaquete(null));
        assertEquals("El nombre del paquete no puede ser nulo o vacío", ex1.getMessage());


        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> dao.buscarNombresRutasPorNombrePaquete(" "));
        assertEquals("El nombre del paquete no puede ser nulo o vacío", ex2.getMessage());
    }


    @Test
    void testBuscarNombresRutasPorNombrePaqueteInexistente() {
        List<String> rutas = paqueteDAO.buscarNombresRutasPorNombrePaquete("PaqueteInexistente");
        assertNotNull(rutas);
        assertTrue(rutas.isEmpty(), "Debe devolver lista vacía si no existe el paquete");
    }*/
}
