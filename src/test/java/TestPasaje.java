package test.java;

import model.Pasaje;
import model.Reserva;
import data_types.DtPasaje;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestPasaje {

    @Test
    public void testConstructorYGettersSetters() {

        Reserva reserva = new Reserva();


        Pasaje pasaje = new Pasaje("Juan", "Pérez", reserva);


        assertEquals("Juan", pasaje.getNombre());
        assertEquals("Pérez", pasaje.getApellido());
        assertEquals(reserva, pasaje.getReserva());
        assertNull(pasaje.getIdentificador());


        pasaje.setNombre("Carlos");
        pasaje.setApellido("Gómez");
        Reserva nuevaReserva = new Reserva();
        pasaje.setReserva(nuevaReserva);


        assertEquals("Carlos", pasaje.getNombre());
        assertEquals("Gómez", pasaje.getApellido());
        assertEquals(nuevaReserva, pasaje.getReserva());
    }

    @Test
    public void testToDto() {
        Pasaje pasaje = new Pasaje("Ana", "López", new Reserva());
        DtPasaje dto = pasaje.toDto();

        // Verificar conversión
        assertNotNull(dto);
        assertEquals("Ana", dto.getNombre());
        assertEquals("López", dto.getApellido());
    }

    @Test
    public void testConstructorVacio() {
        // Verificar que el constructor vacío no rompe nada
        Pasaje pasaje = new Pasaje();
        assertNotNull(pasaje);
        assertNull(pasaje.getNombre());
        assertNull(pasaje.getApellido());
        assertNull(pasaje.getReserva());
    }
}
