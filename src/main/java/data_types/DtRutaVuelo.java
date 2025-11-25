package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "DtRutaVuelo")
@XmlType(name = "DtRutaVuelo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DtRutaVuelo {
    private String nombre;
    private String descripcion;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaAlta;

    private float costoBaseTurista;
    private float costoBaseEjecutivo;
    private float costoEquipajeExtra;
    private String origen;
    private String destino;
    private String aerolinea;
    private String categoria;
    private List<String> vuelos;
    private String urlImagen;
    private String urlVideo;
    private String descripcionCorta;
    private EstadoRuta estado;
    private Integer cantVisitas;

    // Constructor vacío
    public DtRutaVuelo() {
        this.vuelos = new ArrayList<>();
    }

    public DtRutaVuelo(
            String nombre,
            String descripcion,
            LocalDate fechaAlta,
            float costoBaseTurista,
            float costoBaseEjecutivo,
            float costoEquipajeExtra,
            String origen,
            String destino,
            String aerolinea,
            String categoria,
            List<String> vuelos,
            String urlImagen,
            String urlVideo,
            String descripcionCorta,
            EstadoRuta estado,
            Integer cantVisitas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaAlta = fechaAlta;
        this.costoBaseTurista = costoBaseTurista;
        this.costoBaseEjecutivo = costoBaseEjecutivo;
        this.costoEquipajeExtra = costoEquipajeExtra;
        this.origen = origen;
        this.destino = destino;
        this.aerolinea = aerolinea;
        this.categoria = categoria;
        this.vuelos = vuelos;
        this.urlImagen = urlImagen;
        this.urlVideo = urlVideo;
        this.descripcionCorta = descripcionCorta;
        this.estado = estado;
        this.cantVisitas = cantVisitas;
    }

    // Getters
    public String getUrlVideo() {
        return urlVideo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public float getCostoBaseTurista() {
        return costoBaseTurista;
    }

    public float getCostoBaseEjecutivo() {
        return costoBaseEjecutivo;
    }

    public float getCostoEquipajeExtra() {
        return costoEquipajeExtra;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public String getCategoria() {
        return categoria;
    }

    public List<String> getVuelos() {
        return vuelos;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public EstadoRuta getEstado() {
        return estado;
    }

    public Integer getCantVisitas() {
        return cantVisitas != null ? cantVisitas : 0;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public void setCostoBaseTurista(float costoBaseTurista) {
        this.costoBaseTurista = costoBaseTurista;
    }

    public void setCostoBaseEjecutivo(float costoBaseEjecutivo) {
        this.costoBaseEjecutivo = costoBaseEjecutivo;
    }

    public void setCostoEquipajeExtra(float costoEquipajeExtra) {
        this.costoEquipajeExtra = costoEquipajeExtra;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setVuelos(List<String> vuelos) {
        this.vuelos = vuelos;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public void setEstado(EstadoRuta estado) {
        this.estado = estado;
    }

    public void setCantVisitas(Integer cantVisitas) {
        this.cantVisitas = cantVisitas;
    }
}