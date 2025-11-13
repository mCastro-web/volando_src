package test.java;

import data_types.DtPasaje;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtPasaje {

    @Test
    public void testDtPasaje() {
        DtPasaje pasaje = new DtPasaje(1L, "Juan", "Perez");

        assertEquals(1L, pasaje.getId());
        assertEquals("Juan", pasaje.getNombre());
        assertEquals("Perez", pasaje.getApellido());
    }
}