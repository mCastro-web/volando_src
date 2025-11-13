package data_types;

public class DtAerolinea extends DtUsuario {
    private final String descripcion;
    private final String sitioWeb;
    public DtAerolinea(String nickname, String nombre, String email,  String urlImagen,
                       String descripcion, String sitioWeb) {
        super(nickname, nombre, email, urlImagen, "AEROLINEA");
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
    }

    public String getDescripcion() { return descripcion; }
    public String getSitioWeb() { return  sitioWeb; }
}

