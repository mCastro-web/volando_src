package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DtItemPaquete")
@XmlType(name = "DtItemPaquete")
@XmlAccessorType(XmlAccessType.FIELD)
public class DtItemPaquete {

    private int cant;
    private TipoAsiento tipoAsiento;
    private String nombreRutaVuelo;

    // Constructor vacío
    public DtItemPaquete() {
    }

    // Constructor
    public DtItemPaquete(
            int cant,
            TipoAsiento tipoAsiento,
            String nombreRutaVuelo) {
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

    // Setters
    public void setCant(int cant) {
        this.cant = cant;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public void setNombreRuta(String nombreRutaVuelo) {
        this.nombreRutaVuelo = nombreRutaVuelo;
    }
}
