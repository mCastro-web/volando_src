package test.java;

import data_types.DtCategoria;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtCategoria {

    @Test
    public void testConstructorYGetter() {
        String nombre = "Regional";
        DtCategoria dtCategoria = new DtCategoria(nombre);

        assertEquals(nombre, dtCategoria.getNombre());
    }
}
