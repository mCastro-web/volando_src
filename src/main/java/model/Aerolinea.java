package model;

import data_types.DtAerolinea;
import data_types.DtUsuario;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.MapKey;
import jakarta.persistence.CascadeType;
import java.util.HashMap;
import java.util.Map;

@Entity
@DiscriminatorValue("AEROLINEA")
@Access(AccessType.FIELD)
public class Aerolinea extends Usuario {

    @Column(length = 300)
    private String descripcion;

    @Column(length = 200)
    private String sitioWeb;

    // Relación con rutas de vuelo (clave: nombre de ruta)
    @OneToMany(mappedBy = "aerolinea", cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "nombre") // usa el campo "nombre" de RutaVuelo como clave del Map
    private final Map<String, RutaVuelo> rutaVuelos = new HashMap<>();

    /** Constructor vacío requerido por JPA */
    public Aerolinea() { }

    public Aerolinea(String nickname, String nombre, String correo, String contrasenia, String urlImagen, String descripcion, String sitioWeb) {
        super(nickname, nombre, correo, contrasenia, urlImagen);
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

   /* public Collection<RutaVuelo> getRutaVuelo() {
        return new ArrayList<>(rutaVuelos.values());
    }*/



    @Override
    public DtUsuario getDataUsuario() {
        return new DtAerolinea(
                getNickname(),
                getNombre(),
                getEmail(),
                getUrlImagen(),
                getDescripcion(),
                getSitioWeb()
        );
    }
}
