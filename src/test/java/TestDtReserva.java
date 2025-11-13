package test.java;

import data_types.DtCliente;
import data_types.DtPaqueteVuelo;
import data_types.DtVuelo;
import data_types.DtPasaje;
import data_types.DtReserva;
import data_types.TipoDoc;
import data_types.TipoAsiento;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtReserva {

    @Test
    public void testConstructorYGetters() {
        // 🔹 Crear DTOs dependientes
        DtCliente cliente = new DtCliente(
                "nickTest", "Juan", "juan@test.com", "urlImagen",
                "Perez", LocalDate.of(1990, 1, 1), "Uruguaya",
                TipoDoc.CEDULA, "12345678"
        );
        DtVuelo vuelo = new DtVuelo(
                "Vuelo001",
                LocalDate.of(2025, 10, 20),
                LocalTime.of(2, 30),
                50,
                20,
                LocalDate.of(2025, 10, 15),
                "RutaTest",
                //List.of(),
                "urlVuelo"
        );

        DtPaqueteVuelo paquete = new DtPaqueteVuelo(
                "Paquete001",
                "Descripcion paquete",
                10,
                0.1f,
                LocalDate.of(2025, 10, 1)
        );

        DtPasaje pasaje = new DtPasaje(1L, "Juan", "Perez");

        // 🔹 Crear DtReserva
        DtReserva reserva = new DtReserva(
                LocalDate.of(2025, 10, 20),
                TipoAsiento.TURISTA,
                5,
                1500f,
                cliente,
                vuelo,
                paquete,
                List.of(pasaje),
                LocalDate.of(2025, 10, 30)
        );

        // 🔹 Comprobar getters
        assertEquals(LocalDate.of(2025, 10, 20), reserva.getFecha());
        assertEquals(TipoAsiento.TURISTA, reserva.getTipoAsiento());
        assertEquals(5, reserva.getEquipajeExtra());
        assertEquals(1500f, reserva.getCosto());
        assertEquals(cliente, reserva.getCliente());
        assertEquals(vuelo, reserva.getVuelo());
        assertEquals(paquete, reserva.getPaquete());
        assertEquals(1, reserva.getPasajes().size());
        assertEquals(pasaje, reserva.getPasajes().get(0));
        assertEquals(LocalDate.of(2025, 10, 30), reserva.getValidez());
    }

    @Test
    public void testConstructorConPasajesNull() {
        DtCliente cliente = new DtCliente(
                "nickTest", "Juan", "juan@test.com", "urlImagen",
                "Perez", LocalDate.of(1990, 1, 1), "Uruguaya",
                TipoDoc.CEDULA, "12345678"
        );

        DtVuelo vuelo = new DtVuelo(
                "Vuelo002",
                LocalDate.of(2025, 11, 1),
                LocalTime.of(3, 0),
                50,
                20,
                LocalDate.of(2025, 10, 20),
                "RutaTest2",
                //List.of(),
                "urlVuelo2"
        );

        DtPaqueteVuelo paquete = new DtPaqueteVuelo(
                "Paquete002",
                "Descripcion paquete 2",
                5,
                50f,
                LocalDate.of(2025, 10, 25)
        );

        // Pasajes = null
        DtReserva reserva = new DtReserva(
                LocalDate.of(2025, 11, 1),
                TipoAsiento.EJECUTIVO,
                3,
                1200f,
                cliente,
                vuelo,
                paquete,
                null,  // <-- aquí probamos el caso null
                LocalDate.of(2025, 11, 15)
        );

        // Comprobaciones
        assertEquals(0, reserva.getPasajes().size());  // Debe inicializar la lista vacía
        assertEquals(cliente, reserva.getCliente());
        assertEquals(vuelo, reserva.getVuelo());
        assertEquals(paquete, reserva.getPaquete());
        assertEquals(LocalDate.of(2025, 11, 15), reserva.getValidez());
    }
}