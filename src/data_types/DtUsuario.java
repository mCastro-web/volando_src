package data_types;


public class DtUsuario {
    private final String nickname;
    private final String nombre;
    private final String email;
    private final String urlImagen;
    private final String tipo; // "CLIENTE" o "AEROLINEA"

    public DtUsuario(String nickname, String nombre, String email,  String urlImagen, String tipo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.email = email;
        this.urlImagen = urlImagen;
        this.tipo = tipo;
    }

    public String getNickname() { return nickname; }
    public String getNombre()   { return nombre; }
    public String getEmail()    { return email; }
    public String getUrlImagen() { return urlImagen; }
    public String getTipo()     { return tipo; }

}
