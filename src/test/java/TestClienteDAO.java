package test.java;

import dao.ClienteDAO;
import model.Cliente;
import data_types.TipoDoc;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class TestClienteDAO {

    private static ClienteDAO dao;

    @BeforeAll
    public static void setup() {
        dao = new ClienteDAO(); // que use la PU de test
    }

    @Test
    public void testGuardarYObtenerCliente() {
        Cliente cliente = new Cliente(
                "clientetest", "Juan", "juan@test.com", "123456", "urlImagen",
                "Perez", LocalDate.of(1990, 1, 1), "Uruguaya",
                TipoDoc.CEDULA, "12345678"
        );
        dao.guardarCliente(cliente);

        Cliente obt = dao.obtenerClientePorNickname("clientetest");
        assertNotNull(obt, "El cliente no debería ser nulo");
        assertEquals("Juan", obt.getNombre());
        assertEquals("Perez", obt.getApellido());
        assertEquals(TipoDoc.CEDULA, obt.getTipoDocumento());
    }

    @Test
    public void testGuardarClienteDuplicado() {
        Cliente cliente = new Cliente(
                "clientetest2", "Ana", "ana@test.com", "abcdef", "urlImagen",
                "Lopez", LocalDate.of(1995, 5, 5), "Uruguaya",
                TipoDoc.PASAPORTE, "A1234567"
        );
        dao.guardarCliente(cliente);

        // duplicado por nickname
        Cliente duplicado = new Cliente(
                "clientetest2", "Ana2", "ana2@test.com", "abcdef2", "urlImagen2",
                "Lopez2", LocalDate.of(1995, 5, 5), "Uruguaya",
                TipoDoc.PASAPORTE, "A12345678"
        );
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.guardarCliente(duplicado);
        });
        assertTrue(exception.getMessage().contains("nickname"));

        // duplicado por email
        Cliente duplicadoEmail = new Cliente(
                "clientetest3", "Ana3", "ana@test.com", "abcdef3", "urlImagen3",
                "Lopez3", LocalDate.of(1995, 5, 5), "Uruguaya",
                TipoDoc.PASAPORTE, "A12345679"
        );
        exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.guardarCliente(duplicadoEmail);
        });
        assertTrue(exception.getMessage().contains("email"));

        // duplicado por documento
        Cliente duplicadoDoc = new Cliente(
                "clientetest4", "Ana4", "ana4@test.com", "abcdef4", "urlImagen4",
                "Lopez4", LocalDate.of(1995, 5, 5), "Uruguaya",
                TipoDoc.PASAPORTE, "A1234567"
        );
        exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.guardarCliente(duplicadoDoc);
        });
        assertTrue(exception.getMessage().contains("Documento"));
    }

    @Test
    public void testListarNicknames() {
        List<String> nicks = dao.listarNicknames();
        assertNotNull(nicks);
        // opcionalmente podemos chequear que contenga algún nickname guardado
        assertTrue(nicks.contains("clientetest") || nicks.contains("clientetest2"));
    }

    @Test
    public void testModificarCliente() {
        Cliente cliente = new Cliente(
                "clientetestmod", "Pedro", "pedro@test.com", "abc123", "urlOriginal",
                "Sosa", LocalDate.of(1988, 3, 15), "Uruguaya",
                TipoDoc.CEDULA, "45678901"
        );
        dao.guardarCliente(cliente);

        // Modificamos algunos campos
        cliente.setNombre("Pedro Modificado");
        cliente.setUrlImagen("urlNueva");
        cliente.setNacionalidad("Argentina");

        dao.modificarCliente(cliente, cliente.getNickname());

        Cliente actualizado = dao.obtenerClientePorNickname("clientetestmod");
        assertNotNull(actualizado, "El cliente modificado no debería ser nulo");
        assertEquals("Pedro Modificado", actualizado.getNombre());
        assertEquals("urlNueva", actualizado.getUrlImagen());
        assertEquals("Argentina", actualizado.getNacionalidad());
    }


}
