package test.java;

import data_types.DtAerolinea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtAerolinea {

    @Test
    public void testConstructorYGetters() {
        String nickname = "Aero1";
        String nombre = "Aerolínea Test";
        String email = "contacto@test.com";
        String urlImagen = "https://img.com/logo.jpg";
        String descripcion = "Compañía aérea de prueba";
        String sitioWeb = "https://test.com";

        DtAerolinea dtAerolinea = new DtAerolinea(nickname, nombre, email, urlImagen, descripcion, sitioWeb);

        // Validar constructor y getters
        assertEquals(nickname, dtAerolinea.getNickname());
        assertEquals(nombre, dtAerolinea.getNombre());
        assertEquals(email, dtAerolinea.getEmail());
        assertEquals(urlImagen, dtAerolinea.getUrlImagen());
        assertEquals("AEROLINEA", dtAerolinea.getTipo());
        assertEquals(descripcion, dtAerolinea.getDescripcion());
        assertEquals(sitioWeb, dtAerolinea.getSitioWeb());
    }
}
