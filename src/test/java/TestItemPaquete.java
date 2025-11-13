package test.java;

import model.ItemPaquete;
import model.PaqueteVuelo;
import model.RutaVuelo;
import data_types.DtItemPaquete;
import data_types.TipoAsiento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestItemPaquete {

    @Test
    public void testConstructorYGettersSetters() {
        RutaVuelo ruta = new RutaVuelo();
        PaqueteVuelo paquete = new PaqueteVuelo();

        ItemPaquete item = new ItemPaquete(3, TipoAsiento.TURISTA, ruta, paquete);

        // Verificamos el constructor
        assertEquals(3, item.getCant());
        assertEquals(TipoAsiento.TURISTA, item.getTipoAsiento());
        assertEquals(ruta, item.getRutaVuelo());
        assertEquals(paquete, item.getPaqueteVuelo());
        assertNull(item.getIdentificador());

        // Modificamos todo con setters
        item.setCant(5);
        item.setTipoAsiento(TipoAsiento.EJECUTIVO);
        RutaVuelo nuevaRuta = new RutaVuelo();
        PaqueteVuelo nuevoPaquete = new PaqueteVuelo();
        item.setRutaVuelo(nuevaRuta);
        item.setPaqueteVuelo(nuevoPaquete);
        item.setIdentificador(99L);

        // Verificamos los getters
        assertEquals(5, item.getCant());
        assertEquals(TipoAsiento.EJECUTIVO, item.getTipoAsiento());
        assertEquals(nuevaRuta, item.getRutaVuelo());
        assertEquals(nuevoPaquete, item.getPaqueteVuelo());
        assertEquals(99L, item.getIdentificador());
    }

    @Test
    public void testToDtoConRuta() {
        // Ruta simulada con nombre
        RutaVuelo ruta = new RutaVuelo();
        ruta.setNombre("Montevideo-BuenosAires");

        ItemPaquete item = new ItemPaquete(2, TipoAsiento.TURISTA, ruta, new PaqueteVuelo());

        DtItemPaquete dto = item.toDto();

        assertNotNull(dto);
        assertEquals(2, dto.getCant());
        assertEquals(TipoAsiento.TURISTA, dto.getTipoAsiento());
        assertEquals("Montevideo-BuenosAires", dto.getNombreRuta());
    }

    @Test
    public void testToDtoSinRuta() {
        // rutaVuelo = null
        ItemPaquete item = new ItemPaquete(1, TipoAsiento.EJECUTIVO, null, new PaqueteVuelo());
        DtItemPaquete dto = item.toDto();

        assertNotNull(dto);
        assertEquals(1, dto.getCant());
        assertEquals(TipoAsiento.EJECUTIVO, dto.getTipoAsiento());
        assertEquals("Sin ruta", dto.getNombreRuta());
    }

    @Test
    public void testConstructorProtegido() {
        // Este constructor se usa por JPA, pero lo probamos igual
        ItemPaquete item = new ItemPaquete();
        assertNotNull(item);
        assertNull(item.getRutaVuelo());
        assertNull(item.getPaqueteVuelo());
        assertEquals(0, item.getCant());
    }
}
