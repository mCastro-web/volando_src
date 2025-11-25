package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "dtUsuario", propOrder = {
        "nickname",
        "nombre",
        "email",
        "urlImagen",
        "tipo"
})
public class DtUsuario {
    private String nickname;
    private String nombre;
    private String email;
    private String urlImagen;
    private String tipo; // "CLIENTE" o "AEROLINEA"

    // Constructor sin argumentos requerido por JAXB
    public DtUsuario() {
    }

    public DtUsuario(String nickname, String nombre, String email, String urlImagen, String tipo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.urlImagen = urlImagen;
        this.tipo = tipo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
