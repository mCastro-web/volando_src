package model;


import data_types.DtItemPaquete;
import data_types.TipoAsiento;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.AccessType;
import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.FetchType;

@Entity
@Table(name = "ItemsPaquete")
@Access(AccessType.FIELD)
public class ItemPaquete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;

    @Column(name = "cantidad", nullable = false)
    private int cant;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_asiento", length = 20, nullable = false)
    private TipoAsiento tipoAsiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_nombre", referencedColumnName = "nombre", nullable = false)
    private RutaVuelo rutaVuelo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "paquete_id")
    private PaqueteVuelo paqueteVuelo;

    public ItemPaquete() {}

    public ItemPaquete(int cant, TipoAsiento tipoAsiento, RutaVuelo ruta, PaqueteVuelo paquete) {
        this.cant = cant;
        this.tipoAsiento = tipoAsiento;
        this.rutaVuelo = ruta;
        this.paqueteVuelo = paquete;
    }



    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }

    public int getCant() {
        return cant;
    }
    public void setCant(int cant) {
        this.cant = cant;
    }

    public TipoAsiento getTipoAsiento() {
        return tipoAsiento;
    }
    public void setTipoAsiento(TipoAsiento tipoAsiento) {
        this.tipoAsiento = tipoAsiento;
    }

    public RutaVuelo getRutaVuelo(){ return rutaVuelo; }
    public void setRutaVuelo(RutaVuelo rutaVuelo) { this.rutaVuelo = rutaVuelo; }

    public PaqueteVuelo getPaqueteVuelo(){return paqueteVuelo; }
    public void setPaqueteVuelo(PaqueteVuelo paqueteVuelo) { this.paqueteVuelo = paqueteVuelo; }

    public DtItemPaquete toDto() {
        String nombreRuta = null;
        if (this.rutaVuelo != null) {
            nombreRuta = this.rutaVuelo.getNombre();
        }

        return new DtItemPaquete(
                this.cant,
                this.tipoAsiento,
                this.rutaVuelo != null ? this.rutaVuelo.getNombre() : "Sin ruta"
        );
    }
}
