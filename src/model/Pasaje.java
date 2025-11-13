package model;

import data_types.DtPasaje;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.AccessType;
import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "Pasajes")
@Access(AccessType.FIELD)
public class Pasaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pasaje")
    private Long identificador;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 120)
    private String apellido;

    // Muchos Pasajes pertenecen a una Reserva
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false, updatable = false)
    private Reserva reserva;


    public Pasaje() {}

    public Pasaje(String nombre, String apellido, Reserva reserva) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.reserva = reserva;
    }

    // Getters/Setters
    public Long getIdentificador() { return identificador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Reserva getReserva() { return reserva; }
    public void setReserva(Reserva reserva) { this.reserva = reserva; }

    public DtPasaje toDto() {
        return new DtPasaje(this.identificador, this.nombre, this.apellido);
    }
}
