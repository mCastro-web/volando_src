package data_types;
import java.time.LocalDate;
import java.time.LocalTime;


public class DtDatosVueloR{
    private final String nombre;
    private final LocalDate fecha;
    private final LocalTime duracion;
    private final int asientosTurista;
    private final int asientosEjecutivo;
    private final LocalDate fechaAlta;
    private final String origen;
    private final String destino;
    private final float costoEjecutivo;
    private final float costoTurista;
    private final float costoEquipajeExtra;


    public DtDatosVueloR(
            String nombre,
            LocalDate fecha,
            LocalTime duracion,
            int asientosTurista,
            int asientosEjecutivo,
            LocalDate fechaAlta,
            String origen,
            String destino,
            float costoEjecutivo,
            float costoTurista,
            float costoEquipajeExtra
    ) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.duracion = duracion;
        this.asientosTurista = asientosTurista;
        this.asientosEjecutivo = asientosEjecutivo;
        this.fechaAlta = fechaAlta;
        this.origen = origen;
        this.destino = destino;
        this.costoEjecutivo = costoEjecutivo;
        this.costoTurista = costoTurista;
        this.costoEquipajeExtra = costoEquipajeExtra;
    }


    // Getters.


    public float getCostoEjecutivo() {return costoEjecutivo; }
    public float getCostoTurista() {return costoTurista; }
    public float getCostoEquipajeExtra() {return costoEquipajeExtra; }
    public String getNombre() {return nombre; }
    public LocalDate getFecha() {return fecha; }
    public LocalTime getDuracion() {return duracion; }
    public int getAsientosTurista() {return asientosTurista; }
    public int getAsientosEjecutivo() {return asientosEjecutivo; }
    public LocalDate getFechaAlta() {return fechaAlta; }
    public String getOrigen() {return origen; }
    public String getDestino() {return destino; }
}

