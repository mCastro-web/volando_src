package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import data_types.TipoAsiento;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.AccessType;
import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "reservas")
@Access(AccessType.FIELD)
public class Reserva {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;   // PK artificial


    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;   // antes DtFecha


    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_asiento", length = 20)
    private TipoAsiento tipoAsiento;


    @Column(name = "equipaje_extra")
    private int equipajeExtra;


    @Column(name = "costo", nullable = false)
    private float costo;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_nickname", referencedColumnName = "nickname", nullable = false)
    private Cliente cliente;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vuelo_nombre", referencedColumnName = "nombre")
    private Vuelo vuelo;


    @Column(name = "cantidad_pasajes")
    private int cantPasajes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paquete_id")
    private PaqueteVuelo paquete;

    // ---- Relación con Pasaje ----
    @OneToMany(mappedBy = "reserva", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pasaje> pasajes = new ArrayList<>();

    @Column(name = "vencimiento")
    private LocalDate vencimiento;

    @Column(name = "checkin_realizado")
    private Boolean checkinRealizado;

    @Column(name = "hora_inicio_embarque")
    private LocalDateTime horaInicioEmbarque;


    /** Constructor vacío requerido por JPA */
    public Reserva() { }


    public Reserva(TipoAsiento tipoAsiento,
                   LocalDate fecha,
                   int equipajeExtra,
                   float costo,
                   Cliente cliente,
                   Vuelo vuelo,
                   int cantPasajes,
                   PaqueteVuelo paquete,
                   List<Pasaje> pasajes,
                   LocalDate vencimiento) {
        this.tipoAsiento = tipoAsiento;
        this.fecha = fecha;
        this.equipajeExtra = equipajeExtra;
        this.costo = costo;
        this.cliente = cliente;
        this.vuelo = vuelo;
        this.cantPasajes = cantPasajes;
        this.paquete = paquete;
        setPasajes(pasajes); // usar el setter para mantener la relación consistente
        if (paquete != null) {
            this.vencimiento = fecha.plusDays(paquete.getDiasValidez());
        } else {
            this.vencimiento = fecha; // fallback si no hay paquete
        }
    }


    // --- Getters/Setters ---
    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) {this.identificador = identificador; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }


    public TipoAsiento getTipoAsiento() { return tipoAsiento; }
    public void setTipoAsiento(TipoAsiento tipoAsiento) { this.tipoAsiento = tipoAsiento; }


    public int getEquipajeExtra() { return equipajeExtra; }
    public void setEquipajeExtra(int equipajeExtra) { this.equipajeExtra = equipajeExtra; }


    public float getCosto() { return costo; }
    public void setCosto(float costo) { this.costo = costo; }


    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }


    public Vuelo getVuelo() { return vuelo; }
    public void setVuelo(Vuelo vuelo) { this.vuelo = vuelo; }


    public int getCantPasajes() { return cantPasajes; }


    public void setCantPasajes(int cantPasajes) {this.cantPasajes = cantPasajes; }

    public LocalDate getVencimiento() { return vencimiento;    }
    public void setVencimiento(LocalDate vencimiento) { this.vencimiento = vencimiento;    }

    public PaqueteVuelo getPaquete() { return paquete; }
    public void setPaquete(PaqueteVuelo paquete) { this.paquete = paquete; }


    public List<Pasaje> getPasajes() {
        return pasajes;
    }

    public void setPasajes(List<Pasaje> pasajes) {
        this.pasajes.clear();
        if (pasajes != null) {
            for (Pasaje pas : pasajes) {
                addPasaje(pas);
            }
        }
    }
    public void addPasaje(Pasaje pasaje) {
        if (pasaje == null) return;
        pasajes.add(pasaje);
        pasaje.setReserva(this);
    }

    public boolean getCheckin() {
        return checkinRealizado;
    }

    public void setCheckinRealizado(Boolean checkinRealizado) {
        this.checkinRealizado = checkinRealizado;
    }

    public LocalDateTime getHoraInicioEmbarque() {
        return horaInicioEmbarque;
    }

    public void setHoraInicioEmbarque(LocalDateTime horaInicioEmbarque) {
        this.horaInicioEmbarque = horaInicioEmbarque;
    }

}
