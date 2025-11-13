package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.AccessType;
import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Transient;

@Entity
@Table(
        name = "PaquetesVuelo", uniqueConstraints = {
        @UniqueConstraint(name = "uk_paquetes_nombre", columnNames = {"nombre"})
}
)
@Access(AccessType.FIELD)
public class PaqueteVuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;

    @Column(name = "dias_validez", nullable = false)
    private int diasValidez;

    @Column(name = "descuento", nullable = false)
    private float descuento;

    @Column(name = "costo", nullable = false)
    private float costo = 0;

    @Column(name = "altafecha", nullable = false)
    private LocalDate altaFecha;

    @OneToMany(mappedBy = "paqueteVuelo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPaquete> items = new ArrayList<ItemPaquete>();

    // @OneToMany(mappedBy = "paquete", cascade = CascadeType.ALL, orphanRemoval = true)
    //private Set<Reserva> reservaPaquete = new HashSet<>();

    @Transient
    private List<Reserva> reservaPaquete = new ArrayList<Reserva>();

    public  PaqueteVuelo() {}

    public PaqueteVuelo(String nombre, String descripcion, int diasValidez, float descuento, LocalDate altaFecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.diasValidez = diasValidez;
        this.descuento = descuento;
        this.altaFecha =altaFecha;
    }

    // --- Getters/Setters ---
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) {  this.identificador = identificador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getDiasValidez() { return diasValidez; }
 //   public void setDiasValidez(int diasValidez) { this.diasValidez = diasValidez; }

    public float getDescuento() { return descuento; }
    public void setDescuento(float descuento) { this.descuento = descuento; }

    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }

    public LocalDate getAltaFecha() { return altaFecha; }        // <- ahora LocalDate
  //  public void setAltaFecha(LocalDate altaFecha) { this.altaFecha = altaFecha; }

    public List<ItemPaquete> getItems() { return items; }
  //  public void setItems(List<ItemPaquete> items) { this.items = (items != null) ? items : new ArrayList<ItemPaquete>(); }

    public List<Reserva> getReservaPaquete() { return reservaPaquete; }
 /*   public void setReservaPaquete(List<Reserva> reservaPaquete) {
        this.reservaPaquete = (reservaPaquete != null) ? reservaPaquete : new ArrayList<Reserva>();
    }*/

 /*   public float getCostoConDescuento() {
        if (descuento > 0) {
            return costo - (costo * (descuento / 100f));
        }
        return costo;
    }*/
}
