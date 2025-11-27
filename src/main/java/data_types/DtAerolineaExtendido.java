package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtAerolineaExtendida", propOrder = {
        "seguidores",
        "siguiendo",
        "rutasConfirmadas",
        "rutasIngresadas",
        "rutasRechazadas",
        "rutasFinalizadas"
})
public class DtAerolineaExtendido extends DtUsuarioExtendido {

    private String descripcion;
    private String sitioWeb;

    private List<String> rutasConfirmadas;
    private List<String> rutasIngresadas;
    private List<String> rutasRechazadas;
    private List<String> rutasFinalizadas;

    public DtAerolineaExtendido(
            String nickname, String nombre, String email, String urlImagen,
            String descripcion, String sitioWeb,
            List<String> seguidores, List<String> siguiendo,
            List<String> rutasConfirmadas,
            List<String> rutasIngresadas,
            List<String> rutasRechazadas,
            List<String> rutasFinalizadas
    ) {
        super(nickname, nombre, email, urlImagen, "AEROLINEA", seguidores, siguiendo);
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
        this.rutasConfirmadas = rutasConfirmadas;
        this.rutasIngresadas = rutasIngresadas;
        this.rutasRechazadas = rutasRechazadas;
        this.rutasFinalizadas = rutasFinalizadas;
    }
}


