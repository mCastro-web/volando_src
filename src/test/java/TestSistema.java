package test.java;


import data_types.TipoDoc;
import data_types.DtUsuario;
import data_types.TipoAsiento;
import data_types.DtDatosVueloR;
import data_types.DtPaqueteVuelo;
import data_types.DtReserva;
import model.Aerolinea;
import model.Cliente;
import model.Reserva;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import sistema.Sistema;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class TestSistema {

/*
    private static Sistema sistema;


    @BeforeAll
    public static void inicializar() {
        sistema = Sistema.getInstance();  // asegurarse de que no devuelva null


        LocalDate fechaAlta = LocalDate.now();


        // ===== Crear ciudades con aeropuertos =====
        sistema.altaCiudad(
                "Montevideo",
                "Uruguay",
                "Carrasco",
                "Aeropuerto Internacional de Montevideo",
                "https://aeropuertocarrasco.com.uy",
                fechaAlta
        );


        sistema.altaCiudad(
                "Buenos Aires",
                "Argentina",
                "Ezeiza",
                "Aeropuerto Internacional Ministro Pistarini",
                "https://aa2000.com.ar/ezeiza",
                fechaAlta
        );
        sistema.altaCiudad("Santiago", "Chile", "Arturo Merino Benítez", "Aeropuerto Internacional Santiago", "https://scl.aero", fechaAlta);
        sistema.altaCiudad("Lima", "Perú", "Jorge Chávez", "Aeropuerto Internacional Jorge Chávez", "https://lim.aero", fechaAlta);
        sistema.altaCiudad("Asunción", "Paraguay", "Silvio Pettirossi", "Aeropuerto Internacional Silvio Pettirossi", "https://asu.aero", fechaAlta);
        sistema.altaCiudad("Madrid", "España", "Adolfo Suárez Madrid-Barajas", "Aeropuerto Madrid-Barajas", "https://mad.aero", fechaAlta);


        // ===== Crear categoría =====
        sistema.altaCategoria("Internacional");
        // ===== Crear aerolínea para rutas y vuelos =====
        try {
            sistema.consultaUsuario("AeroTestRutas");
        } catch (IllegalArgumentException exception) {
            sistema.altaAerolinea(
                    "AeroTestRutas",          // nickname exclusivo para estas pruebas
                    "Aero Test Rutas",
                    "aerorutas@sistema.com",
                    "123456",
                    "https://aerorutas.com/logo.jpg",
                    "Línea aérea para tests de rutas y vuelos",
                    "https://aerorutas.com"
            );
        }
    }




    // ===================== CLIENTE =====================


    @Test
    public void testAltaClienteExitosa() {
        DtUsuario clienteCreado = sistema.altaCliente(
                "ClienteTest1",
                "Carlos",
                "cliente1@test.com",
                "123456",
                "https://img.com/foto.jpg",
                "Pérez",
                LocalDate.of(1990, 5, 10),
                "Uruguay",
                TipoDoc.CEDULA,
                "45683215"
        );


        assertNotNull(clienteCreado, "El cliente no debería ser nulo después del alta");
        assertEquals("ClienteTest1", clienteCreado.getNickname());
    }


    @Test
    public void testAltaClienteNicknameInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaCliente(
                    "",
                    "Carlos",
                    "correo@test.com",
                    "123456",
                    null,
                    "Pérez",
                    LocalDate.of(1990, 5, 10),
                    "Uruguay",
                    TipoDoc.CEDULA,
                    "45683215"
            );
        });
        assertTrue(exception.getMessage().contains("El nickname es obligatorio"));
    }


    @Test
    public void testAltaClienteEmailInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaCliente(
                    "ClienteEmail",
                    "Lucía",
                    "correo_invalido",
                    "123456",
                    null,
                    "Martínez",
                    LocalDate.of(1988, 3, 12),
                    "Uruguay",
                    TipoDoc.CEDULA,
                    "45683215"
            );
        });
        assertTrue(exception.getMessage().contains("Formato de mail incorrecto"));
    }


    @Test
    public void testAltaClienteCedulaInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaCliente(
                    "ClienteCedula",
                    "Ana",
                    "ana@test.com",
                    "123456",
                    null,
                    "Rodríguez",
                    LocalDate.of(1985, 1, 20),
                    "Uruguay",
                    TipoDoc.CEDULA,
                    "123" // cédula inválida
            );
        });
        assertTrue(exception.getMessage().contains("La cédula es inválida"));
    }


    @Test
    public void testAltaClientePasaporteInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaCliente(
                    "ClientePasaporte",
                    "Jorge",
                    "jorge@test.com",
                    "123456",
                    null,
                    "Fernández",
                    LocalDate.of(1975, 8, 5),
                    "Argentina",
                    TipoDoc.PASAPORTE,
                    "123" // formato inválido
            );
        });
        assertTrue(exception.getMessage().contains("Formato de pasaporte incorrecto"));
    }
    @Test
    void testAltaClienteValidaciones() {
        // Nickname nulo
        var ex1 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente(null, "Juan", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("El nickname es obligatorio", ex1.getMessage());


        // Nickname vacío
        var ex2 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente(" ", "Juan", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("El nickname es obligatorio", ex2.getMessage());


        // Nombre nulo
        var ex3 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", null, "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("El nombre es obligatorio", ex3.getMessage());


        // Nombre vacío
        var ex4 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", " ", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("El nombre es obligatorio", ex4.getMessage());


        // Email nulo
        var ex5 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", null, "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("Formato de mail incorrecto.", ex5.getMessage());


        // Email vacío
        var ex6 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", " ", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("Formato de mail incorrecto.", ex6.getMessage());


        // Email inválido
        var ex7 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "email_invalido", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("Formato de mail incorrecto.", ex7.getMessage());


        // Apellido nulo
        var ex8 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", null,
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("El apellido es obligatorio", ex8.getMessage());


        // Apellido vacío
        var ex9 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", " ",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("El apellido es obligatorio", ex9.getMessage());


        // Fecha de nacimiento nula
        var ex10 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", "Perez",
                        null, "Uruguaya", TipoDoc.CEDULA, "12345678"));
        assertEquals("La fecha de nacimiento es obligatoria", ex10.getMessage());


        // Nacionalidad nula
        var ex11 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), null, TipoDoc.CEDULA, "12345678"));
        assertEquals("La nacionalidad es obligatoria", ex11.getMessage());


        // Nacionalidad vacía
        var ex12 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), " ", TipoDoc.CEDULA, "12345678"));
        assertEquals("La nacionalidad es obligatoria", ex12.getMessage());


        // Tipo de documento nulo
        var ex13 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", null, "12345678"));
        assertEquals("El tipo de documento es obligatorio", ex13.getMessage());


        // Número de documento nulo
        var ex14 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, null));
        assertEquals("El número de documento es obligatorio", ex14.getMessage());


        // Número de documento vacío
        var ex15 = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCliente("juanp", "Juan", "juan@test.com", "1234", "url", "Perez",
                        LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, " "));
        assertEquals("El número de documento es obligatorio", ex15.getMessage());
    }




    @Test
    public void testModificarClienteExitosa() {
        sistema.altaCliente("cliente1", "Juan", "juan@email.com", "1234", null,
                "Pérez", LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "30717413");
        sistema.modificarCliente("cliente1", "Juan Mod", "juan@email.com", "4321", null,
                "Pérez Mod", LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "30717413", "cliente1");


        Cliente mod = sistema.obtenerClientePorNickname("cliente1");
        assertEquals("Juan Mod", mod.getNombre());
        assertEquals("juan@email.com", mod.getEmail());
        assertTrue(BCrypt.checkpw("4321", mod.getContrasenia()));
    }


    @Test
    public void testModificarClienteConNickInexistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.modificarCliente("clienteX", "Nombre", "email@mail.com", "123", null,
                    "Apellido", LocalDate.of(1990, 1, 1), "Uruguaya", TipoDoc.CEDULA, "12345678", "clienteX");
        });
        assertTrue(exception.getMessage().contains("No existe cliente con nickname"));
    }




    // ===================== AEROLÍNEA =====================


    @Test
    public void testAltaAerolineaExitosa() {
        DtUsuario aerolineaCreada = sistema.altaAerolinea(
                "AeroTestSys",
                "Aero Test",
                "aerotest@sistema.com",
                "123456",
                "https://aerolinea.com/logo.jpg",
                "Línea aérea de prueba",
                "https://aerolinea.com"
        );


        assertNotNull(aerolineaCreada, "La aerolínea no debería ser nula después del alta");
        assertEquals("AeroTestSys", aerolineaCreada.getNickname());


        DtUsuario usuario = sistema.consultaUsuario("AeroTestSys");
        assertNotNull(usuario, "El usuario debería existir");
        assertEquals("AeroTestSys", usuario.getNickname());
    }


    @Test
    public void testAltaAerolineaEmailInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaAerolinea(
                    "AeroError",
                    "Aero Error",
                    "correo_invalido",
                    "123456",
                    null,
                    "Sin descripción",
                    null
            );
        });
        assertTrue(exception.getMessage().contains("Formato de mail incorrecto"));
    }


    @Test
    public void testAltaAerolineaInvalido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaAerolinea(
                    " ",
                    "Aero Error",
                    "correo_invalido",
                    "123456",
                    null,
                    "Sin descripción",
                    null
            );
        });
        assertTrue(exception.getMessage().contains("El nickname es obligatorio"));
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaAerolinea(
                    null,
                    "Aero Error",
                    "correo_invalido",
                    "123456",
                    null,
                    "Sin descripción",
                    null
            );
        });
        assertTrue(exception2.getMessage().contains("El nickname es obligatorio"));
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaAerolinea(
                    "test",
                    " ",
                    "correo_invalido",
                    "123456",
                    null,
                    "Sin descripción",
                    null
            );
        });
        assertTrue(exception3.getMessage().contains("El nombre es obligatorio"));
        Exception exception4 = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaAerolinea(
                    "test",
                    null,
                    "correo_invalido",
                    "123456",
                    null,
                    "Sin descripción",
                    null
            );
        });
        assertTrue(exception4.getMessage().contains("El nombre es obligatorio"));
    }
    @Test
    public void testAltaAerolineaDescripcionVacia() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaAerolinea(
                    "AeroDesc",
                    "Aero Desc",
                    "aerodesc@test.com",
                    "123456",
                    null,
                    "",
                    "https://sitio.com"
            );
        });
        assertTrue(exception.getMessage().contains("La descripción es obligatoria"));
        Exception asserted = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaAerolinea(
                    "AeroDesc",
                    "Aero Desc",
                    "aerodesc@test.com",
                    "123456",
                    null,
                    null,
                    "https://sitio.com"
            );
        });
        assertTrue(asserted.getMessage().contains("La descripción es obligatoria"));
    }


    @Test
    public void testModificarAerolineaExitosa() {
        sistema.altaAerolinea("aero1", "Aero", "aero@email.com", "1234", null, "Desc", "www.aero.com");
        sistema.modificarAerolinea("aero1", "Aero Mod", "aero@email.com", "4321", null,
                "Desc Mod", "www.aero.com", "aero1");


        Aerolinea mod = sistema.obtenerAerolineaPorNick("aero1");
        assertEquals("Aero Mod", mod.getNombre());
        assertEquals("aero@email.com", mod.getEmail());
        assertTrue(BCrypt.checkpw("4321", mod.getContrasenia()));
        assertEquals("Desc Mod", mod.getDescripcion());
    }


    @Test
    public void testModificarAerolineaConNickInexistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.modificarAerolinea("aeroX", "Aero", "email@mail.com", "123", null, "Desc", "www.aero.com", "aeroX");
        });
        assertTrue(exception.getMessage().contains("No existe aerolínea con nickname"));
    }
    // ===================== CONSULTAS =====================




    @Test
    public void testConsultaUsuarioInexistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.consultaUsuario("NoExiste123");
        });
        assertTrue(exception.getMessage().contains("No existe el usuario"));
    }


    @Test
    public void testListarNicknamesNoNulo() {
        List<String> lista = sistema.listarNicknames();
        assertNotNull(lista, "La lista de nicknames no debería ser nula");
    }


    @Test
    public void testListarUsuariosPorNickNoNulo() {
        List<String> lista = sistema.listarUsuariosPorNick();
        assertNotNull(lista, "La lista de usuarios no debería ser nula");
    }


    // ===================== RUTAS Y VUELOS =====================


    @Test
    public void testAltaRutaVueloExitosa() {
        sistema.altaRutaVuelo(
                "Ruta Test 1",
                "Ruta de prueba Montevideo - Buenos Aires",
                LocalDate.of(2023, 5, 10),
                100f,
                200f,
                50f,
                "AeroTestRutas",
                "Montevideo",
                "Buenos Aires",
                "Internacional",
                "https://img.com/ruta.jpg",
                "video.com/ruta.mp4",
                "Ruta corta"
        );


        var dtRuta = sistema.obtenerDtRutaPorNombre("Ruta Test 1");
        assertNotNull(dtRuta, "La ruta debería haberse creado correctamente");
        assertEquals("Ruta Test 1", dtRuta.getNombre());
    }


    @Test
    public void testAltaRutaVueloOrigenODestinoNoEncontrado() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaRutaVuelo(
                    "Ruta Error",
                    "Ruta inválida",
                    LocalDate.of(2023, 5, 10),
                    100f,
                    200f,
                    50f,
                    "AeroTestRutas",
                    "CiudadInexistente",
                    "Buenos Aires",
                    "Internacional",
                    "https://img.com/ruta.jpg",
                    "video.com/ruta.mp4",
                    "Ruta inválida"
            );
        });
        assertTrue(exception.getMessage().contains("Ciudad de origen o destino no encontrada"));
    }


    @Test
    public void testAltaRutaVueloMismoOrigenYDestino() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaRutaVuelo(
                    "Ruta Duplicada",
                    "Ruta con mismo origen y destino",
                    LocalDate.of(2023, 5, 10),
                    100f,
                    200f,
                    50f,
                    "AeroTestRutas",
                    "Montevideo",
                    "Montevideo",
                    "Internacional",
                    "https://img.com/ruta.jpg",
                    "video.com/ruta.mp4",
                    "Ruta inválida"
            );
        });
        assertTrue(exception.getMessage().contains("La ciudad de origen y destino no pueden ser la misma"));
    }


    @Test
    public void testListarNombresCategoriasNoNulo() {
        List<String> categorias = sistema.listarNombresCategorias();
        assertNotNull(categorias, "La lista de categorías no debería ser nula");
    }


    @Test
    public void testListarNombresCiudadesNoNulo() {
        List<String> ciudades = sistema.listarNombresCiudades();
        assertNotNull(ciudades, "La lista de ciudades no debería ser nula");
    }


    @Test
    public void testListarNicknamesAerolineasNoNulo() {
        List<String> aerolineas = sistema.listarNicknamesAerolineas();
        assertNotNull(aerolineas, "La lista de aerolíneas no debería ser nula");
    }


    @Test
    public void testAltaVueloExitosa() {
        sistema.altaRutaVuelo(
                "Ruta Test Vuelo",
                "Ruta de vuelo para test",
                LocalDate.of(2023, 6, 15),
                150f,
                300f,
                80f,
                "AeroTestRutas",
                "Montevideo",
                "Santiago",
                "Internacional",
                "https://img.com/vuelo.jpg",
                "video.com/ruta.mp4",
                "Ruta aérea"
        );


        sistema.altaVuelo(
                "Vuelo Test 1",
                LocalDate.of(2023, 7, 1),
                java.time.LocalTime.of(2, 30),
                100,
                20,
                LocalDate.now(),
                "Ruta Test Vuelo",
                "https://img.com/vuelo.jpg"
        );


        // Si no lanza excepción, se considera correcto
        assertTrue(true, "El vuelo debería haberse creado sin errores");
    }


    @Test
    public void testAltaVueloRutaInexistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaVuelo(
                    "Vuelo Sin Ruta",
                    LocalDate.of(2023, 7, 1),
                    java.time.LocalTime.of(3, 0),
                    80,
                    10,
                    LocalDate.now(),
                    "RutaQueNoExiste",
                    "https://img.com/vuelo.jpg"
            );
        });
        assertTrue(exception.getMessage().contains("no existe"));
    }


    @Test
    public void testAltaVueloDuracionInvalida() {
        sistema.altaRutaVuelo(
                "Ruta Duracion",
                "Ruta para probar duración inválida",
                LocalDate.of(2023, 6, 20),
                120f,
                220f,
                60f,
                "AeroTestRutas",
                "Montevideo",
                "Lima",
                "Internacional",
                "https://img.com/ruta.jpg",
                "video.com/ruta.mp4",
                "Ruta test"
        );


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaVuelo(
                    "Vuelo Duracion",
                    LocalDate.of(2023, 7, 10),
                    java.time.LocalTime.MIDNIGHT,
                    80,
                    10,
                    LocalDate.now(),
                    "Ruta Duracion",
                    "https://img.com/vuelo.jpg"
            );
        });
        assertTrue(exception.getMessage().contains("Duración inválida"));
    }


    @Test
    public void testAltaVueloAsientosInvalidos() {
        sistema.altaRutaVuelo(
                "Ruta Asientos",
                "Ruta para probar asientos inválidos",
                LocalDate.of(2023, 6, 25),
                130f,
                260f,
                70f,
                "AeroTestRutas",
                "Montevideo",
                "Asunción",
                "Internacional",
                "https://img.com/ruta.jpg",
                "video.com/ruta.mp4",
                "Ruta test"
        );


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaVuelo(
                    "Vuelo Asientos",
                    LocalDate.of(2023, 7, 15),
                    java.time.LocalTime.of(3, 45),
                    0,
                    10,
                    LocalDate.now(),
                    "Ruta Asientos",
                    "https://img.com/vuelo.jpg"
            );
        });
        assertTrue(exception.getMessage().contains("Cantidad de asientos inválida"));
    }


    @Test
    public void testActualizarEstadoRuta() {
        sistema.altaRutaVuelo(
                "Ruta Estado",
                "Ruta de prueba para cambiar estado",
                LocalDate.of(2023, 7, 5),
                140f,
                280f,
                75f,
                "AeroTestRutas",
                "Montevideo",
                "Madrid",
                "Internacional",
                "https://img.com/ruta.jpg",
                "video.com/ruta.mp4",
                "Ruta estado"
        );


        sistema.actualizarEstadoRuta("Ruta Estado", data_types.EstadoRuta.CONFIRMADA);


        List<String> rutasConfirmadas = sistema.listarRutasConfirmadasAerolinea("AeroTestRutas");
        assertTrue(rutasConfirmadas.contains("Ruta Estado"), "La ruta debería estar confirmada");
    }


    @Test
    public void testListarRutasPorAerolineaNoNulo() {
        List<String> rutas = sistema.listarRutasPorAerolinea("AeroTestRutas");
        assertNotNull(rutas, "La lista de rutas por aerolínea no debería ser nula");
    }


    @Test
    public void testListarRutasConfirmadasAerolineaNoNulo() {
        List<String> rutasConfirmadas = sistema.listarRutasConfirmadasAerolinea("AeroTestRutas");
        assertNotNull(rutasConfirmadas, "La lista de rutas confirmadas no debería ser nula");
    }


    // ===================== RESERVA DE VUELOS =====================


    @Test
    public void testReservarVueloExitosa() {
        // Primero creamos un cliente
        sistema.altaCliente(
                "ClienteReserva",
                "Juan",
                "juan@test.com",
                "123456",
                null,
                "Pérez",
                LocalDate.of(1990, 5, 10),
                "Uruguay",
                TipoDoc.CEDULA,
                "56380266"
        );


        // Crear ruta y vuelo
        sistema.altaRutaVuelo(
                "Ruta Reserva",
                "Ruta de prueba para reserva",
                LocalDate.of(2023, 8, 1),
                100f,
                200f,
                50f,
                "AeroTestRutas",
                "Montevideo",
                "Buenos Aires",
                "Internacional",
                null,
                null,
                null
        );


        sistema.altaVuelo(
                "Vuelo Reserva 1",
                LocalDate.of(2023, 9, 1),
                java.time.LocalTime.of(10, 0),
                100,
                20,
                LocalDate.now(),
                "Ruta Reserva",
                null
        );


        // Datos de pasajeros
        List<String> nombres = List.of("Juan");
        List<String> apellidos = List.of("Pérez");


        // Reservar
        sistema.reservarVuelo(
                "ClienteReserva",
                "Vuelo Reserva 1",
                TipoAsiento.TURISTA,
                2,
                LocalDate.now(),
                1,
                nombres,
                apellidos
        );


        // Verificar que la reserva exista
        List<String> reservas = sistema.listarReservasDeVuelo("Vuelo Reserva 1");
        assertTrue(reservas.size() > 0, "Debe existir al menos una reserva para el vuelo");
    }


    @Test
    public void testReservarVueloClienteInexistente() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.reservarVuelo(
                    "NoExiste",
                    "Vuelo Reserva 1",
                    TipoAsiento.TURISTA,
                    0,
                    LocalDate.now(),
                    1,
                    List.of("Ana"),
                    List.of("Gómez")
            );
        });
        assertTrue(exception.getMessage().contains("Cliente no encontrado"));
    }


    @Test
    public void testReservarVueloVueloInexistente() {
        // Crear cliente
        sistema.altaCliente(
                "ClienteVuelo",
                "Lucía",
                "lucia@test.com",
                "123456",
                null,
                "Martínez",
                LocalDate.of(1992, 2, 15),
                "Uruguay",
                TipoDoc.CEDULA,
                "29253410"
        );


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.reservarVuelo(
                    "ClienteVuelo",
                    "VueloNoExiste",
                    TipoAsiento.TURISTA,
                    0,
                    LocalDate.now(),
                    1,
                    List.of("Lucía"),
                    List.of("Martínez")
            );
        });
        assertTrue(exception.getMessage().contains("Vuelo no encontrado"));
    }


    @Test
    public void testReservarVueloCantidadPasajesInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.reservarVuelo(
                    "ClienteReserva",
                    "Vuelo Reserva 1",
                    TipoAsiento.TURISTA,
                    0,
                    LocalDate.now(),
                    0,
                    List.of(),
                    List.of()
            );
        });
        assertTrue(exception.getMessage().contains("La cantidad de pasajes debe ser mayor a 0"));
    }


    @Test
    public void testListarVuelosDeRutaNoNulo() {
        List<DtDatosVueloR> vuelos = sistema.listarVuelosDeRuta("Ruta Reserva");
        assertNotNull(vuelos, "La lista de vuelos de la ruta no debería ser nula");
    }


    @Test
    public void testListarNombresVuelosPorRutaNoNulo() {
        List<String> nombresVuelos = sistema.listarNombresVuelosPorRuta("Ruta Reserva");
        assertNotNull(nombresVuelos, "La lista de nombres de vuelos de la ruta no debería ser nula");
    }


    // ===================== TESTS ALTA CIUDAD =====================


    @Test
    public void testAltaCiudadExitosa() {
        LocalDate fechaAlta = LocalDate.now();
        // Solo se verifica que no lance excepción
        sistema.altaCiudad("Valparaíso", "Chile", "Aeropuerto Valpo",
                "Aeropuerto internacional Valparaíso", "https://valpo.aero", fechaAlta);
    }


    @Test
    public void testAltaCiudadNombreInvalido() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad("", "Chile", "Aeropuerto Valpo", "Desc", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El nombre de la ciudad es obligatorio"));
    }


    @Test
    public void testAltaCiudadPaisInvalido() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad("Valparaíso", "", "Aeropuerto Valpo", "Desc", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El país es obligatorio"));
    }


    @Test
    public void testAltaCiudadAeropuertoInvalido() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad("Valparaíso", "Chile", "", "Desc", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El aeropuerto es obligatorio"));
    }


    @Test
    public void testAltaCiudadDescripcionAeropuertoNula() {
        LocalDate fechaAlta = LocalDate.now();
        // No lanza excepción aunque la descripción sea null
        sistema.altaCiudad("Mendoza", "Argentina", "Aeropuerto Mendoza", null, "https://mza.aero", fechaAlta);
    }


    @Test
    public void testAltaCiudadSitioWebNulo() {
        LocalDate fechaAlta = LocalDate.now();
        // No lanza excepción aunque el sitio web sea null
        sistema.altaCiudad("Quito", "Ecuador", "Aeropuerto Quito", "Aeropuerto internacional Quito", null, fechaAlta);
    }
    @Test
    public void testAltaCiudadNombreNull() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad(null, "Chile", "Aeropuerto Valpo", "Desc", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El nombre de la ciudad es obligatorio"));
    }


    // ===================== TESTS ALTA CIUDAD - Cobertura de validaciones =====================


    @Test
    public void testAltaCiudadPaisNull() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad("Valparaíso", null, "Aeropuerto Valpo", "Aeropuerto de Valpo", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El país es obligatorio"));
    }


    @Test
    public void testAltaCiudadPaisVacio() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad("Valparaíso", "", "Aeropuerto Valpo", "Aeropuerto de Valpo", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El país es obligatorio"));
    }


    @Test
    public void testAltaCiudadNombreAeropuertoNull() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad("Valparaíso", "Chile", null, "Aeropuerto de Valpo", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El aeropuerto es obligatorio"));
    }


    @Test
    public void testAltaCiudadNombreAeropuertoVacio() {
        LocalDate fechaAlta = LocalDate.now();
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.altaCiudad("Valparaíso", "Chile", "", "Aeropuerto de Valpo", null, fechaAlta)
        );
        assertTrue(exception.getMessage().contains("El aeropuerto es obligatorio"));
    }


    // ===================== TESTS ALTA PAQUETE =====================
    @Test
    public void testAltaPaqueteRVExitosa() {
        LocalDate fecha = LocalDate.now();
        sistema.altaPaqueteRV("Paquete Test", "Paquete de prueba", 10, 15.0f, fecha);


        DtPaqueteVuelo paquete = sistema.obtenerDtPaquetePorNombre("Paquete Test");
        assertNotNull(paquete, "El paquete debería haberse creado correctamente");
        assertEquals("Paquete Test", paquete.nombre());
        assertEquals("Paquete de prueba", paquete.descripcion());
        assertEquals(10, paquete.diasValidez());
        assertEquals(15.0f, paquete.descuento());
        assertEquals(fecha, paquete.altaFecha());
    }


    @Test
    public void testAltaPaqueteRVNombreNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaPaqueteRV(null, "Descripción", 5, 10.0f, LocalDate.now());
        });
        assertTrue(exception.getMessage().contains("El nombre del Paquete es obligatorio"));
    }


    @Test
    public void testAltaPaqueteRVNombreVacio() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaPaqueteRV("   ", "Descripción", 5, 10.0f, LocalDate.now());
        });
        assertTrue(exception.getMessage().contains("El nombre del Paquete es obligatorio"));
    }


    @Test
    public void testAltaPaqueteRVDescripcionNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaPaqueteRV("Paquete1", null, 5, 10.0f, LocalDate.now());
        });
        assertTrue(exception.getMessage().contains("Se debe ingresar una descripción"));
    }


    @Test
    public void testAltaPaqueteRVDescripcionVacia() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaPaqueteRV("Paquete1", "   ", 5, 10.0f, LocalDate.now());
        });
        assertTrue(exception.getMessage().contains("Se debe ingresar una descripción"));
    }


    @Test
    public void testAltaPaqueteRVDiasValidezNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaPaqueteRV("Paquete1", "Descripción", null, 10.0f, LocalDate.now());
        });
        assertTrue(exception.getMessage().contains("Se debe ingresar los días de validez del paquete"));
    }


    @Test
    public void testAltaPaqueteRVDescuentoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaPaqueteRV("Paquete1", "Descripción", 5, null, LocalDate.now());
        });
        assertTrue(exception.getMessage().contains("El valor del descuento es obligatorio"));
    }


    @Test
    public void testAltaPaqueteRVFechaNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.altaPaqueteRV("Paquete1", "Descripción", 5, 10.0f, null);
        });
        assertTrue(exception.getMessage().contains("La fecha del paquete es obligatoria"));
    }
    @Test
    public void testListarNombresPaquetes() {
        // Preparar datos
        sistema.altaPaqueteRV("Paquete5", "Paquete 1", 10, 100f, LocalDate.now());
        sistema.altaPaqueteRV("Paquete6", "Paquete 2", 5, 50f, LocalDate.now());


        List<String> nombres = sistema.listarNombresPaquetes();


        assertNotNull(nombres);
        assertTrue(nombres.contains("Paquete5"));
        assertTrue(nombres.contains("Paquete6"));
        assertEquals(5, nombres.size());
    }


    @Test
    public void testConsultaPaqueteExistente() {
        sistema.altaPaqueteRV("PaqueteConsulta", "Prueba", 7, 70f, LocalDate.now());


        DtPaqueteVuelo paquete = sistema.consultaPaquete("PaqueteConsulta");


        assertNotNull(paquete);
        assertEquals("PaqueteConsulta", paquete.getNombre());
    }


    @Test
    public void testListarNombresPaquetesConRutas() {
        LocalDate hoy = LocalDate.now();
        sistema.altaPaqueteRV("PaqueteSinRuta", "Sin rutas", 5, 50f, hoy);
        sistema.altaPaqueteRV("PaqueteConRuta", "Con rutas", 7, 70f, hoy);
        sistema.altaAerolinea("AerolineaR", "Aerolinea R", "r@aero.com", "123", null, "Desc", null);
        sistema.altaRutaVuelo("RutaParaPaquete", "Ruta prueba", hoy, 100f, 200f, 50f, "AerolineaR", "Montevideo", "Buenos Aires", "Internacional", null, null, null);
        sistema.agregarRutaPaquete("PaqueteConRuta", "AerolineaR", "RutaParaPaquete", 1, TipoAsiento.TURISTA);


        List<String> nombres = sistema.listarNombresPaquetesConRutas();


        assertNotNull(nombres);
        assertTrue(nombres.contains("PaqueteConRuta"));
        assertFalse(nombres.contains("PaqueteSinRuta"));
        assertEquals(2, nombres.size());
    }




    // ===================== TESTS AGREGAR RUTA A PAQUETE =====================
    @Test
    public void testAgregarRutaPaqueteExitosa() {
        LocalDate fecha = LocalDate.now();
        sistema.altaPaqueteRV("PaqueteTestRuta", "Paquete prueba rutas", 10, 20.0f, fecha);
        sistema.altaAerolinea("AerolineaTest", "Aerolinea Test", "correo@aero.com", "123", null, "Descripción", null);
        sistema.altaRutaVuelo(
                "RutaTestPaquete",
                "Ruta de prueba",
                fecha,
                100f,
                200f,
                50f,
                "AerolineaTest",
                "Montevideo",
                "Buenos Aires",
                "Internacional",
                null,
                null,
                null
        );


        sistema.agregarRutaPaquete("PaqueteTestRuta", "AerolineaTest", "RutaTestPaquete", 2, TipoAsiento.TURISTA);
    }


    @Test
    public void testAgregarRutaPaqueteNombrePaqueteNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.agregarRutaPaquete(null, "AerolineaTest", "RutaTest", 2, TipoAsiento.TURISTA);
        });
        assertTrue(exception.getMessage().contains("Debe indicar un Paquete de Vuelo"));
    }


    @Test
    public void testAgregarRutaPaqueteNickAerolineaNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.agregarRutaPaquete("PaqueteTest", null, "RutaTest", 2, TipoAsiento.TURISTA);
        });
        assertTrue(exception.getMessage().contains("Debe indicar una Aerolinea"));
    }


    @Test
    public void testAgregarRutaPaqueteNombreRutaNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.agregarRutaPaquete("PaqueteTest", "AerolineaTest", null, 2, TipoAsiento.TURISTA);
        });
        assertTrue(exception.getMessage().contains("Debe indicar una Ruta de Vuelo"));
    }


    @Test
    public void testAgregarRutaPaqueteTipoAsientoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.agregarRutaPaquete("PaqueteTest", "AerolineaTest", "RutaTest", 2, null);
        });
        assertTrue(exception.getMessage().contains("Debe indicar el tipo de asiento"));
    }


    @Test
    public void testAgregarRutaPaqueteCantidadNula() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.agregarRutaPaquete("PaqueteTest", "AerolineaTest", "RutaTest", null, TipoAsiento.TURISTA);
        });
        assertTrue(exception.getMessage().contains("La cantidad debe ser mayor a 0"));
    }


    @Test
    public void testAgregarRutaPaqueteCantidadInvalida() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            sistema.agregarRutaPaquete("PaqueteTest", "AerolineaTest", "RutaTest", 0, TipoAsiento.TURISTA);
        });
        assertTrue(exception.getMessage().contains("La cantidad debe ser mayor a 0"));
    }


    @Test
    public void testComprarPaqueteExitosa() {
        LocalDate hoy = LocalDate.now();


        // Crear cliente
        sistema.altaCliente("cli120", "Cliente 1", "cli120@email.com", "1234", null,
                "Apellido", hoy.minusYears(30), "Uruguaya", TipoDoc.CEDULA, "50556300");


        // Crear aerolinea y ruta
        sistema.altaAerolinea("AerolineaTest20", "Aerolinea Test", "correo20@aero.com", "123", null, "Descripcion", null);
        sistema.altaRutaVuelo("RutaTest20", "Ruta prueba", hoy, 100f, 200f, 50f, "AerolineaTest",
                "Montevideo", "Buenos Aires", "Internacional", null, null,null);


        // Crear paquete
        sistema.altaPaqueteRV("PaqueteTest20", "Paquete de prueba", 10, 100f, hoy);


        // Agregar ruta al paquete
        sistema.agregarRutaPaquete("PaqueteTest20", "AerolineaTest20", "RutaTest20", 2, TipoAsiento.TURISTA);


        // Comprar paquete
        sistema.comprarPaquete("cli120", "PaqueteTest20", hoy);


        // Obtener datos actualizados
        Cliente cliente = sistema.obtenerClientePorNickname("cli120");
        // Verificar que la reserva se creó correctamente
        assertEquals(1, cliente.getReserva().size());
        Reserva reserva = cliente.getReserva().get(0);
        assertEquals(cliente, reserva.getCliente());
        assertEquals(hoy, reserva.getFecha());
    }




    @Test
    public void testComprarPaqueteClienteNoExiste() {
        LocalDate hoy = LocalDate.now();
        sistema.altaPaqueteRV("PaqueteTest2", "Paquete de prueba", 10, 100f, hoy);


        // Cliente inexistente
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.comprarPaquete("cliInexistente", "PaqueteTest2", hoy)
        );
        assertEquals("Cliente no encontrado", exception.getMessage());
    }


    @Test
    public void testComprarPaquetePaqueteNoExiste() {
        LocalDate hoy = LocalDate.now();
        sistema.altaCliente("cli13", "Cliente 1", "cli13@email.com", "1234", null, "Apellido", hoy.minusYears(30), "Uruguaya", TipoDoc.CEDULA, "55835414");


        // Paquete inexistente
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                sistema.comprarPaquete("cli13", "PaqueteInexistente", hoy)
        );
        assertEquals("Paquete no encontrado", exception.getMessage());
    }


    @Test
    public void testComprarPaqueteYaComprado() {
        LocalDate hoy = LocalDate.now();
        sistema.altaCliente("cli1", "Cliente 1", "cli1@email.com", "1234", null, "Apellido", hoy.minusYears(30), "Uruguaya", TipoDoc.CEDULA, "52772102");
        sistema.altaPaqueteRV("PaqueteTest30", "Paquete de prueba", 10, 100f, hoy);


        // Comprar la primera vez
        sistema.comprarPaquete("cli1", "PaqueteTest30", hoy);


        // Intentar comprar nuevamente
        Exception exception = assertThrows(IllegalStateException.class, () ->
                sistema.comprarPaquete("cli1", "PaqueteTest30", hoy)
        );
        assertEquals("El cliente ya compró este paquete", exception.getMessage());
    }


    @Test
    void testValidarPasaporte() {
        assertTrue(sistema.validarPasaporte("A1234567"));
        assertFalse(sistema.validarPasaporte("12345678"));   // No empieza con letra
        assertFalse(sistema.validarPasaporte("AB123456"));   // Más de una letra
        assertFalse(sistema.validarPasaporte(null));
        assertFalse(sistema.validarPasaporte(""));
    }


    // ===================== listarAerolineas =====================
    @Test
    void testListarAerolineas() {
        List<Aerolinea> aerolineas = sistema.listarAerolineas();
        assertNotNull(aerolineas);
    }


    // ===================== listarDtReservasDeVuelo =====================
    @Test
    void testListarDtReservasDeVuelo() {
        List<DtReserva> reservas = sistema.listarDtReservasDeVuelo("VueloInexistente");
        assertNotNull(reservas);
        assertTrue(reservas.isEmpty() || reservas.get(0) instanceof DtReserva);
    }


    // ===================== obtenerReservasPorClienteVuelo =====================
    @Test
    void testObtenerReservasPorClienteVuelo() {
        List<String> reservas = sistema.obtenerReservasPorClienteVuelo("VueloInexistente", "ClienteInexistente");
        assertNotNull(reservas);
    }


    // ===================== obtenerDtReservaPorClienteVuelo =====================
    @Test
    void testObtenerDtReservaPorClienteVuelo() {
        DtReserva reserva = sistema.obtenerDtReservaPorClienteVuelo("VueloInexistente", "ClienteInexistente");
        assertTrue(reserva == null || reserva instanceof DtReserva);
    }
    
    // ===================== obtenerListaNombresRutasPorPaquete =====================
    @Test
    void testObtenerListaNombresRutasPorPaquete() {
        List<String> rutas = sistema.obtenerListaNombresRutasPorPaquete("PaqueteInexistente");
        assertNotNull(rutas);
        assertTrue(rutas.isEmpty() || rutas.get(0) instanceof String);
    }

    @Test
    void testSubirImagenConNull() {
        // Pasamos null para cubrir el catch
        String url = sistema.subirImagen(null, "nombreArchivo");
        assertNull(url, "Debe devolver null si se pasa null");
    }*/
}
