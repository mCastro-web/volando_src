package test.java;

import data_types.DtUsuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtUsuario {

    @Test
    public void testDtUsuario() {
        DtUsuario usuario = new DtUsuario(
                "nickTest",
                "Juan Perez",
                "juan@test.com",
                "https://img.com/juan.jpg",
                "CLIENTE"
        );

        assertEquals("nickTest", usuario.getNickname());
        assertEquals("Juan Perez", usuario.getNombre());
        assertEquals("juan@test.com", usuario.getEmail());
        assertEquals("https://img.com/juan.jpg", usuario.getUrlImagen());
        assertEquals("CLIENTE", usuario.getTipo());
    }
}