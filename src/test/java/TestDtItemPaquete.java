package test.java;

import data_types.DtItemPaquete;
import data_types.TipoAsiento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDtItemPaquete {

    @Test


    public void testConstructorYGetters() {
        int cant = 2;
        TipoAsiento tipo = TipoAsiento.TURISTA;
        String nombreRuta = "ruta1";

        DtItemPaquete dtItemPaquete = new DtItemPaquete(cant, tipo, nombreRuta);

        // Validar constructor y getters
        assertEquals(cant, dtItemPaquete.getCant());
        assertEquals(tipo, dtItemPaquete.getTipoAsiento());
        assertEquals(nombreRuta, dtItemPaquete.getNombreRuta());
    }
}
