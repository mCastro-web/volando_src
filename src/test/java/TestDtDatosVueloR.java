package test.java;

import data_types.DtDatosVueloR;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtDatosVueloR {

    @Test
    public void testConstructorYGetters() {
        String nombre = "Vuelo001";
        LocalDate fecha = LocalDate.of(2025, 10, 20);
        LocalTime duracion = LocalTime.of(2, 30);
        int asientosTurista = 50;
        int asientosEjecutivo = 20;
        LocalDate fechaAlta = LocalDate.of(2025, 10, 1);
        String origen = "Montevideo";
        String destino = "Buenos Aires";
        float costoEjecutivo = 200f;
        float costoTurista = 100f;
        float costoEquipajeExtra = 30f;

        DtDatosVueloR dtDatosVueloR = new DtDatosVueloR(nombre, fecha, duracion, asientosTurista, asientosEjecutivo,
                fechaAlta, origen, destino, costoEjecutivo, costoTurista, costoEquipajeExtra);

        assertEquals(nombre, dtDatosVueloR.getNombre());
        assertEquals(fecha, dtDatosVueloR.getFecha());
        assertEquals(duracion, dtDatosVueloR.getDuracion());
        assertEquals(asientosTurista, dtDatosVueloR.getAsientosTurista());
        assertEquals(asientosEjecutivo, dtDatosVueloR.getAsientosEjecutivo());
        assertEquals(fechaAlta, dtDatosVueloR.getFechaAlta());
        assertEquals(origen, dtDatosVueloR.getOrigen());
        assertEquals(destino, dtDatosVueloR.getDestino());
        assertEquals(costoEjecutivo, dtDatosVueloR.getCostoEjecutivo());
        assertEquals(costoTurista, dtDatosVueloR.getCostoTurista());
        assertEquals(costoEquipajeExtra, dtDatosVueloR.getCostoEquipajeExtra());
    }
}
