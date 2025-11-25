package model;

import data_types.DtUsuario;
import data_types.EstadoUsuario;
import static data_types.EstadoUsuario.INGRESADO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.AccessType;
import jakarta.persistence.Access;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Inheritance;

@Entity
@Table(
        name = "Usuarios",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_usuarios_email", columnNames = {"email"})
        }
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", length = 16)
@Access(AccessType.FIELD)
public abstract class Usuario {

    @Id
    @Column(name = "nickname", nullable = false, updatable = false, length = 100)
    private String nickname;   // PK natural

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "email", nullable = false, length = 180)
    private String email;

    @Column(name = "contrasenia", nullable = false, length = 255)
    private String contrasenia;

    @Column(name = "imagen")
    private String urlImagen;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoUsuario estado = INGRESADO;

    /** Constructor requerido por JPA (debe ser protected o public y sin args) */
    protected Usuario() { }

    public Usuario(String nickname, String nombre, String email, String contrasenia, String urlImagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.contrasenia = contrasenia;
        this.urlImagen = urlImagen;

    }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) {this.nickname = nickname;  }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getContrasenia() { return contrasenia; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    public abstract DtUsuario getDataUsuario();


    public EstadoUsuario getEstado() {return estado; }

    public void setEstado(EstadoUsuario estado) {this.estado = estado; }
}

