package model;

import data_types.TipoAsiento;
import jakarta.persistence.*;

@Entity
@Table(name = "PaqueteClienteItems")
@Access(AccessType.FIELD)
public class PaqueteClienteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El cliente dueño de este item del paquete
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_nickname", referencedColumnName = "nickname", nullable = false)
    private Cliente cliente;

    // Cantidad restante que el cliente puede usar
    @Column(name = "cantidad_restante", nullable = false)
    private int cantidadRestante;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_asiento", length = 20, nullable = false)
    private TipoAsiento tipoAsiento;

    // La ruta del vuelo incluida en ese ítem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_nombre", referencedColumnName = "nombre", nullable = false)
    private RutaVuelo rutaVuelo;

    // A qué compra/reserva de paquete pertenece este ítem
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reservaPaquete;

    public PaqueteClienteItem() {}

    public PaqueteClienteItem(Cliente cliente,
                              int cantidadRestante,
                              TipoAsiento tipoAsiento,
                              RutaVuelo rutaVuelo,
                              Reserva reservaPaquete) {
        this.cliente = cliente;
        this.cantidadRestante = cantidadRestante;
        this.tipoAsiento = tipoAsiento;
        this.rutaVuelo = rutaVuelo;
        this.reservaPaquete = reservaPaquete;
    }

    // ---- Getters y Setters ----

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public int getCantidadRestante() { return cantidadRestante; }
    public void setCantidadRestante(int cantidadRestante) {
        this.cantidadRestante = cantidadRestante;
    }

    public TipoAsiento getTipoAsiento() { return tipoAsiento; }
    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public RutaVuelo getRutaVuelo() { return rutaVuelo; }
    public void setRutaVuelo(RutaVuelo rutaVuelo) {
        this.rutaVuelo = rutaVuelo;
    }

    public Reserva getReservaPaquete() { return reservaPaquete; }
    public void setReservaPaquete(Reserva reservaPaquete) {
        this.reservaPaquete = reservaPaquete;
    }
}
