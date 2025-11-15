package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import data_types.TipoDoc;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("SOCIAL")
@Access(AccessType.FIELD)
public class Social {

    @Id
    @Column(name = "idSeguidor", nullable = false, updatable = false, length = 100)
    String idSeguidor;

    @Id
    @Column(name = "idSeguido", nullable = false, updatable = false, length = 100)
    String idSeguido;

    protected Social() {};

    public Social(String idSeguidor, String idSeguido) {
        this.idSeguidor = idSeguidor;
        this.idSeguido = idSeguido;
    }

    public String getIdSeguidor() {
        return idSeguidor;
    }

    public void setIdSeguidor(String idSeguidor) {
        this.idSeguidor = idSeguidor;
    }

    public String getIdSeguido() {
        return idSeguido;
    }

    public void setIdSeguido(String idSeguido) {
        this.idSeguido = idSeguido;
    }
}
