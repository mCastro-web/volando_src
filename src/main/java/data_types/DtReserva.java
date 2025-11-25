package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "DtReserva")
@XmlType(name = "DtReserva")
@XmlAccessorType(XmlAccessType.FIELD)
public class DtReserva {

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    private TipoAsiento tipoAsiento;
    private int equipajeExtra;
    private float costo;
    private DtCliente cliente;
    private DtVuelo vuelo;
    private DtPaqueteVuelo paquete; // puede ser null
    private List<DtPasaje> pasajes; // lista de pasajes

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate validez;

    private Boolean checkin;

    // Constructor vacío requerido por JAXB
    public DtReserva() {
        this.pasajes = new ArrayList<>();
    }

    // Constructor completo
    public DtReserva(
            LocalDate fecha,
            TipoAsiento tipoAsiento,
            int equipajeExtra,
            float costo,
            DtCliente cliente,
            DtVuelo vuelo,
            DtPaqueteVuelo paquete,
            List<DtPasaje> pasajes,
            LocalDate validez,
            Boolean checkin) {

        this.fecha = fecha;
        this.tipoAsiento = tipoAsiento;
        this.equipajeExtra = equipajeExtra;
        this.costo = costo;
        this.cliente = cliente;
        this.vuelo = vuelo;
        this.paquete = paquete;
        this.pasajes = (pasajes != null) ? pasajes : new ArrayList<>();
        this.validez = validez;
        this.checkin = checkin;
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }

    public int getEquipajeExtra() {
        return equipajeExtra;
    }

    public float getCosto() {
        return costo;
    }

    public DtCliente getCliente() {
        return cliente;
    }

    public DtVuelo getVuelo() {
        return vuelo;
    }

    public DtPaqueteVuelo getPaquete() {
        return paquete;
    }

    public List<DtPasaje> getPasajes() {
        return pasajes;
    }

    public LocalDate getValidez() {
        return validez;
    }

    public Boolean getCheckin() {
        return checkin;
    }

    // Setters
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public void setEquipajeExtra(int equipajeExtra) {
        this.equipajeExtra = equipajeExtra;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public void setCliente(DtCliente cliente) {
        this.cliente = cliente;
    }

    public void setVuelo(DtVuelo vuelo) {
        this.vuelo = vuelo;
    }

    public void setPaquete(DtPaqueteVuelo paquete) {
        this.paquete = paquete;
    }

    public void setPasajes(List<DtPasaje> pasajes) {
        this.pasajes = pasajes;
    }

    public void setValidez(LocalDate validez) {
        this.validez = validez;
    }

    public void setCheckin(Boolean checkin) {
        this.checkin = checkin;
    }
}
