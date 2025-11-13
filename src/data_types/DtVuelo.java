package data_types;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class DtVuelo {
    private final String nombre;
    private final LocalDate fecha;
    private final LocalTime duracion;
    private final int asientosTurista;
    private final int asientosEjecutivo;
    private final LocalDate fechaAlta;
    private final String ruta;              // relación con la ruta
    private final String urlImagen; // lista de reservas como DTOs

    // Constructor
    public DtVuelo(
            String nombre,
            LocalDate fecha,
            LocalTime duracion,
            int asientosTurista,
            int asientosEjecutivo,
            LocalDate fechaAlta,
            String ruta,
            String urlImagen
    ) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.duracion = duracion;
        this.asientosTurista = asientosTurista;
        this.asientosEjecutivo = asientosEjecutivo;
        this.fechaAlta = fechaAlta;
        this.ruta = ruta;
        this.urlImagen = urlImagen;
    }

    // Getters
    public String getNombre() { return nombre; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getDuracion() { return duracion; }
    public int getAsientosTurista() { return asientosTurista; }
    public int getAsientosEjecutivo() { return asientosEjecutivo; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public String getRuta() { return ruta; }
    public String getUrlImagen(){ return urlImagen; }
}
