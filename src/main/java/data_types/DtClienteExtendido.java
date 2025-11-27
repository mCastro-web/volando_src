package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.time.LocalDate;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtClienteExtendido", propOrder = {
        "seguidores",
        "siguiendo",
        "reservas",
        "paquetes"
})
public class DtClienteExtendido extends DtUsuarioExtendido {

    private String apellido;
    private LocalDate fechaNacimiento;
    private String nacionalidad;
    private TipoDoc tipoDoc;
    private String numeroDoc;

    private List<DtReserva> reservas;
    private List<String> paquetes;

    public DtClienteExtendido(
            String nickname, String nombre, String email, String urlImagen,
            String apellido, LocalDate fechaNacimiento, String nacionalidad,
            TipoDoc tipoDoc, String numeroDoc,
            List<String> seguidores, List<String> siguiendo,
            List<DtReserva> reservas, List<String> paquetes
    ) {
        super(nickname, nombre, email, urlImagen, "CLIENTE", seguidores, siguiendo);
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.nacionalidad = nacionalidad;
        this.tipoDoc = tipoDoc;
        this.numeroDoc = numeroDoc;
        this.reservas = reservas;
        this.paquetes = paquetes;
    }
}

