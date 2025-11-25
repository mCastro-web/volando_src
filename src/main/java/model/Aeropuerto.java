package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;


@Entity
@Table(name = "Aeropuertos")

public class Aeropuerto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    private String nombre;
    private String descripcion;
    private String sitioWeb;
    private LocalDate fechaAlta;

    @OneToOne(mappedBy = "aeropuerto")
    private Ciudad ciudad;

    public Aeropuerto() { }

    public Aeropuerto(String nombre, String descripcion, String sitioWeb, LocalDate fechaAlta, Ciudad ciudad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
        this.fechaAlta = fechaAlta;
        this.ciudad = ciudad;
    }

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getSitioWeb() { return sitioWeb; }
    public void setSitioWeb(String sitioWeb) { this.sitioWeb = sitioWeb; }

    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }

    public Ciudad getCiudad() { return ciudad; }
    public void setCiudad(Ciudad ciudad) { this.ciudad = ciudad; }
}