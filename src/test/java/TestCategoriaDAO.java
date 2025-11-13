package test.java;

import dao.CategoriaDAO;
import model.Categoria;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;

public class TestCategoriaDAO {

    private static CategoriaDAO dao;

    @BeforeAll
    public static void setup() {
        dao = new CategoriaDAO(); // que use la PU de test
    }

    @Test
    public void testGuardarYObtenerCategoria() {
        Categoria categoria = new Categoria("CategoriaTest");
        dao.guardar(categoria);

        Categoria obt = dao.obtenerCategoriaPorNombre("CategoriaTest");
        assertNotNull(obt, "La categoría no debería ser nula");
        assertEquals("CategoriaTest", obt.getNombre());
    }

    @Test
    public void testGuardarCategoriaExistenteExcepcion() {
        Categoria categoria = new Categoria("CategoriaExistente");
        dao.guardar(categoria);

        Categoria categoria1 = new Categoria("CategoriaExistente");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            dao.guardar(categoria1);
        });
        assertTrue(exception.getMessage().contains("Ya existe una categoría"));
    }

    @Test
    public void testListarNombresCategoria() {
        List<String> nombres = dao.listarNombresCategoria();
        assertNotNull(nombres);
        // opcional: verificar que contenga la categoría creada
        assertTrue(nombres.contains("CategoriaTest") || nombres.contains("CategoriaExistente"));
    }
}
