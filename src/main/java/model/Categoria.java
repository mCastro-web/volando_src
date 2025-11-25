package model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Categorias")
@Access(AccessType.FIELD)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long identificador;
    private String nombre;

    public Categoria() {}

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdentificador() { return identificador; }
    public void setIdentificador(Long identificador) { this.identificador = identificador; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

   /* public DtCategoria toDto() {
        return new DtCategoria(this.getNombre());
    }*/
}
