package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.LocalTime;

@XmlRootElement(name = "DtDatosVueloR")
@XmlType(name = "DtDatosVueloR")
@XmlAccessorType(XmlAccessType.FIELD)
public class DtDatosVueloR {
    private String nombre;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;

    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime duracion;

    private int asientosTurista;
    private int asientosEjecutivo;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaAlta;

    private String origen;
    private String destino;
    private float costoEjecutivo;
    private float costoTurista;
    private float costoEquipajeExtra;
    private String urlImagen;

    // Constructor vacío
    public DtDatosVueloR() {
    }

    public DtDatosVueloR(
            String nombre,
            LocalDate fecha,
            LocalTime duracion,
            int asientosTurista,
            int asientosEjecutivo,
            LocalDate fechaAlta,
            String origen,
            String destino,
            float costoEjecutivo,
            float costoTurista,
            float costoEquipajeExtra,
            String urlImagen) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.duracion = duracion;
        this.asientosTurista = asientosTurista;
        this.asientosEjecutivo = asientosEjecutivo;
        this.fechaAlta = fechaAlta;
        this.origen = origen;
        this.destino = destino;
        this.costoEjecutivo = costoEjecutivo;
        this.costoTurista = costoTurista;
        this.costoEquipajeExtra = costoEquipajeExtra;
        this.urlImagen = urlImagen;
    }

    // Getters
    public float getCostoEjecutivo() {
        return costoEjecutivo;
    }

    public float getCostoTurista() {
        return costoTurista;
    }

    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public int getAsientosTurista() {
        return asientosTurista;
    }

    public int getAsientosEjecutivo() {
        return asientosEjecutivo;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public void setAsientosTurista(int asientosTurista) {
        this.asientosTurista = asientosTurista;
    }

    public void setAsientosEjecutivo(int asientosEjecutivo) {
        this.asientosEjecutivo = asientosEjecutivo;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setCostoEjecutivo(float costoEjecutivo) {
        this.costoEjecutivo = costoEjecutivo;
    }

    public void setCostoTurista(float costoTurista) {
        this.costoTurista = costoTurista;
    }

    public void setCostoEquipajeExtra(float costoEquipajeExtra) {
        this.costoEquipajeExtra = costoEquipajeExtra;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
