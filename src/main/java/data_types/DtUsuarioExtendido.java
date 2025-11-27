package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtUsuarioExtendido", propOrder = {
        "seguidores",
        "siguiendo"
})
public class DtUsuarioExtendido extends DtUsuario {

    private List<String> seguidores;
    private List<String> siguiendo;

    public DtUsuarioExtendido() {}

    public DtUsuarioExtendido(String nickname, String nombre, String email,
                              String urlImagen, String tipo,
                              List<String> seguidores, List<String> siguiendo) {
        super(nickname, nombre, email, urlImagen, tipo);
        this.seguidores = seguidores;
        this.siguiendo = siguiendo;
    }

    public List<String> getSeguidores() { return seguidores; }
    public List<String> getSiguiendo() { return siguiendo; }
}
