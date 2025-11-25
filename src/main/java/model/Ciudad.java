package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "Ciudades")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    private String nombre;
    private String pais;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aeropuerto_id")
    private Aeropuerto aeropuerto;

    public Ciudad() { }

    public Ciudad(String nombre, String pais, Aeropuerto aeropuerto) {
        this.nombre = nombre;
        this.pais = pais;
        this.aeropuerto = aeropuerto;
        if (aeropuerto != null) {
            aeropuerto.setCiudad(this);
        }
    }

    // Getters y Setters
    public Long getIdentificador() { return identificador; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPais() { return pais; }
   // public void setPais(String pais) { this.pais = pais; }
    public Aeropuerto getAeropuerto() { return aeropuerto; }
    public void setAeropuerto(Aeropuerto aeropuerto) { this.aeropuerto = aeropuerto; }
}
