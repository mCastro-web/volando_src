package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import model.ItemPaquete;
import model.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data type for PaqueteVuelo
 */
@XmlRootElement(name = "DtPaqueteVuelo")
@XmlType(name = "DtPaqueteVuelo")
@XmlAccessorType(XmlAccessType.FIELD)
public class DtPaqueteVuelo {

    private String nombre;
    private String descripcion;
    private int diasValidez;
    private float descuento;
    private float costo;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate altaFecha;

    private List<ItemPaquete> items;
    private List<Reserva> reservas;

    // Constructor sin argumentos requerido por JAXB
    public DtPaqueteVuelo() {
        this.items = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    // Constructor con 5 parámetros
    public DtPaqueteVuelo(String nombre, String descripcion, int diasValidez, float descuento, float costo, LocalDate altaFecha) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.diasValidez = diasValidez;
        this.descuento = descuento;
        this.costo = costo;
        this.altaFecha = altaFecha;
        this.items = new ArrayList<>();
        this.reservas = new ArrayList<>();
    }

    // Constructor completo con 8 parámetros
    public DtPaqueteVuelo(String nombre, String descripcion, int diasValidez, float descuento, float costo,
            LocalDate altaFecha, List<ItemPaquete> items, List<Reserva> reservas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.diasValidez = diasValidez;
        this.descuento = descuento;
        this.costo = costo;
        this.altaFecha = altaFecha;
        this.items = items != null ? items : new ArrayList<>();
        this.reservas = reservas != null ? reservas : new ArrayList<>();
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getDiasValidez() {
        return diasValidez;
    }

    public float getDescuento() {
        return descuento;
    }

    public float getCosto() {
        return costo;
    }

    public LocalDate getAltaFecha() {
        return altaFecha;
    }

    public List<ItemPaquete> getItems() {
        return items;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    // Setters requeridos por JAXB
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setDiasValidez(int diasValidez) {
        this.diasValidez = diasValidez;
    }

    public void setDescuento(float descuento) {
        this.descuento = descuento;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public void setAltaFecha(LocalDate altaFecha) {
        this.altaFecha = altaFecha;
    }

    public void setItems(List<ItemPaquete> items) {
        this.items = items;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }
}
