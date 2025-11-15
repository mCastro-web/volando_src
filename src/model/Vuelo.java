package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.AccessType;
import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "Vuelos")
@Access(AccessType.FIELD)
public class Vuelo {

    @Id
    @Column(name = "nombre", length = 120, nullable = false, updatable = false)
    private String nombre;   // PK natural (identificador del vuelo)

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;   // antes DtFecha

    @Column(name = "duracion", nullable = false)
    private LocalTime duracion;   // antes DtHora

    @Column(name = "asientos_turista", nullable = false)
    private int asientosTurista;

    @Column(name = "asientos_ejecutivo", nullable = false)
    private int asientosEjecutivo;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;   // antes DtFecha

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_nombre", referencedColumnName = "nombre", nullable = false)
    private RutaVuelo ruta;

    @OneToMany(mappedBy = "vuelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reservaVuelo = new ArrayList<>();

    @Column(name = "imagen")
    private String urlImagen;

    @Column(name = "contadorEjecutivo", nullable = false)
    private Integer contadorEjecutivo;

    @Column(name = "contadorTurista", nullable = false)
    private Integer contadorTurista;

    /** Constructor vacío requerido por JPA */
    public Vuelo() { }

    public Vuelo(String nombre,
                 LocalDate fecha,
                 LocalTime duracion,
                 int asientosTurista,
                 int asientosEjecutivo,
                 LocalDate fechaAlta,
                 RutaVuelo ruta,
                 String urlImagen) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.duracion = duracion;
        this.asientosTurista = asientosTurista;
        this.asientosEjecutivo = asientosEjecutivo;
        this.fechaAlta = fechaAlta;
        this.ruta = ruta;
        this.urlImagen = urlImagen;
        this.contadorEjecutivo = 0;
        this.contadorTurista = 0;
    }

    // --- Getters ---
    public String getNombre() { return nombre; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getDuracion() { return duracion; }
    public int getAsientosTurista() { return asientosTurista; }
    public int getAsientosEjecutivo() { return asientosEjecutivo; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public RutaVuelo getRuta() { return ruta; }
    public List<Reserva> getReservaVuelo() { return reservaVuelo; }
    public String getUrlImagen() {return urlImagen; }

    // --- Setters ---
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setDuracion(LocalTime duracion) { this.duracion = duracion; }
    public void setAsientosTurista(int asientosTurista) { this.asientosTurista = asientosTurista; }
    public void setAsientosEjecutivo(int asientosEjecutivo) { this.asientosEjecutivo = asientosEjecutivo; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public void setRuta(RutaVuelo ruta) { this.ruta = ruta; }
    public void setReservaVuelo(List<Reserva> reservaVuelo) { this.reservaVuelo = reservaVuelo; }
    public void setUrlImagen(String urlImagen) {this.urlImagen = urlImagen; }

    public Integer getContadorTurista() {
        return contadorTurista;
    }

    public void setContadorTurista(Integer contadorTurista) {
        this.contadorTurista = contadorTurista;
    }

    public Integer getContadorEjecutivo() {
        return contadorEjecutivo;
    }

    public void setContadorEjecutivo(Integer contadorEjecutivo) {
        this.contadorEjecutivo = contadorEjecutivo;
    }
}



