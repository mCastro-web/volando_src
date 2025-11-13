package test.java;

import model.Ciudad;
import model.Aerolinea;
import model.Categoria;
import model.RutaVuelo;
import model.Vuelo;
import data_types.EstadoRuta;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRutaVuelo {

    @Test
    public void testGettersAndSetters() {
        // --- Datos base ---
        Ciudad origen = new Ciudad();
        origen.setNombre("Montevideo");

        Ciudad destino = new Ciudad();
        destino.setNombre("Buenos Aires");

        Aerolinea aerolinea = new Aerolinea();
        aerolinea.setNombre("LATAM");

        Categoria categoria = new Categoria();
        categoria.setNombre("Internacional");

        LocalDate fechaAlta = LocalDate.of(2025, 1, 1);

        // --- Constructor completo ---
        RutaVuelo ruta = new RutaVuelo(
                "Ruta MVD-BUE",
                "Vuelo directo entre Montevideo y Buenos Aires",
                fechaAlta,
                200.5f,
                450.0f,
                50.0f,
                origen,
                destino,
                aerolinea,
                categoria,
                "https://img.com/ruta.jpg",
                "Vuelo rápido"
        );

        // --- Validar getters ---
        assertEquals("Ruta MVD-BUE", ruta.getNombre());
        assertEquals("Vuelo directo entre Montevideo y Buenos Aires", ruta.getDescripcion());
        assertEquals(fechaAlta, ruta.getFechaAlta());
        assertEquals(200.5f, ruta.getCostoBaseTurista());
        assertEquals(450.0f, ruta.getCostoBaseEjecutivo());
        assertEquals(50.0f, ruta.getCostoEquipajeExtra());
        assertEquals(origen, ruta.getOrigen());
        assertEquals(destino, ruta.getDestino());
        assertEquals(aerolinea, ruta.getAerolinea());
        assertEquals(categoria, ruta.getCategoria());
        assertEquals("https://img.com/ruta.jpg", ruta.getUrlImagen());
        assertEquals("Vuelo rápido", ruta.getDescripcionCorta());
        assertEquals(EstadoRuta.INGRESADA, ruta.getEstado());

        // --- Probar setters disponibles ---
        ruta.setNombre("Ruta MVD-SCL");
        ruta.setDescripcion("Vuelo a Santiago");
        ruta.setFechaAlta(LocalDate.of(2025, 2, 1));
        ruta.setCostoBaseTurista(300.5f);
        ruta.setCostoBaseEjecutivo(600.0f);
        ruta.setOrigen(destino);
        ruta.setDestino(origen);
        ruta.setAerolinea(aerolinea);
        ruta.setCategoria(categoria);
        ruta.setUrlImagen("https://nuevaimagen.com/ruta.png");

        assertEquals("Ruta MVD-SCL", ruta.getNombre());
        assertEquals("Vuelo a Santiago", ruta.getDescripcion());
        assertEquals(LocalDate.of(2025, 2, 1), ruta.getFechaAlta());
        assertEquals(300.5f, ruta.getCostoBaseTurista());
        assertEquals(600.0f, ruta.getCostoBaseEjecutivo());
        assertEquals(destino, ruta.getOrigen());
        assertEquals(origen, ruta.getDestino());
        assertEquals(aerolinea, ruta.getAerolinea());
        assertEquals(categoria, ruta.getCategoria());
        assertEquals("https://nuevaimagen.com/ruta.png", ruta.getUrlImagen());
    }

    @Test
    public void testToDtoWithVuelos() {
        Ciudad origen = new Ciudad(); origen.setNombre("Montevideo");
        Ciudad destino = new Ciudad(); destino.setNombre("Buenos Aires");
        Aerolinea aerolinea = new Aerolinea(); aerolinea.setNombre("LATAM");
        Categoria categoria = new Categoria(); categoria.setNombre("Internacional");
        LocalDate fechaAlta = LocalDate.of(2025, 3, 10);

        RutaVuelo ruta = new RutaVuelo(
                "Ruta Test",
                "Ruta completa para test",
                fechaAlta,
                150.0f,
                300.0f,
                25.0f,
                origen,
                destino,
                aerolinea,
                categoria,
                "imagen.png",
                "desc corta"
        );

        // Agregar un vuelo al Map
        Vuelo vuelo = new Vuelo();
        vuelo.setNombre("Vuelo123");
        ruta.getVuelos().put("Vuelo123", vuelo);

        var dto = ruta.toDto();

        assertEquals("Ruta Test", dto.getNombre());
        assertEquals("Ruta completa para test", dto.getDescripcion());
        assertEquals(fechaAlta, dto.getFechaAlta());
        assertEquals(150.0f, dto.getCostoBaseTurista());
        assertEquals(300.0f, dto.getCostoBaseEjecutivo());
        assertEquals(25.0f, dto.getCostoEquipajeExtra());
        assertEquals("Montevideo", dto.getOrigen());
        assertEquals("Buenos Aires", dto.getDestino());
        assertEquals("LATAM", dto.getAerolinea());
        assertEquals("Internacional", dto.getCategoria());
        assertEquals("imagen.png", dto.getUrlImagen());
        assertEquals("desc corta", dto.getDescripcionCorta());
        assertEquals(EstadoRuta.INGRESADA, dto.getEstado());
        assertEquals(List.of("Vuelo123"), dto.getVuelos());
    }

    @Test
    public void testToDtoWithNullFields() {
        // Ruta con todos los campos asociados nulos
        RutaVuelo ruta = new RutaVuelo(
                "Ruta Null",
                "Sin datos",
                LocalDate.of(2025, 3, 11),
                10.0f,
                20.0f,
                5.0f,
                null,
                null,
                null,
                null,
                null,
                null
        );

        var dto = ruta.toDto();

        assertEquals("Ruta Null", dto.getNombre());
        assertEquals("Sin datos", dto.getDescripcion());
        assertEquals(LocalDate.of(2025, 3, 11), dto.getFechaAlta());
        assertEquals(10.0f, dto.getCostoBaseTurista());
        assertEquals(20.0f, dto.getCostoBaseEjecutivo());
        assertEquals(5.0f, dto.getCostoEquipajeExtra());
        assertNull(dto.getOrigen());
        assertNull(dto.getDestino());
        assertNull(dto.getAerolinea());
        assertNull(dto.getCategoria());
        assertNull(dto.getUrlImagen());
        assertNull(dto.getDescripcionCorta());
        assertEquals(EstadoRuta.INGRESADA, dto.getEstado());
        assertTrue(dto.getVuelos().isEmpty());
    }

}
