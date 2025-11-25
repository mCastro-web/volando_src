package test.java;

import dao.AerolineaDAO;
import model.Aerolinea;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAerolineaDAO {

    /*private static AerolineaDAO dao;

    @BeforeAll
    public static void setup() {
        dao = new AerolineaDAO(); // que use la PU de test
    }

    @Test
    public void testGuardarYObtenerAerolinea() {
        Aerolinea aerolinea = new Aerolinea("AeroTest", "AeroTest", "aeroTest@gmail.com", "123456", "1234", "descripcion", "www.test.com");
        dao.guardarAerolinea(aerolinea);

        Aerolinea obt = dao.obtenerAerolineaPorNick("AeroTest");
        assertNotNull(obt, "La aerolinea no debería ser nula");
        assertEquals("AeroTest", obt.getNombre());
        assertEquals("descripcion", obt.getDescripcion());
    }


    @Test
    public void testModificarAerolineaExcepcion() {
        Aerolinea aerolinea = new Aerolinea("AeroTest2", "AeroTest2", "aeroTest2@gmail.com", "1234567", "12345", "descripcion2", "www.test.com");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.modificarAerolinea(aerolinea, "inexistente");
        });
        assertTrue(exception.getMessage().contains("No existe aerolínea"));
    }

    @Test
    public void testListarNicknamesAerolinea() {
        List<String> nicks = dao.listarNicknamesAerolinea();
        assertNotNull(nicks);
    }

    @Test
    public void testModificarAerolineaConCambioDePassword() {
        Aerolinea aerolinea = new Aerolinea("NickTest", "Nombre", "mail@test.com", "pass1", "123", "desc", "url");
        dao.guardarAerolinea(aerolinea);

        Aerolinea modificada = new Aerolinea("NickTest", "NuevoNombre", "mail@test.com", "pass2", "123", "descModificada", "url");
        dao.modificarAerolinea(modificada, "NickTest");

        Aerolinea result = dao.obtenerAerolineaPorNick("NickTest");
        assertEquals("NuevoNombre", result.getNombre());
    }*/

}
