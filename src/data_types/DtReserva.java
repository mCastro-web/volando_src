package data_types;

import model.PaqueteVuelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DtReserva {

    private final LocalDate fecha;
    private final TipoAsiento tipoAsiento;
    private final int equipajeExtra;
    private final float costo;
    private final DtCliente cliente;
    private final DtVuelo vuelo;
    private final DtPaqueteVuelo paquete;      // puede ser null
    private final List<DtPasaje> pasajes;      // lista de pasajes
    private final LocalDate validez;
    private final Boolean checkin;

    // Constructor
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
    public LocalDate getFecha() { return fecha; }
    public TipoAsiento getTipoAsiento() { return tipoAsiento; }
    public int getEquipajeExtra() { return equipajeExtra; }
    public float getCosto() { return costo; }
    public DtCliente getCliente() { return cliente; }
    public DtVuelo getVuelo() { return vuelo; }
    public DtPaqueteVuelo getPaquete() { return paquete; }
    public List<DtPasaje> getPasajes() { return pasajes; }
    public LocalDate getValidez() { return validez; }
    public Boolean getCheckin() { return checkin; }
}
