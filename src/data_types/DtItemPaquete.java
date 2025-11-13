package data_types;

public class DtItemPaquete {

    private final int cant;
    private final TipoAsiento tipoAsiento;       // Enum, se puede usar directo
    private final String nombreRutaVuelo;         // DTO de RutaVuelo


    // Constructor
    public DtItemPaquete(
            int cant,
            TipoAsiento tipoAsiento,
            String nombreRutaVuelo
    ) {
        this.cant = cant;
        this.tipoAsiento = tipoAsiento;
        this.nombreRutaVuelo = nombreRutaVuelo;
    }

    // Getters
    public int getCant() {
        return cant;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public String getNombreRuta() {
        return nombreRutaVuelo;
    }

}
