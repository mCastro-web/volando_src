package data_types;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DtVuelo {
    private String nombre;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fecha;
    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime duracion;
    private int asientosTurista;
    private int asientosEjecutivo;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaAlta;
    private String ruta;
    private String urlImagen;

    public DtVuelo() {
    }

    public DtVuelo(
            String nombre,
            LocalDate fecha,
            LocalTime duracion,
            int asientosTurista,
            int asientosEjecutivo,
            LocalDate fechaAlta,
            String ruta,
            String urlImagen) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.duracion = duracion;
        this.asientosTurista = asientosTurista;
        this.asientosEjecutivo = asientosEjecutivo;
        this.fechaAlta = fechaAlta;
        this.ruta = ruta;
        this.urlImagen = urlImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public int getAsientosTurista() {
        return asientosTurista;
    }

    public void setAsientosTurista(int asientosTurista) {
        this.asientosTurista = asientosTurista;
    }

    public int getAsientosEjecutivo() {
        return asientosEjecutivo;
    }

    public void setAsientosEjecutivo(int asientosEjecutivo) {
        this.asientosEjecutivo = asientosEjecutivo;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
