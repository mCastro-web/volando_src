package data_types;

import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import data_types.LocalDateAdapter;
import data_types.LocalTimeAdapter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtCheckin")
public class DtCheckin {

    private String nombreCliente;
    private String apellidoCliente;

    private String nombreVuelo;
    private String nombreRuta;
    private String ciudadOrigen;
    private String ciudadDestino;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate fechaVuelo;

    @XmlElementWrapper(name="pasajeros")
    @XmlElement(name="pasajero")
    private List<DtPasajeCheckin> pasajeros;

    @XmlJavaTypeAdapter(LocalTimeAdapter.class)
    private LocalTime horaEmbarque;

    public DtCheckin() {}

    public DtCheckin(String nombreCliente, String apellidoCliente,
                     String nombreVuelo, String nombreRuta,
                     String ciudadOrigen, String ciudadDestino,
                     LocalDate fechaVuelo,
                     List<DtPasajeCheckin> pasajeros,
                     LocalTime horaEmbarque) {

        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.nombreVuelo = nombreVuelo;
        this.nombreRuta = nombreRuta;
        this.ciudadOrigen = ciudadOrigen;
        this.ciudadDestino = ciudadDestino;
        this.fechaVuelo = fechaVuelo;
        this.pasajeros = pasajeros;
        this.horaEmbarque = horaEmbarque;
    }

    // Getters
    public String getNombreCliente() { return nombreCliente; }
    public String getApellidoCliente() { return apellidoCliente; }
    public String getNombreVuelo() { return nombreVuelo; }
    public String getNombreRuta() { return nombreRuta; }
    public String getCiudadOrigen() { return ciudadOrigen; }
    public String getCiudadDestino() { return ciudadDestino; }
    public LocalDate getFechaVuelo() { return fechaVuelo; }
    public List<DtPasajeCheckin> getPasajeros() { return pasajeros; }
    public LocalTime getHoraEmbarque() { return horaEmbarque; }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "dtPasajeCheckin")
    public static class DtPasajeCheckin {

        private String nombre;
        private String apellido;
        private String tipoAsiento;

        public DtPasajeCheckin() {}

        public DtPasajeCheckin(String nombre, String apellido, String tipoAsiento) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.tipoAsiento = tipoAsiento;
        }

        public String getNombre() { return nombre; }
        public String getApellido() { return apellido; }
        public String getTipoAsiento() { return tipoAsiento; }
    }
}
