package data_types;

import java.time.LocalDate;

public class DtAeropuerto {
    private final String nombre;
    private final String descripcion;
    private final String sitioWeb;   // puede ser null
    private final LocalDate fechaAlta;

    public DtAeropuerto(String nombre, String descripcion, String sitioWeb, LocalDate fechaAlta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
        this.fechaAlta = fechaAlta;
    }

    public String getNombre()      { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getSitioWeb()    { return sitioWeb; }
    public LocalDate getFechaAlta()  { return fechaAlta; }
}
