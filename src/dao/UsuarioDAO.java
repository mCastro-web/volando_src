package dao;


import data_types.DtAerolinea;
import data_types.DtCliente;
import data_types.DtUsuario;
import data_types.EstadoUsuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import model.Aerolinea;
import model.Cliente;
import model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

import static data_types.EstadoUsuario.INGRESADO;

public class UsuarioDAO {

    private static UsuarioDAO instancia;
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("appPU");

    public UsuarioDAO() {}

    public static UsuarioDAO getInstance() {
        if (instancia == null) instancia = new UsuarioDAO();
        return instancia;
    }

    public List<String> listarNicknamesUsuarios() {
        EntityManager entityManager = emf.createEntityManager();
        try {
            return entityManager.createQuery("SELECT u.nickname FROM Usuario u", String.class)
                    .getResultList();
        } finally {
            entityManager.close();
        }
    }


    public DtUsuario dataUsuarioPorNick(String nickname) {
        if (nickname == null || nickname.isBlank()) {
            throw new IllegalArgumentException("El nickname es obligatorio.");
        }

        EntityManager entityManager = emf.createEntityManager(); // asumimos que tenés un 'emf' accesible aquí
        try {
            Usuario usuario = entityManager.find(Usuario.class, nickname);

            if (usuario == null) {
                throw new IllegalArgumentException("No existe el usuario con nickname: " + nickname);
            }

            if (usuario instanceof Cliente cliente) {
                return new DtCliente(
                        cliente.getNickname(),
                        cliente.getNombre(),
                        cliente.getEmail(),
                        cliente.getUrlImagen(),
                        cliente.getApellido(),
                        cliente.getFechaNac(),
                        cliente.getNacionalidad(),
                        cliente.getTipoDocumento(),
                        cliente.getNumDocumento()
                );
            } else if (usuario instanceof Aerolinea aerolinea) {
                return new DtAerolinea(
                        aerolinea.getNickname(),
                        aerolinea.getNombre(),
                        aerolinea.getEmail(),
                        aerolinea.getUrlImagen(),
                        aerolinea.getDescripcion(),
                        aerolinea.getSitioWeb()
                );
            } else {
                // Fallback genérico
                return new DtUsuario(usuario.getNickname(), usuario.getNombre(), usuario.getEmail(),  usuario.getUrlImagen(),  "USUARIO");
            }

        } finally {
            entityManager.close();
        }
    }



    public boolean existeUsuarioConEmail(String email) {
        EntityManager entityManager = emf.createEntityManager();
        try {
            Long count = entityManager.createQuery(
                    "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email",
                    Long.class
            ).setParameter("email", email).getSingleResult();
            return count > 0;
        } finally {
            entityManager.close();
        }
    }

    public DtUsuario iniciarSesion(String identificador, String passwordPlano) {
        if (identificador == null || identificador.isBlank() ||
                passwordPlano == null || passwordPlano.isBlank()) {
            throw new IllegalArgumentException("Faltan credenciales.");
        }

        EntityManager em = emf.createEntityManager();
        try {
            Usuario u = findByIdentificador(em, identificador.trim());

            if (u == null) {
                throw new IllegalArgumentException("Usuario no existe.");
            }

            if (!BCrypt.checkpw(passwordPlano, getPasswordHash(u))) {
                throw new IllegalArgumentException("Contraseña incorrecta.");
            }

            if (u.getEstado() == EstadoUsuario.CERRADO) {
                em.getTransaction().begin();
                try {
                    u.setEstado(INGRESADO);
                    em.getTransaction().commit();
                } catch (RuntimeException ex) {
                    em.getTransaction().rollback();
                    throw ex;
                }
            }

            return u.getDataUsuario();

        } catch (NoResultException e) {
            throw new IllegalArgumentException("Usuario no existe.");
        } finally {
            em.close();
        }
    }

    public DtUsuario cerrarSesion(String nick) {
        if (nick == null || nick.isBlank()) {
            throw new IllegalArgumentException("Nickname requerido.");
        }

        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<Usuario> query = entityManager.createQuery(
                    "SELECT u FROM Usuario u WHERE u.nickname = :nick", Usuario.class);
            query.setParameter("nick", nick);

            Usuario usuario = query.getSingleResult();

            entityManager.getTransaction().begin();
            try {
                if (usuario.getEstado() != EstadoUsuario.CERRADO) {
                    usuario.setEstado(EstadoUsuario.CERRADO);
                }
                entityManager.getTransaction().commit();
            } catch (PersistenceException ex) {
                entityManager.getTransaction().rollback();
                throw ex;
            }

            return usuario.getDataUsuario();

        } catch (NoResultException e) {
            throw new IllegalArgumentException("Usuario no existe.");
        } finally {
            entityManager.close();
        }
    }

    /** Busca por email (LOWER) o nickname (exacto). */
    private Usuario findByIdentificador(EntityManager entityManager, String identificador) {
        TypedQuery<Usuario> query = entityManager.createQuery(
                "SELECT u FROM Usuario u " +
                        "WHERE LOWER(u.email) = :idLower OR u.nickname = :idExact",
                Usuario.class
        );
        query.setParameter("idLower", identificador.toLowerCase());
        query.setParameter("idExact", identificador);
        return query.getSingleResult();
    }

    private String getPasswordHash(Usuario usuario) {
        return usuario.getContrasenia();
    }

    public DtUsuario registrarEstadoUsuario(Usuario usuario){

        EntityManager entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            usuario.setEstado(EstadoUsuario.INGRESADO);
            entityManager.getTransaction().commit();
        } catch (PersistenceException ex) {
            entityManager.getTransaction().rollback();
            throw ex;
        }

        return usuario.getDataUsuario();
    }


}
