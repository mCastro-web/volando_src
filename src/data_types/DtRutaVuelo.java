package data_types;

import java.time.LocalDate;
import java.util.List;

public class DtRutaVuelo {
    private final String nombre;
    private final String descripcion;
    private final LocalDate fechaAlta;
    private final float costoBaseTurista;
    private final float costoBaseEjecutivo;
    private final float costoEquipajeExtra;
    private final String origen;
    private final String destino;
    private final String aerolinea;
    private final String categoria;
    private final List<String> vuelos; // <-- ahora incluimos los vuelos
    private final String urlImagen;
    private final String descripcionCorta;
    private final EstadoRuta estado;

    public DtRutaVuelo(
            String nombre,
            String descripcion,
            LocalDate fechaAlta,
            float costoBaseTurista,
            float costoBaseEjecutivo,
            float costoEquipajeExtra,
            String origen,
            String destino,
            String aerolinea,
            String categoria,
            List<String> vuelos,
            String urlImagen,
            String descripcionCorta,
            EstadoRuta estado
    ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.costoBaseTurista = costoBaseTurista;
        this.costoBaseEjecutivo = costoBaseEjecutivo;
        this.costoEquipajeExtra = costoEquipajeExtra;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.categoria = categoria;
        this.vuelos = vuelos;
        this.urlImagen = urlImagen;
        this.descripcionCorta = descripcionCorta;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public float getCostoBaseTurista() {
        return costoBaseTurista;
    }

    public float getCostoBaseEjecutivo() {
        return costoBaseEjecutivo;
    }

    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public String getCategoria() {
        return categoria;
    }

    public List<String> getVuelos() {
        return vuelos;
    }

    public String getUrlImagen() {return urlImagen; }

    public String getDescripcionCorta() {return descripcionCorta; }

    public EstadoRuta getEstado() {return estado; }
}