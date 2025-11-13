package model;

import data_types.DtCliente;
import data_types.DtUsuario;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import data_types.TipoDoc;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
@DiscriminatorValue("CLIENTE")
@Access(AccessType.FIELD)
public class Cliente extends Usuario {

    @Column(length =120)
    private String apellido;

    @Column(name = "fecha_nac")
    private LocalDate fechaNac;  // <-- reemplazo de DtFecha

    @Column(length = 80)
    private String nacionalidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_documento", length = 40)
    private TipoDoc tipoDocumento;

    @Column(name = "num_documento", length = 40)
    private String numDocumento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reserva> reserva = new ArrayList<>();


    /** Constructor sin args requerido por JPA */
    public Cliente() { }

    public Cliente(String nickname, String nombre, String correo, String contrasenia, String urlImagen,
                   String apellido, LocalDate fechaNac, String nacionalidad,
                   TipoDoc tipoDocumento, String numDocumento) {
        super(nickname, nombre, correo, contrasenia, urlImagen);
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.nacionalidad = nacionalidad;
        this.tipoDocumento = tipoDocumento;
        this.numDocumento = numDocumento;
    }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public LocalDate getFechaNac() { return fechaNac; }
    public void setFechaNac(LocalDate fechaNac) { this.fechaNac = fechaNac; }

    public String getNacionalidad() { return nacionalidad; }
    public void setNacionalidad(String nacionalidad) { this.nacionalidad = nacionalidad; }

    public TipoDoc getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(TipoDoc tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumDocumento() { return numDocumento; }
    public void setNumDocumento(String numDocumento) { this.numDocumento = numDocumento; }

    public List<Reserva> getReserva() { return reserva;    }
    public void setReserva(List<Reserva> reserva) { this.reserva = reserva; }

    @Override
    public DtUsuario getDataUsuario() {
        return new DtCliente(
                getNickname(),
                getNombre(),
                getEmail(),
                getUrlImagen(),
                getApellido(),
                getFechaNac(),
                getNacionalidad(),
                getTipoDocumento(),
                getNumDocumento()
        );
    }
}
