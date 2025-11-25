package data_types;

import java.time.LocalDate;

public class DtCliente extends DtUsuario {
    private final String apellido;
    private final LocalDate fechaNacimiento;
    private final String nacionalidad;
    private final TipoDoc tipoDoc;   // texto para mostrar
    private final String numeroDoc;

    public DtCliente(String nickname, String nombre, String email, String urlImagen,
                     String apellido, LocalDate fechaNacimiento, String nacionalidad,
                     TipoDoc tipoDoc, String numeroDoc) {
        super(nickname, nombre, email, urlImagen, "CLIENTE");
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDoc = tipoDoc;
        this.numeroDoc = numeroDoc;
    }

    public String getApellido() { return apellido; }
    public LocalDate getFechaNacimiento() {;
        return fechaNacimiento; }
    public String getNacionalidad() { return nacionalidad; }
    public TipoDoc getTipoDoc() { return tipoDoc; }
    public String getNumeroDoc() { return numeroDoc; }
}
