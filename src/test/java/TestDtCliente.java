package test.java;

import data_types.DtCliente;
import data_types.TipoDoc;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtCliente {

    @Test
    public void testConstructorYGetters() {
        String nickname = "juan123";
        String nombre = "Juan";
        String email = "juan@test.com";
        String urlImagen = "http://img.com/juan.jpg";
        String apellido = "Perez";
        LocalDate fechaNac = LocalDate.of(1990, 1, 1);
        String nacionalidad = "Uruguaya";
        TipoDoc tipoDoc = TipoDoc.CEDULA;
        String numeroDoc = "12345678";

        DtCliente dtCliente = new DtCliente(nickname, nombre, email, urlImagen,
                apellido, fechaNac, nacionalidad, tipoDoc, numeroDoc);

        assertEquals(nickname, dtCliente.getNickname());
        assertEquals(nombre, dtCliente.getNombre());
        assertEquals(email, dtCliente.getEmail());
        assertEquals(urlImagen, dtCliente.getUrlImagen());
        assertEquals("CLIENTE", dtCliente.getTipo());
        assertEquals(apellido, dtCliente.getApellido());
        assertEquals(fechaNac, dtCliente.getFechaNacimiento());
        assertEquals(nacionalidad, dtCliente.getNacionalidad());
        assertEquals(tipoDoc, dtCliente.getTipoDoc());
        assertEquals(numeroDoc, dtCliente.getNumeroDoc());
    }
}
