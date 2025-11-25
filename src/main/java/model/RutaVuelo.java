package model;

import data_types.DtRutaVuelo;
import data_types.EstadoRuta;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.AccessType;
import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKey;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import static data_types.EstadoRuta.INGRESADA;


@Entity
@Table(name = "RutasVuelo")
@Access(AccessType.FIELD)
public class RutaVuelo {


    @Id
    @Column(name = "nombre", length = 120, nullable = false, updatable = false)
    private String nombre;


    @Column(name = "descripcion", length = 500, nullable = false)
    private String descripcion;


    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;


    @Column(name = "costo_base_turista", nullable = false)
    private float costoBaseTurista;


    @Column(name = "costo_base_ejecutivo", nullable = false)
    private float costoBaseEjecutivo;


    @Column(name = "costo_equipaje_extra", nullable = false)
    private float costoEquipajeExtra;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "origen_id", nullable = false)
    private Ciudad origen;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "destino_id", nullable = false)
    private Ciudad destino;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aerolinea_nickname", referencedColumnName = "nickname", nullable = false)
    private Aerolinea aerolinea;


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;


    @OneToMany(mappedBy = "ruta", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "nombre")
    private final Map<String, Vuelo> vuelos = new HashMap<>();

    @Column(name = "imagen")
    private String urlImagen;

    @Column(name = "video")
    private String urlVideo;

    @Column(name = "descripcion_corta", length = 500)
    private String descripcionCorta;

    @Enumerated(EnumType.STRING)
    private EstadoRuta estado= INGRESADA;

    @Column(name = "cant_visitas", length = 500)
    private Integer cantVisitas = 0;

    // --- Constructores ---

    /**
     * Requerido por JPA
     */
    public RutaVuelo() {
    }


    public RutaVuelo(String nombre,
                     String descripcion,
                     LocalDate fechaAlta,
                     float costoBaseTurista,
                     float costoBaseEjecutivo,
                     float costoEquipajeExtra,
                     Ciudad origen,
                     Ciudad destino,
                     Aerolinea aerolinea,
                     Categoria categoria,
                     String urlImagen,
                     String urlVideo,
                     String descripcionCorta) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.costoBaseTurista = costoBaseTurista;
        this.fechaAlta = fechaAlta;
        this.costoBaseEjecutivo = costoBaseEjecutivo;
        this.costoEquipajeExtra = costoEquipajeExtra;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
        this.descripcionCorta = descripcionCorta;
        this.urlVideo = urlVideo;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    // --- Getters / Setters de atributos simples ---
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public LocalDate getFechaAlta() {
        return fechaAlta;
    }      // <-- LocalDate

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }


    public float getCostoBaseTurista() {
        return costoBaseTurista;
    }

    public void setCostoBaseTurista(float costoBaseTurista) {
        this.costoBaseTurista = costoBaseTurista;
    }


    public float getCostoBaseEjecutivo() {
        return costoBaseEjecutivo;
    }

    public void setCostoBaseEjecutivo(float costoBaseEjecutivo) {
        this.costoBaseEjecutivo = costoBaseEjecutivo;
    }


    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

  /*  public void setCostoEquipajeExtra(float costoEquipajeExtra) {
        this.costoEquipajeExtra = costoEquipajeExtra;
    }*/
    public Map<String, Vuelo> getVuelos() {
        return vuelos;
    }

    public Ciudad getOrigen() {
        return origen;
    }

    public void setOrigen(Ciudad origen) {
        this.origen = origen;
    }


    public Ciudad getDestino() {
        return destino;
    }

    public void setDestino(Ciudad destino) {
        this.destino = destino;
    }


    public Aerolinea getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(Aerolinea aerolinea) {
        this.aerolinea = aerolinea;
    }


    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getUrlImagen() {return urlImagen; }

    public void setUrlImagen(String urlImagen) {this.urlImagen = urlImagen; }

    public String getDescripcionCorta() {return descripcionCorta; }

   // public void setDescripcionCorta(String descripcionCorta) {this.descripcionCorta = descripcionCorta; }

    public EstadoRuta getEstado() {return estado; }


 //   public void setEstado(EstadoRuta estado) {this.estado = estado; }

    // Getter seguro para cantVisitas
    public int getCantVisitas() {
        return cantVisitas != null ? cantVisitas : 0;
    }

    public void setCantVisitas(Integer cantVisitas) {
        this.cantVisitas = cantVisitas;
    }

    public DtRutaVuelo toDto() {
        // Obtenemos solo los nombres de los vuelos
        List<String> nombresVuelos = this.vuelos.keySet().stream()
                .toList();

        return new DtRutaVuelo(
                this.nombre,
                this.descripcion,
                this.fechaAlta,
                this.costoBaseTurista,
                this.costoBaseEjecutivo,
                this.costoEquipajeExtra,
                this.origen != null ? this.origen.getNombre() : null,
                this.destino != null ? this.destino.getNombre() : null,
                this.aerolinea != null ? this.aerolinea.getNombre() : null,
                this.categoria != null ? this.categoria.getNombre() : null,
                nombresVuelos,
                this.urlImagen,
                this.urlVideo,
                this.descripcionCorta,
                this.estado,
                this.cantVisitas != null ? this.cantVisitas : 0

        );
    }

}


