package data_types;

public class DtPasaje {

    private Long identificador;
    private String nombre;
    private String apellido;

    public DtPasaje(Long identificador, String nombre, String apellido) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getters
    public Long getId() { return identificador; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
}
