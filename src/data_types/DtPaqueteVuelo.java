package data_types;

import model.ItemPaquete;
import model.Reserva;

import java.time.LocalDate;
import java.util.List;

/**
 * @param items    DTO de los ítems
 * @param reservas reservas asociadas al paquete
 */
public record DtPaqueteVuelo(String nombre, String descripcion, int diasValidez, float descuento, float costo,
                             LocalDate altaFecha, List<model.ItemPaquete> items, List<model.Reserva> reservas) {
    // Constructor

    public DtPaqueteVuelo(String nombre, String descripcion, int diasValidez, float descuento, LocalDate altaFecha) {
        this(nombre, descripcion, diasValidez, descuento, 0f, altaFecha, List.of(), List.of());
    }


    public String getNombre() {
        return nombre;
    }

    @Override
    public String descripcion() {
        return descripcion;
    }

    @Override
    public int diasValidez() {
        return diasValidez;
    }

    @Override
    public float descuento() {
        return descuento;
    }

    @Override
    public float costo() {
        return costo;
    }

    @Override
    public LocalDate altaFecha() {
        return altaFecha;
    }

    @Override
    public List<ItemPaquete> items() {
        return items;
    }

    @Override
    public List<Reserva> reservas() {
        return reservas;
    }
}
