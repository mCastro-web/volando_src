package data_types;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "DtPasaje")
@XmlType(name = "DtPasaje")
@XmlAccessorType(XmlAccessType.FIELD)
public class DtPasaje {

    private Long identificador;
    private String nombre;
    private String apellido;

    // Constructor vacío
    public DtPasaje() {
    }

    public DtPasaje(Long identificador, String nombre, String apellido) {
        this.identificador = identificador;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    // Getters
    public Long getId() {
        return identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    // Setters
    public void setId(Long identificador) {
        this.identificador = identificador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
